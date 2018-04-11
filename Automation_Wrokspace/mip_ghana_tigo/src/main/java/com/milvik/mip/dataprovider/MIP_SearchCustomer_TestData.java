package com.milvik.mip.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.DataProvider;

import com.milvik.mip.utility.MIP_XLOperation;

public class MIP_SearchCustomer_TestData {
	/**
	 * This method will read the data from specified XL
	 * 
	 * @return
	 */
	@DataProvider(name = "SearchCustomerNegative")
	public static String[][] searchCustomerNegative() {
		Sheet s = MIP_XLOperation
				.loadXL("MIP_Search_Customer_Negative_Scenarios");
		int num_row = MIP_XLOperation.getNumRows();
		int num_cell = MIP_XLOperation.getNumCell();
		int rowcount = 0;
		String[][] data = new String[num_row - 1][num_cell];
		for (int i = 1; i < num_row; i++) {
			Row r = s.getRow(i);
			for (int j = 0; j < r.getLastCellNum(); j++) {
				if (r.getCell(j) == null) {
					data[rowcount][j] = "";
				} else {
					try {
						data[rowcount][j] = r.getCell(j).getStringCellValue();
					} catch (Exception e) {
						data[rowcount][j] = new Double(r.getCell(j)
								.getNumericCellValue()).longValue() + "";
					}
				}
			}
			rowcount++;
		}
		return data;
	}

	@DataProvider(name = "SearchCustomerPositive")
	public static String[][] searchCustomerPositive() {
		return storeData("MIP_SearchCustomer_Positive_Scenarios");
	}

	@DataProvider(name = "ModifyCustomerNegative")
	public static String[][] modifyCustomerNegative() {
		return storeData("MIP_ModifyCustomer_Negative_Scenarios");
	}

	public static String[][] storeData(String filename) {

		Sheet s = MIP_XLOperation.loadXL(filename);
		int numRows = MIP_XLOperation.getNumRows();
		int numcell = MIP_XLOperation.getNumCell();
		int rowcount = 0;
		int DOB_col = 0;
		for (int i = 0; i < numcell; i++) {

			if (s.getRow(0).getCell(i).getStringCellValue()
					.equalsIgnoreCase("DOB")) {
				DOB_col = i;
				break;
			}
		}
		String[][] data = new String[numRows - 1][numcell];
		for (int i = 1; i < numRows; i++) {
			Row r = s.getRow(i);
			for (int j = 0; j < r.getLastCellNum(); j++) {
				if (r.getCell(j) == null) {
					data[rowcount][j] = "";
				} else {
					try {
						data[rowcount][j] = r.getCell(j).getStringCellValue();
					} catch (Exception ex) {
						if (j == DOB_col) {
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

	/**
	 * fname,sname,msisdn,cust_id
	 * 
	 * @return
	 */
	@DataProvider(name = "searchCriteria")
	public static String[][] searchData() {
		String[][] data = { { "sun", "sha", "0272897456", "112" } };
		return data;
	}

	@DataProvider(name = "searchAndValidateCust")
	public static String[][] searchAndValidateCust() {
		String[][] data = { { "0278545666" }, { "0278584569" },
				{ "0278545851" } };
		return data;
	}

	/**
	 * testcase,User Name,Password,Name,MSISDN
	 * 
	 * @return
	 */
	@DataProvider(name = "readyOnlyCheck")
	public static String[][] readOnlyCheck() {
		String[][] data = { { "TC188", "DASHBOARD", "Tele123!", "25185883" } };
		return data;
	}

	/**
	 * User NAme,Password,msisdn
	 * 
	 * @return
	 */
	@DataProvider(name = "readyOnlyofferinfo")
	public static String[][] readOnlyOfferInfoForAllRoles() {
		String[][] data = { { "DASHBOARD", "Tele123!", "25185883" },
				{ "TA10", "Tele123!", "25185883" } };
		return data;
	}

	/**
	 * msisdn,product,fname,sname,DOB,Gender,Insured Relative
	 * Information:(Relationship,Fname,sname,DOB,inform beneficiary,mobile
	 * number,deduction mode.)
	 */
	@DataProvider(name = "registerAndSearch")
	public static String[][] registerAndSearchCust() {
		String[][] data = { { "0277778885", "Xtra-Life", "search", "one",
				"19/05/1986", "Male", "Father", "Relative", "one",
				"10/10/1984", "Yes", "0278545852", "Daily Deduction" } };
		return data;
	}

	@DataProvider(name = "changeDetailsTest")
	public static String[][] searchCustomerChangeDetails() {
		return storeCellData("MIP_SearchCustomer_Modify_Details_Positive_Scenarios");
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
