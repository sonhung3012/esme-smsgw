package com.fis.esme.core.services;

import java.util.Vector;

import com.fis.esme.core.app.ThreadSplitManager;
import com.fis.esme.core.entity.VariableStatic;
import com.fis.esme.core.util.StatisticParams;
import com.fss.thread.ParameterType;
import com.fss.util.AppException;

public class SynchronizeRequestSplitManager extends ThreadSplitManager {

	public Vector getParameterDefinition() {
		Vector vtReturn = new Vector();
		// //////////////////////////////////////////////////////
		vtReturn.addElement(createParameterDefinition("DefaultCommandCode", "",
				ParameterType.PARAM_TEXTBOX_MAX, "", ""));
		vtReturn.addAll(super.getParameterDefinition());
		return vtReturn;
	}

	public void fillParameter() throws AppException {
		super.fillParameter();
		VariableStatic.DEFAULT_COMMAND_CODE = loadMandatory("DefaultCommandCode");
	}

	@Override
	protected void beforeSession() throws Exception {
		super.beforeSession();

	}

	public void getss() {
		String d = "select cp_id,code from esme_cp where upper(code)='SYSTEM_ALERT';";

	}
	public static void main(String [] agm)
	{
		
	}
}
