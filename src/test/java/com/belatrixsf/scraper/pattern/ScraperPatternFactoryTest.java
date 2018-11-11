package com.belatrixsf.scraper.pattern;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

import java.util.Optional;

import com.belatrixsf.scraper.pattern.exception.PatternNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Tests for the pattern factory
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ScraperPatternFactory.class)
public class ScraperPatternFactoryTest {

    /**
     * Creates the partially mocked class
     */
    @Before
    public void beforeEach() {
        spy(ScraperPatternFactory.class);
    }

    /**
     * Tests that a pattern has not been found
     * @throws Exception if an error using PowerMockito is found
     */
    @Test
    public void mustNotFindThePattern() throws Exception {
        doReturn(Optional.of("notExistentPattern"))
            .when(ScraperPatternFactory.class, "getClassName");
        
        assertThrows(PatternNotFoundException.class, ScraperPatternFactory::getPattern, 
                "Pattern must not be present!");

        verifyPrivate(ScraperPatternFactory.class).invoke("getClassName");
    }

    /**
     * Tests that the correct pattern is returned
     * @throws Exception if an error using PowerMockito is found
     */
    @Test
    public void mustReturnAnExpectedPattern() throws Exception {
        doReturn(Optional.of(AtPattern.class.getCanonicalName()))
            .when(ScraperPatternFactory.class, "getClassName");

        ScraperPattern pattern = 
            assertDoesNotThrow(ScraperPatternFactory::getPattern, 
                                "Pattern must be present!");

        assertThat("Not the expected pattern!", pattern, instanceOf(AtPattern.class));

        verifyPrivate(ScraperPatternFactory.class).invoke("getClassName");
    }
}
