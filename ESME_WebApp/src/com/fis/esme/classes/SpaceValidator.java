package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;

@SuppressWarnings("serial")
public class SpaceValidator extends AbstractValidator {

	public SpaceValidator(String errorMessage) {

		super(errorMessage);
	}

	public boolean isValid(Object value) {

		if ((String.valueOf(value)).trim().length() > 0) {
			return true;
		} else
			return false;
	}

}
