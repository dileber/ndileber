package com.drcosu.ndileber.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 2017/10/16.
 */
public class ParamsMapBuilder<T> {
    public Map<String, T> map;

    public ParamsMapBuilder(){
        map = new HashMap<String, T>();
    }

    public ParamsMapBuilder<T> map(String key, T value){
        if(value!=null){
            map.put(key, value);
        }
        return this;
    }


    public Map<String, T> build(){
        return map;
    }

}
