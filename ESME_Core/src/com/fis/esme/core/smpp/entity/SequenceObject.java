package com.fis.esme.core.smpp.entity;

public class SequenceObject {
	private int startWith;
	private int value;
	private int incrementBy;
	
	public SequenceObject() {
		this(1, 1);
	}
	
	public SequenceObject(int startWith, int incrementBy) {
		this.incrementBy = incrementBy;
		if(startWith >= 1 && startWith <= Integer.MAX_VALUE) {
			this.startWith = startWith;
		} else {
			this.startWith = 1;
		}
		this.value = startWith;
	}
	
	public int nextValue() {
		int valueReturn = value;
		value += incrementBy;
		if(value > Integer.MAX_VALUE) {
			value = startWith;
		}
		return valueReturn;
	}
}
