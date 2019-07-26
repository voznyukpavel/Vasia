package com.lux.study.event;

import java.util.EventObject;


public class ActionPanelEvent  extends EventObject {

	private static final long serialVersionUID = 1L;
	
	public ActionPanelEvent(Object source) {
		super(source);
	}
}
