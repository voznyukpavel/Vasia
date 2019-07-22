package com.lux.study.event;

import java.util.EventObject;

public class TableSelectionEvent extends EventObject {


	private static final long serialVersionUID = 1L;
	
	private String name;
	private String group;
	private boolean taskDone;

	public TableSelectionEvent(Object source, String name, String group, boolean sWTtask) {
		super(source);
		this.name = name;
		this.group = group;
		taskDone = sWTtask;
	}

	public String getName() {
		return name;
	}

	public String getGroup() {
		return group;
	}

	public boolean isSWTtask() {
		return taskDone;
	}

}
