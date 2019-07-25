package com.lux.study.ui;

public class MenuCallsActionPanel {
	
	private CallActionPanel action;

	public MenuCallsActionPanel(){
		
	}
	
	public void callActinPanelMethods(CallActionPanel action) {
		this.action=action;
	}
	
	public void actionNew() {
		action.onNewAction();
	}
	
	public void actionSave() {
		action.onSaveAction();
	}
	
	public void actionDelete() {
		action.onDeleteAction();
	}
	
	public void actionCancel() {
		action.onCancelAction();
	}
	
}
