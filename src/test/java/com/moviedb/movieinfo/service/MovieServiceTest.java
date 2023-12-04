package com.moviedb.movieinfo.service;

import com.moviedb.movieinfo.domain.Movie;
import com.moviedb.movieinfo.domain.MovieSearch;
import com.moviedb.movieinfo.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @InjectMocks
    MovieService movieService;

    @Mock
    FetchFromApiService apiService;

    @Mock
    SearchServiceOscar searchServiceOscar;

    @Mock
    MovieRepository movieRepository;

    @Mock
    TransferToDBService transferToDBService;

    private Movie movie;
    private MovieSearch movieSearch;

    @BeforeEach
    void setUp() {
        movie = Movie.builder()
                .title("black man")
                .year("2017")
                .director("Gentian Selo")
                .imdbID("tt6462710")
                .type("movie")
                .imdbRating(5)
                .imdbVotes("10")
                .response("True")
                .build();
        movieSearch = MovieSearch.builder()
                .title(movie.getTitle())
                .year(movie.getYear())
                .type(movie.getType())
                .build();
    }

    @Test
    void checkGetTitleOfMovie() {
        when(apiService.fetchTitleOfMovie(movie.getTitle())).thenReturn(List.of(movieSearch));
        List<MovieSearch> result = movieService.getTitleOfMovie(movie.getTitle());
        verify(transferToDBService).transferToDatabase(List.of(movieSearch));
        assertThat(result.get(0)).isEqualTo(movieSearch);
    }


    @Test
    void checkHasOscar() throws FileNotFoundException {
        when(searchServiceOscar.hasOscar(anyString(),anyString(),anyString())).thenReturn(true);
        boolean hasOscar = movieService.hasOscar("titanic","1997");
        assertThat(hasOscar).isTrue();
    }

    @Test
    void checkRatingToMovieFromApi() {
        when(movieRepository.findMovieInfo(anyString(),anyString(),anyString())).thenReturn(null);
        when(apiService.fetchMovieInfo(anyString(),anyString(),anyString())).thenReturn(movie);
        String newRate = movieService.ratingToMovie(movie.getTitle(), movie.getYear(), movie.getType(), 2);
        assertThat(newRate).isEqualTo("Rate = 4.7");
    }

    @Test
    void checkRatingToMovieFromDB() {
        when(movieRepository.findMovieInfo(anyString(),anyString(),anyString())).thenReturn(movie);
        String newRate = movieService.ratingToMovie(movie.getTitle(), movie.getYear(), movie.getType(), 2);
        assertThat(newRate).isEqualTo("Rate = 4.7");
    }

    @Test
    void checkRatingToMovieWhenNotFoundMovie() {
        when(movieRepository.findMovieInfo(anyString(),anyString(),anyString())).thenReturn(null);
        when(apiService.fetchMovieInfo(anyString(),anyString(),anyString())).thenReturn(Movie.builder().response("false").build());
        String newRate = movieService.ratingToMovie(movie.getTitle(), movie.getYear(), movie.getType(), 2);
        assertThat(newRate).isEqualTo("Movie not found");
    }

    @Test
    void checkFindTop10Movie() {
        movieService.findTop10Movie();
        verify(movieRepository).findTop10Movie();
    }
}