package com.xiangmu.wyxw.activitys;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import com.xiangmu.wyxw.CostomAdapter.TopAdapter;
import com.xiangmu.wyxw.CostomProgressDialog.CustomProgressDialog;
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.Setting_Utils.ShareUtils;
import com.xiangmu.wyxw.utils.CommonUtil;
import com.xiangmu.wyxw.utils.LogUtils;
import com.xiangmu.wyxw.utils.ServerURL;
import com.xiangmu.wyxw.utils.SharedPreferencesUtil;

import java.util.List;

public class ShangTouTiao_HuiGuDetialActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton back;
    private ImageButton more;
    private ScrollView huigu_scrollView;
    private ImageView mimgsrc;
    private TextView mtopicName;
    private TextView mtopicDesc;
    private TextView mattendnum;
    private TextView tv_nobody2;
    private ListView mlv;
    private RelativeLayout rlayout;

    private String topicid;
    private String DetialUrl;//详情地址url
    private int page = 0;
    private HttpUtils httpUtils;
    private BitmapUtils bitmapUtils;
    private HttpHandler<String> handler;
    private TopAdapter topAdapter = null;
    private CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangtoutiao_detial);
        Intent intent = getIntent();
        topicid = intent.getStringExtra("topicid");
        DetialUrl = ServerURL.HuiGuDetialUrl + topicid + "/" + page + "-" + (page + 10) + ".html";
        bindViews();
    }

    // End Of Content View Elements
    private void bindViews() {
        back = (ImageButton) findViewById(R.id.back);
        more = (ImageButton) findViewById(R.id.more);
        rlayout = (RelativeLayout) findViewById(R.id.rlayout);
        huigu_scrollView = (ScrollView) findViewById(R.id.huigu_scrollView);
        huigu_scrollView.post(new Runnable() {
            @Override
            public void run() {
                huigu_scrollView.smoothScrollTo(0, 0);//自动回顶部
            }
        });
        huigu_scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    View childAt = ((ScrollView) view).getChildAt(0);
                    if (childAt.getMeasuredHeight() <= view.getScrollY() + view.getHeight()) {
                        mlv.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView absListView, int i) {
                            }

                            @Override
                            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                                if ((i + i1) == i2) {
                                    rlayout.setVisibility(View.VISIBLE);//将加载进度 设置为可见
                                }
                            }
                        });
                    }
                }
                return false;
            }
        });

        mimgsrc = (ImageView) findViewById(R.id.mimgsrc);
        mtopicName = (TextView) findViewById(R.id.mtopicName);
        mtopicDesc = (TextView) findViewById(R.id.mtopicDesc);
        mattendnum = (TextView) findViewById(R.id.mattendnum);
        tv_nobody2 = (TextView) findViewById(R.id.tv_nobody2);
        mlv = (ListView) findViewById(R.id.mlv);
        back.setOnClickListener(this);
        more.setOnClickListener(this);
        progressDialog = new CustomProgressDialog(this, "正在加载中......", R.anim.donghua_frame);
        inintData(DetialUrl);
    }

    //加载更多
    public void onclick(View view) {
        LogUtils.e("---page", ""+page);
        page = page + 10;
        LogUtils.e("---page", ""+page);
        DetialUrl = ServerURL.HuiGuDetialUrl + topicid + "/" + page + "-" + (page + 10) + ".html";
        LogUtils.e("---", DetialUrl);
        getData(DetialUrl);
        rlayout.setVisibility(View.GONE);
    }

    private void inintData(String url) {
        LogUtils.e("---url", url);
        String result = SharedPreferencesUtil.getData(this, url, "");
        if (!TextUtils.isEmpty(result)) {
                paserData(result);
        }
        getData(url);
    }

    private void getData(final String url) {
        if (!url.equals("")) {
            progressDialog.show();
            httpUtils = new HttpUtils();
            handler = httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    if (responseInfo.result != null) {
                        SharedPreferencesUtil.saveData(ShangTouTiao_HuiGuDetialActivity.this, url, responseInfo.result);
                        paserData(responseInfo.result);
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(ShangTouTiao_HuiGuDetialActivity.this, "数据请求失败,请检查网络连接...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    List<Top.ArticlesEntity> list;
    String imageurl, topicName, topicDesc;

    private void paserData(String result) {
        Top top = new Gson().fromJson(result, Top.class);
        list = top.articles;
        if (list.size()>0){
            imageurl = top.imgsrc;
            topicName = top.topicName;
            topicDesc = top.topicDesc;
            mtopicName.setText("# " + topicName + " #");
            mtopicDesc.setText(top.topicDesc);
            mattendnum.setText(top.attendnum + "人参与");

            bitmapUtils = new BitmapUtils(this);
            bitmapUtils.configDefaultLoadingImage(R.mipmap.stt_bg);
            bitmapUtils.display(mimgsrc, imageurl);

            if (page == 0) {
                topAdapter = new TopAdapter(list, this);
                mlv.setAdapter(topAdapter);
            } else {
                list.addAll(topAdapter.getList());
                topAdapter.setList(list);
            }
            progressDialog.dismiss();
            CommonUtil.setListViewHeightBasedOnChildren(mlv);
        }else {
            tv_nobody2.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.more://弹出悬浮窗
                initPopWindow(view);
                break;
            case R.id.refresh:
                popupWindow.dismiss();
                if (list.size() > 0) {
                    list.clear();
                    topAdapter.notifyDataSetChanged();
                    getData(DetialUrl);
                }
                Toast.makeText(this, "正在刷新中...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share://TODO: 2015/11/14 加分享  详情地址:DetialUrl;标题:topicName;内容:topicDesc;图片连接 imageurl
                popupWindow.dismiss();
                ShareUtils.shareContent(this,"# " + topicName + " #"+"\n"+topicDesc,imageurl);
                break;
            case R.id.quxiao:
                popupWindow.dismiss();
                break;
        }
    }

    PopupWindow popupWindow;
    Button refresh;
    Button share;
    Button quxiao;

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
}
