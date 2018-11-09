package com.belatrixsf.scraper.pattern;

/**
 * Hashtag pattern
 * @author David Gomez
 */
public class HashtagPattern extends TextPattern {

    /**
     * Returns the pattern name
     * @return pattern name
     */
    @Override
    public String getName() {
        return "Hashtag (#) Pattern";
    }

    /**
     * Returns the used hashtag text pattern
     * @return text pattern used for hashtags
     */
    @Override
    protected String getTextPattern() {
        return "\\B#\\w*[a-zA-Z]+\\w*";
    }
}
