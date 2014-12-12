package com.fis.esme.form;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.CustomRegexpValidator;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.PropertyExistedValidator;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.smscommand.SmsCommandTransferer;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class FormSmsCommandFieldFactory extends DefaultFieldFactory implements PropertyExisted {

	private final TextField txtCode = new TextField(TM.get("SmsCommand.field_code.caption"));
	private final TextField txtName = new TextField(TM.get("SmsCommand.field_name.caption"));
	private final ComboBox cbbStatus = new ComboBox(TM.get("SmsCommand.field_status.caption"));

	private String strActive = "1";
	private String strInactive = "0";
	private String oldServiceCode = "";
	private String oldServiceName = "";

	private SmsCommandTransferer serviceSmsCommand = null;

	public FormSmsCommandFieldFactory() {

		initService();
		initComboBox();
		initTextField();
	}

	private void initService() {

		try {
			serviceSmsCommand = CacheServiceClient.serviceSmsCommand;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initComboBox() {

		cbbStatus.removeAllItems();
		cbbStatus.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbStatus.addItem(strActive);
		cbbStatus.addItem(strInactive);
		cbbStatus.setItemCaption(strActive, TM.get("SmsCommand.field_combobox.value_active"));
		cbbStatus.setItemCaption(strInactive, TM.get("SmsCommand.field_combobox.value_inactive"));
		cbbStatus.setNullSelectionAllowed(false);
		cbbStatus.setRequired(true);
		cbbStatus.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbStatus.getCaption()));
		cbbStatus.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
	}

	private void initTextField() {

		txtCode.setMaxLength(50);
		txtCode.setWidth(TM.get("common.form.field.fixedwidth"));
		String errorCodeMsg = TM.get("common.field_code.msg.validator_unicode", txtCode.getCaption());
		String nullCodeMsg = TM.get("common.field.msg.validator_nulloremty", txtCode.getCaption());
		txtCode.setRequired(true);
		txtCode.setRequiredError(nullCodeMsg);
		SpaceValidator emptyCode = new SpaceValidator(nullCodeMsg);
		txtCode.addValidator(emptyCode);
		txtCode.addValidator(new CustomRegexpValidator(TM.get("SmsCommand.field_code.regexp.validator_error", txtCode.getCaption()), true, errorCodeMsg, true));
		PropertyExistedValidator fieldCodeExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtCode.getCaption()), this, "code");
		txtCode.addValidator(fieldCodeExistedValicator);

		txtName.setMaxLength(70);
		txtName.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullNameMsg = TM.get("common.field.msg.validator_nulloremty", txtName.getCaption());
		// txtName.setRequired(true);
		// txtName.setRequiredError(nullNameMsg);
		SpaceValidator empty = new SpaceValidator(nullNameMsg);
		txtName.addValidator(empty);
		PropertyExistedValidator fieldNameExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtName.getCaption()), this, "name");
		txtName.addValidator(fieldNameExistedValicator);

	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		if (propertyId.equals("status")) {
			return cbbStatus;
		} else if (propertyId.equals("code")) {
			txtCode.selectAll();
			return txtCode;
		} else if (propertyId.equals("name")) {
			return txtName;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public void setOldCode(String code) {

		this.oldServiceCode = code;
	}

	public void setOldName(String name) {

		this.oldServiceName = name;
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {

		String val = value.toString().trim().toUpperCase();
		if (property.equals("code")) {
			if (value.toString().equalsIgnoreCase(oldServiceCode)) {
				return true;
			} else {
				// return !(serviceService.checkExistedByField(property,
				// val, true) > 0);
				EsmeSmsCommand ser = new EsmeSmsCommand();
				ser.setCode(val);

				if (serviceSmsCommand.checkExisted(ser) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		} else if (property.equals("name")) {
			if (value.toString().equalsIgnoreCase(oldServiceName)) {
				return true;
			} else {
				// return !(serviceService.checkExistedByField(property,
				// val, true) > 0);
				EsmeSmsCommand ser = new EsmeSmsCommand();
				ser.setName(val);

				if (serviceSmsCommand.checkExisted(ser) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		}

		return true;
	}
}