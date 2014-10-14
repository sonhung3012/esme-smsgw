package com.fis.esme.component;

import java.util.Calendar;

import com.fis.esme.classes.CompareDateTimeValidator;
import com.fis.esme.classes.NotOverDateTime;
import com.fis.esme.util.FormUtil;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.PopupDateField;

import eu.livotov.tpt.i18n.TM;

public class SubDetailSearchFactory extends DefaultFieldFactory
{
	private final String FIELD_WIDTH = "100px";
	private PopupDateField dtFromDate;
	private PopupDateField dtToDate;
	
	protected boolean fromDateNotNull;
	protected boolean toDateNotNull;
	protected int period = 6;
	private int type = Calendar.MONTH;
	
	public SubDetailSearchFactory()
	{
		this(true,true);
	}
	
	public SubDetailSearchFactory(boolean fromDate, boolean toDate, int period)
	{
		this(fromDate, toDate, period, Calendar.MONTH);
	}
	
	public SubDetailSearchFactory(boolean fromDate, boolean toDate, int period, int type)
	{
		this.fromDateNotNull = fromDate;
		this.toDateNotNull = toDate;
		this.period = period;
		this.type = type;
		
		initComponent();
		initValidate();
	}
	
	public SubDetailSearchFactory(boolean fromDate, boolean toDate)
	{
		this(fromDate,toDate,6);
	}
	
	private void initComponent()
	{
		dtFromDate = new PopupDateField(TM.get("main.sub.common.search.factory.fromDate"));
		dtFromDate.setDateFormat("dd/MM/yyyy");
		dtFromDate.setResolution(DateField.RESOLUTION_DAY);
		
		dtToDate = new PopupDateField(TM.get("main.sub.common.search.factory.toDate"));
		dtToDate.setDateFormat("dd/MM/yyyy");
		dtToDate.setResolution(DateField.RESOLUTION_DAY);
		
		sameWidth(FIELD_WIDTH);
	}
	
	public void setFromDateTooltip(String tooltip)
	{
		dtFromDate.setDescription(tooltip);
	}
	
	public void setToDateTooltip(String tooltip)
	{
		dtToDate.setDescription(tooltip);
	}
	
	private void initValidate()
	{
		if (fromDateNotNull)
		{
			dtFromDate.setRequired(true);
			dtFromDate.setRequiredError(FormUtil.getErrorMessageI18n(dtFromDate.getCaption(), 
					"main.common.validate.required"));
//			dtFromDate.setRequiredError("<b>" + dtFromDate.getCaption()
//					+ "</b> không được để trống");
		}
		dtFromDate.setParseErrorMessage(TM.get("main.common.validate.date.format",dtFromDate.getCaption(),dtFromDate.getDateFormat().toUpperCase()));
		
		if (toDateNotNull)
		{
			dtToDate.setRequired(true);
			dtToDate.setRequiredError(FormUtil.getErrorMessageI18n(dtToDate.getCaption(), 
							"main.common.validate.required"));
		}
		dtToDate.setParseErrorMessage(TM.get("main.common.validate.date.format",dtToDate.getCaption(),dtToDate.getDateFormat().toUpperCase()));
		
		CompareDateTimeValidator val = new CompareDateTimeValidator(TM.get("main.sub.common.compare.date", dtToDate.getCaption(), dtFromDate.getCaption()), dtFromDate, 2,true);
		val.setClearTime(true);
		dtToDate.addValidator(val);
		
		if (type == Calendar.YEAR)
		{
			NotOverDateTime val2 = new NotOverDateTime(TM.get("main.sub.common.compare.year", dtToDate.getCaption(), dtFromDate.getCaption(),period),
					dtFromDate, -2, Calendar.YEAR, period,true);
			dtToDate.addValidator(val2);
		}
		else if (type == Calendar.MONTH)
		{
			NotOverDateTime val2 = new NotOverDateTime(TM.get("main.sub.common.compare.month", dtToDate.getCaption(), dtFromDate.getCaption(),period),
					dtFromDate, -2, Calendar.MONTH, period,true);
			dtToDate.addValidator(val2);
		}
	}
	
	protected void sameWidth(String width)
	{
		dtFromDate.setWidth(width);
		dtToDate.setWidth(width);
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
