package com.lux.study.event;

import java.util.EventObject;

import com.lux.study.ui.DataAction;

public class ActionPanelEvent  extends EventObject {

	private static final long serialVersionUID = 1L;
	
	private DataAction action;

	public ActionPanelEvent(Object source,DataAction action) {
		super(source);

		this.action = action;
	}
	
	public DataAction getAction() {
		return action;
	}


}
