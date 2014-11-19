package com.fis.esme.form;

import java.util.ArrayList;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.PropertyExistedValidator;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.service.ServiceTransferer;
import com.fis.esme.util.CacheDB;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class FormServiceFieldFactory extends DefaultFieldFactory implements PropertyExisted {

	private final TextField txtCode = new TextField(TM.get("service.field_code.caption"));
	private final TextField txtName = new TextField(TM.get("service.field_name.caption"));
	private final TextArea txtDescription = new TextArea(TM.get("service.field_description.caption"));
	private final ComboBox cbbStatus = new ComboBox(TM.get("service.field_status.caption"));
	private final ComboBox cbbRoot = new ComboBox(TM.get("service.field_root.caption"));
	private final ComboBox cbbParent = new ComboBox(TM.get("service.field_parent.caption"));

	private String strActive = "1";
	private String strInactive = "0";
	private String oldServiceCode = "";

	private ServiceTransferer serviceService = null;
	private ArrayList<EsmeServices> arrService = new ArrayList<EsmeServices>();

	public FormServiceFieldFactory() {

		initService();
		initComboBox();
		initTextField();
		initTextArea();
	}

	private void initService() {

		try {
			serviceService = CacheServiceClient.serviceService;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initComboBox() {

		long time1 = System.currentTimeMillis();
		try {
			arrService.clear();
			arrService.addAll(CacheDB.cacheService);
		} catch (Exception e) {

			e.printStackTrace();
		}

		// cbbRoot.setImmediate(true);
		cbbRoot.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbRoot.removeAllItems();
		for (EsmeServices service : arrService) {

			cbbRoot.addItem(service.getServiceId());
			cbbRoot.setItemCaption(service.getServiceId(), service.getName());
		}
		cbbRoot.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		// cbbParent.setImmediate(true);
		cbbParent.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbParent.removeAllItems();
		cbbParent.removeAllValidators();
		for (EsmeServices service : arrService) {

			cbbParent.addItem(service.getServiceId());
			cbbParent.setItemCaption(service.getServiceId(), service.getName());
		}
		cbbParent.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbParent.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value instanceof Long) {
					Long id = (Long) value;
					for (EsmeServices esmeServices : arrService) {

						if (esmeServices.getServiceId() == id) {

							if (esmeServices.getStatus().equals("0")) {

								throw new InvalidValueException(TM.get("service1.message.parent.inactive.error"));
							} else if (esmeServices.getName().equals(oldServiceCode)) {

								throw new InvalidValueException(TM.get("service1.message.parent.invalid.error"));
							}
						}
					}
				}

			}

			@Override
			public boolean isValid(Object value) {

				if (value instanceof Long) {
					Long id = (Long) value;
					for (EsmeServices esmeServices : arrService) {

						if (esmeServices.getServiceId() == id) {

							if (esmeServices.getStatus().equals("0")) {

								return false;
							} else if (esmeServices.getName().equals(oldServiceCode)) {

								return false;
							}
						}
					}
				}

				return true;
			}
		});

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

						for (EsmeServices parent : arrService) {

							if (parent.getName().equalsIgnoreCase(oldServiceCode)) {

								for (EsmeServices child : arrService) {

									if (child.getParentId() == parent.getServiceId() && child.getStatus().equals("1")) {

										throw new InvalidValueException(TM.get("service1.message.child.active.error"));
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

						for (EsmeServices parent : arrService) {

							if (parent.getName().equalsIgnoreCase(oldServiceCode)) {

								for (EsmeServices child : arrService) {

									if (child.getParentId() == parent.getServiceId() && child.getStatus().equals("1")) {

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

		long time2 = System.currentTimeMillis();
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(time1 - time2);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	private void initTextField() {

		txtCode.setMaxLength(8);
		txtCode.setWidth(TM.get("common.form.field.fixedwidth"));
		String errorCodeMsg = TM.get("common.field_code.msg.validator_unicode", txtCode.getCaption());
		String nullCodeMsg = TM.get("common.field.msg.validator_nulloremty", txtCode.getCaption());
		txtCode.setRequired(true);
		txtCode.setRequiredError(nullCodeMsg);
		SpaceValidator emptyCode = new SpaceValidator(nullCodeMsg);
		txtCode.addValidator(emptyCode);
		// txtCode.addValidator(new CustomRegexpValidator(TM.get(
		// "service.field_code.regexp.validator_error",
		// txtCode.getCaption()), true, errorCodeMsg, true));
		// PropertyExistedValidator fieldExistedValicator = new
		// PropertyExistedValidator(
		// TM.get("common.field.msg.validator_existed",
		// txtCode.getCaption()), this, "code");
		// txtCode.addValidator(fieldExistedValicator);

		txtName.setMaxLength(50);
		txtName.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullNameMsg = TM.get("common.field.msg.validator_nulloremty", txtName.getCaption());
		txtName.setRequired(true);
		txtName.setRequiredError(nullNameMsg);
		SpaceValidator empty = new SpaceValidator(nullNameMsg);
		txtName.addValidator(empty);
		// txtName.addValidator(new CustomRegexpValidator(TM.get(
		// "service.field_code.regexp.validator_error",
		// txtName.getCaption()), true, errorCodeMsg, true));
		PropertyExistedValidator fieldExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtName.getCaption()), this, "name");
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
		} else if (propertyId.equals("code")) {
			txtCode.selectAll();
			return txtCode;
		} else if (propertyId.equals("name")) {
			return txtName;
		} else if (propertyId.equals("rootId")) {
			return cbbRoot;
		} else if (propertyId.equals("parentId")) {
			return cbbParent;
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
		if (property.equals("name")) {
			if (value.toString().equalsIgnoreCase(oldServiceCode)) {
				return true;
			} else {
				// return !(serviceService.checkExistedByField(property,
				// val, true) > 0);
				EsmeServices ser = new EsmeServices();
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