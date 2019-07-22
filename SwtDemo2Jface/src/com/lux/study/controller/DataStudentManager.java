package com.lux.study.controller;

import java.util.ArrayList;

import com.lux.study.event.ActionPanelEvent;
import com.lux.study.model.DataStudent;
import com.lux.study.ui.DataAction;

public class DataStudentManager  {

	private ArrayList<DataStudentListener> observers;
	
	private DataAction action;
	private DataStudent dataStudent;
	private boolean successSaving; 
	private boolean successRemoving;

	public DataStudentManager() {
		observers = new ArrayList<DataStudentListener>();
	}
	
	public void createStudent(String name,String group,boolean task) {
		dataStudent=new DataStudent(name,group,task);
	}
	
	public void deleteStudent(String name,String group) {
		successRemoving=DataStorage.removeData(dataStudent);
	}
	
	public boolean isSuccessRemoving() {
		return successRemoving;
	}

	public boolean isSuccessSaving() {
		return successSaving;
	}
	

	public void registerObserver(DataStudentListener observer) {
		observers.add(observer);
	}

	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			DataStudentListener observer = (DataStudentListener) observers.get(i);
			observer.update(new ActionPanelEvent(this,action));
		}
	}

	public void setData(DataStudent temp, DataAction action) {
		switch(action) {
		case SAVE:
			successSaving=DataStorage.update(temp, dataStudent);
			break;
		case DELETE:
			successRemoving=DataStorage.removeData(dataStudent);
			break;
		default:
			break;
		}	
		this.action = action;
		
		notifyObservers();
	}

}