package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.AbstractField;

public class CompareNumberValidate extends AbstractValidator {
	private boolean wrongFormatAccepted = false;
	private long integerTarget = 0;
	private int conditional;

	public CompareNumberValidate(String errorMessage) {
		super(errorMessage);
	}

	public CompareNumberValidate(String errorMessage, long integerTarget,
			int conditional) {
		super(errorMessage);
		setIntegerTarget(integerTarget);
		this.conditional = conditional;
	}

	public CompareNumberValidate(String errorMessage, AbstractField fieldTarget,
			int conditional) {
		super(errorMessage);
		if (fieldTarget.getValue().toString().length() > 0)
			setIntegerTarget(Integer
					.parseInt(fieldTarget.getValue().toString()));
		else
			setIntegerTarget(0);
		this.conditional = conditional;
	}

	@Override
	public boolean isValid(Object value) {
		String strValue = value.toString().trim();
		if (strValue.length() < 1)
			return true;

		long val;
		try {
			val = Long.parseLong(strValue);
		} catch (NumberFormatException e) {
			return isWrongFormatAccepted();
		}
		if (conditional == -1)
			return val < getIntegerTarget();
		if (conditional == 0)
			return val == getIntegerTarget();
		if (conditional == 1)
			return val > getIntegerTarget();
		if (conditional == 2) {
			return (val > getIntegerTarget() || val == getIntegerTarget());
		}
		if (conditional == -2) {
			return (val < getIntegerTarget() || val == getIntegerTarget());
		}
		return false;
	}

	public long getIntegerTarget() {
		return integerTarget;
	}

	public void setIntegerTarget(long integerTarget) {
		this.integerTarget = integerTarget;
	}

	public boolean isWrongFormatAccepted() {
		return wrongFormatAccepted;
	}

	public void setWrongFormatAccepted(boolean wrongFormatAccepted) {
		this.wrongFormatAccepted = wrongFormatAccepted;
	}

}
