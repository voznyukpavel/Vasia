package com.lux.study.ui;

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
    private static final String FILE_NAME="DataStudent.txt";

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
        menu.add(new LoadFromFileAction());
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
        getShell().setText("Vasia");
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
            super("Cancel", AS_PUSH_BUTTON);
        }

        public void run() {
            actionPanel.cancel();
        }

    }

    private class DeleteAction extends Action {

        public DeleteAction() {
            super("Delete", AS_PUSH_BUTTON);

        }

        public void run() {
            actionPanel.delete();
        }
    }

    private class NewAction extends Action {

        public NewAction() {
            super("New", AS_PUSH_BUTTON);
        }

        public void run() {
            actionPanel.createNew();
        }
    }

    private class SaveAction extends Action {

        public SaveAction() {
            super("Save", AS_PUSH_BUTTON);
        }

        public void run() {
            actionPanel.save();
        }
    }

    private class ExitAction extends Action {

        ApplicationWindow awin;

        public ExitAction(ApplicationWindow awin) {
            super("Exit@Alt+X", AS_PUSH_BUTTON);
            this.awin = awin;
        }

        public void run() {
            if (MessageDialog.openConfirm(awin.getShell(), "Exit", "Areyou surre?")) {
                awin.close();
            }
        }
    }

    private class SaveToFileAction extends Action {

        public SaveToFileAction() {
            super("SaveToFile", AS_PUSH_BUTTON);
        }

        public void run() {
            String path = createFileDialog("Save", SWT.SAVE);
            dataManager.saveDataStorageToFile(path);
        }
    }

    private class LoadFromFileAction extends Action {

        public LoadFromFileAction() {
            super("LoadFromFile", AS_PUSH_BUTTON);
        }

        public void run() {
            String path =createFileDialog("Open", SWT.OPEN);
            dataManager.getDataFromFileToDataStorage(path);
        }
    }

    private class AboutAction extends Action {

        ApplicationWindow awin;

        public AboutAction(ApplicationWindow awin) {
            super("About", AS_PUSH_BUTTON);
            this.awin = awin;
        }

        public void run() {
            MessageDialog.openInformation(awin.getShell(), "About this program",
                    "The version of this application is 1.0");
        }
    }
    
    private String createFileDialog(String action, int swtType) {
        
        FileDialog fd = new FileDialog(getShell(), swtType);
        fd.setText(action);
      //  fd.setFilterPath("C:/");
      //  String[] filterExt = {FILE_NAME};
       // fd.setFilterExtensions(filterExt);
      //  System.out.println(fd.open());
        return  fd.open();
    }
}
