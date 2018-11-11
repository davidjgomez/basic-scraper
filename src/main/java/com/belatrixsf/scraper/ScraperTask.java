package com.belatrixsf.scraper;

import static com.belatrixsf.scraper.config.Config.getMessage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.List;

import com.belatrixsf.scraper.io.MatchesFileWriter;
import com.belatrixsf.scraper.pattern.ScraperPattern;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scraper task to process one specific URL
 * @author David Gomez
 */
public class ScraperTask implements Runnable {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ScraperTask.class);

    /** Path used to create the output file */
    private String path;

    /** URL to be analized */
    private String url;

    /** Pattern used to search matches */
    private ScraperPattern pattern;

    /**
     * Constructor
     * @param url URL to be analized
     * @param pattern pattern used to search matches
     */
    public ScraperTask(String path, String url, ScraperPattern pattern) {
        this.path = path;
        this.url = url;
        this.pattern = pattern;
    }

    /**
     * Connects to the URL, finds matches and generates the output
     */
    @Override
    public void run() {
        try {
            LOGGER.info(getMessage("scraperTask.scrapingFile", url));

            Document document = Jsoup.connect(url).get();
            List<String> matches = pattern.match(document);
            new MatchesFileWriter(path, url).write(matches);
        } catch (IllegalArgumentException | MalformedURLException e) {
            LOGGER.error(getMessage("scraperTask.errorMalformedURL", e));
        } catch (HttpStatusException e) {   
			LOGGER.error(getMessage("scraperTask.errorRequestingPage", e.getStatusCode(), e.getMessage()));
        } catch (SocketTimeoutException e) {   
			LOGGER.error(getMessage("scraperTask.errorTimeout", url));
		} catch (IOException e) {   
			LOGGER.error(getMessage("scraperTask.errorProcessingPage", url, e.getMessage()));
		}
    }
}
