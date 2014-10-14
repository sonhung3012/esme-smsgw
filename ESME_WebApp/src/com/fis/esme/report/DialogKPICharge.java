package com.fis.esme.report;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.fis.esme.admin.SessionData;
import com.fis.esme.classes.CompareDateTimeValidator;
import com.fis.esme.client.SmsLogTransfererClient;
import com.fis.esme.component.ConfirmDeletionDialog;
import com.fis.esme.persistence.EsmeSmsLog;
import com.fis.esme.smslog.EsmeSmsLogTransferer;
import com.fis.esme.util.Dictionary;
import com.fis.esme.util.FileDownloadResource;
import com.fis.esme.util.FormUtil;
import com.fis.esme.util.LogUtil;
import com.fis.esme.util.MessageAlerter;
import com.fis.esme.util.ReportUtil;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.Action;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import eu.livotov.tpt.gui.dialogs.OptionDialog.OptionDialogResultListener;
import eu.livotov.tpt.gui.dialogs.OptionKind;
import eu.livotov.tpt.i18n.TM;

public class DialogKPICharge extends Window implements Action.Handler,
		OptionDialogResultListener {
	private VerticalLayout mainLayout;
	private Form form;
	private searchFormFieldFactory fieldFactory;

	private HorizontalLayout pnlButton;
	private Button btnAccept;
	private Button btnClose;

	private ConfirmDeletionDialog confirm;

	private Action enterKeyAction = new ShortcutAction("Default key",
			ShortcutAction.KeyCode.ENTER, null);
	private Action escapeKeyAction = new ShortcutAction("Default key",
			ShortcutAction.KeyCode.ESCAPE, null);
	private Action[] actions = new Action[] { enterKeyAction, escapeKeyAction };

	public DialogKPICharge() {
		super(TM.get(DialogKPICharge.class.getName()));
		LogUtil.logAccess(DialogKPICharge.class.getName());
		this.setWidth("400px");
		this.setHeight("235px");
		this.setModal(true);
		this.setResizable(false);
		this.center();
		this.setCloseShortcut(KeyCode.ESCAPE);
		init();

		mainLayout = (VerticalLayout) getContent();// new VerticalLayout();
		mainLayout.setMargin(false, true, true, true);
		mainLayout.setSizeFull();
		mainLayout.addComponent(form);
		mainLayout.addComponent(pnlButton);
		mainLayout.setComponentAlignment(pnlButton, Alignment.BOTTOM_CENTER);
		mainLayout.setExpandRatio(form, 1f);
		// this.addComponent(mainLayout);
	}

	private void init() {
		form = new Form();
		form.setItemDataSource(formItem());
		form.setWriteThrough(false);
		form.setInvalidCommitted(false);
		form.setImmediate(false);
		fieldFactory = new searchFormFieldFactory();
		form.setFormFieldFactory(fieldFactory);

		Object[] visibleProperties = { "fromDate", "toDate" };

		form.setVisibleItemProperties(visibleProperties);
		form.setValidationVisible(false);
		form.focus();
		form.setWidth("100%");

		pnlButton = new HorizontalLayout();
		btnAccept = new Button(TM.get("form.dialog.button.caption_accept"));
		btnAccept.addListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				onAccept();
			}
		});
		btnClose = new Button(TM.get("form.dialog.button.caption_cancel"));
		btnClose.addListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				onClose();
			}
		});

		btnAccept.setCaption(TM.get("report.btn.export.caption"));
		pnlButton.addComponent(btnAccept);
		pnlButton.addComponent(btnClose);

		btnClose.setSizeFull();
		btnAccept.setSizeFull();
		pnlButton.setExpandRatio(btnAccept, 1.0f);
		pnlButton.setExpandRatio(btnClose, 1.0f);
		pnlButton.setSpacing(true);

		btnAccept.setClickShortcut(KeyCode.ENTER);

	}

	public boolean formIsValid() {
		// removeAllValidatorFieldsInFrom();
		boolean valid = true;
		for (final Iterator<?> i = form.getItemPropertyIds().iterator(); i
				.hasNext();) {
			Field field = form.getField(i.next());

			if (field instanceof Table) {
				return true;
			}

			if (!field.isValid()) {
				field.focus();
				if (field instanceof AbstractTextField) {
					((AbstractTextField) field).selectAll();
				}
				form.setValidationVisible(true);
				return false;
			}
		}
		return valid;
	}

	private void onAccept() {
		try {
			if (formIsValid()) {
				form.commit();

				BeanItem<Searcher> beanItem = (BeanItem<Searcher>) form
						.getItemDataSource();
				Searcher searcher = beanItem.getBean();

				EsmeSmsLogTransferer smsService = SmsLogTransfererClient
						.getService();

				searcher.setFromDate(FormUtil.toDate(searcher.getFromDate(),
						FormUtil.notSetDateFields));
				searcher.setToDate(FormUtil.toDate(searcher.getToDate(),
						new Dictionary[] {
								new Dictionary(Calendar.HOUR_OF_DAY, 23),
								new Dictionary(Calendar.MINUTE, 59),
								new Dictionary(Calendar.SECOND, 59) }));

				String[] columns = new String[] { "STT", "requestTime",
						"commandId", "totalSms" };

				List<EsmeSmsLog> list = smsService.lookUpInfo("930000051",
						searcher.getFromDate(), searcher.getToDate(), null,
						null, null);

				Vector<Vector<Object>> vData = new Vector<Vector<Object>>();
				Vector<Object> obj = null;
				int stt = 1;
				for (EsmeSmsLog smsLog : list) {
					obj = new Vector<Object>();
					obj.add(stt);
					obj.add((smsLog.getRequestTime() != null) ? FormUtil.simpleShortDateFormat
							.format(smsLog.getRequestTime()) : "");
					obj.add((smsLog.getEsmeSmsCommand().getCommandId() != 0) ? smsLog
							.getEsmeSmsCommand().getCommandId() : 0);
					obj.add((smsLog.getTotalSms() != null) ? smsLog
							.getTotalSms() : "");
					vData.add(obj);
					stt++;
				}

				String absolutePath = getApplication().getContext()
						.getBaseDirectory().getAbsolutePath();

				ArrayList<String[]> parameters = new ArrayList<String[]>();
				Calendar calr = Calendar.getInstance();
				parameters.add(new String[] {
						"$Report_FromDate",
						FormUtil.simpleShortDateFormat.format(searcher
								.getFromDate()) });
				parameters.add(new String[] {
						"$Report_ToDate",
						FormUtil.simpleShortDateFormat.format(searcher
								.getToDate()) });
				parameters.add(new String[] { "$ExDay",
						calr.get(Calendar.DATE) + "" });
				parameters.add(new String[] { "$ExMonth",
						(calr.get(Calendar.MONTH) + 1) + "" });
				parameters.add(new String[] { "$ExYear",
						calr.get(Calendar.YEAR) + "" });
				parameters.add(new String[] { "$ReportUser",
						SessionData.getUserName() });

				Object rs = ReportUtil.doExportData(absolutePath, "DoanhThuNapThe",
						null, parameters, vData, columns,
						FormUtil.stringShortDateFormat);

				if (rs instanceof String) {
					getApplication().getMainWindow().open(
							new FileDownloadResource(new File(rs.toString()),
									getApplication()));
					MessageAlerter.showMessageI18n(getApplication()
							.getMainWindow(), TM
							.get("common.report.msg.export.success"));
					close();
				} else {
					if ((Integer) rs == 0) {
						MessageAlerter.showErrorMessageI18n(getApplication()
								.getMainWindow(), TM
								.get("common.report.msg.export.emty"));
					} else if ((Integer) rs == -1) {
						MessageAlerter.showErrorMessageI18n(getApplication()
								.getMainWindow(), TM
								.get("common.report.msg.export.fail"));
					}
				}

			}
		} catch (Exception e) {
			MessageAlerter.showErrorMessageI18n(getWindow(), e.getMessage());
			e.printStackTrace();
		}

	}

	private void onClose() {

		form.discard();
		super.close();
	}

	private Item formItem() {
		Searcher search = new Searcher();
		search.setFromDate(FormUtil.getToday(FormUtil.notSetDateFields));
		search.setToDate(FormUtil.getToday(FormUtil.notSetDateFields));
		Item item = new BeanItem<Searcher>(search);
		return item;
	}

	private class searchFormFieldFactory extends DefaultFieldFactory {
		private final String FIElD_WIDTH = "250";
		private PopupDateField dfFromDate = new PopupDateField(
				TM.get("common.report.search.field.fromdate"));
		private PopupDateField dfToDate = new PopupDateField(
				TM.get("common.report.search.field.todate"));

		public searchFormFieldFactory() {
			initComponent();
		}

		private void initComponent() {
			dfFromDate.setWidth(FIElD_WIDTH);
			dfFromDate.setDateFormat(FormUtil.stringShortDateFormat);
			dfFromDate.setRequired(true);
			dfFromDate.setRequiredError(TM.get(
					"common.field.msg.validator_nulloremty",
					dfFromDate.getCaption()));
			dfFromDate.setParseErrorMessage(TM.get(
					"main.common.validate.date.format",
					dfFromDate.getCaption(), dfFromDate.getDateFormat()
							.toUpperCase()));
			dfFromDate.setResolution(DateField.RESOLUTION_DAY);

			dfToDate.setWidth(FIElD_WIDTH);
			dfToDate.setDateFormat(FormUtil.stringShortDateFormat);
			dfToDate.setRequired(true);
			dfToDate.setRequiredError(TM.get(
					"common.field.msg.validator_nulloremty",
					dfToDate.getCaption()));
			dfToDate.setParseErrorMessage(TM.get(
					"main.common.validate.date.format", dfToDate.getCaption(),
					dfToDate.getDateFormat().toUpperCase()));

			// dfToDate.addValidator(new
			// CompareDateTimeValidator(TM.get("common.report.validate.msg.largerSmaller"),
			// dfFromDate, FormUtil.notSetDateFields,Calendar.MONTH,0));

			dfToDate.addValidator(new CompareDateTimeValidator(TM.get(
					"main.sub.common.compare.date", dfToDate.getCaption(),
					dfFromDate.getCaption()), dfFromDate, 2,
					FormUtil.notSetDateFields));
			dfToDate.setResolution(DateField.RESOLUTION_DAY);
		}

		@Override
		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			Field field = null;
			if ("fromDate".equals(propertyId)) {
				return dfFromDate;
			} else if ("toDate".equals(propertyId)) {
				return dfToDate;
			} else {
				field = super.createField(item, propertyId, uiContext);
			}
			return field;
		}
	}

	protected class Searcher {
		private Date fromDate;
		private Date toDate;

		public Searcher(Date fromDate, Date toDate) {
			this.fromDate = fromDate;
			this.toDate = toDate;
		}

		public Searcher() {
			// TODO Auto-generated constructor stub
		}

		public Date getFromDate() {
			return fromDate;
		}

		public void setFromDate(Date fromDate) {
			this.fromDate = fromDate;
		}

		public Date getToDate() {
			return toDate;
		}

		public void setToDate(Date toDate) {
			this.toDate = toDate;
		}
	}

	@Override
	public Action[] getActions(Object target, Object sender) {
		return actions;
	}

	@Override
	public void handleAction(Action action, Object sender, Object target) {
		if (action == enterKeyAction) {
			enterKeyPressed();
		}

		if (action == escapeKeyAction) {
			// escapeKeyPressed();
		}
	}

	/**
	 * Called when user presses an ENTER key
	 */
	public void enterKeyPressed() {
		onAccept();
	}

	/**
	 * Called when user presses an ESC key
	 */
	public void escapeKeyPressed() {
		onClose();
	}

	protected void close() {
		onClose();
	}

	@Override
	public void dialogClosed(OptionKind choice) {
		if (OptionKind.OK.equals(choice)) {
			onAccept();
		} else if (OptionKind.CANCEL.equals(choice)) {
			form.discard();
			super.close();
		}
	}
}
