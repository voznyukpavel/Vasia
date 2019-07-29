package com.lux.study.controller;

import java.util.ArrayList;

import com.lux.study.event.TableSelectionEvent;
import com.lux.study.listener.DataTableListener;
import com.lux.study.model.DataStudent;

public class TableManager {
	private ArrayList<DataTableListener> observers;
	private DataStudent dataStudentBuffer;
	
	public TableManager() {
		observers = new ArrayList<DataTableListener>();
	}
	
	public void setDataStudentBuffer(DataStudent dataStudentBuffer) {
		this.dataStudentBuffer = dataStudentBuffer;
	}

	public String getDataStudentBufferName() {
		return dataStudentBuffer.getName();
	}

	public String getDataStudentBufferGroup() {
		return dataStudentBuffer.getGroup();
	}

	public boolean getDataStudentBufferSWTTask() {
		return dataStudentBuffer.isSWTDOne();
	}
	
	public int getDataStudentBufferId() {
		return dataStudentBuffer.getID();
	}
	
	public String getDataStudentBufferToString() {
		return dataStudentBuffer.toString();
	}

	public boolean isBufferNULL() {
		return dataStudentBuffer == null;
	}

	public void registerObserver(DataTableListener observer) {
		observers.add(observer);
	}

	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			DataTableListener observer = (DataTableListener) observers.get(i);
			observer.update(new TableSelectionEvent(this, dataStudentBuffer.getName(), dataStudentBuffer.getGroup(),
					dataStudentBuffer.isSWTDOne(),dataStudentBuffer.getID()));
		}
	}

	public void setData(Object dataStudent) {
		this.dataStudentBuffer = (DataStudent) dataStudent;
		notifyObservers();
	}

}
