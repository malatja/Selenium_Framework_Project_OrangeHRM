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

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();


    private ExtentReportsManager() {
        // Prevent object creation
    }


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
                    System.getProperty(
                            "browser",
                            "chrome"
                    )
            );
        }

        return extentReports;
    }


    public static void createTest(
            String testName,
            String description) {

        ExtentTest test =
                getExtentReports()
                        .createTest(
                                testName,
                                description
                        );

        extentTest.set(test);
    }


    public static ExtentTest getTest() {

        return extentTest.get();
    }


    public static void removeTest() {

        extentTest.remove();
    }


    public static synchronized void flushReport() {

        if (extentReports != null) {
            extentReports.flush();
        }
    }


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