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
        private By usernameFieldLocator() {
                return By.xpath(LocatorReader.getLocator("loginPage.username_xpath"));
        }

        private By passwordFieldLocator() {
                return By.xpath(LocatorReader.getLocator("loginPage.password_xpath"));
        }

        private By loginButtonLocator() {
                return By.xpath(LocatorReader.getLocator("loginPage.loginButton_xpath"));
        }

        private By adminTabLocator = By.xpath(LocatorReader.getLocator("adminPage.adminTab_xpath"));
        private By userManagementDropdownLocator = By
                        .xpath(LocatorReader.getLocator("adminPage.userManagement.dropdown_xpath"));
        private By usersLinkLocator = By.xpath(LocatorReader.getLocator("adminPage.users.link_xpath"));
        private By usersAddButtonLocator = By.xpath(LocatorReader.getLocator("adminPage.users.addButton_xpath"));
        private By userRoleDropdownLocator = By
                        .xpath(LocatorReader.getLocator("adminPage.users.userRoleDropdown_xpath"));
        private By optionsLocator = By.xpath(LocatorReader.getLocator("adminPage.users.options_xpath"));
        private By employeeNameInputLocator = By
                        .xpath(LocatorReader.getLocator("adminPage.users.employeeNameInput_xpath"));
        private By employeeSuggestionsLocator = By
                        .xpath(LocatorReader.getLocator("adminPage.users.employeeSuggestions_xpath"));
        private By statusDropdownLocator = By.xpath(LocatorReader.getLocator("adminPage.users.statusDropdown_xpath"));
        private By usersUsernameInputLocator = By
                        .xpath(LocatorReader.getLocator("adminPage.users.usernameInput_xpath"));
        private By passwordInputLocator = By.xpath(LocatorReader.getLocator("adminPage.users.passwordInput_xpath"));
        private By confirmPasswordInputLocator = By
                        .xpath(LocatorReader.getLocator("adminPage.users.confirmPasswordInput_xpath"));
        private By saveButtonLocator = By.xpath(LocatorReader.getLocator("adminPage.users.saveButton_xpath"));

        // Action Methods
        public void enterCredentialsAndLogin(String username, String password) {
                new WebDriverWait(driver, Duration.ofSeconds(10))
                                .until(ExpectedConditions.visibilityOfElementLocated(usernameFieldLocator()));
                driver.findElement(usernameFieldLocator()).sendKeys(username);
                driver.findElement(passwordFieldLocator()).sendKeys(password);
                driver.findElement(loginButtonLocator()).click();
        }

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

        public void selectUserRole(String userRole) {

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

                // Click the dropdown
                wait.until(ExpectedConditions.elementToBeClickable(
                                userRoleDropdownLocator)).click();

                // Wait for options to appear
                List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                optionsLocator));

                System.out.println("Options available: " + options);

                // Select the option based on the provided userRole
                for (WebElement option : options) {
                        if (option.getText().equals(userRole)) {
                                option.click();
                                break;
                        }
                }

        }

        public void typeEmployeeNameHintAndSelectFromSuggestions(
                        String employeeNameHint) {

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

                // Employee Name input
                By employeeNameInput = By.xpath("//input[@placeholder='Type for hints...']");

                // All autocomplete options
                By employeeOptions = By.xpath("//div[@role='listbox']//div[@role='option']");

                // 1. Wait for Employee Name field
                WebElement employeeName = wait.until(
                                ExpectedConditions.elementToBeClickable(
                                                employeeNameInput));

                // 2. Clear existing value
                employeeName.clear();

                // 3. Type Excel hint, for example "Pe"
                employeeName.sendKeys(employeeNameHint);

                // 4. Wait until autocomplete options are loaded
                wait.until(
                                ExpectedConditions.numberOfElementsToBeMoreThan(
                                                employeeOptions,
                                                0));

                // 5. Get the available suggestions
                List<WebElement> suggestions = driver.findElements(employeeOptions);

                System.out.println(
                                "Employee suggestions found: "
                                                + suggestions.size());

                // 6. Print suggestions for debugging
                for (WebElement suggestion : suggestions) {

                        System.out.println(
                                        "Employee suggestion: "
                                                        + suggestion.getText());
                }

                // 7. Click the first real employee suggestion
                for (WebElement suggestion : suggestions) {

                        String suggestionText = suggestion.getText().trim();

                        if (!suggestionText.isEmpty()
                                        && !suggestionText.equalsIgnoreCase(
                                                        "Searching...")
                                        && !suggestionText.equalsIgnoreCase(
                                                        "No Records Found")) {

                                wait.until(
                                                ExpectedConditions.elementToBeClickable(
                                                                suggestion))
                                                .click();

                                System.out.println(
                                                "Selected employee: "
                                                                + suggestionText);

                                return;
                        }
                }

                throw new RuntimeException(
                                "No valid employee suggestion found for hint: "
                                                + employeeNameHint);
        }

        public void selectStatus(String status) {

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

                // Click the status dropdown
                wait.until(ExpectedConditions.elementToBeClickable(
                                statusDropdownLocator)).click();

                // Wait for options to appear
                List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                optionsLocator));

                System.out.println("Options available: " + options);

                // Select the option based on the provided status
                for (WebElement option : options) {
                        if (option.getText().equals(status)) {
                                option.click();
                                break;
                        }
                }
        }

        public void enterUsername(String username) {

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

                wait.until(ExpectedConditions.visibilityOfElementLocated(usersUsernameInputLocator));
                driver.findElement(usersUsernameInputLocator).clear();
                driver.findElement(usersUsernameInputLocator).sendKeys(username);
        }

        public void typePassword(String password) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

                wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
                driver.findElement(passwordInputLocator).clear();
                driver.findElement(passwordInputLocator).sendKeys(password);
        }

        public void typeConfirmPassword(String confirmPassword) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

                wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordInputLocator));
                driver.findElement(confirmPasswordInputLocator).clear();
                driver.findElement(confirmPasswordInputLocator).sendKeys(confirmPassword);
        }

        public void clickSaveButton() {

                WebDriverWait wait =
                        new WebDriverWait(driver, Duration.ofSeconds(20));

                WebElement saveButton = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                saveButtonLocator
                        )
                );

                saveButton.click();
        }

}
