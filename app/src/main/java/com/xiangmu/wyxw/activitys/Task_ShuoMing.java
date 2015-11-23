package com.xiangmu.wyxw.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.xiangmu.wyxw.R;

public class Task_ShuoMing extends AppCompatActivity {
private ImageView backsetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__shuo_ming);
        initView();
    }

    private void initView() {
        backsetting= (ImageView) findViewById(R.id.backsetting);
        backsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),Setting_my_Task.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            }
        });
    }
}
