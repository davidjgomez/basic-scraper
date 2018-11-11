# basic-scraper

Basic Java Scraper to search for an specific pattern in the main page of a collection of web pages given in a file. The scraper finds strings and write them down in a new file per site.

## Configuration and Patterns

There is a __scraper.properties__ file in the project where there is a property __pattern.className__ which is used to establish the pattern to be used if there is not a pattern provided from the system properties given on runtime.

There are two patterns implemented:
* __AtPattern (Default):__ Allows to find twitter usernames (with the @)
* __HashtagPattern:__ Allows to find hashtags (with the #)

## Instructions

### Running the Application

Maven can be used to execute the application. In order to use the default pattern, there is only necessary to run the __mvn exec:java__ command.

If it is desired to change the pattern, it can be given using the __pattern.className__ parameter just like this:

__mvn exec:java -Dpattern.className=com.belatrixsf.scraper.pattern.HashtagPattern__

Everytime the application is run, there is going to be created a log file called __basic-scraper.log__ in the root directory of the project.

All the messages are also printed in the command line.

### Running Tests

To run the test it is only necessary to run the command __mvn clean test__ and all the tests will be executed.

## Dependencies

The application depends on the following libraries:

* __JSoup (version 1.11.3):__ Used to connect and get the web pages.
* __JUnit (version 5.3.1):__ Used for unit testing. It was needed to use both Jupiter (normal tests) and Vintage (powermock tests) engines.
* __Powermock (version 1.7.1):__ Used for unit testing where static methods mocking was needed. This library was used specifically with Mockito.
* __Logback (version 1.2.3):__ Used for logging. This library is a native SLF4J implementation.

This application was done to be used with __Java 1.8+__
