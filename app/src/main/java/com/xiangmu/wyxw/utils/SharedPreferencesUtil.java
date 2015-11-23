package com.xiangmu.wyxw.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
	private static String CONFIG = "config";
	private static SharedPreferences sp;
	//保存数据
	public static void saveData(Context context,String key,String data){
		if(sp == null){
			sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		sp.edit().putString(key, data).commit();
	}
	//读取缓存数据
	public static String getData(Context context,String key,String defValue){
		if(sp == null){
			sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		return sp.getString(key, defValue);
	}
}
