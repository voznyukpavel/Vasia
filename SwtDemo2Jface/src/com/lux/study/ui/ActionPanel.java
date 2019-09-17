package com.lux.study.ui;

import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.swt.SWT;

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
import com.lux.study.controller.TableManager;
import com.lux.study.listener.DataTableListener;
import com.lux.study.model.DataStudent;
import com.lux.study.util.TextChecker;

public class ActionPanel implements DataTableListener {
	
	private static final String ARE_YOU_SHURE_YOU_WANT_TO_DELETE="Are you surre, you want to delete current student: ";
	private static final String CONFIRM_CHANGES="Confirm changes";
	private static final String EMPTY_DATA="Empty data";
	private static final String ERROR="Error";
	private static final String DELETE ="Delete";
	private static final String DO_YOU_WANT_TO_SAVE_CURRENT_RECORD= "Do you want to save changes of current record before create new?";
	private static final String GROUP = "Group";
	private static final String NAME = "Name";
	private static final String SWT_TASK_DONE = "SWT task done";
	private static final String WRONG_ACTION_PANEL_STATE="Wrong action panel state '";
	private static final String WRONG_DATA="Wrong data";

	private Composite inputDataComposite;
	private Label nameLabelTitle, groupLabelTitle, taskSWTStatusLabel;
	private Text nameTextValue, groupTextValue;
	private Button taskSWTStatusCheckBox, newButton, saveButton, deleteButton, cancelButton;
	private DataStudentManager dataManager;

	private TableManager dataTableManager;
	private MainWindow mainWindow;

	private DataStudent currentStudent;
	private ActionPanelState state = ActionPanelState.START;
	private boolean isDirty;

	public ActionPanel(MainWindow mainWindow, Composite parentComposite, DataStudentManager dataManager,
			TableManager dataTableManager) {

		this.dataTableManager = dataTableManager;
		this.dataManager = dataManager;
		this.mainWindow = mainWindow;

		initUI(parentComposite);
		setControlsEnable();
		initListeners();
		signUpToTable();
	}

	private void initUI(Composite parentComposite) {
		GridLayout inputDataGridLayout = new GridLayout(4, false);
		inputDataGridLayout.marginTop = 15;
		inputDataGridLayout.marginLeft = 15;
		inputDataGridLayout.marginRight = 15;
		inputDataGridLayout.verticalSpacing = 20;

		inputDataComposite = new Composite(parentComposite, SWT.BORDER);
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

		groupLabelTitle = new Label(inputDataComposite, SWT.NULL);
		groupLabelTitle.setText(GROUP);
		groupLabelTitle.setLayoutData(gridDataLabel);

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

	private void setControlsEnable() {
		switch (state) {
		case START:
			newButton.setEnabled(true);
			saveButton.setEnabled(false);
			deleteButton.setEnabled(false);
			cancelButton.setEnabled(false);
			setInputControlsEnable(false);
			break;

		case NEW:
			newButton.setEnabled(false);
			saveButton.setEnabled(true);
			deleteButton.setEnabled(false);
			cancelButton.setEnabled(true);
			setInputControlsEnable(true);
			break;

		case SELECTED:
			newButton.setEnabled(false);
			saveButton.setEnabled(isDirty);
			deleteButton.setEnabled(true);
			cancelButton.setEnabled(isDirty);
			setInputControlsEnable(true);
			break;

		default:
			fatalStateError(state);
		}
	}

	private void setInputControlsEnable(boolean flag) {
		nameTextValue.setEnabled(flag);
		groupTextValue.setEnabled(flag);
		taskSWTStatusCheckBox.setEnabled(flag);
		if (!flag) {
			clearFieldsAndSWTStatus();
		}
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
				if (tryToSave()) {
					setState(ActionPanelState.START);
				}
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
				saveButton.setEnabled(true);
				isDirty = true;

			}
		});
	}

	private void signUpToTable() {
		dataTableManager.registerObserver(this);
	}

	boolean isEnabledNewButton() {
		return newButton.isEnabled();
	}

	boolean isEnabledSaveButton() {
		return saveButton.isEnabled();
	}

	boolean isEnabledCancelButton() {
		return cancelButton.isEnabled();
	}

	boolean isEnabledDeleteButton() {
		return deleteButton.isEnabled();
	}

	@Override
	public void update(DataStudent dataStudent) {
		switch (state) {
		case START:
			setStateSelected(dataStudent);
			break;
		case NEW:
			if (!areTextFieldsEmpty() && isDirty) {
				if (confirmDialog(CONFIRM_CHANGES, DO_YOU_WANT_TO_SAVE_CURRENT_RECORD)) {
					if (!tryToSave()) {
						dataManager.deselectTablePanel();
						return;
					}
					dataStudent = createNewStudent();
					setStateSelected(dataStudent);
				} else {
					setStateSelected(dataStudent);
				}
			} else {
				setStateSelected(dataStudent);
			}
			break;
		case SELECTED:
			if (isDirty) {
				if (confirmDialog(CONFIRM_CHANGES, DO_YOU_WANT_TO_SAVE_CURRENT_RECORD)) {
					if (!tryToSave()) {
						dataManager.findStudentById(currentStudent.getID());
						return;
					}
					setStateSelected(dataStudent);
				} else {
					dataManager.findStudentById(currentStudent.getID());
					setInputValues();
				}
			} else {
				setStateSelected(dataStudent);
			}
			break;
		default:
			fatalStateError(state);
		}
	}

	private void setStateSelected(DataStudent dataStudent) {
		setState(ActionPanelState.SELECTED);
		currentStudent = dataStudent;
		setInputValues();
	}

	private void setInputValues() {
		if (currentStudent != null) {
			nameTextValue.setText(currentStudent.getName());
			groupTextValue.setText(currentStudent.getGroup());
			taskSWTStatusCheckBox.setSelection(currentStudent.isSWTDOne());
			isDirty = false;
		}
	}

	void createNew() {
		setState(ActionPanelState.NEW);
	}

	private void fatalStateError(ActionPanelState state) {
		throw new RuntimeException(WRONG_ACTION_PANEL_STATE + state.toString() + "'!");
	}

	boolean tryToSave() {
		if (isDataValid()) {
			save();
			return true;
		}
		return false;
	}

	private void save() {
		switch (state) {
		case NEW:
			dataManager.createStudent(nameTextValue.getText(), groupTextValue.getText(),
					taskSWTStatusCheckBox.getSelection(), -1);
			break;
		case SELECTED:
			dataManager.updateStudent(nameTextValue.getText(), groupTextValue.getText(),
					taskSWTStatusCheckBox.getSelection(), currentStudent.getID());
			break;
		default:
			fatalStateError(state);
		}
	}

	void delete() {
		if (confirmDialog(DELETE,
				ARE_YOU_SHURE_YOU_WANT_TO_DELETE + currentStudent.getName() + " ?")) {
			dataManager.deleteStudent(currentStudent.getID());
			setState(ActionPanelState.START);
		}
	}

	void setState(ActionPanelState state) {
		this.state = state;
		if (state == ActionPanelState.NEW) {
			clearFieldsAndSWTStatus();
		}
		setControlsEnable();
	}

	void cancel() {
		switch (state) {
		case NEW:
			setState(ActionPanelState.START);
			break;
		case SELECTED:
			setInputValues();
			break;
		default:
			fatalStateError(state);
		}

	}

	void setLoadState() {
		setState(ActionPanelState.START);
	}

	private void clearFieldsAndSWTStatus() {
		nameTextValue.setText("");
		groupTextValue.setText("");
		taskSWTStatusCheckBox.setSelection(false);
	}

	private DataStudent createNewStudent() {
		return (new DataStudent(nameTextValue.getText(), groupTextValue.getText(), taskSWTStatusCheckBox.getSelection(),
				-1));
	}

	private boolean areTextFieldsEmpty() {
		if (nameTextValue.getText().isEmpty() || groupTextValue.getText().isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean isDataValid() {
		if (areTextFieldsEmpty()) {
			MessageDialog.openError(mainWindow.getShell(), ERROR, EMPTY_DATA);
			return false;
		}
		if (!TextChecker.nameChecker(nameTextValue.getText()) && TextChecker.groupChecker(groupTextValue.getText())) {
			MessageDialog.openError(mainWindow.getShell(), ERROR, WRONG_DATA);
			return false;
		}
		return true;
	}

	private boolean confirmDialog(String title, String message) {
		return MessageDialog.openConfirm(mainWindow.getShell(), title, message);
	}

	private class TextModifyListener implements ModifyListener {
		@Override
		public void modifyText(ModifyEvent e) {
			if (!isDirty) {
				isDirty = true;
				setControlsEnable();
			}
		}
	}
}
