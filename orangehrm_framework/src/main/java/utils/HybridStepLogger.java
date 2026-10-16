
package utils;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

import io.qameta.allure.Allure;

public class HybridStepLogger {

    /*
     * Central screenshot directory.
     *
     * target/test-artifacts/screenshots/
     */

    private static final Path SCREENSHOT_DIR =
            Paths.get(
                    "target",
                    "test-artifacts",
                    "screenshots"
            );


    /**
     * Takes ONE screenshot and:
     *
     * 1. Saves it physically.
     * 2. Attaches it to Allure.
     * 3. Displays it inside Extent Report.
     *
     * Safe for parallel execution.
     */

    public static void logStepWithScreenshot(
            WebDriver driver,
            String stepName) {

        if (driver == null) {

            throw new IllegalStateException(
                    "WebDriver is null while logging step: "
                            + stepName
            );
        }

        try {

            // Create screenshot directory
            Files.createDirectories(
                    SCREENSHOT_DIR
            );


            // Clean screenshot name
            String cleanStepName =
                    stepName.replaceAll(
                            "[^a-zA-Z0-9]",
                            "_"
                    );


            // Limit filename length
            if (cleanStepName.length() > 40) {

                cleanStepName =
                        cleanStepName.substring(
                                0,
                                40
                        );
            }


            // Generate unique screenshot filename
            String fileName =
                    String.format(
                            "%d_%s_%s.png",

                            System.currentTimeMillis(),

                            UUID.randomUUID()
                                    .toString()
                                    .substring(0, 8),

                            cleanStepName
                    );


            Path screenshotPath =
                    SCREENSHOT_DIR.resolve(
                            fileName
                    );


            // Take ONE Selenium screenshot
            byte[] screenshotBytes =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(
                                    OutputType.BYTES
                            );


            // Save screenshot physically
            Files.write(
                    screenshotPath,
                    screenshotBytes
            );


            // Attach screenshot to Allure
            Allure.attachment(
                    stepName,
                    "image/png",
                    new ByteArrayInputStream(
                            screenshotBytes
                    ),
                    null
            );


            // Get ExtentTest for current thread
            ExtentTest extentTest =
                    ExtentReportsManager.getTest();


            if (extentTest != null) {

                /*
                 * Extent HTML:
                 *
                 * target/extent-report/
                 * Extent_EvidenceReport.html
                 *
                 * Screenshot:
                 *
                 * target/test-artifacts/screenshots/
                 */

                String relativePath =
                        "../test-artifacts/screenshots/"
                                + fileName;


                // Log execution step
                extentTest.info(
                        stepName
                );


                // Attach screenshot to Extent Report
                extentTest.addScreenCaptureFromPath(
                        relativePath
                );

            } else {

                System.out.println(
                        "EXTENT TEST NULL: "
                                + stepName
                );
            }


            System.out.println(
                    "SCREENSHOT CAPTURED: "
                            + stepName
                            + " | Thread: "
                            + Thread.currentThread().getId()
            );


        } catch (Exception e) {

            System.err.println(
                    "ERROR IN LOGGING STEP: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}