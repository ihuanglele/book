package com.ihuanglele.book.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public class Tool extends TimerTask{

    // FileWriter 对象 HashMap
    private static HashMap<String, FileWriter> fileWriterHashMap = new HashMap<>();

    private static Timer timer;

    // 打印到控制台
    public static void log(Object o){
        System.out.println(o);
    }

    // 待写入log buf
    private final static HashMap<String,String> fileBufHashMap = new HashMap<>();

    // 是否在执行自动写入文件
    private static Boolean isRuning = false;

    private static Boolean isClose = false;

    // 保存 log 入口
    public static void save(String s, String type) {
        String time = new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
        String content = "【" + time + "】" + s + "\r\n";
        if(fileBufHashMap.containsKey(type)){
            // 已经存在
            fileBufHashMap.put(type,fileBufHashMap.get(type)+content);
        }else {
            fileBufHashMap.put(type,content);
        }
    }


    static {
        timer = new Timer();
        timer.schedule(new Tool(),1000,30000);
    }


    private static FileWriter getWriter(String type) {
        if (fileWriterHashMap.containsKey(type) && null != fileWriterHashMap.get(type)) {
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
                Tool.log("创建日志文件失败" + type);
            }
            return fileWriter;
        }
    }

    // 保存
    public static void closeFileWriter() {
        isClose = true;
        autoWriteBuf();
        timer.cancel();
    }

    @Override
    public void run() {
        autoWriteBuf();
    }

    private synchronized static void autoWriteBuf(){
        log("检测写入");
        if(isRuning){
            return;
        }else {
            isRuning = true;
        }
        log("开始写入");
        for (String type : fileBufHashMap.keySet()) {
            String content = fileBufHashMap.get(type);
            if("".equals(content)){
                continue;
            }
            FileWriter fileWriter = getWriter(type);
            if(null != fileWriter){
                fileBufHashMap.put(type,"");
                try {
                    fileWriter.write(content);
                    fileWriter.flush();
                    if(isClose){
                        fileWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        isRuning = false;
        log("写入完成");
    }

}
