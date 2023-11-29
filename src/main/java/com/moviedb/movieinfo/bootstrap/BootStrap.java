package com.moviedb.movieinfo.bootstrap;

import com.moviedb.movieinfo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BootStrap implements CommandLineRunner {
    @Autowired
    private MovieService movieService;

    @Override
    public void run(String... args) throws Exception {
//        List<String> result = movieService.getTypeOfMovie("batman");
//        result.forEach(System.out::println);
        System.out.println(movieService.IsWonOScar("Titanic"));

    }
}
