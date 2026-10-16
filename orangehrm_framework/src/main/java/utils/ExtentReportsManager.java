
package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public final class ExtentReportsManager {

    private static final String REPORT_DIRECTORY =
            System.getProperty("user.dir")
                    + "/target/extent-report";

    private static final String REPORT_PATH =
            REPORT_DIRECTORY
                    + "/Extent_EvidenceReport.html";

    private static ExtentReports extentReports;

    /*
     * Every parallel thread gets its own ExtentTest.
     */

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();


    private ExtentReportsManager() {

        // Prevent object creation
    }


    /**
     * Creates a single ExtentReports instance.
     */

    public static synchronized ExtentReports getExtentReports() {

        if (extentReports == null) {

            createReportDirectory();

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(REPORT_PATH);

            sparkReporter.config().setDocumentTitle(
                    "OrangeHRM Automation Evidence Report"
            );

            sparkReporter.config().setReportName(
                    "OrangeHRM Selenium Automation Test Execution"
            );

            sparkReporter.config().setTheme(
                    Theme.STANDARD
            );

            extentReports = new ExtentReports();

            extentReports.attachReporter(
                    sparkReporter
            );

            // System information

            extentReports.setSystemInfo(
                    "Project",
                    "OrangeHRM Automation Framework"
            );

            extentReports.setSystemInfo(
                    "Automation Tool",
                    "Selenium WebDriver"
            );

            extentReports.setSystemInfo(
                    "Test Framework",
                    "TestNG"
            );

            extentReports.setSystemInfo(
                    "Java Version",
                    System.getProperty("java.version")
            );

            extentReports.setSystemInfo(
                    "Operating System",
                    System.getProperty("os.name")
            );

            extentReports.setSystemInfo(
                    "Browser",
                    System.getProperty("browser", "chrome")
            );

            extentReports.setSystemInfo(
                    "Parallel Execution",
                    "Enabled"
            );
        }

        return extentReports;
    }


    /**
     * Creates ExtentTest for current thread.
     */

    public static synchronized void createTest(
            String testName,
            String description) {

        ExtentTest test =
                getExtentReports().createTest(
                        testName,
                        description
                );

        extentTest.set(test);
    }


    /**
     * Returns ExtentTest for current thread.
     */

    public static ExtentTest getTest() {

        return extentTest.get();
    }


    /**
     * Removes ExtentTest from current thread.
     */

    public static void removeTest() {

        extentTest.remove();
    }


    /**
     * Flushes report after all tests finish.
     */

    public static synchronized void flushReport() {

        if (extentReports != null) {

            extentReports.flush();
        }
    }


    /**
     * Creates report directory.
     */

    private static void createReportDirectory() {

        try {

            Path directory =
                    Paths.get(REPORT_DIRECTORY);

            Files.createDirectories(directory);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to create Extent Report directory.",
                    e
            );
        }
    }
}