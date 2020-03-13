package com.shortener.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class URLValidatorTest {

	@Test
    public void TestSingletonObject(){
        URLValidator instance1 = URLValidator.INSTANCE;
        URLValidator instance2 = URLValidator.INSTANCE;
        Assertions.assertEquals(instance1, instance2, "Same instance for different objects");
    }
	
	@Test
	public void TestURLMatch() {
		URLValidator urlValidator = URLValidator.INSTANCE;
		boolean match = urlValidator.validateURL("http://www.test.com");
		Assertions.assertTrue(match);
	}

}
