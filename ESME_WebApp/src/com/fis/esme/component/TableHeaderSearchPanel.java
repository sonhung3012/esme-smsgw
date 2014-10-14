package com.fis.esme.component;

import com.fis.esme.util.SearchObj;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Reindeer;

import eu.livotov.tpt.i18n.TM;

public class TableHeaderSearchPanel extends VerticalLayout {
	private TableHeaderActionProvider provider;
	private int action = PanelActionProvider.ACTION_NONE;

	private String permision;

	protected TextField txtKey;
	protected ComboBox cboField;
	protected Button btnSearch;

	private Panel mainPanel;

	private HorizontalLayout mainLayout;

	public TableHeaderSearchPanel(TableHeaderActionProvider parent) {
		this.provider = parent;
		initComponent();
		intLayout();
		checkPermission();
	}

	private void intLayout() {
		this.setSizeFull();
		// -top init

		mainLayout.addComponent(txtKey);
		mainLayout.setComponentAlignment(txtKey, Alignment.MIDDLE_CENTER);
		mainLayout.addComponent(cboField);
		mainLayout.setComponentAlignment(cboField, Alignment.MIDDLE_CENTER);
		mainLayout.addComponent(btnSearch);
		mainLayout.setComponentAlignment(btnSearch, Alignment.MIDDLE_CENTER);

		this.addComponent(mainPanel);
	}

	public void setValueForCboField(String[] fields, String[] fieldsCaption) {
		for (int i = 0; i < fields.length; i++) {
			cboField.addItem(fields[i]);
			cboField.setItemCaption(fields[i], fieldsCaption[i]);
		}
	}

	private void initComponent() {

		mainLayout = new HorizontalLayout();
		mainLayout.setSizeFull();
		// mainLayout.setHeight("40px");

		txtKey = new TextField();
		txtKey.setInputPrompt(TM.get("menu.field.key.inputprompt"));
		cboField = new ComboBox();
		cboField.setInputPrompt(TM.get("menu.field.field.inputprompt"));

		ThemeResource icon = new ThemeResource("icons/32/top_find.png");
		btnSearch = createButton(icon);
		btnSearch.setDescription(TM.get("main.common.button.search.tooltip"));
		btnSearch.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				searchByKey();
			}
		});

		mainPanel = new Panel();
		mainPanel.setStyleName(Reindeer.PANEL_LIGHT);
		mainPanel.setSizeFull();
		mainPanel.setContent(mainLayout);

		mainPanel.addAction(new Button.ClickShortcut(btnSearch, KeyCode.ENTER));
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
		if (permision == null)
			return;
		btnSearch.setEnabled(getPermision().contains("F"));
	}

	public String getPermision() {
		return "IUDFS";
	}

	public void setPermision(String permision) {
		this.permision = permision;
	}

	private void fieldSearch(SearchObj searchObj) {
		// setButtonEnabled(false);
		if (action != PanelActionProvider.ACTION_NONE) {
			return;
		}
		this.action = PanelActionProvider.ACTION_SEARCH;
		provider.fieldSearch(searchObj);
	}

	public int getAction() {
		return action;
	}

	public void clearAction() {
		this.action = PanelActionProvider.ACTION_NONE;
	}

	private void searchByKey() {
		SearchObj searchObj = new SearchObj();
		searchObj.setField((cboField.getValue() == null) ? null : cboField
				.getValue().toString());
		searchObj.setKey(txtKey.getValue().toString());
		fieldSearch(searchObj);
		txtKey.focus();
	}

	public void setActionProvider(TableHeaderActionProvider provider) {
		this.provider = provider;
	}
	
}
