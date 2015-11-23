package com.xiangmu.wyxw.CostomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.xiangmu.wyxw.Bean.Top;
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.Setting_Utils.ShareUtils;

import java.util.List;


/**
 * Created by Administrator on 2015/11/13.
 */
public class TopAdapter extends BaseAdapter{
    private List<Top.ArticlesEntity> list;
    private Context context;

    public TopAdapter(List<Top.ArticlesEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public List<Top.ArticlesEntity> getList() {
        return list;
    }
    public void setList(List<Top.ArticlesEntity> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoudle viewHoudle = null;
        if (view == null){
            viewHoudle = new ViewHoudle();
            view = LayoutInflater.from(context).inflate(R.layout.shangtoutiao_item_topic,null);
            viewHoudle.nickname = (TextView) view.findViewById(R.id.nickname);
            viewHoudle.city_ctime = (TextView) view.findViewById(R.id.city_ctime);
            viewHoudle.body = (TextView) view.findViewById(R.id.body);
            viewHoudle.supportval = (TextView) view.findViewById(R.id.supportval);
            viewHoudle.oppositeval = (TextView) view.findViewById(R.id.oppositeval);
            viewHoudle.person_icon = (ImageView) view.findViewById(R.id.person_icon);
            viewHoudle.imgsrc = (ImageView) view.findViewById(R.id.imgsrc);
            view.setTag(viewHoudle);
        }else {
            viewHoudle = (ViewHoudle) view.getTag();
        }
        final Top.ArticlesEntity entity = list.get(i);
        BitmapUtils bitmapUtils = new BitmapUtils(context);

        if (entity.nickname==null){
            viewHoudle.nickname.setText("暂无昵称");
        }else {
            viewHoudle.nickname.setText(entity.nickname);
        }
        viewHoudle.city_ctime.setText(entity.city+"   "+entity.ctime);
        viewHoudle.body.setText(entity.body);
        viewHoudle.supportval.setText(entity.supportval);
        viewHoudle.oppositeval.setText(entity.oppositeval);
        viewHoudle.person_icon.setImageResource(R.mipmap.shangtoutiao_person);
        bitmapUtils.display(viewHoudle.imgsrc, entity.imgsrc);

        viewHoudle.shangtoutiao_share = (LinearLayout) view.findViewById(R.id.shangtoutiao_share);
        viewHoudle.shangtoutiao_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// TODO: 2015/11/13 加分享
//                Toast.makeText(context, "----" + i, Toast.LENGTH_SHORT).show();
                ShareUtils.shareContent(context,"#"+list.get(i).topicname+"#"+"\n"+entity.city+"\n"+entity.body,entity.imgsrc);
            }
        });
        return view;
    }
    class ViewHoudle{
        ImageView person_icon,imgsrc;
        TextView nickname,city_ctime,body,supportval,oppositeval;
        LinearLayout shangtoutiao_share;
    }
}
