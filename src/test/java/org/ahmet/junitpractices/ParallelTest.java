package org.ahmet.junitpractices;

import org.junit.jupiter.api.Test;

/**
 * This class demonstrates parallel test execution using JUnit 5.
 *
 * <p>To enable parallel execution, the following configurations were applied:</p>
 * <ul>
 *   <li>Added a `junit-platform.properties` file in `src/test/resources` with the following content:
 *     <pre>
 *     junit.jupiter.execution.parallel.enabled = true
 *     junit.jupiter.execution.parallel.mode.default = concurrent
 *     junit.jupiter.execution.parallel.mode.classes.default = concurrent
 *     </pre>
 *   </li>
 *   <li>Configured the Maven Surefire Plugin in the `pom.xml` file:
 *     <pre>
 *     &lt;plugin&gt;
 *         &lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;
 *         &lt;artifactId&gt;maven-surefire-plugin&lt;/artifactId&gt;
 *         &lt;version&gt;3.0.0-M8&lt;/version&gt;
 *         &lt;configuration&gt;
 *             &lt;parallel&gt;methods&lt;/parallel&gt;
 *             &lt;threadCount&gt;4&lt;/threadCount&gt;
 *         &lt;/configuration&gt;
 *     &lt;/plugin&gt;
 *     </pre>
 *   </li>
 * </ul>
 *
 * <p>When executed, the tests in this class will run concurrently on separate threads.</p>
 */
class ParallelTest {

    /**
     * Simulates a test that runs in parallel.
     *
     * @throws InterruptedException if the thread is interrupted during sleep
     */
    @Test
    void test1() throws InterruptedException {
        System.out.println("Running test1 on thread: " + Thread.currentThread().getName());
        Thread.sleep(1000);
    }

    /**
     * Simulates another test that runs in parallel.
     *
     * @throws InterruptedException if the thread is interrupted during sleep
     */
    @Test
    void test2() throws InterruptedException {
        System.out.println("Running test2 on thread: " + Thread.currentThread().getName());
        Thread.sleep(1000);
    }
}