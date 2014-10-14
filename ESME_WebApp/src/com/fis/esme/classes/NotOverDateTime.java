package com.fis.esme.classes;

import java.util.Calendar;
import java.util.Date;

import com.fis.esme.util.FormUtil;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.DateField;


public class NotOverDateTime extends AbstractValidator
{

	private DateField targetObject;
	private int conditional,field,amount;
	private boolean targetNullAllowed = false;
	
	public NotOverDateTime(String errorMessage)
	{
		super(errorMessage);
	}

	public NotOverDateTime(String errorMessage, DateField tagetObject, int conditional, int field, int amount)
	{
		this(errorMessage, tagetObject, conditional, field, amount, false);
	}
	
	public NotOverDateTime(String errorMessage, DateField tagetObject, int conditional, int field, int amount, boolean targetNullAllowed)
	{
		super(errorMessage);
		this.setTagetObject(tagetObject);
		this.conditional = conditional;
		this.field= field;
		this.amount=amount;
		this.targetNullAllowed = targetNullAllowed;
	}
	
	@Override
	public boolean isValid(Object value)
	{
		Date targetDate = (Date)getTagetObject().getValue();
		Date val = (Date)value;
		val = FormUtil.toDate(val, FormUtil.notSetDateFields);
		targetDate = FormUtil.toDate(targetDate, FormUtil.notSetDateFields);
		
		if (targetDate == null)
		{
			if (targetNullAllowed)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		Calendar ca = Calendar.getInstance();
		
		ca.setTime(targetDate);		
		ca.add(field, amount);
		
		targetDate = ca.getTime();
		
		if (conditional == -1)
			return (val).before(targetDate);
		if (conditional == 0)
			return (val).equals(targetDate);
		if (conditional == 1)
			return (val).after(targetDate);
		if (conditional == 2)
		{
			return ((val).after(targetDate) || (val).equals(targetDate));
		}
		if (conditional == -2)
		{
			return ((val).before(targetDate) || (val).equals(targetDate));
		}
		
		return false;
	}

	public DateField getTagetObject()
	{
		return targetObject;
	}

	public void setTagetObject(DateField tagetObject)
	{
		this.targetObject = tagetObject;
	}

	public int getConditional()
	{
		return conditional;
	}

	public void setConditional(int conditional)
	{
		this.conditional = conditional;
	}

	public int getField()
	{
		return field;
	}

	public void setField(int field)
	{
		this.field = field;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}
}
