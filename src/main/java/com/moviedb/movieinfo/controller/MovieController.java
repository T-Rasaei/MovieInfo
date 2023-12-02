package com.moviedb.movieinfo.controller;

import com.moviedb.movieinfo.domain.MovieSearch;
import com.moviedb.movieinfo.domain.Rate;
import com.moviedb.movieinfo.domain.Top10Response;
import com.moviedb.movieinfo.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;


@RestController
@RequestMapping(MovieController.BASE_URL)
@RequiredArgsConstructor
public class MovieController {
    public static final String BASE_URL = "/api/v1/movie";
    private final MovieService movieService;

    @PutMapping("/rating")
    @Operation(tags = "Rate the movie",
            description = "You can rate to a special movie\\n"+
            "** Please enter the information accurately based on the output of the movie searchtitle section.")
    public ResponseEntity<String> ratingToMovie(@RequestParam String title,
                                                @RequestParam String year,
                                                @RequestParam String type,
                                                @RequestParam Rate rate) {
        var response = movieService.ratingToMovie(title.toLowerCase(), year, type.toLowerCase(), rate.getValue());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/Oscar/{title}")
    @Operation(tags = "Has an Oscar?",
            description = "Whether a movie won a “Best Picture” Oscar?\\n " +
            "** Please enter the title accurately based on the output of the movie searchtitle section. ")
    public boolean hasOscar(@PathVariable String title,
                            @RequestParam String year) throws FileNotFoundException {
        return movieService.hasOscar(title, year);
    }

    @GetMapping("/searchtitle/{title}")
    @Operation(tags = "Find title of movie",
            description = "In this section, you can see the title of a movie, then use the exact title for other sections")
    public List<MovieSearch> getTypeOfMovie(@PathVariable String title) {
        return movieService.getTitleOfMovie(title);
    }

    @GetMapping("/top10Movies")
    @Operation(tags = "List of top 10 movies",
    description = "Presenting a list of the top 10 highest rated movies in order of box office value")
    public List<Top10Response> listTop10Movie(){
        return movieService.findTop10Movie();
    }
}
