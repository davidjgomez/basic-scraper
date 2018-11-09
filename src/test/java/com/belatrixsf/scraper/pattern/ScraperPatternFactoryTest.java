package com.belatrixsf.scraper.pattern;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
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
        PowerMockito.spy(ScraperPatternFactory.class);
    }

    /**
     * Tests that the correct pattern is returned
     * @throws Exception if an error using PowerMockito is found
     */
    @Test
    public void mustReturnAnExpectedPattern() throws Exception {
        PowerMockito.doReturn(Optional.of(AtPattern.class.getCanonicalName()))
            .when(ScraperPatternFactory.class, "getClassName");

        Optional<ScraperPattern> pattern = ScraperPatternFactory.getPattern();

        assertTrue(pattern.isPresent(), "Pattern must be present!");
        assertThat("Not the expected pattern!", pattern.get(), instanceOf(AtPattern.class));

        PowerMockito.verifyPrivate(ScraperPatternFactory.class)
            .invoke("getClassName");
    }
    
    /**
     * Tests that a pattern has not been found
     * @throws Exception if an error using PowerMockito is found
     */
    @Test
    public void mustNotFindThePattern() throws Exception {
        PowerMockito.doReturn(Optional.of("notExistentPattern"))
            .when(ScraperPatternFactory.class, "getClassName");

        Optional<ScraperPattern> pattern = ScraperPatternFactory.getPattern();
        
        assertFalse(pattern.isPresent(), "Pattern must not be present!");

        PowerMockito.verifyPrivate(ScraperPatternFactory.class)
            .invoke("getClassName");
    }
}
