package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import com.fis.esme.component.TabChangeProvider;
import com.fis.esme.component.TableContainer;
import com.fis.esme.persistence.EsmeIsdnPrefix;
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.persistence.EsmeSmscRouting;
import com.fis.esme.persistence.SearchEntity;
import com.fis.esme.smsc.SmscTransferer;
import com.fis.esme.smscrouting.SmscRoutingTransferer;
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
import com.vaadin.event.ItemClickEvent;
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

public class PanelSmscRouting extends VerticalLayout implements PanelActionProvider, TabChangeProvider, PagingComponentListener, ServerSort, OptionDialogResultListener {

	private CommonDialog dialog;
	private boolean isLoaded = false;
	private boolean isLoadedPnlAction = false;
	private Form form;
	private CustomTable tbl;
	private TableContainer container;
	private BeanItemContainer<EsmeSmscRouting> data;
	private CommonButtonPanel pnlAction;
	private SmscRoutingFieldFactory fieldFactory;
	private ArrayList<EsmeSmscRouting> canDelete = new ArrayList<EsmeSmscRouting>();
	private ConfirmDeletionDialog confirm;
	private FormSmscDetail parent;
	private int total = 0;
	private List<EsmeSmsc> childNodes = new ArrayList<EsmeSmsc>();
	private SmscRoutingTransferer actionParamService;
	private EsmeSmscRouting interactionSelected = null;

	private final String DEFAULT_SORTED_COLUMN = "name";
	private boolean DEFAULT_SORTED_ASC = true;
	private boolean DEFAULT_EXACT_MATCH = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;
	private EsmeSmscRouting skSearch = null;
	private SmscTransferer actionService;

	public PanelSmscRouting(String title, FormSmscDetail parent) {

		this.parent = parent;
		this.setCaption(title);
		this.setSizeFull();
		// initLayout();
	}

	public PanelSmscRouting(FormSmscDetail formSmscDetail) {

		this(TM.get(FormSmscDetail.class.getName()), formSmscDetail);
		LogUtil.logAccess(PanelSmscRouting.class.getName());
	}

	private void initLayout() {

		initService();
		initComponent();
		this.setSizeFull();
		this.addComponent(container);
	}

	private void initService() {

		try {
			actionParamService = CacheServiceClient.smscRoutingService;
		} catch (Exception e) {

			e.printStackTrace();
		}

		try {
			actionService = CacheServiceClient.smscService;
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void initComponent() {

		data = new BeanItemContainer<EsmeSmscRouting>(EsmeSmscRouting.class);
		initFromActionParam();
		initTable();
	}

	private void initFromActionParam() {

		form = new Form();
		form.setWriteThrough(false);
		form.setInvalidCommitted(false);
		form.setImmediate(false);
		try {
			fieldFactory = new SmscRoutingFieldFactory();
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		form.setFormFieldFactory(fieldFactory);
		dialog = new CommonDialog(TM.get("smscRouting.Dialog"), form, this);
		dialog.setHeight("180px");
		dialog.setWidth("500px");
		dialog.addListener(new Window.CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {

				pnlAction.clearAction();
			}
		});

	}

	private void initComboBoxAction() {

		// Node root
		if (parent.isTreeNodeRoot(parent.getCurrentTreeNode())) {
			fieldFactory.getDataAction(null);
			// if (interactionSelected != null) {
			// PrcService service = interactionSelected.getPrcAction()
			// .getPrcService();
			// fieldFactory.getDataAction(parent.findActionsInServiceByStatus(
			// service, "1"));
			// }
		} else {
			fieldFactory.getDataAction(null);
		}
		// Node # root
		// if (!parent.isTreeNodeRoot(parent.getCurrentTreeNode())
		// && parent.getCurrentTreeNode() instanceof PrcService) {
		// fieldFactory.getDataAction(childNodes);
		// } else
		if (!parent.isTreeNodeRoot(parent.getCurrentTreeNode()) && parent.getCurrentTreeNode() instanceof EsmeSmsc) {
			fieldFactory.getDataAction(childNodes);
		}
	}

	@SuppressWarnings("serial")
	private void initTable() {

		tbl = new CustomTable("", data, pnlAction) {

			@Override
			protected String formatPropertyValue(Object rowId, Object colId, Property property) {

				String pid = (String) colId;
				if ("status".equals(pid)) {
					if ((property.getValue().equals("1"))) {
						return TM.get("smscRouting.strActive");
					} else {
						return TM.get("smscRouting.strInactive");
					}
				}
				return super.formatPropertyValue(rowId, colId, property);
			}

			@Override
			public Collection<?> getSortableContainerPropertyIds() {

				ArrayList<Object> arr = new ArrayList<Object>();
				Object[] sortCol = TM.get("smscRouting.setsortColumns").split(",");
				for (Object obj : sortCol) {

					arr.add(obj);

				}
				return arr;
			}
		};
		tbl.setMultiSelect(true);
		tbl.setImmediate(true);
		tbl.addListener(new Property.ValueChangeListener() {

			@SuppressWarnings("unused")
			public void valueChange(ValueChangeEvent event) {

				Object id = tbl.getValue();
				pnlAction.setRowSelected(id != null);
				if ((id instanceof Set) && id != null) {
					if (((Set<EsmeSmscRouting>) id).iterator().hasNext()) {
						interactionSelected = ((Set<EsmeSmscRouting>) id).iterator().next();
					}
				} else {
					interactionSelected = (EsmeSmscRouting) id;
				}
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

				final EsmeSmscRouting bean = (EsmeSmscRouting) itemId;

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
						EsmeSmscRouting bean = data.getIdByIndex(i);
						bean.setSelect(tbl.isSelectAll());
						tbl.setColumnHeader("select", (tbl.isSelectAll() == true) ? "-" : "+");
						tbl.refreshRowCache();
					}
				}
			}
		});

		tbl.setVisibleColumns(TM.get("smscRouting.setVisibleColumns").split(","));
		tbl.setColumnHeaders(TM.get("smscRouting.setColumnHeaders").split(","));
		tbl.setColumnReorderingAllowed(true);
		tbl.setColumnCollapsingAllowed(true);
		tbl.setColumnCollapsed("priority", true);
		tbl.setColumnCollapsed("prcAction", true);
		tbl.setStyleName("commont_table_noborderLR");
		String[] properties = TM.get("smscRouting.table.columnwidth").split(",");
		String[] propertiesValues = TM.get("smscRouting.table.columnwidth_value").split(",");
		for (int i = 0; i < propertiesValues.length; i++) {
			int width = -1;
			try {
				width = Integer.parseInt(propertiesValues[i]);
			} catch (Exception e) {}
			tbl.setColumnWidth(properties[i], width);
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
		SearchEntity searchEntity = new SearchEntity();
		container.initPager(actionParamService.count(searchEntity, null, DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(true);
		container.removeHeaderSearchLayout();
		container.setVisibleBorderMainLayout(false);
		container.setFilteredColumns(TM.get("smscRouting.table.filteredcolumns").split(","));
		container.setEnableDeleteAllButton(getPermission().contains("D"));
		container.setEnableButtonAddNew(getPermission().contains("I"));
		container.setEnableButtonAddCopy(getPermission().contains("I"));

	}

	public List<EsmeSmscRouting> getAllItemCheckedOnTable() {

		List<EsmeSmscRouting> list = new ArrayList<EsmeSmscRouting>();
		Collection<EsmeSmscRouting> collection = (Collection<EsmeSmscRouting>) tbl.getItemIds();
		for (EsmeSmscRouting obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

	private Window createDialog(Item item) {

		form.setItemDataSource(item);
		Object[] visibleProperties = TM.get("smscRouting.visibleProperties").split(",");
		form.setVisibleItemProperties(visibleProperties);
		form.setValidationVisible(false);
		dialog.setHeight("400px");
		getWindow().addWindow(dialog);
		return dialog;
	}

	public void showDialog(Object obj) {

		if (getWindow().getChildWindows().contains(dialog)) {
			return;
		}
		initComboBoxAction();
		int action = pnlAction.getAction();

		if (parent.isTreeNodeRoot(parent.getCurrentTreeNode())
		/* && action == PanelActionProvider.ACTION_ADD */) {
			pnlAction.clearAction();
			MessageAlerter.showMessage(getWindow(), TM.get("smscparam.msg.add"));
		} else {
			Item item = null;
			if (action == PanelActionProvider.ACTION_EDIT) {

				item = tbl.getItem(obj);
				fieldFactory.setOldSmscId(((EsmeSmscRouting) obj).getEsmeSmsc().getSmscId());
				fieldFactory.setOldPrefixId(((EsmeSmscRouting) obj).getEsmeIsdnPrefix().getPrefixId());
				// fieldFactory.setOldShortCode(((EsmeSmscRouting) obj)
				// .getShortcode());
				// fieldFactory.setOldPriority(((EsmeSmscRouting) obj)
				// .getPriority());
				fieldFactory.insertItemTempForCombobox((EsmeSmscRouting) obj);
			} else if (action == PanelActionProvider.ACTION_ADD_COPY) {
				Set<EsmeSmscRouting> setInstrument = (Set<EsmeSmscRouting>) tbl.getValue();
				EsmeSmscRouting actionParam = setInstrument.iterator().next();
				EsmeSmscRouting newBean = new EsmeSmscRouting();
				// newBean.setDefValue(actionParam.getDefValue());
				// newBean.setDescription(actionParam.getDescription());
				newBean.setEsmeSmsc(actionParam.getEsmeSmsc());
				// newBean.setShortcode(actionParam.getShortcode());
				newBean.setEsmeIsdnPrefix(actionParam.getEsmeIsdnPrefix());
				// newBean.setPriority(actionParam.getPriority());
				// newBean.setStatus(actionParam.getStatus());
				fieldFactory.setOldSmscId(0);
				// fieldFactory.setOldShortCode("");
				fieldFactory.setOldPrefixId(0);
				// fieldFactory.setOldPriority("");
				item = new BeanItem<EsmeSmscRouting>(newBean);
				fieldFactory.insertItemTempForCombobox(null);
			} else if (action == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
				if (parent.isTreeNodeRoot(parent.getCurrentTreeNode())) {
					MessageAlerter.showMessageI18n(getWindow(), "command.notification");
					pnlAction.clearAction();
					return;
				}
				EsmeSmscRouting actionParam = new EsmeSmscRouting();
				// actionParam.setShortcode("");
				// actionParam.setSmscId(0);
				EsmeIsdnPrefix objt = new EsmeIsdnPrefix();
				objt.setPrefixId(0);
				actionParam.setEsmeIsdnPrefix(objt);

				actionParam.setEsmeSmsc(((EsmeSmsc) ((parent.getCurrentTreeNode() instanceof EsmeSmsc) ? (EsmeSmsc) parent.getCurrentTreeNode()
				        : (parent.isTreeNodeRoot(parent.getCurrentTreeNode()) ? null : (childNodes.size() > 0) ? childNodes.get(0) : null))));
				// fieldFactory.setOldShortCode("");
				fieldFactory.setOldSmscId(0);
				fieldFactory.setOldPrefixId(0);
				item = new BeanItem<EsmeSmscRouting>(actionParam);
				fieldFactory.insertItemTempForCombobox(null);
			} else {
				if (parent.isTreeNodeRoot(parent.getCurrentTreeNode())) {
					MessageAlerter.showMessageI18n(getWindow(), "command.notification");
					pnlAction.clearAction();
					return;
				}
				EsmeSmscRouting actionParam = new EsmeSmscRouting();
				// actionParam.setShortcode("");
				// EsmeIsdnPrefix objt = new EsmeIsdnPrefix();
				// objt.setPrefixId(0);
				// actionParam.setEsmeIsdnPrefix(objt);
				actionParam.setEsmeSmsc(((EsmeSmsc) ((parent.getCurrentTreeNode() instanceof EsmeSmsc) ? (EsmeSmsc) parent.getCurrentTreeNode()
				        : (parent.isTreeNodeRoot(parent.getCurrentTreeNode()) ? null : (childNodes.size() > 0) ? childNodes.get(0) : null))));
				fieldFactory.setOldSmscId(0);
				// fieldFactory.setOldShortCode("");
				fieldFactory.setOldPrefixId(0);
				item = new BeanItem<EsmeSmscRouting>(actionParam);
				fieldFactory.insertItemTempForCombobox(null);
			}
			createDialog(item);
		}
	}

	public void accept() {

		try {
			boolean modified = form.isModified();
			if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT && !modified) {
				pnlAction.clearAction();
				return;
			}

			if (form.getField("esmeIsdnPrefix").getValue() == null || form.getField("esmeIsdnPrefix").getValue().toString() == null) {
				MessageAlerter.showErrorMessage(getWindow(), "Prefix isdn must not be empty");
				return;
			} else {

				form.commit();
				BeanItem<EsmeSmscRouting> beanItem = null;
				beanItem = (BeanItem<EsmeSmscRouting>) form.getItemDataSource();
				EsmeSmscRouting smscRouting = beanItem.getBean();
				if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD || pnlAction.getAction() == PanelActionProvider.ACTION_ADD_COPY) {
					try {
						long id = actionParamService.add(smscRouting);
						if (id > 0) {
							smscRouting.setSmscRoutingId(id);
							tbl.addItem(smscRouting);
							setSingleRowSelected(smscRouting);
							if (pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
								pnlAction.clearAction();
								pnlAction.searchOrAddNew(smscRouting.getEsmeSmsc());
							}
							LogUtil.logActionInsert(PanelSmscRouting.class.getName(), "ESME_SMSC_ROUTING", "SMSC_ROUTING_ID", "" + smscRouting.getSmscRoutingId() + "", null);
							tbl.select(smscRouting);

							container.initPager(actionParamService.count(null, null, DEFAULT_EXACT_MATCH));

							MessageAlerter.showMessageI18n(getWindow(), "common.msg.add.success", TM.get("smscRouting.strName2"));
						} else {
							MessageAlerter.showMessageI18n(getWindow(), "common.msg.add.fail", TM.get("smscRouting.strName2"));
						}
					} catch (Exception e) {
						FormUtil.showException(this, e);
					}
				} else if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT) {
					try {
						Vector v = LogUtil.logActionBeforeUpdate(PanelSmscRouting.class.getName(), "ESME_SMSC_ROUTING", "SMSC_ROUTING_ID", "" + smscRouting.getSmscRoutingId() + "", null);
						actionParamService.update(smscRouting);
						setSingleRowSelected(smscRouting);
						LogUtil.logActionAfterUpdate(v);
						container.initPager(actionParamService.count(null, null, DEFAULT_EXACT_MATCH));
						MessageAlerter.showMessageI18n(getWindow(), "common.msg.edit.success", TM.get("smscRouting.strName2"));
					} catch (Exception e) {
						FormUtil.showException(this, e);
					}
				}
			}

		} catch (Exception e) {
			FormUtil.showException(this, e);
		}
		pnlAction.clearAction();
	}

	@Override
	public String getPermission() {

		// return AppClient.getPermission(this.getClass().getName());
		// return "USDI";
		return SessionData.getAppClient().getPermission(this.getClass().getName());
	}

	public void delete(Object object) {

		resetResource();
		if (object instanceof EsmeSmscRouting) {
			EsmeSmscRouting prcService = (EsmeSmscRouting) object;
			// boolean b = serviceService.checkConstraints(obj.getServiceId());
			// if (!b)
			// {
			total++;
			canDelete.add(prcService);
			// }
		} else {
			for (EsmeSmscRouting obj : (List<EsmeSmscRouting>) object) {
				total++;

				// boolean b =
				// serviceService.checkConstraints(obj.getServiceId());
				// if (!b)
				// {
				canDelete.add(obj);
				// }
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
		for (EsmeSmscRouting actionParam : canDelete) {
			try {
				Vector<Vector<String>> log0bjectRelated = new Vector<Vector<String>>();
				LogUtil.logActionDelete(PanelSmscRouting.class.getName(), "ESME_SMSC_ROUTING", "SMSC_ROUTING_ID", "" + actionParam.getSmscRoutingId() + "", log0bjectRelated);
				actionParamService.delete(actionParam);
				tbl.removeItem(actionParam);
				deleted++;
				interactionSelected = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		container.initPager(actionParamService.count(null, null, DEFAULT_EXACT_MATCH));
		MessageAlerter.showMessageI18n(getWindow(), TM.get("message.delete"), deleted, total);
		// }
		// catch (Exception e)
		// {
		// FormUtil.showException(this, e);
		// }
	}

	@Override
	public void filterTree(Object obj) {

		// TODO Auto-generated method stub
	}

	@Override
	public void treeValueChanged(Object obj) {

		childNodes.clear();
		// if (obj instanceof PrcService && !parent.isTreeNodeRoot(obj)) {
		// Collection<?> collection = parent.getChildrenTreeNode(obj);
		// if (collection != null) {
		// childNodes.addAll((Collection<? extends PrcAction>) collection);
		// }
		// } else
		if (obj instanceof EsmeSmsc && !parent.isTreeNodeRoot(obj)) {

			Object parentNode = parent.getParentTreeNode(obj);
			Collection<?> collection = parent.getChildrenTreeNode(parentNode);
			if (collection != null) {
				childNodes.addAll((Collection<? extends EsmeSmsc>) collection);
			}
		}
		loadDataFromDatabase(obj);
	}

	private void initPagerForTable(EsmeSmscRouting skSearch) {

		SearchEntity searchEntity = new SearchEntity();
		int count = actionParamService.count(searchEntity, skSearch, true);
		container.initPager(count);
		if (count <= 0) {
			MessageAlerter.showMessageI18n(getWindow(), TM.get("msg.search.value.emty"));
		}
	}

	private void loadDataFromDatabase(Object obj) {

		skSearch = new EsmeSmscRouting();
		try {
			if (obj != null && (obj instanceof EsmeSmsc) && !parent.isTreeNodeRoot(obj)) {
				data.removeAllItems();
				skSearch = new EsmeSmscRouting();
				skSearch.setEsmeSmsc(((EsmeSmsc) obj));
				initPagerForTable(skSearch);

			}
			// else if (obj != null && (obj instanceof PrcService)
			// && !parent.isTreeNodeRoot(obj)) {
			// skSearch = new EsmeSmscRouting();
			// data.removeAllItems();
			// if (childNodes.size() > 0) {
			//
			// ArrayList<PrcAction> list = new ArrayList<PrcAction>();
			// for (PrcAction mcaAction : actionService
			// .findAllWithoutParameter()) {
			// if (mcaAction.getPrcService().equals(obj))
			// list.add(mcaAction);
			// }
			// skSearch.setListAction(list);
			// initPagerForTable(skSearch);
			//
			// }
			// }
			else if (parent.isTreeNodeRoot(obj)) {
				skSearch = new EsmeSmscRouting();
				data.removeAllItems();
				data.addAll(actionParamService.findAllWithoutParameter());
				initPagerForTable(skSearch);
			}
		} catch (Exception e) {
			FormUtil.showException(getWindow(), e);
			e.printStackTrace();
		}
	}

	@Override
	public void dialogClosed(OptionKind option) {

		if (OptionKind.OK.equals(option)) {
			if (canDelete != null && canDelete.size() > 0) {
				doDelete();
			}
		}
	}

	public TableContainer getContainer() {

		return container;
	}

	private void setSingleRowSelected(Object id) {

		tbl.setMultiSelect(false);
		tbl.select(id);
		tbl.setMultiSelect(true);
	}

	@Override
	public void loadButtonPanel() {

		if (!isLoadedPnlAction) {
			pnlAction = new CommonButtonPanel(this);
			pnlAction.showSearchPanel(true);
			pnlAction.setFromCaption(TM.get(FormSmscDetail.class.getName()));
			pnlAction.setValueForCboField(TM.get("smscRouting.table.filteredcolumns").split(","), TM.get("smscRouting.table.filteredcolumnscaption").split(","));
			pnlAction.setEnableAlphabeltSearch(false);
			// pnlAction.setValueForCboField(
			// TM.get("actionparam.table.filteredcolumns").split(","), TM
			// .get("actionparam.table.filteredcolumnscaption")
			// .split(","));
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

		// ArrayList<String[]> dataDefinition = new ArrayList<String[]>();
		// dataDefinition.add(new String[]{ "status", "0",
		// TM.get("smscRouting.strInactive") });
		// dataDefinition.add(new String[]{ "status", "1",
		// TM.get("smscRouting.strActive") });
		//
		// ArrayList<String[]> parameters = new ArrayList<String[]>();
		// parameters
		// .add(new String[]{
		// "$ReportDate",
		// FormUtil.simpleShortDateFormat.format(FormUtil
		// .getToday(null)) });
		//
		// ReportUtil.doExportDataOnForm("PanelActionParam", dataDefinition,
		// parameters, TM.get("smscRouting.setVisibleColumns"), tbl,
		// FormUtil.stringShortDateFormat);

	}

	@Override
	public void searchOrAddNew(String key) {

		skSearch = new EsmeSmscRouting();
		// skSearch.setShortcode(key);
		DEFAULT_EXACT_MATCH = true;
		SearchEntity searchEntity = new SearchEntity();
		int count = actionParamService.count(searchEntity, skSearch, DEFAULT_EXACT_MATCH);

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
	public void fieldSearch(SearchObj searchObj) {

		if (searchObj.getField() == null && searchObj.getKey() == null)
			return;

		skSearch = new EsmeSmscRouting();
		if (searchObj.getField() == null) {
			// skSearch.setShortcode(searchObj.getKey());
			// skSearch.setSmscId(searchObj.getKey());
		} else {
			if (searchObj.getField().equals("esmeSmsc")) {
				EsmeSmsc objs = new EsmeSmsc();
				// objs.setSmscId(Long.valueOf(searchObj.getKey()));
				objs.setName(searchObj.getKey());
				skSearch.setEsmeSmsc(objs);
			} else if (searchObj.getField().equals("esmeIsdnPrefix")) {
				EsmeIsdnPrefix objs = new EsmeIsdnPrefix();
				// objs.setPrefixId(Long.valueOf(searchObj.getKey()));
				objs.setPrefixValue(searchObj.getKey());
				skSearch.setEsmeIsdnPrefix(objs);
			}
		}
		SearchEntity searchEntity = new SearchEntity();
		int count = actionParamService.count(searchEntity, skSearch, DEFAULT_EXACT_MATCH);
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
			SearchEntity searchEntity = new SearchEntity();
			List<EsmeSmscRouting> listSmscRouting = actionParamService.findAllWithOrderPaging(searchEntity, skSearch, sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH);
			Collections.sort(listSmscRouting, FormUtil.stringComparator(true));
			data.addAll(listSmscRouting);
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
}
