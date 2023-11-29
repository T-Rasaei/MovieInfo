package com.moviedb.movieinfo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moviedb.movieinfo.omdbapi.FetchFromApiService;
import com.moviedb.movieinfo.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(MovieController.BASE_URL)
@RequiredArgsConstructor
public class MovieController {
    public final static String BASE_URL = "/api/v1/movie";
    private final MovieService movieService;

    @GetMapping("/{title}")
    public List<String> getTypeOfMovie(@PathVariable String title){
        return movieService.getTypeOfMovie(title);
    }
}
