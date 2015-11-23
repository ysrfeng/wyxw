package com.xiangmu.wyxw.Setting_Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * Created by Administrator on 2015/11/11.
 */
public class ZDY_TouXiangName_EditText extends EditText {
    //这是EditText右边图片
    private Drawable rigthImage;
    public ZDY_TouXiangName_EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    private void initView() {
        initData();
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initData();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private void initData() {
        if (this.getText().toString().length() == 0) {
            setCompoundDrawables(null, null, null, null);
        } else {
            setCompoundDrawables(null, null, rigthImage, null);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //获得点击的位置
            float x = event.getX();
            //获得图片的宽度
            int width = rigthImage.getBounds().width();
            Log.e("aaa", "==width==" + width + "==getRigth==" + getRight() + "==event.getX()==" + event.getX());
            if (rigthImage != null && x > (getRight() - width)) {
                this.setText("");
            }
        }
        return true;
    }
    //自定义EditText,在其位置添加图片必有的方法setCompoundDrawables();其中参数是指自定义控件四个方位的图片
    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        //图片添加在右边,把rigth赋给全局变量rigthImage
        if (right != null) {
            this.rigthImage = right;
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }
}
