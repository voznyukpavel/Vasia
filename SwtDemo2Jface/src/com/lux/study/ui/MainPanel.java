package com.lux.study.ui;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.lux.study.controller.DataManager;


public class MainPanel extends ApplicationWindow {
	
	private SashForm sashForm;
	private TablePanel tablePanel;
	private ActionPanel actionPanel;
	private DataManager dataManager;


	public MainPanel() {
		super(null);
		dataManager=new DataManager();
	}

	protected Control createContents(Composite parent) {

		sashForm = new SashForm(parent, SWT.HORIZONTAL| SWT.SMOOTH);
		
		
		tablePanel= new TablePanel(this,sashForm,dataManager);
		actionPanel= new ActionPanel(this,sashForm,dataManager);
		
		shellSets();
		return parent;
	}

	protected MenuManager createMenuManager() {
		MainMenuManager mainMenuhead = new MainMenuManager(this,dataManager);
	
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
