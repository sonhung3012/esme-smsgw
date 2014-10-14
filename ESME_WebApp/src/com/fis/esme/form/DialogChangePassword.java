package com.fis.esme.form;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;

import com.fis.esme.admin.SessionData;
import com.fis.esme.classes.CustomRegexpValidator;
import com.fis.esme.classes.FieldsValidator;
import com.fis.esme.classes.FieldsValidatorInterface;
import com.fis.esme.classes.SpaceValidator;
import com.fis.esme.component.ConfirmDeletionDialog;
import com.fis.esme.util.LogUtil;
import com.fis.esme.util.MessageAlerter;
import com.fss.ddtp.DDTP;
import com.fss.util.AppException;
import com.fss.util.StringUtil;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.event.Action;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import eu.livotov.tpt.gui.dialogs.OptionDialog.OptionDialogResultListener;
import eu.livotov.tpt.gui.dialogs.OptionKind;
import eu.livotov.tpt.i18n.TM;

public class DialogChangePassword extends Window implements Action.Handler,
		OptionDialogResultListener {
	private VerticalLayout mainLayout;
	private Form form;
	private PasswordChangeFieldFactory fieldFactory;

	private HorizontalLayout pnlButton;
	private Button btnAccept;
	private Button btnClose;

	private ConfirmDeletionDialog confirm;

	private Action enterKeyAction = new ShortcutAction("Default key",
			ShortcutAction.KeyCode.ENTER, null);
	private Action escapeKeyAction = new ShortcutAction("Default key",
			ShortcutAction.KeyCode.ESCAPE, null);
	private Action[] actions = new Action[] { enterKeyAction, escapeKeyAction };

	public DialogChangePassword() {
		super(TM.get("cpass.dialog.caption"));
		this.setWidth("400px");
		this.setHeight("250px");
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

		LogUtil.logAccess(DialogChangePassword.class.getName());

	}

	private void init() {
		form = new Form();
		form.setItemDataSource(formItem());
		form.setWriteThrough(false);
		form.setInvalidCommitted(false);
		form.setImmediate(false);
		fieldFactory = new PasswordChangeFieldFactory();
		form.setFormFieldFactory(fieldFactory);

		Object[] visibleProperties = { "oldpassword", "newpassword",
				"cfmnewpassword" };
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

		pnlButton.addComponent(btnAccept);
		pnlButton.addComponent(btnClose);
		pnlButton.setWidth("150px");
		btnClose.setSizeFull();
		btnAccept.setSizeFull();
		pnlButton.setExpandRatio(btnAccept, 1.0f);
		pnlButton.setExpandRatio(btnClose, 1.0f);
		pnlButton.setSpacing(true);

		btnAccept.setClickShortcut(KeyCode.ENTER);

	}

	public boolean formIsValid() {
		removeAllValidatorFieldsInFrom();
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
		String OldPassword = null;
		String NewPassword = null;
		final String RealPassword;
		PasswordEntity pe = null;
		try {
			if (formIsValid()) {

				form.commit();

				BeanItem<PasswordEntity> bean = null;
				bean = (BeanItem<PasswordEntity>) form.getItemDataSource();
				pe = bean.getBean();

				// String username =
				// SessionData.getAppClient().getMainApplication().getUser().getUserName();

				OldPassword = pe.getOldpassword();
				NewPassword = pe.getNewpassword();
				RealPassword = pe.getCfmnewpassword();

				DDTP request = new DDTP();
				String strAlgorithm = StringUtil.nvl(SessionData.getAppClient()
						.getSessionValue("Encrypt.Algorithm"), "");
				if ((strAlgorithm != null)) {
					if ((!pe.getOldpassword().equals("")))
						pe.setOldpassword(StringUtil.encrypt(
								pe.getOldpassword(), strAlgorithm));
					if ((!pe.getNewpassword().equals("")))
						pe.setNewpassword(StringUtil.encrypt(
								pe.getNewpassword(), strAlgorithm));
				}

				request.setString("OldPassword", pe.getOldpassword());
				request.setString("NewPassword", pe.getNewpassword());
				request.setString("RealPassword", pe.getCfmnewpassword());

				DDTP response = SessionData.getAppClient().sendRequest(
						"PermissionBean", "changePassword", request);

				ObjectInputStream ois = null;
				try {
					byte[] array = response.getByteArray("$Exception$");
					if (array == null) {
						MessageAlerter.showMessageI18n(getApplication()
								.getMainWindow(), TM.get("cpass.msg.success"));
						onClose();
					} else {
						pe.setOldpassword(OldPassword);
						pe.setNewpassword(NewPassword);

						ois = new ObjectInputStream(new ByteArrayInputStream(
								array));
						AppException app = (AppException) ois.readObject();

						if (app.getInfo() != null) {
							MessageAlerter.showErrorMessageI18n(getWindow(),
									TM.get(app.getReason()), app.getInfo());
							form.getField("newpassword").focus();
							((PasswordField) form.getField("newpassword"))
									.selectAll();

							// oldPassValidator.setErrorMessage(TM.get(app.getReason(),
							// app.getInfo()));
							// form.getField("newpassword").addValidator(
							// oldPassValidator);
							// form.getField("newpassword").focus();
							// ((PasswordField) form.getField("newpassword"))
							// .selectAll();
							// form.setValidationVisible(true);
							//
						} else {
							MessageAlerter.showErrorMessageI18n(getWindow(),
									TM.get("FSS-00006_2"));
							form.getField("oldpassword").focus();
							((PasswordField) form.getField("oldpassword"))
									.selectAll();

							// oldPassValidator.setErrorMessage(app.getReason());
							// form.getField("newpassword").addValidator(
							// oldPassValidator);
							// form.getField("newpassword").focus();
							// ((PasswordField) form.getField("newpassword"))
							// .selectAll();
							// form.setValidationVisible(true);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					ois.close();
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	private AbstractValidator oldPassValidator = new AbstractValidator(null) {
		@Override
		public boolean isValid(Object value) {
			return false;
		}
	};

	private AbstractValidator newPassValidator = new AbstractValidator(null) {
		@Override
		public boolean isValid(Object value) {
			return false;
		}
	};

	private void removeAllValidatorFieldsInFrom() {
		try {
			form.getField("oldpassword").removeValidator(oldPassValidator);
			form.getField("newpassword").removeValidator(newPassValidator);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onClose() {
		if (form.isModified()) {
			if (confirm == null) {
				confirm = new ConfirmDeletionDialog(getApplication());
			}

			confirm.show(TM.get("form.dialog.confirm.save"), this);
		} else {
			form.discard();
			super.close();
		}
	}

	private Item formItem() {
		PasswordEntity pe = new PasswordEntity();
		pe.setNewpassword("");
		pe.setOldpassword("");
		pe.setCfmnewpassword("");
		Item item = new BeanItem<PasswordEntity>(pe);
		return item;
	}

	private class PasswordChangeFieldFactory extends DefaultFieldFactory
			implements FieldsValidatorInterface {
		private final String FIELD_WIDTH = "100%";
		private PasswordField txtOldPassword = new PasswordField(
				TM.get("cpass.field_oldpass.caption"));
		private PasswordField txtNewPassword = new PasswordField(
				TM.get("cpass.field_newpass.caption"));
		private PasswordField txtCfmNewPassword = new PasswordField(
				TM.get("cpass.field_cfmnewpass.caption"));

		public PasswordChangeFieldFactory() {
			initField();
		}

		// public void addValidatorForField(String property, AppException app)
		// {
		// if (property.equals("newpassword"))
		// {
		// String strMsgError = TM.get(app.getReason(), app.getInfo());
		// txtNewPassword.addValidator(new AbstractValidator(strMsgError)
		// {
		// @Override
		// public boolean isValid(Object value)
		// {
		// return false;
		// }
		// });
		// }
		// }
		//
		// public void removeValidatorForField(String property, AppException
		// app)
		// {
		// if (property.equals("newpassword"))
		// {
		//
		// }
		// }

		private void initField() {
			txtOldPassword.setMaxLength(50);
			txtOldPassword.setWidth(FIELD_WIDTH);
			// txtOldPassword.setRequired(true);
			// txtOldPassword.setRequiredError(TM.get(
			// "common.field.msg.validator_nulloremty",
			// txtOldPassword.getCaption()));
			// txtOldPassword.addValidator(new SpaceValidator(TM.get(
			// "common.field.msg.validator_nulloremty",
			// txtOldPassword.getCaption())));
			// txtOldPassword.addValidator(new CustomRegexpValidator(TM.get(
			// "service.field_code.regexp.validator_error",
			// txtOldPassword.getCaption()), true, TM.get(
			// "common.field_code.msg.validator_unicode",
			// txtOldPassword.getCaption()), true));

			txtNewPassword.setMaxLength(50);
			txtNewPassword.setWidth(FIELD_WIDTH);
			txtNewPassword.setRequired(true);
			txtNewPassword.setRequiredError(TM.get(
					"common.field.msg.validator_nulloremty",
					txtNewPassword.getCaption()));
			txtNewPassword.addValidator(new SpaceValidator(TM.get(
					"common.field.msg.validator_nulloremty",
					txtNewPassword.getCaption())));
			FieldsValidator fieldsValidator = new FieldsValidator(this,
					"newpassword", new Field[] { txtOldPassword });
			txtNewPassword.addValidator(fieldsValidator);
			txtNewPassword.addValidator(new CustomRegexpValidator(TM.get(
					"service.field_password.regexp.validator_error",
					txtNewPassword.getCaption()), true, TM.get(
					"service.msg.field_password.regexp.validator_error",
					txtNewPassword.getCaption()), true));

			txtCfmNewPassword.setMaxLength(50);
			txtCfmNewPassword.setWidth(FIELD_WIDTH);
			txtCfmNewPassword.setRequired(true);
			txtCfmNewPassword.setRequiredError(TM.get(
					"common.field.msg.validator_nulloremty",
					txtCfmNewPassword.getCaption()));
			txtCfmNewPassword.addValidator(new SpaceValidator(TM.get(
					"common.field.msg.validator_nulloremty",
					txtCfmNewPassword.getCaption())));
			fieldsValidator = new FieldsValidator(this, "cfmnewpassword",
					new Field[] { txtNewPassword });
			txtCfmNewPassword.addValidator(fieldsValidator);
			txtCfmNewPassword.addValidator(fieldsValidator);
			txtCfmNewPassword.addValidator(new CustomRegexpValidator(TM.get(
					"service.field_password.regexp.validator_error",
					txtCfmNewPassword.getCaption()), true, TM.get(
					"service.msg.field_password.regexp.validator_error",
					txtCfmNewPassword.getCaption()), true));
		}

		@Override
		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			if (propertyId.equals("oldpassword")) {
				return txtOldPassword;
			} else if (propertyId.equals("newpassword")) {
				return txtNewPassword;
			} else if (propertyId.equals("cfmnewpassword")) {
				return txtCfmNewPassword;
			} else {
				return super.createField(item, propertyId, uiContext);
			}
		}

		@Override
		public Object isValid(String property, Object currentFieldValue,
				Object otherObject) {
			Field[] fields = (Field[]) otherObject;

			if (property.equalsIgnoreCase("newpassword")) {
				String oldPassword = fields[0].getValue().toString();
				String newPassword = currentFieldValue.toString();

				if (newPassword.equals(oldPassword))
					return TM.get("cpass.compare.field.value_invalid",
							txtNewPassword.getCaption(),
							txtOldPassword.getCaption());
			}
			if (property.equalsIgnoreCase("cfmnewpassword")) {
				String newPassword = fields[0].getValue().toString();
				String cfmNewPassword = currentFieldValue.toString();

				if (!cfmNewPassword.equals(newPassword))
					return TM.get("cpass.field.value_invalid",
							txtCfmNewPassword.getCaption());
			}
			return null;
		}

	}

	public class PasswordEntity {
		private String oldpassword;
		private String newpassword;
		private String cfmnewpassword;

		public PasswordEntity() {
		}

		public PasswordEntity(String oldpassword, String newpassword,
				String ctmnewpassword) {
			this.oldpassword = oldpassword;
			this.newpassword = newpassword;
			this.cfmnewpassword = ctmnewpassword;
		}

		public String getOldpassword() {
			return oldpassword;
		}

		public void setOldpassword(String oldpassword) {
			this.oldpassword = oldpassword;
		}

		public String getNewpassword() {
			return newpassword;
		}

		public void setNewpassword(String newpassword) {
			this.newpassword = newpassword;
		}

		public String getCfmnewpassword() {
			return cfmnewpassword;
		}

		public void setCfmnewpassword(String cfmnewpassword) {
			this.cfmnewpassword = cfmnewpassword;
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
			escapeKeyPressed();
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
