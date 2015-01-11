package com.fis.esme.core.services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.bean.megloader.SMSLoaderBeanFactory;
import com.fis.esme.core.entity.DateTime;
import com.fis.esme.core.entity.SMSMTObject;
import com.fis.esme.core.util.AsnUtil;
import com.fis.esme.core.util.LinkQueue;
import com.fss.sql.Database;
import com.fss.thread.ParameterType;
import com.fss.thread.ParameterUtil;
import com.fss.thread.ThreadConstant;
import com.fss.util.AppException;
import com.fss.util.StringUtil;
import com.logica.smpp.Data;

public class LoadMTThread extends ManageableThreadEx {
	public LinkQueue qMessageQueue = null;
	private int iRowNumber = 0;
	private int intRetryNumber = 3;
	private int intRetrySpace = 5;
	private int intReloadNumber = 3;
	private int intReloadSpace = 3;
	private int intRetryRowNumber = 0;
	private int intPartitionRank = 10;

	protected class LoadStatisticEntity {
		public int miLoadSuccCount = 0;
		public int miLoadFirstTimeCount = 0;
		public int miLoadFailCount = 0;
		public int miLoadNotloadedCount = 0;
	}

	public void fillParameter() throws AppException {
		iRowNumber = loadUnsignedInteger("RowNumber");
		intRetryRowNumber = loadUnsignedInteger("RetryRowNumber");
		intRetryNumber = loadUnsignedInteger("RetryNumber");
		intRetrySpace = loadUnsignedInteger("RetrySpace");
		intReloadSpace = loadUnsignedInteger("ReloadSpace");
		intPartitionRank = loadUnsignedInteger("PartitionRank");
		super.fillParameter();
	}

	public void validateParameter() throws Exception {
		if (iRowNumber <= 0) {
			iRowNumber = 1000;
		}
	}

	public Vector getParameterDefinition() {
		Vector vtReturn = new Vector();

		vtReturn.addElement(ParameterUtil.createParameterDefinition(
				"RetryRowNumber", "", ParameterType.PARAM_TEXTBOX_MASK,
				"99999", "RetryRow limit"));
		vtReturn.addElement(ParameterUtil.createParameterDefinition(
				"PartitionRank", "", ParameterType.PARAM_TEXTBOX_MASK, "99999",
				"PartitionRank"));

		vtReturn.addElement(ParameterUtil.createParameterDefinition(
				"RetryNumber", "", ParameterType.PARAM_TEXTBOX_MASK, "99999",
				"The Number retry for fail message !"));

		vtReturn.addElement(ParameterUtil.createParameterDefinition(
				"RetrySpace", "", ParameterType.PARAM_TEXTBOX_MASK, "99999",
				"Retry schedule status = 3 after (a) Minus !"));

		vtReturn.addElement(ParameterUtil.createParameterDefinition(
				"ReloadSpace", "", ParameterType.PARAM_TEXTBOX_MASK, "99999",
				"Retry schedule status = 2 after (a) Minute !"));

		vtReturn.addElement(ParameterUtil.createParameterDefinition(
				"RowNumber", "", ParameterType.PARAM_TEXTBOX_MASK, "99999",
				"Rownum limit"));

		vtReturn.addAll(super.getParameterDefinition());
		return vtReturn;
	}

	@Override
	protected void beforeSession() throws Exception {
		// TODO Auto-generated method stub
		super.beforeSession();
		mcnMain = getThreadManager().getConnection();
		qMessageQueue = getThreadManager().getSMSMTQueue();
	}

	@Override
	protected void processSession() throws Exception {
		// TODO Auto-generated method stub
		while (miThreadCommand != ThreadConstant.THREAD_STOP) {
			LoadStatisticEntity statistic = loadFromDB();
			logMonitor("Load total sucessfull count :"
					+ statistic.miLoadSuccCount
					+ "\n     first time count   : "
					+ statistic.miLoadFirstTimeCount
					+ "\n     reload failed count: "
					+ statistic.miLoadFailCount
					+ "\n     notloaded count    : "
					+ statistic.miLoadNotloadedCount);
			Thread.sleep(miDelayTime * 1000);

		}

	}

	protected void afterSession() throws Exception {
		super.afterSession();
		closeTrans();
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	protected LoadStatisticEntity loadFromDB() throws Exception {

		Vector vtMessage = SMSLoaderBeanFactory.getSmsBeanFactory()
				.getMTMessage(mcnMain, iRowNumber, intPartitionRank,
						intRetryRowNumber, intRetrySpace, intRetryNumber,
						intReloadSpace, intReloadNumber);
		LoadStatisticEntity statistic = new LoadStatisticEntity();
		int iLoadSuccCount = 0;
		int iLoadFirstTimeCount = 0;
		int iLoadFailCount = 0;
		int iLoadNotloadedCount = 0;
		if (vtMessage != null && vtMessage.size() > 0) {
			SMSMTObject smsMTObject = null;
			for (int i = 0; i < vtMessage.size(); i++) {
				try {
					Vector vtOneRow = (Vector) vtMessage.elementAt(i);
					smsMTObject = buildObject(vtOneRow);
					if (smsMTObject != null) {
						qMessageQueue.enqueueNotify(smsMTObject);
						SMSLoaderBeanFactory.getSmsBeanFactory()
								.updateMTStatus(mcnMain,
										String.valueOf(smsMTObject.getMt_id()),
										"9", "1");
						if (smsMTObject.getLoadtype().equals("1")) {
							iLoadFirstTimeCount++;
						} else if (smsMTObject.getLoadtype().equals("2")) {
							iLoadFailCount++;
						} else if (smsMTObject.getLoadtype().equals("3")) {
							iLoadNotloadedCount++;
						}
						iLoadSuccCount++;
					}

				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
					logMonitor(e.getMessage());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					logMonitor(e.getMessage());
				}
			}
		}
		statistic.miLoadSuccCount = iLoadSuccCount;
		statistic.miLoadFirstTimeCount = iLoadFirstTimeCount;
		statistic.miLoadFailCount = iLoadFailCount;
		statistic.miLoadNotloadedCount = iLoadNotloadedCount;
		return statistic;
	}

	private SMSMTObject buildObject(Vector vtRowMessage) {
		SMSMTObject smsObject = new SMSMTObject();
		try {
			int mt_id = formatInt(String.valueOf(vtRowMessage.elementAt(0)));
			int request_id = formatInt(String
					.valueOf(vtRowMessage.elementAt(1)));
			int cp_id = formatInt(String.valueOf(vtRowMessage.elementAt(4)));
			String short_code = String.valueOf(vtRowMessage.elementAt(5));
			int retry_number = formatInt(String.valueOf(vtRowMessage
					.elementAt(7)));
			int reload_number = formatInt(String.valueOf(vtRowMessage
					.elementAt(0)));
			Date request_time = DateTime.formatFullDate(String
					.valueOf(vtRowMessage.elementAt(2)));
			Date last_retry_time = DateTime.formatFullDate(String
					.valueOf(vtRowMessage.elementAt(10)));
			String message = String.valueOf(vtRowMessage.elementAt(3));

//			debugMonitor(AsnUtil.bytesToHexString(message
//					.getBytes(Data.ENC_UTF16_LE)), 8);

			String msisdn = String.valueOf(vtRowMessage.elementAt(6));
			String command_code = String.valueOf(vtRowMessage.elementAt(8));
			String register_delivery_report = String.valueOf(vtRowMessage
					.elementAt(9));
			String status = String.valueOf(vtRowMessage.elementAt(11));
			String strLoadType = String.valueOf(vtRowMessage.elementAt(12));
			String strSmsLogID = String.valueOf(vtRowMessage.elementAt(13));
			String strESMETransID = String.valueOf(vtRowMessage.elementAt(14));
			String strMOSequenceNumber = StringUtil.nvl(
					vtRowMessage.elementAt(15), "");
			String strFileUploadID = StringUtil.nvl(vtRowMessage.elementAt(16),
					"0");
			String strSubid = StringUtil.nvl(vtRowMessage.elementAt(17), "");
			String strGroupid = StringUtil.nvl(vtRowMessage.elementAt(18), "");
			int intCustomerID = formatInt(String.valueOf(vtRowMessage
					.elementAt(19)));
			int intAdvId = formatInt(String.valueOf(vtRowMessage.elementAt(20)));
			int intCampaignId = formatInt(String.valueOf(vtRowMessage
					.elementAt(21)));

			smsObject.setCommand_code(command_code);
			smsObject.setCp_id(cp_id);
			smsObject.setLast_retry_time(last_retry_time);
			smsObject.setMessage(message);
			smsObject.setMsisdn(msisdn);
			smsObject.setMt_id(mt_id);
			smsObject.setRegister_delivery_report(register_delivery_report);
			smsObject.setReload_number(reload_number);
			smsObject.setRequest_id(request_id);
			smsObject.setRequest_time(request_time);
			smsObject.setRetry_number(retry_number);
			smsObject.setShort_code(short_code);
			smsObject.setStatus(status);
			smsObject.setLoadtype(strLoadType);
			smsObject.setSms_log_id(strSmsLogID);
			smsObject.setESMETransID(strESMETransID);
			smsObject.setMOSequenceNumber(strMOSequenceNumber);
			smsObject.setFileUploadID(strFileUploadID == "" ? "0"
					: strFileUploadID);
			smsObject.setSubId(strSubid);
			smsObject.setGroupId(strGroupid);
			smsObject.setCustomerId(intCustomerID);
			smsObject.setAdvId(intAdvId);
			smsObject.setCampaignId(intCampaignId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		return smsObject;

	}

	/**
 * 
 */
	public void closeTrans() {
		Database.closeObject(mcnMain);
		try {
			if (mcnMain != null && !mcnMain.isClosed()) {
				Database.closeObject(mcnMain);
				mcnMain = null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @param strValue
	 * @return
	 */
	public int formatInt(String strValue) {
		if (strValue == null || strValue == "") {
			return 0;
		} else {
			return Integer.valueOf(strValue);
		}
	}

	public static void main(String[] agm) {

	}
}
