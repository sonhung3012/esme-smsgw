package com.fis.esme.component;
//package com.fis.prc.component;
//
//import i18n.com.github.peholmst.i18n4vaadin.I18N;
//import i18n.com.github.peholmst.i18n4vaadin.I18NComponent;
//import i18n.com.github.peholmst.i18n4vaadin.I18NListener;
//import i18n.com.github.peholmst.i18n4vaadin.support.I18NComponentSupport;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.Locale;
//import java.util.Set;
//
//import com.fis.prc.admin.AppClient;
//import com.fis.prc.util.LogUtil;
//import com.fis.prc.util.MessageAlerter;
//import com.vaadin.data.Container;
//import com.vaadin.data.Container.ItemSetChangeEvent;
//import com.vaadin.data.Item;
//import com.vaadin.data.Property;
//import com.vaadin.data.Property.ValueChangeEvent;
//import com.vaadin.data.util.BeanItem;
//import com.vaadin.data.util.BeanItemContainer;
//import com.vaadin.event.ItemClickEvent;
//import com.vaadin.terminal.ThemeResource;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Form;
//import com.vaadin.ui.FormFieldFactory;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.Window;
//import com.vaadin.ui.Window.CloseEvent;
//import com.vaadin.ui.themes.BaseTheme;
//
//import eu.livotov.tpt.gui.dialogs.OptionDialog.OptionDialogResultListener;
//import eu.livotov.tpt.gui.dialogs.OptionKind;
//import eu.livotov.tpt.i18n.TM;
//
//public abstract class CommonForm<BEANTYPE> extends VerticalLayout implements
//		PanelActionProvider, OptionDialogResultListener, I18NComponent,
//		I18NListener {
//	private I18NComponentSupport support = new I18NComponentSupport(this);
//	private CommonDialog dialog;
//	private Form form;
//	protected CustomTable tbl;
//	private TableContainer container;
//	protected BeanItemContainer<BEANTYPE> data;
//	private CommonButtonPanel pnlAction;
//	private FormFieldFactory fieldFactory;
//
//	protected int total = 0;
//	private ConfirmDeletionDialog confirm;
//	private ArrayList<BEANTYPE> canDelete = new ArrayList<BEANTYPE>();
//	private String HEIGHT_DIALOG = "350px";
//
//	public CommonForm(String title) {
//		this.setCaption(title);
//		// initLayout();
//	}
//
//	public CommonForm(String strTitle, String strFormlogAccess) {
//		this(strTitle);
//		LogUtil.logAccess(strFormlogAccess);
//	}
//
//	@Override
//	public void localeChanged() {
//		updateLanguage();
//		setFilteredColumns();
//	}
//
//	@Override
//	public void setI18N(I18N i18n) {
//		support.setI18N(i18n);
//	}
//
//	@Override
//	public I18N getI18N() {
//		return support.getI18N();
//	}
//
//	@Override
//	public void attach() {
//		System.out.println("attach");
//		super.attach();
//		getI18N().addListener(this);
//		initLayout();
//	}
//
//	@Override
//	public void detach() {
//		getI18N().removeListener(this);
//		super.detach();
//	}
//
//	private void initLayout() {
//		initComponent();
//		this.setSizeFull();
//		this.addComponent(container);
//		this.setExpandRatio(container, 1.0f);
//		this.setComponentAlignment(container, Alignment.TOP_CENTER);
//
//		HorizontalLayout horizontalLayout = new HorizontalLayout();
//		horizontalLayout.setSpacing(true);
//		this.addComponent(horizontalLayout);
//		this.setComponentAlignment(horizontalLayout, Alignment.TOP_CENTER);
//		Button enButton = new Button("", new Button.ClickListener() {
//
//			public void buttonClick(ClickEvent event) {
//				getI18N().setCurrentLocale(Locale.ENGLISH);
//			}
//		});
//		enButton.addStyleName(BaseTheme.BUTTON_LINK);
//		enButton.setIcon(new ThemeResource("icons/24/en.png"));
//		horizontalLayout.addComponent(enButton);
//
//		Button svButton = new Button("", new Button.ClickListener() {
//
//			public void buttonClick(ClickEvent event) {
//				getI18N().setCurrentLocale(new Locale("vi"));
//			}
//		});
//		svButton.addStyleName(BaseTheme.BUTTON_LINK);
//		svButton.setIcon(new ThemeResource("icons/24/vn.png"));
//		horizontalLayout.addComponent(svButton);
//
//	}
//
//	private void initComponent() {
//		pnlAction = new CommonButtonPanel(this);
//		loadCache();
//		initForm();
//		initTable();
//		initTableAction();
//		initTableContainer();
//		initData();
//	}
//
//	private void initForm() {
//		form = new Form();
//		form.setWriteThrough(false);
//		form.setInvalidCommitted(false);
//		form.setImmediate(false);
//		fieldFactory = createFormFieldFactory();
//		form.setFormFieldFactory(fieldFactory);
//
//		dialog = new CommonDialog(TM.get("service.commondialog.caption"), form,
//				this);
//		dialog.setWidth(TM.get("common.dialog.fixedwidth"));
//		dialog.setHeight(getHeightDialog());
//		dialog.addListener(new Window.CloseListener() {
//
//			@Override
//			public void windowClose(CloseEvent e) {
//				pnlAction.clearAction();
//			}
//		});
//	}
//
//	protected String getHeightDialog() {
//		return HEIGHT_DIALOG;
//	}
//
//	protected void initTableAction() {
//
//		tbl.addListener(new Property.ValueChangeListener() {
//			public void valueChange(ValueChangeEvent event) {
//				Object id = tbl.getValue();
//				pnlAction.setRowSelected(id != null);
//			}
//
//		});
//
//		tbl.addListener(new Container.ItemSetChangeListener() {
//			public void containerItemSetChange(ItemSetChangeEvent event) {
//				pnlAction.setRowSelected(false);
//			}
//		});
//
//		if (getPermission().contains("U")) {
//			tbl.addListener(new ItemClickEvent.ItemClickListener() {
//				private static final long serialVersionUID = 2068314108919135281L;
//
//				public void itemClick(ItemClickEvent event) {
//					if (event.isDoubleClick()) {
//						pnlAction.edit();
//					}
//				}
//			});
//		}
//
//		if (tbl.getContainerDataSource().equals(null)) {
//			pnlAction.setRowSelected(false);
//		}
//
//	}
//
//	private void initTableContainer() {
//		container = new TableContainer(tbl);
//		container.setActionPanel(pnlAction);
////		container.setFilteredColumns(initFilteredColumns());
//	}
//
//	private void setFilteredColumns() {
////		container.setFilteredColumns(initFilteredColumns());
//	}
//
//	private Window createDialog(Item item) {
//		form.setItemDataSource(item);
//		form.setVisibleItemProperties(getVisibleFormFields());
//		form.setValidationVisible(false);
//		form.focus();
//		getWindow().addWindow(dialog);
//		return dialog;
//	}
//
//	public void showDialog() {
//		if (getWindow().getChildWindows().contains(dialog)) {
//			return;
//		}
//		Item item = null;
//		int action = pnlAction.getAction();
//
//		if (action == PanelActionProvider.ACTION_EDIT) {
//			Set<BEANTYPE> set = (Set<BEANTYPE>) tbl.getValue();
//
//			Iterator<BEANTYPE> setSelect = set.iterator();
//
//			BEANTYPE beanItem = null;
//			while (setSelect.hasNext()) {
//				beanItem = setSelect.next();
//			}
//			item = tbl.getItem(beanItem);
//			setOldValue(beanItem);
//		} else if (action == PanelActionProvider.ACTION_ADD_COPY) {
//			Set<BEANTYPE> set = (Set<BEANTYPE>) tbl.getValue();
//			BEANTYPE msv = set.iterator().next();
//
//			BEANTYPE newBean = getNewBean(msv);
//			item = new BeanItem<BEANTYPE>(newBean);
//			setOldValue(null);
//		} else {
//			setOldValue(null);
//			BEANTYPE newBean = getNewBean(null);
//			item = new BeanItem<BEANTYPE>(newBean);
//		}
//		createDialog(item);
//	}
//
//	public void accept() {
//		boolean modified = form.isModified();
//		if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT
//				&& !modified) {
//			pnlAction.clearAction();
//			return;
//		}
//
//		form.commit();
//		BeanItem<BEANTYPE> beanItem = null;
//		beanItem = (BeanItem<BEANTYPE>) form.getItemDataSource();
//		BEANTYPE beantype = beanItem.getBean();
//		if (pnlAction.getAction() == PanelActionProvider.ACTION_ADD
//				|| pnlAction.getAction() == PanelActionProvider.ACTION_ADD_COPY) {
//			addItem(beantype);
//		} else if (pnlAction.getAction() == PanelActionProvider.ACTION_EDIT) {
//			editItem(beantype);
//		}
//
//		pnlAction.clearAction();
//		clearCache();
//	}
//
//	@Override
//	public String getPermission() {
//		return AppClient.getPermission(this.getClass().getName());
//	}
//
//	public void delete() {
//		Set<BEANTYPE> set = (Set<BEANTYPE>) tbl.getValue();
//		resetResource();
//
//		for (BEANTYPE obj : set) {
//			total++;
//			canDelete.add(obj);
//		}
//
//		if (canDelete.size() == 0) {
//			MessageAlerter.showErrorMessageI18n(getWindow(),
//					TM.get("comman.message.delete.error"));
//		} else {
//			String message = TM.get("common.msg.delete.confirm");
//			confirmDeletion(message);
//		}
//	}
//
//	private void resetResource() {
//		canDelete.clear();
//		total = 0;
//	}
//
//	private void confirmDeletion(String message) {
//		if (confirm == null) {
//			confirm = new ConfirmDeletionDialog(getApplication());
//		}
//		confirm.show(message, this);
//	}
//
//	private void initData() {
//		data.removeAllItems();
//		data.addAll(getData());
//	}
//
//	@Override
//	public void dialogClosed(OptionKind option) {
//		if (OptionKind.OK.equals(option)) {
//			if (canDelete != null && canDelete.size() > 0) {
//				doDelete(canDelete);
//			}
//		}
//	}
//
//	@Override
//	public void export() {
//		doExport();
//	}
//
//	public abstract void loadCache();
//
//	public abstract void initTable();
//
//	public abstract void updateLanguage();
//
//	public abstract String[] initFilteredColumns();
//
//	public abstract Object[] getVisibleFormFields();
//
//	public abstract ArrayList<BEANTYPE> getData();
//
//	public abstract void doDelete(ArrayList<BEANTYPE> canDelete);
//
//	public abstract void addItem(BEANTYPE beantype);
//
//	public abstract void editItem(BEANTYPE beantype);
//
//	public abstract void doExport();
//
//	public abstract FormFieldFactory createFormFieldFactory();
//
//	public abstract BEANTYPE getNewBean(BEANTYPE beanSelected);
//
//	public abstract void clearCache();
//
//	public abstract void setOldValue(BEANTYPE beanItem);
//}
