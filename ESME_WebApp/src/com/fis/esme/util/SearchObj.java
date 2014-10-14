package com.fis.esme.util;

public class SearchObj {
	private String key;
	private String field;
	private String alphabet;

	public SearchObj() {
	}

	public SearchObj(String key, String field, String alphabet) {
		super();
		this.key = key;
		this.field = field;
		this.alphabet = alphabet;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
	}
}
