package com.shortener.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IDConverterTest {

	@Test
    public void TestSingletonObject(){
        IDConverter instance1 = IDConverter.INSTANCE;
        IDConverter instance2 = IDConverter.INSTANCE;
        Assertions.assertEquals(instance1, instance2, "Same instance for different objects");
    }
}
