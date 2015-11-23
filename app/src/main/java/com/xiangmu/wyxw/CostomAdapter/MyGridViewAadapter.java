package com.xiangmu.wyxw.CostomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangmu.wyxw.R;

import java.util.List;


/**
 * Created by Administrator on 2015/11/14.
 */
public class MyGridViewAadapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public MyGridViewAadapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ViewHoudle viewHoudle = null;
        if (view == null) {
            viewHoudle = new ViewHoudle();
            view = LayoutInflater.from(context).inflate(R.layout.item_gridview, null);
            viewHoudle.tv = (TextView) view.findViewById(R.id.tv);
            view.setTag(viewHoudle);
        } else {
            viewHoudle = (ViewHoudle) view.getTag();
        }
        viewHoudle.tv.setText(list.get(position));
        return view;
    }

    class ViewHoudle {
        TextView tv;
    }
}
