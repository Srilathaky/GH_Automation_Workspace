package com.milvik.mip.dataprovider;

import org.testng.annotations.DataProvider;

public class MIP_ClaimManagement_TestData {
	/**
	 * Data in the order msisdn,error message
	 * 
	 * @return
	 */
	@DataProvider(name = "claimmsisdnvalidation")
	public static String[][] claimmsisdnvalidation() {
		String[][] data = {
				{ "0278515540", "Mobile Number : Entered value does not exist." },
				{ "027851", "Mobile Number : Enter a valid number." },
				{ "sdgsdg", "Mobile Number : Enter a valid number." } };
		return data;
	}

	/**
	 * Data in the order msisdn
	 * 
	 * @return
	 */
	@DataProvider(name = "claimmsisdn")
	public static String[][] claimmsisdn() {
		String[][] data = { { "0269999999" }, { "0278555550" },
				{ "0551234568" }, { "0278515545" }, { "0200000009" } };
		return data;
	}

	/**
	 * Data in the order field need ti be cleared, msisdn,error message
	 * 
	 * @return
	 */
	@DataProvider(name = "fieldValidation")
	public static String[][] fieldValidation() {
		String[][] data = {
				{
						"Nominee Information for Income Protection Policy",
						"0200000009",

						"Nominee First Name : Field should not be empty."
								+ "Nominee Surname : Field should not be empty."
								+ "Nominee Age : Age should be between 18 and 59 for Income Protection product."
								+ "Nominee MSISDN : Field should not be empty." },
				{
						"Insured Relative Information for HP",
						"0559656465",
						"Insured Relative First Name : Field should not be empty."
								+ "Insured Relative Surname : Field should not be empty."
								+ "Insured Relative Age : Either Age or Date of Birth required to be filled."
								+ "Relationship Name : Please choose a value from the Drop down."
								+ "Nominee MSISDN : Enter a valid number." },
				{
						"Customer Information",
						"0278515545",
						"First Name : Field should not be empty."
								+ "Surname : Field should not be empty."
								+ "Customer : Either Age or Date of Birth required to be filled."
								+ "Nominee MSISDN : Field should not be empty." },
				{
						"Insured Relative Information",
						"0269999999",
						"Relationship Name : Please choose a value from the Drop down."
								+ "Insured Relative First Name : Field should not be empty."
								+ "Insured Relative Surname : Field should not be empty."
								+ "Insured Relative Age : Either Age or Date of Birth required to be filled."
								+ "Nominee MSISDN : Field should not be empty." } };

		return data;
	}

	/**
	 * Data in the order msisdn,product to be edit,fields of customer
	 * details(Cust_fname,cust_sname,cust_dob,gender) Insured_relative
	 * Info(Relationship,fname,sname,age,dob,MobileNumber) Nominee
	 * Information:(Fname,Snmae,age,mobile number)Hp
	 * Relative(relation,fname,sname,age,dob,mobilenum)
	 * 
	 * @return
	 */
	@DataProvider(name = "editInfo")
	public static String[][] editInfo() {
		String[][] data = {
				{ "0278546699", "test", "edit one", "", "02/03/1982", "Male",
						"Father", "changed", "xtra vc", "23", "", "", "", "",
						"", "", "Son", "changed", "hpname", "", "03/06/1986",
						"0278850585" },
				{ "0200000009", "testing", "edit one", "", "01/03/1983",
						"Male", "Son", "changed ch", "xtrad", "55", "", "", "",
						"", "", "", "Son", "changed", "hpd name", "",
						"03/07/1986", "0278808585" },
				{ "0200000009", "test", "edidt one", "", "09/02/1985", "Male",
						"Mother", "Relaives", "tests", "", "10/09/1985",
						"0274087412", "Nominees", "tesst ch", "54",
						"0570101008", "", "", "", "", "", "" } };

		return data;
	}
}
