package com.bing.info;

import com.bing.image.Image;
import com.bing.user.User;

import java.util.List;

public class ImageMeResult {
    private List<Image> images;
    private User user;
    private boolean isMe;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setIsMe(boolean isMe) {
        this.isMe = isMe;
    }

    @Override
    public String toString() {
        return "ImageMeResult{" +
                "images=" + images +
                ", user=" + user +
                ", isMe=" + isMe +
                '}';
    }
}
