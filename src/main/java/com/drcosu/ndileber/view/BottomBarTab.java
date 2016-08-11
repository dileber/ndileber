package com.drcosu.ndileber.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.drcosu.ndileber.R;
import com.drcosu.ndileber.tools.SFont;

/**
 * 菜单下面的 按钮 可以传入图标或者字体图标
 * 使用如下
 * mBar= (BottomBar) findViewById(R.id.bar);
 *  mBar.addItem(new BottomBarTab(this,R.string.home2,BottomBarTab.TYPE_FONT)).
 *  addItem(new BottomBarTab(this,R.string.bubbles,BottomBarTab.TYPE_FONT)).
 *  addItem(new BottomBarTab(this,R.string.newspaper,BottomBarTab.TYPE_FONT)).
 *  addItem(new BottomBarTab(this,R.string.cogs,BottomBarTab.TYPE_FONT));
 * Created by shidawei on 16/8/10.
 */
public class BottomBarTab extends FrameLayout {

    private ImageView mIcon;

    private SFont fIcon;
    private int type;

    private int mTabPosition = -1;

    private Context mContext;

    public final static int TYPE_IMAGE = 1;

    public final static int TYPE_FONT = 2;


    public BottomBarTab(Context context,int icon,int type) {
        this(context, null, icon,type);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int icon,int type) {
        this(context, attrs, 0, icon,type);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon,int type) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.type = type;
        //设置背景 波纹超出边界
        TypedArray typedArray = context.obtainStyledAttributes(
                new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);

        setBackground(drawable);
        //回收
        typedArray.recycle();
        if(type == TYPE_FONT){
            initFont(context, icon);
        }else{
            init(context, icon);
        }


    }

    private void init(Context context, int icon) {

        mIcon = new ImageView(context);

        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(size, size);
        params.gravity = Gravity.CENTER;
        mIcon.setImageResource(icon);
        mIcon.setLayoutParams(params);
        mIcon.setColorFilter(Color.parseColor("#c9c9c9"));
        addView(mIcon);
    }


    private void initFont(Context context, int icon){
        fIcon = new SFont(context);

        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        fIcon.setGravity(Gravity.CENTER);
        fIcon.setText(getResources().getString(icon));
        int textsize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics());
        fIcon.setTextSize(textsize);
        fIcon.setLayoutParams(params);
        fIcon.setTextColor(Color.parseColor("#c9c9c9"));
        addView(fIcon);

    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if(type == TYPE_FONT){
            if (selected) {
                fIcon.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            } else {
                fIcon.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unselect));
            }
        }else {
            if (selected) {
                mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
            } else {
                mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.tab_unselect));
            }
        }

    }

    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }
}