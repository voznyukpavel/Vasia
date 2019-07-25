package com.lux.study.event;

import java.util.EventObject;

public class ActionPanelStateEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	private boolean newButtonState;
	private boolean saveButtonState;
	private boolean deleteButtonState;
	private boolean cancelButtonState;
	
	public ActionPanelStateEvent(Object source, boolean newButtonState, boolean saveButtonState,
			boolean deleteButtonState, boolean cancelButtonState) {
		super(source);
		this.newButtonState = newButtonState;
		this.saveButtonState = saveButtonState;
		this.deleteButtonState = deleteButtonState;
		this.cancelButtonState = cancelButtonState;
	}

	public boolean isNewButtonState() {
		return newButtonState;
	}

	public boolean isSaveButtonState() {
		return saveButtonState;
	}

	public boolean isDeleteButtonState() {
		return deleteButtonState;
	}

	public boolean isCancelButtonState() {
		return cancelButtonState;
	}

}
