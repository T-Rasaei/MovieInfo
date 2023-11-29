package com.moviedb.movieinfo.service;

import com.moviedb.movieinfo.omdbapi.FetchFromApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;


@Service
@RequiredArgsConstructor
public class MovieService {
    private final FetchFromApiService apiService;
    private final static String CSVNAME = "academy_awards.csv";

    public List<String> getTypeOfMovie(String title) {
        return apiService.fetchTypeOfMovie(title);
    }

    public boolean IsWonOScar(String title) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(CSVNAME))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine().toLowerCase();
                if (line.contains(title.toLowerCase()) &&
                        line.contains("best picture") &&
                        line.contains("yes")) {
                    return true;
                }
            }
        }
        return false;
    }
}
