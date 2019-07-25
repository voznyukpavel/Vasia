package com.lux.study.controller;

import java.util.ArrayList;

import com.lux.study.event.ActionPanelStateEvent;
import com.lux.study.listener.ActionPanelStateListener;

public class DataActionPanelStateManager {
	private ArrayList<ActionPanelStateListener> observers;
	private boolean newButtonState;
	private boolean saveButtonState;
	private boolean deleteButtonState;
	private boolean cancelButtonState;
	
	public DataActionPanelStateManager(){
		observers= new ArrayList<ActionPanelStateListener>();
	}
	
	public void setData(boolean newButtonState, boolean saveButtonState, boolean deleteButtonState,boolean cancelButtonState) {
		
		this.newButtonState=newButtonState;
		this.saveButtonState=saveButtonState;
		this.deleteButtonState=deleteButtonState;
		this.cancelButtonState=cancelButtonState;
		
		notifyObserversUpdate();
	}
	
	public void registerObserver(ActionPanelStateListener observer) {
		observers.add(observer);
	}
	
	public void notifyObserversUpdate() {
		for (int i = 0; i < observers.size(); i++) {
			ActionPanelStateListener observer = (ActionPanelStateListener) observers.get(i);
			observer.updatePanelState(new ActionPanelStateEvent(this,newButtonState,saveButtonState,deleteButtonState,cancelButtonState));
		}
	}
}
