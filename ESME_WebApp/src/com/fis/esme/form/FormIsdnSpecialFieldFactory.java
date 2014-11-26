package com.fis.esme.form;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.CustomRegexpValidator;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.PropertyExistedValidator;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.persistence.EsmeIsdnSpecial;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class FormIsdnSpecialFieldFactory extends DefaultFieldFactory implements PropertyExisted {

	private final TextField txtMsisdn = new TextField(TM.get("special.field.msisdn.caption"));
	private final TextField txtName = new TextField(TM.get("special.field.name.caption"));
	private final ComboBox cbbType = new ComboBox(TM.get("special.field.type.caption"));
	private final ComboBox cbbReason = new ComboBox(TM.get("special.field.reason.caption"));
	private final ComboBox cbbStatus = new ComboBox(TM.get("special.field.status.caption"));

	private String strActive = "1";
	private String strInactive = "0";

	private String strType1 = "1";
	private String strType2 = "2";

	private String strReason1 = "1";
	private String strReason2 = "2";
	private String strReason3 = "3";
	private String strReason4 = "4";

	private String oldMsisdn = null;

	private String oldType = null;

	public FormIsdnSpecialFieldFactory() {

		initService();
		initComboBox();
		initTextField();
		initTextArea();
	}

	private void initService() {

	}

	private void initComboBox() {

		cbbStatus.removeAllItems();
		cbbStatus.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbStatus.addItem(strActive);
		cbbStatus.addItem(strInactive);
		cbbStatus.setItemCaption(strActive, TM.get("special.status.value." + strActive));
		cbbStatus.setItemCaption(strInactive, TM.get("special.status.value." + strInactive));
		cbbStatus.setNullSelectionAllowed(false);
		cbbStatus.setRequired(true);
		cbbStatus.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbStatus.getCaption()));
		cbbStatus.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cbbType.removeAllItems();
		cbbType.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbType.addItem(strType1);
		cbbType.addItem(strType2);
		cbbType.setItemCaption(strType1, TM.get("special.type.value." + strType1));
		cbbType.setItemCaption(strType2, TM.get("special.type.value." + strType2));
		cbbType.setNullSelectionAllowed(false);
		cbbType.setRequired(true);
		cbbType.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbType.getCaption()));
		cbbType.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cbbReason.removeAllItems();
		cbbReason.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbReason.addItem(strReason1);
		cbbReason.addItem(strReason2);
		cbbReason.addItem(strReason3);
		cbbReason.addItem(strReason4);
		cbbReason.setItemCaption(strReason1, TM.get("special.reason.value." + strReason1));
		cbbReason.setItemCaption(strReason2, TM.get("special.reason.value." + strReason2));
		cbbReason.setItemCaption(strReason3, TM.get("special.reason.value." + strReason3));
		cbbReason.setItemCaption(strReason4, TM.get("special.reason.value." + strReason4));
		cbbReason.setNullSelectionAllowed(false);
		cbbReason.setRequired(true);
		cbbReason.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbReason.getCaption()));
		cbbReason.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
	}

	private void initTextField() {

		txtMsisdn.removeAllValidators();
		txtMsisdn.setWidth(TM.get("common.form.field.fixedwidth"));
		txtMsisdn.setMaxLength(15);
		String nullNameMsg = TM.get("common.field.msg.validator_nulloremty", txtMsisdn.getCaption());
		txtMsisdn.setRequired(true);
		txtMsisdn.setRequiredError(nullNameMsg);
		txtMsisdn.addValidator(new SpaceValidator(nullNameMsg));
		// txtPhone.addValidator(new CompareNumberValidate(TM.get(
		// "common.field.msisdn.msg.validator_isint",
		// txtPhone.getCaption()), 0, 1));
		String errorCodeMsg = TM.get("common.field_msisdn.msg.validator", txtMsisdn.getCaption());
		txtMsisdn.addValidator(new CustomRegexpValidator(TM.get("form.upload.number.msisdn.1", txtMsisdn.getCaption()), true, errorCodeMsg, true));
		PropertyExistedValidator fieldExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtMsisdn.getCaption()), this, "msisdn");
		txtMsisdn.addValidator(fieldExistedValicator);
		txtMsisdn.setNullRepresentation("");

		txtName.setMaxLength(50);
		txtName.setWidth(TM.get("common.form.field.fixedwidth"));
		nullNameMsg = TM.get("common.field.msg.validator_nulloremty", txtName.getCaption());
		// txtName.setRequired(true);
		// txtName.setRequiredError(nullNameMsg);
		SpaceValidator empty = new SpaceValidator(nullNameMsg);
		txtName.addValidator(empty);
		txtName.setNullRepresentation("");
	}

	private void initTextArea() {

	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		if (propertyId.equals("msisdn")) {
			return txtMsisdn;
		} else if (propertyId.equals("name")) {
			return txtName;
		} else if (propertyId.equals("type")) {
			return cbbType;
		} else if (propertyId.equals("reason")) {
			return cbbReason;
		} else if (propertyId.equals("status")) {
			return cbbStatus;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public String getOldMsisdn() {

		return oldMsisdn;
	}

	public void setOldMsisdn(String oldMsisdn) {

		this.oldMsisdn = oldMsisdn;
	}

	public String getOldType() {

		return oldType;
	}

	public void setOldType(String oldType) {

		this.oldType = oldType;
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {

		String val = value.toString().trim().toUpperCase();
		if (property.equals("msisdn")) {
			if (value.toString().equalsIgnoreCase(oldMsisdn)) {
				return true;
			} else {
				EsmeIsdnSpecial obj = new EsmeIsdnSpecial();
				obj.setMsisdn(val);

				if (CacheServiceClient.serviceIsdnSpecial.checkExisted(null, obj) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}
}