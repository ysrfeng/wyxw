package com.xiangmu.wyxw.Bean;

/**
 * Created by Administrator on 2015/11/18.
 */
public class NewsBean {
    String date;
    String num;
    String title;
    String url;
    String replaycount;
    String lanMuType;

    public String getDate() {
        return date;
    }

    public String getNum() {
        return num;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getReplaycount() {
        return replaycount;
    }

    public String getLanMuType() {
        return lanMuType;
    }




    public NewsBean(String date, String num, String title, String url, String replaycount, String lanMuType) {
        this.date = date;
        this.num = num;
        this.title = title;
        this.url = url;
        this.replaycount = replaycount;
        this.lanMuType = lanMuType;
    }
}
