package com.moviedb.movieinfo.service;

import com.moviedb.movieinfo.domain.MovieSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class FetchFromApiServiceTest {
    FetchFromApiService apiService;

    @BeforeEach
    void setUp() {
        apiService = new FetchFromApiService(new RestTemplate());
    }

    @Test
    void checkFetchTypeOfMovie() {
        String title = "batman";
        List<MovieSearch> result = apiService.fetchTypeOfMovie(title);
        assertThat(result).isNotNull();
    }
}