package com.fis.esme.form.lookup;

import java.util.ArrayList;
import java.util.Collections;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.CompareDateTimeValidator;
import com.fis.esme.classes.FieldsValidatorInterface;
import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.smslog.EsmeSmsLogTransferer;
import com.fis.esme.util.CacheDB;
import com.fis.esme.util.FormUtil;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

public class SearchFormFieldFactory extends DefaultFieldFactory implements FieldsValidatorInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String FIElD_WIDTH = "150px";
	private TextField tfMsisdn = new TextField();
	private PopupDateField dtFromDate = new PopupDateField();
	private PopupDateField dtToDate = new PopupDateField();

	private BeanItemContainer<EsmeShortCode> shortCodeData = new BeanItemContainer<EsmeShortCode>(EsmeShortCode.class);
	// private Label shortCodeLb = new Label(TM.get("smslog.search.shortCodeLb"));
	private ComboBox cbbShortCode = new ComboBox();

	private ComboBox cbbService = new ComboBox();
	private BeanItemContainer<EsmeServices> serviceData = new BeanItemContainer<EsmeServices>(EsmeServices.class);
	// private Label serviceLb = new Label(TM.get("smslog.search.serviceLb"));

	private ComboBox cbbCommand = new ComboBox();
	private BeanItemContainer<EsmeSmsCommand> commandData = new BeanItemContainer<EsmeSmsCommand>(EsmeSmsCommand.class);
	// private Label commandLb = new Label(TM.get("smslog.search.commandLb"));

	private ComboBox cbbCp = new ComboBox();
	private ComboBox cbbType = new ComboBox();
	private ComboBox cbbStatus = new ComboBox();

	private ArrayList<EsmeServices> arrService = new ArrayList<EsmeServices>();
	private ArrayList<EsmeSmsCommand> arrCommand = new ArrayList<EsmeSmsCommand>();
	private ArrayList<EsmeShortCode> arrShortCode = new ArrayList<EsmeShortCode>();
	private ArrayList<EsmeCp> arrCp = new ArrayList<EsmeCp>();

	private EsmeSmsLogTransferer serviceSrv = CacheServiceClient.smsLogServices;

	public SearchFormFieldFactory() throws Exception {

		setDataForComboBox();
		initComponent();
		initValidate();
		sameWidth();
	}

	private void setDataForComboBox() throws Exception {

		// serviceData.addAll(serviceSrv.getServiceActive());
		// shortCodeData.addAll(serviceSrv.getShortCodeActive());
		// commandData.addAll(serviceSrv.getCommandActive());

		arrService.addAll(serviceSrv.getServiceActive());
		arrShortCode.addAll(serviceSrv.getShortCodeActive());
		arrCommand.addAll(serviceSrv.getCommandActive());

		try {
			EsmeCp esmeCp = new EsmeCp();
			CacheDB.cacheCP = CacheServiceClient.serviceCp.findAllWithOrderPaging(esmeCp, null, false, -1, -1, true);
		} catch (com.fis.esme.cp.Exception_Exception e) {
			e.printStackTrace();
		}
		Collections.sort(CacheDB.cacheCP, FormUtil.stringComparator(true));
		arrCp.addAll(CacheDB.cacheCP);
	}

	public void focusFirstField() {

		tfMsisdn.focus();
		tfMsisdn.selectAll();
	}

	private void sameWidth() {

		dtFromDate.setWidth(FIElD_WIDTH);
		dtToDate.setWidth(FIElD_WIDTH);
		tfMsisdn.setWidth(FIElD_WIDTH);
		cbbCommand.setWidth(FIElD_WIDTH);
		cbbShortCode.setWidth(FIElD_WIDTH);
		cbbService.setWidth(FIElD_WIDTH);
		cbbCp.setWidth(FIElD_WIDTH);
		cbbType.setWidth(FIElD_WIDTH);
		cbbStatus.setWidth(FIElD_WIDTH);
	}

	private void initComponent() {

		tfMsisdn = new TextField();

		tfMsisdn.setNullRepresentation("");
		tfMsisdn.focus();
		tfMsisdn.addListener(new FieldEvents.FocusListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void focus(FocusEvent event) {

				tfMsisdn.selectAll();
			}
		});

		dtFromDate = new PopupDateField();
		dtFromDate.setDateFormat(FormUtil.stringShortDateFormat);
		dtFromDate.setRequired(true);
		dtFromDate.setRequiredError(TM.get("smslog.search.RequiredError", "From date"));

		dtFromDate.setParseErrorMessage(TM.get("promdetail.setParseErrorMessage", "From date", dtFromDate.getDateFormat().toUpperCase()));
		dtFromDate.setResolution(PopupDateField.RESOLUTION_DAY);

		dtToDate = new PopupDateField();
		dtToDate.setDateFormat(FormUtil.stringShortDateFormat);
		dtToDate.setRequired(true);
		dtToDate.setRequiredError(TM.get("smslog.search.RequiredError", "To date"));

		dtToDate.setParseErrorMessage(TM.get("promdetail.setParseErrorMessage", "To date", dtToDate.getDateFormat().toUpperCase()));
		dtToDate.setResolution(PopupDateField.RESOLUTION_DAY);

		cbbShortCode.setImmediate(true);
		cbbShortCode.removeAllValidators();
		cbbShortCode.removeAllItems();
		// cbbShortCode.setContainerDataSource(shortCodeData);
		for (EsmeShortCode code : arrShortCode) {
			cbbShortCode.addItem(code.getShortCodeId());
			cbbShortCode.setItemCaption(code.getShortCodeId(), code.getCode());
		}
		// cbbShortCode.setNullSelectionAllowed(false);
		// cbbShortCode.setInputPrompt(TM.get("common.field_combobox.inputprompt",
		// shortCodeLb.getValue().toString().toLowerCase()));
		// cbbShortCode.setRequired(true);
		// cbbShortCode.setRequiredError(TM.get(
		// "common.field.msg.validator_nulloremty", shortCodeLb.getValue()
		// .toString()));
		cbbShortCode.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbShortCode.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value instanceof Long) {
					Long id = (Long) value;
					for (EsmeShortCode shortCode : arrShortCode) {

						if (shortCode.getShortCodeId() == id) {

							if (shortCode.getStatus().equals("0")) {

								throw new InvalidValueException(TM.get("smslog.combo.shortCode.inactive.error"));
							}
						}
					}
				}

			}

			@Override
			public boolean isValid(Object value) {

				if (value instanceof Long) {
					Long id = (Long) value;
					for (EsmeShortCode shortCode : arrShortCode) {

						if (shortCode.getShortCodeId() == id) {

							if (shortCode.getStatus().equals("0")) {

								return false;
							}
						}
					}
				}
				return true;
			}
		});

		cbbService.setImmediate(true);
		cbbService.removeAllValidators();
		cbbService.removeAllItems();
		// cbbService.setContainerDataSource(serviceData);
		for (EsmeServices service : arrService) {
			cbbService.addItem(service.getServiceId());
			cbbService.setItemCaption(service.getServiceId(), service.getName());
		}
		// cbbService.setNullSelectionAllowed(false);
		// cbbService.setInputPrompt(TM.get("common.field_combobox.inputprompt",
		// serviceLb.getValue().toString().toLowerCase()));
		// cbbService.setRequired(true);
		// cbbService.setRequiredError(TM.get(
		// "common.field.msg.validator_nulloremty", serviceLb.getValue()
		// .toString()));
		cbbService.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbService.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value instanceof Long) {
					Long id = (Long) value;
					for (EsmeServices esmeServices : arrService) {

						if (esmeServices.getServiceId() == id) {

							if (esmeServices.getStatus().equals("0")) {

								throw new InvalidValueException(TM.get("smslog.combo.service.inactive.error"));
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
							}
						}
					}
				}
				return true;
			}
		});

		cbbCommand.setImmediate(true);
		cbbCommand.removeAllValidators();
		cbbCommand.removeAllItems();
		// cbbCommand.setContainerDataSource(commandData);
		for (EsmeSmsCommand command : arrCommand) {
			cbbCommand.addItem(command.getCommandId());
			cbbCommand.setItemCaption(command.getCommandId(), command.getCode());
		}
		// cbbCommand.setNullSelectionAllowed(false);
		// cbbCommand.setInputPrompt(TM.get("common.field_combobox.inputprompt",
		// commandLb.getValue().toString().toLowerCase()));
		// cbbCommand.setRequired(true);
		// cbbCommand.setRequiredError(TM.get(
		// "common.field.msg.validator_nulloremty", commandLb.getValue()
		// .toString()));
		cbbCommand.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbCommand.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value instanceof Long) {
					Long id = (Long) value;
					for (EsmeSmsCommand command : arrCommand) {

						if (command.getCommandId() == id) {

							if (command.getStatus().equals("0")) {

								throw new InvalidValueException(TM.get("smslog.combo.command.inactive.error"));
							}
						}
					}
				}

			}

			@Override
			public boolean isValid(Object value) {

				if (value instanceof Long) {
					Long id = (Long) value;
					for (EsmeSmsCommand command : arrCommand) {

						if (command.getCommandId() == id) {

							if (command.getStatus().equals("0")) {

								return false;
							}
						}
					}
				}
				return true;
			}
		});

		cbbCp.setImmediate(true);
		cbbCp.removeAllValidators();
		cbbCp.removeAllItems();
		for (EsmeCp cp : arrCp) {
			cbbCp.addItem(cp.getCpId());
			cbbCp.setItemCaption(cp.getCpId(), cp.getCode());
		}
		cbbCp.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbCp.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value instanceof Long) {
					Long id = (Long) value;
					for (EsmeCp cp : arrCp) {

						if (cp.getCpId() == id) {

							if (cp.getStatus().equals("0")) {

								throw new InvalidValueException(TM.get("smslog.combo.cp.inactive.error"));
							}
						}
					}
				}

			}

			@Override
			public boolean isValid(Object value) {

				if (value instanceof Long) {
					Long id = (Long) value;
					for (EsmeCp cp : arrCp) {

						if (cp.getCpId() == id) {

							if (cp.getStatus().equals("0")) {

								return false;
							}
						}
					}
				}
				return true;
			}
		});

		cbbType.setImmediate(true);
		cbbType.removeAllItems();
		cbbType.addItem("1");
		cbbType.addItem("2");
		cbbType.setItemCaption("1", TM.get("smslog.form.type_1"));
		cbbType.setItemCaption("2", TM.get("smslog.form.type_2"));
		cbbType.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cbbStatus.setImmediate(true);
		cbbStatus.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cbbType.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				if (cbbType.getValue() == null) {

					cbbStatus.removeAllItems();

				} else if (cbbType.getValue() != null && cbbType.getValue() instanceof String) {

					String type = (String) cbbType.getValue();

					if (type.equals("1")) {

						cbbStatus.removeAllItems();
						cbbStatus.addItem("0");
						cbbStatus.addItem("1");
						cbbStatus.addItem("2");
						cbbStatus.addItem("3");
						cbbStatus.addItem("6");

						cbbStatus.setItemCaption("0", TM.get("smslog.mo.status_0"));
						cbbStatus.setItemCaption("1", TM.get("smslog.mo.status_1"));
						cbbStatus.setItemCaption("2", TM.get("smslog.mo.status_2"));
						cbbStatus.setItemCaption("3", TM.get("smslog.mo.status_3"));
						cbbStatus.setItemCaption("6", TM.get("smslog.mo.status_6"));
					} else if (type.equals("2")) {

						cbbStatus.removeAllItems();
						cbbStatus.addItem("0");
						cbbStatus.addItem("1");
						cbbStatus.addItem("2");
						cbbStatus.addItem("4");
						cbbStatus.addItem("5");
						cbbStatus.addItem("6");
						cbbStatus.addItem("7");
						cbbStatus.addItem("8");

						cbbStatus.setItemCaption("0", TM.get("smslog.mt.status_0"));
						cbbStatus.setItemCaption("1", TM.get("smslog.mt.status_1"));
						cbbStatus.setItemCaption("2", TM.get("smslog.mt.status_2"));
						cbbStatus.setItemCaption("4", TM.get("smslog.mt.status_4"));
						cbbStatus.setItemCaption("5", TM.get("smslog.mt.status_5"));
						cbbStatus.setItemCaption("6", TM.get("smslog.mt.status_6"));
						cbbStatus.setItemCaption("7", TM.get("smslog.mt.status_7"));
						cbbStatus.setItemCaption("8", TM.get("smslog.mt.status_8"));
					}

				}
			}
		});

	}

	private void initValidate() {

		// tfMsisdn.setRequired(true);
		tfMsisdn.setMaxLength(11);
		tfMsisdn.setRequiredError(TM.get("smslog.search.RequiredError", "Phone book"));
		String sms = TM.get("smslog.search.FormatNumber", "Phone book");
		// tfMsisdn.addValidator(new CustomRegexpValidator(
		// "^((01)|(1))[0-9]{9}|((09)|(9))[0-9]{8}$", sms));

		if (dtFromDate != null && dtToDate != null) {
			CompareDateTimeValidator val = new CompareDateTimeValidator(TM.get("smslog.search.CompareDatetime", "To date", "From date"), dtFromDate, 2, true);
			val.setClearTime(true);
			dtToDate.addValidator(val);
		}

	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		Field field = null;
		// System.out.println("pro " + propertyId);
		if ("tfMsisdn".equals(propertyId)) {
			return tfMsisdn;
		} else if ("fromDate".equals(propertyId)) {
			return dtFromDate;
		} else if ("toDate".equals(propertyId)) {
			return dtToDate;
		} else if ("cbbShortCode".equals(propertyId)) {
			return cbbShortCode;
		} else if ("cbbCommand".equals(propertyId)) {
			return cbbCommand;
		} else if ("cbbService".equals(propertyId)) {
			return cbbService;
		} else if ("cbbCp".equals(propertyId)) {
			return cbbCp;
		} else if ("cbbType".equals(propertyId)) {
			return cbbType;
		} else if ("cbbStatus".equals(propertyId)) {
			return cbbStatus;
		} else {
			field = super.createField(item, propertyId, uiContext);
		}
		return field;
	}

	@Override
	public Object isValid(String property, Object currentFieldValue, Object otherObject) {

		return null;
	}
}
