package com.lux.study.ui;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.lux.study.controller.DataActionPanelStateManager;
import com.lux.study.controller.DataStudentManager;
import com.lux.study.controller.DataTableManager;


public class MainPanel extends ApplicationWindow {
	
	private SashForm sashForm;
	private TablePanel tablePanel;
	private ActionPanel actionPanel;
	private DataStudentManager dataManager;
	private DataTableManager dataTableManager;
	private DataActionPanelStateManager dataActionPanelStateManager ;
	private MenuCallsActionPanel dataMenuManager;

	public MainPanel() {
		super(null);
		
		dataManager=new DataStudentManager();
		dataTableManager= new DataTableManager();
		dataMenuManager= new MenuCallsActionPanel();		
		dataActionPanelStateManager= new DataActionPanelStateManager();

	}

	protected Control createContents(Composite parent) {

		sashForm = new SashForm(parent, SWT.HORIZONTAL| SWT.SMOOTH);
	
		tablePanel= new TablePanel(this,sashForm,dataManager,dataTableManager);
		actionPanel= new ActionPanel(this,sashForm,dataManager,dataTableManager,dataActionPanelStateManager,dataMenuManager);
		
		shellSets();
		return parent;
	}

	@Override
	protected MenuManager createMenuManager() {
		MainMenuManager mainMenuhead = new MainMenuManager(this,dataActionPanelStateManager,dataMenuManager); 
		return mainMenuhead;
	}

	private void shellSets() {
		getShell().setText("Students");
		getShell().setMinimumSize(770, 300);
		getShell().setSize(770, 300);
	}

	public void run() {
		this.addMenuBar();
		this.setBlockOnOpen(true);
		this.open();
		Display.getCurrent().dispose();
	}
	
	
}
