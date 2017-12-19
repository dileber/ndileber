package com.drcosu.ndileber.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by shidawei on 16/8/10.
 */
public class BottomBar extends LinearLayout {


    private LinearLayout mTabLayout;

    private LayoutParams mTabParams;
    private int mCurrentPosition = 0;

    public BottomBar(Context context) {
        this(context, null);
    }

    public BottomBar(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public BottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        mTabLayout = new LinearLayout(context);
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mTabLayout.setLayoutParams(lp);
        addView(mTabLayout);

        mTabParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        mTabParams.weight = 1;
    }

    public BottomBar addItem(final BottomBarTab tab) {
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = tab.getTabPosition();

                if (mCurrentPosition == position) {

                } else {

                    if(onClickItemMenu !=null){
                        onClickItemMenu.onClickItem(mCurrentPosition,position);
                    }
                    tab.setSelected(true);
                    mTabLayout.getChildAt(mCurrentPosition).setSelected(false);
                    mCurrentPosition = position;
                }
            }
        });

        tab.setTabPosition(mTabLayout.getChildCount());
        tab.setLayoutParams(mTabParams);
        mTabLayout.addView(tab);
        return this;
    }

    /**
     * @param position
     */
    public void setCurrentItem(final int position) {
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                mTabLayout.getChildAt(position).performClick();
            }
        });
    }


    OnClickItemMenu onClickItemMenu = null;

    public interface OnClickItemMenu{
        void onClickItem(int nowPosition,int position);
    }

    public void setOnClickItemMenu(OnClickItemMenu onClickItemMenu) {
        this.onClickItemMenu = onClickItemMenu;
    }

    public int getmCurrentPosition() {
        return mCurrentPosition;
    }
}