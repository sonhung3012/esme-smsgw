/**
 * kiem tra từ thuê bao tới thuê bao có thuộc cùng một dải số 
 * va toi thua bao >= tu thue bao hay không
 */
package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

public class CompareNumberValidator extends AbstractValidator
{
	private Field targetfield;
	
	public Field getTxtFromNumber()
	{
		return targetfield;
	}
	
	public void setTxtFromNumber(Field txtFromNumber)
	{
		this.targetfield = txtFromNumber;
	}
	
	public CompareNumberValidator(String errorMessage, Field targetField)
	{
		super(errorMessage);
		this.setTxtFromNumber(targetField);
	}
	
	@Override
	public boolean isValid(Object value)
	{
		Long toNumber, fromNumber;
		toNumber = Long.parseLong(value.toString().trim());
		fromNumber = Long.parseLong(targetfield.getValue().toString().trim());
		
		return (fromNumber <= toNumber);
	}
	
}
