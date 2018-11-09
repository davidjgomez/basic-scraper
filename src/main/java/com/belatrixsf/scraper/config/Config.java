package com.belatrixsf.scraper.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

/**
 * Configuration of the application
 * @author David Gomez
 */
public class Config {
    
    /** Class unique instance */
    private static final Config INSTANCE = new Config();
    
    /** Properties configuration */
    private Properties scraperConfig;

    /**
     * Constructor
     * Initialises the application properties
     */
    private Config() {
        scraperConfig = new Properties();
        InputStream configFile = Config.class.getResourceAsStream("/scraper.properties");
        
        try {
            scraperConfig.load(configFile);
        } catch (IOException e) {
            System.out.println("Error loading the config file: " + e.getMessage());
		}
    }

    /**
     * Returns the instance of the configuration
     * @return instance
     */
    public static Config getInstance() {
        return INSTANCE;
    }

    /**
     * Returns a property value if present
     * @param key key name of the property
     * @return optional object with the property value
     */
    public Optional<String> getProperty(String key) {
        return Optional.ofNullable(scraperConfig.getProperty(key));
    }
}
