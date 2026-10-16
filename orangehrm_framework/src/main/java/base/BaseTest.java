
package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.ConfigReader;
import utils.HybridStepLogger;

public class BaseTest {

    protected WebDriver driver;
    protected String browser;
    protected WebDriverWait wait;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        // Get browser from config.properties
        browser = System.getProperty(
                "browser",
                ConfigReader.getProperty("browser")
        );

        // Initialise browser for current thread
        DriverFactory.initializeDriver(browser);

        // Get thread-specific WebDriver instance
        driver = DriverFactory.getDriver();

        // Create explicit wait
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );

        // Get OrangeHRM URL
        String orangeHRMUrl =
                ConfigReader.getProperty("OrangeHRM_Url");

        // Open OrangeHRM
        driver.get(orangeHRMUrl);

        // Wait until application is opened
        wait.until(
                ExpectedConditions.urlContains(
                        "orangehrmlive.com"
                )
        );

        System.out.println(
                "APPLICATION OPENED | Thread: "
                        + Thread.currentThread().getId()
        );

        System.out.println(
                "Browser: " + browser
        );

        System.out.println(
                "Current URL: " + driver.getCurrentUrl()
        );
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        try {

            /*
             * Capture final evidence BEFORE
             * closing the browser.
             */

            if (DriverFactory.hasDriver()) {

                String testName =
                        result.getMethod().getMethodName();

                String evidenceName;

                switch (result.getStatus()) {

                    case ITestResult.SUCCESS:

                        evidenceName =
                                testName + " - PASS Evidence";
                        break;

                    case ITestResult.FAILURE:

                        evidenceName =
                                testName + " - FAILURE Evidence";
                        break;

                    case ITestResult.SKIP:

                        evidenceName =
                                testName + " - SKIPPED Evidence";
                        break;

                    default:

                        evidenceName =
                                testName + " - Execution Evidence";
                        break;
                }

                HybridStepLogger.logStepWithScreenshot(
                        DriverFactory.getDriver(),
                        evidenceName
                );
            }

        } finally {

            /*
             * Always close the browser belonging
             * to the current thread.
             */

            DriverFactory.quitDriver();

            System.out.println(
                    "BROWSER CLOSED | Thread: "
                            + Thread.currentThread().getId()
            );
        }
    }
}