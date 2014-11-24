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
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.SearchEntity;
import com.fis.esme.service.Exception_Exception;
import com.fis.esme.service.ServiceTransferer;
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
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Form;
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

public class PanelService extends VerticalLayout implements PanelActionProvider, PagingComponentListener, ServerSort, PanelTreeProvider, OptionDialogResultListener {

	private HorizontalSplitPanel mainLayout;
	private DecoratedTree tree;
	private Form frm;
	private CustomTable tbl;
	private TableContainer container;

	private final String DEFAULT_SORTED_COLUMN = "name";
	private boolean DEFAULT_SORTED_ASC = true;
	private boolean DEFAULT_EXACT_MATCH = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;
	private EsmeServices skSearch = null;

	private CommonButtonPanel pnlAction;
	private CommonDialog dialog;
	private BeanItemContainer<EsmeServices> data;

	private ServiceTransferer service = null;

	private final String[] Col_VisibleForm = TM.get("service1.form.visibleproperties").split(",");
	private final String[] Col_Visible = TM.get("service1.table.setvisiblecolumns").split(",");
	private final String[] Col_Header = TM.get("service1.table.setcolumnheaders").split(",");

	private final String OBJECT_TREE_ROOT = TM.get("services.caption");
	private ArrayList<EsmeServices> canDelete = new ArrayList<EsmeServices>();
	private int total = 0;
	private static EsmeServices treeService = null;
	private FormServiceFieldFactory actionFactory;
	private EsmeServices esmeServiceRoot;

	private ComboBox cboSearch;
	private CommonTreePanel commonTree;
	private ConfirmDeletionDialog confirm;
	private static List<EsmeServices> listChildOfCurnNode = new ArrayList<EsmeServices>();
	SearchEntity searchEntity = new SearchEntity();
	private EsmeServices editRoot = null;
	private EsmeServices deleteRoot = null;

	public PanelService() {

		this.setCaption(TM.get("PanelService"));
		LogUtil.logAccess(PanelService.class.getName());
		initService();
		setSizeFull();
		loadServiceFromDatabase();

		try {
			initLayout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initService() {

		try {
			service = CacheServiceClient.serviceService;
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void loadServiceFromDatabase() {

		try {
			EsmeServices esmeServices = new EsmeServices();
			// esmeServices.setStatus("1");
			CacheDB.cacheService = CacheServiceClient.serviceService.findAllWithOrderPaging(esmeServices, "NAME", false, -1, -1, true);

		} catch (Exception_Exception e) {
			e.printStackTrace();
		}

		Collections.sort(CacheDB.cacheService, FormUtil.stringComparator(true));
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
	//
	// private ComboBox initDataCombo() {
	// loadServiceFromDatabase();
	// servicesData.removeAllItems();
	// servicesData.addAll(CacheDB.cacheService);
	// servicesData.sort(
	// new Object[] { TM.get("action.beanItemContainerService") },
	// new boolean[] { true });
	// return cboService;
	// }

	private void initLayout() throws Exception {

		pnlAction = new CommonButtonPanel(this);
		pnlAction.showSearchPanel(true);
		pnlAction.setFromCaption(TM.get(PanelService.class.getName()));
		this.setSizeFull();
		this.addComponent(pnlAction);

		mainLayout = new HorizontalSplitPanel();
		Panel panel = new Panel();
		panel.setSizeFull();
		panel.setContent(mainLayout);
		mainLayout.setSizeFull();
		this.addComponent(panel);
		this.setExpandRatio(panel, 1f);
		initObjServiceRoot();
		initComponentLeft();
		commonTree = new CommonTreePanel(tree, cboSearch, this);
		initComponent();
		tree.select(esmeServiceRoot);
	}

	private void initComponent() throws Exception {

		data = new BeanItemContainer<EsmeServices>(EsmeServices.class);
		initForm();
		initTable();
		mainLayout.setSplitPosition(250, Sizeable.UNITS_PIXELS);
		mainLayout.setFirstComponent(commonTree);
		mainLayout.setSecondComponent(container);

	}

	@SuppressWarnings("serial")
	private void initTable() throws Exception {

		tbl = new CustomTable("", data, pnlAction) {

			// @Override
			// public Collection<?> getSortableContainerPropertyIds() {
			//
			// ArrayList<Object> arr = new ArrayList<Object>();
			// Object[] sortCol = TM.get("service1.table.setsortcolumns").split(",");
			// for (Object obj : sortCol) {
			//
			// arr.add(obj);
			//
			// }
			// return arr;
			// }

			@Override
			protected String formatPropertyValue(Object rowId, Object colId, Property property) {

				String pid = (String) colId;
				EsmeServices esem = (EsmeServices) rowId;
				if (property.getValue() == null) {
					return super.formatPropertyValue(rowId, colId, property);
				}

				if ("usageAction".equals(pid)) {
					if (property.getValue().equals("1")) {
						return "+";
					} else {
						return "";
					}
				}

				if ("parentId".equals(pid)) {
					if (property.getValue() != null) {
						EsmeServices parent = getParenta(esem);
						if (parent != null) {
							return parent.getName();
						} else {
							return "";
						}
					} else {
						return "";
					}
				}

				if ("rootId".equals(pid)) {
					if (property.getValue() != null) {
						EsmeServices se = getRoot(esem);
						if (se != null) {
							return se.getName();
						} else {
							return "";
						}

					} else {
						return "";
					}
				}

				return super.formatPropertyValue(rowId, colId, property);
			}
		};

		tbl.setImmediate(true);
		tbl.setSelectable(true);
		tbl.setMultiSelect(true);
		tbl.setVisibleColumns(Col_Visible);
		tbl.setColumnHeaders(Col_Header);
		tbl.setColumnCollapsingAllowed(true);
		tbl.setColumnReorderingAllowed(true);
		// tblAction.setColumnCollapsed("prcService", true);

		tbl.addListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {

				Object id = tbl.getValue();
				pnlAction.setRowSelected(id != null);
			}
		});
		tbl.addListener(new Container.ItemSetChangeListener() {

			public void containerItemSetChange(ItemSetChangeEvent event) {

				pnlAction.setRowSelected(false);

			}
		});

		if (getPermission().contains(TM.get("module.right.Update"))) {
			tbl.addListener(new ItemClickEvent.ItemClickListener() {

				private static final long serialVersionUID = 2068314108919135281L;

				public void itemClick(ItemClickEvent event) {

					if (event.isDoubleClick()) {
						pnlAction.edit(event.getItemId());
					}
				}
			});
		}

		tbl.addGeneratedColumn("select", new Table.ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {

				final EsmeServices bean = (EsmeServices) itemId;

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

		tbl.addListener(new Table.HeaderClickListener() {

			public void headerClick(HeaderClickEvent event) {

				String property = event.getPropertyId().toString();
				if (property.equals("select")) {
					tbl.setSelectAll(!tbl.isSelectAll());
					for (int i = 0; i < data.size(); i++) {
						EsmeServices bean = data.getIdByIndex(i);
						bean.setSelect(tbl.isSelectAll());
						tbl.setColumnHeader("select", (tbl.isSelectAll() == true) ? "-" : "+");
						tbl.refreshRowCache();
					}
				}
			}
		});

		tbl.setStyleName("commont_table_noborderLR");
		tbl.setSortContainerPropertyId(TM.get("service1.table.setsortcolumns"));

		String[] VisibleColumns = TM.get("service1.table.columnwidth").split(",");
		String[] VisibleColumnsSize = TM.get("service1.table.columnwidth_value").split(",");
		for (int i = 0; i < VisibleColumns.length; i++) {
			int size = -1;
			try {
				size = Integer.parseInt(VisibleColumnsSize[i]);
			} catch (Exception e) {

			}
			tbl.setColumnWidth(VisibleColumns[i], size);
		}

		if (tbl.getContainerDataSource().equals(null)) {
			pnlAction.setRowSelected(false);
		}

		container = new TableContainer(tbl, this, Integer.parseInt(TM.get("pager.page.rowsinpage"))) {

			@Override
			public void deleteAllItemSelected() {

				pnlAction.delete(getAllItemCheckedOnTable());
			}
		};

		container.initPager(service.count(null, DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(true);
		pnlAction.setValueForCboField(TM.get("service1.table.filteredcolumns").split(","), TM.get("service1.table.filteredcolumnscaption").split(","));
		container.setFilteredColumns(TM.get("service1.table.filteredcolumns").split(","));
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
			data.removeAllItems();

			List<EsmeServices> action = service.findAllWithOrderPaging(skSearch, sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH);
			data.addAll(action);

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

	public List<EsmeServices> getAllItemCheckedOnTable() {

		List<EsmeServices> list = new ArrayList<EsmeServices>();
		Collection<EsmeServices> collection = (Collection<EsmeServices>) tbl.getItemIds();
		for (EsmeServices obj : collection) {
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

	private void buildDataForTreeTable() {

		// servicesData.removeAllItems();
		Collections.sort(CacheDB.cacheService, FormUtil.stringComparator(true));
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

	public EsmeServices getParenta(EsmeServices service) {

		// CacheDB.cacheService.clear();
		// loadServiceFromDatabase();
		for (EsmeServices esmeServices : CacheDB.cacheService) {
			if (esmeServices.getServiceId() == service.getParentId()) {
				return esmeServices;
			}
		}
		return null;
	}

	public EsmeServices getRoot(EsmeServices service) {

		// CacheDB.cacheService.clear();
		// loadServiceFromDatabase();
		if (service != null && service.getParentId() == -1) {
			return service;
		} else if (service != null && service.getParentId() != -1) {
			for (EsmeServices msv : CacheDB.cacheService) {
				if (msv.getServiceId() == service.getParentId()) {
					if (msv.getParentId() != -1) {
						EsmeServices bean = getRoot(msv);
						return bean;
					} else {
						return msv;
					}
				}
			}
		}
		return null;
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

	private void initForm() {

		frm = new Form();
		frm.setImmediate(false);
		frm.setWriteThrough(false);
		actionFactory = new FormServiceFieldFactory();
		frm.setFormFieldFactory(actionFactory);

		dialog = new CommonDialog(TM.get("service.commondialog.caption"), frm, this);
		dialog.setHeight("350px");
		dialog.addListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {

				pnlAction.clearAction();
			}
		});
	}

	private Window createDialog(Item item) {

		frm.setItemDataSource(item);
		frm.setVisibleItemProperties(Col_VisibleForm);
		frm.focus();
		frm.setValidationVisible(false);
		getWindow().addWindow(dialog);
		dialog.setWidth("500px");

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

			item = tbl.getItem(object);
			editRoot = (EsmeServices) object;
			actionFactory.setOldCode(((EsmeServices) object).getName());

		} else if (action == PanelActionProvider.ACTION_ADD_COPY) {
			Set<EsmeServices> setAction = (Set<EsmeServices>) tbl.getValue();
			// Iterator<McaAction> actionSelect = setAction.iterator();
			EsmeServices msv = setAction.iterator().next();
			EsmeServices newBean = new EsmeServices();
			newBean.setName(msv.getName());
			newBean.setRootId(msv.getRootId());
			newBean.setParentId(msv.getParentId());
			newBean.setDesciption(msv.getDesciption());
			newBean.setStatus(msv.getStatus());
			item = new BeanItem<EsmeServices>(newBean);
			actionFactory.setOldCode(null);
		} else if (action == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
			EsmeServices srcService = (EsmeServices) object;
			EsmeServices bean = new EsmeServices();
			bean.setName("");
			bean.setDesciption("");
			bean.setStatus("1");
			item = new BeanItem<EsmeServices>(bean);
		} else {
			// if (treeService == null || treeService == esmeServiceRoot) {
			// MessageAlerter.showMessage(
			// tree.getWindow(),
			// TM.get("common.field_combobox.inputprompt",
			// TM.get("services.caption").toLowerCase()));
			// pnlAction.clearAction();
			// return;
			// }
			actionFactory.setOldCode(null);
			EsmeServices msv = new EsmeServices();
			msv.setName("");
			if (treeService != null) {
				msv.setParentId(treeService.getServiceId());
			}
			EsmeServices se = getRoot(treeService);

			if (se != null) {
				msv.setRootId(se.getServiceId());
			}
			msv.setStatus("1");

			item = new BeanItem<EsmeServices>(msv);
		}
		createDialog(item);
	}

	@Override
	public void accept() {

		try {
			boolean modified = frm.isModified();
			if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT && !modified) {
				pnlAction.clearAction();
				return;
			} else {
				frm.commit();
				BeanItem<EsmeServices> itembean = (BeanItem<EsmeServices>) frm.getItemDataSource();

				EsmeServices action = itembean.getBean();

				if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD || pnlAction.getAction() == PanelActionProvider.ACTION_ADD_COPY
				        || pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
					try {
						if (action.getParentId() == null) {

							action.setParentId(-1l);
						}

						if (action.getRootId() == null) {

							EsmeServices smv = getRoot(action);
							if (smv != null) {

								action.setRootId(smv.getServiceId());
							}
						}

						long id = service.add(action);
						action.setServiceId(id);
						CacheDB.cacheService.add(action);
						if (id > 0) {

							tbl.addItem(action);
							tbl.setMultiSelect(false);
							tbl.select(action);
							tbl.setMultiSelect(true);

							// CacheDB.cacheService.clear();
							// loadServiceFromDatabase();
							container.initPager(service.count(null, DEFAULT_EXACT_MATCH));

							buildDataForTreeTable();
							actionFactory.initComboBox();
							// container.initPager(CacheServiceClient.serviceService.count(
							// action, DEFAULT_EXACT_MATCH));
							if (pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
								pnlAction.clearAction();
								pnlAction.searchOrAddNew(action.getName());
							}
							LogUtil.logActionInsert(PanelService.class.getName(), "ESME_SERVICES", "SERVICE_ID", "" + action.getServiceId() + "", null);
							MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.add.success", TM.get("common.service").toLowerCase()));
						} else {
							MessageAlerter.showErrorMessage(getWindow(), TM.get("common.msg.add.fail", TM.get("common.service").toLowerCase()));
						}

					} catch (Exception e) {
						FormUtil.showException(getWindow(), e);
					}
				} else {
					try {
						Vector vt = LogUtil.logActionBeforeUpdate(PanelService.class.getName(), "ESME_SERVICES", "SERVICE_ID", "" + action.getServiceId() + "", null);
						EsmeServices st = new EsmeServices();
						st.setServiceId((Long) frm.getField("parentId").getValue());
						st.setParentId((Long) frm.getField("parentId").getValue());

						EsmeServices smv = getRoot(st);

						if (smv != null) {

							action.setRootId(smv.getServiceId());
						}

						service.update(action);
						for (EsmeServices service : CacheDB.cacheService) {

							if (service.getServiceId() == action.getServiceId()) {

								service.setName(action.getName());
								service.setParentId(action.getParentId());
								service.setRootId(action.getRootId());
								service.setDesciption(action.getDesciption());
								service.setStatus(service.getStatus());
								break;
							}
						}

						// if (CacheDB.cacheService.size() <= 0) {
						// CacheDB.cacheService.clear();
						// loadServiceFromDatabase();
						// }

						buildDataForTreeTable();
						container.initPager(service.count(null, DEFAULT_EXACT_MATCH));

						// int index = cacheAction.indexOf(action);
						// cacheAction.remove(action);
						// cacheAction.add(index, action);
						tbl.setMultiSelect(false);
						// tblAction.addItem(action);
						tbl.select(action);
						tbl.setMultiSelect(true);
						LogUtil.logActionAfterUpdate(vt);
						actionFactory.initComboBox();
						MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.edit.success", TM.get("common.service").toLowerCase()));
					} catch (Exception e) {
						FormUtil.showException(getWindow(), e);
					}
				}
			}
		} catch (Exception e) {
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
		if (object instanceof EsmeServices) {
			EsmeServices prcService = (EsmeServices) object;
			boolean b = service.checkConstraints(prcService.getServiceId());
			if (!b) {
				total++;
				canDelete.add(prcService);
			} else {

				MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("message.delete.constraints"));
				return;
			}
		} else {

			for (EsmeServices obj : (List<EsmeServices>) object) {
				total++;

				boolean b = service.checkConstraints(obj.getServiceId());
				if (!b) {
					canDelete.add(obj);
				} else if (b && ((List<EsmeServices>) object).size() == 1) {

					MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("message.delete.constraints"));
					return;
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
		for (EsmeServices msv : canDelete) {
			try {
				LogUtil.logActionDelete(PanelService.class.getName(), "ESME_SERVICES", "SERVICE_ID", "" + msv.getServiceId() + "", null);
				service.delete(msv);

				List<EsmeServices> arrService = getAllChildren(msv, CacheDB.cacheService);
				if (arrService != null) {

					EsmeServices parentSer = getParenta(msv);
					if (parentSer != null) {
						for (EsmeServices esmeServices : arrService) {
							esmeServices.setParentId(parentSer.getServiceId());
							service.update(esmeServices);
							for (EsmeServices service : CacheDB.cacheService) {

								if (service.getServiceId() == esmeServices.getServiceId()) {

									service.setParentId(esmeServices.getParentId());
									break;
								}
							}

						}
					} else {
						for (EsmeServices esmeServices : arrService) {
							esmeServices.setParentId(-1l);
							service.update(esmeServices);
							for (EsmeServices service : CacheDB.cacheService) {

								if (service.getServiceId() == esmeServices.getServiceId()) {

									service.setParentId(esmeServices.getParentId());
									break;
								}
							}

						}
					}
				}

				CacheDB.cacheService.remove(msv);
				tbl.removeItem(msv);
				deleted++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// if (CacheDB.cacheService.size() <= 0) {
		// CacheDB.cacheService.clear();
		// loadServiceFromDatabase();
		// }

		buildDataForTreeTable();
		container.initPager(service.count(null, DEFAULT_EXACT_MATCH));
		actionFactory.initComboBox();
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
			skSearch = new EsmeServices();
			if (obj.equals(esmeServiceRoot)) {
				searchEntity.setSwitchCase("");
				skSearch = new EsmeServices();
				container.initPager(CacheServiceClient.serviceService.count(skSearch, DEFAULT_EXACT_MATCH));
			} else {
				treeService = (EsmeServices) obj;
				data.removeAllItems();

				String strServiceId = String.valueOf(treeService.getServiceId());
				for (EsmeServices esmeServices : getAllChildByParentOnTree(listChildOfCurnNode, obj, true)) {
					if (strServiceId == null)
						strServiceId = "" + esmeServices.getServiceId();
					else
						strServiceId += "," + esmeServices.getServiceId();
				}

				skSearch = new EsmeServices();
				// skSearch.setParentId(treeService.getServiceId());
				skSearch.setDesciption(strServiceId);

				container.initPager(CacheServiceClient.serviceService.count(skSearch, DEFAULT_EXACT_MATCH));
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

		skSearch = new EsmeServices();
		skSearch.setName(key);
		DEFAULT_EXACT_MATCH = true;
		int count = service.count(skSearch, DEFAULT_EXACT_MATCH);
		if (count > 0) {
			container.initPager(count);
			pnlAction.SetFocusField(true);
			pnlAction.clearAction();
		} else {
			showDialog(skSearch);
			pnlAction.SetFocusField(false);
		}
		DEFAULT_EXACT_MATCH = false;
	}

	@Override
	public void search() {

		// TODO Auto-generated method stub

	}

	@Override
	public void fieldSearch(SearchObj searchObj) {

		System.out.println("searchObj" + searchObj);
		if (searchObj.getField() == null && searchObj.getKey() == null)
			return;

		skSearch = new EsmeServices();
		if (searchObj.getField() == null) {
			skSearch.setName(searchObj.getKey());

		} else if (searchObj.getField().equals("name")) {

			skSearch.setName(searchObj.getKey());

		} else if (searchObj.getField().equals("desciption")) {

			skSearch.setDesciption(searchObj.getKey() + "_Search");

		}

		int count = service.count(skSearch, DEFAULT_EXACT_MATCH);
		if (count > 0) {
			container.initPager(count);
		} else {
			MessageAlerter.showMessageI18n(getWindow(), TM.get("msg.search.value.emty"));
		}
		pnlAction.clearAction();
	}

}
