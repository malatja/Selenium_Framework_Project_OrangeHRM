package pages.admin_suite;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.LocatorReader;

public class ADM_001_TestAdminTabAndUserManagementHeaderVisibility {

    private final WebDriver driver;

    // Constructor
    public ADM_001_TestAdminTabAndUserManagementHeaderVisibility(WebDriver driver) {
        this.driver = driver;
    }

    // Element Locators
    private By adminTabLocator = By.xpath(LocatorReader.getLocator("adminPage.adminTab_xpath"));
    private By adminAndUserManagementHeaderLocator = By.xpath(LocatorReader.getLocator("adminPage.adminAndUserManagement.header_xpath"));

    // Action Methods
    public void clickAdminTab() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(adminTabLocator));
        driver.findElement(adminTabLocator).click();
    }

    public boolean isAdminAndUserManagementHeaderVisible() {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(adminAndUserManagementHeaderLocator)); 
        return driver.findElement(adminAndUserManagementHeaderLocator).isDisplayed();
    }

}
