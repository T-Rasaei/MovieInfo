package com.moviedb.movieinfo.integration;

import com.moviedb.movieinfo.controller.MovieController;
import com.moviedb.movieinfo.domain.Movie;
import com.moviedb.movieinfo.domain.MovieSearch;
import com.moviedb.movieinfo.domain.Rate;
import com.moviedb.movieinfo.domain.Top10Response;
import com.moviedb.movieinfo.repository.MovieRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class MovieIntegrationTest implements IntegrationTest{
    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost";

    private RestTemplate restTemplate;
    @Autowired
    private MovieRepository movieRepository;
    private Movie movie;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        movie = Movie.builder()
                .id(1L)
                .title("night")
                .year("2010")
                .imdbRating(7.5)
                .imdbVotes("50")
                .response("true")
                .type("movie")
                .boxOffice("$303,300,200")
                .build();
    }

    @AfterEach
    void afterEach() {
        movieRepository.delete(movie);
    }

    @Test
    void checkGetTitleOfMovie(){
        baseUrl = baseUrl + ":" + port + MovieController.BASE_URL + "/searchtitle/titanic";
        List<MovieSearch> result = restTemplate.getForObject(baseUrl,List.class);
        assertThat(result).isNotEmpty();
    }

    @Test
    void checkHasOscar(){
        baseUrl = baseUrl + ":" + port + MovieController.BASE_URL + "/Oscar/titanic?year=1997";
        boolean hasOscar = restTemplate.getForObject(baseUrl,Boolean.class);
        assertThat(hasOscar).isTrue();
    }

    @Test
    void checkHasNotOscar(){
        baseUrl = baseUrl + ":" + port + MovieController.BASE_URL + "/Oscar/titanic?year=1996";
        boolean hasOscar = restTemplate.getForObject(baseUrl,Boolean.class);
        assertThat(hasOscar).isFalse();
    }

    @Test
    void checkRatingToExistsMovie(){
        baseUrl = baseUrl + ":" + port + MovieController.BASE_URL + "/rating?title=titanic&year=1997&type=movie&rate=" + Rate.TWO.name();
        ResponseEntity<String> newRate = restTemplate.exchange(baseUrl, HttpMethod.PUT, null, String.class);
        assertThat(newRate.getBody()).isNotEqualTo("Movie not found");
    }

    @Test
    void checkRatingToNotExistsMovie(){
        baseUrl = baseUrl + ":" + port + MovieController.BASE_URL + "/rating?title=rasaei&year=2024&type=movie&rate=" + Rate.TWO.name();
        ResponseEntity<String> newRate = restTemplate.exchange(baseUrl, HttpMethod.PUT, null, String.class);
        assertThat(newRate.getBody()).isEqualTo("Movie not found");
    }

    @Test
    void checkFindListTop10Movie(){
        movieRepository.save(movie);
        baseUrl = baseUrl + ":" + port + MovieController.BASE_URL + "/top10Movies";
        List<Top10Response> responseList = restTemplate.getForObject(baseUrl,List.class);
        assertThat(responseList).isNotEmpty();

    }
}
