package com.drcosu.ndileber.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

/**
 * 支持多行的editText同时支持imeOptions
 * Created by jing on 2016/8/19.
 */
public class MultiLineEditText extends AppCompatEditText{
    public MultiLineEditText(Context context) {
        super(context);
    }

    public MultiLineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiLineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 来创建和输入法的连接，设置输入法的状态，包括显示什么样的键盘布局。需要注意的地方是这部分代码：
     * @param outAttrs
     * @return
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection connection = super.onCreateInputConnection(outAttrs);
        if (connection == null) return null;
        //移除EditorInfo.IME_FLAG_NO_ENTER_ACTION标志位
        outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        return connection;
    }
}
