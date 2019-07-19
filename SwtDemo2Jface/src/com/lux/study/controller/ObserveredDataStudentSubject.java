package com.lux.study.controller;

import com.lux.study.ui.DataStudentObserver;

public interface ObserveredDataStudentSubject {
	
	void registerObserver(DataStudentObserver observer);
	void notifyObservers();
}