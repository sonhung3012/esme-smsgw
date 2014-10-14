package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.TextField;

public class ValueNotNullValidator extends AbstractValidator {
	// private TextField tf;
	private TextField tf1;
	private TextField tf2;

	public ValueNotNullValidator(String message, TextField tf1, TextField tf2) {
		super(message);
		// this.valueNotNull = valueNotNull;
		// this.tf = tf;
		this.tf1 = tf1;
		this.tf2 = tf2;
	}

	@Override
	public boolean isValid(Object value) {
		int val = 0;
		int val1 = 0;
		int val2 = 0;
		
		if (value != null)
		{
			try
			{
				val = Integer.parseInt(value.toString().trim());
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
				val1 = Integer.parseInt(tf1.toString().trim());
			}
			catch (NumberFormatException ex)
			{
				val1 = 0;
			}
		}
		
		if (tf2.getValue() != null)
		{
			try
			{
				val2 = Integer.parseInt(tf2.toString().trim());
			}
			catch (NumberFormatException ex)
			{
				val2 = 0;
			}
		}
		
		return (val1 + val2 + val > 0);

	}

}
