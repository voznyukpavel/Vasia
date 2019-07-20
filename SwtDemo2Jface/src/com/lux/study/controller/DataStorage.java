package com.lux.study.controller;

import java.util.LinkedList;
import java.util.List;

import com.lux.study.model.DataStudent;

public class DataStorage {
	private static List<DataStudent> entries = new LinkedList<DataStudent>();

	private DataStorage() {

	}

	public static void addData(DataStudent dataStudent) {
		entries.add(dataStudent);
	}

	public static List<DataStudent> getData() {
		return entries;
	}

	public static void removeData(DataStudent dataStudent) {
		entries.remove(dataStudent);
	}

}
