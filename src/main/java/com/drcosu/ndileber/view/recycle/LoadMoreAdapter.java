package com.drcosu.ndileber.view.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drcosu.ndileber.mvp.data.model.SModel;
import com.drcosu.ndileber.mvp.presenter.BasePresenter;

/**
 * Created by shidawei on 2017/4/27.
 */

public abstract class LoadMoreAdapter<VH extends DileberHolder,T extends SModel> extends DileberAdapter<DileberHolder,T>{

    private final int NORMALLAYOUT = 0x100;
    private final int FOOTERLAYOUT = 0x110;

    public LoadMoreAdapter(Context context) {
        super(context);
    }

    public LoadMoreAdapter(Context context, BasePresenter mBasePresenter) {
        super(context, mBasePresenter);
    }


    @Override
    public DileberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTERLAYOUT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(FooterHolder.getLayout(), parent, false);
            return new FooterHolder(view);
        }else{
            return onCreateLoadMoreViewHolder(parent, viewType);
        }
    }

    FooterHolder.FooterState mFooterState = FooterHolder.FooterState.Normal;

    public void setFootState(FooterHolder.FooterState footerState){
        mFooterState = footerState ;
        notifyDataSetChanged();
    }

    public void clearFootState(){
        mFooterState = FooterHolder.FooterState.Normal;
    }

    public abstract VH onCreateLoadMoreViewHolder(ViewGroup parent, int viewType);

    public abstract void addOtherViewHoderData(VH holder, int position);


    @Override
    public void addViewHolderData(DileberHolder holder, int position) {
        if(holder instanceof FooterHolder){
            FooterHolder mFooter = (FooterHolder) holder;
            mFooter.item = mFooterState;
        }else{
            addOtherViewHoderData((VH) holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return getValueCount()+1;
    }

    public abstract int getValueCount();

    @Override
    public int getItemViewType(int position) {
        if (position == getValueCount())
            return FOOTERLAYOUT;
        else
            return NORMALLAYOUT;
    }

}
