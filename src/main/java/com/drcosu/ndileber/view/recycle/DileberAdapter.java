package com.drcosu.ndileber.view.recycle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shidawei on 2017/4/27.
 */

public abstract class DileberAdapter <VH extends DileberHolder> extends RecyclerView.Adapter<DileberHolder>{

    @Override
    public void onBindViewHolder(DileberHolder holder, int position) {
        addViewHolderData((VH) holder,position);
        holder.load();
    }

    public abstract void addViewHolderData(VH holder, int position);

}
