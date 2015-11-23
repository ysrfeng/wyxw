package com.xiangmu.wyxw.conent_frament;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xiangmu.wyxw.Bean.YueDuBean;
import com.xiangmu.wyxw.CostomAdapter.YueDuAdapter;
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.activitys.YueDuDetialActivity;
import com.xiangmu.wyxw.pullrefreshview.PullToRefreshBase;
import com.xiangmu.wyxw.pullrefreshview.PullToRefreshListView;
import com.xiangmu.wyxw.utils.CommonUtil;
import com.xiangmu.wyxw.utils.LogUtils;
import com.xiangmu.wyxw.utils.ServerURL;
import com.xiangmu.wyxw.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/9.
 */
public class YueDuFrament extends Fragment {
    private PullToRefreshListView rListView;
    private ImageButton back;
    private YueDuAdapter adapter;
    private HttpUtils httpUtils;
    private HttpHandler<String> httpHandler;
    private List<YueDuBean.推荐Entity> list = new ArrayList<>();
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        initdata();
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            if (view == null) {
                view = initview(inflater);
            }
            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //初始化控件
    private View initview(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.yuedu_content, null, false);
        httpUtils = new HttpUtils();
        initPullTorefresh(inflate);
        return inflate;
    }

    private void initPullTorefresh(View inflate) {
        rListView = (PullToRefreshListView) inflate.findViewById(R.id.refresh);

        initdata();
        rListView.setPullLoadEnabled(false);  //上拉加载，屏蔽
//        rListView.setPullLoadEnabled(true);
        rListView.setScrollLoadEnabled(true); //设置滚动加载可用
        rListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {//下拉刷新
                if (CommonUtil.isNetWork(getActivity())){
                    getData(ServerURL.yueDuURL,true);
                    String stringDate = CommonUtil.getStringDate();
                    rListView.setLastUpdatedLabel(stringDate);
                }else {
                    Toast.makeText(getActivity(),"网络不给力",Toast.LENGTH_SHORT).show();
                    rListView.onPullDownRefreshComplete();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {//上拉加载
                if (CommonUtil.isNetWork(getActivity())){
                    getData(ServerURL.yueDuURLJiaZai,false);
                }else {
                    Toast.makeText(getActivity(),"网络不给力",Toast.LENGTH_SHORT).show();
                    rListView.onPullUpRefreshComplete();
                }
            }
        });

        rListView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String replyid = adapter.getList().get(position).replyid;
                Intent intent = new Intent(getActivity(), YueDuDetialActivity.class);
                intent.putExtra("yueduDetial", replyid);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.zcdh_set_in, R.anim.zcdh_alpha_out);
            }
        });
    }

    //初始化数据
    private void initdata() {
        if (!CommonUtil.isNetWork(getActivity())) {
            String result = SharedPreferencesUtil.getData(getActivity(), ServerURL.yueDuURL, "");
            if (!TextUtils.isEmpty(result)) {//如果缓存有数据,直接Gson解析
                paserData(result,false);
            }
        } else {
            getData(ServerURL.yueDuURL,false);
        }
    }

    //从网络获取数据
    private void getData(final String url, final boolean isRefresh) {
        LogUtils.e("----------", url);
        if (!url.equals("")) {
            httpHandler = httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    LogUtils.e("--------", responseInfo.result);
                    SharedPreferencesUtil.saveData(getActivity(), url, responseInfo.result);
                    paserData(responseInfo.result,isRefresh);//Gson解析数据
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(getActivity(), "数据请求失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //解析数据并添加到集合
    private void paserData(String result,boolean isRefresh) {
        YueDuBean yueDuBean = new Gson().fromJson(result, YueDuBean.class);
        if (isRefresh == true){
            adapter = new YueDuAdapter(getActivity(), yueDuBean.推荐);
            rListView.getRefreshableView().setAdapter(adapter);
        }else {
            if (adapter == null) {
                adapter = new YueDuAdapter(getActivity(), yueDuBean.推荐);
                rListView.getRefreshableView().setAdapter(adapter);
            } else {
                list.addAll(adapter.getList());
                adapter.setList(list);
            }
        }
        rListView.onPullDownRefreshComplete();
        rListView.onPullUpRefreshComplete();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (httpUtils != null) {
            httpHandler.cancel();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
