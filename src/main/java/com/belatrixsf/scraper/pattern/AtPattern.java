package com.belatrixsf.scraper.pattern;

import static com.belatrixsf.scraper.config.Config.getMessage;

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
        return getMessage("pattern.atPattern.name");
    }

    /**
     * Returns the used at text pattern
     * @return text pattern used for at
     */
    @Override
    protected String getTextPattern() {
        return getMessage("pattern.atPattern.regex");
    }
}
