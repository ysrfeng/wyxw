package com.xiangmu.wyxw.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/14.
 */
public class XinWenJson {
    public static XinWen_toutiao getdata(String data,int type){
        try {
            XinWen_toutiao toutiao=new XinWen_toutiao();
            JSONObject object=new JSONObject(data);
            LogUtils.e("xinwenjsonobject", object + "");
            JSONArray array = null;
            switch (type){
                case XinWen_adapter.rendian:
                    array=object.getJSONArray("T1429173762551");//热点
                    break;
                case XinWen_adapter.toutiao:
                    array=object.getJSONArray("T1348647853363");//头条
                    break;
                case XinWen_adapter.yule:
                    array=object.getJSONArray("T1348648517839");//娱乐T1348648517839
                    break;
                case XinWen_adapter.tiyu:
                    array=object.getJSONArray("T1348649079062");//体育
                    break;
                case XinWen_adapter.caijing:
                    array=object.getJSONArray("T1348648756099");//财经
                    break;
                case XinWen_adapter.keji:
                    array=object.getJSONArray("T1348649580692");//科技
                    break;
                case XinWen_adapter.shishang:
                    array=object.getJSONArray("T1348650593803");//时尚
                    break;
                case XinWen_adapter.lishi:
                    array=object.getJSONArray("T1368497029546");//历史
                    break;
                case XinWen_adapter.caipiao:
                    array=object.getJSONArray("T1356600029035");//彩票
                    break;
                case XinWen_adapter.junshi:
                    array=object.getJSONArray("T1348648141035");//军事
                    break;
                case XinWen_adapter.youxi:
                    array=object.getJSONArray("T1348654151579");//游戏
                    break;
            }
            LogUtils.e("xinwenjsonarray----", "" + array);

            List<XinWen_toutiao.T1348647853363Entity> list=new ArrayList<>();
            for (int i=0;i<array.length();i++){

//                LogUtils.e("xinwenjsonarray",array+"");

                XinWen_toutiao.T1348647853363Entity t1348647853363Entity=new XinWen_toutiao.T1348647853363Entity();
                JSONObject arrayobj=array.getJSONObject(i);
                if (!arrayobj.isNull("skipID")){
                    String skipId=arrayobj.getString("skipID");
                    t1348647853363Entity.setSkipID(skipId);
                };
                if (!arrayobj.isNull("replyCount")){
                    int replyCount=arrayobj.getInt("replyCount");
                    t1348647853363Entity.setReplyCount(replyCount);
                };
                if (!arrayobj.isNull("skipType")){
                    String skiptype=arrayobj.getString("skipType");
                    t1348647853363Entity.setSkipType(skiptype);
                };
                if (!arrayobj.isNull("title")){
                    String title=arrayobj.getString("title");
                    t1348647853363Entity.setTitle(title);
                };
                if (!arrayobj.isNull("digest")){
                    String digest=arrayobj.getString("digest");
                    t1348647853363Entity.setDigest(digest);
                }
                if (!arrayobj.isNull("priority")){
                    int  priority=arrayobj.getInt("priority");
                    t1348647853363Entity.setPriority(priority);
                }
                if (!arrayobj.isNull("imgsrc")){
                    String  imgsrc=arrayobj.getString("imgsrc");
                    t1348647853363Entity.setImgsrc(imgsrc);
                }
                if (!arrayobj.isNull("url")){
                    String  url=arrayobj.getString("url");
                    t1348647853363Entity.setUrl(url);
                }

//                LogUtils.e("xinwenjsont1348647853363Entity", i + "======" + t1348647853363Entity + "");
                if (!arrayobj.isNull("imgextra")){
                    JSONArray imagetraArray=arrayobj.getJSONArray("imgextra");
                    LogUtils.e("xinwenjsonimagetraArray", imagetraArray + "");
                    List<XinWen_toutiao.T1348647853363Entity.ImgextraEntity> listimagestra=new ArrayList<>();
                    for (int j=0;j<imagetraArray.length();j++){
                        XinWen_toutiao.T1348647853363Entity.ImgextraEntity imgextraEntity=new XinWen_toutiao.T1348647853363Entity.ImgextraEntity();

                        JSONObject imagestra=imagetraArray.getJSONObject(j);
                        String imagesra=imagestra.getString("imgsrc");
                        imgextraEntity.setImgsrc(imagesra);
                        listimagestra.add(imgextraEntity);
                    }
                    t1348647853363Entity.setImgextra(listimagestra);
                }


                if (!arrayobj.isNull("ads")){
                    JSONArray adsArray=arrayobj.getJSONArray("ads");
                    List<XinWen_toutiao.T1348647853363Entity.AdsEntity> adsEntities=new ArrayList<>();
                    for (int k=0;k<adsArray.length();k++){
                        LogUtils.e("xinwenjsonads", "xinwenadsentity");
                        XinWen_toutiao.T1348647853363Entity.AdsEntity ads=new XinWen_toutiao.T1348647853363Entity.AdsEntity();
                        JSONObject adsobj=adsArray.getJSONObject(k);
                        String adstitle=adsobj.getString("title");
                        String adsurl=adsobj.getString("url");
                        LogUtils.e("xinwenjsonadsurl", "" + adsurl);
                        String adstag=adsobj.getString("tag");
                        String adsimgsrc=adsobj.getString("imgsrc");

                        ads.setUrl(adsurl);
                        ads.setImgsrc(adsimgsrc);
                        ads.setTitle(adstitle);
                        ads.setTag(adstag);
                        adsEntities.add(ads);
                    }
                    t1348647853363Entity.setAds(adsEntities);
                }

                list.add(t1348647853363Entity);
            }
            toutiao.setT1348647853363(list);
            LogUtils.e("xinwenjson", "========" + list.get(0).getTitle());
            for (int i=0;i<toutiao.getT1348647853363().size();i++){
                LogUtils.e("xinwenjson", i + "========" + toutiao.getT1348647853363().get(i).getTitle());
            }
            LogUtils.e("xinwenjson", "========" + toutiao.getT1348647853363());
            return toutiao;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogUtils.e("xinwenjson", "=======================================================");
        return null;
    }
}
