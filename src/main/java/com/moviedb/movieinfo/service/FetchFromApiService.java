package com.moviedb.movieinfo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviedb.movieinfo.domain.Movie;
import com.moviedb.movieinfo.domain.MovieSearch;
import com.moviedb.movieinfo.domain.MovieSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FetchFromApiService {
    private static final String BASEURL = "https://www.omdbapi.com/";
    private static final String APIKEY = "1d164458";

    private final RestTemplate restTemplate;

    public List<MovieSearch> fetchTypeOfMovie(String title) {
        String url = BASEURL + "?s=" + title + "&apikey=" + APIKEY;
        ObjectMapper mapper = new ObjectMapper();
        MovieSearchResponse result = restTemplate.getForObject(url, MovieSearchResponse.class);
        if (result != null && result.isResponse()){
            return result.getSearch().stream()
                    .map(object -> mapper.convertValue(object, MovieSearch.class))
                    .toList();
        } else {
            return Collections.emptyList();

        }
    }

    public Movie fetchMovieInfo(String title, String year, String type) {
        String url = BASEURL + "?t=" + title + "&y=" + year + "&type=" + type + "&apikey=" + APIKEY;
        return restTemplate.getForObject(url, Movie.class);
    }
}
