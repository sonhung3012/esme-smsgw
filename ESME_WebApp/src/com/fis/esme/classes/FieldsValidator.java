package com.fis.esme.classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.data.validator.AbstractValidator;

public class FieldsValidator extends AbstractValidator
{
	
	private FieldsValidatorInterface fielsValidatorInterface;
	private String property;
	private Object otherObj;
	
	private Matcher matcher = null;
	private Pattern pattern;
	
	public FieldsValidator(FieldsValidatorInterface fielsValidatorInterface,
			String property)
	{
		super("");
		this.fielsValidatorInterface = fielsValidatorInterface;
		this.property = property;
	}
	
	public FieldsValidator(FieldsValidatorInterface fielsValidatorInterface,
			String property, Object otherObj)
	{
		super("");
		this.fielsValidatorInterface = fielsValidatorInterface;
		this.property = property;
		this.otherObj = otherObj;
	}
	
	public void setErrorMgs(String errorMessage)
	{
		this.setErrorMessage(errorMessage);
	}
	
	@Override
	public boolean isValid(Object value)
	{
		Object obj = fielsValidatorInterface.isValid(property, value, otherObj);
		if (obj != null)
		{
			this.setErrorMessage(obj.toString());
			return false;
		}
		else
			return true;
	}
	
	public boolean spaceValid(String value)
	{
		if (value != null)
		{
			if (((String) value).trim().length() > 0)
				return true;
		}
		return false;
	}
	
	public boolean compareIntegerValid(Object value, int integerTarget, int conditional)
	{
		int val;
		try
		{
			val = Integer.parseInt(value.toString());
		}
		catch (NumberFormatException e)
		{
			return true;
		}
		
		if (conditional == -1)
			return val < integerTarget;
		if (conditional == 0)
			return val == integerTarget;
		if (conditional == 1)
			return val > integerTarget;
		if (conditional == 2)
		{
			return (val > integerTarget || val == integerTarget);
		}
		if (conditional == -2)
		{
			return (val < integerTarget || val == integerTarget);
		}
		return false;
	}
	
	public boolean regexpValid(String regexp, boolean complete, String value,
			boolean isAcceptEmty)
	{
		pattern = Pattern.compile(regexp);
		if (isAcceptEmty && value.trim().length() > 0)
		{
			if (complete)
			{
				
				return getMatcher(value.trim()).matches();
				
			}
			else
			{
				return getMatcher(value.trim()).find();
			}
		}
		else if (!isAcceptEmty)
		{
			if (complete)
			{
				
				return getMatcher(value).matches();
				
			}
			else
			{
				return getMatcher(value).find();
			}
		}
		else
		{
			return true;
		}
	}
	
	private Matcher getMatcher(String value)
	{
		if (matcher == null)
		{
			matcher = pattern.matcher(value);
		}
		else
		{
			matcher.reset(value);
		}
		return matcher;
	}
	
}
