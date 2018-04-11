package com.milvik.mip.dataprovider;

import org.testng.annotations.DataProvider;

public class MIP_ListUser_TestData {
	/**
	 * Mobile Number
	 * 
	 * @return
	 */
	@DataProvider(name = "ListUserData")
	public static String[][] listUserData() {
		//String[][] data = { { "0573370082" } };
		 String[][] data = { { "0573370082" }, { "0200678018" } };
		return data;
	}
}
