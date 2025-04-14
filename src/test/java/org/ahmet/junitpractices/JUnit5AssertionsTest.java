package org.ahmet.junitpractices;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JUnit5AssertionsTest {

    @Test
    public void hardAssertions() {
        System.out.println("Starting hard assertions test...");

        // This assertion will pass, and the next line will execute.
        assertEquals(10, 5 + 5, "First assertion failed");
        System.out.println("First assertion passed.");

        // This assertion will fail, and the test execution will stop immediately.
        assertEquals(10, 5 + 3, "Second assertion failed");
        System.out.println("Second assertion passed."); // This line will not execute.
    }

    @Test
    public void softAssertions() {
        System.out.println("Starting soft assertions test...");

        // Using assertAll to group multiple assertions.
        assertAll("Soft Assertions",
                () -> {
                    // This assertion will pass, and the next line will execute.
                    assertEquals(10, 5 + 5, "First assertion failed");
                    System.out.println("First assertion passed.");
                },
                () -> {
                    // This assertion will fail, but execution will continue to the next assertion.
                    assertEquals(10, 5 + 3, "Second assertion failed");
                    System.out.println("Second assertion passed."); // This line will execute.
                },
                () -> {
                    // This assertion will fail, but execution will continue to the next assertion.
                    assertEquals(10, 4 + 4, "Third assertion failed");
                    System.out.println("Third assertion passed."); // This line will execute.
                }
        );
        // All failures will be reported together at the end of the test.
    }
}