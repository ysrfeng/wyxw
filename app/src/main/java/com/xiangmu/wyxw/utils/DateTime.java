package com.xiangmu.wyxw.utils;

import java.util.Calendar;

/**
 * Created by Administrator on 2015/11/18.
 */
public class DateTime {
    public static String getDate(){
        Calendar calendar=Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//获得年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int day = calendar.get(Calendar.DATE);//获取天数
        return year + "年" + month + "月" + day + "日" ;
    }
    public static String getTime() {
        Calendar calendar=Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return hour + "时" + minute + "分" + second + "秒";
    }
    public static String getDate_Time(){
        Calendar calendar=Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//获得年份
        int month = calendar.get(Calendar.MONTH)+1;//获取月份
        int day = calendar.get(Calendar.DATE);//获取天数
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return year + "年" + month + "月" + day + "日"
                +hour + "时" + minute + "分" + second + "秒";
    }
    public void initDate(){

    };
}
