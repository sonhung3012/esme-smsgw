package com.fis.esme.form;

public class Output
{
	private String isdn;
	private String date;
	private String status;
	
	public Output()
	{
	}
	
	public Output(String strIsdn, String strdate, String strStatus)
	{
		this.isdn = strIsdn;
		this.date = strdate;
		this.status = strStatus;
	}
	
	public String getIsdn()
	{
		return isdn;
	}
	
	public void setIsdn(String isdn)
	{
		this.isdn = isdn;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
}

