package com.milvik.mip.pageutil;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.milvik.mip.dbqueries.MIP_RegisterCustomer_Queries;
import com.milvik.mip.utility.MIP_DateFunctionality;
import com.milvik.mip.utility.MIP_Logging;

public class MIP_CustomerManagementPage extends MIP_BasePage {
	public static final String XTRALIFE = "Xtra-Life";
	public static final String HOSPITAL = "Hospitalization";
	public static final String IP = "Income Protection";
	public static final String IP_COVER_ONE = "GHC 5 - GHC 3,000 & 45/night";
	public static final String IP_COVER_TWO = "GHC 7 - GHC 4,000 & 60/night";
	public static final String IP_COVER_THREE = "GHC 9 - GHC 5,000 & 80/night";

	WebDriver driver;
	static Logger logger;
	static {
		logger = MIP_Logging.logDetails("MIP_CustomerManagementPage");
	}

	public MIP_CustomerManagementPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	@FindBy(id = "msisdn")
	public WebElement mobileNum;
	@FindBy(id = "search-icon")
	public WebElement searchIcon;

	public boolean validateBackBtn() {
		return this.waitForElementToVisible(By.id("backBtn")).isDisplayed();
	}

	public void clickOnBackBtn() {
		this.waitForElementToVisible(By.id("backBtn")).click();
	}

	public void enterMsisdn(String msisdn) {
		this.mobileNum.sendKeys(msisdn);
	}

	public String getValidationMessage() {

		return this.waitForElementToVisible(By.id("validationMessages"))
				.getText();
	}

	public void confirmCustManagementPopup(String option) {
		this.confirmPopUp(option);
	}

	public String getSuccessMessage() {
		WebDriverWait w = new WebDriverWait(driver, 50);
		return w.until(
				ExpectedConditions.visibilityOfElementLocated(By
						.id("message_div"))).getText();
	}

	public void validateCustomerDetails(String msisdn, String fname,
			String sname, String dob, String age, String gender) {
		Map<String, String> cust_details = MIP_RegisterCustomer_Queries
				.getCustomerDetails(msisdn);
		Assert.assertTrue(cust_details.get("fname").trim()
				.equalsIgnoreCase(fname.trim()));
		Assert.assertTrue(cust_details.get("sname".trim()).equalsIgnoreCase(
				sname.trim()));
		if (!dob.equals("")) {
			Assert.assertTrue(cust_details.get("dob").equalsIgnoreCase(
					MIP_DateFunctionality.converDateToDBDateFormat(dob)));
		}
		if (!age.equals("")) {

			Assert.assertTrue(cust_details.get("age").equalsIgnoreCase(age));
		}
		Assert.assertTrue(cust_details.get("gender").trim()
				.equalsIgnoreCase(gender.trim()));

	}

	public void validateRelativeInfo(String msisdn, String product_name,
			String fname, String sname, String dob, String age,
			String relationship, String rel_msisdn) {
		Map<String, String> rel_details = MIP_RegisterCustomer_Queries
				.getRelativeInfo(msisdn, product_name);
		Assert.assertTrue(rel_details.get("fname").trim()
				.equalsIgnoreCase(fname.trim()));
		Assert.assertTrue(rel_details.get("sname").trim()
				.equalsIgnoreCase(sname.trim()));
		if (!dob.equals("")) {
			Assert.assertTrue(rel_details.get("dob").equals(
					MIP_DateFunctionality.converDateToDBDateFormat(dob)));
		}
		if (!age.equals("")) {
			Assert.assertTrue(rel_details.get("age").equalsIgnoreCase(age));
		}
		if (!relationship.equals("")) {
			Assert.assertTrue(rel_details.get("cust_relationship")
					.equalsIgnoreCase(relationship));
		}
		if (!rel_msisdn.equals("")) {
			Assert.assertTrue(rel_details.get("msisdn").equalsIgnoreCase(
					rel_msisdn));
		}

	}
}
