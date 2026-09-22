package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.ConfigReader;

public class BaseTest {

    protected WebDriver driver;
    protected String browser;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {

        // Get browser from config.properties
        browser = System.getProperty(
                "browser",
                ConfigReader.getProperty("browser")
        );

        // Initialise browser
        DriverFactory.initializeDriver(browser);

        // Get WebDriver instance
        driver = DriverFactory.getDriver();

        // Create explicit wait
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );

        // Get OrangeHRM URL from config.properties
        String orangeHRMUrl =
                ConfigReader.getProperty("OrangeHRM_Url");

        // Open OrangeHRM application
        driver.get(orangeHRMUrl);

        // Wait until the OrangeHRM URL is opened
        wait.until(
                ExpectedConditions.urlContains(
                        "orangehrmlive.com"
                )
        );

        System.out.println(
                "OrangeHRM application opened successfully."
        );

        System.out.println(
                "Current URL: " + driver.getCurrentUrl()
        );
    }

//     @AfterMethod
//     public void tearDown() {

//         DriverFactory.quitDriver();
//     }
}