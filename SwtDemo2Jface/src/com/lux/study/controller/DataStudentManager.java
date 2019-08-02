package com.lux.study.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.lux.study.listener.DataStudentListener;
import com.lux.study.model.DataStudent;
import com.lux.study.storage.DataStorage;
import com.lux.study.util.DataFileManager;


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

    public void saveDataStorageToFile(File file) throws IOException {
        DataFileManager.saveDataStorageToFile(file,DataStorage.getStudents());
    }
    
    public void getDataFromFileToDataStorage(File file) throws FileNotFoundException, IOException {
        DataStorage.setStudents(DataFileManager.getDataFromFileToDataStorage(file));
        notifyObserversUpdate();
    }

    public void updateStudent(String name, String group, boolean SWTDone, int idStudent) {
        DataStorage.updateDataStudent(idStudent, new DataStudent(name, group, SWTDone, idStudent));
        notifyObserversUpdate();
    }

    public void registerObserver(DataStudentListener observer) {
        observers.add(observer);
    }

    public void notifyObserversUpdate() {
        for (int i = 0; i < observers.size(); i++) {
            DataStudentListener observer = (DataStudentListener) observers.get(i);
            observer.onUpdateDataStudent();
        }
    }
    
    public void notifyObserversOnNew() {
        for (int i = 0; i < observers.size(); i++) {
            DataStudentListener observer = (DataStudentListener) observers.get(i);
            observer.deselectTable();
        }      
    }
    
    public void notifyObserversOnCancelSelection(int id) {
        for (int i = 0; i < observers.size(); i++) {
            DataStudentListener observer = (DataStudentListener) observers.get(i);
            observer.findRow(id);
        }
    }
    

    public void deselectTablePanel() {
        notifyObserversOnNew();
    }

    public void findStudentById(int id) {
        notifyObserversOnCancelSelection(id);
    }

}