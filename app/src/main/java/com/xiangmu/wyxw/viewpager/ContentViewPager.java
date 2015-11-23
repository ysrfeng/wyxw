package com.xiangmu.wyxw.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2015/11/9.
 */
public class ContentViewPager extends ViewPager {

    public ContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //去子控件,如果没有子控件则不触发 触摸事件
    public static boolean GO_TOUTH_CHILD=true;
    //拦截方法
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (GO_TOUTH_CHILD){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    //触摸方法 传给父控件
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (GO_TOUTH_CHILD){
            return false;
        }
        return super.onTouchEvent(ev);
    }
}
