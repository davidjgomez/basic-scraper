package com.belatrixsf.scraper.pattern;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Text patterns base class
 * @author David Gomez
 */
public abstract class TextPattern implements ScraperPattern {

    /**
     * Matches the pattern with the provided HTML text document
     * @param document HTML document to find words matching the pattern
     * @return stream with the matched strings
     */
    @Override
    public List<String> match(Document document) {

        Elements elements = document.getAllElements();
        Predicate<String> patternPredicate = Pattern.compile(getTextPattern()).asPredicate();

        return elements.stream()
                .map(element -> element.text().split("\\s"))
                .flatMap(Stream::of)
                .filter(patternPredicate)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Returns the used text pattern
     * @return text pattern used
     */
    protected abstract String getTextPattern();
}
