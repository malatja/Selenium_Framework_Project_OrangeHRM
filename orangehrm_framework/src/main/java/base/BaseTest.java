package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.ITestContext;
import org.testng.ITestResult;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import utils.ConfigReader;
import utils.HybridStepLogger;
import utils.AllureEnvironmentManager;


public class BaseTest {

    protected WebDriver driver;

    protected String browser;

    protected WebDriverWait wait;


    /**
     * Creates Allure environment information
     * before executing the TestNG suite.
     */
    @BeforeSuite(alwaysRun = true)
    public void createAllureEnvironment(ITestContext context) {

        // Retrieve actual TestNG suite name
        String suiteName = context.getSuite().getName();

        // Generate Allure environment.properties
        AllureEnvironmentManager.createEnvironmentFile(
                suiteName
        );

        System.out.println(
                "TEST SUITE: " + suiteName
        );
    }


    /**
     * Initialises browser before every test method.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        // Get browser from config.properties
        browser = System.getProperty(
                "browser",
                ConfigReader.getProperty("browser")
        );


        // Get headless configuration
        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );

        System.out.println(
                "EXECUTION MODE: "
                        + (headless ? "HEADLESS" : "NORMAL")
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


    /**
     * Captures final evidence and closes browser
     * after every test method.
     */
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