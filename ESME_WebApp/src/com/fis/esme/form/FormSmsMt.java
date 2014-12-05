package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import com.fis.esme.emsmo.EmsMoTransferer;
import com.fis.esme.emsmt.EmsMtTransferer;
import com.fis.esme.emsmt.Exception_Exception;
import com.fis.esme.form.feedbackSearch.ObjectSearchFeedback;
import com.fis.esme.form.feedbackSearch.SearchFormFeedback;
import com.fis.esme.form.feedbackSearch.SearchFormFeedbackFieldFactory;
import com.fis.esme.persistence.EsmeEmsMo;
import com.fis.esme.persistence.EsmeEmsMt;
import com.fis.esme.persistence.EsmeGroups;
import com.fis.esme.persistence.EsmeSubscriber;
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
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.HeaderClickEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.themes.BaseTheme;

import eu.livotov.tpt.gui.dialogs.OptionDialog.OptionDialogResultListener;
import eu.livotov.tpt.gui.dialogs.OptionKind;
import eu.livotov.tpt.i18n.TM;

public class FormSmsMt extends VerticalLayout implements PanelActionProvider, PagingComponentListener, ServerSort, Action.Handler, OptionDialogResultListener {

	private CommonDialog dialog;
	private Form form;
	private CustomTable tbl;
	private CommonButtonPanel pnlAction;

	private HorizontalLayout pnSearch;
	private Panel formPanel = new Panel();

	private SearchFormFeedback searchForm;
	private SearchFormFeedbackFieldFactory searchFactory;
	private ObjectSearchFeedback searcher;

	private TableContainer container;
	private int total = 0;
	private ConfirmDeletionDialog confirm;
	private BeanItemContainer<EsmeEmsMo> data;
	private EmsMtTransferer emsmtService;
	private EmsMoTransferer emsmoService;
	private boolean DEFAULT_EXACT_MATCH = false;
	private FormSmsMtFieldFactory fieldFactory;
	private final String DEFAULT_SORTED_COLUMN = "requestTime";
	private boolean DEFAULT_SORTED_ASC = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;
	private ArrayList<EsmeEmsMo> canDelete = new ArrayList<EsmeEmsMo>();
	private EsmeEmsMt mtSearch = null;
	private EsmeEmsMo moSearch = null;

	private Button btnSearch;
	private Button btnRefresh;

	public FormSmsMt(String key) {

		this();
		if (key != null) {
			pnlAction.clearAction();
			pnlAction.searchOrAddNew(key);
		}
	}

	public FormSmsMt() {

		LogUtil.logAccess(FormSmsMt.class.getName());
		initLayout();
	}

	public void initLayout() {

		pnlAction = new CommonButtonPanel(this);
		pnlAction.showSearchPanel(true);
		pnlAction.setFromCaption(TM.get(FormSmsMt.class.getName()));
		pnlAction.setMargin(false, false, true, false);
		pnlAction.setSelectedValueForCategory(FormSmsMt.class);
		// this.addComponent(pnlAction);

		initComponent();
		HorizontalLayout formLayout = new HorizontalLayout();
		formLayout.setWidth("100%");
		formLayout.addComponent(pnSearch);

		HorizontalLayout fLayout = new HorizontalLayout();
		fLayout.setSizeFull();
		fLayout.addComponent(formLayout);
		fLayout.setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
		formPanel.setContent(fLayout);

		this.setSizeFull();
		formPanel.setHeight("100px");
		this.addComponent(formPanel);
		this.setComponentAlignment(formPanel, Alignment.MIDDLE_CENTER);

		this.addComponent(container);
		this.setExpandRatio(container, 1.0f);
		this.setComponentAlignment(container, Alignment.TOP_CENTER);
	}

	protected ObjectSearchFeedback createSearchObjectFeedback() {

		ObjectSearchFeedback obj = new ObjectSearchFeedback();
		obj.setTfMsisdn(SessionData.getCurrentMSISDN());
		obj.setFromDate(null);
		obj.setToDate(null);
		obj.setCbbShortCode(null);
		obj.setTfMessage(null);
		obj.setTfFeedback(null);
		obj.setCbbStatus(null);
		obj.setCbbGroup(null);

		return obj;
	}

	private void initSearchPn() throws Exception {

		pnSearch = new HorizontalLayout();
		pnSearch.setMargin(false);
		pnSearch.setSpacing(true);

		searchForm = new SearchFormFeedback();
		searchForm.setImmediate(false);
		searchForm.setInvalidCommitted(false);
		searchForm.setWriteThrough(false);

		searcher = createSearchObjectFeedback();
		searchFactory = new SearchFormFeedbackFieldFactory() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Field createField(Item item, Object propertyId, Component uiContext) {

				Field field = null;
				field = super.createField(item, propertyId, uiContext);
				return field;
			}
		};

		searchForm.setFormFieldFactory(searchFactory);
		searchForm.setItemDataSource(new BeanItem<ObjectSearchFeedback>(searcher));

		// button
		btnSearch = new Button(TM.get("main.common.button.search.caption"));
		btnSearch.setWidth("100px");
		// FormLayout frm = new FormLayout();
		// frm.addComponent(btnSearch);
		// frm.setSizeFull();
		searchForm.setSearchButton(btnSearch);
		pnSearch.addComponent(searchForm);

		btnRefresh = new Button(TM.get("main.common.button.clear.caption"));
		btnRefresh.setWidth("100px");
		searchForm.setSearchButtonRefresh(btnRefresh);

		// pnSearch.addComponent(frm);
		pnSearch.setComponentAlignment(searchForm, Alignment.MIDDLE_CENTER);
		// pnSearch.setComponentAlignment(btnSearch, Alignment.MIDDLE_CENTER);

		btnSearch.addListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				search();
			}
		});
		btnSearch.setClickShortcut(KeyCode.ENTER);

		btnRefresh.addListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				searchFactory.refreshAllField();
				data.removeAllItems();
				data.addAll(CacheDB.cacheMo);
				tbl.refreshRowCache();
			}
		});

	}

	private void initComponent() {

		data = new BeanItemContainer<EsmeEmsMo>(EsmeEmsMo.class);
		initService();
		loadDataFromDatabase();
		try {
			initSearchPn();
		} catch (Exception e) {
			e.printStackTrace();
		}

		initTable();
		initForm();
	}

	private void initForm() {

		form = new Form();
		form.setWriteThrough(false);
		form.setInvalidCommitted(false);
		form.setImmediate(false);
		fieldFactory = new FormSmsMtFieldFactory();
		form.setFormFieldFactory(fieldFactory);

		dialog = new CommonDialog(TM.get("emsmt.commondialog.caption"), form, this);
		dialog.setWidth(TM.get("common.dialog.fixedwidth"));
		dialog.setHeight("300px");
		dialog.addListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {

				pnlAction.clearAction();
			}
		});
	}

	@SuppressWarnings("serial")
	private void initTable() {

		tbl = new CustomTable("", data, pnlAction) {

			private static final long serialVersionUID = 1L;

			@Override
			protected String formatPropertyValue(Object rowId, Object colId, Property property) {

				String pid = (String) colId;
				EsmeEmsMo content = (EsmeEmsMo) rowId;
				if ("requestTime".equals(pid)) {
					if (property.getValue() != null)
						return FormUtil.simpleDateFormat.format(property.getValue());
				}
				if ("mtLastRetryTime".equals(pid)) {
					if (property.getValue() != null)
						return FormUtil.simpleDateFormat.format(property.getValue());
				}
				if ("groupName".equals(pid)) {
					if (content.getEsmeGroups().getName() != null) {
						return content.getEsmeGroups().getName();
					} else {
						return "";
					}
				}
				if ("mtStatus".equals(pid)) {
					if (content.getMtStatus().equals("0")) {
						return TM.get("emsmt.table.mtStatus.insert_new");
					} else if (content.getMtStatus().equals("1")) {
						return TM.get("emsmt.table.mtStatus.send_success");
					} else if (content.getMtStatus().equals("2")) {
						return TM.get("emsmt.table.mtStatus.fail_and_retry");
					} else if (content.getMtStatus().equals("3")) {
						return TM.get("emsmt.table.mtStatus.user_dont_receiver");
					} else if (content.getMtStatus().equals("6")) {
						return TM.get("emsmt.table.mtStatus.fail_validate_info");
					} else if (content.getMtStatus().equals("7")) {
						return TM.get("emsmt.table.mtStatus.waiting_approve");
					} else if (content.getMtStatus().equals("9")) {
						return TM.get("emsmt.table.mtStatus.loaded");
					}
				}
				return super.formatPropertyValue(rowId, colId, property);
			}

			@Override
			public Collection<?> getSortableContainerPropertyIds() {

				ArrayList<Object> arr = new ArrayList<Object>();
				Object[] sortCol = TM.get("emsmt.table.setsortcolumns").split(",");
				for (Object obj : sortCol) {

					arr.add(obj);

				}
				return arr;
			}
		};
		tbl.addGeneratedColumn("select", new Table.ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {

				final EsmeEmsMo bean = (EsmeEmsMo) itemId;
				CheckBox checkBox = new CheckBox();
				if ((bean.getMtStatus().equals("1")) || (bean.getMtStatus().equals("9")) || (bean.getMtStatus().equals("0"))) {
					checkBox.setValue(false);
					checkBox.setEnabled(false);
				}
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
						EsmeEmsMo bean = data.getIdByIndex(i);
						if ((!bean.getMtStatus().equals("1")) && (!bean.getMtStatus().equals("9"))) {
							bean.setSelect(tbl.isSelectAll());
							tbl.setColumnHeader("select", (tbl.isSelectAll() == true) ? "-" : "+");
							tbl.refreshRowCache();
						}
					}
				}
			}
		});
		tbl.addGeneratedColumn("FEEDBACK_EDIT", new Table.ColumnGenerator() {

			@Override
			public Component generateCell(Table source, final Object itemId, Object columnId) {

				final EsmeEmsMo bean = (EsmeEmsMo) itemId;

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
					if ((bean.getMtStatus().equals("1")) || (bean.getMtStatus().equals("9")) || (bean.getMtStatus().equals("0"))) {
						btn.setEnabled(false);
					}

					btn = new Button(TM.get("emsmt.table.btn.feedback.caption"));
					btn.setDescription(TM.get("emsmt.table.btn.feedback.caption"));
					btn.setStyleName(BaseTheme.BUTTON_LINK);
					btn.setIcon(new ThemeResource("icons/16/comment.png"));
					btn.setCaption(null);
					btn.addListener(new Button.ClickListener() {

						@Override
						public void buttonClick(ClickEvent event) {

							pnlAction.edit(itemId);
						}
					});

					if (pnlAction != null)
						btn.setEnabled(pnlAction.getPermision().contains("U"));

					buttonLayout.addComponent(btn);
					buttonLayout.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
					if ((bean.getMtStatus().equals("1")) || (bean.getMtStatus().equals("9")) || (bean.getMtStatus().equals("0"))) {
						btn.setEnabled(false);
					}
					return buttonLayout;
				} else {
					return new Label("");
				}
			}
		});

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

		tbl.setSortContainerPropertyId(TM.get("emsmt.table.setsortcolumns").split(","));
		tbl.setVisibleColumns(TM.get("emsmt.table.setvisiblecolumns").split(","));
		tbl.setColumnHeaders(TM.get("emsmt.table.setcolumnheaders").split(","));
		tbl.setStyleName("commont_table_noborderLR");
		String[] columnWidth = TM.get("emsmt.table.columnwidth").split(",");
		String[] columnWidthValue = TM.get("emsmt.table.columnwidth_value").split(",");
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

			@Override
			public void addCopyItemSelected() {

				approveMessageSelected();
			}
		};

		container.initPager(emsmoService.count(null, DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(true);
		pnlAction.setValueForCboField(TM.get("emsmt.table.filteredcolumns").split(","), TM.get("emsmt.table.filteredcolumnscaption").split(","));
		container.setFilteredColumns(TM.get("emsmt.table.filteredcolumnsTable").split(","));
		container.setEnableDeleteAllButton(getPermission().contains("D"));
		container.setEnableButtonAddNew(getPermission().contains("I"));
		container.rePainAdd();
		container.rePainAddCopy();

	}

	private void initService() {

		try {
			emsmoService = CacheServiceClient.emsMoService;
			emsmtService = CacheServiceClient.emsMtService;
		} catch (Exception e) {
			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("common.create.service.fail") + "</br>" + e.getMessage());
			e.printStackTrace();
		}
	}

	private void loadDataFromDatabase() {

		try {

			EsmeEmsMo esmeEmsMo = new EsmeEmsMo();
			CacheDB.cacheMo = emsmoService.findAllWithOrderPaging(esmeEmsMo, sortedColumn, false, -1, -1, DEFAULT_EXACT_MATCH);
			for (int i = 0; i < CacheDB.cacheMo.size(); i++) {
				EsmeEmsMo rowMo = CacheDB.cacheMo.get(i);
				if (rowMo.getEsmeGroups() == null) {
					rowMo.setEsmeGroups(new EsmeGroups());
					rowMo.setEsmeSubscriber(new EsmeSubscriber());
				}
				EsmeEmsMt rowMt = emsmtService.findByMtID(rowMo.getMoId());
				if (rowMt != null) {
					rowMo.setEsmeEmsMt(rowMt);
					rowMo.setMtMessage(rowMt.getMessage());
					rowMo.setMtStatus(rowMt.getStatus());
					rowMo.setMtLastRetryTime(rowMt.getLastRetryTime());

				} else {
					rowMo.setMtStatus("");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void displayData(String sortedColumn, boolean asc, int start, int items) {

		try {
			data.removeAllItems();
			List<EsmeEmsMo> arrEmsMo = emsmoService.findAllWithOrderPaging(moSearch, sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH);

			for (int i = 0; i < arrEmsMo.size(); i++) {
				EsmeEmsMo rowMo = arrEmsMo.get(i);
				if (rowMo.getEsmeGroups() == null) {
					rowMo.setEsmeGroups(new EsmeGroups());
					rowMo.setEsmeSubscriber(new EsmeSubscriber());
				}
				EsmeEmsMt rowMt = emsmtService.findByMtID(rowMo.getMoId());
				if (rowMt != null) {
					rowMo.setEsmeEmsMt(rowMt);
					rowMo.setMtMessage(rowMt.getMessage());
					rowMo.setMtStatus(rowMt.getStatus());
					rowMo.setMtLastRetryTime(rowMt.getLastRetryTime());

				} else {
					rowMo.setMtStatus("");
				}
				data.addBean(rowMo);
			}
			if (container != null)
				container.setLblCount(start);
			tbl.sort(new Object[] { "requestTime" }, new boolean[] { false });
		} catch (Exception e) {
			// MessageAlerter.showErrorMessageI18n(this.getWindow(),
			// TM.get("common.getdata.fail"));
			e.printStackTrace();
		}
	}

	@Override
	public void dialogClosed(OptionKind option) {

		// TODO Auto-generated method stub
		if (OptionKind.OK.equals(option)) {
			if (canDelete != null && canDelete.size() > 0) {
				doDelete();
			} else if (getAllItemCheckedOnTable() != null && getAllItemCheckedOnTable().size() > 0) {

				onApprover();
			}
		}
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

	@Override
	public void requestSort(String cloumn, boolean asc) {

		// TODO Auto-generated method stub
		sortedColumn = cloumn;
		sortedASC = asc;
		int items = container.getItemPerPage();
		displayData(cloumn, asc, 0, items);
		container.changePage(1);
	}

	@Override
	public void displayPage(ChangePageEvent event) {

		// TODO Auto-generated method stub
		int start = event.getPageRange().getIndexPageStart();
		// int end = event.getPageRange().getIndexPageEnd();
		displayData(sortedColumn, sortedASC, start, event.getPageRange().getNumberOfRowsPerPage());
	}

	@Override
	public void accept() {

		// TODO Auto-generated method stub
		form.commit();
		BeanItem<EsmeEmsMo> beanItem = null;
		beanItem = (BeanItem<EsmeEmsMo>) form.getItemDataSource();
		EsmeEmsMo msv = beanItem.getBean();
		String vstrFeedBack = msv.getMtMessage();
		if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD) {
			ArrayList<EsmeEmsMo> arrEmsMo = (ArrayList<EsmeEmsMo>) getAllItemCheckedOnTable();
			for (int i = 0; i < arrEmsMo.size(); i++) {
				EsmeEmsMo rowEmsMo = arrEmsMo.get(i);
				if (rowEmsMo.getEsmeEmsMt() == null) {
					EsmeEmsMt vEmsMt = new EsmeEmsMt();
					if (rowEmsMo.getEsmeGroups() != null) {
						if (rowEmsMo.getEsmeGroups().getGroupId() > 0) {
							vEmsMt.setEsmeGroups(rowEmsMo.getEsmeGroups());
						}
					}
					if (rowEmsMo.getEsmeSubscriber() != null) {
						if (rowEmsMo.getEsmeSubscriber().getSubId() > 0) {
							vEmsMt.setEsmeSubscriber(rowEmsMo.getEsmeSubscriber());
						}
					}

					vEmsMt.setEsmeCp(rowEmsMo.getEsmeCp());
					vEmsMt.setShortCode(rowEmsMo.getShortCode());
					vEmsMt.setMsisdn(rowEmsMo.getMsisdn());
					vEmsMt.setRequestTime(new Date());
					vEmsMt.setLastRetryTime(new java.util.Date());
					vEmsMt.setMessage(vstrFeedBack);
					vEmsMt.setStatus("7");
					if (rowEmsMo.getRetryNumber() != null) {
						vEmsMt.setRetryNumber(rowEmsMo.getRetryNumber());
					}
					vEmsMt.setCommandCode(rowEmsMo.getEsmeSmsCommand().getCode());
					if (rowEmsMo.getEsmeSmsLog() != null) {
						vEmsMt.setEsmeSmsLog(rowEmsMo.getEsmeSmsLog());
					}
					vEmsMt.setEsmeEmsMo(rowEmsMo);
					try {
						long id = emsmtService.add(vEmsMt);
						vEmsMt.setMtId(id);
						rowEmsMo.setEsmeEmsMt(vEmsMt);
						rowEmsMo.setMtMessage(vstrFeedBack);
						rowEmsMo.setMtStatus("7");
						rowEmsMo.setMtLastRetryTime(new java.util.Date());
						CacheDB.cacheMo.add(rowEmsMo);
						MessageAlerter.showMessageI18n(getWindow(), TM.get("emsmt.table.add_feedback.success"));
					} catch (Exception_Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					EsmeEmsMt vEmsMt = rowEmsMo.getEsmeEmsMt();
					vEmsMt.setMessage(vstrFeedBack);
					vEmsMt.setRequestTime(new Date());
					vEmsMt.setLastRetryTime(new java.util.Date());
					rowEmsMo.setMtMessage(vstrFeedBack);
					rowEmsMo.setMtLastRetryTime(new java.util.Date());
					try {
						emsmtService.update(vEmsMt);
						for (EsmeEmsMo esmeEmsMo : CacheDB.cacheMo) {

							if (esmeEmsMo.getMoId() == rowEmsMo.getMoId()) {

								esmeEmsMo.setMtMessage(vstrFeedBack);
								esmeEmsMo.setMtLastRetryTime(new java.util.Date());

							}
						}
						MessageAlerter.showMessageI18n(getWindow(), TM.get("emsmt.table.edit_feedback.success"));
					} catch (Exception_Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} else if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT) {
			if (msv.getEsmeEmsMt() == null) {
				EsmeEmsMt vEmsMt = new EsmeEmsMt();
				if (msv.getEsmeCp() != null) {
					vEmsMt.setEsmeCp(msv.getEsmeCp());
				}
				if (msv.getShortCode() != null) {
					vEmsMt.setShortCode(msv.getShortCode());
				}
				if (msv.getEsmeGroups() != null) {
					if (msv.getEsmeGroups().getGroupId() > 0) {
						vEmsMt.setEsmeGroups(msv.getEsmeGroups());
					}
				}
				if (msv.getEsmeSubscriber() != null) {
					if (msv.getEsmeSubscriber().getSubId() > 0) {
						vEmsMt.setEsmeSubscriber(msv.getEsmeSubscriber());
					}
				}

				vEmsMt.setMsisdn(msv.getMsisdn());
				vEmsMt.setMessage(vstrFeedBack);
				vEmsMt.setStatus("7");
				vEmsMt.setRequestTime(new Date());
				vEmsMt.setLastRetryTime(new java.util.Date());
				if (msv.getRetryNumber() != null) {
					vEmsMt.setRetryNumber(msv.getRetryNumber());
				}

				if (msv.getEsmeSmsCommand() != null && msv.getEsmeSmsCommand().getCode() != null) {

					vEmsMt.setCommandCode(msv.getEsmeSmsCommand().getCode());
				}
				if (msv.getEsmeSmsLog() != null) {
					vEmsMt.setEsmeSmsLog(msv.getEsmeSmsLog());
				}

				vEmsMt.setEsmeEmsMo(msv);
				try {
					long id = emsmtService.add(vEmsMt);
					vEmsMt.setMtId(id);
					msv.setEsmeEmsMt(vEmsMt);
					msv.setMtMessage(vstrFeedBack);
					msv.setMtLastRetryTime(new java.util.Date());
					msv.setMtStatus("7");
					CacheDB.cacheMo.add(msv);
					MessageAlerter.showMessageI18n(getWindow(), TM.get("emsmt.table.add_feedback.success"));
				} catch (Exception_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				EsmeEmsMt vEmsMt = msv.getEsmeEmsMt();
				vEmsMt.setMessage(vstrFeedBack);
				vEmsMt.setLastRetryTime(new java.util.Date());
				msv.setMessage(vstrFeedBack);
				vEmsMt.setRequestTime(new Date());
				msv.setMtLastRetryTime(new java.util.Date());
				try {
					emsmtService.update(vEmsMt);
					for (EsmeEmsMo esmeEmsMo : CacheDB.cacheMo) {

						if (esmeEmsMo.getMoId() == msv.getMoId()) {

							esmeEmsMo.setMtMessage(vstrFeedBack);
							esmeEmsMo.setMtLastRetryTime(new java.util.Date());

						}
					}

					MessageAlerter.showMessageI18n(getWindow(), TM.get("emsmt.table.edit_feedback.success"));
				} catch (Exception_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		tbl.refreshRowCache();
		pnlAction.clearAction();
		FormUtil.clearCache(null);
	}

	@Override
	public void delete(Object object) {

		// TODO Auto-generated method stub
		resetResource();
		if (object instanceof EsmeEmsMo) {
			EsmeEmsMo vEmsMo = (EsmeEmsMo) object;
			boolean b = emsmoService.checkConstraints(vEmsMo.getMoId());
			if (!b) {
				total++;
				canDelete.add(vEmsMo);
			} else {

				MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("message.delete.constraints"));
				return;
			}
		} else {
			// ArrayList<EsmeEmsMo> arrEmsMo= (ArrayList<EsmeEmsMo>)
			// getAllItemCheckedOnTable();
			// for (int i=0;i<arrEmsMo.size();i++){
			// EsmeEmsMo rowEmsMo = arrEmsMo.get(i);
			// total++;
			//
			// boolean b = emsmoService.checkConstraints(rowEmsMo.getMoId());
			// if (!b) {
			// canDelete.add(rowEmsMo);
			// }
			// }
			for (EsmeEmsMo obj : (List<EsmeEmsMo>) object) {
				total++;

				boolean b = emsmoService.checkConstraints(obj.getMoId());
				if (!b) {
					canDelete.add(obj);
				} else if (b && ((List<EsmeEmsMo>) object).size() == 1) {

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

	public void doDelete() {

		int deleted = 0;
		for (EsmeEmsMo msv : canDelete) {
			try {
				EsmeEmsMt vEmsMt = msv.getEsmeEmsMt();
				LogUtil.logActionDelete(FormSmsMt.class.getName(), "SMS_MT", "MT_ID", "" + msv.getMoId() + "", null);

				if (vEmsMt != null) {

					emsmtService.delete(vEmsMt);
				}

				if (msv.getEsmeGroups() != null) {
					if (msv.getEsmeGroups().getGroupId() <= 0) {
						msv.setEsmeGroups(null);
					}
				}
				if (msv.getEsmeSubscriber() != null) {
					if (msv.getEsmeSubscriber().getSubId() <= 0) {
						msv.setEsmeSubscriber(null);
					}
				}
				boolean mess = emsmoService.checkConstraints(msv.getMoId());
				if (!mess) {
					emsmoService.delete(msv);
				}
				tbl.removeItem(msv);
				CacheDB.cacheMo.remove(msv);

				deleted++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		container.initPager(emsmoService.count(null, DEFAULT_EXACT_MATCH));

		FormUtil.clearCache(null);
		MessageAlerter.showMessageI18n(getWindow(), TM.get("message.delete"), deleted, total);
	}

	@Override
	public void showDialog(Object object) {

		// TODO Auto-generated method stub
		if (getWindow().getChildWindows().contains(dialog)) {
			return;
		}

		Item item = null;
		int action = pnlAction.getAction();
		if (action == PanelActionProvider.ACTION_EDIT) {
			item = tbl.getItem(object);
		} else {

			if (getAllItemCheckedOnTable().size() <= 0) {

				MessageAlerter.showMessageI18n(getWindow(), TM.get("emsmt.table.feedback.message_check_empty"));
				return;
			}
			EsmeEmsMo sv = new EsmeEmsMo();
			item = new BeanItem<EsmeEmsMo>(sv);
		}
		createDialog(item);
	}

	private Window createDialog(Item item) {

		form.setItemDataSource(item);
		form.setVisibleItemProperties(TM.get("emsmt.form.visibleproperties").split(","));
		form.setValidationVisible(false);
		// form.focus();
		getWindow().addWindow(dialog);
		return dialog;
	}

	@Override
	public void searchOrAddNew(String key) {

		// TODO Auto-generated method stub
		moSearch = new EsmeEmsMo();
		moSearch.setMessage(key);
		DEFAULT_EXACT_MATCH = true;
		int count = emsmoService.count(moSearch, DEFAULT_EXACT_MATCH);
		if (count > 0) {
			container.initPager(count);
			pnlAction.SetFocusField(true);
			pnlAction.clearAction();
		} else {
			showDialog(moSearch);
			pnlAction.SetFocusField(false);
		}
		DEFAULT_EXACT_MATCH = false;
	}

	@Override
	public void search() {

		moSearch = new EsmeEmsMo();
		searchForm.setValidationVisible(false);

		if (searchForm.isValid()) {
			searchForm.commit();
			try {

				if (searcher.getFromDate() != null && searcher.getToDate() != null) {

					Date fromDate = FormUtil.toDate(searcher.getFromDate(), new com.fis.esme.util.Dictionary[] { new com.fis.esme.util.Dictionary(Calendar.HOUR_OF_DAY, 00),
					        new com.fis.esme.util.Dictionary(Calendar.MINUTE, 00), new com.fis.esme.util.Dictionary(Calendar.SECOND, 00) });

					Date toDate = FormUtil.toDate(searcher.getToDate(), new com.fis.esme.util.Dictionary[] { new com.fis.esme.util.Dictionary(Calendar.HOUR_OF_DAY, 23),
					        new com.fis.esme.util.Dictionary(Calendar.MINUTE, 59), new com.fis.esme.util.Dictionary(Calendar.SECOND, 59) });

					moSearch.setRequestTime(fromDate);
					moSearch.setLastUpdate(toDate);
				}
				if (searcher.getTfMsisdn() != null && !searcher.getTfMsisdn().equalsIgnoreCase("")) {

					moSearch.setMsisdn(searcher.getTfMsisdn());
				} else {
					moSearch.setMsisdn(null);
				}
				if (searcher.getCbbShortCode() != null) {

					moSearch.setEsmeShortCode(searcher.getCbbShortCode());
				} else {
					moSearch.setEsmeShortCode(null);
				}
				if (searcher.getTfMessage() != null && !searcher.getTfMessage().equalsIgnoreCase("")) {

					moSearch.setMessage(searcher.getTfMessage());
				} else {
					moSearch.setMessage(null);
				}

				if (searcher.getTfFeedback() == null) {
					searcher.setTfFeedback("");
				}

				if (!searcher.getTfFeedback().equals("") || searcher.getCbbStatus() != null) {
					for (EsmeEmsMo mo : CacheDB.cacheMo) {

						if (mo.getMtMessage() != null && mo.getMtMessage().toLowerCase().contains(searcher.getTfFeedback().toLowerCase())) {

							if (searcher.getCbbStatus() == null || (searcher.getCbbStatus() != null && mo.getMtStatus().equals(searcher.getCbbStatus()))) {
								if (moSearch.getReason() == null) {
									moSearch.setReason("" + mo.getMoId());
								} else {
									moSearch.setReason(moSearch.getReason() + "," + mo.getMoId());
								}
							}
						}
					}
				}
				if (searcher.getCbbGroup() != null) {

					moSearch.setEsmeGroups(searcher.getCbbGroup());
				} else {
					moSearch.setType(null);
				}

				if ((!searcher.getTfFeedback().equalsIgnoreCase("") || searcher.getCbbStatus() != null) && moSearch.getReason() == null) {

					MessageAlerter.showMessageI18n(getWindow(), TM.get("msg.search.value.emty"));
					return;
				}

				int count = emsmoService.count(moSearch, DEFAULT_EXACT_MATCH);
				if (count > 0) {
					container.initPager(count);
				} else if (count <= 0) {
					MessageAlerter.showMessageI18n(getWindow(), TM.get("msg.search.value.emty"));
				}

			} catch (Exception e) {
				FormUtil.showException(this, e);
			}

		} else {
			searchForm.setValidationVisible(true);
		}

		searchFactory.focusFirstField();

	}

	@Override
	public void fieldSearch(SearchObj searchObj) {

		// TODO Auto-generated method stub
		if (searchObj.getField() == null && searchObj.getKey() == null)
			return;

		moSearch = new EsmeEmsMo();
		if (searchObj.getField() == null) {
			moSearch.setMessage(searchObj.getKey());
		} else {
			if (searchObj.getField().equals("msisdn"))
				moSearch.setMsisdn(searchObj.getKey());
			else if (searchObj.getField().equals("shortCode"))
				moSearch.setShortCode(searchObj.getKey());
			else if (searchObj.getField().equals("message"))
				moSearch.setMessage(searchObj.getKey());
			else if (searchObj.getField().equals("mtMessage"))
				moSearch.setMtMessage(searchObj.getKey());

		}

		int count = emsmoService.count(moSearch, DEFAULT_EXACT_MATCH);
		if (count > 0) {
			container.initPager(count);
		} else {
			MessageAlerter.showMessageI18n(getWindow(), TM.get("msg.search.value.emty"));
		}
		pnlAction.clearAction();
	}

	@Override
	public void export() {

		// TODO Auto-generated method stub

	}

	@Override
	public String getPermission() {

		// TODO Auto-generated method stub
		return SessionData.getAppClient().getPermission(this.getClass().getName());
	}

	public List<EsmeEmsMo> getAllItemCheckedOnTable() {

		List<EsmeEmsMo> list = new ArrayList<EsmeEmsMo>();
		Collection<EsmeEmsMo> collection = (Collection<EsmeEmsMo>) tbl.getItemIds();
		for (EsmeEmsMo obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

	private void setEnableAction(Object id) {

		final boolean enable = (id != null);
		form.setItemDataSource(tbl.getItem(id));
		pnlAction.setRowSelected(enable);

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

	public void approveMessageSelected() {

		if (getAllItemCheckedOnTable() != null && getAllItemCheckedOnTable().size() > 0) {
			String message = TM.get("messagescheduler.dialog.approveScheduler.caption");
			confirmDeletion(message);
		} else {

			MessageAlerter.showMessageI18n(getWindow(), TM.get("emsmt.table.approver.message_check_empty"));
		}

	}

	public void onApprover() {

		List<EsmeEmsMo> list = getAllItemCheckedOnTable();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				EsmeEmsMo rowEmsMo = list.get(i);
				if (rowEmsMo.getEsmeEmsMt() == null) {

					MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("emsmt.table.message_empty_feedback"));
					return;
				} else {

					try {
						rowEmsMo.setMtStatus("0");
						rowEmsMo.getEsmeEmsMt().setStatus("0");

						emsmtService.update(rowEmsMo.getEsmeEmsMt());
						tbl.refreshRowCache();
						MessageAlerter.showMessageI18n(getWindow(), TM.get("emsmt.table.approver.success"));
					} catch (Exception_Exception e) {

						e.printStackTrace();
					}

				}
			}
		}
	}
}
