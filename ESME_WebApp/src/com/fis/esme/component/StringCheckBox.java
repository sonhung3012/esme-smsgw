package com.fis.esme.component;

import com.vaadin.data.Property;
import com.vaadin.ui.CheckBox;

public class StringCheckBox extends CheckBox
{
	@SuppressWarnings("deprecation")
	public StringCheckBox()
	{
		setSwitchMode(true);
	}
	
	public StringCheckBox(String caption, boolean initialState)
	{
		super(caption, initialState);
	}
	
	@SuppressWarnings("deprecation")
	public StringCheckBox(String caption, ClickListener listener)
	{
		super(caption, listener);
		setSwitchMode(true);
	}
	
	@SuppressWarnings("deprecation")
	public StringCheckBox(String caption, Object target, String methodName)
	{
		super(caption, target, methodName);
		setSwitchMode(true);
	}
	
	@SuppressWarnings("deprecation")
	public StringCheckBox(String caption, Property dataSource)
	{
		super(caption, dataSource);
		setSwitchMode(true);
	}
	
	public StringCheckBox(String caption)
	{
		super(caption, false);
	}
	
	@Override
	protected void setInternalValue(Object newValue)
	{
		// Make sure only booleans get through
		if (newValue == null)
		{
			throw new IllegalArgumentException(getClass().getSimpleName()
					+ " only accepts NON-NULL values");
		}
		else
		{
			if (newValue instanceof String)
			{
				boolean checked = "1".equals(newValue);
				super.setInternalValue(checked);
			}
			else if (newValue instanceof Boolean)
			{
				super.setInternalValue(newValue);
			}
			
			else
			{
				throw new IllegalArgumentException(getClass().getSimpleName()
						+ " only accepts Boolean values");
			}
		}
	}
	
	public Object getValue()
	{
		Object value = super.getValue();
		
		if (value instanceof String)
		{
			return "1".equals(value);
		}
		
		return value;
	}
}
