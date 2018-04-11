package com.milvik.mip.customermanagement.testrunner;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
import com.milvik.mip.dataprovider.MIP_RegCust_TestData;
import com.milvik.mip.dbqueries.MIP_RegisterCustomer_Queries;
import com.milvik.mip.listeners.MIP_RetryAnalyzer;
import com.milvik.mip.pageobjects.MIP_HomePage;
import com.milvik.mip.pageobjects.MIP_LoginPage;
import com.milvik.mip.pageobjects.MIP_RegisterCustomerPage;
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

public class MIP_CustRegistration_Test {
	ExtentReports report;
	ExtentTest logger;
	static Logger log;
	MIP_LoginPage loginpage;
	MIP_HomePage homepage;
	MIP_RegisterCustomerPage regpage = null;

	@BeforeTest
	@Parameters({ "flag", "browser" })
	public void test_setup(@Optional("firefox") String browser,
			@Optional("0") String flag) {
		log = MIP_Logging.logDetails("MIP_CustRegistration_Test");
		report = new ExtentReports(
				".\\Test_Reports\\RegisterCustomer_Test.html");
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
			homepage.clickOnMenu(MIP_Menu_Constants.CUSTOMER);
			homepage.clickOnMenu(MIP_Menu_Constants.REGISTER_CUSTOMER);
		} else {
			homepage = new MIP_HomePage(MIP_Test_Configuration.driver);
			/*try {
				if (!homepage.waitForElementToVisible(
						By.linkText(MIP_Menu_Constants.REGISTER_CUSTOMER))
						.isDisplayed()) {*/
					homepage.clickOnMenu(MIP_Menu_Constants.CUSTOMER);
					homepage.clickOnMenu(MIP_Menu_Constants.REGISTER_CUSTOMER);
			/*	}
			} catch (Exception e) {*/
			/*	homepage.clickOnMenu(MIP_Menu_Constants.REGISTER_CUSTOMER);
			}*/
		}
	}

	@Test(priority = 1, testName = "validateCustomerRegPage", dataProvider = "Customer_Registartion_validateobject", dataProviderClass = MIP_RegCust_TestData.class)
	public void regCustOne(String msisdn) throws Throwable {
		try {
			logger = report
					.startTest("Register Customer-validateCustomerRegPage");
			regpage = PageFactory.initElements(MIP_Test_Configuration.driver,
					MIP_RegisterCustomerPage.class);
			try {
				regpage.waitForElementToVisible(By
						.xpath("//h3[contains(text(),'Register Customer')]"));
			} catch (Exception e) {
				homepage.clickOnMenu(MIP_Menu_Constants.REGISTER_CUSTOMER);
			}
			Thread.sleep(1000);
			regpage.waitForElementToVisible(By.id("msisdn"));
			regpage.waitForElementToVisible(By.id("search-icon"));
			regpage.enterMSISDN(msisdn).clickOnSearchButton();
			Assert.assertTrue(regpage.validateRegCustObjects());
			Assert.assertTrue(regpage.msisdnReadOnlyCheck().equalsIgnoreCase(
					"true"));
			regpage.clickOnBack();
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.REGISTER_CUSTOMER);
			log.info("validateCustomerRegPage Test Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@Test(priority = 2, testName = "validateCustomerRegfields", dataProvider = "NegativeTest", dataProviderClass = MIP_RegCust_TestData.class)
	public void regCustTwo(String mobilenum, String error_msg1,
			String error_msg2, String error_msg3, String error_msg4)
			throws Throwable {
		try {
			logger = report
					.startTest("Register Customer-validateCustomerRegfields");

			regpage = PageFactory.initElements(MIP_Test_Configuration.driver,
					MIP_RegisterCustomerPage.class);
			regpage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'Register Customer')]"));

			regpage.waitForElementToVisible(By.id("msisdn"));
			regpage.waitForElementTobeClickable(By.id("search-icon"));
			regpage.enterMSISDN(mobilenum).clickOnSearchButton();
			regpage.clickOnSave();
			Assert.assertTrue(regpage.getValidationMsg().trim()
					.replaceAll("\\s", "")
					.equalsIgnoreCase(error_msg1.trim().replaceAll("\\s", "")));
			Assert.assertTrue(regpage.selectXtraLife().clickOnSave()
					.getValidationMsg().trim().replaceAll("\\s", "")
					.equalsIgnoreCase(error_msg2.trim().replaceAll("\\s", "")));
			regpage.selectXtraLife();
			Assert.assertTrue(regpage.selectHospitalization().clickOnSave()
					.getValidationMsg().replaceAll("\\s", "")
					.equalsIgnoreCase(error_msg3.replaceAll("\\s", "")));
			regpage.selectHospitalization();
			Assert.assertTrue(regpage.selectIncomeProtection().clickOnSave()
					.getValidationMsg().replaceAll("\\s", "")
					.equalsIgnoreCase(error_msg4.replaceAll("\\s", "")));
			regpage.clickOnClear();
			regpage.selectHospitalization();
			Assert.assertTrue(regpage
					.waitForElementToVisible(
							By.xpath("//input[@id='productId'][@value='4']"))
					.getAttribute("disabled").equalsIgnoreCase("true"));
			regpage.selectHospitalization();
			regpage.selectIncomeProtection();
			Assert.assertTrue(regpage
					.waitForElementToVisible(
							By.xpath("//input[@id='productId'][@value='3']"))
					.getAttribute("disabled").equalsIgnoreCase("true"));
			regpage.clickOnBack();

			Assert.assertTrue(regpage.waitForElementToPresent(By.id("msisdn"))
					.isDisplayed());
			Assert.assertTrue(regpage.waitForElementToPresent(
					By.id("search-icon")).isDisplayed());

		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.REGISTER_CUSTOMER);
			log.info("validateCustomerRegfields Test Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@Test(enabled = false, priority = 5, testName = "RegisterNewProduct", dataProvider = "MIP_Customer_Registration_Positive_Scenario", dataProviderClass = MIP_RegCust_TestData.class, retryAnalyzer = MIP_RetryAnalyzer.class)
	public void regCustThree(String testcase, String msisdn, String product,
			String fname, String sname, String dob, String age, String gender,
			String xl_relation, String xl_rel_fname, String xl_rel_sname,
			String xl_rel_dob, String xl_age, String xl_inform_ben,
			String xl_rel_msisdn, String hp_relation, String hp_fname,
			String hp_sname, String hp_dob, String hp_age,
			String inform_ben_hp, String hp_rel_mobilenum, String offer_cover,
			String nominee_fname, String nominee_sname, String nominee_age,
			String inform_nominee, String nominee_mobilenum) throws Throwable {
		try {
			logger = report.startTest("Register Customer-RegisterNewProduct");

			regpage = PageFactory.initElements(MIP_Test_Configuration.driver,
					MIP_RegisterCustomerPage.class);
			regpage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'Register Customer')]"));
			regpage.waitForElementToVisible(By.id("msisdn"));
			regpage.waitForElementToVisible(By.id("search-icon"));
			Thread.sleep(300);
			regpage.enterMSISDN(msisdn).clickOnSearchButton();
			Thread.sleep(300);
			if (!fname.equals("")) {
				regpage.enterCustInfo(fname, sname, dob, age, gender);
			}

			if (product.equalsIgnoreCase(MIP_CustomerManagementPage.XTRALIFE)) {

				regpage.selectXtraLife();
				regpage.enterInsuredRelaiveInfo_XL(xl_relation, xl_rel_fname,
						xl_rel_sname, xl_rel_dob, xl_age, xl_inform_ben,
						xl_rel_msisdn);
				regpage.clickOnSave();
				regpage.confirmPopUp("yes");
				regpage.getSuccessMsg();
				regpage.getSuccessMsg();
				regpage.validateCustomerDetails(msisdn, fname, sname, dob, age,
						gender);
				regpage.validateRelativeInfo(msisdn,
						MIP_CustomerManagementPage.XTRALIFE, xl_rel_fname,
						xl_rel_sname, xl_rel_dob, xl_age, xl_relation,
						xl_rel_msisdn);
				Assert.assertNotEquals(MIP_RegisterCustomer_Queries.getSMSText(
						msisdn, MIP_CustomerManagementPage.XTRALIFE), "");
				if (!xl_rel_msisdn.equals(""))
					Assert.assertNotEquals(MIP_RegisterCustomer_Queries
							.getBenSMSText(xl_rel_msisdn), "");

			} else if (product
					.equalsIgnoreCase(MIP_CustomerManagementPage.HOSPITAL)) {
				regpage.selectHospitalization();
				regpage.enterInsuredRelaiveInfo_HP(hp_relation, hp_fname,
						hp_sname, hp_dob, hp_age, inform_ben_hp,
						hp_rel_mobilenum);
				regpage.clickOnSave();
				regpage.confirmPopUp("yes");
				regpage.getSuccessMsg();
				regpage.validateCustomerDetails(msisdn, fname, sname, dob, age,
						gender);
				regpage.validateRelativeInfo(msisdn,
						MIP_CustomerManagementPage.HOSPITAL, hp_fname,
						hp_sname, hp_dob, hp_age, hp_relation, hp_rel_mobilenum);
				Assert.assertNotEquals(MIP_RegisterCustomer_Queries.getSMSText(
						msisdn, MIP_CustomerManagementPage.HOSPITAL), "");
				if (!hp_rel_mobilenum.equals(""))
					Assert.assertNotEquals(MIP_RegisterCustomer_Queries
							.getBenSMSText(hp_rel_mobilenum), "");

			} else if (product.equalsIgnoreCase(MIP_CustomerManagementPage.IP)) {
				regpage.selectIncomeProtection();
				regpage.selectIncomeProtectionCoverDetails(offer_cover)
						.enterNomineeInfo_IP(nominee_fname, nominee_sname,
								nominee_age, inform_nominee, nominee_mobilenum);
				regpage.clickOnSave();
				regpage.confirmPopUp("yes");
				regpage.getSuccessMsg();
				regpage.validateCustomerDetails(msisdn, fname, sname, dob, age,
						gender);
				regpage.validateRelativeInfo(msisdn,
						MIP_CustomerManagementPage.IP, nominee_fname,
						nominee_sname, "", nominee_age, "", nominee_mobilenum);
				Assert.assertNotEquals(MIP_RegisterCustomer_Queries.getSMSText(
						msisdn, MIP_CustomerManagementPage.IP), "");
				if (!nominee_mobilenum.equals(""))
					Assert.assertNotEquals(MIP_RegisterCustomer_Queries
							.getBenSMSText(nominee_mobilenum), "");
				Assert.assertTrue(MIP_RegisterCustomer_Queries
						.getCoverDetailsIP(msisdn)
						.equalsIgnoreCase(offer_cover));
			} else if (product.contains(MIP_CustomerManagementPage.IP)
					&& product.contains(MIP_CustomerManagementPage.XTRALIFE)) {
				regpage.selectXtraLife();
				regpage.selectIncomeProtection();
				regpage.enterInsuredRelaiveInfo_XL(xl_relation, xl_rel_fname,
						xl_rel_sname, xl_rel_dob, xl_age, xl_inform_ben,
						xl_rel_msisdn);
				regpage.selectIncomeProtectionCoverDetails(offer_cover)
						.enterNomineeInfo_IP(nominee_fname, nominee_sname,
								nominee_age, inform_nominee, nominee_mobilenum);
				regpage.clickOnSave();
				regpage.confirmPopUp("yes");
				regpage.getSuccessMsg();
				regpage.validateCustomerDetails(msisdn, fname, sname, dob, age,
						gender);
				regpage.validateRelativeInfo(msisdn,
						MIP_CustomerManagementPage.XTRALIFE, xl_rel_fname,
						xl_rel_sname, xl_rel_dob, xl_age, xl_relation,
						xl_rel_msisdn);
				regpage.validateRelativeInfo(msisdn,
						MIP_CustomerManagementPage.IP, nominee_fname,
						nominee_sname, "", nominee_age, "", nominee_mobilenum);
				Assert.assertNotEquals(MIP_RegisterCustomer_Queries.getSMSText(
						msisdn, MIP_CustomerManagementPage.XTRALIFE), "");
				if (!xl_rel_msisdn.equals(""))
					Assert.assertNotEquals(MIP_RegisterCustomer_Queries
							.getBenSMSText(xl_rel_msisdn), "");
				Assert.assertNotEquals(MIP_RegisterCustomer_Queries.getSMSText(
						msisdn, MIP_CustomerManagementPage.IP), "");
				if (!nominee_mobilenum.equals(""))
					Assert.assertNotEquals(MIP_RegisterCustomer_Queries
							.getBenSMSText(nominee_mobilenum), "");
				Assert.assertTrue(MIP_RegisterCustomer_Queries
						.getCoverDetailsIP(msisdn)
						.equalsIgnoreCase(offer_cover));
			} else if (product.contains(MIP_CustomerManagementPage.HOSPITAL)
					&& product.contains(MIP_CustomerManagementPage.XTRALIFE)) {
				regpage.selectXtraLife();
				regpage.selectHospitalization();
				regpage.enterInsuredRelaiveInfo_XL(xl_relation, xl_rel_fname,
						xl_rel_sname, xl_rel_dob, xl_age, xl_inform_ben,
						xl_rel_msisdn);
				regpage.enterInsuredRelaiveInfo_HP(hp_relation, hp_fname,
						hp_sname, hp_dob, hp_age, inform_ben_hp,
						hp_rel_mobilenum);
				regpage.clickOnSave();
				regpage.confirmPopUp("yes");
				regpage.getSuccessMsg();
				regpage.validateCustomerDetails(msisdn, fname, sname, dob, age,
						gender);
				regpage.validateRelativeInfo(msisdn,
						MIP_CustomerManagementPage.HOSPITAL, hp_fname,
						hp_sname, hp_dob, hp_age, hp_relation, hp_rel_mobilenum);
				regpage.validateRelativeInfo(msisdn,
						MIP_CustomerManagementPage.XTRALIFE, xl_rel_fname,
						xl_rel_sname, xl_rel_dob, xl_age, xl_relation,
						xl_rel_msisdn);
				Assert.assertNotEquals(MIP_RegisterCustomer_Queries.getSMSText(
						msisdn, MIP_CustomerManagementPage.XTRALIFE), "");
				Assert.assertNotEquals(MIP_RegisterCustomer_Queries.getSMSText(
						msisdn, MIP_CustomerManagementPage.HOSPITAL), "");
				if (!xl_rel_msisdn.equals(""))
					Assert.assertNotEquals(MIP_RegisterCustomer_Queries
							.getBenSMSText(xl_rel_msisdn), "");

				if (!hp_rel_mobilenum.equals(""))
					Assert.assertNotEquals(MIP_RegisterCustomer_Queries
							.getBenSMSText(hp_rel_mobilenum), "");

			}
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.REGISTER_CUSTOMER);
			regpage.waitForElementToVisible(By.id("msisdn"));
			log.info("RegisterNewProduct Test case Failed");
			log.info("Error occured in the test case", t);
			throw t;

		}
	}

	@Test(priority = 3, testName = "agevalidation", dataProvider = "agevalidation", dataProviderClass = MIP_RegCust_TestData.class)
	public void regCustFive(String msisdn, String fname, String sname,
			String age, String gender, String error_msg1, String xl_relation,
			String xl_rel_fname, String xl_rel_sname, String xl_age,
			String xl_inform_ben, String xl_rel_msisdn, String error_msg2,
			String hp_relation, String hp_fname, String hp_sname,
			String hp_age, String inform_ben_hp, String hp_rel_mobilenum,
			String error_msg3, String offer_cover, String nominee_fname,
			String nominee_sname, String nominee_age, String inform_nominee,
			String nominee_mobilenum, String err_msg, String error_msg4)
			throws Throwable {
		try {
			logger = report.startTest("Register Customer-agevalidation");

			regpage = PageFactory.initElements(MIP_Test_Configuration.driver,
					MIP_RegisterCustomerPage.class);
			regpage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'Register Customer')]"));
			regpage.waitForElementToVisible(By.id("msisdn"));
			regpage.waitForElementToVisible(By.id("search-icon"));
			regpage.enterMSISDN(msisdn).clickOnSearchButton();
			if (!fname.equals("")) {
				regpage.enterCustInfo(fname, sname, "", age, gender);
			}

			regpage.selectXtraLife();

			regpage.enterInsuredRelaiveInfo_XL(xl_relation, xl_rel_fname,
					xl_rel_sname, "", xl_age, xl_inform_ben, xl_rel_msisdn);

			regpage.clickOnSave();
			Assert.assertTrue(regpage.getValidationMsg().trim()
					.contains(error_msg1.trim()));
			Assert.assertTrue(regpage.getValidationMsg().trim()
					.contains(error_msg2.trim()));
			regpage.clickOnBack();
			regpage.enterMSISDN(msisdn).clickOnSearchButton();
			if (!fname.equals("")) {
				regpage.enterCustInfo(fname, sname, "", age, gender);
			}
			regpage.selectHospitalization();
			regpage.enterInsuredRelaiveInfo_HP(hp_relation, hp_fname, hp_sname,
					"", hp_age, inform_ben_hp, hp_rel_mobilenum);
			regpage.clickOnSave();
			Assert.assertTrue(regpage.getValidationMsg().trim()
					.contains(error_msg1.trim()));
			Assert.assertTrue(regpage.getValidationMsg().trim()
					.contains(error_msg3.trim()));
			regpage.clickOnBack();

			regpage.enterMSISDN(msisdn).clickOnSearchButton();

			regpage.selectIncomeProtection();
			if (!fname.equals("")) {
				regpage.enterCustInfo(fname, sname, "", age, gender);
			}
			regpage.selectIncomeProtectionCoverDetails(offer_cover)
					.enterNomineeInfo_IP(nominee_fname, nominee_sname,
							nominee_age, inform_nominee, nominee_mobilenum);

			regpage.clickOnSave();
			Assert.assertTrue(regpage.getValidationMsg().trim()
					.contains(err_msg.trim()));
			Assert.assertTrue(regpage.getValidationMsg().trim()
					.contains(error_msg4.trim()));
			regpage.clickOnBack();
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.REGISTER_CUSTOMER);
			log.info("agevalidation test case Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@Test(enabled = false, priority = 4, testName = "RegisterAllProduct", dataProvider = "registerAllProduct", dataProviderClass = MIP_RegCust_TestData.class)
	public void regCustFour(String msisdn, String fname, String sname,
			String dob, String age, String gender, String xl_relation,
			String xl_rel_fname, String xl_rel_sname, String xl_rel_dob,
			String xl_age, String xl_inform_ben, String xl_rel_msisdn,
			String hp_relation, String hp_fname, String hp_sname,
			String hp_dob, String hp_age, String inform_ben_hp,
			String hp_rel_mobilenum, String offer_cover, String nominee_fname,
			String nominee_sname, String nominee_age, String inform_nominee,
			String nominee_mobilenum, String errmsg) throws Throwable {
		try {
			logger = report.startTest("Register Customer-RegisterAllProduct");

			regpage = PageFactory.initElements(MIP_Test_Configuration.driver,
					MIP_RegisterCustomerPage.class);
			regpage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'Register Customer')]"));
			regpage.waitForElementToVisible(By.id("msisdn"));
			regpage.waitForElementToVisible(By.id("search-icon"));
			regpage.enterMSISDN(msisdn).clickOnSearchButton();
			if (!fname.equals("")) {
				regpage.enterCustInfo(fname, sname, dob, age, gender);
			}

			regpage.selectXtraLife();
			regpage.enterInsuredRelaiveInfo_XL(xl_relation, xl_rel_fname,
					xl_rel_sname, xl_rel_dob, xl_age, xl_inform_ben,
					xl_rel_msisdn);
			regpage.clickOnSave();
			regpage.confirmPopUp("yes");
			regpage.validateCustomerDetails(msisdn, fname, sname, dob, age,
					gender);
			regpage.validateRelativeInfo(msisdn,
					MIP_CustomerManagementPage.XTRALIFE, xl_rel_fname,
					xl_rel_sname, xl_rel_dob, xl_age, xl_relation,
					xl_rel_msisdn);
			Assert.assertNotEquals(MIP_RegisterCustomer_Queries.getSMSText(
					msisdn, MIP_CustomerManagementPage.XTRALIFE), "");
			if (!xl_rel_msisdn.equals(""))
				Assert.assertNotEquals(MIP_RegisterCustomer_Queries
						.getBenSMSText(xl_rel_msisdn), "");
			regpage.enterMSISDN(msisdn).clickOnSearchButton();
			regpage.selectHospitalization();
			regpage.enterInsuredRelaiveInfo_HP(hp_relation, hp_fname, hp_sname,
					hp_dob, hp_age, inform_ben_hp, hp_rel_mobilenum);
			if (xl_inform_ben.equalsIgnoreCase("no")) {
				WebElement ele = regpage
						.waitForElementToVisible(By
								.xpath("//input[@id='benInsMsisdnYesNo'][@value='no']"));
				((JavascriptExecutor) MIP_Test_Configuration.driver)
						.executeScript("arguments[0].click();", ele);
			}
			regpage.clickOnSave();
			regpage.confirmPopUp("yes");
			regpage.validateCustomerDetails(msisdn, fname, sname, dob, age,
					gender);
			regpage.validateRelativeInfo(msisdn,
					MIP_CustomerManagementPage.HOSPITAL, hp_fname, hp_sname,
					hp_dob, hp_age, hp_relation, hp_rel_mobilenum);
			Assert.assertNotEquals(MIP_RegisterCustomer_Queries.getSMSText(
					msisdn, MIP_CustomerManagementPage.HOSPITAL), "");
			if (!hp_rel_mobilenum.equals(""))
				Assert.assertNotEquals(MIP_RegisterCustomer_Queries
						.getBenSMSText(hp_rel_mobilenum), "");
			regpage.enterMSISDN(msisdn).clickOnSearchButton();

			regpage.selectIncomeProtection();
			regpage.selectIncomeProtectionCoverDetails(offer_cover)
					.enterNomineeInfo_IP(nominee_fname, nominee_sname,
							nominee_age, inform_nominee, nominee_mobilenum);
			regpage.selectIncomeProtectionCoverDetails(offer_cover);
			if (inform_ben_hp.equalsIgnoreCase("no")) {
				WebElement ele = regpage
						.waitForElementToPresent(By
								.xpath("//input[@id='benHpInsMsisdnYesNo'][@value='no']"));
				((JavascriptExecutor) MIP_Test_Configuration.driver)
						.executeScript("arguments[0].click();", ele);
			}
			if (xl_inform_ben.equalsIgnoreCase("no")) {
				WebElement ele = regpage
						.waitForElementToPresent(By
								.xpath("//input[@id='benInsMsisdnYesNo'][@value='no']"));
				((JavascriptExecutor) MIP_Test_Configuration.driver)
						.executeScript("arguments[0].click();", ele);

			}
			regpage.clickOnSave();
			regpage.confirmPopUp("yes");
			regpage.validateCustomerDetails(msisdn, fname, sname, dob, age,
					gender);
			regpage.validateRelativeInfo(msisdn, MIP_CustomerManagementPage.IP,
					nominee_fname, nominee_sname, "", nominee_age, "",
					nominee_mobilenum);
			Assert.assertNotEquals(MIP_RegisterCustomer_Queries.getSMSText(
					msisdn, MIP_CustomerManagementPage.IP), "");
			if (!nominee_mobilenum.equals(""))
				Assert.assertNotEquals(MIP_RegisterCustomer_Queries
						.getBenSMSText(nominee_mobilenum), "");
			Assert.assertTrue(MIP_RegisterCustomer_Queries.getCoverDetailsIP(
					msisdn).equalsIgnoreCase(offer_cover));
			regpage.enterMSISDN(msisdn).clickOnSearchButton();
			Assert.assertTrue(regpage.getSuccessMsg().trim()
					.equalsIgnoreCase(errmsg.trim()));
			Assert.assertTrue(MIP_RegisterCustomer_Queries
					.getdeRegCustInfoforHP(msisdn));
			Assert.assertTrue(MIP_RegisterCustomer_Queries
					.getdeRegBenInfoforHP(msisdn));
			Map<String, String> cancel_details = MIP_RegisterCustomer_Queries
					.getCustInfoforHPinCancellation(msisdn);
			Assert.assertTrue(cancel_details.get("fname").trim()
					.equalsIgnoreCase(fname.trim()));
			Assert.assertTrue(cancel_details.get("sname").trim()
					.equalsIgnoreCase(sname.trim()));
			if (!age.equals(""))
				Assert.assertTrue(cancel_details.get("age").trim()
						.equalsIgnoreCase(age.trim()));
			if (!dob.equals(""))
				Assert.assertTrue(cancel_details
						.get("dob")
						.trim()
						.equalsIgnoreCase(
								MIP_DateFunctionality.converDateToDBDateFormat(
										dob).trim()));
			Assert.assertTrue(cancel_details.get("gender").trim()
					.equalsIgnoreCase(gender.charAt(0) + "".trim()));
			Assert.assertTrue(cancel_details.get("hpirfname").trim()
					.equalsIgnoreCase(hp_fname.trim()));
			Assert.assertTrue(cancel_details.get("hpirsname").trim()
					.equalsIgnoreCase(hp_sname.trim()));
			if (!hp_dob.equals(""))
				Assert.assertTrue(cancel_details
						.get("hpirdob")
						.trim()
						.equalsIgnoreCase(
								MIP_DateFunctionality.converDateToDBDateFormat(
										hp_dob).trim()));
			if (!hp_age.equals(""))
				Assert.assertTrue(cancel_details.get("hpirage").trim()
						.equalsIgnoreCase(hp_age.trim()));
			Assert.assertTrue(cancel_details.get("hpcust_relationship").trim()
					.equalsIgnoreCase(hp_relation.trim()));
			Assert.assertNotEquals(
					MIP_RegisterCustomer_Queries.getDeRegisterHPSMSText(msisdn),
					"");
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.REGISTER_CUSTOMER);
			log.info("RegisterAllProduct test case Failed");
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
			homepage.clickOnMenu(MIP_Menu_Constants.CUSTOMER);
		report.endTest(logger);
		report.flush();
	}
}
