package pages.admin_suite;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.LocatorReader;

public class ADM_003_AddUserWithValidInformation {

    private WebDriver driver;

    // Constructor
    public ADM_003_AddUserWithValidInformation(WebDriver driver) {
        this.driver = driver;
    }

     // Element Locators
    private By adminTabLocator = By.xpath(LocatorReader.getLocator("adminPage.adminTab_xpath"));
    private By userManagementDropdownLocator = By.xpath(LocatorReader.getLocator("adminPage.userManagement.dropdown_xpath"));
    private By usersLinkLocator = By.xpath(LocatorReader.getLocator("adminPage.users.link_xpath"));
    private By usersAddButtonLocator = By.xpath(LocatorReader.getLocator("adminPage.users.addButton_xpath"));
    private By userRoleDropdownLocator = By.xpath(LocatorReader.getLocator("adminPage.users.userRoleDropdown_xpath"));
    private By optionsLocator = By.xpath(LocatorReader.getLocator("adminPage.users.options_xpath"));
    private By employeeNameInputLocator = By.xpath(LocatorReader.getLocator("adminPage.users.employeeNameInput_xpath"));
    private By employeeSuggestionsLocator = By.xpath(LocatorReader.getLocator("adminPage.users.employeeSuggestions_xpath"));
    private By statusDropdownLocator = By.xpath(LocatorReader.getLocator("adminPage.users.statusDropdown_xpath"));
    private By usersUsernameInputLocator = By.xpath(LocatorReader.getLocator("adminPage.users.usernameInput_xpath"));
    private By passwordInputLocator = By.xpath(LocatorReader.getLocator("adminPage.users.passwordInput_xpath"));
    private By confirmPasswordInputLocator = By.xpath(LocatorReader.getLocator("adminPage.users.confirmPasswordInput_xpath"));
    private By saveButtonLocator = By.xpath(LocatorReader.getLocator("adminPage.users.saveButton_xpath"));



     // Action Methods
    public void clickAdminTab() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(adminTabLocator));
        driver.findElement(adminTabLocator).click();
    }

    public void clickUserManagementDropdown() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(userManagementDropdownLocator));
        driver.findElement(userManagementDropdownLocator).click();
    }

    public void clickUsersLink() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(usersLinkLocator));
        driver.findElement(usersLinkLocator).click();
    }

    public void clickUsersAddButton() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(usersAddButtonLocator));
        driver.findElement(usersAddButtonLocator).click();
    }

    public void selectUserRole() {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(20));

        // Click the dropdown
        wait.until(ExpectedConditions.elementToBeClickable(
                userRoleDropdownLocator)).click();

        // Wait for options to appear
        List<WebElement> options =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        optionsLocator));

        System.out.println("Options available: " + options);     
                
        // Select first actual option (Admin)
        options.get(1).click();
        

        }



    public void typeEmployeeNameHintAndSelectFromSuggestions() {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    // Employee Name input field
    By employeeNameInput =
            By.xpath("//input[@placeholder='Type for hints...']");

    // Autocomplete options
    By employeeOptions =
            By.xpath("(//div[@role='listbox']//div[@role='option'])[1]");

    // 1. Wait for Employee Name input
    WebElement employeeName =
            wait.until(ExpectedConditions.elementToBeClickable(
                    employeeNameInput));

    // 2. Type partial employee name
    employeeName.clear();
    employeeName.sendKeys("p");

    // 3. Wait until suggestions appear
    List<WebElement> options =
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    employeeOptions));

    // 4. Click the first suggestion
    options.get(0).click();
}

        

//     public void typePassword() {
//         new WebDriverWait(driver, Duration.ofSeconds(20))
//                 .until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
//         driver.findElement(passwordInputLocator).clear();
//         driver.findElement(passwordInputLocator).sendKeys("Password123!");
//     }

//     public void typeConfirmPassword() {
//         new WebDriverWait(driver, Duration.ofSeconds(20))
//                 .until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordInputLocator));
//         driver.findElement(confirmPasswordInputLocator).clear();
//         driver.findElement(confirmPasswordInputLocator).sendKeys("Password123!");
//     }

//     public void clickSaveButton() {
//         new WebDriverWait(driver, Duration.ofSeconds(20))
//                 .until(ExpectedConditions.visibilityOfElementLocated(saveButtonLocator));
//         driver.findElement(saveButtonLocator).click();
//     }


}
