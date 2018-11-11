package com.belatrixsf.scraper.io;

import static com.belatrixsf.scraper.config.ConfigTest.TEST_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * Tests the file writer
 * @author David Gomez
 */
public class MatchesFileWriterTest {

    /**
     * Tests that an exception is thrown when an incorrect path to create a file is passed as a parameter
     */
    @Test
    public void mustThrowExceptionBadFilepath() {
        MatchesFileWriter matchesFileWriter = new MatchesFileWriter("./target/mockPath", "mockNoMatchesFile");
        assertThrows(IOException.class, () -> matchesFileWriter.write(Arrays.asList("Mock outcome")));
    }

    /**
     * Tests the result when there are not matches to write in the file
     */
    @Test
    public void mustHaveNotMatchesFoundMessage() throws IOException {
        String url = "mockNoMatchesFile";
        MatchesFileWriter matchesFileWriter = new MatchesFileWriter(TEST_PATH, url);
        matchesFileWriter.write(new ArrayList<String>());
        Path filePath = Paths.get(TEST_PATH, url + ".txt");
        
        assertFalse(filePath.toFile().exists(), "The file must not exist because there are not matches!");
    }

    /**
     * Tests the result when there are must be a group of matches to write in the file
     */
    @Test
    public void mustHaveAGroupOfMatches() throws IOException {
        String url = "mockGroupMatches";
        MatchesFileWriter matchesFileWriter = new MatchesFileWriter(TEST_PATH, url);
        List<String> writtenMatches = IntStream.rangeClosed(1, 100)
                                                .mapToObj(value -> "Match " + value)
                                                .collect(Collectors.toList());
                            
        matchesFileWriter.write(writtenMatches);
        Path filePath = Paths.get(TEST_PATH, url + ".txt");
        
        assertTrue(filePath.toFile().exists(), "The file must exist!");

        List<String> matches = Files.lines(filePath).collect(Collectors.toList());

        assertEquals(100, matches.size(), "There must be more than one result");
        assertIterableEquals(writtenMatches, matches, "The matches are not the expected!");
    }
}
