package com.fis.esme.component;

import com.fis.esme.MainWindow;
import com.fis.esme.admin.SessionData;
import com.fis.esme.util.MessageAlerter;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import eu.livotov.tpt.i18n.TM;

public class PanelSSOError extends Panel {

	private VerticalLayout mainLayout;
	private Button btnBack;
	private Label lblCaption;

	public PanelSSOError(String msisdn,final Window window) {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(true);

		lblCaption = new Label(TM.get(
				"subc.reg.parrent.msg.check.cf_existed-sso",
				msisdn), Label.CONTENT_XHTML);
		lblCaption.setStyleName("sso-error");
		lblCaption.setWidth("800px");
		// lblCaption.setSizeFull();
		mainLayout.addComponent(lblCaption);
		mainLayout.setComponentAlignment(lblCaption, Alignment.MIDDLE_CENTER);

//		MessageAlerter.showMessageI18n(
//				window,
//				TM.get("subc.reg.parrent.msg.check.cf_existed-sso",
//						SessionData.getCurrentMSISDN()));

		btnBack = new Button(TM.get("common.btn.back"));
		mainLayout.addComponent(btnBack);
		mainLayout.setComponentAlignment(btnBack, Alignment.MIDDLE_CENTER);

		btnBack.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				window.open(new ExternalResource(TM.get("potallink")), "");
			}
		});

		this.setContent(mainLayout);
	}

}
