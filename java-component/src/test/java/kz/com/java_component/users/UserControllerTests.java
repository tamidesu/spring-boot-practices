package kz.com.java_component.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = UsersController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    void allUsersTest() throws Exception {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(
                UserBuilder.createUser().withName("Ximena").withEmail("ximena@email.com")
                        .active().withRoles(UserRole.USER, UserRole.ADMIN).withPassword("aw3s0m3R!").build(),
                UserBuilder.createUser().withName("Norma").withEmail("norma@email.com")
                        .active().withRoles(UserRole.USER).withPassword("aw3s0m3R!").build()
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].active").value(true));
    }

    @Test
    void newUserTest() throws Exception {
        User user = UserBuilder.createUser().withName("Dummy").withEmail("dummy@email.com")
                .active().withRoles(UserRole.USER, UserRole.ADMIN).withPassword("aw3s0m3R!").build();

        Mockito.when(userRepository.save(user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(toJson(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("dummy@email.com"));
    }

    @Test
    void findUserByEmailTest() throws Exception {
        User user = UserBuilder.createUser().withName("Dummy").withEmail("dummy@email.com")
                .active().withRoles(UserRole.USER, UserRole.ADMIN).withPassword("aw3s0m3R!").build();

        Mockito.when(userRepository.findById(user.getEmail())).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{email}", user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("dummy@email.com"));
    }

    @Test
    void deleteUserByEmailTest() throws Exception {
        User user = UserBuilder.createUser().withEmail("dummy@email.com").build();

        Mockito.doNothing().when(userRepository).deleteById(user.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{email}", user.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private static String toJson(Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
