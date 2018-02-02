package com.drcosu.ndileber.tools.net;

import com.drcosu.ndileber.tools.HString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by congtaowang on 16/5/4.
 */
public class MapToFormMap {

    public static Map<String, String> httpBuildQueryMap(Map<String, Object> mp) {

        Map<String, String> ret = new HashMap();
        if (mp != null) {
            if (mp.size() > 0) {
                Set<Map.Entry<String, Object>> entries = mp.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    putValue(ret, entry);
                }
            }
        }

        return ret;
    }

    private static void putValue(Map<String, String> ret, Map.Entry<String, Object> entry) {
        final Object value = entry.getValue();
        if (List.class.isInstance(value)) {
            List m = (List) value;
            for (int i = 0, n = m.size(); i < n; ++i) {
                ret.put(HString.concatObject("",entry.getKey(), "[", i, "]"), HString.getStringValue(m.get(i)));
            }
        } else if (value.getClass().isEnum()) {
            Enum e = (Enum) value;
            ret.put(entry.getKey(), String.valueOf(e.ordinal()));
        } else {
            ret.put(entry.getKey(),HString.getStringValue(value));
        }
    }


//    public static Map<String, String> httpBuildQueryMap(Map<String, Object> params) {
//
//        Map<String, String> builtQueryMap = new TreeMap<>(new Comparator<String>() {
//            @Override
//            public int compare(String lhs, String rhs) {
//                return lhs.compareTo(rhs);
//            }
//        });
//
//        Set<Map.Entry<String, Object>> entries = params.entrySet();
//
//        for (Map.Entry<String, Object> entry : entries) {
//
//            String key = entry.getKey();
//            Object value = entry.getValue();
//
//            if (value instanceof Map) {
//                List<String> baseParam = new ArrayList<>();
//                baseParam.add(key);
//                Map<String, String> valueMap = buildQueryMapFromMap(baseParam, ((Map) value));
//                builtQueryMap.putAll(valueMap);
//            } else if (value instanceof Collection) {
//                List<String> baseParam = new ArrayList<>();
//                baseParam.add(key);
//                Map<String, String> valueMap = buildQueryMapFromCollection(baseParam, (Collection) value);
//                builtQueryMap.putAll(valueMap);
//            } else {
//                if (value != null) {
//                    builtQueryMap.put(key, String.valueOf(value));
//                }
//            }
//        }
//        return builtQueryMap;
//    }
//
//    private static Map<String, String> buildQueryMapFromCollection(List<String> baseParam, Collection coll) {
//        Map<String, String> builtQueryMap = new TreeMap<>(new Comparator<String>() {
//            @Override
//            public int compare(String lhs, String rhs) {
//                return lhs.compareTo(rhs);
//            }
//        });
//
//        if (!(coll instanceof List)) {
//            coll = new ArrayList(coll);
//        }
//        List arrColl = (List) coll;
//
//
//        for (int i = 0; i < arrColl.size(); i++) {
//
//            Object value = arrColl.get(i);
//            if (value instanceof Map) {
//                List<String> baseParam2 = new ArrayList<>(baseParam);
//                baseParam2.add(String.valueOf(i));
//                Map<String, String> valueMap = buildQueryMapFromMap(baseParam2, (Map) value);
//                builtQueryMap.putAll(valueMap);
//
//            } else if (value instanceof List) {
//                List<String> baseParam2 = new ArrayList<>(baseParam);
//                baseParam2.add(String.valueOf(i));
//                Map<String, String> valueMap = buildQueryMapFromCollection(baseParam2, (List) value);
//                builtQueryMap.putAll(valueMap);
//            } else {
//                if (value != null) {
//                    builtQueryMap.put(getBaseParamString(baseParam) + "[" + i + "]", (String.valueOf(value)));
//                }
//            }
//        }
//
//        return builtQueryMap;
//    }
//
//    private static Map<String, String> buildQueryMapFromMap(List<String> baseParam, Map<Object, Object> valueMap) {
//        Map<String, String> builtQueryMap = new TreeMap<>(new Comparator<String>() {
//            @Override
//            public int compare(String lhs, String rhs) {
//                return lhs.compareTo(rhs);
//            }
//        });
//
//        Set<Map.Entry<Object, Object>> entries = valueMap.entrySet();
//        for (Map.Entry<Object, Object> entry : entries) {
//
//            String key = String.valueOf(entry.getKey());
//            Object value = entry.getValue();
//            if (value instanceof Map) {
//                List<String> baseParam2 = new ArrayList<>(baseParam);
//                baseParam2.add(key);
//                Map<String, String> valueMap2 = buildQueryMapFromMap(baseParam2, (Map) value);
//                builtQueryMap.putAll(valueMap2);
//            } else if (value instanceof List) {
//                List<String> baseParam2 = new ArrayList<>(baseParam);
//                baseParam2.add(key);
//                Map<String, String> valueMap2 = buildQueryMapFromCollection(baseParam2, (List) value);
//                builtQueryMap.putAll(valueMap2);
//            } else {
//                if (value != null) {
//                    builtQueryMap.put(getBaseParamString(baseParam) + "[" + key + "]", (String.valueOf(value)));
//                }
//            }
//        }
//
//        return builtQueryMap;
//    }
//
//    private static String getBaseParamString(List<String> baseParam) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < baseParam.size(); i++) {
//            String s = baseParam.get(i);
//            if (i == 0) {
//                sb.append(s);
//            } else {
//                sb.append("[" + s + "]");
//            }
//        }
//        return sb.toString();
//    }
}
