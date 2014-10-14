package com.fis.esme.component;

import org.vaadin.addon.customfield.CustomField;

import com.vaadin.data.Property;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;

public class HMSField extends CustomField {

	private final String width = "50px";
	private ComboBox hh;
	private ComboBox mm;
	private ComboBox ss;
	private HorizontalLayout hLayout;

	private boolean hhModified = false;
	private boolean mmModified = false;
	private boolean ssModified = false;

	public HMSField(String caption) {
		this.setCaption(caption);
		init();
		hLayout.addComponent(hh);
		hLayout.addComponent(mm);
		hLayout.addComponent(ss);
		setCompositionRoot(hLayout);
	}

	@Override
	public boolean isModified() {
		return (hhModified || mmModified || ssModified);
	}

	@Override
	public void focus() {
		hh.focus();
		super.focus();
	}

	public void init() {
		hLayout = new HorizontalLayout();
		hLayout.setSpacing(true);

		hh = new ComboBox();
		hh.setWidth(width);
		hh.setInputPrompt("HH");
		for (int i = 0; i <= 23; i++) {
			hh.addItem(i);
			hh.setItemCaption(i, "" + intToString(i) + "");

		}
		hh.setNullSelectionAllowed(false);
		hh.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		hh.addListener(new Property.ValueChangeListener() {
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				hhModified = !event.getProperty().getValue()
						.equals(hh.getData());
			}
		});

		mm = new ComboBox();
		mm.setWidth(width);
		mm.setInputPrompt("MM");
		for (int i = 0; i <= 59; i++) {
			mm.addItem(i);
			mm.setItemCaption(i, "" + intToString(i) + "");
		}
		mm.setNullSelectionAllowed(false);
		mm.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		mm.addListener(new Property.ValueChangeListener() {
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				mmModified = !event.getProperty().getValue()
						.equals(mm.getData());
			}
		});

		ss = new ComboBox();
		ss.setWidth(width);
		ss.setInputPrompt("SS");
		for (int i = 0; i <= 59; i++) {
			ss.addItem(i);
			ss.setItemCaption(i, "" + intToString(i) + "");
		}
		ss.setNullSelectionAllowed(false);
		ss.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		ss.addListener(new Property.ValueChangeListener() {
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				ssModified = !event.getProperty().getValue()
						.equals(ss.getData());
			}
		});
	}

	@Override
	public void setImmediate(boolean immediate) {
		hh.setImmediate(immediate);
		mm.setImmediate(immediate);
		ss.setImmediate(immediate);
		super.setImmediate(immediate);
	}

	@Override
	public String getValue() {
		int h = (hh.getValue() != null) ? (Integer) hh.getValue() : 0;
		int m = (mm.getValue() != null) ? (Integer) mm.getValue() : 0;
		int s = (ss.getValue() != null) ? (Integer) ss.getValue() : 0;

		return intToString(h) + ":" + intToString(m) + ":" + intToString(s);
	}

	public long getTimeInMillis() {
		int h = (hh.getValue() != null) ? (Integer) hh.getValue() : 0;
		int m = (mm.getValue() != null) ? (Integer) mm.getValue() : 0;
		int s = (ss.getValue() != null) ? (Integer) ss.getValue() : 0;
		long mH = h * (60 * (60 * 1000));
		long mM = m * (60 * 1000);
		long mS = s * 1000;
		return (mH + mM + mS);
	}

	private String intToString(int num) {
		String str = "" + num;
		if (str.length() == 1)
			str = "0" + str;
		return str.trim();
	}

	@Override
	public Class<?> getType() {
		return hh.getType();
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException,
			ConversionException {
		if (newValue == null) {
			super.setInternalValue(null);
			return;
		}
		super.setInternalValue(stringToHMSValue(newValue));
	}

	protected void setInternalValue(Object newValue) {
		if (newValue == null) {
			super.setInternalValue(null);
			return;
		}
		super.setInternalValue(stringToHMSValue(newValue));
	}

	public void doValueChange() {
		hh.focus();
	}
	
	public void fieldReadonly(boolean value) {
		
		mm.setReadOnly(value);
		hh.setReadOnly(value);
		ss.setReadOnly(value);
	}

	private String stringToHMSValue(Object newValue) {

		if (newValue == null) {
			return null;
		}

		String[] arrStr = (String[]) newValue.toString().split(":");

		hh.setData(Integer.parseInt(arrStr[0].toString()));
		hh.select(Integer.parseInt(arrStr[0].toString()));

		mm.setData(Integer.parseInt(arrStr[1].toString()));
		mm.select(Integer.parseInt(arrStr[1].toString()));

		ss.setData(Integer.parseInt(arrStr[2].toString()));
		ss.select(Integer.parseInt(arrStr[2].toString()));

		int h = (hh.getValue() != null) ? (Integer) hh.getValue() : 0;
		int m = (mm.getValue() != null) ? (Integer) mm.getValue() : 0;
		int s = (ss.getValue() != null) ? (Integer) ss.getValue() : 0;

		return intToString(h) + ":" + intToString(m) + ":" + intToString(s);
	}

}
