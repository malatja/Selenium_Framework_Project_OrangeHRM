package pages.login_suite;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.LocatorReader;

public class LoginPage {

    private final WebDriver driver;

    // ==== Constructor ====
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }


    // Elment Locators Methods 
    private By usernameFieldLocator(){
        return By.xpath(LocatorReader.getLocator("loginPage.username_xpath"));
    }
    private By passwordFieldLocator(){
        return By.xpath(LocatorReader.getLocator("loginPage.password_xpath"));
    }
    private By loginButtonLocator(){
        return By.xpath(LocatorReader.getLocator("loginPage.loginButton_xpath"));
    }



    // Action Methods
    public void enterCredentialsAndLogin(String username, String password){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(usernameFieldLocator()));            
        driver.findElement(usernameFieldLocator()).sendKeys(username);
        driver.findElement(passwordFieldLocator()).sendKeys(password);
        driver.findElement(loginButtonLocator()).click();
    }

}
