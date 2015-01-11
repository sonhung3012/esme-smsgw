package com.fis.esme.core.util;

import java.util.Properties;

/**
 * <p>
 * Title: 
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * <p>
 * Company: FIS-SOT
 * </p>
 * 
 * @author Trung DD
 * @version 1.0
 */
public class CommonStatistic implements PrintStatistic{

	private StatisticEntity seTotalProcessed = new StatisticEntity("Total of Processed",0,1);
	private StatisticEntity seTotalSuccess = new StatisticEntity("Total of Successed",1,1);
	protected Properties prtStatistic = new Properties();
	protected long mlLastPrint = System.currentTimeMillis();;
	protected long mlLastWarn = System.currentTimeMillis();
	protected long mlCurrent = System.currentTimeMillis();
	
	public int miL1 = StatisticParams.DUR_LEVEL1_WARNING;
	public int miL2 = StatisticParams.DUR_LEVEL2_ERROR;
	public int miL3 = StatisticParams.DUR_LEVEL3_CRITICAL;
	
	public CommonStatistic() {
		prtStatistic.put(seTotalProcessed.getName(),seTotalProcessed);
		prtStatistic.put(seTotalSuccess.getName(),seTotalSuccess);
	}


	public synchronized void increaseTotalProcessed() {
		seTotalProcessed.increaseValue();
	}
	public synchronized void increaseTotalSuccess() {
		seTotalSuccess.increaseValue();
	}

	protected void resetStatistic() {
		Object[] arrKeys = getStatistic().keySet().toArray();
		for (int i = 0; i < arrKeys.length; i++)
		{
			String strKey = arrKeys[i].toString();
			StatisticEntity entity = (StatisticEntity)getStatistic().get(strKey);
			entity.resetValue();
		}
	}

	public boolean isNeedToPrint() {
		//return (miTotalProcessed > 0);
		return true;
	}
	
	public Object[] getKeys()
	{
		Object[] arrKeys = new Object[getStatistic().size()];
		for (int i = 0; i < getStatistic().size(); i++)
		{
			arrKeys[i]= getParamByOrder(i).getName();
		}
		
		return arrKeys;
	}
	
	public void setL1(int iL1)
	{
		miL1 = iL1;
		Object[] arrKeys = getStatistic().keySet().toArray();
		for (int i = 0; i < arrKeys.length; i++)
		{
			String strKey = arrKeys[i].toString();
			StatisticEntity entity = (StatisticEntity)getStatistic().get(strKey);
			entity.setL1(iL1);
		}
	}

	public void setL2(int iL2)
	{
		miL2 = iL2;
		Object[] arrKeys = getStatistic().keySet().toArray();
		for (int i = 0; i < arrKeys.length; i++)
		{
			String strKey = arrKeys[i].toString();
			StatisticEntity entity = (StatisticEntity)getStatistic().get(strKey);
			entity.setL2(iL2);
		}
	}
	public void setL3(int iL3)
	{
		miL3 = iL3;
		Object[] arrKeys = getStatistic().keySet().toArray();
		for (int i = 0; i < arrKeys.length; i++)
		{
			String strKey = arrKeys[i].toString();
			StatisticEntity entity = (StatisticEntity)getStatistic().get(strKey);
			entity.setL3(iL3);
		}
	}
	public void setWarningType(String parName, String parValue)
	{
		StatisticEntity entity = (StatisticEntity)getStatistic().get(parName);
		entity.setWaringType(parValue);
	}
	private StatisticEntity getParamByOrder(int elementAt)
	{
		Object[] arrKeys = getStatistic().keySet().toArray();
		for (int i = 0; i < arrKeys.length; i++)
		{
			String strKey = arrKeys[i].toString();
			StatisticEntity entity = (StatisticEntity)getStatistic().get(strKey);
			if (entity.getParamOrder()==elementAt)
				return entity;
		}
		return null;
	}

	public String printStatistic() {
		
		String statistic = "";
		if (isNeedToPrint()) {
			long current = System.currentTimeMillis();
			statistic = "Duration from last print: "
					+ String.valueOf((current - mlLastPrint) / 1000) + " s";
			for (int order = 0; order < getStatistic().size(); order++)
			{
				StatisticEntity entity = getParamByOrder(order);
				statistic += entity.getValueString();
			}
			resetStatistic();
			mlLastPrint = current;
		}
		return statistic;
	}

	@Override
	public String printWarning() {
		
		String strWarning = "";
		if (isNeedToPrint()) {
			mlCurrent = System.currentTimeMillis();

			for (int order = 0; order < getStatistic().size(); order++)
			{
				StatisticEntity entity = getParamByOrder(order);
				strWarning += entity.getWarning();
			}

			if (!strWarning.equals(""))
				strWarning = "Duration from last warn: "
						+ String.valueOf((mlCurrent - mlLastWarn) / 1000) + " s" + strWarning;

			mlLastWarn = mlCurrent;
		}
		return strWarning;
	}
	
	public Properties getStatistic()
	{
		return prtStatistic;
	}
}
