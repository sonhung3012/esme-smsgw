package com.fis.esme.form;

import java.util.ArrayList;
import java.util.List;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.PropertyExistedValidator;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.groupsdt.GroupsDTTransferer;
import com.fis.esme.persistence.Groups;
import com.fis.esme.persistence.SubGroupBean;
import com.fis.esme.persistence.Subscriber;
import com.fis.esme.subscriberdt.SubscriberDTTransferer;
import com.fis.esme.util.FormUtil;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.DefaultItemSorter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class PanelSubscriberFieldFactory extends DefaultFieldFactory implements PropertyExisted {

	private final TextField txtMsisdn = new TextField(TM.get("subs.field_isdn.caption"));
	private final TextField txtEmail = new TextField(TM.get("subs.field_email.caption"));
	private final TextField txtAddr = new TextField(TM.get("subs.field_addr.caption"));
	private final DateField dfBirth = new DateField(TM.get("subs.field_birth.caption"));
	private final ComboBox cbbStatus = new ComboBox(TM.get("service.field_status.caption"));
	private final ComboBox cbbSex = new ComboBox(TM.get("subs.field_sex.caption"));
	private String strActive = "1";
	private String strInactive = "0";
	private String strMale = "1";
	private String strFemale = "0";
	private String oldMsisdn = "";

	private SubscriberDTTransferer serviceSub = null;
	private GroupsDTTransferer serviceSg = null;
	private ArrayList<Groups> arrService = new ArrayList<Groups>();
	private BeanItemContainer<Groups> beanAction = new BeanItemContainer<Groups>(Groups.class);

	public PanelSubscriberFieldFactory() {

		initService();
		try {
			initComboBox();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTextField();
		initTextArea();
	}

	public void getDataAction(List<Groups> list) {

		if (list == null)
			beanAction.removeAllItems();
		else {
			beanAction.removeAllItems();
			beanAction.addAll(list);
			beanAction.setItemSorter(new DefaultItemSorter(FormUtil.stringComparator(true)));
			beanAction.sort(new Object[] { "name" }, new boolean[] { true });
		}
	}

	private void initService() {

		try {
			serviceSub = CacheServiceClient.serviceSubscriber;
			serviceSg = CacheServiceClient.serviceGroups;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final ComboBox cbSmsc = new ComboBox(TM.get("subs.field.group"));
	private SubGroupBean actParam = null;

	public void insertItemTempForCombobox(SubGroupBean actionParam) {

		if (actParam != null) {
			if (actParam.getGroups() != null && cbSmsc.getItem(actParam.getGroups()) != null) {
				cbSmsc.removeItem(actParam.getGroups());
			}
		}
		if (actionParam != null) {
			if (actionParam.getGroups() != null) {}

		}
		beanAction.sort(new Object[] { "name" }, new boolean[] { true });
	}

	public void initComboBox() throws Exception {

		try {
			arrService.clear();
			arrService.addAll(serviceSg.findAllWithoutParameter());
		} catch (Exception e) {

			e.printStackTrace();
		}

		Groups group = new Groups();
		group.setStatus("1");
		List<Groups> lstSmsc = CacheServiceClient.serviceGroups.findAllWithoutParameter();
		beanAction.addAll(lstSmsc);

		String nullCodeMsg = TM.get("subs.field_group_val");
		cbSmsc.removeAllValidators();
		cbSmsc.setRequired(true);
		cbSmsc.setRequiredError(nullCodeMsg);
		cbSmsc.setWidth(TM.get("common.form.field.fixedwidth"));
		cbSmsc.setContainerDataSource(beanAction);
		cbSmsc.setNullSelectionAllowed(false);
		cbSmsc.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbSmsc.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value instanceof Groups) {

					Groups group = (Groups) value;
					if (group.getStatus().equals("0")) {

						throw new InvalidValueException(TM.get("routing.combo.service.inactive.error"));
					}
				}
			}

			@Override
			public boolean isValid(Object value) {

				if (value instanceof Groups) {

					Groups group = (Groups) value;
					if (group.getStatus().equals("0")) {

						return false;
					}
				}
				return true;
			}
		});

		SpaceValidator groupEmpty = new SpaceValidator(nullCodeMsg);
		cbSmsc.addValidator(groupEmpty);

		cbbStatus.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbStatus.addItem(strActive);
		cbbStatus.addItem(strInactive);
		cbbStatus.setItemCaption(strActive, TM.get("service.field_combobox.value_active"));
		cbbStatus.setItemCaption(strInactive, TM.get("service.field_combobox.value_inactive"));
		cbbStatus.setNullSelectionAllowed(false);
		cbbStatus.setRequired(true);
		cbbStatus.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbStatus.getCaption()));
		cbbStatus.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cbbSex.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbSex.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbSex.addItem(strMale);
		cbbSex.addItem(strFemale);
		cbbSex.setItemCaption(strMale, TM.get("subs.field_combobox.value_male"));
		cbbSex.setItemCaption(strFemale, TM.get("subs.field_combobox.value_female"));
		cbbSex.setNullSelectionAllowed(false);
		cbbSex.setRequired(true);
		cbbSex.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbStatus.getCaption()));
		cbbSex.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
	}

	private void initTextField() {

		txtAddr.setWidth(TM.get("common.form.field.fixedwidth"));

		txtEmail.setWidth(TM.get("common.form.field.fixedwidth"));
		txtEmail.addValidator(new EmailValidator(TM.get("common.field.msg.validator_email", txtEmail.getCaption())));

		dfBirth.setWidth(TM.get("common.form.field.fixedwidth"));
		txtMsisdn.setMaxLength(50);
		txtMsisdn.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullNameMsg = TM.get("common.field.msg.validator_nulloremty", txtMsisdn.getCaption());
		txtMsisdn.setRequired(true);
		txtMsisdn.setRequiredError(nullNameMsg);
		SpaceValidator empty = new SpaceValidator(nullNameMsg);
		txtMsisdn.addValidator(empty);
		txtMsisdn.addValidator(new RegexpValidator("0\\d{9,11}", TM.get("subs.field_msisdn")));

		// txtMsisdn.addValidator(new CustomRegexpValidator(TM.get(
		// "service.field_code.regexp.validator_error",
		// txtMsisdn.getCaption()), true, errorCodeMsg, true));
		PropertyExistedValidator fieldExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtMsisdn.getCaption()), this, "msisdn");
		txtMsisdn.addValidator(fieldExistedValicator);

	}

	private void initTextArea() {

	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		if (propertyId.equals("status")) {
			return cbbStatus;
		} else if (propertyId.equals("msisdn")) {
			return txtMsisdn;
			// } else if (propertyId.equals("rootId")) {
			// return cbbRoot;
		} else if (propertyId.equals("groups")) {
			return cbSmsc;
		} else if (propertyId.equals("sex")) {
			return cbbSex;
		} else if (propertyId.equals("address")) {
			return txtAddr;
		} else if (propertyId.equals("birthDate")) {
			return dfBirth;
		} else if (propertyId.equals("email")) {
			return txtEmail;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public void setOldMsisdn(String oldMsisdn) {

		this.oldMsisdn = oldMsisdn;
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {

		String val = value.toString().trim().toUpperCase();
		if (property.equals("msisdn")) {
			if (value.toString().equalsIgnoreCase(oldMsisdn)) {
				return true;
			} else {
				// return !(serviceService.checkExistedByField(property,
				// val, true) > 0);
				Subscriber ser = new Subscriber();
				ser.setMsisdn(val);

				if (serviceSub.checkExisted(ser) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		}
		return true;
	}

	public TextField getTxtMsisdn() {

		return txtMsisdn;
	}

}