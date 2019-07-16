package com.lux.study.ui;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
public class SWTSimple extends ApplicationWindow {
	private SashForm sashForm;
	private Composite tableComposite,actionComposite;
	
	public SWTSimple() {
		super(null);
		start();
	}

	protected Control createContents(Composite parent) {
	//	GridLayout sashGridLayout= new GridLayout(1,false);
	//	GridData sashGridData= new GridData();

		sashForm = new SashForm(parent, SWT.HORIZONTAL);
	//	sashForm.setLayoutData(sashGridData);

		GridLayout tableGridLayout= new GridLayout(1,false);
		GridLayout actionGridLayout= new GridLayout(4,false);
		
		GridData tableGridData= new GridData();
		

		tableComposite= new Composite(sashForm,SWT.BORDER);
		tableComposite.setLayout(tableGridLayout);
	//	tableComposite.setLayoutData(tableGridData);
			
		actionComposite= new Composite(sashForm,SWT.BORDER);

		shellSets();
		return parent;
	}

	protected MenuManager createMenuManager() {
		MeinMenuManager mainMenuhead= new MeinMenuManager(this);
		return mainMenuhead;
	}
	
	private void shellSets() {
		getShell().setText("Students");
		getShell().setMinimumSize(600, 300);
		getShell().setSize(600, 300);
	}
	
	private void start() {
		this.addMenuBar();
		this.setBlockOnOpen(true);
		this.open();		
		Display.getCurrent().dispose();		
	}
}
