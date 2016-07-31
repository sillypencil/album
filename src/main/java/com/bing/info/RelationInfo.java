package com.bing.info;

import com.bing.user.User;

/**
 * 表示一个User（访问者）与其中的user之间的关系
 */
public class RelationInfo {
    private User user;
    private boolean isFollow;

    public User getUser() {
        return user;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }

}
