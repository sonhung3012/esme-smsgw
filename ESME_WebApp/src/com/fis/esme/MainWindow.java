package com.fis.esme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.vaadin.dialogs.ConfirmDialog;

import com.fis.esme.admin.SessionData;
import com.fis.esme.admin.SessionThread;
import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.component.PanelSSOError;
import com.fis.esme.component.PanelStatusBar;
import com.fis.esme.form.DialogAbout;
import com.fis.esme.form.DialogChangePassword;
import com.fis.esme.form.FormApParam;
import com.fis.esme.form.FormCP;
import com.fis.esme.form.FormFileUploadDetail;
import com.fis.esme.form.FormGroups;
import com.fis.esme.form.FormIsdnPrefix;
import com.fis.esme.form.FormIsdnSpecial;
import com.fis.esme.form.FormLanguage;
import com.fis.esme.form.FormMessage;
import com.fis.esme.form.FormMessageSchedulerApprover;
import com.fis.esme.form.FormShortCode;
import com.fis.esme.form.FormSmsCommand;
import com.fis.esme.form.FormSmsMt;
import com.fis.esme.form.FormSmsRouting;
import com.fis.esme.form.FormSmsc;
import com.fis.esme.form.FormSmscDetail;
import com.fis.esme.form.FormSubscriber;
import com.fis.esme.form.PanelDashboard;
import com.fis.esme.form.PanelService;
import com.fis.esme.form.lookup.LookUpSMS;
import com.fis.esme.util.FormUtil;
import com.fis.esme.util.MessageAlerter;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import eu.livotov.tpt.i18n.TM;

public class MainWindow extends VerticalLayout {

	private HashMap<MenuBar.MenuItem, Class<?>> menuTable;

	private VerticalLayout pnlMainDisplay;
	private Panel pnlFooter;
	private PanelStatusBar pnlStatus;
	private SessionThread sessionThread;
	// private Menu menu;
	private HorizontalLayout menuBarLayout;
	private MenuBar menubar;

	private VerticalLayout mainLayout;
	private Panel mainPanel;

	public MainWindow() {

		// super("MCA Web Application");
		// this = (VerticalLayout) this.getContent();

		menuTable = new HashMap<MenuBar.MenuItem, Class<?>>();
		// buttonPanel = new ButtonPanel();
		initMainPanel();
		initLayout();
	}

	private void initMainPanel() {

		mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.setSpacing(false);
		mainLayout.setMargin(false, true, false, true);

		mainPanel = new Panel();
		mainPanel.setSizeFull();
		mainPanel.setContent(mainLayout);
		mainPanel.setWidth("100%");
		mainPanel.setHeight("100%");

		this.setSizeFull();
		this.setMargin(false);
		this.addComponent(mainPanel);
		this.setComponentAlignment(mainPanel, Alignment.MIDDLE_CENTER);
	}

	private void initLayout() {

		// this.setSizeFull();

		CssLayout banner = new CssLayout();
		banner.setStyleName("toolbar");
		banner.setWidth("100%");
		banner.setHeight("45px");

		CustomLayout custom = new CustomLayout("banner");
		custom.setSizeFull();
		banner.addComponent(custom);

		pnlMainDisplay = new VerticalLayout();
		pnlMainDisplay.setStyleName("mca-panel-caption");
		pnlMainDisplay.setSizeFull();
		pnlMainDisplay.setMargin(false);

		pnlStatus = new PanelStatusBar();
		pnlStatus.setSizeFull();

		pnlFooter = new Panel();
		pnlFooter.setHeight("20px");
		pnlFooter.setContent(pnlStatus);
		pnlFooter.setStyleName("pnlFooter");

		mainLayout.addComponent(banner);
		getTopMenu();
		menuBarLayout = new HorizontalLayout();
		menuBarLayout.setSizeFull();
		menuBarLayout.setMargin(false);
		menuBarLayout.setSpacing(false);
		menuBarLayout.setHeight("30px");
		menuBarLayout.setWidth("100%");
		menuBarLayout.addComponent(menubar);
		mainLayout.addComponent(menuBarLayout);
		mainLayout.addComponent(pnlMainDisplay);
		mainLayout.setExpandRatio(pnlMainDisplay, 1);
		// mainLayout.addComponent(pnlFooter);
	}

	public void initComponent() {

		initSessionThread();
		// menu = new Menu(this);
		// topPanel.removeComponent(menubar);
		// topPanel.addComponent(menu);
		pnlMainDisplay.removeAllComponents();
		initMenubar();
		pnlMainDisplay.addComponent(new PanelDashboard());
	}

	private MenuBar getTopMenu() {

		menubar = new MenuBar();
		menubar.setSizeFull();
		menubar.setWidth("100%");
		menubar.setStyleName("prcmenubar");
		return menubar;
	}

	private void initMenubar() {

		for (final Dashboard dashboard : getDashboard()) {
			MenuBar.MenuItem parentItemp;
			if (dashboard.getParent() == 0) {

				// if (dashboard.getId() == 6)
				// {
				// parentItemp = menubarRight.addItem(dashboard.getCaption(),
				// null);
				// }
				// else
				// {
				// parentItemp = menubar.addItem(dashboard.getCaption(), null);
				// }
				if (!dashboard.isMultiLevel()) {
					parentItemp = menubar.addItem(dashboard.getCaption(), new MenuBar.Command() {

						public void menuSelected(MenuItem selectedItem) {

							callFunction(dashboard.getFunctionName(), (dashboard.getCls() != null) ? dashboard.getCls().getName() : null, dashboard.getCaption());
						}
					});
					parentItemp.setStyleName("prcmenubar");
				} else {
					parentItemp = menubar.addItem(dashboard.getCaption(), null);
					// parentItemp.setIcon(dashboard.getIcon());
					for (final Dashboard childDashboard : getDashboard()) {

						if (childDashboard.getParent() == dashboard.getId()) {
							if (childDashboard.getId() == -1) {
								parentItemp.addSeparator();
								continue;
							} else {
								MenuItem item = parentItemp.addItem(childDashboard.getCaption(), new MenuBar.Command() {

									public void menuSelected(MenuItem selectedItem) {

										callFunction(childDashboard.getFunctionName(), (childDashboard.getCls() != null) ? childDashboard.getCls().getName() : null, childDashboard.getCaption());
									}
								});
								// item.setIcon(new
								// ThemeResource("icons/dashboard/sIbull.png"));
								item.setEnabled(isEnable(childDashboard.getCls()));
							}
						}
					}
				}
			}
		}
	}

	// private void setCurrentButtonStyle(Button button, HorizontalLayout com) {
	// for (int i = 0; i < com.getComponentCount(); i++) {
	// Button btn = (Button) com.getComponent(i);
	// btn.setStyleName("topmenu");
	// }
	// button.setStyleName("current-page");
	// }

	// hungdv
	private void initSessionThread() {

		try {
			sessionThread = new SessionThread(SessionData.getAppClient());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startSessionThread() {

		try {
			/*
			 * SessionThread sessionThread[] = new SessionThread[2000];
			 * 
			 * for (int i = 0; i < 2000; i++) { sessionThread[i] = new SessionThread(); sessionThread[i].startThread(); System.out.println("Session thread start "+i+" ..."); }
			 */
			sessionThread.startThread();
			System.out.println("Session thread start...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stopSessionThread() {

		try {
			sessionThread.stopThread();
			System.out.println("Session thread stop...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void clearCache() {

		FormUtil.clearCache(getWindow());
	}

	private void clearCacheBusiness() {

		new CacheServiceClient();
		// FormUtil.clearCacheBU(getWindow());
	}

	private void openAboutDialog() {

		DialogAbout about = new DialogAbout();
		getWindow().addWindow(about);
	}

	public void checkPermission() {

		List<MenuItem> items = menubar.getItems();
		for (MenuItem item : items) {
			checkPermission(item);
		}
	}

	private void checkPermission(MenuBar.MenuItem menuItem) {

		if (menuItem == null) {
			return;
		}
		List<MenuItem> children = menuItem.getChildren();
		if (children != null && children.size() > 0) {
			for (MenuItem child : children) {
				checkPermission(child);
				if (menuTable.containsKey(child)) {
					Class c = menuTable.get(child);
					child.setEnabled(isEnable(c));
				}
			}
		}
	}

	public boolean isEnable(Class cls) {

		boolean enabled = true;
		if (cls != null) {
			String permission = getPermission(cls.getName());
			enabled = permission.contains("S");
		}
		return enabled;
	}

	private static String getPermission(String name) {

		return SessionData.getAppClient().getPermission(name);
	}

	public void setUsernameCaption(String caption) {

		pnlStatus.setUserCaption(TM.get("main.menubar.login.caption") + " " + caption);
	}

	public void callFunction(ComponentContainer relatedForm) {

		if (relatedForm != null) {
			try {
				showForm(relatedForm, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void callFunction(String functionName, String className, String caption) {

		// ((MainApplication) getApplication()).reloadPermission();

		if (className != null && functionName == null) {
			// boolean isPermission = isEnable(className);
			// if (!isPermission) {
			// MessageAlerter.showErrorMessage(getApplication()
			// .getMainWindow(), TM.get("message.permission"));
			// return;
			// }

			Class<ComponentContainer> obj;
			try {
				obj = (Class<ComponentContainer>) Class.forName(className);
				ComponentContainer relatedForm = obj.newInstance();
				if (relatedForm instanceof Window) {
					opentDialog((Window) relatedForm, caption);
				} else {
					showForm(relatedForm, caption);
				}
				// LogUtil.logAccess(className);
			} catch (ClassNotFoundException e) {
				MessageAlerter.showErrorMessage(this.getWindow(), "ClassNotFoundException: " + e.getMessage());
				e.printStackTrace();
			} catch (InstantiationException e) {
				MessageAlerter.showErrorMessage(this.getWindow(), "InstantiationException: " + e.getMessage());
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				MessageAlerter.showErrorMessage(this.getWindow(), "IllegalAccessException: " + e.getMessage());
				e.printStackTrace();
			}
		} else if (className == null && functionName != null) {
			if (functionName.equals("clearCache")) {
				clearCache();
			} else if (functionName.equals("clearCacheBusiness")) {
				clearCacheBusiness();
			} else if (functionName.equals("logout")) {
				logout();
			}
		}
	}

	public void opentDialog(Window window, String caption) {

		getWindow().addWindow(window);
	}

	public void showForm(ComponentContainer form, String caption) {

		// pnlMainDisplay.removeAllComponents();
		pnlStatus.setFormCaption(caption);
		form.setCaption(null);
		form.setSizeFull();
		// pnlMainDisplay.setContent(form);
		pnlMainDisplay.removeAllComponents();
		pnlMainDisplay.addComponent(form);
		// pnlMainDisplay.setCaption(form.getCaption());
		if (form instanceof PanelSSOError) {
			menuBarLayout.setEnabled(false);
		} else {
			menuBarLayout.setEnabled(true);
		}
		System.out.println("showForm");
	}

	public ArrayList<Dashboard> getDashboard() {

		ArrayList<Dashboard> list = new ArrayList<Dashboard>();

		list.add(new Dashboard(1, new ThemeResource("icons/dashboard/sIHeThong.png"), TM.get("menu.home.caption"), "", 0, false));

		list.add(new Dashboard(2, new ThemeResource("icons/dashboard/sIDanhMuc.png"), TM.get("menu.categories.caption"), null, 0, true));

		list.add(new Dashboard(3, new ThemeResource("icons/dashboard/sIDanhMuc.png"), TM.get("menu.caption.configuration"), null, 0, true));

		list.add(new Dashboard(4, new ThemeResource("icons/dashboard/sIDanhMuc.png"), TM.get("menu.caption.campaign"), null, 0, true));

		list.add(new Dashboard(5, new ThemeResource("icons/dashboard/sIDanhMuc.png"), TM.get("menu.caption.transaction"), null, 0, true));

		// list.add(new Dashboard(4, new ThemeResource("icons/dashboard/sIBaoCao.png"), TM.get("menu.report.caption"), null, 0, true));
		// list.add(new Dashboard(5, new ThemeResource(
		// "icons/dashboard/sITroGiup.png"), TM.get("menu.help.caption"),
		// "", 0, false));
		list.add(new Dashboard(6, new ThemeResource("icons/dashboard/iStar.png"), TM.get("menu.caption.about"), DialogAbout.class, 0, false, false));

		// Dinh Tuyen

		// Blacklist
		// list.add(new Dashboard(6,
		// new ThemeResource("icons/dashboard/iStar.png"), TM
		// .get(FormIsdnSpecial.class.getName()),
		// FormIsdnSpecial.class, true));
		// Trang chủ
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(PanelDashboard.class.getName()), PanelDashboard.class, 1, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(DialogChangePassword.class.getName()), DialogChangePassword.class, 1, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get("menu.caption.system_cleancache"), "clearCache", 1, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get("menu.caption.system_logout"), "logout", 1, true));

		// list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"),
		// TM.get("menu.caption.system_cleancacheBussines"),
		// "clearCacheBusiness", 1, true));

		// Danh mục

		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(PanelService.class.getName()), PanelService.class, 2, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormSmsCommand.class.getName()), FormSmsCommand.class, 2, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormShortCode.class.getName()), FormShortCode.class, 2, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormIsdnPrefix.class.getName()), FormIsdnPrefix.class, 2, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormApParam.class.getName()), FormApParam.class, 2, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormLanguage.class.getName()), FormLanguage.class, 2, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormIsdnSpecial.class.getName()), FormIsdnSpecial.class, 2, true));

		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormCP.class.getName()), FormCP.class, 3, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormSmsc.class.getName()), FormSmsc.class, 3, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormSmscDetail.class.getName()), FormSmscDetail.class, 3, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormSmsRouting.class.getName()), FormSmsRouting.class, 3, true));

		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormGroups.class.getName()), FormGroups.class, 4, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormSubscriber.class.getName()), FormSubscriber.class, 4, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormMessage.class.getName()), FormMessage.class, 4, true));
		// list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormMessageScheduler.class.getName()), FormMessageScheduler.class, 4, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormMessageSchedulerApprover.class.getName()), FormMessageSchedulerApprover.class, 4, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormFileUploadDetail.class.getName()), FormFileUploadDetail.class, 4, true));

		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(LookUpSMS.class.getName()), LookUpSMS.class, 5, true));
		list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(FormSmsMt.class.getName()), FormSmsMt.class, 5, true));

		// list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(DialogKPIDailySummary.class.getName()), DialogKPIDailySummary.class, 4, true));
		// list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(DialogKPIServiceSummary.class.getName()), DialogKPIServiceSummary.class, 4, true));
		// list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(DialogKPICpSummary.class.getName()), DialogKPICpSummary.class, 4, true));
		// list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(DialogKPICommandSummary.class.getName()), DialogKPICommandSummary.class, 4, true));

		// QUAN LY THUE BAO
		/*
		 * list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(PanelFindSubscriber.class.getName()), PanelFindSubscriber.class, 3, true));
		 */

		// Báo cáo

		// list.add(new Dashboard(new
		// ThemeResource("icons/dashboard/iStar.png"),
		// TM.get(FormInvoices.class.getName()), FormInvoices.class,
		// 4, true));

		/*
		 * list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"), TM.get(DialogKPI.class.getName()), DialogKPI.class, 4, true));
		 */

		// About
		// list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"),
		// TM.get("menu.caption.help_manuals"), "", 5, true));
		// list.add(new Dashboard(new ThemeResource("icons/dashboard/iStar.png"),
		// TM.get("menu.caption.help_about"), DialogAbout.class, 5, true));

		return list;
	}

	private void logout() {

		ConfirmDialog.show(getWindow(), TM.get("logout.confirm.title"), TM.get("logout.confirm.description"), TM.get("form.dialog.button.caption_accept"), TM.get("form.dialog.button.caption_cancel"),
		        new ConfirmDialog.Listener() {

			        public void onClose(ConfirmDialog dialog) {

				        if (dialog.isConfirmed()) {
					        getApplication().setUser(null);
					        getApplication().close();
					        clearCache();
					        // getApplication().setLogoutURL(TM.get("logoutURL"));
				        }
			        }
		        });
	}

	public void updateLanguage() {

		pnlStatus.updateLanguage();
	}

	public void setEnableMenu(boolean enabled) {

		menuBarLayout.setEnabled(enabled);
	}
}
