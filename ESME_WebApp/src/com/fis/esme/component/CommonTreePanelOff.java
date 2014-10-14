package com.fis.esme.component;

import com.fis.esme.classes.PanelTreeProvider;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class CommonTreePanelOff extends VerticalLayout
{
	
	private Panel panelTree = new Panel();
	private HorizontalLayout horizon = new HorizontalLayout();
	private Tree tree;
	private PanelTreeProvider pnTreeProvider;
	
	public CommonTreePanelOff(Tree tree,PanelTreeProvider treeprovider)
	{
		setSizeFull();
		setSpacing(false);
		this.tree = tree;
		this.pnTreeProvider = treeprovider;
		initLeftLayout();
		initComponentLeft();
		// addComponent(verTree);
	}
	
	private void initLeftLayout()
	{
		((VerticalLayout)panelTree.getContent()).setSizeUndefined();
		panelTree.addComponent(tree);
		((VerticalLayout)panelTree.getContent()).setMargin(false);
		panelTree.setHeight("100%");
		
		
		// verTree = new VerticalLayout();
		Label lbl = new Label();
		lbl.setHeight("3px");
		this.addComponent(lbl);
		this.addComponent(horizon);
		lbl = new Label();
		lbl.setHeight("3px");
		this.addComponent(lbl);
		this.addComponent(panelTree);
		// this.setComponentAlignment(horizon, Alignment.MIDDLE_RIGHT);
		this.setExpandRatio(panelTree, 1);
	}
	
	private void initComponentLeft()
	{
		
		tree.setImmediate(true);
		tree.setNullSelectionAllowed(false);
		tree.addListener(new Property.ValueChangeListener()
		{
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				treeValueChanged(tree.getValue());
			}
		});
		tree.addListener(new ItemClickEvent.ItemClickListener()
		{
			@Override
			public void itemClick(ItemClickEvent event)
			{
				Object item = event.getItemId();
				if (event.isDoubleClick())
				{
					if (tree.isExpanded(item))
					{
						tree.collapseItem(event.getItemId());
					}
					else
					{
						tree.expandItem(event.getItemId());
					}
				}
			}
		});
	}
	
	
	private void filterTree(Object obj)
	{
		pnTreeProvider.filterTree(obj);
	}
	
	private void treeValueChanged(Object obj)
	{
		if (obj == null)
		{
			return;
		}
		pnTreeProvider.treeValueChanged(obj);
	}
}
