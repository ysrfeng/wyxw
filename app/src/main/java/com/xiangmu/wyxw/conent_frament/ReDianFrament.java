package com.xiangmu.wyxw.conent_frament;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiangmu.wyxw.CostomAdapter.XinWenBaseAdapter;
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.activitys.WebViewActivity;
import com.xiangmu.wyxw.activitys.XinWenXiActivity;
import com.xiangmu.wyxw.pullrefreshview.PullToRefreshBase;
import com.xiangmu.wyxw.pullrefreshview.PullToRefreshListView;
import com.xiangmu.wyxw.utils.CommonUtil;
import com.xiangmu.wyxw.utils.LogUtils;
import com.xiangmu.wyxw.utils.XinWenJson;
import com.xiangmu.wyxw.utils.XinWenURL;
import com.xiangmu.wyxw.utils.XinWenXiData;
import com.xiangmu.wyxw.utils.XinWen_adapter;
import com.xiangmu.wyxw.utils.XinWen_toutiao;
import com.xiangmu.wyxw.utils.XutilsGetData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/9.
 */
public class ReDianFrament extends Fragment {
    String url;
    private XinWenBaseAdapter toutiao_adapter;
    private int daohangtype;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daohangtype = XinWen_adapter.getXinWenType("热点");//获得栏目的类型
        url = new XinWenURL().getRedian();
    }

    View contentview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            if (contentview == null) {
                contentview = initview(inflater);
            }
            return contentview;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private PullToRefreshListView toutiao_lv;
    LinearLayout titlebar;
    //初始化控件
    private View initview(LayoutInflater inflater) {
        final View view = inflater.inflate(R.layout.redian_view, null, false);
        titlebar= (LinearLayout) view.findViewById(R.id.redian_title_bar);
        toutiao_lv = (PullToRefreshListView) view.findViewById(R.id.xinwen_toutiao_lv);
//        toutiao_adapter = new XinWenBaseAdapter(getActivity(), toutiao_list);
//        toutiao_lv.getRefreshableView().setAdapter(toutiao_adapter);
        getdata(url, true);

        toutiao_lv.setPullLoadEnabled(false);  //上拉加载，屏蔽
//        toutiao_lv.setPullLoadEnabled(true);
        toutiao_lv.setScrollLoadEnabled(false); //设置滚动加载可用
        //设置上拉下拉的监听事件
        toutiao_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isScroll=false;//设置为不可以滚动获得控件高度
                //下拉刷新，重新获取数据，填充listview
                getdata(url, true);//刷新数据
                String stringDate = CommonUtil.getStringDate();// 下拉刷新时获取当前的刷新时间
                toutiao_lv.setLastUpdatedLabel(stringDate);//将时间添加到刷新的表头
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });

        return view;
    }

    private XutilsGetData xutilsGetData = new XutilsGetData();

    //网络请求获得数据 refresh   true为刷新数据  false为加载数据  存储根据url保存数据
    public void getdata(String url, final boolean refresh) {

        if (CommonUtil.isNetWork(getActivity())){
            //然后网络请求刷新数据
            xutilsGetData.xUtilsHttp(getActivity(), url, new XutilsGetData.CallBackHttp() {
                @Override
                public void handleData(String data) {
                    LogUtils.e("xinwenactivity==data==", data + "");
                    getshowdata(data, refresh);

                }
            },true);
        }else {
            String data = xutilsGetData.getData(getActivity(), url, null);
            //判断本地数据是否存在  如果没有网络请求
            if (data != null) {
                getshowdata(data, refresh);
            }
        }

    }

    private List<XinWen_toutiao.T1348647853363Entity> toutiao_list = new ArrayList<>();

    TextView footview;
    View headview;
    int height;
    boolean isScroll;
    // 显示数据
    private void getshowdata(String data, boolean refresh) {

        toutiao_list.clear();
        XinWen_toutiao toutiao_object = XinWenJson.getdata(data, daohangtype);//传入类型和数据
        LogUtils.e("toutiao_object", "" + toutiao_object);
        toutiao_list.addAll(toutiao_object.getT1348647853363());

        if (footview == null) {
            footview = new TextView(getActivity());
            footview.setText("亲,请等下一个24小时吧.....");
            footview.setTextSize(20);
            footview.setWidth(getActivity().getWindowManager().getDefaultDisplay().getWidth());
            footview.setGravity(Gravity.CENTER);
            toutiao_lv.getRefreshableView().addFooterView(footview);
        }

        if (headview==null){
            headview=View.inflate(getActivity(),R.layout.redian_listviewhead,null);
            //获得控件的宽高
            int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            headview.measure(w, h);
            height =headview.getMeasuredHeight();
            int width =headview.getMeasuredWidth();

            toutiao_lv.getRefreshableView().addHeaderView(headview);
        }

        //点击listview调用的方法
        toutiao_lv.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0||(position == toutiao_list.size()-1)){
                    return;
                }
                frament2activity(position);//跳转轮播详细页面
            }
        });

        toutiao_adapter = new XinWenBaseAdapter(getActivity(), toutiao_list);
        toutiao_lv.getRefreshableView().setAdapter(toutiao_adapter);
        toutiao_adapter.setToutiao_list(toutiao_list);//填充并刷新数据
        //设置listview的滑动监听
        toutiao_lv.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }

        });
        isScroll=true;
        toutiao_lv.onPullDownRefreshComplete();//隐藏下拉头
    }

    //跳转详细页面方法
    private void frament2activity(int position) {
        int pos = position-1;
        int bujutype = XinWen_adapter.getType(toutiao_list.get(pos).getSkipType());
        //传入详细页面的数据
        XinWenXiData xinWenXi = new XinWenXiData();
        xinWenXi.setBujuType(bujutype);
        xinWenXi.setLanMuType(daohangtype);
        xinWenXi.setReplaycount(toutiao_list.get(pos).getReplyCount());//跟帖数量
        xinWenXi.setTitle(toutiao_list.get(pos).getTitle());//标题
        xinWenXi.setXinwentext(toutiao_list.get(pos).getDigest());//内容

        //根据类型选择跳转的详细页面
        switch (bujutype) {
            case XinWen_adapter.TYPE_putong:
            case XinWen_adapter.TYPE_zhuanti:
            case XinWen_adapter.TYPE_zhibo:
                LogUtils.e("xinwenadapter", "TYPE_zhibo==" + bujutype);
                String urlzhibo = toutiao_list.get(pos).getUrl();
                xinWenXi.setUrl(urlzhibo);//详细页面url
                //跳转到详细页
                Intent intentzhibo = new Intent(getActivity(), WebViewActivity.class);
                intentzhibo.putExtra("xinwendata", xinWenXi);
                startActivity(intentzhibo);
                getActivity().overridePendingTransition(R.anim.xinwen_inactivity, R.anim.xinwen_inactivity);
                break;
            case XinWen_adapter.type_duotu:
                LogUtils.e("xinwenadapter", "type_duotu==" + bujutype);
                String urlduotuRight = toutiao_list.get(pos).getSkipID();
                String urlRighBefor = urlduotuRight.substring(urlduotuRight.lastIndexOf("|") - 4);
                String urlRight = urlRighBefor.replaceAll("\\|", "/");
                String urlduotu = "http://c.3g.163.com/photo/api/set/" + urlRight + ".json";
                //0096|81994    http://c.3g.163.com/photo/api/set/0096/82126.json
                xinWenXi.setUrl(urlduotu);//详细页面url
                //跳转到详细页
                Intent intentduotu = new Intent(getActivity(), XinWenXiActivity.class);
                intentduotu.putExtra("xinwendata", xinWenXi);
                startActivity(intentduotu);
                break;
        }

    }

}

