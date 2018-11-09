package com.belatrixsf.scraper.pattern;

/**
 * At pattern
 * @author David Gomez
 */
public class AtPattern extends TextPattern {

    /**
     * Returns the pattern name
     * @return pattern name
     */
    @Override
    public String getName() {
        return "At (@) Pattern";
    }

    /**
     * Returns the used at text pattern
     * @return text pattern used for at
     */
    @Override
    protected String getTextPattern() {
        return "^@[A-Za-z0-9_]{1,15}$";
    }
}
