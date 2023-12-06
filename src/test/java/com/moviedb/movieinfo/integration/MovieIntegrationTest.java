package com.moviedb.movieinfo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviedb.movieinfo.controller.MovieController;
import com.moviedb.movieinfo.domain.Movie;
import com.moviedb.movieinfo.domain.Rate;
import com.moviedb.movieinfo.repository.MovieRepository;
import com.moviedb.movieinfo.service.FetchFromApiService;
import com.moviedb.movieinfo.service.MovieService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@WithMockUser
class MovieIntegrationTest implements IntegrationTest{
    private final String baseUrl = "http://localhost";

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private FetchFromApiService apiService;
    @LocalServerPort
    private int port;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = apiService.fetchMovieInfo("titanic","type","movie");
        movieRepository.save(movie);
    }

    @AfterEach
    void afterAll() {
        movieRepository.delete(movie);
    }

    @Test
    void checkGetTitleOfMovie() throws Exception {
        mockMvc.perform(get(baseUrl + ":" + port +
                        MovieController.BASE_URL +
                        "/searchtitle/titanic"))
                .andExpect(status().isOk());
    }

    @Test
    void checkHasOscar() throws Exception {
        mockMvc.perform(get(baseUrl + ":" + port +
                        MovieController.BASE_URL+"/Oscar/titanic")
                        .param("year","1997"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is(true)));
    }

    @Test
    void checkHasNotOscar() throws Exception {
        mockMvc.perform(get(baseUrl + ":" + port +
                        MovieController.BASE_URL+"/Oscar/titanic")
                        .param("year","1996"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",is(false)));
    }

    @Test
    void checkRatingToMovie() throws Exception {
        mockMvc.perform(put(baseUrl + ":" + port + MovieController.BASE_URL+"/rating")
                        .param("title","titanic")
                        .param("year","1997")
                        .param("type","movie")
                        .param("rate", Rate.SIX.name())
                )
                .andExpect(status().isOk());
    }

    @Test
    void checkFindListTop10Movie() throws Exception {
        mockMvc.perform(get(baseUrl + ":" + port +
                        MovieController.BASE_URL+"/top10Movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(1)));
    }
}
