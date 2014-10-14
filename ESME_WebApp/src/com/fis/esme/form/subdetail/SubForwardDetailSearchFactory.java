package com.fis.esme.form.subdetail;

import com.fis.esme.component.SubDetailSearchFactory;
import com.vaadin.data.Item;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

public class SubForwardDetailSearchFactory extends SubDetailSearchFactory
{
	private TextField subForwardSearch;
	
	public SubForwardDetailSearchFactory()
	{
		super(false,false);
		initSearch();
	}
	
	private void initSearch()
	{
		subForwardSearch = new TextField("Số chuyển tiếp");
		subForwardSearch.setWidth("100px");
		subForwardSearch.setMaxLength(11);
		String sms = "<span style='color:red'>Số chuyển tiếp cần nhập đúng định dạng 09xxxxxxxx,01xxxxxxxxx hoặc 9xxxxxxxx, 1xxxxxxxxx </span>";
		// subForwardSearch.addValidator(new RegexpValidator(
		// "^((01)|(09)|9|1)[0-9]{8,9}$", sms));
		subForwardSearch.setNullRepresentation("");
	}
	
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext)
	{
		if ("subForwardSearch".equals(propertyId))
		{
			return subForwardSearch;
		}
		return super.createField(item, propertyId, uiContext);
	}
	
}
