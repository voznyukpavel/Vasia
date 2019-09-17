package com.lux.study.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

import com.lux.study.controller.DataStudentManager;
import com.lux.study.controller.TableManager;

public class MainWindow extends ApplicationWindow {

	private static final int WINDOW_WIDTH = 770;
	private static final int WINDOW_HEIGHT = 300;

	private final static Logger logger = Logger.getLogger(MainWindow.class.getName());

	private static final String ABOUT_THIS_PROGRAM="About this program";
	private static final String ARE_YOU_SHURE="Are you surre?";
	private static final String IOERROR="I/O Error";
	private static final String MESSAGE_FILE_READ_ERROR =      "Error occured while file were reading";
	private static final String MESSAGE_FILE_WRITE_ERROR =     "Error occured while file were writing";
	private static final String MESSAGE_FILE_NOT_FOUND_ERROR = "File not found";
	private static final String OPEN="Open";
	private static final String SAVE="Save";
	private static final String SHELL_TITLE="Vasia";
	private static final String THE_VERSION_OF_THIS_APPLICATION="The version of this application is 1.0";

	private SashForm sashForm;
	private ActionPanel actionPanel;
	private DataStudentManager dataManager;
	private TableManager dataTableManager;

	public MainWindow() {
		super(null);

		dataManager = new DataStudentManager();
		dataTableManager = new TableManager();
	}

	protected Control createContents(Composite parent) {

		sashForm = new SashForm(parent, SWT.HORIZONTAL | SWT.SMOOTH);

		TablePanel tablePanel = new TablePanel(sashForm, dataTableManager);
		dataManager.registerObserver(tablePanel);

		actionPanel = new ActionPanel(this, sashForm, dataManager, dataTableManager);
		shellSets();
		return parent;
	}

	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager();

		menuManager.add(createFileMenu());
		menuManager.add(createEditMenu());
		menuManager.add(createHelpMenu());

		return menuManager;
	}

	private MenuManager createFileMenu() {
		MenuManager menu = new MenuManager("&File");
		menu.add(new SaveToFileAction());
		menu.add(new LoadFromFileAction(this));
		menu.add(new Separator());
		menu.add(new ExitAction(this));

		return menu;
	}

	private MenuManager createEditMenu() {
		MenuManager menu = new MenuManager("&Edit");
		NewAction newAction = new NewAction();
		SaveAction saveAction = new SaveAction();
		DeleteAction deleteAction = new DeleteAction();
		CancelAction cancelAction = new CancelAction();

		menu.add(newAction);
		menu.add(saveAction);
		menu.add(deleteAction);
		menu.add(cancelAction);

		menu.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				newAction.setEnabled(actionPanel.isEnabledNewButton());
				saveAction.setEnabled(actionPanel.isEnabledSaveButton());
				deleteAction.setEnabled(actionPanel.isEnabledDeleteButton());
				cancelAction.setEnabled(actionPanel.isEnabledCancelButton());
			}
		});
		return menu;
	}

	private MenuManager createHelpMenu() {
		MenuManager menu = new MenuManager("&Help");
		menu.add(new AboutAction(this));
		return menu;
	}

	private void shellSets() {
		getShell().setText(SHELL_TITLE);
		getShell().setMinimumSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		getShell().setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		this.addMenuBar();
		this.setBlockOnOpen(true);
		this.open();
		Display.getCurrent().dispose();
	}

	private class CancelAction extends Action {

		public CancelAction() {
			super(DataAction.CANCEL.getName(), AS_PUSH_BUTTON);
		}

		public void run() {
			actionPanel.cancel();
		}

	}

	private class DeleteAction extends Action {

		public DeleteAction() {
			super(DataAction.DELETE.getName(), AS_PUSH_BUTTON);

		}

		public void run() {
			actionPanel.delete();
		}
	}

	private class NewAction extends Action {

		public NewAction() {
			super(DataAction.NEW.getName(), AS_PUSH_BUTTON);
		}

		public void run() {
			actionPanel.createNew();
		}
	}

	private class SaveAction extends Action {

		public SaveAction() {
			super(DataAction.SAVE.getName(), AS_PUSH_BUTTON);
		}

		public void run() {
			if (actionPanel.tryToSave()) {
				actionPanel.setState(ActionPanelState.START);
			}
		}
	}

	private class ExitAction extends Action {

		ApplicationWindow awin;

		public ExitAction(ApplicationWindow awin) {
			super("Exit@Alt+X", AS_PUSH_BUTTON);
			this.awin = awin;
		}

		public void run() {
			if (MessageDialog.openConfirm(awin.getShell(), DataAction.EXIT.getName(), ARE_YOU_SHURE)) {
				awin.close();
			}
		}
	}

	private class SaveToFileAction extends Action {

		public SaveToFileAction() {
			super(DataAction.SAVE_TO_FILE.getName(), AS_PUSH_BUTTON);
		}

		public void run() {
			File file = createFileDialog(SAVE, SWT.SAVE);
			try {
				if (file != null) {
					dataManager.saveDataStorageToFile(file);
				}
			} catch (IOException e) {
				logger.log(Level.SEVERE, MESSAGE_FILE_WRITE_ERROR + " ", e);
			}
		}
	}

	private class LoadFromFileAction extends Action {
		ApplicationWindow awin;

		public LoadFromFileAction(ApplicationWindow awin) {
			super(DataAction.LOAD_FROM_FILE.getName(), AS_PUSH_BUTTON);
			this.awin = awin;
		}

		public void run() {
			File file = createFileDialog(OPEN, SWT.OPEN);
			try {
				if (file != null) {
					dataManager.getDataFromFileToDataStorage(file);
					actionPanel.setLoadState();
				}
			} catch (FileNotFoundException e) {
				MessageDialog.openError(awin.getShell(), IOERROR, MESSAGE_FILE_NOT_FOUND_ERROR);
			} catch (Exception e) {
				MessageDialog.openError(awin.getShell(), IOERROR, MESSAGE_FILE_READ_ERROR);
			}
		}
	}

	private File createFileDialog(String action, int swtType) {
		FileDialog fd = new FileDialog(getShell(), swtType);
		fd.setText(action);
		fd.setText(OPEN);
		String[] filterExt = { "*.txt", "*.*" };
		fd.setFilterExtensions(filterExt);
		File file = null;
		if (fd.open() != null) {
			file = new File(fd.getFilterPath() + "\\" + fd.getFileName());
		}
		return file;
	}

	private class AboutAction extends Action {

		ApplicationWindow awin;

		public AboutAction(ApplicationWindow awin) {
			super(DataAction.ABOUT.getName(), AS_PUSH_BUTTON);
			this.awin = awin;
		}

		public void run() {
			MessageDialog.openInformation(awin.getShell(), ABOUT_THIS_PROGRAM,
					THE_VERSION_OF_THIS_APPLICATION);
		}
	}
}
