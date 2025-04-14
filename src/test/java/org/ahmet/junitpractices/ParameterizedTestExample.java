package org.ahmet.junitpractices;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParameterizedTestExample {

    @ParameterizedTest
    @ValueSource(ints = {10, 20, 30, 40})
    public void testNumbersLessThan50(int number) {
        assertTrue(number < 50, "Number is not less than 50");
    }
}