package com.fis.esme.classes;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

public class PromServiceUsageValidator extends PromPercentValidator
{
	private TextField tf1;
	private TextField tf2;
	private CheckBox cbo;
	
	public PromServiceUsageValidator(TextField tf1, TextField tf2, CheckBox cbo)
	{
		super();
		this.tf1 = tf1;
		this.tf2 = tf2;
		this.cbo = cbo;
	}
	
	@Override
	public boolean isValid(Object value)
	{
		if (!super.isValid(value))
		{
			setErrorMessage(super.getErrorMessage());
			return false;
		}
		
		if (cbo.booleanValue())
		{
			return true;
		}
		
		long val = 0;
		
		if (value == null || "".equals(value.toString().trim()))
		{
			val = 0;
		}
		
		long val1 = 0;
		long val2 = 0;
		
		if (value != null)
		{
			try
			{
				val = Long.parseLong(value.toString().trim());
			}
			catch (NumberFormatException ex)
			{
				val = 0;
			}
		}
		
		if (tf1.getValue() != null)
		{
			try
			{
				val1 = Long.parseLong(tf1.toString().trim());
			}
			catch (NumberFormatException ex)
			{
				ex.printStackTrace();
				val1 = 0;
			}
		}
		
		if (tf2.getValue() != null)
		{
			try
			{
				val2 = Long.parseLong(tf2.toString().trim());
			}
			catch (NumberFormatException ex)
			{
				ex.printStackTrace();
				val2 = 0;
			}
		}
		setErrorMessage(TM.get("promdetail.ValidateValue"));
		return (val1 + val2 + val > 0);
//		if (value == null || "".equals(value.toString().trim()))
//		{
//			int val = 0;
//			
//		}
		
//		return true;
	}
}
