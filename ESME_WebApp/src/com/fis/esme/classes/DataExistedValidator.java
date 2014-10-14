package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.ComboBox;

public class DataExistedValidator extends AbstractValidator
{
	private DataExisted dataExisted;
	private ComboBox comboBox;
	
	public DataExistedValidator(String message, DataExisted dataExisted,
			ComboBox comboBox)
	{
		super(message);
		this.dataExisted = dataExisted;
		this.comboBox = comboBox;
	}
	
	@Override
	public boolean isValid(Object value)
	{
//		System.out.println(dataExisted.isExitsted(value, comboBox.getValue()));
		return dataExisted.isExitsted(value, comboBox.getValue());
	}
	
}
