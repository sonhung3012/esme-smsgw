package com.fis.esme.form.subdetail;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

public class SearchBackupDetailFactory extends DefaultFieldFactory
{
	private TextField txtcontactmsisdn;
	private TextField txtcontactname;
	
	public SearchBackupDetailFactory()
	{
		initSearch();
	}
	
	private void initSearch()
	{
		txtcontactmsisdn = new TextField(TM.get("phonebackup.contactmsisdn.title"));
		txtcontactmsisdn.setNullRepresentation("");
		
		txtcontactname = new TextField(TM.get("phonebackup.contactname.title"));
		txtcontactname.setNullRepresentation("");
	}
	
//	public void setCaptionField(String caption)
//	{
//		txtcontactname.setCaption(caption);
//	}
	
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext)
	{
		if ("contactMsisdn".equals(propertyId))
		{
			return txtcontactmsisdn;
		}
		else if ("contactName".equals(propertyId))
		{
			return txtcontactname;
		}
		return super.createField(item, propertyId, uiContext);
	}
	
}
