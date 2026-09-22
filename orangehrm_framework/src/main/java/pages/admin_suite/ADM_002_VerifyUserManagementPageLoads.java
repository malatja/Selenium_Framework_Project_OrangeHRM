package pages.admin_suite;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.LocatorReader;

public class ADM_002_VerifyUserManagementPageLoads {

    private final WebDriver driver;

    // Constructor
    public ADM_002_VerifyUserManagementPageLoads(WebDriver driver) {
        this.driver = driver;
    }
    
    // Element Locators
    private By usernameFieldLocator(){
        return By.xpath(LocatorReader.getLocator("loginPage.username_xpath"));
    }
    private By passwordFieldLocator(){
        return By.xpath(LocatorReader.getLocator("loginPage.password_xpath"));
    }
    private By loginButtonLocator(){
        return By.xpath(LocatorReader.getLocator("loginPage.loginButton_xpath"));
    }
    private By adminTabLocator = By.xpath(LocatorReader.getLocator("adminPage.adminTab_xpath"));
    private By userManagementDropdownLocator = By.xpath(LocatorReader.getLocator("adminPage.userManagement.dropdown_xpath"));
    private By usersLinkLocator = By.xpath(LocatorReader.getLocator("adminPage.users.link_xpath"));
    private By usersUsernameInputLocator = By.xpath(LocatorReader.getLocator("adminPage.users.usernameInput_xpath"));
    private By usersUserRoleDropdownLocator = By.xpath(LocatorReader.getLocator("adminPage.users.userRoleDropdown_xpath"));
    private By usersEmployeeNameInputLocator = By.xpath(LocatorReader.getLocator("adminPage.users.employeeNameInput_xpath"));
    private By usersStatusDropdownLocator = By.xpath(LocatorReader.getLocator("adminPage.users.statusDropdown_xpath"));
    private By usersResetButtonLocator = By.xpath(LocatorReader.getLocator("adminPage.users.resetButton_xpath"));
    private By usersSearchButtonLocator = By.xpath(LocatorReader.getLocator("adminPage.users.searchButton_xpath"));
    private By usersAddButtonLocator = By.xpath(LocatorReader.getLocator("adminPage.users.addButton_xpath"));
    private By usersTableLocator = By.xpath(LocatorReader.getLocator("adminPage.users.table_xpath"));


    
    // Action Methods
    public void enterCredentialsAndLogin(String username, String password){
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

    public void clickManagementDropdown() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(userManagementDropdownLocator));
        driver.findElement(userManagementDropdownLocator).click();
    }

    public void clickUsersLink() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(usersLinkLocator));
        driver.findElement(usersLinkLocator).click();
    }

    public boolean isUsernameFieldVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(usersUsernameInputLocator));
        return driver.findElement(usersUsernameInputLocator).isDisplayed();
    }

    public boolean isUserRoleFieldVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(usersUserRoleDropdownLocator));
        return driver.findElement(usersUserRoleDropdownLocator).isDisplayed();
    }

    public boolean isEmployeeNameFieldVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(usersEmployeeNameInputLocator));
        return driver.findElement(usersEmployeeNameInputLocator).isDisplayed();
    }

    public boolean isStatusFieldVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(usersStatusDropdownLocator));
        return driver.findElement(usersStatusDropdownLocator).isDisplayed();
    }

    public boolean isSearchButtonVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(usersSearchButtonLocator));
        return driver.findElement(usersSearchButtonLocator).isDisplayed();
    }

    public boolean isResetButtonVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(usersResetButtonLocator));
        return driver.findElement(usersResetButtonLocator).isDisplayed();
    }

    public boolean isAddButtonVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(usersAddButtonLocator));
        return driver.findElement(usersAddButtonLocator).isDisplayed();
    }
 
    public boolean isUsersTableVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(usersTableLocator));
        return driver.findElement(usersTableLocator).isDisplayed();
    }
    

}
