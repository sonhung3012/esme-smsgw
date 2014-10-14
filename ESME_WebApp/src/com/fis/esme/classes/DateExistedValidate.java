package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.ComboBox;

public class DateExistedValidate extends AbstractValidator {
	private DateExitsted service;
	private ComboBox toDate;

	public DateExistedValidate(String errorMessage, DateExitsted service,
			ComboBox toDate) {
		super(errorMessage);
		this.service = service;
		this.toDate = toDate;
	}

	@Override
	public boolean isValid(Object value) {

		int fronDate = (Integer) value;
		int toDate = (Integer) this.toDate.getValue();
		// System.out.println(fronDate);
		// System.out.println(toDate);
		return service.isDateExitsted(fronDate, toDate);
	}

}
