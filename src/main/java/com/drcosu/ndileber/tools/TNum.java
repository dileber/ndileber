package com.drcosu.ndileber.tools;


import java.text.DecimalFormat;

/**
 * Created by H2 on 2016/9/22.
 */
public class TNum {

    static char noKeep = '#';
    static char mKeep = '0';

    /**
     * 获取金额规则
     * @param point 小数点后几位
     * @param keep 不足是否要补0
     * @return
     */
    public static String moneyFormat(int point,boolean keep){
        String start = "##,###,###,###,##0";
        StringBuilder end = new StringBuilder(".");
        boolean first = false;
        for(int i=0;i<point;i++){
            first = true;
            if(keep){
                end.append(mKeep);
            }else{
                end.append(noKeep);
            }
        }
        if(first){
            return start+end.toString();
        }
        return start;
    }

    public static String getMoney(Object money,String format){

        DecimalFormat myformat = new DecimalFormat(format);
        return myformat.format(money);

//        if(String.class.isInstance(money)){
//
//        }else if(Float.class.isInstance(money)){
//
//        }else if(Double.class.isInstance(money)){
//
//        }

    }

}
