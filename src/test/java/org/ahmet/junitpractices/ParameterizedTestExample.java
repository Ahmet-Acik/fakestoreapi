package org.ahmet.junitpractices;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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

    // Testing data from an external CSV file
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    // Reads data from 'data.csv', skipping the first line (header)
    public void testCsvFileSource(String name, int age) {
        assertTrue(name.length() > 0, "Name is empty"); // Asserts that the name is not empty
        assertTrue(age > 0, "Age is not positive"); // Asserts that the age is a positive number
    }

    // Testing addition with a variety of cases using a custom ArgumentsProvider
    @ParameterizedTest
    @ArgumentsSource(ComplexAdditionArgumentsProvider.class) // Uses a custom provider for test data
    public void testComplexAddition(int a, int b, int expectedSum) {
        int actualSum = a + b; // Calculates the sum of a and b
        assertEquals(expectedSum, actualSum, "Sum is incorrect"); // Asserts that the calculated sum matches the expected sum
    }

    // Custom ArgumentsProvider to supply more complex test data
    static class ComplexAdditionArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(org.junit.jupiter.api.extension.ExtensionContext context) {
            return Stream.of(
                    Arguments.of(1, 2, 3),         // Simple case
                    Arguments.of(-1, -2, -3),     // Negative numbers
                    Arguments.of(0, 0, 0),        // Zero case
                    Arguments.of(1000, 2000, 3000), // Large numbers
                    Arguments.of(-5, 5, 0)        // Mixed positive and negative
            );
        }
    }

    enum Status {
        ACTIVE, INACTIVE, PENDING
    }

    // Testing all enum constants
    @ParameterizedTest
    @EnumSource(Status.class) // Provides all constants of the Status enum
    public void testEnumValues(Status status) {
        assertNotNull(status, "Enum value is null"); // Asserts that the enum value is not null
    }

}