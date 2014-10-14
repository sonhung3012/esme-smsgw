package com.fis.esme.component;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import eu.livotov.tpt.i18n.TM;

public class ConfigPolicyButtonPanel extends VerticalLayout {
	private ConfigPolicyActionProvider provider;
	private int action = ConfigPolicyActionProvider.ACTION_NONE;
	private boolean delayedFocus;
	private String permision;

	private FormLayout cboChildLayout = new FormLayout();

	private Button btnCopy = null;
	private Button btnDelete = null;
	private Button btnCancel = null;

	private Button btnApply = null;
	private Button btnCancelApply = null;

	private HorizontalLayout btnLayout;
	private HorizontalLayout btnLayoutApply;

	private HorizontalLayout mainLayout;

	private int mode = 0;

	public ConfigPolicyButtonPanel(ConfigPolicyActionProvider parent) {
		this.provider = parent;
		// if (provider != null)
		// this.permision = provider.getPermission();
		initComponent();
		intLayout();
		checkPermission();
	}

	private void intLayout() {
		this.setSizeFull();

		btnLayout.addComponent(btnCopy);
		btnLayout.setComponentAlignment(btnCopy, Alignment.MIDDLE_RIGHT);
		btnLayout.addComponent(btnDelete);
		btnLayout.setComponentAlignment(btnDelete, Alignment.MIDDLE_RIGHT);
		btnLayout.addComponent(btnCancel);
		btnLayout.setComponentAlignment(btnCancel, Alignment.MIDDLE_RIGHT);

		btnLayoutApply.addComponent(btnApply);
		btnLayoutApply.setComponentAlignment(btnApply, Alignment.MIDDLE_RIGHT);
		btnLayoutApply.addComponent(btnCancelApply);
		btnLayoutApply.setComponentAlignment(btnCancelApply,
				Alignment.MIDDLE_RIGHT);

		mainLayout = new HorizontalLayout();
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.addComponent(btnLayout);
		mainLayout.setComponentAlignment(btnLayout, Alignment.MIDDLE_RIGHT);
		this.addComponent(cboChildLayout);
		this.addComponent(mainLayout);
		this.setSizeFull();
		this.setMargin(false);

	}

	public void setVisibleCboChild(int mode) {
		if (mode == 0) {
			mainLayout.removeAllComponents();
			mainLayout.addComponent(btnLayoutApply);
			mainLayout.setComponentAlignment(btnLayoutApply,
					Alignment.MIDDLE_RIGHT);
//			System.out.println(mainLayout.getComponentCount());
			mode = 1;
		} else if (mode == 1) {
			mainLayout.removeAllComponents();
			mainLayout.addComponent(btnLayout);
			mainLayout.setComponentAlignment(btnLayout, Alignment.MIDDLE_RIGHT);
			mode = 0;
		}
//		System.out.println("mode:" + mode);
	}

	private void initComponent() {

		// ThemeResource icon = new ThemeResource("icons/32/top_add.png");
		// btnCopy = createButton(icon);
		// btnCopy.setDescription(TM.get("main.common.button.add.tooltip"));
		// btnCopy.addListener(new Button.ClickListener() {
		// public void buttonClick(ClickEvent event) {
		//
		// }
		// });

		btnLayout = new HorizontalLayout();
		btnLayout.setSizeFull();
		btnLayout.setWidth("200px");
		btnLayout.setMargin(false);

		btnLayoutApply = new HorizontalLayout();
		btnLayoutApply.setSizeFull();
		btnLayoutApply.setWidth("150px");
		btnLayoutApply.setMargin(false);

		btnCopy = new Button(TM.get("table.common.btn.copy.caption"));
		btnCopy.setDescription(TM.get("table.common.btn.copy.description"));
		btnCopy.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				setVisibleCboChild(0);
				provider.cpCopy();
			}
		});

		btnDelete = new Button(TM.get("table.common.btn.delete.caption"));
		btnDelete.setDescription(TM.get("table.common.btn.delete.description"));
		btnDelete.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				provider.cpDelete();
			}
		});

		btnCancel = new Button(TM.get("table.common.btn.cancel.caption"));
		btnCancel.setDescription(TM.get("table.common.btn.cancel.description"));
		btnCancel.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				provider.cpCancel();
			}
		});

		btnCancelApply = new Button(TM.get("table.common.btn.cancel.caption"));
		btnCancelApply.setDescription(TM
				.get("table.common.btn.cancel.description"));
		btnCancelApply.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				setVisibleCboChild(1);
				provider.cpCancelApply();
			}
		});

		btnApply = new Button(TM.get("table.common.btn.apply.caption"));
		btnApply.setDescription(TM.get("table.common.btn.apply.description"));
		btnApply.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				provider.cpApply();
			}
		});

	}

	private Button createButton(ThemeResource icon) {
		Button btn = new Button();
		btn.setStyleName(BaseTheme.BUTTON_LINK);
		btn.setIcon(icon);
		btn.setWidth("32px");
		btn.setHeight("32px");
		return btn;
	}

	public void checkPermission() {

		System.out.println("permision:" + getPermision());

		if (permision == null)
			return;
		// btnAdd.setEnabled(getPermision().contains("I"));

	}

	public String getPermision() {
		return permision;
	}

	public void setPermision(String permision) {
		this.permision = permision;
	}

	public int getAction() {
		return action;
	}

	public void clearAction() {
		this.action = PanelActionProvider.ACTION_NONE;
	}

	public void setButtonEnabled(boolean enabled) {
		// btnAdd.setEnabled(enabled);
		// btnReregister.setEnabled(enabled);
		// btnRevenueReport.setEnabled(enabled);
		// btnKIPReport.setEnabled(enabled);
		// btnSearchOrAdd.setEnabled(enabled);
		// btnSubcSearch.setEnabled(enabled);
	}

	public void setRowSelected(boolean selected) {

	}

}
