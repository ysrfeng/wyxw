package com.xiangmu.wyxw.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.Setting_Utils.ZiTiScale;

/**
 * Created by Administrator on 2015/11/12.
 */
public class Setting_set_page extends AppCompatActivity implements View.OnClickListener {
    ImageView backlogin;
    LinearLayout set, personal_set;
    TextView email, huancun, banbenhao;
    RelativeLayout bangdingpingtai;
    RelativeLayout chajianset;
    RelativeLayout typeset;
    RelativeLayout textSize;
    RelativeLayout newspush;
    RelativeLayout wifiOffline;
    RelativeLayout wifinoloading;
    RelativeLayout more_loading;
    RelativeLayout phone_cleancache;
    RelativeLayout isupdate;
    RelativeLayout helpfankui;
    RelativeLayout yingyongtuijian;
    RelativeLayout weiwangyipingfen;
    RelativeLayout fengmiangushi;
    RelativeLayout about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hgz_set_page_layout);
        initView();
    }

    private void initView() {
        backlogin = (ImageView) findViewById(R.id.backlogin);
        set = (LinearLayout) findViewById(R.id.set);
        personal_set = (LinearLayout) findViewById(R.id.personal_set);
        email = (TextView) findViewById(R.id.email);
        huancun = (TextView) findViewById(R.id.huancun);
        banbenhao = (TextView) findViewById(R.id.banbenhao);
        bangdingpingtai = (RelativeLayout) findViewById(R.id.bangdingpingtai);
        chajianset = (RelativeLayout) findViewById(R.id.chajianset);
        typeset = (RelativeLayout) findViewById(R.id.typeset);
        textSize = (RelativeLayout) findViewById(R.id.textSize);
        newspush = (RelativeLayout) findViewById(R.id.newspush);
        wifiOffline = (RelativeLayout) findViewById(R.id.wifiOffline);
        wifinoloading = (RelativeLayout) findViewById(R.id.wifinoloading);
        more_loading = (RelativeLayout) findViewById(R.id.more_loading);
        phone_cleancache = (RelativeLayout) findViewById(R.id.phone_cleancache);
        isupdate = (RelativeLayout) findViewById(R.id.isupdate);
        helpfankui = (RelativeLayout) findViewById(R.id.helpfankui);
        yingyongtuijian = (RelativeLayout) findViewById(R.id.yingyongtuijian);
        weiwangyipingfen = (RelativeLayout) findViewById(R.id.weiwangyipingfen);
        fengmiangushi = (RelativeLayout) findViewById(R.id.fengmiangushi);
        about = (RelativeLayout) findViewById(R.id.about);

        listenerEvent();
    }

    private void listenerEvent() {
        backlogin.setOnClickListener(this);
        set.setOnClickListener(this);
        personal_set.setOnClickListener(this);
        email.setOnClickListener(this);
        huancun.setOnClickListener(this);
        banbenhao.setOnClickListener(this);
        bangdingpingtai.setOnClickListener(this);
        chajianset.setOnClickListener(this);
        typeset.setOnClickListener(this);
        textSize.setOnClickListener(this);
        newspush.setOnClickListener(this);
        wifiOffline.setOnClickListener(this);
        wifinoloading.setOnClickListener(this);
        more_loading.setOnClickListener(this);
        phone_cleancache.setOnClickListener(this);
        isupdate.setOnClickListener(this);
        helpfankui.setOnClickListener(this);
        yingyongtuijian.setOnClickListener(this);
        weiwangyipingfen.setOnClickListener(this);
        fengmiangushi.setOnClickListener(this);
        about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backlogin:
//                Intent intent=new Intent(this, Setting_set_page.class);
//                startActivity(intent);
                overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
                finish();
                break;
            case R.id.personal_set:
                Intent intent2 = new Intent(this, Setting_set_person.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
                break;
            case R.id.bangdingpingtai:

                break;
            case R.id.chajianset:

                break;
            // TODO: 2015/11/18 字体
            case R.id.typeset:

                break;
            // TODO: 2015/11/19 字体大小
            case R.id.textSize:
                ZiTiScale.zitiStyle2(this, new WebView(this));
                break;
            case R.id.newspush:

                break;
            case R.id.wifiOffline:

                break;
            case R.id.wifinoloading:

                break;
            case R.id.more_loading:

                break;
            case R.id.phone_cleancache:

                break;
            case R.id.isupdate:

                break;
            case R.id.helpfankui:

                break;
            case R.id.yingyongtuijian:

                break;
            case R.id.weiwangyipingfen:

                break;
            case R.id.fengmiangushi:

                break;
            case R.id.about:

                break;
        }
    }
}
