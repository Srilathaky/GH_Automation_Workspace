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

public class MIP_SearchCustomer_Queries {
	static ResultSet result;
	static Logger logger;
	static {
		logger = MIP_Logging.logDetails("MIP_SearchCustomer_Queries");
	}

	public static int countCustomerRecordByMSISDN(String msisdn) {
		logger.info("Executing countCustomerRecordByMSISDN query");
		int count = 0;
		int count1 = 0;
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM customer_subscription where cust_id=(select cust_id from customer_details where msisdn="
							+ msisdn + ") and is_deactivated=0;");
			result.next();
			count = result.getInt("count");
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM bima_cancellations where cust_id=(select cust_id from customer_details where msisdn="
							+ msisdn + ");");
			result.next();
			if (result.getString("count") != null) {
				count1 = result.getInt("count");
			}

		} catch (SQLException e) {
			logger.error(
					"Error while executing the countCustomerRecordByMSISDN queries",
					e);
		}
		return count + count1;
	}

	public static int countCustomerRecordByCustId(String cust_id) {
		logger.info("Executing countCustomerRecordByCustId query");
		int count = 0;
		int count1 = 0;
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM customer_subscription where cust_id="
							+ cust_id + " and is_deactivated=0;");
			result.next();
			count = result.getInt("count");
			result = MIP_DataBaseConnection.st
					.executeQuery("SELECT count(*) as count FROM bima_cancellations where cust_id="
							+ cust_id + ";");
			result.next();
			if (result.getString("count") != null) {
				count1 = result.getInt("count");
			}

		} catch (SQLException e) {
			logger.error(
					"Error while executing the countCustomerRecordByCustId queries",
					e);
		}
		return count + count1;
	}

	public static Map<String, String> getCustomerSearchData(String msisdn,
			String product) {
		logger.info("Executing getCustomerSearchData query");
		Map<String, String> searchDeatils = new HashMap<String, String>();
		String deRegisteredDate = "N/A";
		try {
			result = MIP_DataBaseConnection.st
					.executeQuery("Select concat(cd.fname,' ',cd.sname) as customer_name,concat(ud.fname,' ',ud.sname) as registered_by,cs.reg_date,cs.conf_date,cs.is_confirmed,cs.is_deactivated from customer_details cd join customer_subscription cs"
							+ " on cd.cust_id=cs.cust_id join user_details ud on ud.user_id=cs.reg_by "
							+ " where cd.cust_id=(select cust_id from customer_details where msisdn="
							+ msisdn
							+ ") and cs.product_id=(select product_id from product_details where product_name='"
							+ product + "');");
			result.next();
			searchDeatils.put("customer_name",
					result.getString("customer_name"));
			searchDeatils.put("registered_by",
					result.getString("registered_by"));
			searchDeatils.put("reg_date", result.getString("reg_date"));
			if (result.getString("conf_date") == null) {
				searchDeatils.put("conf_date", "");
			} else {
				searchDeatils.put("conf_date", result.getString("conf_date"));
			}
			if (result.getString("is_deactivated").equals("1")) {
				searchDeatils.put("Status", "De-registered");
				result = MIP_DataBaseConnection.st
						.executeQuery("SELECT pc_date FROM product_cancellations where cust_id=(select cust_id from customer_details where msisdn="
								+ msisdn
								+ ") and product_id=(select product_id from product_details"
								+ " where product_name='" + product + "');");
				result.next();
				searchDeatils.put("deRegisteredDate",
						result.getString("pc_date"));
			} else if (result.getString("is_confirmed").equals("0")) {
				searchDeatils.put("Status", "Not Confirmed");
				searchDeatils.put("deRegisteredDate", deRegisteredDate);
			} else if (result.getString("is_confirmed").equals("1")) {
				searchDeatils.put("Status", "Confirmed");
				searchDeatils.put("deRegisteredDate", deRegisteredDate);
			}

		} catch (SQLException e) {
			logger.error(
					"Error while executing the countCustomerRecordByCustId queries",
					e);
		}
		return searchDeatils;
	}

	public static Map<String, String> getDeactivatedRelativeInfo(String msisdn,
			String product_name) {
		logger.info("Executing getDeactivatedRelativeInfo query");
		Map<String, String> rel_info = new HashMap<String, String>();
		try {
			if (product_name
					.equalsIgnoreCase(MIP_CustomerManagementPage.XTRALIFE)) {
				result = MIP_DataBaseConnection.st
						.executeQuery("select irfname as fname,irsname as sname,irage as age,irdob as dob,cust_relationship,irMsisdn as msisdn from bima_cancellations where msisdn="
								+ msisdn + ";");

			} else if (product_name
					.equalsIgnoreCase(MIP_CustomerManagementPage.HOSPITAL)) {
				result = MIP_DataBaseConnection.st
						.executeQuery("select hpirfname as fname,hpirsname as sname,hpirage as age,hpirdob as dob,hpcust_relationship as cust_relationship,hp_msisdn as msisdn from bima_cancellations where  msisdn="
								+ msisdn + ";");

			} else if (product_name
					.equalsIgnoreCase(MIP_CustomerManagementPage.IP)) {
				result = MIP_DataBaseConnection.st
						.executeQuery("select kinfname as fname,kinsname as sname,kinage as age,kiMsisdn as msisdn from bima_cancellations where  msisdn="
								+ msisdn + ";");

			}

			result.next();
			if (result.getString("fname") == null)
				rel_info.put("fname", "");
			else
				rel_info.put("fname", result.getString("fname"));
			if (!(result.getString("sname") == null))
				rel_info.put("sname", result.getString("sname"));
			else
				rel_info.put("sname", "");
			if (product_name
					.equalsIgnoreCase(MIP_CustomerManagementPage.XTRALIFE)
					|| product_name
							.equalsIgnoreCase(MIP_CustomerManagementPage.HOSPITAL)) {
				if (result.getString("dob") == null) {
					rel_info.put("dob", "");
				} else {
					rel_info.put("dob", result.getString("dob"));
				}
				rel_info.put("cust_relationship",
						result.getString("cust_relationship"));
			}
			if (result.getString("age") == null) {
				rel_info.put("age", "");
			} else {
				rel_info.put("age", result.getString("age"));
			}

			if (result.getString("msisdn") == null) {
				rel_info.put("msisdn", "");
			} else {
				rel_info.put("msisdn", result.getString("msisdn"));
			}

		} catch (SQLException e) {
			logger.error(
					"Error while executing the getDeactivatedRelativeInfo queries",
					e);
		}
		return rel_info;
	}

	public static void main(String[] args) {
		MIP_ReadPropertyFile.loadProperty("config");
		MIP_DataBaseConnection.connectToDatabase();
		MIP_SearchCustomer_Queries.getDeactivatedRelativeInfo("0271052831",
				MIP_CustomerManagementPage.HOSPITAL);

	}
}
