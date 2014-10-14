package com.fis.esme;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fis.esme.admin.SessionData;
import com.fis.esme.component.PanelSSOError;
import com.fis.esme.util.FormUtil;
import com.fss.dictionary.DefaultDictionary;
import com.vaadin.Application;
import com.vaadin.terminal.ParameterHandler;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import eu.livotov.tpt.TPTApplication;
import eu.livotov.tpt.i18n.TM;

public class MainApplication extends TPTApplication implements ParameterHandler {
	// public static MainApplication INSTANCE;
	private CustomLogin loginWindow;
	private MainWindow frmMain;
	private Window mainWindow;
	private String ipRemoteAddr;

	@Override
	public void applicationInit() {

		setTheme("mca");
		SessionData data = new SessionData(this);
		SessionData.getAppClient().reset(this);
		getContext().addTransactionListener(data);

		DefaultDictionary.setCurrentLanguage("EN");
		this.setLocale(new Locale("en", "EN"));
		// this.setLocale(new Locale("vi", "VN"));
		mainWindow = new Window(TM.get("main.title"));
		setMainWindow(mainWindow);

		mainWindow.addParameterHandler(this);

		initMainWindow();
		initLoginWindow();
		reloadResource();
		// setMainWindow(new MainWindow());
	}

	private void initMainWindow() {
		frmMain = new MainWindow();
	}

	private void initLoginWindow() {
		loginWindow = new CustomLogin(this);
	}

	public MainWindow getApplicatoinMainWindow() {
		return frmMain;
	}

	public void reloadResource() {
		// boolean login = (getUser() != null && getUser().isLoggedIn());
		boolean login = SessionData.isLoggedIn();
		if (login) {
			mainWindow.setContent(frmMain);
			// frmMain.checkPermission();
			// frmMain.setUsernameCaption(getUser().getUserName());

			frmMain.initComponent();
			frmMain.setUsernameCaption(SessionData.getUserName());
			frmMain.updateLanguage();
			((VerticalLayout) mainWindow.getContent())
					.removeStyleName("mca-login-background");
			// INSTANCE = (MainApplication) mainWindow.getApplication();

			// hungdv
			frmMain.startSessionThread();
		} else {
			mainWindow.setContent(loginWindow);
			((VerticalLayout) mainWindow.getContent())
					.addStyleName("mca-login-background");
		}

		// frmMain.setVisible(login);
		// loginWindow.setVisible(!login);
		System.out.println("login checked");
	}

	public String getPermission(String moduleName) {
		return SessionData.getPermission(moduleName);
	}

	public void reloadPermission() {
		try {
			// SessionData.setPermissionInfo(SecGroupData.getPermissionInfo());
		} catch (Exception e) {
			FormUtil.showException(getMainWindow(), e);
		}
	}

	// @Override
	// public AppUser getUser() {
	// Object user = super.getUser();
	// if (user instanceof AppUser) {
	// return (AppUser) user;
	// }
	//
	// return new AppUser();
	// }

	@Override
	public void transactionStart(Application application, Object transactionData) {

		super.transactionStart(application, transactionData);
		if (getIpRemoteAddr() == null) {
			if (transactionData instanceof HttpServletRequest) {
				HttpServletRequest httpServletRequest = (HttpServletRequest) transactionData;
				setIpRemoteAddr(httpServletRequest.getRemoteAddr());
			}
		}
	}

	public String getIpRemoteAddr() {
		return ipRemoteAddr;
	}

	public void setIpRemoteAddr(String ipRemoteAddr) {
		this.ipRemoteAddr = ipRemoteAddr;
	}

	// private static String sessionExpiredCaption;
	// private static String sessionExpiredMessage;
	// private static boolean sessionExpiredNotificationEnabled;

	// messages.setSessionExpiredURL(TM.get("sessionExpiredURL"));

	// private static String communicationErrorCaption = TM
	// .get("communicationErrorCaption");
	// private static String communicationErrorMessage = TM
	// .get("communicationErrorMessage");
	// private static boolean communicationErrorNotificationEnabled = TM.get(
	// "communicationErrorNotificationEnabled").equals("true") ? true
	// : false;
	//
	// private static String authenticationErrorCaption = TM
	// .get("authenticationErrorCaption");
	// private static String authenticationErrorMessage = TM
	// .get("authenticationErrorMessage");
	// private static boolean authenticationErrorNotificationEnabled = TM.get(
	// "authenticationErrorNotificationEnabled").equals("true") ? true
	// : false;
	//
	// private static String internalErrorCaption =
	// TM.get("internalErrorCaption");
	// private static String internalErrorMessage =
	// TM.get("internalErrorMessage");
	// private static boolean internalErrorNotificationEnabled = TM.get(
	// "internalErrorNotificationEnabled").equals("true") ? true : false;
	//
	// private static String outOfSyncCaption = TM.get("outOfSyncCaption");
	// private static String outOfSyncMessage = TM.get("outOfSyncMessage");
	// private static boolean outOfSyncNotificationEnabled = TM.get(
	// "outOfSyncNotificationEnabled").equals("true") ? true : false;
	//
	// private static String cookiesDisabledCaption = TM
	// .get("cookiesDisabledCaption");
	// private static String cookiesDisabledMessage = TM
	// .get("cookiesDisabledMessage");
	// private static boolean cookiesDisabledNotificationEnabled = TM.get(
	// "cookiesDisabledNotificationEnabled").equals("true") ? true : false;

	public static SystemMessages getSystemMessages() {
		CustomizedSystemMessages messages = new CustomizedSystemMessages();

		messages.setSessionExpiredCaption("Phiên làm việc hết hạn");
		// messages.setSessionExpiredMessage("Hãy lưu ý những dữ liệu bạn đang làm việc chưa được lưu lại, và <u>bấm vào đây</u> để tiếp tục");
		messages.setSessionExpiredCaption("Sessions expire");
		messages.setSessionExpiredMessage("Please note that the data you are working on has not been saved, and <u> click here </ u> to continue");
		messages.setSessionExpiredNotificationEnabled(true);

		messages.setCommunicationErrorCaption("Communication problem");
		// messages.setCommunicationErrorMessage("Hãy lưu ý những dữ liệu bạn đang làm việc chưa được lưu lại, và <u>bấm vào đây</u> để tiếp tục");
		messages.setCommunicationErrorMessage("Please note that the data you are working on has not been saved, and <u> click here </ u> to continue");
		messages.setCommunicationErrorNotificationEnabled(true);

		messages.setAuthenticationErrorCaption("Authentication problem");
		// messages.setAuthenticationErrorMessage("Hãy lưu ý những dữ liệu bạn đang làm việc chưa được lưu lại, và <u>bấm vào đây</u> để tiếp tục");
		messages.setAuthenticationErrorMessage("Please note that the data you are working on has not been saved, and <u> click here </ u> to continue");
		messages.setAuthenticationErrorNotificationEnabled(true);

		messages.setInternalErrorCaption("Internal error");
		// messages.setInternalErrorMessage("Xin vui lòng thông báo cho quản trị viên. <br/> Hãy lưu ý những dữ liệu bạn đang làm việc chưa được lưu lại, và <u>bấm vào đây</u> để tiếp tục");
		messages.setInternalErrorMessage("Please notify an administrator. <br/> Please note the data you are working is not saved, and <u> click here </ u> to continue");
		messages.setInternalErrorNotificationEnabled(true);

		// messages.setOutOfSyncCaption("Không đồng bộ với máy chủ");
		// messages.setOutOfSyncMessage("Xảy ra lỗi đồng bộ với máy chủ. <br/> Hãy lưu ý những dữ liệu bạn đang làm việc chưa được lưu lại, và <u>bấm vào đây</u> để đồng bộ lại.");
		messages.setOutOfSyncCaption("Not synchronized with the server");
		messages.setOutOfSyncMessage("An error occurred in sync with the server. <br/> Please note the data you are working is not saved, and <u> click here </ u> to sync again.");
		messages.setOutOfSyncNotificationEnabled(true);

		// messages.setCookiesDisabledCaption("Cookies bị vô hiệu hóa");
		// messages.setCookiesDisabledMessage("Ứng dụng này đòi hỏi làm việc với cookie. <br/> Xin vui lòng bật cookie trong trình duyệt của bạn và <u>bấm vào đây</u> để thử lại.");
		messages.setCookiesDisabledCaption("Cookies bị vô hiệu hóa");
		messages.setCookiesDisabledMessage("This application requires cookies to work with. <br/> Please enable cookies in your browser and <u> click here </ u> to try again.");
		messages.setCookiesDisabledNotificationEnabled(true);
		return messages;
	}

	@Override
	public void firstApplicationStartup() {
		// sessionExpiredCaption = TM.get("sessionExpiredCaption");
		// sessionExpiredMessage = TM.get("sessionExpiredMessage");
		// sessionExpiredNotificationEnabled = TM.get(
		// "sessionExpiredNotificationEnabled").equals("true") ? true
		// : false;

	}

	@Override
	public void handleParameters(Map<String, String[]> parameters) {
		// if (!TM.get("sso_login").equals("1")) {
		// System.out.println("return login");
		// return;
		// } else {
		// if (parameters.containsKey(TM.get("username"))
		// && parameters.containsKey(TM.get("sessionid"))) {
		// String username = ((String[]) parameters.get("username"))[0];
		// String sessionid = ((String[]) parameters.get("sessionid"))[0];
		// System.out.println("sso: " + (username + " - " + sessionid));
		//
		// //
		// http://localhost:8050/PRC_WebApp/?username=84909090909&sessionid=111231231293812904823094
		//
		// // String strCheckCommand =
		// // "islogin#84909090909#sessionid#111231231293812904823094 \n";
		//
		// String strCheckCommand = "islogin#" + username + "#sessionid#"
		// + sessionid + " \n";
		//
		// Authenticate s = new Authenticate();
		// SSOObject sso;
		// try {
		// sso = s.sendChecking(strCheckCommand);
		// System.out.println("status : " + sso.status);
		// System.out.println("des : " + sso.description);
		// if (sso.status
		// .equalsIgnoreCase(Authenticate.SSO_SUCCESSFULL)) {
		// CustomLogin login = new CustomLogin(this);
		// // login.loginWindow("admin", "vm$@123");
		// login.loginWindow("admin", "123");
		//
		// PrcInfoOutput prcInfoOutput =
		// CacheServiceClient.servicePRCCustomercare
		// .checkRegister(FormUtil.cutMSISDN(username),
		// TM.get("SUBC_ROLE_PARRENT"));
		// System.out.println("SSO Sub status 1:");
		// if (prcInfoOutput.getLstSubscriber().size() > 0) {
		// PrcSubscriber subsc = SubscriberUtil
		// .objSubcToSubc(prcInfoOutput
		// .getLstSubscriber().get(0));
		//
		// System.out.println("SSO Sub status 2:"
		// + subsc.getActStatus());
		// if (subsc.getActStatus().equals(
		// TM.get("SUBC_ROLE_PARRENT"))) {
		// SessionData.getCurrentApplication()
		// .getApplicatoinMainWindow()
		// .setEnableMenu(true);
		// } else
		// setPanelToMainWindow(username);
		// } else {
		// System.out.println("SSO Sub status 3:");
		// setPanelToMainWindow(username);
		// }
		// System.out.println("SSO Sub status:4");
		// SessionData.setCurrentMSISDN(FormUtil
		// .cutMSISDN(username));
		// } else {
		// if (!SessionData.isLoggedIn()) {
		// mainWindow.open(
		// new ExternalResource(TM.get("potallink")),
		// "");
		// }
		// }
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// mainWindow.open(new ExternalResource(TM.get("potallink")),
		// "");
		// }
		// } else if (!SessionData.isLoggedIn()) {
		// mainWindow.open(new ExternalResource(TM.get("potallink")), "");
		// }
		//
		// }
	}

	private void setPanelToMainWindow(String msisdn) {
		SessionData
				.getCurrentApplication()
				.getApplicatoinMainWindow()
				.callFunction(
						new PanelSSOError(msisdn, SessionData
								.getCurrentApplication()
								.getApplicatoinMainWindow().getWindow()));
	}

}
