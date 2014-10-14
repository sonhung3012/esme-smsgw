package com.fis.esme.component;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.TextField;

public class PromTextField extends TextField
{

	public PromTextField()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public PromTextField(Property dataSource)
	{
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

	public PromTextField(String caption, Property dataSource)
	{
		super(caption, dataSource);
		// TODO Auto-generated constructor stub
	}

	public PromTextField(String caption, String value)
	{
		super(caption, value);
		// TODO Auto-generated constructor stub
	}

	public PromTextField(String caption)
	{
		super(caption);
		// TODO Auto-generated constructor stub
	}
	
	public void validate() throws Validator.InvalidValueException
	{
		Collection<Validator> validators = getValidators();
		// If there is no validator, there can not be any errors
		if (validators == null)
		{
			return;
		}
		
		// Initialize temps
		Validator.InvalidValueException firstError = null;
		LinkedList<InvalidValueException> errors = null;
		final Object value = getValue();
		
		// Gets all the validation errors
		for (final Iterator<Validator> i = validators.iterator(); i.hasNext();)
		{
			try
			{
				(i.next()).validate(value);
			}
			catch (final Validator.InvalidValueException e)
			{
				if (firstError == null)
				{
					firstError = e;
				}
				else
				{
					if (errors == null)
					{
						errors = new LinkedList<InvalidValueException>();
						errors.add(firstError);
					}
					errors.add(e);
				}
			}
		}
		
		// If there were no error
		if (firstError == null)
		{
			return;
		}
		
		// If only one error occurred, throw it forwards
		if (errors == null)
		{
			throw firstError;
		}
		
		// Creates composite validator
		final Validator.InvalidValueException[] exceptions = new Validator.InvalidValueException[errors
				.size()];
		int index = 0;
		for (final Iterator<InvalidValueException> i = errors.iterator(); i
				.hasNext();)
		{
			exceptions[index++] = i.next();
		}
		
		throw new Validator.InvalidValueException(null, exceptions);
	}
}
