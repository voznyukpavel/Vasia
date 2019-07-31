package com.lux.study.controller;

import java.util.ArrayList;


import com.lux.study.listener.DataStudentListener;
import com.lux.study.model.DataStudent;
import com.lux.study.storage.DataStorage;

public class DataStudentManager {

    private ArrayList<DataStudentListener> observers;

    public DataStudentManager() {
        observers = new ArrayList<DataStudentListener>();
    }

    public void createStudent(String name, String group, boolean SWTDone, int idStudent) {
        DataStorage.appendDataStudent(new DataStudent(name, group, SWTDone, idStudent));
        notifyObserversUpdate();
    }

    public void deleteStudent(int idStudent) {
        DataStorage.removeDataStudent(idStudent);
        notifyObserversUpdate();
    }
    
    public void saveDataStorageToFile(String path) {
        
    }
    
    public void getDataFromFileToDataStorage(String path) {
        
        notifyObserversUpdate();
    }

    public void updateStudent(String name, String group, boolean SWTDone, int idStudent) {
        DataStorage.updateDataStudent(idStudent, new DataStudent(name, group, SWTDone, idStudent));
        notifyObserversUpdate();
    }

    public void registerObserver(DataStudentListener observer) {
        observers.add(observer);
    }

    // TODO: File Save, File Load, через диалог выбора файла и папки

    public void notifyObserversUpdate() {
        for (int i = 0; i < observers.size(); i++) {
            DataStudentListener observer = (DataStudentListener) observers.get(i);
            observer.onUpdateDataStudent();
        }
    }

}