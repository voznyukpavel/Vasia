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

    private static Map<Integer, DataStudent> map;

    public static void saveDataStorageToFile(File file, Map<Integer, DataStudent> map) throws IOException {
        byte buf[] = readDataFromDataStorage(map).getBytes();
        OutputStream out = new FileOutputStream(file+".txt");
        out.write(buf);
        out.close();

    }

    // TODO: Separate methods
    public static Map<Integer, DataStudent> getDataFromFileToDataStorage(File file)
            throws FileNotFoundException, IOException {

        map = new HashMap<Integer, DataStudent>();
        String data = "";
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((data = br.readLine()) != null) {
            map.putAll(deserializeData(data));
        }
        br.close();
        return map;
    }

    private static Map<Integer, DataStudent> deserializeData(String data) {
        StringTokenizer st = new StringTokenizer(data, "|");
        while (st.hasMoreTokens()) {
            int id = Integer.parseInt(st.nextToken());
            String name = st.nextToken();
            String group = st.nextToken();
            boolean isSWTDone = Boolean.parseBoolean(st.nextToken());
            map.put(id, new DataStudent(name, group, isSWTDone, id));
        }
        return map;
    }

    private static String readDataFromDataStorage(Map<Integer, DataStudent> map) {
        String data = "";
        for (DataStudent entry : map.values()) {
            data += serializeDataToString(entry);
        }
        return data;
    }

    // TODO: implement new method to serialize data to String
    private static String serializeDataToString(DataStudent dataStudent) {
        return dataStudent.getID() + "|" + dataStudent.getName() + "|" + dataStudent.getGroup() + "|"
                + dataStudent.isSWTDOne() + "\n";
    }
}
