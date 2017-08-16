package com.drcosu.ndileber.tools;

import android.util.SparseArray;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

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
        cal.add(Calendar.HOUR_OF_DAY, amount);
        return cal.getTime();
    }

    /**
     * 格式化时间
     * @param pattern
     * @param date
     * @return
     */
    public static String getDateStr(String pattern,long date){
        return getDateStr(pattern,new Date(date));
    }

    /**
     * 格式化时间
     * @param pattern
     * @param date
     * @return
     */
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


    public static boolean isEarly(int days, long time) {
        return (currentTimeMillis() - time) > (days * 24 * 3600 * 1000);
    }

    public static int currentTimeSecond() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long[] getTsTimes() {
        long[] times = new long[2];

        Calendar calendar = Calendar.getInstance();

        times[0] = calendar.getTimeInMillis() / 1000;

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        times[1] = calendar.getTimeInMillis() / 1000;

        return times;
    }

    public static Date getDatetime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    public static String getFormatDatetime(int year, int month, int day) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new GregorianCalendar(year, month, day).getTime());
    }

    public static Date getDateFromFormatString(String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getNowDatetime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return (formatter.format(new Date()));
    }

    public static int getNow() {
        return (int) ((new Date()).getTime() / 1000);
    }

    public static String getNowDateTime(String format) {
        Date date = new Date();

        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        return df.format(date);
    }

    public static String getDateString(long milliseconds) {
        return getDateTimeString(milliseconds, "yyyyMMdd");
    }

    public static String getTimeString(long milliseconds) {
        return getDateTimeString(milliseconds, "HHmmss");
    }

    public static String getBeijingNowTimeString(String format) {
        TimeZone timezone = TimeZone.getTimeZone("Asia/Shanghai");

        Date date = new Date(currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        formatter.setTimeZone(timezone);

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeZone(timezone);
        String prefix = gregorianCalendar.get(Calendar.AM_PM) == Calendar.AM ? "上午" : "下午";

        return prefix + formatter.format(date);
    }

    public static String getBeijingNowTime(String format) {
        TimeZone timezone = TimeZone.getTimeZone("Asia/Shanghai");

        Date date = new Date(currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        formatter.setTimeZone(timezone);

        return formatter.format(date);
    }

    public static String getDateTimeString(long milliseconds, String format) {
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        return formatter.format(date);
    }


    public static String getFavoriteCollectTime(long milliseconds) {
        String showDataString = "";
        Date today = new Date();
        Date date = new Date(milliseconds);
        Date firstDateThisYear = new Date(today.getYear(), 0, 0);
        if (!date.before(firstDateThisYear)) {
            SimpleDateFormat dateformatter = new SimpleDateFormat("MM-dd", Locale.getDefault());
            showDataString = dateformatter.format(date);
        } else {
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            showDataString = dateformatter.format(date);
        }
        return showDataString;
    }

    public static String getTimeShowStringBest(Date currentTime){
        String dataString;
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todaybegin = todayStart.getTime();
        Date tomorrowbegin = new Date(todaybegin.getTime() + 3600 * 24 * 1000);
        Date houtianbegin = new Date(tomorrowbegin.getTime() + 3600 * 24 * 1000);
        Date dahoutianbegin = new Date(houtianbegin.getTime() + 3600 * 24 * 1000);
        Date yesterdaybegin = new Date(todaybegin.getTime() - 3600 * 24 * 1000);
        Date preyesterday = new Date(yesterdaybegin.getTime() - 3600 * 24 * 1000);
        String st = getTodayTimeBucket(currentTime);

        if(currentTime.getTime()>dahoutianbegin.getTime()){
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            dataString = dateformatter.format(currentTime);
        }else if(currentTime.getTime()>houtianbegin.getTime()){
            dataString = "后天 "+st;
        }else if(currentTime.getTime()>tomorrowbegin.getTime()){
            dataString = "明天 "+st;
        }else if(currentTime.getTime()>todaybegin.getTime()){
            dataString = "今天 "+st;
        }else if(currentTime.getTime()>yesterdaybegin.getTime()){
            dataString = "昨天 "+st;
        }else if(currentTime.getTime()>preyesterday.getTime()){
            dataString = "前天 "+st;
        }else{
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            dataString = dateformatter.format(currentTime);
        }

//
//        if(currentTime.getTime()>todaybegin.getTime()&&currentTime.getTime()<tomorrowbegin.getTime()){
//
//        }else if(currentTime.getTime()>tomorrowbegin.getTime()&&currentTime.getTime()<houtianbegin.getTime()){
//
//        }
//
//
//        if(currentTime.before(todaybegin)&&currentTime.after(yesterdaybegin)){
//            dataString = "昨天 "+st;
//        }else if(currentTime.before(yesterdaybegin)&&currentTime.after(preyesterday)){
//            dataString = "前天 "+st;
//        }else if(currentTime.before(tomorrowbegin)&&currentTime.after(todaybegin)){
//            dataString = "今天 "+st;
//        }else if(currentTime.before(houtianbegin)&&currentTime.after(tomorrowbegin)){
//            dataString = "明天 "+st;
//        }else if(currentTime.before(dahoutianbegin)&&currentTime.after(houtianbegin)){
//            dataString = "后天 "+st;
//        }else {
//            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            dataString = dateformatter.format(currentTime);
//        }

//
//
//        if(!currentTime.before(tomorrowbegin)){
//            dataString = "明天 "+st;
//        }else if (!currentTime.before(todaybegin)) {
//
//
//            if(!currentTime.before(dahoutianbegin)){
//                SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//                dataString = dateformatter.format(currentTime);
//            }else if(!currentTime.before(houtianbegin)){
//                dataString = "后天 "+st;
//            }else {
//                dataString = "今天 "+st;
//            }
//        } else if (!currentTime.before(yesterdaybegin)) {
//            dataString = "昨天 "+st;
//        } else if (!currentTime.before(preyesterday)) {
//            dataString = "前天 "+st;
//        } else {
//            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            dataString = dateformatter.format(currentTime);
//        }
        return dataString;
    }

    public static String getTimeShowString(long milliseconds, boolean abbreviate) {
        String dataString;
        String timeStringBy24;

        Date currentTime = new Date(milliseconds);
        Date today = new Date();
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todaybegin = todayStart.getTime();
        Date yesterdaybegin = new Date(todaybegin.getTime() - 3600 * 24 * 1000);
        Date preyesterday = new Date(yesterdaybegin.getTime() - 3600 * 24 * 1000);

        if (!currentTime.before(todaybegin)) {
            dataString = "今天";
        } else if (!currentTime.before(yesterdaybegin)) {
            dataString = "昨天";
        } else if (!currentTime.before(preyesterday)) {
            dataString = "前天";
        } else if (isSameWeekDates(currentTime, today)) {
            dataString = getWeekOfDate(currentTime);
        } else {
            SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dataString = dateformatter.format(currentTime);
        }

        SimpleDateFormat timeformatter24 = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeStringBy24 = timeformatter24.format(currentTime);

        if (abbreviate) {
            if (!currentTime.before(todaybegin)) {
                return getTodayTimeBucket(currentTime);
            } else {
                return dataString;
            }
        } else {
            return dataString + " " + timeStringBy24;
        }
    }

    /**
     * 根据不同时间段，显示不同时间
     *
     * @param date
     * @return
     */
    public static String getTodayTimeBucket(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat timeformatter0to11 = new SimpleDateFormat("KK:mm", Locale.getDefault());
        SimpleDateFormat timeformatter1to12 = new SimpleDateFormat("hh:mm", Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5) {
            return "凌晨 " + timeformatter0to11.format(date);
        } else if (hour >= 5 && hour < 12) {
            return "上午 " + timeformatter0to11.format(date);
        } else if (hour >= 12 && hour < 18) {
            return "下午 " + timeformatter1to12.format(date);
        } else if (hour >= 18 && hour < 24) {
            return "晚上 " + timeformatter1to12.format(date);
        }
        return "";
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        // String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    public static boolean isSameDay(long time1, long time2) {
        return isSameDay(new Date(time1), new Date(time2));
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        return sameDay;
    }

    /**
     * 判断两个日期是否在同一周
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    public static long getSecondsByMilliseconds(long milliseconds) {
        long seconds = new BigDecimal((float) ((float) milliseconds / (float) 1000)).setScale(0,
                BigDecimal.ROUND_HALF_UP).intValue();
        // if (seconds == 0) {
        // seconds = 1;
        // }
        return seconds;
    }

    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else retStr = "" + i;
        return retStr;
    }

    public static String getElapseTimeForShow(int milliseconds) {
        StringBuilder sb = new StringBuilder();
        int seconds = milliseconds / 1000;
        if (seconds < 1)
            seconds = 1;
        int hour = seconds / (60 * 60);
        if (hour != 0) {
            sb.append(hour).append("小时");
        }
        int minute = (seconds - 60 * 60 * hour) / 60;
        if (minute != 0) {
            sb.append(minute).append("分");
        }
        int second = (seconds - 60 * 60 * hour - 60 * minute);
        if (second != 0) {
            sb.append(second).append("秒");
        }
        return sb.toString();
    }

    public enum Week{
        XINQI("星期"),ZHOU("周");
        String t;
        Week(String t){
            this.t = t;
        }

        public String getS() {
            return t;
        }
    }

    /**
     * 取指定日期为星期几
     * @param time 指定日期
     * @return
     */
    public static String getWeekNumber(Week type,Date time) {
        String week = "日";
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(time);
        int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return getWeekNumber(type,intTemp);
    }

    public static String getWeekNumber(Week type,int day) {
        String week = "日";
        switch (day){
            case 0:
                week = "日";
                break;
            case 1:
                week = "一";
                break;
            case 2:
                week = "二";
                break;
            case 3:
                week = "三";
                break;
            case 4:
                week = "四";
                break;
            case 5:
                week = "五";
                break;
            case 6:
                week = "六";
                break;
        }
        return type.getS()+week;
    }

}
