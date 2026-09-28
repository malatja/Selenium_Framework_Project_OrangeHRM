package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    /*
     * ThreadLocal WebDriver allows each test thread
     * to have its own browser instance.
     */
    private static final ThreadLocal<WebDriver> driverThread =
            new ThreadLocal<>();


    /**
     * Initialises the browser.
     *
     * @param browserName Browser to launch:
     *                    chrome, firefox, or edge.
     */
    public static void initializeDriver(String browserName) {

        // Default browser
        if (browserName == null || browserName.isBlank()) {
            browserName = "chrome";
        }

        switch (browserName.toLowerCase()) {

            case "chrome":
                driverThread.set(
                        new ChromeDriver()
                );
                break;

            case "firefox":
                driverThread.set(
                        new FirefoxDriver()
                );
                break;

            case "edge":
                driverThread.set(
                        new EdgeDriver()
                );
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: "
                                + browserName
                                + ". Supported browsers: "
                                + "chrome, firefox, edge."
                );
        }

        // Maximise browser window
        getDriver()
                .manage()
                .window()
                .maximize();
    }


    /**
     * Returns the WebDriver instance
     * belonging to the current thread.
     *
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {

        WebDriver driver =
                driverThread.get();

        if (driver == null) {

            throw new IllegalStateException(
                    "WebDriver has not been initialised. "
                            + "Call initializeDriver() first."
            );
        }

        return driver;
    }


    /**
     * Checks whether a WebDriver instance
     * currently exists for this thread.
     *
     * This is useful for HybridStepLogger
     * before attempting to capture screenshots.
     *
     * @return true if WebDriver exists,
     *         otherwise false
     */
    public static boolean hasDriver() {

        return driverThread.get() != null;
    }


    /**
     * Quits the browser and removes
     * the WebDriver from ThreadLocal.
     */
    public static void quitDriver() {

        WebDriver driver =
                driverThread.get();

        if (driver != null) {

            try {

                driver.quit();

            } finally {

                /*
                 * Always remove the driver
                 * from ThreadLocal.
                 */
                driverThread.remove();
            }
        }
    }
}