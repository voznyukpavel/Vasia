package com.lux.study.controller;

import java.util.Set;
import java.util.TreeSet;

import com.lux.study.model.ComparatorByStudent;
import com.lux.study.model.DataStudent;

public class DataStorage {
	private static Set<DataStudent> entries = new TreeSet<DataStudent>(new ComparatorByStudent());

	private DataStorage() {

	}

	public static boolean removeData(DataStudent dataStudent) {
		return entries.remove(dataStudent);
	}

	public static boolean update(DataStudent temp, DataStudent dataStudent) {
		entries.remove(temp);
		return entries.add(dataStudent);
	}

	public static Set<DataStudent> getData() {
		return entries;
	}

}
