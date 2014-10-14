package com.fis.esme.classes;

import java.util.Date;

import com.fis.esme.util.FormUtil;

public class SearchDetail
{
	private Date fromDate;
	private Date toDate;
	
	public SearchDetail(){}

	public Date getFromDate()
	{
		fromDate = FormUtil.toDate(fromDate, FormUtil.notSetDateFields);
		return fromDate;
	}

	public void setFromDate(Date fromDate)
	{
		this.fromDate = fromDate;
	}

	public Date getToDate()
	{
		toDate = FormUtil.toDate(toDate, FormUtil.notSetDateFields);
//		toDate = FormUtil.toDate(
//				toDate, new Dictionary[]{
//						new Dictionary(Calendar.HOUR_OF_DAY, 23),
//						new Dictionary(Calendar.MINUTE, 59),
//						new Dictionary(Calendar.SECOND, 59) });
		return toDate;
	}

	public void setToDate(Date toDate)
	{
		this.toDate = toDate;
	}
}
