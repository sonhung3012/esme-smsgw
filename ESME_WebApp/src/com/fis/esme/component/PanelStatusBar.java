package com.fis.esme.component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import eu.livotov.tpt.i18n.TM;

public class PanelStatusBar extends HorizontalLayout {
	private Label lblFormCaption;
	private Label lblCurrentFunc;

	private Label lblCurrentUser;

	// private Label lblLastIP;
	// private Label lblPasswordExpiredIn;

	public PanelStatusBar() {
		this(TM.get("menu.caption.system_home"), "test", "test", "test");
	}

	public PanelStatusBar(String formcaptoin, String currentIP, String lastIP,
			String passwordExpiredIn) {
		lblFormCaption = new Label(formcaptoin);
		lblCurrentFunc = new Label("", Label.CONTENT_XHTML);
		// lblCurrentIP = new Label(currentIP);
		// lblLastIP = new Label(lastIP);
		// lblPasswordExpiredIn = new Label(passwordExpiredIn);

		initLayout();
	}

	private void initLayout() {

		HorizontalLayout funcLayout = new HorizontalLayout();
		funcLayout.addComponent(lblCurrentFunc);
		funcLayout.addComponent(lblFormCaption);
		funcLayout.setComponentAlignment(lblCurrentFunc, Alignment.MIDDLE_LEFT);
		funcLayout.setComponentAlignment(lblFormCaption, Alignment.MIDDLE_LEFT);
		funcLayout.setSpacing(true);
		
		lblCurrentUser = new Label();
		HorizontalLayout userLayout = new HorizontalLayout();
		userLayout.setSpacing(true);
		userLayout.addComponent(lblCurrentUser);
		userLayout
				.setComponentAlignment(lblCurrentUser, Alignment.MIDDLE_RIGHT);
		

		// this.addComponent(new Label("Địa chỉ IP: "));
		// this.addComponent(lblCurrentIP);
		// this.addComponent(new Label("IP đăng nhập lần trước: "));
		// this.addComponent(lblLastIP);
		// this.addComponent(new Label("Tên đăng nhập: "));
		// this.addComponent(lblPasswordExpiredIn);
		this.addComponent(funcLayout);
		this.addComponent(userLayout);
		this.setComponentAlignment(funcLayout, Alignment.MIDDLE_LEFT);
		this.setComponentAlignment(userLayout, Alignment.MIDDLE_RIGHT);
	}

	public String getFormCaption() {
		return lblFormCaption.getCaption();
	}

	public void setFormCaption(String caption) {
		this.lblFormCaption.setValue(caption);
	}

	public void setUserCaption(String username) {
		this.lblCurrentUser.setValue(username);
	}

	// public String getCurrentIP()
	// {
	// return lblCurrentIP.getCaption();
	// }
	//
	// public void setCurrentIP(String currentIP)
	// {
	// this.lblCurrentIP.setCaption(currentIP);
	// }
	//
	// public String getLastIP()
	// {
	// return lblLastIP.getCaption();
	// }
	//
	// public void setLastIP(String lastIP)
	// {
	// this.lblLastIP.setCaption(lastIP);
	// }
	//
	// public String getPasswordExpiredIn()
	// {
	// return lblPasswordExpiredIn.getCaption();
	// }
	//
	// public void setPasswordExpiredIn(String passwordExpiredIn)
	// {
	// this.lblPasswordExpiredIn.setCaption(passwordExpiredIn);
	// }

	public void updateLanguage() {
		lblCurrentFunc.setValue(TM.get("main.window.statusbar.caption"));
		lblFormCaption.setValue(TM.get("menu.caption.system_home"));
	}
}
