package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;

public class StartMsisdnValidator extends AbstractValidator
{
	
	private String msisdn;
	
	public StartMsisdnValidator(String errorMessage)
	{
		super(errorMessage);
	}
	
	public StartMsisdnValidator(String errorMessage, String msisdn)
	{
		super(errorMessage);
		this.msisdn = msisdn;
	}
	
	@Override
	public boolean isValid(Object value)
	{
		String msisdn = value.toString().trim();
		if ((msisdn.startsWith("9") || msisdn.startsWith("1")
				|| msisdn.startsWith("09") || msisdn.startsWith("01"))
				&& (msisdn.length() >= 9) && (msisdn.length() <= 11))
		{
			return true;
		}
		return false;
	}
	
	public String getMsisdn()
	{
		return msisdn;
	}
	
	public void setMsisdn(String msisdn)
	{
		this.msisdn = msisdn;
	}
}
