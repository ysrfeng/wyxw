package com.xiangmu.wyxw.CostomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangmu.wyxw.Bean.SearchBean;
import com.xiangmu.wyxw.R;

import java.util.List;

/**
 * Created by Administrator on 2015/11/16.
 */
public class SearchResultAdapter extends BaseAdapter {
    private List<SearchBean.DocEntity.ResultEntity> list;
    private Context context;

    public List<SearchBean.DocEntity.ResultEntity> getList() {
        return list;
    }

    public SearchResultAdapter(List<SearchBean.DocEntity.ResultEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoudle2 viewHoudle2 = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_searchresult, null);
            viewHoudle2 = new ViewHoudle2();
            viewHoudle2.result_title = (TextView) view.findViewById(R.id.result_title);
            viewHoudle2.result_ptime = (TextView) view.findViewById(R.id.result_ptime);
            view.setTag(viewHoudle2);
        } else {
            viewHoudle2 = (ViewHoudle2) view.getTag();
        }
        String title = list.get(i).title;//专家：<em>中国</em>需要股权分散的B类企业
        String str = title.replace("<em>", "");
        String replace = str.replace("</em>", "");
        viewHoudle2.result_title.setText(replace);
        viewHoudle2.result_ptime.setText(list.get(i).ptime);
        return view;
    }
    class ViewHoudle2 {
        TextView result_title,result_ptime;
    }
}
