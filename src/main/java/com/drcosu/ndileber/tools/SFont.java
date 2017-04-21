package com.drcosu.ndileber.tools;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.drcosu.ndileber.app.SApplication;
import static com.drcosu.ndileber.utils.Check.checkNotNull;

/**
 * Created by shidawei on 16/2/3.
 */
public class SFont extends AppCompatTextView {

    public SFont(Context context) {
        this(context,null,0);
    }

    public SFont(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface typeface = SApplication.icon_font;
        checkNotNull(typeface,"字体图标为空");
        setTypeface(typeface);
    }



}
