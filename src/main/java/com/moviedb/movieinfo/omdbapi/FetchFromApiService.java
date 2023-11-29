package com.moviedb.movieinfo.omdbapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviedb.movieinfo.domain.MovieSearch;
import com.moviedb.movieinfo.domain.MovieSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FetchFromApiService {
    private static final String BASEURL = "https://www.omdbapi.com/";
    private static final String APIKEY = "1d164458";

    private final RestTemplate restTemplate;

    public List<String> fetchTypeOfMovie(String title){
        ObjectMapper mapper = new ObjectMapper();
        String url = BASEURL + "?s=" + title + "&apikey=" + APIKEY;
        MovieSearchResponse response = restTemplate.getForObject(url,MovieSearchResponse.class);
        return response.getSearch().stream()
                .map(object -> mapper.convertValue(object, MovieSearch.class))
                .map(MovieSearch::getTitle)
                .collect(Collectors.toList());
    }
}
