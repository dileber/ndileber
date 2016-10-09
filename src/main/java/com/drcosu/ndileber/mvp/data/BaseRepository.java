package com.drcosu.ndileber.mvp.data;

import com.drcosu.ndileber.app.BaseConfiger;
import com.drcosu.ndileber.mvp.data.source.local.BaseLocalDataSource;
import com.drcosu.ndileber.mvp.data.source.remote.BaseRemoteDataSource;
import com.drcosu.ndileber.tools.debug.DebugNoCacheMap;

import java.util.Map;

/**
 * Created by shidawei on 16/8/17.
 */
public class BaseRepository<T1 extends BaseLocalDataSource,T2 extends BaseRemoteDataSource> {

    protected T1 localDataSource = null;

    protected T2 remoteDataSource = null;

    protected Map<String,Object> cache = null;

    /**
     * 是否刷新缓存
     */
    private boolean refCache = false;

    protected BaseRepository(T1 localDataSource, T2 remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
        if(BaseConfiger.BUG_STATIC){
            cache = new DebugNoCacheMap();
        }
    }

    public void refOver(){
        refCache = false;
    }

    public void refStart(){
        refCache = true;
    }

    public boolean isRefCache(){
        return refCache;
    }

}
