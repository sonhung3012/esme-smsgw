/**
 * 
 */
package com.fis.esme.classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.data.validator.AbstractValidator;

public class CompareMsisdnValidator extends AbstractValidator {
	private String regexp1 = "^01[0-9]{9}$";
	private String regexp2 = "^09[0-9]{8}$";
	private String regexp3 = "^9[0-9]{8}$";
	private String regexp4 = "^1[0-9]{9}$";
	Pattern pattern = null;
	Matcher matcher = null;

	public CompareMsisdnValidator(String errorMessage) {
		super(errorMessage);
	}

	@Override
	public boolean isValid(Object value) {
		String val = value.toString().trim();
		if (val.equals("") || val == null) {
			return true;
		}
		if (val.startsWith("9")) {
			pattern = Pattern.compile(regexp3);
		} else if (val.startsWith("1")) {
			pattern = Pattern.compile(regexp4);
		} else if (val.startsWith("09")) {
			pattern = Pattern.compile(regexp2);
		} else {
			pattern = Pattern.compile(regexp1);
		}
		matcher = pattern.matcher(val.trim());
		return matcher.matches();
	}
}
