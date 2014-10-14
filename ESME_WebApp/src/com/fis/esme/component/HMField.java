
package com.fis.esme.component;

import org.vaadin.addon.customfield.CustomField;

import com.vaadin.data.Property;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;

public class HMField extends CustomField {

	private final String width = "50px";
	private ComboBox hh;
	private ComboBox mm;
	private HorizontalLayout hLayout;

	private boolean hhModified = false;
	private boolean mmModified = false;

	public HMField(String caption) {
		this.setCaption(caption);
		init();
		hLayout.addComponent(hh);
		hLayout.addComponent(mm);
		setCompositionRoot(hLayout);
	}

	@Override
	public boolean isModified() {
		return (hhModified || mmModified);
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
		hh.setNullSelectionAllowed(true);
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
		mm.setNullSelectionAllowed(true);
		mm.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		mm.addListener(new Property.ValueChangeListener() {
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				mmModified = !event.getProperty().getValue()
						.equals(mm.getData());
			}
		});

	}

	@Override
	public void setImmediate(boolean immediate) {
		hh.setImmediate(immediate);
		mm.setImmediate(immediate);
		super.setImmediate(immediate);
	}

	@Override
	public String getValue() {
		int h = (hh.getValue() != null) ? (Integer) hh.getValue() : 0;
		int m = (mm.getValue() != null) ? (Integer) mm.getValue() : 0;

		return intToString(h) + ":" + intToString(m);
	}

	public long getTimeInMillis() {
		int h = (hh.getValue() != null) ? (Integer) hh.getValue() : 0;
		int m = (mm.getValue() != null) ? (Integer) mm.getValue() : 0;
		long mH = h * (60 * (60 * 1000));
		long mM = m * (60 * 1000);
		return (mH + mM);
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
		super.setInternalValue(stringToHMValue(newValue));
	}

	protected void setInternalValue(Object newValue) {
		if (newValue == null) {
			super.setInternalValue(null);
			return;
		}
		super.setInternalValue(stringToHMValue(newValue));
	}

	public void doValueChange() {
		hh.focus();
	}
	
	public void fieldReadonly(boolean value) {
		
		mm.setReadOnly(value);
		hh.setReadOnly(value);
	}

	private String stringToHMValue(Object newValue) {

		if (newValue == null) {
			return null;
		}

		String[] arrStr = (String[]) newValue.toString().split(":");

		hh.setData(Integer.parseInt(arrStr[0].toString()));
		hh.select(Integer.parseInt(arrStr[0].toString()));

		mm.setData(Integer.parseInt(arrStr[1].toString()));
		mm.select(Integer.parseInt(arrStr[1].toString()));

		int h = (hh.getValue() != null) ? (Integer) hh.getValue() : 0;
		int m = (mm.getValue() != null) ? (Integer) mm.getValue() : 0;

		return intToString(h) + ":" + intToString(m);
	}

}
