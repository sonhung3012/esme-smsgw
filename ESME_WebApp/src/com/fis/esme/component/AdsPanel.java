package com.fis.esme.component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class AdsPanel extends VerticalLayout {
	private Label lblText = null;

	public AdsPanel() {
		this.setHeight("25px");
		this.setStyleName("AdsPanel");
		initLayout();
	}

	private void initLayout() {
		lblText = new Label(
				"<marquee onmouseover='this.stop()' onmouseout='this.start()'>My scrolling text</marquee>",
				Label.CONTENT_XHTML);
		this.addComponent(lblText);
		this.setComponentAlignment(lblText, Alignment.MIDDLE_CENTER);
	}
}
