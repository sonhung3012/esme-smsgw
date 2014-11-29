package com.fis.esme.form.lookup;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.vaadin.jonatan.contexthelp.ContextHelp;

import com.fis.esme.admin.SessionData;
import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.ServerSort;
import com.fis.esme.component.CustomTable;
import com.fis.esme.component.PagingComponent.ChangePageEvent;
import com.fis.esme.component.PagingComponent.PagingComponentListener;
import com.fis.esme.component.PanelActionProvider;
import com.fis.esme.component.TableContainer;
import com.fis.esme.persistence.EsmeSmsLog;
import com.fis.esme.persistence.SubSearchDetail;
import com.fis.esme.smslog.EsmeSmsLogTransferer;
import com.fis.esme.util.Dictionary;
import com.fis.esme.util.FileDownloadResource;
import com.fis.esme.util.FormUtil;
import com.fis.esme.util.MessageAlerter;
import com.fis.esme.util.ReportUtil;
import com.fis.esme.util.SearchObj;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import eu.livotov.tpt.gui.dialogs.OptionDialog.OptionDialogResultListener;
import eu.livotov.tpt.gui.dialogs.OptionKind;
import eu.livotov.tpt.i18n.TM;

public class LookUpSMS extends VerticalLayout implements PanelActionProvider, PagingComponentListener, ServerSort, Action.Handler, OptionDialogResultListener {

	private static final long serialVersionUID = 1L;
	// private HorizontalLayout pnSearch;
	private HorizontalLayout pnSearch;
	// private CommonButtonPanel pnlAction;
	private Panel formPanel = new Panel();
	// private com.fis.esme.form.lookup.SearchForm form;
	public ContextHelp contextHelp;
	private CustomTable tbl;
	private BeanItemContainer<EsmeSmsLog> data;
	private TableContainer container;
	private EsmeSmsLogTransferer serviceSearch;
	private Button btnSearch = new Button(TM.get("smslog.button.search.caption"));
	private Button btnReport = new Button(TM.get("smslog.button.export.caption"));

	// private CheckBox ckbPakageStatus = new CheckBox(
	// TM.get("subc.search.packagestatus.caption"));

	private com.fis.esme.form.lookup.SearchForm searchForm;
	private com.fis.esme.form.lookup.SearchFormFieldFactory searchFactory;
	private ObjectSearch searcher;

	private final String DEFAULT_SORTED_COLUMN = "REQUEST_TIME";
	private boolean DEFAULT_SORTED_ASC = false;
	private boolean DEFAULT_EXACT_MATCH = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;

	private Date dtCurrent;
	private Date dtBefor;
	private Calendar ca = Calendar.getInstance();
	private SubSearchDetail searchDetail = new SubSearchDetail();

	public LookUpSMS(String title, String pemission, String key) throws Exception {

		this(title, pemission);

	}

	public LookUpSMS(String title, String pemission) throws Exception {

		this.setCaption(title);
		// LogUtil.logAccess(LookUpSMS.class.getName());
		initDates();
		initLayout();
	}

	public LookUpSMS() throws Exception {

		this(TM.get(LookUpSMS.class.getName()), null, null);
	}

	private void initService() {

		try {
			serviceSearch = CacheServiceClient.smsLogServices;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initLayout() throws Exception {

		// pnlAction = new CommonButtonPanel(this);
		// pnlAction.showSearchPanel(false);
		// pnlAction.setFromCaption(TM.get(LookUpSMS.class.getName()));
		// pnlAction.setMargin(false, false, true, false);
		//
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
		// formPanel.addAction(new Button.ClickShortcut(btnSearch,
		// KeyCode.ENTER));

		// HorizontalLayout packageLayout = new HorizontalLayout();
		// packageLayout.setSizeFull();

		// container.addHeaderButtonLayout(packageLayout);
		this.setSizeFull();
		this.setSpacing(true);
		formPanel.setHeight("150px");
		this.addComponent(formPanel);
		this.setComponentAlignment(formPanel, Alignment.MIDDLE_CENTER);
		this.addComponent(container);
		this.setExpandRatio(container, 1f);

	}

	protected ObjectSearch createSearchObject() {

		ObjectSearch obj = new ObjectSearch();
		obj.setTfMsisdn(SessionData.getCurrentMSISDN());
		obj.setFromDate(null);
		obj.setToDate(null);
		obj.setCbbShortCode(null);
		obj.setCbbService(null);
		obj.setCbbCommand(null);
		obj.setCbbCp(null);
		obj.setCbbType(null);
		obj.setCbbStatus(null);

		return obj;
	}

	private void initSearchPn() throws Exception {

		pnSearch = new HorizontalLayout();
		pnSearch.setMargin(false);
		pnSearch.setSpacing(true);

		searchForm = new com.fis.esme.form.lookup.SearchForm();
		searchForm.setImmediate(false);
		searchForm.setInvalidCommitted(false);
		searchForm.setWriteThrough(false);

		searcher = createSearchObject();
		searcher.setFromDate(ca.getTime());
		searcher.setToDate(ca.getTime());
		searchFactory = new SearchFormFieldFactory() {

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
		searchForm.setItemDataSource(new BeanItem<ObjectSearch>(searcher));

		// button
		btnSearch = new Button(TM.get("smslog.button.search.caption"));
		btnSearch.setWidth("100px");
		// FormLayout frm = new FormLayout();
		// frm.addComponent(btnSearch);
		// frm.setSizeFull();
		searchForm.setSearchButton(btnSearch);
		pnSearch.addComponent(searchForm);

		btnReport = new Button(TM.get("smslog.button.export.caption"));
		btnReport.setWidth("100px");
		searchForm.setSearchButtonReport(btnReport);

		// pnSearch.addComponent(frm);
		pnSearch.setComponentAlignment(searchForm, Alignment.MIDDLE_CENTER);
		// pnSearch.setComponentAlignment(btnSearch, Alignment.MIDDLE_CENTER);

		btnSearch.addListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				new EsmeSmsLog();
				search();
			}
		});
		btnSearch.setClickShortcut(KeyCode.ENTER);

		btnReport.addListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				export();
			}

		});

	}

	private void initComponent() throws Exception {

		initService();
		initSearchPn();
		initTable();

	}

	@SuppressWarnings("serial")
	private void initTable() {

		data = new BeanItemContainer<EsmeSmsLog>(EsmeSmsLog.class);
		tbl = new CustomTable("", data) {

			@Override
			public Collection<?> getSortableContainerPropertyIds() {

				ArrayList<Object> arr = new ArrayList<Object>();
				Object[] sortCol = TM.get("smslog.search.table.setsortcolumns").split(",");
				for (Object obj : sortCol) {
					arr.add(obj);
				}
				return arr;
			}

			@Override
			protected String formatPropertyValue(Object rowId, Object colId, Property property) {

				String pid = (String) colId;
				Object val = property.getValue();
				EsmeSmsLog content = (EsmeSmsLog) rowId;
				if (val == null) {
					return super.formatPropertyValue(rowId, colId, property);
				}
				// System.out.println("pp " + property.getValue() +" pid "+pid +
				// " check "+"requestTime".equals(pid));
				if ("requestTime".equals(pid)) {
					return FormUtil.simpleDateFormat.format((Date) property.getValue());
				}

				if ("type".equals(pid)) {
					if (content.getType() != null) {
						if (content.getType().equals("1")) {
							return TM.get("smslog.form.type_1");
						} else if (content.getType().equals("2")) {
							return TM.get("smslog.form.type_2");
						}
					} else {
						return "";
					}
				}

				if ("esmeSmsc".equals(pid)) {
					if (content.getEsmeSmsc() != null) {
						return content.getEsmeSmsc().getName();
					} else {
						return "";
					}
				}

				if ("esmeFileUpload".equals(pid)) {
					if (content.getEsmeFileUpload() != null) {
						return content.getEsmeFileUpload().getFileName();
					} else {
						return "";
					}
				}

				if ("status".equals(pid)) {
					// data.get
					if (content.getStatus() != null && content.getType() != null) {

						if (content.getType().equals("1")) {

							if (content.getStatus().equals("1")) {
								return TM.get("smslog.mo.status_1");
							} else if (content.getStatus().equals("0")) {
								return TM.get("smslog.mo.status_0");
							} else if (content.getStatus().equals("2")) {
								return TM.get("smslog.mo.status_2");
							} else if (content.getStatus().equals("3")) {
								return TM.get("smslog.mo.status_3");
							} else if (content.getStatus().equals("6")) {
								return TM.get("smslog.mo.status_6");
							} else {
								return "";
							}

						} else if (content.getType().equals("2")) {

							if (content.getStatus().equals("0")) {
								return TM.get("smslog.mt.status_0");
							} else if (content.getStatus().equals("1")) {
								return TM.get("smslog.mt.status_1");
							} else if (content.getStatus().equals("2")) {
								return TM.get("smslog.mt.status_2");
							} else if (content.getStatus().equals("4")) {
								return TM.get("smslog.mt.status_4");
							} else if (content.getStatus().equals("5")) {
								return TM.get("smslog.mt.status_5");
							} else if (content.getStatus().equals("6")) {
								return TM.get("smslog.mt.status_6");
							} else if (content.getStatus().equals("7")) {
								return TM.get("smslog.mt.status_7");
							} else if (content.getStatus().equals("8")) {
								return TM.get("smslog.mt.status_8");
							} else {
								return "";
							}
						}
					}
				}

				return super.formatPropertyValue(rowId, colId, property);
			}

			@Override
			public String getColumnAlignment(Object propertyId) {

				if (propertyId.equals("requestTime") || propertyId.equals("msisdn")) {
					return Table.ALIGN_RIGHT;
				} /*
				 * else if (propertyId.equals("parentId") || propertyId.equals("roleId")) { return Table.ALIGN_LEFT; } else if (propertyId.equals("select")) { return Table.ALIGN_CENTER; }
				 */
				return super.getColumnAlignment(propertyId);
			}

		};

		tbl.addActionHandler(this);
		tbl.setMultiSelect(true);
		tbl.setImmediate(true);
		tbl.setStyleName("commont_table_noborderLR");

		tbl.setVisibleColumns(TM.get("smslog.search.table.setvisiblecolumns").split(","));
		tbl.setColumnHeaders(TM.get("smslog.search.table.setcolumnheaders").split(","));
		String[] columnWidth = TM.get("smslog.search.table.columnwidth").split(",");
		String[] columnWidthValue = TM.get("smslog.search.table.columnwidth_value").split(",");
		for (int i = 0; i < columnWidth.length; i++) {
			tbl.setColumnWidth(columnWidth[i], Integer.parseInt(columnWidthValue[i]));
		}

		container = new TableContainer(tbl, this, Integer.parseInt(TM.get("pager.page.rowsinpage"))) {};

		searchDetail.setFromDate(dtBefor);
		searchDetail.setToDate(dtCurrent);

		int count = 0;
		try {
			count = serviceSearch.count(searchDetail, null, DEFAULT_EXACT_MATCH);
			container.initPager(count);
		} catch (Exception e) {

			e.printStackTrace();
		}

		container.removeHeaderSearchLayout();
		container.removeDeleteAllLayout();
		container.setFilteredColumns(TM.get("smslog.search.table.filteredcolumns").split(","));
		// container.removeLayoutOnlySMS();

	}

	private void initDates() {

		dtCurrent = ca.getTime();
		dtBefor = ca.getTime();
		dtBefor = FormUtil.toDate(dtBefor, new Dictionary[] { new Dictionary(Calendar.HOUR_OF_DAY, 0), new Dictionary(Calendar.MINUTE, 0), new Dictionary(Calendar.SECOND, 0) });
		searchDetail.setFromDate(dtBefor);
		searchDetail.setToDate(dtCurrent);
	}

	// public void initForm() throws Exception {
	//
	// form = new com.fis.esme.form.lookup.SearchForm();
	// form.setWriteThrough(false);
	// form.setInvalidCommitted(false);
	// form.setImmediate(false);
	// onCancel();
	// }

	// public void fillForm(Item item) {
	//
	// form.setItemDataSource(item);
	// form.setVisibleItemProperties(TM.get("smslog.search.form.visibleproperties").split(","));
	// form.setValidationVisible(false);
	// form.focus();
	// }

	// private void onCancel() {
	//
	// ObjectSearch searchEntity = new ObjectSearch();
	// // searchEntity.setMsisdn(TM.get("common.msisdn.startswith"));
	// Item item = new BeanItem<ObjectSearch>(searchEntity);
	// fillForm(item);
	// }

	@Override
	public void dialogClosed(OptionKind arg0) {

	}

	@Override
	public Action[] getActions(Object target, Object sender) {

		return null;
	}

	@Override
	public void handleAction(Action action, Object sender, Object target) {

	}

	@Override
	public void requestSort(String cloumn, boolean asc) {

	}

	@Override
	public void displayPage(ChangePageEvent event) {

		int start = event.getPageRange().getIndexPageStart();
		displayData(sortedColumn, sortedASC, start, event.getPageRange().getNumberOfRowsPerPage());
	}

	private void displayData(String sortedColumn, boolean asc, int start, int items) {

		try {

			data.removeAllItems();
			// searchDetail.setFromDate(dtBefor);
			// searchDetail.setToDate(dtCurrent);
			data.addAll(serviceSearch.findAll(searchDetail, null, DEFAULT_SORTED_COLUMN, DEFAULT_SORTED_ASC, start, items, true));

			if (container != null)
				container.setLblCount(start);
			// tbl.sort(new Object [] {"sentDatetime"}, new boolean [] {true});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void search() {

		// contextHelp.hideHelp();
		searchForm.setValidationVisible(false);
		if (searchForm.isValid()) {
			searchForm.commit();
			try {

				Date fromDate = FormUtil.toDate(searcher.getFromDate(), new com.fis.esme.util.Dictionary[] { new com.fis.esme.util.Dictionary(Calendar.HOUR_OF_DAY, 00),
				        new com.fis.esme.util.Dictionary(Calendar.MINUTE, 00), new com.fis.esme.util.Dictionary(Calendar.SECOND, 00) });

				Date toDate = FormUtil.toDate(searcher.getToDate(), new com.fis.esme.util.Dictionary[] { new com.fis.esme.util.Dictionary(Calendar.HOUR_OF_DAY, 23),
				        new com.fis.esme.util.Dictionary(Calendar.MINUTE, 59), new com.fis.esme.util.Dictionary(Calendar.SECOND, 59) });
				searchDetail.setFromDate(fromDate);
				searchDetail.setToDate(toDate);
				if (searcher.getTfMsisdn() != null && !searcher.getTfMsisdn().equalsIgnoreCase("")) {

					searchDetail.setMsisdn(searcher.getTfMsisdn());
				} else {
					searchDetail.setMsisdn(null);
				}
				if (searcher.getCbbCommand() != null && !searcher.getCbbCommand().equalsIgnoreCase("")) {

					searchDetail.setCommandId(Long.parseLong(searcher.getCbbCommand()));
				} else {
					searchDetail.setCommandId(null);
				}
				if (searcher.getCbbService() != null && !searcher.getCbbService().equalsIgnoreCase("")) {

					searchDetail.setServiceId(Long.parseLong(searcher.getCbbService()));
				} else {
					searchDetail.setServiceId(null);
				}
				if (searcher.getCbbShortCode() != null && !searcher.getCbbShortCode().equalsIgnoreCase("")) {

					searchDetail.setShortcodeId(Long.parseLong(searcher.getCbbShortCode()));
				} else {
					searchDetail.setShortcodeId(null);
				}

				if (searcher.getCbbCp() != null && !searcher.getCbbCp().equalsIgnoreCase("")) {

					searchDetail.setCpId(Long.parseLong(searcher.getCbbCp()));
				} else {
					searchDetail.setCpId(null);
				}
				if (searcher.getCbbType() != null && !searcher.getCbbType().equalsIgnoreCase("")) {

					searchDetail.setType(searcher.getCbbType());
				} else {
					searchDetail.setType(null);
				}

				if (searcher.getCbbStatus() != null && !searcher.getCbbStatus().equalsIgnoreCase("")) {

					searchDetail.setStatus(searcher.getCbbStatus());
				} else {
					searchDetail.setStatus(null);
				}

				int count = 0;
				count = serviceSearch.count(searchDetail, null, DEFAULT_EXACT_MATCH);
				container.initPager(count);

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

	}

	@Override
	public void export() {

		searchForm.setValidationVisible(false);

		if (searchForm.isValid()) {
			searchForm.commit();
			try {

				Date fromDate = FormUtil.toDate(searcher.getFromDate(), new com.fis.esme.util.Dictionary[] { new com.fis.esme.util.Dictionary(Calendar.HOUR_OF_DAY, 00),
				        new com.fis.esme.util.Dictionary(Calendar.MINUTE, 00), new com.fis.esme.util.Dictionary(Calendar.SECOND, 00) });

				Date toDate = FormUtil.toDate(searcher.getToDate(), new com.fis.esme.util.Dictionary[] { new com.fis.esme.util.Dictionary(Calendar.HOUR_OF_DAY, 23),
				        new com.fis.esme.util.Dictionary(Calendar.MINUTE, 59), new com.fis.esme.util.Dictionary(Calendar.SECOND, 59) });
				searchDetail.setFromDate(fromDate);
				searchDetail.setToDate(toDate);
				if (searcher.getTfMsisdn() != null && !searcher.getTfMsisdn().equalsIgnoreCase("")) {

					searchDetail.setMsisdn(searcher.getTfMsisdn());
				} else {
					searchDetail.setMsisdn(null);
				}
				if (searcher.getCbbCommand() != null && !searcher.getCbbCommand().equalsIgnoreCase("")) {

					searchDetail.setCommandId(Long.parseLong(searcher.getCbbCommand()));
				} else {
					searchDetail.setCommandId(null);
				}
				if (searcher.getCbbService() != null && !searcher.getCbbService().equalsIgnoreCase("")) {

					searchDetail.setServiceId(Long.parseLong(searcher.getCbbService()));
				} else {
					searchDetail.setServiceId(null);
				}
				if (searcher.getCbbShortCode() != null && !searcher.getCbbShortCode().equalsIgnoreCase("")) {

					searchDetail.setShortcodeId(Long.parseLong(searcher.getCbbShortCode()));
				} else {
					searchDetail.setShortcodeId(null);
				}

				if (searcher.getCbbCp() != null && !searcher.getCbbCp().equalsIgnoreCase("")) {

					searchDetail.setCpId(Long.parseLong(searcher.getCbbCp()));
				} else {
					searchDetail.setCpId(null);
				}
				if (searcher.getCbbType() != null && !searcher.getCbbType().equalsIgnoreCase("")) {

					searchDetail.setType(searcher.getCbbType());
				} else {
					searchDetail.setType(null);
				}

				if (searcher.getCbbStatus() != null && !searcher.getCbbStatus().equalsIgnoreCase("")) {

					searchDetail.setStatus(searcher.getCbbStatus());
				} else {
					searchDetail.setStatus(null);
				}

				String[] columns = new String[] { "STT", "requestTime", "msisdn", "status", "type", "esmeServices", "esmeCp", "esmeShortCode", "esmeSmsCommand", "smsContent" };

				List<EsmeSmsLog> list = serviceSearch.findAll(searchDetail, null, DEFAULT_SORTED_COLUMN, DEFAULT_SORTED_ASC, 0, 1000000000, true);

				Vector<Vector<Object>> vData = new Vector<Vector<Object>>();
				Vector<Object> obj = null;
				int stt = 1;
				for (EsmeSmsLog pamLog : list) {
					obj = new Vector<Object>();
					obj.add(stt);

					obj.add((pamLog.getRequestTime() != null) ? FormUtil.simpleDateFormat.format(pamLog.getRequestTime()) : "");
					obj.add((pamLog.getMsisdn() != null) ? pamLog.getMsisdn() : "");
					// obj.add((pamLog.getStatus() != null) ? pamLog.getStatus() : "");
					// obj.add((pamLog.getType() != null) ? pamLog.getType() : "");

					if (pamLog.getStatus() != null && pamLog.getType() != null) {

						if (pamLog.getType().equals("1")) {

							if (pamLog.getStatus().equals("1")) {
								obj.add(TM.get("smslog.mo.status_1"));
							} else if (pamLog.getStatus().equals("0")) {
								obj.add(TM.get("smslog.mo.status_0"));
							} else if (pamLog.getStatus().equals("2")) {
								obj.add(TM.get("smslog.mo.status_2"));
							} else if (pamLog.getStatus().equals("3")) {
								obj.add(TM.get("smslog.mo.status_3"));
							} else if (pamLog.getStatus().equals("6")) {
								obj.add(TM.get("smslog.mo.status_6"));
							} else {
								obj.add("");
							}

						} else if (pamLog.getType().equals("2")) {

							if (pamLog.getStatus().equals("0")) {
								obj.add(TM.get("smslog.mt.status_0"));
							} else if (pamLog.getStatus().equals("1")) {
								obj.add(TM.get("smslog.mt.status_1"));
							} else if (pamLog.getStatus().equals("2")) {
								obj.add(TM.get("smslog.mt.status_2"));
							} else if (pamLog.getStatus().equals("4")) {
								obj.add(TM.get("smslog.mt.status_4"));
							} else if (pamLog.getStatus().equals("5")) {
								obj.add(TM.get("smslog.mt.status_5"));
							} else if (pamLog.getStatus().equals("6")) {
								obj.add(TM.get("smslog.mt.status_6"));
							} else if (pamLog.getStatus().equals("7")) {
								obj.add(TM.get("smslog.mt.status_7"));
							} else if (pamLog.getStatus().equals("8")) {
								obj.add(TM.get("smslog.mt.status_8"));
							} else {
								obj.add("");
							}
						}
					}

					if (pamLog.getType() != null) {

						if (pamLog.getType().equals("1")) {

							obj.add(TM.get("smslog.form.type_1"));
						} else if (pamLog.getType().equals("2")) {

							obj.add(TM.get("smslog.form.type_2"));
						}
					} else {
						obj.add("");
					}

					if (pamLog.getEsmeServices() != null) {
						obj.add((pamLog.getEsmeServices().getName() != null) ? pamLog.getEsmeServices().getName() : "");
					} else {
						obj.add("");
					}

					if (pamLog.getEsmeCp() != null) {
						obj.add((pamLog.getEsmeCp().getCode() != null) ? pamLog.getEsmeCp().getCode() : "");
					} else {
						obj.add("");
					}

					if (pamLog.getEsmeShortCode() != null) {
						obj.add((pamLog.getEsmeShortCode().getCode() != null) ? pamLog.getEsmeShortCode().getCode() : "");
					} else {
						obj.add("");
					}

					if (pamLog.getEsmeSmsCommand() != null) {
						obj.add((pamLog.getEsmeSmsCommand().getName() != null) ? pamLog.getEsmeSmsCommand().getName() : "");
					} else {
						obj.add("");
					}
					obj.add((pamLog.getSmsContent() != null) ? pamLog.getSmsContent() : "");

					vData.add(obj);
					stt++;
				}

				String absolutePath = getApplication().getContext().getBaseDirectory().getAbsolutePath();

				ArrayList<String[]> parameters = new ArrayList<String[]>();
				Calendar calr = Calendar.getInstance();
				parameters.add(new String[] { "$Report_FromDate", FormUtil.simpleShortDateFormat.format(searcher.getFromDate()) });
				parameters.add(new String[] { "$Report_ToDate", FormUtil.simpleShortDateFormat.format(searcher.getToDate()) });
				parameters.add(new String[] { "$ExDay", calr.get(Calendar.DATE) + "" });
				parameters.add(new String[] { "$ExMonth", (calr.get(Calendar.MONTH) + 1) + "" });
				parameters.add(new String[] { "$ExYear", calr.get(Calendar.YEAR) + "" });
				parameters.add(new String[] { "$ReportUser", SessionData.getUserName() });
				parameters.add(new String[] { "$Size", String.valueOf(list.size()) });

				Object rs = ReportUtil.doExportData(absolutePath, "LOG", null, parameters, vData, columns, FormUtil.stringShortDateFormat);

				if (rs instanceof String) {
					getApplication().getMainWindow().open(new FileDownloadResource(new File(rs.toString()), getApplication()));
					MessageAlerter.showMessageI18n(getApplication().getMainWindow(), TM.get("common.report.msg.export.success"));
				} else {
					if ((Integer) rs == 0) {
						MessageAlerter.showErrorMessageI18n(getApplication().getMainWindow(), TM.get("common.report.msg.export.emty"));
					} else if ((Integer) rs == -1) {
						MessageAlerter.showErrorMessageI18n(getApplication().getMainWindow(), TM.get("common.report.msg.export.fail"));
					}
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
	public String getPermission() {

		return null;
	}

	@Override
	public void accept() {

	}

	@Override
	public void delete(Object object) {

	}

	@Override
	public void showDialog(Object object) {

	}

	@Override
	public void searchOrAddNew(String key) {

	}

}
