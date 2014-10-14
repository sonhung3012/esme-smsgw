package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;

public class PropertyExistedValidator extends AbstractValidator
{

	private PropertyExisted service;
	private String property;
	public PropertyExistedValidator(String errorMessage, PropertyExisted service, String property)
	{
		super(errorMessage);
		this.service = service;
		this.property = property;
	}

	@Override
	public boolean isValid(Object value)
	{
		return service.isPropertyExisted(this.property, value);
	}

	
	
}
