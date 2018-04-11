package com.milvik.mip.dbqueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.milvik.mip.pageutil.MIP_CustomerManagementPage;
import com.milvik.mip.utility.MIP_DataBaseConnection;
import com.milvik.mip.utility.MIP_Logging;
import com.milvik.mip.utility.MIP_ReadPropertyFile;

public class MIP_RegisterCustomer_Queries {
	static ResultSet result;
	static Logger logger;
	static {
		logger = MIP_Logging.logDetails("MIP_RegisterCustomer_Queries");
	}
	static Map<String, String> custDetails = new HashMap<String, String>();
	static Map<String, String> offer_subs = new HashMap<String, String>();
	static Map<String, String> ben_info = new HashMap<String, String>();
	static String sms;

	public static Map<String, String> getCustomerDetails(String msisdn) {
		logger.info("Executing getCustomerDetails query");
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("select cd.fname ,cd.sname,cd.dob,cd.age,cd.gender,cd.implied_age from customer_details cd where msisdn="
							+ msisdn + ";");
			result.next();

			custDetails.put("fname", result.getString("fname"));
			custDetails.put("sname", result.getString("sname"));
			if (result.getString("dob") == null) {
				custDetails.put("dob", "");
			} else {
				custDetails.put("dob", result.getString("dob"));
			}
			custDetails.put("age", result.getString("age"));
			custDetails.put("implied_age", result.getString("implied_age"));
			if (result.getString("gender").equalsIgnoreCase("M")) {
				custDetails.put("gender", "Male");
			} else {
				custDetails.put("gender", "Female");
			}

		} catch (SQLException e) {
			logger.error(
					"Error while executing the getCustomerDetails queries", e);
		}
		return custDetails;
	}

	public static Map<String, String> getRelativeInfo(String msisdn,
			String product_name) {
		logger.info("Executing getRelativeInfo query");
		Map<String, String> rel_info = new HashMap<String, String>();
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("select id.fname,id.sname,"
							+ " id.dob ,id.age,id.cust_relationship,ins_msisdn as msisdn from customer_details cd join customer_subscription cs on cd.cust_id=cs.cust_id"
							+ " join insured_relative_details id on (id.cust_id=cs.cust_id and id.offer_id=(select product_id from product_details where product_name='"
							+ product_name
							+ "'))"
							+ " where cs.cust_id=(select cust_id from customer_details where msisdn="
							+ msisdn + ");");
			result.next();

			rel_info.put("fname", result.getString("fname"));
			rel_info.put("sname", result.getString("sname"));
			if (result.getString("dob") == null) {
				rel_info.put("dob", "");
			} else {
				rel_info.put("dob", result.getString("dob"));
			}
			rel_info.put("age", result.getString("age"));
			rel_info.put("cust_relationship",
					result.getString("cust_relationship"));
			if (result.getString("msisdn") == null) {
				rel_info.put("msisdn", "");
			} else {
				rel_info.put("msisdn", result.getString("msisdn"));
			}

		} catch (SQLException e) {
			logger.error("Error while executing the getRelativeInfo queries", e);
		}
		return rel_info;
	}

	public static String getSMSText(String msisdn, String product_name) {
		logger.info("Executing getSMSText query");
		try {
			if (product_name
					.equalsIgnoreCase(MIP_CustomerManagementPage.XTRALIFE)) {
				result = MIP_DataBaseConnection.st
						.executeQuery("select sms_text from sms_in_queue where sms_msisdn="
								+ msisdn
								+ " and sms_template_name like 'dashboard_tigo_%xl%_reg_success%';");
			} else if (product_name
					.equalsIgnoreCase(MIP_CustomerManagementPage.HOSPITAL)) {
				result = MIP_DataBaseConnection.st
						.executeQuery("select sms_text from sms_in_queue where sms_msisdn="
								+ msisdn
								+ " and sms_template_name like 'dashboard_%hp%_reg_success%';");

			} else if (product_name
					.equalsIgnoreCase(MIP_CustomerManagementPage.IP)) {
				result = MIP_DataBaseConnection.st
						.executeQuery("select sms_text from sms_in_queue where sms_msisdn="
								+ msisdn
								+ " and sms_template_name like 'dashboard_%ip%_reg_success%';");
			}

			result.next();
			if (result.getString("sms_text") == null) {
				sms = "";
			} else {
				sms = result.getString("sms_text");
			}

		} catch (SQLException e) {
			logger.error("Error while executing the getSMSText queries", e);
		}
		return sms;
	}

	public static String getBenSMSText(String msisdn) {
		logger.info("Executing getBenSMSText query");
		try {

			result = MIP_DataBaseConnection.st
					.executeQuery("select sms_text from sms_in_queue where sms_msisdn="
							+ msisdn
							+ " and sms_template_name='beneficiary_sms' order by sms_queue_id desc limit 1;");

			result.next();
			if (result.getString("sms_text") == null) {
				sms = "";
			} else {
				sms = result.getString("sms_text");
			}

		} catch (SQLException e) {
			logger.error("Error while executing the getBenSMSText queries", e);
		}
		return sms;
	}

	public static String getCoverDetailsIP(String msisdn) {
		logger.info("Executing getCoverDetailsIP query");

		String cover_details = "";
		try {

			result = MIP_DataBaseConnection.st
					.executeQuery("select pd.product_cover,pd.cover_charges from product_cover_details pd join customer_subscription cs"
							+ " on cs.product_level_id=pd.product_level_id and cs.product_id=4 where cs.cust_id=(select cust_id from customer_details where msisdn="
							+ msisdn + ");");

			result.next();
			if (result.getString("product_cover").equals("3000.00")
					&& result.getString("cover_charges").equals("5.00")) {
				cover_details = MIP_CustomerManagementPage.IP_COVER_ONE;
			} else if (result.getString("product_cover").equals("4000.00")
					&& result.getString("cover_charges").equals("7.00")) {
				cover_details = MIP_CustomerManagementPage.IP_COVER_TWO;
			} else if (result.getString("product_cover").equals("5000.00")
					&& result.getString("cover_charges").equals("9.00")) {
				cover_details = MIP_CustomerManagementPage.IP_COVER_THREE;
			}

		} catch (SQLException e) {
			logger.error("Error while executing the getCoverDetailsIP queries",
					e);
		}
		return cover_details;
	}

	public static boolean getdeRegCustInfoforHP(String msisdn) {
		logger.info("Executing getdeRegCustInfo query");
		int count = 0;
		try {

			result = MIP_DataBaseConnection.st
					.executeQuery("select * from customer_subscription where cust_id=(select cust_id from customer_details where msisdn="
							+ msisdn
							+ " ) and product_id=(select product_id from product_details where product_name='Hospitalization');");
			while (result.next()) {
				count++;
			}
			if (count == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			logger.error("Error while executing the getCoverDetailsIP queries",
					e);
		}
		return false;

	}

	public static Map<String, String> getCustInfoforHPinCancellation(
			String msisdn) {
		logger.info("Executing getCustInfoforHPinCancellation query");
		Map<String, String> cancel_details = new HashMap<String, String>();
		try {

			result = MIP_DataBaseConnection.st
					.executeQuery("select fname,sname,age,dob,gender,hpirfname,hpirsname,hpirage,hpirdob,hpcust_relationship from bima_cancellations where cust_id=(select cust_id from customer_details where msisdn="
							+ msisdn + ")");
			result.next();
			if (result.getString("fname") == null)
				cancel_details.put("fname", "");
			else
				cancel_details.put("fname", result.getString("fname"));
			if (result.getString("sname") == null)
				cancel_details.put("sname", "");
			else
				cancel_details.put("sname", result.getString("sname"));
			if (result.getString("age") == null)
				cancel_details.put("age", "");
			else
				cancel_details.put("age", result.getString("age"));
			if (result.getString("dob") == null)
				cancel_details.put("dob", "");
			else
				cancel_details.put("dob", result.getString("dob"));
			if (result.getString("gender") == null)
				cancel_details.put("gender", "");
			else
				cancel_details.put("gender", result.getString("gender"));
			if (result.getString("hpirfname") == null)
				cancel_details.put("hpirfname", "");
			else
				cancel_details.put("hpirfname", result.getString("hpirfname"));
			if (result.getString("hpirsname") == null)
				cancel_details.put("hpirsname", "");
			else
				cancel_details.put("hpirsname", result.getString("hpirsname"));
			if (result.getString("hpirdob") == null)
				cancel_details.put("hpirdob", "");
			else
				cancel_details.put("hpirdob", result.getString("hpirdob"));
			if (result.getString("hpcust_relationship") == null)
				cancel_details.put("hpcust_relationship", "");
			else
				cancel_details.put("hpcust_relationship",
						result.getString("hpcust_relationship"));
			if (result.getString("hpirage") == null)
				cancel_details.put("hpirage", "");
			else
				cancel_details.put("hpirage", result.getString("hpirage"));

		} catch (SQLException e) {
			logger.error(
					"Error while executing the getCustInfoforHPinCancellation queries",
					e);
		}
		return cancel_details;

	}

	public static boolean getdeRegBenInfoforHP(String msisdn) {
		logger.info("Executing getdeRegBenInfoforHP query");
		int count = 0;
		try {

			result = MIP_DataBaseConnection.st
					.executeQuery("	select * from insured_relative_details where cust_id=(select cust_id from customer_details where msisdn="
							+ msisdn
							+ ") and offer_id=(select product_id from product_details where product_name='Hospitalization')");
			while (result.next()) {
				count++;
			}
			if (count == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			logger.error(
					"Error while executing the getdeRegBenInfoforHP queries", e);
		}
		return false;

	}

	public static String getDeRegisterHPSMSText(String msisdn) {
		logger.info("Executing getBenSMSText query");
		try {

			result = MIP_DataBaseConnection.st
					.executeQuery("select sms_text from sms_in_queue where sms_msisdn="
							+ msisdn
							+ " and sms_template_name='dereg_move_hp_to_ip' order by sms_queue_id desc limit 1;");

			result.next();
			if (result.getString("sms_text") == null) {
				sms = "";
			} else {
				sms = result.getString("sms_text");
			}

		} catch (SQLException e) {
			logger.error("Error while executing the getBenSMSText queries", e);
		}
		return sms;
	}

	public static void main(String[] args) {
		MIP_ReadPropertyFile.loadProperty("config");
		MIP_DataBaseConnection.connectToDatabase();
		getCustInfoforHPinCancellation("0548579105");
	}
}
