package com.belatrixsf.scraper.pattern;

import java.util.List;

import org.jsoup.nodes.Document;

/**
 * Base pattern interface used to implement different 
 * search patterns to be used on an HTML document
 * @author David Gomez
 */
public interface ScraperPattern {

    /**
     * Returns the pattern name
     * @return pattern name
     */
    public String getName();

    /**
     * Matches the pattern with the provided HTML document
     * @param document HTML document to match the pattern
     * @return list of found matches
     */
    public List<String> match(Document document);
}
