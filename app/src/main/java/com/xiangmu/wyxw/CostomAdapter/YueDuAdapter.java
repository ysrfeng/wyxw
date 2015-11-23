package com.xiangmu.wyxw.CostomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.xiangmu.wyxw.Bean.YueDuBean;
import com.xiangmu.wyxw.R;

import java.util.List;


/**
 * Created by Administrator on 2015/11/9.
 */
public class YueDuAdapter extends BaseAdapter {
    private List<YueDuBean.推荐Entity> list;
    private Context context;
    final int VIEW_TYPE =5;
    final int TYPE_1 = 1;
    final int TYPE_2 = 2;
    final int TYPE_3 = 3;
    final int TYPE_4 = 4;

    public List<YueDuBean.推荐Entity> getList() {
        return list;
    }

    public void setList(List<YueDuBean.推荐Entity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public YueDuAdapter(Context context, List<YueDuBean.推荐Entity> list) {
        this.context = context;
        this.list = list;
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
            String template = list.get(i).template;
            if (template.equals("pic1")){
                return TYPE_2;
            }else if (template.equals("pic31")){
                return TYPE_3;
            }else if (template.equals("pic32")){
                return TYPE_4;
            }else if (template.equals("normal")){
                return TYPE_1;
            }
        return TYPE_1;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHoudle_normal viewHoudle_normal = null;
            ViewHoudle_pic1 viewHoudle_pic1= null;
            ViewHoudle_pic31 viewHoudle_pic31 = null;
            ViewHoudle_pic32 viewHoudle_pic32 = null;
            int type = getItemViewType(i);
            //无convertView，需要new出各个控件
            if (view == null) {
                //按当前所需的样式，确定new的布局
                switch (type) {
                    case TYPE_1:
                        viewHoudle_normal = new ViewHoudle_normal();
                        view = LayoutInflater.from(context).inflate(R.layout.yuedu_item_normal, null);
                        viewHoudle_normal.normal_iv = (ImageView) view.findViewById(R.id.normal_iv);
                        viewHoudle_normal.normal_title = (TextView) view.findViewById(R.id.normal_title);
                        viewHoudle_normal.normal_source = (TextView) view.findViewById(R.id.normal_source);
                        view.setTag(viewHoudle_normal);
                        break;
                    case TYPE_2:
                        viewHoudle_pic1 = new ViewHoudle_pic1();
                        view = LayoutInflater.from(context).inflate(R.layout.yuedu_item_pic1, null);
                        viewHoudle_pic1.pic1_iv = (ImageView) view.findViewById(R.id.pic1_iv);
                        viewHoudle_pic1.pic1_tv = (TextView) view.findViewById(R.id.pic1_tv);
                        viewHoudle_pic1.pic1_source = (TextView) view.findViewById(R.id.pic1_source);
                        view.setTag(viewHoudle_pic1);
                        break;
                    case TYPE_3:
                        viewHoudle_pic31 = new ViewHoudle_pic31();
                        view = LayoutInflater.from(context).inflate(R.layout.yuedu_item_pic31, null);
                        viewHoudle_pic31.pic31_iv1 = (ImageView) view.findViewById(R.id.pic31_iv1);
                        viewHoudle_pic31.pic31_iv2 = (ImageView) view.findViewById(R.id.pic31_iv2);
                        viewHoudle_pic31.pic31_iv3 = (ImageView) view.findViewById(R.id.pic31_iv3);
                        viewHoudle_pic31.pic31_tv = (TextView) view.findViewById(R.id.pic31_tv);
                        viewHoudle_pic31.pic31_source = (TextView) view.findViewById(R.id.pic31_source);
                        view.setTag(viewHoudle_pic31);
                        break;
                    case TYPE_4:
                        viewHoudle_pic32 = new ViewHoudle_pic32();
                        view = LayoutInflater.from(context).inflate(R.layout.yuedu_item_pic32, null);
                        viewHoudle_pic32.pic32_iv1 = (ImageView) view.findViewById(R.id.pic32_iv1);
                        viewHoudle_pic32.pic32_iv2 = (ImageView) view.findViewById(R.id.pic32_iv2);
                        viewHoudle_pic32.pic32_iv3 = (ImageView) view.findViewById(R.id.pic32_iv3);
                        viewHoudle_pic32.pic32_tv = (TextView) view.findViewById(R.id.pic32_tv);
                        viewHoudle_pic32.pic32_source = (TextView) view.findViewById(R.id.pic32_source);
                        view.setTag(viewHoudle_pic32);
                        break;
                }
            } else {
                //有convertView，按样式，取得不用的布局
                switch (type) {
                    case TYPE_1:
                        viewHoudle_normal = (ViewHoudle_normal) view.getTag();
                        break;
                    case TYPE_2:
                        viewHoudle_pic1 = (ViewHoudle_pic1) view.getTag();
                        break;
                    case TYPE_3:
                        viewHoudle_pic31 = (ViewHoudle_pic31) view.getTag();
                        break;
                    case TYPE_4:
                        viewHoudle_pic32 = (ViewHoudle_pic32) view.getTag();
                        break;
                }
            }
            YueDuBean.推荐Entity entity = list.get(i);
            BitmapUtils bitmapUtils = new BitmapUtils(context);
            //设置资源
            switch (type) {
                case TYPE_1:
                    if ("".equals(entity.imgsrc)){
                        viewHoudle_normal.normal_iv.setVisibility(View.GONE);
                    }else {
                        bitmapUtils.display(viewHoudle_normal.normal_iv, entity.imgsrc);
                    }
                    viewHoudle_normal.normal_title.setText(entity.title);
                    viewHoudle_normal.normal_source.setText(entity.source);
                    break;
                case TYPE_2:
                    bitmapUtils.display(viewHoudle_pic1.pic1_iv, entity.imgsrc);
                    viewHoudle_pic1.pic1_tv.setText(entity.title);
                    viewHoudle_pic1.pic1_source.setText(entity.source);
                    break;
                case TYPE_3:
                    bitmapUtils.display(viewHoudle_pic31.pic31_iv1, entity.imgsrc);
                    bitmapUtils.display(viewHoudle_pic31.pic31_iv2, entity.imgnewextra.get(0).imgsrc);
                    bitmapUtils.display(viewHoudle_pic31.pic31_iv3, entity.imgnewextra.get(0).imgsrc);
                    viewHoudle_pic31.pic31_tv.setText(entity.title);
                    viewHoudle_pic31.pic31_source.setText(entity.source);
                    break;
                case TYPE_4:
                    bitmapUtils.display(viewHoudle_pic32.pic32_iv1, entity.imgsrc);
                    bitmapUtils.display(viewHoudle_pic32.pic32_iv2, entity.imgnewextra.get(0).imgsrc);
                    bitmapUtils.display(viewHoudle_pic32.pic32_iv3, entity.imgnewextra.get(0).imgsrc);
                    viewHoudle_pic32.pic32_tv.setText(entity.title);
                    viewHoudle_pic32.pic32_source.setText(entity.source);
                    break;
            }
        return view;
    }

    //item样式(四种)
    class ViewHoudle_normal {
        ImageView normal_iv;
        TextView normal_title, normal_source;
    }

    class ViewHoudle_pic1 {
        ImageView pic1_iv;
        TextView pic1_tv, pic1_source;
    }

    class ViewHoudle_pic31 {
        ImageView pic31_iv1, pic31_iv2, pic31_iv3;
        TextView pic31_tv, pic31_source;
    }

    class ViewHoudle_pic32 {
        ImageView pic32_iv1, pic32_iv2, pic32_iv3;
        TextView pic32_tv, pic32_source;
    }
}
