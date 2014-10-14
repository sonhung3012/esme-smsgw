package com.fis.esme.component;

import com.vaadin.Application;
import com.vaadin.ui.Window;

import eu.livotov.tpt.gui.dialogs.OptionDialog;
import eu.livotov.tpt.gui.dialogs.OptionKind;
import eu.livotov.tpt.i18n.TM;

public class ConfirmDeletionDialog extends OptionDialog
{
	public ConfirmDeletionDialog(Application app)
	{
		super(app);
		initComponent();
	}
	
	public ConfirmDeletionDialog(Window window)
	{
		super(window);
		initComponent();
	}

	private void initComponent()
	{
		this.setButtonText(OptionKind.OK, TM.get("form.dialog.confirm.yes"));
		this.setButtonText(OptionKind.CANCEL, TM.get("form.dialog.confirm.no"));
		this.setResizable(false);
	}

	public void show(String message, OptionDialog.OptionDialogResultListener listener)
	{
		this.showConfirmationDialog(TM.get("form.dialog.confirm.title"), message, listener);
	}
}
