package com.lux.study.controller;

import com.lux.study.model.DataStudent;
import com.lux.study.ui.DataAction;

public interface DataStudentObserver {
	public void update(DataStudent dataStudent,DataAction action);
}
