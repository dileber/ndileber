package com.drcosu.ndileber.tools.selectdialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.drcosu.ndileber.R;
import com.drcosu.ndileber.mvp.data.model.SModel;
import com.drcosu.ndileber.mvp.data.model.SelectModel;
import com.drcosu.ndileber.tools.UUi;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择弹框
 *
 * ここは使いかだ
 final List<String> list = new ArrayList<String>();
 list.add("美女你好");
 list.add("帅哥你好");
 list.add("世界你好");
 //构造实例化选择弹窗
 SelectDialog chooseDialog = new SelectDialog.Builder(MainActivity.this)
 .setDataList(list)
 .setButtonColor(getResources().getColor(R.color.text_10))
 .setButtonSize(14)
 .setLastButtonSize(14)
 .setTitleText("编辑选择")
 .build();
 //对选择弹窗item点击事件监听
 chooseDialog.setButtonListener(new BaseNoticeWindow.OnButtonListener() {
@Override
public void onSureListener(View v) {
mShowTextView.setText(list.get((Integer) v.getTag()));
}
@Override
public void onDiscardListener(View v) {

}
@Override
public void onDismissListener(View v, int nType) {

}
});
 chooseDialog.show(mShowTextView);

 *
 * Created by liuhongxia on 2016/05/30
 */

public class SelectDialog<T extends SelectModel> extends BaseNoticeWindow implements OnClickListener {


    private List<T> mDataList = new ArrayList<>(0);

    private Context mContext;
    private String mTitleText = null;
    private int mTitleBackgroundColor = -1;
    private int mTitleTextColor = Color.WHITE;
    private int mButtonColor = -1;
    private int mTitleTextSize;
    private int mButtonSize;
    private int mLastButtonSize;

    private ArrayList<Button> buttonList = new ArrayList<Button>();



    public static class Builder<T extends SelectModel> {
        Context mContext;
        String mTitleText = null;
        int mTitleBackgroundColor = -1;
        int mTitleTextColor = Color.WHITE;
        int mButtonColor = -1;
        int mTitleTextSize = 18;
        int mButtonSize = 14;
        int mLastButtonSize = 17;
        List<T> mDataList;


        public Builder(Context context) {

            mContext = context;
        }

        public SelectDialog build() {

            return new SelectDialog(this);
        }

        public Builder setTitleText(String title) {

            if (TextUtils.isEmpty(title)) {

                return null;
            }

            mTitleText = title;
            return this;
        }

        public Builder setTitleBackground(int colorResId){
            mTitleBackgroundColor = colorResId;
            return this;
        }

        public Builder setTitleTextColor(int colorResId){
            mTitleTextColor = colorResId;
            return this;
        }
        public Builder setTitleTextSize(int textSize){
            mTitleTextSize = textSize;
            return this;
        }

        public Builder setButtonColor(int buttonColor) {

            mButtonColor = buttonColor;
            return this;
        }

        public Builder setButtonSize(int buttonSize) {

            mButtonSize = buttonSize;
            return this;
        }


        public Builder setLastButtonSize(int buttonSize) {

            mLastButtonSize = buttonSize;
            return this;
        }

        public Builder setDataList(List<T> list) {
            mDataList = list;
            return this;
        }
    }



    public SelectDialog(Builder builder) {
        mContext = builder.mContext;
        mTitleText = builder.mTitleText;
        mTitleBackgroundColor = builder.mTitleBackgroundColor;
        mTitleTextColor = builder.mTitleTextColor;
        mButtonColor = builder.mButtonColor;
        mDataList = builder.mDataList;
        mTitleTextSize = builder.mTitleTextSize;
        mButtonSize = builder.mButtonSize;
        mLastButtonSize = builder.mLastButtonSize;
        init();
    }

    private void init(){

        if (null == mDataList) {
            return;
        }
        //头部标题TextView
        LayoutParams titleParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, UUi.dip2px(mContext, 44));
        TextView titleTextView = new TextView(mContext);
        titleTextView.setLayoutParams(titleParams);
        titleTextView.setBackgroundColor(getColor(R.color.dileber_topbar));
        titleTextView.setTextColor(mTitleTextColor);
        titleTextView.setText(mTitleText);
        titleTextView.setTextSize(mTitleTextSize);
        titleTextView.setGravity(Gravity.CENTER);

        //用于添加button和textview的layout
        LinearLayout layout = new LinearLayout(mContext);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = UUi.dip2px(mContext, 50);
        layoutParams.rightMargin = UUi.dip2px(mContext, 50);
        layout.setLayoutParams(layoutParams);
        layout.setGravity(Gravity.CENTER);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(getColor(R.color.dileber_white));
        layout.addView(titleTextView);


        //button的属性
        LayoutParams btnParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, UUi.dip2px(mContext,44));
        btnParams.leftMargin = UUi.dip2px(mContext, 15);
        btnParams.rightMargin = UUi.dip2px(mContext, 15);

        //主Layout
        LinearLayout mainLayout = new LinearLayout(mContext);
        LayoutParams mainLayoutParams =new LayoutParams(
                LayoutParams.MATCH_PARENT,  mContext.getResources().getDisplayMetrics().heightPixels);
        mainLayout.setLayoutParams(mainLayoutParams);
        mainLayout.setGravity(Gravity.CENTER);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(getColor(R.color.dileber_alpha50));



        //textView的属性
        LayoutParams textParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, UUi.dip2px(mContext,1));
        textParams.leftMargin = UUi.dip2px(mContext, 15);
        textParams.rightMargin = UUi.dip2px(mContext, 15);

        int size = mDataList.size();
        for (int i = 0; i < size + 1; i++) {
            Button tDiscardBtn = new Button(mContext);
            buttonList.add(tDiscardBtn);
            tDiscardBtn.setLayoutParams(btnParams);
            tDiscardBtn.setGravity(Gravity.CENTER);
            if (i == size) {
                tDiscardBtn.setText("取消");
                tDiscardBtn.setTextSize(mLastButtonSize);
                tDiscardBtn.setBackgroundColor(getColor(R.color.dileber_white));
                tDiscardBtn.setTextColor(getColor(R.color.dileber_topbar));
            } else {
                tDiscardBtn.setText(mDataList.get(i).getTitle());
                tDiscardBtn.setTextSize(mButtonSize);
                tDiscardBtn.setBackgroundColor(getColor(R.color.dileber_white));
                tDiscardBtn.setTextColor(mButtonColor);

                tDiscardBtn.setTag(i);
            }
            final int mtag = i;
            tDiscardBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null) {
                        dismiss();
                        return;
                    }

                    if (v.getTag() != null) {
                        mListener.onSureListener(v,mDataList.get(mtag));
                    } else {
                        mListener.onDiscardListener(v);
                    }

                    Dismiss(v, BaseNoticeWindow.TYPE_DISMISS_BUTTON);
                }
            });
            tDiscardBtn.setGravity(Gravity.CENTER);
            TextView textView = new TextView(mContext);
            textView.setBackgroundColor(getColor(R.color.dileber_line));
            textView.setLayoutParams(textParams);

            layout.addView(tDiscardBtn);
            //去掉最后一行的线
            if (i != size) {
                layout.addView(textView);
            }
        }
        mainLayout.addView(layout);
        //设置按钮的属性
//        setAllButtonStyle();
        this.setContentView(mainLayout);
        this.setWidth(mContext.getResources().getDisplayMetrics().widthPixels);
        this.setHeight(mContext.getResources().getDisplayMetrics().heightPixels);
//        this.setAnimationStyle(R.style.menu_dialog_animation);
        this.setOutsideTouchable(true);
        mainLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Dismiss(view, BaseNoticeWindow.TYPE_DISMISS_NORMAL);
            }
        });
    }

    public void show(View parent) {
        if (!((Activity) mContext).isFinishing()) {
            this.showAtLocation(parent, Gravity.CENTER, 0, 0);
        }
    }

    public void setAllButtonTextColor(int colorId) {
        for (Button button : buttonList) {
            button.setTextColor(colorId);
        }
    }

    /**
     * 设置button的样式
     */
    public void setAllButtonStyle() {
        for (Button button : buttonList) {
            button.setTextSize(14);
            button.setBackgroundColor(getColor(R.color.dileber_white));
            button.setTextColor(getColor(R.color.dileber_text_10));
        }
    }

    private int getColor(@ColorRes int id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return mContext.getColor(id);
        }else{
            return mContext.getResources().getColor(id);
        }
    }

    @Override
    public void onClick(View v) {


    }

    private void Dismiss(View v, int nType) {

        if (null != mListener) {

            mListener.onDismissListener(v, nType);
        }

        dismiss();
    }


}


