package com.drcosu.ndileber.mvp.fragment;

import android.app.Fragment;
import android.util.SparseArray;
import android.view.View;

import com.drcosu.ndileber.tools.UUi;


/**
 * Created by shidawei on 16/6/2.
 */
public class BaseFragment extends Fragment{

    /**
     * 主要用于缓存view
     * 用 sparseArray 代替hashmap是个性能上不错的选择
     * SparseArray来代替HashMap了，但是要注意SparseArray中的key是int类型\
     */
    protected final SparseArray<View> mViews = new SparseArray<View>();

    public <T extends View> T getView(int id) {
        return UUi.getView(getActivity(),mViews,id);
    }


}
