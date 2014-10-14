package com.fis.esme.component;

import java.util.Collection;
import java.util.Set;

import com.fis.esme.admin.SessionData;
import com.fis.esme.component.PagingComponent.ChangePageEvent;
import com.fis.esme.component.PagingComponent.PagingComponentListener;
import com.fis.esme.util.MessageAlerter;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.filter.Or;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Reindeer;

import eu.livotov.tpt.i18n.TM;

public class TableContainer extends CustomComponent// implements Handler
{

	private TextField txtFilter;
	private Button btnSearch;
	private HorizontalLayout filterMainLayout;

	private Table table;
	// private HorizontalLayout pnControl;
	private Panel mainPanel;
	private VerticalLayout mainLayout;
	private FormLayout pncombo;
	private ComboBox cboItemsPerPage = new ComboBox();
	private PagingComponent pager;
	private PagingComponentListener listener;
	private int currentStartCount = 0;
	private int totalItems = 0;
	private GridLayout paperLayout;
	private HorizontalLayout actionButtonLayout;
	private HorizontalLayout headerSearchLayout;
	private HorizontalLayout headerButtonLayout;
	private HorizontalLayout countLayout;
	private Label lblCount;

	private String[] filteredColumns = null;

	private Button btnDeleteAll;
	private Button btnAdd;
	private Button btnAddCopy;

	private HorizontalLayout dellAllButtonLayout;
	private HorizontalLayout addNewButtonLayout;
	private HorizontalLayout addCopyButtonLayout;

	public TableContainer(Table table) {
		this(table, new PagingComponentListener() {
			public void displayPage(ChangePageEvent event) {
			}
		});
	}

	public TableContainer(Table table, PagingComponentListener pageListener) {
		this(table, pageListener, 5);
	}

	public TableContainer(Table table, PagingComponentListener pageListener,
			int defaultItemsPerPage) {
		this.table = table;
		this.listener = pageListener;
		table.setCaption(null);
		table.setColumnWidth(null, 30);
		initLayout(defaultItemsPerPage);
		// if (table instanceof CustomTable) {
		// CustomTable tbl = (CustomTable) table;
		// btnDeleteAll.setEnabled(tbl.getPnlAction().getPermision()
		// .contains("D"));
		// btnAdd.setEnabled(tbl.getPnlAction().getPermision().contains("I"));
		// }
	}

	public void removePaperLayout() {
		mainLayout.removeComponent(paperLayout);
	}

	private void initLayout(int defaultItemsPerPage) {
		innitConponent();

		mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);

		mainPanel = new Panel();
		mainPanel.setSizeFull();
		mainPanel.setContent(mainLayout);

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		txtFilter = new TextField();

		txtFilter.setInputPrompt(TM.get("main.common.txt.filter.description"));
		btnSearch = new Button(TM.get("main.common.button.filter.tooltip"),
				new Button.ClickListener() {

					public void buttonClick(ClickEvent event) {
						filter();
						// System.out.println("before focusing textfield");
						// txtFilter.focus();
					}
				});
		btnSearch.setDescription(TM.get("main.common.button.filter.tooltip"));
		btnSearch.setIcon(new ThemeResource("icons/32/filter.png"));
		btnSearch.setStyleName(BaseTheme.BUTTON_LINK);
		btnSearch.setWidth("30px");
		btnSearch.setHeight("30px");
		btnSearch.setCaption(null);

		actionButtonLayout = new HorizontalLayout();
		actionButtonLayout.setSizeFull();
		actionButtonLayout.setWidth("90px");

		dellAllButtonLayout = new HorizontalLayout();
		dellAllButtonLayout.setSizeFull();

		addCopyButtonLayout = new HorizontalLayout();
		addCopyButtonLayout.setSizeFull();

		addNewButtonLayout = new HorizontalLayout();
		addNewButtonLayout.setSizeFull();

		actionButtonLayout.addComponent(addNewButtonLayout);
		actionButtonLayout.addComponent(addCopyButtonLayout);
		actionButtonLayout.addComponent(dellAllButtonLayout);

		pncombo = new FormLayout();
		pncombo.setWidth("200px");
		pncombo.setMargin(false, false, false, true);
		pncombo.setSpacing(false);

		headerSearchLayout = new HorizontalLayout();
		headerSearchLayout.setSizeFull();
		headerSearchLayout.setWidth("300px");

		headerButtonLayout = new HorizontalLayout();
		headerButtonLayout.setSizeFull();

		Panel filterPanel = new Panel();
		filterPanel.setStyleName(Reindeer.PANEL_LIGHT);
		filterPanel.setWidth("170px");
		HorizontalLayout filterLayout = new HorizontalLayout();
		filterLayout.setSizeFull();
		filterLayout.addComponent(txtFilter);
		filterLayout.addComponent(btnSearch);
		filterLayout.setComponentAlignment(txtFilter, Alignment.MIDDLE_LEFT);
		filterLayout.setComponentAlignment(btnSearch, Alignment.MIDDLE_LEFT);
		filterPanel.setContent(filterLayout);

		filterMainLayout = new HorizontalLayout();
		filterMainLayout.setSizeFull();
		filterMainLayout.addComponent(filterPanel);
		filterMainLayout.setComponentAlignment(filterPanel,
				Alignment.MIDDLE_LEFT);
		filterPanel
				.addAction(new Button.ClickShortcut(btnSearch, KeyCode.ENTER));
		filterMainLayout.setHeight("30px");

		filterMainLayout.addComponent(pncombo);
		filterMainLayout.setComponentAlignment(pncombo, Alignment.MIDDLE_LEFT);

		filterMainLayout.addComponent(headerSearchLayout);
		filterMainLayout.setComponentAlignment(headerSearchLayout,
				Alignment.MIDDLE_LEFT);

		filterMainLayout.addComponent(headerButtonLayout);
		filterMainLayout.setComponentAlignment(headerButtonLayout,
				Alignment.MIDDLE_LEFT);

		filterMainLayout.addComponent(actionButtonLayout);
		filterMainLayout.setComponentAlignment(actionButtonLayout,
				Alignment.MIDDLE_RIGHT);

		mainLayout.addComponent(filterMainLayout);

		// pnControl = new HorizontalLayout();
		// pnControl.setSizeFull();
		// pnControl.setMargin(false,false,true,false);
		// pnControl.setHeight("180px");
		// mainLayout.addComponent(pnControl);
		mainLayout.addComponent(table);
		mainLayout.setExpandRatio(table, 1.0f);

		setCompositionRoot(mainPanel);

		cboItemsPerPage = new ComboBox();
		cboItemsPerPage.setWidth("120px");
		cboItemsPerPage.setDescription(TM.get("pager.page.cbbnumofpage"));
		cboItemsPerPage.setImmediate(true);
		String[] itemps = TM.get("pager.itempsperpage.itemps").split(",");
		for (int i = 0; i < itemps.length; i++) {
			int itemp = Integer.parseInt(itemps[i]);
			cboItemsPerPage.addItem(itemp);
			cboItemsPerPage.setItemCaption(itemp,
					TM.get("pager.itempsperpage.caption", itemp));
		}

		cboItemsPerPage.setNullSelectionAllowed(false);
		cboItemsPerPage.setValue(defaultItemsPerPage);
		cboItemsPerPage.addListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				initPager(totalItems);
			}
		});

		countLayout = new HorizontalLayout();
		countLayout.setSizeFull();
		lblCount = new Label();
		countLayout.addComponent(lblCount);
		countLayout.setComponentAlignment(lblCount, Alignment.MIDDLE_RIGHT);

		paperLayout = new GridLayout(3, 1);
		paperLayout.setWidth("100%");
		paperLayout.setHeight("30px");
		mainLayout.addComponent(paperLayout);

		pager = new PagingComponent(0, 0, 0, listener);
		pager.setSizeFull();
		pager.setWidth("98%");

		paperLayout.addComponent(pager, 2, 0);
		paperLayout.setComponentAlignment(pager, Alignment.MIDDLE_RIGHT);
		paperLayout.addComponent(cboItemsPerPage, 0, 0);
		paperLayout.setComponentAlignment(cboItemsPerPage,
				Alignment.MIDDLE_LEFT);

		// paperLayout.addComponent(deleteButtonLayout, 0, 0);
		paperLayout.addComponent(countLayout, 1, 0);

		btnDeleteAll = new Button(TM.get("table.common.btn.deleteall.caption"));
		btnDeleteAll.setDescription(TM
				.get("table.common.btn.deleteall.description"));
		btnDeleteAll.setIcon(new ThemeResource("icons/32/delete.png"));
		btnDeleteAll.setStyleName(BaseTheme.BUTTON_LINK);
		btnDeleteAll.setWidth("30px");
		btnDeleteAll.setHeight("30px");
		btnDeleteAll.setCaption(null);
		btnDeleteAll.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				deleteAllItemSelected();
			}
		});

		btnAdd = new Button(TM.get("table.common.btn.addnew.caption"));
		btnAdd.setDescription(TM.get("table.common.btn.addnew.description"));
		btnAdd.setIcon(new ThemeResource("icons/32/add.png"));
		btnAdd.setStyleName(BaseTheme.BUTTON_LINK);
		btnAdd.setWidth("30px");
		btnAdd.setHeight("30px");
		btnAdd.setCaption(null);
		btnAdd.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				((CustomTable) getTable()).getPnlAction().add();
			}
		});

		btnAddCopy = new Button(TM.get("table.common.btn.addcopy.caption"));
		btnAddCopy.setDescription(TM
				.get("table.common.btn.addcopy.description"));
		btnAddCopy.setIcon(new ThemeResource("icons/32/addcopy.png"));
		btnAddCopy.setStyleName(BaseTheme.BUTTON_LINK);
		btnAddCopy.setWidth("30px");
		btnAddCopy.setHeight("30px");
		btnAddCopy.setCaption(null);
		btnAddCopy.addListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				Set coll = (Set) ((CustomTable) getTable()).getValue();

				if (coll != null && coll.size() > 0)
					((CustomTable) getTable()).getPnlAction().addCopy(
							((CustomTable) getTable()).getValue());
				else {
					MessageAlerter.showMessageI18n(btnAddCopy.getWindow(),
							TM.get("common.table.addcopy.emty"));
				}
			}
		});

		setVidibleButtonAddNew(true);
		setVidibleButtonAddCopy(true);
		setVidibleButtonDeleteAll(true);
	}

	public void setCombo(ComboBox cb) {
		pncombo.removeAllComponents();
		pncombo.addComponent(cb);
	}

	// public void setActionPanel(CommonButtonPanel pnlAction) {
	// pnControl.removeAllComponents();
	// System.out.println(pnlAction == null);
	// pnControl.addComponent(pnlAction);
	// }

	private void innitConponent() {
		table.setSizeFull();
		table.setSelectable(true);
		table.setImmediate(true);
		table.setNullSelectionAllowed(false);
		table.addListener(new Container.ItemSetChangeListener() {
			public void containerItemSetChange(ItemSetChangeEvent event) {
				table.setValue(null);
			}
		});
	}

	public void initPager(int total, int displayedPages) {
		if (total > 0) {
			paperLayout.setVisible(true);
			this.totalItems = total;
			if (pager != null) {
				paperLayout.removeComponent(pager);
			}
			int itemPerPage = (Integer) cboItemsPerPage.getValue();
			pager = new PagingComponent(itemPerPage, displayedPages, total,
					listener);
			pager.setStyleNameCurrentButtonState("current-page");
			pager.setStyleNameButtonsFirstAndLast("disabled-page");

			paperLayout.addComponent(pager, 2, 0);
			paperLayout.setComponentAlignment(pager, Alignment.MIDDLE_RIGHT);
			paperLayout.setColumnExpandRatio(0, 0.2f);
			paperLayout.setColumnExpandRatio(1, 0.3f);
			paperLayout.setColumnExpandRatio(2, 0.5f);
		} else {
			paperLayout.setVisible(false);
			table.getContainerDataSource().removeAllItems();
		}
	}

	public void setVisiblePaperLayout(boolean visibled) {
		paperLayout.setVisible(visibled);
	}

	public void reInitPagerAfterDelete() {

	}

	public void reSetLblCountByDelete(int deleted) {
		int pageSize = ((currentStartCount + 1) + table.size());
		totalItems = totalItems - deleted;
		if (((pageSize - deleted) == getTotalItem())
				|| (deleted == Integer
						.parseInt(TM.get("pager.page.rowsinpage")))
				|| (((pageSize - deleted) - currentStartCount) <= getTotalItem())) {
			initPager(getTotalItem());
		} else {
			lblCount.setValue(currentStartCount + " - " + (pageSize - deleted)
					+ " / " + getTotalItem());
		}
	}

	public void setLblCount(int start) {
		currentStartCount = (start + 1);
		lblCount.setValue(currentStartCount + " - " + (start + table.size())
				+ " / " + getTotalItem());
	}

	// public void initPager(int displayedPages)
	// {
	// initPager(null, displayedPages);
	// }

	public void initPager(int total) {
		initPager(total, Integer.parseInt(TM.get("pager.page.displaypage")));
	}

	// public void initPager()
	// {
	// initPager(null, 11);
	// }
	public void removePaper() {
		mainLayout.removeComponent(paperLayout);
	}

	public void removeLayoutPaper() {
		mainLayout.removeComponent(paperLayout);
		btnSearch.setVisible(false);
		txtFilter.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				filter();
			}
		});
	}

	public int getItemPerPage() {
		return (Integer) cboItemsPerPage.getValue();
	}

	public void setTotalItem(int total) {
		this.totalItems = total;
	}

	public int getTotalItem() {
		return this.totalItems;
	}

	public int getCurrentPage() {
		return pager.getCurrentPage();
	}

	public void changePage(int page) {
		pager.changePage(page);
	}

	public void removeClickShortcut() {
		// btnSearch.removeClickShortcut();
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Table getTable() {
		return table;
	}

	public void setFilteredColumns(String[] filteredCols) {
		if (filteredCols == null || filteredCols.length == 0) {
			return;
		}

		this.filteredColumns = filteredCols;
		txtFilter.setDescription(TM.get("main.common.table.filter.caption")
				+ " " + getFilteredColumnHeader(filteredColumns));
	}

	private String getFilteredColumnHeader(String[] cols) {
		String str = "<strong>";
		for (String col : cols) {
			str += table.getColumnHeader(col) + ", ";
		}
		return str.substring(0, str.length() - 2) + "</strong>";
	}

	private void filter() {
		Filterable f = (Filterable) table.getContainerDataSource();
		if (filteredColumns == null || filteredColumns.length == 0) {
			return;
		}

		f.removeAllContainerFilters();
		String txt = txtFilter.toString().trim();
		if (!txt.equals("")) {
			int len = filteredColumns.length;

			DateStringFilter[] arr = new DateStringFilter[len];
			for (int i = 0; i < len; i++) {
				arr[i] = new DateStringFilter(filteredColumns[i], txt, true,
						false);
			}

			f.addContainerFilter(new Or(arr));
		}
		txtFilter.focus();
	}

	public void setEnableDeleteAllButton(boolean enabled) {
		btnDeleteAll.setEnabled(enabled);
	}

	public void setEnableButtonAddNew(boolean enabled) {
		btnAdd.setEnabled(enabled);
	}

	public void setEnableButtonAddCopy(boolean enabled) {
		btnAddCopy.setEnabled(enabled);
	}

	public void setVidibleButtonDeleteAll(boolean visible) {
		dellAllButtonLayout.removeAllComponents();
		if (visible) {
			dellAllButtonLayout.addComponent(btnDeleteAll);
			dellAllButtonLayout.setComponentAlignment(btnDeleteAll,
					Alignment.MIDDLE_RIGHT);
		}
	}

	public void setVidibleButtonAddNew(boolean visible) {
		addNewButtonLayout.removeAllComponents();
		if (visible) {
			addNewButtonLayout.addComponent(btnAdd);
			addNewButtonLayout.setComponentAlignment(btnAdd,
					Alignment.MIDDLE_RIGHT);
		}
	}

	public void setVidibleButtonAddCopy(boolean visible) {
		addCopyButtonLayout.removeAllComponents();
		if (visible) {
			addCopyButtonLayout.addComponent(btnAddCopy);
			addCopyButtonLayout.setComponentAlignment(btnAddCopy,
					Alignment.MIDDLE_RIGHT);
		}
	}

	public void removeLayoutOnlySMS() {
		filterMainLayout.removeComponent(pncombo);
	}

	public void removeDeleteAllLayout() {
		filterMainLayout.removeComponent(actionButtonLayout);
	}

	public void removeHeaderSearchLayout() {
		filterMainLayout.removeComponent(headerSearchLayout);
	}

	public void removeHeaderButtonLayout() {
		filterMainLayout.removeComponent(headerButtonLayout);
	}

	public void removePanelCombo() {
		filterMainLayout.removeComponent(pncombo);
	}

	public void addHeaderSearchLayout(TableHeaderSearchPanel panel) {
		headerSearchLayout.addComponent(panel);
		headerSearchLayout.setComponentAlignment(panel, Alignment.MIDDLE_LEFT);
	}

	public void addHeaderButtonLayout(Component layout) {
		headerButtonLayout.addComponent(layout);
		headerButtonLayout
				.setComponentAlignment(layout, Alignment.MIDDLE_RIGHT);
	}

	public void deleteAllItemSelected() {

	}

	public void setTableHeight(String height) {
		table.setHeight(height);
	}

	public void setVisibleBorderMainLayout(boolean visible) {
		if (!visible)
			mainPanel.setStyleName(Reindeer.PANEL_LIGHT);
	}

	public void setMainPanelStyleName(String styleName) {
		mainPanel.setStyleName(styleName);
	}

	public void addComponentToMainLayout(Component c, int index) {
		mainLayout.addComponent(c, index);
	}
	public void rePainAdd(){
		btnAdd.setIcon(new ThemeResource("icons/32/feedback.png"));
		btnAdd.setDescription(TM.get("main.common.button.feedBackAll.tooltip"));
		actionButtonLayout.removeComponent(addCopyButtonLayout);
	}
}
