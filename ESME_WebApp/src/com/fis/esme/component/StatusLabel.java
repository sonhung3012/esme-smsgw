package com.fis.esme.component;

import com.vaadin.ui.TextField;

public class StatusLabel extends TextField
{
	public StatusLabel(String caption, String value)
	{
		super(caption,value);
		this.setReadOnly(true);
	}
}
