package com.fis.esme.form;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.CompareIntegerValidate;
import com.fis.esme.classes.CustomRegexpValidator;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.PropertyExistedValidator;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.shortcode.ShortCodeTransferer;
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
public class FormShortCodeFieldFactory extends DefaultFieldFactory implements
		PropertyExisted {
	private final TextField txtCode = new TextField(
			TM.get("Shortcode.field_code.caption"));
	private final TextField txtPrice = new TextField(
			TM.get("Shortcode.field_price.caption"));
	private final TextField txtMtFreeNumber = new TextField(
			TM.get("Shortcode.field_mtfreenumber.caption"));
	private final TextArea txtDescription = new TextArea(
			TM.get("Shortcode.field_description.caption"));
	private final ComboBox cbbStatus = new ComboBox(
			TM.get("Shortcode.field_status.caption"));

	private String strActive = "1";
	private String strInactive = "0";
	private String oldServiceCode = "";
	
	 private CustomRegexpValidator validatechater ;
	 private CustomRegexpValidator validateNumber ;

	private ShortCodeTransferer serviceShortCode = null;

	public FormShortCodeFieldFactory() {
		initService() ;
		initComboBox();
		initTextField();
		initTextArea();
	}

	private void initService() {
		try {
			serviceShortCode = CacheServiceClient.serviceShortCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initComboBox() {
		cbbStatus.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbStatus.addItem(strActive);
		cbbStatus.addItem(strInactive);
		cbbStatus.setItemCaption(strActive,
				TM.get("Shortcode.field_combobox.value_active"));
		cbbStatus.setItemCaption(strInactive,
				TM.get("Shortcode.field_combobox.value_inactive"));
		cbbStatus.setNullSelectionAllowed(false);
		cbbStatus.setRequired(true);
		cbbStatus
				.setRequiredError(TM.get(
						"common.field.msg.validator_nulloremty",
						cbbStatus.getCaption()));
		cbbStatus.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
	}

	private void initTextField() {
		txtCode.setMaxLength(50);
		txtCode.setImmediate(true);
		txtCode.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullCodeMsg = TM.get("common.field.msg.validator_nulloremty",
				txtCode.getCaption());
		txtCode.setRequired(true);
		txtCode.setRequiredError(nullCodeMsg);
		SpaceValidator emptyCode = new SpaceValidator(nullCodeMsg);
		txtCode.addValidator(emptyCode);
		PropertyExistedValidator fieldExistedValicator = new PropertyExistedValidator(
				TM.get("common.field.msg.validator_existed",
						txtCode.getCaption()), this, "code");
		txtCode.addValidator(fieldExistedValicator);
		String errorCodeMsg = TM.get("common.field.msg.validator_only*",
				txtCode.getCaption());
		validatechater = new CustomRegexpValidator(TM.get(
				"Shortcode.field_code.regexp.validator_error",
				txtCode.getCaption()), true, errorCodeMsg, true);
		
		 validateNumber = new CustomRegexpValidator(TM.get(
				"Shortcode.field_code.regexp.validator_errornumber",
				txtCode.getCaption()), true, errorCodeMsg, true);
		 txtCode.removeValidator(validatechater);
		 txtCode.removeValidator(validateNumber);
		txtCode.addListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (txtCode.getValue().toString().contains("*")) {
					
					txtCode.removeValidator(validatechater);
					txtCode.removeValidator(validateNumber);
					txtCode.addValidator(validatechater);
				}else{
					txtCode.removeValidator(validatechater);
					txtCode.removeValidator(validateNumber);
					txtCode.addValidator(validateNumber);
				}
				
			}
		});

		txtPrice.setMaxLength(9);
		txtPrice.setWidth(TM.get("common.form.field.fixedwidth"));
		txtPrice.setNullRepresentation("");
//		String nullCodeMsg1 = TM.get("common.field.msg.validator_nulloremty",
//				txtPrice.getCaption());
//		SpaceValidator emptyCode1 = new SpaceValidator(nullCodeMsg1);
//		txtPrice.addValidator(emptyCode1);
		String errorPriorityMsg1 = TM.get("common.field.msg.validator_isint",
				txtPrice.getCaption());
//		txtPrice.addValidator(new CustomRegexpValidator(TM.get(
//				"Shortcode1.field_code.regexp.validator_error",
//				txtPrice.getCaption()), true, errorPriorityMsg1, true));
		txtPrice.addValidator(new CompareIntegerValidate(errorPriorityMsg1, 0, 1));
		
		txtMtFreeNumber.setMaxLength(2);
		txtMtFreeNumber.setWidth(TM.get("common.form.field.fixedwidth"));
		txtMtFreeNumber.setNullRepresentation("");
//		String nullCodeMsg2 = TM.get("common.field.msg.validator_nulloremty",
//				txtMtFreeNumber.getCaption());
//		SpaceValidator emptyCode2 = new SpaceValidator(nullCodeMsg2);
//		txtMtFreeNumber.addValidator(emptyCode2);
		String errorPriorityMsg2 = TM.get("common.field.msg.validator_isint",
				txtMtFreeNumber.getCaption());
		txtMtFreeNumber.addValidator(new CompareIntegerValidate(errorPriorityMsg2, 0, 1));
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
		} else if (propertyId.equals("code")) {
			txtCode.selectAll();
			return txtCode;
		} else if (propertyId.equals("price")) {
			return txtPrice;
		} else if (propertyId.equals("mtFreeNumber")) {
			return txtMtFreeNumber;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public void setOldCode(String code) {
		this.oldServiceCode = code;
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {
		String val = value.toString().trim().toUpperCase();
		if (property.equals("code")) {
			if (value.toString().equalsIgnoreCase(oldServiceCode)) {
				return true;
			} else {
				EsmeShortCode ser = new EsmeShortCode();
				ser.setCode(val);
				
				if (serviceShortCode.checkExisted(ser) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		}
		return true;
	}
}