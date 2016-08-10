package com.drcosu.ndileber.mvp.data.source.local;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }
}
