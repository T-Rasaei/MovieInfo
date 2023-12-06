package com.moviedb.movieinfo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviedb.movieinfo.domain.AuthorizeRequest;
import com.moviedb.movieinfo.domain.UserResponse;
import com.moviedb.movieinfo.jwt.JwtAuthenticationFilter;
import com.moviedb.movieinfo.jwt.JwtService;
import com.moviedb.movieinfo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private UserResponse userResponse;
    private AuthorizeRequest authorizeRequest;

    @BeforeEach
    void setUp() {
        userResponse = UserResponse.builder()
                .userName("test")
                .accessToken("token")
                .build();
        authorizeRequest = AuthorizeRequest.builder()
                .username("test")
                .password("test")
                .build();
    }

    @Test
    void checkCreate() throws Exception {
        when(userService.createUser(any())).thenReturn(userResponse);
        this.mockMvc.perform(post(UserController.BASE_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName",is(userResponse.getUserName())));
    }

    @Test
    void checkAuthenticate() throws Exception {
        when(userService.authorize(any())).thenReturn(userResponse);
        this.mockMvc.perform(post(UserController.BASE_URL + "/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorizeRequest)))
                .andExpect(status().isOk());
    }
}