package com.lux.study.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.lux.study.controller.DataStudentManager;
import com.lux.study.controller.TableManager;

public class MainWindow extends ApplicationWindow {
    
    private static final int WINDOW_WIDTH = 770;
    private static final int WINDOW_HEIGHT = 300;

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
        menu.add(new ExitAction(this));
        return menu;
    }

    private MenuManager createEditMenu() {
        MenuManager menu = new MenuManager("&Edit");

        SaveAction saveAction = new SaveAction();
        DeleteAction deleteAction = new DeleteAction();
        CancelAction cancelAction = new CancelAction();

        menu.add(new NewAction());
        menu.add(saveAction);
        menu.add(deleteAction);
        menu.add(cancelAction);

        menu.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager manager) {
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
}