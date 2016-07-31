package com.bing.user;

import com.bing.image.Album;
import com.bing.info.RelationInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/6/15.
 */
public class User {
    private int id;
    private String account;
    private String password;
    private String nickname;
    private String avatarPath;
    private int followNum;
    private int fansNum;
    private int favorNum;
    private int collectNum;
    private int notifyNum;
    private Date registerTime;
    private List<Integer> leaderIds;
    private List<Integer> followerIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public int getFavorNum() {
        return favorNum;
    }

    public void setFavorNum(int favorNum) {
        this.favorNum = favorNum;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }

    public int getNotifyNum() {
        return notifyNum;
    }

    public void setNotifyNum(int notifyNum) {
        this.notifyNum = notifyNum;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public List<Integer> getLeaderIds() {
        return leaderIds;
    }

    public void setLeaderIds(List<Integer> leaderIds) {
        this.leaderIds = leaderIds;
    }

    public List<Integer> getFollowerIds() {
        return followerIds;
    }

    public void setFollowerIds(List<Integer> followerIds) {
        this.followerIds = followerIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", followNum=" + followNum +
                ", fansNum=" + fansNum +
                ", favorNum=" + favorNum +
                ", collectNum=" + collectNum +
                ", notifyNum=" + notifyNum +
                ", registerTime=" + registerTime +
                '}';
    }
}
