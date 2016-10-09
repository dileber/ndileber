package com.drcosu.ndileber.mvp.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shidawei on 16/2/9.
 */
public class DBHelper extends SQLiteOpenHelper {

    private List<String> createSql;

    private String DATABASE_NAME;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,List<String> createSql) {
        super(context, name, factory, version);
        DATABASE_NAME = name;
        this.createSql = createSql;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler,List<String> createSql) {
        super(context, name, factory, version, errorHandler);
        DATABASE_NAME = name;
        this.createSql = createSql;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        if(createSql==null||createSql.size()==0){
            throw new NullPointerException("数据库创建语句为空");
        }
        for(String create:createSql){
            sqLiteDatabase.execSQL(create);
        }
    }

    /**
     * android_metadata是默认的表
     * 当SQLite数据库中包含自增列时，会自动建立一个名为 sqlite_sequence 的表。
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Logger.sl(Log.DEBUG,"数据库版本有变动","DROP TABLE IF EXISTS " ,DATABASE_NAME,i," fdsa ",i1);
        Cursor cursor = sqLiteDatabase.rawQuery("select name from sqlite_master where type='table' and name !='android_metadata' and name!='sqlite_sequence' order by name", null);
        while(cursor.moveToNext()){
            //遍历出表名
            String name = cursor.getString(0);
            Logger.sl(Log.DEBUG,"数据库表名",name);

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + name);
        }
        onCreate(sqLiteDatabase);
    }
}
