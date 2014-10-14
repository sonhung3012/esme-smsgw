package com.fis.esme.form.subdetail;

import java.util.ArrayList;
import java.util.Calendar;

import com.fis.esme.classes.SearchDetail;
import com.fis.esme.component.CommonSubscriberPanel;

public abstract class DefaultSubscriberPanel<BEANTYPE> 
						extends CommonSubscriberPanel<BEANTYPE, SearchDetail>
{
	public abstract ArrayList<BEANTYPE> getData(SearchDetail search);
	
	public DefaultSubscriberPanel()
	{
		super();
		setFocusProperty("fromDate");
	}

	public SearchDetail createSearchObject()
	{
		Calendar ca = Calendar.getInstance();
		SearchDetail date = new SearchDetail();
		date.setToDate(ca.getTime());
		ca.add(Calendar.MONTH, -1);
		date.setFromDate(ca.getTime());
		return date;
	}
}
