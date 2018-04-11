package com.milvik.mip.pageutil;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.milvik.mip.utility.MIP_Logging;

public class MIP_RoleManagementPage extends MIP_BasePage {

	WebDriver driver;
	static Logger logger;
	static {
		logger = MIP_Logging.logDetails("MIP_RoleManagementPage");
	}

	public MIP_RoleManagementPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='1']")
	public WebElement homeMenu_checkbox;
	@FindBy(xpath = "//div[@id='label_menu'][1]")
	public WebElement homeMenu;

	@FindBy(xpath = "//div[2][@id='label_menu']")
	public WebElement branchmanageMenu;
	@FindBy(xpath = "//input[@id='29']")
	public WebElement branchmanageMenu_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][1]")
	public WebElement addBranchMenu;
	@FindBy(xpath = "//div[@id='label_subMenu'][2]")
	public WebElement listBranchMenu;
	@FindBy(xpath = "//input[@id='30']")
	public WebElement addBranch_checkbox;
	@FindBy(xpath = "//input[@id='31']")
	public WebElement listBranch_checkbox;

	@FindBy(xpath = "//div[@id='label_menu'][3]")
	public WebElement usermanageMenu;
	@FindBy(xpath = "//input[@id='2']")
	public WebElement usermanageMenu_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][3]")
	public WebElement adduserMenu;
	@FindBy(xpath = "//input[@id='12']")
	public WebElement addUser_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][4]")
	public WebElement listuserMenu;
	@FindBy(xpath = "//input[@id='13']")
	public WebElement listUser_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][5]")
	public WebElement searchuserMenu;
	@FindBy(xpath = "//input[@id='14']")
	public WebElement searchUser_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][6]")
	public WebElement resetPasswordMenu;
	@FindBy(xpath = "//input[@id='15']")
	public WebElement resetPassword_checkbox;

	@FindBy(xpath = "//div[@id='label_menu'][4]")
	public WebElement leavemanageMenu;
	@FindBy(xpath = "//input[@id='34']")
	public WebElement leavemanageMenu_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][7]")
	public WebElement applyLeaveMenu;
	@FindBy(xpath = "//input[@id='35']")
	public WebElement applyLeave_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][8]")
	public WebElement viewLeaveMenu;
	@FindBy(xpath = "//input[@id='38']")
	public WebElement viewLeave_checkbox;

	@FindBy(xpath = "//div[@id='label_menu'][5]")
	public WebElement custmanageMenu;
	@FindBy(xpath = "//input[@id='3']")
	public WebElement custmanageMenu_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][9]")
	public WebElement regCustMenu;
	@FindBy(xpath = "//input[@id='16']")
	public WebElement regCust_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][10]")
	public WebElement searchCustMenu;
	@FindBy(xpath = "//input[@id='17']")
	public WebElement searchCust_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][11]")
	public WebElement deRegCustMenu;
	@FindBy(xpath = "//input[@id='32']")
	public WebElement deRegCust_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][12]")
	public WebElement downloadESignature;
	@FindBy(xpath = "//input[@id='40']")
	public WebElement downloadESignature_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][13]")
	public WebElement changeDeductionMode;
	@FindBy(xpath = "//input[@id='48']")
	public WebElement changeDeductionMode_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][14]")
	public WebElement coverHistMenu;
	@FindBy(xpath = "//input[@id='49']")
	public WebElement coverHist_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][15]")
	public WebElement loyalty;
	@FindBy(xpath = "//input[@id='51']")
	public WebElement loyalty_checkbox;

	@FindBy(xpath = "//div[@id='label_menu'][6]")
	public WebElement claimsmanageMenu;
	@FindBy(xpath = "//input[@id='41']")
	public WebElement claimsmanageMenu_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][16]")
	public WebElement claimSearch;
	@FindBy(xpath = "//input[@id='43']")
	public WebElement claimSearch_checkbox;

	@FindBy(xpath = "//div[@id='label_menu'][7]")
	public WebElement smsmanageMenu;
	@FindBy(xpath = "//input[@id='6']")
	public WebElement smsmanageMenu_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][17]")
	public WebElement editSmsMenu;
	@FindBy(xpath = "//input[@id='22']")
	public WebElement editSms_checkbox;

	@FindBy(xpath = "//div[@id='label_menu'][9]")
	public WebElement rolemanageMenu;
	@FindBy(xpath = "//input[@id='45']")
	public WebElement rolemanageMenu_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][21]")
	public WebElement addRoleMenu;
	@FindBy(xpath = "//input[@id='46']")
	public WebElement addRole_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][22]")
	public WebElement viewRoleMenu;
	@FindBy(xpath = "//input[@id='47']")
	public WebElement viewRole_checkbox;

	@FindBy(xpath = "//div[@id='label_menu'][8]")
	public WebElement reportmanageMenu;
	@FindBy(xpath = "//input[@id='8']")
	public WebElement reportmanageMenu_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][18]")
	public WebElement custReportMenu;
	@FindBy(xpath = "//input[@id='27']")
	public WebElement custReport_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][19]")
	public WebElement agentReportMenu;
	@FindBy(xpath = "//input[@id='33']")
	public WebElement agentReport_checkbox;

	@FindBy(xpath = "//div[@id='label_subMenu'][20]")
	public WebElement deductionReportMenu;
	@FindBy(xpath = "//input[@id='50']")
	public WebElement deductionReport_checkbox;

	@FindBy(xpath = "//div[@id='label_menu'][10]")
	public WebElement adminConfigMenu;
	@FindBy(xpath = "//input[@id='9']")
	public WebElement adminConfig_checkbox;

	@FindBy(xpath = "//div[@id='label_menu'][11]")
	public WebElement changePassMenu;
	@FindBy(xpath = "//input[@id='10']")
	public WebElement changePass_checkbox;

	@FindBy(xpath = "//div[@id='label_menu'][12]")
	public WebElement logoutMenu;
	@FindBy(xpath = "//input[@id='11']")
	public WebElement logout_checkbox;

	@FindBy(id = "saveBtn")
	public WebElement save;
	@FindBy(id = "clearBtn")
	public WebElement clear;

	public void clickOnSave() {
		this.waitForElementToVisible(By.id("saveBtn")).click();

	}

	public void clickOnClear() {
		this.waitForElementToVisible(By.id("clearBtn"));
		clear.click();
	}

	public void selectAddBranch() {
		this.waitForElementToVisible(By.xpath("//input[@id='30']"));
		this.addBranch_checkbox.click();
	}

	public void selectListBranch() {
		this.waitForElementToVisible(By.xpath("//input[@id='31']"));
		this.listBranch_checkbox.click();
	}

	public void selectAddUser() {
		this.waitForElementToVisible(By.xpath("//input[@id='12']"));
		this.addUser_checkbox.click();
	}

	public void selectListUser() {
		this.waitForElementToVisible(By.xpath("//input[@id='13']"));
		this.listUser_checkbox.click();
	}

	public void selectSearchUser() {
		this.waitForElementToVisible(By.xpath("//input[@id='14']"));
		this.searchUser_checkbox.click();
	}

	public void selectresetPassword() {
		this.waitForElementToVisible(By.xpath("//input[@id='15']"));
		this.resetPassword_checkbox.click();
	}

	public void selectapplyLeave() {
		this.waitForElementToVisible(By.xpath("//input[@id='35']"));
		this.applyLeave_checkbox.click();
	}

	public void selectviewLeave() {
		this.waitForElementToVisible(By.xpath("//input[@id='38']"));
		this.viewLeave_checkbox.click();
	}

	public void selectregsCust() {
		this.waitForElementToVisible(By.xpath("//input[@id='16']"));
		this.regCust_checkbox.click();
	}

	public void selectsearchCust() {
		this.waitForElementToVisible(By.xpath("//input[@id='17']"));
		this.searchCust_checkbox.click();
	}

	public void selectdownloadESignature() {
		this.waitForElementToVisible(By.xpath("//input[@id='40']"));
		this.downloadESignature_checkbox.click();
	}

	public void selectloyalty() {
		this.waitForElementToVisible(By.xpath("//input[@id='51']"));
		this.loyalty_checkbox.click();
	}

	public void selectdeRegsCust() {
		this.waitForElementToVisible(By.xpath("//input[@id='32']"));
		this.deRegCust_checkbox.click();
	}

	public void selectIPREgisterAccess() {
		this.waitForElementToVisible(By.id("isIPReg")).click();
	}

	public void selectcoverHistry() {
		this.waitForElementToVisible(By.xpath("//input[@id='49']"));
		this.coverHist_checkbox.click();
	}

	public void selectchangeDeductionMode() {
		this.waitForElementToVisible(By.xpath("//input[@id='48']"));
		this.changeDeductionMode_checkbox.click();
	}

	public void selecteditSms() {
		this.waitForElementToVisible(By.xpath("//input[@id='22']"));
		this.editSms_checkbox.click();
	}

	public void selectclaimSearch() {
		this.waitForElementToVisible(By.xpath("//input[@id='43']"));
		this.claimSearch_checkbox.click();
	}

	public void selectaddRole() {
		this.waitForElementToVisible(By.xpath("//input[@id='46']"));
		this.addRole_checkbox.click();
	}

	public void selectviewRole() {
		this.waitForElementToVisible(By.xpath("//input[@id='47']"));
		this.viewRole_checkbox.click();
	}

	public void selectCustReport() {
		this.waitForElementToVisible(By.xpath("//input[@id='27']"));
		this.custReport_checkbox.click();
	}

	public void selectAgentReport() {
		this.waitForElementToVisible(By.xpath("//input[@id='33']"));
		this.agentReport_checkbox.click();
	}

	public void selectDeductionReport() {
		this.waitForElementToVisible(By.xpath("//input[@id='50']"));
		this.deductionReport_checkbox.click();
	}

	public void selectAdminConfig() {
		this.waitForElementToVisible(By.xpath("//input[@id='9']"));
		this.adminConfig_checkbox.click();
	}

	public String getValidationMessage() {
		return this.waitForElementToVisible(By.id("validationMessages"))
				.getText();
	}
}
