package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;

public class NameExistedValidator extends AbstractValidator
{

	private NameExisted service;

	public NameExistedValidator(String errorMessage, NameExisted service)
	{
		super(errorMessage);
		this.service = service;
	}

	@Override
	public boolean isValid(Object value)
	{
		String name = value.toString().trim();
//		System.out.println(name);
		return service.isNameExisted(name);
	}
}
