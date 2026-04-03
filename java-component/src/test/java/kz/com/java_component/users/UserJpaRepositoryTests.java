package kz.com.java_component.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Import(UserConfiguration.class)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserJpaRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest");

    @Test
    void findAllTest() {
        var users = userRepository.findAll();
        assertThat(users).isNotEmpty();
        assertThat(users).isInstanceOf(Iterable.class);
        assertThat(users).element(0).isInstanceOf(User.class);
        assertThat(users).element(0).matches(User::isActive);
    }

    @Test
    void saveTest() {
        User dummy = UserBuilder.createUser()
                .withName("Dummy").withEmail("dummy@email.com")
                .active().withRoles(UserRole.INFO).withPassword("aw3s0m3R!")
                .build();

        User saved = userRepository.save(dummy);
        assertThat(saved).isNotNull();
        assertThat(saved).isInstanceOf(User.class);
        assertThat(saved.isActive()).isTrue();
    }

    @Test
    void findByIdTest() {
        var user = userRepository.findById("norma@email.com");
        assertThat(user).isNotNull();
        assertThat(user.get()).isInstanceOf(User.class);
        assertThat(user.get().isActive()).isTrue();
        assertThat(user.get().getName()).isEqualTo("Norma");
    }

    @Test
    void deleteByIdTest() {
        var user = userRepository.findById("ximena@email.com");
        assertThat(user).isNotNull();
        assertThat(user.get().getName()).isEqualTo("Ximena");

        userRepository.deleteById("ximena@email.com");

        var deleted = userRepository.findById("ximena@email.com");
        assertThat(deleted).isEmpty();
    }
}
