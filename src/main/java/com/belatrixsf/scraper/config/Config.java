package com.belatrixsf.scraper.config;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Configuration of the application
 * @author David Gomez
 */
public enum Config {
    
    INSTANCE;
    
    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    /** Properties configuration */
    private static Properties scraperConfig;

    /** Resource Bundle */
    private static ResourceBundle messages;

    /**
     * Initializes the application properties
     */
    static {
        scraperConfig = new Properties();
        InputStream configFile = Config.class.getResourceAsStream("/scraper.properties");
        
        try {
            scraperConfig.load(configFile);
            messages = ResourceBundle.getBundle("messagesBundle");
        } catch (IOException e) {
            LOGGER.error("Error loading the scraper config file", e);
        }
    }

    /**
     * Returns a property value if present, otherwise empty
     * @param key key name of the property
     * @return optional object with the property value
     */
    public static Optional<String> getProperty(String key) {
        String value = System.getProperty(key, scraperConfig.getProperty(key));
        return Optional.ofNullable(value);
    }

    /**
     * Returns a message value if present, otherwise empty
     * @param key key name of the message
     * @return the message value or empty if not found
     */
    public static String getMessage(String key, Object... args) {
        String value = MessageFormat.format(messages.getString(key), args);
        return Optional.of(value).orElse("");
    }
}
