package com.lux.study.controller;

import java.util.ArrayList;

import com.lux.study.listener.DataTableListener;
import com.lux.study.model.DataStudent;

public class TableManager {
    private ArrayList<DataTableListener> observers;

    public TableManager() {
        observers = new ArrayList<DataTableListener>();
    }

    public void registerObserver(DataTableListener observer) {
        observers.add(observer);
    }
    
    public void tableSelectionChanged(DataStudent dataStudent) {
        notifyObservers(dataStudent);
    }

    public void notifyObservers(DataStudent dataStudent) {
        for (int i = 0; i < observers.size(); i++) {
            DataTableListener observer = (DataTableListener) observers.get(i);
            observer.update(dataStudent);
        }
    }

}
