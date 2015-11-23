package com.xiangmu.wyxw.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.xiangmu.wyxw.CostomAdapter.CollectionAdapter;
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.conent_frament.Collection_gentie;
import com.xiangmu.wyxw.conent_frament.Collection_news;
import com.xiangmu.wyxw.conent_frament.Collection_pic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/12.
 */
public class Setting_Collection extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private List<String> stringList;
    private PagerTabStrip pagerTabStrip;
    private ImageView backlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hgz_collection);
        initData();
        initView();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        Fragment fragment = new Fragment();
        Collection_news news = new Collection_news();
        Collection_gentie gentie = new Collection_gentie();
        Collection_pic pic = new Collection_pic();
        fragmentList.add(news);
        fragmentList.add(gentie);
        fragmentList.add(pic);
        stringList = new ArrayList<>();
        stringList.add("新闻");
        stringList.add("跟帖");
        stringList.add("图片");
    }

    private void initView() {
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setTabIndicatorColorResource(R.color.colorPrimary);
        backlogin= (ImageView) findViewById(R.id.backlogin);
        backlogin.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        CollectionAdapter adapter = new CollectionAdapter(getSupportFragmentManager(), fragmentList, stringList);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backlogin:
                overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
                finish();
                break;
        }
    }
}
