package com.moviedb.movieinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviedb.movieinfo.domain.MovieSearch;
import com.moviedb.movieinfo.domain.Rate;
import com.moviedb.movieinfo.domain.Top10Response;
import com.moviedb.movieinfo.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    private MovieSearch movieSearch1;
    private MovieSearch movieSearch2;
    private List<MovieSearch> movieSearchList;
    private Top10Response top10Response;

    @BeforeEach
    void setUp() {
        top10Response = new Top10Response(
                "black man",
                "2017",
                "movie",
                5,
                "10");

        MovieSearch MovieSearch1 = new MovieSearch("tt0120338",
                "Titanic",
                "1997",
                "movie",
                "");
        MovieSearch MovieSearch2 = new MovieSearch("tt1640571",
                "Titanic II",
                "2010",
                "series",
                "" );
        movieSearchList = List.of(MovieSearch1,MovieSearch2);
    }

    @Test
    void checkGetTitleOfMovie() throws Exception {
        when(movieService.getTitleOfMovie(anyString())).thenReturn(movieSearchList);
        mockMvc.perform(get(MovieController.BASE_URL+"/searchtitle/{title}","titanic"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(movieSearchList.size())));
    }

    @Test
    void checkHasOscar() throws Exception {
        when(movieService.hasOscar(anyString(), anyString())).thenReturn(true);
        mockMvc.perform(get(MovieController.BASE_URL+"/Oscar/{title}","titanic")
                        .param("year","1997"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is(true)));
    }

    @Test
    void checkRatingToMovie() throws Exception {
        when(movieService.ratingToMovie(anyString(), anyString(), anyString(), anyInt())).thenReturn("Rate = 4.7");
        mockMvc.perform(put(MovieController.BASE_URL+"/rating")
                        .param("title","titanic")
                        .param("year","1997")
                        .param("type","movie")
                        .param("rate", Rate.TWO.name())
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is("Rate = 4.7")));

    }


    @Test
    void checkFindListTop10Movie() throws Exception {
        when(movieService.findTop10Movie()).thenReturn(List.of(top10Response));
        mockMvc.perform(get(MovieController.BASE_URL+"/top10Movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(1)));
    }
}