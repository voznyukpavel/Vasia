package com.lux.study.model;

import java.util.Comparator;

public class ComparatorByStudentReversed implements Comparator<DataStudent>{

	@Override
	public int compare(DataStudent dataStudent1, DataStudent dataStudent2) {
		
		int nameCompare=dataStudent2.getName().compareTo(dataStudent1.getName());
		int groupCompare=dataStudent1.getGroup().compareTo(dataStudent2.getGroup());
		
		if (nameCompare == 0) { 
            return ((groupCompare == 0) ? nameCompare : groupCompare); 
        } else { 
            return nameCompare; 
        } 
	}

}