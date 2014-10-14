package com.fis.esme.form;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.FieldsValidator;
import com.fis.esme.classes.FieldsValidatorInterface;
import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsRouting;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;

import eu.livotov.tpt.i18n.TM;

public class FormSmsRoutingFactory extends DefaultFieldFactory implements
		FieldsValidatorInterface {

	// private TextField txtService = new TextField(
	// TM.get("routing.field.services.caption"));
	private ComboBox cboServices = new ComboBox(
			TM.get("routing.field.services.caption"));
	private ComboBox cboCP = new ComboBox(TM.get("routing.field.cp.caption"));
	private ComboBox cboShortCode = new ComboBox(
			TM.get("routing.field.shortcode.caption"));
	private ComboBox cboCommand = new ComboBox(
			TM.get("routing.field.smscommand.caption"));
	private EsmeShortCode oldEsmeShortCode;
	private EsmeSmsCommand oldEsmeSmsCommand;

	private BeanItemContainer<EsmeServices> dataServices;
	private BeanItemContainer<EsmeCp> dataContainerCP;
	private BeanItemContainer<EsmeShortCode> dataContainerSortCode;
	private BeanItemContainer<EsmeSmsCommand> dataContainerCommand;

	// private EsmeServices treeService = null;

	public FormSmsRoutingFactory() {
	}

	public FormSmsRoutingFactory(BeanItemContainer<EsmeServices> dataServices,
			BeanItemContainer<EsmeCp> dataContainerCP,
			BeanItemContainer<EsmeShortCode> dataContainerSortCode,
			BeanItemContainer<EsmeSmsCommand> dataContainerCommand) {
		this.dataServices = dataServices;
		this.dataContainerCP = dataContainerCP;
		this.dataContainerSortCode = dataContainerSortCode;
		this.dataContainerCommand = dataContainerCommand;

		initService();
		initComboBox();
		initTextField();
		initTextArea();
		initDateField();
		initCommon();
		// setDataForComboBox();
	}

	private void initService() {
	}

	public EsmeShortCode getOldEsmeShortCode() {
		return oldEsmeShortCode;
	}

	public void setOldEsmeShortCode(EsmeShortCode oldEsmeShortCode) {
		this.oldEsmeShortCode = oldEsmeShortCode;
	}

	public EsmeSmsCommand getOldEsmeSmsCommand() {
		return oldEsmeSmsCommand;
	}

	public void setOldEsmeSmsCommand(EsmeSmsCommand oldEsmeSmsCommand) {
		this.oldEsmeSmsCommand = oldEsmeSmsCommand;
	}

	private void initComboBox() {

		cboServices.setImmediate(true);
		cboServices.setContainerDataSource(dataServices);
		cboServices.setNullSelectionAllowed(false);
		// cboServices.setInputPrompt(TM.get("common.field_combobox.inputprompt",
		// cboServices.getCaption().toLowerCase()));
		cboServices.setRequired(true);
		cboServices.setRequiredError(TM.get(
				"common.field.msg.validator_nulloremty", cboServices
						.getCaption().toString()));
		cboServices.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cboCP.setImmediate(true);
		cboCP.setContainerDataSource(dataContainerCP);
		cboCP.setNullSelectionAllowed(false);
		// cboCP.setInputPrompt(TM.get("common.field_combobox.inputprompt",
		// cboCP
		// .getCaption().toLowerCase()));
		cboCP.setRequired(true);
		cboCP.setRequiredError(TM.get("common.field.msg.validator_nulloremty",
				cboCP.getCaption().toString()));
		cboCP.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cboShortCode.setImmediate(true);
		cboShortCode.setContainerDataSource(dataContainerSortCode);
		cboShortCode.setNullSelectionAllowed(false);
		// cboShortCode.setInputPrompt(TM.get("common.field_combobox.inputprompt",
		// cboShortCode.getCaption().toLowerCase()));
		cboShortCode.setRequired(true);
		cboShortCode.setRequiredError(TM.get(
				"common.field.msg.validator_nulloremty", cboShortCode
						.getCaption().toString()));
		cboShortCode.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		cboCommand.setImmediate(true);
		cboCommand.setContainerDataSource(dataContainerCommand);
		cboCommand.setNullSelectionAllowed(false);
		// cboCommand.setInputPrompt(TM.get("common.field_combobox.inputprompt",
		// cboCommand.getCaption().toLowerCase()));
		cboCommand.setRequired(true);
		cboCommand.setRequiredError(TM.get(
				"common.field.msg.validator_nulloremty", cboCommand
						.getCaption().toString()));
		cboCommand.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		FieldsValidator fieldValicator = new FieldsValidator(this,
				"esmeSmsCommand", new Field[] { cboServices, cboCP,
						cboShortCode });
		cboCommand.addValidator(fieldValicator);

	}

	private void initTextField() {
		// txtService.setNullRepresentation("");
	}

	private void initTextArea() {
		// TODO Auto-generated method stub

	}

	private void initCommon() {
		cboServices.setWidth(TM.get("common.form.field.fixedwidth"));
		cboCP.setWidth(TM.get("common.form.field.fixedwidth"));
		cboCommand.setWidth(TM.get("common.form.field.fixedwidth"));
		cboShortCode.setWidth(TM.get("common.form.field.fixedwidth"));

		// txtMSISDN.setNullRepresentation("");
	}

	private void initDateField() {

	}

	// private void setDataForComboBox() {
	// if (CacheDB.cacheCP.size() <= 0) {
	// try {
	// CacheDB.cacheCP = CacheServiceClient.serviceCp
	// .findAllWithoutParameter();
	// Collections.sort(CacheDB.cacheCP,
	// FormUtil.stringComparator(true));
	// dataContainerCP.addAll(CacheDB.cacheCP);
	// } catch (Exception_Exception e) {
	// e.printStackTrace();
	// }
	// } else {
	// dataContainerCP.addAll(CacheDB.cacheCP);
	// }
	// // for (EsmeCp esmeCp : CacheDB.cacheCP) {
	// // cboCP.addItem(esmeCp.getCpId());
	// // cboCP.setItemCaption(esmeCp.getCpId(), esmeCp.getCode() + " - "
	// // + esmeCp.getDesciption());
	// // }
	//
	// if (CacheDB.cacheShortCode.size() <= 0) {
	// try {
	// CacheDB.cacheShortCode = CacheServiceClient.serviceShortCode
	// .findAllWithoutParameter();
	// Collections.sort(CacheDB.cacheShortCode,
	// FormUtil.stringComparator(true));
	// dataContainerSortCode.addAll(CacheDB.cacheShortCode);
	// } catch (com.fis.esme.shortcode.Exception_Exception e) {
	// e.printStackTrace();
	// }
	// } else {
	// dataContainerSortCode.addAll(CacheDB.cacheShortCode);
	// }
	//
	// if (CacheDB.cacheSmsCommand.size() <= 0) {
	// try {
	// CacheDB.cacheSmsCommand = CacheServiceClient.serviceSmsCommand
	// .findAllWithoutParameter();
	// Collections.sort(CacheDB.cacheSmsCommand,
	// FormUtil.stringComparator(true));
	// dataContainerCommand.addAll(CacheDB.cacheSmsCommand);
	// } catch (com.fis.esme.smscommand.Exception_Exception e) {
	// e.printStackTrace();
	// }
	// } else {
	// dataContainerCommand.addAll(CacheDB.cacheSmsCommand);
	// }
	// }

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		if (propertyId.equals("esmeCp")) {
			cboCP.focus();
			return cboCP;
		} else if (propertyId.equals("esmeShortCode")) {
			return cboShortCode;
		} else if (propertyId.equals("esmeSmsCommand")) {
			return cboCommand;
		} else if (propertyId.equals("esmeServices")) {
			// txtService.setValue(treeService.getName());
			// txtService.setReadOnly(true);
			return cboServices;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	// public EsmeServices getTreeService() {
	// return treeService;
	// }
	//
	// public void setTreeService(EsmeServices treeService) {
	// this.treeService = treeService;
	// }

	@Override
	public Object isValid(String property, Object currentFieldValue,
			Object otherObject) {
		try {

			Field[] fields = (Field[]) otherObject;
			// EsmeServices esmeServices = (EsmeServices) fields[0].getValue();
			// EsmeCp esmeCp = (EsmeCp) fields[1].getValue();
			EsmeShortCode esmeShortCode = (EsmeShortCode) fields[2].getValue();
			EsmeSmsCommand esmeSmsCommand = (EsmeSmsCommand) currentFieldValue;

			EsmeSmsRouting routing = new EsmeSmsRouting();
			// routing.setEsmeCp(esmeCp);
			// routing.setEsmeServices(esmeServices);
			routing.setEsmeShortCode(esmeShortCode);
			routing.setEsmeSmsCommand(esmeSmsCommand);

			if ((oldEsmeShortCode != null && esmeShortCode
					.equals(oldEsmeShortCode))
					&& (oldEsmeSmsCommand != null && esmeSmsCommand
							.equals(oldEsmeSmsCommand))) {
				return null;
			}

			if (CacheServiceClient.serviceSmsRouting
					.checkExisted(null, routing) > 0) {
				return TM.get("routing.fields.msg.validator_existed");
			}

			return null;
		} catch (Exception e) {
			return null;
		}
	}
}
