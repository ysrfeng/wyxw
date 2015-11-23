package com.xiangmu.wyxw.Setting_Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.xiangmu.wyxw.R;

/**
 * Created by Administrator on 2015/11/11.
 */
public class TouxiangName {
    public static void NiceName(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("修改昵称");
        View view = LayoutInflater.from(context).inflate(R.layout.hgz_personal_nickname, null);
        final EditText nackname = (EditText) view.findViewById(R.id.nickname);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                String nick = nackname.getText().toString();
                SharedPreferences preferences = context.getSharedPreferences("useInfo", Context.MODE_APPEND);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("user_Name", nick);
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
