package com.xiangmu.wyxw.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlitehelper extends SQLiteOpenHelper{
    private static final String DATABASENAME = "newsSearch.db";
    private static int version = 1;
    public MySqlitehelper(Context context) {
        super(context, DATABASENAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table searchHistory(_id integer primary key autoincrement,url varchar,searchWord varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
