package com.fis.esme.form;

import java.util.ArrayList;
import java.util.Collection;
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
import com.fis.esme.component.ConfirmDeletionDialog;
import com.fis.esme.component.CustomTable;
import com.fis.esme.component.PagingComponent.ChangePageEvent;
import com.fis.esme.component.PagingComponent.PagingComponentListener;
import com.fis.esme.component.PanelActionProvider;
import com.fis.esme.component.TabChangeProvider;
import com.fis.esme.component.TableContainer;
import com.fis.esme.groupsdt.GroupsDTTransferer;
import com.fis.esme.persistence.Groups;
import com.fis.esme.persistence.SearchEntity;
import com.fis.esme.persistence.SubGroupBean;
import com.fis.esme.persistence.Subscriber;
import com.fis.esme.subscriberdt.Exception_Exception;
import com.fis.esme.subscriberdt.SubscriberDTTransferer;
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

public class PanelSubscriber extends VerticalLayout implements PanelActionProvider, TabChangeProvider, PagingComponentListener, ServerSort, OptionDialogResultListener, PanelTreeProvider {

	private CommonDialog dialog;
	private boolean isLoaded = false;
	private boolean isLoadedPnlAction = false;
	private Form form;
	private CustomTable tbl;
	private TableContainer container;
	private BeanItemContainer<SubGroupBean> data;
	private CommonButtonPanel pnlAction;
	private PanelSubscriberFieldFactory fieldFactory;
	private ArrayList<SubGroupBean> canDelete = new ArrayList<SubGroupBean>();
	private ConfirmDeletionDialog confirm;
	private FormSubscriber smscDetail;
	private int total = 0;

	private List<Groups> childNodes = new ArrayList<Groups>();
	private List<Subscriber> listSubscriber = new ArrayList<Subscriber>();
	String strRegexMsisdnExisted = "";

	private SubscriberDTTransferer smscParamService;
	private GroupsDTTransferer groupService;
	private SubGroupBean oldSmscParam = new SubGroupBean();
	// private SubGroupBean interactionSelected = null;

	private final String DEFAULT_SORTED_COLUMN = "msisdn";
	private boolean DEFAULT_SORTED_ASC = true;
	private boolean DEFAULT_EXACT_MATCH = false;
	private String sortedColumn = DEFAULT_SORTED_COLUMN;
	private boolean sortedASC = DEFAULT_SORTED_ASC;
	private SubGroupBean skSearch = null;

	// private SmscTransferer actionService;

	public PanelSubscriber(String title, FormSubscriber smscDetail) {

		this.smscDetail = smscDetail;
		this.setCaption(title);
		this.setSizeFull();
		// initLayout();
	}

	public PanelSubscriber(FormSubscriber smscDetail) {

		this(TM.get(FormSubscriber.class.getName()), smscDetail);
		LogUtil.logAccess(PanelSubscriber.class.getName());
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
			smscParamService = CacheServiceClient.serviceSubscriber;
			groupService = CacheServiceClient.serviceGroups;

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

		data = new BeanItemContainer<SubGroupBean>(SubGroupBean.class);
		initFormSmscParam();
		initTable();
	}

	private void initFormSmscParam() {

		form = new Form();
		form.setWriteThrough(false);
		form.setInvalidCommitted(false);
		form.setImmediate(false);
		try {
			fieldFactory = new PanelSubscriberFieldFactory();
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		form.setFormFieldFactory(fieldFactory);
		dialog = new CommonDialog(TM.get("subs.CommonDialog.Title"), form, this);
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
		} else {
			fieldFactory.getDataAction(null);
		}
		if (!smscDetail.isTreeNodeRoot(smscDetail.getCurrentTreeNode()) && smscDetail.getCurrentTreeNode() instanceof Groups) {
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
				Object[] sortCol = TM.get("subs.setsortColumns").split(",");
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
				// if (((Set<SubGroupBean>) id).iterator().hasNext()) {
				// interactionSelected = ((Set<SubGroupBean>) id)
				// .iterator().next();
				// }
				// } else {
				// interactionSelected = (SubGroupBean) id;
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

				final SubGroupBean bean = (SubGroupBean) itemId;

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
						SubGroupBean bean = data.getIdByIndex(i);
						bean.setSelect(tbl.isSelectAll());
						tbl.setColumnHeader("select", (tbl.isSelectAll() == true) ? "-" : "+");
						tbl.refreshRowCache();
					}
				}
			}
		});

		tbl.setVisibleColumns(TM.get("subs.setVisibleColumns").split(","));
		tbl.setColumnHeaders(TM.get("subs.setColumnHeaders").split(","));
		tbl.setColumnReorderingAllowed(true);
		tbl.setColumnCollapsingAllowed(true);
		// tbl.setColumnCollapsed("priority", true);
		// tbl.setColumnCollapsed("prcAction", true);
		tbl.setStyleName("commont_table_noborderLR");
		String[] properties = TM.get("subs.setVisibleColumns").split(",");
		String[] propertiesValues = TM.get("subs.propertiesValue").split(",");
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
		container.initPager(getSmscParamService().count(searchEntity, null, DEFAULT_EXACT_MATCH));
		container.setVidibleButtonDeleteAll(true);
		container.removeHeaderSearchLayout();
		container.setVisibleBorderMainLayout(false);
		container.setFilteredColumns(TM.get("subs.table.filteredcolumns").split(","));
		container.setEnableDeleteAllButton(getPermission().contains("D"));
		container.setEnableButtonAddNew(getPermission().contains("I"));
		container.setEnableButtonAddCopy(getPermission().contains("I"));

	}

	public List<SubGroupBean> getAllItemCheckedOnTable() {

		List<SubGroupBean> list = new ArrayList<SubGroupBean>();
		Collection<SubGroupBean> collection = (Collection<SubGroupBean>) tbl.getItemIds();
		for (SubGroupBean obj : collection) {
			if (obj.isSelect()) {
				obj.setGroups(groupService.findBySubGgroup(obj.getSubId()));
				list.add(obj);
			}
		}
		return list;
	}

	private Window createDialog(Item item) {

		form.setItemDataSource(item);
		Object[] visibleProperties = TM.get("subs.visibleProperties").split(",");
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

		// initComboBoxAction();
		Item item = null;
		int action = pnlAction.getAction();

		if (action == PanelActionProvider.ACTION_EDIT) {

			item = tbl.getItem(obj);
			// fieldFactory.setOldPriority(((SubGroupBean) obj)
			// .getPriority());
			fieldFactory.insertItemTempForCombobox((SubGroupBean) obj);

		} else if (action == PanelActionProvider.ACTION_ADD_COPY) {

			Set<SubGroupBean> setInstrument = (Set<SubGroupBean>) tbl.getValue();
			SubGroupBean smscParam = setInstrument.iterator().next();
			SubGroupBean newBean = new SubGroupBean();
			newBean.setGroups(smscParam.getGroups());
			newBean.setMsisdn(smscParam.getMsisdn());
			newBean.setBirthDate(smscParam.getBirthDate());
			newBean.setCreateDate(smscParam.getCreateDate());
			newBean.setEmail(smscParam.getEmail());
			newBean.setSex(smscParam.getSex());
			newBean.setStatus(smscParam.getStatus());
			newBean.setAddress(smscParam.getAddress());
			item = new BeanItem<SubGroupBean>(newBean);
			fieldFactory.insertItemTempForCombobox(null);

		} else if (action == PanelActionProvider.ACTION_SEARCH_ADDNEW) {

			// if (smscDetail.isTreeNodeRoot(smscDetail.getCurrentTreeNode())) {
			//
			// MessageAlerter.showMessageI18n(getWindow(), "command.notification");
			// pnlAction.clearAction();
			// return;
			// }

			SubGroupBean smscParam = new SubGroupBean();
			smscParam.setMsisdn("");
			smscParam.setBirthDate(new Date());
			smscParam.setCreateDate(new Date());
			smscParam.setEmail("");
			smscParam.setSex("1");
			smscParam.setAddress("");
			smscParam.setStatus("1");
			smscParam.setGroups(((Groups) ((smscDetail.getCurrentTreeNode() instanceof Groups) ? (Groups) smscDetail.getCurrentTreeNode()
			        : (smscDetail.isTreeNodeRoot(smscDetail.getCurrentTreeNode()) ? null : (childNodes.size() > 0) ? childNodes.get(0) : null))));
			item = new BeanItem<SubGroupBean>(smscParam);
			fieldFactory.insertItemTempForCombobox(smscParam);
		} else {

			// if (smscDetail.isTreeNodeRoot(smscDetail.getCurrentTreeNode())) {
			// MessageAlerter.showMessageI18n(getWindow(), "command.notification");
			// pnlAction.clearAction();
			// return;
			// }

			SubGroupBean smscParam = new SubGroupBean();
			smscParam.setMsisdn("");
			smscParam.setBirthDate(new Date());
			smscParam.setCreateDate(new Date());
			smscParam.setEmail("");
			smscParam.setSex("1");
			smscParam.setStatus("1");
			smscParam.setAddress("");
			if (skSearch != null && skSearch.getGroups() != null) {

				smscParam.setGroups(skSearch.getGroups());
			}

			// smscParam.setGroups(groupService.findBySubGgroup(arg0))
			item = new BeanItem<SubGroupBean>(smscParam);
			fieldFactory.insertItemTempForCombobox(smscParam);
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
			BeanItem<SubGroupBean> beanItem = null;
			beanItem = (BeanItem<SubGroupBean>) form.getItemDataSource();
			SubGroupBean sgBean = beanItem.getBean();
			Subscriber smscParam = convertToSubscriber(sgBean);

			if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD || pnlAction.getAction() == PanelActionProvider.ACTION_ADD_COPY
			        || pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {

				try {

					long id = getSmscParamService().add(smscParam, sgBean.getGroups().getGroupId());
					if (id > 0) {
						smscParam.setSubId(id);
						tbl.addItem(smscParam);
						setSingleRowSelected(smscParam);

						if (skSearch != null && skSearch.getAddress() != null && skSearch.getAddress() != "") {

							skSearch.setAddress(skSearch.getAddress() + "," + id);
						}

						container.initPager(getSmscParamService().count(null, convertToSubscriber(skSearch), DEFAULT_EXACT_MATCH));

						listSubscriber.add(smscParam);

						if (pnlAction.getAction() == PanelActionProvider.ACTION_SEARCH_ADDNEW) {

							pnlAction.clearAction();
							pnlAction.searchOrAddNew(smscParam.getMsisdn());
						}

						LogUtil.logActionInsert(PanelSubscriber.class.getName(), "SUBSCRIBER", "SUB_ID", "" + smscParam.getSubId() + "", null);
						tbl.select(smscParam);
						MessageAlerter.showMessageI18n(getWindow(), "common.msg.add.success", TM.get("subs.namecfm"));
					} else {
						MessageAlerter.showMessageI18n(getWindow(), "common.msg.add.fail", TM.get("subs.namecfm"));
					}
				} catch (Exception e) {
					FormUtil.showException(this, e);
				}

			} else if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT) {

				try {

					Vector v = LogUtil.logActionBeforeUpdate(PanelSubscriber.class.getName(), "SUBSCRIBER", "SUB_ID", "" + smscParam.getSubId() + "", null);
					getSmscParamService().update(smscParam, sgBean.getGroups().getGroupId());
					setSingleRowSelected(smscParam);

					for (Subscriber sub : listSubscriber) {

						if (sub.getSubId() == smscParam.getSubId()) {

							sub.setMsisdn(smscParam.getMsisdn());
							break;
						}
					}

					LogUtil.logActionAfterUpdate(v);
					MessageAlerter.showMessageI18n(getWindow(), "common.msg.edit.success", TM.get("subs.namecfm"));

				} catch (Exception e) {
					FormUtil.showException(this, e);
				}

			}
		} catch (Exception e) {
			FormUtil.showException(this, e);
		}
		pnlAction.clearAction();
	}

	public Subscriber convertToSubscriber(SubGroupBean bean) {

		if (bean != null) {
			Subscriber newBean = new Subscriber();
			newBean.setSubId(bean.getSubId());
			newBean.setMsisdn(bean.getMsisdn());
			newBean.setBirthDate(bean.getBirthDate());
			newBean.setCreateDate(bean.getCreateDate());
			newBean.setEmail(bean.getEmail());
			newBean.setSex(bean.getSex());
			newBean.setStatus(bean.getStatus());
			newBean.setAddress(bean.getAddress());
			return newBean;
		}
		return null;
	}

	@Override
	public String getPermission() {

		// return AppClient.getPermission(this.getClass().getName());
		// return "USDI";
		return SessionData.getAppClient().getPermission(this.getClass().getName());
	}

	public void delete(Object object) {

		resetResource();
		if (object instanceof SubGroupBean) {
			SubGroupBean prcService = (SubGroupBean) object;
			// boolean b = serviceService.checkConstraints(obj.getServiceId());
			// if (!b)
			// {
			total++;
			canDelete.add(prcService);
			// }
		} else {
			for (SubGroupBean obj : (List<SubGroupBean>) object) {
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
		for (SubGroupBean smscParam : canDelete) {
			try {
				Vector<Vector<String>> log0bjectRelated = new Vector<Vector<String>>();
				LogUtil.logActionDelete(PanelSubscriber.class.getName(), "SUBSCRIBER", "SUB_ID", "" + smscParam.getSubId() + "", log0bjectRelated);
				getSmscParamService().delete(convertToSubscriber(smscParam), smscParam.getGroups().getGroupId());
				tbl.removeItem(smscParam);
				for (Subscriber subscriber : listSubscriber) {

					if (subscriber.getSubId() == smscParam.getSubId()) {

						listSubscriber.remove(subscriber);
						break;
					}
				}
				deleted++;
				// interactionSelected = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
		if (obj instanceof Groups && !smscDetail.isTreeNodeRoot(obj)) {

			Object smscDetailNode = smscDetail.getParentTreeNode(obj);
			Collection<?> collection = smscDetail.getChildrenTreeNode(smscDetailNode);
			if (collection != null) {
				childNodes.addAll((Collection<? extends Groups>) collection);
			}
		}
		loadDataFromDatabase(obj);
	}

	private void initPagerForTable(SubGroupBean skSearch) {

		SearchEntity searchEntity = new SearchEntity();

		String subIds = "0";

		if (skSearch.getGroups() != null) {
			try {

				List<Subscriber> listSubs = getSmscParamService().findSubcribersByGroup(skSearch.getGroups().getGroupId());
				if (listSubs.size() != 0) {
					for (int i = 0; i < listSubs.size(); i++) {

						subIds = i == 0 ? String.valueOf(listSubs.get(i).getSubId()) : subIds + "," + String.valueOf(listSubs.get(i).getSubId());
					}
				}

				Collection<Groups> childs = (Collection<Groups>) smscDetail.getChildrenTreeNode(skSearch.getGroups());

				if (childs != null && childs.size() != 0) {

					for (Groups groupChild : childs) {

						List<Subscriber> listChildSubs = getSmscParamService().findSubcribersByGroup(groupChild.getGroupId());
						for (Subscriber sub : listChildSubs) {

							subIds += "," + String.valueOf(sub.getSubId());
						}
					}
				}

				skSearch.setAddress(subIds);

			} catch (Exception_Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// subs.setAddress();
		int count = getSmscParamService().count(searchEntity, convertToSubscriber(skSearch), DEFAULT_EXACT_MATCH);
		container.initPager(count);
		// if (count <= 0) {
		// MessageAlerter.showMessageI18n(getWindow(),
		// TM.get("smsc.detail.search.value.emty"));
		// }
	}

	private void loadDataFromDatabase(Object obj) {

		skSearch = new SubGroupBean();
		try {
			if (obj != null && (obj instanceof Groups) && !smscDetail.isTreeNodeRoot(obj)) {
				data.removeAllItems();
				skSearch = new SubGroupBean();
				skSearch.setGroups(((Groups) obj));
				initPagerForTable(skSearch);

			}
			// else if (obj != null && (obj instanceof PrcService)
			// && !smscDetail.isTreeNodeRoot(obj)) {
			// skSearch = new SubGroupBean();
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
				skSearch = new SubGroupBean();
				data.removeAllItems();
				listSubscriber.clear();
				List<SubGroupBean> arrlist = new ArrayList<SubGroupBean>();
				arrlist = convertToSubGroupBean(getSmscParamService().findAllWithoutParameter());

				if (arrlist != null) {

					data.addAll(arrlist);
					for (SubGroupBean subGroupBean : arrlist) {

						listSubscriber.add(convertToSubscriber(subGroupBean));
					}
				}
				if (skSearch != null)
					initPagerForTable(skSearch);
			}
		} catch (Exception e) {
			FormUtil.showException(getWindow(), e);
			e.printStackTrace();
		}
	}

	private List<SubGroupBean> convertToSubGroupBean(List<Subscriber> lst) {

		if (lst != null && lst.size() > 0) {
			List<SubGroupBean> arr = new ArrayList<SubGroupBean>();
			for (int i = 0; i < lst.size(); i++) {
				Subscriber subs = lst.get(i);
				SubGroupBean sub = new SubGroupBean(subs.getSubId(), subs.getMsisdn(), subs.getStatus(), subs.getEmail(), subs.getSex(), subs.getCreateDate(), subs.getAddress(), subs.getBirthDate());

				try {

					List<Groups> groups = getSmscParamService().findGroupsBySub(subs.getSubId());
					if (groups.size() > 0) {

						sub.setGroups(groups.get(0));
					}

				} catch (Exception_Exception e) {
					e.printStackTrace();
				}

				arr.add(sub);
			}
			return arr;
		}
		return null;
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

		if (id instanceof SubGroupBean)
			loadDataFromDatabase(((SubGroupBean) id).getGroups());
		tbl.setMultiSelect(false);
		tbl.select(id);
		tbl.setMultiSelect(true);
	}

	@Override
	public void loadButtonPanel() {

		if (!isLoadedPnlAction) {
			pnlAction = new CommonButtonPanel(this);
			pnlAction.showSearchPanel(true);
			pnlAction.setFromCaption(TM.get(FormSubscriber.class.getName()));
			// pnlAction.setValueForCboField(
			// TM.get("actionparam.table.filteredcolumns").split(","), TM
			// .get("actionparam.table.filteredcolumnscaption")
			// .split(","));
			pnlAction.setValueForCboField(TM.get("subs.table.filteredcolumns").split(","), TM.get("subs.table.filteredcolumnscaption").split(","));
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

		skSearch = new SubGroupBean();
		skSearch.setMsisdn(key);
		DEFAULT_EXACT_MATCH = true;
		SearchEntity searchEntity = new SearchEntity();
		int count = getSmscParamService().count(searchEntity, convertToSubscriber(skSearch), DEFAULT_EXACT_MATCH);

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

		skSearch = new SubGroupBean();
		if (searchObj.getField() == null) {
			skSearch.setMsisdn(searchObj.getKey());
		} else if (searchObj.getField().equals("msisdn")) {
			skSearch.setMsisdn(searchObj.getKey());
		} else if (searchObj.getField().equals("email")) {
			skSearch.setEmail(searchObj.getKey());
		}
		SearchEntity searchEntity = new SearchEntity();
		int count = getSmscParamService().count(searchEntity, convertToSubscriber(skSearch), false);
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
			List<SubGroupBean> arrlist = new ArrayList<SubGroupBean>();
			List<Subscriber> lst = getSmscParamService().findAllWithOrderPaging(searchEntity, convertToSubscriber(skSearch), sortedColumn, asc, start, items, DEFAULT_EXACT_MATCH);
			if (lst != null && lst.size() > 0)
				arrlist = convertToSubGroupBean(lst);
			if (arrlist != null && arrlist.size() > 0)
				data.addAll(arrlist);
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

	public SubGroupBean getSkSearch() {

		return skSearch;
	}

	public SubscriberDTTransferer getSmscParamService() {

		return smscParamService;
	}

	public List<Subscriber> getListSubscriber() {

		return listSubscriber;
	}

}