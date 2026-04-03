//package kz.com.my_retro;
//
//import kz.com.my_retro.client.User;
//import kz.com.my_retro.client.UsersClient;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class UsersClientTest {
//
//    @Autowired
//    UsersClient usersClient;
//
//    @Test
//    void findUserTest() {
//        User user = usersClient.findUserByEmail("norma@email.com");
//        Assertions.assertThat(user).isNotNull();
//        Assertions.assertThat(user.name()).isEqualTo("Norma");
//        Assertions.assertThat(user.email()).isEqualTo("norma@email.com");
//    }
//}
