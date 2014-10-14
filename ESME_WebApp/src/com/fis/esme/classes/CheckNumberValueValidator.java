package com.fis.esme.classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.data.validator.AbstractStringValidator;

public class CheckNumberValueValidator extends AbstractStringValidator
{
	
	private Pattern pattern;
	private Matcher matcher;
	private String regexp;
	
	public CheckNumberValueValidator(String regexp, String errorMessage)
	{
		super(errorMessage);
		this.regexp = regexp;
	}
	
	@Override
	protected boolean isValidString(String value)
	{
		if (value == null || "".equals(value.trim()))
		{
			return false;
		}
		pattern = Pattern.compile(regexp);
		matcher = pattern.matcher(value.trim());
		
		if (!matcher.matches())
		{
			return false;
		}
		else
		{
			Long val = Long.parseLong(value.trim());
			if(val <= 0)
				return false;
		}
		return true;
	}
	
}
