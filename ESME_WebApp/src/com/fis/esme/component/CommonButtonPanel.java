package com.fis.esme.component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.fis.esme.admin.SessionData;
import com.fis.esme.classes.CheckFormatMSISDNValidator;
import com.fis.esme.util.MessageAlerter;
import com.fis.esme.util.SearchObj;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Reindeer;

import eu.livotov.tpt.i18n.TM;

public class CommonButtonPanel extends VerticalLayout {

	private PanelActionProvider provider;
	private int action = PanelActionProvider.ACTION_NONE;
	private boolean delayedFocus;
	private String permision;

	protected Button btnAdd;
	protected Button btnReregister;
	protected Button btnRevenueReport;
	protected Button btnKIPReport;
	protected Button btnSearchOrAdd;
	protected Button btnSubcSearch;
	protected TextField txtCode;
	protected ComboBox cboCategories;
	protected TextField txtSubcriber;
	private GridLayout tMainLayout;
	private HorizontalLayout tLeftLayout;
	private HorizontalLayout tCenterLayout;
	private Panel tRightPanel;
	private HorizontalLayout tRightLayout;
	private Panel topPanel;

	protected TextField txtKey;
	protected ComboBox cboField;
	protected Button btnSearch;

	private Panel bottomPanel;
	private HorizontalLayout bMainLayout;
	private HorizontalLayout bTopLayout;
	private HorizontalLayout bBottomLayout;

	private HorizontalLayout captionLayout;
	private HorizontalLayout captionLayoutLeft;
	private HorizontalLayout captionLayoutRight;
	private Label lblCaption;

	private Label lblUser;

	private boolean showBottomPanel = false;

	private Object currentForm = null;

	public CommonButtonPanel(PanelActionProvider parent) {

		this.provider = parent;
		if (provider != null)
			this.permision = provider.getPermission();
		initComponent();
		intLayout();
		createListCategories();
		// setValueForComponent();
		// setValueForCategory(parent);
		checkPermission();

		if (TM.get("sso_login").equals("1")) {
			this.removeAllComponents();
			this.setHeight("0px");
			txtSubcriber.setValue(SessionData.getCurrentMSISDN());
			// System.out.println("remove search panel");
		}
	}

	@Override
	public void setMargin(boolean t, boolean r, boolean b, boolean l) {

		super.setMargin(false);
	}

	private void intLayout() {

		this.setSizeFull();
		this.setMargin(false, false, false, false);
		// -top init

		tMainLayout = new GridLayout(3, 1);
		tMainLayout.setSizeFull();
		tMainLayout.setMargin(false);
		tMainLayout.setSpacing(true);
		tMainLayout.setRowExpandRatio(0, 0.3f);
		tMainLayout.setRowExpandRatio(1, 0.3f);
		tMainLayout.setRowExpandRatio(2, 0.4f);

		tMainLayout.addComponent(tLeftLayout, 0, 0);
		tMainLayout.setComponentAlignment(tLeftLayout, Alignment.MIDDLE_RIGHT);
		tMainLayout.addComponent(tCenterLayout, 1, 0);
		tMainLayout.setComponentAlignment(tCenterLayout, Alignment.MIDDLE_CENTER);
		tMainLayout.addComponent(tRightPanel, 2, 0);
		tMainLayout.setComponentAlignment(tRightPanel, Alignment.MIDDLE_RIGHT);
		topPanel.setContent(tMainLayout);

		tLeftLayout.addComponent(btnAdd);
		tLeftLayout.setComponentAlignment(btnAdd, Alignment.MIDDLE_CENTER);
		tLeftLayout.addComponent(btnReregister);
		tLeftLayout.setComponentAlignment(btnReregister, Alignment.MIDDLE_CENTER);
		tLeftLayout.addComponent(btnRevenueReport);
		tLeftLayout.setComponentAlignment(btnRevenueReport, Alignment.MIDDLE_CENTER);
		tLeftLayout.addComponent(btnKIPReport);
		tLeftLayout.setComponentAlignment(btnKIPReport, Alignment.MIDDLE_CENTER);

		tCenterLayout.addComponent(txtCode);
		tCenterLayout.setComponentAlignment(txtCode, Alignment.MIDDLE_CENTER);
		tCenterLayout.addComponent(cboCategories);
		tCenterLayout.setComponentAlignment(cboCategories, Alignment.MIDDLE_CENTER);
		tCenterLayout.addComponent(btnSearchOrAdd);
		tCenterLayout.setComponentAlignment(btnSearchOrAdd, Alignment.MIDDLE_CENTER);

		Label lbl = new Label(TM.get("common.msisdn.country.vn"));
		tRightLayout.addComponent(lbl);
		tRightLayout.setComponentAlignment(lbl, Alignment.MIDDLE_RIGHT);
		tRightLayout.addComponent(txtSubcriber);
		tRightLayout.setComponentAlignment(txtSubcriber, Alignment.MIDDLE_CENTER);
		tRightLayout.addComponent(btnSubcSearch);
		tRightLayout.setComponentAlignment(btnSubcSearch, Alignment.MIDDLE_CENTER);

		tRightPanel.setContent(tRightLayout);

		bTopLayout.addComponent(txtKey);
		bTopLayout.setComponentAlignment(txtKey, Alignment.MIDDLE_LEFT);
		bTopLayout.addComponent(cboField);
		bTopLayout.setComponentAlignment(cboField, Alignment.MIDDLE_LEFT);
		bTopLayout.addComponent(btnSearch);
		bTopLayout.setComponentAlignment(btnSearch, Alignment.MIDDLE_LEFT);

		bMainLayout.addComponent(bTopLayout);
		bMainLayout.setComponentAlignment(bTopLayout, Alignment.MIDDLE_LEFT);
		bMainLayout.addComponent(bBottomLayout);
		bMainLayout.setComponentAlignment(bBottomLayout, Alignment.MIDDLE_RIGHT);

		this.showSearchPanel(false);
		this.setSpacing(false);
	}

	public void showSearchPanel(boolean show) {

		this.setHeight("70");
		// this.addComponent(topPanel);
		this.addComponent(captionLayout);

		createButtonByAlphabel();
		this.addComponent(bottomPanel);

		// this.showBottomPanel = show;
		// if (this.showBottomPanel) {
		// this.setHeight("110");
		// this.addComponent(topPanel);
		// this.addComponent(captionLayout);
		// this.addComponent(bottomPanel);
		// } else {
		// createButtonByAlphabel();
		// this.setHeight("70");
		// this.addComponent(topPanel);
		// this.addComponent(captionLayout);
		// }

		// if (TM.get("sso_login").equals("1")) {
		// this.removeAllComponents();
		// this.setHeight("0px");
		// txtSubcriber.setValue(SessionData.getCurrentMSISDN());
		// // System.out.println("remove search panel 2");
		// }
	}

	public void setEnableBottomPanel(boolean enabled) {

		bottomPanel.setEnabled(enabled);
	}

	public void setSelectedValueForCategory(Class cls) {

		currentForm = cls;
		cboCategories.select(cls);
	}

	public void setValueForCategory(Class cls) {

		// cboCategories.removeAllItems();
		// if (cls == null)
		// return;
		// System.out.println("setValueForCategory:" +
		// cls.getClass().getName());
		// cboCategories.addItem(cls);
		// cboCategories.setItemCaption(cls.getClass(),
		// TM.get(cls.getClass().getName()));

		// for (Class c : createListCategories()) {
		// cboCategories.addItem(c);
		// cboCategories.setItemCaption(c.getClass(),
		// TM.get(c.getClass().getName()));
		// if (c.getClass().equals(cls)) {
		// cboCategories.select(cls);
		// }
		// }
	}

	private void createListCategories() {

		List<Class> list = new ArrayList<Class>();
		// list.add(FormService.class);
		// list.add(FormLanguage.class);

		for (Class cls : list) {
			cboCategories.addItem(cls);
			cboCategories.setItemCaption(cls, TM.get(cls.getName()));
		}
	}

	public void setValueForCboField(String[] fields, String[] fieldsCaption) {

		for (int i = 0; i < fields.length; i++) {
			cboField.addItem(fields[i]);
			cboField.setItemCaption(fields[i], fieldsCaption[i]);
		}
	}

	public void setFromCaption(String caption) {

		lblCaption.setValue(caption);
	}

	private void initComponent() {

		lblCaption = new Label();
		lblCaption.setStyleName("lableformcaption");
		lblUser = new Label();
		lblUser.setValue(SessionData.getUserName());
		lblUser.setStyleName("lableformcaption");
		captionLayoutLeft = new HorizontalLayout();
		captionLayoutLeft.setSizeFull();

		Label lblCaptionIcon = new Label();
		lblCaptionIcon.setIcon(new ThemeResource("icons/32/home.png"));
		lblCaptionIcon.setWidth("20px");
		HorizontalLayout funcLayout = new HorizontalLayout();
		funcLayout.setHeight("22px");
		funcLayout.addComponent(lblCaptionIcon);
		funcLayout.addComponent(lblCaption);
		funcLayout.setComponentAlignment(lblCaptionIcon, Alignment.BOTTOM_LEFT);
		funcLayout.setComponentAlignment(lblCaption, Alignment.BOTTOM_LEFT);
		funcLayout.setSpacing(true);

		HorizontalLayout userLayout = new HorizontalLayout();
		userLayout.setHeight("22px");
		userLayout.setSpacing(true);
		userLayout.addComponent(lblUser);
		userLayout.setComponentAlignment(lblUser, Alignment.BOTTOM_RIGHT);
		Label lblUserIcon = new Label();
		lblUserIcon.setIcon(new ThemeResource("icons/32/user.png"));
		lblUserIcon.setWidth("20px");
		lblUserIcon.setHeight("20px");
		userLayout.addComponent(lblUserIcon);
		userLayout.setComponentAlignment(lblUserIcon, Alignment.BOTTOM_RIGHT);

		captionLayout = new HorizontalLayout();
		captionLayout.setSizeFull();
		captionLayout.setHeight("22px");
		captionLayout.addComponent(funcLayout);
		captionLayout.addComponent(userLayout);
		captionLayout.setComponentAlignment(funcLayout, Alignment.MIDDLE_LEFT);
		captionLayout.setComponentAlignment(userLayout, Alignment.MIDDLE_RIGHT);

		txtCode = new TextField();
		txtCode.setWidth(TM.get("common.form.field.fixedwidth.150"));
		txtCode.setInputPrompt(TM.get("menu.field.code.inputprompt"));

		cboCategories = new ComboBox();
		cboCategories.setWidth(TM.get("common.form.field.fixedwidth.150"));
		cboCategories.setInputPrompt(TM.get("menu.field.categories.inputprompt"));
		cboCategories.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {

				// searchOrAddNew(txtKey.getValue());
			}
		});

		txtSubcriber = new TextField();
		// txtSubcriber.setValue(TM.get("common.msisdn.startswith"));
		txtSubcriber.setWidth(TM.get("common.form.field.fixedwidth.150"));
		txtSubcriber.setInputPrompt(TM.get("menu.field.subc.inputprompt"));

		txtSubcriber.setEnabled(!TM.get("sso_login").equals("1"));

		tLeftLayout = new HorizontalLayout();
		tLeftLayout.setSpacing(true);

		tCenterLayout = new HorizontalLayout();
		tCenterLayout.setSpacing(true);

		tRightLayout = new HorizontalLayout();
		tRightLayout.setSpacing(true);
		tRightPanel = new Panel();
		tRightPanel.setStyleName(Reindeer.PANEL_LIGHT);
		tRightPanel.setWidth("250px");

		topPanel = new Panel();
		topPanel.setSizeFull();
		topPanel.setHeight("38px");

		// -bottom init
		txtKey = new TextField();
		txtKey.setWidth(TM.get("common.form.field.fixedwidth.120"));
		txtKey.setInputPrompt(TM.get("menu.field.key.inputprompt"));
		cboField = new ComboBox();
		cboField.setWidth(TM.get("common.form.field.fixedwidth.120"));
		cboField.setInputPrompt(TM.get("menu.field.field.inputprompt"));

		txtKey.setNullRepresentation("");
		txtCode.setNullRepresentation("");
		txtSubcriber.setNullRepresentation("");

		bBottomLayout = new HorizontalLayout();
		bBottomLayout.setSpacing(true);

		bMainLayout = new HorizontalLayout();
		bMainLayout.setSizeFull();
		bMainLayout.setSpacing(true);
		bMainLayout.setMargin(false);
		bMainLayout.setStyleName("Menu_bMainLayout");

		bTopLayout = new HorizontalLayout();
		bTopLayout.setSpacing(true);

		bottomPanel = new Panel();
		bottomPanel.setSizeFull();
		bottomPanel.setContent(bMainLayout);
		bottomPanel.setHeight("36px");

		ThemeResource icon = new ThemeResource("icons/32/top_add.png");
		btnAdd = createButton(icon);
		btnAdd.setDescription(TM.get("main.common.button.add.tooltip"));
		btnAdd.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				add();
			}
		});
		// this.addComponent(btnAdd);

		icon = new ThemeResource("icons/32/top_refresh.png");
		// this.addComponent(createButton(icon));
		btnReregister = createButton(icon);
		btnReregister.setDescription(TM.get("main.common.button.reregister.tooltip"));
		btnReregister.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				// provider.reregister();
			}
		});
		// this.addComponent(btnEdit);

		icon = new ThemeResource("icons/32/top_report.png");
		btnRevenueReport = createButton(icon);
		btnRevenueReport.setDescription(TM.get("main.common.button.rvreport.tooltip"));
		btnRevenueReport.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				// provider.report();
			}
		});
		// this.addComponent(btnDelete);

		icon = new ThemeResource("icons/32/top_report.png");
		btnKIPReport = createButton(icon);
		btnKIPReport.setDescription(TM.get("main.common.button.kpireport.tooltip"));
		btnKIPReport.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				// provider.report();
			}
		});
		// this.addComponent(btnSearch);

		icon = new ThemeResource("icons/32/top_find.png");
		btnSearchOrAdd = createButton(icon);
		btnSearchOrAdd.setDescription(TM.get("main.common.button.searchoradd.tooltip"));
		btnSearchOrAdd.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				searchOrAddNew(txtCode.getValue());
				// txtCode.focus();
			}
		});

		icon = new ThemeResource("icons/32/gotosubs.png");
		btnSubcSearch = createButton(icon);
		btnSubcSearch.setWidth("30px");
		btnSubcSearch.setHeight("30px");
		btnSubcSearch.setDescription(TM.get("main.common.button.subsearch.tooltip"));
		btnSubcSearch.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				subcSearch();
			}
		});

		icon = new ThemeResource("icons/32/top_find.png");
		btnSearch = createButton(icon);
		btnSearch.setDescription(TM.get("main.common.button.search.tooltip"));
		btnSearch.setStyleName(BaseTheme.BUTTON_LINK);
		btnSearch.setWidth("30px");
		btnSearch.setHeight("30px");
		btnSearch.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				searchByKey();
			}
		});

		bottomPanel.addAction(new Button.ClickShortcut(btnSearch, KeyCode.ENTER));
		topPanel.addAction(new Button.ClickShortcut(btnSearchOrAdd, KeyCode.ENTER));
		tRightPanel.addAction(new Button.ClickShortcut(btnSubcSearch, KeyCode.ENTER));

	}

	private void createButtonByAlphabel() {

		final String all = TM.get("common.alphabel.all");
		String str = all + ",A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
		String[] strAlphabel = str.split(",");
		bBottomLayout.removeAllComponents();
		bBottomLayout.addComponent(new Label("|"));
		for (final String string : strAlphabel) {
			final Button btn = new Button(string);
			btn.setStyleName(BaseTheme.BUTTON_LINK);
			btn.addListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {

					if (all.equals(string)) {
						txtKey.setValue("");
						cboField.setValue(null);
						searchByKey();
						return;
					}

					txtKey.setValue(null);
					SearchObj searchObj = new SearchObj();

					if (cboField.getValue() == null)
						cboField.select(cboField.getItemIds().iterator().next());

					searchObj.setField((cboField.getValue() == null) ? null : cboField.getValue().toString());

					searchObj.setKey(TM.get("common.field.startswith") + string);
					fieldSearch(searchObj);
					btn.focus();
					// MessageAlerter.showMessageI18n(getWindow(),
					// "Chưa hoàn thiện");
				}
			});
			bBottomLayout.addComponent(btn);
			bBottomLayout.setComponentAlignment(btn, Alignment.BOTTOM_CENTER);
			bBottomLayout.addComponent(new Label("|"));
		}
	}

	private Button createButton(ThemeResource icon) {

		Button btn = new Button();
		btn.setStyleName(BaseTheme.BUTTON_LINK);
		btn.setIcon(icon);
		btn.setWidth("30px");
		btn.setHeight("30px");
		return btn;
	}

	public void checkPermission() {

		System.out.println("permision:" + getPermision());

		if (permision == null)
			return;

		btnAdd.setEnabled(getPermision().contains("I"));

		btnSearch.setEnabled(getPermision().contains("F"));
		btnSearchOrAdd.setEnabled(getPermision().contains("F"));
		btnSubcSearch.setEnabled(getPermision().contains("F"));

		txtCode.setEnabled(getPermision().contains("F"));
		txtKey.setEnabled(getPermision().contains("F"));
		txtSubcriber.setEnabled(getPermision().contains("F"));
		cboCategories.setEnabled(getPermision().contains("F"));
		cboField.setEnabled(getPermision().contains("F"));
		bBottomLayout.setEnabled(getPermision().contains("F"));

	}

	public String getPermision() {

		return permision;
	}

	public void setPermision(String permision) {

		this.permision = permision;
	}

	public void delete(Object object) {

		provider.delete(object);
	}

	public void add() {

		// setButtonEnabled(false);
		if (action != PanelActionProvider.ACTION_NONE) {
			return;
		}
		this.action = PanelActionProvider.ACTION_ADD;
		provider.showDialog(null);
	}

	public void edit(Object object) {

		// setButtonEnabled(false);
		if (action != PanelActionProvider.ACTION_NONE) {
			return;
		}
		this.action = PanelActionProvider.ACTION_EDIT;
		provider.showDialog(object);
	}

	public void addCopy(Object object) {

		// setButtonEnabled(false);
		if (action != PanelActionProvider.ACTION_NONE) {
			return;
		}
		this.action = PanelActionProvider.ACTION_ADD_COPY;
		provider.showDialog(object);
	}

	public void searchOrAddNew(Object key) {

		// System.out.println("searchOrAddNew>>");
		if (action != PanelActionProvider.ACTION_NONE) {
			return;
		}
		this.action = PanelActionProvider.ACTION_SEARCH_ADDNEW;
		if (provider == null) {

			return;
		} else {
			// System.out.println("action:" + action);
			if (cboCategories.getValue() != null && (txtCode.getValue() != null)) {
				String code = txtCode.getValue().toString();
				// System.out.println(code);
				if (cboCategories.getValue().equals(currentForm)) {
					// System.out.println("Van form nay");
					provider.searchOrAddNew(code);
				} else {
					// System.out.println("form khacs");
					try {
						Class<ComponentContainer> cls = (Class) cboCategories.getValue();
						// System.out.println("cls:" + cls);
						Constructor<?> cons = cls.getConstructor(String.class);
						// System.out.println("cons:" + cons);
						ComponentContainer relatedForm = (ComponentContainer) cons.newInstance(code);

						SessionData.getCurrentApplication().getApplicatoinMainWindow().callFunction(relatedForm);

					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}

			} else {
				cboCategories.focus();
			}
		}

	}

	private void search() {

		// setButtonEnabled(false);
		if (action != PanelActionProvider.ACTION_NONE) {
			return;
		}
		this.action = PanelActionProvider.ACTION_SEARCH;
		provider.search();
	}

	private void fieldSearch(SearchObj searchObj) {

		// setButtonEnabled(false);
		if (action != PanelActionProvider.ACTION_NONE) {
			return;
		}
		this.action = PanelActionProvider.ACTION_SEARCH;

		if (provider != null)
			provider.fieldSearch(searchObj);
	}

	private void subcSearch() {

		String msisdn = txtSubcriber.getValue().toString();

		if (CheckFormatMSISDNValidator.checkMSISDN(msisdn)) {
			// SessionData
			// .getCurrentApplication()
			// .getApplicatoinMainWindow()
			// .showForm(
			// new PanelFindSubscriber(
			// TM.get(PanelFindSubscriber.class.getName()),
			// getPermision(), msisdn), null);
		} else
			MessageAlerter.showErrorMessageI18n(getWindow(), TM.get("main.common.validate.msisdn.format.invablid"));

	}

	public int getAction() {

		return action;
	}

	public void clearAction() {

		this.action = PanelActionProvider.ACTION_NONE;
	}

	public void setButtonEnabled(boolean enabled) {

		// btnAdd.setEnabled(enabled);
		// btnReregister.setEnabled(enabled);
		// btnRevenueReport.setEnabled(enabled);
		// btnKIPReport.setEnabled(enabled);
		// btnSearchOrAdd.setEnabled(enabled);
		// btnSubcSearch.setEnabled(enabled);
	}

	public void setRowSelected(boolean selected) {

	}

	private void searchByKey() {

		SearchObj searchObj = new SearchObj();
		searchObj.setField((cboField.getValue() == null) ? null : cboField.getValue().toString());
		searchObj.setKey(txtKey.getValue().toString());
		fieldSearch(searchObj);
		txtKey.focus();
	}

	public void setActionProvider(PanelActionProvider provider) {

		this.provider = provider;
	}

	public void SetFocusField(boolean focus) {

		if (focus) {
			txtCode.focus();
		} else {
			return;
		}

	}

	public void setEnableAlphabeltSearch(boolean enabled) {

		bBottomLayout.setEnabled(enabled);
	}

	public void setVisibleTopLayout() {

		bottomPanel.removeAllComponents();
		this.removeComponent(bottomPanel);
	}

	public void reSetValueMSISDN(String msisdn) {

		txtSubcriber.setValue(msisdn);
	}

	public void cusSearchLayout(Component com) {

		bottomPanel.removeAllComponents();
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setSizeFull();
		vLayout.addComponent(com);
		vLayout.setComponentAlignment(com, Alignment.MIDDLE_CENTER);
		bottomPanel.setContent(vLayout);
	}
}
