package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.TextField;

public class SystemBlacklistCheckerValidation extends AbstractValidator{
	private SystemBlacklistChecker service;
	private TextField fromNumber;
	public SystemBlacklistCheckerValidation(String errorMessage, SystemBlacklistChecker service, TextField fromNumber)
	{
		super(errorMessage);
		this.service = service;
		this.fromNumber = fromNumber;
	}

	@Override
	public boolean isValid(Object value) 
	{
		String toNumber = value.toString().trim();
		try 
		{
			return service.checkSystemBlacklistExisted(fromNumber.getValue().toString().trim(), toNumber);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
}
