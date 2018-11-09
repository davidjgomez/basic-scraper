package com.belatrixsf.scraper.pattern;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Hashtag pattern tests
 * @author David Gomez
 */
public class HashtagPatternTest {
    
    /** Pattern to be tested */
    private static ScraperPattern pattern;

    /** Test document */
    private static Document document;

    /**
     * Instantiates pattern before testing and parses the test document
     * @throws IOException if any error trying to parse the tested HTML found
     */
    @BeforeAll
    public static void setUp() throws IOException {
        pattern = new HashtagPattern();
        document = Jsoup.parse(
            HashtagPattern.class.getResourceAsStream("/pattern/hashtagPattern.html"), "UTF-8", 
                                                    "http://mock.site.com");        
    }

    /**
     * Tests pattern where it must find multiple values
     */
    @Test
    public void mustFindMultipleValues() {
        List<String> matches = pattern.match(document);
        assertNotNull(matches, "Null stream must not be provided!");

        List<String> expectedMatches = 
            asList("#ThisTitleISGOOD_|#ProvingPatternsHashTag|#2InTable|#Par?t|#the#123|#_test123|#a|#n|#d|#Only|#EvenAboutLongHashtagsNoMatterWhat"
            .split("\\|"));

        assertTrue(matches.containsAll(expectedMatches), "Not found all the expected matches!");
    }

    /**
     * Tests pattern where some unexpected values must not be found
     */
    @Test
    public void mustNotFindMultipleValues() {
        List<String> matches = pattern.match(document);
        assertNotNull(matches, "Null stream must not be provided!");

        List<String> unexpectedMatches = 
            asList("m#ain.css|#main.js|#notTaken|#123|#?.,"
            .split("\\|"));
        
        assertFalse(matches.containsAll(unexpectedMatches), "Some unexpected matches were found!");
    }
}
