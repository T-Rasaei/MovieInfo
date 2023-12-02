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
    void checkGetTitleOfMovie() {
        String title = "batman";
        movieService.getTitleOfMovie(title);
        verify(apiService).fetchTitleOfMovie(title);
    }


    @Test
    void hasOscar() {
    }

    @Test
    void ratingToMovie() {
    }

    @Test
    void findTop10Movie() {
    }
}