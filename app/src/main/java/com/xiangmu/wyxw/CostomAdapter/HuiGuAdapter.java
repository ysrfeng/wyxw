package com.xiangmu.wyxw.CostomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiangmu.wyxw.Bean.HuiGu;
import com.xiangmu.wyxw.R;

import java.util.List;

/**
 * Created by Administrator on 2015/11/14.
 */
public class HuiGuAdapter extends BaseAdapter {
    private Context context;
    private List<HuiGu> list;
    final int TYPE_1 = 1;
    final int TYPE_2 = 2;

    public HuiGuAdapter(Context context, List<HuiGu> list) {
        this.context = context;
        this.list = list;
    }

    public List<HuiGu> getList() {
        return list;
    }

    public void setList(List<HuiGu> list) {
        this.list = list;
        notifyDataSetChanged();
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
    public int getItemViewType(int i) {
        if (i == 0) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoudle1 viewHoudle1 = null;
        ViewHoudle2 viewHoudle2 = null;
        int type = getItemViewType(i);
        if (view == null) {
            switch (type) {
                case TYPE_1:
                    view = LayoutInflater.from(context).inflate(R.layout.shangtoutiao_item_huigu, null);
                    viewHoudle1 = new ViewHoudle1();
                    viewHoudle1.topicname1 = (TextView) view.findViewById(R.id.topicname1);
                    viewHoudle1.time1 = (TextView) view.findViewById(R.id.time1);
                    viewHoudle1.topicDesc1 = (TextView) view.findViewById(R.id.topicDesc1);
                    view.setTag(viewHoudle1);
                    break;
                case TYPE_2:
                    view = LayoutInflater.from(context).inflate(R.layout.shangtoutiao_item_huigu2, null);
                    viewHoudle2 = new ViewHoudle2();
                    viewHoudle2.topicname2 = (TextView) view.findViewById(R.id.topicname2);
                    viewHoudle2.time2 = (TextView) view.findViewById(R.id.time2);
                    viewHoudle2.topicDesc2 = (TextView) view.findViewById(R.id.topicDesc2);
                    view.setTag(viewHoudle2);
                    break;
            }
        }else {
            //有convertView，按样式，取得不用的布局
            switch (type) {
                case TYPE_1:
                    viewHoudle1 = (ViewHoudle1) view.getTag();
                    break;
                case TYPE_2:
                    viewHoudle2 = (ViewHoudle2) view.getTag();
            }
        }

        //设置资源
        switch (type) {
            case TYPE_1:
                viewHoudle1.topicname1.setText("# "+list.get(i).topicname);
                viewHoudle1.time1.setText(list.get(i).ctime);
                viewHoudle1.topicDesc1.setText(list.get(i).topicDesc);
                break;
            case TYPE_2:
                viewHoudle2.topicname2.setText("# " + list.get(i).topicname);
                viewHoudle2.time2.setText(list.get(i).ctime);
                viewHoudle2.topicDesc2.setText(list.get(i).topicDesc);
                break;
        }
        return view;
    }

    class ViewHoudle1 {
        TextView topicname1, time1, topicDesc1;
    }

    class ViewHoudle2 {
        TextView topicname2, time2, topicDesc2;
    }
}
