package com.fis.esme.core.log;

public class SMSLogRecord extends LogRecord
{
	private int miRetryCount = 0;
	
	public SMSLogRecord()
	{
		super();
	}
	
	public int getRetryCount()
	{
		return miRetryCount;
	}
	
	public void increaseRetryCount()
	{
		miRetryCount++;
	}
}
