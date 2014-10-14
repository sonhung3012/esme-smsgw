package com.fis.esme.form.subdetail;

import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;

public class SubSmsLogDetailSearchForm extends Form
{
	private HorizontalLayout mainLayout;
	private FormLayout pnlLeft;
	private FormLayout pnlRight;
	
	public SubSmsLogDetailSearchForm()
	{
		mainLayout = new HorizontalLayout();
		setLayout(mainLayout);
		mainLayout.setSpacing(true);
		mainLayout.setMargin(false);
		pnlLeft = new FormLayout();
		pnlLeft.setStyleName("formlayout-notop");
		pnlLeft.setSpacing(true);
		pnlLeft.setMargin(false);
		pnlRight = new FormLayout();
		pnlRight.setStyleName("formlayout-notop");
		pnlRight.setSpacing(true);
		pnlRight.setMargin(false);
		mainLayout.addComponent(pnlRight);
		mainLayout.addComponent(pnlLeft);
		setInvalidCommitted(false); // no invalid values in datamodel
		// setFormFieldFactory(fieldFactory);
	}
	
	@Override
	protected void attachField(Object propertyId, Field field)
	{
		if (propertyId.equals("toDate"))
		{
			pnlLeft.addComponentAsFirst(field);
		}
		else if (propertyId.equals("direction"))
		{
			pnlLeft.addComponent(field);
		}
		else if (propertyId.equals("service"))
		{
			pnlRight.addComponent(field);
		}
		else if (propertyId.equals("fromDate"))
		{
			pnlRight.addComponentAsFirst(field);
		}
	}
}
