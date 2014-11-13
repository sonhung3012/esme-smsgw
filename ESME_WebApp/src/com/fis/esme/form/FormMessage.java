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
import com.fis.esme.message.MessageTransferer;
import com.fis.esme.messagecontent.MessageContentTransferer;
import com.fis.esme.persistence.EsmeLanguage;
import com.fis.esme.persistence.EsmeMessage;
import com.fis.esme.persistence.EsmeMessageContent;
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
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.HeaderClickEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.themes.BaseTheme;

import eu.livotov.tpt.gui.dialogs.OptionDialog.OptionDialogResultListener;
import eu.livotov.tpt.gui.dialogs.OptionKind;
import eu.livotov.tpt.i18n.TM;

public class FormMessage extends VerticalLayout implements PanelActionProvider, PagingComponentListener, ServerSort, Action.Handler, OptionDialogResultListener {

	private CommonDialog dialog;
	private Form form;
	private CustomTable tbl;
	private TableContainer container;
	private BeanItemContainer<EsmeMessageContent> data;
	private CommonButtonPanel pnlAction;
	private FormMessageFieldFactory fieldFactory;

	private final String DEFAULT_SORTED_COLUMN = "esmeMessage";
	private boolean DEFAULT_SORTED_ASC = true;
	private boolean DEFAULT_EXACT_MATCH = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;
	// private int totalInDatabase = 0;
	private EsmeMessageContent skSearch = null;
	private EsmeLanguage defaultLanguage = null;

	private int total = 0;
	private ArrayList<EsmeMessageContent> canDelete = new ArrayList<EsmeMessageContent>();
	private ConfirmDeletionDialog confirm;
	private MessageTransferer serviceMessage;
	private MessageContentTransferer serviceContent;

	public FormMessage(String key) {

		this();
		if (key != null) {
			pnlAction.clearAction();
			pnlAction.searchOrAddNew(key);
		}
	}

	public FormMessage() {

		LogUtil.logAccess(FormMessage.class.getName());
		initLayout();
	}

	private void initLayout() {

		pnlAction = new CommonButtonPanel(this);
		pnlAction.showSearchPanel(true);
		pnlAction.setFromCaption(TM.get(FormMessage.class.getName()));
		pnlAction.setMargin(false, false, true, false);
		this.addComponent(pnlAction);
		initComponent();
		this.setSizeFull();
		this.addComponent(container);
		this.setExpandRatio(container, 1.0f);
		this.setComponentAlignment(container, Alignment.TOP_CENTER);
	}

	private void initComponent() {

		data = new BeanItemContainer<EsmeMessageContent>(EsmeMessageContent.class);
		initService();
		initLanguage();
		initTable();
		initForm();
	}

	private void initLanguage() {

		try {
			if (defaultLanguage == null) {
				ArrayList<EsmeLanguage> lang = (ArrayList<EsmeLanguage>) CacheServiceClient.serviceLanguage.findAllWithoutParameter();
				for (EsmeLanguage esmeLanguage : lang) {
					if (esmeLanguage.getIsDefault().equalsIgnoreCase("1")) {
						defaultLanguage = esmeLanguage;
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void initService() {

		try {
			serviceMessage = CacheServiceClient.serviceMessage;
			serviceContent = CacheServiceClient.serviceMessageContent;
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
				Object[] sortCol = TM.get("message.table.setsortcolumns").split(",");
				for (Object obj : sortCol) {
					arr.add(obj);

				}
				return arr;
			}

			@Override
			protected String formatPropertyValue(Object rowId, Object colId, Property property) {

				String pid = (String) colId;
				EsmeMessageContent content = (EsmeMessageContent) rowId;

				if ("code".equals(pid)) {
					if (content.getEsmeMessage().getCode() != null) {
						return content.getEsmeMessage().getCode();
					} else {
						return "";
					}

				}

				if ("name".equals(pid)) {
					if (content.getEsmeMessage().getName() != null) {
						return content.getEsmeMessage().getName();
					} else {
						return "";
					}

				}

				if ("desciption".equals(pid)) {
					if (content.getEsmeMessage().getDesciption() != null) {
						return content.getEsmeMessage().getDesciption();
					} else {
						return "";
					}

				}

				if ("status".equals(pid)) {
					if (content.getEsmeMessage().getStatus().equals("1")) {
						return TM.get("messagescheduler.table.status.Active");
					} else {
						return TM.get("messagescheduler.table.status.InActive");
					}
				}

				if ("lastModify".equals(pid)) {
					if (property.getValue() != null)
						return FormUtil.simpleDateFormat.format(property.getValue());
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

		tbl.addGeneratedColumn("select", new Table.ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {

				final EsmeMessageContent bean = (EsmeMessageContent) itemId;

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

		if (getPermission().contains(TM.get("module.right.Update"))) {
			tbl.addListener(new ItemClickEvent.ItemClickListener() {

				private static final long serialVersionUID = 2068314108919135281L;

				public void itemClick(ItemClickEvent event) {

					if (event.isDoubleClick()) {
						BeanItem<EsmeMessageContent> item = (BeanItem<EsmeMessageContent>) event.getItem();
						EsmeMessageContent bean = item.getBean();
						if (bean.getEsmeMessage().getStatus().equals("0")) {
							pnlAction.edit(event.getItemId());
						}
					}
				}
			});
		}
		tbl.addGeneratedColumn("DELETE_EDIT_MO", new Table.ColumnGenerator() {

			@Override
			public Component generateCell(Table source, final Object itemId, Object columnId) {

				final EsmeMessageContent bean = (EsmeMessageContent) itemId;

				Container container = source.getContainerDataSource();

				if (container instanceof BeanItemContainer<?>) {
					// int id = con.indexOfId(itemId);
					HorizontalLayout buttonLayout = new HorizontalLayout();
					buttonLayout.setSpacing(true);
					// buttonLayout.setSizeFull();
					Button btn = new Button(TM.get("table.common.btn.delete.caption"));
					btn.setDescription(TM.get("table.common.btn.delete.description"));
					btn.setStyleName(BaseTheme.BUTTON_LINK);
					btn.setIcon(new ThemeResource("icons/16/delete.png"));
					btn.setCaption(null);
					btn.addListener(new Button.ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {

							pnlAction.delete(itemId);
						}
					});

					if (pnlAction != null)
						btn.setEnabled(pnlAction.getPermision().contains("D"));

					buttonLayout.addComponent(btn);
					buttonLayout.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);

					btn = new Button(TM.get("table.common.btn.edit.caption"));
					btn.setDescription(TM.get("table.common.btn.edit.description"));
					btn.setStyleName(BaseTheme.BUTTON_LINK);
					btn.setIcon(new ThemeResource("icons/16/edit.png"));
					btn.setCaption(null);
					btn.addListener(new Button.ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {

							pnlAction.edit(itemId);
							;
						}
					});

					if (pnlAction != null)
						btn.setEnabled(pnlAction.getPermision().contains("U"));

					buttonLayout.addComponent(btn);
					buttonLayout.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
					if (bean.getEsmeMessage().getStatus().equals("1")) {
						btn.setEnabled(false);
					} else {
						btn.setEnabled(true);
					}
					return buttonLayout;
				} else {
					return new Label("");
				}
			}
		});
		tbl.addListener(new Table.HeaderClickListener() {

			public void headerClick(HeaderClickEvent event) {

				String property = event.getPropertyId().toString();
				if (property.equals("select")) {
					tbl.setSelectAll(!tbl.isSelectAll());
					for (int i = 0; i < data.size(); i++) {
						EsmeMessageContent bean = data.getIdByIndex(i);
						bean.setSelect(tbl.isSelectAll());
						tbl.setColumnHeader("select", (tbl.isSelectAll() == true) ? "-" : "+");
						tbl.refreshRowCache();
					}
				}
			}
		});

		tbl.setSortContainerPropertyId(TM.get("message.table.setsortcolumns").split(","));

		tbl.setVisibleColumns(TM.get("message.table.setvisiblecolumns").split(","));
		tbl.setColumnHeaders(TM.get("message.table.setcolumnheaders").split(","));
		tbl.setStyleName("commont_table_noborderLR");

		String[] columnWidth = TM.get("message.table.columnwidth").split(",");
		String[] columnWidthValue = TM.get("message.table.columnwidth_value").split(",");
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
		container.initPager(serviceContent.count(null, DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(true);
		pnlAction.setValueForCboField(TM.get("message.table.filteredcolumns").split(","), TM.get("message.table.filteredcolumnscaption").split(","));
		container.setFilteredColumns(TM.get("message.table.filteredcolumns").split(","));
		container.setEnableDeleteAllButton(getPermission().contains("D"));
		container.setEnableButtonAddNew(getPermission().contains("I"));
		container.setEnableButtonAddCopy(getPermission().contains("I"));
	}

	private void displayData(String sortedColumn, boolean asc, int start, int items) {

		try {
			data.removeAllItems();
			data.addAll(serviceContent.findAllWithOrderPaging(skSearch, sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH));
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
		fieldFactory = new FormMessageFieldFactory();
		form.setFormFieldFactory(fieldFactory);

		dialog = new CommonDialog(TM.get("message.commondialog.caption"), form, this);
		dialog.setWidth(TM.get("common.dialog.fixedwidth"));
		dialog.setHeight("450px");
		dialog.addListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {

				pnlAction.clearAction();
			}
		});
	}

	private Window createDialog(Item item) {

		form.setItemDataSource(item);
		form.setVisibleItemProperties(TM.get("message.form.visibleproperties").split(","));
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
			((EsmeMessageContent) object).setCode(((EsmeMessageContent) object).getEsmeMessage().getCode());
			((EsmeMessageContent) object).setName(((EsmeMessageContent) object).getEsmeMessage().getName());
			((EsmeMessageContent) object).setDesciption(((EsmeMessageContent) object).getEsmeMessage().getDesciption());
			((EsmeMessageContent) object).setStatus(((EsmeMessageContent) object).getEsmeMessage().getStatus());
			fieldFactory.setOldCode(((EsmeMessageContent) object).getCode());
			fieldFactory.setOldMessage(((EsmeMessageContent) object).getEsmeMessage());
			fieldFactory.setOldLanguage(((EsmeMessageContent) object).getEsmeLanguage());
		} else if (action == PanelActionProvider.ACTION_ADD_COPY) {
			Set<EsmeMessageContent> setInstrument = (Set<EsmeMessageContent>) tbl.getValue();
			EsmeMessageContent msv = setInstrument.iterator().next();

			EsmeMessageContent newBean = new EsmeMessageContent();
			fieldFactory.setOldCode(null);
			fieldFactory.setOldMessage(null);
			fieldFactory.setOldLanguage(null);
			newBean.setName(msv.getEsmeMessage().getName());
			newBean.setCode("");
			newBean.setEsmeLanguage(msv.getEsmeLanguage());
			newBean.setMessage(msv.getMessage());
			newBean.setDesciption(msv.getEsmeMessage().getDesciption());
			newBean.setStatus("0");
			newBean.setLastModify(new Date());
			item = new BeanItem<EsmeMessageContent>(newBean);
		} else if (action == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
			EsmeMessageContent bean = new EsmeMessageContent();
			bean.setName("");
			bean.setDesciption("");
			bean.setStatus("0");
			item = new BeanItem<EsmeMessageContent>(bean);
		} else {
			fieldFactory.setOldCode(null);
			fieldFactory.setOldMessage(null);
			fieldFactory.setOldLanguage(null);
			EsmeMessageContent msv = new EsmeMessageContent();
			msv.setName("");
			msv.setCode("");
			msv.setDesciption("");
			if (defaultLanguage != null) {
				msv.setEsmeLanguage(defaultLanguage);
			}
			msv.setMessage("");
			msv.setStatus("0");
			msv.setLastModify(new Date());
			item = new BeanItem<EsmeMessageContent>(msv);
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
			BeanItem<EsmeMessageContent> beanItem = null;
			beanItem = (BeanItem<EsmeMessageContent>) form.getItemDataSource();
			EsmeMessageContent msv = beanItem.getBean();
			if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD || pnlAction.getAction() == PanelActionProvider.ACTION_ADD_COPY
			        || pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
				try {
					EsmeMessage mess = new EsmeMessage();
					mess.setCode(msv.getCode());
					mess.setName(msv.getName());
					mess.setDesciption(msv.getDesciption());
					mess.setStatus(msv.getStatus());
					mess.setLastModify(new Date());

					long messId = serviceMessage.add(mess);
					mess.setMessageId(messId);

					if (messId > 0) {

						msv.setEsmeMessage(mess);
						long id = serviceContent.add(msv);
						msv.setId(id);

						container.initPager(serviceContent.count(null, DEFAULT_EXACT_MATCH));

						// tbl.addItem(msv);
						tblSetARowSelect(msv);

						LogUtil.logActionInsert(FormMessage.class.getName(), "ESME_MESSAGE_CONTENT", "MSG_CONTENT_ID", "" + msv.getId() + "", null);

						if (pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
							pnlAction.clearAction();
							pnlAction.searchOrAddNew(msv.getCode());
						}

						MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.add.success", TM.get("common.message").toLowerCase()));
					} else {
						MessageAlerter.showErrorMessage(getWindow(), TM.get("common.msg.add.fail", TM.get("common.message").toLowerCase()));
					}
				} catch (Exception e) {
					FormUtil.showException(this, e);
				}
			} else if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT) {
				try {

					Vector v = LogUtil.logActionBeforeUpdate(FormMessage.class.getName(), "ESME_MESSAGE_CONTENT", "MSG_CONTENT_ID", "" + msv.getId() + "", null);

					msv.setLastModify(new Date());

					EsmeMessageContent messageContent = CacheServiceClient.serviceMessageContent.findByMessageIdAndLanguageId(msv.getEsmeMessage().getMessageId(), msv.getEsmeLanguage()
					        .getLanguageId());
					if (messageContent != null) {
						serviceContent.update(msv);
					} else {

						serviceContent.add(msv);
						fieldFactory.setOldMessage(msv.getEsmeMessage());
					}

					EsmeMessage mess = new EsmeMessage();
					mess.setMessageId(msv.getEsmeMessage().getMessageId());
					mess.setCode(msv.getCode());
					mess.setName(msv.getName());
					mess.setDesciption(msv.getDesciption());
					mess.setStatus(msv.getStatus());
					mess.setLastModify(new Date());

					serviceMessage.update(mess);

					container.initPager(serviceContent.count(null, DEFAULT_EXACT_MATCH));

					tblSetARowSelect(msv);
					LogUtil.logActionAfterUpdate(v);

					MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.edit.success", TM.get("common.message").toLowerCase()));

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
			data.addAll(serviceContent.findAllWithoutParameter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(Object object) {

		resetResource();
		if (object instanceof EsmeMessageContent) {
			EsmeMessageContent EsmeServices = (EsmeMessageContent) object;
			boolean b = serviceContent.checkConstraints(EsmeServices.getId());
			if (!b) {
				total++;
				canDelete.add(EsmeServices);
			} else {

				MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("message.delete.constraints"));
				return;
			}
		} else {
			for (EsmeMessageContent obj : (List<EsmeMessageContent>) object) {
				total++;

				boolean b = serviceContent.checkConstraints(obj.getId());
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
		for (EsmeMessageContent msv : canDelete) {
			try {
				LogUtil.logActionDelete(FormMessage.class.getName(), "ESME_MESSAGE_CONTENT", "MSG_CONTENT_ID", "" + msv.getId() + "", null);

				serviceContent.delete(msv);
				boolean mess = serviceMessage.checkConstraints(msv.getEsmeMessage().getMessageId());
				if (!mess) {
					serviceMessage.delete(msv.getEsmeMessage());
				}
				// cacheService.remove(msv);
				tbl.removeItem(msv);

				deleted++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		container.initPager(serviceContent.count(null, DEFAULT_EXACT_MATCH));

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

		if (searchObj.getKey() == null)
			return;

		skSearch = new EsmeMessageContent();
		if (searchObj.getField() == null) {
			skSearch.setMessage(searchObj.getKey() + "_name");
		} else {
			if (searchObj.getField().equals("code"))
				skSearch.setMessage(searchObj.getKey() + "_code");
			else if (searchObj.getField().equals("name"))
				skSearch.setMessage(searchObj.getKey() + "_name");
			else if (searchObj.getField().equals("message"))
				skSearch.setMessage(searchObj.getKey() + "_message");
			else if (searchObj.getField().equals("desciption"))
				skSearch.setMessage(searchObj.getKey() + "_desciption");

		}

		int count = serviceContent.count(skSearch, DEFAULT_EXACT_MATCH);
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

	public List<EsmeMessageContent> getAllItemCheckedOnTable() {

		List<EsmeMessageContent> list = new ArrayList<EsmeMessageContent>();
		Collection<EsmeMessageContent> collection = (Collection<EsmeMessageContent>) tbl.getItemIds();
		for (EsmeMessageContent obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

	@Override
	public void searchOrAddNew(String key) {

		skSearch = new EsmeMessageContent();
		skSearch.setMessage(key);
		DEFAULT_EXACT_MATCH = true;
		int count = serviceContent.count(skSearch, DEFAULT_EXACT_MATCH);
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

	private boolean messageContentValidate(String messageContent) {

		String strMsgContenEmtyError = TM.get("common.itrdt.sms.error.contentemty");
		String strMsgContenUnicodeError = TM.get("common.itrdt.sms.error.contenunicode");
		if (messageContent.length() < 1) {

			MessageAlerter.showErrorMessageI18n(getWindow(), strMsgContenEmtyError);
			return false;
		}
		return true;
	}

}