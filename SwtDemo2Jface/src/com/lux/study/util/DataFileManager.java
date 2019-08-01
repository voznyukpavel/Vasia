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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lux.study.model.DataStudent;

public class DataFileManager {
    private final static Logger logger = Logger.getLogger(DataFileManager.class.getName());

    private static final String MESSAGE_FILE_WRITE_ERROR = "Error occured while file were recording";
    private static final String MESSAGE_FILE_READ_ERROR = "Error occured while file were reading";
    private static final String MESSAGE_FILE_NOT_FOUND_ERROR = "File not found";

    private static Map<Integer, DataStudent> map;

    public static void saveDataStorageToFile(String path, Map<Integer, DataStudent> map) {
        File file = new File(path);
        byte buf[] = null;
        String data = "";
        for (DataStudent entry : map.values()) {
            // TODO: implement new method to serialize data to String
            data += (entry.toString() + "\n");
        }
        buf = data.getBytes();
        try (OutputStream out = new FileOutputStream(file)) {
            out.write(buf);
        } catch (IOException e) {
            logger.log(Level.SEVERE, MESSAGE_FILE_WRITE_ERROR, e);
        }
    }

    // TODO: Separate methods
    public static Map<Integer, DataStudent> getDataFromFileToDataStorage(String path) {
        File file = new File(path);
        map = new HashMap<Integer, DataStudent>();
        String data = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((data = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(data, " ");
                while (st.hasMoreTokens()) {
                    int id = Integer.parseInt(st.nextToken());
                    String name = st.nextToken();
                    String group = st.nextToken();
                    boolean isSWTDone = Boolean.parseBoolean(st.nextToken());
                    map.put(id, new DataStudent(name, group, isSWTDone, id));
                }
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, MESSAGE_FILE_NOT_FOUND_ERROR + " " + path, e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, MESSAGE_FILE_READ_ERROR + " " + path, e);
        }
        return map;
    }
}
