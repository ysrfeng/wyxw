package com.xiangmu.wyxw.activitys;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiangmu.wyxw.R;

import java.util.ArrayList;
import java.util.List;

public class GenTieActivity extends AppCompatActivity {
    private ListView listView;
    private TextView textView;
    private android.view.animation.Animation animation;
    private List list;
    private View mNightView = null;
    private WindowManager mWindowManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mNightView == null) {
            mNightView = new TextView(this);
            mNightView.setBackgroundColor(0x75000000);
        }
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        setContentView(R.layout.activity_gen_tie);
        animation= AnimationUtils.loadAnimation(this,R.anim.nn);
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            list.add("aaa" + i);
        }
        listView.setAdapter(new MyListAdapter());
    }

    class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size() == 0 ? 0 : list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(GenTieActivity.this, R.layout.gentie_listview_item, null);
            }
//            textView = (TextView) convertView.findViewById(R.id.tv_one);
            final View finalConvertView = convertView;
            ImageView imageView = (ImageView) convertView.findViewById(R.id.zan);
            imageView.setBackground(getResources().getDrawable(R.drawable.biz_news_list_other_segments_support));
            imageView.setEnabled(true);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout relativeLayout = (RelativeLayout) v.getParent();
                    LinearLayout linearLayout = (LinearLayout) relativeLayout.getParent();
                    TextView zannum = (TextView) linearLayout.findViewById(R.id.zannum);
                    zannum.setText((Integer.parseInt(zannum.getText().toString()) + 1) + "");
                    textView = (TextView) relativeLayout.findViewById(R.id.tv_one);
                    Log.e("aa","---------"+textView);
                    Toast.makeText(GenTieActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                    textView.setVisibility(View.VISIBLE);
                    v.setBackground(getResources().getDrawable(R.drawable.biz_news_list_other_segments_support_done));
                    v.setEnabled(false);
                    textView.startAnimation(animation);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            textView.setVisibility(View.GONE);
                        }
                    }, 500);
                }
            });
            LinearLayout linearparent = (LinearLayout) convertView.findViewById(R.id.parentlinea);
            linearparent.removeAllViews();
            if (position == 3 | position == 8 | position == 13 | position == 17 | position == 28) {
                switch (position) {
                    case 3:
                        //到时候可以根据 字段判断具体添加几条回复
                        for (int i = 0; i < 2; i++) {
                            View view = View.inflate(GenTieActivity.this, R.layout.gentie_huifu_item, null);
                            if (i == 0) {
                                TextView textView = (TextView) view.findViewById(R.id.in_info);
                                textView.setText("吐槽者....死!!!!!!!!!!!");
                            }
                            TextView num = (TextView) view.findViewById(R.id.item_num);
                            num.setText("" + (i + 1));
                            linearparent.addView(view);
                        }
                        break;
                    case 8:
                        for (int i = 0; i < 5; i++) {
                            View view = View.inflate(GenTieActivity.this, R.layout.gentie_huifu_item, null);
                            if (i == 0) {
                                TextView textView = (TextView) view.findViewById(R.id.in_info);
                                textView.setText("吐槽者....死!!!!!!!!!!!");
                            }
                            TextView num = (TextView) view.findViewById(R.id.item_num);
                            num.setText("" + (i + 1));
                            linearparent.addView(view);
                        }
                        break;
                    case 13:
                        for (int i = 0; i < 2; i++) {
                            View view = View.inflate(GenTieActivity.this, R.layout.gentie_huifu_item, null);
                            if (i == 0) {
                                TextView textView = (TextView) view.findViewById(R.id.in_info);
                                textView.setText("吐槽者....死!!!!!!!!!!!");
                            }
                            TextView num = (TextView) view.findViewById(R.id.item_num);
                            num.setText("" + (i + 1));
                            linearparent.addView(view);
                        }
                        break;
                    case 17:
                        for (int i = 0; i < 9; i++) {
                            View view = View.inflate(GenTieActivity.this, R.layout.gentie_huifu_item, null);
                            if (i == 0) {
                                TextView textView = (TextView) view.findViewById(R.id.in_info);
                                textView.setText("吐槽者....死!!!!!!!!!!!");
                            }
                            TextView num = (TextView) view.findViewById(R.id.item_num);
                            num.setText("" + (i + 1));
                            linearparent.addView(view);
                        }
                        break;
                    case 28:
                        for (int i = 0; i < 2; i++) {
                            View view = View.inflate(GenTieActivity.this, R.layout.gentie_huifu_item, null);
                            if (i == 0) {
                                TextView textView = (TextView) view.findViewById(R.id.in_info);
                                textView.setText("吐槽者....死!!!!!!!!!!!");
                            }
                            TextView num = (TextView) view.findViewById(R.id.item_num);
                            num.setText("" + (i + 1));
                            linearparent.addView(view);
                        }
                        break;

                }
            }
            return convertView;
        }
    }

}
