package com.fis.esme.component;

import java.util.ArrayList;

import org.vaadin.hene.popupbutton.PopupButton;

import com.vaadin.data.Container;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

import eu.livotov.tpt.i18n.TM;

public abstract class CommonSubscriberPanel<BEANTYPE, SEARCHTYPE> extends VerticalLayout
	implements Action.Handler
{
	private HorizontalLayout pnSearch;
	private Button btnSearch;
	
	private Form frmSearch;
	private FormFieldFactory searchFactory;
	
	private Form frmDetail;
	private FormFieldFactory detailFactory;
	
	protected Table tbl;
	protected BeanItemContainer<BEANTYPE> data;
	
	private SEARCHTYPE searcher;
	private boolean isVisible;
//	private DialogSubscriberDetail parent;
//	private DialogSubscriberADVDetail parentADV;
	private Window dialogDetail;
	private Table tblSub;
	
	private String focusProperty;
	private PopupButton btnExtend;

	public CommonSubscriberPanel()
	{
		
	}

	public void doLayout()
	{
		if (!isVisible)
		{
			setSizeFull();
			initDetailForm();
			configTable();
			
			initLayout();
			
			Label lb = new Label("");
			lb.setHeight("3px");
			addComponent(lb);
			
			addComponent(pnSearch);
			
			lb = new Label("");
			lb.setHeight("2px");
			addComponent(lb);
			
			addComponent(tbl);
			this.setExpandRatio(tbl, 1);
			search();
			isVisible = true;
			
			createDetailDialog();
		}
	}
	
	private void initLayout()
	{
		pnSearch = new HorizontalLayout();
		pnSearch.setSpacing(true);
		initTopPanel();
	}
	
	private void configTable()
	{
		initTable();
		tbl.setNullSelectionAllowed(false);
		initTableAction();
	}

	private void initTopPanel()
	{
		initSearchForm();
		initExtended();
		
		btnSearch = new Button(TM.get("main.common.button.search.tooltip"));
		btnSearch.setClickShortcut(KeyCode.ENTER);
		pnSearch.addComponent(frmSearch);
		pnSearch.addComponent(btnSearch);

		btnSearch.addListener(new Button.ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				search();
			}
		});
		
		pnSearch.setComponentAlignment(btnSearch, Alignment.TOP_LEFT);
	}
	
	public void setFocusProperty(String property)
	{
		this.focusProperty = property;
	}
	
//	protected abstract SEARCHTYPE createSearchObject()
//	{
//		Calendar ca = Calendar.getInstance();
//		SearchDetail date = new SearchDetail();
//		date.setToDate(ca.getTime());
//		ca.add(Calendar.MONDAY, -6);
//		date.setFromDate(ca.getTime());
//		return null;
//	}
	
	protected FormFieldFactory createSearchFactory()
	{
		return DefaultFieldFactory.get();
	}
	
	protected Form createSearchForm()
	{
		return new SubDetailSearchForm();
	}
	
	public void setFormSearchCaption(String caption)
	{
		frmSearch.setCaption(caption);
	}
	
	private void initSearchForm()
	{
		frmSearch = createSearchForm();
		frmSearch.setImmediate(false);
		frmSearch.setInvalidCommitted(false);
		frmSearch.setWriteThrough(false);
		
		searcher = createSearchObject();
		searchFactory = createSearchFactory();
		
		frmSearch.setFormFieldFactory(searchFactory);
		frmSearch.setItemDataSource(new BeanItem<SEARCHTYPE>(searcher));
	}
	
	private void changeItem()
	{
		Object id = tbl.getValue();
		boolean idnull = (id==null||data.size()<1);
		
		if (frmDetail != null)
		{
			if (idnull)
			{
				this.removeComponent(frmDetail);
			}
			else
			{
				this.addComponent(frmDetail);
		        frmDetail.setItemDataSource(tbl.getItem(id));
		        frmDetail.setVisibleItemProperties(getVisibleDetailFields());
		        frmDetail.setReadOnly(true);
			}
		}
	}
	
	private void initTableAction()
	{
		tbl.addListener(new Container.ItemSetChangeListener()
		{
			@Override
			public void containerItemSetChange(ItemSetChangeEvent event)
			{
				if (event.getContainer().size() < 1)
				{
					btnExtend.setEnabled(false);
				}
				else
				{
					btnExtend.setEnabled(true);
				}
			}
		});
		
		tbl.addListener(new ItemClickEvent.ItemClickListener()
		{
			
			@Override
			public void itemClick(ItemClickEvent event)
			{
				if (event.isDoubleClick())
				{
					showDialog();
//					getApplication().getMainWindow().addWindow(dialogDetail);
//					setDetailSize();
//					dialogDetail.center();
//					dialogDetail.focus();
					
//					Window win = new Window(frmDetail.getCaption());
//					frmDetail.setCaption(null);
//					win.setCloseShortcut(KeyCode.ESCAPE);
//					win.addComponent(frmDetail);
//					win.setModal(true);
//					getApplication().getMainWindow().addWindow(win);
//					win.center();
//					win.setWidth(frmDetail.getWidth(),frmDetail.getWidthUnits());
//					win.setHeight(frmDetail.getHeight(), frmDetail.getHeightUnits());
//					win.focus();
				}
			}
		});
	}
	
	public void showDialog()
	{
		Object id = tbl.getValue();
		boolean idnull = (id==null||data.size()<1);
		
		if (frmDetail != null)
		{
			if (!idnull)
			{
				if (tblSub == null)
				{
					frmDetail.setItemDataSource(tbl.getItem(id));
				}
				else
				{
					beforeOpenDialog(id);
					Container con = tblSub.getContainerDataSource();
					if (con.size() < 1)
					{
						frmDetail.setItemDataSource(getDefaultItem());
					}
					else
					{
						Object id2 = tblSub.getContainerDataSource().getItemIds().iterator().next();
						tblSub.select(id2);
//						frmDetail.setItemDataSource(tblSub.getItem(id2));
					}
				}
				
				frmDetail.setVisibleItemProperties(getVisibleDetailFields());
//		        frmDetail.setReadOnly(true);
				getApplication().getMainWindow().addWindow(dialogDetail);
				setDetailSize();
				dialogDetail.center();
				dialogDetail.focus();
			}
//			else
//			{
//				this.addComponent(frmDetail);
//		        frmDetail.setItemDataSource(tbl.getItem(id));
//		        frmDetail.setVisibleItemProperties(getVisibleDetailFields());
//		        frmDetail.setReadOnly(true);
//			}
		}
	}
	
	protected void beforeOpenDialog(Object id)
	{
		
	}
	
	protected Item getDefaultItem()
	{
		return null;
	}
	
	private void createDetailDialog()
	{
		dialogDetail = new Window(frmDetail.getCaption())
		{
			@Override
			protected void close()
			{
				super.close();
				tbl.focus();
			}
		};
		
		tblSub = createSubTable();
		configSubTable();
		
		frmDetail.setCaption(null);
		frmDetail.setEnabled(false);
		frmDetail.setStyleName("mca-enabled");
		dialogDetail.setCloseShortcut(KeyCode.ESCAPE);
		dialogDetail.addComponent(frmDetail);
		
		if (tblSub != null)
		{
			dialogDetail.addComponent(tblSub);
//			((VerticalLayout)dialogDetail.getContent()).setExpandRatio(tblSub, 3);
//			((VerticalLayout)dialogDetail.getContent()).setExpandRatio(frmDetail, 1);
//			tblSub.setSizeFull();
		}
		
		dialogDetail.setModal(true);
		((VerticalLayout)dialogDetail.getContent()).setSpacing(false);
		((VerticalLayout)dialogDetail.getContent()).setMargin(false, true, false, true);
		setDetailSize();
//		getApplication().getMainWindow().addWindow(dialogDetail);
//		dialogDetail.center();
	}
	
	protected Table createSubTable()
	{
		return null;
	}
	
	private void configSubTable()
	{
		if (tblSub != null)
		{
			tblSub.addListener(new Property.ValueChangeListener()
			{
				@Override
				public void valueChange(ValueChangeEvent event)
				{
					Item item = tblSub.getItem(event.getProperty().getValue());
					frmDetail.setItemDataSource(item);
					frmDetail.setVisibleItemProperties(getVisibleDetailFields());
				}
			});
		}
	}
	
	protected String getDetailWidth()
	{
		return "500px";
	}
	
	protected String getDetailHeight()
	{
		return "200px";
	}
	
	private void setDetailSize()
	{
		dialogDetail.setSizeUndefined();
		dialogDetail.setHeight(getDetailHeight());
		dialogDetail.setWidth(getDetailWidth());
	}

	private void search()
	{
		frmSearch.setValidationVisible(false);
		data.removeAllItems();
		if (frmSearch.isValid())
		{
			frmSearch.commit();
			
			ArrayList<BEANTYPE> list = getData(searcher);
			if (list == null)
			{
				list = new ArrayList<BEANTYPE>();
			}
			
//			if (parent != null)
//			{
//				int size = list.size();
//				
//				if (size > 0)
//				{
//					parent.showNotification("Tìm thấy " + size + " bản ghi");
//				}
//				else
//				{
//					parent.showNotification("Không tìm thấy bản ghi nào");
//				}
//			}
			
			data.addAll(list);
			if (list.size()>0)
			{
				tbl.select(list.get(0));
//				tbl.focus();
			}
			
			Field field = frmSearch.getField(focusProperty);
			if (field != null)
			{
				field.focus();
				if (field instanceof AbstractTextField)
				{
					((AbstractTextField)field).selectAll();
				}
			}
		}
		else
		{
			frmSearch.setValidationVisible(true);
		}
	}
	
	private void initDetailForm()
	{
		frmDetail = new Form();//createDetailForm();
		if (frmDetail != null)
		{
			detailFactory = createDetailFactory();
			detailFactory = createDetailFactory();
			frmDetail.setFormFieldFactory(detailFactory);
			frmDetail.setWidth("100%");
//			frmDetail.setReadOnly(false);
//			frmDetail.setEnabled(false);
			frmDetail.setVisibleItemProperties(getVisibleDetailFields());
		}
	}
	
	protected Form createDetailForm()
	{
		return new Form();
	}
	
	protected FormFieldFactory createDetailFactory()
	{
		return DefaultFieldFactory.get(); 
	}
	
	public void setFormDetailCaption(String caption)
	{
		if (frmDetail != null)
		{
			frmDetail.setCaption(caption);
		}
	}
	
	private void initExtended()
	{
		btnExtend = new PopupButton(TM.get("main.sub.common.more"));
		btnExtend.setEnabled(false);
//		btnExtend.addClickListener(new SplitButton.SplitButtonClickListener()
//		{
//			
//			@Override
//			public void splitButtonClick(SplitButtonClickEvent event)
//			{
//				boolean vis = btnExtend.isPopupVisible();
//				btnExtend.setPopupVisible(!vis);
//			}
//		});
		
		final VerticalLayout lay = new VerticalLayout();
		final Action[] actions = getActions(null, null);
		if (actions != null && actions.length > 0)
		{
			for (int i = 0; i < actions.length; i++)
			{
				final Action a = actions[i];
				Button btn = createButton(a.getCaption(), null);
				btn.addListener(new Button.ClickListener()
				{
					@Override
					public void buttonClick(ClickEvent event)
					{
						btnExtend.setPopupVisible(false);
						handleAction(a, lay, event.getSource());
					}
				});
				lay.addComponent(btn);
			}
		}
		btnExtend.setComponent(lay);
		lay.setSizeUndefined();
	}

	public void setExtendedVisible(boolean visible)
	{
		if (visible)
		{
			pnSearch.addComponent(btnExtend);
		}
		else
		{
			pnSearch.removeComponent(btnExtend);
		}
	}
	
	private Button createButton(String caption, String icon)
	{
		Button button = new Button(caption);
		button.setStyleName(Reindeer.BUTTON_LINK);
		if (icon != null)
		{
			button.setIcon(new ThemeResource(icon));
		}
		return button;
	}
	
//	protected Action[] initActions()
//	{
//		return null;
//	}
	
	public Action[] getActions(Object target, Object sender)
	{
		return null;
	}
	
	public void handleAction(Action action, Object sender, Object target)
	{
		
	}
	
//	public void setDialogSubscriberDetail(DialogSubscriberDetail parent)
//	{
//		this.parent = parent;
//	}
//	public void setDialogSubscriberADVDetail(DialogSubscriberADVDetail parent)
//	{
//		this.parentADV = parent;
//	}
	public abstract void initTable();
//	public abstract FormFieldFactory initDetailFactory();
	public abstract Object[] getVisibleDetailFields();
	public abstract ArrayList<BEANTYPE> getData(SEARCHTYPE search);
	public abstract SEARCHTYPE createSearchObject();
}
