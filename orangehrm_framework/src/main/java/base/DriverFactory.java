package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    /*
     * ThreadLocal WebDriver ensures that
     * every parallel thread has its own browser.
     */

    private static final ThreadLocal<WebDriver> driverThread =
            new ThreadLocal<>();


    /**
     * Initialises browser for the current thread.
     */
    public static void initializeDriver(String browserName) {

        // Prevent accidental driver replacement
        if (driverThread.get() != null) {

            throw new IllegalStateException(
                    "WebDriver is already initialised for this thread."
            );
        }

        // Default browser
        if (browserName == null || browserName.isBlank()) {

            browserName = "chrome";
        }
        

        // Read headless configuration from Maven
        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );

        WebDriver driver;

        switch (browserName.toLowerCase()) {

            case "chrome":

                ChromeOptions chromeOptions = new ChromeOptions();

                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }

                driver = new ChromeDriver(chromeOptions);

                break;


            case "firefox":

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (headless) {
                    firefoxOptions.addArguments("-headless");
                }

                driver = new FirefoxDriver(firefoxOptions);

                break;


            case "edge":

                EdgeOptions edgeOptions = new EdgeOptions();

                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--window-size=1920,1080");
                }

                driver = new EdgeDriver(edgeOptions);

                break;


            default:

                throw new IllegalArgumentException(
                        "Unsupported browser: "
                                + browserName
                                + ". Supported browsers: chrome, firefox, edge."
                );
        }

        // Store driver against current thread
        driverThread.set(driver);

        try {

            // Maximise browser window
            getDriver()
                    .manage()
                    .window()
                    .maximize();

        } catch (RuntimeException e) {

            quitDriver();

            throw e;
        }

        System.out.println(
                "DRIVER INITIALISED | Browser: "
                        + browserName
                        + " | Headless: "
                        + headless
                        + " | Thread: "
                        + Thread.currentThread().getId()
        );
    }


    /**
     * Returns WebDriver for current thread.
     */
    public static WebDriver getDriver() {

        WebDriver driver = driverThread.get();

        if (driver == null) {

            throw new IllegalStateException(
                    "WebDriver has not been initialised. "
                            + "Call initializeDriver() first."
            );
        }

        return driver;
    }


    /**
     * Checks whether current thread has a driver.
     */
    public static boolean hasDriver() {

        return driverThread.get() != null;
    }


    /**
     * Closes browser and removes ThreadLocal reference.
     */
    public static void quitDriver() {

        WebDriver driver = driverThread.get();

        if (driver != null) {

            try {

                driver.quit();

            } finally {

                driverThread.remove();
            }
        }
    }
}