package com.belatrixsf.scraper;

import static com.belatrixsf.scraper.config.Config.getMessage;
import static java.lang.Runtime.getRuntime;
import static java.util.concurrent.Executors.newFixedThreadPool;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import com.belatrixsf.scraper.io.URLFileReader;
import com.belatrixsf.scraper.pattern.ScraperPattern;
import com.belatrixsf.scraper.pattern.ScraperPatternFactory;
import com.belatrixsf.scraper.pattern.exception.PatternNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web scraper
 * @author David Gomez
 */
public class Scraper {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(Scraper.class);

    /**
     * Returns the executor service
     * The 10 factor is due to an I/O-bound kind of activity of the scraper
     * @return executor service to parallelize the scrapping
     */ 
    private static ExecutorService getExecutorService() {
        int numProcessors = getRuntime().availableProcessors();
        return newFixedThreadPool(numProcessors * 10);
    }

    /**
     * Shutdowns the thread executor 
     * @param executorService executor to shutdown
     */
    private static void shutdown(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            LOGGER.error(getMessage("scraper.errorShutingDownService", e.getMessage()));
        }
        finally {
            LOGGER.info(getMessage("scraper.finished"));
        }
    }

    /**
     * Finds the matches over multiple webpages based on an established pattern
     * 
     * @param fileName URLs source file name
     * @param pattern  pattern used to find the matches
     * @throws IOException if it was not possible to read the file
     */
    private static void scrap(String fileName, ScraperPattern pattern) throws IOException {
		LOGGER.info(getMessage("scraper.starting", fileName, pattern.getName()));
        ExecutorService executorService = getExecutorService();

		try(Stream<String> urls = URLFileReader.readURLs(fileName)) {
		    String parentPath = Paths.get(fileName).getParent().toString();
		    urls.forEach(url -> executorService.execute(
		            new ScraperTask(parentPath, url, pattern)
		        ));
		}
		finally {
            shutdown(executorService);
        }
    }

    /**
     * Finds the matches over multiple webpages based on an established pattern
     * @param fileName URLs source file name
     */
    public static void scrap(String fileName) {
        try {
            ScraperPattern pattern = ScraperPatternFactory.getPattern();
            scrap(fileName, pattern);
        }
        catch(PatternNotFoundException e){
            LOGGER.error(e.getMessage());
        }
        catch (InvalidPathException | IOException e) {
            LOGGER.error(getMessage("scraper.errorReadingURLsFile", fileName));
        }
    }
}
