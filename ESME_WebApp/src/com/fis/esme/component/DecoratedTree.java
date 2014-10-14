package com.fis.esme.component;

import java.util.HashMap;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Tree;

public class DecoratedTree extends Tree
{
	private final ThemeResource ICON_ROOT = new ThemeResource("icons/16/home.png");
	private final ThemeResource ICON_SERVICE = new ThemeResource("icons/16/service2.png");
	private final ThemeResource ICON_ACTION = new ThemeResource("icons/16/mobile.png");
	private final ThemeResource ICON_PACKAGE = new ThemeResource("icons/16/package.png");
	private final ThemeResource ICON_PROMOTION = new ThemeResource("icons/16/promotion.png");
	private final ThemeResource ICON_CHARGEBAND = new ThemeResource("icons/16/chargeband.png");
	private final ThemeResource ICON_SUB_DETAIL_PARENT = new ThemeResource("icons/16/month_calendar.png");
	private final ThemeResource ICON_SUB_DETAIL = new ThemeResource("icons/16/chart.png");
	private final ThemeResource ICON_OTHERS = new ThemeResource("icons/16/money.png");
	
	private final HashMap<Class<?>, ThemeResource> iconMapper = new HashMap<Class<?>, ThemeResource>();
	
	public DecoratedTree()
	{
		super();
		initTree();
	}

	public DecoratedTree(String caption, Container dataSource)
	{
		super(caption, dataSource);
		initTree();
	}

	public DecoratedTree(String caption)
	{
		super(caption);
		initTree();
	}
	
	private void initIconMapper()
	{
//		iconMapper.put(McaService.class, ICON_SERVICE);
//		iconMapper.put(McaAction.class, ICON_ACTION);
//		iconMapper.put(McaPackage.class, ICON_PACKAGE);
//		iconMapper.put(Promotion.class, ICON_PROMOTION);
//		iconMapper.put(ChargeBand.class, ICON_CHARGEBAND);
		iconMapper.put(String.class, ICON_SUB_DETAIL_PARENT);
		iconMapper.put(Object.class, ICON_OTHERS);
	}
	
	private ThemeResource getIcon(Object itemId)
	{
		if (this.isRoot(itemId))
		{
			if (rootItemIds().size() > 1)
			{
				return ICON_SUB_DETAIL_PARENT;
			}
			else
			{
				return ICON_ROOT;
			}
		}
		
		if (itemId instanceof CommonSubscriberPanel<?, ?>)
		{
			return ICON_SUB_DETAIL;
		}
		
		Class<? extends Object> key = itemId.getClass();
		if (iconMapper.containsKey(key))
		{
			return iconMapper.get(key);
		}
		else
		{
			return iconMapper.get(Object.class);
		}
	}
	
	private void initTree()
	{
		initIconMapper();
		
		this.addContainerProperty("icon", Resource.class, null);
        this.addContainerProperty("caption", String.class, null);
        this.setItemIconPropertyId("icon");
        this.setItemCaptionPropertyId("caption");
        this.setItemCaptionMode(Tree.ITEM_CAPTION_MODE_PROPERTY);
        
        this.setItemStyleGenerator(new ItemStyleGenerator()
		{
			@Override
			public String getStyle(Object itemId)
			{
				return "no-children";
			}
		});
        
        this.setItemDescriptionGenerator(new ItemDescriptionGenerator()
		{
			
			@Override
			public String generateDescription(Component source, Object itemId,
					Object propertyId)
			{
				return itemId.toString();
			}
		});
	}
	
	public Property getContainerProperty(Object itemId,
			Object propertyId)
	{
		Property p = super.getContainerProperty(itemId, propertyId);
		
		if ("icon".equals(propertyId))
		{
			p.setValue(getIcon(itemId));
//			if (this.isRoot(itemId))
//			{
//				p.setValue(ICON_ROOT);
//			}
//			else
//			{
//				p.setValue(getIcon(itemId));
//			}
		}
		else if ("caption".equals(propertyId))
		{
			p.setValue(itemId);
		}
		
		return p;
	}
}
