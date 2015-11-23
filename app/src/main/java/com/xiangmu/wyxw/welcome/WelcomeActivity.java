package com.xiangmu.wyxw.welcome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.activitys.MainActivity;

public class WelcomeActivity extends AppCompatActivity implements ScrollViewListener {
    private LinearLayout mLLAnim;
    private WelcomeScollView mSVmain;
    private int mScrollViewHeight;
    private int mStartAnimateTop;
    private boolean hasStart = false;
    private TextView tvInNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        boolean isfirst = getSharedPreferences("welcome", MODE_PRIVATE).getBoolean("isfirst", true);
        initView();
        if (isfirst) {
            setView();
            //跳转页面  不再加载引导页面
        } else {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }
    private void initView() {
        mLLAnim= (LinearLayout) findViewById(R.id.ll_anim);
        mSVmain = (WelcomeScollView) findViewById(R.id.sv_main);
        tvInNew= (TextView) findViewById(R.id.tvInNew);

    }
    private void setView() {
        mSVmain.setScrollListener(this);
        mLLAnim.setVisibility(View.INVISIBLE);
        tvInNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //同上  跳转页面
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                getSharedPreferences("welcome", MODE_PRIVATE).edit().putBoolean("isfirst", false).commit();
                finish();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mScrollViewHeight = mSVmain.getHeight();
        mStartAnimateTop = mScrollViewHeight / 5 * 4;
    }

    @Override
    public void scrollViewlistener(int top, int oldTop) {
        int animTop = mLLAnim.getTop() - top;
        if (top > oldTop) {
            if (animTop < mStartAnimateTop && !hasStart) {
                Animation anim = AnimationUtils.loadAnimation(this, R.anim.show);
                mLLAnim.setVisibility(View.VISIBLE);
                mLLAnim.startAnimation(anim);
                hasStart = true;
            }
        } else {
            if (animTop > mStartAnimateTop && hasStart) {
                Animation anim = AnimationUtils.loadAnimation(this, R.anim.close);
                mLLAnim.setVisibility(View.INVISIBLE);
                mLLAnim.startAnimation(anim);
                hasStart = false;
            }
        }
    }
}

