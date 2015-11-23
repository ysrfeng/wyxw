package com.xiangmu.wyxw.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Administrator on 2015/11/18.
 */
public class MyPost {
    MySqlOpenHelper mySqlOpenHelper;
    Context context;
    public MyPost(Context context) {
        this.context=context;
    }

    public boolean doPost(String name,String password) {
        mySqlOpenHelper = new MySqlOpenHelper(context);
        String names = Md5Utils.encodeBy32BitMD5(name);
        String passwords = Md5Utils.encodeBy32BitMD5(password);
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = mySqlOpenHelper.getWritableDatabase();
            Cursor cursor = writableDatabase.query("login_date", null, "name =?", new String[]{names}, null, null, null, null);
            while (cursor.moveToNext()) {
//                int password1 = cursor.getColumnIndex("password");
//                int url = cursor.getColumnIndex("url");
                String string = cursor.getString(cursor.getColumnIndex("password"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                if (passwords.equals(string)) {
                    context.getSharedPreferences("useInfo", Context.MODE_PRIVATE).edit().
                            putString("userName", name).commit();
                    Log.e("aaa", "----------userName"+name);
                    if (url != null) {
                        context.getSharedPreferences("useInfo", Context.MODE_PRIVATE).edit().putString("pic_path", url).commit();
                        Log.e("aaa","--------pic_path"+url);
                    }

                    cursor.close();
                    writableDatabase.close();
                    mySqlOpenHelper.close();
                    return true;
                }
            }
            cursor.close();
            writableDatabase.close();
            mySqlOpenHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

