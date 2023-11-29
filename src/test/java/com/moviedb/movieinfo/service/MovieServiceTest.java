package com.moviedb.movieinfo.service;

import com.moviedb.movieinfo.omdbapi.FetchFromApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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