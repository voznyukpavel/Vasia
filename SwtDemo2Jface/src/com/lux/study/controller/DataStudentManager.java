package com.lux.study.controller;

import java.util.ArrayList;

import com.lux.study.model.DataStudent;
import com.lux.study.ui.DataAction;

public class DataStudentManager  {

	private ArrayList<DataStudentObserver> observers;
	private DataStudent dataStudent;
	private DataAction action;
	private boolean successSaving; 
	private boolean successRemoving;

	public boolean isSuccessRemoving() {
		return successRemoving;
	}

	public boolean isSuccessSaving() {
		return successSaving;
	}

	public DataStudentManager() {
		observers = new ArrayList<DataStudentObserver>();
	}

	public void dataChenged() {	
		notifyObservers();
	}

	public void registerObserver(DataStudentObserver observer) {
		observers.add(observer);
	}

	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			DataStudentObserver observer = (DataStudentObserver) observers.get(i);
			observer.update(dataStudent, action);
		}
	}

	public void setData(DataStudent dataStudent, DataAction action) {
		this.dataStudent = dataStudent;
		switch(action) {
		case SAVE:
			successSaving=DataStorage.addData(dataStudent);
			break;
		case DELETE:
			successRemoving=DataStorage.removeData(dataStudent);
			break;
		}	
		this.action = action;
		dataChenged();
	}

}