package com.lux.study.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.lux.study.controller.DataManager;
import com.lux.study.model.DataStudent;

public class TablePanel implements DataStudentObserver {

	private static final String NAME = "Name";
	private static final String GROUP = "Group";
	private static final String DONE = "SWT Done";

	private List<DataStudent> entries;
	private SashForm sashForm;
	private Composite tableComposite;
	private TableViewer tableViever;
	private TableLayout tableLayout;
	private DataManager dataManager;
	private DataStudent dataStudent;
	private MainPanel mainwindow;
	private Table table;

	public TablePanel(MainPanel mainwindow, SashForm sashForm, DataManager dataManager) {
		super();
		this.mainwindow = mainwindow;
		this.dataManager = dataManager;
		this.sashForm = sashForm;
		entries = new LinkedList();
	
		initUI();
		initListeners();
		sighnUp();
	}

	/*
	 * public DataStudent getDataStudent() { return dataStudent; }
	 * 
	 * public void setDataStudent(DataStudent dataStudent) { this.dataStudent =
	 * dataStudent; }
	 */

	private void sighnUp(){
		dataManager.registerObserver(this);
	}
	
	@Override
	public void update(DataStudent dataStudent,DataAction action) {
		switch(action) {
		case NEW:
			addNewInstance(dataStudent);
		case SAVE:
		
		case DELETE:
			
		case CANCEL:
			
		}		
	}

	private void addNewInstance(DataStudent dataStudent) {
		entries.add(dataStudent);
		tableViever.refresh();
	}
	
	private void initUI() {
		GridLayout tableGridLayout = new GridLayout(1, false);
		GridData tableGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);

		tableComposite = new Composite(sashForm, SWT.BORDER);
		tableComposite.setLayout(tableGridLayout);

		tableViever = new TableViewer(tableComposite);
		tableViever.setContentProvider(new StudentContentProvider());
		tableViever.setLabelProvider(new StudentLabelProvider());
		tableViever.setInput(entries);

		tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData(60, true));
		tableLayout.addColumnData(new ColumnWeightData(20, true));
		tableLayout.addColumnData(new ColumnWeightData(20, true));
		tableViever.getTable().setLayout(tableLayout);
		
		table = tableViever.getTable();
		table.setLayoutData(tableGridData);

		TableColumn tableColumn = new TableColumn(table, SWT.PUSH);
		tableColumn.setText(NAME);
		tableColumn = new TableColumn(table, SWT.PUSH);
		tableColumn.setText(GROUP);
		tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setText(DONE);

		for (int i = 0, n = table.getColumnCount(); i < n; i++) {
			table.getColumn(i).pack();
		}

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	
	}
	private void initListeners() {
		table.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				setTextsFromTable(e);
			}
		});
	}

	private void setTextsFromTable(MouseEvent e) {
		IStructuredSelection selection = (IStructuredSelection) tableViever.getSelection();
		Object selections = selection.getFirstElement();
		if (selections != null) {
			//dataManager.setFields((DataStudent) selections);
		}
	}

	private class StudentContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			return ((List<DataStudent>) inputElement).toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	class StudentLabelProvider implements ITableLabelProvider/* , ITableColorProvider */ {
		
		public Image getColumnImage(Object element, int columnIndex) {
			DataStudent datastudent = (DataStudent) element;
			Image imagey = null;
			if (columnIndex == 2) {
				if (datastudent.isSWTDOne()) {
					try {
						imagey = new Image(null, (new FileInputStream("./source/CHECKED.png")));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					try {
						imagey = new Image(null, (new FileInputStream("./source/UCHECKED.png")));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			return imagey;
//			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			DataStudent datastudent = (DataStudent) element;
			switch (columnIndex) {
			case 0:
				return datastudent.getName();
			case 1:
				return datastudent.getGroup();
			case 2:
				break;
			}
			return "";
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}

		/*
		 * @Override public Color getBackground(Object arg0, int arg1) { if
		 * (((DataStudent) arg0).isSWTDOne()&&arg1<2) return new
		 * Color(Display.getCurrent(), new RGB(43, 150, 192)); else return new
		 * Color(Display.getCurrent(), new RGB(209, 126, 65)); // return null; }
		 * 
		 * @Override public Color getForeground(Object arg0, int arg1) {
		 * 
		 * return null; }
		 */
	}


}
