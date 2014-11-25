package com.fis.esme.form;

import com.fis.esme.classes.CustomRegexpValidator;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.PropertyExistedValidator;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.client.IsdnPrefixTransfererClient;
import com.fis.esme.isdnprefix.IsdnPrefixTransferer;
import com.fis.esme.persistence.EsmeIsdnPrefix;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class FormIsdnPrefixFieldFactory extends DefaultFieldFactory implements PropertyExisted {

	private final TextField txtPrefixValue = new TextField(TM.get("isdnprefix.field_prefix_value.caption"));
	private final TextArea txtDescription = new TextArea(TM.get("service.field_description.caption"));
	private final ComboBox cbbStatus = new ComboBox(TM.get("isdnprefix.field_status.caption"));

	private String strActive = "1";
	private String strInactive = "0";
	private String oldPrefixValue = "";

	private IsdnPrefixTransferer serviceService = null;

	public FormIsdnPrefixFieldFactory() {

		initService();
		initComboBox();
		initTextField();
		initTextArea();
	}

	private void initService() {

		try {
			serviceService = IsdnPrefixTransfererClient.getService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initComboBox() {

		cbbStatus.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbStatus.addItem(strActive);
		cbbStatus.addItem(strInactive);
		cbbStatus.setItemCaption(strActive, TM.get("isdnprefix.field_combobox.value_active"));
		cbbStatus.setItemCaption(strInactive, TM.get("isdnprefix.field_combobox.value_inactive"));
		cbbStatus.setNullSelectionAllowed(false);
		cbbStatus.setRequired(true);
		cbbStatus.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbStatus.getCaption()));
		cbbStatus.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
	}

	private void initTextField() {

		txtPrefixValue.setMaxLength(8);
		txtPrefixValue.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullCodeMsg = TM.get("common.field.msg.validator_nulloremty", txtPrefixValue.getCaption());
		txtPrefixValue.setRequired(true);
		txtPrefixValue.setRequiredError(nullCodeMsg);
		SpaceValidator emptyCode = new SpaceValidator(nullCodeMsg);
		txtPrefixValue.addValidator(emptyCode);
		PropertyExistedValidator fieldExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtPrefixValue.getCaption()), this, "prefixValue");
		txtPrefixValue.addValidator(fieldExistedValicator);
		String sms = TM.get("isdnprefix.field_prefix_value.valid", txtPrefixValue.getCaption());
		txtPrefixValue.addValidator(new CustomRegexpValidator("[0-9\\?\\*]*", sms));

	}

	private void initTextArea() {

		txtDescription.setWidth(TM.get("common.form.field.fixedwidth"));
		txtDescription.setHeight("50px");
		txtDescription.setMaxLength(300);
		txtDescription.setNullRepresentation("");
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		if (propertyId.equals("status")) {
			return cbbStatus;
		} else if (propertyId.equals("prefixValue")) {
			txtPrefixValue.selectAll();
			return txtPrefixValue;
		} else if (propertyId.equals("description")) {
			return txtDescription;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public void setOldPrefixValue(String prefixValue) {

		this.oldPrefixValue = prefixValue;
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {

		String val = value.toString().trim();
		EsmeIsdnPrefix ser = new EsmeIsdnPrefix();
		if (val.equals(this.oldPrefixValue)) {
			return true;
		}
		if (property.equals("prefixValue")) {
			ser.setPrefixValue(val);
		} else if (property.equals("status")) {
			ser.setStatus(val);
		}

		if (serviceService.checkExisted(ser) >= 1) {
			return false;
		} else {
			return true;
		}
	}
}