package com.drcosu.ndileber.tools;

/**
 * Created by shidawei on 16/1/18.
 */
public final class HString {

    /**
     * 链接字符
     * @param args
     * @return
     */
    public static String concatString(String... args){

        return concat("",args);
    }

    /**
     * 链接字符串，第一个是分割符号
     * @param splt 分隔符
     * @param args 参数
     * @return
     */
    public static String concat(String splt,String... args){
        if(args.length<=0){
            return "";
        }
        StringBuilder ret = new StringBuilder();
        for(String temp:args){
            if (temp == null) {
                ret.append(splt).append("null");
            } else {
                ret.append(splt).append(temp);
            }
        }
        return ret.toString();
    }

    /**
     * 链接对象，第一个是分割符号
     * @param splt 分隔符
     * @param args 参数
     * @return
     */
    public static String concatObject(String splt,Object... args){
        if(args.length<=0){
            return "";
        }
        StringBuilder ret = new StringBuilder();
        for(Object temp:args){
            if (temp == null) {
                ret.append(splt).append("null");
            } else {
                ret.append(splt).append(temp.toString());
            }
        }
        return ret.toString();
    }

    public static String getStringValue(Object obj){

        return obj==null?"":obj.toString();
    }

}
