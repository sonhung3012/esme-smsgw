package com.fis.esme.form;

import java.util.List;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.FieldsValidator;
import com.fis.esme.classes.FieldsValidatorInterface;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.isdnprefix.IsdnPrefixTransferer;
import com.fis.esme.persistence.EsmeIsdnPrefix;
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.persistence.EsmeSmscRouting;
import com.fis.esme.shortcode.ShortCodeTransferer;
import com.fis.esme.smsc.SmscTransferer;
import com.fis.esme.smscrouting.SmscRoutingTransferer;
import com.fis.esme.util.FormUtil;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.DefaultItemSorter;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class SmscRoutingFieldFactory extends DefaultFieldFactory implements PropertyExisted, FieldsValidatorInterface {

	// public void setOldShortCode(String oldShortCode) {
	// this.oldShortCode = oldShortCode;
	// }

	public void setOldSmscId(long oldSmscId) {

		this.oldSmscId = oldSmscId;
	}

	public void setOldPrefixId(long oldPrefixId) {

		this.oldPrefixId = oldPrefixId;
	}

	private BeanItemContainer<EsmeSmsc> smscData = new BeanItemContainer<EsmeSmsc>(EsmeSmsc.class);
	private BeanItemContainer<EsmeIsdnPrefix> isdnPrefixData = new BeanItemContainer<EsmeIsdnPrefix>(EsmeIsdnPrefix.class);
	// private BeanItemContainer<EsmeShortCode> shortCodeData = new
	// BeanItemContainer<EsmeShortCode>(
	// EsmeShortCode.class);
	// private BeanItemContainer<EsmeSmscRouting> beanSmscRouting = new
	// BeanItemContainer<EsmeSmscRouting>(
	// EsmeSmscRouting.class);
	// private final ComboBox cbShortCode = new ComboBox(
	// TM.get("smscRouting.shortCode.caption"));
	private final ComboBox cbSmsc = new ComboBox(TM.get("smscRouting.smsc.caption"));
	private final ComboBox cbPrefix = new ComboBox(TM.get("smscRouting.prefix.caption"));

	private String strActive = "1";
	private String strInactive = "0";
	private String oldName = "";
	private String oldShortCode = "";
	private long oldPrefixId = 0;
	// private String oldPriority = "";
	private long oldSmscId = 0;
	EsmeSmscRouting actParam = null;

	private SmscTransferer smscService = CacheServiceClient.smscService;
	private IsdnPrefixTransferer isdnPrefixService = CacheServiceClient.isdnPrefixService;
	private ShortCodeTransferer serviceShortCode = CacheServiceClient.serviceShortCode;
	private SmscRoutingTransferer smscRoutingService = CacheServiceClient.smscRoutingService;

	public SmscRoutingFieldFactory() throws Exception {

		// setDataForComboBox();
		initService();
		initComboBox();
	}

	private void setDataForComboBox() throws Exception {

		EsmeSmsc esmeSmsc = new EsmeSmsc();
		esmeSmsc.setStatus("1");
		smscData.addAll(smscService.findAllWithOrderPaging(esmeSmsc, null, false, -1, -1, true));
		smscData.sort(new Object[] { "name" }, new boolean[] { true });

		// EsmeShortCode shortCode = new EsmeShortCode();
		// shortCode.setStatus("1");
		// shortCodeData.addAll(serviceShortCode.findAllWithOrderPaging(shortCode,
		// null, false, -1, -1, true));
		// shortCodeData.sort(new Object[] { "code" }, new boolean[] { true });

		EsmeIsdnPrefix isdnPrefix = new EsmeIsdnPrefix();
		isdnPrefix.setStatus("1");
		isdnPrefixData.addAll(isdnPrefixService.findAllWithOrderPaging(isdnPrefix, null, false, -1, -1, true));
		isdnPrefixData.sort(new Object[] { "prefixValue" }, new boolean[] { true });
		// EsmeSmscRouting smscRouting = new EsmeSmscRouting();
		// smscRouting.setStatus("1");
		// isdnPrefixData.addAll(smscRoutingService.findAllWithOrderPaging(
		// smscRouting, null, false, -1, -1, true));
	}

	private void initService() {

		// try {
		// actService = ActionTransfererClient.getService();
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// }
		//
		// try {
		// actionParamService = ActionParamTransfererClient.getService();
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// }
	}

	private void initComboBox() throws Exception {

		EsmeSmsc esmeSmsc = new EsmeSmsc();
		// esmeSmsc.setStatus("1");
		smscData.addAll(smscService.findAllWithOrderPaging(esmeSmsc, null, false, -1, -1, true));
		smscData.sort(new Object[] { "name" }, new boolean[] { true });

		String nullCodeMsg = TM.get("frmCommon.fieldNotNull", TM.get("frmActionparam.action"));
		cbSmsc.removeAllItems();
		cbSmsc.removeAllValidators();
		cbSmsc.setRequired(true);
		cbSmsc.setRequiredError(nullCodeMsg);
		cbSmsc.setWidth(TM.get("common.form.field.fixedwidth"));
		cbSmsc.setContainerDataSource(smscData);
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

		// String nullCodeMsg = TM.get("frmCommon.fieldNotNull",
		// TM.get("frmActionparam.action"));
		// cbShortCode.setRequired(true);
		// cbShortCode.setRequiredError(nullCodeMsg);
		// cbShortCode.setWidth(TM.get("common.form.field.fixedwidth"));
		// cbShortCode.setContainerDataSource(shortCodeData);
		// cbShortCode.setNullSelectionAllowed(false);
		// cbShortCode.setInputPrompt(TM.get("frmCommon.setInputPrompt",
		// TM.get("frmActionparam.action2")));
		// cbShortCode.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		// EsmeShortCode shortCode = new EsmeShortCode();
		// shortCode.setStatus("1");
		// shortCodeData.addAll(serviceShortCode.findAllWithOrderPaging(shortCode,
		// null, false, -1, -1, true));
		// shortCodeData.sort(new Object[] { "code" }, new boolean[] { true });
		// cbShortCode.setRequired(true);
		// cbShortCode.setRequiredError(nullCodeMsg);
		// cbShortCode.setWidth(TM.get("common.form.field.fixedwidth"));
		// cbShortCode.setContainerDataSource(shortCodeData);
		// cbShortCode.setNullSelectionAllowed(false);
		// // cbShortCode.setInputPrompt(TM.get("frmCommon.setInputPrompt",
		// // TM.get("frmActionparam.action2")));
		// cbShortCode.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		// cbSmsc.setRequired(true);
		// cbSmsc.setRequiredError(nullCodeMsg);
		// cbSmsc.setWidth(TM.get("common.form.field.fixedwidth"));
		// cbSmsc.setContainerDataSource(shortCodeData);
		// cbSmsc.setNullSelectionAllowed(false);
		// cbSmsc.setInputPrompt(TM.get("frmCommon.setInputPrompt",
		// TM.get("frmActionparam.action2")));
		// cbSmsc.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		EsmeIsdnPrefix isdnPrefix = new EsmeIsdnPrefix();
		// isdnPrefix.setStatus("1");
		isdnPrefixData.addAll(isdnPrefixService.findAllWithOrderPaging(isdnPrefix, null, false, -1, -1, true));
		isdnPrefixData.sort(new Object[] { "prefixValue" }, new boolean[] { true });

		String nullNameMsg1 = TM.get("common.field.msg.validator_nulloremty", cbPrefix.getCaption());
		cbPrefix.removeAllItems();
		cbPrefix.removeAllValidators();
		cbPrefix.setImmediate(true);
		cbPrefix.setRequired(true);
		cbPrefix.setRequiredError(nullNameMsg1);
		cbPrefix.setWidth(TM.get("common.form.field.fixedwidth"));
		cbPrefix.setContainerDataSource(isdnPrefixData);
		cbPrefix.setNullSelectionAllowed(false);
		// cbPrefix.setInputPrompt(TM.get("frmCommon.setInputPrompt",
		// TM.get("frmActionparam.action2")));
		cbPrefix.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		FieldsValidator fieldValicator = new FieldsValidator(this, "esmeIsdnPrefix", new Field[] { cbSmsc });
		cbPrefix.addValidator(fieldValicator);
		// cbPriority.setFilteringMode(ComboBox.FILTERINGMODE_OFF);
		cbPrefix.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value instanceof EsmeIsdnPrefix) {

					EsmeIsdnPrefix prefix = (EsmeIsdnPrefix) value;
					if (prefix.getStatus().equals("0")) {

						throw new InvalidValueException(TM.get("smscRouting.combo.prefix.inactive.error"));
					}
				}
			}

			@Override
			public boolean isValid(Object value) {

				if (value instanceof EsmeIsdnPrefix) {

					EsmeIsdnPrefix prefix = (EsmeIsdnPrefix) value;
					if (prefix.getStatus().equals("0")) {

						return false;
					}
				}
				return true;
			}
		});

	}

	// private void initTextField() throws Exception_Exception {
	// /* txtname */
	// txtName.setMaxLength(50);
	// txtName.setWidth(TM.get("common.form.field.fixedwidth"));
	// String nullNameMsg = TM.get("common.field.msg.validator_nulloremty",
	// txtName.getCaption());
	// txtName.setRequired(true);
	// txtName.setRequiredError(nullNameMsg);
	// SpaceValidator empty = new SpaceValidator(nullNameMsg);
	// txtName.addValidator(empty);
	// // txtName.addValidator(new CustomRegexpValidator(TM.get(
	// // "frmActionparam.customRegexpValidator", 1, 50), true, TM.get(
	// // "frmCommon.addValidatorCode",
	// // TM.get("frmActionparam.name",
	// // TM.get("frmActionparam.nameActionparam2")), true)));
	// txtName.setNullRepresentation("");
	// txtName.addValidator(new FieldsValidator(this, "name", new Field[] {
	// cbAction, txtName }));
	// /* txtDescription */
	// txtaDescription.setMaxLength(100);
	// txtaDescription.setWidth(TM.get("common.form.field.fixedwidth"));
	// txtaDescription.setNullRepresentation("");
	// /**/
	// cbPriority.addValidator(new FieldsValidator(this, "priority",
	// new Field[] { cbAction, txtName }));
	//
	// // txtDefVal
	// txtDefValue.setMaxLength(100);
	// txtDefValue.setWidth(TM.get("common.form.field.fixedwidth"));
	// String nullNameMsg1 = TM.get("common.field.msg.validator_nulloremty",
	// txtDefValue.getCaption());
	// txtDefValue.setRequired(true);
	// txtDefValue.setRequiredError(nullNameMsg1);
	// SpaceValidator empty1 = new SpaceValidator(nullNameMsg1);
	// txtDefValue.addValidator(empty1);
	// txtDefValue.addValidator(new CustomRegexpValidator(TM.get(
	// "frmActionparam.customRegexpValidator", 1, 100), true, TM.get(
	// "common.field_code.msg.validator_unicode",
	// txtDefValue.getCaption()), true));
	// txtDefValue.setNullRepresentation("");
	// }

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		// System.out.println("filed "+propertyId);
		if (propertyId.equals("esmeSmsc")) {
			return cbSmsc;
		} else if (propertyId.equals("esmeIsdnPrefix")) {
			return cbPrefix;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public void getDataAction(List<EsmeSmsc> list) {

		if (list == null)
			smscData.removeAllItems();
		else {
			smscData.removeAllItems();
			smscData.addAll(list);
			smscData.setItemSorter(new DefaultItemSorter(FormUtil.stringComparator(true)));
			smscData.sort(new Object[] { "shortCode" }, new boolean[] { true });
		}
	}

	@Override
	public Object isValid(String property, Object currentFieldValue, Object otherObject) {

		try {
			Field[] fields = (Field[]) otherObject;
			EsmeSmsc esmeSmsc = (EsmeSmsc) ((Field) fields[0]).getValue();
			// EsmeCp esmeCp = (EsmeCp) fields[1].getValue();
			// EsmeShortCode esmeShortCode = (EsmeShortCode)
			// fields[2].getValue();
			EsmeIsdnPrefix isdnPrefix = (EsmeIsdnPrefix) currentFieldValue;

			EsmeSmscRouting routing = new EsmeSmscRouting();
			routing.setEsmeSmsc(esmeSmsc);
			routing.setEsmeIsdnPrefix(isdnPrefix);
			if (oldPrefixId == isdnPrefix.getPrefixId() && oldSmscId == esmeSmsc.getSmscId())
				return null;
			if (smscRoutingService.checkExisted(routing) > 0) {
				return TM.get("smscRouting.fields.msg.validator_existed");
			}

			return null;
		} catch (Exception e) {
			return null;
		}
	}

	// @Override
	// public Object isValid(String property, Object currentFieldValue,
	// Object otherObject) {
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
	//
	// Long prefixId = null;
	// EsmeIsdnPrefix isdnPrefix = new EsmeIsdnPrefix();
	// if (fields[1] instanceof EsmeIsdnPrefix) {
	// prefixId = (Long) ((Field) fields[1]).getValue();
	// } else {
	// isdnPrefix = (EsmeIsdnPrefix) ((Field) fields[1]).getValue();
	// prefixId = isdnPrefix.getPrefixId();
	// }
	//
	// // String value = (String) fields[2].getValue();
	// {
	// List<String> field = new ArrayList<String>();
	// field.add("esmeSmsc");
	// field.add("esmeIsdnPrefix");
	// List<Object> values = new ArrayList<Object>();
	// values.add(smscId);
	// values.add(prefixId);
	// }
	//
	// return null;
	// }

	@Override
	public boolean isPropertyExisted(String property, Object value) {

		String val = value.toString().trim();
		// System.out.println("val "+val +" pro "+property);
		EsmeSmscRouting obj = new EsmeSmscRouting();
		if (property.equals("esmeSmsc")) {
			EsmeSmsc smsc = new EsmeSmsc();
			smsc.setSmscId(Long.valueOf(val));
			obj.setEsmeSmsc(smsc);
		} else if (property.equals("esmeIsdnPrefix")) {
			EsmeIsdnPrefix pre = new EsmeIsdnPrefix();
			pre.setPrefixId(Long.valueOf(val));
			obj.setEsmeIsdnPrefix(pre);
		}
		// return !(serviceService.checkExistedByField(property,
		// val, true) > 0);

		if (smscRoutingService.checkExisted(obj) >= 1) {
			// System.out.println("check "+obj.getEsmeIsdnPrefix().getPrefixId()
			// );
			return false;
		} else {
			return true;
		}
	}

	public void insertItemTempForCombobox(EsmeSmscRouting actionParam) {

		// if (actParam != null) {
		// if (actParam.getSmscId()>0
		// && cbAction.getItem(actParam.getSmscId()) != null) {
		// cbAction.removeItem(actParam.getSmscId());
		// }
		// }
		// if (actionParam != null) {
		// if (actionParam.getSmscId()>0) {
		// // if (actionParam.getPrcAction().getStatus().equals("0")) {
		// // actParam = actionParam;
		// // cbAction.addItem(actParam.getPrcAction());
		// // }
		// }
		//
		// }
		// beanAction.sort(new Object[] { "name" }, new boolean[] { true });
	}
}