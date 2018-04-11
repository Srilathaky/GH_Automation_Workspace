package com.milvik.mip.claimsmanagement.testrunner;

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
import com.milvik.mip.dataprovider.MIP_ClaimManagement_TestData;
import com.milvik.mip.dbqueries.MIP_ClaimManagement_Queries;
import com.milvik.mip.pageobjects.MIP_HomePage;
import com.milvik.mip.pageobjects.MIP_LoginPage;
import com.milvik.mip.pageobjects.MIP_SearchAndModifyPage;
import com.milvik.mip.pageutil.MIP_CustomerManagementPage;
import com.milvik.mip.testconfig.MIP_Test_Configuration;
import com.milvik.mip.utility.MIP_BrowserFactory;
import com.milvik.mip.utility.MIP_DataBaseConnection;
import com.milvik.mip.utility.MIP_DateFunctionality;
import com.milvik.mip.utility.MIP_LaunchApplication;
import com.milvik.mip.utility.MIP_Logging;
import com.milvik.mip.utility.MIP_ReadPropertyFile;
import com.milvik.mip.utility.MIP_ScreenShots;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class MIP_SearchModifyCustomer_Test {
	ExtentReports report;
	ExtentTest logger;
	static Logger log;
	MIP_LoginPage loginpage = null;
	MIP_SearchAndModifyPage searchclaim = null;
	MIP_HomePage homepage = null;

	@BeforeTest
	@Parameters({ "flag", "browser" })
	public void test_setup(@Optional("firefox") String browser,
			@Optional("0") String flag) {
		log = MIP_Logging.logDetails("MIP_SearchUser_Test");
		report = new ExtentReports(".\\Test_Reports\\Search_User_Test.html");
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
			homepage.clickOnMenu(MIP_Menu_Constants.CLAIMS);
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
		} else {
			homepage = new MIP_HomePage(MIP_Test_Configuration.driver);
			homepage.clickOnMenu(MIP_Menu_Constants.CLAIMS);
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
		}
	}

	@Test(testName = "TC238-TC239")
	public void claimManagementOne() throws Throwable {
		try {
			logger = report.startTest("Claim Management-TC238-TC239");
			log.info("Running test case - TC238-TC239");
			searchclaim = PageFactory.initElements(
					MIP_Test_Configuration.driver,
					MIP_SearchAndModifyPage.class);
			homepage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'Search and Modify Customer')]"));
			Assert.assertTrue(searchclaim.msisdn.isDisplayed());
			Assert.assertTrue(searchclaim.search_icon.isDisplayed());
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
			log.info("Testcase TC238-TC239  Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@Test(testName = "TC240-TC243", dataProvider = "claimmsisdnvalidation", dataProviderClass = MIP_ClaimManagement_TestData.class)
	public void claimManagementTwo(String msisdn, String errormsg)
			throws Throwable {
		try {
			logger = report.startTest("Claim Management-TC240-TC243");
			log.info("Running test case - TC240-TC243");
			homepage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'Search and Modify Customer')]"));
			searchclaim = PageFactory.initElements(
					MIP_Test_Configuration.driver,
					MIP_SearchAndModifyPage.class);
			homepage.waitForElementToVisible(By.id("msisdn"));
			Thread.sleep(1000);

			searchclaim.enterMsisdn(msisdn).clickOnSearchIcon();
			Assert.assertTrue(searchclaim.getValidationMessage().trim()
					.equalsIgnoreCase(errormsg.trim()));
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
			log.info("Testcase TC240-TC243  Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@Test(testName = "TC244-TC245-TC247", dataProvider = "claimmsisdn", dataProviderClass = MIP_ClaimManagement_TestData.class)
	public void claimManagementThree(String msisdn) throws Throwable {
		try {
			logger = report.startTest("Claim Management-TC244-TC245");
			log.info("Running test case - TC244");

			homepage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'Search and Modify Customer')]"));
			MIP_SearchAndModifyPage searchclaim = PageFactory.initElements(
					MIP_Test_Configuration.driver,
					MIP_SearchAndModifyPage.class);
			homepage.waitForElementToVisible(By.id("msisdn"));
			Thread.sleep(1000);
			searchclaim.enterMsisdn(msisdn).clickOnSearchIcon();
			Assert.assertTrue(searchclaim.validateClaimSearchtableHeading());
			String customerName = searchclaim.getCustomerNameFromTable();
			List<String> details = MIP_ClaimManagement_Queries
					.getCustomerNameAndAge(msisdn);
			Assert.assertTrue(details.get(0).equalsIgnoreCase(customerName));
			Assert.assertTrue(details.get(1).equalsIgnoreCase(
					searchclaim.getCustomerAgeFromTable()));
			Assert.assertTrue(details.get(2).equalsIgnoreCase(
					searchclaim.getCustomerImpliedAgeFromTable()));
			searchclaim.clickOnCustomerNameLink();
			Assert.assertTrue(searchclaim.validateviewCustomerObjects());
			List<String> dbcustdetails = MIP_ClaimManagement_Queries
					.getCustomerInformation(msisdn);
			List<String> dashboardcustdetails = searchclaim
					.getCustomerInformation();
			Assert.assertTrue(dbcustdetails.equals(dashboardcustdetails));
			List<String> productDeitails = MIP_ClaimManagement_Queries
					.getSubscribedProduct(msisdn);
			if (productDeitails.contains(MIP_CustomerManagementPage.XTRALIFE)) {
				List<String> dbrelativedetails = MIP_ClaimManagement_Queries
						.getCustomerRelaiveDetails(msisdn,
								MIP_CustomerManagementPage.XTRALIFE);
				List<String> dashboardrelativedetails = searchclaim
						.getInsuredRelativeInformation();
				Assert.assertTrue(dbrelativedetails
						.equals(dashboardrelativedetails));
			}
			if (productDeitails.contains(MIP_CustomerManagementPage.IP)) {
				List<String> dbrelativedetails = MIP_ClaimManagement_Queries
						.getIncomeProtectionInformation(msisdn);
				List<String> dashboardrelativedetails = searchclaim
						.getIncomeProtectionInformation();
				Assert.assertTrue(dbrelativedetails
						.equals(dashboardrelativedetails));
				String res1 = searchclaim.getIncomeProtectionCoverDetails()
						.replaceAll("GHC", "").trim();
				String res2 = MIP_ClaimManagement_Queries
						.getIncomeProtectionCoverDetails(msisdn);
				boolean value = false;
				for (int i = 0; i < res1.length(); i++) {
					if (res1.charAt(i) == res2.charAt(i)) {
						value = true;
					}
				}
				Assert.assertTrue(value);
			}
			if (productDeitails.contains(MIP_CustomerManagementPage.HOSPITAL)) {
				List<String> dbrelativedetails = MIP_ClaimManagement_Queries
						.getCustomerRelaiveDetails(msisdn,
								MIP_CustomerManagementPage.HOSPITAL);
				List<String> dashboardrelativedetails = searchclaim
						.getHPRelativeInformation();
				Assert.assertTrue(dbrelativedetails
						.equals(dashboardrelativedetails));
			}
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
			log.info("Testcase TC244-TC245  Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@Test(testName = "TC246-TC248", dataProvider = "claimmsisdn", dataProviderClass = MIP_ClaimManagement_TestData.class)
	public void claimManagementFour(String msisdn) throws Throwable {
		try {
			logger = report.startTest("Claim Management-TC246-TC248");
			log.info("Running test case - TC246-TC248");
			homepage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'Search and Modify Customer')]"));
			MIP_SearchAndModifyPage searchclaim = PageFactory.initElements(
					MIP_Test_Configuration.driver,
					MIP_SearchAndModifyPage.class);
			homepage.waitForElementToVisible(By.id("msisdn"));
			Thread.sleep(1000);
			searchclaim.enterMsisdn(msisdn).clickOnSearchIcon();
			searchclaim.clickOnCustomerNameLink();
			Assert.assertTrue(searchclaim.readOnlyCheck());
			searchclaim.clickOnClear();
			List<String> details = searchclaim.getCustomerInformation();
			for (int i = 0; i < details.size(); i++) {
				if (i != 5) {
					Assert.assertTrue(details.get(i).equals(""));
				}
			}
			List<String> productDeitails = MIP_ClaimManagement_Queries
					.getSubscribedProduct(msisdn);
			if (productDeitails.contains(MIP_CustomerManagementPage.XTRALIFE)) {
				List<String> dashboardrelativedetails = searchclaim
						.getInsuredRelativeInformation();
				for (String str : dashboardrelativedetails) {
					Assert.assertTrue(str.equals(""));
				}
			}
			if (productDeitails.contains(MIP_CustomerManagementPage.IP)) {
				List<String> dashboardrelativedetails = searchclaim
						.getIncomeProtectionInformation();
				for (String str : dashboardrelativedetails) {
					Assert.assertTrue(str.equals(""));
				}

			}
			if (productDeitails.contains(MIP_CustomerManagementPage.HOSPITAL)) {
				List<String> dashboardrelativedetails = searchclaim
						.getHPRelativeInformation();
				for (String str : dashboardrelativedetails) {
					Assert.assertTrue(str.equals(""));
				}

			}
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
			log.info("Testcase TC246-TC248  Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@Test(testName = "ValidationScenario", dataProvider = "fieldValidation", dataProviderClass = MIP_ClaimManagement_TestData.class)
	public void claimManagementFive(String fields, String msisdn,
			String errormsg) throws Throwable {
		try {
			logger = report.startTest("ValidationScenario");
			log.info("Running test case - ValidationScenario");
			homepage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'Search and Modify Customer')]"));
			MIP_SearchAndModifyPage searchclaim = PageFactory.initElements(
					MIP_Test_Configuration.driver,
					MIP_SearchAndModifyPage.class);
			homepage.waitForElementToVisible(By.id("msisdn"));
			Thread.sleep(1000);
			searchclaim.enterMsisdn(msisdn).clickOnSearchIcon();
			searchclaim.clickOnCustomerNameLink();
			if (fields.equalsIgnoreCase("Customer Information")) {
				Assert.assertTrue(searchclaim
						.clearCustomerInformation()
						.clickOnSave()
						.getValidationMessage()
						.trim()
						.replaceAll("\\s", "")
						.equalsIgnoreCase(errormsg.trim().replaceAll("\\s", "")));
			} else if (fields.equalsIgnoreCase("Insured Relative Information")) {
				Assert.assertTrue(searchclaim
						.clearInsuredRelativeInformation()
						.clickOnSave()
						.getValidationMessage()
						.trim()
						.replaceAll("\\s", "")
						.equalsIgnoreCase(errormsg.trim().replaceAll("\\s", "")));
			} else if (fields
					.equalsIgnoreCase("Nominee Information for Income Protection Policy")) {
				Assert.assertTrue(searchclaim
						.clearNomineeInformation()
						.clickOnSave()
						.getValidationMessage()
						.trim()
						.replaceAll("\\s", "")
						.equalsIgnoreCase(errormsg.trim().replaceAll("\\s", "")));
			} else if (fields
					.equalsIgnoreCase("Insured Relative Information for HP")) {
				Assert.assertTrue(searchclaim
						.clearInsuredRelativeInformationHP()
						.clickOnSave()
						.getValidationMessage()
						.trim()
						.replaceAll("\\s", "")
						.equalsIgnoreCase(errormsg.trim().replaceAll("\\s", "")));
			}
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
			log.info("Testcase ValidationScenario  Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@Test(testName = "EditCstomerdetails", dataProvider = "editInfo", dataProviderClass = MIP_ClaimManagement_TestData.class)
	public void claimManagementSix(String msisdn, String cust_fname,
			String cust_sname, String cust_age, String dob, String cust_gender,
			String relationship, String rel_fname, String rel_sname,
			String rel_age, String rel_dob, String rel_mobileNumber,
			String Nominee_Fname, String Nominee_Snmae, String Nominee_age,
			String Nominee_mobilenumber, String hp_relationship,
			String hp_fname, String hp_sname, String hp_age, String hp_dob,
			String hp_msisdn) throws Throwable {
		try {
			logger = report.startTest("EditCstomerdetails");
			log.info("Running test case - EditCstomerdetails");
			homepage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'Search and Modify Customer')]"));
			MIP_SearchAndModifyPage searchclaim = PageFactory.initElements(
					MIP_Test_Configuration.driver,
					MIP_SearchAndModifyPage.class);
			homepage.waitForElementToVisible(By.id("msisdn"));
			Thread.sleep(1000);

			searchclaim.enterMsisdn(msisdn).clickOnSearchIcon();
			searchclaim.clickOnCustomerNameLink();
			searchclaim
					.clearCustomerInformation(cust_fname, cust_sname, cust_age,
							dob)
					.enterCustomerInformation(cust_fname, cust_sname, cust_age,
							dob, cust_gender).informBenOption().clickOnSave()
					.confirmPopUp("yes");
			List<String> details = MIP_ClaimManagement_Queries
					.getCustomerInformation(msisdn);
			if (!cust_fname.equals(""))
				Assert.assertTrue(details.toString().trim()
						.contains(cust_fname.trim()));
			if (!cust_sname.equals(""))
				Assert.assertTrue(details.toString().trim()
						.contains(cust_sname.trim()));
			if (!cust_age.equals(""))
				Assert.assertTrue(details.toString().contains(cust_age));
			if (!cust_gender.equals(""))
				Assert.assertTrue(details.toString().contains(
						cust_gender.charAt(0) + ""));
			if (!dob.equals(""))
				Assert.assertTrue(details.toString().contains(
						MIP_DateFunctionality.converDateToDBDateFormat(dob)));
			List<String> productDeitails = MIP_ClaimManagement_Queries
					.getSubscribedProduct(msisdn);
			if (productDeitails.contains(MIP_CustomerManagementPage.XTRALIFE)) {
				searchclaim.waitForElementToVisible(By.linkText("here"))
						.click();
				searchclaim.enterMsisdn(msisdn).clickOnSearchIcon();
				searchclaim
						.clickOnCustomerNameLink()
						.clearInsuredRelativeInformation(relationship,
								rel_fname, rel_sname, rel_age, rel_dob,
								rel_mobileNumber)
						.enterInsuredRelativeInformation(relationship,
								rel_fname, rel_sname, rel_age, rel_dob,
								rel_mobileNumber).informBenOption()
						.clickOnSave().confirmPopUp("yes");
				List<String> rel_details = MIP_ClaimManagement_Queries
						.getCustomerRelaiveDetails(msisdn,
								MIP_CustomerManagementPage.XTRALIFE);
				if (!relationship.equals(""))
					Assert.assertTrue(rel_details.toString().contains(
							relationship));
				if (!rel_fname.equals(""))
					Assert.assertTrue(rel_details.toString()
							.contains(rel_fname));
				if (!rel_sname.equals(""))
					Assert.assertTrue(rel_details.toString()
							.contains(rel_sname));
				if (!rel_age.equals(""))
					Assert.assertTrue(rel_details.toString().trim()
							.contains(rel_age));
				if (!rel_mobileNumber.equals(""))
					Assert.assertTrue(rel_details.toString().contains(
							rel_mobileNumber));
				if (!rel_dob.equals(""))
					Assert.assertTrue(rel_details.toString().contains(
							MIP_DateFunctionality
									.converDateToDBDateFormat(rel_dob)));

			}

			if (productDeitails.contains(MIP_CustomerManagementPage.IP)) {
				searchclaim.waitForElementToVisible(By.linkText("here"))
						.click();
				searchclaim.enterMsisdn(msisdn).clickOnSearchIcon();
				searchclaim
						.clickOnCustomerNameLink()
						.clearNomineeInformation(Nominee_Fname, Nominee_Snmae,
								Nominee_age, Nominee_mobilenumber)
						.enterNomineeInformation(Nominee_Fname, Nominee_Snmae,
								Nominee_age, Nominee_mobilenumber)
						.informBenOption().clickOnSave().confirmPopUp("yes");
				List<String> nominee_details = MIP_ClaimManagement_Queries
						.getIncomeProtectionInformation(msisdn);
				if (!Nominee_Fname.equals(""))
					Assert.assertTrue(nominee_details.toString().contains(
							Nominee_Fname));
				if (!Nominee_Snmae.equals(""))
					Assert.assertTrue(nominee_details.toString().contains(
							Nominee_Snmae));
				if (!Nominee_age.equals(""))
					Assert.assertTrue(nominee_details.toString().contains(
							Nominee_age));
				if (!Nominee_mobilenumber.equals(""))
					Assert.assertTrue(nominee_details.toString().contains(
							Nominee_mobilenumber));

			}
			if (productDeitails.contains(MIP_CustomerManagementPage.HOSPITAL)) {
				searchclaim.waitForElementToVisible(By.linkText("here"))
						.click();
				searchclaim.enterMsisdn(msisdn).clickOnSearchIcon();
				searchclaim
						.clickOnCustomerNameLink()
						.clearInsuredRelativeInformationHP(hp_relationship,
								hp_fname, hp_sname, hp_age, hp_dob, hp_msisdn)
						.enterRelativeInformationHP(hp_relationship, hp_fname,
								hp_sname, hp_age, hp_dob, hp_msisdn)
						.informBenOption().clickOnSave().confirmPopUp("yes");
				List<String> rel_details = MIP_ClaimManagement_Queries
						.getCustomerRelaiveDetails(msisdn,
								MIP_CustomerManagementPage.HOSPITAL);
				if (!hp_relationship.equals(""))
					Assert.assertTrue(rel_details.toString().contains(
							hp_relationship));
				if (!hp_fname.equals(""))
					Assert.assertTrue(rel_details.toString().contains(hp_fname));
				if (!hp_sname.equals(""))
					Assert.assertTrue(rel_details.toString().contains(hp_sname));
				if (!hp_age.equals(""))
					Assert.assertTrue(rel_details.toString().contains(hp_age));
				if (!hp_msisdn.equals(""))
					Assert.assertTrue(rel_details.toString()
							.contains(hp_msisdn));
				if (!hp_dob.equals(""))
					Assert.assertTrue(rel_details.toString().contains(
							MIP_DateFunctionality
									.converDateToDBDateFormat(hp_dob)));

			}
			homepage.clickOnMenu(MIP_Menu_Constants.CLAIMS);
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
			log.info("Testcase TC246-TC248  Failed");
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
			homepage.clickOnMenu(MIP_Menu_Constants.CLAIMS);
		report.endTest(logger);
		report.flush();
	}
}
