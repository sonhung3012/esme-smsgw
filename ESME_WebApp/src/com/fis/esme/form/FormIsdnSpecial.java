package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.vaadin.dialogs.ConfirmDialog;

import com.fis.esme.admin.SessionData;
import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.PanelTreeProvider;
import com.fis.esme.classes.ServerSort;
import com.fis.esme.component.CommonButtonPanel;
import com.fis.esme.component.CommonDialog;
import com.fis.esme.component.CommonTreeTablePanel;
import com.fis.esme.component.ConfirmDeletionDialog;
import com.fis.esme.component.CustomTable;
import com.fis.esme.component.CustomTreeTable;
import com.fis.esme.component.PagingComponent.ChangePageEvent;
import com.fis.esme.component.PagingComponent.PagingComponentListener;
import com.fis.esme.component.PanelActionProvider;
import com.fis.esme.component.TableContainer;
import com.fis.esme.persistence.EsmeIsdnPermission;
import com.fis.esme.persistence.EsmeIsdnSpecial;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.persistence.SearchEntity;
import com.fis.esme.service.Exception_Exception;
import com.fis.esme.util.CacheDB;
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
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Form;
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

public class FormIsdnSpecial extends VerticalLayout implements PanelActionProvider, PagingComponentListener, ServerSort, Action.Handler, OptionDialogResultListener, PanelTreeProvider {

	private HorizontalSplitPanel mainLayout;
	private CustomTreeTable treeTable;
	private ComboBox cboSearch;
	private CommonTreeTablePanel commonTree;
	private HorizontalLayout layoutButtonBock;
	private Button btnBlock;

	private CommonDialog dialog;
	private Form form;
	private CustomTable tbl;
	private TableContainer container;
	private BeanItemContainer<EsmeIsdnSpecial> data;
	private CommonButtonPanel pnlAction;
	private FormIsdnSpecialFieldFactory fieldFactory;

	private final String DEFAULT_SORTED_COLUMN = "msisdn";
	private boolean DEFAULT_SORTED_ASC = true;
	private boolean DEFAULT_EXACT_MATCH = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;
	// private int totalInDatabase = 0;
	private EsmeIsdnSpecial skSearch = null;

	private int total = 0;
	private ArrayList<EsmeIsdnSpecial> canDelete = new ArrayList<EsmeIsdnSpecial>();
	private ConfirmDeletionDialog confirm;

	private static List<EsmeServices> listServicesForIsdn = new ArrayList<EsmeServices>();

	private BeanItemContainer<EsmeServices> dataSevices;
	private String oldID = null;

	private String strType1 = "1";
	private String strType2 = "2";

	public FormIsdnSpecial(String key) {

		this();
		if (key != null) {
			pnlAction.clearAction();
			pnlAction.searchOrAddNew(key);
		}
	}

	public FormIsdnSpecial() {

		LogUtil.logAccess(FormIsdnSpecial.class.getName());
		initLayout();
	}

	private void initLayout() {

		pnlAction = new CommonButtonPanel(this);
		pnlAction.showSearchPanel(true);
		pnlAction.setEnableAlphabeltSearch(false);
		pnlAction.setFromCaption(TM.get(FormIsdnSpecial.class.getName()));
		pnlAction.setMargin(false, false, true, false);

		initComponent();

		mainLayout = new HorizontalSplitPanel();
		Panel panel = new Panel();
		panel.setSizeFull();
		panel.setContent(mainLayout);
		mainLayout.setSizeFull();

		mainLayout.setSplitPosition(1000, Sizeable.UNITS_PIXELS);
		mainLayout.setFirstComponent(container);
		mainLayout.setSecondComponent(commonTree);

		this.addComponent(pnlAction);
		this.setSizeFull();
		this.addComponent(panel);
		this.setExpandRatio(panel, 1.0f);
		this.setComponentAlignment(panel, Alignment.TOP_CENTER);
	}

	private void initComponent() {

		data = new BeanItemContainer<EsmeIsdnSpecial>(EsmeIsdnSpecial.class);
		dataSevices = new BeanItemContainer<EsmeServices>(EsmeServices.class);
		initService();
		initTable();
		initForm();
		initTreeTable();
		// loadDataFromDatabase();
	}

	private void initTreeTable() {

		if (CacheDB.cacheService.size() <= 0) {
			try {
				EsmeServices esmeServices = new EsmeServices();
				esmeServices.setStatus("1");
				CacheDB.cacheService = CacheServiceClient.serviceService.findAllWithOrderPaging(esmeServices, null, false, -1, -1, true);
				Collections.sort(CacheDB.cacheService, FormUtil.stringComparator(true));
			} catch (Exception_Exception e) {
				e.printStackTrace();
			}
		} else {

		}

		List<EsmeServices> list = new ArrayList<EsmeServices>();
		list.addAll(CacheDB.cacheService);

		cboSearch = new ComboBox();
		cboSearch.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		treeTable = new CustomTreeTable(null, dataSevices);
		treeTable.setSizeFull();
		treeTable.setStyleName("commont_table_noborderLR");
		treeTable.setSelectable(true);
		treeTable.addGeneratedColumn("select", new Table.ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {

				final EsmeServices bean = (EsmeServices) itemId;

				CheckBox checkBox = new CheckBox(bean.getName());
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

		try {
			treeTable.addActionHandler(this);
			// treeTable.setMultiSelect(true);
			treeTable.setImmediate(true);

			treeTable.addListener(new Property.ValueChangeListener() {

				public void valueChange(ValueChangeEvent event) {

					Object id = treeTable.getValue();
					setEnableAction(id);
				}

			});

			treeTable.addListener(new Container.ItemSetChangeListener() {

				public void containerItemSetChange(ItemSetChangeEvent event) {

					pnlAction.setRowSelected(false);
				}
			});

			// if (getPermission().contains("U")) {
			// treeTable.addListener(new ItemClickEvent.ItemClickListener() {
			// private static final long serialVersionUID =
			// 2068314108919135281L;
			//
			// public void itemClick(ItemClickEvent event) {
			// if (event.isDoubleClick()) {
			// // pnlAction.edit();
			// }
			// }
			// });
			// }

			treeTable.setVisibleColumns(TM.get("special.service.table.filteredcolumns").split(","));
			treeTable.setColumnHeaders(TM.get("special.service.table.setcolumnheaders").split(","));

			buildDataForTreeTable();

		} catch (Exception e) {
			e.printStackTrace();
		}

		layoutButtonBock = new HorizontalLayout();
		layoutButtonBock.setSpacing(true);
		layoutButtonBock.setMargin(false);

		btnBlock = new Button(TM.get("special.service.btn.block.caption"));
		btnBlock.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				final Set<EsmeIsdnSpecial> set = (Set<EsmeIsdnSpecial>) tbl.getValue();

				if ((set == null) || (set.size() <= 0) || (set.size() > 1)) {
					MessageAlerter.showMessage(tbl.getWindow(), TM.get("special.btn.value.emty-multi"));
					return;
				} else {
					String btnCaption = btnBlock.getCaption().toLowerCase();
					ConfirmDialog.show(getWindow(), TM.get("form.dialog.button.caption_title", btnCaption), TM.get("form.dialog.button.caption_description", btnCaption),
					        TM.get("form.dialog.button.caption_accept"), TM.get("form.dialog.button.caption_cancel"), new ConfirmDialog.Listener() {

						        public void onClose(ConfirmDialog dialog) {

							        if (dialog.isConfirmed()) {
								        doIsdnPermission(set.iterator().next(), getAllItemCheckedOnTreeTable());
							        }
						        }
					        });
				}
			}
		});

		layoutButtonBock.addComponent(btnBlock);
		layoutButtonBock.setComponentAlignment(btnBlock, Alignment.MIDDLE_CENTER);

		commonTree = new CommonTreeTablePanel(treeTable, cboSearch, this);
		commonTree.addFooterButtonLayout(layoutButtonBock);
	}

	private void buildDataForTreeTable() {

		dataSevices.removeAllItems();
		treeTable.removeAllItems();
		List<EsmeServices> listRootDepartment = null;
		listRootDepartment = getAllChildrenIsRoot(null, CacheDB.cacheService);

		// container.setDataForCboSearch(listRootDepartment);

		// data.addBean(departmentRoot);
		// treeTable.setCollapsed(departmentRoot, false);
		for (EsmeServices esmeServices : listRootDepartment) {
			setSelectForTreeNode(esmeServices);
			dataSevices.addBean(esmeServices);
			// treeTable.setParent(voipDepartment, departmentRoot);
			treeTable.setCollapsed(esmeServices, false);
			buildTreeNode(esmeServices, getAllChildren(esmeServices, CacheDB.cacheService));
		}

		cboSearch.setContainerDataSource(treeTable.getContainerDataSource());
	}

	public void buildTreeNode(EsmeServices parent, List<EsmeServices> list) {

		for (EsmeServices esmeServices : list) {
			setSelectForTreeNode(esmeServices);
			if (esmeServices.getParentId() == parent.getServiceId()) {
				dataSevices.addBean(esmeServices);
				treeTable.setParent(esmeServices, parent);
				treeTable.setCollapsed(esmeServices, false);
				List<EsmeServices> listTemp = getAllChildren(esmeServices, CacheDB.cacheService);
				if (listTemp.size() > 0) {
					buildTreeNode(esmeServices, listTemp);
				}
			}
		}
	}

	private List<EsmeServices> getAllChildrenIsRoot(EsmeServices parent, List<EsmeServices> list) {

		List<EsmeServices> listChildren = new ArrayList<EsmeServices>();
		for (EsmeServices esmeServices : list) {
			setSelectForTreeNode(esmeServices);
			if ((esmeServices.getParentId() == null)) {
				listChildren.add(esmeServices);
			}
		}
		return listChildren;
	}

	private List<EsmeServices> getAllChildren(EsmeServices parent, List<EsmeServices> list) {

		List<EsmeServices> listChildren = new ArrayList<EsmeServices>();
		for (EsmeServices esmeServices : list) {
			setSelectForTreeNode(esmeServices);
			if ((esmeServices.getParentId() != null)) {
				if (parent.getServiceId() == esmeServices.getParentId()) {
					listChildren.add(esmeServices);
				}
			}
		}
		return listChildren;
	}

	private void setSelectForTreeNode(EsmeServices esmeServices) {

		if (listServicesForIsdn.contains(esmeServices))
			esmeServices.setSelect(true);
		else
			esmeServices.setSelect(false);
	}

	private void initService() {

	}

	@SuppressWarnings("serial")
	private void initTable() {

		tbl = new CustomTable("", data, pnlAction) {

			@Override
			public Collection<?> getSortableContainerPropertyIds() {

				ArrayList<Object> arr = new ArrayList<Object>();
				Object[] sortCol = TM.get("special.table.setsortcolumns").split(",");
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

				if ("type".equals(pid)) {
					return TM.get("special.type.value." + property.getValue().toString());
				}

				if ("reason".equals(pid)) {
					return TM.get("special.reason.value." + property.getValue().toString());
				}

				return super.formatPropertyValue(rowId, colId, property);
			}
		};

		tbl.addActionHandler(this);
		tbl.setMultiSelect(true);
		tbl.setImmediate(true);

		tbl.addListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {

				Object id = tbl.getValue();
				setEnableAction(id);
			}

		});

		tbl.addListener(new Container.ItemSetChangeListener() {

			public void containerItemSetChange(ItemSetChangeEvent event) {

				pnlAction.setRowSelected(false);
			}
		});

		// if (getPermission().contains("U")) {
		tbl.addListener(new ItemClickEvent.ItemClickListener() {

			private static final long serialVersionUID = 2068314108919135281L;

			public void itemClick(ItemClickEvent event) {

				EsmeIsdnSpecial bean = (EsmeIsdnSpecial) ((BeanItem) event.getItem()).getBean();
				setTreeTablePanelCaption(bean);
				reBuildTreeTable(bean);
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

				final EsmeIsdnSpecial bean = (EsmeIsdnSpecial) itemId;

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
						EsmeIsdnSpecial bean = data.getIdByIndex(i);
						bean.setSelect(tbl.isSelectAll());
						tbl.setColumnHeader("select", (tbl.isSelectAll() == true) ? "-" : "+");
						tbl.refreshRowCache();
					}
				}
			}
		});

		// tbl.setSortDisabled(true);
		// tbl.setSortContainerPropertyId(TM.get("special.table.setsortcolumns")
		// .split(","));

		tbl.setSortContainerPropertyId(TM.get("special.table.setsortcolumns").split(","));

		tbl.setVisibleColumns(TM.get("special.table.setvisiblecolumns").split(","));
		tbl.setColumnHeaders(TM.get("special.table.setcolumnheaders").split(","));
		tbl.setStyleName("commont_table_noborderLR");

		String[] columnWidth = TM.get("special.table.columnwidth").split(",");
		String[] columnWidthValue = TM.get("special.table.columnwidth_value").split(",");
		for (int i = 0; i < columnWidth.length; i++) {
			tbl.setColumnWidth(columnWidth[i], Integer.parseInt(columnWidthValue[i]));
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
		// container.setActionPanel(pnlAction);
		container.initPager(CacheServiceClient.serviceIsdnSpecial.count(null, DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(true);
		pnlAction.setValueForCboField(TM.get("special.table.filteredcolumns").split(","), TM.get("special.table.filteredcolumnscaption").split(","));
		container.setFilteredColumns(TM.get("special.table.filteredcolumns").split(","));
		container.setEnableDeleteAllButton(getPermission().contains(TM.get("module.right.Delete")));
		container.setEnableButtonAddNew(getPermission().contains(TM.get("module.right.Insert")));
		container.setEnableButtonAddCopy(getPermission().contains(TM.get("module.right.Insert")));
		container.removeLayoutOnlySMS();
		container.removeHeaderSearchLayout();
		container.setVisibleBorderMainLayout(false);
	}

	private void setTreeTablePanelCaption(EsmeIsdnSpecial esmeIsdnSpecial) {

		if (esmeIsdnSpecial.getType().equals(strType2)) {
			treeTable.setColumnHeaders(TM.get("special.service.table.setcolumnheaders-block").split(","));
			btnBlock.setCaption(TM.get("special.service.btn.block.caption-block"));
		} else {
			treeTable.setColumnHeaders(TM.get("special.service.table.setcolumnheaders").split(","));
			btnBlock.setCaption(TM.get("special.service.btn.block.caption"));
		}
	}

	private void displayData(String sortedColumn, boolean asc, int start, int items) {

		try {
			data.removeAllItems();
			data.addAll(CacheServiceClient.serviceIsdnSpecial.findAllWithOrderPaging(null, skSearch, sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH));
			if (container != null)
				container.setLblCount(start);
			// System.out.println("all isdn:" + data.size());
			// tbl.sort(new Object[] { "name" }, new boolean[] { true });
		} catch (Exception e) {
			// MessageAlerter.showErrorMessageI18n(this.getWindow(),
			// TM.get("common.getdata.fail"));
			e.printStackTrace();
		}
	}

	@Override
	public void displayPage(ChangePageEvent event) {

		int start = event.getPageRange().getIndexPageStart();
		// int end = event.getPageRange().getIndexPageEnd();
		displayData(sortedColumn, sortedASC, start, event.getPageRange().getNumberOfRowsPerPage());
	}

	private void initForm() {

		form = new Form();
		form.setWriteThrough(false);
		form.setInvalidCommitted(false);
		form.setImmediate(false);
		fieldFactory = new FormIsdnSpecialFieldFactory();
		form.setFormFieldFactory(fieldFactory);

		dialog = new CommonDialog(TM.get("special.dialog.caption"), form, this);
		dialog.setWidth(TM.get("common.dialog.fixedwidth"));
		dialog.setHeight("300px");
		dialog.addListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {

				pnlAction.clearAction();
			}
		});
	}

	private Window createDialog(Item item) {

		form.setItemDataSource(item);
		form.setVisibleItemProperties(TM.get("special.form.visibleproperties").split(","));
		form.setValidationVisible(false);
		// form.focus();
		getWindow().addWindow(dialog);
		return dialog;
	}

	public void showDialog(Object object) {

		if (getWindow().getChildWindows().contains(dialog)) {
			return;
		}

		Item item = null;
		int action = pnlAction.getAction();

		if (action == PanelActionProvider.ACTION_EDIT) {
			item = tbl.getItem(object);
			fieldFactory.setOldMsisdn(((EsmeIsdnSpecial) object).getMsisdn());
			fieldFactory.setOldType(((EsmeIsdnSpecial) object).getType());
		} else if (action == PanelActionProvider.ACTION_ADD_COPY) {
			Set<EsmeIsdnSpecial> setInstrument = (Set<EsmeIsdnSpecial>) tbl.getValue();
			EsmeIsdnSpecial bean = setInstrument.iterator().next();
			EsmeIsdnSpecial newBean = new EsmeIsdnSpecial();
			fieldFactory.setOldMsisdn(null);
			fieldFactory.setOldType(null);
			newBean.setMsisdn("");
			newBean.setName(bean.getName());
			newBean.setReason(bean.getReason());
			newBean.setStatus(bean.getStatus());
			newBean.setType(bean.getType());

			item = new BeanItem<EsmeIsdnSpecial>(newBean);
		} else if (action == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
			EsmeIsdnSpecial newBean = new EsmeIsdnSpecial();
			fieldFactory.setOldMsisdn(null);
			fieldFactory.setOldType(null);
			newBean.setMsisdn(null);
			newBean.setName(null);
			newBean.setReason("1");
			newBean.setStatus("1");
			newBean.setType("2");
			item = new BeanItem<EsmeIsdnSpecial>(newBean);
		} else {
			fieldFactory.setOldMsisdn(null);
			fieldFactory.setOldType(null);
			EsmeIsdnSpecial newBean = new EsmeIsdnSpecial();
			newBean.setMsisdn("");
			newBean.setName("");
			newBean.setReason("1");
			newBean.setStatus("1");
			newBean.setType("2");
			item = new BeanItem<EsmeIsdnSpecial>(newBean);
		}
		createDialog(item);
	}

	private void reBuildTreeTable(EsmeIsdnSpecial esmeIsdnSpecial) {

		listServicesForIsdn.clear();
		if (esmeIsdnSpecial != null) {
			EsmeIsdnPermission esmeIsdnPermission = new EsmeIsdnPermission();
			esmeIsdnPermission.setEsmeIsdnSpecial(esmeIsdnSpecial);
			esmeIsdnPermission.setType(esmeIsdnSpecial.getType().equals("1") ? "2" : "1");
			SearchEntity searchEntity = new SearchEntity();
			searchEntity.setSwitchCase(TM.get("CASE-END"));
			try {

				for (EsmeIsdnPermission esmeIPer : CacheServiceClient.serviceIsdnPermission.findAllWithParameter(searchEntity, esmeIsdnPermission)) {
					listServicesForIsdn.add(esmeIPer.getEsmeServices());
				}
			} catch (com.fis.esme.isdnpermission.Exception_Exception e) {
				e.printStackTrace();
			}
		}
		buildDataForTreeTable();
	}

	public void doIsdnPermission(EsmeIsdnSpecial esmeIsdnSpecial, List<EsmeServices> listSevicesSelected) {

		int failCount = 0;

		String isdnPermissionType = esmeIsdnSpecial.getType().equals("1") ? "2" : "1";

		EsmeIsdnPermission esmeIsdnPermission = new EsmeIsdnPermission();
		esmeIsdnPermission.setPermissionId(-1);
		esmeIsdnPermission.setEsmeIsdnSpecial(esmeIsdnSpecial);
		esmeIsdnPermission.setType(isdnPermissionType);
		try {
			CacheServiceClient.serviceIsdnPermission.delete(esmeIsdnPermission);
		} catch (com.fis.esme.isdnpermission.Exception_Exception e1) {
			e1.printStackTrace();
			MessageAlerter.showMessage(btnBlock.getWindow(), TM.get("special.submit.apterblock.fail"));
			return;
		}

		for (EsmeServices esmeServices : listSevicesSelected) {
			EsmeIsdnPermission iPer = new EsmeIsdnPermission();
			iPer.setEsmeIsdnSpecial(esmeIsdnSpecial);
			iPer.setEsmeServices(esmeServices);
			iPer.setType(isdnPermissionType);

			try {
				Long id = CacheServiceClient.serviceIsdnPermission.add(iPer);
				if (id != null) {
					iPer.setPermissionId(id);
				}
			} catch (com.fis.esme.isdnpermission.Exception_Exception e) {
				e.printStackTrace();
				failCount++;
				continue;
			}
		}

		if (isdnPermissionType.equals("2"))
			MessageAlerter.showMessageI18n(btnBlock.getWindow(), TM.get("special.submit.open.success", listSevicesSelected.size() - failCount, listSevicesSelected.size()));
		if (isdnPermissionType.equals("1"))
			MessageAlerter.showMessageI18n(btnBlock.getWindow(), TM.get("special.submit.block.success", listSevicesSelected.size() - failCount, listSevicesSelected.size()));

	}

	public void accept() {

		try {

			boolean modified = form.isModified();
			if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT && !modified) {
				pnlAction.clearAction();
				return;
			}

			form.commit();
			BeanItem<EsmeIsdnSpecial> beanItem = null;
			beanItem = (BeanItem<EsmeIsdnSpecial>) form.getItemDataSource();
			EsmeIsdnSpecial bean = beanItem.getBean();
			bean.getMsisdn().trim();

			if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD || pnlAction.getAction() == PanelActionProvider.ACTION_ADD_COPY
			        || pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
				try {
					String id = CacheServiceClient.serviceIsdnSpecial.add(bean);
					bean.setMsisdn(id);
					if (id != null) {
						// cacheService.add(msv);
						container.initPager(CacheServiceClient.serviceIsdnSpecial.count(null, DEFAULT_EXACT_MATCH));

						// tbl.addItem(msv);
						tblSetARowSelect(bean);

						LogUtil.logActionInsert(FormIsdnSpecial.class.getName(), "ESME_ISDN_SPECIAL", "MSISDN", "" + bean.getMsisdn() + "", null);

						if (pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
							pnlAction.clearAction();
							pnlAction.searchOrAddNew(bean.getName());
						}

						MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.add.success", TM.get("special.caption").toLowerCase()));
					} else {
						MessageAlerter.showErrorMessage(getWindow(), TM.get("common.msg.add.fail", TM.get("special.caption").toLowerCase()));
					}
				} catch (Exception e) {
					FormUtil.showException(this, e);
				}
			} else if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT) {
				try {

					Vector v = LogUtil.logActionBeforeUpdate(FormIsdnSpecial.class.getName(), "ESME_ISDN_SPECIAL", "MSISDN", "" + bean.getMsisdn() + "", null);

					if (bean.getMsisdn().equalsIgnoreCase(fieldFactory.getOldMsisdn())) {
						CacheServiceClient.serviceIsdnSpecial.update(bean);
					} else {
						ArrayList<EsmeIsdnPermission> arrDel = (ArrayList<EsmeIsdnPermission>) CacheServiceClient.serviceIsdnSpecial.getPermissionByMisdn(fieldFactory.getOldMsisdn());
						CacheServiceClient.serviceIsdnSpecial.deletePermissionByMsisdn(fieldFactory.getOldMsisdn());
						CacheServiceClient.serviceIsdnSpecial.updateSpecial(bean, fieldFactory.getOldMsisdn());
						for (EsmeIsdnPermission esmeIsdnPermission : arrDel) {
							EsmeIsdnPermission addPermission = new EsmeIsdnPermission();
							addPermission.setEsmeIsdnSpecial(bean);
							addPermission.setEsmeServices(esmeIsdnPermission.getEsmeServices());
							addPermission.setType(esmeIsdnPermission.getType());
							CacheServiceClient.serviceIsdnPermission.add(addPermission);
						}
					}

					if (!bean.getType().equals(fieldFactory.getOldType())) {
						EsmeIsdnPermission esmeIsdnPermission = new EsmeIsdnPermission();
						esmeIsdnPermission.setPermissionId(-1);
						esmeIsdnPermission.setEsmeIsdnSpecial(bean);
						try {
							CacheServiceClient.serviceIsdnPermission.delete(esmeIsdnPermission);
						} catch (com.fis.esme.isdnpermission.Exception_Exception e1) {
							e1.printStackTrace();
							MessageAlerter.showMessage(getWindow(), e1.getMessage());
						}
					}

					// int index = cacheService.indexOf(msv);
					// cacheService.remove(msv);
					// cacheService.add(index, msv);
					tblSetARowSelect(bean);
					LogUtil.logActionAfterUpdate(v);

					MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.edit.success", TM.get("special.caption").toLowerCase()));

				} catch (Exception e) {
					FormUtil.showException(this, e);
				}
			}

			setTreeTablePanelCaption(bean);
			reBuildTreeTable(bean);

		} catch (Exception e) {
			FormUtil.showException(this, e);
		}
		pnlAction.clearAction();
		// FormUtil.clearCache(null);
	}

	@Override
	public String getPermission() {

		return SessionData.getAppClient().getPermission(this.getClass().getName());
	}

	private void loadDataFromDatabase() {

		try {
			data.addAll(CacheServiceClient.serviceIsdnSpecial.findAllWithoutParameter());
		} catch (com.fis.esme.isdnspecial.Exception_Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(Object object) {

		resetResource();
		if (object instanceof EsmeIsdnSpecial) {
			EsmeIsdnSpecial esmeIsdnSpecial = (EsmeIsdnSpecial) object;
			boolean b = CacheServiceClient.serviceIsdnSpecial.checkConstraints(esmeIsdnSpecial.getMsisdn());
			if (!b) {
				total++;
				canDelete.add(esmeIsdnSpecial);
			}
		} else {
			for (EsmeIsdnSpecial obj : (List<EsmeIsdnSpecial>) object) {
				total++;

				boolean b = CacheServiceClient.serviceIsdnSpecial.checkConstraints(obj.getMsisdn());
				if (!b) {
					canDelete.add(obj);
				}
			}
		}

		if (canDelete.size() == 0) {
			MessageAlerter.showMessageI18n(getWindow(), TM.get("comman.message.delete.data.emty"));
		} else {
			String message = TM.get("common.msg.delete.confirm");
			confirmDeletion(message);
		}
	}

	private void resetResource() {

		canDelete.clear();
		total = 0;
	}

	private void confirmDeletion(String message) {

		if (confirm == null) {
			confirm = new ConfirmDeletionDialog(getApplication());
		}
		confirm.show(message, this);
	}

	private void doDelete() {

		// try
		// {
		int deleted = 0;
		for (EsmeIsdnSpecial msv : canDelete) {
			try {
				LogUtil.logActionDelete(FormIsdnSpecial.class.getName(), "ESME_ISDN_SPECIAL", "MSISDN", "" + msv.getMsisdn() + "", null);

				CacheServiceClient.serviceIsdnSpecial.delete(msv);
				// cacheService.remove(msv);
				tbl.removeItem(msv);

				deleted++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		container.initPager(CacheServiceClient.serviceIsdnSpecial.count(null, DEFAULT_EXACT_MATCH));

		// FormUtil.clearCache(null);
		MessageAlerter.showMessageI18n(getWindow(), TM.get("message.delete"), deleted, total);
		reBuildTreeTable(null);
		// }
		// catch (Exception e)
		// {
		// FormUtil.showException(this, e);
		// }
	}

	private void setEnableAction(Object id) {

		final boolean enable = (id != null);
		form.setItemDataSource(tbl.getItem(id));
		pnlAction.setRowSelected(enable);
		// System.out.println("setEnableAction" + enable);
		// tbl.removeAllActionHandlers();
		// tbl.addActionHandler(new Action.Handler() {
		//
		// @Override
		// public void handleAction(Action action, Object sender, Object target)
		// {
		// pnlAction.doRightClickAction(action);
		// }
		//
		// @Override
		// public Action[] getActions(Object target, Object sender) {
		// List<Action> actions = pnlAction
		// .getRightClickTableAction(enable);
		// return actions.toArray(new Action[actions.size()]);
		// }
		// });

	}

	private void tblSetARowSelect(Object id) {

		tbl.setMultiSelect(false);
		tbl.select(id);
		tbl.setMultiSelect(true);
	}

	@Override
	public void dialogClosed(OptionKind option) {

		if (OptionKind.OK.equals(option)) {
			if (canDelete != null && canDelete.size() > 0) {
				doDelete();
			}
		}
	}

	@Override
	public void requestSort(String cloumn, boolean asc) {

		// TODO Auto-generated method stub
		sortedColumn = cloumn;
		sortedASC = asc;
		int items = container.getItemPerPage();
		displayData(cloumn, asc, 0, items);
		container.changePage(1);
	}

	// @Override
	// public Action[] getActions(Object target, Object sender) {
	// List<Action> actions = pnlAction.getRightClickTableAction(false);
	// return actions.toArray(new Action[actions.size()]);
	// }
	//
	// @Override
	// public void handleAction(Action action, Object sender, Object target) {
	// pnlAction.doRightClickAction(action);
	// }

	@Override
	public void export() {

	}

	// class ExtendedActionHandler implements Action.Handler {
	// private boolean enable;
	//
	// public ExtendedActionHandler(boolean enable) {
	// System.out.println("ew create action");
	// this.enable = enable;
	// }
	//
	// @Override
	// public void handleAction(Action action, Object sender, Object target) {
	// pnlAction.doRightClickAction(action);
	// }
	//
	// @Override
	// public Action[] getActions(Object target, Object sender) {
	// List<Action> actions = pnlAction
	// .getRightClickTableAction(this.enable);
	// return actions.toArray(new Action[actions.size()]);
	// }
	// }

	@Override
	public void fieldSearch(SearchObj searchObj) {

		// System.out.println("searchObj" + searchObj);
		if (searchObj.getField() == null && searchObj.getKey() == null)
			return;

		skSearch = new EsmeIsdnSpecial();
		if (searchObj.getField() == null) {
			skSearch.setMsisdn(searchObj.getKey());
			skSearch.setName(searchObj.getKey());
		} else {
			if (searchObj.getField().equals("msisdn"))
				skSearch.setMsisdn(searchObj.getKey());
			else if (searchObj.getField().equals("name"))
				skSearch.setName(searchObj.getKey());
		}

		int count = CacheServiceClient.serviceIsdnSpecial.count(skSearch, DEFAULT_EXACT_MATCH);
		container.initPager(count);
		if (count <= 0) {
			MessageAlerter.showMessageI18n(getWindow(), TM.get("msg.search.value.emty"));
		}
		pnlAction.clearAction();
	}

	@Override
	public Action[] getActions(Object target, Object sender) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleAction(Action action, Object sender, Object target) {

		// TODO Auto-generated method stub

	}

	public List<EsmeIsdnSpecial> getAllItemCheckedOnTable() {

		List<EsmeIsdnSpecial> list = new ArrayList<EsmeIsdnSpecial>();
		Collection<EsmeIsdnSpecial> collection = (Collection<EsmeIsdnSpecial>) tbl.getItemIds();
		for (EsmeIsdnSpecial obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

	public List<EsmeServices> getAllItemCheckedOnTreeTable() {

		List<EsmeServices> list = new ArrayList<EsmeServices>();
		Collection<EsmeServices> collection = (Collection<EsmeServices>) treeTable.getItemIds();
		for (EsmeServices obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

	@Override
	public void searchOrAddNew(String key) {

		skSearch = new EsmeIsdnSpecial();
		skSearch.setName(key);
		DEFAULT_EXACT_MATCH = true;
		int count = CacheServiceClient.serviceIsdnSpecial.count(skSearch, DEFAULT_EXACT_MATCH);
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

	}

	@Override
	public void filterTree(Object obj) {

		treeTable.select(obj);
		treeTable.focus();
	}

	@Override
	public void treeValueChanged(Object obj) {

		// TODO Auto-generated method stub

	}

}