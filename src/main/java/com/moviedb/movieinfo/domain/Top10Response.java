package com.moviedb.movieinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Top10Response {
    private String title;
    private String year;
    private String type;
    private double rate;
    private String boxOffice;
}
