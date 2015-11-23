package com.xiangmu.wyxw.Setting_Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.utils.LogUtils;

/**
 * Created by Administrator on 2015/11/18.
 */
public class ZiTiScale {

    public static void zitiStyle(final Context context, WebView webView) {
        final WebSettings settings = webView.getSettings();
        final SharedPreferences sharedPreferences = context.getSharedPreferences("hgz", Context.MODE_APPEND);
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        String string = sharedPreferences.getString("teda1", null);
        if (string == null) {
            LogUtils.e("------------>", "==string==" + string);
            LogUtils.e("------------>", "默认的字体为小号");
            settings.setSupportZoom(true);
            settings.setBuiltInZoomControls(true);
            settings.setTextSize(WebSettings.TextSize.SMALLER);
        } else {
            LogUtils.e("------------>", "==string==" + string);
            switch (string) {
                case "teda":
                    settings.setSupportZoom(true);
                    settings.setBuiltInZoomControls(true);
                    settings.setTextSize(WebSettings.TextSize.LARGER);
                    break;
                case "dahaozi":
                    settings.setSupportZoom(true);
                    settings.setBuiltInZoomControls(true);
                    settings.setTextSize(WebSettings.TextSize.LARGER);
                    break;
                case "zhonghaozi":
                    settings.setSupportZoom(true);
                    settings.setBuiltInZoomControls(true);
                    settings.setTextSize(WebSettings.TextSize.NORMAL);
                    break;
                case "xiaohaozi":
                    settings.setSupportZoom(true);
                    settings.setBuiltInZoomControls(true);
                    settings.setTextSize(WebSettings.TextSize.SMALLER);
                    break;

            }
        }
    }

    public static void zitiStyle2(final Context context, WebView webView) {
        final WebSettings settings = webView.getSettings();
        final SharedPreferences sharedPreferences = context.getSharedPreferences("hgz", Context.MODE_APPEND);
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("修改昵称");
        View view = LayoutInflater.from(context).inflate(R.layout.hgz_ziti_scale, null);
        final TextView teda = (TextView) view.findViewById(R.id.teda);
        final TextView dahaozi = (TextView) view.findViewById(R.id.dahaozi);
        final TextView zhonghaozi = (TextView) view.findViewById(R.id.zhonghaozi);
        final TextView xiaohaozi = (TextView) view.findViewById(R.id.xiaohaozi);
        teda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teda.setTextColor(Color.YELLOW);
                settings.setSupportZoom(true);
                settings.setBuiltInZoomControls(true);
                settings.setTextSize(WebSettings.TextSize.LARGEST);
                LogUtils.e("----------->", "点击改为特大号字体");
                edit.putString("teda1", "teda");
                edit.commit();
                LogUtils.e("----------->", "点击改为特大号字体2");
            }
        });
        dahaozi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dahaozi.setTextColor(Color.YELLOW);
                settings.setSupportZoom(true);
                settings.setBuiltInZoomControls(true);
                settings.setTextSize(WebSettings.TextSize.LARGER);
                edit.putString("teda1", "dahaozi");
                edit.commit();
            }
        });
        zhonghaozi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhonghaozi.setTextColor(Color.YELLOW);
                settings.setSupportZoom(true);
                settings.setBuiltInZoomControls(true);
                settings.setTextSize(WebSettings.TextSize.NORMAL);
                edit.putString("teda1", "zhonghaozi");
                edit.commit();
            }
        });
        xiaohaozi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiaohaozi.setTextColor(Color.YELLOW);
                settings.setSupportZoom(true);
                settings.setBuiltInZoomControls(true);
                settings.setTextSize(WebSettings.TextSize.SMALLER);
                edit.putString("teda1", "xiaohaozi");
                edit.commit();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setView(view);//自定义的布局
        //将builder创建
        builder.create();
        //将builder显示
        builder.show();
    }
}
