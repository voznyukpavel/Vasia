package com.lux.study.ui;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import com.lux.study.controller.DataManager;

public class MainMenuManager  extends MenuManager {

	private MenuManager fileMenu;
	private MenuManager editMenu;
	private MenuManager helpMenu;
	private MainPanel mainwindow;
	private DataManager datamanager;
	

	MainMenuManager(MainPanel mainwindow,DataManager datamanager) {
		fileMenu = new MenuManager("File");
		editMenu = new MenuManager("Edit");
		helpMenu = new MenuManager("Help");
		this.datamanager=datamanager;
		this.mainwindow = mainwindow;
		initMenu();
	}

	private void initMenu() {
		fileMenu.add(new Exit(mainwindow));
		editMenu.add(new New());
		editMenu.add(new Save());
		editMenu.add(new Delete());
		editMenu.add(new Cancel());
		helpMenu.add(new About(mainwindow));
		add(fileMenu);
		add(editMenu);
		add(helpMenu);
	}

	private class New extends Action {

		public New() {
			super("New", AS_PUSH_BUTTON);
		}
		public void run() {
		//	System.out.println(datamanager);			
		}
	}

	private class Save extends Action {

		public Save() {
			super("Save", AS_PUSH_BUTTON);
		}
		public void run() {
		//	datamanager.setAction(DataAction.SAVE);
		}
	}

	private class Delete extends Action {

		public Delete() {
			super("Delete", AS_PUSH_BUTTON);
		}
		public void run() {
		//	datamanager.setAction(DataAction.DELETE);
		}
	}

	private class Cancel extends Action {

		public Cancel() {
			super("Cancel", AS_PUSH_BUTTON);
		}
		public void run() {
		//	datamanager.setAction(DataAction.CANCEL);
		}
	}

	private class About extends Action {
		private ApplicationWindow awin;

		public About(ApplicationWindow awin) {
			super("About", AS_PUSH_BUTTON);
			this.awin = awin;
		}
		public void run() {
			MessageDialog.openInformation(awin.getShell(), "About this program", "The version of this application is 1.0");
		}
	}
	
	private class Exit extends Action {
		private ApplicationWindow awin;
		public Exit(ApplicationWindow awin) {
			super("Exit@Alt+X", AS_PUSH_BUTTON);
			this.awin = awin;
		}
		public void run() {
			this.awin.close();
		}
	}

}
