package com.fis.esme.core.app;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.fis.esme.core.util.CommonStatistic;
import com.fis.esme.core.util.PrintStatistic;
import com.fis.esme.core.util.StatisticParams;
import com.fss.thread.ParameterType;
import com.fss.thread.ThreadConstant;
import com.fss.util.AppException;

/**
 * <p>Title: MCA GW</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: FIS-SOT</p>
 *
 * @author LiemLT
 * @version 1.0
 */

public class ThreadSplitManager extends ManageableThreadEx implements PrintStatistic{
    public ThreadSplitManager() {
    }
	public static void main(String [] agm)
	{
		System.out.println("Print any thing");
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
    public List<ThreadSplitBase> mlstThreadSplit = new ArrayList<
            ThreadSplitBase>();

    public Vector getParameterDefinition() {
        Vector vtReturn = new Vector();
        ////////////////////////////////////////////////////////
        vtReturn.addElement(createParameterDefinition("ThreadNumber", "",
                ParameterType.PARAM_TEXTBOX_MASK, "99999", ""));
        vtReturn.addElement(createParameterDefinition("SplitClassName", "",
                ParameterType.PARAM_TEXTBOX_MAX, "9999", ""));
		vtReturn.addElement(createParameterDefinition("PrintStatisticCycle", "30",
				ParameterType.PARAM_TEXTBOX_MASK, "9999", "in seconds"));
		vtReturn.addElement(createParameterDefinition("PrintWarningCycle", "120",
				ParameterType.PARAM_TEXTBOX_MASK, "9999", "in seconds"));
		vtReturn.addElement(createParameterDefinition("L1WarningTime", String.valueOf(StatisticParams.DUR_LEVEL1_WARNING),
				ParameterType.PARAM_TEXTBOX_MASK, "9999", "in seconds"));
		vtReturn.addElement(createParameterDefinition("L2ErrorTime", String.valueOf(StatisticParams.DUR_LEVEL2_ERROR),
				ParameterType.PARAM_TEXTBOX_MASK, "9999", "in seconds"));
		vtReturn.addElement(createParameterDefinition("L3CriticalTime", String.valueOf(StatisticParams.DUR_LEVEL3_CRITICAL),
				ParameterType.PARAM_TEXTBOX_MASK, "9999", "in seconds"));
		Vector vtDefinition = getTabStatisticDefinition();
		vtReturn.addElement(createParameterDefinition("WarningPolicy", "",
				"11", vtDefinition,
				"Contains blacklist of warning parameters"));

		vtReturn.addAll(super.getParameterDefinition());
		return vtReturn;
    }

    public Vector getTabStatisticDefinition()
    {
		Vector vtComboBox = new Vector();
		vtComboBox.add(StatisticParams.STR_CHECK);
		vtComboBox.add(StatisticParams.STR_NORMAL);
    	Vector vtTab = new Vector();
    	Object[] keys = getStatistic().getKeys();
    	for (int i = 0; i < keys.length; i++)
		{
			String strKey = keys[i].toString();
	    	vtTab.addElement(createParameterDefinition(strKey, "",ParameterType.PARAM_COMBOBOX, vtComboBox, "", "0"));
		}
    	return vtTab;
    }
    
    public void fillParameter() throws AppException {
        super.fillParameter();
        miThreadNumber = loadUnsignedInteger("ThreadNumber");
        mstrThreadClassName = loadMandatory("SplitClassName");
		miPrintCycle = loadUnsignedInteger("PrintStatisticCycle");
		miWarningCycle = loadUnsignedInteger("PrintWarningCycle");
		miL1 = loadUnsignedInteger("L1WarningTime");
		miL2 = loadUnsignedInteger("L2ErrorTime");
		miL3 = loadUnsignedInteger("L3CriticalTime");
		getStatistic().setL1(miL1);
		getStatistic().setL2(miL2);
		getStatistic().setL3(miL3);
		
		Object obj = getParameter("WarningPolicy");
		if (obj != null && obj instanceof Vector) {
			mvtWarningConf = (Vector)((Vector) obj).elementAt(0);
		} else {
			mvtWarningConf = new Vector();
		}
		
    	Object[] keys = getStatistic().getKeys();
    	for (int i = 0; i < keys.length; i++)
		{
			String strKey = keys[i].toString();
			if (i >= mvtWarningConf.size())
				throw new AppException("Parameter: \"WarningPolicy\".\""+strKey + "\" must not be null");
			String strValue = mvtWarningConf.elementAt(i).toString();
			if (strValue.equals(""))
			{
				throw new AppException("Parameter: \"WarningPolicy\".\""+strKey + "\" must not be null");
			}
	    	getStatistic().setWarningType(strKey, strValue);
		}

    }

    public void processSession() throws Exception {
		mbAppDoStop=false;
        try {
			while (!mbAppDoStop && miThreadCommand != ThreadConstant.THREAD_STOP) {
			    if (miThreadNumber > mlstThreadSplit.size()) {
			        for (int i = 0; i < miThreadNumber; i++) {
			            if (mlstThreadSplit.size() <= i) {
			                mlstThreadSplit.add(initThreadSplit(i));
			            }
			        }
			    } else if (miThreadNumber < mlstThreadSplit.size()) {
			        while (miThreadNumber < mlstThreadSplit.size()) {
			            int iLastThreadIndex = mlstThreadSplit.size() - 1;

			            ThreadSplitBase threadTemp = mlstThreadSplit.remove(
			                    iLastThreadIndex);

			            if (threadTemp != null) {
			                threadTemp.disable();
			            }
			        }
			    }
				long mlCurrent = System.currentTimeMillis();
				if (mlCurrent>mlNextPrint)
				{
					String strStatistic = printStatistic();
					if (!strStatistic.equals(""))
					{
						logMonitor(strStatistic);
					}
					mlNextPrint = mlCurrent + miPrintCycle*1000;
				}
				
				mlCurrent = System.currentTimeMillis();
				if (mlCurrent>mlNextWarn && miWarningCycle > 0)
				{
					String strWarning = printWarning();
					if (!strWarning.equals(""))
					{
						logMonitor(strWarning);
					}
					mlNextWarn = mlCurrent + miWarningCycle*1000;
				}
				fillLogFile();
				// Delay
				for (int iIndex = 0; iIndex < miDelayTime && !mbAppDoStop
						&& miThreadCommand != ThreadConstant.THREAD_STOP; iIndex++) {
					Thread.sleep(1000); // Time unit is second
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			for (int i = 0; i < mlstThreadSplit.size(); i++) {
				ThreadSplitBase threadTemp = mlstThreadSplit.get(i);
				if (threadTemp != null) {
					threadTemp.disable();
				}
			}
			// Wait until all thread completed
			while (mlstThreadSplit.size() > 0) {
				Thread.sleep(1000); // Time unit is second
			}
		}
    }

    public ThreadSplitBase initThreadSplit(int iIndex) throws
            ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        ThreadSplitBase thSplitBase = (ThreadSplitBase) Class.forName(
                mstrThreadClassName).newInstance();
        thSplitBase.setThreadManager(this);
        thSplitBase.setIndex(iIndex);
        thSplitBase.setDelayTime(miDelayTime);

        thSplitBase.start();

        return thSplitBase;
    }

    public Connection createConnection() throws Exception {
        return mmgrMain.getConnection();
    }

    public void remove(ThreadSplitBase thSplitBase) {
		for (int i = 0; i < mlstThreadSplit.size(); i++) {
			ThreadSplitBase threadTemp = (ThreadSplitBase) mlstThreadSplit.get(i);
			if (thSplitBase.getIndex() == threadTemp.getIndex()) {
				mlstThreadSplit.remove(i);
				break;
			}
		}
    }
    public void doRestartManager() {
        mbAppDoStop=true;
    }
    public boolean isDoRestartManager() {
        return mbAppDoStop;
    }

	@Override
	public String printStatistic() {
		// TODO Auto-generated method stub
		return getStatistic().printStatistic();
	}

	@Override
	public String printWarning() {
		// TODO Auto-generated method stub
		return getStatistic().printWarning();
	}
	public CommonStatistic getStatistic()
	{
		return mStatCommon;
	}

}