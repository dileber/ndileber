package com.drcosu.ndileber.tools;


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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
    }

    /**
     * 获取小数
     * @param decimal 数据
     * @param num 保留几位
     * @param model 是否要四舍五入
     * @return
     */
    public static String getDecimal(double decimal,int num,boolean model) {
        if(num<0){
            num=0;
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留小数
        nf.setMaximumFractionDigits(num);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        if(model){
            nf.setRoundingMode(RoundingMode.UP);
        }else{
            nf.setRoundingMode(RoundingMode.DOWN);
        }
        return nf.format(decimal);
    }

}
