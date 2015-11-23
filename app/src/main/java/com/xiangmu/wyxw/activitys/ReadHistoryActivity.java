package com.xiangmu.wyxw.activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xiangmu.wyxw.Bean.NewsBean;
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.utils.CalendarView;
import com.xiangmu.wyxw.utils.DateTime;
import com.xiangmu.wyxw.utils.ItemListener;
import com.xiangmu.wyxw.utils.MySqlOpenHelper;
import com.xiangmu.wyxw.utils.XinWenXiData;

import java.util.ArrayList;

public class ReadHistoryActivity extends AppCompatActivity implements ItemListener, AdapterView.OnItemClickListener {

    private CalendarView calendarView;
    private ListView listView;
    private ArrayList<NewsBean> arrayList;
    private MyReadAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_history);
//        日历  在本页面中
        calendarView = (CalendarView) findViewById(R.id.view);
        calendarView.setItemListener(this);
        listView = (ListView) findViewById(R.id.listView);
        String date = DateTime.getDate();
        arrayList = getlistDate(date);

        adapter = new MyReadAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public ArrayList getlistDate(String date) {
        arrayList = new ArrayList();
        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(this);
        SQLiteDatabase writableDatabase = mySqlOpenHelper.getWritableDatabase();
        Cursor cursor = writableDatabase.query("read_date", null, "date=?", new String[]{date}, null, null, null);
        while (cursor.moveToNext()) {
            String num = cursor.getString(2) + "";
            String title = cursor.getString(3) + "";
            String url = cursor.getString(4) + "";
            String replaycount = cursor.getString(5) + "";
            String lanMuType = cursor.getString(6) + "";
            arrayList.add(new NewsBean(date, num, title, url, replaycount, lanMuType));
        }
        return arrayList;
    }

    //接口回调  更新数据
    @Override
    public void itemlistener(String s) {
        arrayList = getlistDate(s);
        if (arrayList.size() == 0) {
            listView.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    // TODO: 2015/11/19  //点击进入历史记录的详情页面
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        String date = arrayList.get(position).getDate();
        String num = arrayList.get(position).getNum();
        String title = arrayList.get(position).getTitle();
        String url1 = arrayList.get(position).getUrl();
        String replaycount = arrayList.get(position).getReplaycount();
        String lanMuType = arrayList.get(position).getLanMuType();
        int Id = Integer.parseInt(num);
        Intent intent = null;
        if (Id == 1) {
            intent = new Intent(this, YueDuDetialActivity.class);
            intent.putExtra("yueduDetial", url1);
            intent.putExtra("Id", Id);
            startActivity(intent);
        } else if (Id == 2) {
            intent = new Intent(this, WebViewActivity.class);
            XinWenXiData xinWenXiData = new XinWenXiData();
            xinWenXiData.setUrl(url1);
            xinWenXiData.setTitle(title);
            xinWenXiData.setReplaycount(Integer.parseInt(replaycount));
            intent.putExtra("xinwendata", xinWenXiData);
            startActivity(intent);
        } else if (Id == 3) {
            intent = new Intent(this, XinWenXiActivity.class);
            XinWenXiData xinWenXiData = new XinWenXiData();
            xinWenXiData.setUrl(url1);
            xinWenXiData.setTitle(title);
            xinWenXiData.setLanMuType(Integer.parseInt(lanMuType));
            xinWenXiData.setReplaycount(Integer.parseInt(replaycount));
            intent.putExtra("xinwendata", xinWenXiData);
            startActivity(intent);

// TODO: 2015/11/18
//          要加动画
            overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out);
        }
    }

        class MyReadAdapter extends BaseAdapter {
            @Override
            public int getCount() {
                return arrayList.size() == 0 ? 0 : arrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return arrayList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(ReadHistoryActivity.this, R.layout.read_item, null);
                }
                TextView title = (TextView) convertView.findViewById(R.id.read_item);
                title.setText(arrayList.get(position).getTitle());
                return convertView;
            }
        }

}