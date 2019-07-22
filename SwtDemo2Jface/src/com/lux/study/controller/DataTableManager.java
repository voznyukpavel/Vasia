package com.lux.study.controller;

import java.util.ArrayList;

import com.lux.study.event.TableSelectionEvent;
import com.lux.study.model.DataStudent;

public class DataTableManager {
	private ArrayList<DataTableListener> observers;
	private DataStudent dataStudentBuffer;

	public void setDataStudentBuffer(DataStudent dataStudentBuffer) {
		this.dataStudentBuffer = dataStudentBuffer;
	}

	public void setDataStudentBuffer(String name, String group, boolean task) {
		dataStudentBuffer = new DataStudent(name, group, task);
	}

	public DataStudent getDataStudentBuffer() {
		return dataStudentBuffer;
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

	public String getDataStudentBufferToString() {
		return dataStudentBuffer.toString();
	}

	public boolean checkBufferToNULL() {
		return dataStudentBuffer == null;
	}

	public DataTableManager() {
		observers = new ArrayList<DataTableListener>();
	}

	public void dataChenged() {
		notifyObservers();
	}

	public void registerObserver(DataTableListener observer) {
		observers.add(observer);
	}

	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			DataTableListener observer = (DataTableListener) observers.get(i);
			observer.update(new TableSelectionEvent(this, dataStudentBuffer.getName(), dataStudentBuffer.getGroup(),
					dataStudentBuffer.isSWTDOne()));
		}
	}

	public void setData(Object dataStudent) {
		this.dataStudentBuffer = (DataStudent) dataStudent;
		dataChenged();
	}

}
