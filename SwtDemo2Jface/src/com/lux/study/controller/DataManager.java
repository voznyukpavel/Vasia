package com.lux.study.controller;

import java.util.ArrayList;

import com.lux.study.model.DataStudent;
import com.lux.study.ui.DataAction;

public class DataManager  {

	private ArrayList<DataStudentObserver> observers;
	private DataStudent dataStudent;
	private DataAction action;
	private DataStudent buffer;

	public DataManager() {
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
		DataStorage.addData(dataStudent);
		this.action = action;
		dataChenged();
	}

	/*
	 * public void setFields(DataStudent selections) { buffer = selections; }
	 */

	public DataStudent getBuffer() {
		return buffer;
	}
}