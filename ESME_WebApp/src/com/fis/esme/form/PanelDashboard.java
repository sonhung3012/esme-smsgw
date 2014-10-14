package com.fis.esme.form;


import com.fis.esme.Dashboard;
import com.fis.esme.MainWindow;
import com.fis.esme.admin.SessionData;
import com.fis.esme.component.CommonButtonPanel;
import com.fis.esme.component.PanelActionProvider;
import com.fis.esme.util.SearchObj;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import eu.livotov.tpt.i18n.TM;

public class PanelDashboard extends VerticalLayout implements
		PanelActionProvider {

	private MainWindow mainWindow;
	private CommonButtonPanel pnlAction;
	CustomLayout custom;

	// public PanelDashboard()
	// {
	// }

	public PanelDashboard() {
//		this.setCaption(TM.get("menu.caption.system_home"));
		this.mainWindow = SessionData.getCurrentApplication()
				.getApplicatoinMainWindow();
		initLayout();
		this.addComponent(custom);
	}

	private void initLayout() {
		pnlAction = new CommonButtonPanel(this);
		pnlAction.showSearchPanel(false);
		pnlAction.setFromCaption(TM.get(PanelDashboard.class.getName()));
		pnlAction.setMargin(false, false, true, false);
		pnlAction.setVisibleTopLayout();
		pnlAction.setHeight("30px");
		this.addComponent(pnlAction);
		custom = new CustomLayout("dashboard");
		VerticalLayout vLayout = new VerticalLayout();
		Button btn = null;
		//
		// for (Dashboard dashboard : mainWindow.getDashboard())
		// {
		// vLayout = new VerticalLayout();
		// for (Dashboard childDashboard : mainWindow.getDashboard())
		// {
		// if (childDashboard.getParent() == dashboard.getId() &&
		// childDashboard.getId() >= 0)
		// {
		// btn = createButton(childDashboard.getIcon(),
		// childDashboard.getCaption(),
		// childDashboard.getFunctionName());
		// vLayout.addComponent(btn);
		// btn.setEnabled(mainWindow.isEnable(childDashboard.getCls()));
		// }
		// }
		// custom.addComponent(new
		// Label(dashboard.getCaption()),"caption"+dashboard.getId());
		// String location = "location" + dashboard.getId();
		// custom.addComponent(vLayout, "location4");
		// }
		
//		vLayout.addComponent(new Label(TM.get("menu.caption.thuebao")));
//		for (Dashboard childDashboard : mainWindow.getDashboard()) {
//			if (childDashboard.getParent() == 5 && childDashboard.getId() != -1) {
//				btn = createButton(childDashboard.getIcon(),
//						childDashboard.getCaption(), childDashboard);
//				vLayout.addComponent(btn);
//				btn.setEnabled(mainWindow.isEnable(childDashboard.getCls()));
//			}
//		}
//		custom.addComponent(vLayout, "thuebao");

		vLayout = new VerticalLayout();
		vLayout.addComponent(new Label(TM.get("menu.caption.categories")));
		for (Dashboard childDashboard : mainWindow.getDashboard()) {
			if (childDashboard.getParent() == 2 && childDashboard.getId() != -1) {
				btn = createButton(childDashboard.getIcon(),
						childDashboard.getCaption(), childDashboard);
				vLayout.addComponent(btn);
				btn.setEnabled(mainWindow.isEnable(childDashboard.getCls()));
			}
		}
		custom.addComponent(vLayout, "danhmuc");

		vLayout = new VerticalLayout();
		vLayout.addComponent(new Label(TM.get("menu.caption.quanlycuoc")));
		for (Dashboard childDashboard : mainWindow.getDashboard()) {
			if (childDashboard.getParent() == 3 && childDashboard.getId() != -1) {
				btn = createButton(childDashboard.getIcon(),
						childDashboard.getCaption(), childDashboard);
				vLayout.addComponent(btn);
				btn.setEnabled(mainWindow.isEnable(childDashboard.getCls()));
			}
		}
		custom.addComponent(vLayout, "quanlycuoc");
		
		
		vLayout = new VerticalLayout();
		vLayout.addComponent(new Label(TM.get("menu.caption.report")));
		for (Dashboard childDashboard : mainWindow.getDashboard()) {
			if (childDashboard.getParent() == 4 && childDashboard.getId() != -1) {
				btn = createButton(childDashboard.getIcon(),
						childDashboard.getCaption(), childDashboard);
				vLayout.addComponent(btn);
				btn.setEnabled(mainWindow.isEnable(childDashboard.getCls()));
			}
		}
		custom.addComponent(vLayout, "baocao");

//		vLayout = new VerticalLayout();
//		for (Dashboard childDashboard : mainWindow.getDashboard()) {
//			if (childDashboard.getParent() == 5 && childDashboard.getId() != -1) {
//				btn = createButton(childDashboard.getIcon(),
//						childDashboard.getCaption(), childDashboard);
//				vLayout.addComponent(btn);
//				btn.setEnabled(mainWindow.isEnable(childDashboard.getCls()));
//			}
//		}
//		custom.addComponent(vLayout, "trogiup");

	}

//	private Button createButton(ThemeResource icon, String caption,
//			final Class<?> form) {
//		Button btn = new Button(caption);
//		btn.setStyleName(BaseTheme.BUTTON_LINK);
//		btn.setDescription(caption);
//		btn.setIcon(icon);
//		btn.addListener(new Button.ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				try {
//					mainWindow.showForm(
//							(ComponentContainer) form.newInstance(), null);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		return btn;
//	}

	private Button createButton(ThemeResource icon, String caption,
			final Dashboard childDashboard) {
		Button btn = new Button(caption);
		btn.setStyleName(BaseTheme.BUTTON_LINK);
		btn.addStyleName("btnLink");
		btn.setDescription(caption);
		btn.setIcon(icon);
		btn.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				try {
					mainWindow.callFunction(childDashboard.getFunctionName(),
							(childDashboard.getCls() != null) ? childDashboard
									.getCls().getName() : null, childDashboard
									.getCaption());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return btn;
	}

	@Override
	public void accept() {
		

	}

	@Override
	public void delete(Object object) {
		

	}

	@Override
	public void showDialog(Object object) {
		

	}

	@Override
	public void searchOrAddNew(String key) {
		

	}

	@Override
	public void search() {
		

	}

	@Override
	public void fieldSearch(SearchObj searchObj) {
		

	}

	@Override
	public void export() {
		

	}

	@Override
	public String getPermission() {
		
		return null;
	}
}
