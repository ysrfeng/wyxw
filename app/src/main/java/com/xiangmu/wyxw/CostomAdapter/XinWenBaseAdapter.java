package com.xiangmu.wyxw.CostomAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.utils.LogUtils;
import com.xiangmu.wyxw.utils.XinWenXiData;
import com.xiangmu.wyxw.utils.XinWen_adapter;
import com.xiangmu.wyxw.utils.XinWen_toutiao;
import com.xiangmu.wyxw.utils.XutilsGetData;

import java.util.List;

/**
 * Created by Administrator on 2015/11/11.
 */
public class XinWenBaseAdapter extends BaseAdapter {
    //头条数据的listview的adapter
    private List<XinWen_toutiao.T1348647853363Entity> toutiao_list;
    private Context context;

    public XinWenBaseAdapter(Context context, List<XinWen_toutiao.T1348647853363Entity> toutiao_list) {
        this.context = context;
        this.toutiao_list = toutiao_list;
    }

    public List<XinWen_toutiao.T1348647853363Entity> getToutiao_list() {
        return toutiao_list;
    }

    public void setToutiao_list(List<XinWen_toutiao.T1348647853363Entity> toutiao_list) {
        this.toutiao_list = toutiao_list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return toutiao_list.size();
    }

    @Override
    public Object getItem(int i) {
        return toutiao_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }




    //重写系统方法
    @Override
    public int getItemViewType(int position) {
        String skipType = toutiao_list.get(position).getSkipType();
        int type = XinWen_adapter.getType(skipType);
        return type;
    }

    //重写方法返回4中类型
    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        XinWenXiData data=new XinWenXiData();//设置跳转详细页面数据类
        int type=getItemViewType(i);
        Viewholder putong = null;
        ViewholderZT zhuanti = null;
        ViewholderZB zhibo = null;
        ViewholderImage duotu = null;
        if (view == null) {
            switch (type) {
                case XinWen_adapter.TYPE_putong:
                    LogUtils.e("skiptype", "TYPE_putong");
                    putong = new Viewholder();
                    view = View.inflate(context, R.layout.xinwen_toutiao_item_putong, null);
                    putong.toutiaop_imagesrc = (ImageView) view.findViewById(R.id.toutiaop_imgsrc);
                    putong.toutiaop_title = (TextView) view.findViewById(R.id.toutiaop_title);
                    putong.toutiaop_replaycount = (TextView) view.findViewById(R.id.toutiaop_replaycount);
                    putong.toutiaop_digest = (TextView) view.findViewById(R.id.toutiaop_digest);
                    view.setTag(putong);
                    break;
                case XinWen_adapter.TYPE_zhuanti:
                    LogUtils.e("skiptype", "TYPE_zhuanti");
                    zhuanti = new ViewholderZT();
                    view = View.inflate(context, R.layout.xinwen_toutiao_item_zhuanti, null);
                    zhuanti.toutiaozt_imagesrc = (ImageView) view.findViewById(R.id.toutiaozt_imgsrc);
                    zhuanti.toutiaozt_title = (TextView) view.findViewById(R.id.toutiaozt_title);
                    zhuanti.toutiaozt_digest = (TextView) view.findViewById(R.id.toutiaozt_digest);
                    view.setTag(zhuanti);
                    break;
                case XinWen_adapter.TYPE_zhibo:
                    LogUtils.e("skiptype", "TYPE_zhibo");
                    zhibo = new ViewholderZB();
                    view = View.inflate(context, R.layout.xinwen_toutiao_item_zhibo, null);
                    zhibo.toutiaozb_imagesrc = (ImageView) view.findViewById(R.id.toutiaozb_imgsrc);
                    zhibo.toutiaozb_title = (TextView) view.findViewById(R.id.toutiaozb_title);
                    zhibo.toutiaozb_digest = (TextView) view.findViewById(R.id.toutiaozb_digest);
                    view.setTag(zhibo);
                    break;
                case XinWen_adapter.type_duotu:
                    LogUtils.e("skiptype", "type_duotu");
                    duotu = new ViewholderImage();
                    view = View.inflate(context, R.layout.xinwen_toutiao_item_duotu, null);
                    duotu.toutiaodt_replaycount = (TextView) view.findViewById(R.id.toutiaodt_replaycount);
                    duotu.toutiaodt_title = (TextView) view.findViewById(R.id.toutiaodt_title);
                    duotu.toutiaodt_imagesrc1 = (ImageView) view.findViewById(R.id.toutiaodt_imgsrc1);
                    duotu.toutiaodt_imagesrc2 = (ImageView) view.findViewById(R.id.toutiaodt_imgsrc2);
                    duotu.toutiaodt_imagesrc3 = (ImageView) view.findViewById(R.id.toutiaodt_imgsrc3);
                    view.setTag(duotu);
                    break;
            }
        } else {
            switch (type) {
                case XinWen_adapter.TYPE_putong:
                    LogUtils.e("skiptype", "elseTYPE_putong" + view.getTag());
                    putong = (Viewholder) view.getTag();
                    break;
                case XinWen_adapter.TYPE_zhuanti:
                    zhuanti = (ViewholderZT) view.getTag();
                    break;
                case XinWen_adapter.TYPE_zhibo:
                    zhibo = (ViewholderZB) view.getTag();
                    break;
                case XinWen_adapter.type_duotu:
                    duotu = (ViewholderImage) view.getTag();
                    break;
            }
        }
        switch (type) {
            case XinWen_adapter.TYPE_putong:
                putong.toutiaop_title.setText(toutiao_list.get(i).getTitle() + "");
                putong.toutiaop_replaycount.setText(toutiao_list.get(i).getReplyCount() + "");
                putong.toutiaop_digest.setText(toutiao_list.get(i).getDigest() + "");
                XutilsGetData.xUtilsImageiv(putong.toutiaop_imagesrc, toutiao_list.get(i).getImgsrc(), context,false);
                break;
            case XinWen_adapter.TYPE_zhuanti:
                zhuanti.toutiaozt_title.setText(toutiao_list.get(i).getTitle() + "");
                zhuanti.toutiaozt_digest.setText(toutiao_list.get(i).getDigest() + "");
                XutilsGetData.xUtilsImageiv(zhuanti.toutiaozt_imagesrc, toutiao_list.get(i).getImgsrc(), context,false);
                break;
            case XinWen_adapter.TYPE_zhibo:
                zhibo.toutiaozb_title.setText(toutiao_list.get(i).getTitle() + "");
                zhibo.toutiaozb_digest.setText(toutiao_list.get(i).getDigest() + "");
                XutilsGetData.xUtilsImageiv(zhibo.toutiaozb_imagesrc, toutiao_list.get(i).getImgsrc(), context,false);
                break;
            case XinWen_adapter.type_duotu:
                duotu.toutiaodt_title.setText(toutiao_list.get(i).getTitle() + "");
                duotu.toutiaodt_replaycount.setText(toutiao_list.get(i).getReplyCount() + "");
                XutilsGetData.xUtilsImageiv(duotu.toutiaodt_imagesrc1, toutiao_list.get(i).getImgsrc(), context,false);
                List<XinWen_toutiao.T1348647853363Entity.ImgextraEntity> imagextralist=toutiao_list.get(i).getImgextra();
                if (imagextralist!=null){
                    XutilsGetData.xUtilsImageiv(duotu.toutiaodt_imagesrc2, imagextralist.get(0).getImgsrc(), context,false);
                    XutilsGetData.xUtilsImageiv(duotu.toutiaodt_imagesrc3, imagextralist.get(1).getImgsrc(), context,false);
                }
                break;
        }
        return view;
    }

    //普通条目布局的viewholder
    public class Viewholder {
        ImageView toutiaop_imagesrc;
        TextView toutiaop_title;
        TextView toutiaop_digest;
        TextView toutiaop_replaycount;
    }
    public class ViewholderZT {
        ImageView toutiaozt_imagesrc;
        TextView toutiaozt_title;
        TextView toutiaozt_digest;
    }
    public class ViewholderZB {
        ImageView toutiaozb_imagesrc;
        TextView toutiaozb_title;
        TextView toutiaozb_digest;
    }

    public class ViewholderImage {
        TextView toutiaodt_title;
        TextView toutiaodt_replaycount;
        ImageView toutiaodt_imagesrc1;
        ImageView toutiaodt_imagesrc2;
        ImageView toutiaodt_imagesrc3;

    }
}

