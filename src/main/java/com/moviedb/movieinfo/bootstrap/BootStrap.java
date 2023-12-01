package com.moviedb.movieinfo.bootstrap;

import com.moviedb.movieinfo.domain.Movie;
import com.moviedb.movieinfo.service.FetchFromApiService;
import com.moviedb.movieinfo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class BootStrap implements CommandLineRunner {
    @Autowired
    private MovieService movieService;
    @Autowired
    FetchFromApiService apiService;

    @Override
    public void run(String... args) throws Exception {
//        List<String> result = movieService.getTypeOfMovie("batman");
//        result.forEach(System.out::println);
//        System.out.println(movieService.hasOscar("Titanic"));
//        Movie movie = apiService.fetchMovieInfo("titanic");
//        System.out.println(movie);

//        movieService.ratingToMovie("titanic",1);
    }
}
