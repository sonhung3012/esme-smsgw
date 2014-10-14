package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.fis.esme.admin.SessionData;
import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.ServerSort;
import com.fis.esme.component.CommonButtonPanel;
import com.fis.esme.component.CommonDialog;
import com.fis.esme.component.ConfirmDeletionDialog;
import com.fis.esme.component.CustomTable;
import com.fis.esme.component.PagingComponent.ChangePageEvent;
import com.fis.esme.component.PagingComponent.PagingComponentListener;
import com.fis.esme.component.PanelActionProvider;
import com.fis.esme.component.TableContainer;
import com.fis.esme.persistence.EsmeIsdnSpecial;
import com.fis.esme.service.ServiceTransferer;
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

public class CopyOfFormIsdnSpecial extends VerticalLayout implements
		PanelActionProvider, PagingComponentListener, ServerSort,
		Action.Handler, OptionDialogResultListener {

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

	private String oldID = null;

	public CopyOfFormIsdnSpecial(String key) {
		this();
		if (key != null) {
			pnlAction.clearAction();
			pnlAction.searchOrAddNew(key);
		}
	}

	public CopyOfFormIsdnSpecial() {
		LogUtil.logAccess(CopyOfFormIsdnSpecial.class.getName());
		initLayout();
	}

	private void initLayout() {
		pnlAction = new CommonButtonPanel(this);
		pnlAction.showSearchPanel(true);
		pnlAction.setFromCaption(TM.get(CopyOfFormIsdnSpecial.class.getName()));
		pnlAction.setMargin(false, false, true, false);
		this.addComponent(pnlAction);
		initComponent();
		this.setSizeFull();
		this.addComponent(container);
		this.setExpandRatio(container, 1.0f);
		this.setComponentAlignment(container, Alignment.TOP_CENTER);
	}

	private void initComponent() {
		data = new BeanItemContainer<EsmeIsdnSpecial>(EsmeIsdnSpecial.class);
		initService();
		initTable();
		initForm();
		// loadDataFromDatabase();
	}

	private void initService() {

	}

	@SuppressWarnings("serial")
	private void initTable() {

		tbl = new CustomTable("", data, pnlAction) {
			@Override
			public Collection<?> getSortableContainerPropertyIds() {
				ArrayList<Object> arr = new ArrayList<Object>();
				Object[] sortCol = TM.get("special.table.setsortcolumns")
						.split(",");
				for (Object obj : sortCol) {
//					System.out.println(obj);
					arr.add(obj);

				}
				return arr;
			}

			@Override
			protected String formatPropertyValue(Object rowId, Object colId,
					Property property) {
				String pid = (String) colId;
				if (property.getValue() == null) {
					return super.formatPropertyValue(rowId, colId, property);
				}

				if ("type".equals(pid)) {
					return TM.get("special.type.value."
							+ property.getValue().toString());
				}

				if ("reason".equals(pid)) {
					return TM.get("special.reason.value."
							+ property.getValue().toString());
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
		// tbl.addListener(new ItemClickEvent.ItemClickListener() {
		// private static final long serialVersionUID = 2068314108919135281L;
		//
		// public void itemClick(ItemClickEvent event) {
		// if (event.isDoubleClick()) {
		// pnlAction.edit();
		// }
		// }
		// });
		// }

		tbl.addGeneratedColumn("select", new Table.ColumnGenerator() {
			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
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
						tbl.setColumnHeader("select",
								(tbl.isSelectAll() == true) ? "-" : "+");
						tbl.refreshRowCache();
					}
				}
			}
		});

		// tbl.setSortDisabled(true);
		// tbl.setSortContainerPropertyId(TM.get("special.table.setsortcolumns")
		// .split(","));

		tbl.setSortContainerPropertyId(TM.get("special.table.setsortcolumns")
				.split(","));

		tbl.setVisibleColumns(TM.get("special.table.setvisiblecolumns").split(
				","));
		tbl.setColumnHeaders(TM.get("special.table.setcolumnheaders")
				.split(","));
		tbl.setStyleName("commont_table_noborderLR");

		String[] columnWidth = TM.get("special.table.columnwidth").split(",");
		String[] columnWidthValue = TM.get("special.table.columnwidth_value")
				.split(",");
		for (int i = 0; i < columnWidth.length; i++) {
			tbl.setColumnWidth(columnWidth[i],
					Integer.parseInt(columnWidthValue[i]));
		}

		if (tbl.getContainerDataSource().equals(null)) {
			pnlAction.setRowSelected(false);
		}

		container = new TableContainer(tbl, this, Integer.parseInt(TM
				.get("pager.page.rowsinpage"))) {
			@Override
			public void deleteAllItemSelected() {
				pnlAction.delete(getAllItemCheckedOnTable());
			}
		};
		// container.setActionPanel(pnlAction);
		container.initPager(CacheServiceClient.serviceIsdnSpecial.count(null,
				DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(true);
		pnlAction.setValueForCboField(TM.get("special.table.filteredcolumns")
				.split(","), TM.get("special.table.filteredcolumnscaption")
				.split(","));
		container.setFilteredColumns(TM.get("special.table.filteredcolumns")
				.split(","));
		container.setEnableDeleteAllButton(getPermission().contains("D"));
	}

	private void displayData(String sortedColumn, boolean asc, int start,
			int items) {
		try {
			data.removeAllItems();
			data.addAll(CacheServiceClient.serviceIsdnSpecial
					.findAllWithOrderPaging(null, skSearch, sortedColumn, asc,
							start, items, DEFAULT_EXACT_MATCH));
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
		displayData(sortedColumn, sortedASC, start, event.getPageRange()
				.getNumberOfRowsPerPage());
	}

	private void initForm() {
		form = new Form();
		form.setWriteThrough(false);
		form.setInvalidCommitted(false);
		form.setImmediate(false);
		fieldFactory = new FormIsdnSpecialFieldFactory();
		form.setFormFieldFactory(fieldFactory);

		dialog = new CommonDialog(TM.get("special.commondialog.caption"), form,
				this);
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
		form.setVisibleItemProperties(TM.get("special.form.visibleproperties")
				.split(","));
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
			// fieldFactory.setOldCode(((EsmeIsdnSpecial) object).getCode());
		} else if (action == PanelActionProvider.ACTION_ADD_COPY) {
			Set<EsmeIsdnSpecial> setInstrument = (Set<EsmeIsdnSpecial>) tbl
					.getValue();
			EsmeIsdnSpecial bean = setInstrument.iterator().next();
			EsmeIsdnSpecial newBean = new EsmeIsdnSpecial();
			fieldFactory.setOldMsisdn(null);
			newBean.setMsisdn(null);
			newBean.setName(bean.getName());
			newBean.setReason(bean.getReason());
			newBean.setStatus(bean.getStatus());
			newBean.setType(bean.getType());

			item = new BeanItem<EsmeIsdnSpecial>(newBean);
		} else if (action == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
			EsmeIsdnSpecial srcService = (EsmeIsdnSpecial) object;
			EsmeIsdnSpecial newBean = new EsmeIsdnSpecial();
			fieldFactory.setOldMsisdn(null);
			newBean.setMsisdn(null);
			newBean.setName(null);
			newBean.setReason("1");
			newBean.setStatus("1");
			newBean.setType("2");
			item = new BeanItem<EsmeIsdnSpecial>(newBean);
		} else {
			fieldFactory.setOldMsisdn(null);
			EsmeIsdnSpecial newBean = new EsmeIsdnSpecial();
			newBean.setMsisdn(null);
			newBean.setName(null);
			newBean.setReason("1");
			newBean.setStatus("1");
			newBean.setType("2");
			item = new BeanItem<EsmeIsdnSpecial>(newBean);
		}
		createDialog(item);
	}

	public void accept() {
		try {
//			System.out.println("acce - " + pnlAction.getAction());
			boolean modified = form.isModified();
			if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT
					&& !modified) {
				pnlAction.clearAction();
				return;
			}

			form.commit();
			BeanItem<EsmeIsdnSpecial> beanItem = null;
			beanItem = (BeanItem<EsmeIsdnSpecial>) form.getItemDataSource();
			EsmeIsdnSpecial bean = beanItem.getBean();
			if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD
					|| pnlAction.getAction() == PanelActionProvider.ACTION_ADD_COPY
					|| pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
				try {
					String id = CacheServiceClient.serviceIsdnSpecial.add(bean);
					bean.setMsisdn(id);
					if (id != null) {
						// cacheService.add(msv);
						container
								.initPager(CacheServiceClient.serviceIsdnSpecial
										.count(null, DEFAULT_EXACT_MATCH));

						// tbl.addItem(msv);
						tblSetARowSelect(bean);

						LogUtil.logActionInsert(
								CopyOfFormIsdnSpecial.class.getName(),
								"ESME_ISDN_SPECIAL", "MSISDN",
								"" + bean.getMsisdn() + "", null);

						if (pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
							pnlAction.clearAction();
							pnlAction.searchOrAddNew(bean.getName());
						}

						MessageAlerter
								.showMessageI18n(getWindow(), TM.get(
										"common.msg.add.success",
										TM.get("common.service").toLowerCase()));
					} else {
						MessageAlerter
								.showErrorMessage(getWindow(), TM.get(
										"common.msg.add.fail",
										TM.get("common.service").toLowerCase()));
					}
				} catch (Exception e) {
					FormUtil.showException(this, e);
				}
			} else if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT) {
				try {

					Vector v = LogUtil.logActionBeforeUpdate(
							CopyOfFormIsdnSpecial.class.getName(),
							"ESME_ISDN_SPECIAL", "MSISDN",
							"" + bean.getMsisdn() + "", null);

					CacheServiceClient.serviceIsdnSpecial.update(bean);
					// int index = cacheService.indexOf(msv);
					// cacheService.remove(msv);
					// cacheService.add(index, msv);
					tblSetARowSelect(bean);
					LogUtil.logActionAfterUpdate(v);

					MessageAlerter.showMessageI18n(
							getWindow(),
							TM.get("common.msg.edit.success",
									TM.get("common.service").toLowerCase()));

				} catch (Exception e) {
					FormUtil.showException(this, e);
				}
			}
		} catch (Exception e) {
			FormUtil.showException(this, e);
		}

		pnlAction.clearAction();
		// FormUtil.clearCache(null);
	}

	@Override
	public String getPermission() {
		return SessionData.getAppClient().getPermission(
				this.getClass().getName());
	}

	private void loadDataFromDatabase() {
		try {
			data.addAll(CacheServiceClient.serviceIsdnSpecial
					.findAllWithoutParameter());
		} catch (com.fis.esme.isdnspecial.Exception_Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(Object object) {
		resetResource();
		if (object instanceof EsmeIsdnSpecial) {
			EsmeIsdnSpecial esmeIsdnSpecial = (EsmeIsdnSpecial) object;
			boolean b = CacheServiceClient.serviceIsdnSpecial
					.checkConstraints(esmeIsdnSpecial.getMsisdn());
			if (!b) {
				total++;
				canDelete.add(esmeIsdnSpecial);
			}
		} else {
			for (EsmeIsdnSpecial obj : (List<EsmeIsdnSpecial>) object) {
				total++;

				boolean b = CacheServiceClient.serviceIsdnSpecial
						.checkConstraints(obj.getMsisdn());
				if (!b) {
					canDelete.add(obj);
				}
			}
		}

		if (canDelete.size() == 0) {
			MessageAlerter.showErrorMessageI18n(getWindow(),
					TM.get("comman.message.delete.error"));
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
				LogUtil.logActionDelete(CopyOfFormIsdnSpecial.class.getName(),
						"ESME_ISDN_SPECIAL", "MSISDN", "" + msv.getMsisdn()
								+ "", null);

				CacheServiceClient.serviceIsdnSpecial.delete(msv);
				// cacheService.remove(msv);
				tbl.removeItem(msv);

				deleted++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		container.initPager(CacheServiceClient.serviceIsdnSpecial.count(null,
				DEFAULT_EXACT_MATCH));

		// FormUtil.clearCache(null);
		MessageAlerter.showMessageI18n(getWindow(), TM.get("message.delete"),
				deleted, total);

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
//		System.out.println("searchObj" + searchObj);
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

		int count = CacheServiceClient.serviceIsdnSpecial.count(skSearch,
				DEFAULT_EXACT_MATCH);
		container.initPager(count);
		if (count <= 0) {
			MessageAlerter.showMessageI18n(getWindow(),
					TM.get("msg.search.value.emty"));
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
		Collection<EsmeIsdnSpecial> collection = (Collection<EsmeIsdnSpecial>) tbl
				.getItemIds();
		for (EsmeIsdnSpecial obj : collection) {
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
		int count = CacheServiceClient.serviceIsdnSpecial.count(skSearch,
				DEFAULT_EXACT_MATCH);
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