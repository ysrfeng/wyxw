package com.xiangmu.wyxw.utils;

import android.content.Context;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public class XinWenXi {
    public static List<PhotosObj> getdata(String data,Context context,int lanmuType){
        XutilsGetData xutilsGetData=new XutilsGetData();
        LogUtils.e("xinwenactivitydata", data);
        List<PhotosObj> list=new ArrayList<>();
        try {
            JSONObject object=new JSONObject(data);
            JSONArray photos=object.getJSONArray("photos");
            String gentieUrl=object.getString("commenturl");
            PhotosObj photosObj=new PhotosObj();
            photosObj.setGentieUrl(gentieUrl);
            list.add(photosObj);

            List<PhotosObj.Photos> photosList=new ArrayList<>();
            for (int i=0;i<photos.length();i++){
                PhotosObj.Photos ps= new PhotosObj.Photos();

                ImageView imageView=new ImageView(context);
                JSONObject photo= (JSONObject) photos.get(i);
                String imgurl=photo.getString("imgurl");
                xutilsGetData.xUtilsImageiv(imageView, imgurl, context,true);
                String text=null;
                switch (lanmuType){
                    case XinWen_adapter.yule:
                    case XinWen_adapter.lishi:
                        text=photo.getString("imgtitle");
                        break;
                    default:
                        text=photo.getString("note");
                        break;
                }

                ps.setImg(imageView);
                ps.setText(text);

                photosList.add(ps);
            }
            list.set(0,photosObj).setPhotosList(photosList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        xutilsGetData.XutilsClose();
        return list;
    }
    public static class PhotosObj{
        List<Photos> photosList;
        String gentieUrl;

        public List<Photos> getPhotosList() {
            return photosList;
        }

        public void setPhotosList(List<Photos> photosList) {
            this.photosList = photosList;
        }

        public static class Photos{
            ImageView image;
            String text;
            public ImageView getImg() {
                return image;
            }

            public void setImg(ImageView img) {
                this.image = img;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getText() {
                return text;
            }
        }

        public String getGentieUrl() {
            return gentieUrl;
        }

        public void setGentieUrl(String gentieUrl) {
            this.gentieUrl = gentieUrl;
        }
    }
}
