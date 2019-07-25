package com.lux.study.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.lux.study.controller.DataStudentManager;
import com.lux.study.controller.DataTableManager;
import com.lux.study.event.ActionPanelEvent;
import com.lux.study.listener.DataStudentListener;
import com.lux.study.model.DataStudent;
import com.lux.study.storage.DataStorage;

@SuppressWarnings("deprecation")
public class TablePanel implements DataStudentListener {

	private static final String ID = "ID";
	private static final String NAME = "Name";
	private static final String GROUP = "Group";
	private static final String DONE = "SWT Done";

	private SashForm sashForm;
	private Composite tableComposite;
	private TableViewer tableViever;
	private TableLayout tableLayout;
	private DataStudentManager dataManager;
	private DataTableManager dataTableManager;
	private Table table;
	
	private final Logger logger = Logger.getLogger(TablePanel.class.getName());
	
	private static final String MESSAGE_FILE_READ_ERROR = "Error occured while read file";

	public TablePanel(MainPanel mainwindow, SashForm sashForm, DataStudentManager dataManager,
			DataTableManager dataTableManager) {
		super();

		this.dataManager = dataManager;
		this.dataTableManager = dataTableManager;
		this.sashForm = sashForm;
		initUI();
		initListeners();
		sighnUp();
	}

	private void sighnUp() {
		dataManager.registerObserver(this);
	}

	@Override
	public void onUpdateDataStudent(ActionPanelEvent event) {
	
		addNewInstance();
	}

	@Override
	public void onDeleteDataStudent(ActionPanelEvent event) {
		
		addNewInstance();
	}

	private void addNewInstance() {
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
		tableViever.setSorter(new StudentViewerSorter(NAME));
		tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData(30, true));
		tableLayout.addColumnData(new ColumnWeightData(30, true));
		tableLayout.addColumnData(new ColumnWeightData(20, true));
		tableLayout.addColumnData(new ColumnWeightData(20, true));
		tableViever.getTable().setLayout(tableLayout);

		table = tableViever.getTable();
		table.setSelection(SWT.FULL_SELECTION);
		table.setLayoutData(tableGridData);
		table.setEnabled(true);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViever, SWT.NONE);
		tableViewerColumn.setLabelProvider(new TextTableColumnProvider(0));
		tableViewerColumn.getColumn().setText(ID);
		tableViewerColumn.getColumn().addSelectionListener((SelectionListener) new ColumnsSelectionListener(ID));

		tableViewerColumn = new TableViewerColumn(tableViever, SWT.NONE);
		tableViewerColumn.setLabelProvider(new TextTableColumnProvider(1));
		tableViewerColumn.getColumn().setText(NAME);
		tableViewerColumn.getColumn().addSelectionListener((SelectionListener) new ColumnsSelectionListener(NAME));

		tableViewerColumn = new TableViewerColumn(tableViever, SWT.NONE);
		tableViewerColumn.setLabelProvider(new TextTableColumnProvider(2));
		tableViewerColumn.getColumn().setText(GROUP);
		tableViewerColumn.getColumn().addSelectionListener((SelectionListener) new ColumnsSelectionListener(GROUP));

		tableViewerColumn = new TableViewerColumn(tableViever, SWT.NONE);
		tableViewerColumn.setLabelProvider(new ImageTableColumnProvider(3));
		tableViewerColumn.getColumn().setText(DONE);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

	}

	private void initListeners() {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				setTextsFromTable(e);
			}			
			
		});
	}

	private void setTextsFromTable(MouseEvent e) {
		IStructuredSelection selection = (IStructuredSelection) tableViever.getSelection();
		Object selections = selection.getFirstElement();
		if (selections != null) {
			dataTableManager.setData(selections);
		}
	}

	private class ColumnsSelectionListener extends SelectionAdapter {
		private String column;

		public ColumnsSelectionListener(String column) {
			this.column = column;
		}

		public void widgetSelected(SelectionEvent event) {
			((StudentViewerSorter) tableViever.getSorter()).doSort(column);
			tableViever.refresh();
		}

	}

	private class StudentContentProvider implements IStructuredContentProvider {
		@SuppressWarnings({ "rawtypes" })
		public Object[] getElements(Object inputElement) {
	
			return ((Set) inputElement).toArray();
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
				return Integer.toString(datastudent.getID());
			case 1:
				return datastudent.getName();
			case 2:
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
			if (index == 3) {
				if (datastudent.isSWTDOne()) {
					try {
						image = new Image(null, (new FileInputStream("./source/CHECKED.png")));
					} catch (FileNotFoundException e) {
						logger.log(Level.SEVERE, MESSAGE_FILE_READ_ERROR, e);
					}
				} else {
					try {
						image = new Image(null, (new FileInputStream("./source/UCHECKED.png")));
					} catch (FileNotFoundException e) {
						logger.log(Level.SEVERE, MESSAGE_FILE_READ_ERROR, e);
					}
				}
			}
			return image;
		}
	}

	private class StudentViewerSorter extends ViewerSorter {

		private static final int ASCENDING = 0;
		private static final int DESCENDING = 1;
		private String column;
		private int direction;

		StudentViewerSorter(String column) {
			this.column = column;
		}

		public void doSort(String column) {
			if (column == this.column) {
				direction = 1 - direction;
			} else {
				this.column = column;
				direction = ASCENDING;
			}
		}

		public int compare(Viewer viewer, Object e1, Object e2) {
			int rc = 0;
			DataStudent ds1 = (DataStudent) e1;
			DataStudent ds2 = (DataStudent) e2;
			switch (column) {
			case NAME:
				rc = collator.compare(ds1.getName(), ds2.getName());
				break;
			case GROUP:
				rc = collator.compare(ds1.getGroup(), ds2.getGroup());
				break;
			}
			if (direction == DESCENDING)
				rc = -rc;
			return rc;
		}
	}

}
