package com.xiangmu.wyxw.activitys;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.Setting_Utils.MyDate;


public class Setting_my_Task extends AppCompatActivity implements View.OnClickListener{
    private ImageView backsetting;
    private ImageView duihuan;
    private RelativeLayout fabiao, share, xinshang, read_text, read_news, open_client;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hgz_activity_my__task);
        initView();
        String date = MyDate.getDate();
        if (!date.equals("24:00:00")) {
            Drawable drawable = getResources().getDrawable(R.color.ve_place);
//            client_glod.setBackground(drawable);
//            jinbijifen.setBackground(drawable);
//            tv_client.setBackground(drawable);
            open_client.setBackground(drawable);
            Toast.makeText(this, "登录客户端成功金币+5", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        backsetting = (ImageView) findViewById(R.id.backsetting);
        duihuan = (ImageView) findViewById(R.id.duihuan);
        open_client = (RelativeLayout) findViewById(R.id.open_client);
        fabiao = (RelativeLayout) findViewById(R.id.fabiao);
        share = (RelativeLayout) findViewById(R.id.share);
        xinshang = (RelativeLayout) findViewById(R.id.xinshang);
        read_text = (RelativeLayout) findViewById(R.id.read_text);
        read_news = (RelativeLayout) findViewById(R.id.read_news);
        backsetting.setOnClickListener(this);
        fabiao.setOnClickListener(this);
        duihuan.setOnClickListener(this);
        share.setOnClickListener(this);
        xinshang.setOnClickListener(this);
        read_text.setOnClickListener(this);
        read_news.setOnClickListener(this);
        open_client.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backsetting:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
                break;
            case R.id.duihuan:
                Intent intent1 = new Intent(this, Setting_glodmall.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.open_client:
                Intent intent2 = new Intent(this, Task_ShuoMing.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.fabiao:
                Intent intent3 = new Intent(this, Task_ShuoMing.class);
                startActivity(intent3);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.share:
                Intent intent4 = new Intent(this, Task_ShuoMing.class);
                startActivity(intent4);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.xinshang:
                Intent intent5 = new Intent(this, Task_ShuoMing.class);
                startActivity(intent5);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.read_text:
                Intent intent6 = new Intent(this, Task_ShuoMing.class);
                startActivity(intent6);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.read_news:
                Intent intent7 = new Intent(this, Task_ShuoMing.class);
                startActivity(intent7);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }

}
