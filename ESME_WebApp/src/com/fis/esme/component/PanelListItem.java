package com.fis.esme.component;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

public class PanelListItem extends Panel
{
	
	private Label lblContent;
	private Embedded embedded;
	private List<String> items = new ArrayList<String>();
	private VerticalLayout mainLayout;
	private String iconItemStyle;
	private Button btnShow;
	protected Window subWindow;
	
	public PanelListItem()
	{
		init();
		initActionButton();
		this.setContent(mainLayout);
	}
	
	public PanelListItem(String caption, ThemeResource image)
	{
		this();
		this.setCaption(caption);
		this.setImage(image);
	}
	
	private void init()
	{
		mainLayout = new VerticalLayout();
		embedded = new Embedded();
		
		lblContent = new Label();
		lblContent.setContentMode(Label.CONTENT_XHTML);
		
		btnShow = new Button("Ảnh minh họa");
		btnShow.setStyleName(BaseTheme.BUTTON_LINK);
		btnShow.setVisible(false);
		
		mainLayout.addComponent(embedded);
		mainLayout.setComponentAlignment(embedded, Alignment.TOP_CENTER);
		mainLayout.addComponent(lblContent);
		mainLayout.addComponent(btnShow);
		mainLayout.setComponentAlignment(btnShow, Alignment.TOP_RIGHT);
		
		mainLayout.setMargin(false, true, true, false);
		
		this.setIconItemStyle("ul");
		fill();
	}
	
	public void addItem(String item)
	{
		items.add(item);
		fill();
	}
	
	public void addContent(String itemContent)
	{
		if (itemContent == null || itemContent.trim().length() <= 0)
		{
			return;
		}
		clearAllItems();
		String[] contents = itemContent.split("@\\$");
		for (String item : contents)
		{
			items.add(item);
		}
		fill();
	}
	
	public void addItem(int index, String item)
	{
		items.add(index, item);
		fill();
	}
	
	public void removeItem(String item)
	{
		if (items.size() > 0)
		{
			items.remove(item);
			fill();
		}
	}
	
	public void removeItem(int index)
	{
		if (items.size() > 0)
		{
			items.remove(index);
			fill();
		}
	}
	
	public void clearAllItems()
	{
		items.clear();
		fill();
	}
	
	public void clearImage()
	{
		embedded.setIcon(null);
	}
	
	public void setImage(ThemeResource image)
	{
		embedded.setIcon(image);
	}
	
	public String getIconItemStyle()
	{
		return iconItemStyle;
	}
	
	public void setIconItemStyle(String htmltag)
	{
		this.iconItemStyle = htmltag;
	}
	
	private void fill()
	{
		String str = "";
		str += "<" + this.iconItemStyle + ">";
		for (String item : items)
		{
			str += "<li>" + item + "</li>";
		}
		str += "</" + this.iconItemStyle + ">";
		lblContent.setValue(str);
		
	}
	
	public void setVisionButtonShowImage(boolean isCheck)
	{
		btnShow.setVisible(isCheck);
	}
	
	private void initActionButton()
	{
		btnShow.addListener(new Button.ClickListener()
		{
			
			@Override
			public void buttonClick(ClickEvent event)
			{
				if (subWindow == null)
				{
					Embedded embeddedImage = new Embedded();
					embeddedImage.setSizeFull();
					embeddedImage.setIcon(new ThemeResource(
							"icons/provisioning/danhsach.png"));
					subWindow = new Window("Ảnh minh họa");
					subWindow.setPositionX(700);
					subWindow.setPositionY(100);
					subWindow.setHeight("290px");
					subWindow.setWidth("300px");
					subWindow.setImmediate(true);
					subWindow.addComponent(embeddedImage);
				}
				if (!getApplication().getMainWindow().getChildWindows()
						.contains(subWindow))
				{
					getApplication().getMainWindow().addWindow(subWindow);
				}
				
			}
		});
	}
	
}
