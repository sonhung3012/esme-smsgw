package com.fis.esme.form;

import java.util.ArrayList;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.CustomRegexpValidator;
import com.fis.esme.classes.FieldsValidator;
import com.fis.esme.classes.FieldsValidatorInterface;
import com.fis.esme.classes.NameExisted;
import com.fis.esme.classes.NameExistedValidator;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.PropertyExistedValidator;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.cp.CpTransferer;
import com.fis.esme.persistence.EsmeCp;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class FormCpFieldFactory extends DefaultFieldFactory implements PropertyExisted, NameExisted, FieldsValidatorInterface {

	private final TextField txtCode = new TextField(TM.get("cp.field_code.caption"));
	private final TextField txtUserName = new TextField(TM.get("cp.field_username.caption"));
	private final TextField txtPass = new TextField(TM.get("cp.field_pass.caption"));
	private final ComboBox cbbProtocal = new ComboBox(TM.get("cp.field_protocal.caption"));
	private final TextField txtshortcode = new TextField(TM.get("cp.field_shortcode.caption"));
	private final TextField txtReceivePassword = new TextField(TM.get("cp.field_ReceivePassword.caption"));
	private final TextField txtReceiveUsername = new TextField(TM.get("cp.field_ReceiveUsername.caption"));
	private final TextField txtReceiveUrlMsg = new TextField(TM.get("cp.field_ReceiveUrlMsg.caption"));
	private final TextArea txtDescription = new TextArea(TM.get("cp.field_description.caption"));
	private final ComboBox cbbStatus = new ComboBox(TM.get("cp.field_status.caption"));

	private String strActive = "1";
	private String strInactive = "0";
	private String oldServiceCode = "";
	private String oldShortCode = "";
	private String oldUsername = "";
	private String oldPassWord = "";

	public String getOldPassWord() {

		return oldPassWord;
	}

	public void setOldPassWord(String oldPassWord) {

		this.oldPassWord = oldPassWord;
	}

	private Byte strSmtp = 1;
	private Byte strHttp = 2;
	private Byte strWeb = 3;
	private Byte strInternal = 4;

	private CpTransferer serviceCp = null;
	private NameExistedValidator codeExistedValidator;

	public FormCpFieldFactory() {

		initService();
		initComboBox();
		initTextField();
		initTextArea();
	}

	private void initService() {

		try {
			serviceCp = CacheServiceClient.serviceCp;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initComboBox() {

		cbbProtocal.removeAllItems();
		cbbProtocal.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbProtocal.setImmediate(true);
		cbbProtocal.addItem(strSmtp);
		cbbProtocal.addItem(strHttp);
		cbbProtocal.addItem(strWeb);
		cbbProtocal.addItem(strInternal);
		cbbProtocal.setItemCaption(strSmtp, TM.get("cp.field.combobox.protocal.smtp"));
		cbbProtocal.setItemCaption(strHttp, TM.get("cp.field.combobox.protocal.http"));
		cbbProtocal.setItemCaption(strWeb, TM.get("cp.field.combobox.protocal.web"));
		cbbProtocal.setItemCaption(strInternal, TM.get("cp.field.combobox.protocal.internal"));
		cbbProtocal.setNullSelectionAllowed(false);
		cbbProtocal.setRequired(true);
		cbbProtocal.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbProtocal.getCaption()));
		cbbProtocal.setValue(strSmtp);
		cbbProtocal.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbProtocal.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				if (cbbProtocal.getValue() != null) {

					if (cbbProtocal.getValue().toString().equalsIgnoreCase("2")) {
						txtReceiveUrlMsg.setRequired(true);
						txtReceiveUrlMsg.setRequiredError(TM.get("common.field.msg.validator_nulloremty", txtReceiveUrlMsg.getCaption()));
					} else {
						txtReceiveUrlMsg.setRequired(false);
					}

					if (cbbProtocal.getValue().toString().equalsIgnoreCase("1")) {
						txtUserName.setMaxLength(16);
						txtPass.setMaxLength(9);
					} else {
						txtUserName.setMaxLength(40);
						txtPass.setMaxLength(100);
					}
				}
			}
		});

		cbbStatus.removeAllItems();
		cbbStatus.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbStatus.addItem(strActive);
		cbbStatus.addItem(strInactive);
		cbbStatus.setItemCaption(strActive, TM.get("cp.field_combobox.value_active"));
		cbbStatus.setItemCaption(strInactive, TM.get("cp.field_combobox.value_inactive"));
		cbbStatus.setNullSelectionAllowed(false);
		cbbStatus.setRequired(true);
		cbbStatus.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbStatus.getCaption()));
		cbbStatus.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
	}

	private void initTextField() {

		txtCode.setMaxLength(50);
		txtCode.setWidth(TM.get("common.form.field.fixedwidth"));
		txtCode.setNullRepresentation("");
		String errorCodeMsg = TM.get("common.field_code.msg.validator_unicode", txtCode.getCaption());
		String nullCodeMsg = TM.get("common.field.msg.validator_nulloremty", txtCode.getCaption());
		txtCode.setRequired(true);
		txtCode.setRequiredError(nullCodeMsg);
		SpaceValidator emptyCode = new SpaceValidator(nullCodeMsg);
		txtCode.addValidator(emptyCode);
		txtCode.addValidator(new CustomRegexpValidator(TM.get("cp.field_code.regexp.validator_error", txtCode.getCaption()), true, errorCodeMsg, true));
		PropertyExistedValidator fieldExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtCode.getCaption()), this, "code");
		txtCode.addValidator(fieldExistedValicator);

		txtUserName.setMaxLength(16);
		txtUserName.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullNameMsg12 = TM.get("common.field.msg.validator_nulloremty", txtUserName.getCaption());
		txtUserName.setRequired(true);
		txtUserName.setRequiredError(nullNameMsg12);
		SpaceValidator empty12 = new SpaceValidator(nullNameMsg12);
		txtUserName.addValidator(empty12);
		txtUserName.setNullRepresentation("");
		FieldsValidator fieldValicator = new FieldsValidator(this, "username", new Field[] { txtUserName });
		txtUserName.addValidator(fieldValicator);

		txtshortcode.setMaxLength(50);
		txtshortcode.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullNameMsg9 = TM.get("common.field.msg.validator_nulloremty", txtshortcode.getCaption());
		txtshortcode.setRequired(true);
		txtshortcode.setRequiredError(nullNameMsg9);
		SpaceValidator empty = new SpaceValidator(nullNameMsg9);
		txtshortcode.addValidator(empty);
		// String errorPriorityMsg2 = TM.get("common.field.msg.validator_isint",
		// txtshortcode.getCaption());
		// txtshortcode.addValidator(new
		// CompareIntegerValidate(errorPriorityMsg2, 0, 1));
		txtshortcode.setNullRepresentation("");
		String errorCodeMsg1 = TM.get("common.field.msg.validator_isint", txtshortcode.getCaption());
		txtshortcode.addValidator(new CustomRegexpValidator(TM.get("cp.field_shortcode.byte.regexp.validator_error", txtshortcode.getCaption()), true, errorCodeMsg1, true));
		codeExistedValidator = new NameExistedValidator(TM.get("common.field.msg.validator_existed", TM.get("defaultshortcode.exit")), this);
		txtshortcode.addValidator(codeExistedValidator);

		txtReceivePassword.setMaxLength(100);
		txtReceivePassword.setSecret(true);
		String nullNameMsgrp = TM.get("common.field.msg.validator_nulloremty", txtReceivePassword.getCaption());
		SpaceValidator emptyrp = new SpaceValidator(nullNameMsgrp);
		txtReceivePassword.setWidth(TM.get("common.form.field.fixedwidth"));
		txtReceivePassword.addValidator(emptyrp);
		txtReceivePassword.setNullRepresentation("");

		txtReceiveUsername.setMaxLength(50);
		txtReceiveUsername.setWidth(TM.get("common.form.field.fixedwidth"));
		txtReceivePassword.setSecret(true);
		String nullNameMsgru = TM.get("common.field.msg.validator_nulloremty", txtReceivePassword.getCaption());
		SpaceValidator emptyru = new SpaceValidator(nullNameMsgru);
		txtReceiveUsername.addValidator(emptyru);
		txtReceiveUsername.setNullRepresentation("");

		txtReceiveUrlMsg.setMaxLength(200);
		txtReceiveUrlMsg.setWidth(TM.get("common.form.field.fixedwidth"));
		txtReceivePassword.setSecret(true);
		String nullNameMsg5 = TM.get("common.field.msg.validator_nulloremty", txtReceivePassword.getCaption());
		SpaceValidator empty5 = new SpaceValidator(nullNameMsg5);
		txtReceiveUrlMsg.addValidator(empty5);
		txtReceiveUrlMsg.setNullRepresentation("");

		txtPass.setMaxLength(9);
		txtPass.setSecret(true);
		txtPass.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullNameMsg13 = TM.get("common.field.msg.validator_nulloremty", txtPass.getCaption());
		txtPass.setRequired(true);
		txtPass.setRequiredError(nullNameMsg13);
		SpaceValidator empty13 = new SpaceValidator(nullNameMsg13);
		txtPass.addValidator(empty13);
		txtPass.setNullRepresentation("");

		// txtProtocal.setMaxLength(2);
		// txtProtocal.setWidth(TM.get("common.form.field.fixedwidth"));
		// txtProtocal.setNullRepresentation("");
		// String nullCodeMsgp = TM.get("common.field.msg.validator_nulloremty",
		// txtProtocal.getCaption());
		// txtProtocal.setRequired(true);
		// txtProtocal.setRequiredError(nullCodeMsgp);
		// SpaceValidator emptyCodep = new SpaceValidator(nullCodeMsgp);
		// txtProtocal.addValidator(emptyCodep);
		// String errorPriorityMsg1 = TM.get("common.field.msg.validator_isint",
		// txtProtocal.getCaption());
		// txtProtocal.addValidator(new
		// CompareIntegerValidate(errorPriorityMsg1, 0, 1));
	}

	private void initTextArea() {

		txtDescription.setWidth(TM.get("common.form.field.fixedwidth"));
		txtDescription.setHeight("50px");
		txtDescription.setMaxLength(150);
		txtDescription.setNullRepresentation("");
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		if (propertyId.equals("status")) {
			return cbbStatus;
		} else if (propertyId.equals("desciption")) {
			return txtDescription;
		} else if (propertyId.equals("defaultShortCode")) {
			return txtshortcode;
		} else if (propertyId.equals("receivePassword")) {
			return txtReceivePassword;
		} else if (propertyId.equals("receiveUrlMsg")) {
			return txtReceiveUrlMsg;
		} else if (propertyId.equals("receiveUsername")) {
			return txtReceiveUsername;
		} else if (propertyId.equals("password")) {
			return txtPass;
		} else if (propertyId.equals("protocal")) {
			return cbbProtocal;
		} else if (propertyId.equals("code")) {
			txtCode.selectAll();
			return txtCode;
		} else if (propertyId.equals("username")) {
			return txtUserName;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public void setOldCode(String code) {

		this.oldServiceCode = code;
	}

	public String getOldShortCode() {

		return oldShortCode;
	}

	public void setOldShortCode(String oldShortCode) {

		this.oldShortCode = oldShortCode;
	}

	public String getOldUsername() {

		return oldUsername;
	}

	public void setOldUsername(String oldUsername) {

		this.oldUsername = oldUsername;
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {

		String val = value.toString().trim().toUpperCase();
		if (property.equals("code")) {
			if (value.toString().equalsIgnoreCase(oldServiceCode)) {
				return true;
			} else {
				EsmeCp ser = new EsmeCp();
				ser.setCode(val);

				if (serviceCp.checkExisted(ser) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isNameExisted(String name) {

		String trim = name.trim().toUpperCase();
		if (trim.equalsIgnoreCase(oldShortCode) || "".equals(trim)) {
			return true;
		}

		else {
			try {
				EsmeCp cp = new EsmeCp();
				cp.setDefaultShortCode(trim);
				ArrayList<EsmeCp> cpArr = (ArrayList<EsmeCp>) serviceCp.findAllWithOrderPaging(cp, "", false, 0, 10, false);
				if (cpArr.size() >= 1) {
					return false;
				} else {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	@Override
	public Object isValid(String property, Object currentFieldValue, Object otherObject) {

		String trim = currentFieldValue.toString().trim().toUpperCase();
		if (oldUsername != null && (trim.equalsIgnoreCase(oldUsername.toUpperCase()) || "".equals(trim))) {
			return null;
		} else {
			try {
				EsmeCp esmecp = new EsmeCp();
				esmecp.setUsername(currentFieldValue.toString().trim());
				ArrayList<EsmeCp> cpArr = (ArrayList<EsmeCp>) serviceCp.findAllWithOrderPaging(esmecp, "", false, 0, 10, true);
				if (cpArr.size() >= 1) {
					return "<span style='color:red'><b>Username</b> already exists</span>";
				} else {
					return null;
				}

			} catch (Exception e) {
				return null;
			}
		}
	}
}