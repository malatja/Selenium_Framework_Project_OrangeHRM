package tests;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import base.BaseTest;
import pages.admin_suite.ADM_001_TestAdminTabAndUserManagementHeaderVisibility;
import pages.admin_suite.ADM_002_VerifyUserManagementPageLoads;
import pages.login_suite.LoginPage;

public class Admin_Suite_Tests extends BaseTest {

    @DataProvider(name = "loginData")
    public Object [][] loginData() throws Exception {
        return utils.ExcelReader.getExcelData(
           utils.ConfigReader.getProperty("authentication.workbook"),
           utils.ConfigReader.getProperty("authentication.validCredentials.sheet")
        );
    }

    @Test( priority = 1, dataProvider = "loginData", description = "ADM-001 — Verify Admin page is accessible")
    public void ADM_001_TestAdminTabAndUserManagementHeaderVisibility(String username, String password) throws Exception {
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterCredentialsAndLogin(username, password);

        ADM_001_TestAdminTabAndUserManagementHeaderVisibility adm_001_Page = 
                new ADM_001_TestAdminTabAndUserManagementHeaderVisibility(driver);

        adm_001_Page.clickAdminTab();
        adm_001_Page.isAdminAndUserManagementHeaderVisible();

    }

    @Test( priority = 2, dataProvider = "loginData",  description = "ADM-002 — Verify User Management page loads")
    public void ADM_002_VerifyUserManagementPageLoads(String username, String password) throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterCredentialsAndLogin(username, password);


        ADM_002_VerifyUserManagementPageLoads adm_002_Page = 
                new ADM_002_VerifyUserManagementPageLoads(driver);

        adm_002_Page.clickAdminTab();
        adm_002_Page.clickManagementDropdown();
        adm_002_Page.clickUsersLink();
        adm_002_Page.isUsernameFieldVisible();
        adm_002_Page.isUserRoleFieldVisible();
        adm_002_Page.isEmployeeNameFieldVisible();
        adm_002_Page.isStatusFieldVisible();
        adm_002_Page.isSearchButtonVisible();
        adm_002_Page.isResetButtonVisible();
        adm_002_Page.isAddButtonVisible();
        adm_002_Page.isUsersTableVisible();
    }

    
    @Test( priority = 3, dataProvider = "loginData", description = "ADM-003 — Add user with valid information")
    public void ADM_003_AddUserWithValidInformation(String username, String password) throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterCredentialsAndLogin(username, password);

        pages.admin_suite.ADM_003_AddUserWithValidInformation adm_003_Page = 
                new pages.admin_suite.ADM_003_AddUserWithValidInformation(driver);

        adm_003_Page.clickAdminTab();
        adm_003_Page.clickUserManagementDropdown();
        adm_003_Page.clickUsersLink();
        adm_003_Page.clickUsersAddButton();
        adm_003_Page.selectUserRole();
        adm_003_Page.typeEmployeeNameHintAndSelectFromSuggestions();
        // adm_003_Page.typeEmployeeNameHintAndSelectFromSuggestions();
        // adm_003_Page.typePassword();
        // adm_003_Page.typeConfirmPassword();
        // adm_003_Page.clickSaveButton();
    }
}
