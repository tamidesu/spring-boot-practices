//package kz.com.java_component;
//
//import kz.com.java_component.users.User;
//import kz.com.java_component.users.UserRole;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//
//import java.net.URI;
//import java.util.List;
//import java.util.Map;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class JavaComponentApplicationTests {
//
//	@Value("${local.server.port}")
//	private int port;
//
//	@Autowired
//	private TestRestTemplate restTemplate;
//
//	private String baseUrl() {
//		return "http://localhost:" + port;
//	}
//
//	@Test
//	void indexPageShouldReturnHeaderOneContent() {
//		String body = restTemplate.getForObject(baseUrl() + "/", String.class);
//
//		Assertions.assertThat(body)
//				.contains("Simple Users Rest Application");
//	}
//
//	@Test
//	void usersEndPointShouldReturnCollectionWithTwoUsers() {
//		// В Java корректнее достать как массив, либо как List<Map> (из-за erasure).
//		User[] users = restTemplate.getForObject(baseUrl() + "/users", User[].class);
//
//		Assertions.assertThat(users).isNotNull();
//		Assertions.assertThat(users.length).isEqualTo(2);
//	}
//
//	@Test
//	void userEndPointPostNewUserShouldReturnUser() {
//		User user = new User(
//				"dummy@email.com",
//				"Dummy",
//				null,
//				"aw2s0meR!",
//				java.util.List.of(UserRole.USER),
//				true
//		);
//
//		User created = restTemplate.postForObject(baseUrl() + "/users", user, User.class);
//
//		Assertions.assertThat(created).isNotNull();
//		Assertions.assertThat(created.email()).isEqualTo(user.email());
//
//		User[] users = restTemplate.getForObject(baseUrl() + "/users", User[].class);
//		Assertions.assertThat(users).isNotNull();
//		Assertions.assertThat(users.length).isGreaterThanOrEqualTo(2);
//	}
//
//	@Test
//	void userEndPointDeleteUserShouldReturnVoid() {
//		// email содержит '@' — это ок в path, но лучше строить URI через builder/encode
//		URI uri = URI.create(baseUrl() + "/users/norma@email.com");
//		restTemplate.delete(uri);
//
//		User[] users = restTemplate.getForObject(baseUrl() + "/users", User[].class);
//
//		Assertions.assertThat(users).isNotNull();
//		Assertions.assertThat(users.length).isLessThanOrEqualTo(2);
//	}
//
//	@Test
//	void userEndPointFindUserShouldReturnUser() {
//		User user = restTemplate.getForObject(
//				baseUrl() + "/users/ximena@email.com",
//				User.class
//		);
//
//		Assertions.assertThat(user).isNotNull();
//		Assertions.assertThat(user.email()).isEqualTo("ximena@email.com");
//	}
//}
