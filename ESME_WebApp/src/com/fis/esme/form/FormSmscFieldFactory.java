package com.fis.esme.form;

import com.fis.esme.classes.CustomRegexpValidator;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.PropertyExistedValidator;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.client.SmscTransfererClient;
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.smsc.SmscTransferer;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class FormSmscFieldFactory extends DefaultFieldFactory implements PropertyExisted {

	private final TextField txtCode = new TextField(TM.get("smsc.field_code.caption"));
	private final TextField txtName = new TextField(TM.get("smsc.field_name.caption"));
	private final TextArea txtDescription = new TextArea(TM.get("smsc.field_description.caption"));
	private final ComboBox cbbStatus = new ComboBox(TM.get("smsc.field_status.caption"));
	private final ComboBox cbbStartupType = new ComboBox(TM.get("smsc.field_startuptype.caption"));
	private final TextField txtClassName = new TextField(TM.get("smsc.field_classname.caption"));
	private final TextField txtDefaultShortCode = new TextField(TM.get("smsc.field_defaultshortcode.caption"));

	private String strActive = "1";
	private String strInactive = "0";
	private String oldServiceCode = "";
	private String oldName = "";

	public String getOldName() {

		return oldName;
	}

	public void setOldName(String oldName) {

		this.oldName = oldName;
	}

	private SmscTransferer serviceService = null;

	public FormSmscFieldFactory() {

		initService();
		initComboBox();
		initTextField();
		initTextArea();
	}

	private void initService() {

		try {
			serviceService = SmscTransfererClient.getService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String startupType_val0 = "0";
	private String startupType_val1 = "1";
	private String startupType_val2 = "2";

	private void initComboBox() {

		cbbStatus.removeAllItems();
		cbbStatus.setWidth(TM.get("common.form.field.fixedwidth"));
		// cbbStatus.setInputPrompt(TM.get("cbb.inputprompt"));
		cbbStatus.addItem(strActive);
		cbbStatus.addItem(strInactive);
		cbbStatus.setItemCaption(strActive, TM.get("smsc.field_combobox.value_active"));
		cbbStatus.setItemCaption(strInactive, TM.get("smsc.field_combobox.value_inactive"));

		String nullCodeMsg = TM.get("frmCommon.fieldNotNull", TM.get("frmActionparam.action"));
		cbbStatus.setRequired(true);
		cbbStatus.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbStatus.getCaption()));
		cbbStatus.setNullSelectionAllowed(false);
		// cbbStatus.setNullSelectionAllowed(false);
		// cbbStatus.setRequired(true);
		// cbbStatus
		// .setRequiredError(TM.get(
		// "common.field.msg.validator_nulloremty",
		// cbbStatus.getCaption()));
		cbbStatus.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cbbStartupType.removeAllItems();
		cbbStartupType.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbStartupType.addItem(startupType_val0);
		cbbStartupType.addItem(startupType_val1);
		cbbStartupType.addItem(startupType_val2);
		cbbStartupType.setItemCaption(startupType_val0, TM.get("smsc.form.startuptype_0"));
		cbbStartupType.setItemCaption(startupType_val1, TM.get("smsc.form.startuptype_1"));
		cbbStartupType.setItemCaption(startupType_val2, TM.get("smsc.form.startuptype_2"));
		// cbbStartupType.addItem(strActive);
		// cbbStartupType.addItem(strInactive);
		// cbbStatus.setItemCaption(strActive,
		// TM.get("service.field_combobox.value_active"));
		// cbbStatus.setItemCaption(strInactive,
		// TM.get("service.field_combobox.value_inactive"));
		// cbbStartupType.setNullSelectionAllowed(false);
		// cbbStartupType.setRequired(true);
		// cbbStartupType
		// .setRequiredError(TM.get(
		// "common.field.msg.validator_nulloremty",
		// cbbStatus.getCaption()));
		// cbbStartupType.setInputPrompt(TM.get("cbb.inputprompt"));
		cbbStartupType.setRequired(true);
		cbbStartupType.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbStartupType.getCaption()));
		cbbStartupType.setNullSelectionAllowed(false);
		cbbStartupType.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

	}

	private void initTextField() {

		txtCode.setMaxLength(50);
		// txtCode.setInputPrompt(TM.get("smsc.code.default"));
		txtCode.setWidth(TM.get("common.form.field.fixedwidth"));
		// String errorCodeMsg =
		// TM.get("common.field_code.msg.validator_unicode",
		// txtCode.getCaption());
		String nullCodeMsg = TM.get("common.field.msg.validator_nulloremty", txtCode.getCaption());
		txtCode.setRequired(true);
		txtCode.setRequiredError(nullCodeMsg);
		SpaceValidator emptyCode = new SpaceValidator(nullCodeMsg);
		txtCode.addValidator(emptyCode);
		// txtCode.addValidator(new CustomRegexpValidator(TM.get(
		// "service.field_code.regexp.validator_error",
		// txtCode.getCaption()), true, errorCodeMsg, true));
		PropertyExistedValidator fieldExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtCode.getCaption()), this, "code");
		txtCode.addValidator(fieldExistedValicator);

		txtName.setMaxLength(100);
		// txtName.setInputPrompt(TM.get("smsc.name.default"));
		txtName.setWidth(TM.get("common.form.field.fixedwidth"));
		// String nullNameMsg = TM.get("common.field.msg.validator_nulloremty",
		// txtName.getCaption());
		txtName.setRequired(true);
		String nullNameMsg = TM.get("common.field.msg.validator_nulloremty", txtName.getCaption());
		txtName.setRequiredError(nullNameMsg);
		SpaceValidator emptyName = new SpaceValidator(nullNameMsg);
		txtName.addValidator(emptyName);
		fieldExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtName.getCaption()), this, "name");
		txtName.addValidator(fieldExistedValicator);

		txtDefaultShortCode.setMaxLength(20);
		// txtDefaultShortCode.setInputPrompt(TM
		// .get("smsc.default_shortcode.default"));
		txtDefaultShortCode.setWidth(TM.get("common.form.field.fixedwidth"));
		// String nullNameMsg = TM.get("common.field.msg.validator_nulloremty",
		// txtName.getCaption());
		// txtDefaultShortCode.setRequired(true);
		// txtDefaultShortCode.setRequiredError(nullNameMsg);
		// SpaceValidator empty = new SpaceValidator(nullNameMsg);
		// txtDefaultShortCode.addValidator(empty);

		txtDefaultShortCode.setRequired(true);
		String nullScMsg = TM.get("common.field.msg.validator_nulloremty", txtDefaultShortCode.getCaption());
		txtDefaultShortCode.setRequiredError(nullScMsg);
		SpaceValidator emptySc = new SpaceValidator(nullScMsg);
		txtDefaultShortCode.addValidator(emptySc);
		String sms = TM.get("smsc.field_short_code.valid", txtDefaultShortCode.getCaption());
		txtDefaultShortCode.addValidator(new CustomRegexpValidator("[0-9]*", sms));

		txtClassName.setMaxLength(100);
		// txtClassName.setInputPrompt(TM.get("smsc.class_name.default"));
		txtClassName.setWidth(TM.get("common.form.field.fixedwidth"));
		txtClassName.setRequired(true);
		String nullClnMsg = TM.get("common.field.msg.validator_nulloremty", txtClassName.getCaption());
		txtClassName.setRequiredError(nullClnMsg);
		SpaceValidator emptyCln = new SpaceValidator(nullClnMsg);
		txtClassName.addValidator(emptyCln);

		// String nullNameMsg = TM.get("common.field.msg.validator_nulloremty",
		// txtName.getCaption());
		// txtClassName.setRequired(true);
		// txtClassName.setRequiredError(nullNameMsg);
		// SpaceValidator empty = new SpaceValidator(nullNameMsg);
		// txtClassName.addValidator(empty);

		// private final TextField txtClassName = new TextField(
		// TM.get("service.field_code.caption"));
		// private final TextField txtDefaultShortCode = new TextField(
		// TM.get("service.field_code.caption"));
	}

	private void initTextArea() {

		txtDescription.setWidth(TM.get("common.form.field.fixedwidth"));
		txtDescription.setHeight("50px");
		txtDescription.setMaxLength(100);
		txtDescription.setNullRepresentation("");
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		// System.out.println("propertyId " + propertyId);
		if (propertyId.equals("status")) {
			return cbbStatus;
		} else if (propertyId.equals("desciption")) {
			return txtDescription;
		} else if (propertyId.equals("code")) {
			txtCode.selectAll();
			return txtCode;
		} else if (propertyId.equals("name")) {
			return txtName;
		} else if (propertyId.equals("defaulShortCode")) {
			return txtDefaultShortCode;
		} else if (propertyId.equals("className")) {
			return txtClassName;
		} else if (propertyId.equals("startupType")) {
			return cbbStartupType;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public void setOldCode(String code) {

		this.oldServiceCode = code;
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {

		String val = value.toString().trim();
		if (property.equals("code")) {
			if (value.toString().equalsIgnoreCase(oldServiceCode)) {
				return true;
			} else {
				// return !(serviceService.checkExistedByField(property,
				// val, true) > 0);
				EsmeSmsc ser = new EsmeSmsc();
				ser.setCode(val);

				if (serviceService.checkExisted(ser) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		}
		if (property.equals("name")) {
			if (value.toString().equalsIgnoreCase(oldName)) {
				return true;
			} else {
				// return !(serviceService.checkExistedByField(property,
				// val, true) > 0);
				EsmeSmsc ser = new EsmeSmsc();
				ser.setName(val);

				if (serviceService.checkExisted(ser) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}
}