package com.belatrixsf.scraper.io;

import static com.belatrixsf.scraper.config.Config.getMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * URL file reader
 * @author David Gomez
 */
public class URLFileReader {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(URLFileReader.class);

    /**
     * Reads all the URLs from a file
     * 
     * @param fileName source file name
     * @return optional object with stream with the URLs or empty
     * @throws InvalidPathException if path is malformed
     * @throws IOException if it was not possible to read the file
     */
    public static Stream<String> readURLs(String fileName) throws InvalidPathException, IOException {
        LOGGER.info(getMessage("io.URLFileReader.readingURLsFile", fileName));
        return Files.lines(Paths.get(fileName));
    }
}
