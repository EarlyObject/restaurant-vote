package com.sar.ws.ui.controller;

import com.sar.ws.SpringApplicationContext;
import com.sar.ws.io.repositories.RoleRepository;
import com.sar.ws.io.repositories.UserRepository;
import com.sar.ws.shared.dto.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@Disabled
class UserControllerIntegrationTest {


    @Autowired
    private SpringApplicationContext context;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;


    @BeforeEach
    void setUp() {

    }

    @Test
    void createUser() throws Exception {
        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
        String user = "{\"firstName\" : \"User\",\n" +
                "\t\"lastName\"  : \"TestUser\",\n" +
                "\t\"email\"\t\t: \"test@test.com\",\n" +
                "\t\"password\"  : \"123\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(textPlainUtf8));
    }

    @Test
    void getUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void postVote() {
    }

    @Test
    void getVotes() {
    }
}