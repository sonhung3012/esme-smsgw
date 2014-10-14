package com.fis.esme.form;

import java.util.ArrayList;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.PropertyExistedValidator;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.groupsdt.GroupsDTTransferer;
import com.fis.esme.persistence.Groups;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class FormGroupsFieldFactory extends DefaultFieldFactory implements
		PropertyExisted {
	private final TextField txtName = new TextField(
			TM.get("groups.field_name.caption"));
	private final TextArea txtDescription = new TextArea(
			TM.get("service.field_description.caption"));
	private final ComboBox cbbStatus = new ComboBox(
			TM.get("service.field_status.caption"));
	private final ComboBox cbbRoot = new ComboBox(
			TM.get("service.field_root.caption"));
	private final ComboBox cbbParent = new ComboBox(
			TM.get("service.field_parent.caption"));

	private String strActive = "1";
	private String strInactive = "0";
	private String oldName = "";

	private GroupsDTTransferer serviceService = null;
	private ArrayList<Groups> arrService = new ArrayList<Groups>();

	public FormGroupsFieldFactory() {
		initService();
		initComboBox();
		initTextField();
		initTextArea();
	}

	private void initService() {
		try {
			serviceService = CacheServiceClient.serviceGroups;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initComboBox() {

		try {
			arrService.clear();
			arrService.addAll(serviceService
					.findAllWithoutParameter());
		} catch (Exception e) {

			e.printStackTrace();
		}

		cbbRoot.setImmediate(true);
		cbbRoot.setWidth(TM.get("common.form.field.fixedwidth"));
		for (Groups service : arrService) {
			cbbRoot.addItem(service.getGroupId());
			cbbRoot.setItemCaption(service.getGroupId(), service.getName());
		}
		cbbRoot.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cbbParent.setImmediate(true);
		cbbParent.setWidth(TM.get("common.form.field.fixedwidth"));
		for (Groups service : arrService) {
			cbbParent.addItem(service.getGroupId());
			cbbParent.setItemCaption(service.getGroupId(), service.getName());
		}
		cbbParent.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cbbStatus.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbStatus.addItem(strActive);
		cbbStatus.addItem(strInactive);
		cbbStatus.setItemCaption(strActive,
				TM.get("service.field_combobox.value_active"));
		cbbStatus.setItemCaption(strInactive,
				TM.get("service.field_combobox.value_inactive"));
		cbbStatus.setNullSelectionAllowed(false);
		cbbStatus.setRequired(true);
		cbbStatus
				.setRequiredError(TM.get(
						"common.field.msg.validator_nulloremty",
						cbbStatus.getCaption()));
		cbbStatus.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
	}

	private void initTextField() {

		txtName.setMaxLength(50);
		txtName.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullNameMsg = TM.get("common.field.msg.validator_nulloremty",
				txtName.getCaption());
		txtName.setRequired(true);
		txtName.setRequiredError(nullNameMsg);
		SpaceValidator empty = new SpaceValidator(nullNameMsg);
		txtName.addValidator(empty);
		// txtName.addValidator(new CustomRegexpValidator(TM.get(
		// "service.field_code.regexp.validator_error",
		// txtName.getCaption()), true, errorCodeMsg, true));
		PropertyExistedValidator fieldExistedValicator = new PropertyExistedValidator(
				TM.get("common.field.msg.validator_existed",
						txtName.getCaption()), this, "name");
		txtName.addValidator(fieldExistedValicator);
	}

	private void initTextArea() {
		txtDescription.setWidth(TM.get("common.form.field.fixedwidth"));
		txtDescription.setHeight("50px");
		txtDescription.setMaxLength(100);
		txtDescription.setNullRepresentation("");
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		if (propertyId.equals("status")) {
			return cbbStatus;
		} else if (propertyId.equals("desciption")) {
			return txtDescription;
		} else if (propertyId.equals("name")) {
			return txtName;
			// } else if (propertyId.equals("rootId")) {
			// return cbbRoot;
		} else if (propertyId.equals("parentId")) {
			return cbbParent;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {
		String val = value.toString().trim().toUpperCase();
		if (property.equals("name")) {
			if (value.toString().equalsIgnoreCase(oldName)) {
				return true;
			} else {
				// return !(serviceService.checkExistedByField(property,
				// val, true) > 0);
				Groups ser = new Groups();
				ser.setName(val);

				if (serviceService.checkExisted(ser) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		}
		return true;
	}
}