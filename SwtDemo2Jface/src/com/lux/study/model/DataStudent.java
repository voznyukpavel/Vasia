package com.lux.study.model;

public class DataStudent {
	private String name;
	private String group;
	private boolean isSWTDOne;

	public DataStudent(String name, String group, boolean isSWTDOne) {
		super();
		this.name = name;
		this.group = group;
		this.isSWTDOne = isSWTDOne;
	}

	public String getName() {
		return name;
	}

	public String getGroup() {
		return group;
	}		

	public boolean isSWTDOne() {
		return isSWTDOne;
	}
	
}
