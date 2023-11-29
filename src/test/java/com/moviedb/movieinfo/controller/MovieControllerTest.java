package com.moviedb.movieinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviedb.movieinfo.omdbapi.FetchFromApiService;
import com.moviedb.movieinfo.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {
    @MockBean
    private MovieService movieService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void checkGetTypeOfMovie() throws Exception {
        String title = "batman";
        List<String> movieList = List.of(
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
        when(movieService.getTypeOfMovie(anyString())).thenReturn(movieList);
        mockMvc.perform(get(MovieController.BASE_URL+"/{title}",title))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(movieList.size())));
    }
}