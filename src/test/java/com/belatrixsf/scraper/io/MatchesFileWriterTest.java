package com.belatrixsf.scraper.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * Tests the file writer
 * @author David Gomez
 */
public class MatchesFileWriterTest {

    /** Path of the test directory */
    private static final String TEST_PATH = "./target";

    /**
     * Tests that an exception is thrown when an incorrect path to create a file is passed as a parameter
     */
    @Test
    public void mustThrowExceptionBadFilepath() {
        MatchesFileWriter matchesFileWriter = new MatchesFileWriter("./target/mockPath", "mockNoMatchesFile");
        assertThrows(IOException.class, () -> matchesFileWriter.write(new ArrayList<String>()));
    }

    /**
     * Tests the result when there are not matches to write in the file
     */
    @Test
    public void mustHaveNotMatchesFoundMessage() throws IOException {
        String fileName = "mockNoMatchesFile";
        MatchesFileWriter matchesFileWriter = new MatchesFileWriter(TEST_PATH, fileName);
        matchesFileWriter.write(new ArrayList<String>());

        List<String> matches = Files.lines(Paths.get(TEST_PATH, fileName + ".txt"))
                                    .collect(Collectors.toList());

        assertEquals(1, matches.size(), "There must be only one result");
        assertEquals("Matches not found!", matches.iterator().next());
    }

    /**
     * Tests the result when there are must be a group of matches to write in the file
     */
    @Test
    public void mustHaveAGroupOfMatches() throws IOException {
        String fileName = "mockGroupMatchesFile";
        MatchesFileWriter matchesFileWriter = new MatchesFileWriter(TEST_PATH, fileName);
        List<String> writtenMatches = IntStream.rangeClosed(1, 100)
                                                .mapToObj(value -> "Match " + value)
                                                .collect(Collectors.toList());
                            
        matchesFileWriter.write(writtenMatches);

        List<String> matches = Files.lines(Paths.get(TEST_PATH, fileName + ".txt"))
                                    .collect(Collectors.toList());

        assertEquals(100, matches.size(), "There must be more than one result");
        assertIterableEquals(writtenMatches, matches, "The matches are not the expected!");
    }
}
