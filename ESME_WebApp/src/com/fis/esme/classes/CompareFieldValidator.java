/**
 * 
 */
package com.fis.esme.classes;

import java.util.Date;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.Field;

/**
 * @author HaiTX
 *
 */
public class CompareFieldValidator extends AbstractValidator
{

	private Field targetField;
	public Field getTargetField() {
		return targetField;
	}

	public void setTargetField(Field targetField) {
		this.targetField = targetField;
	}

	public CompareFieldValidator(String errorMessage)
	{
		super(errorMessage);
	}

	public CompareFieldValidator(String errorMessage, Field targetField)
	{
		super(errorMessage);
		this.setTargetField(targetField);
	}
	
	@Override
	public boolean isValid(Object value)
	{
		String val = value.toString();
		String targetValue = targetField.getValue().toString();
		if (val.equals(targetValue))
			return false;
		else
			return true;
	}

	

}
