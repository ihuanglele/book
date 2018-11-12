package com.ihuanglele.book.util;

import com.ihuanglele.book.page.Book;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public class Tool {

    private static HashMap<String, FileWriter> fileWriterHashMap = new HashMap<>();

    public static void log(Object o){
        System.out.println(o);
    }

    public static void save(String s, String type) {
        FileWriter fileWriter = getWriter(type);
        if (null != fileWriter) {
            try {
                String time = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
                fileWriter.write("【" + time + "】" + s + "\r\n");
            } catch (IOException e) {
                e.printStackTrace();
                log("【type】写入失败:'" + e.getMessage());
            }
        }
    }

    private static FileWriter getWriter(String type) {
        type = type.replace("/", "");
        if (fileWriterHashMap.containsKey(type)) {
            return fileWriterHashMap.get(type);
        } else {
            FileWriter fileWriter = null;
            try {
                File file = new File("./log/" + type + ".log");
                Boolean f = true;
                if (!file.exists()) {
                    f = file.createNewFile();
                }
                if (f) {
                    fileWriter = new FileWriter(file.getAbsoluteFile(), true);
                    fileWriterHashMap.put(type, fileWriter);
                }
            } catch (IOException e) {
//                e.printStackTrace();
                Tool.log("创建日志文件失败" + type);
            }
            return fileWriter;
        }
    }

    public static void closeFileWriter() {
        for (String type : fileWriterHashMap.keySet()) {
            FileWriter fileWriter = fileWriterHashMap.get(type);
            try {
                fileWriter.close();
            } catch (IOException e) {
//                    e.printStackTrace();
            }
        }
    }

}
