package com.moviedb.movieinfo.repository;


import com.moviedb.movieinfo.domain.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MovieRepositoryTest {
    @Autowired
    MovieRepository movieRepository;
    private Movie movie;


    @BeforeEach
    void setUp() {
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
        movieRepository.save(movie);
    }

    @AfterEach
    void afterEach() {
        movieRepository.delete(movie);
    }

    @Test
    void findMovieInfo() {
        Movie result = movieRepository.findMovieInfo(movie.getTitle(),
                movie.getYear(),
                movie.getType());
        assertThat(result).isEqualTo(movie);
    }

    @Test
    void findTop10Movie() {
        List<Movie> result = movieRepository.findTop10Movie();
        assertThat(result).isSortedAccordingTo(Comparator.comparing(Movie::getImdbRating).reversed());
    }
}