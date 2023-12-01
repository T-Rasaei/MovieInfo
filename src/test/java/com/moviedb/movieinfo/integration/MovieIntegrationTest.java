package com.moviedb.movieinfo.integration;

import com.moviedb.movieinfo.controller.MovieController;
import com.moviedb.movieinfo.service.FetchFromApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieIntegrationTest {
    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost";
    @Autowired
    private FetchFromApiService apiService;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void checkGetTypeOfMovie(){
        List<String> movieList = List.of(
                "Batman Begins",
                "The Batman",
                "Batman v Superman: Dawn of Justice",
                "Batman",
                "Batman Returns",
                "Batman & Robin",
                "Batman Forever",
                "The Lego Batman Movie",
                "Batman: The Animated Series",
                "Batman v Superman: Dawn of Justice (Ultimate Edition)");
        baseUrl = baseUrl + ":" + port + MovieController.BASE_URL + "/batman";
        List<String> result = restTemplate.getForObject(baseUrl,List.class);
        assertThat(result.size()).isEqualTo(movieList.size());
        assertThat(result).isEqualTo(movieList);
    }
}
