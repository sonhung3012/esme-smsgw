package com.fis.esme.form;

import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.event.ShortcutAction.KeyCode.ESCAPE;

import com.vaadin.Application;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Window;

public class WDialog extends Window implements Handler
{
	private Action enterKeyAction = new ShortcutAction("Default key", ENTER, null);
	private Action escapeKeyAction = new ShortcutAction("Default key", ESCAPE, null);
	private Action[] actions = new Action[]{ enterKeyAction, escapeKeyAction };
	
	private Focusable owner;
	
	public WDialog(String caption)
	{
		this(caption, null);
	}
	
	public WDialog(String caption, Focusable owner)
	{
		this.owner = owner;
		this.addActionHandler(this);
		setModal(true);
		setCaption(caption);
		
		setWidth("50em");
		setHeight("40em");
	}
	
	@Override
	public Action[] getActions(Object target, Object sender)
	{
		return actions;
	}
	
	@Override
	public void handleAction(Action action, Object sender, Object target)
	{
		if (action == enterKeyAction)
		{
			enterKeyPressed();
		}
		else
		
		if (action == escapeKeyAction)
		{
			escapeKeyPressed();
		}
	}
	
	protected void escapeKeyPressed()
	{
		System.out.println("escape");
		close();
	}
	
	@Override
	protected void close()
	{
		super.close();
		if (owner != null)
		{
			owner.focus();
		}
	}
	
	protected void enterKeyPressed()
	{
		
	}
	
	public void show(Application app)
	{
		app.getMainWindow().addWindow(this);
	}
	
	public Focusable getOwner()
	{
		return owner;
	}
	
	public void setOwner(Focusable owner)
	{
		this.owner = owner;
	}
}
