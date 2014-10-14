//package com.fis.esme.component;
//
////package com.fis.prc.component;
//
//import com.fis.prc.MainWindow;
//import com.fis.prc.admin.PermissionAdapter;
//import com.fis.prc.admin.SessionData;
//import com.fis.prc.form.FormCampaign;
//import com.fis.prc.form.FormChannel;
//import com.fis.prc.form.FormInteractionDetail;
//import com.fis.prc.form.FormLanguage;
//import com.fis.prc.form.FormListRole;
//import com.fis.prc.form.FormPackage;
//import com.fis.prc.form.FormPrcAction;
//import com.fis.prc.form.FormPrcBundle;
//import com.fis.prc.form.FormPrcOfferDtail;
//import com.fis.prc.form.FormService;
//import com.fis.prc.form.PanelPackage;
//import com.fis.prc.form.subscriber.PanelFindSubscriber;
//import com.fis.prc.form.subscriber.PanelSessionError;
//import com.fis.prc.form.subscriber.RegSubcriber;
//import com.vaadin.terminal.ExternalResource;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Component;
//import com.vaadin.ui.GridLayout;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.VerticalLayout;
//
//import eu.livotov.tpt.i18n.TM;
//
//public class Menu extends VerticalLayout {
//
//	Button btnHome = null;
//	Button btnAdministrator = null;
//
//	Button btnCategories = null;
//	Button btnService = null;
//	// Button btnBundle = null;
//	Button btnLanguage = null;
//	Button btnChannel = null;
//	Button btnAction = null;
//	Button btnInteraction = null;
//	Button btnpackage = null;
//	Button btnListRole = null;
//	Button btnCharges = null;
//	Button btnCampaign = null;
//	Button btnPackageList = null;
//
//	Button btnSubscribers = null;
//	Button btnSubcSearch = null;
//	Button btnSubcRegister = null;
//	Button btnSessionError = null;
//	Button btnCommercialOffer = null;
//
//	Button btnReports = null;
//	Button btnHelp = null;
//
//	GridLayout mainLayout = new GridLayout(1, 2);
//	HorizontalLayout btnLayout = new HorizontalLayout();
//	HorizontalLayout childLayout = new HorizontalLayout();
//
//	MainWindow mainWindow;
//	PermissionAdapter permissionAdapter;
//
//	Button btnCurrent = null;
//
//	public Menu(MainWindow mainWindow) {
//		this.mainWindow = mainWindow;
//		permissionAdapter = new PermissionAdapter();
//		btnLayout.setSizeFull();
//		btnLayout.setWidth("100%");
//		btnLayout.setStyleName("TopMenuStyle");
//		childLayout.setSizeFull();
//		mainLayout.setSizeFull();
//		mainLayout.addComponent(btnLayout, 0, 0);
//		mainLayout.addComponent(childLayout, 0, 1);
//		this.addComponent(mainLayout);
//
//		btnSubcSearch = createButton("Tìm kiếm thuê bao", null,
//				PanelFindSubscriber.class.getName(), null);
//		btnSubcRegister = createButton("Đăng ký dịch vụ", null,
//				RegSubcriber.class.getName(), null);
//		btnSessionError = createButton("Xử lý thuê bao đấu lỗi", null,
//				PanelSessionError.class.getName(), null);
//		btnCommercialOffer = createButton("Danh mục gói dịch vụ", null,
//				FormPrcOfferDtail.class.getName(), null);
//		btnListRole = createButton("Danh mục quyền", null,
//				FormListRole.class.getName(), null);
//
//		btnService = createButton("Danh mục nhóm tác động", null,
//				FormService.class.getName(), null);
//		btnpackage = createButton("Danh mục gói cước", null,
//				FormPackage.class.getName(), null);
//		btnLanguage = createButton("Danh mục ngôn ngữ", null,
//				FormLanguage.class.getName(), null);
//		btnChannel = createButton("Danh mục kênh giao tiếp", null,
//				FormChannel.class.getName(), null);
//		btnAction = createButton("Danh mục tác động", null,
//				FormPrcAction.class.getName(), null);
//		btnInteraction = createButton("Chi tiết tác động", null,
//				FormInteractionDetail.class.getName(), null);
//		btnCampaign = createButton("Danh mục khuyến mãi", null,
//				FormCampaign.class.getName(), null);
//		// btnBundle = createButton("Danh mục tài khoản", null,
//		// FormPrcBundle.class.getName(), null);
//		btnPackageList = createButton("Danh mục gói cước", null,
//				PanelPackage.class.getName(), null);
//
//		btnHome = createButton(TM.get("menu.home.caption"), null, null, null);
//		btnLayout.addComponent(btnHome);
//		btnAdministrator = createButton(TM.get("menu.administrator.caption"),
//				null, "URL.Administration", null);
//		btnLayout.addComponent(btnAdministrator);
//		btnCategories = createButton(TM.get("menu.categories.caption"), null,
//				null, new Component[] { btnService, btnAction, btnInteraction,
//						btnLanguage, btnChannel, btnCampaign, btnPackageList });
//		btnLayout.addComponent(btnCategories);
//		btnCharges = createButton(TM.get("menu.qlcuoc.caption"), null, null,
//				null);
//		btnLayout.addComponent(btnCharges);
//		btnSubscribers = createButton("Quản lý thuê bao", null, null,
//				new Component[] { btnSubcSearch, btnSubcRegister,
//						btnSessionError, btnCommercialOffer, btnListRole });
//		btnLayout.addComponent(btnSubscribers);
//		btnReports = createButton("Báo cáo", null, null, null);
//		btnLayout.addComponent(btnReports);
//		btnHelp = createButton("Trợ giúp", null, null, null);
//		btnLayout.addComponent(btnHelp);
//
//		initConponent();
//	}
//
//	private void setCurrentButtonStyle(Button button, HorizontalLayout com) {
//		for (int i = 0; i < com.getComponentCount(); i++) {
//			Button btn = (Button) com.getComponent(i);
//			btn.setStyleName("topmenu");
//		}
//		button.setStyleName("current-page");
//	}
//
//	private void initConponent() {
//
//	}
//
//	private Button createButton(String caption, String width,
//			final String function, final Component[] coms) {
//		final Button button = new Button(caption);
//		button.addListener(new Button.ClickListener() {
//			@Override
//			public void buttonClick(ClickEvent event) {
//				if (function == null) {
//					addChildButton(coms);
//				} else {
//					if (function.equals("URL.Administration")) {
//						// getWindow()
//						// .open(new ExternalResource(TM
//						// .get("common.admin.url")), "_blank");
//					} else {
//						// mainWindow.callFunction(function);
//						mainWindow.callFunction(null, function, null);
//						btnCurrent = button;
//					}
//				}
//				setCurrentButtonStyle(event.getButton(),
//						(HorizontalLayout) event.getButton().getParent());
//
//			}
//		});
//		System.out.println("functionL" + function);
//		button.setEnabled(isEnable(function));
//		if (width != null)
//			button.setWidth(width);
//		button.setSizeFull();
//		// button.setStyleName(BaseTheme.BUTTON_LINK);
//		button.setStyleName("topmenu");
//		return button;
//	}
//
//	private void addChildButton(Component[] coms) {
//		childLayout.removeAllComponents();
//		if (coms == null)
//			return;
//		for (Component component : coms) {
//			if (btnCurrent == component)
//				component.addStyleName("current-page");
//			else
//				component.addStyleName("topmenu");
//			childLayout.addComponent(component);
//		}
//	}
//
//	public boolean isEnable(String cls) {
//		boolean enabled = true;
//		if (cls != null) {
//			String permission = SessionData.getAppClient().getPermission(cls);
//			System.out.println("permission menu: " + permission);
//			enabled = permission.contains("S");
//		}
//		return enabled;
//	}
//}
