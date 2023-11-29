package com.moviedb.movieinfo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private List<String> genre;
    private List<String> director;
    private List<String> writer;
    private List<String> actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String poster;
    @ManyToOne(fetch = FetchType.LAZY)
    private Rating ratings;
    private String metascore;
    private float imdbRating;
    private Long imdbVotes;
    private String imdbID;
    private String type;
    private String dvd;
    private String boxOffice;
    private String production;
    private String website;
    private String Response;
}
