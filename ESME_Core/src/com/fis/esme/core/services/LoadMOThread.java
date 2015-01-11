package com.fis.esme.core.services;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import com.fis.esme.core.app.ManageableThreadEx;
import com.fis.esme.core.bean.megloader.SMSLoaderBeanFactory;
import com.fis.esme.core.entity.DateTime;
import com.fis.esme.core.entity.SMSMOObject;
import com.fis.esme.core.util.LinkQueue;
import com.fss.sql.Database;
import com.fss.thread.ParameterType;
import com.fss.thread.ParameterUtil;
import com.fss.thread.ThreadConstant;
import com.fss.util.AppException;

public class LoadMOThread extends ManageableThreadEx {
	// private PreparedStatement pstmtUpdateReloadStatus = null;
	public LinkQueue qMessageQueue = null;
	private int iRowNumber = 0;
	private int intRetryNumber = 3;
	private int intRetrySpace = 5;
	private int intRetryRowNumber = 0;
	private int intPartitionRank = 10;

	protected class LoadStatisticEntity {
		public int miLoadSuccCount = 0;
		public int miLoadFirstTimeCount = 0;
		public int miLoadFailCount = 0;
	}

	public void fillParameter() throws AppException {
		iRowNumber = loadUnsignedInteger("RowNumber");
		intRetryRowNumber = loadUnsignedInteger("RetryRowNumber");
		intRetryNumber = loadUnsignedInteger("RetryNumber");
		intRetrySpace = loadUnsignedInteger("RetrySpace");
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
		qMessageQueue = getThreadManager().getLqMessageMOQueue();
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
					+ statistic.miLoadFailCount);
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
				.getMOMessage(mcnMain, iRowNumber, intPartitionRank,
						intRetryRowNumber, intRetrySpace, intRetryNumber);
		LoadStatisticEntity statistic = new LoadStatisticEntity();
		int iLoadSuccCount = 0;
		int iLoadFirstTimeCount = 0;
		int iLoadFailCount = 0;
		int iLoadNotloadedCount = 0;
		if (vtMessage != null && vtMessage.size() > 0) {
			SMSMOObject smsMOObject = null;
			for (int i = 0; i < vtMessage.size(); i++) {
				try {
					Vector vtOneRow = (Vector) vtMessage.elementAt(i);
					smsMOObject = buildObject(vtOneRow);
					if (smsMOObject != null) {
						qMessageQueue.enqueueNotify(smsMOObject);
						SMSLoaderBeanFactory.getSmsBeanFactory()
								.updateMTStatus(mcnMain,
										String.valueOf(smsMOObject.getMo_id()),
										"9", "1");
						if (smsMOObject.getLoadtype().equals("1")) {
							iLoadFirstTimeCount++;
						} else if (smsMOObject.getLoadtype().equals("2")) {
							iLoadFailCount++;
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
		return statistic;
	}

	private SMSMOObject buildObject(Vector vtRowMessage) {
		SMSMOObject smsObject = new SMSMOObject();
		try {
			int mo_id = formatInt(String.valueOf(vtRowMessage.elementAt(0)));
			int request_id = formatInt(String
					.valueOf(vtRowMessage.elementAt(1)));
			String message = String.valueOf(vtRowMessage.elementAt(2));
			String request_time = String.valueOf(vtRowMessage.elementAt(3));
			String status = String.valueOf(vtRowMessage.elementAt(4));
			String msisdn = String.valueOf(vtRowMessage.elementAt(5));
			String short_code = String.valueOf(vtRowMessage.elementAt(6));
			int retry_number = formatInt(String.valueOf(vtRowMessage
					.elementAt(7)));
			String last_update = String.valueOf(vtRowMessage.elementAt(8));
			String type = String.valueOf(vtRowMessage.elementAt(9));
			String loadtype = String.valueOf(vtRowMessage.elementAt(10));

			int service_id = formatInt(String.valueOf(vtRowMessage
					.elementAt(11)));
			int service_parent_id = formatInt(String.valueOf(vtRowMessage
					.elementAt(12)));
			int service_roor_id = formatInt(String.valueOf(vtRowMessage
					.elementAt(13)));
			int cp_id = formatInt(String.valueOf(vtRowMessage.elementAt(14)));
			int smsc_id = formatInt(String.valueOf(vtRowMessage.elementAt(15)));
			int short_code_id = formatInt(String.valueOf(vtRowMessage
					.elementAt(16)));
			int command_id = formatInt(String.valueOf(vtRowMessage
					.elementAt(17)));
			String strSMS_Log_Id = String.valueOf(vtRowMessage.elementAt(18));

			String strProtocal = String.valueOf(vtRowMessage.elementAt(19));

			smsObject.setMo_id(mo_id);
			smsObject.setRequest_id(request_id);
			smsObject.setMessage(message);
			smsObject.setRequest_time(DateTime.formatFullDate(request_time));
			smsObject.setStatus(status);
			smsObject.setMsisdn(msisdn);
			smsObject.setShort_code(short_code);
			smsObject.setRetry_number(retry_number);
			smsObject.setLast_update(DateTime.formatFullDate(last_update));
			smsObject.setType(loadtype);
			smsObject.setLoadtype(loadtype);
			smsObject.setService_id(service_id);
			smsObject.setService_parent_id(service_parent_id);
			smsObject.setService_roor_id(service_roor_id);
			smsObject.setCp_id(cp_id);
			smsObject.setSmsc_id(smsc_id);
			smsObject.setCommand_id(command_id);
			smsObject.setShort_code_id(short_code_id);
			smsObject.setSmsLogId(strSMS_Log_Id);
			smsObject.setProtocal(strProtocal);

			debugMonitor("Logid : " + strSMS_Log_Id + ",mo_id : " + mo_id, 3);

		} catch (Exception e) {
			// TODO: handle exception
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
	public static void main(String [] agm)
	{
		
	}
}
