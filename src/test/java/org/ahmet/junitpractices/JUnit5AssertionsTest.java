package org.ahmet.junitpractices;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JUnit5AssertionsTest {

    @Test
    public void hardAssertions() {
        assertEquals(10, 5 + 5, "First assertion failed");
        System.out.println("First assertion passed.");

        assertEquals(10, 5 + 3, "Second assertion failed");
        System.out.println("Second assertion passed.");
    }

    @Test
    public void softAssertions() {
        assertAll("Soft Assertions",
                () -> assertEquals(10, 5 + 5, "First assertion failed"),
                () -> assertEquals(10, 5 + 3, "Second assertion failed"),
                () -> assertEquals(10, 4 + 4, "Third assertion failed")
        );
    }
}