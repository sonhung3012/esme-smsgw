package com.fis.esme.form.subdetail;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

public class SearchPhoneBookDetailFactory extends DefaultFieldFactory
{
	private TextField txtmsisdn;
	private TextField txtname;
	
	public SearchPhoneBookDetailFactory()
	{
		initSearch();
	}
	
	private void initSearch()
	{
		txtmsisdn = new TextField(TM.get("SearchPhoneBookDetail.txtmsisdn"));
		// txtmsisdn.setWidth("100px");
		txtmsisdn.setMaxLength(15);
		// String sms =
		// "<span style='color:red'>Số điện thoại sai định dạng 09xxxxxxxx,01xxxxxxxxx hoặc 9xxxxxxxx, 1xxxxxxxxx </span>";
		// txtmsisdn.addValidator(new RegexpValidator(
		// "^((01)|(09)|9|1)[0-9]{8,9}$", sms));
		// txtmsisdn.addValidator(new RegexpValidator(
		// "^(09|9)[0-9]{8}|(01|1)[0-9]{9}$", sms));
		// txtmsisdn.setNullRepresentation("");
		
		txtname = new TextField(TM.get("SearchPhoneBookDetail.txtname"));
		// txtmsisdn.setWidth("100px");
		txtname.setMaxLength(20);
	}
	
	public void setCaptionField(String caption)
	{
		txtname.setCaption(caption);
	}
	
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext)
	{
		if ("msisdn".equals(propertyId))
		{
			return txtmsisdn;
		}
		else if ("name".equals(propertyId))
		{
			return txtname;
		}
		return super.createField(item, propertyId, uiContext);
	}
	
}
