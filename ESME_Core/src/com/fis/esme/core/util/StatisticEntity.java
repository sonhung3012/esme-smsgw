package com.fis.esme.core.util;
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

public class StatisticEntity {
	private int miValue = 0;
	private String mstrName = "";
	private int miZeroDuration = 0;
	private long mlLastCheckpoint = 0;
	private String mstrWarningType = StatisticParams.STR_CHECK;
	private int miParamOrder = 0;
	private int miLevel = 1;
	
	private int miL1 = StatisticParams.DUR_LEVEL1_WARNING; //5 phut.
	private int miL2 = StatisticParams.DUR_LEVEL2_ERROR; //10 phut.
	private int miL3 = StatisticParams.DUR_LEVEL3_CRITICAL; //60 phut.
	
	public StatisticEntity() {
		resetValue();
		resetZeroDuration();
	}

	public StatisticEntity(String parameterName, int order, int level) {
		mstrName = parameterName;
		miParamOrder = order;
		miLevel = level;
		resetValue();
		resetZeroDuration();
	}
	public StatisticEntity(String parameterName,int l1, int l2, int l3) {
		mstrName = parameterName;
		miL1 = l1;
		miL2 = l2;
		miL3 = l3;
		resetValue();
		resetZeroDuration();
	}
	
	public int getParamOrder()
	{
		return miParamOrder;
	}
	public void increaseValue()
	{
		miValue ++;
		resetZeroDuration();
	}

	public String getName()
	{
		return mstrName;
	}

	public String getValueString()
	{
		String lpad = "";
		if (miLevel == 1) lpad = "\n\t";
		if (miLevel == 2) lpad = "\n\t+\t";
		if (miLevel == 3) lpad = "\n\t+\t+\t";
		return lpad + mstrName + ": " + String.valueOf(miValue);
	}
	
	public String getWarning()
	{
		String strWarning = "";
		if (mstrWarningType.equals(StatisticParams.STR_NORMAL))
		{
			return strWarning;
		}
		
		String lpad = "";
		if (miLevel == 1) lpad = "\n\t";
		if (miLevel == 2) lpad = "\n\t+\t";
		if (miLevel == 3) lpad = "\n\t+\t+\t";
		
		if (miZeroDuration >= miL3)
		{
			strWarning += lpad + StatisticParams.STR_LEVEL3_CRITICAL + ": " + mstrName + " is zero for " + miZeroDuration + " s";
		}
		else if (miZeroDuration >= miL2)
		{
			strWarning += lpad + StatisticParams.STR_LEVEL2_ERROR + ": " + mstrName + " is zero for " + miZeroDuration + " s";
		}
		else if (miZeroDuration >= miL1)
		{
			strWarning += lpad + StatisticParams.STR_LEVEL1_WARNING + ": " + mstrName + " is zero for " + miZeroDuration + " s";
		}
		
		return strWarning;
	}
	
	public int getValue()
	{
		return miValue;
	}
	
	public int getCurrentZeroDurationInSecond()
	{
		return miZeroDuration; 
	}
	
	private void checkPoint()
	{
		if (miValue>0)
		{
			miZeroDuration = 0;
		}
		else
		{
			miZeroDuration += (int)(System.currentTimeMillis() - mlLastCheckpoint)/1000;
		}
		mlLastCheckpoint = System.currentTimeMillis();
	}
	
	public void resetValue()
	{
		checkPoint();
		miValue = 0;
	}
	
	private void resetZeroDuration()
	{
		miZeroDuration = 0;
	}
	
	public void setL1(int iL1)
	{
		miL1 = iL1;
	}
	public void setL2(int iL2)
	{
		miL2 = iL2;
	}
	public void setL3(int iL3)
	{
		miL3 = iL3;
	}
	public void setWaringType(String strType)
	{
		mstrWarningType = strType;
	}
	
}
