package com.belatrixsf.scraper.io;


import static com.belatrixsf.scraper.config.Config.getMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File matches writer
 * @author David Gomez
 */
public class MatchesFileWriter {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchesFileWriter.class);

    /** Path where the output file is created */
    private String path;

    /** Analized URL */
    private String url;

    /**
     * Constructor
     * @param path path where the output file is created
     * @param fileName analyzed URL
     */
    public MatchesFileWriter(String path, String url) {
        this.path = path;
        this.url = url;
    }

    /**
     * Returns the absolute output file name based on the processed URL
     * @return absolute output file name
     */
    private Path getOutputFileName() {
        Path finalPath = Paths.get(path, url.
            substring(0, Math.min(url.length(), 200)).
            replaceAll("(https?:|www|[/\\.:])", "").
            concat(".txt"));

        return finalPath;
    }

    /**
     * Writes all the matches to an specified file
     * @param matches matches found
     */
    public void write(List<String> matches) throws IOException {

        if(!matches.isEmpty()) {
            Path filePath = getOutputFileName();
            Files.write(filePath, matches);
            LOGGER.info(getMessage("io.matchesFileWriter.resultFileCreated", url, filePath));
        }
        else {
            LOGGER.info(getMessage("io.matchesFileWriter.resultFileNotCreated", url));
        }
    }
}
