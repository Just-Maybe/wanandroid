package com.example.wanandroid.bean;

/**
 * Created by Miracle on 2020/8/4
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class CoinBean {
    /**
     * level : 2
     * rank : 3442
     * userId : 71652
     * coinCount : 165
     * username : q**ahaboy
     */
    private int level;
    private String rank;
    private int userId;
    private int coinCount;
    private String username;

    public void setLevel(int level) {
        this.level = level;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public String getRank() {
        return rank;
    }

    public int getUserId() {
        return userId;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public String getUsername() {
        return username;
    }
}
