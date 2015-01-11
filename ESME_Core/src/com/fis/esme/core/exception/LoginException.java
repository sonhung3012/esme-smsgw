package com.fis.esme.core.exception;

public class LoginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int iErrorCode;
	public String strErrorMessage = "";

	public LoginException(int code, String message) {
		iErrorCode = code;
		strErrorMessage = message;
	}

	public int getErrorCode() {
		return iErrorCode;
	}

	public void setErrorCode(int iErrorCode) {
		this.iErrorCode = iErrorCode;
	}

	public String getErrorMessage() {
		return strErrorMessage;
	}

	public void setErrorMessage(String strErrorMessage) {
		this.strErrorMessage = strErrorMessage;
	}

}
