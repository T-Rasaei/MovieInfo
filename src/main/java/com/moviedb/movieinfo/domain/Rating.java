package com.moviedb.movieinfo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @JsonProperty("Source")
    private String source;
    @JsonProperty("Value")
    private String value;
}
