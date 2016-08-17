package com.drcosu.ndileber.mvp.data;

import com.drcosu.ndileber.mvp.data.source.local.BaseLocalDataSource;
import com.drcosu.ndileber.mvp.data.source.remote.BaseRemoteDataSource;

/**
 * Created by shidawei on 16/8/17.
 */
public class BaseRepository<T1 extends BaseLocalDataSource,T2 extends BaseRemoteDataSource> {

    protected T1 localDataSource = null;

    protected T2 remoteDataSource = null;

    protected BaseRepository(T1 localDataSource, T2 remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

}
