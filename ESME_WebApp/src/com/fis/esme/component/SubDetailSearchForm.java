package com.fis.esme.component;

import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class SubDetailSearchForm extends Form
{
	private HorizontalLayout mainLayout;
	private FormLayout pnlLeft;
	private FormLayout pnlRight;
	
	public SubDetailSearchForm()
	{
		mainLayout = new HorizontalLayout();
		setLayout(mainLayout);
		mainLayout.setMargin(false);
		pnlLeft = new FormLayout();
		pnlLeft.setSpacing(false);
		pnlLeft.setMargin(false);
		pnlRight = new FormLayout();
		pnlRight.setSpacing(false);
		pnlRight.setMargin(false);
		mainLayout.addComponent(pnlLeft);
		mainLayout.addComponent(pnlRight);
		setInvalidCommitted(false); // no invalid values in datamodel
//		setFormFieldFactory(fieldFactory);
	}
	
	@Override
	protected void attachField(Object propertyId, Field field)
	{
		if (propertyId.equals("fromDate"))
		{
			pnlLeft.addComponent(field);
		}
		else
		{
			pnlRight.addComponent(field);
		}
	}
}
