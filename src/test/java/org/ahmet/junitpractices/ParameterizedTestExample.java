package org.ahmet.junitpractices;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParameterizedTestExample {

    // Testing if numbers are less than 50 using a fixed set of integers
    @ParameterizedTest
    @ValueSource(ints = {10, 20, 30, 40}) // Provides a set of integers as input
    public void testNumbersLessThan50(int number) {
        assertTrue(number < 50, "Number is not less than 50"); // Asserts that the number is less than 50
    }

    // Testing the sum of two numbers using inline CSV data
    @ParameterizedTest
    @CsvSource({"10, 20, 30", // First row: a=10, b=20, expectedSum=30
            "30, 40, 70", // Second row: a=30, b=40, expectedSum=70
            "50, 60, 110", // Third row: a=50, b=60, expectedSum=110
    })
    public void testSum(int a, int b, int expectedSum) {
        int actualSum = a + b; // Calculates the sum of a and b
        assertTrue(actualSum == expectedSum, "Sum is incorrect"); // Asserts that the calculated sum matches the expected sum
    }

    // Testing if strings are non-empty using a method as the data source
    @ParameterizedTest
    @MethodSource("provideNonEmptyStrings") // Uses a method to provide test data
    public void testNonEmptyStrings(String str) {
        assertTrue(str.length() > 0, "The string \"" + str + "\" is unexpectedly empty."); // Asserts that the string is not empty
    }

    // Provides a set of non-empty strings for the test above
    private static String[] provideNonEmptyStrings() {
        return new String[]{"Test", "Example", "ParameterizedTest", "JUnit5"}; // Returns an array of non-empty strings
    }



}