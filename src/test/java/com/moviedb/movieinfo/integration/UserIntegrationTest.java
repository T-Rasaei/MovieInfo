package com.moviedb.movieinfo.integration;

import com.moviedb.movieinfo.controller.UserController;
import com.moviedb.movieinfo.domain.*;
import com.moviedb.movieinfo.repository.TokenRepository;
import com.moviedb.movieinfo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@EnableTransactionManagement
class UserIntegrationTest implements IntegrationTest{
    private final String baseUrl = "http://localhost";
    @LocalServerPort
    private int port;
    @Autowired
    private UserRepository userRepository;
    private HttpHeaders headers;
    private RestTemplate restTemplate;


    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
    }


    @Test
    void checkCreateUser() {
        UserRequest userRequest = UserRequest.builder()
                .userName("test-create")
                .password("test-create")
                .build();
        HttpEntity<UserRequest> requestEntity = new HttpEntity<>(userRequest, headers);
        ResponseEntity<UserResponse> responseCreateUser = restTemplate.exchange(baseUrl + ":" + port +
                        UserController.BASE_URL + "/create",
                HttpMethod.POST,
                requestEntity,
                UserResponse.class);
        assertThat(responseCreateUser.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void checkAuthorize() {
        UserRequest userRequest = UserRequest.builder()
                .userName("test-authorize")
                .password("test-authorize")
                .build();
        userRepository.save(User.builder()
                .userName(userRequest.getUserName())
                .password(new BCryptPasswordEncoder().encode(userRequest.getPassword()))
                .role(Role.USER)
                .build());
        HttpEntity<UserRequest> requestEntity = new HttpEntity<>(userRequest, headers);
        ResponseEntity<UserResponse> responseCreateUser = restTemplate.exchange(baseUrl + ":" + port +
                        UserController.BASE_URL + "/authorize",
                HttpMethod.POST,
                requestEntity,
                UserResponse.class);
        assertThat(responseCreateUser.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
