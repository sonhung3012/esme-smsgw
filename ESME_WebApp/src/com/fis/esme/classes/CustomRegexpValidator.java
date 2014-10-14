package com.fis.esme.classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fis.esme.util.MessageAlerter;
import com.vaadin.data.validator.AbstractStringValidator;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class CustomRegexpValidator extends AbstractStringValidator {

	private Pattern pattern;
	private boolean complete;
	private transient Matcher matcher = null;
	private boolean acceptEmty = false;

	/**
	 * Creates a validator for checking that the regular expression matches the
	 * complete string to validate.
	 * 
	 * @param regexp
	 *            a Java regular expression
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 */
	public CustomRegexpValidator(String regexp, String errorMessage) {
		this(regexp, true, errorMessage);
	}

	/**
	 * Creates a validator for checking that the regular expression matches the
	 * string to validate.
	 * 
	 * @param regexp
	 *            a Java regular expression
	 * @param complete
	 *            true to use check for a complete match, false to look for a
	 *            matching substring
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 */
	public CustomRegexpValidator(String regexp, boolean complete,
			String errorMessage) {
		super(errorMessage);
		pattern = Pattern.compile(regexp);
		this.complete = complete;
	}

	public CustomRegexpValidator(String regexp, boolean complete,
			String errorMessage, boolean acceptEmty) {
		super(errorMessage);
		this.setAcceptEmty(acceptEmty);
		pattern = Pattern.compile(regexp);
		this.complete = complete;
	}

	public boolean isValid(String value) {
		return this.isValidString(value);
	}

	@Override
	protected boolean isValidString(String value) {
		if (value == null)
			return true;
		String val = value.trim();
		if (isAcceptEmty() && val.length() > 0) {
			if (complete) {

				return getMatcher(val).matches();

			} else {
				return getMatcher(val).find();
			}
		} else if (!isAcceptEmty()) {
			if (complete) {

				return getMatcher(val).matches();

			} else {
				return getMatcher(val).find();
			}
		} else {
			return true;
		}
	}

	/**
	 * Get a new or reused matcher for the pattern
	 * 
	 * @param value
	 *            the string to find matches in
	 * @return Matcher for the string
	 */
	private Matcher getMatcher(String value) {
		if (matcher == null) {
			matcher = pattern.matcher(value);
		} else {
			matcher.reset(value);
		}
		return matcher;
	}

	public boolean isAcceptEmty() {
		return acceptEmty;
	}

	public void setAcceptEmty(boolean acceptEmty) {
		this.acceptEmty = acceptEmty;
	}

}
