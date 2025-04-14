package org.ahmet.junitpractices;

import org.junit.jupiter.api.*;

public class JUnit5LifecycleTest {

    @BeforeAll
    public static void setupAll() {
        System.out.println("BeforeAll: Runs once before all tests.");
    }

    @BeforeEach
    public void setupEach() {
        System.out.println("BeforeEach: Runs before each test.");
    }

    @Test
    public void test1() {
        System.out.println("Test1: Running test 1.");
    }

    @Test
    public void test2() {
        System.out.println("Test2: Running test 2.");
    }

    @AfterEach
    public void tearDownEach() {
        System.out.println("AfterEach: Runs after each test.");
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("AfterAll: Runs once after all tests.");
    }
}