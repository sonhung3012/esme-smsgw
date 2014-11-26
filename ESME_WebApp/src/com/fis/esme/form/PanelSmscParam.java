package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.fis.esme.admin.SessionData;
import com.fis.esme.app.CacheServiceClient;
import com.fis.esme.classes.PanelTreeProvider;
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
import com.fis.esme.persistence.EsmeSmsc;
import com.fis.esme.persistence.EsmeSmscParam;
import com.fis.esme.persistence.SearchEntity;
import com.fis.esme.smscparam.SmscParamTransferer;
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

public class PanelSmscParam extends VerticalLayout implements PanelActionProvider, TabChangeProvider, PagingComponentListener, ServerSort, OptionDialogResultListener, PanelTreeProvider {

	private CommonDialog dialog;
	private boolean isLoaded = false;
	private boolean isLoadedPnlAction = false;
	private Form form;
	private CustomTable tbl;
	private TableContainer container;
	private BeanItemContainer<EsmeSmscParam> data;
	private CommonButtonPanel pnlAction;
	private SmscParamFieldFactory fieldFactory;
	private ArrayList<EsmeSmscParam> canDelete = new ArrayList<EsmeSmscParam>();
	private ConfirmDeletionDialog confirm;
	private FormSmscDetail smscDetail;
	private int total = 0;
	private List<EsmeSmsc> childNodes = new ArrayList<EsmeSmsc>();
	private SmscParamTransferer smscParamService;
	private EsmeSmscParam oldSmscParam = new EsmeSmscParam();
	// private EsmeSmscParam interactionSelected = null;

	private final String DEFAULT_SORTED_COLUMN = "name";
	private boolean DEFAULT_SORTED_ASC = true;
	private boolean DEFAULT_EXACT_MATCH = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;
	private EsmeSmscParam skSearch = null;

	// private SmscTransferer actionService;

	public PanelSmscParam(String title, FormSmscDetail smscDetail) {

		this.smscDetail = smscDetail;
		this.setCaption(title);
		this.setSizeFull();
		// initLayout();
	}

	public PanelSmscParam(FormSmscDetail smscDetail) {

		this(TM.get(FormSmscDetail.class.getName()), smscDetail);
		LogUtil.logAccess(PanelSmscParam.class.getName());
	}

	// public PanelSmscParam(FormSmscDetail formSmscDetail) {
	// // TODO Auto-generated constructor stub
	// }

	private void initLayout() {

		initService();
		initComponent();
		this.setSizeFull();
		this.addComponent(container);
	}

	private void initService() {

		try {
			smscParamService = CacheServiceClient.smscParamService;
		} catch (Exception e) {

			e.printStackTrace();
		}

		// try {
		// actionService = CacheServiceClient.smscService;
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// }
	}

	private void initComponent() {

		data = new BeanItemContainer<EsmeSmscParam>(EsmeSmscParam.class);
		initFormSmscParam();
		initTable();
	}

	private void initFormSmscParam() {

		form = new Form();
		form.setWriteThrough(false);
		form.setInvalidCommitted(false);
		form.setImmediate(false);
		try {
			fieldFactory = new SmscParamFieldFactory();
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		form.setFormFieldFactory(fieldFactory);
		dialog = new CommonDialog(TM.get("smscParam.CommonDialog.Title"), form, this);
		dialog.setHeight("360px");
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
		if (smscDetail.isTreeNodeRoot(smscDetail.getCurrentTreeNode())) {
			fieldFactory.getDataAction(null);
			// if (interactionSelected != null) {
			// PrcService service = interactionSelected.getPrcAction()
			// .getPrcService();
			// fieldFactory.getDataAction(smscDetail.findActionsInServiceByStatus(
			// service, "1"));
			// }
		} else {
			fieldFactory.getDataAction(null);
		}
		// Node # root
		// if (!smscDetail.isTreeNodeRoot(smscDetail.getCurrentTreeNode())
		// && smscDetail.getCurrentTreeNode() instanceof PrcService) {
		// fieldFactory.getDataAction(childNodes);
		// } else
		if (!smscDetail.isTreeNodeRoot(smscDetail.getCurrentTreeNode()) && smscDetail.getCurrentTreeNode() instanceof EsmeSmsc) {
			fieldFactory.getDataAction(childNodes);
		}
	}

	@SuppressWarnings("serial")
	private void initTable() {

		tbl = new CustomTable("", data, pnlAction) {

			@Override
			protected String formatPropertyValue(Object rowId, Object colId, Property property) {

				String pid = (String) colId;
				// if ("status".equals(pid)) {
				// if ((property.getValue().equals("1"))) {
				// return TM.get("frmActionparam.strActive");
				// } else {
				// return TM.get("frmActionparam.strInactive");
				// }
				// }
				return super.formatPropertyValue(rowId, colId, property);
			}

			@Override
			public Collection<?> getSortableContainerPropertyIds() {

				ArrayList<Object> arr = new ArrayList<Object>();
				Object[] sortCol = TM.get("smscParam.setsortColumns").split(",");
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
				// if ((id instanceof Set) && id != null) {
				// if (((Set<EsmeSmscParam>) id).iterator().hasNext()) {
				// interactionSelected = ((Set<EsmeSmscParam>) id)
				// .iterator().next();
				// }
				// } else {
				// interactionSelected = (EsmeSmscParam) id;
				// }
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

				final EsmeSmscParam bean = (EsmeSmscParam) itemId;

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
						EsmeSmscParam bean = data.getIdByIndex(i);
						bean.setSelect(tbl.isSelectAll());
						tbl.setColumnHeader("select", (tbl.isSelectAll() == true) ? "-" : "+");
						tbl.refreshRowCache();
					}
				}
			}
		});

		tbl.setVisibleColumns(TM.get("smscParam.setVisibleColumns").split(","));
		tbl.setColumnHeaders(TM.get("smscParam.setColumnHeaders").split(","));
		tbl.setColumnReorderingAllowed(true);
		tbl.setColumnCollapsingAllowed(true);
		// tbl.setColumnCollapsed("priority", true);
		// tbl.setColumnCollapsed("prcAction", true);
		tbl.setStyleName("commont_table_noborderLR");
		String[] properties = TM.get("smscParam.setVisibleColumns").split(",");
		String[] propertiesValues = TM.get("smscParam.propertiesValue").split(",");
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
		container.initPager(smscParamService.count(searchEntity, null, DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(true);
		container.removeHeaderSearchLayout();
		container.setVisibleBorderMainLayout(false);
		container.setFilteredColumns(TM.get("smscparam.table.filteredcolumns").split(","));
		container.setEnableDeleteAllButton(getPermission().contains("D"));
		container.setEnableButtonAddNew(getPermission().contains("I"));
		container.setEnableButtonAddCopy(getPermission().contains("I"));

	}

	public List<EsmeSmscParam> getAllItemCheckedOnTable() {

		List<EsmeSmscParam> list = new ArrayList<EsmeSmscParam>();
		Collection<EsmeSmscParam> collection = (Collection<EsmeSmscParam>) tbl.getItemIds();
		for (EsmeSmscParam obj : collection) {
			if (obj.isSelect())
				list.add(obj);
		}
		return list;
	}

	private Window createDialog(Item item) {

		form.setItemDataSource(item);
		Object[] visibleProperties = TM.get("smscParam.visibleProperties").split(",");
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
		if (smscDetail.isTreeNodeRoot(smscDetail.getCurrentTreeNode())) {
			pnlAction.clearAction();
			MessageAlerter.showMessage(getWindow(), TM.get("smscparam.msg.add"));
		} else {
			Item item = null;
			if (action == PanelActionProvider.ACTION_EDIT) {
				item = tbl.getItem(obj);
				((EsmeSmscParam) obj).setSmscId((EsmeSmsc) smscDetail.getCurrentTreeNode());
				oldSmscParam.setSmscId((((EsmeSmscParam) obj).getSmscId()));
				oldSmscParam.setValue(((EsmeSmscParam) obj).getValue());
				oldSmscParam.setName(((EsmeSmscParam) obj).getName());
				fieldFactory.setOldSmscId((((EsmeSmscParam) obj).getSmscId()).getSmscId());
				fieldFactory.setOldName(((EsmeSmscParam) obj).getName());
				fieldFactory.setOldValue(((EsmeSmscParam) obj).getValue());
				// fieldFactory.setOldPriority(((EsmeSmscParam) obj)
				// .getPriority());
				fieldFactory.insertItemTempForCombobox((EsmeSmscParam) obj);
			} else if (action == PanelActionProvider.ACTION_ADD_COPY) {
				Set<EsmeSmscParam> setInstrument = (Set<EsmeSmscParam>) tbl.getValue();
				EsmeSmscParam smscParam = setInstrument.iterator().next();
				EsmeSmscParam newBean = new EsmeSmscParam();
				// newBean.setDefValue(smscParam.getDefValue());
				// newBean.setDescription(smscParam.getDescription());
				smscParam.setSmscId((EsmeSmsc) smscDetail.getCurrentTreeNode());
				newBean.setSmscId(smscParam.getSmscId());
				newBean.setName(smscParam.getName());
				newBean.setValue(smscParam.getValue());
				// newBean.setPriority(smscParam.getPriority());
				// newBean.setStatus(smscParam.getStatus());
				fieldFactory.setOldSmscId(0);
				fieldFactory.setOldName("");
				fieldFactory.setOldValue("");
				// fieldFactory.setOldPriority("");
				item = new BeanItem<EsmeSmscParam>(newBean);
				fieldFactory.insertItemTempForCombobox(null);
			} else if (action == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
				if (smscDetail.isTreeNodeRoot(smscDetail.getCurrentTreeNode())) {
					MessageAlerter.showMessageI18n(getWindow(), "command.notification");
					pnlAction.clearAction();
					return;
				}
				EsmeSmscParam smscParam = new EsmeSmscParam();
				smscParam.setName("");
				smscParam.setValue("");
				fieldFactory.setOldName("");
				fieldFactory.setOldValue("");
				smscParam.setSmscId(((EsmeSmsc) ((smscDetail.getCurrentTreeNode() instanceof EsmeSmsc) ? (EsmeSmsc) smscDetail.getCurrentTreeNode() : (smscDetail.isTreeNodeRoot(smscDetail
				        .getCurrentTreeNode()) ? null : (childNodes.size() > 0) ? childNodes.get(0) : null))));
				fieldFactory.setOldSmscId(0);
				item = new BeanItem<EsmeSmscParam>(smscParam);
				fieldFactory.insertItemTempForCombobox(null);
			} else {
				if (smscDetail.isTreeNodeRoot(smscDetail.getCurrentTreeNode())) {
					MessageAlerter.showMessageI18n(getWindow(), "command.notification");
					pnlAction.clearAction();
					return;
				}
				EsmeSmscParam smscParam = new EsmeSmscParam();
				smscParam.setValue("");
				smscParam.setName("");
				smscParam.setSmscId(((EsmeSmsc) ((smscDetail.getCurrentTreeNode() instanceof EsmeSmsc) ? (EsmeSmsc) smscDetail.getCurrentTreeNode() : (smscDetail.isTreeNodeRoot(smscDetail
				        .getCurrentTreeNode()) ? null : (childNodes.size() > 0) ? childNodes.get(0) : null))));
				fieldFactory.setOldSmscId(0);
				item = new BeanItem<EsmeSmscParam>(smscParam);
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
			form.commit();
			BeanItem<EsmeSmscParam> beanItem = null;
			beanItem = (BeanItem<EsmeSmscParam>) form.getItemDataSource();
			EsmeSmscParam smscParam = beanItem.getBean();
			if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD || pnlAction.getAction() == PanelActionProvider.ACTION_ADD_COPY) {
				try {
					long id = smscParamService.add(smscParam);
					if (id > 0) {
						EsmeSmsc smsc = new EsmeSmsc();
						smsc.setSmscId(id);
						smscParam.setSmscId(smsc);
						tbl.addItem(smscParam);
						setSingleRowSelected(smscParam);
						if (pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {
							pnlAction.clearAction();
							pnlAction.searchOrAddNew(smscParam.getName());
						}
						LogUtil.logActionInsert(PanelSmscParam.class.getName(), "ESME_SMSC_PARAM", "SMSC_ID", "" + smscParam.getSmscId() + "", null);
						tbl.select(smscParam);
						container.initPager(smscParamService.count(null, null, DEFAULT_EXACT_MATCH));
						MessageAlerter.showMessageI18n(getWindow(), "common.msg.add.success", TM.get("smscParam.namecfm"));
					} else {
						MessageAlerter.showMessageI18n(getWindow(), "common.msg.add.fail", TM.get("smscParam.namecfm"));
					}
				} catch (Exception e) {
					FormUtil.showException(this, e);
				}
			} else if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT) {
				try {
					Vector v = LogUtil.logActionBeforeUpdate(PanelSmscParam.class.getName(), "ESME_SMSC_PARAM", "SMSC_ID", "" + smscParam.getSmscId() + "", null);

					smscParamService.editDetail(oldSmscParam, smscParam);
					setSingleRowSelected(smscParam);

					LogUtil.logActionAfterUpdate(v);

					container.initPager(smscParamService.count(null, null, DEFAULT_EXACT_MATCH));
					MessageAlerter.showMessageI18n(getWindow(), "common.msg.edit.success", TM.get("smscParam.namecfm"));
				} catch (Exception e) {
					FormUtil.showException(this, e);
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
		if (object instanceof EsmeSmscParam) {
			EsmeSmscParam prcService = (EsmeSmscParam) object;
			// boolean b = serviceService.checkConstraints(obj.getServiceId());
			// if (!b)
			// {
			total++;
			canDelete.add(prcService);
			// }
		} else {
			for (EsmeSmscParam obj : (List<EsmeSmscParam>) object) {
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
		for (EsmeSmscParam smscParam : canDelete) {
			try {
				Vector<Vector<String>> log0bjectRelated = new Vector<Vector<String>>();
				LogUtil.logActionDelete(PanelSmscParam.class.getName(), "ESME_SMSC_PARAM", "SMSC_ID", "" + smscParam.getSmscId() + "", log0bjectRelated);
				smscParamService.delete(smscParam);
				tbl.removeItem(smscParam);
				deleted++;
				// interactionSelected = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		container.initPager(smscParamService.count(null, null, DEFAULT_EXACT_MATCH));
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
		// if (obj instanceof PrcService && !smscDetail.isTreeNodeRoot(obj)) {
		// Collection<?> collection = smscDetail.getChildrenTreeNode(obj);
		// if (collection != null) {
		// childNodes.addAll((Collection<? extends PrcAction>) collection);
		// }
		// } else
		if (obj instanceof EsmeSmsc && !smscDetail.isTreeNodeRoot(obj)) {

			Object smscDetailNode = smscDetail.getParentTreeNode(obj);
			Collection<?> collection = smscDetail.getChildrenTreeNode(smscDetailNode);
			if (collection != null) {
				childNodes.addAll((Collection<? extends EsmeSmsc>) collection);
			}
		}
		loadDataFromDatabase(obj);
	}

	private void initPagerForTable(EsmeSmscParam skSearch) {

		SearchEntity searchEntity = new SearchEntity();
		int count = smscParamService.count(searchEntity, skSearch, DEFAULT_EXACT_MATCH);
		container.initPager(count);
		if (count < 0) {
			MessageAlerter.showMessageI18n(getWindow(), TM.get("smsc.detail.search.value.emty"));
		}
	}

	private void loadDataFromDatabase(Object obj) {

		skSearch = new EsmeSmscParam();
		try {
			if (obj != null && (obj instanceof EsmeSmsc) && !smscDetail.isTreeNodeRoot(obj)) {
				data.removeAllItems();
				skSearch = new EsmeSmscParam();
				skSearch.setSmscId(((EsmeSmsc) obj));
				initPagerForTable(skSearch);

			}
			// else if (obj != null && (obj instanceof PrcService)
			// && !smscDetail.isTreeNodeRoot(obj)) {
			// skSearch = new EsmeSmscParam();
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
			else if (smscDetail.isTreeNodeRoot(obj)) {
				skSearch = new EsmeSmscParam();
				data.removeAllItems();
				// esmeCp.setStatus("1");
				List<EsmeSmscParam> listParams = smscParamService.findAllWithOrderPaging(null, skSearch, null, false, -1, -1, true);

				if (listParams != null) {

					data.addAll(listParams);
				}
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

		if (id instanceof EsmeSmscParam)
			loadDataFromDatabase(((EsmeSmscParam) id).getSmscId());
		tbl.setMultiSelect(false);
		tbl.select(id);
		tbl.setMultiSelect(true);
	}

	@Override
	public void loadButtonPanel() {

		if (!isLoadedPnlAction) {
			pnlAction = new CommonButtonPanel(this);
			pnlAction.showSearchPanel(true);
			pnlAction.setFromCaption(TM.get(PanelSmscParam.class.getName()));
			// pnlAction.setValueForCboField(
			// TM.get("actionparam.table.filteredcolumns").split(","), TM
			// .get("actionparam.table.filteredcolumnscaption")
			// .split(","));
			pnlAction.setValueForCboField(TM.get("smscparam.table.filteredcolumns").split(","), TM.get("smscparam.table.filteredcolumnscaption").split(","));
			smscDetail.addButtonPanel(pnlAction);
			isLoadedPnlAction = true;
		} else {
			smscDetail.addButtonPanel(this.pnlAction);
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
		// TM.get("frmActionparam.strInactive") });
		// dataDefinition.add(new String[]{ "status", "1",
		// TM.get("frmActionparam.strActive") });
		//
		// ArrayList<String[]> parameters = new ArrayList<String[]>();
		// parameters
		// .add(new String[]{
		// "$ReportDate",
		// FormUtil.simpleShortDateFormat.format(FormUtil
		// .getToday(null)) });
		//
		// ReportUtil.doExportDataOnForm("PanelActionParam", dataDefinition,
		// parameters, TM.get("frmActionparam.setVisibleColumns"), tbl,
		// FormUtil.stringShortDateFormat);

	}

	@Override
	public void searchOrAddNew(String key) {

		skSearch = new EsmeSmscParam();
		skSearch.setName(key);
		DEFAULT_EXACT_MATCH = true;
		SearchEntity searchEntity = new SearchEntity();
		int count = smscParamService.count(searchEntity, skSearch, DEFAULT_EXACT_MATCH);

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

		System.out.println("searchObj" + searchObj);
		if (searchObj.getField() == null && searchObj.getKey() == null)
			return;

		skSearch = new EsmeSmscParam();
		if (searchObj.getField() == null) {
			skSearch.setName(searchObj.getKey());
			skSearch.setValue(searchObj.getKey());
		} else {
			if (searchObj.getField().equals("name"))
				skSearch.setName(searchObj.getKey());
			else if (searchObj.getField().equals("value"))
				skSearch.setValue(searchObj.getKey());
		}
		SearchEntity searchEntity = new SearchEntity();
		int count = smscParamService.count(searchEntity, skSearch, false);
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
			data.addAll(smscParamService.findAllWithOrderPaging(searchEntity, skSearch, sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH));
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
