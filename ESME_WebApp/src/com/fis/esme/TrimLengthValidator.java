package com.fis.esme;

import com.vaadin.data.validator.AbstractValidator;

@SuppressWarnings("serial")
public class TrimLengthValidator extends AbstractValidator
{
	private int minLength = -1;
	private int maxLength = -1;
	private boolean trim = true;
	
	public TrimLengthValidator(String errorMessage)
	{
		this(errorMessage, -1, -1, true);
	}
	
	public TrimLengthValidator(String errorMessage, int minLength, int maxLength)
	{
		this(errorMessage, minLength, maxLength, true);
	}
	
	public TrimLengthValidator(String errorMessage, int minLength,
			int maxLength, boolean trim)
	{
		super(errorMessage);
		setTrim(trim);
		setMinLength(minLength);
		setMaxLength(maxLength);
	}
	
	public boolean isValid(Object value)
	{
		if (value == null)
		{
			return false;
		}
		
		String s = null;
		if (isTrim())
		{
			s = value.toString().trim();
		}
		else
		{
			s = value.toString();
		}
		
		if (s == null)
		{
			return false;
		}
		
		final int len = s.length();
		if ((minLength >= 0 && len < minLength)
				|| (maxLength >= 0 && len > maxLength))
		{
			return false;
		}
		return true;
	}
	
	public final int getMaxLength()
	{
		return maxLength;
	}
	
	public final int getMinLength()
	{
		return minLength;
	}
	
	public void setMaxLength(int maxLength)
	{
		if (maxLength < -1)
		{
			maxLength = -1;
		}
		this.maxLength = maxLength;
	}
	
	public void setMinLength(int minLength)
	{
		if (minLength < -1)
		{
			minLength = -1;
		}
		this.minLength = minLength;
	}
	
	public boolean isTrim()
	{
		return trim;
	}
	
	public void setTrim(boolean trim)
	{
		this.trim = trim;
	}
}
