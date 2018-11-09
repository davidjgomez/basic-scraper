package com.belatrixsf.scraper.pattern;

import java.util.Optional;

import com.belatrixsf.scraper.config.Config;

/**
 * Factory to provide the available patterns
 * @author David Gomez
 */
public class ScraperPatternFactory {

    /**
     * Return the class name if found, otherwise empty
     * @return class name
     */
    private static Optional<String> getClassName() {
        return Config.getInstance().getProperty("pattern.className");
    }

    /**
     * Return the selected pattern if found
     * @return optional object with the selected pattern or empty
     */
    public static Optional<ScraperPattern> getPattern() {
        Optional<String> className = getClassName();

        try {        
            if (className.isPresent()) {
                Class<?> clazz = Class.forName(className.get());
                return Optional.of((ScraperPattern) clazz.newInstance());
            }
        } 
        catch ( ClassNotFoundException | InstantiationException | 
                IllegalAccessException | ClassCastException e) {
            System.out.println("Impossible to create an instance of the Pattern class " + className + ": " + e.getMessage());
        }

        return Optional.empty();
    }
}
