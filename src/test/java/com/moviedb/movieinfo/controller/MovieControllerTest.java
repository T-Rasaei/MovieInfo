package com.moviedb.movieinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviedb.movieinfo.domain.MovieSearch;
import com.moviedb.movieinfo.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
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
        String title = "titanic";
        MovieSearch movie1 = new MovieSearch("tt0120338",
                "Titanic",
                "1997",
                "movie",
                "");
        MovieSearch movie2 = new MovieSearch("tt1640571",
                "Titanic II",
                "2010",
                "series",
                "" );
        List<MovieSearch> movieList = List.of(movie1,movie2);

        when(movieService.getTypeOfMovie(anyString())).thenReturn(movieList);
        mockMvc.perform(get(MovieController.BASE_URL+"/{title}",title))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(movieList.size())));
    }
}