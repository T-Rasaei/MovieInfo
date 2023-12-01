package com.moviedb.movieinfo.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
public class SearchServiceOscar{
    public boolean hasOscar(String title, String year, String fileName) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine().toLowerCase();
                if (line.contains(title.toLowerCase()) &&
                        line.contains("best picture") &&
                        line.contains(year) &&
                        line.contains("yes")) {
                    return true;
                }
            }
        }
        return false;
    }
}
