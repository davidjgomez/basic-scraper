package com.belatrixsf.scraper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.List;

import com.belatrixsf.scraper.io.MatchesFileWriter;
import com.belatrixsf.scraper.pattern.ScraperPattern;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Scraper task to process one specific URL
 * @author David Gomez
 */
public class ScraperTask implements Runnable {

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
            System.out.println("Processing " + url + "...");

            Document document = Jsoup.connect(url).get();
            List<String> matches = pattern.match(document);
            new MatchesFileWriter(path, url).write(matches);

            System.out.println("End of process of " + url);
        } catch (MalformedURLException e) {
            System.out.println("The URL is malformed or is using an inadequate protocol: " + url);
        } catch (HttpStatusException e) {   
			System.out.println("Error "+ e.getStatusCode() + " requesting the page: " + e.getMessage());
        } catch (SocketTimeoutException e) {   
			System.out.println("Timeout requesting " + url);
		} catch (IOException e) {   
			System.out.println("Cannot process the URL ("+ url + "): " + e.getMessage());
		}
    }
}
