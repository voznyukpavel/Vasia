package com.lux.study.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.lux.study.model.DataStudent;

public class DataStorage {
	
	private static Map<Integer, DataStudent> students = new HashMap<Integer, DataStudent>();
	private static int counter = 0;

	private DataStorage() {

	}

	public static void appendDataStudent(DataStudent dataStudent) {
		++counter;
		dataStudent.setID(counter);
		students.put(counter, dataStudent);
	}

	public static void removeDataStudent(int idStudent) {
		students.remove(idStudent);
	}

	public static void updateDataStudent(int idStudent, DataStudent dataStudent) {
		students.replace(idStudent, dataStudent);
	}

	public static Set<DataStudent> getData() {
		Set<DataStudent> set = new HashSet<DataStudent>(students.values());
		return set;
	}

}
