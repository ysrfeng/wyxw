package com.xiangmu.wyxw.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.xiangmu.wyxw.R;

public class ReadChievement extends AppCompatActivity implements View.OnClickListener {
    private ImageView backlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
    }

    private void getIntentData() {
        int number = getIntent().getIntExtra("number", 0);
        Log.e("aa", "" + number);
        if (number != 0) {
            if (number > 100) {
                Log.e("aa", "----------" + number);
                setContentView(R.layout.hgz_set_page_layout);
            } else {
                setContentView(R.layout.hgz_activity_read_chievement);
                initView();
            }
        }
    }

    private void initView() {
        backlogin = (ImageView) findViewById(R.id.backlogin);
        backlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backlogin:
//                Intent intent = new Intent(this, SheZhiFrament.class);
//                startActivity(intent);
                overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
                finish();
                break;
        }
    }
}
