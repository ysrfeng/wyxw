package com.xiangmu.wyxw.Setting_Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by Administrator on 2015/11/11.
 */
public class FontsType {
    //字体的
    static String 行楷 = "fonts/xingkai.ttf";
    static String 宋楷 = "fonts/songti.ttf";
    static String 鱼楷 = "fonts/yuti.ttf";
    static String 楷体 = "fonts/kaiti.ttf";

    public static Typeface type(AssetManager assets, String path) {
        if (path.equals(行楷)) {
            Typeface typeface1 = Typeface.createFromAsset(assets, 行楷);
            return typeface1;
        }
        if (path.equals(宋楷)) {
            Typeface typeface1 = Typeface.createFromAsset(assets, 宋楷);
            return typeface1;
        }
        if (path.equals(鱼楷)) {
            Typeface typeface1 = Typeface.createFromAsset(assets, 鱼楷);
            return typeface1;
        }else {
            Typeface typeface1 = Typeface.createFromAsset(assets, 楷体);
            return typeface1;
        }
    }
    //字体的大小
    public static float fontScale(Context context, float scale) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        float rate = (scale * (float) screenHeight / 1280);
        Log.e("------>", "==rate==" + rate);
        return rate;
    }
}
