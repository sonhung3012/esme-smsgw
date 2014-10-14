package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.AbstractField;

public class CompareIntegerValidate extends AbstractValidator {
	private boolean wrongFormatAccepted = false;
	private int integerTarget = 0;
	private int conditional;

	public CompareIntegerValidate(String errorMessage) {
		super(errorMessage);
	}

	public CompareIntegerValidate(String errorMessage, int integerTarget,
			int conditional) {
		super(errorMessage);
		setIntegerTarget(integerTarget);
		this.conditional = conditional;
	}

	public CompareIntegerValidate(String errorMessage,
			AbstractField fieldTarget, int conditional) {
		super(errorMessage);
		setIntegerTarget((Integer) fieldTarget.getValue());
		this.conditional = conditional;
	}

	@Override
	public boolean isValid(Object value) {
		String strValue = value.toString().trim();
		if (value.toString().contains(" "))
			return false;
		if (strValue.length() < 1)
			return true;

		int val;
		try {
			val = Integer.parseInt(strValue);
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

	public int getIntegerTarget() {
		return integerTarget;
	}

	public void setIntegerTarget(int integerTarget) {
		this.integerTarget = integerTarget;
	}

	public boolean isWrongFormatAccepted() {
		return wrongFormatAccepted;
	}

	public void setWrongFormatAccepted(boolean wrongFormatAccepted) {
		this.wrongFormatAccepted = wrongFormatAccepted;
	}

}
