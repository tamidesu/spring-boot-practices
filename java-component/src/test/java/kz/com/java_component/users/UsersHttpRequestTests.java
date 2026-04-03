package kz.com.java_component.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersHttpRequestTests {

    private static final String USERS_PATH = "/users";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void indexPageShouldReturnHeaderOneContent() {
        String html = restTemplate
                .withBasicAuth("manager@email.com", "aw2s0meR!")
                .getForObject("/", String.class);
        assertThat(html).isNotNull();
    }

    @Test
    void usersEndPointShouldReturnCollectionWithTwoUsers() {
        Collection<?> response = restTemplate
                .withBasicAuth("manager@email.com", "aw2s0meR!")
                .getForObject(USERS_PATH, Collection.class);
        assertThat(response.size()).isGreaterThan(1);
    }

    @Test
    void userEndPointPostNewUserShouldReturnUser() {
        var user = new User(
                "dummy@email.com", "Dummy",
                "https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
                "SomeAw2s0meR!",
                List.of(UserRole.USER),
                true
        );

        User response = restTemplate
                .withBasicAuth("manager@email.com", "aw2s0meR!")
                .postForObject(USERS_PATH, user, User.class);

        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo(user.getEmail());

        Collection<?> users = restTemplate
                .withBasicAuth("manager@email.com", "aw2s0meR!")
                .getForObject(USERS_PATH, Collection.class);
        assertThat(users.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void userEndPointDeleteUserShouldReturnVoid() {
        restTemplate
                .withBasicAuth("manager@email.com", "aw2s0meR!")
                .delete(USERS_PATH + "/norma@email.com");

        Collection<?> users = restTemplate
                .withBasicAuth("manager@email.com", "aw2s0meR!")
                .getForObject(USERS_PATH, Collection.class);
        assertThat(users.size()).isLessThanOrEqualTo(2);
    }

    @Test
    void userEndPointFindUserShouldReturnUser() {
        User user = restTemplate
                .withBasicAuth("manager@email.com", "aw2s0meR!")
                .getForObject(USERS_PATH + "/ximena@email.com", User.class);

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("ximena@email.com");
    }
}
