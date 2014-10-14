package com.fis.esme;

import java.util.Locale;
import java.util.Vector;

import com.fis.esme.admin.SessionData;
import com.fis.esme.util.FormUtil;
import com.fis.esme.util.LogUtil;
import com.fis.esme.util.MessageAlerter;
import com.fss.ddtp.DDTP;
import com.fss.util.AppException;
import com.fss.util.StringUtil;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import de.steinwedel.vaadin.MessageBox;
import de.steinwedel.vaadin.MessageBox.ButtonType;
import eu.livotov.tpt.i18n.TM;

public class CustomLogin extends VerticalLayout {
	private TextField username;
	private PasswordField password;
	private MainApplication application;
	private ComboBox language;

	private Button btnLogin = new Button();
	private Embedded mcasystemEmb = new Embedded();
	private Embedded usernameEmb = new Embedded();
	private Embedded passwordEmb = new Embedded();
	private Embedded copyrightEmbt = new Embedded();

	public CustomLogin(MainApplication app) {

		this.application = app;
		setMargin(true);
		setSizeFull();

		language = new ComboBox("");
		language.setWidth("121px");

		Locale vi_VN = new Locale("vi", "VN");
		language.addItem(vi_VN);
		language.setItemCaption(vi_VN, TM.get("language.caption.vi"));
		Locale en_US = new Locale("en", "EN");
		language.addItem(en_US);
		language.setItemCaption(en_US, TM.get("language.caption.en"));

		language.setNullSelectionAllowed(false);
		language.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		getCurrentLocate();

		language.setImmediate(true);
		language.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (language.getValue() != null) {
					setLableByLocate((Locale) language.getValue());
				}
			}
		});

		CustomLayout custom = new CustomLayout("login");
		custom.setSizeFull();
		addComponent(custom);
		setStyleName("loginVLayoutStyle");

		// custom.addComponent(language, "changelanguage");

		CssLayout vmcasystem = new CssLayout();
		vmcasystem.setWidth("284px");
		vmcasystem.setHeight("185px");
		vmcasystem.addComponent(mcasystemEmb);
		custom.addComponent(vmcasystem, "lblMCAsystem");

		CssLayout vusername = new CssLayout();
		vusername.setWidth("134px");
		vusername.setHeight("32px");
		vusername.addComponent(usernameEmb);
		custom.addComponent(vusername, "lblUsername");

		CssLayout vpassword = new CssLayout();
		vpassword.setWidth("134px");
		vpassword.setHeight("32px");
		vpassword.addComponent(passwordEmb);
		custom.addComponent(vpassword, "lblPassword");

		CssLayout vcopyright = new CssLayout();
		vcopyright.setWidth("651px");
		vcopyright.setHeight("54px");
		vcopyright.addComponent(copyrightEmbt);
		custom.addComponent(vcopyright, "lblCopyright");

		username = new TextField();
		username.setValue("admin");
		username.focus();
		username.setMaxLength(50);
		// username.setHeight("19px");
		// username.setWidth("115px");
		username.setStyleName("txtLoginField");
		custom.addComponent(username, "username");

		password = new PasswordField();
//		password.setValue("");
		password.setMaxLength(50);
		// password.setHeight("19px");
		// password.setWidth("115px");
		password.setStyleName("txtLoginField");
		custom.addComponent(password, "password");

		btnLogin.setStyleName(BaseTheme.BUTTON_LINK);
		btnLogin.setWidth("150px");
		btnLogin.setHeight("32px");
		custom.addComponent(btnLogin, "okbutton");
		btnLogin.setClickShortcut(KeyCode.ENTER);
		btnLogin.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				final String user = username.getValue().toString().trim();
				final String pass = (String) password.getValue();
				loginWindow(user, pass);
			}
		});

	}

	private Locale getCurrentLocate() {
		Locale locale = application.getLocale();
		setLableByLocate(locale);
		language.select(locale);
		return locale;
	}

	private void setLableByLocate(Locale locate) {
		application.setLocale(locate);
		// application.reloadResource();
		if (locate.toString().equals("vi_VN")) {
			mcasystemEmb.setIcon(new ThemeResource("images/vi_login_01.jpg"));
			usernameEmb
					.setIcon(new ThemeResource("images/vi_login_01_2_04.jpg"));
			passwordEmb
					.setIcon(new ThemeResource("images/vi_login_01_2_06.jpg"));
			copyrightEmbt.setIcon(new ThemeResource("images/vi_login_13.jpg"));
			btnLogin.setIcon(new ThemeResource("images/vi_login_11.jpg"));
		} else if (locate.toString().equals("en_EN")) {
			mcasystemEmb.setIcon(new ThemeResource("images/vi_login_01.jpg"));
			usernameEmb
					.setIcon(new ThemeResource("images/vi_login_01_2_04.jpg"));
			passwordEmb
					.setIcon(new ThemeResource("images/vi_login_01_2_06.jpg"));
			copyrightEmbt.setIcon(new ThemeResource("images/vi_login_13.jpg"));
			btnLogin.setIcon(new ThemeResource("images/vi_login_11.jpg"));
			
		}
	}

	public boolean login(final String strUserName, String strPassword) {
		try {
			final DDTP request = new DDTP();
			String strAlgorithm = StringUtil.nvl(SessionData.getAppClient()
					.getSessionValue("Encrypt.Algorithm"), "");

			if (!"".equals(strPassword) && !strAlgorithm.equals("")) {
				strPassword = StringUtil.encrypt(strPassword, strAlgorithm);
			}

			final String password = (strPassword == null ? "" : strPassword);
			request.setString("strIPAddress", SessionData.getAppClient()
					.getIpRemoteAddr());
			request.setString("strUserName", strUserName);
			request.setString("strPassword", strPassword);
			DDTP response = SessionData.getAppClient().sendRequest(
					"PermissionBean", "login", request);

			String nouser = response.getString("$Exception$");

			if (nouser != null && nouser.contains("FSS-00016")) {
				throw new AppException("FSS-00016", "CustomLogin");
			}

			if (response.getString("MaxSessionExceeded") != null) {
				MessageBox mb = new MessageBox(getWindow(),
						TM.get("message.msg.system"), MessageBox.Icon.QUESTION,
						TM.get("message.msg.MaxSessionExceeded"),
						new MessageBox.ButtonConfig(MessageBox.ButtonType.YES,
								TM.get("form.dialog.confirm.yes")),
						new MessageBox.ButtonConfig(MessageBox.ButtonType.NO,
								TM.get("form.dialog.confirm.no")));
				mb.show(new MessageBox.EventListener() {

					@Override
					public void buttonClicked(ButtonType buttonType) {
						if (ButtonType.YES == buttonType) {
							try {
								request.setString("DestroyIfExceed", "TRUE");
								DDTP response = SessionData.getAppClient()
										.sendRequest("PermissionBean", "login",
												request);
								loginWindow(strUserName, password);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					}
				});
				// throw new AppException("FSS-00019", "CustomLogin");
				return false;
			}

			if (response.getString("PasswordExpired") != null) {

				// final FormChangePassword changePassword = new
				// FormChangePassword();
				// changePassword.setCustomLogin(this);
				// changePassword.setUserName(strUserName);
				//
				// MessageBox mb = new MessageBox(getWindow(),
				// TM.get("message.msg.system"), MessageBox.Icon.QUESTION,
				// TM.get("message.msg.PasswordExpired"),
				// new MessageBox.ButtonConfig(MessageBox.ButtonType.YES,
				// TM.get("form.dialog.confirm.yes")),
				// new MessageBox.ButtonConfig(MessageBox.ButtonType.NO,
				// TM.get("form.dialog.confirm.no")));
				// mb.show(new MessageBox.EventListener()
				// {
				//
				// @Override
				// public void buttonClicked(ButtonType buttonType)
				// {
				// if (ButtonType.YES == buttonType)
				// {
				// getWindow().addWindow(changePassword);
				// }
				//
				// }
				// });
				//
				// // throw new AppException("FSS-10003", "CustomLogin");
				return false;

			}

			String strError = StringUtil.nvl(
					response.getString("Out.strError"), "");
			Vector vtPermission = response.getVector("vtPermission");

			if (strError.equals("1")) {
				throw new AppException("FSS-00006", "CustomLogin");
				// throw new Exception("Máº­t kháº©u khÃ´ng Ä‘Ãºng");
			} else if (strError.equals("2")) {
				throw new AppException("FSS-00016", "CustomLogin");
				// throw new Exception(
				// "TÃ i khoáº£n nÃ y khÃ´ng tá»“n táº¡i trong há»‡ thá»‘ng hoáº·c Ä‘Ã£ bá»‹ khÃ³a");
			}
			/*
			 * AppUser user = new AppUser(); user.setLoggedIn(true);
			 * user.setPermissionInfo(vtPermission);
			 * user.setUserName(strUserName);
			 * 
			 * application.setUser(user);
			 */

			SessionData.setUserName(strUserName);
			SessionData.setLoggedIn(true);
			SessionData.setPermissionInfo(vtPermission);

			LogUtil.logAccess(CustomLogin.class.getName());

			if (language.getValue() != null) {
				application.setLocale((Locale) language.getValue());
			}

			//
			// SessionData.getAppClient().setSessionValue("SessionUser",
			// strUserName);
			// SessionData.getAppClient().setPermissionInfo(vtPermission);
			// SessionData.getAppClient().setLoginStatus(true);
			return true;
		} catch (AppException e) {
			String msg = TM.get(e.getReason().substring(0, 9));
			e.printStackTrace();
			MessageAlerter.showErrorMessage(getWindow(), msg);
			// this.getWindow().showNotification(msg,
			// Notification.TYPE_ERROR_MESSAGE);
			return false;
		} catch (Exception e) {
			FormUtil.showException(this, e);
			return false;
		}
	}

	public void loginWindow(String user, String pass) {
//		System.out.println(user+"="+pass);
		if (user.equals("") || user == null) {

			MessageAlerter.showErrorMessage(application.getMainWindow(),
					TM.get("main.login.username.notnull"));
		} else {

			if (login(user, pass)) {
				application.reloadResource();
			}

		}
	}

}
