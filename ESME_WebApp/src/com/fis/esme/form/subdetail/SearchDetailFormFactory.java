package com.fis.esme.form.subdetail;

import com.fis.esme.classes.CompareDateTimeValidator;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;

import eu.livotov.tpt.i18n.TM;

public class SearchDetailFormFactory extends DefaultFieldFactory
{
	private DateField dtFromDate;
	private DateField dtToDate;
	
	public SearchDetailFormFactory()
	{
		initComponent();
		initValidate();
	}
	
	private void initComponent()
	{
		dtFromDate = new DateField("Từ ngày");
		dtFromDate.setResolution(DateField.RESOLUTION_SEC);
		
		dtToDate = new DateField("Tới ngày");
		dtToDate.setResolution(DateField.RESOLUTION_SEC);
	}
	
	private void initValidate()
	{
		dtFromDate.setDateFormat("dd/MM/yyyy");
		dtFromDate.setRequired(true);
		dtFromDate.setRequiredError("<b>Ngày bắt đầu</b> không được để trống!");
		
		dtToDate.setDateFormat("dd/MM/yyyy");
		
		CompareDateTimeValidator val = new CompareDateTimeValidator(
				"<b>Ngày kết thúc</b> phải lớn hơn <b>ngày bắt đầu.</b>",
				dtFromDate, 1);
		val.setClearTime(true);
		dtToDate.addValidator(val);
	}
	
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext)
	{
		Field field = null;
		if ("fromDate".equals(propertyId))
		{
			return dtFromDate;
		}
		else if ("toDate".equals(propertyId))
		{
			return dtToDate;
		}
		
		else
		{
			field = super.createField(item, propertyId, uiContext);
		}
		return field;
	}
}
