package tests;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import base.BaseTest;
import pages.admin_suite.ADM_001_TestAdminTabAndUserManagementHeaderVisibility;
import pages.admin_suite.ADM_002_VerifyUserManagementPageLoads;

public class Admin_Suite_Tests extends BaseTest {

    // ========== DATA PROVIDERS ==========
    
    // Data provider for test case 1
    @DataProvider(name = "ADM_001_TestAdminTabAndUserManagementHeaderVisibility")
    public Object [][] ADM_001_TestAdminTabAndUserManagementHeaderVisibility() throws Exception {
        return utils.ExcelReader.getExcelData(
           utils.ConfigReader.getProperty("authentication.workbook"),
           utils.ConfigReader.getProperty("authentication.validCredentials.sheet")
        );
    }

    // Data provider for test case 2
    @DataProvider(name = "ADM_002_VerifyUserManagementPageLoads")
    public Object [][] ADM_002_VerifyUserManagementPageLoads() throws Exception {
        return utils.ExcelReader.getExcelData(
           utils.ConfigReader.getProperty("authentication.workbook"),
           utils.ConfigReader.getProperty("authentication.validCredentials.sheet")
        );
    }

    // Data provider for test case 3
    @DataProvider(name = "CombinedValidCredsAndValidUserData")
    public Object[][] CombinedValidCredsAndValidUserData() throws Exception {

        // Read login credentials from Login-Suite.xlsx
        String[][] validLoginData = utils.ExcelReader.getExcelData(
                utils.ConfigReader.getProperty(
                        "authentication.workbook"
                ),
                utils.ConfigReader.getProperty(
                        "authentication.validCredentials.sheet"
                )
        );

        // Read user data from Admin-Suite.xlsx
        String[][] validUserData = utils.ExcelReader.getExcelData(
                utils.ConfigReader.getProperty(
                        "admin.workbook"
                ),
                utils.ConfigReader.getProperty(
                        "admin.userData.sheet"
                )
        );

        // Make sure login data exists
        if (validLoginData.length == 0) {
            throw new RuntimeException(
                    "No valid login data found in Login-Suite.xlsx"
            );
        }

        // Make sure admin user data exists
        if (validUserData.length == 0) {
            throw new RuntimeException(
                    "No user data found in Admin-Suite.xlsx"
            );
        }

        /*
        * Login-Suite.xlsx:
        * username | password
        *
        * Admin-Suite.xlsx:
        * userRole | employeeNameHint | status |
        * passwordValue | confirmPassword
        *
        * Combined:
        * username | password | userRole | employeeNameHint |
        * status | passwordValue | confirmPassword
        */

        Object[][] combinedData =
                new Object[validUserData.length][8];

        for (int i = 0; i < validUserData.length; i++) {

            // Login-Suite.xlsx
            combinedData[i][0] = validLoginData[0][0];
            combinedData[i][1] = validLoginData[0][1];

            // Admin-Suite.xlsx
            combinedData[i][2] = validUserData[i][0];
            combinedData[i][3] = validUserData[i][1];
            combinedData[i][4] = validUserData[i][2];
            combinedData[i][5] = validUserData[i][3];
            combinedData[i][6] = validUserData[i][4];
            combinedData[i][7] = validUserData[i][5];
        }

        return combinedData;
    }


    // ============== TEST CASES ==============


    // ======= Test Case 1 ==============
    @Test( priority = 1, dataProvider = "ADM_001_TestAdminTabAndUserManagementHeaderVisibility", description = "ADM-001 — Verify Admin page is accessible")
    public void ADM_001_TestAdminTabAndUserManagementHeaderVisibility(String username, String password) throws Exception {
        

        ADM_001_TestAdminTabAndUserManagementHeaderVisibility adm_001_Page = 
                new ADM_001_TestAdminTabAndUserManagementHeaderVisibility(driver);
                
        adm_001_Page.enterCredentialsAndLogin(username, password);
        adm_001_Page.clickAdminTab();
        adm_001_Page.isAdminAndUserManagementHeaderVisible();

    }


    // ======= Test Case 2 ==============
    @Test( priority = 2, dataProvider = "ADM_002_VerifyUserManagementPageLoads",  description = "ADM-002 — Verify User Management page loads")
    public void ADM_002_VerifyUserManagementPageLoads(String username, String password) throws Exception {
       


        ADM_002_VerifyUserManagementPageLoads adm_002_Page = 
                new ADM_002_VerifyUserManagementPageLoads(driver);

        adm_002_Page.enterCredentialsAndLogin(username, password);
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

    // ======= Test Case 3 ==============
    @Test( priority = 3, dataProvider = "CombinedValidCredsAndValidUserData", description = "ADM-003 — Add user with valid information")
    public void ADM_003_AddUserWithValidInformation(String username, String password, String userRole, String employeeNameHint, String status, String usersUsername, String passwordValue, String confirmPassword) throws Exception {
        

        pages.admin_suite.ADM_003_AddUserWithValidInformation adm_003_Page = 
                new pages.admin_suite.ADM_003_AddUserWithValidInformation(driver);

        adm_003_Page.enterCredentialsAndLogin(username, password);
        adm_003_Page.clickAdminTab();
        adm_003_Page.clickUserManagementDropdown();
        adm_003_Page.clickUsersLink();
        adm_003_Page.clickUsersAddButton();
        adm_003_Page.selectUserRole(userRole);
        adm_003_Page.typeEmployeeNameHintAndSelectFromSuggestions(employeeNameHint);
        adm_003_Page.selectStatus(status);
        adm_003_Page.enterUsername(usersUsername);
        adm_003_Page.typePassword(passwordValue);
        adm_003_Page.typeConfirmPassword(confirmPassword);
        adm_003_Page.clickSaveButton();
    }

}
