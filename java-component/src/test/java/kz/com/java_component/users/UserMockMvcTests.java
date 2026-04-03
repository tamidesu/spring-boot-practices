package kz.com.java_component.users;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
@ActiveProfiles("mockMvc")
public class UserMockMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createUserTests() throws Exception {
        String location = mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .contentType("application/json")
                                .content("""
                                {
                                    "email": "dummy@email.com",
                                    "name": "Dummy",
                                    "password": "aw2s0meR!",
                                    "gravatarUrl": "https://www.gravatar.com/avatar/fb651279f4712e209991e05610dfb03a?d=wavatar",
                                    "userRole": ["USER"],
                                    "active": true
                                }
                                """)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(MockMvcRequestBuilders.get(location))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true));
    }

    @Test
    void allUsersTests() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name")
                        .value("Dummy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$..active")
                        .value(Matchers.hasItem(true)));
    }
}
