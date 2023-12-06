package com.moviedb.movieinfo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviedb.movieinfo.controller.UserController;
import com.moviedb.movieinfo.domain.*;
import com.moviedb.movieinfo.repository.TokenRepository;
import com.moviedb.movieinfo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@WithMockUser
@EnableTransactionManagement
class UserIntegrationTest implements IntegrationTest{
    private final String baseUrl = "http://localhost";
    @LocalServerPort
    private int port;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private UserRequest userRequest;
    private AuthorizeRequest authorizeRequest;


    @BeforeEach
    void setUp() {
        userRequest = UserRequest.builder()
                .userName("test")
                .password("test")
                .build();
        authorizeRequest = AuthorizeRequest.builder()
                .username("test")
                .password("test")
                .build();
    }

    @AfterEach
    void afterEach(){
        tokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void checkCreateUser() throws Exception {
        this.mockMvc.perform(post(baseUrl + ":" + port + UserController.BASE_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName",is(userRequest.getUserName())));
    }

    @Test
    void checkAuthorize() throws Exception {
        userRepository.save(User.builder()
                .userName(userRequest.getUserName())
                .password(new BCryptPasswordEncoder().encode(userRequest.getPassword()))
                .role(Role.USER)
                .build());
        this.mockMvc.perform(post(baseUrl + ":" + port + UserController.BASE_URL + "/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorizeRequest)))
                .andExpect(status().isOk());
    }
}
