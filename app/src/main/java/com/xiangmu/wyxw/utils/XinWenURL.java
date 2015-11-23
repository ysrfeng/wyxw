package com.xiangmu.wyxw.utils;

/**
 * Created by Administrator on 2015/11/13.
 */
public class XinWenURL {
    public int stratPage = 0;
    //热点
    String redian="http://c.3g.163.com/nc/article/list/T1429173762551/0-20.html";

    String toutiao = "http://c.m.163.com/nc/article/headline/T1348647853363/" + stratPage + "-" + (stratPage + 20) + ".html";//头条
    String yule = "http://c.m.163.com/nc/article/list/T1348648517839/" + stratPage + "-" + (stratPage + 20) + ".html";//娱乐
    String tiyu = "http://c.m.163.com/nc/article/list/T1348649079062/" + stratPage + "-" + (stratPage + 20) + ".html";//体育
    String chaijing = "http://c.m.163.com/nc/article/list/T1348648756099/" + stratPage + "-" + (stratPage + 20) + ".html";//财经
    String keji = "http://c.m.163.com/nc/article/list/T1348649580692/" + stratPage + "-" + (stratPage + 20) + ".html";//科技
    String qiche = "http://c.m.163.com/nc/article/list/T1348654060988/" + stratPage + "-" + (stratPage + 20) + ".html";//汽车
    String shishang = "http://c.m.163.com/nc/article/list/T1348650593803/" + stratPage + "-" + (stratPage + 20) + ".html";//时尚
    String junshi = "http://c.m.163.com/nc/article/list/T1348648141035/" + stratPage + "-" + (stratPage + 20) + ".html";//军事
    String lishi = "http://c.m.163.com/nc/article/list/T1368497029546/" + stratPage + "-" + (stratPage + 20) + ".html";//历史
    String caipiao = "http://c.m.163.com/nc/article/list/T1356600029035/" + stratPage + "-" + (stratPage + 20) + ".html";//彩票
    String youxi = "http://c.m.163.com/nc/article/list/T1348654151579/" + stratPage + "-" + (stratPage + 20) + ".html";//游戏
    String yingshi = "http://c.m.163.com/nc/article/list/T1348648650048/" + stratPage + "-" + (stratPage + 20) + ".html";//影视
    String luntan = "http://c.m.163.com/nc/article/list/T1419386592923/" + stratPage + "-" + (stratPage + 20) + ".html";//论坛
    public int getStratPage() {
        return stratPage;
    }
    public void setStratPage(int stratPage) {
        this.stratPage = stratPage;
    }

    public String getRedian() {
        return redian;
    }

    public String getToutiao() {
        int page = getStratPage();
        String toutiao = "http://c.m.163.com/nc/article/headline/T1348647853363/" + page + "-" + (page + 20) + ".html";//头条
        return toutiao;
    }

    public String getYule() {
        int page = getStratPage();
        String yule = "http://c.m.163.com/nc/article/list/T1348648517839/" + stratPage + "-" + (stratPage + 20) + ".html";//娱乐
        return yule;
    }

    public String getTiyu() {
        int page = getStratPage();
        String tiyu = "http://c.m.163.com/nc/article/list/T1348649079062/" + stratPage + "-" + (stratPage + 20) + ".html";//体育
        return tiyu;
    }

    public String getChaijing() {
        int page = getStratPage();
        String chaijing = "http://c.m.163.com/nc/article/list/T1348648756099/" + stratPage + "-" + (stratPage + 20) + ".html";//财经
        return chaijing;
    }

    public String getKeji() {
        int page = getStratPage();
        String keji = "http://c.m.163.com/nc/article/list/T1348649580692/" + stratPage + "-" + (stratPage + 20) + ".html";//科技
        return keji;
    }

    public String getQiche() {
        String qiche = "http://c.m.163.com/nc/article/list/T1348654060988/" + stratPage + "-" + (stratPage + 20) + ".html";//汽车
        return qiche;
    }

    public String getShishang() {
        String shishang = "http://c.m.163.com/nc/article/list/T1348650593803/" + stratPage + "-" + (stratPage + 20) + ".html";//时尚
        return shishang;
    }

    public String getJunshi() {
        String junshi = "http://c.m.163.com/nc/article/list/T1348648141035/" + stratPage + "-" + (stratPage + 20) + ".html";//军事
        return junshi;
    }

    public String getLishi() {
        String lishi = "http://c.m.163.com/nc/article/list/T1368497029546/" + stratPage + "-" + (stratPage + 20) + ".html";//历史
        return lishi;
    }

    public String getCaipiao() {
        String caipiao = "http://c.m.163.com/nc/article/list/T1356600029035/" + stratPage + "-" + (stratPage + 20) + ".html";//彩票
        return caipiao;
    }

    public String getYouxi() {
        String youxi = "http://c.m.163.com/nc/article/list/T1348654151579/" + stratPage + "-" + (stratPage + 20) + ".html";//游戏
        return youxi;
    }

    public String getYingshi() {
        String yingshi = "http://c.m.163.com/nc/article/list/T1348648650048/" + stratPage + "-" + (stratPage + 20) + ".html";//影视
        return yingshi;
    }

    public String getLuntan() {
        String luntan = "http://c.m.163.com/nc/article/list/T1419386592923/" + stratPage + "-" + (stratPage + 20) + ".html";//论坛
        return luntan;
    }
}
