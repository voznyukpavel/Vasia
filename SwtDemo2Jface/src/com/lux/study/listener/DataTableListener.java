package com.lux.study.controller;

import com.lux.study.event.TableSelectionEvent;

public interface DataTableListener {
	
	public void update(TableSelectionEvent tableSelectionEvent);
}
