package utils;

import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import base.DriverFactory;

public class TestNGListener
        implements ITestListener, IExecutionListener {


    /**
     * Runs once before the complete
     * TestNG execution starts.
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


        /*
         * Use default description
         * when @Test description is empty.
         */
        if (description == null
                || description.isBlank()) {

            description =
                    "OrangeHRM automated test";
        }


        /*
         * Create the test inside
         * Extent Reports.
         */
        ExtentReportsManager.createTest(
                testName,
                description
        );


        /*
         * Log test start.
         */
        if (ExtentReportsManager.getTest() != null) {

            ExtentReportsManager
                    .getTest()
                    .info(
                            "Test Started: "
                                    + testName
                    );
        }


        System.out.println(
                "TEST STARTED: "
                        + testName
        );
    }


    /**
     * Runs when a test PASSES.
     */
    @Override
    public void onTestSuccess(
            ITestResult result) {

        String testName =
                result.getMethod()
                        .getMethodName();


        /*
         * Capture final PASS screenshot.
         *
         * HybridStepLogger will:
         *
         * 1. Take ONE screenshot
         * 2. Save screenshot physically
         * 3. Attach screenshot to Allure
         * 4. Display screenshot in Extent
         */
        if (DriverFactory.hasDriver()) {

            HybridStepLogger
                    .logStepWithScreenshot(
                            DriverFactory.getDriver(),
                            testName
                                    + " - PASS Evidence"
                    );
        }


        /*
         * Mark test as PASSED
         * inside Extent Report.
         */
        if (ExtentReportsManager.getTest() != null) {

            ExtentReportsManager
                    .getTest()
                    .pass(
                            "✅ Test Passed"
                    );
        }


        System.out.println(
                "TEST PASSED: "
                        + testName
        );


        /*
         * Remove ExtentTest from
         * current ThreadLocal.
         */
        ExtentReportsManager.removeTest();
    }


    /**
     * Runs when a test FAILS.
     */
    @Override
    public void onTestFailure(
            ITestResult result) {

        String testName =
                result.getMethod()
                        .getMethodName();

        Throwable throwable =
                result.getThrowable();


        /*
         * Capture FAILURE screenshot
         * before WebDriver is closed.
         *
         * The SAME screenshot is attached
         * to both Allure and Extent.
         */
        if (DriverFactory.hasDriver()) {

            HybridStepLogger
                    .logStepWithScreenshot(
                            DriverFactory.getDriver(),
                            testName
                                    + " - FAILURE Evidence"
                    );
        }


        /*
         * Add exception / stack trace
         * to Extent Report.
         */
        if (ExtentReportsManager.getTest() != null) {

            if (throwable != null) {

                ExtentReportsManager
                        .getTest()
                        .fail(throwable);

            } else {

                ExtentReportsManager
                        .getTest()
                        .fail(
                                "❌ Test Failed"
                        );
            }
        }


        System.err.println(
                "TEST FAILED: "
                        + testName
        );


        /*
         * Remove ExtentTest from
         * current ThreadLocal.
         */
        ExtentReportsManager.removeTest();
    }


    /**
     * Runs when a test is SKIPPED.
     */
    @Override
    public void onTestSkipped(
            ITestResult result) {

        String testName =
                result.getMethod()
                        .getMethodName();

        Throwable throwable =
                result.getThrowable();


        /*
         * Capture screenshot if
         * WebDriver is available.
         */
        if (DriverFactory.hasDriver()) {

            HybridStepLogger
                    .logStepWithScreenshot(
                            DriverFactory.getDriver(),
                            testName
                                    + " - SKIPPED Evidence"
                    );
        }


        /*
         * Mark test as skipped
         * in Extent Reports.
         */
        if (ExtentReportsManager.getTest() != null) {

            ExtentReportsManager
                    .getTest()
                    .skip(
                            "⚠️ Test Skipped"
                    );


            /*
             * Add reason / exception
             * when available.
             */
            if (throwable != null) {

                ExtentReportsManager
                        .getTest()
                        .skip(throwable);
            }
        }


        System.out.println(
                "TEST SKIPPED: "
                        + testName
        );


        /*
         * Remove ExtentTest from
         * ThreadLocal.
         */
        ExtentReportsManager.removeTest();
    }


    /**
     * Runs once after the complete
     * TestNG execution finishes.
     */
    @Override
    public void onExecutionFinish() {

        /*
         * Write all Extent test information
         * into the HTML report.
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


        /*
         * Extent Report location
         */
        System.out.println(
                "Extent Report:"
        );

        System.out.println(
                System.getProperty("user.dir")
                        + "/target/extent-report/"
                        + "Extent_EvidenceReport.html"
        );


        /*
         * Allure Results location
         */
        System.out.println(
                "Allure Results:"
        );

        System.out.println(
                System.getProperty("user.dir")
                        + "/target/allure-report/"
                        + "allure-results"
        );


        System.out.println(
                "=========================================="
        );
    }
}