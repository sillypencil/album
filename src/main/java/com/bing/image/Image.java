package com.bing.image;

import com.bing.user.User;

import java.util.Date;

/**
 * Created by Administrator on 2015/6/18.
 */
public class Image {
    private int id;
    private String imagePath;
    private int favorNum;
    private int collectNum;
    private int ownAlbumId;
    private int ownUserId;
    private Date createTime;
    private User user;
    private Album album;
    private boolean isRemove;

    private Permission permission;

    private FilterType filterType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getOwnAlbumId() {
        return ownAlbumId;
    }

    public void setOwnAlbumId(int ownAlbumId) {
        this.ownAlbumId = ownAlbumId;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public int getOwnUserId() {
        return ownUserId;
    }

    public void setOwnUserId(int ownUserId) {
        this.ownUserId = ownUserId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setIsRemove(boolean isRemove) {
        this.isRemove = isRemove;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(id, image.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", favorNum=" + favorNum +
                ", collectNum=" + collectNum +
                ", ownAlbumId=" + ownAlbumId +
                ", createTime=" + createTime +
                '}';
    }
}
