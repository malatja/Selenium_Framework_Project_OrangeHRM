package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BrowserLaunchTest extends BaseTest {

    @Test(description = "Verify that the browser launches successfully")
    public void verifyBrowserLaunchesSuccessfully() {

        Assert.assertNotNull(
                driver,
                "Browser did not launch successfully."
        );

        System.out.println("Browser launched successfully.");
        System.out.println("Browser title: " + driver.getTitle());
    }
}