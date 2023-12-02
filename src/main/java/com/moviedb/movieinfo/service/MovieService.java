package com.moviedb.movieinfo.service;

import com.moviedb.movieinfo.domain.Movie;
import com.moviedb.movieinfo.domain.MovieSearch;
import com.moviedb.movieinfo.domain.Top10Response;
import com.moviedb.movieinfo.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MovieService {
    private static final String CSVNAME = "academy_awards.csv";

    private final FetchFromApiService apiService;
    private final SearchServiceOscar searchServiceOscar;
    private final MovieRepository movieRepository;
    private final TransferToDBService transferToDBService;

    public List<MovieSearch> getTitleOfMovie(String title) {
        List<MovieSearch> result = apiService.fetchTitleOfMovie(title);
        if (! result.isEmpty())
            transferToDBService.transferToDatabase(result);
        return result;
    }

    public boolean hasOscar(String title, String year) throws FileNotFoundException {
        return searchServiceOscar.hasOscar(title, year, CSVNAME);
    }

    public String ratingToMovie(String title, String year, String type, int rate) {
        double totalVotesSum;
        long totalVotsNum;
        double newRate;
        double scale = Math.pow(10, 1);
        Movie movie = movieRepository.findMovieInfo(title, year, type);
        if (movie == null) {
            movie = apiService.fetchMovieInfo(title, year, type);
        }
        if (movie.getResponse().equals("True")) {
            totalVotsNum = Long.valueOf(movie.getImdbVotes().replace(",", ""));
            totalVotesSum = movie.getImdbRating() * totalVotsNum;
            newRate = (totalVotesSum + rate) / (totalVotsNum + 1);
            newRate = Math.round(newRate * scale) / scale;
            movie.setImdbVotes(String.valueOf(totalVotsNum + 1));
            movie.setImdbRating(newRate);
            movieRepository.save(movie);
            return "Rate = " + newRate;
        } else {
            return "Movie not found";
        }
    }

    public List<Top10Response> findTop10Movie() {
        List<Movie> movieList = movieRepository.findTop10Movie();
        List<Top10Response> top10ResponseList = new ArrayList<>();
        movieList.forEach(movie ->
                top10ResponseList.add(Top10Response.builder()
                        .title(movie.getTitle())
                        .year(movie.getYear())
                        .type(movie.getType())
                        .rate(movie.getImdbRating())
                        .boxOffice(movie.getBoxOffice())
                        .build())
        );
        top10ResponseList.sort(Comparator.comparing(Top10Response::getBoxOffice).reversed());
        return top10ResponseList;
    }
}
