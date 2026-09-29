package pages.admin_suite;
 
import java.time.Duration;
import java.util.List;
 
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
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

        private By adminAndUserManagementHeaderLocator = By.xpath(LocatorReader.getLocator("adminPage.adminAndUserManagement.header_xpath"));


 
        // Action Methods
        @Step("Entering Credentials and Logging In")
        public void enterCredentialsAndLogin(String username, String password) {
                new WebDriverWait(driver, Duration.ofSeconds(10))
                                .until(ExpectedConditions.visibilityOfElementLocated(usernameFieldLocator()));
                driver.findElement(usernameFieldLocator()).sendKeys(username);
                driver.findElement(passwordFieldLocator()).sendKeys(password);
                driver.findElement(loginButtonLocator()).click();
        }
 
        @Step("Clicking Admin Tab")
        public void clickAdminTab() {
 
                new WebDriverWait(driver, Duration.ofSeconds(20))
                                .until(ExpectedConditions.visibilityOfElementLocated(adminTabLocator));
                driver.findElement(adminTabLocator).click();
        }
 
        @Step("Clicking User Management Dropdown")
        public void clickUserManagementDropdown() {
                new WebDriverWait(driver, Duration.ofSeconds(20))
                                .until(ExpectedConditions.visibilityOfElementLocated(userManagementDropdownLocator));
                driver.findElement(userManagementDropdownLocator).click();
        }
 
        @Step("Clicking Users Link")
        public void clickUsersLink() {
                new WebDriverWait(driver, Duration.ofSeconds(20))
                                .until(ExpectedConditions.visibilityOfElementLocated(usersLinkLocator));
                driver.findElement(usersLinkLocator).click();
        }
 
        @Step("Clicking Users Add Button")
        public void clickUsersAddButton() {
                new WebDriverWait(driver, Duration.ofSeconds(20))
                                .until(ExpectedConditions.visibilityOfElementLocated(usersAddButtonLocator));
                driver.findElement(usersAddButtonLocator).click();
        }
 
        @Step("Selecting User Role: {userRole}")
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
 
        // *************************************************************************************************
        @Step("Typing Employee Name Hint and Selecting from Suggestions")       
        public void typeEmployeeNameHintAndSelectFromSuggestions(String employeeNameHint) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
 
                // 1. Clear field and type the hint
                WebElement employeeNameField = wait.until(
                                ExpectedConditions.elementToBeClickable(employeeNameInputLocator));
                employeeNameField.clear();
                employeeNameField.sendKeys(employeeNameHint);
 
                // 2. Wait for at least one suggestion to be visible and stable
                wait.until(ExpectedConditions.visibilityOfElementLocated(employeeSuggestionsLocator));
 
                // 3. Print suggestions safely using fresh element relocation
                List<WebElement> suggestions = driver.findElements(employeeSuggestionsLocator);
                System.out.println("Employee suggestions found: " + suggestions.size());
                for (WebElement suggestion : suggestions) {
                        try {
                                System.out.println("Suggestion: " + suggestion.getText());
                        } catch (StaleElementReferenceException e) {
                                // Context changed while printing; skip or log
                                System.out.println("Suggestion became stale while reading text.");
                        }
                }
 
                // 4. FIX: Re-locate the first element right before clicking to avoid stale
                // reference
                WebElement firstSuggestion = wait
                                .until(ExpectedConditions.elementToBeClickable(employeeSuggestionsLocator));
 
                // Capture text before clicking (clicking might dismiss the dropdown, making it
                // stale instantly)
                String selectedText = firstSuggestion.getText();
                firstSuggestion.click();
 
                System.out.println("Selected employee: " + selectedText);
        }
 
        // *************************************************************************************************
 
        @Step("Selecting Status: {status}")
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
 
        @Step("Entering Username")
        public void enterUsername(String username) {
 
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
 
                wait.until(ExpectedConditions.visibilityOfElementLocated(usersUsernameInputLocator));
                driver.findElement(usersUsernameInputLocator).clear();
                driver.findElement(usersUsernameInputLocator).sendKeys(username);
        }
 
        @Step("Typing Password")
        public void typePassword(String password) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
 
                wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
                driver.findElement(passwordInputLocator).clear();
                driver.findElement(passwordInputLocator).sendKeys(password);
        }
 
        @Step("Typing Confirm Password")
        public void typeConfirmPassword(String confirmPassword) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
 
                wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordInputLocator));
                driver.findElement(confirmPasswordInputLocator).clear();
                driver.findElement(confirmPasswordInputLocator).sendKeys(confirmPassword);
        }
 
        @Step("Clicking Save Button")
        public void clickSaveButton() {
 
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
 
                WebElement saveButton = wait.until(
                                ExpectedConditions.elementToBeClickable(
                                                saveButtonLocator));
 
                saveButton.click();
        }

        @Step("Checking Admin and User Management Header is Displayed")
        public boolean isAdminAndUserManagementHeaderDisplayed() {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                try {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(adminAndUserManagementHeaderLocator));
                        return true;
                } catch (Exception e) {
                        return false;
                }
        }
 
}
 