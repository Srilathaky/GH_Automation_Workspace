package com.milvik.mip.dbqueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.milvik.mip.pageutil.MIP_CustomerManagementPage;
import com.milvik.mip.utility.MIP_DataBaseConnection;
import com.milvik.mip.utility.MIP_Logging;
import com.milvik.mip.utility.MIP_ReadPropertyFile;

public class MIP_Homepage_CustomerStatistics_Queries {
	static ResultSet result;
	static Logger logger;
	static {
		logger = MIP_Logging
				.logDetails("MIP_Homepage_CustomerStatistics_Queries");
	}

	public static String getConfirmedCust() {
		String count = "";
		logger.info("Executing getConfirmedCust query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM offer_subscription where is_confirmed=1;");
			result.next();
			count = result.getString("count");
		} catch (SQLException e) {
			logger.error("Error while executing the getConfirmedCust queries",
					e);
		}
		return count;
	}

	public static String getUnconfirmedCust() {
		String count = "";
		logger.info("Executing getUnconfirmedCust query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM offer_subscription where is_confirmed=0;");
			result.next();
			count = result.getString("count");
		} catch (SQLException e) {
			logger.error(
					"Error while executing the getUnconfirmedCust queries", e);
		}
		return count;
	}

	public static String getSelfRegCount() {
		String count = "";
		logger.info("Executing getSelfRegCount query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM offer_subscription where created_by=2 ;");
			result.next();
			count = result.getString("count");
		} catch (SQLException e) {
			logger.error("Error while executing the getSelfRegCount queries", e);
		}
		return count;
	}

	public static String getCustRegCount() {
		String count = "";
		logger.info("Executing getCustRegCount query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM offer_subscription where created_by!=2 ;");
			result.next();
			count = result.getString("count");
		} catch (SQLException e) {
			logger.error("Error while executing the getCustRegCount queries", e);
		}
		return count;
	}

	public static String getCustConfCount() {
		String count = "";
		logger.info("Executing getCustConfCount query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM offer_subscription where created_by!=2 and is_confirmed=1;");
			result.next();
			count = result.getString("count");
		} catch (SQLException e) {
			logger.error("Error while executing the getCustConfCount queries",
					e);
		}
		return count;
	}

	public static String getConfCust_diffRole(String username) {
		String count = "";
		logger.info("Executing getConfCust_diffRole query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM offer_subscription where is_confirmed=1 and "
							+ "created_by=(SELECT user_id FROM user_details where user_uid='"
							+ username + "');");
			result.next();
			count = result.getString("count");
		} catch (SQLException e) {
			logger.error(
					"Error while executing the getConfCust_diffRole queries", e);
		}
		return count;
	}

	public static String getUnconfCust_diffRole(String username) {
		String count = "";
		logger.info("Executing getUnconfCust_diffRole query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM offer_subscription where is_confirmed!=1 and "
							+ "created_by=(SELECT user_id FROM user_details where user_uid='"
							+ username + "');");
			result.next();
			count = result.getString("count");
		} catch (SQLException e) {
			logger.error(
					"Error while executing the getUnconfCust_diffRole queries",
					e);
		}
		return count;
	}

	public static String userDetails(String username) {
		String details = null;
		logger.info("Executing userDetails query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("select concat(fname,' ',sname) as name from  user_details where user_uid='"
							+ username + "';");
			result.next();
			details = result.getString("name");
		} catch (SQLException e) {
			logger.error("Error while executing the userDetails queries", e);
		}
		return details;
	}

	public static List<String> getXLHomePageStatistics(String username,
			String productname) {
		String count = "";
		List<String> details = new ArrayList<String>();
		logger.info("Executing getHomePageStatistics query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM customer_subscription where reg_by=(select user_id from user_details where user_uid='"
							+ username
							+ "') and product_id=(select product_id from product_details where product_name='"
							+ productname
							+ "') and reg_date between  concat(year(CURRENT_DATE()),'-',Month(CURRENT_DATE()),'-16')  and  concat(year(CURRENT_DATE()),'-',Month(CURRENT_DATE())+1,'-15') and is_confirmed=1;");
			result.next();
			count = result.getString("count");
			details.add(count);

			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM customer_subscription where reg_by=(select user_id from user_details where user_uid='"
							+ username
							+ "') and product_id=(select product_id from product_details where product_name='"
							+ productname
							+ "') and reg_date between  concat(year(CURRENT_DATE()),'-',Month(CURRENT_DATE()),'-16')  and  concat(year(CURRENT_DATE()),'-',Month(CURRENT_DATE())+1,'-15') and is_confirmed=0;");
			result.next();
			count = result.getString("count");
			details.add(count);

			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM customer_subscription where reg_by=(select user_id from user_details where user_uid='"
							+ username
							+ "') and product_id=(select product_id from product_details where product_name='"
							+ productname
							+ "') and conf_date between concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE()),'-16') and concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE())+1,'-15') and is_confirmed=1 and is_fully_deducted=1 and fully_deducted_date between concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE()),'-16') and concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE())+1,'-15');");
			result.next();
			count = result.getString("count");
			details.add(count);

			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*)/2 as count FROM customer_subscription where reg_by=(select user_id from user_details where user_uid='"
							+ username
							+ "') and product_id=(select product_id from product_details where product_name='"
							+ productname
							+ "') and conf_date between concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE()),'-16') and concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE())+1,'-15') and is_confirmed=1 and is_partially_deducted=1 and partially_deducted_date between concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE()),'-16') and concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE())+1,'-15');");
			result.next();
			count = result.getString("count");
			details.add(count);

			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT (SELECT count(*)/2  FROM customer_subscription where reg_by=(select user_id from user_details where user_uid='"
							+ username
							+ "') and product_id=(select product_id from product_details where product_name='"
							+ productname
							+ "') and conf_date between concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE())-1,'-16') and concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE()),'-15') and is_confirmed=1 and is_partially_deducted=1 and partially_deducted_date between concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE()),'-16') and concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE())+1,'-15'))+(SELECT count(*) FROM customer_subscription where reg_by=(select user_id from user_details where user_uid='TA6') and product_id=(select product_id from product_details where product_name='Xtra-Life') and conf_date between concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE())-1,'-16') and concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE()),'-15') and is_confirmed=1 and is_fully_deducted=1 and fully_deducted_date between concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE()),'-16') and concat(Year(CURRENT_DATE()),'-',Month(CURRENT_DATE())+1,'-15')) as count;");
			result.next();
			count = result.getString("count");
			details.add(count);
		} catch (SQLException e) {
			logger.error(
					"Error while executing the getHomePageStatistics queries",
					e);
		}
		return details;
	}

	public static void main(String[] args) {
		MIP_ReadPropertyFile.loadProperty("config");
		MIP_DataBaseConnection.connectToDatabase();
		getXLHomePageStatistics("TA6", MIP_CustomerManagementPage.IP);
	}
}
