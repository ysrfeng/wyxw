package com.xiangmu.wyxw.activitys;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.Setting_Utils.ShareUtils;
import com.xiangmu.wyxw.jieping.ScreenShot;
import com.xiangmu.wyxw.utils.DateTime;

import java.util.ArrayList;

public class PictureActivity extends AppCompatActivity {
    private int onclicitem;
    private LinearLayout linearLayout;
    private ArrayList<Drawable> arrayList;
    private ArrayList<String> stringList;
    private ImageView imageShow;
    private TextView text;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        if (path != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(path + ".png");
            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageBitmap(bitmap);
        } else {
            path = DateTime.getDate_Time();
        }
        if (arrayList == null) {
            arrayList = new ArrayList();
            arrayList.add(getResources().getDrawable(R.drawable.naodong_1));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_2));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_3));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_4));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_5));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_6));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_7));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_8));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_9));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_10));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_11));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_12));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_13));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_14));
            arrayList.add(getResources().getDrawable(R.drawable.naodong_15));
        }
        if (stringList == null) {
            stringList = new ArrayList<>();
            stringList.add("我和我的小伙伴都惊呆了");
            stringList.add("你懂得");
            stringList.add("麻蛋,老子裤子都脱了,你给我看这个?");
            stringList.add("你好流弊啊");
            stringList.add("我读书少,表骗我");
            stringList.add("不明觉里");
            stringList.add("我去年买了个表啊");
            stringList.add("翻滚吧,牛宝宝");
            stringList.add("笑死老子了");
            stringList.add("知道真相的我眼泪流下来");
            stringList.add("感觉不会再爱了");
            stringList.add("看我的手势...");
            stringList.add("静静的看你装13");
            stringList.add("2到无穷大");
            stringList.add("分分钟,又涨姿势了");
        }
        findViewById(R.id.loginimage_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // TODO: 2015/11/18  加动画  左至右
                //加动画  左至右
            }
        });
        findViewById(R.id.loginimage_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2015/11/18  加动画  左至右
                //点击分享  开始截图  存储   并将图片分享出去
                Bitmap bitmap1 = takeScreenShot();
                picpath = path + "涂鸦.png";
                ScreenShot.savePic(bitmap1, picpath);//存储图片
                handler.sendEmptyMessageAtTime(1, 500);

            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.parentview);
        imageShow = (ImageView) findViewById(R.id.imageShow);
        text = (TextView) findViewById(R.id.text);

    }

    private String picpath;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // TODO: 2015/11/18 添加分享功能  地址为 picpath
//            picpath 为成员变量能直接用  分享成功后销毁本页面
            ShareUtils.shareContent(PictureActivity.this, "跟帖截屏,值得一看哦!", picpath);

        }
    };

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onClick(View v) {
        ImageView imageView = (ImageView) linearLayout.getTag();
        if (imageView != null) {
            imageView.setBackground(getResources().getDrawable(R.drawable.touming));
        }
        switch (v.getId()) {
            case R.id.image1:
                imaSetBack(0, v);
                break;
            case R.id.image2:
                imaSetBack(1, v);
                break;
            case R.id.image3:
                imaSetBack(2, v);
                break;
            case R.id.image4:
                imaSetBack(3, v);
                break;
            case R.id.image5:
                imaSetBack(4, v);
                break;
            case R.id.image6:
                imaSetBack(5, v);
                break;
            case R.id.image7:
                imaSetBack(6, v);
                break;
            case R.id.image8:
                imaSetBack(7, v);
                break;
            case R.id.image9:
                imaSetBack(8, v);
                break;
            case R.id.image10:
                imaSetBack(9, v);
                break;
            case R.id.image11:
                imaSetBack(10, v);
                break;
            case R.id.image12:
                imaSetBack(11, v);
                break;
            case R.id.image13:
                imaSetBack(12, v);
                break;
            case R.id.image14:
                imaSetBack(13, v);
                break;
            case R.id.image15:
                imaSetBack(14, v);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void imaSetBack(int num, View v) {
        v.setBackground(getResources().getDrawable(R.drawable.titlebar_background));
        linearLayout.setTag(v);
        imageShow.setBackground(arrayList.get(num));
        text.setText(stringList.get(num));
    }

    public Bitmap takeScreenShot() {
        // View是你须要截图的View
        View view = this.getWindow().getDecorView();
        int width1 = getWindow().getDecorView().getWidth();
        int height1 = getWindow().getDecorView().getHeight();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        // 获取状况栏高度
        Rect frame = new Rect();
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        // 获取屏幕长和高
        int width = this.getWindowManager().getDefaultDisplay().getWidth();
        int height = this.getWindowManager().getDefaultDisplay().getHeight();
        // 去掉题目栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 90, statusBarHeight + 140, width - 200, height1 - statusBarHeight - 300);
        view.destroyDrawingCache();
        return b;
    }

}

