package com.fis.esme.component;

import com.fis.esme.classes.PanelTreeProvider;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import eu.livotov.tpt.i18n.TM;

public class CommonTreeTablePanel extends VerticalLayout {
	private VerticalLayout panelTree = new VerticalLayout();
	private HorizontalLayout panelButton = new HorizontalLayout();
	// private VerticalLayout verTree;
	private ComboBox cboSearch;
	private Button btnSearch = new Button();
	private HorizontalLayout horizon = new HorizontalLayout();
	private TreeTable tree;
	private PanelTreeProvider pnTreeProvider;
	private Button btnAction = null;

	public CommonTreeTablePanel(TreeTable tree, ComboBox cboSearch,
			Button btnAction, PanelTreeProvider treeprovider) {
		this.btnAction = btnAction;
		setSizeFull();
		setSpacing(false);
		this.tree = tree;
		this.cboSearch = cboSearch;
		this.pnTreeProvider = treeprovider;
		initLeftLayout();
		initComponentLeft();
	}

	public CommonTreeTablePanel(TreeTable tree, ComboBox cboSearch,
			PanelTreeProvider treeprovider) {
		this(tree, cboSearch, null, treeprovider);
		// addComponent(verTree);
	}

	private void initLeftLayout() {
		panelTree.addComponent(tree);
		panelTree.setMargin(false);
		panelTree.setSizeFull();
		panelTree.setSpacing(false);

		horizon.setSizeUndefined();
		horizon.setWidth("100%");
		horizon.addComponent(cboSearch);
		if (btnAction != null) {
			horizon.addComponent(btnAction);
			horizon.setComponentAlignment(btnAction, Alignment.MIDDLE_RIGHT);
		}
		// horizon.addComponent(btnSearch);
		// horizon.setComponentAlignment(btnSearch, Alignment.MIDDLE_RIGHT);
		cboSearch.setSizeFull();
		horizon.setExpandRatio(cboSearch, 1);
		horizon.setSpacing(true);
		// horizon.setExpandRatio(btnSearch, 1);

		ThemeResource icon = new ThemeResource("icons/16/search.png");
		btnSearch.setStyleName(BaseTheme.BUTTON_LINK);
		btnSearch.setIcon(icon);
		btnSearch.setDescription(TM.get("main.common.button.search.tooltip"));
		btnSearch.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				filterTree(cboSearch.getValue());
				// cboSearch.select(null);
				// cboSearch.focus();
			}
		});

		// verTree = new VerticalLayout();
		Label lbl = new Label();
		lbl.setHeight("3px");
		this.addComponent(lbl);
		this.addComponent(horizon);
		lbl = new Label();
		lbl.setHeight("3px");
		this.setSpacing(false);
		this.addComponent(lbl);
		this.addComponent(panelTree);
		// this.setComponentAlignment(horizon, Alignment.MIDDLE_RIGHT);
		this.setExpandRatio(panelTree, 1);
	}

	public void addFooterButtonLayout(Component com) {
		com.setHeight("30px");
		this.addComponent(com);
		this.setComponentAlignment(com, Alignment.MIDDLE_CENTER);
	}

	public void setVisibleTreePanelBorder(String style) {
		panelTree.setStyleName(style);
	}

	public void setComboboxSearchDescription(String desc) {
		cboSearch.setInputPrompt(desc);
		cboSearch.setDescription(desc);
	}

	private void initComponentLeft() {

		cboSearch.setInputPrompt(TM.get("main.common.tree.cboinput"));
		cboSearch.setImmediate(true);
		cboSearch.setScrollToSelectedItem(true);
		cboSearch.setDescription(TM.get("main.common.tree.cbotooltip"));
		cboSearch.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				filterTree(cboSearch.getValue());

			}
		});
		tree.setImmediate(true);
		tree.setNullSelectionAllowed(false);
		tree.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				treeValueChanged(tree.getValue());
			}
		});
		// tree.addListener(new ItemClickEvent.ItemClickListener() {
		// @Override
		// public void itemClick(ItemClickEvent event) {
		// Object item = event.getItemId();
		// if (event.isDoubleClick()) {
		// if (tree.isExpanded(item)) {
		// tree.collapseItem(event.getItemId());
		// } else {
		// tree.expandItem(event.getItemId());
		// }
		// }
		// }
		// });
	}

	public void setButtonSearchTooltip(String tooltip) {
		btnSearch.setDescription(tooltip);
	}

	public void setComboBoxSearchTooltip(String tooltip) {
		cboSearch.setDescription(tooltip);

	}

	public void setComBoxSearchInputPrompt(String inputPrompt) {
		cboSearch.setInputPrompt(inputPrompt);
	}

	private void filterTree(Object obj) {
		pnTreeProvider.filterTree(obj);
	}

	private void treeValueChanged(Object obj) {
		if (obj == null) {
			return;
		}
		pnTreeProvider.treeValueChanged(obj);
	}
}
