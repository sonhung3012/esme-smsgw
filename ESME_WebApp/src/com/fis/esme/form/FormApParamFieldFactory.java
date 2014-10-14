package com.fis.esme.form;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.apparam.ApParamTransferer;
import com.fis.esme.classes.ObjectExistedByProperty;
import com.fis.esme.classes.ObjectExistedByPropertyValidator;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.persistence.ApParam;
import com.vaadin.data.Item;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class FormApParamFieldFactory extends DefaultFieldFactory implements
		PropertyExisted,ObjectExistedByProperty {
	private final TextField txtType = new TextField(
			TM.get("ApParam.field_type.caption"));
	private final TextField txtName = new TextField(
			TM.get("ApParam.field_name.caption"));
	private final TextField txtValue = new TextField(
			TM.get("ApParam.field_value.caption"));
	private final TextArea txtDescription = new TextArea(
			TM.get("ApParam.field_description.caption"));

	private String oldType = "";
	private String oldName = "";

	private ApParamTransferer serviceApParam = null;

	public FormApParamFieldFactory() {
		initService() ;
		initTextField();
		initTextArea();
	}

	private void initService() {
		try {
			serviceApParam = CacheServiceClient.serviceApParam;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initTextField() {
		txtType.setMaxLength(20);
		txtType.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullCodeMsg = TM.get("common.field.msg.validator_nulloremty",
				txtType.getCaption());
		txtType.setRequired(true);
		txtType.setRequiredError(nullCodeMsg);
		SpaceValidator emptyCode = new SpaceValidator(nullCodeMsg);
		txtType.addValidator(emptyCode);
		
		
		txtName.setMaxLength(40);
		txtName.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullNameMsg = TM.get("common.field.msg.validator_nulloremty",
				txtName.getCaption());
//		String errorCodeMsg = TM.get("common.field_code.msg.validator_unicode",
//				txtName.getCaption());
//		txtName.addValidator(new CustomRegexpValidator(TM.get(
//				"ApParam.field_code.regexp.validator_error",
//				txtName.getCaption()), true, errorCodeMsg, true));
		txtName.setRequired(true);
		txtName.setRequiredError(nullNameMsg);
		SpaceValidator empty = new SpaceValidator(nullNameMsg);
		txtName.addValidator(empty);
//		PropertyExistedValidator fieldExistedValicator = new PropertyExistedValidator(
//				TM.get("common.field.msg.validator_existed",
//						txtName.getCaption()), this, "parName");
//		txtName.addValidator(fieldExistedValicator);
		
		ObjectExistedByPropertyValidator objectExistedValicator = new ObjectExistedByPropertyValidator(
				TM.get("common.fields.msg.validator_existed11"),
				this, new AbstractField[]{ txtType,txtName});
		txtName.addValidator(objectExistedValicator);
		
		
		
		txtValue.setMaxLength(40);
		txtValue.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullNameMsgv = TM.get("common.field.msg.validator_nulloremty",
				txtValue.getCaption());
		txtValue.setRequired(true);
		txtValue.setRequiredError(nullNameMsgv);
		SpaceValidator emptyv = new SpaceValidator(nullNameMsgv);
		txtValue.addValidator(emptyv);
	}

	private void initTextArea() {
		txtDescription.setWidth(TM.get("common.form.field.fixedwidth"));
		txtDescription.setHeight("50px");
		txtDescription.setMaxLength(100);
		txtDescription.setNullRepresentation("");
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		if (propertyId.equals("parValue")) {
			return txtValue;
		} else if (propertyId.equals("description")) {
			return txtDescription;
		} else if (propertyId.equals("parType")) {
			txtType.selectAll();
			return txtType;
		} else if (propertyId.equals("parName")) {
			return txtName;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public String getOldType() {
		return oldType;
	}

	public void setOldType(String oldType) {
		this.oldType = oldType;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {
//		String val = value.toString().trim().toUpperCase();
//		if (property.equals("parName") || property.equals("parType")) {
//			if (value.toString().equalsIgnoreCase(oldType)) {
//				return true;
//			} else {
//				ApParam ser = new ApParam();
//				ser.setParName(val);
//				
//				if (serviceApParam.checkExisted(ser) >= 1) {
//					return false;
//				} else {
//					return true;
//				}
//			}
//		}
		return true;
	}

	@Override
	public boolean isObjectExisted(Object value, Field[] field) {
		
		String type = field[0].getValue().toString();
//		int type = Integer.parseInt(field[1].getValue().toString()); 
		String name = field[1].getValue().toString();
		if (type.equalsIgnoreCase(oldType) && name.equalsIgnoreCase(oldName)) {
			return true;
		} else {
			ApParam ser = new ApParam();
			ser.setParType(type);
			ser.setParName(name);
			int i = serviceApParam.checkExisted(ser);
			return !(i > 0);
		}
	}
}