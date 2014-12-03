package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.PanelTreeProvider;
import com.fis.esme.client.SmscParamTransfererClient;
import com.fis.esme.client.SmscTransfererClient;
import com.fis.esme.component.CommonButtonPanel;
import com.fis.esme.component.CommonTreePanel;
import com.fis.esme.component.ConfirmDeletionDialog;
import com.fis.esme.component.DecoratedTree;
import com.fis.esme.component.TabChangeProvider;
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.persistence.EsmeSmscParam;
import com.fis.esme.smsc.SmscTransferer;
import com.fis.esme.smscparam.SmscParamTransferer;
import com.fis.esme.util.CacheDB;
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

public class FormSmscDetail extends CustomComponent implements PanelTreeProvider, OptionDialogResultListener {

	private VerticalLayout mainPanel;
	private HorizontalLayout pnlActionLayout;
	private HorizontalSplitPanel mainLayout;
	private CommonTreePanel commonTree;
	private TabSheet tabsheet;
	public static EsmeSmsc mcaActionRoot;
	private PanelTreeProvider selectedTab;
	private Object currentTreeNode;
	// private Object tempTreeNode;

	private final String OBJECT_ACTION_ROOT = TM.get("smsc.detail.tree.RootCaption");
	private ComboBox cboSearch;
	private Tree tree;

	private SmscTransferer smscService;
	// private ServiceTransferer serviceService;

	private ArrayList<EsmeSmsc> lstAction = new ArrayList<EsmeSmsc>();
	// private ArrayList<PrcService> lstService = new ArrayList<PrcService>();

	// private ArrayList<PrcInteraction> arrInteraction = new
	// ArrayList<PrcInteraction>();
	// private ArrayList<PrcAdvertisement> arrAdvertisement = new
	// ArrayList<PrcAdvertisement>();
	// private ArrayList<PrcSmsCommand> arrSmsCommand = new
	// ArrayList<PrcSmsCommand>();
	// private ArrayList<PrcCommandParam> arrCammandParam = new
	// ArrayList<PrcCommandParam>();
	// private ArrayList<PrcMapParam> arrMapParam = new
	// ArrayList<PrcMapParam>();
	private ArrayList<EsmeSmscParam> arrAcionParam = new ArrayList<EsmeSmscParam>();

	// private PanelInteraction pnInteraction;
	// private PanelAdvertisement pnAdvertisement;
	private PanelSmscRouting pnSmscRouting;
	private PanelSmscParam pnSmscParam;
	// private PanelMapParam pnMapParam;
	// private PanelCommandParam pnCommandParam;
	private CommonButtonPanel plnAction;

	// private InteractionTransferer interactionService;
	// private AdvertisementTransferer advertisementService;
	// private SmsCommandTransferer smsCommandService;
	// private CommandParamTransferer commandParamService;
	private SmscParamTransferer smscParamService;
	// private MapParamTransferer mapParamSevice;

	private ConfirmDeletionDialog confirm;

	public FormSmscDetail() throws Exception {

		this.setCaption(TM.get("smsc.detail.form.caption"));
		initService();
		initServiceParam();
		loadSmscFromDatabase();
		initObjActionRoot();
		initLayout();
		FormUtil.setTabEnabled(tabsheet);
	}

	private void initService() {

		try {
			smscService = SmscTransfererClient.getService();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void initServiceParam() {

		try {
			smscParamService = SmscParamTransfererClient.getService();
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
		plnAction.setFromCaption(TM.get(FormSmscDetail.class.getName()));
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
		String searchTooltip = TM.get("smsc.detail.tooltip.search");
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
				lstAction.addAll(smscService.findAllWithoutParameter());
			} catch (Exception e) {
				FormUtil.showException(this, e);
			}
		}
		Collections.sort(lstAction, FormUtil.stringComparator(true));

	}

	private void initObjActionRoot() {

		mcaActionRoot = new EsmeSmsc();
		mcaActionRoot.setSmscId(0);
		mcaActionRoot.setCode("");
		mcaActionRoot.setDesciption("");
		mcaActionRoot.setStatus("1");
		mcaActionRoot.setName(OBJECT_ACTION_ROOT);
	}

	private void initComponent() {

		mainLayout.setSplitPosition(20, Sizeable.UNITS_PERCENTAGE);
		mainLayout.setFirstComponent(commonTree);
		mainLayout.setSecondComponent(initComponentRight());
		tree.select(mcaActionRoot);
	}

	private void initComponentLeft() throws Exception {

		// cboSearch = new ComboBox();
		// cboSearch.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		tree = new DecoratedTree();
		tree.addItem(mcaActionRoot);
		tree.setImmediate(true);
		tree.setNullSelectionAllowed(false);
		tree.setChildrenAllowed(mcaActionRoot, true);

		// for (int i = 0; i < lstService.size(); i++) {
		// PrcService mcaService = lstService.get(i);
		// if (mcaService.getStatus().equals("1")) {
		// tree.addItem(mcaService);
		// tree.setParent(mcaService, mcaActionRoot);
		// tree.setChildrenAllowed(mcaService, false);
		// cboSearch.addItem(mcaService);
		// for (int j = 0; j < lstAction.size(); j++) {
		// EsmeSmsc mcaAction = lstAction.get(j);
		// if (mcaAction.getStatus().equals("1")) {
		// PrcService _service = mcaAction.getPrcService();
		// if (mcaService.getServiceId() == _service
		// .getServiceId()) {
		// tree.setChildrenAllowed(mcaService, true);
		// tree.addItem(mcaAction);
		// tree.setParent(mcaAction, mcaService);
		// tree.setChildrenAllowed(mcaAction, false);
		// }
		//
		// cboSearch.addItem(mcaAction);
		// }
		// }
		// }
		//
		// }
		List<EsmeSmsc> list = new ArrayList<EsmeSmsc>();
		list.addAll(CacheDB.cacheSmsc);
		// System.out.println("list:" + list.size());
		cboSearch = new ComboBox();
		cboSearch.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		tree = new DecoratedTree();
		buildDataForTreeTable();
		tree.setStyleName("mca-normal-node");
		cboSearch.setContainerDataSource(tree.getContainerDataSource());
		// tree.expandItemsRecursively(mcaActionRoot);
	}

	private void buildDataForTreeTable() throws Exception {

		// servicesData.removeAllItems();
		tree.removeAllItems();
		EsmeSmsc smsc = new EsmeSmsc();
		// smsc.setStatus("1");

		List<EsmeSmsc> listRootDepartment = new ArrayList<EsmeSmsc>();
		listRootDepartment = CacheServiceClient.smscService.findAllWithOrderPaging(smsc, null, false, -1, -1, true);
		// listRootDepartment = getAllChildrenIsRoot(null, CacheDB.cacheSmsc);

		// container.setDataForCboSearch(listRootDepartment);

		// data.addBean(departmentRoot);
		// treeTable.setCollapsed(departmentRoot, false);
		// System.out.println("tree size " + listRootDepartment.size());
		tree.addItem(mcaActionRoot);
		tree.setNullSelectionAllowed(false);
		tree.setImmediate(true);
		tree.setChildrenAllowed(mcaActionRoot, true);
		for (EsmeSmsc esmeServices : listRootDepartment) {

			tree.addItem(esmeServices);
			tree.setItemIcon(esmeServices, FisDefaultTheme.ICON_SERVICE);
			tree.setParent(esmeServices, mcaActionRoot);
			tree.setChildrenAllowed(esmeServices, true);
			cboSearch.addItem(esmeServices);
			// buildTree1(parent, list);

			// dataSevices.addBean(esmeServices);
			// treeTable.setParent(voipDepartment, departmentRoot);;
			// buildTreeNode(esmeServices,
			// getAllChildren(esmeServices, CacheDB.cacheSmsc));
		}
		// tree.expandItem(esmeSmscRoot);
		tree.expandItemsRecursively(mcaActionRoot);
	}

	private TabSheet initComponentRight() {

		// pnInteraction = new PanelInteraction(this);
		// pnAdvertisement = new PanelAdvertisement(this);
		// pnSmsCommand = new PanelSMSCommand(this);
		pnSmscParam = new PanelSmscParam(this);
		pnSmscRouting = new PanelSmscRouting(this);
		// pnMapParam = new PanelMapParam(this);
		// pnCommandParam = new PanelCommandParam(this);
		//
		tabsheet = new TabSheet();
		tabsheet.setSizeFull();
		// tabsheet.addTab(pnInteraction).setCaption(
		// TM.get("itrdt.tabsheet_pninteraction.caption"));
		// tabsheet.addTab(pnAdvertisement).setCaption(
		// TM.get("itrdt.tabsheet_pnadvertisement.caption"));
		// tabsheet.addTab(pnSmsCommand).setCaption(
		// TM.get("itrdt.tabsheet_pnsmscommand.caption"));
		tabsheet.addTab(pnSmscParam).setCaption(TM.get("smscparam.caption"));
		tabsheet.addTab(pnSmscRouting).setCaption(TM.get("smscRouting.caption"));
		// tabsheet.addTab(pnMapParam).setCaption(
		// TM.get("form.interact.detail.tab.mappa"));
		tabsheet.setSelectedTab(pnSmscParam);
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

	// public ArrayList<EsmeSmsc> findActionsByService(PrcService service) {
	// ArrayList<EsmeSmsc> list = new ArrayList<EsmeSmsc>();
	// for (EsmeSmsc mcaAction : lstAction) {
	// if (mcaAction.getPrcService().equals(service))
	// list.add(mcaAction);
	// }
	// return list;
	// }
	public void treeSelect(EsmeSmsc action) {

		tree.setSelectable(true);
		tree.select(action);
	}

	// public ArrayList<EsmeSmsc> findActionsInServiceByStatus(
	// PrcService service, String status) {
	// ArrayList<EsmeSmsc> list = new ArrayList<EsmeSmsc>();
	// for (EsmeSmsc mcaAction : lstAction) {
	// if (mcaAction.getPrcService().equals(service)
	// && mcaAction.getStatus().equals(status))
	// list.add(mcaAction);
	// }
	// return list;
	// }

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

	// private void initDataSms() {
	//
	// try {
	// if (arrInteraction.size() <= 0) {
	//
	// arrInteraction.clear();
	// arrInteraction.addAll(smsCommandService
	// .findByInteraction(((EsmeSmsc) getCurrentTreeNode())
	// .getActionId()));
	// }
	//
	// if (arrAdvertisement.size() <= 0) {
	//
	// arrAdvertisement.clear();
	// arrAdvertisement.addAll(smsCommandService
	// .findByAdvertisement(((EsmeSmsc) getCurrentTreeNode())
	// .getActionId()));
	// }
	// if (arrSmsCommand.size() <= 0) {
	//
	// arrSmsCommand.clear();
	// arrSmsCommand.addAll(smsCommandService
	// .findBySmsCommand(((EsmeSmsc) getCurrentTreeNode())
	// .getActionId()));
	// }
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	//
	// }

	@Override
	public void dialogClosed(OptionKind option) {

		if (OptionKind.OK.equals(option)) {

			// initDataSms();
		}
	}

	private class ActionCopyDialog extends Window implements Button.ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {

			// TODO Auto-generated method stub

		}
		//
		// private Form frm = new Form();
		// private ActionFieldFactory fieldFactory;
		// private VerticalLayout mainLayout = new VerticalLayout();
		// private VerticalLayout btnLayout = new VerticalLayout();
		// private HorizontalLayout hoLayout = new HorizontalLayout();
		// private Button btnAdd = new Button("Đồng ý");
		// private Button btnCancel = new Button("Hủy");
		// private EsmeSmsc actionObj;
		// private BeanItem<EsmeSmsc> item;
		//
		// public ActionCopyDialog(String title) {
		//
		// initLayout();
		// this.setContent(mainLayout);
		// this.setWidth("450px");
		// this.setHeight("570px");
		// this.setModal(true);
		// this.setResizable(false);
		// this.setCaption(title);
		//
		// }
		//
		// public ActionCopyDialog() {
		//
		// this(TM.get(CommercialOfferDialog.class.getName()));
		// }
		//
		// public void initLayout() {
		//
		// actionObj = (EsmeSmsc) getCurrentTreeNode();
		// item = new BeanItem<EsmeSmsc>(actionObj);
		// frm.setWriteThrough(false);
		// frm.setInvalidCommitted(false);
		// frm.setImmediate(false);
		// fieldFactory = new ActionFieldFactory();
		// frm.setFormFieldFactory(fieldFactory);
		// frm.setItemDataSource(item);
		// frm.setVisibleItemProperties(TM.get("action.Col_VisibleForm")
		// .split(","));
		// frm.setValidationVisible(false);
		// frm.focus();
		//
		// btnAdd.setWidth("100px");
		// btnAdd.addListener(this);
		// btnCancel.setWidth("100px");
		// btnCancel.addListener(this);
		// hoLayout.addComponent(btnAdd);
		// hoLayout.addComponent(btnCancel);
		//
		// hoLayout.setSpacing(true);
		// hoLayout.setMargin(true, false, true, false);
		//
		// btnLayout.addComponent(hoLayout);
		// btnLayout.setComponentAlignment(hoLayout, Alignment.MIDDLE_CENTER);
		// mainLayout.addComponent(frm);
		// mainLayout.addComponent(btnLayout);
		// mainLayout.setExpandRatio(frm, 1.0f);
		// mainLayout.setSizeFull();
		// }
		//
		// public boolean isValid() {
		// boolean valid = true;
		// for (final Iterator<?> i = frm.getItemPropertyIds().iterator(); i
		// .hasNext();) {
		// Field field = frm.getField(i.next());
		//
		// if (field instanceof Table) {
		// return true;
		// }
		//
		// if (!field.isValid()) {
		// field.focus();
		// if (field instanceof AbstractTextField) {
		// ((AbstractTextField) field).selectAll();
		// }
		// frm.setValidationVisible(true);
		// return false;
		// }
		// }
		// return valid;
		// }
		//
		// private void addCopyData(EsmeSmsc msv) {
		//
		// try {
		// if (arrInteraction.size() > 0) {
		// for (PrcInteraction prcInter : arrInteraction) {
		// prcInter.setEsmeSmsc(msv);
		//
		// interactionService.addInteraction(prcInter);
		//
		// }
		// }
		//
		// if (arrAdvertisement.size() > 0) {
		//
		// for (PrcAdvertisement prcAdver : arrAdvertisement) {
		// prcAdver.setEsmeSmsc(msv);
		// advertisementService.add(prcAdver);
		// }
		// }
		//
		// if (arrSmsCommand.size() > 0) {
		//
		// for (PrcSmsCommand smsCommand : arrSmsCommand) {
		//
		// arrCammandParam.clear();
		// arrCammandParam.addAll(smsCommandService
		// .findByCommandParam(smsCommand.getCommandId()));
		// smsCommand.setEsmeSmsc(msv);
		// long c = smsCommandService.addCommand(smsCommand, -1);
		// smsCommand.setCommandId(c);
		//
		// for (PrcCommandParam cmdParam : arrCammandParam) {
		//
		// arrMapParam.clear();
		// arrMapParam.addAll(smsCommandService
		// .findByMapParam(cmdParam.getParamId()));
		// if (arrMapParam.size() >= 1) {
		// cmdParam.setPrcSmsCommand(smsCommand);
		// long a = commandParamService.add(cmdParam);
		// cmdParam.setParamId(a);
		//
		// for (PrcMapParam mapParam : arrMapParam) {
		// arrAcionParam.clear();
		// arrAcionParam.addAll(smsCommandService
		// .findByActionParam(mapParam
		// .getActionid()));
		//
		// for (EsmeSmscParam actionParam : arrAcionParam) {
		//
		// actionParam.setEsmeSmsc(msv);
		// long b = actionParamService
		// .add(actionParam);
		// actionParam.setParamId(b);
		// if (b > 0) {
		// boolean check = mapParamSevice
		// .checkMapParamExisted(cmdParam
		// .getParamId(),
		// actionParam
		// .getParamId());
		// if (check == false) {
		//
		// mapParamSevice.insertMapParam(b, a);
		// }
		//
		// }
		//
		// }
		// }
		// }
		//
		// }
		// }
		//
		// }
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// }
		//
		// }
		//
		// private void loadTree(EsmeSmsc msv){
		//
		// lstAction.clear();
		// lstService.clear();
		// loadActionFromDatabase();
		// tree.removeAllItems();
		// tree.addItem(mcaActionRoot);
		// tree.setChildrenAllowed(mcaActionRoot, true);
		// for (int i = 0; i < lstService.size(); i++) {
		// PrcService mcaService = lstService.get(i);
		// if (mcaService.getStatus().equals("1")) {
		// tree.addItem(mcaService);
		// tree.setParent(mcaService, mcaActionRoot);
		// tree.setChildrenAllowed(mcaService, false);
		// cboSearch.addItem(mcaService);
		// for (int j = 0; j < lstAction.size(); j++) {
		// EsmeSmsc mcaAction = lstAction.get(j);
		// if (mcaAction.getStatus().equals("1")) {
		// PrcService _service = mcaAction.getPrcService();
		// if (mcaService.getServiceId() == _service
		// .getServiceId()) {
		// tree.setChildrenAllowed(mcaService, true);
		// tree.addItem(mcaAction);
		// tree.setParent(mcaAction, mcaService);
		// tree.setChildrenAllowed(mcaAction, false);
		// }
		//
		// cboSearch.addItem(mcaAction);
		// }
		// }
		// }
		//
		// }
		// tree.expandItemsRecursively(mcaActionRoot);
		// // PrcService serviceTree = msv.getPrcService();
		// // EsmeSmsc actionTree = msv;
		// // if (actionTree.getStatus().equals("1")) {
		// // PrcService _service = actionTree
		// // .getPrcService();
		// // if (serviceTree.getServiceId() == _service
		// // .getServiceId()) {
		// // tree.setChildrenAllowed(serviceTree,
		// // true);
		// // tree.addItem(actionTree);
		// // tree.
		// // tree.setParent(actionTree, serviceTree);
		// // tree.setChildrenAllowed(actionTree,
		// // false);
		// // }
		// //
		// // cboSearch.addItem(actionTree);
		// // }
		// }
		//
		// @Override
		// public void buttonClick(ClickEvent event) {
		//
		// Button source = event.getButton();
		//
		// if (source == btnAdd) {
		// frm.setValidationVisible(false);
		// if (isValid()) {
		// frm.commit();
		// item = (BeanItem<EsmeSmsc>) frm.getItemDataSource();
		// EsmeSmsc msv = item.getBean();
		// try {
		// int id = actionService.addAction(msv);
		// msv.setActionId(id);
		// if (id > 0) {
		//
		// addCopyData(msv);
		// loadTree(msv);
		//
		// MessageAlerter.showMessageI18n(getApplication()
		// .getMainWindow().getWindow(),
		// "Sao chép dữ liệu thành công");
		// } else {
		//
		// MessageAlerter.showErrorMessage(getApplication()
		// .getMainWindow().getWindow(),
		// "Sao chép dữ liệu thất bại");
		// }
		//
		// this.close();
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// }
		// }
		//
		// } else if (source == btnCancel) {
		//
		// this.close();
		// }
		// }

	}
}
