package com.moviedb.movieinfo.integration;

import com.moviedb.movieinfo.controller.MovieController;
import com.moviedb.movieinfo.controller.UserController;
import com.moviedb.movieinfo.domain.*;
import com.moviedb.movieinfo.repository.MovieRepository;
import com.moviedb.movieinfo.repository.TokenRepository;
import com.moviedb.movieinfo.repository.UserRepository;
import com.moviedb.movieinfo.service.FetchFromApiService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class MovieIntegrationTest implements IntegrationTest {
    private final String baseUrl = "http://localhost";
    @LocalServerPort
    private int port;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FetchFromApiService apiService;
    private Movie movie;
    private HttpHeaders headers;
    private RestTemplate restTemplate;
    @Autowired
    private TokenRepository tokenRepository;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setBearerAuth(accessToken());
        movie = apiService.fetchMovieInfo("titanic", "type", "movie");
        movieRepository.save(movie);
    }

    @AfterEach
    void tearDown() {
        tokenRepository.deleteAll();
        userRepository.delete(userRepository.findByUserName("test").orElseThrow());
        movieRepository.delete(movie);
    }

    String accessToken() {
        UserRequest userRequest = new UserRequest("test", "1234");
        HttpEntity<UserRequest> requestEntity = new HttpEntity<>(userRequest, headers);
        ResponseEntity<UserResponse> response = restTemplate.exchange(baseUrl + ":" + port +
                        UserController.BASE_URL + "/create",
                HttpMethod.POST,
                requestEntity,
                UserResponse.class);
        return Objects.requireNonNull(response.getBody()).getAccessToken();
    }

    @Test
    void checkGetTitleOfMovie() {
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(baseUrl + ":" + port +
                        MovieController.BASE_URL + "/searchtitle/{title}",
                HttpMethod.GET,
                requestEntity,
                List.class,
                "titanic");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void checkHasOscar() {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("year", "1997");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + ":" + port +
                        MovieController.BASE_URL + "/Oscar/titanic")
                .queryParams(params);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                Boolean.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isTrue();
    }

    @Test
    void checkHasNotOscar() {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("year", "1996");
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + ":" + port +
                        MovieController.BASE_URL + "/Oscar/titanic")
                .queryParams(params);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                Boolean.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isFalse();
    }

    @Test
    void checkRatingToMovie() {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "titanic");
        params.add("year", "1997");
        params.add("type", "movie");
        params.add("rate", Rate.SIX.name());
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + ":" + port +
                        MovieController.BASE_URL + "/rating")
                .queryParams(params);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(),
                HttpMethod.PUT,
                requestEntity,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void checkFindListTop10Movie() {
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(baseUrl + ":" + port +
                        MovieController.BASE_URL + "/top10Movies",
                HttpMethod.GET,
                requestEntity,
                List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
