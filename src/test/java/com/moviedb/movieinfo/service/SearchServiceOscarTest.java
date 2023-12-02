package com.moviedb.movieinfo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SearchServiceOscarTest {
    private SearchServiceOscar searchServiceOscar;

    @BeforeEach
    void setUp() {
        searchServiceOscar = new SearchServiceOscar();
    }

    @Test
    void checkHasOscar() throws FileNotFoundException {
        boolean hasOscar = searchServiceOscar.hasOscar("titanic","1997","academy_awards.csv");
        assertThat(hasOscar).isTrue();
    }

    @Test
    void checkHasNotOscar() throws FileNotFoundException {
        boolean hasOscar = searchServiceOscar.hasOscar("titanic","1996","academy_awards.csv");
        assertThat(hasOscar).isFalse();
    }
}