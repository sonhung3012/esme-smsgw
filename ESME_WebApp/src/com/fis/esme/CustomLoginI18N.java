package com.fis.esme;
//package com.fis.mca;
//
//import java.util.Hashtable;
//import java.util.Locale;
//import java.util.Vector;
//
//import javax.naming.AuthenticationException;
//import javax.naming.AuthenticationNotSupportedException;
//import javax.naming.CommunicationException;
//import javax.naming.Context;
//import javax.naming.NamingException;
//import javax.naming.directory.DirContext;
//import javax.naming.directory.InitialDirContext;
//import javax.naming.ldap.LdapContext;
//
//import com.fis.mca.admin.AppClient;
//import com.fis.mca.admin.SessionData;
//import com.fis.mca.util.FormUtil;
//import com.fis.mca.util.LogUtil;
//import com.fis.mca.util.MessageAlerter;
//import com.fss.ddtp.DDTP;
//import com.fss.util.AppException;
//import com.fss.util.StringUtil;
//import com.vaadin.data.Property;
//import com.vaadin.data.Property.ValueChangeEvent;
//import com.vaadin.event.ShortcutAction.KeyCode;
//import com.vaadin.terminal.ThemeResource;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.ComboBox;
//import com.vaadin.ui.CssLayout;
//import com.vaadin.ui.CustomLayout;
//import com.vaadin.ui.Embedded;
//import com.vaadin.ui.PasswordField;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.themes.BaseTheme;
//
//import eu.livotov.tpt.i18n.TM;
//
//public class CustomLoginI18N extends VerticalLayout
//{
//	private TextField username;
//	private PasswordField password;
//	private MainApplication application;
//	private ComboBox language;
//	
//	private Button btnLogin = new Button();
//	private Embedded mcasystemEmb = new Embedded();
//	private Embedded usernameEmb = new Embedded();
//	private Embedded passwordEmb = new Embedded();
//	private Embedded copyrightEmbt = new Embedded();
//	
//	private String ldapDefaultPass = "Ldap@default*pass";
//
//	public CustomLoginI18N(MainApplication app)
//	{
//		
//		this.application = app;
//		setMargin(true);
//		setSizeFull();
//
//		language = new ComboBox("");
//		language.setWidth("121px");
//		
//		Locale vi_VN = new Locale("vi", "VN");
//		language.addItem(vi_VN);
//		language.setItemCaption(vi_VN, "Tiếng Việt");
//		Locale en_US = new Locale("en", "US");
//		language.addItem(en_US);
//		language.setItemCaption(en_US, "English");
//		
//		language.setNullSelectionAllowed(false);
//		language.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
//		
//		getCurrentLocate();
//
//		language.setImmediate(true);
//		language.addListener(new Property.ValueChangeListener()
//		{
//			@Override
//			public void valueChange(ValueChangeEvent event)
//			{
//				if (language.getValue() != null)
//				{
//					setLableByLocate((Locale)language.getValue());
//				}
//			}
//		});
//		
//		CustomLayout custom = new CustomLayout("login");
//		custom.setSizeFull();
//		addComponent(custom);
//		setStyleName("loginVLayoutStyle");
//		
//		custom.addComponent(language, "changelanguage");
//		
//		CssLayout vmcasystem = new CssLayout();
//		vmcasystem.setWidth("284px");
//		vmcasystem.setHeight("185px");
//		vmcasystem.addComponent(mcasystemEmb);
//		custom.addComponent(vmcasystem, "lblMCAsystem");
//		
//		CssLayout vusername = new CssLayout();
//		vusername.setWidth("134px");
//		vusername.setHeight("32px");
//		vusername.addComponent(usernameEmb);
//		custom.addComponent(vusername, "lblUsername");
//		
//		CssLayout vpassword = new CssLayout();
//		vpassword.setWidth("134px");
//		vpassword.setHeight("32px");
//		vpassword.addComponent(passwordEmb);
//		custom.addComponent(vpassword, "lblPassword");
//		
//		CssLayout vcopyright = new CssLayout();
//		vcopyright.setWidth("651px");
//		vcopyright.setHeight("54px");
//		vcopyright.addComponent(copyrightEmbt);
//		custom.addComponent(vcopyright, "lblCopyright");
//		
//		username = new TextField();
//		username.focus();
//		username.setMaxLength(50);
//		// username.setHeight("19px");
//		// username.setWidth("115px");
//		username.setStyleName("txtLoginField");
//		custom.addComponent(username, "username");
//		
//		password = new PasswordField();
//		password.setMaxLength(50);
//		// password.setHeight("19px");
//		// password.setWidth("115px");
//		password.setStyleName("txtLoginField");
//		custom.addComponent(password, "password");
//		
//		btnLogin.setStyleName(BaseTheme.BUTTON_LINK);
//		btnLogin.setWidth("150px");
//		btnLogin.setHeight("32px");
//		custom.addComponent(btnLogin, "okbutton");
//		btnLogin.setClickShortcut(KeyCode.ENTER);
//		btnLogin.addListener(new Button.ClickListener()
//		{
//			
//			@Override
//			public void buttonClick(ClickEvent event)
//			{
//				String user = username.getValue().toString().trim();
//				String pass = (String) password.getValue();
//				if (user.equals("") || user == null)
//				{
//					// application.getMainWindow().showNotification("Bạn phải điền thông tin đăng nhập",Notification.TYPE_ERROR_MESSAGE);
//					MessageAlerter.showErrorMessage(
//							application.getMainWindow(),
//							TM.get("main.login.username.notnull"));
//				}
//				else
//				{
////					if (login(user, pass))
////					{
////						application.reloadResource();
////					}
////					boolean au = ldapAuthen(user, pass);
////					System.out.println(au);
//					if (authen(user,pass))
//					{
//						application.reloadResource();
//					}
//				}
//				
//			}
//		});
//		
//	}
//	
//	private Locale getCurrentLocate()
//	{
//		Locale locale = application.getLocale();
//		setLableByLocate(locale);
//		language.select(locale);
//		return locale;
//	}
//	
//	private void setLableByLocate(Locale locate)
//	{
//		application.setLocale(locate);
////		application.reloadResource();
//		if (locate.toString().equals("vi_VN"))
//		{
//			mcasystemEmb.setIcon(new ThemeResource("images/vi_login_01.jpg"));
//			usernameEmb
//					.setIcon(new ThemeResource("images/vi_login_01_2_04.jpg"));
//			passwordEmb
//					.setIcon(new ThemeResource("images/vi_login_01_2_06.jpg"));
//			copyrightEmbt.setIcon(new ThemeResource("images/vi_login_13.jpg"));
//			btnLogin.setIcon(new ThemeResource("images/vi_login_11.jpg"));
//		}
//		else if (locate.toString().equals("en_US"))
//		{
//			mcasystemEmb.setIcon(new ThemeResource("images/en_login_01.jpg"));
//			usernameEmb
//					.setIcon(new ThemeResource("images/en_login_01_2_04.jpg"));
//			passwordEmb
//					.setIcon(new ThemeResource("images/en_login_01_2_06.jpg"));
//			copyrightEmbt.setIcon(new ThemeResource("images/en_login_13.jpg"));
//			btnLogin.setIcon(new ThemeResource("images/en_login_11.jpg"));
//		}
//	}
//	
//	private boolean authen(String user, String pass)
//	{
//		try
//		{
//			String type = getAuthenticationType(user);
//			System.out.println("type: "  + type);
//			if ("1".equals(type))
//			{
//				boolean ldap = ldapAuthen(user, pass);
//				
//				if (ldap)
//				{
//					return login(user, ldapDefaultPass);
//				}
//				
//				return ldap;
//			}
//			else
//			{
//				return login(user, pass);
//			}
//		}
//		catch (Exception ex)
//		{
//			ex.printStackTrace();
//		}
//		
//		return false;
//	}
//	
//	public boolean login(String strUserName, String strPassword)
//	{
//		try
//		{
//			DDTP request = new DDTP();
//			String strAlgorithm = StringUtil.nvl(
//					AppClient.getSessionValue("Encrypt.Algorithm"), "");
//			
//			if (!strPassword.equals("") && !strAlgorithm.equals(""))
//			{
//				strPassword = StringUtil.encrypt(strPassword, strAlgorithm);
//			}
//			
//			request.setString("strUserName", strUserName);
//			request.setString("strPassword", strPassword);
//			DDTP response = AppClient.sendRequest("PermissionBean", "login",
//					request);
//			String nouser = response.getString("$Exception$");
//			
//			if (nouser != null && nouser.contains("FSS-00016"))
//			{
//				throw new AppException("FSS-00016", "CustomLogin");
//			}
//			
//			if (response.getString("MaxSessionExceeded") != null)
//			{
//				throw new AppException("FSS-00019", "CustomLogin");
//			}
//			
//			if (response.getString("PasswordExpired") != null)
//			{
//				throw new AppException("FSS-10003", "CustomLogin");
//			}
//			
//			String strError = StringUtil.nvl(
//					response.getString("Out.strError"), "");
//			Vector vtPermission = response.getVector("vtPermission");
//			
//			if (strError.equals("1"))
//			{
//				throw new AppException("FSS-00006", "CustomLogin");
//			}
//			else if (strError.equals("2"))
//			{
//				throw new AppException("FSS-00016", "CustomLogin");
//			}
////			AppUser user = new AppUser();
////			user.setLoggedIn(true);
////			user.setPermissionInfo(vtPermission);
////			user.setUserName(strUserName);
//			
//			SessionData.setUserName(strUserName);
//			SessionData.setLoggedIn(true);
//			SessionData.setPermissionInfo(vtPermission);
//			
////			application.setUser(user);
//			LogUtil.logAccess("CustomLogin");
//			
//			if (language.getValue() != null)
//			{
//				application.setLocale((Locale)language.getValue());
//			}
//			return true;
//		}
//		catch (AppException e)
//		{
//			String msg = TM.get(e.getReason().substring(0, 9));
//			e.printStackTrace();
//			MessageAlerter.showErrorMessage(getWindow(), msg);
//			return false;
//		}
//		catch (Exception e)
//		{
//			FormUtil.showException(this, e);
//			return false;
//		}
//	}
//	
//	private String getAuthenticationType(String user)
//	{
////		ActionTransferer client = ActionTransfererClient.getService();
//		
//		String loginType;
//		try
//		{
////			loginType = client.getLoginType(user);
//		}
//		catch (Exception ex)
//		{
//			ex.printStackTrace();
//			loginType = "-1";
//		}
//		
//		return "Admin";
//	}
//	
//	private boolean ldapAuthen(String user, String pass)
//	{
//		Hashtable<String, String> environment = new Hashtable<String, String>();
//		try
//		{
//			environment.put(LdapContext.CONTROL_FACTORIES,
//					"com.sun.jndi.ldap.ControlFactory");
//			environment.put(Context.INITIAL_CONTEXT_FACTORY,
//					"com.sun.jndi.ldap.LdapCtxFactory");
////			environment.put(Context.PROVIDER_URL, Config.getString("PROVIDER_URL"));
////			environment.put(Context.SECURITY_AUTHENTICATION, Config.getString("SECURITY_AUTHENTICATION"));
////			environment.put(Context.STATE_FACTORIES, Config.getString("STATE_FACTORIES"));
////			environment.put(Context.OBJECT_FACTORIES, Config.getString("OBJECT_FACTORIES"));
////			
////			environment.put(Context.SECURITY_PRINCIPAL, user + Config.getString("LDAP_DOMAIN"));
//			environment.put(Context.SECURITY_CREDENTIALS, pass);
//			
//			if (pass == null || "".equals(pass))
//			{
//				throw new AuthenticationException("empty password");
//			}
//			
////			String s = "";
////			s += Config.getString("PROVIDER_URL") + "\n";
////			s += Config.getString("SECURITY_AUTHENTICATION") + "\n";
////			s += user + "\n";
////			s += pass + "\n";
////			s += Config.getString("STATE_FACTORIES") + "\n";
////			s += Config.getString("OBJECT_FACTORIES") + "\n";
////			s += "";
////			System.out.println(s);
//			// connect to LDAP
//			DirContext ctx = new InitialDirContext(environment);
//			return true;
//		}
//		catch (CommunicationException ex)
//		{
//			MessageAlerter.showErrorMessageI18n(getWindow(), "ldap.connect.fail");
//			ex.printStackTrace();
//		}
//		catch (AuthenticationNotSupportedException ex)
//		{
//			String au = environment.get(Context.SECURITY_AUTHENTICATION);
//			MessageAlerter.showErrorMessageI18n(getWindow(), "ldap.authen.notsupport",au);
//			ex.printStackTrace();
//		}
//		catch (AuthenticationException ex)
//		{
//			MessageAlerter.showErrorMessageI18n(getWindow(), "ldap.login.fail");
//			ex.printStackTrace();
//		}
//		catch (NamingException ex)
//		{
//			MessageAlerter.showErrorMessage(getWindow(), ex.getMessage());
//			ex.printStackTrace();
//		}
//		catch (Exception ex)
//		{
//			// TODO Auto-generated catch block
//			ex.printStackTrace();
//		}
//		
//		return false;
//	}
//}
