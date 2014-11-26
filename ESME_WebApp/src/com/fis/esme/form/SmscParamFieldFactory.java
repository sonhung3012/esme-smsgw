package com.fis.esme.form;

import java.util.List;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.FieldsValidator;
import com.fis.esme.classes.FieldsValidatorInterface;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.client.SmscParamTransfererClient;
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.persistence.EsmeSmscParam;
import com.fis.esme.smscparam.Exception_Exception;
import com.fis.esme.smscparam.SmscParamTransferer;
import com.fis.esme.util.FormUtil;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.DefaultItemSorter;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class SmscParamFieldFactory extends DefaultFieldFactory implements PropertyExisted, FieldsValidatorInterface {

	// public void setOldPriority(String oldPriority) {
	// this.oldPriority = oldPriority;
	// }
	//
	public void setOldSmscId(long oldSmscId) {

		this.oldSmscId = oldSmscId;
	}

	public void setOldName(String oldName) {

		this.oldName = oldName;
	}

	public void setOldValue(String oldValue) {

		this.oldValue = oldValue;
	}

	private final TextField txtName = new TextField(TM.get("smscParam.field.name"));
	private final TextField txtValue = new TextField(TM.get("smscParam.field.value"));
	// private final TextArea txtaDescription = new TextArea(
	// TM.get("frmActionparam.description"));
	// private final ComboBox cbStatus = new ComboBox(
	// TM.get("frmCommon.cboStatus"));
	private final ComboBox cbSmsc = new ComboBox(TM.get("smscParam.field.smsc"));
	private BeanItemContainer<EsmeSmsc> beanAction = new BeanItemContainer<EsmeSmsc>(EsmeSmsc.class);
	// private final ComboBox cbPriority = new ComboBox(
	// TM.get("frmActionparam.priority"));
	private String strActive = "1";
	private String strInactive = "0";
	private String oldName = "";
	private String oldValue = "";
	// private String oldPriority = "";
	private long oldSmscId = 0;
	EsmeSmscParam actParam = null;

	private SmscParamTransferer service;

	// private ActionParamTransferer actionParamService;

	public SmscParamFieldFactory() throws Exception {

		initService();
		// initComboBox();
		initTextField();
		initComboBox();
	}

	private void initService() {

		try {
			service = SmscParamTransfererClient.getService();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void initComboBox() throws Exception {

		EsmeSmsc esmeSmsc = new EsmeSmsc();
		List<EsmeSmsc> lstSmsc = CacheServiceClient.smscService.findAllWithOrderPaging(esmeSmsc, null, false, -1, -1, true);
		beanAction.addAll(lstSmsc);

		String nullCodeMsg = TM.get("frmCommon.fieldNotNull", TM.get("frmActionparam.action"));
		cbSmsc.removeAllItems();
		cbSmsc.removeAllValidators();
		cbSmsc.setRequired(true);
		cbSmsc.setRequiredError(nullCodeMsg);
		cbSmsc.setWidth(TM.get("common.form.field.fixedwidth"));
		cbSmsc.setContainerDataSource(beanAction);
		cbSmsc.setNullSelectionAllowed(false);
		// cbSmsc.setInputPrompt(TM.get("frmCommon.setInputPrompt",
		// TM.get("frmActionparam.action2")));
		cbSmsc.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbSmsc.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value instanceof EsmeSmsc) {

					EsmeSmsc smsc = (EsmeSmsc) value;
					if (smsc.getStatus().equals("0")) {

						throw new InvalidValueException(TM.get("smscParam.combo.smsc.inactive.error"));
					}
				}
			}

			@Override
			public boolean isValid(Object value) {

				if (value instanceof EsmeSmsc) {

					EsmeSmsc smsc = (EsmeSmsc) value;
					if (smsc.getStatus().equals("0")) {

						return false;
					}
				}
				return true;
			}
		});
	}

	public void insertItemTempForCombobox(EsmeSmscParam actionParam) {

		if (actParam != null) {
			if (actParam.getSmscId() != null && cbSmsc.getItem(actParam.getSmscId()) != null) {
				cbSmsc.removeItem(actParam.getSmscId());
			}
		}
		if (actionParam != null) {
			if (actionParam.getSmscId() != null) {
				// if (actionParam.getPrcAction().getStatus().equals("0")) {
				// actParam = actionParam;
				// cbAction.addItem(actParam.getPrcAction());
				// }
			}

		}
		beanAction.sort(new Object[] { "name" }, new boolean[] { true });
	}

	private void initTextField() throws Exception_Exception {

		/* txtname */
		txtName.setMaxLength(50);
		txtName.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullNameMsg = TM.get("common.field.msg.validator_nulloremty", txtName.getCaption());
		txtName.setRequired(true);
		txtName.setRequiredError(nullNameMsg);
		SpaceValidator empty = new SpaceValidator(nullNameMsg);
		txtName.addValidator(empty);
		txtName.setNullRepresentation("");

		// PropertyExistedValidator fieldExistedValicator = new PropertyExistedValidator(
		// TM.get("common.field.msg.validator_existed",
		// txtName.getCaption()), this, "name");
		// txtName.addValidator(fieldExistedValicator);

		txtName.addValidator(new FieldsValidator(this, "name", new Field[] { cbSmsc, txtName }));

		txtValue.setMaxLength(100);
		txtValue.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullNameMsg1 = TM.get("common.field.msg.validator_nulloremty", txtValue.getCaption());
		txtValue.setRequired(true);
		txtValue.setRequiredError(nullNameMsg1);
		SpaceValidator empty1 = new SpaceValidator(nullNameMsg1);
		txtValue.addValidator(empty1);
		txtValue.setNullRepresentation("");
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		if (propertyId.equals("name")) {
			txtName.selectAll();
			return txtName;
		} else if (propertyId.equals("value")) {
			return txtValue;
		} else if (propertyId.equals("smscId")) {
			return cbSmsc;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public void getDataAction(List<EsmeSmsc> list) {

		if (list == null)
			beanAction.removeAllItems();
		else {
			beanAction.removeAllItems();
			beanAction.addAll(list);
			beanAction.setItemSorter(new DefaultItemSorter(FormUtil.stringComparator(true)));
			beanAction.sort(new Object[] { "name" }, new boolean[] { true });
		}
	}

	@Override
	public Object isValid(String property, Object currentFieldValue, Object otherObject) {

		// Field[] fields = (Field[]) otherObject;
		// Long smscId = null;
		// EsmeSmsc esmeSmsc = new EsmeSmsc();
		//
		// if (fields[0] instanceof EsmeSmsc) {
		// smscId = (Long) ((Field) fields[0]).getValue();
		// } else {
		// esmeSmsc = (EsmeSmsc) ((Field) fields[0]).getValue();
		// smscId = esmeSmsc.getSmscId();
		// }
		// String name = (String) fields[1].getValue();
		// if (property.equals("name")) {
		// if (name.toString().equalsIgnoreCase(oldName)
		// && smscId == oldSmscId) {
		// return null;
		// } else {
		// EsmeSmscParam pr = new EsmeSmscParam();
		// EsmeSmsc smsc = new EsmeSmsc();
		// smsc.setSmscId(smscId);
		// pr.setSmscId(smsc);
		// pr.setName(name);
		// if (service.checkExisted(pr) >= 1) {
		// return TM.get("smscParam.fields.msg.validator_existed");
		// }
		// return null;
		// }
		// }
		try {
			Field[] fields = (Field[]) otherObject;
			EsmeSmsc esmeSmsc = (EsmeSmsc) ((Field) fields[0]).getValue();
			// EsmeCp esmeCp = (EsmeCp) fields[1].getValue();
			// EsmeShortCode esmeShortCode = (EsmeShortCode)
			// fields[2].getValue();
			String name = (String) currentFieldValue;

			EsmeSmscParam pr = new EsmeSmscParam();
			pr.setSmscId(esmeSmsc);
			pr.setName(name);
			if (oldName.equalsIgnoreCase(name) && oldSmscId == esmeSmsc.getSmscId())
				return null;
			if (service.checkExisted(pr) > 0) {
				return TM.get("smscParam.fields.msg.validator_existed");
			}

			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {

		if (value != null) {
			String val = value.toString().trim();
			EsmeSmscParam ser = new EsmeSmscParam();
			if (property.equals("name")) {
				if (value.toString().equalsIgnoreCase(oldName)) {
					return true;
				}
				ser.setName(val);

				if (service.checkExisted(ser) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		}
		return true;
	}
}