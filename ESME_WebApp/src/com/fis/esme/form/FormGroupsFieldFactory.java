package com.fis.esme.form;

import java.util.ArrayList;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.PropertyExistedValidator;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.groupsdt.GroupsDTTransferer;
import com.fis.esme.persistence.Groups;
import com.fis.esme.util.CacheDB;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class FormGroupsFieldFactory extends DefaultFieldFactory implements PropertyExisted {

	private final TextField txtCode = new TextField(TM.get("groups.field_code.caption"));
	private final TextField txtName = new TextField(TM.get("groups.field_name.caption"));
	private final ComboBox cbbStatus = new ComboBox(TM.get("groups.field_status.caption"));
	private final ComboBox cbbRoot = new ComboBox(TM.get("groups.field_root.caption"));
	private final ComboBox cbbParent = new ComboBox(TM.get("groups.field_parent.caption"));

	private String strActive = "1";
	private String strInactive = "0";
	private String oldCode = "";
	private String oldName = "";

	private GroupsDTTransferer serviceService = null;
	private ArrayList<Groups> arrGroups = new ArrayList<Groups>();

	public FormGroupsFieldFactory() {

		initService();
		initComboBox();
		initTextField();
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
			arrGroups.clear();
			arrGroups.addAll(CacheDB.cacheGroupsDT);
		} catch (Exception e) {

			e.printStackTrace();
		}

		cbbRoot.setImmediate(true);
		cbbRoot.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbRoot.removeAllItems();
		for (Groups group : arrGroups) {
			cbbRoot.addItem(group.getGroupId());
			cbbRoot.setItemCaption(group.getGroupId(), group.getDesciption());
		}
		cbbRoot.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cbbParent.setImmediate(true);
		cbbParent.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbParent.removeAllValidators();
		cbbParent.removeAllItems();
		for (Groups group : arrGroups) {
			cbbParent.addItem(group.getGroupId());
			cbbParent.setItemCaption(group.getGroupId(), group.getDesciption());
		}
		cbbParent.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbParent.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value instanceof Long) {
					Long id = (Long) value;
					for (Groups group : arrGroups) {

						if (group.getGroupId() == id) {

							if (group.getStatus().equals("0")) {

								throw new InvalidValueException(TM.get("groups.message.parent.inactive.error"));
							} else if (group.getName().equals(oldName)) {

								throw new InvalidValueException(TM.get("groups.message.parent.invalid.error"));
							}
						}
					}
				}

			}

			@Override
			public boolean isValid(Object value) {

				if (value instanceof Long) {
					Long id = (Long) value;
					for (Groups group : arrGroups) {

						if (group.getGroupId() == id) {

							if (group.getStatus().equals("0")) {

								return false;
							} else if (group.getName().equals(oldName)) {

								return false;
							}
						}
					}
				}

				return true;
			}
		});

		cbbStatus.removeAllItems();
		cbbStatus.removeAllValidators();
		cbbStatus.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbStatus.addItem(strActive);
		cbbStatus.addItem(strInactive);
		cbbStatus.setItemCaption(strActive, TM.get("service.field_combobox.value_active"));
		cbbStatus.setItemCaption(strInactive, TM.get("service.field_combobox.value_inactive"));
		cbbStatus.setNullSelectionAllowed(false);
		cbbStatus.setRequired(true);
		cbbStatus.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbStatus.getCaption()));
		cbbStatus.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbStatus.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value instanceof String) {

					String status = (String) value;
					if (status.equals("0")) {

						for (Groups parent : arrGroups) {

							if (parent.getName().equalsIgnoreCase(oldName)) {

								for (Groups child : arrGroups) {

									if (child.getParentId() == parent.getGroupId() && child.getStatus().equals("1")) {

										throw new InvalidValueException(TM.get("groups.message.child.active.error"));
									}

								}
								break;
							}
						}
					}
				}
			}

			@Override
			public boolean isValid(Object value) {

				if (value instanceof String) {

					String status = (String) value;
					if (status.equals("0")) {

						for (Groups parent : arrGroups) {

							if (parent.getName().equalsIgnoreCase(oldName)) {

								for (Groups child : arrGroups) {

									if (child.getParentId() == parent.getGroupId() && child.getStatus().equals("1")) {

										return false;
									}

								}
								return true;
							}
						}
					}
				}

				return true;
			}
		});

	}

	private void initTextField() {

		txtCode.setMaxLength(50);
		txtCode.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullCodeMsg = TM.get("common.field.msg.validator_nulloremty", txtCode.getCaption());
		txtCode.setRequired(true);
		txtCode.setRequiredError(nullCodeMsg);
		SpaceValidator emptyCode = new SpaceValidator(nullCodeMsg);
		txtCode.addValidator(emptyCode);
		// txtName.addValidator(new CustomRegexpValidator(TM.get(
		// "service.field_code.regexp.validator_error",
		// txtName.getCaption()), true, errorCodeMsg, true));
		PropertyExistedValidator fieldCodeExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtCode.getCaption()), this, "name");
		txtCode.addValidator(fieldCodeExistedValicator);

		txtName.setWidth(TM.get("common.form.field.fixedwidth"));
		txtName.setMaxLength(50);
		String nullNameMsg = TM.get("common.field.msg.validator_nulloremty", txtName.getCaption());
		txtName.setRequired(true);
		txtName.setRequiredError(nullNameMsg);
		SpaceValidator emptyName = new SpaceValidator(nullNameMsg);
		txtName.addValidator(emptyName);
		PropertyExistedValidator fieldNameExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtName.getCaption()), this, "desciption");
		txtName.addValidator(fieldNameExistedValicator);

	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		if (propertyId.equals("status")) {
			return cbbStatus;
		} else if (propertyId.equals("desciption")) {
			return txtName;
		} else if (propertyId.equals("name")) {
			return txtCode;
		} else if (propertyId.equals("rootId")) {
			return cbbRoot;
		} else if (propertyId.equals("parentId")) {
			return cbbParent;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public void setOldCode(String oldCode) {

		this.oldCode = oldCode;
	}

	public void setOldName(String oldName) {

		this.oldName = oldName;
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {

		String val = value.toString().trim().toUpperCase();
		if (property.equals("name")) {
			if (value.toString().equalsIgnoreCase(oldCode)) {
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
		} else if (property.equals("desciption")) {
			if (value.toString().equalsIgnoreCase(oldName)) {
				return true;
			} else {
				// return !(serviceService.checkExistedByField(property,
				// val, true) > 0);
				Groups ser = new Groups();
				ser.setDesciption(val);

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