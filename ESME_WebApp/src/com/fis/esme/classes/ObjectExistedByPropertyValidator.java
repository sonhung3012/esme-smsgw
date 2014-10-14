package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.Field;

public class ObjectExistedByPropertyValidator extends AbstractValidator
{
	
	private ObjectExistedByProperty service;
	private Field[] field;
	
	public ObjectExistedByPropertyValidator(String errorMessage,
			ObjectExistedByProperty service, Field[] field)
	{
		super(errorMessage);
		this.service = service;
		this.field = field;
	}
	
	@Override
	public boolean isValid(Object value)
	{
		return service.isObjectExisted(value, field);
	}
	
}
