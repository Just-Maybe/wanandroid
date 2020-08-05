package com.example.wanandroid.bean;

/**
 * Created by Miracle on 2020/8/5
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class CoinBean {

    /**
     * date : 1596621475000
     * reason : 签到
     * id : 269971
     * type : 1
     * userName : qqhahaboy
     * userId : 71652
     * coinCount : 21
     * desc : 2020-08-05 17:57:55 签到 , 积分：10 + 11
     */
    private long date;
    private String reason;
    private int id;
    private int type;
    private String userName;
    private int userId;
    private int coinCount;
    private String desc;

    public void setDate(long date) {
        this.date = date;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getDate() {
        return date;
    }

    public String getReason() {
        return reason;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public String getDesc() {
        return desc;
    }
}
