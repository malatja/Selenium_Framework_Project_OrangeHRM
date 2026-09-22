package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    /**
     * Initialises the browser.
     */
    public static void initializeDriver(String browserName) {

        if (browserName == null || browserName.isBlank()) {
            browserName = "chrome";
        }

        switch (browserName.toLowerCase()) {

            case "chrome":
                driverThread.set(new ChromeDriver());
                break;

            case "firefox":
                driverThread.set(new FirefoxDriver());
                break;

            case "edge":
                driverThread.set(new EdgeDriver());
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browserName
                                + ". Supported browsers: chrome, firefox, edge."
                );
        }

        getDriver().manage().window().maximize();
    }

    /**
     * Returns the WebDriver for the current thread.
     */
    public static WebDriver getDriver() {

        if (driverThread.get() == null) {
            throw new IllegalStateException(
                    "WebDriver has not been initialised. "
                            + "Call initializeDriver() first."
            );
        }

        return driverThread.get();
    }

    /**
     * Quits the browser and removes the driver from ThreadLocal.
     */
    public static void quitDriver() {

        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove();
        }
    }
}