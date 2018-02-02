package com.drcosu.ndileber.tools;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shidawei on 16/1/20.
 */
public class HJson {

    public static class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement json, Type type,JsonDeserializationContext context) throws JsonParseException {
            return new Date(json.getAsJsonPrimitive().getAsLong());
        }
    }

    public static class DateSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime());
        }
    }

    private static Gson gson = null;
    static {
        if (gson == null) {
            //gson = new Gson();
            GsonBuilder gsonb = new GsonBuilder();
            //Json中的日期表达方式没有办法直接转换成我们的Date类型, 因此需要单独注册一个Date的反序列化类.
            //DateDeserializer ds = new DateDeserializer();
            //给GsonBuilder方法单独指定Date类型的反序列化方法
            gson = gsonb.registerTypeAdapter(Date.class, new DateDeserializer())
                    .registerTypeAdapter(Date.class,new DateSerializer())
                    .setDateFormat(DateFormat.LONG)
                    .create();


        }
    }

    public static Gson getGson() {
        return gson;
    }

    public static Gson simpleGson = new Gson();


    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T fromJson(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> toList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> toListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     * 注意：整型会转为double
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> toMaps(String gsonString,Class<T> cls) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    /**
     * 转成maplist
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> HashMap<String, List<T>> toMapsList(String gsonString, Class<T> cls) {
        HashMap<String, List<T>> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<HashMap<String, List<T>>>() {
            }.getType());
        }
        return map;
    }


    /**
     * 如果报错则使用
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            mList.add(gson.fromJson(elem, cls));
        }
        return mList;
    }


    public static <T1,T2> HashMap<T1, List<T2>> toMapsListT(String gsonString,Class<T1> cls1 ,Class<T2> cls2) {
        HashMap<T1, List<T2>> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<HashMap<T1, List<T2>>>() {
            }.getType());
        }
        return map;
    }

}
