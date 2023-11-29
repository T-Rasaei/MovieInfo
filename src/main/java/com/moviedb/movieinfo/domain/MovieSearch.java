package com.moviedb.movieinfo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MovieSearch {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private String year;
    private String imdbID;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Poster")
    private String poster;
}
