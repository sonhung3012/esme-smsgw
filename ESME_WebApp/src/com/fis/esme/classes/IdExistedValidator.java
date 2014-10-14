package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;

public class IdExistedValidator extends AbstractValidator{

	private IdExisted service;
	public IdExistedValidator(String errorMessage, IdExisted service) {
		super(errorMessage);
		this.service = service;
	}
	@Override
	public boolean isValid(Object value) {
		String id = value.toString().trim();
//		System.out.println(id);
		return service.isIdExisted(id);
	}

}
