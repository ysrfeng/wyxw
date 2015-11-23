package com.xiangmu.wyxw.Setting_Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/11/16.
 */
public class MyDate {
    //获得系统时间(24小时时间格式)
    public static String getDate(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
        String time = dateFormat.format(new Date());
        return time;
    }
}
