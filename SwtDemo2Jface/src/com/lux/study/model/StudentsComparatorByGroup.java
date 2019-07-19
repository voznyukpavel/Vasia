package com.lux.study.model;
import java.util.Comparator;

public class StudentsComparatorByGroup implements Comparator<DataStudent>{

	@Override
	public int compare(DataStudent dataStudent1, DataStudent dataStudent2) {
		
		int nameCompare=dataStudent1.getName().compareTo(dataStudent2.getName());
		int groupCompare=dataStudent1.getGroup().compareTo(dataStudent2.getGroup());
		
		if (groupCompare == 0) { 
            return ((nameCompare == 0) ? groupCompare : nameCompare); 
        } else { 
            return groupCompare; 
        } 
	}

}
