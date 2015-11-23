package com.xiangmu.wyxw.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2015/11/18.
 */
public class HttpPostThread implements Runnable {
    private String name;
    private String password;
    private Handler hand;
    private String img = "";
    private Context context;
    private MyPost myPost;
    public HttpPostThread( Context context,String name, String password, Handler hand) {
        this.context=context;
        this.name = name;
        this.password = password;
        this.hand = hand;
        myPost= new MyPost(context);
    }


//    public HttpPostThread(String url, String value, String img,Handler hand) {
//        this.url = url;
//        this.value = value;
//        this.img = img;
//        this.hand = hand;
//    }

    @Override
    public void run() {
        Message msg = hand.obtainMessage();
        boolean isnull = myPost.doPost(name, password);
//        String result = null;
//        if ("".equalsIgnoreCase(img)) {
//            isnull = myGet.doPost(name, password);
//        } else {
////            result = myGet.doPost(url, img, value);
//        }
        msg.what = 200;
        msg.obj = isnull;
        // 给主ui发送消息传递数据
        hand.sendMessage(msg);
    }
}
