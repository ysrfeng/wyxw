package com.xiangmu.wyxw.activitys;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xiangmu.wyxw.Bean.Top;
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.Setting_Utils.ShareUtils;
import com.xiangmu.wyxw.conent_frament.Fragment_HuiGu;
import com.xiangmu.wyxw.conent_frament.Fragment_Top;
import com.xiangmu.wyxw.utils.ServerURL;
import com.xiangmu.wyxw.utils.SharedPreferencesUtil;

public class ShangTouTiaoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton back;
    private ImageButton more;
    private ScrollView stt_scrollView;
    private ImageButton jieshao;
    private ImageView lay_imgsrc;
    private TextView tv_topicName;
    private TextView tv_topicDesc;
    private TextView tv_attendnum;
    RadioButton rb1;
    RadioButton rb2;
    private HttpUtils httpUtils;
    private BitmapUtils bitmapUtils;
    private HttpHandler<String> handler;
    String TopUrl = ServerURL.TopUrl + "0-10.html";
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangtoutiao);
        bindViews();
    }

    // End Of Content View Elements
    private void bindViews() {
        back = (ImageButton) findViewById(R.id.back);
        more = (ImageButton) findViewById(R.id.more);
        stt_scrollView = (ScrollView) findViewById(R.id.stt_scrollView);
        stt_scrollView.post(new Runnable() {
            @Override
            public void run() {
                stt_scrollView.smoothScrollTo(0, 0);//自动回顶部
            }
        });

        stt_scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    View childAt = ((ScrollView) view).getChildAt(0);
                    if (childAt.getMeasuredHeight() <= view.getScrollY() + view.getHeight()) {

                    }
                }
                return false;
            }
        });
        jieshao = (ImageButton) findViewById(R.id.jieshao);
        rg = (RadioGroup) findViewById(R.id.rg);
        lay_imgsrc = (ImageView) findViewById(R.id.lay_imgsrc);
        tv_topicName = (TextView) findViewById(R.id.tv_topicName);
        tv_topicDesc = (TextView) findViewById(R.id.tv_topicDesc);
        tv_attendnum = (TextView) findViewById(R.id.tv_attendnum);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rg = (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(2000);
                sAnim.setFillAfter(true);
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton radioBtn = (RadioButton) group.getChildAt(i);
                    if (radioBtn.isChecked()) {
                        radioBtn.startAnimation(sAnim);
                    } else {
                        radioBtn.clearAnimation();
                    }
                }
            }
        });
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        back.setOnClickListener(this);
        more.setOnClickListener(this);
        jieshao.setOnClickListener(this);

        inintData();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new Fragment_Top()).commit();
    }

    private void inintData() {
        String result = SharedPreferencesUtil.getData(this, TopUrl, "");
        if (!TextUtils.isEmpty(result)) {
            paserData(result);
        }
        getData(TopUrl);
    }

    private void getData(final String url) {
        if (!url.equals("")) {
            httpUtils = new HttpUtils();
            handler = httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    if (responseInfo.result != null) {
                        SharedPreferencesUtil.saveData(ShangTouTiaoActivity.this, url, responseInfo.result);
                        paserData(responseInfo.result);
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(ShangTouTiaoActivity.this, "数据请求失败,请检查网络连接...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    String topicName;
    String topicDesc;
    String imgsrc;

    private void paserData(String result) {
        Top top = new Gson().fromJson(result, Top.class);
        topicName = top.topicName;
        topicDesc = top.topicDesc;
        imgsrc = top.imgsrc;
        tv_topicName.setText("# " + topicName + " #");
        tv_topicDesc.setText(topicDesc);
        tv_attendnum.setText(top.attendnum + "人参与");
        bitmapUtils = new BitmapUtils(this);
        bitmapUtils.configDefaultLoadingImage(R.mipmap.stt_bg);
        bitmapUtils.display(lay_imgsrc, imgsrc);
        stt_scrollView.setVisibility(View.VISIBLE);//数据填充完毕显示布局
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (httpUtils != null) {
            handler.cancel();
        }
        if (bitmapUtils != null) {
            bitmapUtils.cancel();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb1:
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new Fragment_Top()).commit();
                break;
            case R.id.rb2:
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new Fragment_HuiGu()).commit();
                break;
            case R.id.back:
                ShangTouTiaoActivity.this.finish();
                break;
            case R.id.more:
                initPopWindow(view);
                break;
            case R.id.jieshao:
                Toast.makeText(this, "这里是上头条的简介哦....", Toast.LENGTH_SHORT).show();
                initJianaJiewView(more);//初始化上头条介绍界面
                break;

            case R.id.refresh://刷新
                popupWindow.dismiss();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new Fragment_Top()).commit();
                Toast.makeText(this, "正在刷新中...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share://TODO: 2015/11/14 加分享 标题:topicName; 内容:topicDesc; 图片连接:imgsrc
                popupWindow.dismiss();
                ShareUtils.shareContent(this, "# " + topicName + " #" + "\n" + topicDesc, imgsrc);
                break;
            case R.id.quxiao://取消
                popupWindow.dismiss();
                break;
        }
    }

    //上头条简介popWindow
    private void initJianaJiewView(View view) {
        int width = (int) (getWindowManager().getDefaultDisplay().getWidth());
        int height = getWindowManager().getDefaultDisplay().getHeight();
        PopupWindow jianJiewWindow = new PopupWindow(width, height - 100);
        View jianJieView = View.inflate(this, R.layout.popwindow_sttjianjie, null);
        jianJiewWindow.setContentView(jianJieView);
        jianJiewWindow.setFocusable(true);
        jianJiewWindow.setBackgroundDrawable(new ColorDrawable(0));
        jianJiewWindow.showAsDropDown(view, 0, 0);
    }

    PopupWindow popupWindow;
    Button refresh;
    Button share;
    Button quxiao;

    //tittleBar左侧菜单popWindow
    private void initPopWindow(View view) {
        int width = (int) (getWindowManager().getDefaultDisplay().getWidth() / 2.5);
        int height = getWindowManager().getDefaultDisplay().getHeight() / 3;
        popupWindow = new PopupWindow(width, height);
        View popwindow_more = View.inflate(this, R.layout.popwindow_more, null);
        popupWindow.setContentView(popwindow_more);
        //点击空白区关闭窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.showAsDropDown(view, 0, 0);
        refresh = (Button) popwindow_more.findViewById(R.id.refresh);
        share = (Button) popwindow_more.findViewById(R.id.share);
        quxiao = (Button) popwindow_more.findViewById(R.id.quxiao);
        refresh.setOnClickListener(this);
        share.setOnClickListener(this);
        quxiao.setOnClickListener(this);
    }
}
