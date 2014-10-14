package com.fis.esme.form.subdetail;

import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;

public class SubForwardDetailSearchForm extends Form
{
	private HorizontalLayout mainLayout;
	private FormLayout pnlLeft;
	private FormLayout pnlRight;
	private FormLayout pnlMid;
	
	public SubForwardDetailSearchForm()
	{
		mainLayout = new HorizontalLayout();
		setLayout(mainLayout);
		mainLayout.setMargin(false);
		pnlLeft = new FormLayout();
		pnlLeft.setSpacing(false);
		pnlLeft.setMargin(false);
		pnlMid = new FormLayout();
		pnlMid.setSpacing(false);
		pnlMid.setMargin(false);
		pnlRight = new FormLayout();
		pnlRight.setSpacing(false);
		pnlRight.setMargin(false);
		mainLayout.addComponent(pnlLeft);
		mainLayout.addComponent(pnlMid);
		mainLayout.addComponent(pnlRight);
		setInvalidCommitted(false); // no invalid values in datamodel
		// setFormFieldFactory(fieldFactory);
	}
	
	@Override
	protected void attachField(Object propertyId, Field field)
	{
		if (propertyId.equals("toDate"))
		{
			pnlRight.addComponent(field);
		}
		else if (propertyId.equals("fromDate"))
		{
			pnlMid.addComponent(field);
		}
		else
		{
			pnlLeft.addComponent(field);
		}
	}
}
