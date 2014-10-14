package com.fis.esme.form.lookup;

import java.util.ArrayList;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.CompareDateTimeValidator;
import com.fis.esme.classes.CustomRegexpValidator;
import com.fis.esme.classes.FieldsValidatorInterface;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.smslog.EsmeSmsLogTransferer;
import com.fis.esme.util.FormUtil;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

public class SearchFormFieldFactory extends DefaultFieldFactory implements
		FieldsValidatorInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String FIElD_WIDTH = "150px";
	private TextField tfMsisdn = new TextField();
	private PopupDateField dtFromDate = new PopupDateField();
	private PopupDateField dtToDate = new PopupDateField();

	private BeanItemContainer<EsmeShortCode> shortCodeData = new BeanItemContainer<EsmeShortCode>(
			EsmeShortCode.class);
//	private Label shortCodeLb = new Label(TM.get("smslog.search.shortCodeLb"));
	private ComboBox cbbShortCode = new ComboBox();

	private ComboBox cbbService = new ComboBox();
	private BeanItemContainer<EsmeServices> serviceData = new BeanItemContainer<EsmeServices>(
			EsmeServices.class);
//	private Label serviceLb = new Label(TM.get("smslog.search.serviceLb"));

	private ComboBox cbbCommand = new ComboBox();
	private BeanItemContainer<EsmeSmsCommand> commandData = new BeanItemContainer<EsmeSmsCommand>(
			EsmeSmsCommand.class);
//	private Label commandLb = new Label(TM.get("smslog.search.commandLb"));
	
	private ArrayList<EsmeServices> arrService = new ArrayList<EsmeServices>();
	private ArrayList<EsmeSmsCommand> arrCommand = new ArrayList<EsmeSmsCommand>();
	private ArrayList<EsmeShortCode> arrShortCode = new ArrayList<EsmeShortCode>();

	private EsmeSmsLogTransferer serviceSrv = CacheServiceClient.smsLogServices;

	public SearchFormFieldFactory() throws Exception {
		setDataForComboBox();
		initComponent();
		initValidate();
		sameWidth();
	}

	private void setDataForComboBox() throws Exception {
//		serviceData.addAll(serviceSrv.getServiceActive());
//		shortCodeData.addAll(serviceSrv.getShortCodeActive());
//		commandData.addAll(serviceSrv.getCommandActive());
		
		arrService.addAll(serviceSrv.getServiceActive());
		arrShortCode.addAll(serviceSrv.getShortCodeActive());
		arrCommand.addAll(serviceSrv.getCommandActive());
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
		dtFromDate.setRequiredError(TM.get("smslog.search.RequiredError",
				"From date"));

		dtFromDate.setParseErrorMessage(TM.get(
				"promdetail.setParseErrorMessage", "From date",
				dtFromDate.getDateFormat().toUpperCase()));
		dtFromDate.setResolution(PopupDateField.RESOLUTION_DAY);

		dtToDate = new PopupDateField();
		dtToDate.setDateFormat(FormUtil.stringShortDateFormat);
		dtToDate.setRequired(true);
		dtToDate.setRequiredError(TM.get("smslog.search.RequiredError",
				"To date"));

		dtToDate.setParseErrorMessage(TM.get("promdetail.setParseErrorMessage",
				"To date", dtToDate.getDateFormat().toUpperCase()));
		dtToDate.setResolution(PopupDateField.RESOLUTION_DAY);

		cbbShortCode.setImmediate(true);
//		cbbShortCode.setContainerDataSource(shortCodeData);
		for (EsmeShortCode code : arrShortCode) {
			cbbShortCode.addItem(code.getShortCodeId());
			cbbShortCode.setItemCaption(code.getShortCodeId(), code.getCode());
		}
		// cbbShortCode.setNullSelectionAllowed(false);
//		cbbShortCode.setInputPrompt(TM.get("common.field_combobox.inputprompt",
//				shortCodeLb.getValue().toString().toLowerCase()));
		// cbbShortCode.setRequired(true);
		// cbbShortCode.setRequiredError(TM.get(
		// "common.field.msg.validator_nulloremty", shortCodeLb.getValue()
		// .toString()));
		cbbShortCode.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cbbService.setImmediate(true);
//		cbbService.setContainerDataSource(serviceData);
		for (EsmeServices service : arrService) {
			cbbService.addItem(service.getServiceId());
			cbbService.setItemCaption(service.getServiceId(), service.getName());
		}
		// cbbService.setNullSelectionAllowed(false);
//		cbbService.setInputPrompt(TM.get("common.field_combobox.inputprompt",
//				serviceLb.getValue().toString().toLowerCase()));
		// cbbService.setRequired(true);
		// cbbService.setRequiredError(TM.get(
		// "common.field.msg.validator_nulloremty", serviceLb.getValue()
		// .toString()));
		cbbService.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cbbCommand.setImmediate(true);
//		cbbCommand.setContainerDataSource(commandData);
		for (EsmeSmsCommand command : arrCommand) {
			cbbCommand.addItem(command.getCommandId());
			cbbCommand.setItemCaption(command.getCommandId(), command.getCode());
		}
		// cbbCommand.setNullSelectionAllowed(false);
//		cbbCommand.setInputPrompt(TM.get("common.field_combobox.inputprompt",
//				commandLb.getValue().toString().toLowerCase()));
		// cbbCommand.setRequired(true);
		// cbbCommand.setRequiredError(TM.get(
		// "common.field.msg.validator_nulloremty", commandLb.getValue()
		// .toString()));
		cbbCommand.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

	}

	private void initValidate() {
//		tfMsisdn.setRequired(true);
		tfMsisdn.setMaxLength(11);
		tfMsisdn.setRequiredError(TM.get("smslog.search.RequiredError",
				"Phone book"));
		String sms = TM
				.get("smslog.search.FormatNumber", "Phone book");
//		tfMsisdn.addValidator(new CustomRegexpValidator(
//				"^((01)|(1))[0-9]{9}|((09)|(9))[0-9]{8}$", sms));

		if (dtFromDate != null && dtToDate != null) {
			CompareDateTimeValidator val = new CompareDateTimeValidator(TM.get(
					"smslog.search.CompareDatetime", "To date",
					"From date"), dtFromDate, 2, true);
			val.setClearTime(true);
			dtToDate.addValidator(val);
		}

	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field field = null;
//		System.out.println("pro " + propertyId);
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
		} else {
			field = super.createField(item, propertyId, uiContext);
		}
		return field;
	}

	@Override
	public Object isValid(String property, Object currentFieldValue,
			Object otherObject) {
		return null;
	}
}
