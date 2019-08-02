package com.lux.study.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.lux.study.controller.TableManager;
import com.lux.study.listener.DataStudentListener;
import com.lux.study.model.DataStudent;
import com.lux.study.storage.DataStorage;

public class TablePanel implements DataStudentListener {

    private static final String ID = "ID";
    private static final String NAME = "Name";
    private static final String GROUP = "Group";
    private static final String DONE = "SWT Done";

    private static final String[] COLUMNS = { ID, NAME, GROUP, DONE };

    private DataStudentsComparator comparator;
    private Composite tableComposite;
    private TableViewer tableViever;
    private TableManager tableManager;
    private Table table;

    private final Logger logger = Logger.getLogger(TablePanel.class.getName());
    private static final String MESSAGE_FILE_READ_ERROR = "Error occured while read file";

    public TablePanel(Composite parentComposite, TableManager dataTableManager) {
        super();

        comparator = new DataStudentsComparator();
        this.tableManager = dataTableManager;
        initUI(parentComposite);
        initListeners();
    }

    @Override
    public void onUpdateDataStudent() {
        table.deselectAll();
        setDataToTable();
    }

    @Override
    public void deselectTable() {
        table.deselectAll();
    }
    
    @Override
    public void loaded() {
        setDataToTable();
        TableItem[] tableItems = table.getItems();
        table.setSelection(tableItems[0]);
       clickOnTable();
    }

    
    @Override
    public void findRow(int row) {
        TableItem[] tableItems = table.getItems();
        for(int i=0;i<tableItems.length;i++) {
            if(Integer.parseInt(tableItems[i].getText(0))==row) {
                table.setSelection(tableItems[i]);
                break;
            }
        }
    }

    private void setDataToTable() {
        tableViever.setInput(DataStorage.getData());
        tableViever.refresh();
    }

    private void initUI(Composite parentComposite) {
        GridLayout tableGridLayout = new GridLayout(1, false);
        GridData tableGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);

        tableComposite = new Composite(parentComposite, SWT.BORDER);
        tableComposite.setLayout(tableGridLayout);

        tableViever = new TableViewer(tableComposite,
                SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
        tableViever.setContentProvider(ArrayContentProvider.getInstance());
        tableViever.setComparator(comparator);

        createTextColumn(0, 50);
        createTextColumn(1, 100);
        createTextColumn(2, 100);
        createImageColumn(3, 70);

        table = tableViever.getTable();
        table.setLayoutData(tableGridData);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
    }

    private void createTextColumn(int index, int width) {
        TableViewerColumn column = createColumn(index, width);
        column.setLabelProvider(new TextTableColumnProvider(index));
        if (index != 0)
            column.getColumn().addSelectionListener(getSelectionAdapter(column.getColumn(), index));
    }

    private void createImageColumn(int index, int width) {
        TableViewerColumn column = createColumn(index, width);
        column.setLabelProvider(new ImageTableColumnProvider(index));
    }

    private TableViewerColumn createColumn(int index, int width) {
        TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViever, SWT.NONE);
        tableViewerColumn.getColumn().setText(COLUMNS[index]);
        tableViewerColumn.getColumn().setWidth(width);
        return tableViewerColumn;
    }

    private void initListeners() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                clickOnTable();
            }
        });
    }

    private SelectionAdapter getSelectionAdapter(TableColumn column, int index) {
        SelectionAdapter selectionAdapter = new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                comparator.setColumn(index);
                int dir = comparator.getDirection();
                tableViever.getTable().setSortDirection(dir);
                tableViever.getTable().setSortColumn(column);
                tableViever.refresh();
            }
        };
        return selectionAdapter;
    }

    private void clickOnTable() {
        DataStudent selection = (DataStudent) ((IStructuredSelection) tableViever.getSelection()).getFirstElement();
        if (selection != null) {

            tableManager.tableSelectionChanged(selection);
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
            DataStudent dataStudent = (DataStudent) element;
            switch (index) {
            case 0:
                return Integer.toString(dataStudent.getID());
            case 1:
                return dataStudent.getName();
            case 2:
                return dataStudent.getGroup();
            }
            return null;
        }

        @Override
        public Image getImage(Object element) {
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
        public String getText(Object element) {
            return null;
        }

        @Override
        public Image getImage(Object element) {
            DataStudent datastudent = (DataStudent) element;
            Image image = null;
            if (index == 3) {
                if (datastudent.isSWTDOne()) {
                    try {
                        image = new Image(Display.getCurrent(), (new FileInputStream("./source/CHECKED.png")));
                    } catch (FileNotFoundException e) {
                        logger.log(Level.SEVERE, MESSAGE_FILE_READ_ERROR, e);
                    }
                } else {
                    try {
                        image = new Image(Display.getCurrent(), (new FileInputStream("./source/UCHECKED.png")));
                    } catch (FileNotFoundException e) {
                        logger.log(Level.SEVERE, MESSAGE_FILE_READ_ERROR, e);
                    }
                }
            }
            return image;
        }
    }

    private class DataStudentsComparator extends ViewerComparator {
        private static final int DESCENDING = 1;
        private int direction = DESCENDING;
        private int propertyIndex;

        public DataStudentsComparator() {
            this.propertyIndex = 0;
            direction = DESCENDING;
        }

        public int getDirection() {
            return direction == 1 ? SWT.DOWN : SWT.UP;
        }

        public void setColumn(int column) {
            if (column == this.propertyIndex) {
                direction = 1 - direction;
            } else {
                this.propertyIndex = column;
                direction = DESCENDING;
            }
        }

        @Override
        public int compare(Viewer viewer, Object e1, Object e2) {
            DataStudent dataStudent1 = (DataStudent) e1;
            DataStudent dataStudent2 = (DataStudent) e2;
            int rc = 0;
            switch (propertyIndex) {
            case 1:
                rc = dataStudent1.getName().compareTo(dataStudent2.getName());
                break;
            case 2:
                rc = dataStudent1.getGroup().compareTo(dataStudent2.getGroup());
                break;
            default:
                rc = 0;
            }
            if (direction == DESCENDING) {
                rc = -rc;
            }
            return rc;
        }
    }




}
