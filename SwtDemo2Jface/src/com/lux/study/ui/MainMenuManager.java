package com.lux.study.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;

import com.lux.study.controller.DataActionPanelStateManager;
import com.lux.study.event.ActionPanelStateEvent;
import com.lux.study.listener.ActionPanelStateListener;

public class MainMenuManager extends MenuManager implements ActionPanelStateListener {

	private MenuManager fileMenu;
	private MenuManager editMenu;
	private MenuManager helpMenu;
	private MainPanel mainwindow;
	
	private DataActionPanelStateManager dataActionPanelmanager;
	private MenuCallsActionPanel callActionPanel ;
	
	private New newButton;
	private Save saveButton;
	private Delete deleteButton;
	private Cancel cancelButton;
	
	public MainMenuManager(MainPanel mainwindow, DataActionPanelStateManager dataActionPanelmanager,MenuCallsActionPanel dataMenuManager
			 ) {
		fileMenu = new MenuManager("File");
		editMenu = new MenuManager("Edit");
		helpMenu = new MenuManager("Help");
		
		this.callActionPanel=dataMenuManager;
		this.dataActionPanelmanager = dataActionPanelmanager;
		this.mainwindow = mainwindow;
		
		initMenu();
		sighnUp();
	}

	private void initMenu() {
		
		newButton=new New();
		saveButton= new Save();
		deleteButton= new Delete();
		cancelButton= new Cancel();
		
		fileMenu.add(new Exit(mainwindow));
		
		editMenu.add(newButton);
		editMenu.add(saveButton);
		editMenu.add(deleteButton);
		editMenu.add(cancelButton);

		helpMenu.add(new About(mainwindow));

		add(fileMenu);
		add(editMenu);
		add(helpMenu);
		
	}

	@Override
	public void updatePanelState(ActionPanelStateEvent event) {
		
		newButton.setEnabled(event.isNewButtonState());
		saveButton.setEnabled(event.isSaveButtonState());
		deleteButton.setEnabled(event.isDeleteButtonState());
		cancelButton.setEnabled(event.isCancelButtonState());
		
	}

	private class New extends Action {

		public New() {
			super("New", AS_PUSH_BUTTON);
		}

		public void run() {
			callActionPanel.actionNew();
		}
	}

	private class Save extends Action {

		public Save() {
			super("Save", AS_PUSH_BUTTON);

		}

		public void run() {
			callActionPanel.actionSave();
		}
	}

	private class Delete extends Action {

		public Delete() {
			super("Delete", AS_PUSH_BUTTON);

		}

		public void run() {
			callActionPanel.actionDelete();
		}
	}

	private class Cancel extends Action {

		public Cancel() {
			super("Cancel", AS_PUSH_BUTTON);
		}

		public void run() {
			callActionPanel.actionCancel();
		}
	}

	private class About extends Action {
		private ApplicationWindow awin;

		public About(ApplicationWindow awin) {
			super("About", AS_PUSH_BUTTON);
			this.awin = awin;
		}

		public void run() {
			MessageDialog.openInformation(awin.getShell(), "About this program",
					"The version of this application is 1.0");
		}
	}

	private class Exit extends Action {
		private ApplicationWindow awin;

		public Exit(ApplicationWindow awin) {
			super("Exit@Alt+X", AS_PUSH_BUTTON);
			this.awin = awin;
		}

		public void run() {
			boolean result = MessageDialog.openConfirm(awin.getShell(), "Exit", "Areyou surre?");
			if (result) {
				this.awin.close();
			}
		}
	}

	private void sighnUp() {
		dataActionPanelmanager.registerObserver(this);
	}
}
