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
import com.fis.esme.language.LanguageTransferer;
import com.fis.esme.persistence.EsmeLanguage;
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

public class FormLanguage extends VerticalLayout implements PanelActionProvider, PagingComponentListener, ServerSort, Action.Handler, OptionDialogResultListener {

	private CommonDialog dialog;
	private Form form;
	private CustomTable tbl;
	private TableContainer container;
	private BeanItemContainer<EsmeLanguage> data;
	private CommonButtonPanel pnlAction;
	private FormLanguageFieldFactory fieldFactory;
	private EsmeLanguage defaultLanguage;

	private final String DEFAULT_SORTED_COLUMN = "code";
	private boolean DEFAULT_SORTED_ASC = true;
	private boolean DEFAULT_EXACT_MATCH = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;
	// private int totalInDatabase = 0;
	private EsmeLanguage skSearch = null;

	private int total = 0;
	private ArrayList<EsmeLanguage> canDelete = new ArrayList<EsmeLanguage>();
	private ConfirmDeletionDialog confirm;
	private LanguageTransferer languageService;

	private String oldID = null;

	public FormLanguage(String key) {

		this();
		if (key != null) {
			pnlAction.clearAction();
			pnlAction.searchOrAddNew(key);
		}
	}

	public FormLanguage() {

		LogUtil.logAccess(FormLanguage.class.getName());
		initLayout();
	}

	private void initLayout() {

		pnlAction = new CommonButtonPanel(this);
		pnlAction.showSearchPanel(true);
		pnlAction.setFromCaption(TM.get(FormLanguage.class.getName()));
		pnlAction.setMargin(false, false, true, false);
		pnlAction.setSelectedValueForCategory(FormLanguage.class);
		this.addComponent(pnlAction);
		initComponent();
		this.setSizeFull();
		this.addComponent(container);
		this.setExpandRatio(container, 1.0f);
		this.setComponentAlignment(container, Alignment.TOP_CENTER);
	}

	private void initComponent() {

		data = new BeanItemContainer<EsmeLanguage>(EsmeLanguage.class);
		initService();
		initTable();
		initForm();
		// loadDataFromDatabase();
	}

	private void initService() {

		try {
			languageService = CacheServiceClient.serviceLanguage;
		} catch (Exception e) {
			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("common.create.service.fail") + "</br>" + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	private void initTable() {

		tbl = new CustomTable("", data, pnlAction) {

			private static final long serialVersionUID = 1L;

			@Override
			protected String formatPropertyValue(Object rowId, Object colId, Property property) {

				String pid = (String) colId;
				/* isDefault */
				if ("isDefault".equals(pid)) {
					if ((property.getValue().equals("1"))) {
						return TM.get("frmCommon.IsDefault");
					} else {
						return "";
					}
				}

				return super.formatPropertyValue(rowId, colId, property);
			}

			@Override
			public Collection<?> getSortableContainerPropertyIds() {

				ArrayList<Object> arr = new ArrayList<Object>();
				Object[] sortCol = TM.get("language.table.setsortcolumns").split(",");
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

				final EsmeLanguage bean = (EsmeLanguage) itemId;

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
						EsmeLanguage bean = data.getIdByIndex(i);
						bean.setSelect(tbl.isSelectAll());
						tbl.setColumnHeader("select", (tbl.isSelectAll() == true) ? "-" : "+");
						tbl.refreshRowCache();
					}
				}
			}
		});

		// tbl.setSortDisabled(true);

		// tbl.setSortContainerPropertyId(TM.get("language.table.setsortcolumns")
		// .split(","));

		tbl.setVisibleColumns(TM.get("language.table.setvisiblecolumns").split(","));
		tbl.setColumnHeaders(TM.get("language.table.setcolumnheaders").split(","));
		tbl.setStyleName("commont_table_noborderLR");
		String[] columnWidth = TM.get("language.table.columnwidth").split(",");
		String[] columnWidthValue = TM.get("language.table.columnwidth_value").split(",");
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
		container.initPager(languageService.count(null, DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(true);
		pnlAction.setValueForCboField(TM.get("language.table.filteredcolumns").split(","), TM.get("language.table.filteredcolumnscaption").split(","));
		container.setFilteredColumns(TM.get("language.table.filteredcolumns").split(","));
		container.setEnableDeleteAllButton(getPermission().contains("D"));
		container.setEnableButtonAddNew(getPermission().contains("I"));
		container.setEnableButtonAddCopy(getPermission().contains("I"));

	}

	private void displayData(String sortedColumn, boolean asc, int start, int items) {

		try {
			data.removeAllItems();
			// data.addAll(languageService.findAllWithOrderPaging(skSearch,
			// sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH));
			ArrayList<EsmeLanguage> lang = (ArrayList<EsmeLanguage>) languageService.findAllWithOrderPaging(skSearch, sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH);
			for (EsmeLanguage lan : lang) {
				if (lan.getIsDefault().equals("1")) {
					defaultLanguage = lan;
				}

				data.addBean(lan);
			}
			if (container != null)
				container.setLblCount(start);
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
		fieldFactory = new FormLanguageFieldFactory();
		form.setFormFieldFactory(fieldFactory);

		dialog = new CommonDialog(TM.get("language.commondialog.caption"), form, this);
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
		form.setVisibleItemProperties(TM.get("language.form.visibleproperties").split(","));
		form.setValidationVisible(false);
		form.focus();
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

			fieldFactory.setOldCode(((EsmeLanguage) object).getCode());

		} else if (action == PanelActionProvider.ACTION_ADD_COPY) {
			Set<EsmeLanguage> setInstrument = (Set<EsmeLanguage>) tbl.getValue();
			EsmeLanguage msv = setInstrument.iterator().next();

			EsmeLanguage newBean = new EsmeLanguage();
			newBean.setCode("");
			fieldFactory.setOldCode(msv.getCode());
			newBean.setName(msv.getName());
			newBean.setStatus(msv.getStatus());
			newBean.setIsDefault(msv.getIsDefault());

			item = new BeanItem<EsmeLanguage>(newBean);
		} else if (action == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
			EsmeLanguage srcService = (EsmeLanguage) object;
			EsmeLanguage bean = new EsmeLanguage();
			bean.setCode(srcService.getCode());
			bean.setName("");
			bean.setStatus("1");
			bean.setIsDefault("0");
			item = new BeanItem<EsmeLanguage>(bean);
		} else {
			fieldFactory.setOldCode(null);
			EsmeLanguage msv = new EsmeLanguage();
			msv.setCode("");
			msv.setName("");
			msv.setStatus("1");
			msv.setIsDefault("0");
			item = new BeanItem<EsmeLanguage>(msv);
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
			BeanItem<EsmeLanguage> beanItem = null;
			beanItem = (BeanItem<EsmeLanguage>) form.getItemDataSource();
			EsmeLanguage msv = beanItem.getBean();
			if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD || pnlAction.getAction() == PanelActionProvider.ACTION_ADD_COPY
			        || pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
				try {

					if (msv.getIsDefault().equals("1")) {
						languageService.updateAllLanguage(msv, "IS_DEFAULT", "0");
						defaultLanguage.setIsDefault("0");
						defaultLanguage = msv;
					}
					long id = languageService.add(msv);
					msv.setLanguageId(id);
					if (id > 0) {
						// cacheService.add(msv);
						container.initPager(languageService.count(null, DEFAULT_EXACT_MATCH));

						// tbl.addItem(msv);
						tblSetARowSelect(msv);

						LogUtil.logActionInsert(FormLanguage.class.getName(), "ESME_LANGUAGE", "LANGUAGE_ID", "" + msv.getLanguageId() + "", null);

						if (pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
							pnlAction.clearAction();
							pnlAction.searchOrAddNew(msv.getCode());
						}

						MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.add.success", TM.get("common.language").toLowerCase()));
					} else {
						MessageAlerter.showErrorMessage(getWindow(), TM.get("common.msg.add.fail", TM.get("common.language").toLowerCase()));
					}
				} catch (Exception e) {
					FormUtil.showException(this, e);
				}
			} else if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT) {
				try {

					Vector v = LogUtil.logActionBeforeUpdate(FormLanguage.class.getName(), "ESME_LANGUAGE", "LANGUAGE_ID", "" + msv.getLanguageId() + "", null);

					if (msv.getIsDefault().equals("1")) {

						if (defaultLanguage != null) {
							languageService.updateAllLanguage(msv, "IS_DEFAULT", "0");
							defaultLanguage.setIsDefault("0");
						}

						defaultLanguage = msv;
					}

					languageService.edit(msv);
					tblSetARowSelect(msv);
					LogUtil.logActionAfterUpdate(v);
					container.initPager(languageService.count(null, DEFAULT_EXACT_MATCH));

					MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.edit.success", TM.get("common.language").toLowerCase()));

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

		// return AppClient.getPermission(this.getClass().getName());
		return SessionData.getAppClient().getPermission(this.getClass().getName());
	}

	public void delete(Object object) {

		resetResource();
		String langDefault = "";
		if (object instanceof EsmeLanguage) {
			EsmeLanguage prcService = (EsmeLanguage) object;
			langDefault = prcService.getIsDefault();
			total++;
			boolean b = languageService.checkConstraints(prcService.getLanguageId());
			if (!b) {
				if (!"1".equals(prcService.getIsDefault())) {
					canDelete.add(prcService);
				}
			} else {

				MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("message.delete.constraints"));
				return;
			}
			// {

			// canDelete.add(prcService);
			// }
		} else {
			for (EsmeLanguage obj : (List<EsmeLanguage>) object) {
				total++;
				if ("1".equals(obj.getIsDefault())) {
					langDefault = obj.getIsDefault();
				}
				if (!"1".equals(obj.getIsDefault())) {
					canDelete.add(obj);
					// b = true;
				}
			}
		}

		if (canDelete.size() == 0) {
			if (object != null && langDefault.equalsIgnoreCase("1")) {
				MessageAlerter.showErrorMessageI18n(getWindow(), "frmLanguage.errorMessageDelete");
			} else {
				MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("comman.message.delete.error"));
			}

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

		System.out.println("delete ing");
		// try
		// {
		int deleted = 0;
		for (EsmeLanguage msv : canDelete) {
			try {
				LogUtil.logActionDelete(FormLanguage.class.getName(), "ESME_LANGUAGE", "LANGUAGE_ID", "" + msv.getLanguageId() + "", null);

				languageService.delete(msv);
				// cacheService.remove(msv);
				tbl.removeItem(msv);

				deleted++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		container.initPager(languageService.count(null, DEFAULT_EXACT_MATCH));

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

		skSearch = new EsmeLanguage();
		if (searchObj.getField() == null) {
			skSearch.setCode(searchObj.getKey());
			skSearch.setName(searchObj.getKey());
		} else {
			if (searchObj.getField().equals("code"))
				skSearch.setCode(searchObj.getKey());
			else if (searchObj.getField().equals("name"))
				skSearch.setName(searchObj.getKey());
		}

		container.initPager(languageService.count(skSearch, DEFAULT_EXACT_MATCH));

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

	public List<EsmeLanguage> getAllItemCheckedOnTable() {

		List<EsmeLanguage> list = new ArrayList<EsmeLanguage>();
		Collection<EsmeLanguage> collection = (Collection<EsmeLanguage>) tbl.getItemIds();
		for (EsmeLanguage obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

	@Override
	public void searchOrAddNew(String key) {

		skSearch = new EsmeLanguage();
		skSearch.setCode(key);
		DEFAULT_EXACT_MATCH = true;
		int count = languageService.count(skSearch, DEFAULT_EXACT_MATCH);
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

}