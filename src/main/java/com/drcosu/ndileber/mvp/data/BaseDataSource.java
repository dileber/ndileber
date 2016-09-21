package com.drcosu.ndileber.mvp.data;

import java.util.List;

/**
 * Created by shidawei on 16/8/4.
 */
public interface BaseDataSource {

    interface BaseCallback<T> {

        void onSuccess(T t);

        void onFailure(DataSourceException e);


    }

}
