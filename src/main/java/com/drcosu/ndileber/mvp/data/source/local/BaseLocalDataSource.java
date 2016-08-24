package com.drcosu.ndileber.mvp.data.source.local;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;

import com.drcosu.ndileber.app.ActivityManager;
import com.drcosu.ndileber.tools.HContentProvider;
import com.drcosu.ndileber.tools.HPref;
import com.drcosu.ndileber.utils.Check;

/**
 * 本地数据基本源,包括sharepreference和数据库源
 * Created by shidawei on 16/8/4.
 */
public class BaseLocalDataSource {

    /**
     * sharepreference 管理器
     */
    protected HPref hPref = HPref.getInstance();

    protected DBManager dbManager = DBManager.getInstance();

    protected HContentProvider hContentProvider = HContentProvider.getInstance();



}
