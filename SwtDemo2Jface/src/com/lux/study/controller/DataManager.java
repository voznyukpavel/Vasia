package com.lux.study.controller;

import java.util.ArrayList;

import com.lux.study.model.DataStudent;
import com.lux.study.ui.DataAction;
import com.lux.study.ui.DataStudentObserver;


public class DataManager implements ObserveredDataStudentSubject{
	
	private ArrayList<DataStudentObserver> observers;
	private DataStudent dataStudent;
	private DataAction action;
	private DataStudent buffer;
	
	public DataManager() {
		observers= new ArrayList<DataStudentObserver>();
	}
	
	public void dataChenged() {
		notifyObservers();
	}

    @Override
    public void registerObserver(DataStudentObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            DataStudentObserver observer = (DataStudentObserver) observers.get(i);
            observer.update(dataStudent,action);
        }
    }
    
    public void setData(DataStudent dataStudent,DataAction action) {
    	this.dataStudent=dataStudent;
    	this.action=action;
    	dataChenged();
    }
	

}
