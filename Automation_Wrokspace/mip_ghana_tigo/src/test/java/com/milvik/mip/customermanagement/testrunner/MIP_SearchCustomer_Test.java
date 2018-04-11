package com.milvik.mip.customermanagement.testrunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.milvik.mip.dataprovider.MIP_SearchCustomer_TestData;
import com.milvik.mip.dbqueries.MIP_RegisterCustomer_Queries;
import com.milvik.mip.dbqueries.MIP_SearchCustomer_Queries;
import com.milvik.mip.pageobjects.MIP_HomePage;
import com.milvik.mip.pageobjects.MIP_LoginPage;
import com.milvik.mip.pageobjects.MIP_RegisterCustomerPage;
import com.milvik.mip.pageobjects.MIP_SearchAndModifyPage;
import com.milvik.mip.pageobjects.MIP_SearchCustomerPage;
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

public class MIP_SearchCustomer_Test {
	ExtentReports report;
	ExtentTest logger;
	static Logger log;

	MIP_LoginPage loginpage = null;
	MIP_HomePage homepage = null;

	@BeforeTest
	@Parameters({ "flag", "browser" })
	public void test_setup(@Optional("firefox") String browser,
			@Optional("0") String flag) {
		log = MIP_Logging.logDetails("MIP_Search_Customer_Test");
		report = new ExtentReports(".\\Test_Reports\\SearchCustomer_Test.html");
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
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
		} else {
			homepage = new MIP_HomePage(MIP_Test_Configuration.driver);
			/*try {
				if (!homepage.waitForElementToVisible(
						By.linkText(MIP_Menu_Constants.SEARCH_CUSTOMER))
						.isDisplayed()) {*/
					homepage.clickOnMenu(MIP_Menu_Constants.CUSTOMER);
					homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			/*	}
			} catch (Exception e) {*/
			/*	homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			}*/
		}
	}

	@Test(testName = "TC610 to TC615", dataProvider = "SearchCustomerNegative", dataProviderClass = MIP_SearchCustomer_TestData.class)
	public void searchCustOne(String testcase, String fname, String sname,
			String msisdn, String cust_id, String errmsg) throws Throwable {
		MIP_SearchCustomerPage searchpage = null;
		try {
			logger = report.startTest("Search Customer-TC150 to TC152");
			log.info("Running test case -" + testcase);
			try {
				homepage.waitForElementToVisible(By
						.xpath("//h3[contains(text(),'Search Customer')]"));
			} catch (Exception e) {
				homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			}
			searchpage = new MIP_SearchCustomerPage(
					MIP_Test_Configuration.driver);
			searchpage.waitForElementToVisible(By.id("msisdn"));
			searchpage.enterMSISDN(msisdn).enterCustomerFName(fname)
					.enterCustomerSName(sname).enterMSISDN(msisdn)
					.enterCust_id(cust_id).clickOnSearch();
			Assert.assertTrue(searchpage.getValidationMsg().trim()
					.replaceAll("\\s", "")
					.equalsIgnoreCase(errmsg.trim().replaceAll("\\s", "")));
			searchpage.clickOnClear();
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			log.info("Testcase " + testcase + " Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@Test(testName = "TC154", dataProvider = "searchCriteria", dataProviderClass = MIP_SearchCustomer_TestData.class)
	public void searchCustTwo(String fname, String sname, String msisdn,
			String cust_id) throws Throwable {
		MIP_SearchCustomerPage searchpage = null;
		try {
			logger = report.startTest("Search Customer-TC154");
			log.info("Running test case -TC154");
			try {
				homepage.waitForElementToVisible(By
						.xpath("//h3[contains(text(),'Search Customer')]"));
			} catch (Exception e) {
				homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			}
			searchpage = new MIP_SearchCustomerPage(
					MIP_Test_Configuration.driver);
			Assert.assertTrue(searchpage.validateSearchCustObjects());
			searchpage.enterMSISDN(msisdn).enterCustomerFName(fname)
					.enterCustomerSName(sname).enterMSISDN(msisdn)
					.enterCust_id(cust_id).clickOnClear();
			Assert.assertTrue(searchpage.getFname().equals(""));
			Assert.assertTrue(searchpage.getSname().equals(""));
			Assert.assertTrue(searchpage.getmsisdn().equals(""));
			Assert.assertTrue(searchpage.getcustId().equals(""));
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			log.info("Testcase TC154 is  Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@Test(testName = "SearchbyallSearchCrteria", dataProvider = "searchCriteria", dataProviderClass = MIP_SearchCustomer_TestData.class)
	public void searchCustThree(String fname, String sname, String msisdn,
			String cust_id) throws Throwable {
		MIP_SearchCustomerPage searchpage = null;
		try {
			logger = report.startTest("Search Customer-TC155-TC153");
			log.info("Running test case -SearchbyallSearchCrteria");

			try {
				homepage.waitForElementToVisible(By
						.xpath("//h3[contains(text(),'Search Customer')]"));
			} catch (Exception e) {
				homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			}
			searchpage = new MIP_SearchCustomerPage(
					MIP_Test_Configuration.driver);
			searchpage.waitForElementToVisible(By.id("msisdn"));
			searchpage.enterCustomerFName(fname).clickOnSearch();
			Assert.assertTrue(searchpage.validateSearchTableContent());
			Assert.assertTrue(searchpage.ValidateCustomerName(fname));
			homepage.clickOnMenu(MIP_Menu_Constants.CUSTOMER);
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			searchpage.enterCustomerSName(sname).clickOnSearch();
			Assert.assertTrue(searchpage.ValidateCustomerName(sname));
			homepage.clickOnMenu(MIP_Menu_Constants.CUSTOMER);
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			searchpage.enterMSISDN(msisdn).clickOnSearch();
			Assert.assertEquals(MIP_SearchCustomer_Queries
					.countCustomerRecordByMSISDN(msisdn), searchpage
					.ValidateMSISDNSearch(msisdn));
			homepage.clickOnMenu(MIP_Menu_Constants.CUSTOMER);
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			searchpage.enterCust_id(cust_id).clickOnSearch();
			Assert.assertEquals(MIP_SearchCustomer_Queries
					.countCustomerRecordByCustId(cust_id), searchpage
					.ValidateMSISDNSearch(msisdn));
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			log.info("Testcase TC155-TC153 is  Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	@Test(testName = "searchCustValidateDetails", dataProvider = "searchAndValidateCust", dataProviderClass = MIP_SearchCustomer_TestData.class)
	public void searchCustFour(String msisdn) throws Throwable {
		MIP_SearchCustomerPage searchpage = null;
		try {
			logger = report
					.startTest("Search Customer-searchCustValidateDetails");
			log.info("Running test case -searchCustValidateDetails");
			String date = "";
			try {
				homepage.waitForElementToVisible(By
						.xpath("//h3[contains(text(),'Search Customer')]"));
			} catch (Exception e) {
				homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			}
			searchpage = new MIP_SearchCustomerPage(
					MIP_Test_Configuration.driver);
			Map<String, String> cust_details = MIP_RegisterCustomer_Queries
					.getCustomerDetails(msisdn);
			searchpage.waitForElementToVisible(By.id("msisdn"));
			searchpage.enterMSISDN(msisdn).clickOnSearch();
			Assert.assertEquals(MIP_SearchCustomer_Queries
					.countCustomerRecordByMSISDN(msisdn), searchpage
					.ValidateMSISDNSearch(msisdn));
			List<String> product_name = searchpage.getOfferName();
			for (String product : product_name) {
				Map<String, String> search_result = MIP_SearchCustomer_Queries
						.getCustomerSearchData(msisdn, product);
				Assert.assertTrue(search_result.get("customer_name")
						.equalsIgnoreCase(searchpage.getCustomerName(product)));
				Assert.assertTrue(search_result.get("registered_by")
						.equalsIgnoreCase(searchpage.getRegisteredBy(product)));
				date = searchpage.getRegisteredDate(product);
				if (!date.equals("")) {
					String[] date_format = date.split("\\s");
					String new_format = MIP_DateFunctionality
							.converDateToDBDateFormat(date_format[0]);
					date = new_format + date_format[1];
				}
				Assert.assertTrue(search_result.get("reg_date")
						.replaceAll("\\s", "")
						.equalsIgnoreCase(date.replaceAll("\\s", "")));
				date = searchpage.getConfDate(product);
				if (!date.equals("")) {
					String[] date_format = date.split("\\s");
					String new_format = MIP_DateFunctionality
							.converDateToDBDateFormat(date_format[0]);
					date = new_format + date_format[1];
				}
				Assert.assertTrue(search_result.get("conf_date")
						.replaceAll("\\s", "")
						.equalsIgnoreCase(date.replaceAll("\\s", "")));
				date = searchpage.getDeRegisteredDate(product);
				if (!date.equals("N/A")) {
					String[] date_format = date.split("\\s");
					String new_format = MIP_DateFunctionality
							.converDateToDBDateFormat(date_format[0]);
					date = new_format + date_format[1];
				}
				Assert.assertTrue(search_result.get("deRegisteredDate")
						.replaceAll("\\s", "")
						.equalsIgnoreCase(date.replaceAll("\\s", "")));
				Assert.assertTrue(search_result.get("Status").equalsIgnoreCase(
						searchpage.getStatus(product)));
				searchpage.clickOnCustomerNameLink(product);

				Assert.assertTrue(cust_details.get("fname").equalsIgnoreCase(
						searchpage.getFname()));
				Assert.assertTrue(cust_details.get("sname").equalsIgnoreCase(
						searchpage.getSname()));
				Assert.assertTrue(cust_details.get("dob").equalsIgnoreCase(
						searchpage.getdob()));
				Assert.assertTrue(cust_details.get("age").equalsIgnoreCase(
						searchpage.getage()));
				Assert.assertTrue(cust_details.get("implied_age")
						.equalsIgnoreCase(searchpage.getimpliedAge()));
				Assert.assertTrue(cust_details.get("gender").equalsIgnoreCase(
						searchpage.getgender()));
				Map<String, String> rel_info = new HashMap<String, String>();
				Map<String, String> rel_details = new HashMap<String, String>();
				if (search_result.get("Status").equalsIgnoreCase(
						"De-registered")) {
					rel_details = MIP_SearchCustomer_Queries
							.getDeactivatedRelativeInfo(msisdn, product);
				} else {
					rel_details = MIP_RegisterCustomer_Queries.getRelativeInfo(
							msisdn, product);

				}

				if (product
						.equalsIgnoreCase(MIP_CustomerManagementPage.HOSPITAL)) {
					rel_info = searchpage.getHPRelativeInfoInViewCusDetails();
					rel_info.put("dob", rel_info.get("dob").split("\\.")[0]);
				} else if (product
						.equalsIgnoreCase(MIP_CustomerManagementPage.XTRALIFE)) {
					rel_info = searchpage
							.getXtralifeRelativeInfoInViewCusDetails();
					rel_info.put("dob", rel_info.get("dob").split("\\.")[0]);

				} else if (product
						.equalsIgnoreCase(MIP_CustomerManagementPage.IP)) {
					rel_info = searchpage.getIPRelativeInfoInViewCusDetails();
					rel_details.remove("dob");
					rel_details.remove("cust_relationship");
				}

				Assert.assertTrue(rel_details.equals(rel_info));

				searchpage.clickOnBack().enterMSISDN(msisdn).clickOnSearch();

			}
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);

		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			log.info("Testcase searchCustValidateDetails is  Failed");
			log.info("Error occured in the test case", t);
			throw t;
		}
	}

	// Register and search customer and validate the changes performed.
	@Test(enabled = true, priority = 10, testName = "TestModificationInSearch", dataProvider = "changeDetailsTest", dataProviderClass = MIP_SearchCustomer_TestData.class)
	public void searchCustFive(String testcase, String msisdn, String product,
			String fname, String sname, String dob, String age, String gender,
			String xl_relation, String xl_rel_fname, String xl_rel_sname,
			String xl_rel_dob, String xl_age, String xl_inform_ben,
			String xl_rel_msisdn, String hp_relation, String hp_fname,
			String hp_sname, String hp_dob, String hp_age,
			String inform_ben_hp, String hp_rel_mobilenum, String offer_cover,
			String nominee_fname, String nominee_sname, String nominee_age,
			String inform_nominee, String nominee_mobilenum,
			String modify_fname, String modify_sname, String modify_age,
			String modify_DOB, String modify_gender, String modify_XL_fname,
			String modify_XL_sname, String modify_XL_Age, String modify_Xl_DOB,
			String modify_XL_mobilenum, String modify_XL_Relation,
			String modify_IP_fname, String modify_IP_sname,
			String modify_IP_Age, String modify_IP_mobilenum,
			String modify_HP_fname, String modify_HP_sname,
			String modify_HP_age, String modify_HP_DOB,
			String modify_HP_msisdn, String modify_HP_Relation)
			throws Throwable {
		MIP_RegisterCustomerPage regpage = null;
		try {
			logger = report
					.startTest("Search Customer-TestModificationInSearch "
							+ testcase);

			homepage.clickOnMenu(MIP_Menu_Constants.REGISTER_CUSTOMER);
			regpage = new MIP_RegisterCustomerPage(
					MIP_Test_Configuration.driver);
			homepage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'Register Customer')]"));
			homepage.waitForElementToVisible(By.id("msisdn"));
			regpage.enterMSISDN(msisdn).clickOnSearchButton();
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

			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			homepage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'Search Customer')]"));
			MIP_SearchCustomerPage searchpage = PageFactory
					.initElements(MIP_Test_Configuration.driver,
							MIP_SearchCustomerPage.class);
			searchpage.enterMSISDN(msisdn);
			searchpage.clickOnSearch();
			searchpage.clickOnCustomerNameLink(product);
			Assert.assertTrue(searchpage.validateNoChangesPerformed());
			searchpage.clickOnBack();

			homepage.clickOnMenu(MIP_Menu_Constants.CLAIMS);
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CLAIMS);
			MIP_SearchAndModifyPage searchclaim = PageFactory.initElements(
					MIP_Test_Configuration.driver,
					MIP_SearchAndModifyPage.class);
			searchclaim
					.waitForElementToVisible(By
							.xpath("//*[contains(text(),'Search and Modify Customer')]"));
			searchclaim.enterMsisdn(msisdn).clickOnSearchIcon();
			searchclaim.clickOnCustomerNameLink();
			searchclaim.clearCustomerInformation(modify_fname, modify_sname,
					modify_age, modify_DOB).enterCustomerInformation(
					modify_fname, modify_sname, modify_age, modify_DOB,
					modify_gender);

			if (product.equalsIgnoreCase(MIP_CustomerManagementPage.XTRALIFE)) {
				searchclaim
						.clearInsuredRelativeInformation(modify_XL_fname,
								modify_XL_sname, modify_XL_Age,
								modify_XL_mobilenum)
						.enterInsuredRelativeInformation(modify_XL_Relation,
								modify_XL_fname, modify_XL_sname,
								modify_XL_Age, modify_Xl_DOB,
								modify_XL_mobilenum).informBenOption()
						.clickOnSave().confirmPopUp("yes");

			}

			if (product.equalsIgnoreCase(MIP_CustomerManagementPage.IP)) {

				searchclaim
						.clearNomineeInformation(modify_IP_fname,
								modify_IP_sname, modify_IP_Age,
								modify_IP_mobilenum)
						.enterNomineeInformation(modify_IP_fname,
								modify_IP_sname, modify_IP_Age,
								modify_IP_mobilenum).informBenOption()
						.clickOnSave().confirmPopUp("yes");

			}

			if (product.equalsIgnoreCase(MIP_CustomerManagementPage.HOSPITAL)) {

				searchclaim
						.clearInsuredRelativeInformationHP(modify_HP_Relation,
								modify_HP_fname, modify_HP_sname,
								modify_HP_age, modify_HP_DOB, modify_HP_msisdn)
						.enterRelativeInformationHP(modify_HP_Relation,
								modify_HP_fname, modify_HP_sname,
								modify_HP_age, modify_HP_DOB, modify_HP_msisdn)
						.informBenOption().clickOnSave().confirmPopUp("yes");

			}
			homepage.clickOnMenu(MIP_Menu_Constants.CUSTOMER);
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			homepage.waitForElementToVisible(By
					.xpath("//h3[contains(text(),'Search Customer')]"));
			searchpage = PageFactory
					.initElements(MIP_Test_Configuration.driver,
							MIP_SearchCustomerPage.class);
			searchpage.enterMSISDN(msisdn);
			searchpage.clickOnSearch();
			searchpage.clickOnCustomerNameLink(product);
			Assert.assertTrue(searchpage.validateChageDeatilsTableHeading());
			Map<String, String> change_details = searchpage.getChangedDetails();
			// List<String> input_details = new ArrayList<String>();
			if (!modify_fname.equals("")) {
				Assert.assertTrue(modify_fname.trim().equalsIgnoreCase(
						change_details.get("Customer new First name").trim()));
				Assert.assertTrue(fname.trim().equalsIgnoreCase(
						change_details.get("Customer old First name").trim()));
			} else {
				Assert.assertTrue(change_details.get("Customer new First name")
						.equalsIgnoreCase(""));
				Assert.assertTrue(change_details.get("Customer old First name")
						.equalsIgnoreCase(""));
			}
			if (!modify_sname.equals("")) {
				Assert.assertTrue(modify_sname.trim().equalsIgnoreCase(
						change_details.get("Customer new Last name").trim()));
				Assert.assertTrue(sname.trim().equalsIgnoreCase(
						change_details.get("Customer old Last name").trim()));
			} else {
				Assert.assertTrue(change_details.get("Customer new Last name")
						.equalsIgnoreCase(""));
				Assert.assertTrue(change_details.get("Customer old Last name")
						.equalsIgnoreCase(""));
			}
			if (!modify_DOB.equals("")) {
				Assert.assertTrue(MIP_DateFunctionality
						.converDateToDBDateFormat(modify_DOB).equals(
								change_details
										.get("Customer new Date of Birth")));
				Assert.assertTrue(MIP_DateFunctionality
						.converDateToDBDateFormat(dob).equals(
								change_details
										.get("Customer old Date of Birth")));

			} else {
				Assert.assertTrue(change_details.get(
						"Customer new Date of Birth").equals(""));
				Assert.assertTrue(change_details.get(
						"Customer old Date of Birth").equals(""));
			}
			if (!modify_age.equals("")) {
				Assert.assertTrue(modify_age.equals(change_details
						.get("Customer new Age")));
				Assert.assertTrue(age.equals(change_details
						.get("Customer old Age")));
			}
			if (!modify_gender.equals("")
					&& !(modify_gender.equalsIgnoreCase(gender))) {
				Assert.assertTrue((modify_gender.charAt(0) + "")
						.equalsIgnoreCase(change_details
								.get("Customer New Gender")));
				Assert.assertTrue((gender.charAt(0) + "")
						.equalsIgnoreCase(change_details
								.get("Customer Old Gender")));

			} else {
				Assert.assertTrue(change_details.get("Customer New Gender")
						.equals(""));
				Assert.assertTrue(change_details.get("Customer Old Gender")
						.equals(""));
			}
			if (!modify_XL_fname.equals("")) {
				Assert.assertTrue(modify_XL_fname.trim().equalsIgnoreCase(
						change_details.get("New Xl Beneficiary First Name")
								.trim()));
				Assert.assertTrue(xl_rel_fname.trim().equalsIgnoreCase(
						change_details.get("Old Xl Beneficiary First Name")
								.trim()));
			} else {
				Assert.assertTrue(change_details.get(
						"New Xl Beneficiary First Name").equals(""));
				Assert.assertTrue(change_details.get(
						"Old Xl Beneficiary First Name").equals(""));
			}
			if (!modify_XL_sname.equals("")) {
				Assert.assertTrue(modify_XL_sname.trim().equalsIgnoreCase(
						change_details.get("New Xl Beneficiary Last Name")
								.trim()));
				Assert.assertTrue(xl_rel_sname.trim().equalsIgnoreCase(
						change_details.get("Old Xl Beneficiary Last Name")
								.trim()));
			} else {
				change_details.get("New Xl Beneficiary Last Name").equals("");
				change_details.get("Old Xl Beneficiary Last Name").equals("");
			}
			if (!modify_Xl_DOB.equals("")) {

				Assert.assertTrue(MIP_DateFunctionality
						.converDateToDBDateFormat(modify_Xl_DOB)
						.equals(change_details
								.get("New Xl Beneficiary Date of Birth")));
				Assert.assertTrue(MIP_DateFunctionality
						.converDateToDBDateFormat(xl_rel_dob)
						.equals(change_details
								.get("Old Xl Beneficiary Date of Birth")));
			} else {
				Assert.assertTrue(change_details.get(
						"New Xl Beneficiary Date of Birth").equals(""));
				Assert.assertTrue(change_details.get(
						"Old Xl Beneficiary Date of Birth").equals(""));
			}
			if (!modify_XL_Age.equals("")) {
				Assert.assertTrue(modify_XL_Age.equals(change_details
						.get("New Xl Beneficiary Age")));
				Assert.assertTrue(xl_age.equals(change_details
						.get("Old Xl Beneficiary Age")));
			}
			if (!modify_XL_mobilenum.equals("")) {
				System.out.println(change_details
						.get("New Xl Beneficiary Mobile Number"));

				System.out.println(change_details.get(
						"New Xl Beneficiary Mobile Number").equals(""));
				Assert.assertTrue(modify_XL_mobilenum.equals(change_details
						.get("New Xl Beneficiary Mobile Number")));
				Assert.assertTrue(xl_rel_msisdn.equals(change_details
						.get("Old Xl Beneficiary Mobile Number")));
			} else {
				Assert.assertTrue(change_details.get(
						"New Xl Beneficiary Mobile Number").equals(""));
			}
			if (!modify_XL_Relation.equals("")) {
				Assert.assertTrue(modify_XL_Relation
						.equalsIgnoreCase(change_details
								.get("New Xl Beneficiary Relationship")));
				Assert.assertTrue(xl_relation.equalsIgnoreCase(change_details
						.get("Old XL Beneficiary Relationship")));
			} else {
				Assert.assertTrue(change_details.get(
						"New Xl Beneficiary Relationship").equals(""));
				Assert.assertTrue(change_details.get(
						"Old XL Beneficiary Relationship").equals(""));
			}
			if (!modify_IP_fname.equals("")) {
				Assert.assertTrue(modify_IP_fname.trim().equalsIgnoreCase(
						change_details.get("New IP Beneficiary First Name")
								.trim()));
				Assert.assertTrue(nominee_fname.trim().equalsIgnoreCase(
						change_details.get("Old IP Beneficiary First Name")
								.trim()));
			} else {
				Assert.assertTrue(change_details.get(
						"New IP Beneficiary First Name").equals(""));
				Assert.assertTrue(change_details.get(
						"Old IP Beneficiary First Name").equals(""));
			}
			if (!modify_IP_sname.equals("")) {
				Assert.assertTrue(modify_IP_sname.trim().equalsIgnoreCase(
						change_details.get("New IP Beneficiary Last Name")
								.trim()));
				Assert.assertTrue(nominee_sname.trim().equalsIgnoreCase(
						change_details.get("Old IP Beneficiary Last Name")
								.trim()));
			} else {
				Assert.assertTrue(change_details.get(
						"New IP Beneficiary Last Name").equals(""));
				Assert.assertTrue(change_details.get(
						"Old IP Beneficiary Last Name").equals(""));
			}
			if (!modify_IP_Age.equals("")) {

				Assert.assertTrue(modify_IP_Age.equals(change_details
						.get("New IP Beneficiary Age")));
				Assert.assertTrue(nominee_age.equals(change_details
						.get("Old IP Beneficiary Age")));
			}
			if (!modify_IP_mobilenum.equals("")) {
				Assert.assertTrue(modify_IP_mobilenum.equals(change_details
						.get("New IP Beneficiary Mobile Number")));
				Assert.assertTrue(nominee_mobilenum.equals(change_details
						.get("Old IP Beneficiary Mobile Number")));
			} else {
				Assert.assertTrue(change_details.get(
						"New IP Beneficiary Mobile Number").equals(""));
				Assert.assertTrue(change_details.get(
						"Old IP Beneficiary Mobile Number").equals(""));
			}
			if (!modify_HP_fname.equals("")) {
				Assert.assertTrue(modify_HP_fname.trim().equalsIgnoreCase(
						change_details.get("New HP Beneficiary First Name")
								.trim()));
				Assert.assertTrue(hp_fname.trim().equalsIgnoreCase(
						change_details.get("Old HP Beneficiary First Name")
								.trim()));
			} else {
				Assert.assertTrue(change_details.get(
						"New HP Beneficiary First Name").equals(""));
				Assert.assertTrue(change_details.get(
						"Old HP Beneficiary First Name").equals(""));
			}
			if (!modify_HP_sname.equals("")) {
				Assert.assertTrue(modify_HP_sname.trim().equalsIgnoreCase(
						change_details.get("New HP Beneficiary Last Name")
								.trim()));
				Assert.assertTrue(hp_sname.trim().equalsIgnoreCase(
						change_details.get("Old HP Beneficiary Last Name")
								.trim()));
			} else {
				Assert.assertTrue(change_details.get(
						"New HP Beneficiary Last Name").equals(""));
				Assert.assertTrue(change_details.get(
						"Old HP Beneficiary Last Name").equals(""));
			}
			if (!modify_HP_DOB.equals("")) {

				Assert.assertTrue(MIP_DateFunctionality
						.converDateToDBDateFormat(modify_HP_DOB)
						.equals(change_details
								.get("New HP Beneficiary Date of Birth")));
				Assert.assertTrue(MIP_DateFunctionality
						.converDateToDBDateFormat(hp_dob)
						.equals(change_details
								.get("Old HP Beneficiary Date of Birth")));
			} else {
				Assert.assertTrue(change_details.get(
						"New HP Beneficiary Date of Birth").equals(""));
				Assert.assertTrue(change_details.get(
						"Old HP Beneficiary Date of Birth").equals(""));
			}
			if (!modify_HP_age.equals("")) {
				Assert.assertTrue(modify_HP_age.equals(change_details
						.get("New HP Beneficiary Age")));
				Assert.assertTrue(hp_age.equals(change_details
						.get("Old HP Beneficiary Age")));
			}
			if (!modify_HP_msisdn.equals("")) {
				Assert.assertTrue(modify_HP_msisdn.equals(change_details
						.get("New HP Beneficiary Mobile Number")));
				Assert.assertTrue(hp_rel_mobilenum.equals(change_details
						.get("Old HP Beneficiary Mobile Number")));
			} else {
				Assert.assertTrue(change_details.get(
						"New HP Beneficiary Mobile Number").equals(""));
				Assert.assertTrue(change_details.get(
						"Old HP Beneficiary Mobile Number").equals(""));
			}
			if (!modify_HP_Relation.equals("")) {
				Assert.assertTrue(modify_HP_Relation
						.equalsIgnoreCase(change_details
								.get("New HP Beneficiary Relationship")));
				Assert.assertTrue(hp_relation.equalsIgnoreCase(change_details
						.get("Old HP Beneficiary Relationship")));
			} else {
				Assert.assertTrue(change_details.get(
						"New HP Beneficiary Relationship").equals(""));
				Assert.assertTrue(change_details.get(
						"Old HP Beneficiary Relationship").equals(""));
			}
			searchpage.clickOnBack();
		} catch (Throwable t) {
			homepage.clickOnMenu(MIP_Menu_Constants.SEARCH_CUSTOMER);
			log.info("Testcase TestModificationInSearch " + testcase
					+ "  Failed");
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
