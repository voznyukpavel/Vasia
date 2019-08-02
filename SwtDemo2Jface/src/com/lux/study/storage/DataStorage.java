package com.lux.study.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.lux.study.model.DataStudent;

public class DataStorage {

    private static final int MAXIMUM = 100000;
    private static final int MINIMUM = 1;

    private static Map<Integer, DataStudent> students = new HashMap<Integer, DataStudent>();
 //   private static int counter = 0; 
    // TODO: re-implement Id generation
    private static Random rn;

    private DataStorage() {

    }

    public static void setStudents(Map<Integer, DataStudent> students) {
        DataStorage.students = students;
    }

    public static Map<Integer, DataStudent> getStudents() {
        return students;
    }

    public static void appendDataStudent(DataStudent dataStudent) {
        rn=new Random();
        int randomNum = rn.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
        dataStudent.setID(randomNum);
        students.put(randomNum, dataStudent);
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

}
