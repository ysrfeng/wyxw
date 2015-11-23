package com.xiangmu.wyxw.CostomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.xiangmu.wyxw.Bean.Collection;
import com.xiangmu.wyxw.R;
import java.util.List;

/**
 * Created by Administrator on 2015/11/12.
 */
public class CollectionNews_adapter extends BaseAdapter {
    private Context context;
    private List<Collection> myDataList;

    public CollectionNews_adapter(Context context, List<Collection> myDataList) {
        this.context = context;
        this.myDataList = myDataList;
    }

    @Override
    public int getCount() {
        return myDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return myDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
ViewHolder viewHolder;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.hgz_collection_news_adapter_item,null);
            viewHolder=new ViewHolder();
            viewHolder.name= (TextView) convertView.findViewById(R.id.name);
            viewHolder.number= (TextView) convertView.findViewById(R.id.number);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(myDataList.get(position).getName());
        viewHolder.number.setText(myDataList.get(position).getNumber());
        return convertView;
    }
    class ViewHolder{
        TextView name;
        TextView number;
    }
}
