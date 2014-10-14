package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;

import eu.livotov.tpt.i18n.TM;

public class PromPercentValidator extends AbstractValidator
{
	public PromPercentValidator()
	{
		super("");
	}

	@Override
	public boolean isValid(Object value)
	{
		if (value == null)
		{
//			setErrorMessage("INVALID_NUMBER_NULL");
			return true;
		}
		
		String str = value.toString().trim();
		
		if ("".equals(str))
		{
//			setErrorMessage(TM.get("promdetail.ValidateNumber"));
			return true;
		}
		
		if (!str.matches("[0-9]{1,10}"))
		{
			setErrorMessage(TM.get("promdetail.ValidateNumber"));
			return false;
		}
		
		long val = 0;
		try
		{
			val = Long.parseLong(str);
		}
		catch (Exception ex)
		{
			setErrorMessage(TM.get("promdetail.ValidateNumber"));
			return false;
		}
		
		if (val < 0 || val > 100)
		{
			setErrorMessage(TM.get("promdetail.ValidateFreeType"));
			return false;
		}
		
		return true;
	}
	
}
