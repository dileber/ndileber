package com.drcosu.ndileber.tools;

import com.drcosu.ndileber.tools.string.MD5;

import java.util.Locale;
import java.util.UUID;

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

    /**
     * 获取32位uuid
     *
     * @return
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成唯一号
     *
     * @return
     */
    public static String get36UUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        return uniqueId;
    }

    public static String makeMd5(String source) {
        return MD5.getStringMD5(source);
    }

    public static String getPercentString(float percent) {
        return String.format(Locale.US, "%d%%", (int) (percent * 100));
    }


    /**
     * 删除字符串中的空白符
     *
     * @param content
     * @return String
     */
    public static String removeBlanks(String content) {
        if (content == null) {
            return null;
        }
        StringBuilder buff = new StringBuilder();
        buff.append(content);
        for (int i = buff.length() - 1; i >= 0; i--) {
            if (' ' == buff.charAt(i) || ('\n' == buff.charAt(i)) || ('\t' == buff.charAt(i))
                    || ('\r' == buff.charAt(i))) {
                buff.deleteCharAt(i);
            }
        }
        return buff.toString();
    }

}
