package com.moviedb.movieinfo.service;

import com.moviedb.movieinfo.domain.Movie;
import com.moviedb.movieinfo.domain.MovieSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class FetchFromApiServiceTest {
    FetchFromApiService apiService;
    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = Movie.builder()
                .title("black man")
                .year("2017")
                .director("Gentian Selo")
                .imdbID("tt6462710")
                .type("movie")
                .response("True")
                .build();
        apiService = new FetchFromApiService(new RestTemplate());
    }

    @Test
    void checkFetchTitleOfMovie() {
        List<MovieSearch> result = apiService.fetchTitleOfMovie(movie.getTitle());
        assertThat(result).isNotNull();
    }

    @Test
    void fetchMovieInfo() {
        Movie result = apiService.fetchMovieInfo(movie.getTitle(), movie.getYear(), movie.getType());
        assertThat(result.getTitle()).isEqualTo(movie.getTitle());
        assertThat(result.getYear()).isEqualTo(movie.getYear());
        assertThat(result.getDirector()).isEqualTo(movie.getDirector());
        assertThat(result.getImdbID()).isEqualTo(movie.getImdbID());
        assertThat(result.getType()).isEqualTo(movie.getType());
        assertThat(result.getResponse()).isEqualTo(movie.getResponse());

    }
}