package com.milvik.mip.dbqueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.milvik.mip.utility.MIP_DataBaseConnection;
import com.milvik.mip.utility.MIP_Logging;

public class MIP_ClaimManagement_Queries {
	static ResultSet result;
	static Logger logger;
	static {
		logger = MIP_Logging.logDetails("MIP_ClaimManagement_Queries");
	}

	public static List<String> getCustomerNameAndAge(String msisdn) {
		List<String> custdetails = new ArrayList<String>();
		logger.info("Executing  getCustomerNameAndAge query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT concat(fname,' ',sname) as name,age,implied_age FROM customer_details where  msisdn="
							+ msisdn + ";");
			result.next();
			custdetails.add(result.getString("name"));
			custdetails.add(result.getString("age"));
			custdetails.add(result.getString("implied_age"));

		} catch (SQLException e) {
			logger.error(
					"Error while executing the getCustomerNameAndAge queries",
					e);
		}
		return custdetails;
	}

	public static List<String> getCustomerInformation(String msisdn) {
		List<String> custdetails = new ArrayList<String>();
		logger.info("Executing  getCustomerInformation query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT fname,sname,dob,age,gender,implied_age FROM customer_details where  msisdn="
							+ msisdn + ";");
			result.next();
			custdetails.add(result.getString("fname"));
			custdetails.add(result.getString("sname"));
			if (result.getString("dob") == null) {
				custdetails.add("");
			} else {
				custdetails.add(result.getString("dob"));
			}
			custdetails.add(result.getString("age"));
			if (result.getString("gender") == null) {
				custdetails.add(result.getString(""));
			} else {
				custdetails.add(result.getString("gender"));
			}
			custdetails.add(result.getString("implied_age"));

		} catch (SQLException e) {
			logger.error(
					"Error while executing the getCustomerInformation queries",
					e);
		}
		return custdetails;
	}

	public static List<String> getCustomerRelaiveDetails(String msisdn,
			String product) {
		List<String> relativedetails = new ArrayList<String>();
		logger.info("Executing  getCustomerRelaiveDetails query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT cust_relationship,fname,sname,dob,age,ins_msisdn FROM insured_relative_details where cust_id=(select cust_id from customer_details where msisdn="
							+ msisdn
							+ ")and cust_relationship is not null and offer_id=(select product_id from product_details where product_name='"
							+ product + "');");
			result.next();
			relativedetails.add(result.getString("cust_relationship"));
			relativedetails.add(result.getString("fname"));
			relativedetails.add(result.getString("sname"));
			if (result.getString("dob") == null) {
				relativedetails.add("");
			} else {
				relativedetails.add(result.getString("dob"));
			}
			relativedetails.add(result.getString("age"));
			if (result.getString("ins_msisdn") == null)
				relativedetails.add("");
			else
				relativedetails.add(result.getString("ins_msisdn"));

		} catch (SQLException e) {
			logger.error(
					"Error while executing the getCustomerRelaiveDetails queries",
					e);
		}
		return relativedetails;
	}

	public static List<String> getSubscribedProduct(String msisdn) {
		List<String> productdetails = new ArrayList<String>();
		logger.info("Executing  getSubscribedProduct query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT  pd.product_name FROM product_details pd join customer_subscription cs on cs.product_id=pd.product_id where cs.cust_id=(select cust_id from customer_details where msisdn="
							+ msisdn + ");");
			while (result.next()) {
				productdetails.add(result.getString("product_name"));
			}

		} catch (SQLException e) {
			logger.error(
					"Error while executing the getSubscribedProduct queries", e);
		}
		return productdetails;
	}

	public static List<String> getIncomeProtectionInformation(String msisdn) {
		List<String> relativedetails = new ArrayList<String>();
		logger.info("Executing  getIncomeProtectionNomineeInformation query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT fname,sname,dob,age,ins_msisdn FROM insured_relative_details "
							+ "where cust_id=(select cust_id from customer_details where msisdn="
							+ msisdn
							+ ") and cust_relationship is  null and offer_id=4;");
			result.next();
			relativedetails.add(result.getString("fname"));
			relativedetails.add(result.getString("sname"));
			relativedetails.add(result.getString("age"));
			if (result.getString("ins_msisdn") == null)
				relativedetails.add("");
			else
				relativedetails.add(result.getString("ins_msisdn"));

		} catch (SQLException e) {
			logger.error(
					"Error while executing the getIncomeProtectionNomineeInformation queries",
					e);
		}
		return relativedetails;
	}

	// //////////////
	public static List<String> getHospitalInformation(String msisdn) {
		List<String> relativedetails = new ArrayList<String>();
		logger.info("Executing  getHospitalInformation query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT fname,sname,dob,age,ins_msisdn FROM insured_relative_details "
							+ "where cust_id=(select cust_id from customer_details where msisdn="
							+ msisdn + ") and cust_relationship is  null;");
			result.next();
			relativedetails.add(result.getString("fname"));
			relativedetails.add(result.getString("sname"));
			relativedetails.add(result.getString("age"));
			relativedetails.add(result.getString("ins_msisdn"));

		} catch (SQLException e) {
			logger.error(
					"Error while executing the getIncomeProtectionNomineeInformation queries",
					e);
		}
		return relativedetails;
	}

	public static String getIncomeProtectionCoverDetails(String msisdn) {
		String product_cover = "";
		logger.info("Executing  getIncomeProtectionCoverDetails query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT pcd.product_cover FROM product_cover_details pcd join product_details pd on pcd.product_id=pd.product_id "
							+ "join customer_subscription cs on cs.product_level_id=pcd.product_level_id "
							+ "where  pd.product_name='Income Protection' and cust_id=(select cust_id from customer_details where msisdn='"
							+ msisdn + "'); ");
			result.next();
			return result.getString("product_cover");
		} catch (SQLException e) {
			logger.error(
					"Error while executing the getIncomeProtectionCoverDetails queries",
					e);
		}
		return product_cover;
	}

}