package br.com.santander.userservice.controller;

import br.com.santander.userservice.dto.UserDto;
import br.com.santander.userservice.dto.UserInputDto;
import br.com.santander.userservice.entities.Role;
import br.com.santander.userservice.exception.UserNotFoundException;
import br.com.santander.userservice.services.UserServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserServices userServices;

    @Test
    void createUserRoleClient() throws Exception {
        UserInputDto userInputDto = getUserInputDto();
        ObjectMapper mapper = new ObjectMapper();

        Mockito.when(userServices.createUserRoleClient(userInputDto)).thenReturn(userDto());
        System.out.println(mapper.writeValueAsString(userInputDto));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .content(mapper.writeValueAsString(userInputDto)).contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"name\":\"Admin\",\"status\":false,\"role\":\"ADMIN\"}"));
    }

    @Test
    void itShouldReturnBaqRequestWhenCreateUserBodyIsEmpty() throws Exception {
        UserInputDto userInputDto = getUserInputDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json("{\"type\":\"about:blank\",\"title\":\"Bad Request\",\"status\":400,\"detail\":\"Failed to read request\",\"instance\":\"/api/v1/users\"}"));
    }

    @Test
    void itShouldReturnStatusOKWhenFindByEmail() throws Exception {
        // given
        String email = "admin@culysoft.com";
        Mockito.when(userServices.findByEmail(email)).thenReturn(userDto());

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/email/" + email))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"name\":\"Admin\",\"status\":false,\"role\":\"ADMIN\"}"));
    }

    @Test
    void itShouldReturnThrowWhenFindByEmail() throws Exception {
        // given
        String email = "admin@culysoft.com";
        Mockito.when(userServices.findByEmail(email)).thenThrow(UserNotFoundException.class);

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/email/" + email))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private UserDto userDto() {
        UserDto userDto = new UserDto();

        userDto.setName("Admin");
        userDto.setRole(Role.ADMIN);

        return userDto;
    }

    private UserInputDto getUserInputDto() {
        UserInputDto userInputDto = new UserInputDto();

        userInputDto.setName("Client");
        userInputDto.setEmail("client@culysoft.com");
        userInputDto.setPassword("1234");

        return userInputDto;
    }
}