package com.ihuanglele.book.util;

import java.util.HashMap;

/**
 * Created by ihuanglele on 2018/11/19.
 */
public final class Config {

    private static HashMap<String,String> conf = new HashMap<>();

    private static Config config = null;

    private static boolean isInit = false;

    public static void autoSet(String key,String value){
        conf.put(key,value);
    }

    public static String get(String key){
        return conf.get(key);
    }

    public static void init(String[] args){
        if(!isInit){
            isInit = true;
            conf.put("site","QisuuLa");
            conf.put("start","1");
            conf.put("store","FileStore");
            for (String key : args){
                String[] param = key.split("=",2);
                if(param[0].startsWith("-config=")){
                    // 传入配置路径

                }else {
                    if(param.length > 1){
                        conf.put(param[0],param[1]);
                    }
                }
            }
        }

    }


}
