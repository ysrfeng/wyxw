package com.xiangmu.wyxw.conent_frament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.utils.CommonUtil;
import com.xiangmu.wyxw.utils.LogUtils;
import com.xiangmu.wyxw.utils.ServerURL;
import com.xiangmu.wyxw.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;


public class Fragment_Top extends Fragment {
    private ListView lv;
    private TextView tv_nobody;
    private RelativeLayout fg_rlayout;

    private HttpUtils httpUtils;
    private BitmapUtils bitmapUtils;
    private HttpHandler<String> handler;
    private String TopUrl;
    private int page = 0;
    private TopAdapter topAdapter;
    private List<Top.ArticlesEntity> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_shang_tou_tiao, null);
        lv = (ListView) view.findViewById(R.id.lv);
        fg_rlayout = (RelativeLayout) view.findViewById(R.id.fg_rlayout);
        tv_nobody = (TextView) view.findViewById(R.id.tv_nobody);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if ((i + i1) == i2) {
                    fg_rlayout.setVisibility(View.VISIBLE);//将加载进度 设置为可见
                }
            }
        });
          //加载更多
        fg_rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.e("---page", "" + page);
                page = page + 10;
                LogUtils.e("---page", ""+page);
                TopUrl = ServerURL.TopUrl+page+"-"+(page+10)+".html";
                LogUtils.e("---", TopUrl);
                getData(TopUrl);
                fg_rlayout.setVisibility(View.GONE);
            }
        });
        TopUrl = ServerURL.TopUrl+page+"-"+(page+10)+".html";
        inintData(TopUrl);
        return view;
    }
    private void inintData(String url){
        if (!CommonUtil.isNetWork(getActivity())){
            String result = SharedPreferencesUtil.getData(getActivity(), url, "");
            if (!TextUtils.isEmpty(result)) {
                paserData(result);
            }
        }else {
            getData(url);
        }
    }

    private void getData(final String url) {
        if (!url.equals("")) {
            httpUtils = new HttpUtils();
            handler = httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    if (responseInfo.result != null) {
                        SharedPreferencesUtil.saveData(getActivity(), url, responseInfo.result);
                        paserData(responseInfo.result);
                    }
                }
                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "链接地址有误.........", Toast.LENGTH_SHORT).show();
        }
    }
    private void paserData(String result) {
        Top top = new Gson().fromJson(result, Top.class);
        list = top.articles;
        if (list.size() > 0) {
            if (page == 0) {
                topAdapter = new TopAdapter(list, getActivity());
                lv.setAdapter(topAdapter);
            }else {
                list.addAll(topAdapter.getList());
                topAdapter.setList(list);
            }
            CommonUtil.setListViewHeightBasedOnChildren(lv);
        } else {
            tv_nobody.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (httpUtils != null) {
            handler.cancel();
        }
        if (bitmapUtils != null) {
            bitmapUtils.cancel();
        }
    }
}
