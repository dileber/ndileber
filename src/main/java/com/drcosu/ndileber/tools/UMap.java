package com.drcosu.ndileber.tools;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by WaTaNaBe on 2017/10/31.
 */

public class UMap {
    /**
     * map排序工具，内部也进行排序，用于发送安全数据校验
     * @param map
     * @return
     */
    public static Map<String, Object> sort(Map<String, Object> map) {
        Map<String, Object> result = new TreeMap<String, Object>(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        Iterator<String> keys = map.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            Object temp = map.get(key);
            if(temp != null){
                if(temp instanceof Map){
                    Map m = (Map)temp;
                    if(m.size()>0){
                        temp = sort((Map<String,Object>)temp);
                        result.put(key, temp);
                    }
                }else{
                    result.put(key, temp);
                }
            }

        }
        return result;
    }
}
