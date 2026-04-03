package kz.com.java_component.users;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static kz.com.java_component.users.UserGravatar.getGravatarUrlFromEmail;
import static org.assertj.core.api.Assertions.*;

@JsonTest
public class UserJsonTests {

    @Autowired
    private JacksonTester<User> jacksonTester;

    @Test
    void serializeUserJsonTest() throws IOException {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        User user = UserBuilder.createUser(validator)
                .withEmail("dummy@email.com")
                .withPassword("aw2s0me")
                .withName("Dummy")
                .withRoles(UserRole.USER)
                .active()
                .build();

        var json = jacksonTester.write(user);

        assertThat(json).extractingJsonPathValue("$.email")
                .isEqualTo("dummy@email.com");
        assertThat(json).extractingJsonPathArrayValue("$.userRole").size()
                .isEqualTo(1);
        assertThat(json).extractingJsonPathBooleanValue("$.active")
                .isTrue();
        assertThat(json).extractingJsonPathValue("$.gravatarUrl")
                .isNotNull();
        assertThat(json).extractingJsonPathValue("$.gravatarUrl")
                .isEqualTo(getGravatarUrlFromEmail(user.getEmail()));
    }

    @Test
    void serializeUserJsonFileTest() throws IOException {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        User user = UserBuilder.createUser(validator)
                .withEmail("dummy@email.com")
                .withPassword("aw2s0me")
                .withName("Dummy")
                .withRoles(UserRole.USER)
                .active()
                .build();

        var json = jacksonTester.write(user);
        // Needs src/test/resources/kz/com/java_component/users/user.json
        assertThat(json).isEqualToJson("user.json");
    }

    @Test
    void deserializeUserJsonTest() throws IOException {
        String userJson = """
                {
                  "email": "dummy@email.com",
                  "name": "Dummy",
                  "password": "aw2s0me",
                  "userRole": ["USER"],
                  "active": true
                }
                """;

        User user = jacksonTester.parseObject(userJson);
        assertThat(user.getEmail()).isEqualTo("dummy@email.com");
        assertThat(user.getPassword()).isEqualTo("aw2s0me");
        assertThat(user.isActive()).isTrue();
    }

    @Test
    void userValidationTest() {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();

        // AssertJ style
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() ->
                        UserBuilder.createUser(validator)
                                .withEmail("dummy@email.com")
                                .withName("Dummy")
                                .withRoles(UserRole.USER)
                                .active()
                                .build()  // missing password → should throw
                );

        // JUnit 5 style
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> UserBuilder.createUser(validator)
                        .withName("Dummy")
                        .withRoles(UserRole.USER)
                        .active()
                        .build()  // missing email AND password
        );
        assertThat(exception.getMessage()).contains("email");
    }
}
