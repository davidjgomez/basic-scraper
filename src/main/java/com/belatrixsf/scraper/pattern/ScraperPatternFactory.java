package com.belatrixsf.scraper.pattern;

import static com.belatrixsf.scraper.config.Config.getMessage;

import java.util.Optional;

import com.belatrixsf.scraper.config.Config;
import com.belatrixsf.scraper.pattern.exception.PatternNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory to provide the available patterns
 * @author David Gomez
 */
public class ScraperPatternFactory {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ScraperPatternFactory.class);

    /**
     * Return the class name if found, otherwise empty
     * @return class name
     */
    private static Optional<String> getClassName() {
        return Config.getProperty("pattern.className");
    }

    /**
     * Return the selected pattern if found
     * @return optional object with the selected pattern or empty
     */
    private static Optional<ScraperPattern> getOptionalPattern() {
        Optional<String> className = getClassName();

        try {        
            if (className.isPresent()) {
                Class<?> clazz = Class.forName(className.get());
                return Optional.of((ScraperPattern) clazz.newInstance());
            }
        } 
        catch ( ClassNotFoundException | InstantiationException | 
                IllegalAccessException | ClassCastException e) {
            LOGGER.error(getMessage("pattern.scraperPatternFactory.errorCreatingPattern", e.getMessage()));
        }

        return Optional.empty();
    }

    /**
     * Return the selected pattern if found
     * 
     * @return selected pattern
     * @throws PatternNotFoundException if pattern was not found
     */
    public static ScraperPattern getPattern() throws PatternNotFoundException {
        return getOptionalPattern().orElseThrow(PatternNotFoundException::new);
    }
}
