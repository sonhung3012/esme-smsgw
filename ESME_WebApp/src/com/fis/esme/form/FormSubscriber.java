package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fis.esme.classes.PanelTreeProvider;
import com.fis.esme.client.GroupsDTTransfererClient;
import com.fis.esme.component.CommonButtonPanel;
import com.fis.esme.component.CommonTreePanel;
import com.fis.esme.component.ConfirmDeletionDialog;
import com.fis.esme.component.DecoratedTree;
import com.fis.esme.component.TabChangeProvider;
import com.fis.esme.groupsdt.GroupsDTTransferer;
import com.fis.esme.persistence.Groups;
import com.fis.esme.util.FisDefaultTheme;
import com.fis.esme.util.FormUtil;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import eu.livotov.tpt.gui.dialogs.OptionDialog.OptionDialogResultListener;
import eu.livotov.tpt.gui.dialogs.OptionKind;
import eu.livotov.tpt.i18n.TM;

public class FormSubscriber extends CustomComponent implements PanelTreeProvider, OptionDialogResultListener {

	private VerticalLayout mainPanel;
	private HorizontalLayout pnlActionLayout;
	private HorizontalSplitPanel mainLayout;
	private CommonTreePanel commonTree;
	private TabSheet tabsheet;
	public static Groups mcaActionRoot;
	private PanelTreeProvider selectedTab;
	private Object currentTreeNode;
	// private Object tempTreeNode;

	private final String OBJECT_ACTION_ROOT = TM.get("groups.caption");
	private ComboBox cboSearch;
	private Tree tree;

	private GroupsDTTransferer groupsService;

	private ArrayList<Groups> lstAction = new ArrayList<Groups>();
	private PanelSubscriber pnSmscParam;
	private CommonButtonPanel plnAction;

	private ConfirmDeletionDialog confirm;
	private PanelSubGroup pnSmsCommand;

	public FormSubscriber() throws Exception {

		this.setCaption(TM.get("subs.detail.form.caption"));
		initService();
		loadSmscFromDatabase();
		initObjActionRoot();
		initLayout();
		FormUtil.setTabEnabled(tabsheet);
	}

	private void initService() {

		try {
			groupsService = GroupsDTTransfererClient.getService();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void initLayout() throws Exception {

		mainLayout = new HorizontalSplitPanel();

		mainPanel = new VerticalLayout();
		mainPanel.setSizeFull();

		pnlActionLayout = new HorizontalLayout();
		pnlActionLayout.setSizeFull();
		// pnlActionLayout.setMargin(false, false, true, false);

		plnAction = new CommonButtonPanel(null);
		plnAction.showSearchPanel(true);
		plnAction.setEnableAlphabeltSearch(false);
		plnAction.setFromCaption(TM.get(FormSubscriber.class.getName()));
		pnlActionLayout.addComponent(plnAction);
		pnlActionLayout.setMargin(false);

		mainPanel.addComponent(pnlActionLayout);
		Panel mainPanelTemp = new Panel();
		mainPanelTemp.setSizeFull();
		pnlActionLayout.setHeight("110");
		mainPanelTemp.setContent(mainLayout);
		mainPanel.addComponent(mainPanelTemp);
		// mainPanel.setExpandRatio(pnlActionLayout, 0.2f);
		mainPanel.setExpandRatio(mainPanelTemp, 1f);
		mainPanel.setSpacing(true);

		this.setCompositionRoot(mainPanel);

		initComponentLeft();
		commonTree = new CommonTreePanel(tree, cboSearch, this);
		commonTree.setButtonSearchTooltip(TM.get("common.btn.search"));
		String searchTooltip = TM.get("subs.detail.tooltip.search");
		commonTree.setComboBoxSearchTooltip(searchTooltip);
		commonTree.setComBoxSearchInputPrompt(searchTooltip);
		initComponent();
		// if (lstService.size() > 0)
		// {
		// tree.select(lstService.get(0));
		// }
		// else
		// {
		// tree.select(mcaActionRoot);
		// }
	}

	private void loadSmscFromDatabase() {

		if (lstAction.size() <= 0) {
			try {
				lstAction.addAll(groupsService.findAllWithoutParameter());
			} catch (Exception e) {
				FormUtil.showException(this, e);
			}
		}
		Collections.sort(lstAction, FormUtil.stringComparator(true));

	}

	private void initObjActionRoot() {

		mcaActionRoot = new Groups();
		mcaActionRoot.setGroupId(-1l);
		mcaActionRoot.setDesciption("");
		mcaActionRoot.setStatus("1");
		mcaActionRoot.setName(OBJECT_ACTION_ROOT);
	}

	private void initComponent() {

		mainLayout.setSplitPosition(250, Sizeable.UNITS_PIXELS);
		mainLayout.setFirstComponent(commonTree);
		mainLayout.setSecondComponent(initComponentRight());
		tree.select(mcaActionRoot);
	}

	private void initComponentLeft() throws Exception {

		tree = new DecoratedTree();
		tree.addItem(mcaActionRoot);
		tree.setImmediate(true);
		tree.setNullSelectionAllowed(false);
		tree.setChildrenAllowed(mcaActionRoot, true);
		List<Groups> list = new ArrayList<Groups>();
		list.addAll(lstAction);
		cboSearch = new ComboBox();
		cboSearch.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		tree = new DecoratedTree();
		buildDataForTreeTable();
		tree.setStyleName("mca-normal-node");
		cboSearch.setContainerDataSource(tree.getContainerDataSource());
	}

	private List<Groups> getAllChildrenIsRoot(Groups parent, List<Groups> list) {

		List<Groups> listChildren = new ArrayList<Groups>();
		for (Groups esmeServices : list) {
			if ((esmeServices.getParentId() == -1)) {
				listChildren.add(esmeServices);
			}
		}
		return listChildren;
	}

	private List<Groups> getAllChildren(Groups parent, List<Groups> list) {

		List<Groups> listChildren = new ArrayList<Groups>();
		for (Groups esmeServices : list) {
			if ((esmeServices.getParentId() != -1)) {
				if (parent.getGroupId() == esmeServices.getParentId()) {
					listChildren.add(esmeServices);
				}
			}
		}
		return listChildren;
	}

	public void buildTreeNode(Groups parent, List<Groups> list) {

		for (Groups esmeServices : list) {
			if (esmeServices.getParentId() == parent.getGroupId()) {

				tree.addItem(esmeServices);
				tree.setItemIcon(esmeServices, FisDefaultTheme.ICON_SERVICE);
				tree.setParent(esmeServices, parent);
				tree.setChildrenAllowed(esmeServices, true);
				cboSearch.addItem(esmeServices);
				List<Groups> listTemp = getAllChildren(esmeServices, lstAction);
				if (listTemp.size() > 0) {
					buildTreeNode(esmeServices, listTemp);
				}
			}
		}
		tree.expandItemsRecursively(parent);
	}

	private void buildDataForTreeTable() throws Exception {

		tree.removeAllItems();
		List<Groups> listRootDepartment = null;
		listRootDepartment = getAllChildrenIsRoot(null, lstAction);

		tree.addItem(mcaActionRoot);
		tree.setNullSelectionAllowed(false);
		tree.setImmediate(true);
		tree.setChildrenAllowed(mcaActionRoot, true);
		for (Groups esmeServices : listRootDepartment) {

			tree.addItem(esmeServices);
			tree.setItemIcon(esmeServices, FisDefaultTheme.ICON_SERVICE);
			tree.setParent(esmeServices, mcaActionRoot);
			tree.setChildrenAllowed(esmeServices, true);
			cboSearch.addItem(esmeServices);
			buildTreeNode(esmeServices, getAllChildren(esmeServices, lstAction));
		}
		// tree.expandItem(esmeServiceRoot);
		tree.expandItemsRecursively(mcaActionRoot);
	}

	private TabSheet initComponentRight() {

		// pnInteraction = new PanelInteraction(this);
		// pnAdvertisement = new PanelAdvertisement(this);
		pnSmsCommand = new PanelSubGroup(this);
		pnSmscParam = new PanelSubscriber(this);
		// pnSmscRouting = new PanelSmscRouting(this);
		// pnMapParam = new PanelMapParam(this);
		// pnCommandParam = new PanelCommandParam(this);
		//
		tabsheet = new TabSheet();
		tabsheet.setSizeFull();
		tabsheet.addTab(pnSmscParam).setCaption(TM.get("subs.caption.subcriber.caption"));
		tabsheet.addTab(pnSmsCommand).setCaption(TM.get("subs.upload.file.caption"));
		tabsheet.setSelectedTab(pnSmscParam);
		tabsheet.addListener(new TabSheet.SelectedTabChangeListener() {

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {

				getSelectedTab(currentTreeNode);
			}
		});
		return tabsheet;
	}

	private void selectAndExpand(Object obj) {

		if (obj == null) {
			obj = OBJECT_ACTION_ROOT;
		}

		tree.select(obj);
		Object parent = tree.getParent(obj);
		tree.expandItem(parent);
	}

	@Override
	public void filterTree(Object obj) {

		selectAndExpand(obj);
	}

	@Override
	public void treeValueChanged(Object obj) {

		if (obj == null) {
			setCurrentTreeNode(mcaActionRoot);
			getSelectedTab(mcaActionRoot);
			return;
		}
		setCurrentTreeNode(obj);
		getSelectedTab(obj);
	}

	public Collection<?> getChildrenTreeNode(Object nodeID) {

		return tree.getChildren(nodeID);
	}

	public Object getParentTreeNode(Object nodeID) {

		return tree.getParent(nodeID);
	}

	public void addButtonPanel(CommonButtonPanel buttonPanel) {

		pnlActionLayout.removeAllComponents();
		pnlActionLayout.addComponent(buttonPanel);
	}

	public boolean isTreeNodeRoot(Object node) {

		return (tree.isRoot(node)) ? true : false;
	}

	public Object getTreeNodeRoot() {

		return mcaActionRoot;
	}

	public Object getCurrentTreeNode() {

		return currentTreeNode;
	}

	public void setCurrentTreeNode(Object currentTreeNode) {

		this.currentTreeNode = currentTreeNode;
	}

	public void treeSelect(Groups action) {

		tree.setSelectable(true);
		tree.select(action);
	}

	private void getSelectedTab(Object treeNode) {

		selectedTab = (PanelTreeProvider) tabsheet.getSelectedTab();
		// ((TabChangeProvider) selectedTab).loadButtonPanel();
		if (selectedTab == null || treeNode == null) {
			return;
		}
		if (selectedTab instanceof TabChangeProvider) {
			((TabChangeProvider) selectedTab).loadButtonPanel();
			((TabChangeProvider) selectedTab).loadForm();
		}
		selectedTab.treeValueChanged(treeNode);
	}

	private void confirmDeletion(String message) {

		if (confirm == null) {
			confirm = new ConfirmDeletionDialog(getApplication());
		}
		confirm.show(message, this);
	}

	@Override
	public void dialogClosed(OptionKind option) {

		if (OptionKind.OK.equals(option)) {

			// initDataSms();
		}
	}

	private class ActionCopyDialog extends Window implements Button.ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {

		}
	}

	public PanelSubscriber getPnSmscParam() {

		return pnSmscParam;
	}

}