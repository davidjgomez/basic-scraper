package com.belatrixsf.scraper.pattern.exception;

import com.belatrixsf.scraper.config.Config;

/**
 * Pattern not found Exception
 * @author David Gomez
 */
public class PatternNotFoundException extends Exception {

    /** Serial version */
    private static final long serialVersionUID = 349549834983403L;

    /**
     * Constructor
     * @param message exception message
     */
    public PatternNotFoundException() {
        super(Config.getMessage("pattern.exception.patternNotFoundException"));
    }
}
