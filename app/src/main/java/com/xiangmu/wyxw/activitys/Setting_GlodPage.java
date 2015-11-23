package com.xiangmu.wyxw.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiangmu.wyxw.R;

public class Setting_GlodPage extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout zhuanjinbi, huajinbi;
    private ImageView backsetting;
    private TextView jinbiCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting__glod_page);
        initView();
        jinbiCount.setText("5");
    }

    private void initView() {
        backsetting = (ImageView) findViewById(R.id.backsetting);
        zhuanjinbi = (RelativeLayout) findViewById(R.id.zhuanjinbi);
        huajinbi = (RelativeLayout) findViewById(R.id.huajinbi);
        jinbiCount= (TextView) findViewById(R.id.jinbiCount);
        backsetting.setOnClickListener(this);
        zhuanjinbi.setOnClickListener(this);
        huajinbi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backsetting:
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                finish();
                break;
            case R.id.zhuanjinbi:

                break;
            case R.id.huajinbi:

                break;
        }
    }
}
