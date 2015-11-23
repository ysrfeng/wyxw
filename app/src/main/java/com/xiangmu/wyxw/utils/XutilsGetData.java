package com.xiangmu.wyxw.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xiangmu.wyxw.CostomProgressDialog.CustomProgressDialog;
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.activitys.YueDuDetialActivity;

/**
 * Created by Administrator on 2015/11/10.
 */
public class XutilsGetData {
    //数据存储的名字
    private  String CONFIG = "config";

    private HttpHandler<String> hand;
    private HttpUtils http;
    private  String data = null;
    private  CallBackHttp callbackhttp;
//    static CustomProgressDialog dialog = null;
    //网络请求string数据
    public  void xUtilsHttp(final Context context, final String url, CallBackHttp callback, final boolean isprogressdialog) {
        //设置精度条
//        final ProgressBar progressBar= (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.setMax(100);
        http = new HttpUtils();
        callbackhttp = callback;
        if (isprogressdialog){

            if (dialog==null){
                dialog=new CustomProgressDialog(context,"正在加载中.......", R.anim.donghua_frame);
            }
            dialog.show();
        }
        //打开子线程请求网络
        final CustomProgressDialog finalDialog = dialog;
        hand = http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            //开始请求调用的方法
            public void onStart() {
                super.onStart();
//                progressBar.setVisibility(View.VISIBLE);

            }

            //正在请求调用的方法
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
//                progressBar.setProgress((int)(((float)current/total)*100));
            }

            //请求成功回调的方法
            public void onSuccess(ResponseInfo<String> responseInfo) {
//                progressBar.setVisibility(View.GONE);
                LogUtils.e("onSuccess", responseInfo + "");
                LogUtils.e("onSuccessurl", url + "");
                data = responseInfo.result;//获得网络请求的字符串
                LogUtils.e("onSuccess", data + "");
                callbackhttp.handleData(data);//接口回调的方法
                saveData(context, url, data);//保存数据
                if (finalDialog !=null){
                    finalDialog.dismiss();

                }
            }

            //请求失败回调的方法
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private  SharedPreferences sp;

    //保存数据
    public  void saveData(Context context, String key, String data) {
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, data).commit();
    }

    //读取本地缓存数据
    public  String getData(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    private static BitmapUtils utils;
    private static CallBackImage callbackimage;
    //网络请求图片
    static CustomProgressDialog dialog = null;
    public static void xUtilsImageiv(ImageView iv, String imageurl, Context context,boolean isprogressdialog) {
//        BitmapDisplayConfig config=new BitmapDisplayConfig();
//        final Animation alpha= AnimationUtils.loadAnimation(MainActivity.this,R.anim.alpha);
//        config.setAnimation(alpha);
        //第一个参数为上下文 第二个参数为缓冲路径(如果不写也会缓存到默认路径)
//        callbackimage=callback;

        utils = new BitmapUtils(context, context.getFilesDir().getPath());//保存图片路径

        if (isprogressdialog){
            if (dialog==null){
                dialog=new CustomProgressDialog(context,"正在加载中.......", R.anim.donghua_frame);
            }
            dialog.show();
        }
        //第一个为放入图片的控件  第二个为图片地址  config为显示方式   CallBack为回调的方法可以自定义显示
//        utils.display(iv, url, config);
        utils.display(iv, imageurl, new BitmapLoadCallBack<ImageView>() {
            //请求成功调用的方法
            public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
//                imageView.setAnimation(alpha);
                imageView.setImageBitmap(bitmap);
//                callbackimage.handleData(bitmap);
                if (dialog !=null){
                    dialog.dismiss();
                }
            }

            //请求失败调用的方法
            public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {

            }
        });
    }

    //关闭Xutils流
    public  void XutilsClose() {
        if (hand != null) {
            hand.cancel();
        }
        if (utils != null) {
            utils.cancel();
        }
    }

    //数据接口回调
    public interface CallBackHttp {
        void handleData(String data);
    }

    //图片的接口回调
    public interface CallBackImage {
        void handleData(Bitmap bitmap);
    }
}
