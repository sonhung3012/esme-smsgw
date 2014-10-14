package com.fis.esme.form.subdetail;

import com.fis.esme.component.SubDetailSearchFactory;
import com.vaadin.data.Item;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class NumberBlacklistDetailSearchFactory extends SubDetailSearchFactory
{
	private TextField numberBlacklistSearch;
	
	public NumberBlacklistDetailSearchFactory()
	{
		super();
		initSearch();
	}
	
	private void initSearch()
	{
		numberBlacklistSearch = new TextField(TM.get("pnSubBlacklist.txtMsisdn"));
		numberBlacklistSearch.setWidth("120px");
		numberBlacklistSearch.setMaxLength(11);
		String sms = TM.get("frmSystemBlackList.CompareMsisdnValidator", TM.get("pnSubBlacklist.txtMsisdn2"));
//		numberBlacklistSearch.addValidator(new RegexpValidator(
//				"^((01)|(09)|9|1)[0-9]{8,9}$", sms));
		numberBlacklistSearch.setNullRepresentation("");
	}
	
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext)
	{
		System.out.println(propertyId);
		Field field = null;
		if ("numberBlacklist".equals(propertyId))
		{
			return numberBlacklistSearch;
		}
		else{
			field = super.createField(item, propertyId, uiContext);
		}
		return field;
	}
	
}
