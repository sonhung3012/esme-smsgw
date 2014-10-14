package com.fis.esme.component;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.BaseTheme;

import eu.livotov.tpt.i18n.TM;

public class CopyOfCommonButtonPanel extends HorizontalLayout
{
	private PanelActionProvider provider;
	private int action = PanelActionProvider.ACTION_NONE;
	protected Button btnAdd;
	protected Button btnEdit;
	protected Button btnDelete;
	protected Button btnSearch;
	protected Button btnAddCopy;
	protected Button btnExport;
	private String permision;
	
	public CopyOfCommonButtonPanel(PanelActionProvider parent)
	{
		this.provider = parent;
		this.permision = provider.getPermission();
		initButton();
		checkPermission();
		setRowSelected(false);
	}
	
	private void initButton()
	{
		ThemeResource icon = new ThemeResource("icons/16/add.png");
		btnAdd = createButton(icon);
		btnAdd.setDescription(TM.get("main.common.button.add.tooltip"));
		btnAdd.addListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				add();
			}
		});
		// this.addComponent(btnAdd);
		
		icon = new ThemeResource("icons/16/edit.png");
		// this.addComponent(createButton(icon));
		btnEdit = createButton(icon);
		btnEdit.setDescription(TM.get("main.common.button.edit.tooltip"));
		btnEdit.addListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				edit();
			}
		});
		// this.addComponent(btnEdit);
		
		icon = new ThemeResource("icons/16/delete.png");
		btnDelete = createButton(icon);
		btnDelete.setDescription(TM.get("main.common.button.delete.tooltip"));
		btnDelete.addListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
//				provider.delete(applicationData);
			}
		});
		// this.addComponent(btnDelete);
		
		icon = new ThemeResource("icons/16/search.png");
		btnSearch = createButton(icon);
		btnSearch.setDescription(TM.get("main.common.button.search.tooltip"));
		btnSearch.addListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				search();
			}
		});
		// this.addComponent(btnSearch);
		
		icon = new ThemeResource("icons/16/add_copy.png");
		btnAddCopy = createButton(icon);
		btnAddCopy.setDescription(TM.get("main.common.button.copy.tooltip"));
		btnAddCopy.addListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				addCopy();
			}
		});
		
		icon = new ThemeResource("icons/16/export.png");
		btnExport = createButton(icon);
		btnExport.setDescription("Xuất dữ liệu");
		btnExport.addListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				provider.export();
			}
		});
		
	}
	
	public void checkPermission()
	{
		this.removeAllComponents();
//		System.out.println(permision);
		
		if (permision.contains("I"))
		{
			this.addComponent(btnAdd);
			this.addComponent(btnAddCopy);
		}
		if (permision.contains("U"))
		{
			this.addComponent(btnEdit);
		}
		if (permision.contains("D"))
		{
			this.addComponent(btnDelete);
		}
		if (permision.contains("F"))
		{
			this.addComponent(btnSearch);
		}
//		this.addComponent(btnExport);
		
	}
	
//	public void setAllowSearch(boolean allow)
//	{
//		if (allow)
//		{
//			if (this.getComponentIndex(btnSearch) < 0)
//			{
//				this.addComponent(btnSearch);
//			}
//		}
//		else
//		{
//			this.removeComponent(btnSearch);
//		}
//	}
	
	private Button createButton(ThemeResource icon)
	{
		Button btn = new Button();
		btn.setStyleName(BaseTheme.BUTTON_LINK);
		btn.setIcon(icon);
		btn.setWidth("17px");
		btn.setHeight("17px");
		return btn;
	}
	
	public void add()
	{
//		setButtonEnabled(false);
		if (action != PanelActionProvider.ACTION_NONE)
		{
			return;
		}
		this.action = PanelActionProvider.ACTION_ADD;
//		provider.showDialog();
	}
	
	public void addCopy()
	{
		if (action != PanelActionProvider.ACTION_NONE)
		{
			return;
		}
		this.action = PanelActionProvider.ACTION_ADD_COPY;
//		provider.showDialog();
	}
	
	public void edit()
	{
//		setButtonEnabled(false);
		if (action != PanelActionProvider.ACTION_NONE)
		{
			return;
		}
		this.action = PanelActionProvider.ACTION_EDIT;
//		provider.showDialog();
	}
	
	private void search()
	{
//		setButtonEnabled(false);
		if (action != PanelActionProvider.ACTION_NONE)
		{
			return;
		}
		this.action = PanelActionProvider.ACTION_SEARCH;
//		provider.showDialog();
	}
	
	public int getAction()
	{
		return action;
	}
	
	public void clearAction()
	{
		this.action = PanelActionProvider.ACTION_NONE;
	}
	
	public void setButtonEnabled(boolean enabled)
	{
		btnAdd.setEnabled(enabled);
		btnEdit.setEnabled(enabled);
		btnDelete.setEnabled(enabled);
		btnSearch.setEnabled(enabled);
		btnAddCopy.setEnabled(enabled);
		btnExport.setEnabled(enabled);
	}
	
	public void setRowSelected(boolean selected)
	{
		btnEdit.setEnabled(selected);
		btnDelete.setEnabled(selected);
		btnAddCopy.setEnabled(selected);
	}
}
