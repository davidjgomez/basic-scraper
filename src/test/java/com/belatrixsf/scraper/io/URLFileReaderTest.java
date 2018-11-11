package com.belatrixsf.scraper.io;

import static com.belatrixsf.scraper.io.URLFileReader.readURLs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * Tests the file reader
 * @author David Gomez
 */
public class URLFileReaderTest {

    /** Path of the test directory */
    private static final String TEST_PATH = "./target";

    /**
     * Tests that the result of reading an malformed path is empty
     * 
     * @throws IOException
     * @throws InvalidPathException
     */
    @Test
    public void mustThrowExceptionInvalidPath() throws IOException {
        Executable executable = () -> readURLs("#%34535.,::;3535\\][]#$%#$");
        assertThrows(InvalidPathException.class, executable, "Invalid path!");
    }
    
    /**
     * Tests that the result of reading an unexistent file is empty
     */
    @Test
    public void mustThrowExceptionInvalidFile() {
        Executable executable = () -> readURLs("anUnexistentFile.txt");
        assertThrows(IOException.class, executable, "Invalid file!");
    }

    /**
     * Tests that the read URLs are the expected
     */
    @Test
    public void mustGetGroupURLs() throws IOException {
        Path filePath = Paths.get(TEST_PATH, "testURLsFile.txt");

        List<String> writtenUrls = IntStream.rangeClosed(1, 100)
                                    .mapToObj(value -> "http://www.mocksite" + value + ".com")
                                    .collect(Collectors.toList());
        Files.write(filePath, writtenUrls);

        List<String> urls = readURLs(filePath.toString()).collect(Collectors.toList());

        assertEquals(writtenUrls.size(), urls.size(), "The number of URLs is not the expected!");
        assertIterableEquals(writtenUrls, urls, "There are not the expected URLs!");
    }
}
