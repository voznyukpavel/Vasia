package com.lux.study.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.lux.study.model.DataStudent;

public class DataFileManager {

    public static void saveDataStorageToFile(File file, Map<Integer, DataStudent> map) throws IOException {
        byte buf[] = readDataFromDataStorage(map).getBytes();
        try (OutputStream out = new FileOutputStream(file + ".txt")) {
            out.write(buf);
        }
    }

    public static Map<Integer, DataStudent> getDataFromFileToDataStorage(File file)
            throws FileNotFoundException, IOException {

        Map<Integer, DataStudent> map = new HashMap<Integer, DataStudent>();
        String data = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((data = br.readLine()) != null) {
                DataStudent dataStudent = deserializeData(data);
                map.put(dataStudent.getID(), dataStudent);
            }
        }
        return map;
        // TODO: state after load data
    }

    private static DataStudent deserializeData(String data) {
        StringTokenizer st = new StringTokenizer(data, "|");
        int id = Integer.parseInt(st.nextToken());
        String name = st.nextToken();
        String group = st.nextToken();
        boolean isSWTDone = Boolean.parseBoolean(st.nextToken());
        return new DataStudent(name, group, isSWTDone, id);
    }

    private static String readDataFromDataStorage(Map<Integer, DataStudent> map) {
        String data = "";
        for (DataStudent entry : map.values()) {
            data += serializeDataToString(entry);
        }
        return data;
    }

    private static String serializeDataToString(DataStudent dataStudent) {
        return dataStudent.getID() + "|" + dataStudent.getName() + "|" + dataStudent.getGroup() + "|"
                + dataStudent.isSWTDOne() + "\n";
    }
}
