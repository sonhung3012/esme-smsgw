package com.fis.esme.form.feedbackSearch;

import java.util.ArrayList;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.CompareDateTimeValidator;
import com.fis.esme.classes.FieldsValidatorInterface;
import com.fis.esme.emsmo.EmsMoTransferer;
import com.fis.esme.persistence.EsmeGroups;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.util.FormUtil;
import com.vaadin.data.Item;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

public class SearchFormFeedbackFieldFactory extends DefaultFieldFactory implements FieldsValidatorInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String FIElD_WIDTH = "150px";
	private TextField tfMsisdn;
	private PopupDateField dtFromDate;
	private PopupDateField dtToDate;
	private ComboBox cbbShortCode;
	private TextField tfMessage;
	private TextField tfFeedback;
	private ComboBox cbbStatus;
	private ComboBox cbbGroup;

	private ArrayList<EsmeShortCode> arrShortCode = new ArrayList<EsmeShortCode>();
	private ArrayList<EsmeGroups> arrGroup = new ArrayList<EsmeGroups>();

	private EmsMoTransferer emsmoService = CacheServiceClient.emsMoService;;

	public SearchFormFeedbackFieldFactory() throws Exception {

		setDataForComboBox();
		initComponent();
		initValidate();
		sameWidth();
	}

	private void setDataForComboBox() throws Exception {

		arrShortCode.addAll(CacheServiceClient.serviceShortCode.findAllWithOrderPaging(null, null, false, -1, -1, true));
		arrGroup.addAll(CacheServiceClient.GroupsService.findAllWithOrderPaging(null, null, false, -1, -1, true));

	}

	public void focusFirstField() {

		tfMsisdn.focus();
		tfMsisdn.selectAll();
	}

	private void sameWidth() {

		dtFromDate.setWidth(FIElD_WIDTH);
		dtToDate.setWidth(FIElD_WIDTH);
		tfMsisdn.setWidth(FIElD_WIDTH);
		tfFeedback.setWidth(FIElD_WIDTH);
		cbbShortCode.setWidth(FIElD_WIDTH);
		tfMessage.setWidth(FIElD_WIDTH);
		cbbGroup.setWidth(FIElD_WIDTH);
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
		dtFromDate.setParseErrorMessage(TM.get("promdetail.setParseErrorMessage", TM.get("emsmt.formSearch.field.fromDate.caption"), dtFromDate.getDateFormat().toUpperCase()));
		dtFromDate.setRequired(true);
		dtFromDate.setRequiredError(TM.get("smslog.search.RequiredError", TM.get("emsmt.formSearch.field.fromDate.caption")));
		dtFromDate.setResolution(PopupDateField.RESOLUTION_DAY);

		dtToDate = new PopupDateField();
		dtToDate.setDateFormat(FormUtil.stringShortDateFormat);
		dtToDate.setParseErrorMessage(TM.get("promdetail.setParseErrorMessage", TM.get("emsmt.formSearch.field.toDate.caption"), dtToDate.getDateFormat().toUpperCase()));
		dtToDate.setRequired(true);
		dtToDate.setRequiredError(TM.get("smslog.search.RequiredError", TM.get("emsmt.formSearch.field.toDate.caption")));
		dtToDate.setResolution(PopupDateField.RESOLUTION_DAY);

		cbbShortCode = new ComboBox();
		cbbShortCode.setImmediate(true);
		cbbShortCode.removeAllValidators();
		cbbShortCode.removeAllItems();
		// cbbShortCode.setContainerDataSource(shortCodeData);
		for (EsmeShortCode code : arrShortCode) {
			cbbShortCode.addItem(code);
			cbbShortCode.setItemCaption(code, code.getCode());
		}
		cbbShortCode.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		tfMessage = new TextField();
		tfMessage.setNullRepresentation("");

		tfFeedback = new TextField();
		tfFeedback.setNullRepresentation("");

		cbbGroup = new ComboBox();
		cbbGroup.setImmediate(true);
		cbbGroup.removeAllValidators();
		cbbGroup.removeAllItems();
		for (EsmeGroups group : arrGroup) {
			cbbGroup.addItem(group);
			cbbGroup.setItemCaption(group, group.getName());
		}
		cbbGroup.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cbbStatus = new ComboBox();
		cbbStatus.setImmediate(true);
		cbbStatus.removeAllItems();
		cbbStatus.addItem("0");
		cbbStatus.addItem("1");
		cbbStatus.addItem("2");
		cbbStatus.addItem("3");
		cbbStatus.addItem("6");
		cbbStatus.addItem("7");
		cbbStatus.addItem("9");
		cbbStatus.setItemCaption("0", TM.get("emsmt.table.mtStatus.insert_new"));
		cbbStatus.setItemCaption("1", TM.get("emsmt.table.mtStatus.send_success"));
		cbbStatus.setItemCaption("2", TM.get("emsmt.table.mtStatus.fail_and_retry"));
		cbbStatus.setItemCaption("3", TM.get("emsmt.table.mtStatus.user_dont_receiver"));
		cbbStatus.setItemCaption("6", TM.get("emsmt.table.mtStatus.fail_validate_info"));
		cbbStatus.setItemCaption("7", TM.get("emsmt.table.mtStatus.waiting_approve"));
		cbbStatus.setItemCaption("9", TM.get("emsmt.table.mtStatus.loaded"));

		cbbStatus.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

	}

	private void initValidate() {

		// tfMsisdn.setRequired(true);
		tfMsisdn.setMaxLength(11);
		tfMsisdn.setRequiredError(TM.get("smslog.search.RequiredError", TM.get("emsmt.formSearch.field.msisdn.caption")));
		String sms = TM.get("smslog.search.FormatNumber", TM.get("emsmt.formSearch.field.msisdn.caption"));
		// tfMsisdn.addValidator(new CustomRegexpValidator(
		// "^((01)|(1))[0-9]{9}|((09)|(9))[0-9]{8}$", sms));

		if (dtFromDate != null && dtToDate != null) {
			CompareDateTimeValidator val = new CompareDateTimeValidator(TM.get("emsmt.search.CompareDatetime", TM.get("emsmt.formSearch.field.toDate.caption"),
			        TM.get("emsmt.formSearch.field.fromDate.caption")), dtFromDate, 2, true);
			val.setClearTime(true);
			dtToDate.addValidator(val);
		}

	}

	public void refreshAllField() {

		tfMsisdn.setValue(null);
		dtFromDate.setValue(null);
		dtToDate.setValue(null);
		cbbShortCode.setValue(null);
		tfMessage.setValue(null);
		tfFeedback.setValue(null);
		cbbStatus.setValue(null);
		cbbGroup.setValue(null);

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
		} else if ("tfMessage".equals(propertyId)) {
			return tfMessage;
		} else if ("tfFeedback".equals(propertyId)) {
			return tfFeedback;
		} else if ("cbbStatus".equals(propertyId)) {
			return cbbStatus;
		} else if ("cbbGroup".equals(propertyId)) {
			return cbbGroup;
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
