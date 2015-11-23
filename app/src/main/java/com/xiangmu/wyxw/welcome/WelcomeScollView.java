package com.xiangmu.wyxw.welcome;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2015/11/18.
 */
public class WelcomeScollView extends ScrollView {
    ScrollViewListener scrollViewListener;
    public WelcomeScollView(Context context) {
        super(context);
    }
    public WelcomeScollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener!=null) {
            scrollViewListener.scrollViewlistener( t,  oldt);
        }
    }
    public void setScrollListener(ScrollViewListener scrollViewListener){
        this.scrollViewListener=scrollViewListener;
    }
}
