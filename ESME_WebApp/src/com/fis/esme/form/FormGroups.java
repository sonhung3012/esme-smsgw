package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
import com.fis.esme.groupsdt.GroupsDTTransferer;
import com.fis.esme.persistence.Groups;
import com.fis.esme.persistence.SearchEntity;
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

public class FormGroups extends VerticalLayout implements PanelActionProvider, PagingComponentListener, ServerSort, PanelTreeProvider, OptionDialogResultListener {

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
	private Groups skSearch = null;

	private CommonButtonPanel pnlAction;
	private CommonDialog dialog;
	private BeanItemContainer<Groups> data;

	private GroupsDTTransferer service = null;

	private final String[] Col_VisibleForm = TM.get("groups.form.visibleproperties").split(",");
	private final String[] Col_Visible = TM.get("groups.table.setvisiblecolumns").split(",");
	private final String[] Col_Header = TM.get("groups.table.setcolumnheaders").split(",");

	private final String OBJECT_TREE_ROOT = TM.get("groups.caption");
	private ArrayList<Groups> canDelete = new ArrayList<Groups>();
	private int total = 0;
	private static Groups treeService = null;
	private FormGroupsFieldFactory actionFactory;
	private Groups groupRoot;

	private ComboBox cboSearch;
	private CommonTreePanel commonTree;
	private ConfirmDeletionDialog confirm;
	private static List<Groups> listChildOfCurnNode = new ArrayList<Groups>();
	SearchEntity searchEntity = new SearchEntity();
	private Groups editRoot = null;
	private Groups deleteRoot = null;

	public FormGroups() {

		this.setCaption(TM.get(FormGroups.class.getName()));
		LogUtil.logAccess(FormGroups.class.getName());
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
			service = CacheServiceClient.serviceGroups;
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void loadServiceFromDatabase() {

		try {
			Groups esmeServices = new Groups();
			// esmeServices.setStatus("1");
			CacheDB.cacheGroupsDT = CacheServiceClient.serviceGroups.findAllWithOrderPaging(esmeServices, "NAME", false, -1, -1, true);
			Collections.sort(CacheDB.cacheGroupsDT, FormUtil.stringComparator(true));

		} catch (Exception e) {
			e.printStackTrace();
		}

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
	// servicesData.addAll(CacheDB.cacheGroupsDT);
	// servicesData.sort(
	// new Object[] { TM.get("action.beanItemContainerService") },
	// new boolean[] { true });
	// return cboService;
	// }

	private void initLayout() throws Exception {

		pnlAction = new CommonButtonPanel(this);
		pnlAction.showSearchPanel(true);
		pnlAction.setFromCaption(TM.get(FormGroups.class.getName()));
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
		String searchTooltip = TM.get("subs.detail.tooltip.search");
		commonTree.setComboBoxSearchTooltip(searchTooltip);
		commonTree.setComBoxSearchInputPrompt(searchTooltip);
		initComponent();
		tree.select(groupRoot);
	}

	private void initComponent() throws Exception {

		data = new BeanItemContainer<Groups>(Groups.class);
		initForm();
		initTable();
		mainLayout.setSplitPosition(20, Sizeable.UNITS_PERCENTAGE);
		mainLayout.setFirstComponent(commonTree);
		mainLayout.setSecondComponent(container);

	}

	@SuppressWarnings("serial")
	private void initTable() throws Exception {

		tbl = new CustomTable("", data, pnlAction) {

			@Override
			public Collection<?> getSortableContainerPropertyIds() {

				ArrayList<Object> arr = new ArrayList<Object>();
				Object[] sortCol = TM.get("groups.table.setsortcolumns").split(",");
				for (Object obj : sortCol) {

					arr.add(obj);

				}
				return arr;
			}

			@Override
			protected String formatPropertyValue(Object rowId, Object colId, Property property) {

				String pid = (String) colId;
				Groups esem = (Groups) rowId;
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
						Groups parent = getParenta(esem);
						if (parent != null) {
							return parent.getDesciption();
						} else {
							return "";
						}
					} else {
						return "";
					}
				}

				if ("rootId".equals(pid)) {
					if (property.getValue() != null) {
						Groups root = getRoot(esem);
						if (root != null) {
							return root.getDesciption();
						} else {
							return "";
						}

					} else {
						return "";
					}
				}

				if ("createDatetime".equals(pid)) {
					if (property.getValue() != null)
						return FormUtil.simpleDateFormat.format(property.getValue());
					else
						return "";
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

				final Groups bean = (Groups) itemId;

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
						Groups bean = data.getIdByIndex(i);
						bean.setSelect(tbl.isSelectAll());
						tbl.setColumnHeader("select", (tbl.isSelectAll() == true) ? "-" : "+");
						tbl.refreshRowCache();
					}
				}
			}
		});

		tbl.setStyleName("commont_table_noborderLR");

		String[] VisibleColumns = TM.get("groups.table.columnwidth").split(",");
		String[] VisibleColumnsSize = TM.get("groups.table.columnwidth_value").split(",");
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
		pnlAction.setValueForCboField(TM.get("groups.table.filteredcolumns").split(","), TM.get("groups.table.filteredcolumnscaption").split(","));
		container.setFilteredColumns(TM.get("groups.table.filteredcolumns").split(","));
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

			List<Groups> action = service.findAllWithOrderPaging(skSearch, sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH);

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

		groupRoot = new Groups();
		groupRoot.setDesciption(OBJECT_TREE_ROOT);
		groupRoot.setName(OBJECT_TREE_ROOT);
		groupRoot.setGroupId(-1);
		groupRoot.setStatus("1");
		groupRoot.setParentId((long) -1);
		groupRoot.setRootId((long) -1);
	}

	public List<Groups> getAllItemCheckedOnTable() {

		List<Groups> list = new ArrayList<Groups>();
		Collection<Groups> collection = (Collection<Groups>) tbl.getItemIds();
		for (Groups obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

	private void initComponentLeft() {

		List<Groups> list = new ArrayList<Groups>();
		list.addAll(CacheDB.cacheGroupsDT);
		// System.out.println("list:" + list.size());
		cboSearch = new ComboBox();
		cboSearch.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		tree = new DecoratedTree();
		buildDataForTreeTable();
		tree.setStyleName("mca-normal-node");
		cboSearch.setContainerDataSource(tree.getContainerDataSource());

	}

	public void buildTreeNode(Groups parent, List<Groups> list) {

		for (Groups esmeServices : list) {
			if (esmeServices.getParentId() == parent.getGroupId()) {

				tree.addItem(esmeServices);
				tree.setItemIcon(esmeServices, FisDefaultTheme.ICON_SERVICE);
				tree.setParent(esmeServices, parent);
				tree.setChildrenAllowed(esmeServices, true);
				cboSearch.addItem(esmeServices);

				// dataSevices.addBean(esmeServices);
				// tree.setParent(esmeServices, parent);
				// tree.setItemDescriptionGenerator(generator)
				// tree.setItemIcon(esmeServices, FisDefaultTheme.ICON_SERVICE);
				List<Groups> listTemp = getAllChildren(esmeServices, CacheDB.cacheGroupsDT);
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
		Collections.sort(CacheDB.cacheGroupsDT, FormUtil.stringComparator(true));
		tree.removeAllItems();
		List<Groups> listRootDepartment = null;
		// loadServiceFromDatabase();
		listRootDepartment = getAllChildrenIsRoot(null, CacheDB.cacheGroupsDT);

		// container.setDataForCboSearch(listRootDepartment);

		// data.addBean(departmentRoot);
		// treeTable.setCollapsed(departmentRoot, false);

		tree.addItem(groupRoot);
		tree.setNullSelectionAllowed(false);
		tree.setImmediate(true);
		tree.setChildrenAllowed(groupRoot, true);
		for (Groups group : listRootDepartment) {

			tree.addItem(group);
			tree.setItemIcon(group, FisDefaultTheme.ICON_SERVICE);
			tree.setParent(group, groupRoot);
			tree.setChildrenAllowed(group, true);
			cboSearch.addItem(group);
			// buildTree1(parent, list);

			// dataSevices.addBean(esmeServices);
			// treeTable.setParent(voipDepartment, departmentRoot);;
			buildTreeNode(group, getAllChildren(group, CacheDB.cacheGroupsDT));
		}
		// tree.expandItem(esmeServiceRoot);
		tree.expandItemsRecursively(groupRoot);
	}

	public Groups getParenta(Groups service) {

		// CacheDB.cacheGroupsDT.clear();
		// loadServiceFromDatabase();
		for (Groups esmeServices : CacheDB.cacheGroupsDT) {
			if (esmeServices.getGroupId() == service.getParentId()) {
				return esmeServices;
			}
		}
		return null;
	}

	public Groups getRoot(Groups group) {

		// CacheDB.cacheGroupsDT.clear();
		// loadServiceFromDatabase();

		if (group != null && group.getParentId() == null) {
			group.setParentId(-1l);
		}

		if (group != null) {

			if (group.getParentId() == -1) {

				return group;
			} else if (group.getParentId() != -1) {

				for (Groups msv : CacheDB.cacheGroupsDT) {
					if (msv.getGroupId() == group.getParentId()) {

						if (msv.getParentId() != -1) {
							Groups bean = getRoot(msv);
							return bean;
						} else {
							return msv;
						}
					}
				}
			}
		}
		return null;
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
		for (Groups group : list) {
			if ((group.getParentId() != -1)) {
				if (parent.getGroupId() == group.getParentId()) {
					listChildren.add(group);
				}
			}
		}
		return listChildren;
	}

	private List<Groups> getAllChildByParentOnTree(List<Groups> list, Object item, boolean clear) {

		if (clear)
			listChildOfCurnNode.clear();
		getAllChildByParentOnTree(list, item);
		return listChildOfCurnNode;
	}

	private void getAllChildByParentOnTree(List<Groups> list, Object item) {

		Collection<Groups> coll = (Collection<Groups>) tree.getChildren(item);
		if (coll == null)
			return;
		if (coll.size() > 0) {
			for (Groups esmeServices : coll) {
				listChildOfCurnNode.add(esmeServices);
				getAllChildByParentOnTree(listChildOfCurnNode, esmeServices);
			}
		}
	}

	private boolean isParentGroup(Groups group) {

		for (Groups bean : CacheDB.cacheGroupsDT) {

			if (bean.getParentId() == group.getGroupId()) {

				return true;
			}
		}

		return false;
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
		actionFactory = new FormGroupsFieldFactory();
		frm.setFormFieldFactory(actionFactory);

		dialog = new CommonDialog(TM.get("groups.commondialog.caption"), frm, this);
		dialog.setHeight("250px");
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
		dialog.setWidth("450px");

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
			editRoot = (Groups) object;
			actionFactory.setOldCode(((Groups) object).getName());
			actionFactory.setOldName(((Groups) object).getDesciption());

		} else if (action == PanelActionProvider.ACTION_ADD_COPY) {
			Set<Groups> setAction = (Set<Groups>) tbl.getValue();
			// Iterator<McaAction> actionSelect = setAction.iterator();
			Groups msv = setAction.iterator().next();
			Groups newBean = new Groups();
			newBean.setName(msv.getName());
			newBean.setRootId(msv.getRootId());
			newBean.setParentId(msv.getParentId());
			newBean.setDesciption(msv.getDesciption());
			newBean.setStatus(msv.getStatus());
			newBean.setCreateDatetime(new Date());
			item = new BeanItem<Groups>(newBean);

			actionFactory.setOldCode(null);
			actionFactory.setOldName(null);
		} else if (action == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
			Groups srcService = (Groups) object;
			Groups bean = new Groups();
			bean.setName("");
			bean.setDesciption("");
			bean.setStatus("1");
			bean.setCreateDatetime(new Date());
			item = new BeanItem<Groups>(bean);
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
			actionFactory.setOldName(null);
			Groups msv = new Groups();
			msv.setName("");
			msv.setDesciption("");
			if (treeService != null) {
				msv.setParentId(treeService.getGroupId());
			}
			Groups se = getRoot(treeService);

			if (se != null) {
				msv.setRootId(se.getGroupId());
			}
			msv.setStatus("1");
			msv.setCreateDatetime(new Date());
			item = new BeanItem<Groups>(msv);
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
				BeanItem<Groups> itembean = (BeanItem<Groups>) frm.getItemDataSource();
				Groups action = itembean.getBean();

				if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD || pnlAction.getAction() == PanelActionProvider.ACTION_ADD_COPY
				        || pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
					try {

						if (action.getParentId() == null) {

							action.setParentId(-1l);
						}

						if (action.getRootId() == null) {

							Groups smv = getRoot(action);
							if (smv != null) {

								action.setRootId(smv.getGroupId());
							}
						}

						long id = service.add(action);
						action.setGroupId(id);
						CacheDB.cacheGroupsDT.add(action);
						if (id > 0) {

							tbl.addItem(action);
							tbl.setMultiSelect(false);
							tbl.select(action);
							tbl.setMultiSelect(true);

							// CacheDB.cacheGroupsDT.clear();
							// loadServiceFromDatabase();
							buildDataForTreeTable();
							container.initPager(service.count(null, DEFAULT_EXACT_MATCH));
							actionFactory.initComboBox();
							// container.initPager(CacheServiceClient.serviceService.count(
							// action, DEFAULT_EXACT_MATCH));
							if (pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
								pnlAction.clearAction();
								pnlAction.searchOrAddNew(action.getName());
							}
							LogUtil.logActionInsert(FormGroups.class.getName(), "GROUPSS", "GROUP_ID", "" + action.getGroupId() + "", null);
							MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.add.success", TM.get("common.service").toLowerCase()));
						} else {
							MessageAlerter.showErrorMessage(getWindow(), TM.get("common.msg.add.fail", TM.get("common.service").toLowerCase()));
						}

					} catch (Exception e) {
						FormUtil.showException(getWindow(), e);
					}
				} else {
					try {
						Vector vt = LogUtil.logActionBeforeUpdate(FormGroups.class.getName(), "GROUPSS", "GROUP_ID", "" + action.getGroupId() + "", null);
						Groups st = new Groups();

						st.setGroupId((Long) frm.getField("parentId").getValue());
						st.setParentId((Long) frm.getField("parentId").getValue());

						Groups smv = getRoot(st);
						if (smv != null) {

							action.setRootId(smv.getGroupId());
						}
						service.update(action);
						for (Groups group : CacheDB.cacheGroupsDT) {

							if (group.getGroupId() == action.getGroupId()) {

								group.setName(action.getName());
								group.setParentId(action.getParentId());
								group.setRootId(action.getRootId());
								group.setCreateDatetime(action.getCreateDatetime());
								group.setDesciption(action.getDesciption());
								group.setStatus(action.getStatus());

								break;
							}
						}

						// CacheDB.cacheGroupsDT.clear();
						// loadServiceFromDatabase();
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
		if (object instanceof Groups) {
			Groups prcGroup = (Groups) object;

			if (isParentGroup(prcGroup)) {

				MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("groups.message.delete.parent.error"));
				return;
			}

			boolean b = service.checkConstraints(prcGroup.getGroupId());
			if (!b) {
				total++;
				canDelete.add(prcGroup);
			} else {

				MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("message.delete.constraints"));
				return;
			}

		} else {

			List<Groups> listDel = (List<Groups>) object;
			if (listDel.size() == 1 && isParentGroup(listDel.get(0))) {

				MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("groups.message.delete.parent.error"));
				return;
			}

			for (Groups obj : listDel) {
				total++;

				boolean b = service.checkConstraints(obj.getGroupId());
				if (!b) {
					canDelete.add(obj);
				} else if (b && listDel.size() == 1) {

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
		for (Groups msv : canDelete) {
			try {
				if (!isParentGroup(msv)) {
					LogUtil.logActionDelete(FormGroups.class.getName(), "GROUPSS", "GROUP_ID", "" + msv.getGroupId() + "", null);
					service.delete(msv);
					CacheDB.cacheGroupsDT.remove(msv);
					tbl.removeItem(msv);
					deleted++;
				}
				// List<Groups> arrService = getAllChildren(msv, CacheDB.cacheGroupsDT);
				// if (arrService != null) {
				//
				// Groups parentSer = getParenta(msv);
				// if (parentSer != null) {
				// for (Groups esmeServices : arrService) {
				// esmeServices.setParentId(parentSer.getGroupId());
				// service.update(esmeServices);
				// for (Groups group : CacheDB.cacheGroupsDT) {
				//
				// if (group.getGroupId() == esmeServices.getGroupId()) {
				//
				// group.setParentId(esmeServices.getParentId());
				//
				// break;
				// }
				// }
				//
				// }
				// } else {
				// for (Groups esmeServices : arrService) {
				// esmeServices.setParentId(-1l);
				// service.update(esmeServices);
				// for (Groups group : CacheDB.cacheGroupsDT) {
				//
				// if (group.getGroupId() == esmeServices.getGroupId()) {
				//
				// group.setParentId(esmeServices.getParentId());
				//
				// break;
				// }
				// }
				//
				// }
				// }
				// }

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// CacheDB.cacheGroupsDT.clear();
		// loadServiceFromDatabase();
		buildDataForTreeTable();
		container.initPager(service.count(null, DEFAULT_EXACT_MATCH));
		actionFactory.initComboBox();
		FormUtil.clearCache(null);
		MessageAlerter.showMessageI18n(getWindow(), TM.get("message.delete"), deleted, total);

	}

	public static Groups getObjMcaService() {

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
			skSearch = new Groups();
			if (obj.equals(groupRoot)) {
				searchEntity.setSwitchCase("");
				skSearch = new Groups();
				container.initPager(CacheServiceClient.serviceGroups.count(skSearch, DEFAULT_EXACT_MATCH));

			} else {
				treeService = (Groups) obj;
				data.removeAllItems();

				String strGroupId = String.valueOf(treeService.getGroupId());
				for (Groups esmeServices : getAllChildByParentOnTree(listChildOfCurnNode, obj, true)) {
					if (strGroupId == null)
						strGroupId = "" + esmeServices.getGroupId();
					else
						strGroupId += "," + esmeServices.getGroupId();
				}

				skSearch = new Groups();
				// skSearch.setParentId(treeService.getGroupId());
				skSearch.setDesciption(strGroupId);

				container.initPager(CacheServiceClient.serviceGroups.count(skSearch, DEFAULT_EXACT_MATCH));
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

		skSearch = new Groups();
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
		if (searchObj.getKey() == null)
			return;

		skSearch = new Groups();
		if (searchObj.getField() == null) {
			skSearch.setName(searchObj.getKey());

		} else {
			if (searchObj.getField().equals("name"))
				skSearch.setName(searchObj.getKey());
			if (searchObj.getField().equals("desciption"))
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
