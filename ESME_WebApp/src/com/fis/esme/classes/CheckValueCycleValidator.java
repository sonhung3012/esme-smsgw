package com.fis.esme.classes;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.IntegerValidator;

public class CheckValueCycleValidator extends AbstractValidator{
	private Integer val;

	public CheckValueCycleValidator(String errorMessage) {
		super(errorMessage);
	}

	@Override
	public boolean isValid(Object value) {
		try 
		{
			if (value == null || "".equals(value))
			{
				return false;
			}
			else
			{
				int val = Integer.parseInt(value.toString().trim());
				return val >= 0;
			}
//			try {
//				System.out.println("checkvaluecycle: " + value);
//				
//				val = Integer.parseInt(value);
//				return (val > 0);
//			} catch (Exception e) {
//				System.out.println("exception inner");
//				e.printStackTrace();
//				return true;
//			}
		} catch (Exception e) {
//			System.out.println("exception outer");
//			e.printStackTrace();
			return false;
		}
		// return true;
	}
}
