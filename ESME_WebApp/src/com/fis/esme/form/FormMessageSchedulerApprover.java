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
import com.fis.esme.component.CommonTreeTablePanel;
import com.fis.esme.component.ConfirmDeletionDialog;
import com.fis.esme.component.CustomTable;
import com.fis.esme.component.CustomTreeTable;
import com.fis.esme.component.HMField;
import com.fis.esme.component.PagingComponent.ChangePageEvent;
import com.fis.esme.component.PagingComponent.PagingComponentListener;
import com.fis.esme.component.PanelActionProvider;
import com.fis.esme.component.TableContainerApprover;
import com.fis.esme.message.MessageTransferer;
import com.fis.esme.messagecontent.MessageContentTransferer;
import com.fis.esme.persistence.EsmeGroups;
import com.fis.esme.persistence.EsmeLanguage;
import com.fis.esme.persistence.EsmeMessage;
import com.fis.esme.persistence.EsmeMessageContent;
import com.fis.esme.persistence.EsmeScheduler;
import com.fis.esme.persistence.EsmeSchedulerAction;
import com.fis.esme.persistence.EsmeSchedulerDetail;
import com.fis.esme.scheduler.Exception_Exception;
import com.fis.esme.scheduler.SchedulerTransferer;
import com.fis.esme.scheduleraction.SchedulerActionTransferer;
import com.fis.esme.schedulerdetail.SchedulerDetailTransferer;
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
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.HeaderClickEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.themes.Reindeer;

import eu.livotov.tpt.gui.dialogs.OptionDialog.OptionDialogResultListener;
import eu.livotov.tpt.gui.dialogs.OptionKind;
import eu.livotov.tpt.i18n.TM;

public class FormMessageSchedulerApprover extends VerticalLayout implements PanelActionProvider, PagingComponentListener, ServerSort, Action.Handler, OptionDialogResultListener, PanelTreeProvider {

	private HorizontalSplitPanel mainLayout;
	private CustomTreeTable treeTable;
	private ComboBox cboSearch;
	private CommonTreeTablePanel commonTree;
	private BeanItemContainer<EsmeGroups> dataGroups;

	private CommonDialog dialog;
	private Form form;
	private CustomTable tbl;
	private TableContainerApprover container;
	private BeanItemContainer<EsmeMessageContent> data;
	private CommonButtonPanel pnlAction;
	private FormMessageFieldFactory fieldFactory;

	private final String DEFAULT_SORTED_COLUMN = "esmeMessage";
	private boolean DEFAULT_SORTED_ASC = true;
	private boolean DEFAULT_EXACT_MATCH = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;
	private EsmeMessageContent skSearch = null;
	private EsmeLanguage defaultLanguage = null;
	private EsmeMessageContent esmeMessageContentSelect;
	private EsmeSchedulerAction esmeSchedulerAction;
	private EsmeScheduler esmeSchedulerSelect;
	private EsmeMessage esmeMessageSelect;
	private ArrayList<EsmeSchedulerDetail> arrSchedulerDetail;
	private int total = 0;
	private ArrayList<EsmeMessageContent> canDelete = new ArrayList<EsmeMessageContent>();
	private ConfirmDeletionDialog confirm;
	private MessageTransferer serviceMessage;
	private MessageContentTransferer serviceContent;
	private SchedulerTransferer serviceScheduler;
	private SchedulerDetailTransferer serviceSchedulerDetail;
	private SchedulerActionTransferer serviceSchedulerAction;

	private VerticalLayout veSchedule = new VerticalLayout();
	private VerticalLayout veLeft = new VerticalLayout();
	private GridLayout grlSchedule = new GridLayout(3, 3);
	private Label lblDate = new Label("Date");
	private Label lblTime = new Label("Time");
	private Label lblType = new Label("Type");
	private TextField txtdateWM = new TextField();
	private Button btnDate = new Button();
	private Button btnSchedule = new Button();
	private Button btnRemove = new Button();
	private HorizontalLayout layoutButtonSchedule = new HorizontalLayout();
	private PopupDateField dtDate = new PopupDateField();
	private HMField dtTime = new HMField("");
	private ComboBox cboType = new ComboBox();
	private String Directly = "1";
	private String Weekly = "2";
	private String Monthy = "3";
	private ThemeResource ICON_DATE = new ThemeResource("icons/24/calendar_background.png");
	private DialogWeeklyTableApprover weeklyTable;

	private Panel pnlMessage = new Panel();
	private Panel pnlSchedule = new Panel("Message Schedule");

	public FormMessageSchedulerApprover(String key) {

		this();
		if (key != null) {
			pnlAction.clearAction();
			pnlAction.searchOrAddNew(key);
		}
	}

	public FormMessageSchedulerApprover() {

		LogUtil.logAccess(FormMessageSchedulerApprover.class.getName());
		initLayout();
	}

	private void initLayout() {

		pnlAction = new CommonButtonPanel(this);
		pnlAction.showSearchPanel(true);
		pnlAction.setFromCaption(TM.get(FormMessageSchedulerApprover.class.getName()));
		pnlAction.setMargin(false, false, true, false);
		this.addComponent(pnlAction);
		initComponent();
		this.setSizeFull();
		mainLayout = new HorizontalSplitPanel();
		Panel panel = new Panel();
		panel.setSizeFull();
		panel.setContent(mainLayout);

		pnlSchedule.setSizeFull();
		pnlSchedule.setContent(veSchedule);

		pnlMessage.setContent(container);
		pnlMessage.setHeight("310px");
		pnlSchedule.setHeight("200px");
		veLeft.setSizeFull();
		veLeft.addComponent(pnlMessage);
		veLeft.setComponentAlignment(pnlMessage, Alignment.MIDDLE_CENTER);
		veLeft.addComponent(pnlSchedule);
		veLeft.setComponentAlignment(pnlSchedule, Alignment.MIDDLE_CENTER);
		// veLeft.setExpandRatio(pnlMessage, 0.6f);
		// veLeft.setExpandRatio(pnlSchedule, 0.4f);

		mainLayout.setSizeFull();
		mainLayout.setSplitPosition(1000, Sizeable.UNITS_PIXELS);
		mainLayout.setFirstComponent(veLeft);

		mainLayout.setSecondComponent(commonTree);

		this.addComponent(pnlAction);
		this.setSizeFull();
		this.addComponent(panel);
		this.setExpandRatio(panel, 1.0f);
		this.setComponentAlignment(panel, Alignment.TOP_CENTER);
	}

	private void initComponent() {

		data = new BeanItemContainer<EsmeMessageContent>(EsmeMessageContent.class);
		dataGroups = new BeanItemContainer<EsmeGroups>(EsmeGroups.class);
		initService();
		initLanguage();
		initTable();
		initForm();
		initTreeTable();
		intitScheduler();
	}

	public void intitScheduler() {

		cboType.setRequired(true);
		cboType.setWidth("250px");
		cboType.setImmediate(true);
		cboType.addItem(Directly);
		cboType.addItem(Weekly);
		cboType.addItem(Monthy);
		cboType.setItemCaption(Directly, "One-Time Schedule");
		cboType.setItemCaption(Weekly, "Weekly");
		cboType.setItemCaption(Monthy, "Monthy");
		cboType.select(Directly);
		cboType.setNullSelectionAllowed(false);
		dtTime.setWidth("250px");
		dtDate.setWidth("250px");
		dtDate.setResolution(DateField.RESOLUTION_DAY);
		txtdateWM.setEnabled(false);
		txtdateWM.setNullRepresentation("");
		txtdateWM.setImmediate(true);
		txtdateWM.setRequired(true);
		txtdateWM.setWidth("205px");
		lblDate.setWidth("120px");
		lblTime.setWidth("120px");
		lblType.setWidth("120px");
		btnDate.setWidth("24px");
		btnDate.setStyleName(Reindeer.BUTTON_LINK);
		btnDate.setIcon(ICON_DATE);
		btnDate.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				if (cboType.getValue() != null && (cboType.getValue().toString().equalsIgnoreCase("2") || cboType.getValue().toString().equalsIgnoreCase("3"))) {
					weeklyTable = new DialogWeeklyTableApprover("Scheduler", cboType.getValue().toString(), FormMessageSchedulerApprover.this);
					weeklyTable.show(getApplication());

				}
			}
		});
		cboType.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				txtdateWM.setValue("");
				if (cboType.getValue().toString().equalsIgnoreCase("3")) {
					grlSchedule.removeAllComponents();
					grlSchedule.addComponent(lblType, 0, 0);
					grlSchedule.addComponent(cboType, 1, 0, 2, 0);
					grlSchedule.addComponent(lblTime, 0, 1);
					grlSchedule.addComponent(dtTime, 1, 1, 2, 1);
					grlSchedule.addComponent(lblDate, 0, 2);
					grlSchedule.addComponent(txtdateWM, 1, 2);
					grlSchedule.addComponent(btnDate, 2, 2);
				} else if (cboType.getValue().toString().equalsIgnoreCase("2")) {
					grlSchedule.removeAllComponents();
					grlSchedule.addComponent(lblType, 0, 0);
					grlSchedule.addComponent(cboType, 1, 0, 2, 0);
					grlSchedule.addComponent(lblTime, 0, 1);
					grlSchedule.addComponent(dtTime, 1, 1, 2, 1);
					grlSchedule.addComponent(lblDate, 0, 2);
					grlSchedule.addComponent(txtdateWM, 1, 2);
					grlSchedule.addComponent(btnDate, 2, 2);
				} else {
					grlSchedule.removeAllComponents();
					grlSchedule.addComponent(lblType, 0, 0);
					grlSchedule.addComponent(cboType, 1, 0, 2, 0);
					grlSchedule.addComponent(lblTime, 0, 1);
					grlSchedule.addComponent(dtTime, 1, 1, 2, 1);
					// grlSchedule.addComponent(lblDate, 0, 2);
				}
			}
		});
		btnSchedule = new Button(TM.get("messagescheduler.buttom.btnSchedule.caption"));
		btnSchedule.setWidth("100px");
		btnSchedule.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				onScheduler();
			}
		});
		btnRemove = new Button(TM.get("messagescheduler.buttom.btnRemove.caption"));
		btnRemove.setWidth("100px");
		btnRemove.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				onRemove();
			}
		});
		layoutButtonSchedule.setHeight("30px");
		grlSchedule.setHeight("120px");
		layoutButtonSchedule.setSpacing(true);
		layoutButtonSchedule.setMargin(false);
		layoutButtonSchedule.addComponent(btnSchedule);
		layoutButtonSchedule.setComponentAlignment(btnSchedule, Alignment.MIDDLE_CENTER);
		layoutButtonSchedule.addComponent(btnRemove);
		layoutButtonSchedule.setComponentAlignment(btnRemove, Alignment.MIDDLE_CENTER);

		grlSchedule.setStyleName("Form_GridLayout_Style");
		grlSchedule.setImmediate(true);
		grlSchedule.setSpacing(true);
		grlSchedule.setRowExpandRatio(1, 1.0f);
		grlSchedule.setColumnExpandRatio(2, 1.0f);
		grlSchedule.addComponent(lblType, 0, 0);
		grlSchedule.addComponent(cboType, 1, 0, 2, 0);
		grlSchedule.addComponent(lblTime, 0, 1);
		grlSchedule.addComponent(dtTime, 1, 1, 2, 1);
		// grlSchedule.addComponent(lblDate, 0, 2);
		// grlSchedule.addComponent(lmonth, 1, 2);
		// veSchedule.setSizeFull();
		veSchedule.setStyleName("verticallayout_margintop");
		veSchedule.setSpacing(true);
		veSchedule.addComponent(grlSchedule);
		veSchedule.addComponent(layoutButtonSchedule);
		veSchedule.setMargin(false, true, false, false);
		veSchedule.setComponentAlignment(grlSchedule, Alignment.MIDDLE_CENTER);
		veSchedule.setComponentAlignment(layoutButtonSchedule, Alignment.MIDDLE_CENTER);

	}

	private void initTreeTable() {

		try {
			EsmeGroups esmeGroups = new EsmeGroups();
			CacheDB.cacheGroups = CacheServiceClient.GroupsService.findAllWithOrderPaging(esmeGroups, null, false, -1, -1, true);
			Collections.sort(CacheDB.cacheGroups, FormUtil.stringComparator(true));
		} catch (com.fis.esme.groups.Exception_Exception e) {
			e.printStackTrace();
		}
		List<EsmeGroups> list = new ArrayList<EsmeGroups>();
		list.addAll(CacheDB.cacheGroups);

		cboSearch = new ComboBox();
		cboSearch.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		treeTable = new CustomTreeTable(null, dataGroups);
		treeTable.setSizeFull();
		treeTable.setStyleName("commont_table_noborderLR");
		treeTable.setSelectable(true);
		treeTable.addGeneratedColumn("select", new Table.ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {

				final EsmeGroups bean = (EsmeGroups) itemId;

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
			treeTable.setVisibleColumns(TM.get("messagescheduler.groups.table.filteredcolumns").split(","));
			treeTable.setColumnHeaders(TM.get("messagescheduler.groups.table.setcolumnheaders").split(","));

			buildDataForTreeTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
		commonTree = new CommonTreeTablePanel(treeTable, cboSearch, this);
		commonTree.setComboBoxSearchTooltip(TM.get("messagescheduler.groups.combobox.cbotooltip"));
		commonTree.setComBoxSearchInputPrompt(TM.get("messagescheduler.groups.combobox.cboinput"));
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
			serviceScheduler = CacheServiceClient.SchedulerService;
			serviceSchedulerDetail = CacheServiceClient.SchedulerDetailService;
			serviceSchedulerAction = CacheServiceClient.SchedulerActionService;
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
				Object[] sortCol = TM.get("messagescheduler.table.setsortcolumns").split(",");
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
					if (content.getEsmeMessage().getStatus().equals("0")) {
						return TM.get("messagescheduler.table.status.InActive");
					} else {
						return TM.get("messagescheduler.table.status.Active");
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
		tbl.addListener(new ItemClickListener() {

			public void itemClick(ItemClickEvent event) {

				esmeSchedulerSelect = null;
				esmeSchedulerAction = null;
				arrSchedulerDetail = null;

				BeanItemContainer<?> con = (BeanItemContainer<?>) tbl.getContainerDataSource();
				BeanItem<EsmeMessageContent> item = (BeanItem<EsmeMessageContent>) event.getItem();
				esmeMessageContentSelect = item.getBean();
				esmeMessageSelect = esmeMessageContentSelect.getEsmeMessage();
				fillMessageScheduler();
				treeTable.requestRepaint();
				treeTable.refreshRowCache();
			}
		});

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
						pnlAction.edit(event.getItemId());
					}
				}
			});
		}

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

		tbl.setSortContainerPropertyId(TM.get("messagescheduler.table.setsortcolumns").split(","));

		tbl.setVisibleColumns(TM.get("messageschedulerapprover.table.setvisiblecolumns").split(","));
		tbl.setColumnHeaders(TM.get("messagescheduler.table.setcolumnheaders").split(","));
		tbl.setStyleName("commont_table_noborderLR");

		String[] columnWidth = TM.get("messagescheduler.table.columnwidth").split(",");
		String[] columnWidthValue = TM.get("messageschedulerapprover.table.columnwidth").split(",");
		for (int i = 0; i < columnWidth.length; i++) {
			tbl.setColumnWidth(columnWidth[i], Integer.parseInt(columnWidthValue[i]));
		}

		if (tbl.getContainerDataSource().equals(null)) {
			pnlAction.setRowSelected(false);
		}

		container = new TableContainerApprover(tbl, this, Integer.parseInt(TM.get("pager.page.rowsinpage"))) {

			@Override
			public void deleteAllItemSelected() {

				pnlAction.delete(getAllItemCheckedOnTable());
			}
		};
		// container.setActionPanel(pnlAction);
		container.initPager(serviceContent.count(null, DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(true);
		pnlAction.setValueForCboField(TM.get("messagescheduler.table.filteredcolumns").split(","), TM.get("messagescheduler.table.filteredcolumnscaption").split(","));
		container.setFilteredColumns(TM.get("messagescheduler.table.filteredcolumns").split(","));
		container.setEnableDeleteAllButton(getPermission().contains("D"));
		container.setEnableButtonAddNew(getPermission().contains("I"));
		container.setEnableButtonAddCopy(getPermission().contains("I"));
		container.setEnableButtonApprover(true);
		container.btnApprover.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				onApprover();
			}
		});

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

		dialog = new CommonDialog(TM.get("messagescheduler.commondialog.caption"), form, this);
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
		form.setVisibleItemProperties(TM.get("messagescheduler.form.visibleproperties").split(","));
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
			fieldFactory.setEnabledCboStatus(true);
		} else if (action == PanelActionProvider.ACTION_ADD_COPY) {
			fieldFactory.setEnabledCboStatus(false);
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
			newBean.setStatus("1");
			newBean.setLastModify(new Date());
			item = new BeanItem<EsmeMessageContent>(newBean);
		} else if (action == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
			fieldFactory.setEnabledCboStatus(false);
			EsmeMessageContent bean = new EsmeMessageContent();
			bean.setName("");
			bean.setDesciption("");
			bean.setStatus("1");
			item = new BeanItem<EsmeMessageContent>(bean);
		} else {
			fieldFactory.setEnabledCboStatus(false);
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
			msv.setStatus("1");
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

						LogUtil.logActionInsert(FormMessageSchedulerApprover.class.getName(), "ESME_MESSAGE_CONTENT", "MSG_CONTENT_ID", "" + msv.getId() + "", null);

						if (pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
							pnlAction.clearAction();
							pnlAction.searchOrAddNew(msv.getCode());
						}

						MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.add.success", TM.get("common.messagescheduler").toLowerCase()));
					} else {
						MessageAlerter.showErrorMessage(getWindow(), TM.get("common.msg.add.fail", TM.get("common.messagescheduler").toLowerCase()));
					}
				} catch (Exception e) {
					FormUtil.showException(this, e);
				}
			} else if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT) {
				try {

					Vector v = LogUtil.logActionBeforeUpdate(FormMessageSchedulerApprover.class.getName(), "ESME_MESSAGE_CONTENT", "MSG_CONTENT_ID", "" + msv.getId() + "", null);

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

					MessageAlerter.showMessageI18n(getWindow(), TM.get("common.msg.edit.success", TM.get("common.messagescheduler").toLowerCase()));

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
				LogUtil.logActionDelete(FormMessageSchedulerApprover.class.getName(), "ESME_MESSAGE_CONTENT", "MSG_CONTENT_ID", "" + msv.getId() + "", null);

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
	}

	private void setEnableAction(Object id) {

		final boolean enable = (id != null);
		form.setItemDataSource(tbl.getItem(id));
		pnlAction.setRowSelected(enable);
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

	@Override
	public void export() {

	}

	@Override
	public void fieldSearch(SearchObj searchObj) {

		if (searchObj.getField() == null && searchObj.getKey() == null)
			return;

		skSearch = new EsmeMessageContent();
		if (searchObj.getField() == null) {
			skSearch.setMessage(searchObj.getKey());
		} else {
			if (searchObj.getField().equals("message"))
				skSearch.setMessage(searchObj.getKey());
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

	@Override
	public void filterTree(Object obj) {

		// TODO Auto-generated method stub
		treeTable.select(obj);
		treeTable.focus();
	}

	@Override
	public void treeValueChanged(Object obj) {

		// TODO Auto-generated method stub

	}

	private void buildDataForTreeTable() {

		dataGroups.removeAllItems();
		treeTable.removeAllItems();
		List<EsmeGroups> listRootDepartment = null;
		listRootDepartment = getAllChildrenIsRoot(null, CacheDB.cacheGroups);
		for (EsmeGroups esmeGroups : listRootDepartment) {
			dataGroups.addBean(esmeGroups);
			treeTable.setCollapsed(esmeGroups, false);
			buildTreeNode(esmeGroups, getAllChildren(esmeGroups, CacheDB.cacheGroups));
		}

		cboSearch.setContainerDataSource(treeTable.getContainerDataSource());
	}

	private List<EsmeGroups> getAllChildrenIsRoot(EsmeGroups parent, List<EsmeGroups> list) {

		List<EsmeGroups> listChildren = new ArrayList<EsmeGroups>();
		for (EsmeGroups esmeGroups : list) {
			if ((esmeGroups.getParentId() == -1)) {
				listChildren.add(esmeGroups);
			}
		}
		return listChildren;
	}

	private List<EsmeGroups> getAllChildren(EsmeGroups parent, List<EsmeGroups> list) {

		List<EsmeGroups> listChildren = new ArrayList<EsmeGroups>();
		for (EsmeGroups esmeGroups : list) {
			if ((esmeGroups.getParentId() != -1)) {
				if (parent.getGroupId() == esmeGroups.getParentId()) {
					listChildren.add(esmeGroups);
				}
			}
		}
		return listChildren;
	}

	public void buildTreeNode(EsmeGroups parent, List<EsmeGroups> list) {

		for (EsmeGroups esmeGroups : list) {
			if (esmeGroups.getParentId() == parent.getGroupId()) {
				dataGroups.addBean(esmeGroups);
				treeTable.setParent(esmeGroups, parent);
				treeTable.setCollapsed(esmeGroups, false);
				List<EsmeGroups> listTemp = getAllChildren(esmeGroups, CacheDB.cacheGroups);
				if (listTemp.size() > 0) {
					buildTreeNode(esmeGroups, listTemp);
				}
			}
		}
	}

	public void setValueDate(String value) {

		txtdateWM.setValue("");
		txtdateWM.setValue(value);
	}

	public String getValueDate() {

		if (txtdateWM.getValue() != null && !txtdateWM.getValue().toString().equalsIgnoreCase("")) {
			return txtdateWM.getValue().toString();
		}

		return null;
	}

	public List<EsmeGroups> getAllItemCheckedOnTreeTable() {

		List<EsmeGroups> list = new ArrayList<EsmeGroups>();
		Collection<EsmeGroups> collection = (Collection<EsmeGroups>) treeTable.getItemIds();
		for (EsmeGroups obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

	public void onScheduler() {

		if (esmeMessageContentSelect == null) {
			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("messagescheduler.tbl.notnull"));
			return;
		}
		System.out.println("Scheduler");
		if (dtTime.getValue() == null || dtTime.getValue().toString().trim().equalsIgnoreCase("")) {
			dtTime.focus();
			dtTime.setValue(null);
			MessageAlerter.showErrorMessage(getWindow(), "Time field cannot be empty");
		} else if ((cboType.getValue().toString().equalsIgnoreCase("2") || cboType.getValue().toString().equalsIgnoreCase("3"))
		        && (txtdateWM.getValue() == null || txtdateWM.getValue().toString().trim().equalsIgnoreCase(""))) {
			txtdateWM.focus();
			txtdateWM.setValue(null);
			MessageAlerter.showErrorMessage(getWindow(), "Date field cannot be empty");
		} else {
			ArrayList<String> stringDate = new ArrayList<String>();
			if (cboType.getValue().toString().equalsIgnoreCase("2") || cboType.getValue().toString().equalsIgnoreCase("3")) {
				String dateWM = txtdateWM.getValue().toString();
				if (cboType.getValue().toString().equalsIgnoreCase("2")) {
					if (dateWM.contains("Monday")) {
						stringDate.add("1");
					}
					if (dateWM.contains("Tuesday")) {
						stringDate.add("2");
					}
					if (dateWM.contains("Wednesday")) {
						stringDate.add("3");
					}
					if (dateWM.contains("Thursday")) {
						stringDate.add("4");
					}
					if (dateWM.contains("Friday")) {
						stringDate.add("5");
					}
					if (dateWM.contains("Saturday")) {
						stringDate.add("6");
					}
					if (dateWM.contains("Sunday")) {
						stringDate.add("7");
					}

				} else if (cboType.getValue().toString().equalsIgnoreCase("3")) {
					if (dateWM.contains("1st.")) {
						stringDate.add("1");
					}
					if (dateWM.contains("2nd.")) {
						stringDate.add("2");
					}
					if (dateWM.contains("3rd.")) {
						stringDate.add("3");
					}
					if (dateWM.contains("4th.")) {
						stringDate.add("4");
					}
					if (dateWM.contains("5th.")) {
						stringDate.add("5");
					}
					if (dateWM.contains("6th.")) {
						stringDate.add("6");
					}
					if (dateWM.contains("7th.")) {
						stringDate.add("7");
					}
					if (dateWM.contains("8th.")) {
						stringDate.add("8");
					}
					if (dateWM.contains("9th.")) {
						stringDate.add("9");
					}
					if (dateWM.contains("10th")) {
						stringDate.add("10");
					}
					if (dateWM.contains("11th")) {
						stringDate.add("11");
					}
					if (dateWM.contains("12th")) {
						stringDate.add("12");
					}
					if (dateWM.contains("13th")) {
						stringDate.add("13");
					}
					if (dateWM.contains("14th")) {
						stringDate.add("14");
					}
					if (dateWM.contains("15th")) {
						stringDate.add("15");
					}
					if (dateWM.contains("16th")) {
						stringDate.add("16");
					}
					if (dateWM.contains("17th")) {
						stringDate.add("17");
					}
					if (dateWM.contains("18th")) {
						stringDate.add("18");
					}
					if (dateWM.contains("19th")) {
						stringDate.add("19");
					}
					if (dateWM.contains("20th")) {
						stringDate.add("20");
					}
					if (dateWM.contains("21st")) {
						stringDate.add("21");
					}
					if (dateWM.contains("22nd")) {
						stringDate.add("22");
					}
					if (dateWM.contains("23rd")) {
						stringDate.add("23");
					}
					if (dateWM.contains("24th")) {
						stringDate.add("24");
					}
					if (dateWM.contains("25th")) {
						stringDate.add("25");
					}
					if (dateWM.contains("26th")) {
						stringDate.add("26");
					}
					if (dateWM.contains("27th")) {
						stringDate.add("27");
					}
					if (dateWM.contains("28th")) {
						stringDate.add("28");
					}
					if (dateWM.contains("29th")) {
						stringDate.add("29");
					}
					if (dateWM.contains("30th")) {
						stringDate.add("30");
					}
					if (dateWM.contains("31st")) {
						stringDate.add("31");
					}
				}
			}
			onCommonSaveAndScheduler(dtTime.getValue(), stringDate);
		}
	}

	public void onCommonSaveAndScheduler(String vstrTime, ArrayList<String> dtStringDate) {

		List<EsmeGroups> listGroupsSelected = getAllItemCheckedOnTreeTable();
		if (listGroupsSelected.size() <= 0) {
			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("messagescheduler.group.notnull"));
			return;
		}
		String vstrGroupsID = "";
		for (int i = 0; i < listGroupsSelected.size(); i++) {
			EsmeGroups esmeGroups = listGroupsSelected.get(i);
			vstrGroupsID = vstrGroupsID + esmeGroups.getGroupId() + ":";
		}
		vstrGroupsID = vstrGroupsID.substring(0, vstrGroupsID.length() - 1);
		if (getSelectMassageContent() == null) {
			return;
		}
		EsmeMessageContent esmeMessageContent = getSelectMassageContent();
		EsmeMessage esmeMessage = esmeMessageContent.getEsmeMessage();
		EsmeScheduler esmeScheduler = new EsmeScheduler();
		esmeScheduler.setType(cboType.getValue().toString());
		esmeScheduler.setName(esmeMessage.getName() + esmeMessage.getMessageId());
		esmeScheduler.setDescription("");
		esmeScheduler.setStatus("1");
		esmeScheduler.setSchedulerStatus("0");
		esmeScheduler.setTime(vstrTime);
		try {
			long idScheduler = serviceScheduler.add(esmeScheduler);
			esmeScheduler.setSchedulerId(idScheduler);
			for (int i = 0; i < dtStringDate.size(); i++) {
				EsmeSchedulerDetail esmeSchedulerDetail = new EsmeSchedulerDetail();
				esmeSchedulerDetail.setEsmeScheduler(esmeScheduler);
				esmeSchedulerDetail.setDate(dtStringDate.get(i));
				try {
					long idSchedulerDetail = serviceSchedulerDetail.add(esmeSchedulerDetail);
				} catch (com.fis.esme.schedulerdetail.Exception_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MessageAlerter.showErrorMessage(getWindow(), TM.get("common.msg.add.fail", TM.get("common.schedulerdetail").toLowerCase()));
				}
			}
			EsmeSchedulerAction esmeSchedulerAction = new EsmeSchedulerAction();
			esmeSchedulerAction.setEsmeScheduler(esmeScheduler);
			esmeSchedulerAction.setEsmeMessage(esmeMessage);
			esmeSchedulerAction.setGroupId(vstrGroupsID);
			esmeSchedulerAction.setStatus("1");
			esmeSchedulerAction.setLastDatetime(new Date());
			esmeSchedulerAction.setExecutingStatus("0");
			esmeSchedulerAction.setFailNumber(0);
			try {
				long idSchedulerAction = serviceSchedulerAction.add(esmeSchedulerAction);
				MessageAlerter.showMessageI18n(getWindow(), TM.get("messagescheduler.insert.true"));
				btnSchedule.setEnabled(false);
				btnRemove.setEnabled(true);
				cboType.setEnabled(false);
				dtTime.setEnabled(false);
			} catch (com.fis.esme.scheduleraction.Exception_Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MessageAlerter.showErrorMessage(getWindow(), TM.get("common.msg.add.fail", TM.get("common.scheduleraction").toLowerCase()));
			}
		} catch (Exception_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MessageAlerter.showErrorMessage(getWindow(), TM.get("common.msg.add.fail", TM.get("common.scheduler").toLowerCase()));
		}

	}

	public void onRemove() {

		System.out.println("Remove");
		esmeSchedulerSelect = null;
		esmeSchedulerAction = null;
		arrSchedulerDetail = null;
		if (esmeMessageContentSelect == null) {
			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("messagescheduler.tbl.notnull"));
			return;
		}
		EsmeSchedulerAction sesmeSchedulerAction = new EsmeSchedulerAction();
		sesmeSchedulerAction.setEsmeMessage(esmeMessageContentSelect.getEsmeMessage());
		List<EsmeSchedulerAction> dataSchedulerAction;
		try {
			dataSchedulerAction = serviceSchedulerAction.findAllWithOrderPaging(sesmeSchedulerAction, null, false, 0, 1, true);
			if (dataSchedulerAction.size() > 0) {
				esmeSchedulerAction = dataSchedulerAction.get(0);
			}
		} catch (com.fis.esme.scheduleraction.Exception_Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EsmeSchedulerDetail sesmeSchedulerDetail = new EsmeSchedulerDetail();
		esmeSchedulerSelect = esmeSchedulerAction.getEsmeScheduler();
		sesmeSchedulerDetail.setEsmeScheduler(esmeSchedulerSelect);
		try {
			arrSchedulerDetail = (ArrayList<EsmeSchedulerDetail>) serviceSchedulerDetail.findAllWithOrderPaging(sesmeSchedulerDetail, null, false, 0, 40, true);
		} catch (com.fis.esme.schedulerdetail.Exception_Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (esmeSchedulerAction != null) {
			boolean mess = serviceSchedulerAction.checkConstraints(esmeSchedulerAction.getSchaId());
			if (!mess) {
				try {
					serviceSchedulerAction.delete(esmeSchedulerAction);
					try {
						if (arrSchedulerDetail != null) {
							for (int i = 0; i < arrSchedulerDetail.size(); i++) {
								EsmeSchedulerDetail rsmeSchedulerDetail = arrSchedulerDetail.get(i);
								boolean mess1 = serviceSchedulerDetail.checkConstraints(rsmeSchedulerDetail.getSchedulerDetailId());
								if (!mess1) {
									serviceSchedulerDetail.delete(rsmeSchedulerDetail);

								}
							}
							if (esmeSchedulerSelect != null) {
								boolean mess2 = serviceScheduler.checkConstraints(esmeSchedulerSelect.getSchedulerId());
								if (!mess2) {
									try {
										serviceScheduler.delete(esmeSchedulerSelect);
										MessageAlerter.showMessageI18n(getWindow(), TM.get("messagescheduler.delete.true"));
										btnSchedule.setEnabled(true);
										btnRemove.setEnabled(false);
										cboType.setEnabled(true);
										dtTime.setEnabled(true);
									} catch (com.fis.esme.scheduler.Exception_Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
					} catch (com.fis.esme.schedulerdetail.Exception_Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (com.fis.esme.scheduleraction.Exception_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void fillMessageScheduler() {

		try {
			System.out.println("fillMessageScheduler");
			for (int j = 0; j < dataGroups.size(); j++) {
				EsmeGroups esmeGroups = dataGroups.getIdByIndex(j);
				esmeGroups.setSelect(false);
			}
			EsmeSchedulerAction sesmeSchedulerAction = new EsmeSchedulerAction();
			sesmeSchedulerAction.setEsmeMessage(esmeMessageContentSelect.getEsmeMessage());
			List<EsmeSchedulerAction> dataSchedulerAction = serviceSchedulerAction.findAllWithOrderPaging(sesmeSchedulerAction, null, false, 0, 1, true);
			if (dataSchedulerAction.size() > 0) {

				esmeSchedulerAction = dataSchedulerAction.get(0);
				String vstrGroupID = esmeSchedulerAction.getGroupId();
				String[] listGroupID = vstrGroupID.split(":");
				for (int i = 0; i < listGroupID.length; i++) {
					for (int j = 0; j < dataGroups.size(); j++) {
						EsmeGroups esmeGroups = dataGroups.getIdByIndex(j);
						if (listGroupID[i].equals(esmeGroups.getGroupId() + "")) {
							esmeGroups.setSelect(true);
						}
					}
				}
				EsmeSchedulerDetail esmeSchedulerDetail = new EsmeSchedulerDetail();
				esmeSchedulerSelect = esmeSchedulerAction.getEsmeScheduler();
				esmeSchedulerDetail.setEsmeScheduler(esmeSchedulerSelect);
				try {
					arrSchedulerDetail = (ArrayList<EsmeSchedulerDetail>) serviceSchedulerDetail.findAllWithOrderPaging(esmeSchedulerDetail, null, false, 0, 40, true);
					String vstrDate = "";
					for (int i = 0; i < arrSchedulerDetail.size(); i++) {
						EsmeSchedulerDetail vesmeSchedulerDetail = arrSchedulerDetail.get(i);
						if (esmeSchedulerSelect.getType().toString().equalsIgnoreCase("2") || esmeSchedulerSelect.getType().toString().equalsIgnoreCase("3")) {
							String dateWM = vesmeSchedulerDetail.getDate().toString();
							if (esmeSchedulerSelect.getType().toString().equalsIgnoreCase("2")) {
								if (dateWM.contains("1")) {
									vstrDate = vstrDate + "Monday;";
								}
								if (dateWM.contains("2")) {
									vstrDate = vstrDate + "Tuesday;";
								}
								if (dateWM.contains("3")) {
									vstrDate = vstrDate + "Wednesday;";
								}
								if (dateWM.contains("4")) {
									vstrDate = vstrDate + "Thursday;";
								}
								if (dateWM.contains("5")) {
									vstrDate = vstrDate + "Friday;";
								}
								if (dateWM.contains("6")) {
									vstrDate = vstrDate + "Saturday;";
								}
								if (dateWM.contains("7")) {
									vstrDate = vstrDate + "Sunday;";
								}

							} else if (esmeSchedulerSelect.getType().toString().equalsIgnoreCase("3")) {
								if (dateWM.contains("1")) {
									vstrDate = vstrDate + "1st;";
								}
								if (dateWM.contains("2")) {
									vstrDate = vstrDate + "2nd;";
								}
								if (dateWM.contains("3")) {
									vstrDate = vstrDate + "3rd;";
								}
								if (dateWM.contains("4")) {
									vstrDate = vstrDate + "4th;";
								}
								if (dateWM.contains("5")) {
									vstrDate = vstrDate + "5th;";
								}
								if (dateWM.contains("6")) {
									vstrDate = vstrDate + "6th;";
								}
								if (dateWM.contains("7")) {
									vstrDate = vstrDate + "7th;";
								}
								if (dateWM.contains("8")) {
									vstrDate = vstrDate + "8th;";
								}
								if (dateWM.contains("9")) {
									vstrDate = vstrDate + "9th;";
								}
								if (dateWM.contains("10")) {
									vstrDate = vstrDate + "10th;";
								}
								if (dateWM.contains("11")) {
									vstrDate = vstrDate + "11th;";
								}
								if (dateWM.contains("12")) {
									vstrDate = vstrDate + "12th;";
								}
								if (dateWM.contains("13")) {
									vstrDate = vstrDate + "13th;";
								}
								if (dateWM.contains("14")) {
									vstrDate = vstrDate + "14th;";
								}
								if (dateWM.contains("15")) {
									vstrDate = vstrDate + "15th;";
								}
								if (dateWM.contains("16")) {
									vstrDate = vstrDate + "16th;";
								}
								if (dateWM.contains("17")) {
									vstrDate = vstrDate + "17th;";
								}
								if (dateWM.contains("18")) {
									vstrDate = vstrDate + "18th;";
								}
								if (dateWM.contains("19")) {
									vstrDate = vstrDate + "19th;";
								}
								if (dateWM.contains("20")) {
									vstrDate = vstrDate + "20th;";
								}
								if (dateWM.contains("21")) {
									vstrDate = vstrDate + "21st;";
								}
								if (dateWM.contains("22")) {
									vstrDate = vstrDate + "22nd;";
								}
								if (dateWM.contains("23")) {
									vstrDate = vstrDate + "23rd;";
								}
								if (dateWM.contains("24")) {
									vstrDate = vstrDate + "24th;";
								}
								if (dateWM.contains("25")) {
									vstrDate = vstrDate + "25th;";
								}
								if (dateWM.contains("26")) {
									vstrDate = vstrDate + "26th;";
								}
								if (dateWM.contains("27")) {
									vstrDate = vstrDate + "27th;";
								}
								if (dateWM.contains("28")) {
									vstrDate = vstrDate + "28th;";
								}
								if (dateWM.contains("29")) {
									vstrDate = vstrDate + "29th;";
								}
								if (dateWM.contains("30")) {
									vstrDate = vstrDate + "30th;";
								}
								if (dateWM.contains("31")) {
									vstrDate = vstrDate + "31st;";
								}
							}
						}
					}
					cboType.setValue(esmeSchedulerSelect.getType());
					if (vstrDate.length() > 0) {
						vstrDate = vstrDate.substring(0, vstrDate.length() - 1);
					}
					txtdateWM.setValue(vstrDate);
					dtTime.setValue(esmeSchedulerSelect.getTime());
				} catch (com.fis.esme.schedulerdetail.Exception_Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				btnSchedule.setEnabled(false);
				btnRemove.setEnabled(true);
				cboType.setEnabled(false);
				dtTime.setEnabled(false);
			} else {
				cboType.setValue(Directly);
				btnSchedule.setEnabled(true);
				btnRemove.setEnabled(false);
				cboType.setEnabled(true);
				dtTime.setEnabled(true);
				dtTime.setValue("00:00");
			}
		} catch (com.fis.esme.scheduleraction.Exception_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onApprover() {

		List<EsmeMessageContent> list = getAllItemCheckedOnTable();
		if (list.size() <= 0) {
			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("messagescheduler.tbl.notnull"));
			return;
		}
		try {
			for (int i = 0; i < list.size(); i++) {
				EsmeMessageContent rowEsmeMessageContent = list.get(i);
				EsmeMessage rowEsmeMessage = rowEsmeMessageContent.getEsmeMessage();
				if (rowEsmeMessage.getStatus().equals("0")) {
					rowEsmeMessage.setStatus("1");
					serviceMessage.update(rowEsmeMessage);
				}
				tbl.refreshRowCache();
				MessageAlerter.showMessageI18n(getWindow(), TM.get("messagescheduler.approver.true"));
			}
		} catch (com.fis.esme.message.Exception_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public EsmeMessageContent getSelectMassageContent() {

		EsmeMessageContent reObject = null;
		Collection<EsmeMessageContent> collection = (Collection<EsmeMessageContent>) tbl.getValue();
		if (collection.size() <= 0) {
			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("messagescheduler.tbl.notnull"));
			return null;
		}
		for (EsmeMessageContent obj : collection) {
			reObject = obj;
			break;
		}
		return reObject;
	}
}
