/**
 * 
 */
package com.fis.esme.classes;

import java.util.Calendar;
import java.util.Date;

import com.fis.esme.util.FormUtil;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.DateField;

/**
 * @author HaiTX
 * 
 */
public class CompareDateTimeValidator extends AbstractValidator {
	private DateField targetObject;
	private int conditional;
	private Integer[] notSetFields = null;
	private boolean targetNullAllowed = false;
	private int largerSmaller = -1;
	private int largerSmallerField = -1;
	private boolean clearTime = false;

	public CompareDateTimeValidator(String errorMessage) {
		super(errorMessage);
	}

	public CompareDateTimeValidator(String errorMessage,
			DateField targetObject, int conditional) {
		this(errorMessage, targetObject, conditional, false);
	}

	public CompareDateTimeValidator(String errorMessage, DateField tagetObject,
			int conditional, Integer[] notSetFields) {
		this(errorMessage, tagetObject, conditional, notSetFields, false);
	}

	public CompareDateTimeValidator(String errorMessage, DateField tagetObject,
			Integer[] notSetFields, int largerSmallerField, int largerSmaller) {
		this(errorMessage, tagetObject, -1, notSetFields, largerSmaller,
				largerSmallerField, false);
	}

	public CompareDateTimeValidator(String errorMessage, DateField tagetObject,
			int conditional, boolean targetNullAllowed) {
		super(errorMessage);
		this.setTargetObject(tagetObject);
		this.conditional = conditional;
		this.targetNullAllowed = targetNullAllowed;
	}

	public CompareDateTimeValidator(String errorMessage, DateField tagetObject,
			int conditional, Integer[] notSetFields, boolean targetNullAllowed) {
		super(errorMessage);
		this.setTargetObject(tagetObject);
		this.setNotSetFields(notSetFields);
		this.conditional = conditional;
		this.targetNullAllowed = targetNullAllowed;
	}

	public CompareDateTimeValidator(String errorMessage, DateField tagetObject,
			int conditional, Integer[] notSetFields, int largerSmaller,
			int largerSmallerField, boolean targetNullAllowed) {
		super(errorMessage);
		this.setTargetObject(tagetObject);
		this.setNotSetFields(notSetFields);
		this.conditional = conditional;
		this.targetNullAllowed = targetNullAllowed;
		this.largerSmaller = largerSmaller;
		this.largerSmallerField = largerSmallerField;
	}

	private static Calendar doNotSetField(Calendar calendar, Integer[] fields) {
		for (Integer integer : fields) {
			calendar.set(integer, 00);
		}
		return calendar;
	}

	@Override
	public boolean isValid(Object value) {
		Date val = (Date) value;
		Date targetDate = (Date) getTargetObject().getValue();

		if (targetDate == null)
			return false;

		if (clearTime) {
			val = FormUtil.toDate(val, FormUtil.notSetDateFields);
			targetDate = FormUtil.toDate(targetDate, FormUtil.notSetDateFields);
		}

		if (getNotSetFields() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(targetDate);
			calendar = doNotSetField(calendar, getNotSetFields());
			targetDate = calendar.getTime();

			calendar = Calendar.getInstance();
			calendar.setTime((Date) value);
			calendar = doNotSetField(calendar, getNotSetFields());
			val = calendar.getTime();
		}

		if (targetDate == null) {
			if (targetNullAllowed) {
				return true;
			} else {
				return false;
			}
		}

		if (largerSmallerField >= 0 && largerSmaller >= 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date) value);

			Calendar calTargetDate = Calendar.getInstance();
			calTargetDate.setTime(targetDate);

			int v = calendar.get(largerSmallerField);
			int vTarget = calTargetDate.get(largerSmallerField);
			boolean b = ((v <= vTarget - largerSmaller) || (v <= vTarget
					+ largerSmaller));
			return b;
		}

		if (conditional == -1)
			return val.before(targetDate);
		if (conditional == 0)
			return val.toString().equalsIgnoreCase(targetDate.toString());
		if (conditional == 1)
			return val.after(targetDate);
		if (conditional == 2) {
			return (val.equals(targetDate) || val.after(targetDate));
		}
		if (conditional == -2) {
			return (val.equals(targetDate) || val.before(targetDate));
		}
		return false;
	}

	private DateField getTargetObject() {
		return targetObject;
	}

	private void setTargetObject(DateField targetObject) {
		this.targetObject = targetObject;
	}

	private Integer[] getNotSetFields() {
		return notSetFields;
	}

	private void setNotSetFields(Integer[] notSetFields) {
		this.notSetFields = notSetFields;
	}

	public boolean isTargetNullAllowed() {
		return targetNullAllowed;
	}

	public void setTargetNullAllowed(boolean targetNullAllowed) {
		this.targetNullAllowed = targetNullAllowed;
	}

	public boolean isClearTime() {
		return clearTime;
	}

	public void setClearTime(boolean clearTime) {
		this.clearTime = clearTime;
	}

}
