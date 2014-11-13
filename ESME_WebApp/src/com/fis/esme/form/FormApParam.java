package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.fis.esme.admin.SessionData;
import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.apparam.ApParamTransferer;
import com.fis.esme.classes.ServerSort;
import com.fis.esme.component.CommonButtonPanel;
import com.fis.esme.component.CommonDialog;
import com.fis.esme.component.ConfirmDeletionDialog;
import com.fis.esme.component.CustomTable;
import com.fis.esme.component.PagingComponent.ChangePageEvent;
import com.fis.esme.component.PagingComponent.PagingComponentListener;
import com.fis.esme.component.PanelActionProvider;
import com.fis.esme.component.TableContainer;
import com.fis.esme.persistence.ApParam;
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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Form;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.HeaderClickEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

import eu.livotov.tpt.gui.dialogs.OptionDialog.OptionDialogResultListener;
import eu.livotov.tpt.gui.dialogs.OptionKind;
import eu.livotov.tpt.i18n.TM;

public class FormApParam extends VerticalLayout implements PanelActionProvider, PagingComponentListener, ServerSort, Action.Handler, OptionDialogResultListener {

	private CommonDialog dialog;
	private Form form;
	private CustomTable tbl;
	private TableContainer container;
	private BeanItemContainer<ApParam> data;
	private CommonButtonPanel pnlAction;
	private FormApParamFieldFactory fieldFactory;

	private final String DEFAULT_SORTED_COLUMN = "parType";
	private boolean DEFAULT_SORTED_ASC = true;
	private boolean DEFAULT_EXACT_MATCH = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;
	// private int totalInDatabase = 0;
	private ApParam skSearch = null;

	private int total = 0;
	private ArrayList<ApParam> canDelete = new ArrayList<ApParam>();
	private ConfirmDeletionDialog confirm;
	private ApParamTransferer serviceApParam;

	private String oldID = null;

	public FormApParam(String key) {

		this();
		if (key != null) {
			pnlAction.clearAction();
			pnlAction.searchOrAddNew(key);
		}
	}

	public FormApParam() {

		LogUtil.logAccess(FormApParam.class.getName());
		initLayout();
	}

	private void initLayout() {

		pnlAction = new CommonButtonPanel(this);
		pnlAction.showSearchPanel(true);
		pnlAction.setFromCaption(TM.get(FormApParam.class.getName()));
		pnlAction.setMargin(false, false, true, false);
		this.addComponent(pnlAction);
		initComponent();
		this.setSizeFull();
		this.addComponent(container);
		this.setExpandRatio(container, 1.0f);
		this.setComponentAlignment(container, Alignment.TOP_CENTER);
	}

	private void initComponent() {

		data = new BeanItemContainer<ApParam>(ApParam.class);
		initService();
		initTable();
		initForm();
		// loadDataFromDatabase();
	}

	private void initService() {

		try {
			serviceApParam = CacheServiceClient.serviceApParam;
		} catch (Exception e) {
			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("common.create.service.fail") + "</br>" + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	private void initTable() {

		tbl = new CustomTable("", data, pnlAction) {

			@Override
			public Collection<?> getSortableContainerPropertyIds() {

				ArrayList<Object> arr = new ArrayList<Object>();
				Object[] sortCol = TM.get("ApParam.table.setsortcolumns").split(",");
				for (Object obj : sortCol) {

					arr.add(obj);

				}
				return arr;
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

				final ApParam bean = (ApParam) itemId;

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
						ApParam bean = data.getIdByIndex(i);
						bean.setSelect(tbl.isSelectAll());
						tbl.setColumnHeader("select", (tbl.isSelectAll() == true) ? "-" : "+");
						tbl.refreshRowCache();
					}
				}
			}
		});

		// tbl.setSortDisabled(true);
		// tbl.setSortContainerPropertyId(TM.get("service.table.setsortcolumns")
		// .split(","));

		tbl.setSortContainerPropertyId(TM.get("ApParam.table.setsortcolumns").split(","));

		tbl.setVisibleColumns(TM.get("ApParam.table.setvisiblecolumns").split(","));
		tbl.setColumnHeaders(TM.get("ApParam.table.setcolumnheaders").split(","));
		tbl.setStyleName("commont_table_noborderLR");

		String[] columnWidth = TM.get("ApParam.table.columnwidth").split(",");
		String[] columnWidthValue = TM.get("ApParam.table.columnwidth_value").split(",");
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
		container.initPager(serviceApParam.count(null, DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(true);
		pnlAction.setValueForCboField(TM.get("ApParam.table.filteredcolumns").split(","), TM.get("ApParam.table.filteredcolumnscaption").split(","));
		container.setFilteredColumns(TM.get("ApParam.table.filteredcolumns").split(","));
		container.setEnableDeleteAllButton(getPermission().contains("D"));
		container.setEnableButtonAddNew(getPermission().contains("I"));
		container.setEnableButtonAddCopy(getPermission().contains("I"));
	}

	private void displayData(String sortedColumn, boolean asc, int start, int items) {

		try {
			data.removeAllItems();
			data.addAll(serviceApParam.findAllWithOrderPaging(skSearch, sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH));
			if (container != null)
				container.setLblCount(start);

			tbl.sort(new Object[] { "name" }, new boolean[] { true });
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
		fieldFactory = new FormApParamFieldFactory();
		form.setFormFieldFactory(fieldFactory);

		dialog = new CommonDialog(TM.get("ApParam.commondialog.caption"), form, this);
		dialog.setWidth(TM.get("common.dialog.fixedwidth"));
		dialog.setHeight("350px");
		dialog.addListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {

				pnlAction.clearAction();
			}
		});
	}

	private Window createDialog(Item item) {

		form.setItemDataSource(item);
		form.setVisibleItemProperties(TM.get("ApParam.form.visibleproperties").split(","));
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

			fieldFactory.setOldName(((ApParam) object).getParName());
			fieldFactory.setOldType(((ApParam) object).getParType());
		} else if (action == PanelActionProvider.ACTION_ADD_COPY) {
			Set<ApParam> setInstrument = (Set<ApParam>) tbl.getValue();
			ApParam msv = setInstrument.iterator().next();

			ApParam newBean = new ApParam();
			fieldFactory.setOldName(null);
			newBean.setParName(msv.getParName());
			newBean.setDescription(msv.getDescription());
			newBean.setParType(msv.getParType());
			newBean.setParValue(msv.getParValue());

			item = new BeanItem<ApParam>(newBean);
		} else if (action == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
			ApParam srcService = (ApParam) object;
			ApParam bean = new ApParam();
			bean.setParName(srcService.getParName());
			bean.setDescription("");
			bean.setParType(srcService.getParType());
			bean.setParValue("");
			item = new BeanItem<ApParam>(bean);
		} else {
			fieldFactory.setOldName(null);
			fieldFactory.setOldType(null);
			ApParam msv = new ApParam();
			msv.setParName("");
			msv.setDescription("");
			msv.setParType("");
			msv.setParValue("");
			item = new BeanItem<ApParam>(msv);
		}
		createDialog(item);
	}

	public void accept() {

		try {
			boolean modified = form.isModified();
			if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT && !modified) {
				pnlAction.clearAction();
				return;
			}

			form.commit();
			BeanItem<ApParam> beanItem = null;
			beanItem = (BeanItem<ApParam>) form.getItemDataSource();
			ApParam msv = beanItem.getBean();
			if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD || pnlAction.getAction() == PanelActionProvider.ACTION_ADD_COPY
			        || pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
				try {
					String id = serviceApParam.add(msv);
					msv.setParType(id);
					if (id != null) {
						// cacheService.add(msv);
						container.initPager(serviceApParam.count(null, DEFAULT_EXACT_MATCH));

						// tbl.addItem(msv);
						tblSetARowSelect(msv);

						LogUtil.logActionInsert(FormApParam.class.getName(), "AP_PARAM", "PAR_TYPE", "" + msv.getParType() + "", null);

						if (pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
							pnlAction.clearAction();
							pnlAction.searchOrAddNew(msv.getParType());
						}

						MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.add.success", TM.get("common.ApParam").toLowerCase()));
					} else {
						MessageAlerter.showErrorMessage(getWindow(), TM.get("common.msg.add.fail", TM.get("common.ApParam").toLowerCase()));
					}
				} catch (Exception e) {
					FormUtil.showException(this, e);
				}
			} else if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT) {
				try {

					Vector v = LogUtil.logActionBeforeUpdate(FormApParam.class.getName(), "AP_PARAM", "PAR_TYPE", "" + msv.getParType() + "", null);

					// serviceApParam.update(msv);
					ApParam pa = new ApParam();
					pa.setParType(fieldFactory.getOldType());
					pa.setParName(fieldFactory.getOldName());
					serviceApParam.delete(pa);
					serviceApParam.add(msv);
					// int index = cacheService.indexOf(msv);
					// cacheService.remove(msv);
					// cacheService.add(index, msv);
					tblSetARowSelect(msv);
					LogUtil.logActionAfterUpdate(v);

					MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.edit.success", TM.get("common.ApParam").toLowerCase()));

				} catch (Exception e) {
					FormUtil.showException(this, e);
				}
			}
		} catch (Exception e) {
			FormUtil.showException(this, e);
		}

		pnlAction.clearAction();
		FormUtil.clearCache(null);
	}

	@Override
	public String getPermission() {

		return SessionData.getAppClient().getPermission(this.getClass().getName());
	}

	private void loadDataFromDatabase() {

		try {
			data.addAll(serviceApParam.findAllWithoutParameter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(Object object) {

		resetResource();
		if (object instanceof ApParam) {
			ApParam ApParam = (ApParam) object;
			boolean b = serviceApParam.checkConstraints(ApParam.getParType());
			if (!b) {
				total++;
				canDelete.add(ApParam);
			} else {

				MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("message.delete.constraints"));
				return;
			}
		} else {
			for (ApParam obj : (List<ApParam>) object) {
				total++;

				boolean b = serviceApParam.checkConstraints(obj.getParType());
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
		for (ApParam msv : canDelete) {
			try {
				LogUtil.logActionDelete(FormApParam.class.getName(), "AP_PARAM", "PAR_TYPE", "" + msv.getParType() + "", null);

				serviceApParam.delete(msv);
				// cacheService.remove(msv);
				tbl.removeItem(msv);

				deleted++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		container.initPager(serviceApParam.count(null, DEFAULT_EXACT_MATCH));

		FormUtil.clearCache(null);
		MessageAlerter.showMessageI18n(getWindow(), TM.get("message.delete"), deleted, total);

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

		System.out.println("searchObj" + searchObj);
		if (searchObj.getField() == null && searchObj.getKey() == null)
			return;

		skSearch = new ApParam();
		if (searchObj.getField() == null) {

			skSearch.setParName(searchObj.getKey());

		} else {

			if (searchObj.getField().equals("parType"))

				skSearch.setParType(searchObj.getKey());

			else if (searchObj.getField().equals("parName"))

				skSearch.setParName(searchObj.getKey());

			else if (searchObj.getField().equals("parValue"))

				skSearch.setParValue(searchObj.getKey());

			else if (searchObj.getField().equals("description"))

				skSearch.setDescription(searchObj.getKey());

		}

		int count = serviceApParam.count(skSearch, DEFAULT_EXACT_MATCH);
		if (count > 0) {
			container.initPager(count);
		} else {
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

	public List<ApParam> getAllItemCheckedOnTable() {

		List<ApParam> list = new ArrayList<ApParam>();
		Collection<ApParam> collection = (Collection<ApParam>) tbl.getItemIds();
		for (ApParam obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

	@Override
	public void searchOrAddNew(String key) {

		skSearch = new ApParam();
		skSearch.setParType(key);
		DEFAULT_EXACT_MATCH = true;
		int count = serviceApParam.count(skSearch, DEFAULT_EXACT_MATCH);
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

}