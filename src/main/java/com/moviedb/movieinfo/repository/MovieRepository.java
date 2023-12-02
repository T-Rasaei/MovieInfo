package com.moviedb.movieinfo.repository;

import com.moviedb.movieinfo.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "SELECT * FROM movie " +
            "WHERE title=lower(:title) AND year=:year AND type=lower(:type)",
            nativeQuery = true)
    Movie findMovieInfo(@Param("title") String title,
                        @Param("year") String year,
                        @Param("type") String type);

    @Query(value = "SELECT * FROM movie WHERE  box_office IS NOT NULL \n" +
            "AND NOT box_office LIKE 'N/A' " +
            "ORDER BY imdb_rating DESC limit 10",
            nativeQuery = true)
    List<Movie> findTop10Movie();
}
