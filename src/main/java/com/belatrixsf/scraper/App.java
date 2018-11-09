package com.belatrixsf.scraper;

import java.util.Scanner;

/**
 * Spider main class
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Welcome to the basic spider!");
        System.out.println("Choose one pattern among the following to process the pages: ");


        System.out.println("Please write the file with its absolute path: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        scanner.close();

        Scraper.scrap(fileName);
    }
}
