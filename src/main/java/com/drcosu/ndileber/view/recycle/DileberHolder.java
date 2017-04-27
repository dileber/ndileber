package com.drcosu.ndileber.view.recycle;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by shidawei on 2017/4/27.
 */

public abstract class DileberHolder extends RecyclerView.ViewHolder{

    public DileberHolder(View itemView) {
        super(itemView);
    }

    public abstract void load();

    protected <T extends View> T findView(int resId) {
        return (T) (itemView.findViewById(resId));
    }

}
