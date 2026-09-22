package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.login_suite.LoginPage;
import utils.ConfigReader;
import utils.ExcelReader;



public class Login_Suite_Tests extends BaseTest  {

    // ================= DATA PROVIDERS =================

    // Data Provider for test 1
    @DataProvider(name = "loginData")
    public Object [][] loginData() throws Exception {
        return ExcelReader.getExcelData(
           ConfigReader.getProperty("authentication.workbook"),
           ConfigReader.getProperty("authentication.validCredentials.sheet")
        );
    }

    // Data Provider for test 2
    @DataProvider(name = "invalidLoginData")
    public Object [][] invalidLoginData() throws Exception {
        return ExcelReader.getExcelData(
           ConfigReader.getProperty("authentication.workbook"),
           ConfigReader.getProperty("authentication.invalidCredentials.sheet")
        );
    }


    //=============================== TEST CASES ===============================

    // ======= Test Case 1 ================
    @Test(priority = 1, dataProvider = "loginData",  description = "Verify that the user can log in successfully with valid credentials")
    public void testLogin(String username, String password) throws Exception {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterCredentialsAndLogin(username, password);
    }

    // ======= Test Case 2 ================
    @Test(priority = 2, dataProvider = "invalidLoginData",  description = "Verify that the user cannot log in with invalid credentials")
    public void testInvalidLogin(String username, String password) throws Exception {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterCredentialsAndLogin(username, password);
    }


}
