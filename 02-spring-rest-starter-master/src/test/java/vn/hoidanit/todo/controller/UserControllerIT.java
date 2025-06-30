package vn.hoidanit.todo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import vn.hoidanit.todo.entity.User;
import vn.hoidanit.todo.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createUser_shouldReturnUser_whenValid() throws Exception {
        //arrange
        User inputUser = new User(null, "hoidanit", "hoidangit@gmail.com");
        //action
        String resultStr = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(inputUser)))
                        .andExpect(status().isCreated()).andReturn()
                        .getResponse().getContentAsString();
        //assert
        User outputUser = objectMapper.readValue(resultStr, User.class);
        assertEquals(inputUser.getName(), outputUser.getName());

    }

    @Test
    public void getUserById_shouldEmpty_whenIdNotFound() throws Exception {
        //arrange

        //action
        this.mockMvc.perform(get("/users/{id}", 0)).andExpect(status().isNotFound());

        //assert
    }


    //    @Test
//    public void name() throws Exception {
//        //arrange
//
//        //action
//
//        //assert
//    }
//

        @Test
    public void deleteUser() throws Exception {
        //arrange
        this.userRepository.deleteAll();
        User inputUser = new User(null, "name-delete", "<EMAIL>");
        User userInput = this.userRepository.saveAndFlush(inputUser);

        //action
        this.mockMvc.perform(delete("/users/{id}", userInput.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
        //assert
            assertEquals(0, this.userRepository.count());
    }


    @Test
    public void getUserById() throws Exception {
        //arrange
        this.userRepository.deleteAll();
        User inputUser = new User(null, "name-by-id", "<EMAIL1>");

        User userInput = this.userRepository.saveAndFlush(inputUser);

        //action
        String resultStr = this.mockMvc.perform(get("/users/{id}", userInput.getId()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        User userOutput = this.objectMapper.readValue(resultStr, User.class);
        //assert
        assertEquals(userInput.getId(), userOutput.getId());
        assertEquals("name-by-id", userOutput.getName());
    }

    @Test
    public void getAllUsers() throws Exception {
        //arrange
        this.userRepository.deleteAll();
        User user1 = new User(null, "name1", "<EMAIL1>");
        User user2 = new User(null, "name2", "<EMAIL2>");

        List<User> data = List.of(user1, user2);

        this.userRepository.saveAll(data);

        //action
        String resultStr = this.mockMvc.perform(get("/users")).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<User> result = this.objectMapper.readValue(resultStr, new TypeReference<List<User>>() {});

        //assert
        assertEquals(2, result.size());
        assertEquals("<EMAIL1>", result.get(0).getEmail() );

    }
}
