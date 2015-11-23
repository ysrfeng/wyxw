package com.xiangmu.wyxw.Setting_Utils;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

/**
 * Created by Administrator on 2015/11/18.
 */
public class ShareUtils {
    private static UMSocialService umSocialService=UMServiceFactory.getUMSocialService("com.xiangmu.wyxw.conent_frament");;
//    UMSocialService umSocialService;
    public static void shareContent(Context context, String title, String url) {
//        umSocialService = UMServiceFactory.getUMSocialService("com.xiangmu.wyxw.conent_frament");
        umSocialService.setShareContent(title);
        umSocialService.setShareImage(new UMImage(context, url));
        //分享到qq空间
        QZoneSsoHandler handler = new QZoneSsoHandler((Activity) context, "1104966900", "voYmRxQSvtpCnGUE");
        handler.setShareAfterAuthorize(false);
        handler.setTargetUrl("http://f.hiphotos.baidu.com/image/pic/item/21a4462309f79052adae76d108f3d7ca7acbd5af.jpg");
        //添加到分享
        handler.addToSocialSDK();
        //分享到腾讯微博
        umSocialService.getConfig().setSsoHandler(new TencentWBSsoHandler());
        //添加微信平台
        UMWXHandler handler1 = new UMWXHandler(context, "wxf20fc8795a65183d", "d4624c36b6795d1d99dcf0547af5443d");
        handler1.addToSocialSDK();
        //添加微信朋友圈
        UMWXHandler handler2 = new UMWXHandler(context, "wxf20fc8795a65183d", "d4624c36b6795d1d99dcf0547af5443d");
        handler2.setToCircle(true);
        handler2.addToSocialSDK();
        //分享给QQ好友
        UMQQSsoHandler handler3 = new UMQQSsoHandler((Activity) context, "1104966900", "voYmRxQSvtpCnGUE");
        handler3.addToSocialSDK();
        //分享到人人网
        RenrenSsoHandler handler4 = new RenrenSsoHandler(context, "481831", "038236ab416044b4881788902d956f25", "7acd8b4a55af4521a630753310ce50e5");
        umSocialService.getConfig().setSsoHandler(handler4);
        umSocialService.setAppWebSite(SHARE_MEDIA.RENREN, "http://www.baidu.com");
        //是否只有已经登录的用户才能打开分享选择页
        umSocialService.openShare((Activity) context, false);

    }

    public static void shareQQFriend(Context context, String title, String url) {
        umSocialService.setShareContent(title);
        umSocialService.setShareImage(new UMImage(context, url));
        //分享给QQ好友
        UMQQSsoHandler handler3 = new UMQQSsoHandler((Activity) context, "1104966900", "voYmRxQSvtpCnGUE");
        handler3.addToSocialSDK();
        umSocialService.openShare((Activity) context, false);
    }

    public static void shareQQZore(Context context, String title, String url) {
        umSocialService.setShareContent(title);
        umSocialService.setShareImage(new UMImage(context, url));
        //分享到qq空间
        QZoneSsoHandler handler = new QZoneSsoHandler((Activity) context, "1104966900", "voYmRxQSvtpCnGUE");
        handler.setShareAfterAuthorize(false);
        handler.setTargetUrl("http://f.hiphotos.baidu.com/image/pic/item/21a4462309f79052adae76d108f3d7ca7acbd5af.jpg");
        umSocialService.openShare((Activity) context, false);
    }
    public static void shareTengXun(Context context,String title,String url){
        umSocialService.setShareContent(title);
        umSocialService.setShareImage(new UMImage(context, url));
        //分享到腾讯微博
        umSocialService.getConfig().setSsoHandler(new TencentWBSsoHandler());
        umSocialService.openShare((Activity) context, false);
    }
    public static void shareWeiXin(Context context,String title,String url){
        umSocialService.setShareContent(title);
        umSocialService.setShareImage(new UMImage(context, url));
        //添加微信平台
        UMWXHandler handler1 = new UMWXHandler(context, "wxf20fc8795a65183d", "d4624c36b6795d1d99dcf0547af5443d");
        handler1.addToSocialSDK();
        //添加微信朋友圈
        UMWXHandler handler2 = new UMWXHandler(context, "wxf20fc8795a65183d", "d4624c36b6795d1d99dcf0547af5443d");
        handler2.setToCircle(true);
        handler2.addToSocialSDK();
        umSocialService.openShare((Activity) context, false);
    }
}
