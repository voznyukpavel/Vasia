package com.lux.study.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
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

import com.lux.study.controller.DataStudentManager;
import com.lux.study.controller.DataStorage;
import com.lux.study.controller.DataStudentObserver;
import com.lux.study.controller.DataTableManager;
import com.lux.study.model.DataStudent;

public class TablePanel implements DataStudentObserver {

	private static final String NAME = "Name";
	private static final String GROUP = "Group";
	private static final String DONE = "SWT Done";

//	private List<DataStudent> entries;
	private SashForm sashForm;
	private Composite tableComposite;
	private TableViewer tableViever;
	private TableLayout tableLayout;
	private DataStudentManager dataManager;
	private DataTableManager dataTableManager;
	private DataStudent dataStudent;
	private MainPanel mainwindow;
	private Table table;


	public TablePanel(MainPanel mainwindow, SashForm sashForm, DataStudentManager dataManager,DataTableManager dataTableManager) {
		super();
		this.mainwindow = mainwindow;
		this.dataManager = dataManager;
		this.dataTableManager=dataTableManager;
		this.sashForm = sashForm;
		//entries = new LinkedList();
	//	dataStorage=new DataStorage(dataManager);

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

	private void sighnUp() {
		dataManager.registerObserver(this);
	}

	@Override
	public void update(DataStudent dataStudent, DataAction action) {
//	System.out.println(dataStorage.getLast());	
		switch (action) {
		case NEW:

		case SAVE:
			addNewInstance(dataStudent);
		case DELETE:

		case CANCEL:

		}
	}

	private void addNewInstance(DataStudent dataStudent) {
		tableViever.setInput(DataStorage.getData());
		tableViever.refresh();
	}

	private void initUI() {
		GridLayout tableGridLayout = new GridLayout(1, false);
		GridData tableGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);

		tableComposite = new Composite(sashForm, SWT.BORDER);
		tableComposite.setLayout(tableGridLayout);

		tableViever = new TableViewer(tableComposite);

		tableViever.setContentProvider(new StudentContentProvider());

		

		tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData(60, true));
		tableLayout.addColumnData(new ColumnWeightData(20, true));
		tableLayout.addColumnData(new ColumnWeightData(20, true));
		tableViever.getTable().setLayout(tableLayout);

		table = tableViever.getTable();
		table.setLayoutData(tableGridData);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViever, SWT.PUSH);
		tableViewerColumn.setLabelProvider(new TextTableColumnProvider(0));
		tableViewerColumn.getColumn().setText(NAME);

		tableViewerColumn = new TableViewerColumn(tableViever, SWT.PUSH);
		tableViewerColumn.setLabelProvider(new TextTableColumnProvider(1));
		tableViewerColumn.getColumn().setText(GROUP);

		tableViewerColumn = new TableViewerColumn(tableViever, SWT.NONE);
		tableViewerColumn.setLabelProvider(new ImageTableColumnProvider(2));
		tableViewerColumn.getColumn().setText(DONE);

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
			dataTableManager.setData((DataStudent)selections);
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

	private class TextTableColumnProvider extends ColumnLabelProvider {
		private int index;

		public TextTableColumnProvider(int index) {
			super();
			this.index = index;
		}

		@Override
		public String getText(Object element) {
			DataStudent datastudent = (DataStudent) element;
			switch (index) {
			case 0:
				return datastudent.getName();
			case 1:
				return datastudent.getGroup();
			}
			return null;

		}

	}

	private class ImageTableColumnProvider extends ColumnLabelProvider {
		private int index;

		public ImageTableColumnProvider(int index) {
			super();
			this.index = index;
		}

		@Override
		public Image getImage(Object element) {
			DataStudent datastudent = (DataStudent) element;
			Image image = null;
			if (index == 2) {
				if (datastudent.isSWTDOne()) {
					try {
						image = new Image(null, (new FileInputStream("./source/CHECKED.png")));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					try {
						image = new Image(null, (new FileInputStream("./source/UCHECKED.png")));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			return image;
		}
	}
}
