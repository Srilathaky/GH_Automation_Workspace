package com.milvik.mip.rolemanagement.testrunner;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.milvik.mip.constants.MIP_Menu_Constants;
import com.milvik.mip.dataprovider.MIP_RoleManagement_TestData;
import com.milvik.mip.dbqueries.MIP_RoleManagement_Queries;
import com.milvik.mip.pageobjects.MIP_HomePage;
import com.milvik.mip.pageobjects.MIP_LoginPage;
import com.milvik.mip.pageobjects.MIP_ViewRolePage;
import com.milvik.mip.testconfig.MIP_Test_Configuration;
import com.milvik.mip.utility.MIP_BrowserFactory;
import com.milvik.mip.utility.MIP_DataBaseConnection;
import com.milvik.mip.utility.MIP_LaunchApplication;
import com.milvik.mip.utility.MIP_Logging;
import com.milvik.mip.utility.MIP_ReadPropertyFile;
import com.milvik.mip.utility.MIP_ScreenShots;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class MIP_ViewRole_Test {
	ExtentReports report;
	ExtentTest logger;
	static Logger log;
	MIP_LoginPage loginpage = null;
	MIP_HomePage homepage = null;
	MIP_ViewRolePage viewrole = null;

	@BeforeTest
	@Parameters({ "flag", "browser" })
	public void test_setup(@Optional("firefox") String browser,
			@Optional("0") String flag) {
		log = MIP_Logging.logDetails("MIP_ViewRole_Test");
		report = new ExtentReports(".\\Test_Reports\\MIP_ViewRole_Test.html");
		if (flag.equals("0")) {
			try {
				Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
			} catch (Exception e) {
				log.info("No exe found");
			}
			MIP_Test_Configuration.driver = MIP_BrowserFactory.openBrowser(
					MIP_Test_Configuration.driver, browser);

			MIP_ReadPropertyFile.loadProperty("config");
			MIP_DataBaseConnection.connectToDatabase();
			MIP_LaunchApplication
					.openApplication(MIP_Test_Configuration.driver);
			loginpage = PageFactory.initElements(MIP_Test_Configuration.driver,
					MIP_LoginPage.class);
			homepage = loginpage.login(
					MIP_ReadPropertyFile.getPropertyValue("username"),
					MIP_ReadPropertyFile.getPropertyValue("password"));
			homepage.clickOnMenu(MIP_Menu_Constants.ROLE);
			homepage.clickOnMenu(MIP_Menu_Constants.VIEW_ROLE);

		} else {
			homepage = new MIP_HomePage(MIP_Test_Configuration.driver);
			homepage.clickOnMenu(MIP_Menu_Constants.ROLE);
			homepage.clickOnMenu(MIP_Menu_Constants.VIEW_ROLE);
		}
	}


	@Test(testName = "TC198")
	public void viewRoleOne() throws Throwable {
		try {
			logger = report.startTest("View Role -TC198");
			log.info("Running test case - TC198");
			homepage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'View Roles')]"));
			viewrole = PageFactory.initElements(MIP_Test_Configuration.driver,
					MIP_ViewRolePage.class);
			Assert.assertTrue(viewrole.role.isDisplayed());
			Assert.assertEquals(viewrole.homeMenu.getText().trim(),
					MIP_Menu_Constants.HOME.trim());
			Assert.assertTrue(viewrole.homeMenu_checkbox.getAttribute(
					"disabled").equalsIgnoreCase("true"));

			Assert.assertEquals(viewrole.branchmanageMenu.getText().trim(),
					MIP_Menu_Constants.BRANCH.trim());
			Assert.assertTrue(viewrole.branchmanageMenu_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.addBranchMenu.getText().trim(),
					MIP_Menu_Constants.ADD_BRANCH.trim());
			Assert.assertTrue(viewrole.addBranch_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.listBranchMenu.getText().trim(),
					MIP_Menu_Constants.LIST_BRANCH.trim());
			Assert.assertTrue(viewrole.listBranch_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.usermanageMenu.getText().trim(),
					MIP_Menu_Constants.USER.trim());
			Assert.assertTrue(viewrole.usermanageMenu_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.adduserMenu.getText().trim(),
					MIP_Menu_Constants.ADD_USER.trim());
			Assert.assertTrue(viewrole.addUser_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.listuserMenu.getText().trim(),
					MIP_Menu_Constants.LIST_USER.trim());
			Assert.assertTrue(viewrole.listUser_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.searchuserMenu.getText().trim(),
					MIP_Menu_Constants.SEARCH_USER.trim());
			Assert.assertTrue(viewrole.searchUser_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.resetPasswordMenu.getText().trim(),
					MIP_Menu_Constants.RESET_PASSWORD.trim());
			Assert.assertTrue(viewrole.resetPassword_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.leavemanageMenu.getText().trim(),
					MIP_Menu_Constants.LEAVE.trim());
			Assert.assertTrue(viewrole.leavemanageMenu_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.applyLeaveMenu.getText().trim(),
					MIP_Menu_Constants.APPLY_LEAVE.trim());
			Assert.assertTrue(viewrole.applyLeave_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.viewLeaveMenu.getText().trim(),
					MIP_Menu_Constants.VIEW_LEAVE.trim());
			Assert.assertTrue(viewrole.viewLeave_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.custmanageMenu.getText().trim(),
					MIP_Menu_Constants.CUSTOMER.trim());
			Assert.assertTrue(viewrole.custmanageMenu_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.regCustMenu.getText().trim(),
					MIP_Menu_Constants.REGISTER_CUSTOMER.trim());
			Assert.assertTrue(viewrole.regCust_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.searchCustMenu.getText().trim(),
					MIP_Menu_Constants.SEARCH_CUSTOMER.trim());
			Assert.assertTrue(viewrole.searchCust_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.deRegCustMenu.getText().trim(),
					MIP_Menu_Constants.DE_REGISTER_CUSTOMER.trim());
			Assert.assertTrue(viewrole.deRegCust_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.downloadESignature.getText().trim(),
					MIP_Menu_Constants.E_SIGNATURE.trim());
			Assert.assertTrue(viewrole.downloadESignature_checkbox
					.isDisplayed());

			Assert.assertEquals(viewrole.changeDeductionMode.getText().trim(),
					MIP_Menu_Constants.CHANGE_DEDUCTION_MODE.trim());
			Assert.assertTrue(viewrole.changeDeductionMode_checkbox
					.isDisplayed());

			Assert.assertEquals(viewrole.loyalty.getText().trim(),
					MIP_Menu_Constants.LOYALTY.trim());
			Assert.assertTrue(viewrole.loyalty_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.coverHistMenu.getText().trim(),
					MIP_Menu_Constants.COVER_HISTORY.trim());
			Assert.assertTrue(viewrole.coverHist_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.smsmanageMenu.getText().trim(),
					MIP_Menu_Constants.SMS.trim());
			Assert.assertTrue(viewrole.smsmanageMenu_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.editSmsMenu.getText().trim(),
					MIP_Menu_Constants.EDIT_SMS_TEMPLATE.trim());
			Assert.assertTrue(viewrole.editSmsMenu.isDisplayed());

			Assert.assertEquals(viewrole.claimSearch.getText().trim(),
					MIP_Menu_Constants.SEARCH_CLAIMS.trim());
			Assert.assertTrue(viewrole.claimSearch_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.claimsmanageMenu.getText().trim(),
					MIP_Menu_Constants.CLAIMS.trim());
			Assert.assertTrue(viewrole.claimsmanageMenu_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.deductionReportMenu.getText().trim(),
					MIP_Menu_Constants.DEDUCTION_REPORT.trim());
			Assert.assertTrue(viewrole.deductionReport_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.rolemanageMenu.getText().trim(),
					MIP_Menu_Constants.ROLE.trim());
			Assert.assertTrue(viewrole.rolemanageMenu_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.addRoleMenu.getText().trim(),
					MIP_Menu_Constants.ADD_ROLE.trim());
			Assert.assertTrue(viewrole.addRole_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.viewRoleMenu.getText().trim(),
					MIP_Menu_Constants.VIEW_ROLE.trim());
			Assert.assertTrue(viewrole.viewRole_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.reportmanageMenu.getText().trim(),
					MIP_Menu_Constants.REPORT.trim());
			Assert.assertTrue(viewrole.reportmanageMenu_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.custReportMenu.getText().trim(),
					MIP_Menu_Constants.CUST_REPORT.trim());
			Assert.assertTrue(viewrole.custReport_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.agentReportMenu.getText().trim(),
					MIP_Menu_Constants.AGENT_REPORT.trim());
			Assert.assertTrue(viewrole.agentReport_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.adminConfigMenu.getText().trim(),
					MIP_Menu_Constants.ADMIN_CONFIG.trim());
			Assert.assertTrue(viewrole.adminConfig_checkbox.isDisplayed());

			Assert.assertEquals(viewrole.changePassMenu.getText().trim(),
					MIP_Menu_Constants.CHANGE_PASSWORD.trim());
			Assert.assertTrue(viewrole.changePass_checkbox.getAttribute(
					"disabled").equalsIgnoreCase("true"));

			Assert.assertEquals(viewrole.logoutMenu.getText().trim(),
					MIP_Menu_Constants.LOGOUT.trim());
			Assert.assertTrue(viewrole.logout_checkbox.getAttribute("disabled")
					.equalsIgnoreCase("true"));
			Assert.assertTrue(viewrole.save.isDisplayed());
			Assert.assertTrue(viewrole.clear.isDisplayed());
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.VIEW_ROLE);
			log.info("Testcase TC198  Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@Test(testName = "TC199", dataProvider = "viewRoleTestData", dataProviderClass = MIP_RoleManagement_TestData.class)
	public void viewRoleTwo(String role) throws Throwable {
		try {
			logger = report.startTest("View Role -TC199");
			log.info("Running test case - TC199");
			homepage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'View Roles')]"));
			viewrole = PageFactory.initElements(MIP_Test_Configuration.driver,
					MIP_ViewRolePage.class);
			viewrole.selectRole(role);
			List<String> roleaccess = MIP_RoleManagement_Queries
					.getRoleAcess(role);

			Assert.assertTrue(viewrole.homeMenu_checkbox.isSelected());
			Assert.assertTrue(viewrole.changePass_checkbox.isSelected());
			Assert.assertTrue(viewrole.logout_checkbox.isSelected());

			roleaccess.remove("Home");
			roleaccess.remove("Logout");
			roleaccess.remove("Change Password");

			for (int i = 0; i < roleaccess.size(); i++) {
				switch (roleaccess.get(i)) {
				case MIP_Menu_Constants.BRANCH:
					Assert.assertTrue(viewrole.branchmanageMenu_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.ADD_BRANCH:
					Assert.assertTrue(viewrole.addBranch_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.LIST_BRANCH:
					Assert.assertTrue(viewrole.listBranch_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.USER:
					Assert.assertTrue(viewrole.usermanageMenu_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.ADD_USER:
					Assert.assertTrue(viewrole.addUser_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.SEARCH_USER:
					Assert.assertTrue(viewrole.searchUser_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.LIST_USER:
					Assert.assertTrue(viewrole.listUser_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.RESET_PASSWORD:
					Assert.assertTrue(viewrole.resetPassword_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.LEAVE:
					Assert.assertTrue(viewrole.leavemanageMenu_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.APPLY_LEAVE:
					Assert.assertTrue(viewrole.applyLeave_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.VIEW_LEAVE:
					Assert.assertTrue(viewrole.viewLeave_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.CUSTOMER:
					Assert.assertTrue(viewrole.custmanageMenu_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.REGISTER_CUSTOMER:
					Assert.assertTrue(viewrole.regCust_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.SEARCH_CUSTOMER:
					Assert.assertTrue(viewrole.searchCust_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.DE_REGISTER_CUSTOMER:
					Assert.assertTrue(viewrole.deRegCust_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.COVER_HISTORY:
					Assert.assertTrue(viewrole.coverHist_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.E_SIGNATURE:
					Assert.assertTrue(viewrole.downloadESignature_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.SMS:
					Assert.assertTrue(viewrole.smsmanageMenu_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.EDIT_SMS_TEMPLATE:
					Assert.assertTrue(viewrole.editSms_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.CHANGE_DEDUCTION_MODE:
					Assert.assertTrue(viewrole.changeDeductionMode_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.LOYALTY:
					Assert.assertTrue(viewrole.loyalty_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.ROLE:
					Assert.assertTrue(viewrole.rolemanageMenu_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.ADD_ROLE:
					Assert.assertTrue(viewrole.addRole_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.VIEW_ROLE:
					Assert.assertTrue(viewrole.viewRole_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.REPORT:
					Assert.assertTrue(viewrole.reportmanageMenu_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.CUST_REPORT:
					Assert.assertTrue(viewrole.custReport_checkbox.isSelected());
					break;
				case MIP_Menu_Constants.AGENT_REPORT:
					Assert.assertTrue(viewrole.agentReport_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.DEDUCTION_REPORT:
					Assert.assertTrue(viewrole.deductionReport_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.CLAIMS:
					Assert.assertTrue(viewrole.claimsmanageMenu_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.SEARCH_CLAIMS:
					Assert.assertTrue(viewrole.claimSearch_checkbox
							.isSelected());
					break;
				case MIP_Menu_Constants.ADMIN_CONFIG:
					Assert.assertTrue(viewrole.adminConfig_checkbox
							.isSelected());
					break;
				default:
					Assert.assertFalse(true);
				}
			}
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.VIEW_ROLE);
			log.info("Testcase TC199  Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}

	}

	@Test(testName = "TC201", dataProvider = "viewRoleTestData", dataProviderClass = MIP_RoleManagement_TestData.class)
	public void viewRoleThree(String role) throws Throwable {
		try {
			logger = report.startTest("View Role -TC201");
			log.info("Running test case - TC201");
			homepage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'View Roles')]"));
			viewrole = PageFactory.initElements(MIP_Test_Configuration.driver,
					MIP_ViewRolePage.class);
			viewrole.selectRole(role);
			viewrole.selectregsCust();
			viewrole.selectAddBranch();
			viewrole.clickOnClear();
			Assert.assertTrue(viewrole.getSelectedOption().equals(""));
			Assert.assertFalse(viewrole.homeMenu_checkbox.isSelected());

			Assert.assertFalse(viewrole.branchmanageMenu_checkbox.isSelected());
			Assert.assertFalse(viewrole.addBranch_checkbox.isSelected());
			Assert.assertFalse(viewrole.listBranch_checkbox.isSelected());

			Assert.assertFalse(viewrole.usermanageMenu_checkbox.isSelected());
			Assert.assertFalse(viewrole.addUser_checkbox.isSelected());
			Assert.assertFalse(viewrole.listUser_checkbox.isSelected());
			Assert.assertFalse(viewrole.searchUser_checkbox.isSelected());
			Assert.assertFalse(viewrole.resetPassword_checkbox.isSelected());

			Assert.assertFalse(viewrole.leavemanageMenu_checkbox.isSelected());
			Assert.assertFalse(viewrole.applyLeave_checkbox.isSelected());
			Assert.assertFalse(viewrole.viewLeave_checkbox.isSelected());

			Assert.assertFalse(viewrole.custmanageMenu_checkbox.isSelected());
			Assert.assertFalse(viewrole.regCust_checkbox.isSelected());
			Assert.assertFalse(viewrole.searchCust_checkbox.isSelected());
			Assert.assertFalse(viewrole.deRegCust_checkbox.isSelected());
			Assert.assertFalse(viewrole.downloadESignature_checkbox
					.isSelected());
			Assert.assertFalse(viewrole.coverHist_checkbox.isSelected());

			Assert.assertFalse(viewrole.smsmanageMenu_checkbox.isSelected());
			Assert.assertFalse(viewrole.editSms_checkbox.isSelected());
			Assert.assertFalse(viewrole.changeDeductionMode_checkbox
					.isSelected());
			Assert.assertFalse(viewrole.loyalty_checkbox.isSelected());
			Assert.assertFalse(viewrole.claimSearch_checkbox.isSelected());

			Assert.assertFalse(viewrole.rolemanageMenu_checkbox.isSelected());
			Assert.assertFalse(viewrole.addRole_checkbox.isSelected());
			Assert.assertFalse(viewrole.viewRole_checkbox.isSelected());

			Assert.assertFalse(viewrole.reportmanageMenu_checkbox.isSelected());
			Assert.assertFalse(viewrole.custReport_checkbox.isSelected());
			Assert.assertFalse(viewrole.agentReport_checkbox.isSelected());

			Assert.assertFalse(viewrole.adminConfig_checkbox.isSelected());
			Assert.assertFalse(viewrole.changePass_checkbox.isSelected());
			Assert.assertFalse(viewrole.logout_checkbox.isSelected());
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.VIEW_ROLE);
			log.info("Testcase TC201  Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@Test(testName = "TC200", dataProvider = "viewRoleUpdate", dataProviderClass = MIP_RoleManagement_TestData.class)
	public void viewRoleFour(String testcase, String roleName,
			String addbranch, String listbranch, String adduser,
			String listuser, String searchuser, String resetpassword,
			String applyleave, String viewleave, String regCust,
			String ipRegisterAccess, String searchCust, String deregCust,
			String downloadesignature, String changeDeduction, String loyalty,
			String coverHist, String editsms, String addRole, String viewRole,
			String custReport, String agentReport, String deductionReport,
			String claimSearch, String adminConfig, String msg)
			throws Throwable {
		try {
			logger = report.startTest("View Role -TC200");
			log.info("Running test case - TC200");
			homepage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'View Roles')]"));
			viewrole = PageFactory.initElements(MIP_Test_Configuration.driver,
					MIP_ViewRolePage.class);
			viewrole.selectRole(roleName);
			if (addbranch.equalsIgnoreCase("true")) {
				if (!viewrole.addBranch_checkbox.isSelected()) {
					viewrole.selectAddBranch();
				}
			}
			if (addbranch.equalsIgnoreCase("false")) {
				if (viewrole.addBranch_checkbox.isSelected()) {
					viewrole.selectAddBranch();
				}
			}
			if (listbranch.equalsIgnoreCase("true")) {
				if (!viewrole.listBranch_checkbox.isSelected()) {
					viewrole.selectListBranch();
				}
			}
			if (listbranch.equalsIgnoreCase("false")) {
				if (viewrole.listBranch_checkbox.isSelected()) {
					viewrole.selectListBranch();
				}
			}
			if (listuser.equalsIgnoreCase("true")) {
				if (!viewrole.listUser_checkbox.isSelected()) {
					viewrole.selectListUser();
				}
			}
			if (listuser.equalsIgnoreCase("false")) {
				if (viewrole.listUser_checkbox.isSelected()) {
					viewrole.selectListUser();
				}
			}
			if (searchuser.equalsIgnoreCase("true")) {
				if (!viewrole.searchUser_checkbox.isSelected()) {
					viewrole.selectSearchUser();
				}
			}
			if (searchuser.equalsIgnoreCase("false")) {
				if (viewrole.searchUser_checkbox.isSelected()) {
					viewrole.selectSearchUser();
				}
			}
			if (adduser.equalsIgnoreCase("true")) {
				if (!viewrole.addUser_checkbox.isSelected()) {
					viewrole.selectAddUser();
				}
			}
			if (adduser.equalsIgnoreCase("false")) {
				if (viewrole.addUser_checkbox.isSelected()) {
					viewrole.selectAddUser();
				}
			}
			if (resetpassword.equalsIgnoreCase("true")) {
				if (!viewrole.resetPassword_checkbox.isSelected()) {
					viewrole.selectresetPassword();
				}
			}
			if (resetpassword.equalsIgnoreCase("false")) {
				if (viewrole.resetPassword_checkbox.isSelected()) {
					viewrole.selectresetPassword();
				}
			}
			if (applyleave.equalsIgnoreCase("true")) {
				if (!viewrole.applyLeave_checkbox.isSelected()) {
					viewrole.selectapplyLeave();
				}
			}
			if (applyleave.equalsIgnoreCase("false")) {
				if (viewrole.applyLeave_checkbox.isSelected()) {
					viewrole.selectapplyLeave();
				}
			}
			if (viewleave.equalsIgnoreCase("true")) {
				if (!viewrole.viewLeave_checkbox.isSelected()) {
					viewrole.selectviewLeave();
				}
			}
			if (viewleave.equalsIgnoreCase("false")) {
				if (viewrole.viewLeave_checkbox.isSelected()) {
					viewrole.selectviewLeave();
				}
			}
			if (regCust.equalsIgnoreCase("true")) {
				if (!viewrole.regCust_checkbox.isSelected()) {
					viewrole.selectregsCust();
				}
				if (ipRegisterAccess.equalsIgnoreCase("true")) {
					if (!viewrole.waitForElementToVisible(By.id("isIPReg"))
							.isSelected()) {
						viewrole.selectIPREgisterAccess();
					}
				}
			}
			if (regCust.equalsIgnoreCase("false")) {
				if (viewrole.regCust_checkbox.isSelected()) {
					viewrole.selectregsCust();
				}
			}
			if (searchCust.equalsIgnoreCase("true")) {
				if (!viewrole.searchCust_checkbox.isSelected()) {
					viewrole.selectsearchCust();
				}
			}
			if (searchCust.equalsIgnoreCase("false")) {
				if (viewrole.searchCust_checkbox.isSelected()) {
					viewrole.selectsearchCust();
				}
			}
			if (deregCust.equalsIgnoreCase("true")) {
				if (!viewrole.deRegCust_checkbox.isSelected()) {
					viewrole.selectdeRegsCust();
				}
			}
			if (deregCust.equalsIgnoreCase("false")) {
				if (viewrole.deRegCust_checkbox.isSelected()) {
					viewrole.selectdeRegsCust();
				}
			}
			if (downloadesignature.equalsIgnoreCase("true")) {
				if (!viewrole.downloadESignature_checkbox.isSelected()) {
					viewrole.selectdownloadESignature();
				}
			}
			if (downloadesignature.equalsIgnoreCase("false")) {
				if (viewrole.downloadESignature_checkbox.isSelected()) {
					viewrole.selectdownloadESignature();
				}
			}
			if (coverHist.equalsIgnoreCase("true")) {
				if (!viewrole.coverHist_checkbox.isSelected()) {
					viewrole.selectcoverHistry();
				}
			}
			if (coverHist.equalsIgnoreCase("false")) {
				if (viewrole.coverHist_checkbox.isSelected()) {
					viewrole.selectcoverHistry();
				}
			}
			if (changeDeduction.equalsIgnoreCase("true")) {
				if (!viewrole.changeDeductionMode_checkbox.isSelected()) {
					viewrole.selectchangeDeductionMode();
				}
			}
			if (changeDeduction.equalsIgnoreCase("false")) {
				if (viewrole.changeDeductionMode_checkbox.isSelected()) {
					viewrole.selectchangeDeductionMode();
				}
			}
			if (loyalty.equalsIgnoreCase("true")) {
				if (!viewrole.loyalty_checkbox.isSelected()) {
					viewrole.selectloyalty();
				}
			}
			if (loyalty.equalsIgnoreCase("false")) {
				if (viewrole.loyalty_checkbox.isSelected()) {
					viewrole.selectloyalty();
				}
			}
			if (editsms.equalsIgnoreCase("true")) {
				if (!viewrole.editSms_checkbox.isSelected()) {
					viewrole.selecteditSms();
				}
			}
			if (editsms.equalsIgnoreCase("false")) {
				if (viewrole.editSms_checkbox.isSelected()) {
					viewrole.selecteditSms();
				}
			}
			if (deductionReport.equalsIgnoreCase("true")) {
				if (!viewrole.deductionReport_checkbox.isSelected()) {
					viewrole.selectDeductionReport();
				}
			}
			if (deductionReport.equalsIgnoreCase("false")) {
				if (viewrole.deductionReport_checkbox.isSelected()) {
					viewrole.selectDeductionReport();
				}
			}
			if (claimSearch.equalsIgnoreCase("true")) {
				if (!viewrole.claimSearch_checkbox.isSelected()) {
					viewrole.selectclaimSearch();
				}
			}
			if (claimSearch.equalsIgnoreCase("false")) {
				if (viewrole.claimSearch_checkbox.isSelected()) {
					viewrole.selectclaimSearch();
				}
			}
			if (addRole.equalsIgnoreCase("true")) {
				if (!viewrole.addRole_checkbox.isSelected()) {
					viewrole.selectaddRole();
				}
			}
			if (addRole.equalsIgnoreCase("false")) {
				if (viewrole.addRole_checkbox.isSelected()) {
					viewrole.selectaddRole();
				}
			}
			if (viewRole.equalsIgnoreCase("true")) {
				if (!viewrole.viewRole_checkbox.isSelected()) {
					viewrole.selectviewRole();
				}
			}
			if (viewRole.equalsIgnoreCase("false")) {
				if (viewrole.viewRole_checkbox.isSelected()) {
					viewrole.selectviewRole();
				}
			}
			if (custReport.equalsIgnoreCase("true")) {
				if (!viewrole.custReport_checkbox.isSelected()) {
					viewrole.selectCustReport();
				}
			}
			if (custReport.equalsIgnoreCase("false")) {
				if (viewrole.custReport_checkbox.isSelected()) {
					viewrole.selectCustReport();
				}
			}
			if (agentReport.equalsIgnoreCase("true")) {
				if (!viewrole.agentReport_checkbox.isSelected()) {
					viewrole.selectAgentReport();
				}
			}
			if (agentReport.equalsIgnoreCase("false")) {
				if (viewrole.agentReport_checkbox.isSelected()) {
					viewrole.selectAgentReport();
				}
			}
			if (adminConfig.equalsIgnoreCase("true")) {
				if (!viewrole.adminConfig_checkbox.isSelected()) {
					viewrole.selectAdminConfig();
				}
			}
			if (adminConfig.equalsIgnoreCase("false")) {
				if (viewrole.adminConfig_checkbox.isSelected()) {
					viewrole.selectAdminConfig();
				}
			}
			viewrole.clickOnSave();
			viewrole.confirmViewRole("yes");
			Assert.assertTrue(viewrole.getSuccessMessage().contains(msg));
			List<String> roleaccess = MIP_RoleManagement_Queries
					.getRoleAcess(roleName);

			if (addbranch.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.ADD_BRANCH));
			}

			if (listbranch.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.LIST_BRANCH));
			}

			if (listuser.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.LIST_USER));
			}

			if (searchuser.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.SEARCH_USER));
			}

			if (adduser.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.ADD_USER));
			}

			if (resetpassword.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.RESET_PASSWORD));
			}

			if (applyleave.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.APPLY_LEAVE));
			}

			if (viewleave.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.VIEW_LEAVE));
			}

			if (regCust.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.REGISTER_CUSTOMER));
			}

			if (searchCust.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.SEARCH_CUSTOMER));
			}

			if (deregCust.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.DE_REGISTER_CUSTOMER));
			}

			if (downloadesignature.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.E_SIGNATURE));
			}
			if (changeDeduction.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.CHANGE_DEDUCTION_MODE));
			}
			if (loyalty.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.LOYALTY));
			}

			if (coverHist.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.COVER_HISTORY));
			}

			if (editsms.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.EDIT_SMS_TEMPLATE));
			}

			if (deductionReport.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.DEDUCTION_REPORT));
			}

			if (claimSearch.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.SEARCH_CLAIMS));
			}

			if (addRole.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.ADD_ROLE));
			}

			if (viewRole.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.VIEW_ROLE));
			}

			if (custReport.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.CUST_REPORT));
			}

			if (agentReport.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.AGENT_REPORT));
			}

			if (adminConfig.equalsIgnoreCase("true")) {
				Assert.assertTrue(roleaccess
						.contains(MIP_Menu_Constants.ADMIN_CONFIG));
			}
			Assert.assertTrue(roleaccess.contains(MIP_Menu_Constants.HOME));
			Assert.assertTrue(roleaccess.contains(MIP_Menu_Constants.LOGOUT));
			Assert.assertTrue(roleaccess
					.contains(MIP_Menu_Constants.CHANGE_PASSWORD));
			viewrole.waitForElementToVisible(By.linkText("here")).click();
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.VIEW_ROLE);
			log.info("Testcase TC201  Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@AfterMethod(alwaysRun = true)
	public void after_test(ITestResult res) {

		if (res.getStatus() == ITestResult.FAILURE) {
			MIP_ScreenShots.takeScreenShot(MIP_Test_Configuration.driver,
					res.getName());
			logger.log(LogStatus.FAIL, "Test Failed");
			logger.log(LogStatus.ERROR, res.getThrowable());
		} else {
			logger.log(LogStatus.PASS, "Test passed");
		}
	}

	@AfterTest(alwaysRun = true)
	@Parameters("flag")
	public void tear_down(@Optional("0") String flag) {
		if (flag.equals("0"))
			MIP_BrowserFactory.closeDriver(MIP_Test_Configuration.driver);
		else
			homepage.clickOnMenu(MIP_Menu_Constants.ROLE);
		report.endTest(logger);
		report.flush();
	}

}
