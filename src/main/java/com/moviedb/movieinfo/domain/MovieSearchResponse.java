package com.moviedb.movieinfo.domain;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class MovieSearchResponse {

    @JsonProperty("Search")
    private List<MovieSearch> search;
    private String totalResults;
    @JsonProperty("Response")
    private boolean response;
}
