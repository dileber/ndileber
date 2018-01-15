package com.drcosu.ndileber.view.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drcosu.ndileber.mvp.data.model.SModel;
import com.drcosu.ndileber.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shidawei on 2017/4/27.
 */

public abstract class DileberAdapter <VH extends DileberHolder,T extends SModel> extends RecyclerView.Adapter<DileberHolder> implements IDileberData<T>{

    protected List<T> mModel = new ArrayList<>();

    protected Context mContext;

    public DileberAdapter(Context context){
        this.mContext = context;
    }

    protected BasePresenter mPresenter;

    public DileberAdapter(Context context,BasePresenter mBasePresenter){
        this(context);
        this.mPresenter = mBasePresenter;
    }

    @Override
    public void onBindViewHolder(DileberHolder holder, int position) {
        addViewHolderData((VH) holder,position);
        holder.load(mContext);
    }

    protected abstract void addViewHolderData(VH holder, int position);

    @Override
    public void addData(List<T> data) {
        mModel.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void addData(T data) {
        mModel.add(data);
        notifyDataSetChanged();
    }

    @Override
    public void refresh(List<T> data) {
        if(data!=null){
            mModel = data;
        }
        notifyDataSetChanged();
    }

    @Override
    public void clean() {
        mModel.clear();
        notifyDataSetChanged();
    }

}
