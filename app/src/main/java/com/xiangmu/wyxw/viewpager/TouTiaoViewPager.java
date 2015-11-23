package com.xiangmu.wyxw.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2015/11/14.
 */
public class TouTiaoViewPager extends ViewPager {

    public TouTiaoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public static boolean GO_TOUTH_CHILD=true;

    //当我执行我的触摸事件时父控件不能对我进行干扰
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (GO_TOUTH_CHILD){
            //此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
            getParent().requestDisallowInterceptTouchEvent(true);
            super.onTouchEvent(ev); //注意这句不能 return super.onTouchEvent(arg0); 否则触发parent滑动
            return true;
        }
        return super.onTouchEvent(ev);
    }
}
