package com.drcosu.ndileber.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shidawei on 16/3/14.
 */
public class UTime {

    public static Date addMinute(Date date,int amount){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, amount);
        return cal.getTime();
    }

    public static Date addYear(Date date,int amount){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, amount);
        return cal.getTime();
    }
    public static Date addMonth(Date date,int amount){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, amount);
        return cal.getTime();
    }
    public static Date addDate(Date date,int amount){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }
    public static Date addHour(Date date,int amount){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, amount);
        return cal.getTime();
    }

    public static String getDateStr(String pattern,Date date){
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static Date getDateFromStr(String pattern,String dateStr)throws Exception{
        if(dateStr == null){
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.parse(dateStr);
    }

    public interface Pattern{
        String y_m_d = "yyyy-MM-dd";
        String y_m_d_h_m_s = "yyyy-MM-dd HH:mm:ss";
    }

}
