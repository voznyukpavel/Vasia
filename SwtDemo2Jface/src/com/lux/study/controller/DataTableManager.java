package com.lux.study.controller;

import java.util.ArrayList;

import com.lux.study.model.DataStudent;


public class DataTableManager {
	private ArrayList<DataTableObserver> observers;
	private DataStudent dataStudent;

	public DataTableManager() {
		observers = new ArrayList<DataTableObserver>();
	}

	public void dataChenged() {	
		notifyObservers();
	}

	public void registerObserver(DataTableObserver observer) {
		observers.add(observer);
	}

	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			DataTableObserver observer = (DataTableObserver) observers.get(i);
			observer.update(dataStudent);
		}
	}

	public void setData(DataStudent dataStudent) {
		this.dataStudent = dataStudent;
		dataChenged();
	}
	 

}
