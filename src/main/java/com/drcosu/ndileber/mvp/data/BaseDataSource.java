package com.drcosu.ndileber.mvp.data;

import java.util.List;

/**
 * Created by shidawei on 16/8/4.
 */
public interface BaseDataSource {

    interface BaseCallback<T> {

        /**
         * 请求成功返回数据
         * @param t
         */
        void onSuccess(T t);

        /**
         * 请求失败，网络错误
         * @param e
         */
        void onFailure(DataSourceException e);

    }

}
