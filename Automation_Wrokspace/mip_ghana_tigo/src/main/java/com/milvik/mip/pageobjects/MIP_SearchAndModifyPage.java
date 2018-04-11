package com.milvik.mip.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.milvik.mip.constants.MIP_Constants;
import com.milvik.mip.pageutil.MIP_BasePage;
import com.milvik.mip.utility.MIP_DateFunctionality;
import com.milvik.mip.utility.MIP_Logging;

public class MIP_SearchAndModifyPage extends MIP_BasePage {
	WebDriver driver;
	static Logger logger;
	static {
		logger = MIP_Logging.logDetails("MIP_SearchAndModifyPage");
	}

	public MIP_SearchAndModifyPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	@FindBy(id = "msisdn")
	public WebElement msisdn;
	@FindBy(id = "search-icon")
	public WebElement search_icon;

	public MIP_SearchAndModifyPage enterMsisdn(String msisdn) {
		this.waitForElementToVisible(By.id("msisdn"));
		this.msisdn.sendKeys(msisdn);
		logger.info("Entered msisdn");
		return this;
	}

	public void clickOnSearchIcon() {
		this.search_icon.click();
		logger.info("Clicked on  SearchIcon");
	}

	public String getValidationMessage() {
		logger.info("Getting validation message");
		return this.waitForElementToVisible(By.id("validationMessages"))
				.getText();
	}

	public boolean validateClaimSearchtableHeading() {
		logger.info("Validating table heading");
		this.waitForElementToVisible(By.id("custList"));
		boolean value1 = driver
				.findElement(
						By.xpath("//table[@id='custList']/thead//tr//th[contains(text(),\"Customer's Name\")]"))

				.getText().equalsIgnoreCase("Customer's Name");
		boolean value2 = driver
				.findElement(
						By.xpath("//table[@id='custList']/thead//tr//th[contains(text(),'Mobile Number')]"))
				.getText().equalsIgnoreCase("Mobile Number");

		boolean value3 = driver
				.findElement(
						By.xpath("//table[@id='custList']/thead//tr//th[contains(text(),'Age at Time of Registration')]"))
				.getText().equalsIgnoreCase("Age at Time of Registration");
		boolean value4 = driver
				.findElement(
						By.xpath("//table[@id='custList']/thead//tr//th[contains(text(),'Implied Age Today')]"))
				.getText().equalsIgnoreCase("Implied Age Today");
		if (value1 && value2 && value3 && value4) {
			return true;
		}
		return false;

	}

	public int getIndex(String text) {
		List<WebElement> list = driver.findElements(By
				.xpath("//table[@id='custList']/thead//tr//th"));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getText().trim().equalsIgnoreCase(text.trim())) {
				return i + 1;
			}
		}
		return -1;
	}

	public String getCustomerNameFromTable() {
		logger.info("Getting customer name from table");
		this.waitForElementToVisible(By.id("custList"));
		return driver.findElement(
				By.xpath("//table[@id='custList']/tbody//tr//td["
						+ getIndex("Customer's Name") + "]")).getText();
	}

	public String getCustomerAgeFromTable() {
		logger.info("Getting customer Age from table");
		this.waitForElementToVisible(By.id("custList"));
		return driver.findElement(
				By.xpath("//table[@id='custList']/tbody//tr//td["
						+ getIndex("Age at Time of Registration") + "]"))
				.getText();
	}

	public String getCustomerImpliedAgeFromTable() {
		logger.info("Getting customer Implied age from table");
		this.waitForElementToVisible(By.id("custList"));
		return driver.findElement(
				By.xpath("//table[@id='custList']/tbody//tr//td["
						+ getIndex("Implied Age Today") + "]")).getText();
	}

	public MIP_SearchAndModifyPage clickOnCustomerNameLink() {
		logger.info("Clicking on CustomerNameLink");
		this.waitForElementToVisible(By.id("custList"));
		this.waitForElementToVisible(By
				.xpath("//table[@id='custList']/tbody//tr//td/a"));
		driver.findElement(By.xpath("//table[@id='custList']/tbody//tr//td/a"))
				.click();
		logger.info("Clicked on CustomerNameLink");
		return this;
	}

	public boolean validateviewCustomerObjects() {
		return this
				.waitForElementToVisible(By.xpath("//div[@class='pagetitle']"))
				.getText().trim().equalsIgnoreCase("VIEW CUSTOMER DETAILS");

	}

	public List<String> getCustomerInformation() {
		logger.info("Getting Customer Information");
		List<String> deails = new ArrayList<String>();
		deails.add(driver.findElement(By.id("fname")).getAttribute("value"));
		deails.add(driver.findElement(By.id("sname")).getAttribute("value"));
		String value = driver.findElement(By.id("dob")).getAttribute("value");
		if (!value.equals(""))
			deails.add(MIP_DateFunctionality.converDateToDBDateFormat(value));
		else
			deails.add("");
		deails.add(driver.findElement(By.id("age")).getAttribute("value"));
		List<WebElement> gender = driver.findElements(By.id("gender"));
		if (gender.get(0).isSelected()) {
			deails.add(gender.get(0).getAttribute("value"));
		} else if (gender.get(1).isSelected()) {
			deails.add(gender.get(1).getAttribute("value"));
		} else {
			deails.add("");
		}
		deails.add(driver.findElement(By.id("impliedAge"))
				.getAttribute("value"));
		return deails;
	}

	public List<String> getInsuredRelativeInformation() {
		logger.info("Getting Customer Relative  Information");
		List<String> deails = new ArrayList<String>();
		Select s = new Select(driver.findElement(By.id("relation")));
		deails.add(s.getFirstSelectedOption().getText());
		deails.add(driver.findElement(By.id("insRelFname")).getAttribute(
				"value"));
		deails.add(driver.findElement(By.id("insRelSurname")).getAttribute(
				"value"));
		String value = driver.findElement(By.id("insRelIrDoB")).getAttribute(
				"value");
		if (!value.equals(""))
			deails.add(MIP_DateFunctionality.converDateToDBDateFormat(value));
		else
			deails.add("");
		deails.add(driver.findElement(By.id("insRelAge")).getAttribute("value"));
		deails.add(driver.findElement(By.id("insMsisdn")).getAttribute("value"));
		return deails;
	}

	public List<String> getHPRelativeInformation() {
		logger.info("Getting HP Relative  Information");
		List<String> deails = new ArrayList<String>();
		Select s = new Select(driver.findElement(By.id("insHpRelation")));
		deails.add(s.getFirstSelectedOption().getText());
		deails.add(driver.findElement(By.id("insHpRelFname")).getAttribute(
				"value"));
		deails.add(driver.findElement(By.id("insHpRelSurname")).getAttribute(
				"value"));
		String value = driver.findElement(By.id("insHpRelIrDoB")).getAttribute(
				"value");
		if (!value.equals(""))
			deails.add(MIP_DateFunctionality.converDateToDBDateFormat(value));
		else
			deails.add("");
		deails.add(driver.findElement(By.id("insHpRelAge")).getAttribute(
				"value"));
		deails.add(driver.findElement(By.id("insHpMsisdn")).getAttribute(
				"value"));
		return deails;
	}

	public List<String> getIncomeProtectionInformation() {
		logger.info("Getting Income Protection  Information");
		List<String> deails = new ArrayList<String>();

		deails.add(driver.findElement(By.id("ipNomFirstName")).getAttribute(
				"value"));
		deails.add(driver.findElement(By.id("ipNomSurName")).getAttribute(
				"value"));
		deails.add(driver.findElement(By.id("ipNomAge")).getAttribute("value"));
		deails.add(driver.findElement(By.id("ipInsMsisdn")).getAttribute(
				"value"));
		return deails;
	}

	public String getIncomeProtectionCoverDetails() {
		Select s = new Select(driver.findElement(By.id("productCoverIdIP")));
		return s.getFirstSelectedOption().getText().replaceAll("\\,", "");
	}

	public boolean readOnlyCheck() {
		boolean value1 = false;
		boolean value2 = false;
		boolean value3 = false;
		boolean value4 = false;
		this.waitForElementToVisible(By.id("div_id_2"));
		value1 = driver
				.findElement(By.xpath("//input[@id='productId'][@value='2']"))
				.getAttribute("disabled").equalsIgnoreCase("true");
		value2 = driver
				.findElement(By.xpath("//input[@id='productId'][@value='3']"))
				.getAttribute("disabled").equalsIgnoreCase("true");
		value3 = driver
				.findElement(By.xpath("//input[@id='productId'][@value='4']"))
				.getAttribute("disabled").equalsIgnoreCase("true");
		List<WebElement> list = driver.findElements(By.id("productCoverIdIP"));
		if (list.size() != 0) {
			value4 = list.get(0).getAttribute("disabled")
					.equalsIgnoreCase("true");
		}
		if (value1 && value2 && value3 && value4) {
			return true;
		} else
			return false;
	}

	public MIP_SearchAndModifyPage clickOnClear() {
		logger.info("Clicking on Clear button in Claim Management page");
		this.clickOnElement(By.id("clearBtn"));
		return this;
	}

	public MIP_SearchAndModifyPage clickOnSave() {
		logger.info("Clicking on Save button in Claim Management page");
		this.clickOnElement(By.id("saveBtn"));
		return this;
	}

	public MIP_SearchAndModifyPage clearCustomerInformation() {
		logger.info("Clearing Customer Information");
		this.waitForElementToVisible(By.id("fname"));
		driver.findElement(By.id("fname")).clear();
		driver.findElement(By.id("sname")).clear();
		driver.findElement(By.id("age")).clear();
		return this;
	}

	public MIP_SearchAndModifyPage clearCustomerInformation(String fname,
			String sname, String age, String dob) {
		logger.info("Clearing Customer Information");
		this.waitForElementToVisible(By.id("fname"));
		if (!fname.equals(""))
			driver.findElement(By.id("fname")).clear();
		if (!sname.equals(""))
			driver.findElement(By.id("sname")).clear();
		if (!age.equals(""))
			driver.findElement(By.id("age")).clear();
		if (!dob.equals(""))
			driver.findElement(
					By.xpath("//div[@id='calBut1']//following-sibling::div[@class='clear-icon']"))
					.click();
		return this;
	}

	public MIP_SearchAndModifyPage clearInsuredRelativeInformation(
			String relation, String fname, String sname, String age,
			String dob, String mobilemun) {
		this.waitForElementToVisible(By.id("relation"));
		logger.info("Clearing Customer Relative  Information");
		if (!relation.equals("")) {
			Select s = new Select(driver.findElement(By.id("relation")));
			s.selectByVisibleText("");
		}
		if (!fname.equals(""))
			driver.findElement(By.id("insRelFname")).clear();
		if (!sname.equals(""))
			driver.findElement(By.id("insRelSurname")).clear();
		if (!age.equals("") || !dob.equals("")) {
			driver.findElement(By.id("insRelAge")).clear();
			driver.findElement(
					By.xpath("//div[@id='calBut2']//following-sibling::div[@class='clear-icon']"))
					.click();
		}
		if (!mobilemun.equals(""))
			driver.findElement(By.id("insMsisdn")).clear();

		return this;

	}

	public MIP_SearchAndModifyPage clearInsuredRelativeInformationHP(
			String relation, String fname, String sname, String age,
			String dob, String mobilemun) {
		this.waitForElementToVisible(By.id("insHpRelation"));
		logger.info("Clearing Customer Relative  Information");
		if (!relation.equals("")) {
			Select s = new Select(driver.findElement(By.id("insHpRelation")));
			s.selectByVisibleText("");
		}
		if (!fname.equals(""))
			driver.findElement(By.id("insHpRelFname")).clear();
		if (!sname.equals(""))
			driver.findElement(By.id("insHpRelSurname")).clear();
		if (!age.equals("") || !dob.equals("")) {
			driver.findElement(By.id("insHpRelAge")).clear();
			driver.findElement(
					By.xpath("//div[@id='calBut3']//following-sibling::div[@class='clear-icon']"))
					.click();
		}
		if (!mobilemun.equals(""))
			driver.findElement(By.id("insHpMsisdn")).clear();

		return this;

	}

	public MIP_SearchAndModifyPage clearInsuredRelativeInformationHP() {
		this.waitForElementToVisible(By.id("insHpRelation"));
		logger.info("Clearing Customer Relative  Information");
		Select s = new Select(driver.findElement(By.id("insHpRelation")));
		s.selectByVisibleText("");

		driver.findElement(By.id("insHpRelFname")).clear();
		driver.findElement(By.id("insHpRelSurname")).clear();
		driver.findElement(By.id("insHpRelAge")).clear();
		driver.findElement(By.id("insHpMsisdn")).clear();
		driver.findElement(
				By.xpath("//div[@id='calBut3']//following-sibling::div[@class='clear-icon']"))
				.click();
		return this;

	}

	public MIP_SearchAndModifyPage clearInsuredRelativeInformation(
			String fname, String sname, String age, String msisdn) {
		this.waitForElementToVisible(By.id("relation"));
		logger.info("Clearing Customer Relative  Information");
		if (!fname.equals(""))
			driver.findElement(By.id("insRelFname")).clear();
		if (!sname.equals(""))
			driver.findElement(By.id("insRelSurname")).clear();
		if (!age.equals(""))
			driver.findElement(By.id("insRelAge")).clear();
		if (!msisdn.equals(""))
			driver.findElement(By.id("insMsisdn")).clear();
		return this;

	}

	public MIP_SearchAndModifyPage clearInsuredRelativeInformation() {
		this.waitForElementToVisible(By.id("relation"));
		logger.info("Clearing Customer Relative  Information");
		Select s = new Select(driver.findElement(By.id("relation")));
		s.selectByVisibleText("");
		driver.findElement(By.id("insRelFname")).clear();
		driver.findElement(By.id("insRelSurname")).clear();
		driver.findElement(By.id("insRelAge")).clear();
		driver.findElement(By.id("insMsisdn")).clear();
		return this;

	}

	public MIP_SearchAndModifyPage clearNomineeInformation() {
		this.waitForElementToVisible(By.id("ipNomFirstName"));
		logger.info("Clearing Income Protection  Information");
		driver.findElement(By.id("ipNomFirstName")).clear();
		driver.findElement(By.id("ipNomSurName")).clear();
		driver.findElement(By.id("ipNomAge")).clear();
		driver.findElement(By.id("ipInsMsisdn")).clear();
		return this;

	}

	public MIP_SearchAndModifyPage clearNomineeInformation(String fname,
			String sname, String age, String mobilenum) {
		this.waitForElementToVisible(By.id("ipNomFirstName"));
		logger.info("Clearing Income Protection  Information");
		if (!fname.equals(""))
			driver.findElement(By.id("ipNomFirstName")).clear();
		if (!sname.equals(""))
			driver.findElement(By.id("ipNomSurName")).clear();
		if (!age.equals(""))
			driver.findElement(By.id("ipNomAge")).clear();
		if (!mobilenum.equals(""))
			driver.findElement(By.id("ipInsMsisdn")).clear();
		return this;

	}

	public MIP_SearchAndModifyPage enterCustomerInformation(String fname,
			String sname, String age, String dob, String gender) {
		logger.info("Entering Customer Information");
		if (!fname.equals(""))
			driver.findElement(By.id("fname")).sendKeys(fname);
		if (!sname.equals(""))
			driver.findElement(By.id("sname")).sendKeys(sname);
		if (!age.equals(""))
			driver.findElement(By.id("age")).sendKeys(age);
		if (!gender.equals("")) {
			if (gender.equalsIgnoreCase("Male")) {
				driver.findElement(
						By.xpath("//input[@id='gender'][@value='M']")).click();
			} else {
				driver.findElement(
						By.xpath("//input[@id='gender'][@value='F']")).click();
			}
		}
		if (!dob.equals("")) {
			logger.info("Selecting DOB");
			String[] date = MIP_DateFunctionality.getDate(dob,
					MIP_Constants.DOB_FORMAT);
			this.waitForElementToVisible(
					By.xpath("//div[@class='calendar-icon']")).click();

			Actions a = new Actions(driver);
			a.moveToElement(
					this.waitForElementToVisible(By
							.xpath("//table/tbody/tr/td/div/div[1]/table/tbody/tr/td/div/div")))
					.build().perform();
			this.waitForElementToVisible(
					By.xpath("//table/tbody/tr/td/div/div[1]/table/tbody/tr/td/div/div"))
					.click();
			WebElement ele = this.waitForElementToVisible(By
					.className("DynarchCalendar-menu-year"));
			ele.clear();
			ele.sendKeys(date[2]);
			this.waitForElementToVisible(
					By.xpath("//table[@class='DynarchCalendar-menu-mtable']/tbody//tr//td/div[contains(text(),'"
							+ date[1] + "')]")).click();
			if ((date[0].charAt(0) + "").equals("0")) {
				date[0] = date[0].charAt(1) + "";
			}

			this.waitForElementToVisible(
					By.xpath("//div[@class='DynarchCalendar-body']/table[@class='DynarchCalendar-bodyTable']/tbody//tr//td/div[@class='DynarchCalendar-day' or @class='DynarchCalendar-day DynarchCalendar-weekend'][contains(text(),'"
							+ date[0] + "')]")).click();

		}
		return this;
	}

	public MIP_SearchAndModifyPage enterInsuredRelativeInformation(
			String relationship, String fname, String sname, String age,
			String dob, String msisdn) {
		this.waitForElementToVisible(By.id("relation"));
		logger.info("Entering Customer Relative  Information");
		if (!relationship.equals("")) {
			Select s = new Select(driver.findElement(By.id("relation")));
			s.selectByVisibleText(relationship);
		}
		if (!fname.equals(""))
			driver.findElement(By.id("insRelFname")).sendKeys(fname);
		if (!sname.equals(""))
			driver.findElement(By.id("insRelSurname")).sendKeys(sname);
		if (!age.equals(""))
			driver.findElement(By.id("insRelAge")).sendKeys(age);
		if (!msisdn.equals(""))
			driver.findElement(By.id("insMsisdn")).sendKeys(msisdn);
		else
			driver.findElement(
					By.xpath("//input[@id='benInsMsisdnYesNo'][@value='no']"))
					.click();
		if (!dob.equals("")) {
			logger.info("Selecting DOB for Insured relative XL");
			String[] date = MIP_DateFunctionality.getDate(dob,
					MIP_Constants.DOB_FORMAT);
			this.waitForElementToVisible(By.id("calBut2")).click();
			Actions a = new Actions(driver);
			WebDriverWait w = new WebDriverWait(driver, 5);
			try {
				a.moveToElement(
						w.until(ExpectedConditions.visibilityOfElementLocated((By
								.xpath("//table[2]/tbody/tr/td/div/div[1]/table/tbody/tr/td/div/div")))))
						.build().perform();
				this.waitForElementToVisible(
						By.xpath("//table[2]/tbody/tr/td/div/div[1]/table/tbody/tr/td/div/div"))
						.click();
				WebElement ele = this
						.waitForElementToVisible(By
								.xpath("//table[2]/tbody/tr/td/div/div[4]/table/tbody/tr/td/table[1]/tbody/tr[1]/td/input"));

				ele.clear();
				ele.sendKeys(date[2]);

				this.waitForElementToVisible(
						By.xpath("//table[2]/tbody/tr/td/div/div[4]/table/tbody/tr/td/table[2]/tbody/tr/td/div[contains(text(),'"
								+ date[1] + "')]")).click();
				if ((date[0].charAt(0) + "").equals("0")) {
					date[0] = date[0].charAt(1) + "";
				}
				this.waitForElementToVisible(
						By.xpath("//table[2]/tbody/tr/td/div/div[2]/table/tbody//tr//td/div[@class='DynarchCalendar-day' or @class='DynarchCalendar-day DynarchCalendar-weekend'][contains(text(),'"
								+ date[0] + "')]")).click();
			} catch (Exception e) {
				a.moveToElement(
						this.waitForElementToVisible(By
								.xpath("//table/tbody/tr/td/div/div[1]/table/tbody/tr/td/div/div")))
						.build().perform();
				this.waitForElementToVisible(
						By.xpath("//table/tbody/tr/td/div/div[1]/table/tbody/tr/td/div/div"))
						.click();
				WebElement ele = this
						.waitForElementToVisible(By
								.xpath("//table/tbody/tr/td/div/div[4]/table/tbody/tr/td/table[1]/tbody/tr[1]/td/input"));

				ele.clear();
				ele.sendKeys(date[2]);

				this.waitForElementToVisible(
						By.xpath("//table/tbody/tr/td/div/div[4]/table/tbody/tr/td/table[2]/tbody/tr/td/div[contains(text(),'"
								+ date[1] + "')]")).click();
				if ((date[0].charAt(0) + "").equals("0")) {
					date[0] = date[0].charAt(1) + "";
				}
				this.waitForElementToVisible(
						By.xpath("//table/tbody/tr/td/div/div[2]/table/tbody//tr//td/div[@class='DynarchCalendar-day' or @class='DynarchCalendar-day DynarchCalendar-weekend'][contains(text(),'"
								+ date[0] + "')]")).click();
			}

		}
		return this;
	}

	public MIP_SearchAndModifyPage enterRelativeInformationHP(
			String relationship, String fname, String sname, String age,
			String dob, String msisdn) {
		this.waitForElementToVisible(By.id("insHpRelation"));
		logger.info("Entering Customer Relative  Information");
		if (!relationship.equals("")) {
			Select s = new Select(driver.findElement(By.id("insHpRelation")));
			s.selectByVisibleText(relationship);
		}
		if (!fname.equals(""))
			driver.findElement(By.id("insHpRelFname")).sendKeys(fname);
		if (!sname.equals(""))
			driver.findElement(By.id("insHpRelSurname")).sendKeys(sname);
		if (!age.equals(""))
			driver.findElement(By.id("insHpRelAge")).sendKeys(age);
		if (!msisdn.equals(""))
			driver.findElement(By.id("insHpMsisdn")).sendKeys(msisdn);
		else
			driver.findElement(
					By.xpath("//input[@id='benHpInsMsisdnYesNo'][@value='no']"))
					.click();
		if (!dob.equals("")) {
			logger.info("Selecting DOB for Insured relative HP");
			String[] date = MIP_DateFunctionality.getDate(dob,
					MIP_Constants.DOB_FORMAT);
			this.waitForElementToVisible(By.id("calBut3")).click();
			Actions a = new Actions(driver);
			WebElement ele = null;
			WebDriverWait w = new WebDriverWait(driver, 5);
			try {
				a.moveToElement(
						w.until(ExpectedConditions.visibilityOfElementLocated(By
								.xpath("//table[2]/tbody/tr/td/div/div[1]/table/tbody/tr/td/div/div"))))
						.build().perform();
				w.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath("//table[2]/tbody/tr/td/div/div[1]/table/tbody/tr/td/div/div")))
						.click();
				ele = this
						.waitForElementToVisible(By
								.xpath("//table[2]/tbody/tr/td/div/div[4]/table/tbody/tr/td/table[1]/tbody/tr[1]/td/input"));
				ele.clear();
				ele.sendKeys(date[2]);
				w.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath("//table[2]/tbody/tr/td/div/div[4]/table/tbody/tr/td/table[2]/tbody/tr/td/div[contains(text(),'"
								+ date[1] + "')]"))).click();

				if ((date[0].charAt(0) + "").equals("0")) {
					date[0] = date[0].charAt(1) + "";
				}

				this.waitForElementToVisible(
						By.xpath("//table[2]/tbody/tr/td/div/div[2]/table/tbody/tr/td/div[@class='DynarchCalendar-day' or @class='DynarchCalendar-day DynarchCalendar-weekend'][contains(text(),'"
								+ date[0] + "')]")).click();
				if (driver.findElement(By.id("insHpRelIrDoB"))
						.getAttribute("value").equals("")) {
					w.until(ExpectedConditions.visibilityOfElementLocated(By
							.xpath("//table[2]/tbody/tr/td/div/div[4]/table/tbody/tr/td/table[2]/tbody/tr/td/div[contains(text(),'"
									+ date[1] + "')]"))).click();
					this.waitForElementToVisible(
							By.xpath("//table[2]/tbody/tr/td/div/div[2]/table/tbody/tr/td/div[@class='DynarchCalendar-day' or @class='DynarchCalendar-day DynarchCalendar-weekend'][contains(text(),'"
									+ date[0] + "')]")).click();
				}
			} catch (TimeoutException e) {
				try {
					a.moveToElement(
							w.until(ExpectedConditions.visibilityOfElementLocated(By
									.xpath("//table/tbody/tr/td/div/div[1]/table/tbody/tr/td/div/div"))))
							.build().perform();
					w.until(ExpectedConditions.visibilityOfElementLocated(By
							.xpath("//table/tbody/tr/td/div/div[1]/table/tbody/tr/td/div/div")))
							.click();
					ele = this
							.waitForElementToVisible(By
									.xpath("//table/tbody/tr/td/div/div[4]/table/tbody/tr/td/table[1]/tbody/tr[1]/td/input"));
					ele.clear();
					ele.sendKeys(date[2]);
					w.until(ExpectedConditions.visibilityOfElementLocated(By
							.xpath("//table/tbody/tr/td/div/div[4]/table/tbody/tr/td/table[2]/tbody/tr/td/div[contains(text(),'"
									+ date[1] + "')]"))).click();
					if ((date[0].charAt(0) + "").equals("0")) {
						date[0] = date[0].charAt(1) + "";
					}

					this.waitForElementToVisible(
							By.xpath("//table/tbody/tr/td/div/div[2]/table/tbody/tr/td/div[@class='DynarchCalendar-day' or @class='DynarchCalendar-day DynarchCalendar-weekend'][contains(text(),'"
									+ date[0] + "')]")).click();
				} catch (TimeoutException e1) {
					a.moveToElement(
							w.until(ExpectedConditions.visibilityOfElementLocated(By
									.xpath("//table[3]/tbody/tr/td/div/div[1]/table/tbody/tr/td/div/div"))))
							.build().perform();
					w.until(ExpectedConditions.visibilityOfElementLocated(By
							.xpath("//table[3]/tbody/tr/td/div/div[1]/table/tbody/tr/td/div/div")))
							.click();
					ele = this
							.waitForElementToVisible(By
									.xpath("//table[3]/tbody/tr/td/div/div[4]/table/tbody/tr/td/table[1]/tbody/tr[1]/td/input"));
					ele.clear();
					ele.sendKeys(date[2]);
					w.until(ExpectedConditions.visibilityOfElementLocated(By
							.xpath("//table[3]/tbody/tr/td/div/div[4]/table/tbody/tr/td/table[2]/tbody/tr/td/div[contains(text(),'"
									+ date[1] + "')]"))).click();
					if ((date[0].charAt(0) + "").equals("0")) {
						date[0] = date[0].charAt(1) + "";
					}

					this.waitForElementToVisible(
							By.xpath("//table[3]/tbody/tr/td/div/div[2]/table/tbody/tr/td/div[@class='DynarchCalendar-day' or @class='DynarchCalendar-day DynarchCalendar-weekend'][contains(text(),'"
									+ date[0] + "')]")).click();
				}
			}
		}
		logger.info("Entered Insured Relative Information for HP");
		return this;
	}

	public MIP_SearchAndModifyPage enterNomineeInformation(
			String Nominee_Fname, String Nominee_Snmae, String Nominee_age,
			String Nominee_mobilenumber) {
		logger.info("Entering Nominee  Information");
		this.waitForElementToVisible(By.id("ipNomFirstName"));
		if (!Nominee_Fname.equals(""))
			driver.findElement(By.id("ipNomFirstName")).sendKeys(Nominee_Fname);
		if (!Nominee_Snmae.equals(""))
			driver.findElement(By.id("ipNomSurName")).sendKeys(Nominee_Snmae);
		if (!Nominee_age.equals(""))
			driver.findElement(By.id("ipNomAge")).sendKeys(Nominee_age);
		if (!Nominee_mobilenumber.equals(""))
			driver.findElement(By.id("ipInsMsisdn")).sendKeys(
					Nominee_mobilenumber);
		else
			driver.findElement(
					By.xpath("//input[@id='benMsisdnYesNo'][@value='no']"))
					.click();
		return this;

	}

	public MIP_SearchAndModifyPage informBenOption() {
		if (driver.findElements(By.id("insMsisdn")).get(0).isDisplayed()) {
			if (driver.findElement(By.id("insMsisdn")).getAttribute("value")
					.equals(""))
				driver.findElement(
						By.xpath("//input[@id='benInsMsisdnYesNo'][@value='no']"))
						.click();
		}
		if (driver.findElements(By.id("ipInsMsisdn")).get(0).isDisplayed()) {
			if (driver.findElement(By.id("ipInsMsisdn")).getAttribute("value")
					.equals(""))
				driver.findElement(
						By.xpath("//input[@id='benMsisdnYesNo'][@value='no']"))
						.click();
		}
		if (driver.findElements(By.id("insHpMsisdn")).get(0).isDisplayed()) {
			if (driver.findElement(By.id("insHpMsisdn")).getAttribute("value")
					.equals(""))
				driver.findElement(
						By.xpath("//input[@id='benHpInsMsisdnYesNo'][@value='no']"))
						.click();
		}
		return this;
	}
}
