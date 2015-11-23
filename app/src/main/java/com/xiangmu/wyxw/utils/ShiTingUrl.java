package com.xiangmu.wyxw.utils;

/**
 * Created by Administrator on 2015/11/18.
 */
public class ShiTingUrl {
    public int stratPage = 0;
    public int getStratPage() {
        return stratPage;
    }
    public void setStratPage(int stratPage) {
        this.stratPage = stratPage;
    }

    public String getShiTingUrl() {
        int page = getStratPage();
        String JzStUrl = "http://c.m.163.com/nc/video/list/V9LG4B3A0/y/" + stratPage + "-" + (stratPage + 20) + ".html";
        return JzStUrl;
    }
}
