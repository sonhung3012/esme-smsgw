package com.fis.esme.form;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.CustomRegexpValidator;
import com.fis.esme.classes.PropertyExisted;
import com.fis.esme.classes.PropertyExistedValidator;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.language.LanguageTransferer;
import com.fis.esme.message.MessageTransferer;
import com.fis.esme.messagecontent.MessageContentTransferer;
import com.fis.esme.persistence.EsmeLanguage;
import com.fis.esme.persistence.EsmeMessage;
import com.fis.esme.persistence.EsmeMessageContent;
import com.fis.esme.util.FormUtil;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.DefaultItemSorter;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import eu.livotov.tpt.i18n.TM;

@SuppressWarnings("serial")
public class FormMessageFieldFactory extends DefaultFieldFactory implements PropertyExisted {

	private final TextField txtCode = new TextField(TM.get("message.field_code.caption"));
	private final TextField txtName = new TextField(TM.get("message.field_name.caption"));
	private final TextArea txtDescription = new TextArea(TM.get("message.field_description.caption"));
	private final TextArea txtMessage = new TextArea(TM.get("message.field_message.caption"));
	private ComboBox cbbLanguage = new ComboBox(TM.get("message.field_language.caption"));
	private final ComboBox cbbStatus = new ComboBox(TM.get("message.field_status.caption"));
	private DialogApParam dialogApParam;
	private AbstractComponent parent;
	private String strActive = "1";
	private String strInactive = "0";
	private String oldServiceCode = "";
	private String oldServiceName = "";
	private EsmeMessage oldMessage = null;
	private EsmeLanguage oldLanguage = null;

	public EsmeLanguage getOldLanguage() {

		return oldLanguage;
	}

	public void setOldLanguage(EsmeLanguage oldLanguage) {

		this.oldLanguage = oldLanguage;
	}

	private EsmeMessageContent messageContent = null;

	private MessageTransferer serviceMessage = null;
	private MessageContentTransferer serviceMessageContent = null;
	private LanguageTransferer serviceLanguage = null;

	private BeanItemContainer<EsmeLanguage> languageData = new BeanItemContainer<EsmeLanguage>(EsmeLanguage.class);

	public FormMessageFieldFactory(AbstractComponent parent) {

		this.parent = parent;
		initService();
		initComboBox();
		initTextField();
		initTextArea();
	}

	private void initService() {

		try {
			serviceMessage = CacheServiceClient.serviceMessage;
			serviceMessageContent = CacheServiceClient.serviceMessageContent;
			serviceLanguage = CacheServiceClient.serviceLanguage;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initComboBox() {

		if (languageData.size() <= 0) {
			try {
				languageData.addAll(serviceLanguage.findAll());
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		languageData.setItemSorter(new DefaultItemSorter(FormUtil.stringComparator(true)));
		languageData.sort(new Object[] { "name" }, new boolean[] { true });
		cbbLanguage.setImmediate(true);
		cbbLanguage.removeAllItems();
		cbbLanguage.removeAllValidators();
		cbbLanguage.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbLanguage.setContainerDataSource(languageData);
		cbbLanguage.setNullSelectionAllowed(false);
		cbbLanguage.setInputPrompt(TM.get("common.field_combobox.inputprompt_an", cbbLanguage.getCaption().toLowerCase()));
		cbbLanguage.setRequired(true);
		cbbLanguage.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbLanguage.getCaption()));
		cbbLanguage.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbLanguage.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				if (oldMessage != null) {

					EsmeMessage esmeMessage = oldMessage;
					EsmeLanguage esmeLanguage = (EsmeLanguage) cbbLanguage.getValue();

					try {
						messageContent = serviceMessageContent.findByMessageIdAndLanguageId(esmeMessage.getMessageId(), esmeLanguage.getLanguageId());
					} catch (Exception e) {

						e.printStackTrace();
					}
					if (messageContent != null) {

						getTxtMessage().setValue(messageContent.getMessage());
					} else {
						getTxtMessage().setValue("");
					}

				}
			}
		});

		cbbLanguage.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {

				if (value instanceof EsmeLanguage) {

					EsmeLanguage language = (EsmeLanguage) value;
					if (language.getStatus().equals("0")) {

						throw new InvalidValueException(TM.get("message.language.inactive.error"));
					}
				}
			}

			@Override
			public boolean isValid(Object value) {

				if (value instanceof EsmeLanguage) {

					EsmeLanguage language = (EsmeLanguage) value;
					if (language.getStatus().equals("0")) {

						return false;
					}
				}
				return true;
			}
		});

		cbbStatus.removeAllItems();
		cbbStatus.setWidth(TM.get("common.form.field.fixedwidth"));
		cbbStatus.addItem(strActive);
		cbbStatus.addItem(strInactive);
		cbbStatus.setItemCaption(strActive, TM.get("message.field_combobox.value_active"));
		cbbStatus.setItemCaption(strInactive, TM.get("message.field_combobox.value_inactive"));
		cbbStatus.setNullSelectionAllowed(false);
		cbbStatus.setRequired(true);
		cbbStatus.setRequiredError(TM.get("common.field.msg.validator_nulloremty", cbbStatus.getCaption()));
		cbbStatus.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cbbStatus.setEnabled(false);
	}

	private void initTextField() {

		txtCode.setMaxLength(50);
		txtCode.setWidth(TM.get("common.form.field.fixedwidth"));
		String errorCodeMsg = TM.get("common.field_code.msg.validator_unicode", txtCode.getCaption());
		String nullCodeMsg = TM.get("common.field.msg.validator_nulloremty", txtCode.getCaption());
		txtCode.setNullRepresentation("");
		txtCode.setRequired(true);
		txtCode.setRequiredError(nullCodeMsg);
		SpaceValidator emptyCode = new SpaceValidator(nullCodeMsg);
		txtCode.addValidator(emptyCode);
		txtCode.addValidator(new CustomRegexpValidator(TM.get("message.field_code.regexp.validator_error", txtCode.getCaption()), true, errorCodeMsg, true));
		PropertyExistedValidator fieldCodeExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtCode.getCaption()), this, "code");
		txtCode.addValidator(fieldCodeExistedValicator);

		txtName.setMaxLength(40);
		txtName.setWidth(TM.get("common.form.field.fixedwidth"));
		String nullNameMsg = TM.get("common.field.msg.validator_nulloremty", txtName.getCaption());
		txtName.setNullRepresentation("");
		txtName.setRequired(true);
		txtName.setRequiredError(nullNameMsg);
		SpaceValidator empty = new SpaceValidator(nullNameMsg);
		txtName.addValidator(empty);
		PropertyExistedValidator fieldNameExistedValicator = new PropertyExistedValidator(TM.get("common.field.msg.validator_existed", txtName.getCaption()), this, "name");
		txtName.addValidator(fieldNameExistedValicator);

	}

	private void initTextArea() {

		getTxtMessage().setMaxLength(500);
		getTxtMessage().setWidth(TM.get("common.form.field.fixedwidth"));
		String nullmessgeMsg = TM.get("common.field.msg.validator_nulloremty", getTxtMessage().getCaption());
		getTxtMessage().setRequired(true);
		getTxtMessage().setRequiredError(nullmessgeMsg);
		SpaceValidator empty = new SpaceValidator(nullmessgeMsg);
		getTxtMessage().addValidator(empty);
		getTxtMessage().setNullRepresentation("");

		final ShortcutListener shortCutParam = new ShortcutListener("", KeyCode.F9, null) {

			@Override
			public void handleAction(Object sender, Object target) {

				dialogApParam = new DialogApParam(TM.get("message.dialogApparam.visibleColumnsHeader"), FormMessageFieldFactory.this);
				dialogApParam.show(parent.getApplication());
			}
		};

		getTxtMessage().addListener(new FieldEvents.FocusListener() {

			@Override
			public void focus(FocusEvent event) {

				getTxtMessage().addShortcutListener(shortCutParam);
			}
		});

		getTxtMessage().addListener(new FieldEvents.BlurListener() {

			@Override
			public void blur(BlurEvent event) {

				getTxtMessage().removeShortcutListener(shortCutParam);
			}
		});

		txtDescription.setWidth(TM.get("common.form.field.fixedwidth"));
		txtDescription.setHeight("50px");
		txtDescription.setMaxLength(70);
		txtDescription.setNullRepresentation("");
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {

		if (propertyId.equals("status")) {
			return cbbStatus;
		} else if (propertyId.equals("desciption")) {
			return txtDescription;
		} else if (propertyId.equals("message")) {
			return getTxtMessage();
		} else if (propertyId.equals("esmeLanguage")) {
			return cbbLanguage;
		} else if (propertyId.equals("code")) {
			txtCode.selectAll();
			return txtCode;
		} else if (propertyId.equals("name")) {
			return txtName;
		} else {
			return super.createField(item, propertyId, uiContext);
		}
	}

	public void setOldCode(String code) {

		this.oldServiceCode = code;
	}

	public void setOldName(String name) {

		this.oldServiceName = name;
	}

	public EsmeMessage getOldMessage() {

		return oldMessage;
	}

	public void setOldMessage(EsmeMessage oldMessage) {

		this.oldMessage = oldMessage;
	}

	@Override
	public boolean isPropertyExisted(String property, Object value) {

		String val = value.toString().trim().toUpperCase();
		if (property.equals("code")) {
			if (value.toString().equalsIgnoreCase(oldServiceCode)) {
				return true;
			} else {

				EsmeMessage ser = new EsmeMessage();
				ser.setCode(val);

				if (serviceMessage.checkExisted(ser) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		} else if (property.equals("name")) {
			if (value.toString().equalsIgnoreCase(oldServiceName)) {
				return true;
			} else {

				EsmeMessage ser = new EsmeMessage();
				ser.setName(val);

				if (serviceMessage.checkExisted(ser) >= 1) {
					return false;
				} else {
					return true;
				}
			}
		}
		return true;
	}

	public void setEnabledCboStatus(boolean vblEnabled) {

		cbbStatus.setEnabled(vblEnabled);
	}

	public TextArea getTxtMessage() {

		return txtMessage;
	}
}