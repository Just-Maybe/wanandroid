package com.example.wanandroid.bean;

import java.util.List;

/**
 * Created by Miracle on 2020/8/5
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class CoinListBean {

    /**
     * over : false
     * pageCount : 1
     * total : 12
     * curPage : 0
     * offset : -20
     * size : 20
     * datas : [{"date":1596621475000,"reason":"签到","id":269971,"type":1,"userName":"qqhahaboy","userId":71652,"coinCount":21,"desc":"2020-08-05 17:57:55 签到 , 积分：10 + 11"},{"date":1596547720000,"reason":"签到","id":269263,"type":1,"userName":"qqhahaboy","userId":71652,"coinCount":20,"desc":"2020-08-04 21:28:40 签到 , 积分：10 + 10"},{"date":1596456914000,"reason":"签到","id":268401,"type":1,"userName":"qqhahaboy","userId":71652,"coinCount":19,"desc":"2020-08-03 20:15:14 签到 , 积分：10 + 9"},{"date":1596352724000,"reason":"签到","id":267418,"type":1,"userName":"qqhahaboy","userId":71652,"coinCount":18,"desc":"2020-08-02 15:18:44 签到 , 积分：10 + 8"},{"date":1596245562000,"reason":"签到","id":266927,"type":1,"userName":"qqhahaboy","userId":71652,"coinCount":17,"desc":"2020-08-01 09:32:42 签到 , 积分：10 + 7"},{"date":1596197972000,"reason":"签到","id":266749,"type":1,"userName":"qqhahaboy","userId":71652,"coinCount":16,"desc":"2020-07-31 20:19:32 签到 , 积分：10 + 6"},{"date":1596112987000,"reason":"签到","id":265933,"type":1,"userName":"qqhahaboy","userId":71652,"coinCount":15,"desc":"2020-07-30 20:43:07 签到 , 积分：10 + 5"},{"date":1596023809000,"reason":"签到","id":265060,"type":1,"userName":"qqhahaboy","userId":71652,"coinCount":14,"desc":"2020-07-29 19:56:49 签到 , 积分：10 + 4"},{"date":1595944577000,"reason":"签到","id":264274,"type":1,"userName":"qqhahaboy","userId":71652,"coinCount":13,"desc":"2020-07-28 21:56:17 签到 , 积分：10 + 3"},{"date":1595852637000,"reason":"签到","id":263488,"type":1,"userName":"qqhahaboy","userId":71652,"coinCount":12,"desc":"2020-07-27 20:23:57 签到 , 积分：10 + 2"},{"date":1595724960000,"reason":"签到","id":262356,"type":1,"userName":"qqhahaboy","userId":71652,"coinCount":11,"desc":"2020-07-26 08:56:00 签到 , 积分：10 + 1"},{"date":1595644793000,"reason":"签到","id":262058,"type":1,"userName":"qqhahaboy","userId":71652,"coinCount":10,"desc":"2020-07-25 10:39:53 签到 , 积分：10 + 0"}]
     */
    private boolean over;
    private int pageCount;
    private int total;
    private int curPage;
    private int offset;
    private int size;
    private List<CoinBean> datas;

    public void setOver(boolean over) {
        this.over = over;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setDatas(List<CoinBean> datas) {
        this.datas = datas;
    }

    public boolean isOver() {
        return over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getTotal() {
        return total;
    }

    public int getCurPage() {
        return curPage;
    }

    public int getOffset() {
        return offset;
    }

    public int getSize() {
        return size;
    }

    public List<CoinBean> getDatas() {
        return datas;
    }

}
