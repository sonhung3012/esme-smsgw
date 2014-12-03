package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.PanelTreeProvider;
import com.fis.esme.component.CommonButtonPanel;
import com.fis.esme.component.CommonTreePanel;
import com.fis.esme.component.ConfirmDeletionDialog;
import com.fis.esme.component.DecoratedTree;
import com.fis.esme.component.TabChangeProvider;
import com.fis.esme.fileupload.FileUploadTransferer;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.service.ServiceTransferer;
import com.fis.esme.util.CacheDB;
import com.fis.esme.util.FisDefaultTheme;
import com.fis.esme.util.FormUtil;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

import eu.livotov.tpt.gui.dialogs.OptionDialog.OptionDialogResultListener;
import eu.livotov.tpt.gui.dialogs.OptionKind;
import eu.livotov.tpt.i18n.TM;

public class FormFileUploadDetail extends CustomComponent implements PanelTreeProvider, OptionDialogResultListener {

	private VerticalLayout mainPanel;
	private HorizontalSplitPanel mainLayout;
	private CommonTreePanel commonTree;
	private TabSheet tabsheet;
	private PanelTreeProvider selectedTab;
	private Object currentTreeNode;

	private HorizontalLayout pnlActionLayout;
	private CommonButtonPanel plnAction;

	private EsmeServices esmeServiceRoot;
	private static List<EsmeServices> listChildOfCurnNode = new ArrayList<EsmeServices>();

	private final String OBJECT_ACTION_ROOT = TM.get("services.caption");
	private ComboBox cboSearch;
	private Tree tree;

	private ServiceTransferer service;

	private PanelFileUpload pnFileUpload;
	private PanelMT pnMT;

	private FileUploadTransferer fileUploadService;
	// private MapParamTransferer mapParamSevice;

	private ConfirmDeletionDialog confirm;

	public FormFileUploadDetail() throws Exception {

		this.setCaption(TM.get("com.fis.esme.form.FormFileUploadDetail"));
		initService();
		initServiceParam();
		loadSmscFromDatabase();
		initObjActionRoot();
		initLayout();
		FormUtil.setTabEnabled(tabsheet);
	}

	private void initService() {

		try {
			service = CacheServiceClient.serviceService;
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void initServiceParam() {

		try {
			fileUploadService = CacheServiceClient.fileUploadService;
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
		plnAction.setFromCaption(TM.get(FormFileUploadDetail.class.getName()));
		plnAction.setEnableBottomPanel(false);
		pnlActionLayout.addComponent(plnAction);
		pnlActionLayout.setMargin(false);

		mainPanel.addComponent(pnlActionLayout);
		Panel mainPanelTemp = new Panel();
		mainPanelTemp.setSizeFull();
		pnlActionLayout.setHeight("75");
		mainPanelTemp.setContent(mainLayout);
		mainPanel.addComponent(mainPanelTemp);
		mainPanel.setExpandRatio(mainPanelTemp, 1f);
		mainPanel.setSpacing(true);

		this.setCompositionRoot(mainPanel);

		initComponentLeft();
		commonTree = new CommonTreePanel(tree, cboSearch, this);
		commonTree.setButtonSearchTooltip(TM.get("common.btn.search"));
		String searchTooltip = TM.get("fileupload.detail.tooltip.search");
		commonTree.setComboBoxSearchTooltip(searchTooltip);
		commonTree.setComBoxSearchInputPrompt(searchTooltip);
		initComponent();
	}

	private void loadSmscFromDatabase() {

		if (CacheDB.cacheService.size() <= 0) {
			try {
				CacheDB.cacheService.addAll(service.findAllWithoutParameter());
			} catch (Exception e) {
				FormUtil.showException(this, e);
			}
		}
		Collections.sort(CacheDB.cacheService, FormUtil.stringComparator(true));

	}

	private void initObjActionRoot() {

		esmeServiceRoot = new EsmeServices();
		esmeServiceRoot.setServiceId(0);
		esmeServiceRoot.setDesciption("");
		esmeServiceRoot.setStatus("1");
		esmeServiceRoot.setName(OBJECT_ACTION_ROOT);
	}

	private void initComponent() {

		mainLayout.setSplitPosition(20, Sizeable.UNITS_PERCENTAGE);
		mainLayout.setFirstComponent(commonTree);
		mainLayout.setSecondComponent(initComponentRight());
		tree.select(esmeServiceRoot);
	}

	private void initComponentLeft() {

		List<EsmeServices> list = new ArrayList<EsmeServices>();
		list.addAll(CacheDB.cacheService);
		cboSearch = new ComboBox();
		cboSearch.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		tree = new DecoratedTree();
		buildDataForTreeTable();
		tree.setStyleName("mca-normal-node");
		cboSearch.setContainerDataSource(tree.getContainerDataSource());

	}

	public void buildTreeNode(EsmeServices parent, List<EsmeServices> list) {

		for (EsmeServices esmeServices : list) {
			if (esmeServices.getParentId() == parent.getServiceId()) {

				tree.addItem(esmeServices);
				tree.setItemIcon(esmeServices, FisDefaultTheme.ICON_SERVICE);
				tree.setParent(esmeServices, parent);
				tree.setChildrenAllowed(esmeServices, true);
				cboSearch.addItem(esmeServices);

				List<EsmeServices> listTemp = getAllChildren(esmeServices, CacheDB.cacheService);
				if (listTemp.size() > 0) {
					buildTreeNode(esmeServices, listTemp);
				}
			}
		}
		tree.expandItemsRecursively(parent);
		// tree.expandItem(parent);
	}

	private void buildDataForTreeTable() {

		tree.removeAllItems();
		List<EsmeServices> listRootDepartment = null;
		listRootDepartment = getAllChildrenIsRoot(null, CacheDB.cacheService);

		tree.addItem(esmeServiceRoot);
		tree.setNullSelectionAllowed(false);
		tree.setImmediate(true);
		tree.setChildrenAllowed(esmeServiceRoot, true);
		for (EsmeServices esmeServices : listRootDepartment) {

			tree.addItem(esmeServices);
			tree.setItemIcon(esmeServices, FisDefaultTheme.ICON_SERVICE);
			tree.setParent(esmeServices, esmeServiceRoot);
			tree.setChildrenAllowed(esmeServices, true);
			cboSearch.addItem(esmeServices);
			buildTreeNode(esmeServices, getAllChildren(esmeServices, CacheDB.cacheService));
		}

		tree.expandItemsRecursively(esmeServiceRoot);
	}

	private List<EsmeServices> getAllChildrenIsRoot(EsmeServices parent, List<EsmeServices> list) {

		List<EsmeServices> listChildren = new ArrayList<EsmeServices>();
		for (EsmeServices esmeServices : list) {
			if ((esmeServices.getParentId() == -1)) {
				listChildren.add(esmeServices);
			}
		}
		return listChildren;
	}

	private List<EsmeServices> getAllChildren(EsmeServices parent, List<EsmeServices> list) {

		List<EsmeServices> listChildren = new ArrayList<EsmeServices>();
		for (EsmeServices esmeServices : list) {
			if ((esmeServices.getParentId() != -1)) {
				if (parent.getServiceId() == esmeServices.getParentId()) {
					listChildren.add(esmeServices);
				}
			}
		}
		return listChildren;
	}

	private List<EsmeServices> getAllChildByParentOnTree(List<EsmeServices> list, Object item, boolean clear) {

		if (clear)
			listChildOfCurnNode.clear();
		getAllChildByParentOnTree(list, item);
		return listChildOfCurnNode;
	}

	private void getAllChildByParentOnTree(List<EsmeServices> list, Object item) {

		Collection<EsmeServices> coll = (Collection<EsmeServices>) tree.getChildren(item);
		if (coll == null)
			return;
		if (coll.size() > 0) {
			for (EsmeServices esmeServices : coll) {
				listChildOfCurnNode.add(esmeServices);
				getAllChildByParentOnTree(listChildOfCurnNode, esmeServices);
			}
		}
	}

	private TabSheet initComponentRight() {

		pnMT = new PanelMT(this);
		pnFileUpload = new PanelFileUpload(this);

		tabsheet = new TabSheet();
		tabsheet.setSizeFull();
		tabsheet.addTab(pnMT).setCaption(TM.get("smsMT.caption"));
		tabsheet.addTab(pnFileUpload).setCaption(TM.get("panelfileupload.caption"));
		tabsheet.setSelectedTab(pnMT);
		tabsheet.addListener(new TabSheet.SelectedTabChangeListener() {

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {

				// selectedTab = (PanelTreeProvider) tabsheet.getSelectedTab();
				// selectedTab.treeValueChanged(currentTreeNode);
				getSelectedTab(currentTreeNode);

				// pnAdvertisement.getContainer().removeClickShortcut();
				// pnInteraction.getContainer().removeClickShortcut();
				// pnSmsCommand.getContainer().removeClickShortcut();
				//
				// if (pnAdvertisement.equals(selectedTab))
				// {
				// pnAdvertisement.getContainer().setSearchShortcut();
				// }
				// else if (pnInteraction.equals(selectedTab))
				// {
				// pnInteraction.getContainer().setSearchShortcut();
				// }
				// else if (pnSmsCommand.equals(selectedTab))
				// {
				// pnSmsCommand.getContainer().setSearchShortcut();
				// }

				// if (pnInteraction.equals(selectedTab)) {
				// pnInteraction.getContainer().setSearchShortcut();
				// }

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
			setCurrentTreeNode(esmeServiceRoot);
			getSelectedTab(esmeServiceRoot);
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

		return esmeServiceRoot;
	}

	public Object getCurrentTreeNode() {

		return currentTreeNode;
	}

	public void setCurrentTreeNode(Object currentTreeNode) {

		this.currentTreeNode = currentTreeNode;
	}

	public void treeSelect(EsmeSmsc action) {

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

}
