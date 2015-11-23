package com.xiangmu.wyxw.conent_frament;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xiangmu.wyxw.Bean.HuiGu;
import com.xiangmu.wyxw.CostomAdapter.HuiGuAdapter;
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.activitys.ShangTouTiao_HuiGuDetialActivity;
import com.xiangmu.wyxw.utils.CommonUtil;
import com.xiangmu.wyxw.utils.LogUtils;
import com.xiangmu.wyxw.utils.ServerURL;
import com.xiangmu.wyxw.utils.SharedPreferencesUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 上头条--->往期回顾
 * <p/>
 * Created by Administrator on 2015/11/13.
 */
public class Fragment_HuiGu extends Fragment {
    public ListView lv;
    public HttpUtils httpUtils;
    public HttpHandler<String> handler;
    public HuiGuAdapter huiGuAdapter;
    public List<HuiGu> list = new ArrayList<>();

    private int page = 0;
    private String HuiGuUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_shang_tou_tiao, null);
        lv = (ListView) view.findViewById(R.id.lv);
        //listview点击进入往期回顾的详细页面
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ShangTouTiao_HuiGuDetialActivity.class);
                intent.putExtra("topicid", huiGuAdapter.getList().get(i).topicid);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.zcdh_rotate2, R.anim.zcdh_alpha_out);
            }
        });

        HuiGuUrl = ServerURL.HuiGuUrl + page + "-" + (page + 10) + ".html";
        inintData(HuiGuUrl);
        return view;
    }

    private void inintData(String url) {
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
        }
    }

    private void paserData(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);

                String mtopicid = jo.getString("topicid");
                String mimgsrc = jo.getString("imgsrc");
                String mtopicname = jo.getString("topicname");
                String mctime = jo.getString("ctime");
                String mtopicDesc = jo.getString("topicDesc");
                list.add(new HuiGu(mtopicid, mimgsrc, mtopicname, mctime, mtopicDesc));

                LogUtils.e("---  list.size()", list.size() + "");
                LogUtils.e("---", mtopicid);
                LogUtils.e("---", mimgsrc);
                LogUtils.e("---", mtopicname);
                LogUtils.e("---", mctime);
                LogUtils.e("---", mtopicDesc);
            }
            if (huiGuAdapter == null) {
                huiGuAdapter = new HuiGuAdapter(getActivity(), list);
                lv.setAdapter(huiGuAdapter);
                CommonUtil.setListViewHeightBasedOnChildren(lv);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtils.e("---e", e + "");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (httpUtils != null) {
            handler.cancel();
        }
    }
}
