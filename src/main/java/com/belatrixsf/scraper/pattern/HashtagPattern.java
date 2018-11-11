package com.belatrixsf.scraper.pattern;

import static com.belatrixsf.scraper.config.Config.getMessage;

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
        return getMessage("pattern.hashtagPattern.name");
    }

    /**
     * Returns the used hashtag text pattern
     * @return text pattern used for hashtags
     */
    @Override
    protected String getTextPattern() {
        return getMessage("pattern.hashtagPattern.regex");
    }
}
