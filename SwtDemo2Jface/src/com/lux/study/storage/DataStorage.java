package com.lux.study.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.lux.study.model.DataStudent;

public class DataStorage {


    private static Map<Integer, DataStudent> students = new HashMap<Integer, DataStudent>();
    private static int counter = 0;

    private DataStorage() {

    }

    public static void setStudents(Map<Integer, DataStudent> students) {
        counter=foundMAXID(students)+1;
        DataStorage.students = students;
    }

    public static Map<Integer, DataStudent> getStudents() {
        return students;
    }

    public static void appendDataStudent(DataStudent dataStudent) {
        counter++;
        dataStudent.setID(counter);
        students.put(counter, dataStudent);
    }

    public static void appendDataStudent(int id, DataStudent dataStudent) {
        students.put(id, dataStudent);
    }

    public static void removeDataStudent(int idStudent) {
        students.remove(idStudent);
    }

    public static void updateDataStudent(int idStudent, DataStudent dataStudent) {
        students.replace(idStudent, dataStudent);
    }

    public static Set<DataStudent> getData() {
        Set<DataStudent> set = new HashSet<DataStudent>(students.values());
        return set;
    }

    private static int foundMAXID(Map<Integer, DataStudent> students) {
        int max=1;
        for(int i=1;i<students.size();i++) {
            if(students.get(i).getID()>max) {
                max=students.get(i).getID();
            }
        }
        return max;
    }

}
