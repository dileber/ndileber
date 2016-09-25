package com.drcosu.ndileber.tools;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by shidawei on 2016/9/25.
 */
public class UAge {

    /**
     * 通过出生日期获得年龄
     * @param dateOfBirth
     * @return
     */
    public static Integer getAge(Date dateOfBirth) {
        if(dateOfBirth==null){
            return null;
        }
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        born.setTime(dateOfBirth);
        if (born.after(now)) {
            return -1;
        }
        age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
            age -= 1;
        }
        return age;
    }


}
