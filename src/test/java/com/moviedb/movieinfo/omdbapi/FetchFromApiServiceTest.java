package com.moviedb.movieinfo.omdbapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FetchFromApiServiceTest {
    FetchFromApiService apiService;

    @BeforeEach
    void setUp() {
        apiService = new FetchFromApiService(new RestTemplate());
    }

    @Test
    void checkFetchTypeOfMovie() {
        String title = "batman";
        List<String> expectedResult = List.of(
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
        List<String> result = apiService.fetchTypeOfMovie(title);
        assertThat(result).isEqualTo(expectedResult);
    }
}