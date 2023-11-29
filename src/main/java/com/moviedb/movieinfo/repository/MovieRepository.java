package com.moviedb.movieinfo.repository;

import com.moviedb.movieinfo.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie,Long> {

}
