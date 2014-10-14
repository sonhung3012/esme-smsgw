package com.fis.esme.classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.data.validator.AbstractValidator;

public class CheckFormatISNDValidator extends AbstractValidator {
	private String regexp1 = "^01[0-9]{9}$";
	private String regexp2 = "^09[0-9]{8}$";
	private String regexp3 = "^9[0-9]{8}$";
	private String regexp4 = "^1[0-9]{9}$";

	private String regexp8409 = "[0-9]{12}$";
	private String regexp8401 = "[0-9]{13}$";
	private String regexp849 = "[0-9]{11}$";
	private String regexp841 = "[0-9]{12}$";

	Pattern pattern = null;
	Matcher matcher = null;

	public CheckFormatISNDValidator(String errorMessage) {
		super(errorMessage);
	}

	// public static void main(String[] arg) {
	// CheckFormatISNDValidator s = new CheckFormatISNDValidator("lỗi");
	// System.out.println(s.isValid("8401642554485"));
	// System.out.println(s.isValid("840942321989"));
	// System.out.println(s.isValid("841642554485"));
	// System.out.println(s.isValid("849423219895"));
	// }

	@Override
	public boolean isValid(Object value) {

		if ("".equals(value) || value == null) {
			return true;
		}
		String[] str = value.toString().trim().split(";");

		for (String val : str) {

			if (val.startsWith("9")) {
				pattern = Pattern.compile(regexp3);
			} else if (val.startsWith("1")) {
				pattern = Pattern.compile(regexp4);
			} else if (val.startsWith("09")) {
				pattern = Pattern.compile(regexp2);
			} else if (val.startsWith("01")) {
				pattern = Pattern.compile(regexp1);
			} else if (val.startsWith("841")) {
				pattern = Pattern.compile(regexp841);
			} else if (val.startsWith("8401")) {
				pattern = Pattern.compile(regexp8401);
			} else if (val.startsWith("8409")) {
				pattern = Pattern.compile(regexp8409);
			} else if (val.startsWith("849")) {
				pattern = Pattern.compile(regexp849);
			} else {
				return false;
			}

			matcher = pattern.matcher(val.trim());

			if (!matcher.matches()) {
				return false;
			}

		}
		return true;
	}

}
