/**
 * 
 */
package com.fis.esme.classes;

import java.util.Calendar;
import java.util.Date;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

/**
 * @author HaiTX
 * 
 */
public class CompareHMSValidator extends AbstractValidator
{
	private Field targetObject;
	private int conditional;
	private String separator;
	
	public CompareHMSValidator(String errorMessage)
	{
		super(errorMessage);
	}
	
	public CompareHMSValidator(String errorMessage, Field tagetObject,
			String separator, int conditional)
	{
		super(errorMessage);
		this.targetObject = tagetObject;
		this.conditional = conditional;
		this.separator = separator;
	}
	
	public CompareHMSValidator(String errorMessage, Field tagetObject, int conditional)
	{
		super(errorMessage);
		this.targetObject = tagetObject;
		this.conditional = conditional;
		this.separator = ":";
	}
	
	public String getSeparator()
	{
		return separator;
	}
	
	public void setSeparator(String separator)
	{
		this.separator = separator;
	}
	
	@Override
	public boolean isValid(Object value)
	{
		try
		{
			String[] curentValue = value.toString().split(separator);
			String[] targetValue = getTargetObject().getValue().toString()
					.split(separator);
			
			Calendar curentTime = Calendar.getInstance();
			curentTime.set(00, 00, 00, Integer.parseInt(curentValue[0]),
					Integer.parseInt(curentValue[1]),
					Integer.parseInt(curentValue[2]));
			Calendar targetTime = Calendar.getInstance();
			targetTime.set(00, 00, 00, Integer.parseInt(targetValue[0]),
					Integer.parseInt(targetValue[1]),
					Integer.parseInt(targetValue[2]));
			
			if (conditional == -1)
			{
				return (curentTime.getTimeInMillis() < targetTime
						.getTimeInMillis());
			}
			if (conditional == 0)
			{
				return (curentTime.getTimeInMillis() == targetTime
						.getTimeInMillis());
			}
			if (conditional == 1)
			{
				return (curentTime.getTimeInMillis() > targetTime
						.getTimeInMillis());
			}
			if (conditional == 2)
			{
				return (curentTime.getTimeInMillis() >= targetTime
						.getTimeInMillis());
			}
			if (conditional == -2)
			{
				return (curentTime.getTimeInMillis() <= targetTime
						.getTimeInMillis());
			}
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean isValid(String strCurentTime, String strTargetTime, String separator, int conditional)
	{
		try
		{
			String[] curentValue = strCurentTime.split(separator);
			String[] targetValue = strTargetTime.split(separator);
			
			Calendar curentTime = Calendar.getInstance();
			curentTime.set(00, 00, 00, Integer.parseInt(curentValue[0]),
					Integer.parseInt(curentValue[1]),
					Integer.parseInt(curentValue[2]));
			Calendar targetTime = Calendar.getInstance();
			targetTime.set(00, 00, 00, Integer.parseInt(targetValue[0]),
					Integer.parseInt(targetValue[1]),
					Integer.parseInt(targetValue[2]));
			
			if (conditional == -1)
			{
				return (curentTime.getTimeInMillis() < targetTime
						.getTimeInMillis());
			}
			if (conditional == 0)
			{
				return (curentTime.getTimeInMillis() == targetTime
						.getTimeInMillis());
			}
			if (conditional == 1)
			{
				return (curentTime.getTimeInMillis() > targetTime
						.getTimeInMillis());
			}
			if (conditional == 2)
			{
				return (curentTime.getTimeInMillis() >= targetTime
						.getTimeInMillis());
			}
			if (conditional == -2)
			{
				return (curentTime.getTimeInMillis() <= targetTime
						.getTimeInMillis());
			}
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public Field getTargetObject()
	{
		return targetObject;
	}
	
	public void setTargetObject(Field targetObject)
	{
		this.targetObject = targetObject;
	}
	
	public int getConditional()
	{
		return conditional;
	}
	
	public void setConditional(int conditional)
	{
		this.conditional = conditional;
	}
	
}
