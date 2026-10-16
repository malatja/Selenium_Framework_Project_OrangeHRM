
package utils;

import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListener
        implements ITestListener, IExecutionListener {


    /**
     * Runs once before the complete TestNG execution.
     */

    @Override
    public void onExecutionStart() {

        // Initialise Extent Reports
        ExtentReportsManager.getExtentReports();

        // Create Allure environment information
        AllureEnvironmentManager.createEnvironmentFile();

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "AUTOMATION TEST EXECUTION STARTED"
        );

        System.out.println(
                "PARALLEL EXECUTION ENABLED"
        );

        System.out.println(
                "=========================================="
        );
    }


    /**
     * Runs whenever a test method starts.
     */

    @Override
    public void onTestStart(ITestResult result) {

        String testName =
                result.getMethod()
                        .getMethodName();

        String description =
                result.getMethod()
                        .getDescription();


        // Default description
        if (description == null
                || description.isBlank()) {

            description =
                    "OrangeHRM automated test";
        }


        // Create ExtentTest for current thread
        ExtentReportsManager.createTest(
                testName,
                description
        );


        // Log test start
        if (ExtentReportsManager.getTest() != null) {

            ExtentReportsManager
                    .getTest()
                    .info(
                            "Test Started: " + testName
                    );

            ExtentReportsManager
                    .getTest()
                    .info(
                            "Thread ID: "
                                    + Thread.currentThread().getId()
                    );
        }


        System.out.println(
                "TEST STARTED: "
                        + testName
                        + " | Thread: "
                        + Thread.currentThread().getId()
        );
    }


    /**
     * Runs when a test PASSES.
     */

    @Override
    public void onTestSuccess(ITestResult result) {

        String testName =
                result.getMethod()
                        .getMethodName();


        if (ExtentReportsManager.getTest() != null) {

            ExtentReportsManager
                    .getTest()
                    .pass(
                            "TEST PASSED"
                    );
        }


        System.out.println(
                "TEST PASSED: " + testName
        );


        // Remove current thread ExtentTest
        ExtentReportsManager.removeTest();
    }


    /**
     * Runs when a test FAILS.
     */

    @Override
    public void onTestFailure(ITestResult result) {

        String testName =
                result.getMethod()
                        .getMethodName();

        Throwable throwable =
                result.getThrowable();


        // Record failure in Extent Report
        if (ExtentReportsManager.getTest() != null) {

            if (throwable != null) {

                ExtentReportsManager
                        .getTest()
                        .fail(throwable);

            } else {

                ExtentReportsManager
                        .getTest()
                        .fail(
                                "TEST FAILED"
                        );
            }
        }


        System.err.println(
                "TEST FAILED: " + testName
        );


        // Remove current thread ExtentTest
        ExtentReportsManager.removeTest();
    }


    /**
     * Runs when a test is SKIPPED.
     */

    @Override
    public void onTestSkipped(ITestResult result) {

        String testName =
                result.getMethod()
                        .getMethodName();

        Throwable throwable =
                result.getThrowable();


        // Record skipped test in Extent Report
        if (ExtentReportsManager.getTest() != null) {

            ExtentReportsManager
                    .getTest()
                    .skip(
                            "TEST SKIPPED"
                    );

            if (throwable != null) {

                ExtentReportsManager
                        .getTest()
                        .skip(throwable);
            }
        }


        System.out.println(
                "TEST SKIPPED: " + testName
        );


        // Remove current thread ExtentTest
        ExtentReportsManager.removeTest();
    }


    /**
     * Runs once after the complete TestNG execution.
     */

    @Override
    public void onExecutionFinish() {

        /*
         * Flush only after all parallel
         * test executions have completed.
         */

        ExtentReportsManager.flushReport();


        System.out.println(
                "=========================================="
        );

        System.out.println(
                "AUTOMATION TEST EXECUTION COMPLETED"
        );

        System.out.println(
                "=========================================="
        );


        // Extent Report location
        System.out.println(
                "Extent Report:"
        );

        System.out.println(
                System.getProperty("user.dir")
                        + "/target/extent-report/"
                        + "Extent_EvidenceReport.html"
        );


        // Allure Results location
        System.out.println(
                "Allure Results:"
        );

        System.out.println(
                System.getProperty("user.dir")
                        + "/target/allure-report/"
                        + "allure-results"
        );


        // Screenshot location
        System.out.println(
                "Screenshots:"
        );

        System.out.println(
                System.getProperty("user.dir")
                        + "/target/test-artifacts/screenshots"
        );


        System.out.println(
                "=========================================="
        );
    }
}