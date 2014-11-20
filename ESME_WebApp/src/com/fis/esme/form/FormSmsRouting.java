package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.fis.esme.admin.SessionData;
import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.PanelTreeProvider;
import com.fis.esme.classes.ServerSort;
import com.fis.esme.component.CommonButtonPanel;
import com.fis.esme.component.CommonDialog;
import com.fis.esme.component.CommonTreePanel;
import com.fis.esme.component.ConfirmDeletionDialog;
import com.fis.esme.component.CustomTable;
import com.fis.esme.component.DecoratedTree;
import com.fis.esme.component.PagingComponent.ChangePageEvent;
import com.fis.esme.component.PagingComponent.PagingComponentListener;
import com.fis.esme.component.PanelActionProvider;
import com.fis.esme.component.TableContainer;
import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.EsmeShortCode;
import com.fis.esme.persistence.EsmeSmsCommand;
import com.fis.esme.persistence.EsmeSmsRouting;
import com.fis.esme.persistence.SearchEntity;
import com.fis.esme.service.Exception_Exception;
import com.fis.esme.util.CacheDB;
import com.fis.esme.util.FisDefaultTheme;
import com.fis.esme.util.FormUtil;
import com.fis.esme.util.LogUtil;
import com.fis.esme.util.MessageAlerter;
import com.fis.esme.util.SearchObj;
import com.vaadin.data.Container;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.HeaderClickEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

import eu.livotov.tpt.gui.dialogs.OptionDialog.OptionDialogResultListener;
import eu.livotov.tpt.gui.dialogs.OptionKind;
import eu.livotov.tpt.i18n.TM;

public class FormSmsRouting extends VerticalLayout implements PanelActionProvider, PagingComponentListener, ServerSort, PanelTreeProvider, OptionDialogResultListener {

	private HorizontalSplitPanel mainLayout;
	private DecoratedTree tree;
	private Form frmRouter;
	private CustomTable tblRouter;
	private TableContainer container;

	private final String DEFAULT_SORTED_COLUMN = null;
	private boolean DEFAULT_SORTED_ASC = true;
	private boolean DEFAULT_EXACT_MATCH = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;
	private EsmeSmsRouting skSearch = new EsmeSmsRouting();
	private SearchEntity searchEntity = new SearchEntity();
	private CommonButtonPanel pnlAction;
	private CommonDialog dialog;
	private BeanItemContainer<EsmeSmsRouting> routerData;

	// private final String[] Col_VisibleForm = TM.get("action.Col_VisibleForm")
	// .split(",");
	// private final String[] Col_Visible = TM.get("action.Col_Visible")
	// .split(",");
	// private final String[] Col_Header =
	// TM.get("action.Col_Header").split(",");

	private final String OBJECT_TREE_ROOT = TM.get("services.caption");
	private ArrayList<EsmeSmsRouting> canDelete = new ArrayList<EsmeSmsRouting>();
	private int total = 0;
	private static EsmeServices treeService = null;
	private FormSmsRoutingFactory actionFactory;
	private EsmeServices esmeServiceRoot;

	// private ComboBox cboService;
	private ComboBox cboSearch;
	private CommonTreePanel commonTree;
	private ConfirmDeletionDialog confirm;

	private BeanItemContainer<EsmeServices> servicesData = new BeanItemContainer<EsmeServices>(EsmeServices.class);

	// private ActionBucketTransferer bucketService;
	// private ArrayList<EsmeSmsRoutingBucket> lstactionBucket = new
	// ArrayList<EsmeSmsRoutingBucket>();
	// private ArrayList<String> strlstUsageID = new ArrayList<String>() ;
	// private String strUsageAction;
	private static List<EsmeServices> listChildOfCurnNode = new ArrayList<EsmeServices>();

	private HorizontalLayout layoutSearch = new HorizontalLayout();
	private Button btnClear = new Button(TM.get("main.common.button.clear.caption"));
	private ComboBox cboCP = new ComboBox(TM.get("routing.field.cp.caption"));
	private ComboBox cboShortCode = new ComboBox(TM.get("routing.field.shortcode.caption"));
	private ComboBox cboCommand = new ComboBox(TM.get("routing.field.smscommand.caption"));

	private BeanItemContainer<EsmeCp> dataContainerCP = new BeanItemContainer<EsmeCp>(EsmeCp.class);
	private BeanItemContainer<EsmeShortCode> dataContainerSortCode = new BeanItemContainer<EsmeShortCode>(EsmeShortCode.class);
	private BeanItemContainer<EsmeSmsCommand> dataContainerCommand = new BeanItemContainer<EsmeSmsCommand>(EsmeSmsCommand.class);

	public FormSmsRouting() {

		this.setCaption(TM.get("FormEsmeSmsRouting"));
		LogUtil.logAccess(FormSmsRouting.class.getName());
		initService();
		setSizeFull();
		loadServiceFromDatabase();

		try {
			// initCombo();
			initLayout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initService() {

	}

	private void loadServiceFromDatabase() {

		if (CacheDB.cacheService.size() <= 0) {
			try {
				EsmeServices esmeServices = new EsmeServices();
				// esmeServices.setStatus("1");
				CacheDB.cacheService = CacheServiceClient.serviceService.findAllWithOrderPaging(esmeServices, null, false, -1, -1, true);
				Collections.sort(CacheDB.cacheService, FormUtil.stringComparator(true));
			} catch (Exception_Exception e) {
				e.printStackTrace();
			}
		} else {

		}

	}

	private void initLayout() throws Exception {

		pnlAction = new CommonButtonPanel(this);
		pnlAction.showSearchPanel(true);
		pnlAction.setFromCaption(TM.get(FormSmsRouting.class.getName()));
		this.setSizeFull();
		this.addComponent(pnlAction);

		mainLayout = new HorizontalSplitPanel();
		Panel panel = new Panel();
		panel.setSizeFull();
		panel.setContent(mainLayout);
		mainLayout.setSizeFull();
		this.addComponent(panel);
		this.setExpandRatio(panel, 1f);

		initComboBox();
		setDataForComboBox();

		initObjServiceRoot();
		initComponentLeft();
		commonTree = new CommonTreePanel(tree, cboSearch, this);
		initComponent();
		initSearchLayout();
		tree.select(esmeServiceRoot);
	}

	private void initSearchLayout() {

		btnClear.setDescription(TM.get("main.common.button.clear.description"));
		btnClear.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				cboCP.setValue(null);
				cboShortCode.setValue(null);
				cboCommand.setValue(null);
			}
		});

		FormLayout flCP = new FormLayout();
		flCP.setSizeFull();
		flCP.setMargin(false);
		flCP.addComponent(cboCP);
		flCP.setComponentAlignment(cboCP, Alignment.MIDDLE_CENTER);

		FormLayout flShortCode = new FormLayout();
		flShortCode.setSizeFull();
		flShortCode.setMargin(false);
		flShortCode.addComponent(cboShortCode);
		flShortCode.setComponentAlignment(cboShortCode, Alignment.MIDDLE_CENTER);

		FormLayout flCommand = new FormLayout();
		flCommand.setSizeFull();
		flCommand.setMargin(false);
		flCommand.addComponent(cboCommand);
		flCommand.setComponentAlignment(cboCommand, Alignment.MIDDLE_CENTER);

		layoutSearch.setSizeFull();
		layoutSearch.setSpacing(true);
		// layoutSearch.setWidth("820px");
		// layoutSearch.setHeight("65px");
		layoutSearch.addComponent(flCP);
		layoutSearch.setComponentAlignment(flCP, Alignment.MIDDLE_CENTER);
		layoutSearch.addComponent(flShortCode);
		layoutSearch.setComponentAlignment(flShortCode, Alignment.MIDDLE_CENTER);
		layoutSearch.addComponent(flCommand);
		layoutSearch.setComponentAlignment(flCommand, Alignment.MIDDLE_CENTER);
		layoutSearch.setStyleName("routing_searchlayout");

		HorizontalLayout hlayoutSearch = new HorizontalLayout();
		hlayoutSearch.setSizeFull();
		hlayoutSearch.addComponent(layoutSearch);
		hlayoutSearch.setComponentAlignment(layoutSearch, Alignment.MIDDLE_CENTER);
		hlayoutSearch.addComponent(btnClear);
		hlayoutSearch.setComponentAlignment(btnClear, Alignment.MIDDLE_CENTER);
		hlayoutSearch.setExpandRatio(layoutSearch, 1f);
		hlayoutSearch.setWidth("900px");

		pnlAction.cusSearchLayout(hlayoutSearch);
	}

	private void initComponent() throws Exception {

		routerData = new BeanItemContainer<EsmeSmsRouting>(EsmeSmsRouting.class);
		initForm();
		initTable();
		mainLayout.setSplitPosition(250, Sizeable.UNITS_PIXELS);
		mainLayout.setFirstComponent(commonTree);
		mainLayout.setSecondComponent(container);
	}

	private void initComboBox() {

		cboCP.setWidth(TM.get("common.form.field.fixedwidth.150"));
		cboCommand.setWidth(TM.get("common.form.field.fixedwidth.150"));
		cboShortCode.setWidth(TM.get("common.form.field.fixedwidth.150"));

		cboCP.setImmediate(true);
		cboCP.setContainerDataSource(dataContainerCP);
		cboCP.setNullSelectionAllowed(true);
		// cboCP.setInputPrompt(TM.get("common.field_combobox.inputprompt",
		// cboCP
		// .getCaption().toLowerCase()));
		cboCP.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cboCP.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				skSearch.setEsmeCp((EsmeCp) cboCP.getValue());
				container.initPager(CacheServiceClient.serviceSmsRouting.count(searchEntity, skSearch, true));
			}
		});

		cboShortCode.setImmediate(true);
		cboShortCode.setContainerDataSource(dataContainerSortCode);
		cboShortCode.setNullSelectionAllowed(true);
		// cboShortCode.setInputPrompt(TM.get("common.field_combobox.inputprompt",
		// cboShortCode.getCaption().toLowerCase()));
		cboShortCode.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cboShortCode.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				skSearch.setEsmeShortCode((EsmeShortCode) cboShortCode.getValue());
				container.initPager(CacheServiceClient.serviceSmsRouting.count(searchEntity, skSearch, true));
			}
		});

		cboCommand.setImmediate(true);
		cboCommand.setContainerDataSource(dataContainerCommand);
		cboCommand.setNullSelectionAllowed(true);
		// cboCommand.setInputPrompt(TM.get("common.field_combobox.inputprompt",
		// cboCommand.getCaption().toLowerCase()));
		cboCommand.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		cboCommand.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				skSearch.setEsmeSmsCommand((EsmeSmsCommand) cboCommand.getValue());
				container.initPager(CacheServiceClient.serviceSmsRouting.count(searchEntity, skSearch, true));
			}
		});

	}

	private void setDataForComboBox() {

		try {
			EsmeCp esmeCp = new EsmeCp();
			// esmeCp.setStatus("1");
			CacheDB.cacheCP = CacheServiceClient.serviceCp.findAllWithOrderPaging(esmeCp, null, false, -1, -1, true);
		} catch (com.fis.esme.cp.Exception_Exception e) {
			e.printStackTrace();
		}
		Collections.sort(CacheDB.cacheCP, FormUtil.stringComparator(true));
		dataContainerCP.addAll(CacheDB.cacheCP);

		try {
			EsmeShortCode esmeShortCode = new EsmeShortCode();
			// esmeShortCode.setStatus("1");
			CacheDB.cacheShortCode = CacheServiceClient.serviceShortCode.findAllWithOrderPaging(esmeShortCode, null, false, -1, -1, true);
			Collections.sort(CacheDB.cacheShortCode, FormUtil.stringComparator(true));
			dataContainerSortCode.addAll(CacheDB.cacheShortCode);
		} catch (com.fis.esme.shortcode.Exception_Exception e) {
			e.printStackTrace();
		}

		try {
			EsmeSmsCommand esmeSmsCommand = new EsmeSmsCommand();
			// esmeSmsCommand.setStatus("1");
			CacheDB.cacheSmsCommand = CacheServiceClient.serviceSmsCommand.findAllWithOrderPaging(esmeSmsCommand, null, false, -1, -1, true);
			Collections.sort(CacheDB.cacheSmsCommand, FormUtil.stringComparator(true));
			dataContainerCommand.addAll(CacheDB.cacheSmsCommand);
		} catch (com.fis.esme.smscommand.Exception_Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	private void initTable() throws Exception {

		tblRouter = new CustomTable("", routerData, pnlAction) {

			@Override
			public Collection<?> getSortableContainerPropertyIds() {

				ArrayList<Object> arr = new ArrayList<Object>();
				Object[] sortCol = TM.get("routing.table.setsortcolumns").split(",");
				for (Object obj : sortCol) {
					// System.out.println(obj);
					arr.add(obj);

				}
				return arr;
			}

			@Override
			protected String formatPropertyValue(Object rowId, Object colId, Property property) {

				String pid = (String) colId;
				if (property.getValue() == null) {
					return super.formatPropertyValue(rowId, colId, property);
				}

				// if ("usageAction".equals(pid)) {
				// if (property.getValue().equals("1")) {
				// return "+";
				// } else {
				// return "";
				// }
				// }

				return super.formatPropertyValue(rowId, colId, property);
			}
		};

		tblRouter.setSortContainerPropertyId(TM.get("routing.table.setsortcolumns").split(","));
		tblRouter.setImmediate(true);
		tblRouter.setSelectable(true);
		tblRouter.setMultiSelect(true);
		tblRouter.setVisibleColumns(TM.get("routing.table.setvisiblecolumns").split(","));
		tblRouter.setColumnHeaders(TM.get("routing.table.setcolumnheaders").split(","));

		tblRouter.addListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {

				Object id = tblRouter.getValue();
				pnlAction.setRowSelected(id != null);
			}
		});
		tblRouter.addListener(new Container.ItemSetChangeListener() {

			public void containerItemSetChange(ItemSetChangeEvent event) {

				pnlAction.setRowSelected(false);

			}
		});

		if (getPermission().contains(TM.get("module.right.Update"))) {
			tblRouter.addListener(new ItemClickEvent.ItemClickListener() {

				private static final long serialVersionUID = 2068314108919135281L;

				public void itemClick(ItemClickEvent event) {

					if (event.isDoubleClick()) {
						pnlAction.edit(event.getItemId());
					}
				}
			});
		}

		tblRouter.addGeneratedColumn("select", new Table.ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {

				final EsmeSmsRouting bean = (EsmeSmsRouting) itemId;

				CheckBox checkBox = new CheckBox();
				checkBox.setImmediate(true);
				checkBox.addListener(new Property.ValueChangeListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(Property.ValueChangeEvent event) {

						bean.setSelect((Boolean) event.getProperty().getValue());
					}
				});
				if (bean.isSelect()) {
					checkBox.setValue(true);
				} else {
					checkBox.setValue(false);
				}
				return checkBox;
			}
		});

		tblRouter.addListener(new Table.HeaderClickListener() {

			public void headerClick(HeaderClickEvent event) {

				String property = event.getPropertyId().toString();
				if (property.equals("select")) {
					tblRouter.setSelectAll(!tblRouter.isSelectAll());
					for (int i = 0; i < routerData.size(); i++) {
						EsmeSmsRouting bean = routerData.getIdByIndex(i);
						bean.setSelect(tblRouter.isSelectAll());
						tblRouter.setColumnHeader("select", (tblRouter.isSelectAll() == true) ? "-" : "+");
						tblRouter.refreshRowCache();
					}
				}
			}
		});

		tblRouter.setStyleName("commont_table_noborderLR");

		tblRouter.setColumnCollapsed(TM.get("routing.table.columnwidth_collapsed"), true);
		String[] ColumnCollapsed = TM.get("routing.table.columnwidth_collapsed").split(",");
		for (String col : ColumnCollapsed) {
			tblRouter.setColumnCollapsed(col, true);
		}

		String[] columnWidth = TM.get("routing.table.columnwidth").split(",");
		String[] columnWidthValue = TM.get("routing.table.columnwidth_value").split(",");
		for (int i = 0; i < columnWidth.length; i++) {
			int size = -1;
			try {
				size = Integer.parseInt(columnWidthValue[i]);
			} catch (Exception e) {

			}
			tblRouter.setColumnWidth(columnWidth[i], size);
		}

		if (tblRouter.getContainerDataSource().equals(null)) {
			pnlAction.setRowSelected(false);
		}

		container = new TableContainer(tblRouter, this, Integer.parseInt(TM.get("pager.page.rowsinpage"))) {

			@Override
			public void deleteAllItemSelected() {

				pnlAction.delete(getAllItemCheckedOnTable());
			}
		};

		// container.initPager(CacheServiceClient.serviceSmsRouting.count(null,
		// DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(true);
		container.setEnableDeleteAllButton(getPermission().contains(TM.get("module.right.Delete")));
		container.setEnableButtonAddNew(getPermission().contains(TM.get("module.right.Insert")));
		container.setEnableButtonAddCopy(getPermission().contains(TM.get("module.right.Insert")));
		pnlAction.setValueForCboField(TM.get("routing.table.filteredcolumns").split(","), TM.get("routing.table.filteredcolumnscaption").split(","));
		container.setFilteredColumns(TM.get("routing.table.filteredcolumns").split(","));
		container.removeHeaderSearchLayout();
		container.setVisibleBorderMainLayout(false);
		container.setEnableDeleteAllButton(getPermission().contains("D"));
		container.setEnableButtonAddNew(getPermission().contains("I"));
		container.setEnableButtonAddCopy(getPermission().contains("I"));

	}

	@Override
	public void requestSort(String cloumn, boolean asc) {

		sortedColumn = cloumn;
		sortedASC = asc;
		int items = container.getItemPerPage();
		displayData(cloumn, asc, 0, items);
		container.changePage(1);
	}

	@Override
	public void displayPage(ChangePageEvent event) {

		int start = event.getPageRange().getIndexPageStart();
		// int end = event.getPageRange().getIndexPageEnd();
		displayData(sortedColumn, sortedASC, start, event.getPageRange().getNumberOfRowsPerPage());
	}

	private void displayData(String sortedColumn, boolean asc, int start, int items) {

		try {
			routerData.removeAllItems();

			System.out.println("--------------");
			System.out.println(skSearch.getEsmeCp());
			System.out.println(skSearch.getEsmeShortCode());
			System.out.println(skSearch.getEsmeSmsCommand());

			List<EsmeSmsRouting> list = CacheServiceClient.serviceSmsRouting.findAllWithOrderPaging(searchEntity, skSearch, sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH);
			routerData.addAll(list);
			if (container != null)
				container.setLblCount(start);
		} catch (Exception e) {
			// MessageAlerter.showErrorMessageI18n(this.getWindow(),
			// TM.get("common.getdata.fail"));
			e.printStackTrace();
		}
	}

	private void initObjServiceRoot() {

		esmeServiceRoot = new EsmeServices();
		esmeServiceRoot.setDesciption("");
		esmeServiceRoot.setName(OBJECT_TREE_ROOT);
		esmeServiceRoot.setServiceId(-1);
		esmeServiceRoot.setStatus("1");
		esmeServiceRoot.setParentId((long) 0);
		esmeServiceRoot.setRootId((long) 0);
	}

	public List<EsmeSmsRouting> getAllItemCheckedOnTable() {

		List<EsmeSmsRouting> list = new ArrayList<EsmeSmsRouting>();
		Collection<EsmeSmsRouting> collection = (Collection<EsmeSmsRouting>) tblRouter.getItemIds();
		for (EsmeSmsRouting obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

	private void initComponentLeft() {

		List<EsmeServices> list = new ArrayList<EsmeServices>();
		list.addAll(CacheDB.cacheService);
		// System.out.println("list:" + list.size());
		cboSearch = new ComboBox();
		cboSearch.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		tree = new DecoratedTree();
		buildDataForTreeTable();
		tree.setStyleName("mca-normal-node");
		cboSearch.setContainerDataSource(tree.getContainerDataSource());
	}

	private void buildDataForTreeTable() {

		servicesData.removeAllItems();
		servicesData.addAll(CacheDB.cacheService);
		// servicesData.sort(
		// new Object[] { TM.get("action.beanItemContainerService") },
		// new boolean[] { true });

		// servicesData.removeAllItems();
		tree.removeAllItems();
		List<EsmeServices> listRootDepartment = null;
		listRootDepartment = getAllChildrenIsRoot(null, CacheDB.cacheService);

		// container.setDataForCboSearch(listRootDepartment);

		// data.addBean(departmentRoot);
		// treeTable.setCollapsed(departmentRoot, false);

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
			// buildTree1(parent, list);

			// dataSevices.addBean(esmeServices);
			// treeTable.setParent(voipDepartment, departmentRoot);;
			buildTreeNode(esmeServices, getAllChildren(esmeServices, CacheDB.cacheService));
		}
		// tree.expandItem(esmeServiceRoot);
		tree.expandItemsRecursively(esmeServiceRoot);
	}

	public void buildTreeNode(EsmeServices parent, List<EsmeServices> list) {

		for (EsmeServices esmeServices : list) {
			if (esmeServices.getParentId() == parent.getServiceId()) {

				tree.addItem(esmeServices);
				tree.setItemIcon(esmeServices, FisDefaultTheme.ICON_SERVICE);
				tree.setParent(esmeServices, parent);
				tree.setChildrenAllowed(esmeServices, true);
				cboSearch.addItem(esmeServices);

				// dataSevices.addBean(esmeServices);
				// tree.setParent(esmeServices, parent);
				// tree.setItemDescriptionGenerator(generator)
				// tree.setItemIcon(esmeServices, FisDefaultTheme.ICON_SERVICE);
				List<EsmeServices> listTemp = getAllChildren(esmeServices, CacheDB.cacheService);
				if (listTemp.size() > 0) {
					buildTreeNode(esmeServices, listTemp);
				}
			}
		}
		tree.expandItemsRecursively(parent);
		// tree.expandItem(parent);
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

	private void selectAndExpand(Object obj) {

		if (obj == null) {
			obj = OBJECT_TREE_ROOT;
		}
		tree.select(obj);
		Object parent = tree.getParent(obj);
		tree.expandItem(parent);
	}

	// private void initCombo() {
	// cboService = new ComboBox(TM.get("action.cboService.caption"));
	// cboService.setWidth("320px");
	// cboService.setImmediate(true);
	// cboService.setNullSelectionAllowed(false);
	// cboService.setInputPrompt(TM.get("action.cboService.setInputPrompt"));
	// cboService.setRequired(true);
	// cboService.setRequiredError(TM.get("action.setRequiredError",
	// TM.get("action.cboService.caption")));
	// cboService.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
	// servicesData.setItemSorter(new DefaultItemSorter(FormUtil
	// .stringComparator(true)));
	// servicesData.sort(
	// new Object[] { TM.get("action.beanItemContainerService") },
	// new boolean[] { true });
	// cboService.setContainerDataSource(servicesData);
	// }

	private void initForm() {

		frmRouter = new Form();
		frmRouter.setImmediate(false);
		frmRouter.setWriteThrough(false);
		System.out.println("servicesData:" + servicesData.size());
		actionFactory = new FormSmsRoutingFactory(servicesData, dataContainerCP, dataContainerSortCode, dataContainerCommand);
		frmRouter.setFormFieldFactory(actionFactory);

		dialog = new CommonDialog(TM.get("services.dialog.caption"), frmRouter, this);
		dialog.setHeight("280px");
		dialog.addListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {

				pnlAction.clearAction();
			}
		});
	}

	private Window createDialog(Item item) {

		frmRouter.setItemDataSource(item);
		frmRouter.setVisibleItemProperties(TM.get("routing.form.visibleproperties").split(","));
		frmRouter.focus();
		frmRouter.setValidationVisible(false);
		getWindow().addWindow(dialog);

		return dialog;
	}

	@Override
	public void showDialog(Object object) {

		if (getWindow().getChildWindows().contains(dialog)) {
			return;
		}

		Item item = null;
		int action = pnlAction.getAction();

		if (action == PanelActionProvider.ACTION_EDIT) {
			item = tblRouter.getItem(object);
			actionFactory.setOldEsmeShortCode(((EsmeSmsRouting) object).getEsmeShortCode());
			actionFactory.setOldEsmeSmsCommand(((EsmeSmsRouting) object).getEsmeSmsCommand());
		} else if (action == PanelActionProvider.ACTION_ADD_COPY) {
			Set<EsmeSmsRouting> coll = (Set<EsmeSmsRouting>) tblRouter.getValue();
			EsmeSmsRouting bean = coll.iterator().next();
			EsmeSmsRouting newBean = new EsmeSmsRouting();
			newBean.setEsmeCp(bean.getEsmeCp());
			newBean.setEsmeServices(bean.getEsmeServices());
			newBean.setEsmeShortCode(bean.getEsmeShortCode());
			newBean.setEsmeSmsCommand(bean.getEsmeSmsCommand());
			// actionFactory.setTreeService(bean.getEsmeServices());
			actionFactory.setOldEsmeShortCode(null);
			actionFactory.setOldEsmeSmsCommand(null);
			item = new BeanItem<EsmeSmsRouting>(newBean);
		} else if (action == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
			Set<EsmeSmsRouting> coll = (Set<EsmeSmsRouting>) tblRouter.getValue();
			EsmeSmsRouting bean = coll.iterator().next();
			EsmeSmsRouting newBean = new EsmeSmsRouting();
			newBean.setEsmeCp(null);
			newBean.setEsmeServices(treeService);
			newBean.setEsmeShortCode(null);
			newBean.setEsmeSmsCommand(null);
			actionFactory.setOldEsmeShortCode(null);
			actionFactory.setOldEsmeSmsCommand(null);
			// PrcService srcService = (PrcService) object;
			// PrcService bean = new PrcService();
			// bean.setCode(srcService.getCode());
			// bean.setName("");
			// bean.setDescription("");
			// bean.setStatus("1");
			item = new BeanItem<EsmeSmsRouting>(bean);
		} else {

			if (treeService == null || treeService == esmeServiceRoot) {
				MessageAlerter.showMessage(tree.getWindow(), TM.get("common.field_combobox.inputprompt", TM.get("services.caption").toLowerCase()));
				pnlAction.clearAction();
				return;
			}
			// actionFactory.setTreeService(treeService);
			EsmeSmsRouting bean = new EsmeSmsRouting();
			bean.setEsmeCp(null);
			bean.setEsmeServices(treeService);
			bean.setEsmeShortCode(null);
			bean.setEsmeSmsCommand(null);
			actionFactory.setOldEsmeShortCode(null);
			actionFactory.setOldEsmeSmsCommand(null);
			item = new BeanItem<EsmeSmsRouting>(bean);
		}
		createDialog(item);
	}

	@Override
	public void accept() {

		try {

			boolean modified = frmRouter.isModified();
			if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT && !modified) {
				pnlAction.clearAction();
				return;
			} else {
				frmRouter.commit();
				BeanItem<EsmeSmsRouting> itembean = (BeanItem<EsmeSmsRouting>) frmRouter.getItemDataSource();
				EsmeSmsRouting bean = itembean.getBean();

				if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD || pnlAction.getAction() == PanelActionProvider.ACTION_ADD_COPY
				        || pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
					try {

						long id = CacheServiceClient.serviceSmsRouting.add(bean);
						bean.setRoutingId(id);
						if (id > 0) {
							container.initPager(CacheServiceClient.serviceSmsRouting.count(searchEntity, skSearch, DEFAULT_EXACT_MATCH));
							// tblAction.addItem(action);
							tblRouter.setMultiSelect(false);
							tblRouter.select(bean);
							tblRouter.setMultiSelect(true);
							if (pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
								pnlAction.clearAction();
								// pnlAction.searchOrAddNew(bean.getCode());
							}
							LogUtil.logActionInsert(FormSmsRouting.class.getName(), "PRC_SMS_ROUTING", "ROUTING_ID", "" + bean.getRoutingId() + "", null);

							MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.add.success", TM.get("routing.caption").toLowerCase()));

						} else {
							MessageAlerter.showErrorMessage(getWindow(), TM.get("common.msg.add.fail", TM.get("routing.caption").toLowerCase()));
						}

					} catch (Exception e) {
						FormUtil.showException(getWindow(), e);
					}
				} else {
					try {
						Vector vt = LogUtil.logActionBeforeUpdate(FormSmsRouting.class.getName(), "PRC_SMS_ROUTING", "ROUTING_ID", "" + bean.getRoutingId() + "", null);
						CacheServiceClient.serviceSmsRouting.update(bean);

						// int index = cacheAction.indexOf(action);
						// cacheAction.remove(action);
						// cacheAction.add(index, action);
						tblRouter.setMultiSelect(false);
						// tblAction.addItem(action);
						tblRouter.select(bean);
						tblRouter.setMultiSelect(true);
						LogUtil.logActionAfterUpdate(vt);
						MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.edit.success", TM.get("routing.caption").toLowerCase()));
					} catch (Exception e) {
						FormUtil.showException(getWindow(), e);
					}
				}

				tree.select(bean.getEsmeServices());
			}

		} catch (Exception e) {
			e.printStackTrace();
			MessageAlerter.showMessageI18n(getWindow(), "action.errorMessage");
		}
		pnlAction.clearAction();
		FormUtil.clearCache(null);
	}

	private void resetResource() {

		canDelete.clear();
		total = 0;
	}

	@Override
	public void delete(Object object) {

		resetResource();
		if (object instanceof EsmeSmsRouting) {
			EsmeSmsRouting prcService = (EsmeSmsRouting) object;
			boolean b = CacheServiceClient.serviceSmsRouting.checkConstraints(prcService.getRoutingId());
			if (!b) {
				total++;
				canDelete.add(prcService);
			} else {

				MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("message.delete.constraints"));
				return;
			}
		} else {
			for (EsmeSmsRouting obj : (List<EsmeSmsRouting>) object) {
				total++;

				boolean b = CacheServiceClient.serviceSmsRouting.checkConstraints(obj.getRoutingId());
				if (!b) {
					canDelete.add(obj);
				}
			}
		}

		if (canDelete.size() == 0) {
			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("comman.message.delete.error"));
		} else {
			String message = TM.get("common.msg.delete.confirm");
			confirmDeletion(message);
		}

	}

	private void confirmDeletion(String message) {

		if (confirm == null) {
			confirm = new ConfirmDeletionDialog(getApplication());
		}
		confirm.show(message, this);
	}

	@Override
	public String getPermission() {

		// return AppClient.getPermission(this.getClass().getName());
		return SessionData.getAppClient().getPermission(this.getClass().getName());

	}

	private void deleteAction() {

		int deleted = 0;
		// Object obj = null;
		for (EsmeSmsRouting mcaaction : canDelete) {
			try {
				LogUtil.logActionDelete(FormSmsRouting.class.getName(), "ESME_SMS_ROUTING", "ROUTING_ID", "" + mcaaction.getRoutingId() + "", null);

				CacheServiceClient.serviceSmsRouting.delete(mcaaction);

				// cacheAction.remove(mcaaction);
				tblRouter.removeItem(mcaaction);
				deleted++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		container.initPager(CacheServiceClient.serviceSmsRouting.count(searchEntity, skSearch, DEFAULT_EXACT_MATCH));

		FormUtil.clearCache(null);
		MessageAlerter.showMessageI18n(getWindow(), TM.get("message.delete"), deleted, total);

	}

	public static EsmeServices getObjMcaService() {

		return treeService;
	}

	@Override
	public void filterTree(Object obj) {

		selectAndExpand(obj);

	}

	@Override
	public void treeValueChanged(Object obj) {

		if (obj == null) {
			return;
		}
		try {
			treeService = null;
			searchEntity.setSwitchCase(TM.get("FIND_BY_GROUP"));
			if (obj.equals(esmeServiceRoot)) {
				searchEntity.setValues(null);
				// System.out.println("root cua service: " + obj.toString());
				// routerData.removeAllItems();
				// routerData.addAll(CacheServiceClient.serviceSmsRouting
				// .findAllWithoutParameter());
				container.initPager(CacheServiceClient.serviceSmsRouting.count(searchEntity, skSearch, DEFAULT_EXACT_MATCH));
			} else {
				treeService = (EsmeServices) obj;
				routerData.removeAllItems();
				// skSearch.setEsmeServices((EsmeServices) obj);

				String strServiceId = String.valueOf(treeService.getServiceId());
				for (EsmeServices esmeServices : getAllChildByParentOnTree(listChildOfCurnNode, obj, true)) {
					if (strServiceId == null)
						strServiceId = "" + esmeServices.getServiceId();
					else
						strServiceId += "," + esmeServices.getServiceId();
				}

				searchEntity.setValues(strServiceId);
				container.initPager(CacheServiceClient.serviceSmsRouting.count(searchEntity, skSearch, DEFAULT_EXACT_MATCH));
				pnlAction.clearAction();
			}
		} catch (Exception e) {
			FormUtil.showException(getWindow(), e);
		}
	}

	@Override
	public void dialogClosed(OptionKind option) {

		if (OptionKind.OK.equals(option)) {
			if (canDelete != null && canDelete.size() > 0) {
				deleteAction();
			}
		}

	}

	@Override
	public void export() {

	}

	@Override
	public void searchOrAddNew(String key) {

		// skSearch = new EsmeSmsRouting();
		// skSearch.setCode(key);
		// DEFAULT_EXACT_MATCH = true;
		// int count = CacheServiceClient.serviceSmsRouting.count(skSearch,
		// DEFAULT_EXACT_MATCH);
		// if (count > 0) {
		// container.initPager(count);
		// pnlAction.SetFocusField(true);
		// pnlAction.clearAction();
		// } else {
		// showDialog(skSearch);
		// pnlAction.SetFocusField(false);
		// }
		// DEFAULT_EXACT_MATCH = false;
	}

	@Override
	public void search() {

		// TODO Auto-generated method stub

	}

	@Override
	public void fieldSearch(SearchObj searchObj) {

		// System.out.println("searchObj" + searchObj);
		// if (searchObj.getField() == null && searchObj.getKey() == null)
		// return;
		//
		// skSearch = new EsmeSmsRouting();
		// if (searchObj.getField() == null) {
		//
		// skSearch.setEsmeServices(null);
		// skSearch.setEsmeShortCode(null);
		// skSearch.setEsmeSmsCommand(null);
		//
		// skSearch.setCode(searchObj.getKey());
		// skSearch.setName(searchObj.getKey());
		// skSearch.setDescription(searchObj.getKey());
		// } else {
		// if (searchObj.getField().equals("code"))
		// skSearch.setCode(searchObj.getKey());
		// else if (searchObj.getField().equals("name"))
		// skSearch.setName(searchObj.getKey());
		// else if (searchObj.getField().equals("description"))
		// skSearch.setDescription(searchObj.getKey());
		// }
		//
		// int count = actionService.count(skSearch, DEFAULT_EXACT_MATCH);
		// if (count > 0) {
		// container.initPager(count);
		// } else {
		// MessageAlerter.showMessageI18n(getWindow(),
		// TM.get("msg.search.value.emty"));
		// }
		pnlAction.clearAction();
	}

	public BeanItemContainer<EsmeCp> getCpData() {

		return dataContainerCP;
	}

	public BeanItemContainer<EsmeCp> getDataContainerCP() {

		return dataContainerCP;
	}

	public void setDataContainerCP(BeanItemContainer<EsmeCp> dataContainerCP) {

		this.dataContainerCP = dataContainerCP;
	}

	public BeanItemContainer<EsmeShortCode> getDataContainerSortCode() {

		return dataContainerSortCode;
	}

	public void setDataContainerSortCode(BeanItemContainer<EsmeShortCode> dataContainerSortCode) {

		this.dataContainerSortCode = dataContainerSortCode;
	}

	public BeanItemContainer<EsmeSmsCommand> getDataContainerCommand() {

		return dataContainerCommand;
	}

	public void setDataContainerCommand(BeanItemContainer<EsmeSmsCommand> dataContainerCommand) {

		this.dataContainerCommand = dataContainerCommand;
	}

}
