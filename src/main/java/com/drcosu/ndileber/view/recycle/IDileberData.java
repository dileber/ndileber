package com.drcosu.ndileber.view.recycle;

import java.util.List;

/**
 * Created by shidawei on 2017/5/17.
 */

public interface IDileberData<T> {

    void addData(List<T> data);
    void addData(T data);
    void clean();

}
