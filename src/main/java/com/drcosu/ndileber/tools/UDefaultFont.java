package com.drcosu.ndileber.tools;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.StringRes;
import android.widget.TextView;

import com.drcosu.ndileber.R;
import com.drcosu.ndileber.app.SApplication;

/**
 * 字体图标工具 必须将SApplication 中的默认图标打开
 * Created by shidawei on 2017/4/8.
 */

public class UDefaultFont {

    /**
     * 设置星星
     * @param startext
     * @param starNum
     * @param totalStar
     */
    public static void setStar(TextView startext, int starNum, int totalStar) {
        StringBuilder sb = new StringBuilder();
        Context context = startext.getContext();
        if (starNum > totalStar)
            totalStar = starNum;
        for (int i = 0; i < totalStar; i++) {
            if (i < starNum)
                sb.append(context.getString(R.string.dileber_icon_star_full));
            else
                sb.append(context.getString(R.string.dileber_icon_star_empty));
            if (i != starNum - 1)
                sb.append("");
        }
        startext.setTypeface(SApplication.default_icon_font);
        startext.setText(sb.toString());
    }

    /**
     * 给普通textview添加图标
     * @param tv
     * @param str
     */
    public static void setTextViewFont(TextView tv, @StringRes int str){
        tv.setTypeface(SApplication.default_icon_font);
        tv.setText(tv.getContext().getString(str));
    }

    /**
     * 给普通textview添加多个图标
     * @param tv
     * @param str
     */
    public static void setTextViewFonts(TextView tv,@StringRes int[] str){
        StringBuilder sb = new StringBuilder();
        Context context = tv.getContext();
        for(int r :str){
            sb.append(context.getString(r));
        }
        tv.setTypeface(SApplication.default_icon_font);
        tv.setText(sb.toString());
    }

}
