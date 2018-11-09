package com.belatrixsf.scraper;

import static java.lang.Runtime.getRuntime;
import static java.util.concurrent.Executors.newFixedThreadPool;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import com.belatrixsf.scraper.io.URLFileReader;
import com.belatrixsf.scraper.pattern.ScraperPattern;
import com.belatrixsf.scraper.pattern.ScraperPatternFactory;

/**
 * Web scraper
 * @author David Gomez
 */
public class Scraper {

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
    private static void shutdown(ExecutorService executorService) throws InterruptedException {
        executorService.shutdown();
        if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
    }

    /**
     * Finds the matches over multiple webpages based on an established pattern
     * @param fileName URLs source file name
     */
    public static void scrap(String fileName) throws InterruptedException {
        Optional<ScraperPattern> pattern = ScraperPatternFactory.getPattern();
        
        if(pattern.isPresent()) {
            ExecutorService executorService = getExecutorService();

            try(Stream<String> urls = URLFileReader.readURLs(fileName)) {
                String parentPath = Paths.get(fileName).getParent().toString();
                urls.forEach(url -> executorService.execute(
                        new ScraperTask(parentPath, url, pattern.get())
                    ));
            }
            finally {
                shutdown(executorService);
            }
        }
        else {
            System.out.println("Unable to load pattern!");
        }
    }
}
