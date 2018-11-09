package com.belatrixsf.scraper.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * URL file reader
 * @author David Gomez
 */
public class URLFileReader {

    /**
     * Reads all the URLs from a file
     * @param fileName source file name
     * @return optional object with stream with the URLs or empty
     */
    public static Stream<String> readURLs(String fileName) {
        try {
            return Files.lines(Paths.get(fileName));
        } catch (IOException e) {
            System.out.println("Cannot read the file " + fileName + ": " + e.getMessage());
        }
        
        return Stream.empty();
    }
}
