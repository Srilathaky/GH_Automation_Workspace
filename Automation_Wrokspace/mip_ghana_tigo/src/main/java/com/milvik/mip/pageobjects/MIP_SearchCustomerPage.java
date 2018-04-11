package com.milvik.mip.pageobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.milvik.mip.pageutil.MIP_BasePage;
import com.milvik.mip.pageutil.MIP_CustomerManagementPage;
import com.milvik.mip.utility.MIP_DateFunctionality;
import com.milvik.mip.utility.MIP_Logging;

public class MIP_SearchCustomerPage extends MIP_BasePage {
	WebDriver driver;
	static Logger logger;
	static {
		logger = MIP_Logging.logDetails("MIP_SearchCustomerPage");
	}

	public MIP_SearchCustomerPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public MIP_SearchCustomerPage enterMSISDN(String msisdn) {
		try {
			logger.info("Entering the customer MSISDN");
			if (!msisdn.equals(""))
				this.enterText(this.waitForElementToVisible(By.id("msisdn")),
						msisdn);
		} catch (Exception e) {
			logger.error("Error while entering the MSISDN", e);
		}
		return this;
	}

	public MIP_SearchCustomerPage enterCustomerFName(String name) {
		try {
			logger.info("Entering the customer First Name");
			if (!name.equals(""))
				this.enterText(this.waitForElementToVisible(By.id("fname")),
						name);
		} catch (Exception e) {
			logger.error("Error while entering the Customer first Name", e);
		}
		return this;
	}

	public MIP_SearchCustomerPage enterCustomerSName(String name) {
		try {
			logger.info("Entering the customer Sur Name");
			if (!name.equals(""))
				this.enterText(this.waitForElementToVisible(By.id("sname")),
						name);
		} catch (Exception e) {
			logger.error("Error while entering the Customer Sur  Name", e);
		}
		return this;
	}

	public MIP_SearchCustomerPage enterCust_id(String cust_id) {
		try {
			logger.info("Entering the customer id");
			if (!cust_id.equals(""))
				this.enterText(this.waitForElementToVisible(By.id("custId")),
						cust_id);
		} catch (Exception e) {
			logger.error("Error while entering the Customer id", e);
		}
		return this;
	}

	public MIP_SearchCustomerPage clickOnSearch() {
		try {
			logger.info("Clicking on search button");
			this.waitForElementToVisible(By.id("searchBtn")).click();
		} catch (Exception e) {
			logger.error("Error while clicking on Search button", e);
		}
		return this;
	}

	public MIP_SearchCustomerPage clickOnClear() {
		this.clickOnElement(By.id("clearBtn"));
		return this;
	}

	public MIP_SearchCustomerPage clickOnBack() {
		this.clickOnElement(By.id("backBtn"));
		return this;
	}

	public String getFname() {
		return this.waitForElementToVisible(By.id("fname")).getAttribute(
				"value");
	}

	public String getSname() {
		return this.waitForElementToVisible(By.id("sname")).getAttribute(
				"value");
	}

	public String getmsisdn() {
		return this.waitForElementToVisible(By.id("msisdn")).getAttribute(
				"value");
	}

	public String getcustId() {
		return this.waitForElementToVisible(By.id("custId")).getAttribute(
				"value");
	}

	public String getdob() {
		String dob = this.waitForElementToVisible(By.id("dob")).getAttribute(
				"value");
		if (dob.contains("/")) {
			dob = MIP_DateFunctionality.converDateToDBDateFormat(dob);
		}
		return dob;
	}

	public String getgender() {
		if (this.waitForElementToVisible(
				By.xpath("//input[@id='gender'][@value='M']")).isSelected()) {
			return "Male";
		} else {
			return "Female";
		}
	}

	public String getimpliedAge() {
		return this.waitForElementToVisible(
				By.xpath("//div[@id='implied_age_div']//div/input[@id='age']"))
				.getAttribute("value");
	}

	public String getage() {
		return this.waitForElementToVisible(By.id("age")).getAttribute("value");
	}

	public String getValidationMsg() {
		logger.info("Getting Validation message in Customer registration page");
		return this.waitForElementToVisible(By.id("validationMessages"))
				.getText();
	}

	public String getSuccessMsg() {
		return this.waitForElementToVisible(
				By.xpath("//div[@id='validationMessages']//li")).getText();
	}

	public boolean validateSearchCustObjects() {
		if (this.waitForElementToVisible(By.id("fname")).isDisplayed()
				&& this.waitForElementToVisible(By.id("msisdn")).isDisplayed()
				&& this.waitForElementToVisible(By.id("sname")).isDisplayed()
				&& this.waitForElementToVisible(By.id("custId")).isDisplayed()
				&& this.waitForElementToVisible(By.id("searchBtn"))
						.isDisplayed()
				&& this.waitForElementToVisible(By.id("clearBtn"))
						.isDisplayed()) {
			return true;
		}
		return false;

	}

	public boolean validateSearchTableContent() {
		this.waitForElementToVisible(By.id("customerDetailsList"));
		String _xpath = "//table[@id='customerDetailsList']/thead/tr/th[contains(text(),";
		if (this.waitForElementToVisible(By.xpath(_xpath + "'Offer Name')]"))
				.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath("//table[@id='customerDetailsList']/thead/tr/th[contains(text(),\"Customer's Name\")]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(_xpath + "'Mobile Number')]")).isDisplayed()
				&& this.waitForElementToVisible(By.xpath(_xpath + "'Status')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(_xpath + "'Confirmed Date')]")).isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(_xpath + "'Registered By')]")).isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(_xpath + "'Registered Date')]")).isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(_xpath + "'DeRegistered Date')]"))
						.isDisplayed()) {
			return true;
		}
		return false;
	}

	public int getTableHeadingIndex(String heading) {
		int count = -1;
		this.waitForElementToVisible(By.id("customerDetailsList"));
		List<WebElement> index = driver.findElements(By
				.xpath("//table[@id='customerDetailsList']/thead/tr/th"));
		for (int i = 0; i < index.size(); i++) {
			if (index.get(i).getText().equalsIgnoreCase(heading)) {
				return i + 1;

			}
		}
		return count;
	}

	public boolean ValidateCustomerName(String cus_name) {
		this.waitForElementToVisible(By.id("customerDetailsList"));
		int count = 0;
		List<WebElement> name = driver.findElements(By
				.xpath("//table[@id='customerDetailsList']/tbody/tr/td/a"));
		for (int i = 0; i < name.size(); i++) {
			if (name.get(i).getText().toUpperCase()
					.contains(cus_name.toUpperCase())) {
				count++;
			}
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

	public int ValidateMSISDNSearch(String msisdn) {
		this.waitForElementToVisible(By.id("customerDetailsList"));
		int index = getTableHeadingIndex("Mobile Number");
		List<WebElement> product_count = driver.findElements(By
				.xpath("//table[@id='customerDetailsList']/tbody//tr//td["
						+ index + "]"));
		return product_count.size();

	}

	public List<String> getOfferName() {
		this.waitForElementToVisible(By.id("customerDetailsList"));
		int index = getTableHeadingIndex("Offer Name");
		List<WebElement> table_offer_name = driver.findElements(By
				.xpath("//table[@id='customerDetailsList']/tbody//tr//td["
						+ index + "]"));
		List<String> offer_name = new ArrayList<String>();
		for (WebElement e : table_offer_name) {
			offer_name.add(e.getText());
		}
		return offer_name;
	}

	public String getStatus(String productname) {
		this.waitForElementToVisible(By.id("customerDetailsList"));
		int index = getTableHeadingIndex("Offer Name");
		int index_status = getTableHeadingIndex("Status") - 1;
		String status = driver.findElement(
				By.xpath("//table[@id='customerDetailsList']/tbody//tr//td["
						+ index + "][contains(text(),'" + productname
						+ "')]//following-sibling::td[" + index_status + "]"))
				.getText();
		return status;
	}

	public String getCustomerName(String productname) {
		this.waitForElementToVisible(By.id("customerDetailsList"));
		int index = getTableHeadingIndex("Offer Name");
		String status = driver.findElement(
				By.xpath("//table[@id='customerDetailsList']/tbody//tr//td["
						+ index + "][contains(text(),'" + productname
						+ "')]//following-sibling::td[1]/a")).getText();
		return status;
	}

	public String getConfDate(String productname) {
		this.waitForElementToVisible(By.id("customerDetailsList"));
		int index = getTableHeadingIndex("Offer Name");
		int index_status = getTableHeadingIndex("Confirmed Date") - 1;
		String status = driver.findElement(
				By.xpath("//table[@id='customerDetailsList']/tbody//tr//td["
						+ index + "][contains(text(),'" + productname
						+ "')]//following-sibling::td[" + index_status + "]"))
				.getText();
		return status;
	}

	public String getRegisteredBy(String productname) {
		this.waitForElementToVisible(By.id("customerDetailsList"));
		int index = getTableHeadingIndex("Offer Name");
		int index_status = getTableHeadingIndex("Registered By") - 1;
		String status = driver.findElement(
				By.xpath("//table[@id='customerDetailsList']/tbody//tr//td["
						+ index + "][contains(text(),'" + productname
						+ "')]//following-sibling::td[" + index_status + "]"))
				.getText();
		return status;
	}

	public String getRegisteredDate(String productname) {
		this.waitForElementToVisible(By.id("customerDetailsList"));
		int index = getTableHeadingIndex("Offer Name");
		int index_status = getTableHeadingIndex("Registered Date") - 1;
		String status = driver.findElement(
				By.xpath("//table[@id='customerDetailsList']/tbody//tr//td["
						+ index + "][contains(text(),'" + productname
						+ "')]//following-sibling::td[" + index_status + "]"))
				.getText();
		return status;
	}

	public String getDeRegisteredDate(String productname) {
		this.waitForElementToVisible(By.id("customerDetailsList"));
		int index = getTableHeadingIndex("Offer Name");
		int index_status = getTableHeadingIndex("DeRegistered Date") - 1;
		String status = driver.findElement(
				By.xpath("//table[@id='customerDetailsList']/tbody//tr//td["
						+ index + "][contains(text(),'" + productname
						+ "')]//following-sibling::td[" + index_status + "]"))
				.getText();
		return status;
	}

	public MIP_SearchCustomerPage clickOnCustomerNameLink(String productname) {
		if (productname.equalsIgnoreCase(MIP_CustomerManagementPage.XTRALIFE))
			productname = MIP_CustomerManagementPage.XTRALIFE;
		else if (productname.equalsIgnoreCase(MIP_CustomerManagementPage.IP))
			productname = MIP_CustomerManagementPage.IP;
		else if (productname
				.equalsIgnoreCase(MIP_CustomerManagementPage.HOSPITAL))
			productname = MIP_CustomerManagementPage.HOSPITAL;
		this.waitForElementToVisible(By.id("customerDetailsList"));
		int index = getTableHeadingIndex("Offer Name");
		this.waitForElementToVisible(
				By.xpath("//table[@id='customerDetailsList']/tbody//tr//td["
						+ index + "][contains(text(),'" + productname
						+ "')]//following-sibling::td[1]/a")).click();
		return this;
	}

	public Map<String, String> getHPRelativeInfoInViewCusDetails() {
		Map<String, String> rel_info = new HashMap<String, String>();
		this.waitForElementToVisible(By
				.xpath("//*[contains(text(),'View Customer Details')]"));
		rel_info.put("cust_relationship",
				this.waitForElementToVisible(By.id("insHpRelation"))
						.getAttribute("value"));
		rel_info.put("fname",
				this.waitForElementToVisible(By.id("insHpRelFname"))
						.getAttribute("value"));
		rel_info.put("sname",
				this.waitForElementToVisible(By.id("insHpRelSurname"))
						.getAttribute("value"));
		String dob = this.waitForElementToVisible(By.id("insHpRelIrDoB"))
				.getAttribute("value");
		String age = this.waitForElementToVisible(By.id("insHpRelAge"))
				.getAttribute("value");
		rel_info.put("age", age);
		if (dob.contains("/")) {

			dob = MIP_DateFunctionality.converDateToDBDateFormat(dob);
		}
		rel_info.put("dob", dob);
		rel_info.put("msisdn",
				this.waitForElementToVisible(By.id("insHpMsisdn"))
						.getAttribute("value"));
		return rel_info;

	}

	public Map<String, String> getXtralifeRelativeInfoInViewCusDetails() {
		Map<String, String> rel_info = new HashMap<String, String>();
		this.waitForElementToVisible(By
				.xpath("//*[contains(text(),'View Customer Details')]"));
		rel_info.put("cust_relationship",
				this.waitForElementToVisible(By.id("insRelation"))
						.getAttribute("value"));
		rel_info.put("fname",
				this.waitForElementToVisible(By.id("insRelFname"))
						.getAttribute("value"));
		rel_info.put("sname",
				this.waitForElementToVisible(By.id("insRelSurname"))
						.getAttribute("value"));
		String dob = this.waitForElementToVisible(By.id("insRelIrDoB"))
				.getAttribute("value");
		if (dob.contains("/")) {

			dob = MIP_DateFunctionality.converDateToDBDateFormat(dob);
		}
		rel_info.put("age", this.waitForElementToVisible(By.id("insRelAge"))
				.getAttribute("value"));
		rel_info.put("dob", dob);
		rel_info.put("msisdn", this.waitForElementToVisible(By.id("insMsisdn"))
				.getAttribute("value"));
		return rel_info;

	}

	public Map<String, String> getIPRelativeInfoInViewCusDetails() {
		Map<String, String> rel_info = new HashMap<String, String>();
		this.waitForElementToVisible(By
				.xpath("//*[contains(text(),'View Customer Details')]"));

		rel_info.put("fname", this.waitForElementToVisible(By.id("ipNomFname"))
				.getAttribute("value"));
		rel_info.put(
				"sname",
				this.waitForElementToVisible(
						By.xpath("//div[@id='label_ipNomSurName']//following-sibling::div/input"))
						.getAttribute("value"));

		rel_info.put(
				"age",
				this.waitForElementToVisible(
						By.xpath("//div[@id='label_ipNomAge']//following-sibling::div/input"))
						.getAttribute("value"));

		rel_info.put("msisdn",
				this.waitForElementToVisible(By.id("insIPMsisdn"))
						.getAttribute("value"));
		return rel_info;

	}

	public String getChangesPerformedMessage() {
		return this
				.waitForElementToVisible(
						By.xpath("//*[contains(text(),'Changes Performed')]//following-sibling::div"))
				.getText();

	}

	public boolean validateNoChangesPerformed() {
		return this.waitForElementToVisible(
				By.xpath("//*[contains(text(),'Nothing found to display.')]"))
				.isDisplayed();
	}

	public boolean validateChageDeatilsTableHeading() {
		this.waitForElementToVisible(By.id("summaryDetailsChangesList"));
		String xpath = "//table[@id='summaryDetailsChangesList']/thead/tr//th[contains(text(),'";
		if (this.waitForElementToVisible(By.xpath(xpath + "Modified Date')]"))
				.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Modified By')]")).isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Customer Mobile Number')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Customer new First name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Customer old First name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Customer new Last name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Customer old Last name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Customer new Age')]")).isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Customer old Age')]")).isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Customer new Date of Birth')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Customer old Date of Birth')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Customer New Gender')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Customer Old Gender')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New Xl Beneficiary First Name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old Xl Beneficiary First Name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New Xl Beneficiary Last Name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old Xl Beneficiary Last Name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New Xl Beneficiary Age')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old Xl Beneficiary Age')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New Xl Beneficiary Date of Birth')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old Xl Beneficiary Date of Birth')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New Xl Beneficiary Mobile Number')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old Xl Beneficiary Mobile Number')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New Xl Beneficiary Relationship')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old XL Beneficiary Relationship')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New IP Beneficiary First Name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old IP Beneficiary First Name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New IP Beneficiary Last Name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old IP Beneficiary Last Name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New IP Beneficiary Age')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old IP Beneficiary Age')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New IP Beneficiary Mobile Number')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old IP Beneficiary Mobile Number')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New HP Beneficiary First Name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old HP Beneficiary First Name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New HP Beneficiary Last Name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old HP Beneficiary Last Name')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New HP Beneficiary Age')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old HP Beneficiary Age')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New HP Beneficiary Date of Birth')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old HP Beneficiary Date of Birth')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New HP Beneficiary Mobile Number')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old HP Beneficiary Mobile Number')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "New HP Beneficiary Relationship')]"))
						.isDisplayed()
				&& this.waitForElementToVisible(
						By.xpath(xpath + "Old HP Beneficiary Relationship')]"))
						.isDisplayed()) {
			return true;

		}
		return false;

	}

	public Map<String, String> getChangedDetails() {
		Map<String, String> change_details = new HashMap<String, String>();
		this.waitForElementToVisible(By.id("summaryDetailsChangesList"));
		/*
		 * List<WebElement> details = driver .findElements(By .xpath(
		 * "//table[@id='summaryDetailsChangesList']/tbody/tr[1]/td[3]//following-sibling::td"
		 * ));
		 */
		change_details.put("Customer new First name",
				getChangeDetails("Customer new First name"));
		change_details.put("Customer old First name",
				getChangeDetails("Customer old First name"));
		change_details.put("Customer new Last name",
				getChangeDetails("Customer new Last name"));
		change_details.put("Customer old Last name",
				getChangeDetails("Customer old Last name"));
		change_details.put("Customer new Age",
				getChangeDetails("Customer new Age"));
		change_details.put("Customer old Age",
				getChangeDetails("Customer old Age"));
		change_details.put("Customer new Date of Birth",
				getChangeDetails("Customer new Date of Birth"));
		change_details.put("Customer old Date of Birth",
				getChangeDetails("Customer old Date of Birth"));
		change_details.put("Customer New Gender",
				getChangeDetails("Customer New Gender"));
		change_details.put("Customer Old Gender",
				getChangeDetails("Customer Old Gender"));

		change_details.put("New Xl Beneficiary First Name",
				getChangeDetails("New Xl Beneficiary First Name"));
		change_details.put("Old Xl Beneficiary First Name",
				getChangeDetails("Old Xl Beneficiary First Name"));
		change_details.put("New Xl Beneficiary Last Name",
				getChangeDetails("New Xl Beneficiary Last Name"));
		change_details.put("Old Xl Beneficiary Last Name",
				getChangeDetails("Old Xl Beneficiary Last Name"));
		change_details.put("New Xl Beneficiary Age",
				getChangeDetails("New Xl Beneficiary Age"));
		change_details.put("Old Xl Beneficiary Age",
				getChangeDetails("Old Xl Beneficiary Age"));
		change_details.put("New Xl Beneficiary Date of Birth",
				getChangeDetails("New Xl Beneficiary Date of Birth"));
		change_details.put("Old Xl Beneficiary Date of Birth",
				getChangeDetails("Old Xl Beneficiary Date of Birth"));
		change_details.put("New Xl Beneficiary Mobile Number",
				getChangeDetails("New Xl Beneficiary Mobile Number"));
		change_details.put("Old Xl Beneficiary Mobile Number",
				getChangeDetails("Old Xl Beneficiary Mobile Number"));
		change_details.put("New Xl Beneficiary Relationship",
				getChangeDetails("New Xl Beneficiary Relationship"));
		change_details.put("Old XL Beneficiary Relationship",
				getChangeDetails("Old XL Beneficiary Relationship"));
		change_details.put("New IP Beneficiary First Name",
				getChangeDetails("New IP Beneficiary First Name"));
		change_details.put("Old IP Beneficiary First Name",
				getChangeDetails("Old IP Beneficiary First Name"));
		change_details.put("New IP Beneficiary Last Name",
				getChangeDetails("New IP Beneficiary Last Name"));
		change_details.put("Old IP Beneficiary Last Name",
				getChangeDetails("Old IP Beneficiary Last Name"));
		change_details.put("New IP Beneficiary Age",
				getChangeDetails("New IP Beneficiary Age"));
		change_details.put("Old IP Beneficiary Age",
				getChangeDetails("Old IP Beneficiary Age"));
		change_details.put("New IP Beneficiary Mobile Number",
				getChangeDetails("New IP Beneficiary Mobile Number"));
		change_details.put("Old IP Beneficiary Mobile Number",
				getChangeDetails("Old IP Beneficiary Mobile Number"));
		change_details.put("New HP Beneficiary First Name",
				getChangeDetails("New HP Beneficiary First Name"));
		change_details.put("Old HP Beneficiary First Name",
				getChangeDetails("Old HP Beneficiary First Name"));
		change_details.put("New HP Beneficiary Last Name",
				getChangeDetails("New HP Beneficiary Last Name"));
		change_details.put("Old HP Beneficiary Last Name",
				getChangeDetails("Old HP Beneficiary Last Name"));
		change_details.put("New HP Beneficiary Age",
				getChangeDetails("New HP Beneficiary Age"));
		change_details.put("New HP Beneficiary Date of Birth",
				getChangeDetails("New HP Beneficiary Date of Birth"));
		change_details.put("Old HP Beneficiary Date of Birth",
				getChangeDetails("Old HP Beneficiary Date of Birth"));
		change_details.put("Old HP Beneficiary Mobile Number",
				getChangeDetails("Old HP Beneficiary Mobile Number"));
		change_details.put("New HP Beneficiary Relationship",
				getChangeDetails("New HP Beneficiary Relationship"));
		change_details.put("Old HP Beneficiary Relationship",
				getChangeDetails("Old HP Beneficiary Relationship"));
		change_details.put("New HP Beneficiary Mobile Number",
				getChangeDetails("New HP Beneficiary Mobile Number"));
		change_details.put("Old HP Beneficiary Mobile Number",
				getChangeDetails("Old HP Beneficiary Mobile Number"));

		return change_details;
	}

	public String getChangeDetails(String heading) {
		this.waitForElementToVisible(By.id("summaryDetailsChangesList"));
		List<WebElement> index = driver.findElements(By
				.xpath("//table[@id='summaryDetailsChangesList']/thead/tr/th"));
		for (int i = 0; i < index.size(); i++) {
			if (index.get(i).getText().trim().equalsIgnoreCase(heading.trim())) {
				return this
						.waitForElementToVisible(
								By.xpath("//table[@id='summaryDetailsChangesList']/tbody/tr[1]/td["
										+ (i + 1) + "]")).getText();
			}
		}
		return "";
	}

}
