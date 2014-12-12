package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import com.fis.esme.cp.CpTransferer;
import com.fis.esme.persistence.EsmeCp;
import com.fis.esme.util.FormUtil;
import com.fis.esme.util.LogUtil;
import com.fis.esme.util.MessageAlerter;
import com.fis.esme.util.SearchObj;
import com.fss.util.StringUtil;
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

public class FormCP extends VerticalLayout implements PanelActionProvider, PagingComponentListener, ServerSort, Action.Handler, OptionDialogResultListener {

	private CommonDialog dialog;
	private Form form;
	private CustomTable tbl;
	private TableContainer container;
	private BeanItemContainer<EsmeCp> data;
	private CommonButtonPanel pnlAction;
	private FormCpFieldFactory fieldFactory;

	private final String DEFAULT_SORTED_COLUMN = "name";
	private boolean DEFAULT_SORTED_ASC = true;
	private boolean DEFAULT_EXACT_MATCH = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;
	// private int totalInDatabase = 0;
	private EsmeCp skSearch = null;

	private int total = 0;
	private ArrayList<EsmeCp> canDelete = new ArrayList<EsmeCp>();
	private ConfirmDeletionDialog confirm;
	private CpTransferer serviceCP;

	private String oldID = null;

	public FormCP(String key) {

		this();
		if (key != null) {
			pnlAction.clearAction();
			pnlAction.searchOrAddNew(key);
		}
	}

	public FormCP() {

		LogUtil.logAccess(FormCP.class.getName());
		initLayout();
	}

	private void initLayout() {

		pnlAction = new CommonButtonPanel(this);
		pnlAction.showSearchPanel(true);
		pnlAction.setFromCaption(TM.get(FormCP.class.getName()));
		pnlAction.setMargin(false, false, true, false);
		this.addComponent(pnlAction);
		initComponent();
		this.setSizeFull();
		this.addComponent(container);
		this.setExpandRatio(container, 1.0f);
		this.setComponentAlignment(container, Alignment.TOP_CENTER);
	}

	private void initComponent() {

		data = new BeanItemContainer<EsmeCp>(EsmeCp.class);
		initService();
		initTable();
		initForm();
		// loadDataFromDatabase();
	}

	private void initService() {

		try {
			serviceCP = CacheServiceClient.serviceCp;
		} catch (Exception e) {
			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("common.create.service.fail") + "</br>" + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	private void initTable() {

		tbl = new CustomTable("", data, pnlAction) {

			@Override
			protected String formatPropertyValue(Object rowId, Object colId, Property property) {

				String pid = (String) colId;

				if ("protocal".equals(pid)) {
					if (property.getValue().toString().equalsIgnoreCase("1")) {
						return TM.get("cp.field.combobox.protocal.smtp");
					} else if (property.getValue().toString().equalsIgnoreCase("2")) {
						return TM.get("cp.field.combobox.protocal.http");
					} else if (property.getValue().toString().equalsIgnoreCase("3")) {
						return TM.get("cp.field.combobox.protocal.web");
					} else if (property.getValue().toString().equalsIgnoreCase("4")) {
						return TM.get("cp.field.combobox.protocal.internal");
					} else {
						return "";
					}

				}

				if ("password".equals(pid)) {
					if (property.getValue() != null)
						return "......";
				}

				if ("receivePassword".equals(pid)) {
					if (property.getValue() != null && !property.getValue().toString().equalsIgnoreCase(null) && !property.getValue().toString().equalsIgnoreCase("")) {
						return "......";
					} else {
						return " ";
					}
				}

				if ("createDatetime".equals(pid)) {
					if (property.getValue() != null)
						return FormUtil.simpleDateFormat.format(property.getValue());
				}

				return super.formatPropertyValue(rowId, colId, property);
			}

			// @Override
			// public Collection<?> getSortableContainerPropertyIds() {
			//
			// ArrayList<Object> arr = new ArrayList<Object>();
			// Object[] sortCol = TM.get("cp.table.setsortcolumns").split(",");
			// for (Object obj : sortCol) {
			// arr.add(obj);
			// }
			// return arr;
			// }
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

				final EsmeCp bean = (EsmeCp) itemId;

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
						EsmeCp bean = data.getIdByIndex(i);
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

		tbl.setSortContainerPropertyId(TM.get("cp.table.setsortcolumns").split(","));

		tbl.setVisibleColumns(TM.get("cp.table.setvisiblecolumns").split(","));
		tbl.setColumnHeaders(TM.get("cp.table.setcolumnheaders").split(","));
		tbl.setStyleName("commont_table_noborderLR");

		String[] columnWidth = TM.get("cp.table.columnwidth").split(",");
		String[] columnWidthValue = TM.get("cp.table.columnwidth_value").split(",");
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
		container.initPager(serviceCP.count(null, DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(true);
		pnlAction.setValueForCboField(TM.get("cp.table.filteredcolumns").split(","), TM.get("cp.table.filteredcolumnscaption").split(","));
		container.setFilteredColumns(TM.get("cp.table.filteredcolumns").split(","));
		container.setEnableDeleteAllButton(getPermission().contains("D"));
		container.setEnableButtonAddNew(getPermission().contains("I"));
		container.setEnableButtonAddCopy(getPermission().contains("I"));
	}

	private void displayData(String sortedColumn, boolean asc, int start, int items) {

		try {
			data.removeAllItems();
			data.addAll(serviceCP.findAllWithOrderPaging(skSearch, sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH));
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
		fieldFactory = new FormCpFieldFactory();
		form.setFormFieldFactory(fieldFactory);

		dialog = new CommonDialog(TM.get("cp.commondialog.caption"), form, this);
		dialog.setWidth(TM.get("common.dialog.fixedwidth"));
		dialog.setHeight("500px");
		dialog.addListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {

				pnlAction.clearAction();
			}
		});
	}

	private Window createDialog(Item item) {

		form.setItemDataSource(item);
		form.setVisibleItemProperties(TM.get("cp.form.visibleproperties").split(","));
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

			fieldFactory.setOldCode(((EsmeCp) object).getCode());
			fieldFactory.setOldUsername(((EsmeCp) object).getUsername());
			fieldFactory.setOldShortCode(((EsmeCp) object).getDefaultShortCode());
			fieldFactory.setOldPassWord(((EsmeCp) object).getPassword());
		} else if (action == PanelActionProvider.ACTION_ADD_COPY) {
			Set<EsmeCp> setInstrument = (Set<EsmeCp>) tbl.getValue();
			EsmeCp msv = setInstrument.iterator().next();

			EsmeCp newBean = new EsmeCp();
			fieldFactory.setOldUsername(null);
			fieldFactory.setOldCode(null);
			fieldFactory.setOldShortCode(null);
			fieldFactory.setOldPassWord(null);

			newBean.setCode(msv.getCode());
			newBean.setStatus(msv.getStatus());
			newBean.setDesciption(msv.getDesciption());
			newBean.setDefaultShortCode(msv.getDefaultShortCode());
			newBean.setProtocal(msv.getProtocal());
			newBean.setUsername(msv.getUsername());
			newBean.setPassword(msv.getPassword());
			newBean.setReceiveUsername(msv.getReceiveUsername());
			newBean.setReceivePassword(msv.getReceivePassword());
			newBean.setReceiveUrlMsg(msv.getReceiveUrlMsg());

			item = new BeanItem<EsmeCp>(newBean);
		} else if (action == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
			EsmeCp srcService = (EsmeCp) object;
			EsmeCp bean = new EsmeCp();
			bean.setDesciption("");
			bean.setStatus("1");
			item = new BeanItem<EsmeCp>(bean);
		} else {
			fieldFactory.setOldUsername(null);
			fieldFactory.setOldCode(null);
			fieldFactory.setOldShortCode(null);
			fieldFactory.setOldPassWord(null);
			EsmeCp msv = new EsmeCp();
			msv.setCode("");
			// msv.setDefaultShortCode("1");
			msv.setDesciption("");
			msv.setStatus("1");
			msv.setProtocal((byte) 1);
			msv.setCreateDatetime(new Date());
			item = new BeanItem<EsmeCp>(msv);
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
			BeanItem<EsmeCp> beanItem = null;
			beanItem = (BeanItem<EsmeCp>) form.getItemDataSource();
			EsmeCp msv = beanItem.getBean();

			String strPassword = (msv.getPassword() != null) ? StringUtil.encrypt(msv.getPassword(), StringUtil.nvl(SessionData.getAppClient().getSessionValue("Encrypt.Algorithm"), "")) : null;

			if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT && msv.getPassword().equalsIgnoreCase(fieldFactory.getOldPassWord())) {

			} else {
				msv.setPassword(strPassword);
			}

			/*
			 * System.out.println("ss>>>>>>" + msv.getReceivePassword()); if (msv.getReceivePassword() != null && !msv.getReceivePassword().equalsIgnoreCase(null) &&
			 * !msv.getReceivePassword().equalsIgnoreCase("")) { String strRePassword = (msv.getReceivePassword() != null) ? StringUtil .encrypt(msv.getReceivePassword(), StringUtil.nvl(
			 * SessionData.getAppClient().getSessionValue( "Encrypt.Algorithm"), "")) : null; msv.setReceivePassword(strRePassword); }
			 */

			if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD || pnlAction.getAction() == PanelActionProvider.ACTION_ADD_COPY
			        || pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
				try {

					long id = serviceCP.add(msv);
					msv.setCpId(id);
					if (id > 0) {
						// cacheService.add(msv);
						container.initPager(serviceCP.count(null, DEFAULT_EXACT_MATCH));

						// tbl.addItem(msv);
						tblSetARowSelect(msv);

						LogUtil.logActionInsert(FormCP.class.getName(), "ESME_CP", "CP_ID", "" + msv.getCpId() + "", null);

						if (pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
							pnlAction.clearAction();
							pnlAction.searchOrAddNew(msv.getCode());
						}

						MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.add.success", TM.get("common.cp").toLowerCase()));
					} else {
						MessageAlerter.showErrorMessage(getWindow(), TM.get("common.msg.add.fail", TM.get("common.cp").toLowerCase()));
					}
				} catch (Exception e) {
					FormUtil.showException(this, e);
				}
			} else if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT) {
				try {

					Vector v = LogUtil.logActionBeforeUpdate(FormCP.class.getName(), "ESME_CP", "CP_ID", "" + msv.getCpId() + "", null);

					serviceCP.update(msv);
					// int index = cacheService.indexOf(msv);
					// cacheService.remove(msv);
					// cacheService.add(index, msv);
					tblSetARowSelect(msv);
					LogUtil.logActionAfterUpdate(v);
					container.initPager(serviceCP.count(null, DEFAULT_EXACT_MATCH));
					MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.edit.success", TM.get("common.cp").toLowerCase()));

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
			data.addAll(serviceCP.findAllWithoutParameter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(Object object) {

		resetResource();
		if (object instanceof EsmeCp) {
			EsmeCp EsmeCp = (EsmeCp) object;
			boolean b = serviceCP.checkConstraints(EsmeCp.getCpId());
			if (!b) {
				total++;
				canDelete.add(EsmeCp);
			} else {

				MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("message.delete.constraints"));
				return;
			}
		} else {
			for (EsmeCp obj : (List<EsmeCp>) object) {
				total++;

				boolean b = serviceCP.checkConstraints(obj.getCpId());
				if (!b) {
					canDelete.add(obj);
				} else if (b && ((List<EsmeCp>) object).size() == 1) {

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
		for (EsmeCp msv : canDelete) {
			try {
				LogUtil.logActionDelete(FormCP.class.getName(), "ESME_CP", "CP_ID", "" + msv.getCpId() + "", null);

				serviceCP.delete(msv);
				// cacheService.remove(msv);
				tbl.removeItem(msv);

				deleted++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		container.initPager(serviceCP.count(null, DEFAULT_EXACT_MATCH));

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
		if (searchObj.getKey() == null)
			return;

		skSearch = new EsmeCp();
		if (searchObj.getField() == null) {
			skSearch.setCode(searchObj.getKey());
		} else {
			if (searchObj.getField().equals("code"))
				skSearch.setCode(searchObj.getKey());
			else if (searchObj.getField().equals("desciption"))
				skSearch.setDesciption(searchObj.getKey());
			else if (searchObj.getField().equals("defaultShortCode"))
				skSearch.setDefaultShortCode(searchObj.getKey());
			else if (searchObj.getField().equals("username"))
				skSearch.setUsername(searchObj.getKey());
			else if (searchObj.getField().equals("receiveUsername"))
				skSearch.setReceiveUsername(searchObj.getKey());
			else if (searchObj.getField().equals("receiveUrlMsg"))
				skSearch.setReceiveUrlMsg(searchObj.getKey());

		}

		int count = serviceCP.count(skSearch, DEFAULT_EXACT_MATCH);
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

	public List<EsmeCp> getAllItemCheckedOnTable() {

		List<EsmeCp> list = new ArrayList<EsmeCp>();
		Collection<EsmeCp> collection = (Collection<EsmeCp>) tbl.getItemIds();
		for (EsmeCp obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

	@Override
	public void searchOrAddNew(String key) {

		skSearch = new EsmeCp();
		skSearch.setCode(key);
		DEFAULT_EXACT_MATCH = true;
		int count = serviceCP.count(skSearch, DEFAULT_EXACT_MATCH);
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