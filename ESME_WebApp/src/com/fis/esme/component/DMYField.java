package com.fis.esme.component;

import org.vaadin.addon.customfield.CustomField;

import com.vaadin.data.Property;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;

public class DMYField extends CustomField
{
	
	private final String width = "50px";
	private ComboBox dd;
	private ComboBox mm;
	private ComboBox yy;
	private HorizontalLayout hLayout;

	private boolean ddModified = false;
	private boolean mmModified = false;
	private boolean yyModified = false;
	
	public DMYField(String caption)
	{
		this.setCaption(caption);
		init();
		hLayout.addComponent(dd);
		hLayout.addComponent(mm);
		hLayout.addComponent(yy);
		setCompositionRoot(hLayout);
	}
	
	@Override
	public boolean isModified()
	{
		return (ddModified || mmModified || yyModified);
	}
	
	public void init()
	{
		hLayout = new HorizontalLayout();
		hLayout.setSpacing(true);
		
		dd = new ComboBox();
		dd.setWidth(width);
		dd.setInputPrompt("HH");
		for (int i = 0; i <= 23; i++)
		{
			dd.addItem(i);
			dd.setItemCaption(i, "" + intToString(i) + "");
			
		}
		dd.setNullSelectionAllowed(false);
		dd.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		dd.addListener(new Property.ValueChangeListener()
		{
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event)
			{
				ddModified = !event.getProperty().getValue()
						.equals(dd.getData());
			}
		});
		
		mm = new ComboBox();
		mm.setWidth(width);
		mm.setInputPrompt("MM");
		for (int i = 0; i <= 59; i++)
		{
			mm.addItem(i);
			mm.setItemCaption(i, "" + intToString(i) + "");
		}
		mm.setNullSelectionAllowed(false);
		mm.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		mm.addListener(new Property.ValueChangeListener()
		{
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event)
			{
				mmModified = !event.getProperty().getValue()
						.equals(mm.getData());
			}
		});
		
		yy = new ComboBox();
		yy.setWidth(width);
		yy.setInputPrompt("SS");
		for (int i = 0; i <= 59; i++)
		{
			yy.addItem(i);
			yy.setItemCaption(i, "" + intToString(i) + "");
		}
		yy.setNullSelectionAllowed(false);
		yy.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		yy.addListener(new Property.ValueChangeListener()
		{
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event)
			{
				yyModified = !event.getProperty().getValue()
						.equals(yy.getData());
			}
		});
	}
	
	@Override
	public String getValue()
	{
		int h = (dd.getValue() != null) ? (Integer) dd.getValue() : 0;
		int m = (mm.getValue() != null) ? (Integer) mm.getValue() : 0;
		int s = (yy.getValue() != null) ? (Integer) yy.getValue() : 0;

		return intToString(h) + ":" + intToString(m) + ":" + intToString(s);
	}
	
	public long getTimeInMillis()
	{
		int h = (dd.getValue() != null) ? (Integer) dd.getValue() : 0;
		int m = (mm.getValue() != null) ? (Integer) mm.getValue() : 0;
		int s = (yy.getValue() != null) ? (Integer) yy.getValue() : 0;
		long mH = h * (60 * (60 * 1000));
		long mM = m * (60 * 1000);
		long mS = s * 1000;
		return (mH + mM + mS);
	}
	
	private String intToString(int num)
	{
		String str = "" + num;
		if (str.length() == 1)
			str = "0" + str;
		return str.trim();
	}
	
	@Override
	public Class<?> getType()
	{
		return dd.getType();
	}
	
	
	protected void setInternalValue(Object newValue)
	{
		if (newValue == null)
		{
			super.setInternalValue(null);
			return;
		}
		
		String[] arrStr = (String[]) newValue.toString().split(":");
		
		dd.setData(Integer.parseInt(arrStr[0].toString()));
		dd.select(Integer.parseInt(arrStr[0].toString()));
		
		mm.setData(Integer.parseInt(arrStr[1].toString()));
		mm.select(Integer.parseInt(arrStr[1].toString()));
		
		yy.setData(Integer.parseInt(arrStr[2].toString()));
		yy.select(Integer.parseInt(arrStr[2].toString()));

		int h = (dd.getValue() != null) ? (Integer) dd.getValue() : 0;
		int m = (mm.getValue() != null) ? (Integer) mm.getValue() : 0;
		int s = (yy.getValue() != null) ? (Integer) yy.getValue() : 0;
		
		super.setInternalValue(intToString(h) + ":" + intToString(m) + ":"
				+ intToString(s));
		
	}
	


}
