package com.moviedb.movieinfo.service;

import com.moviedb.movieinfo.domain.Movie;
import com.moviedb.movieinfo.domain.MovieSearch;
import com.moviedb.movieinfo.repository.MovieRepository;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferToDBService {
    private final FetchFromApiService apiService;
    private final MovieRepository movieRepository;

    @Transient
    public void transferToDatabase(List<MovieSearch> movieSearchList) {
        List<Movie> movieList = new ArrayList<>();
        for (MovieSearch movieSearch : movieSearchList) {
            Movie existsMovie = movieRepository.findMovieInfo(movieSearch.getTitle(),
                    movieSearch.getYear(),
                    movieSearch.getType());
            if (existsMovie == null)
                movieList.add(apiService.fetchMovieInfo(movieSearch.getTitle(),
                        movieSearch.getYear(),
                        movieSearch.getType()));
        }
        movieRepository.saveAll(movieList);
    }
}
