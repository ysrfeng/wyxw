package com.xiangmu.wyxw.BroadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            Toast.makeText(context, "检测到当前并未连接网络......",Toast.LENGTH_SHORT).show();
        } else if (wifiNetInfo.isConnected()) {
            Toast.makeText(context, "正在使用wifi连接,请放心使用......", Toast.LENGTH_SHORT).show();
        } else if (mobNetInfo.isConnected()) {
            Toast.makeText(context, "检测到正在使用移动网络,可能会消耗很多流量,建议在wifi下浏览.....", Toast.LENGTH_SHORT).show();
        } 
    }
}
