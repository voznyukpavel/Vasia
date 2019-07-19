package com.lux.study.ui;

import com.lux.study.model.DataStudent;

public interface DataStudentObserver {
	public void update(DataStudent dataStudent,DataAction action);
}
