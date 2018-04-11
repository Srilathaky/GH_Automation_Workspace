package com.milvik.mip.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.DataProvider;

import com.milvik.mip.utility.MIP_XLOperation;

/**
 * @author srilatha_yajnanaraya
 *
 */
public class MIP_RegCust_TestData {

	@DataProvider(name = "NegativeTest")
	public String[][] regCustNegative() {
		String error_msg1 = "Available Products  :  : Please select a CheckBox"
				+ "First Name : Field should not be empty."
				+ "Surname : Field should not be empty."
				+ "Customer : Either Age or Date of Birth required to be filled."
				+ "Gender : Please select an option.";
		String error_msg2 = "First Name : Field should not be empty."
				+ "Surname : Field should not be empty."
				+ "Customer : Either Age or Date of Birth required to be filled."
				+ "Gender : Please select an option."
				+ "Relationship Name : Please choose a value from the Drop down."
				+ "Insured Relative First Name : Field should not be empty."
				+ "Insured Relative Surname : Field should not be empty."
				+ "Insured Relative : Either Age or Date of Birth required to be filled."
				+ "Mobile Number  :  : Field should not be empty.";
		String error_msg3 = "First Name : Field should not be empty."
				+ "Surname : Field should not be empty."
				+ "Customer : Either Age or Date of Birth required to be filled."
				+ "Gender : Please select an option."
				+ "Insured Relative First Name : Field should not be empty."
				+ "Insured Relative Surname : Field should not be empty."
				+ "Insured Relative : Either Age or Date of Birth required to be filled."
				+ "Mobile Number  :  : Field should not be empty.";
		String error_msg4 = "First Name : Field should not be empty."
				+ "Surname : Field should not be empty."
				+ "Customer : Either Age or Date of Birth required to be filled."
				+ "Gender : Please select an option."
				+ "Offer Cover Level  :  : Please choose a value from the Drop down."
				+ "Nominee First Name : Field should not be empty."
				+ "Nominee Surname : Field should not be empty."
				+ "Nominee Age : Field should not be empty."
				+ "Mobile Number  :  : Field should not be empty.";
		String data[][] = { { "0255588888", error_msg1, error_msg2, error_msg3,
				error_msg4 } };
		return data;
	}

	/**
	 * Mobile Number
	 * 
	 * @return
	 */
	@DataProvider(name = "Customer_Registartion_validateobject")
	public String[][] Customer_Registartion_validateobject() {
		String data[][] = { { "0558888880" } };
		return data;
	}

	/**
	 * msisdn,fname, sname, String dob, String age, String gender, xl_relation,
	 * xl_rel_fname, xl_rel_sname, xl_rel_dob, xl_age, xl_inform_ben,
	 * xl_rel_msisdn, hp_relation, hp_fname, hp_sname, hp_dob, hp_age,
	 * inform_ben_hp, hp_rel_mobilenum, offer_cover, nominee_fname,
	 * nominee_sname, nominee_age, inform_nominee, nominee_mobilenum,errormsg
	 */
	@DataProvider(name = "registerAllProduct")
	public String[][] customer_Registartion_AllProduct() {
		String data[][] = { { "0540897109", "Ben", "ton", "20/10/1974", "",
				"Female", "Son", "xtra", "life", "10/08/1982", "", "no", "",
				"Daughter", "hp test", "One", "02/02/1990", "", "yes",
				"0278585858", "GHC 5 - GHC 3,000 & 45/night", "IP test", "one",
				"56", "No", "",
				"Customer deactivated Hospitalization and may do a reactivation" } };
		return data;
	}

	/**
	 * msisdn,fname, sname, String dob, String age, String gender,"error msg",
	 * xl_relation, xl_rel_fname, xl_rel_sname, xl_rel_dob, xl_age,
	 * xl_inform_ben, xl_rel_msisdn,error_msg, hp_relation, hp_fname, hp_sname,
	 * hp_dob, hp_age, inform_ben_hp, hp_rel_mobilenum,error_msg, offer_cover,
	 * nominee_fname, nominee_sname, nominee_age, inform_nominee,
	 * nominee_mobilenum,errormsg
	 */
	@DataProvider(name = "agevalidation")
	public String[][] ageValidation() {
		String data[][] = {
				{ "0540500105", "Ben", "ton", "75", "Female",
						"Customer : Age should be between 18 and 69", "Son",
						"xtra", "life", "79", "no", "",
						"Insured Relative : Age should be between 18 and 69.",
						"Daughter", "hp test", "One", "78", "yes",
						"0278585858",
						"Insured Relative : Age should be between 05 and 69.",
						"GHC 5 - GHC 3,000 & 45/night", "IP test", "one", "70",
						"No", "","Customer : Age should be between 18 and 59 for Income Protection product.",
						"Nominee Age : Age should be between 18 and 59 for Income Protection product." },
				{ "0540570105", "Ben", "ton", "05", "Female",
						"Customer : Age should be between 18 and 69", "Son",
						"xtra", "life", "17", "no", "",
						"Insured Relative : Age should be between 18 and 69.",
						"Daughter", "hp test", "One", "03", "yes",
						"0278585858",
						"Insured Relative : Age should be between 05 and 69.",
						"GHC 5 - GHC 3,000 & 45/night", "IP test", "one", "75",
						"No", "","Customer : Age should be between 18 and 59 for Income Protection product.",
						"Nominee Age : Age should be between 18 and 59 for Income Protection product." } };
		return data;
	}

	@DataProvider(name = "MIP_Customer_Registration_Positive_Scenario")
	public String[][] registerNewCustomer() {
		return storeCellData("MIP_Customer_Registration_Positive_Scenario");
	}

	@DataProvider(name = "Customer_Registartion_NegativeScenarios")
	public String[][] Negative_scenario() {
		return storeCellData("MIP_Customer_Registration_Negative_Scenarios");
	}

	public static String[][] storeCellData(String filename) {
		Sheet s = MIP_XLOperation.loadXL(filename);
		int numRows = MIP_XLOperation.getNumRows();
		int numcell = MIP_XLOperation.getNumCell();
		int rowcount = 0;
		List<String> DOB_col = new ArrayList<String>();
		for (int i = 0; i < numcell; i++) {

			if (s.getRow(0).getCell(i).getStringCellValue().toUpperCase()
					.contains("DOB")) {
				DOB_col.add(i + "");
			}
		}
		String[][] data = new String[numRows - 1][numcell];
		for (int i = 1; i < numRows; i++) {
			Row r = s.getRow(i);
			for (int j = 0; j < numcell; j++) {
				if (r.getCell(j) == null) {
					data[rowcount][j] = "";
				} else {
					try {
						data[rowcount][j] = r.getCell(j).getStringCellValue();
					} catch (Exception ex) {
						if (DOB_col.contains(j + "")) {
							DataFormatter df = new DataFormatter();
							data[rowcount][j] = df
									.formatCellValue(r.getCell(j));
						} else {
							data[rowcount][j] = new Double(r.getCell(j)
									.getNumericCellValue()).longValue() + "";
						}

					}
				}

			}
			rowcount++;
		}
		return data;
	}

}
