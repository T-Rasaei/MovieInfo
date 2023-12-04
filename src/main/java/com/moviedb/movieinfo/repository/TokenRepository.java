package com.moviedb.movieinfo.repository;

import com.moviedb.movieinfo.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Integer> {
}
