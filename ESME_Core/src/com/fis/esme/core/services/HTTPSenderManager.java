package com.fis.esme.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.fis.esme.core.app.ThreadSplitBase;
import com.fis.esme.core.app.ThreadSplitManager;
import com.fis.esme.core.entity.VariableStatic;
import com.fis.esme.core.smsc.util.QueueMOManager;
import com.fis.esme.core.util.CommonStatistic;
import com.fis.esme.core.util.PrintStatistic;
import com.fis.esme.core.util.StatisticParams;

/**
 * <p>
 * Title: GW
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * 
 * <p>
 * Company: FIS-SOT
 * </p>
 * 
 * @author HungVM
 * @version 1.0
 */

public class HTTPSenderManager extends ThreadSplitManager implements
		PrintStatistic {
	public HTTPSenderManager() {
	}

	protected int miThreadNumber = 0;
	protected String mstrThreadClassName;
	private boolean mbAppDoStop = false;
	private int miPrintCycle = 30;
	private int miWarningCycle = 30;
	protected int miL1 = StatisticParams.DUR_LEVEL1_WARNING;
	protected int miL2 = StatisticParams.DUR_LEVEL2_ERROR;
	protected int miL3 = StatisticParams.DUR_LEVEL3_CRITICAL;
	private long mlNextPrint = 0;
	private long mlNextWarn = 0;
	public CommonStatistic mStatCommon = new CommonStatistic();
	private Vector mvtWarningConf;
	private QueueMOManager qQueueMOManager;

	public List<ThreadSplitBase> mlstThreadSplit = new ArrayList<ThreadSplitBase>();

	@Override
	public void beforeSession() throws Exception {
		// TODO Auto-generated method stub
		super.beforeSession();
		qQueueMOManager = getThreadManager().getMOQueue();
		qQueueMOManager.addDispatcher(this);
		mprtDeliverSM = new Properties();
		mprtDeliverSM.put("DataType", VariableStatic.PROTOCAL_TYPE_HTTP);
	}

	@Override
	protected void afterSession() throws Exception {
		super.afterSession();
		qQueueMOManager.removeDispatcher(this);
	}

	public static void main(String [] agm)
	{
		
	}
}