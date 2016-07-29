package com.bing.user;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

/**
 * Created by Administrator on 2015/6/18.
 */
public class Notify {
    private int id;
    private NotifyType notifyType;
    private int notifyContentId;
    private int notifyUserId;
    private User notifyUser;
    private int notifiedUserId;
    private Date createTime;
    private boolean isRemove;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setIsNotify(boolean isNotify) {
        this.isRemove = isNotify;
    }

    public NotifyType getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(NotifyType notifyType) {
        this.notifyType = notifyType;
    }

    public int getNotifyContentId() {
        return notifyContentId;
    }

    public void setNotifyContentId(int notifyContentId) {
        this.notifyContentId = notifyContentId;
    }

    public int getNotifyUserId() {
        return notifyUserId;
    }

    public void setNotifyUserId(int notifyUserId) {
        this.notifyUserId = notifyUserId;
    }

    public int getNotifiedUserId() {
        return notifiedUserId;
    }

    public void setNotifiedUserId(int notifiedUserId) {
        this.notifiedUserId = notifiedUserId;
    }

    public User getNotifyUser() {
        return notifyUser;
    }

    public void setNotifyUser(User notifyUser) {
        this.notifyUser = notifyUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Notify notify = (Notify) o;

        return new EqualsBuilder()
                .append(id, notify.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Notify{" +
                "id=" + id +
                ", notifyType=" + notifyType +
                ", notifiedUserId=" + notifiedUserId +
                ", createTime=" + createTime +
                ", isRemove=" + isRemove +
                ", notifyContentId=" + notifyContentId +
                ", notifyUserId=" + notifyUserId +
                '}';
    }
}
