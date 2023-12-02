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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferToDBServiceTest {
    @InjectMocks
    TransferToDBService transferToDBService;

    @Mock
    MovieRepository movieRepository;

    @Mock
    FetchFromApiService fromApiService;
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
                .response("True")
                .build();
        movieSearch = MovieSearch.builder()
                .title(movie.getTitle())
                .year(movie.getYear())
                .type(movie.getType())
                .build();
    }

    @Test
    void checkTransferToDatabaseWhenNotExistsInDB() {
        when(movieRepository.findMovieInfo(anyString(), anyString(), anyString())).thenReturn(null);
        when(fromApiService.fetchMovieInfo(anyString(), anyString(), anyString())).thenReturn(movie);
        transferToDBService.transferToDatabase(List.of(movieSearch));
        verify(movieRepository).saveAll(List.of(movie));
    }

    @Test
    void checkTransferToDatabaseWhenExistsInDB() {
        when(movieRepository.findMovieInfo(anyString(), anyString(), anyString())).thenReturn(movie);
        transferToDBService.transferToDatabase(List.of(movieSearch));
        verify(movieRepository).saveAll(new ArrayList<>());
    }
}