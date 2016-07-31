package com.bing.image;

import com.bing.user.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/6/18.
 */
public class Album {
    private int id;
    private String name;
    private String description;
    private int pictureNum;
    private Date createTime;
    private int ownUserId;
    private Image cover;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPictureNum() {
        return pictureNum;
    }

    public void setPictureNum(int pictureNum) {
        this.pictureNum = pictureNum;
    }

    public int getOwnUserId() {
        return ownUserId;
    }

    public void setOwnUserId(int ownUserId) {
        this.ownUserId = ownUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Image getCover() {
        return cover;
    }

    public void setCover(Image cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        return id == album.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pictureNum=" + pictureNum +
                ", createTime=" + createTime +
                '}';
    }
}
