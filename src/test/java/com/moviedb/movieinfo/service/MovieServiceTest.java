package com.moviedb.movieinfo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @InjectMocks
    MovieService movieService;

    @Mock
    FetchFromApiService apiService;

    @Test
    void checkGetTypeOfMovie() {
        String title = "batman";
        movieService.getTypeOfMovie(title);
        verify(apiService).fetchTypeOfMovie(title);
    }
}