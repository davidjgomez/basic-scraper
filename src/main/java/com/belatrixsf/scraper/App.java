package com.belatrixsf.scraper;

import static com.belatrixsf.scraper.config.Config.getMessage;

import java.util.Scanner;

/**
 * Scraper main class
 * @author David Gomez
 */
public class App {
    public static void main(String[] args) {
        System.out.println(getMessage("app.welcome"));
        System.out.println(getMessage("app.queryURLsFilePath"));
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        scanner.close();

        Scraper.scrap(fileName);
    }
}
