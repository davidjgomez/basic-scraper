package com.belatrixsf.scraper;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;

import com.belatrixsf.scraper.config.ConfigTest;
import com.belatrixsf.scraper.pattern.HashtagPattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests the scraper task
 * @author David Gomez
 */
public class ScraperTaskTest {

    /**
     * Thread to create a mock server to test the scraper task
     * @author David Gomez
     */
    private static class MockServer extends Thread {

        private static final Logger LOGGER = LoggerFactory.getLogger(MockServer.class);
        /**
         * Creates and sends a long test HTML
         * @param out stream to send the HTML to the scraper
         */
        private void sendLongHTML(PrintWriter out) {
            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("\r\n");

            StringBuilder html = new StringBuilder();

            for(int i=0; i < 15000; i++) {
                html.append("<div>#HashtagTest").append(i).append(" </div>");
                html.append("<span>Another thing ").append(i).append("</span>");
            } 

            out.println(html.toString());
        }

        /**
         * Creates a mock HTTP server
         */
        @Override
        public void run() {

            try(ServerSocket server = new ServerSocket(9999);
                Socket client = server.accept();
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            ) {
                in.readLine();
                sendLongHTML(out);  
            }
            catch(IOException e) {
                LOGGER.error("Error in HTML test server: ", e);
            }
        }
    }

    /**
     * Tests that scraping a mock HTML page does not exceed a time limit
     */
    @Test
    public void mustProcessPageOnTime() {
        new MockServer().start();

        Executable executable = () -> new ScraperTask(
            ConfigTest.TEST_PATH, "http://localhost:9999", 
            new HashtagPattern()).run();

        assertTimeoutPreemptively(Duration.ofSeconds(5), executable, "Execution time exceeded 5 seconds!");
    }
}
