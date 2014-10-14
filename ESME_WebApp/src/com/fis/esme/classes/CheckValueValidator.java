package com.fis.esme.classes;

import com.vaadin.data.validator.IntegerValidator;

public class CheckValueValidator extends IntegerValidator
{
	private Integer val;
	
	public CheckValueValidator(String errorMessage)
	{
		super(errorMessage);
	}
	
	@Override
	protected boolean isValidString(String value)
	{
		try
		{
			try
			{
				val = Integer.parseInt(value);
				return ((val >= 0) && (val <= 100));
			}
			catch (Exception e)
			{
				return true;
			}
		}
		catch (Exception e)
		{
			return false;
		}
		// return true;
	}
}
