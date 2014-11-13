package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fis.esme.admin.SessionData;
import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.CompareDateTimeValidator;
import com.fis.esme.classes.ServerSort;
import com.fis.esme.component.CommonButtonPanel;
import com.fis.esme.component.CustomTable;
import com.fis.esme.component.PagingComponent.ChangePageEvent;
import com.fis.esme.component.PagingComponent.PagingComponentListener;
import com.fis.esme.component.PanelActionProvider;
import com.fis.esme.component.TabChangeProvider;
import com.fis.esme.component.TableContainer;
import com.fis.esme.fileupload.Exception_Exception;
import com.fis.esme.fileupload.FileUploadTransferer;
import com.fis.esme.persistence.EsmeFileUpload;
import com.fis.esme.persistence.EsmeServices;
import com.fis.esme.util.Dictionary;
import com.fis.esme.util.FormUtil;
import com.fis.esme.util.LogUtil;
import com.fis.esme.util.MessageAlerter;
import com.fis.esme.util.SearchObj;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;

import eu.livotov.tpt.gui.dialogs.OptionDialog.OptionDialogResultListener;
import eu.livotov.tpt.gui.dialogs.OptionKind;
import eu.livotov.tpt.i18n.TM;

public class PanelFileUpload extends VerticalLayout implements PanelActionProvider, TabChangeProvider, PagingComponentListener, ServerSort, OptionDialogResultListener {

	private boolean isLoaded = false;
	private CustomTable tbl;
	private TableContainer container;
	private BeanItemContainer<EsmeFileUpload> data;
	private FormFileUploadDetail parent;
	private List<EsmeServices> childNodes = new ArrayList<EsmeServices>();
	private FileUploadTransferer fileUploadService;

	private boolean isLoadedPnlAction = false;
	private CommonButtonPanel pnlAction;

	private final String DEFAULT_SORTED_COLUMN = "name";
	private boolean DEFAULT_SORTED_ASC = true;
	private boolean DEFAULT_EXACT_MATCH = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;
	private EsmeFileUpload skSearch = null;

	private PopupDateField effDate;
	private PopupDateField expDate;
	private Button btnSearch;
	private FormLayout frm = new FormLayout();
	private FormLayout frm1 = new FormLayout();
	private FormLayout frm2 = new FormLayout();
	private HorizontalLayout sarchLayout = new HorizontalLayout();
	private Panel plLayout = new Panel();

	public PanelFileUpload(String title, FormFileUploadDetail parent) {

		this.parent = parent;
		this.setCaption(title);
		this.setSizeFull();
		// initLayout();
	}

	public PanelFileUpload(FormFileUploadDetail parent) {

		this(TM.get(FormFileUploadDetail.class.getName()), parent);
		LogUtil.logAccess(PanelFileUpload.class.getName());
	}

	private void initLayout() {

		initService();
		initComponent();
		initDateField();
		plLayout.setContent(sarchLayout);
		this.setSizeFull();
		this.addComponent(plLayout);
		this.addComponent(container);
		this.setExpandRatio(container, 1.0f);
	}

	private void initDateField() {

		btnSearch = new Button(TM.get("fileUpload.button.search.caption"));
		btnSearch.setWidth("100px");
		btnSearch.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				Date dtFromDate = (Date) effDate.getValue();
				Date dtToDate = (Date) expDate.getValue();
				if (dtFromDate != null && dtToDate != null) {

					try {
						data.removeAllItems();
						data.addAll(CacheServiceClient.fileUploadService.findAllByDate(FormUtil.toDate(dtFromDate, FormUtil.notSetDateFields),
						        FormUtil.toDate(dtToDate, new Dictionary[] { new Dictionary(Calendar.HOUR_OF_DAY, 23), new Dictionary(Calendar.MINUTE, 59), new Dictionary(Calendar.SECOND, 59) })));
					} catch (Exception_Exception e) {

						e.printStackTrace();
					}
				} else {
					if (dtFromDate == null) {
						MessageAlerter.showErrorMessage(getWindow(), TM.get("common.field.msg.validator_nulloremty", effDate.getCaption()));
					} else if (dtToDate == null) {
						MessageAlerter.showErrorMessage(getWindow(), TM.get("common.field.msg.validator_nulloremty", expDate.getCaption()));
					} else if (expDate.isValid()) {
						MessageAlerter.showErrorMessage(getWindow(), TM.get("frmPromotionSchedule.setParseErrorMessage", expDate.getDateFormat().toUpperCase()));
					}
				}

			}
		});

		effDate = new PopupDateField("From date");
		effDate.addStyleName("mca-subscriber-readonly");
		effDate.setWidth("180px");
		effDate.setDateFormat(FormUtil.stringShortDateFormat);
		effDate.setResolution(PopupDateField.RESOLUTION_DAY);
		effDate.setLenient(true);
		effDate.setRequired(true);
		effDate.setRequiredError(TM.get("common.field.msg.validator_nulloremty", effDate.getCaption()));
		effDate.setParseErrorMessage(TM.get("frmPromotionSchedule.setParseErrorMessage", effDate.getDateFormat().toUpperCase()));

		expDate = new PopupDateField("To date");
		expDate.addStyleName("mca-subscriber-readonly");
		expDate.setWidth("180px");
		expDate.setRequired(true);
		expDate.setRequiredError(TM.get("common.field.msg.validator_nulloremty", expDate.getCaption()));
		expDate.setDateFormat(FormUtil.stringShortDateFormat);
		expDate.setResolution(PopupDateField.RESOLUTION_DAY);
		expDate.setLenient(true);
		expDate.setParseErrorMessage(TM.get("frmPromotionSchedule.setParseErrorMessage", expDate.getDateFormat().toUpperCase()));
		CompareDateTimeValidator date = new CompareDateTimeValidator(TM.get("frmPromotionSchedule.compare.date", expDate.getCaption(), effDate.getCaption()), effDate, 2);
		expDate.addValidator(date);

		frm.addComponent(effDate);
		frm1.addComponent(expDate);
		frm2.addComponent(btnSearch);
		sarchLayout.addComponent(frm);
		sarchLayout.addComponent(frm1);
		sarchLayout.addComponent(frm2);
		sarchLayout.setSpacing(true);
		sarchLayout.setMargin(false, false, false, true);

	}

	private void initService() {

		try {
			fileUploadService = CacheServiceClient.fileUploadService;
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void initComponent() {

		data = new BeanItemContainer<EsmeFileUpload>(EsmeFileUpload.class);
		initTable();
	}

	@SuppressWarnings("serial")
	private void initTable() {

		tbl = new CustomTable("", data, null) {

			@Override
			protected String formatPropertyValue(Object rowId, Object colId, Property property) {

				String pid = (String) colId;
				// if ("status".equals(pid)) {
				// if ((property.getValue().equals("1"))) {
				// return TM.get("smscRouting.strActive");
				// } else {
				// return TM.get("smscRouting.strInactive");
				// }
				// }
				if ("createDate".equals(pid)) {
					if (property.getValue() != null)
						return FormUtil.simpleDateFormat.format(property.getValue());
				}
				return super.formatPropertyValue(rowId, colId, property);
			}
		};
		tbl.setMultiSelect(true);
		tbl.setImmediate(true);
		tbl.setVisibleColumns(TM.get("fileUpload.setVisibleColumns").split(","));
		tbl.setColumnHeaders(TM.get("fileUpload.setColumnHeaders").split(","));
		tbl.setColumnReorderingAllowed(true);
		tbl.setColumnCollapsingAllowed(true);
		tbl.setColumnCollapsed("priority", true);
		tbl.setColumnCollapsed("prcAction", true);
		tbl.setStyleName("commont_table_noborderLR");
		String[] properties = TM.get("fileUpload.setVisibleColumns").split(",");
		String[] propertiesValues = TM.get("fileUpload.propertiesValue").split(",");
		for (int i = 0; i < propertiesValues.length; i++) {
			int width = -1;
			try {
				width = Integer.parseInt(propertiesValues[i]);
			} catch (Exception e) {}
			tbl.setColumnWidth(properties[i], width);
		}

		container = new TableContainer(tbl, this, Integer.parseInt(TM.get("pager.page.rowsinpage"))) {};
		container.initPager(fileUploadService.count(null, DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(false);
		container.setVidibleButtonAddNew(false);
		container.setVidibleButtonAddCopy(false);
		container.removeHeaderSearchLayout();
		container.setVisibleBorderMainLayout(false);
		container.setFilteredColumns(TM.get("fileUpload.table.filteredcolumns").split(","));

	}

	@Override
	public String getPermission() {

		// return AppClient.getPermission(this.getClass().getName());
		// return "USDI";
		return SessionData.getAppClient().getPermission(this.getClass().getName());
	}

	public void delete(Object object) {

	}

	@Override
	public void filterTree(Object obj) {

	}

	@Override
	public void treeValueChanged(Object obj) {

		childNodes.clear();
		if (obj instanceof EsmeServices && !parent.isTreeNodeRoot(obj)) {

			Object parentNode = parent.getParentTreeNode(obj);
			Collection<?> collection = parent.getChildrenTreeNode(parentNode);
			if (collection != null) {
				childNodes.addAll((Collection<? extends EsmeServices>) collection);
			}
		}
		loadDataFromDatabase(obj);
	}

	private void initPagerForTable(EsmeFileUpload skSearch) {

		int count = fileUploadService.count(skSearch, DEFAULT_EXACT_MATCH);
		container.initPager(count);
		if (count <= 0) {
			MessageAlerter.showMessageI18n(getWindow(), TM.get("msg.search.value.emty"));
		}
	}

	private void loadDataFromDatabase(Object obj) {

		skSearch = new EsmeFileUpload();
		try {
			if (obj != null && (obj instanceof EsmeServices) && !parent.isTreeNodeRoot(obj)) {
				data.removeAllItems();
				skSearch = new EsmeFileUpload();
				skSearch.setEsmeServices((EsmeServices) obj);
				initPagerForTable(skSearch);
				// data.removeAllItems();
				// data.addAll(CacheServiceClient.fileUploadService
				// .findAllWithOrderPaging(skSearch, "", false, 0, 100,
				// false));

			} else if (parent.isTreeNodeRoot(obj)) {
				skSearch = new EsmeFileUpload();
				data.removeAllItems();
				// data.addAll(fileUploadService.findAllWithoutParameter());
				initPagerForTable(skSearch);
			}
		} catch (Exception e) {
			FormUtil.showException(getWindow(), e);
			e.printStackTrace();
		}
	}

	@Override
	public void dialogClosed(OptionKind option) {

	}

	public TableContainer getContainer() {

		return container;
	}

	@Override
	public void loadButtonPanel() {

		if (!isLoadedPnlAction) {
			pnlAction = new CommonButtonPanel(this);
			pnlAction.showSearchPanel(true);
			pnlAction.setFromCaption(TM.get(FormFileUploadDetail.class.getName()));
			// pnlAction.setValueForCboField(
			// TM.get("actionparam.table.filteredcolumns").split(","), TM
			// .get("actionparam.table.filteredcolumnscaption")
			// .split(","));
			pnlAction.setValueForCboField(TM.get("fileUpload.table.filteredcolumns").split(","), TM.get("fileUpload.table.filteredcolumnscaption").split(","));
			parent.addButtonPanel(pnlAction);
			isLoadedPnlAction = true;
		} else {
			parent.addButtonPanel(this.pnlAction);
		}

	}

	@Override
	public void loadForm() {

		if (!isLoaded) {
			initLayout();
			isLoaded = true;
		}
	}

	@Override
	public void export() {

	}

	@Override
	public void searchOrAddNew(String key) {

	}

	@Override
	public void search() {

	}

	@Override
	public void fieldSearch(SearchObj searchObj) {

		if (searchObj.getKey() == null)
			return;

		skSearch = new EsmeFileUpload();
		if (searchObj.getField() == null) {
			skSearch.setFileName(searchObj.getKey());
		} else {
			if (searchObj.getField().equals("fileName"))
				skSearch.setFileName(searchObj.getKey());
			else if (searchObj.getField().equals("totalRecord") && searchObj.getKey().matches("\\d+"))
				skSearch.setTotalRecord(Long.parseLong(searchObj.getKey()));
			else if (searchObj.getField().equals("totalSucess") && searchObj.getKey().matches("\\d+"))
				skSearch.setTotalSucess(Long.parseLong(searchObj.getKey()));
			else if (searchObj.getField().equals("totalFail") && searchObj.getKey().matches("\\d+"))
				skSearch.setTotalFail(Long.parseLong(searchObj.getKey()));

		}

		int count = fileUploadService.count(skSearch, DEFAULT_EXACT_MATCH);
		if (count > 0) {
			container.initPager(count);
		} else {
			MessageAlerter.showMessageI18n(getWindow(), TM.get("msg.search.value.emty"));
		}

		pnlAction.clearAction();
	}

	@Override
	public void requestSort(String cloumn, boolean asc) {

		sortedColumn = cloumn;
		sortedASC = asc;
		int items = container.getItemPerPage();
		displayData(cloumn, asc, 0, items);
		container.changePage(1);
	}

	private void displayData(String sortedColumn, boolean asc, int start, int items) {

		try {
			data.removeAllItems();

			data.addAll(fileUploadService.findAllWithOrderPaging(skSearch, sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH));
			// try {
			// Date dtCurrent = Calendar.getInstance().getTime();
			// data.removeAllItems();
			// data.addAll(CacheServiceClient.fileUploadService.findAllByDate(
			// FormUtil.toDate(dtCurrent, FormUtil.notSetDateFields),
			// FormUtil.toDate(dtCurrent, new Dictionary[] {
			// new Dictionary(Calendar.HOUR_OF_DAY, 23),
			// new Dictionary(Calendar.MINUTE, 59),
			// new Dictionary(Calendar.SECOND, 59) })));
			// } catch (Exception_Exception e) {
			//
			// e.printStackTrace();
			// }
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
		displayData(sortedColumn, sortedASC, start, event.getPageRange().getNumberOfRowsPerPage());
	}

	@Override
	public void accept() {

	}

	@Override
	public void showDialog(Object object) {

	}
}
