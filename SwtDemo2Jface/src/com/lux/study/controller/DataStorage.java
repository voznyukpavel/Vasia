package com.lux.study.controller;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.lux.study.model.DataStudent;
import com.lux.study.model.StudentsComparator;

public class DataStorage // implements DataStudentObserver
{
	private static List<DataStudent> entries = new LinkedList<DataStudent>();
	static StudentsComparator comparator= new StudentsComparator();

	private DataStorage() {

	}

	public static void addData(DataStudent dataStudent) {
		entries.add(dataStudent);
		Collections.sort(entries, comparator);
	}

	public static List<DataStudent> getData() {
         return entries;
	}

}
