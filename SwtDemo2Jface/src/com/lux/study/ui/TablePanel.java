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
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;

import com.lux.study.controller.TableManager;
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

    private static final String[] COLUMNS = { ID, NAME, GROUP, DONE };

    private Composite tableComposite;
    private TableViewer tableViever;
    private TableManager tableManager;
    private Table table;

    private final Logger logger = Logger.getLogger(TablePanel.class.getName());

    private static final String MESSAGE_FILE_READ_ERROR = "Error occured while read file";

    public TablePanel(Composite parentComposite, TableManager dataTableManager) {
        super();
        this.tableManager = dataTableManager;
        initUI(parentComposite);
        initListeners();
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

    private void initUI(Composite parentComposite) {
        GridLayout tableGridLayout = new GridLayout(1, false);
        GridData tableGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1);

        tableComposite = new Composite(parentComposite, SWT.BORDER);
        tableComposite.setLayout(tableGridLayout);

        tableViever = new TableViewer(tableComposite);

        tableViever.setContentProvider(ArrayContentProvider.getInstance());
        // tableViever.setComparator(new ViewerComparator() {
        //
        // @Override
        // public int compare(Viewer viewer, Object e1, Object e2) {
        // System.out.println(viewer);
        // return super.compare(viewer, e1, e2);
        // }
        //
        // @Override
        // public boolean isSorterProperty(Object element, String property) {
        // System.out.println(element);
        // return super.isSorterProperty(element, property);
        // }
        //
        // @Override
        // public void sort(Viewer viewer, Object[] elements) {
        // System.out.println(viewer);
        // super.sort(viewer, elements);
        // }
        //
        //
        //
        // });
        // tableViever.setSorter(new StudentViewerSorter(NAME));

        table = tableViever.getTable();
        table.setSelection(SWT.FULL_SELECTION);
        table.setLayoutData(tableGridData);
        table.setEnabled(true);

        createTextColumn(0, 50);
        createTextColumn(1, 100);
        createTextColumn(2, 100);
        createImageColumn(3, 50);

        table.setHeaderVisible(true);
        table.setLinesVisible(true);
    }

    private void createTextColumn(int index, int width) {
        TableViewerColumn column = createColumn(index, width);
        column.setLabelProvider(new TextTableColumnProvider(index));
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

    private void clickOnTable() {
        IStructuredSelection selection = (IStructuredSelection) tableViever.getSelection();
        Object selections = selection.getFirstElement();
        if (selections != null) {
            tableManager.setData(selections);
        }
    }

    // private class ColumnsSelectionListener extends SelectionAdapter {
    //
    // public void widgetSelected(SelectionEvent event) {
    // TableColumn tableColumn = (TableColumn) event.getSource();
    // ((StudentViewerSorter) tableViever.getSorter()).doSort(tableColumn.getText());
    // tableViever.refresh();
    // }
    // }

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
