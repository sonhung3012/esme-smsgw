package com.fis.esme.form;

import com.fis.esme.util.Versioning;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

import eu.livotov.tpt.i18n.TM;

public class DialogAbout extends Window
{
	private VerticalLayout mainLayout;
	private VerticalLayout vertitle;
	
	public DialogAbout()
	{
		super(TM.get("app.help.title", Versioning.APP_NAME, Versioning.APP_VERSION));
		this.setWidth("500px");
		this.setHeight("450px");
		this.setModal(true);
		this.setResizable(false);
		this.center();
		this.setCloseShortcut(KeyCode.ESCAPE);
		initabout();
	}
	
	public void initabout()
	{
		mainLayout = (VerticalLayout)getContent();
		vertitle = new VerticalLayout();
		Panel mainInfo = new Panel();
		mainInfo.setWidth("100%");
		mainInfo.setHeight("250px");
		
		HorizontalLayout pnlImg = new HorizontalLayout();
		pnlImg.setWidth("100%");
		pnlImg.setHeight("247px");
		
		Embedded em = new Embedded("", new ThemeResource("images/logo.jpg"));
		em.setMimeType("image/jpg");
		pnlImg.addComponent(em);
		mainInfo.setContent(pnlImg);
		
		pnlImg.setComponentAlignment(em, Alignment.MIDDLE_CENTER);
		
		Label lbinfor = new Label(
				"<div align='center' style='font-weight:bold'>"+TM.get("app.help.company")+"</div>"
						+ "<div align='center'>"+TM.get("app.help.head")+"<div>"
						+ "<div align='center'>"+TM.get("app.help.address")+"<div>"
						+ "<div align='center'>"+TM.get("app.help.phone")+"<div>",
				Label.CONTENT_XHTML);
		lbinfor.setSizeFull();
		Link link = new Link("www.fis.com.vn",
		        new ExternalResource("http://fis.com.vn/"));
//		Button btnLink = new Button("www.fis.com.vn");
//		btnLink.setStyleName(BaseTheme.BUTTON_LINK);
		
		Button btnclose = new Button(TM.get("app.help.button"));
		btnclose.addListener(new ClickListener()
		{
			
			@Override
			public void buttonClick(ClickEvent event)
			{
				close();
			}
		});
		vertitle.addComponent(mainInfo);
		vertitle.addComponent(lbinfor);
		vertitle.addComponent(link);
		vertitle.setComponentAlignment(link, Alignment.TOP_CENTER);
		vertitle.setSpacing(true);
		vertitle.setExpandRatio(mainInfo, 1.0f);
		mainLayout.addComponent(vertitle);
		mainLayout.addComponent(btnclose);
		mainLayout.setSpacing(true);
		mainLayout.setComponentAlignment(vertitle, Alignment.TOP_CENTER);
		mainLayout.setComponentAlignment(btnclose, Alignment.TOP_RIGHT);
		mainLayout.setMargin(true, true, false, true);
		btnclose.focus();
		this.setContent(mainLayout);
		this.setTheme("mca");
	}
}
