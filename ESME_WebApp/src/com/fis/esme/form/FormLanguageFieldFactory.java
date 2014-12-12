package com.fis.esme.form;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.CustomRegexpValidator;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.PropertyExistedValidator;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.component.StringCheckBox;
import com.fis.esme.language.LanguageTransferer;
import com.fis.esme.persistence.EsmeLanguage;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

public class FormLanguageFieldFactory extends DefaultFieldFactory implements PropertyExisted {

	private final ComboBox cboStatus = new ComboBox(TM.get("frmCommon.cboStatus"));
	private final StringCheckBox cboDefault = new StringCheckBox(TM.get("frmCommon.IsDefault"));
	private final TextField txtName = new TextField(TM.get("frmCommon.txtName"));
	private final TextField txtCode = new TextField(TM.get("frmCommon.txtCode"));
	private String strActive = "1";
	private String strInactive = "0";
	private boolean search = false;
	private String oldCode = "";
	private String oldName = "";
	private LanguageTransferer serviceLanguage = null;

	public FormLanguageFieldFactory() {

		this(false);
	}

	private void initService() {

		try {
			serviceLanguage = CacheServiceClient.serviceLanguage;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FormLanguageFieldFactory(boolean search) {

		this.search = search;
		initService();
		initCombobox();
		initTextField();

		cboDefault.setImmediate(true);
		cboDefault.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				boolean val = (Boolean) event.getProperty().getValue();
				if (val) {
					cboStatus.select(strActive);
					cboStatus.setEnabled(false);
				} else {
					cboStatus.setEnabled(true);
				}
			}
		});
	}

	private void initCombobox() {

		cboStatus.removeAllItems();
		cboStatus.setWidth(TM.get("common.form.field.fixedwidth"));
		cboStatus.addItem(strActive);
		cboStatus.addItem(strInactive);
		cboStatus.setItemCaption(strActive, TM.get("frmCommon.strActive"));
		cboStatus.setItemCaption(strInactive, TM.get("frmCommon.strInactive"));
		cboStatus.setNullSelectionAllowed(false);
		cboStatus.setRequired(true);
		cboStatus.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cboStatus.setRequiredError(TM.get("frmCommon.fieldNotNull", cboStatus.getCaption()));
	}

	private void initTextField() {

		txtName.setMaxLength(20);
		txtName.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullNameMsg = TM.get("common.field.msg.validator_nulloremty", txtName.getCaption());
		txtName.setRequired(true);
		txtName.setRequiredError(nullNameMsg);
		SpaceValidator empty = new SpaceValidator(nullNameMsg);
		txtName.addValidator(empty);
		PropertyExistedValidator fieldNameExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtName.getCaption()), this, "name");
		txtName.addValidator(fieldNameExistedValicator);

		/* txtCode */
		txtCode.setMaxLength(10);
		txtCode.setWidth(TM.get("common.form.field.fixedwidth"));
		String errorCodeMsg = TM.get("common.field_code.msg.validator_unicode", txtCode.getCaption());
		String nullCodeMsg = TM.get("common.field.msg.validator_nulloremty", txtCode.getCaption());
		txtCode.setRequired(true);
		txtCode.setRequiredError(nullCodeMsg);
		SpaceValidator emptyCode = new SpaceValidator(nullCodeMsg);
		txtCode.addValidator(emptyCode);
		txtCode.addValidator(new CustomRegexpValidator(TM.get("language.field_code.regexp.validator_error", txtCode.getCaption()), true, errorCodeMsg, true));
		PropertyExistedValidator fieldCodeExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtCode.getCaption()), this, "code");
		txtCode.addValidator(fieldCodeExistedValicator);

	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		/* status */
		if ("status".equals(propertyId)) {
			/**
			 * dong moi them
			 * */
			EsmeLanguage bean = ((BeanItem<EsmeLanguage>) item).getBean();
			if ("1".equals(bean.getIsDefault())) {
				cboStatus.setEnabled(false);
			} else {
				cboStatus.setEnabled(true);
			}
			return cboStatus;
		}

		/* is default */
		else if ("isDefault".equals(propertyId)) {
			/**
			 * dong moi them
			 * */
			EsmeLanguage bean = ((BeanItem<EsmeLanguage>) item).getBean();
			cboDefault.setEnabled(!"1".equals(bean.getIsDefault()));

			return cboDefault;
		}

		/* name */
		else if ("name".equals(propertyId)) {
			return txtName;
		}

		/* code */
		else if ("code".equals(propertyId)) {
			txtCode.selectAll();
			return txtCode;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public boolean isSearch() {

		return search;
	}

	public void setSearch(boolean search) {

		this.search = search;
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {

		String val = value.toString().trim().toUpperCase();
		if (property.equals("code")) {
			if (value.toString().equalsIgnoreCase(oldCode)) {
				return true;
			} else {
				// return !(serviceService.checkExistedByField(property,
				// val, true) > 0);
				EsmeLanguage ser = new EsmeLanguage();
				ser.setCode(val);

				if (serviceLanguage.checkExisted(ser) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		} else if (property.equals("name")) {

			if (value.toString().equalsIgnoreCase(oldName)) {
				return true;
			} else {
				// return !(serviceService.checkExistedByField(property,
				// val, true) > 0);
				EsmeLanguage ser = new EsmeLanguage();
				ser.setName(val);

				if (serviceLanguage.checkExisted(ser) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		}

		return true;
	}

	public void setOldCode(String oldCode) {

		this.oldCode = oldCode;
	}

	public void setOldName(String oldName) {

		this.oldName = oldName;
	}

}
