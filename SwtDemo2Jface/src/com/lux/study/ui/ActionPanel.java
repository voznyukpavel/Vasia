package com.lux.study.ui;

import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.lux.study.controller.DataStudentManager;
import com.lux.study.controller.DataTableManager;
import com.lux.study.event.TableSelectionEvent;
import com.lux.study.controller.DataTableListener;

import com.lux.study.util.TextChecker;

public class ActionPanel implements DataTableListener {

	private static final String NAME = "Name";
	private static final String GROUP = "Group";
	private static final String SWT_TASK_DONE = "SWT task done";

	private SashForm sashForm;
	private Composite inputDataComposite;
	private Label nameLabelTitle, groupLabelTitel, taskSWTStatusLabel;
	private Text nameTextValue, groupTextValue;
	private Button taskSWTStatusCheckBox, newButton, saveButton, deleteButton, cancelButton;
	private DataStudentManager dataManager;
	private DataTableManager dataTableManager;
	private MainPanel mainwindow;

	public ActionPanel(MainPanel mainwindow, SashForm sashForm, DataStudentManager dataManager,
			DataTableManager dataTableManager) {

		this.dataTableManager = dataTableManager;
		this.dataManager = dataManager;
		this.mainwindow = mainwindow;
		this.sashForm = sashForm;
		initUI();
		preInstalInit();
		initListeners();
		sighnUp();
	}

	@Override
	public void update(TableSelectionEvent event) {
		nameTextValue.setText(event.getName());
		groupTextValue.setText(event.getGroup());
		taskSWTStatusCheckBox.setSelection(event.isSWTtask());
	}

	private void initUI() {

		GridLayout inputDataGridLayout = new GridLayout(4, false);
		inputDataGridLayout.marginTop = 15;
		inputDataGridLayout.marginLeft = 15;
		inputDataGridLayout.marginRight = 15;
		inputDataGridLayout.verticalSpacing = 20;

		inputDataComposite = new Composite(sashForm, SWT.BORDER);
		inputDataComposite.setLayout(inputDataGridLayout);

		GridData gridDataLabel = new GridData();
		gridDataLabel.verticalAlignment = SWT.BEGINNING;
		gridDataLabel.horizontalAlignment = SWT.FILL;
		gridDataLabel.verticalIndent = 5;

		nameLabelTitle = new Label(inputDataComposite, SWT.NULL);
		nameLabelTitle.setText(NAME);
		nameLabelTitle.setLayoutData(gridDataLabel);

		GridData gridDataText = new GridData();
		gridDataText.grabExcessHorizontalSpace = true;
		gridDataText.horizontalAlignment = GridData.FILL;
		gridDataText.horizontalSpan = 3;
		nameTextValue = new Text(inputDataComposite, SWT.BORDER | SWT.RIGHT);
		nameTextValue.setLayoutData(gridDataText);

		groupLabelTitel = new Label(inputDataComposite, SWT.NULL);
		groupLabelTitel.setText(GROUP);
		groupLabelTitel.setLayoutData(gridDataLabel);

		groupTextValue = new Text(inputDataComposite, SWT.BORDER | SWT.RIGHT);
		groupTextValue.setLayoutData(gridDataText);

		taskSWTStatusLabel = new Label(inputDataComposite, SWT.NULL);
		taskSWTStatusLabel.setText(SWT_TASK_DONE);
		taskSWTStatusLabel.setLayoutData(gridDataLabel);

		taskSWTStatusCheckBox = new Button(inputDataComposite, SWT.CHECK);
		taskSWTStatusCheckBox.setLayoutData(new GridData(SWT.END, SWT.NONE, false, false, 3, 0));

		GridData gridDataButton = new GridData();

		gridDataButton.widthHint = 80;
		gridDataButton.heightHint = 30;
		gridDataButton.horizontalAlignment = SWT.END;

		newButton = new Button(inputDataComposite, SWT.PUSH);
		newButton.setText(DataAction.NEW.getName());
		newButton.setLayoutData(gridDataButton);

		saveButton = new Button(inputDataComposite, SWT.PUSH);
		saveButton.setText(DataAction.SAVE.getName());
		saveButton.setLayoutData(gridDataButton);

		deleteButton = new Button(inputDataComposite, SWT.PUSH);
		deleteButton.setText(DataAction.DELETE.getName());
		deleteButton.setLayoutData(gridDataButton);

		cancelButton = new Button(inputDataComposite, SWT.PUSH);
		cancelButton.setText(DataAction.CANCEL.getName());

		GridData gridDataCancelButton = new GridData();
		gridDataCancelButton.widthHint = 80;
		gridDataCancelButton.heightHint = 30;
		gridDataCancelButton.horizontalAlignment = SWT.BEGINNING;
		cancelButton.setLayoutData(gridDataCancelButton);

	}

	private void preInstalInit() {
		nameTextValue.setEnabled(false);
		taskSWTStatusCheckBox.setEnabled(false);
		groupTextValue.setEnabled(false);
		saveButton.setEnabled(false);
		deleteButton.setEnabled(false);
		cancelButton.setEnabled(false);
	}

	private void initListeners() {
		TextModifyListener textModifyListener = new TextModifyListener();
		nameTextValue.addModifyListener(textModifyListener);
		groupTextValue.addModifyListener(textModifyListener);

		newButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				createNew();
			}
		});

		saveButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				save();
			}
		});

		deleteButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				delete();
			}
		});

		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				cancel();
			}
		});

		taskSWTStatusCheckBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				cancelButton.setEnabled(true);
				saveButton.setEnabled(!areTextFildsEmpty()&&checkFields());
			}
		});
	}

	private void sighnUp() {
		dataTableManager.registerObserver(this);
	}

	private void createNew() {
		if (areBothOfFildsEmpty()) {
			nameTextValue.setEnabled(true);
			groupTextValue.setEnabled(true);
			taskSWTStatusCheckBox.setEnabled(true);
			cancelButton.setEnabled(true);
		} else {
			if (MessageDialog.openConfirm(mainwindow.getShell(), "Clear fields",
					"Are you surre, you want to clear data?")) {
				clearFildsAndSWTStatus();
			}
		}

	}

	private void save() {
		dataManager.createStudent(nameTextValue.getText(), groupTextValue.getText(),
				taskSWTStatusCheckBox.getSelection());

		if (!dataTableManager.checkBufferToNULL()  && !tempEqualsToFieldsAndSWT()&&checkFields()) {
			dataManager.setData(dataTableManager.getDataStudentBuffer(), DataAction.SAVE);
		} else if (dataTableManager.checkBufferToNULL())

		{
			dataTableManager.setDataStudentBuffer(nameTextValue.getText(), groupTextValue.getText(),
					taskSWTStatusCheckBox.getSelection());
			dataManager.setData(dataTableManager.getDataStudentBuffer(), DataAction.SAVE);
		}

		if (!dataManager.isSuccessSaving()) {
			MessageDialog.openWarning(mainwindow.getShell(), "Data Cuoldnot be added",
					"This Student is allready exist");
		}
		dataTableManager.setDataStudentBuffer(null);
	}

	private void delete() {
		if (MessageDialog.openConfirm(mainwindow.getShell(), "Delete",
				"Are you surre, you want to terminate the student: "
						+ dataTableManager.getDataStudentBufferToString()  + " ?")) {
			deleteButton.setEnabled(false);
			dataManager.createStudent(nameTextValue.getText(), groupTextValue.getText(),
					taskSWTStatusCheckBox.getSelection());
			dataManager.setData(null, DataAction.DELETE);
			if (dataManager.isSuccessRemoving()) {
				MessageDialog.openInformation(mainwindow.getShell(), "Deleted",
						" Student " + dataTableManager.getDataStudentBufferToString() +" was deleted");
			}
			dataTableManager.setDataStudentBuffer(null);
			clearFildsAndSWTStatus();
		}
	}

	private void cancel() {
		if (!areBothOfFildsEmpty() &&dataTableManager.checkBufferToNULL() ) {
			clearFildsAndSWTStatus();
		} else if (!dataTableManager.checkBufferToNULL() ) {
			nameTextValue.setText(dataTableManager.getDataStudentBufferName());
			groupTextValue.setText(dataTableManager.getDataStudentBufferGroup());
			taskSWTStatusCheckBox.setSelection(dataTableManager.getDataStudentBufferSWTTask());
		}
		cancelButton.setEnabled(false);
	}

	private void clearFildsAndSWTStatus() {
		nameTextValue.setText("");
		groupTextValue.setText("");
		taskSWTStatusCheckBox.setSelection(false);
	}

	private boolean tempEqualsToFieldsAndSWT() {
		return (dataTableManager.getDataStudentBufferName().equals(nameTextValue.getText())
				&& dataTableManager.getDataStudentBufferGroup().equals(groupTextValue.getText())
				&& dataTableManager.getDataStudentBufferSWTTask() == taskSWTStatusCheckBox.getSelection());
	}

	private boolean areTextFildsEmpty() {
		if (nameTextValue.getText().isEmpty() || groupTextValue.getText().isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean areBothOfFildsEmpty() {
		if (nameTextValue.getText().isEmpty() && groupTextValue.getText().isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean checkFields() {
		return TextChecker.checker(nameTextValue.getText(), groupTextValue.getText()) && !areTextFildsEmpty();
	}
	

	private class TextModifyListener implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			if (!dataTableManager.checkBufferToNULL()  ) {
				deleteButton.setEnabled(true);
			}
			cancelButton.setEnabled(true);
			if (checkFields() && dataTableManager.checkBufferToNULL() 
					|| !dataTableManager.checkBufferToNULL()  && (!tempEqualsToFieldsAndSWT())) {
				saveButton.setEnabled(true);
			} else {
				saveButton.setEnabled(false);
			}
		}
	}
}
