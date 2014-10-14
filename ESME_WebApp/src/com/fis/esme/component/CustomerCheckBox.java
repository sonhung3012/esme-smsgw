package com.fis.esme.component;

import com.vaadin.data.Property;
import com.vaadin.ui.CheckBox;

public class CustomerCheckBox extends CheckBox
{

	private int id;
	
	public CustomerCheckBox() {
		super();
		
	}

	public CustomerCheckBox(String caption, boolean initialState) {
		super(caption, initialState);
		
	}

	public CustomerCheckBox(String caption, ClickListener listener) {
		super(caption, listener);
		
	}

	public CustomerCheckBox(String caption, Object target, String methodName) {
		super(caption, target, methodName);
		
	}

	public CustomerCheckBox(String caption, Property dataSource) {
		super(caption, dataSource);
		
	}

	public CustomerCheckBox(String caption) {
		super(caption);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
