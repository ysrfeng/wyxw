package com.xiangmu.wyxw.activitys;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xiangmu.wyxw.Bean.SearchBean;
import com.xiangmu.wyxw.CostomAdapter.MyGridViewAadapter;
import com.xiangmu.wyxw.CostomAdapter.SearchResultAdapter;
import com.xiangmu.wyxw.CostomProgressDialog.CustomProgressDialog;
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.pullrefreshview.PullToRefreshListView;
import com.xiangmu.wyxw.utils.CommonUtil;
import com.xiangmu.wyxw.utils.LogUtils;
import com.xiangmu.wyxw.utils.MySqlitehelper;
import com.xiangmu.wyxw.utils.ServerURL;
import com.xiangmu.wyxw.utils.SharedPreferencesUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    // Content View Elements
    private ImageButton back;
    private TextView noHotWords;
    private ImageView clear_history;
    private LinearLayout layoutsearchResult;
    private RelativeLayout layout_sousuoHis;
    private PullToRefreshListView lv_searchResult;
    private SearchView search_view;
    private GridView houtWord_gridview;
    private GridView gv_searchHistory;
    private MyGridViewAadapter gridViewAadapter;
    private List<String> list = new ArrayList<>();
    private HttpUtils httpUtils;
    private HttpHandler<String> handler;
    private String tuijian;//推荐热词
    private String keywords;//搜索关键字
    private SearchResultAdapter searchResultAdapter;
    private MySqlitehelper mySqlitehelper;
    private SQLiteDatabase writableDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bindViews();

        mySqlitehelper = new MySqlitehelper(this);
        writableDatabase = mySqlitehelper.getWritableDatabase();
        queryDB();//查询数据
    }

    private void bindViews() {
        back = (ImageButton) findViewById(R.id.back);
        clear_history = (ImageView) findViewById(R.id.clear_history);
        noHotWords = (TextView) findViewById(R.id.noHotWords);
        layoutsearchResult = (LinearLayout) findViewById(R.id.searchResult);//搜索结果布局
        layout_sousuoHis = (RelativeLayout) findViewById(R.id.layout_sousuoHis);//搜索历史布局
        lv_searchResult = (PullToRefreshListView) findViewById(R.id.lv_searchResult);//搜索结果
        houtWord_gridview = (GridView) findViewById(R.id.houtWord_gridview);//热词推荐
        gv_searchHistory = (GridView) findViewById(R.id.gv_searchHistory);//搜索历史
        search_view = (SearchView) findViewById(R.id.search_view);
        search_view.setSubmitButtonEnabled(true);//是否显示确认搜索按钮
        search_view.setIconified(false);//设置搜索框默认展开
//        android:imeOptions="actionSearch" 设置点击输入法自动匹配是确认,下一条...
//        app:defaultQueryHint="请输入关键字..."  设置输入框展开默认显示文字
        search_view.onActionViewExpanded();//表示在内容为空时不显示取消的x按钮，内容不为空时显示.

        inintHotWordsData();//加载热词推荐数据
        inintClick();
    }
    MyGridViewAadapter adapterHistory = null;
    //查询数据库
    private void queryDB() {
        String searchWord = null;
        List<String> historyList = new ArrayList<>();
        Cursor cursor = writableDatabase.query("searchHistory", null, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            searchWord = cursor.getString(cursor.getColumnIndex("searchWord"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            LogUtils.e("--->searchHistory",searchWord+"  "+url);
            historyList.add(searchWord);
        }
        cursor.close();
        //设置gv_searchHistory属性
        int size = historyList.size();
        int length = 100;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gv_searchHistory.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gv_searchHistory.setColumnWidth(itemWidth); // 设置列表项宽
        gv_searchHistory.setHorizontalSpacing(5); // 设置列表项水平间距
        gv_searchHistory.setStretchMode(GridView.NO_STRETCH);
        gv_searchHistory.setNumColumns(size); // 设置列数量=列表集合数
        if (historyList.size()>0){
            adapterHistory = new MyGridViewAadapter(historyList,this);
            gv_searchHistory.setAdapter(adapterHistory);
            layout_sousuoHis.setVisibility(View.VISIBLE);
        }
    }


    //初始化各监听事件
    private void inintClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.this.finish();
            }
        });

        //搜索历史记录item监听
        gv_searchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String word = adapterHistory.getList().get(i);
                Toast.makeText(SearchActivity.this, word, Toast.LENGTH_SHORT).show();
                search_view.setQuery(word, false);
                initSearchNews(ServerURL.searchUrl1 + word + ServerURL.searchUrl2);//执行新闻搜索请求
            }
        });

        //清空搜索历史
        clear_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchActivity.this,"正在清空搜索历史...",Toast.LENGTH_SHORT).show();
                layout_sousuoHis.setVisibility(View.GONE);
                int delete = writableDatabase.delete("searchHistory", null, null);
                if (delete >= 1){
                    Toast.makeText(SearchActivity.this,"搜索历史清空完成...",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //热词推荐
        houtWord_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tuijian = gridViewAadapter.getList().get(i);
                keywords = tuijian;
                search_view.setQuery(tuijian, true);
            }
        });


        search_view.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });

        //搜索框文本变化监听
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                keywords = query;
                initSearchNews(ServerURL.searchUrl1 + keywords + ServerURL.searchUrl2);//执行新闻搜索请求
                //添加数据
                ContentValues contentValues = new ContentValues();
                contentValues.put("url",ServerURL.searchUrl1 + keywords + ServerURL.searchUrl2);
                contentValues.put("searchWord", keywords);
                writableDatabase.insert("searchHistory", null, contentValues);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    layoutsearchResult.setVisibility(View.GONE);//隐藏搜索结果布局
                    queryDB();
                }
                return true;
            }
        });

        lv_searchResult.setPullLoadEnabled(false);
        lv_searchResult.setPullRefreshEnabled(false);
        lv_searchResult.setScrollLoadEnabled(false);
        lv_searchResult.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String docid = searchResultAdapter.getList().get(i).docid;
                Intent intent = new Intent(SearchActivity.this, YueDuDetialActivity.class);
                intent.putExtra("yueduDetial", docid);
                startActivity(intent);
                overridePendingTransition(R.anim.zcdh_set_in, R.anim.zcdh_alpha_out);
                layoutsearchResult.setVisibility(View.GONE);//隐藏搜索结果布局
                queryDB();
            }
        });
    }

    //初始化新闻搜索数据请求
     CustomProgressDialog progressDialog;
    private void initSearchNews(String url) {
        progressDialog = new CustomProgressDialog(this,"数据正在请求中...", R.anim.donghua_frame);
        if (!CommonUtil.isNetWork(this)) {//无网络读缓存
            if (keywords != null) {
                LogUtils.e("---", url);
                String searchResult = SharedPreferencesUtil.getData(this, url, "");
                if (!TextUtils.isEmpty(searchResult)) {
                    paserData(2, searchResult);
                }
            }
        } else {
            if (keywords != null) {
                getData(2, url);
                progressDialog.show();
            }
        }
    }

    //初始化热词推荐数据
    private void inintHotWordsData() {
        noHotWords.setVisibility(View.VISIBLE);
        if (!CommonUtil.isNetWork(this)) {
            String result = SharedPreferencesUtil.getData(this, ServerURL.tuiJianWord, "");
            if (!TextUtils.isEmpty(result)) {
                paserData(1, result);
            }
        } else {
            getData(1, ServerURL.tuiJianWord);//如果无缓存再去请求网络
        }
    }

    private void getData(int flag, final String url) {
        if (!url.equals("")) {
            httpUtils = new HttpUtils();
            switch (flag) {
                case 1:
                    handler = httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            if (responseInfo.result != null) {
                                SharedPreferencesUtil.saveData(SearchActivity.this, url, responseInfo.result);
                                paserData(1, responseInfo.result);
                            }
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(SearchActivity.this, "数据请求失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                case 2:
                    handler = httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            if (responseInfo.result != null) {
                                SharedPreferencesUtil.saveData(SearchActivity.this, url, responseInfo.result);
                                paserData(2, responseInfo.result);
                            }
                        }
                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(SearchActivity.this, "数据请求失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    }

    private void paserData(int flag, String result) {
        switch (flag) {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("hotWordList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        String hotWord = jo.getString("hotWord");
                        list.add(hotWord);
                    }
                    gridViewAadapter = new MyGridViewAadapter(list, this);
                    houtWord_gridview.setAdapter(gridViewAadapter);
                    noHotWords.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                SearchBean searchBean = new Gson().fromJson(result, SearchBean.class);
                LogUtils.e("---", searchBean.doc.result.get(0).title);
                layout_sousuoHis.setVisibility(View.GONE);//隐藏搜索历史
                progressDialog.dismiss();
                layoutsearchResult.setVisibility(View.VISIBLE);//显示搜索结果布局
                searchResultAdapter = new SearchResultAdapter(searchBean.doc.result, this);
                lv_searchResult.getRefreshableView().setAdapter(searchResultAdapter);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (httpUtils != null) {
            handler.cancel();
        }
        if (writableDatabase != null){
            writableDatabase.close();
        }
    }
}
