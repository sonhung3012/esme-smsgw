package com.fis.esme.component;

import java.util.Iterator;

import com.vaadin.event.Action;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import eu.livotov.tpt.gui.dialogs.OptionDialog.OptionDialogResultListener;
import eu.livotov.tpt.gui.dialogs.OptionKind;
import eu.livotov.tpt.i18n.TM;

public class CommonDialog extends Window implements Action.Handler, Window.CloseListener, OptionDialogResultListener
{
	private Form form;
	private HorizontalLayout pnlButton;
	private VerticalLayout mainLayout;// = new VerticalLayout();
	private Button btnAccept;
	private Button btnClose;
	private PanelActionProvider provider;
	
	private Action enterKeyAction = new ShortcutAction("Default key",
			ShortcutAction.KeyCode.ENTER, null);
	private Action escapeKeyAction = new ShortcutAction("Default key",
			ShortcutAction.KeyCode.ESCAPE, null);
	private Action[] actions = new Action[]{ enterKeyAction, escapeKeyAction };
	
	private ConfirmDeletionDialog confirm;
	
	public CommonDialog(String title, Form form, PanelActionProvider provider)
	{
		super(title);
		this.form = form;
		this.form.setValidationVisible(false);
		form.setSizeFull();
		this.provider = provider;
		addActionHandler(this);
		addListener((CloseListener) this);
		this.center();
		this.setModal(true);
		this.setWidth("450px");
		this.setHeight("350px");
		this.setResizable(false);

		initLayout();
	}
	
	private void initLayout()
	{
		mainLayout = (VerticalLayout) this.getContent();
		mainLayout.setMargin(false,false,true,true);
		mainLayout.setSizeFull();
		
		initButtonPanel();
		mainLayout.addComponent(form);
		mainLayout.addComponent(pnlButton);
		
		mainLayout.setComponentAlignment(pnlButton, Alignment.BOTTOM_CENTER);
		mainLayout.setExpandRatio(form, 1f);
	}
	
	private void initButtonPanel()
	{
		pnlButton = new HorizontalLayout();
		btnAccept = new Button(TM.get("form.dialog.button.caption_accept"));
		btnAccept.addListener(new ClickListener()
		{
			
			public void buttonClick(ClickEvent event)
			{
				onAccept();
			}
		});
		btnClose = new Button(TM.get("form.dialog.button.caption_cancel"));
		btnClose.addListener(new ClickListener()
		{
			
			public void buttonClick(ClickEvent event)
			{
				onCancel();
			}
		});

		pnlButton.addComponent(btnAccept);
		pnlButton.addComponent(btnClose);
		pnlButton.setWidth("150px");
		btnClose.setSizeFull();
		btnAccept.setSizeFull();
		pnlButton.setExpandRatio(btnAccept, 1.0f);
		pnlButton.setExpandRatio(btnClose, 1.0f);
		pnlButton.setSpacing(true);
//		pnlalignbutton.addComponent(pnlButton);
//		pnlalignbutton.setComponentAlignment(pnlButton, Alignment.TOP_CENTER);
	}
	
	public boolean isValid()
	{
		boolean valid = true;
		for (final Iterator<?> i = form.getItemPropertyIds().iterator(); i
				.hasNext();)
		{
			Field field = form.getField(i.next());
			
			if (field instanceof Table)
			{
				return true;
			}
			
			if (!field.isValid())
			{
				field.focus();
				if (field instanceof AbstractTextField)
				{
					((AbstractTextField)field).selectAll();
				}
				form.setValidationVisible(true);
				return false;
			}
		}
		return valid;
	}
	
	private void onCancel()
	{
		if (form.isModified())
		{
			if (confirm == null)
			{
				confirm = new ConfirmDeletionDialog(getApplication());
			}
			
			confirm.show(TM.get("form.dialog.confirm.save"), this);
		}
		else
		{
			form.discard();
			super.close();
		}
	}
	
	private void onAccept()
	{
		form.setValidationVisible(false);
		if (isValid())
		{
//			this.setEnabled(false);
			provider.accept();
			super.close();
		}
//		else
//		{
//			form.setValidationVisible(true);
//			System.out.println("total validators: " + form.getValidators().size());
//		}
	}
	
	protected void close() 
	{
        onCancel();
    }
	
	public void setForm(Form form)
	{
		this.form = form;
	}

	@Override
	public void windowClose(CloseEvent e)
	{
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
		
		if (action == escapeKeyAction)
		{
			escapeKeyPressed();
		}
	}

	/**
	 * Called when user presses an ENTER key
	 */
	public void enterKeyPressed()
	{
		onAccept();
	}
	
	/**
	 * Called when user presses an ESC key
	 */
	public void escapeKeyPressed()
	{
		onCancel();
	}

	@Override
	public void dialogClosed(OptionKind choice)
	{
		if (OptionKind.OK.equals(choice))
		{
			onAccept();
		}
		else if (OptionKind.CANCEL.equals(choice))
		{
			form.discard();
			super.close();
		}
	}
}




























