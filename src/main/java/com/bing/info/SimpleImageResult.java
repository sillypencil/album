package com.bing.info;

import com.bing.image.Image;

/**
 * 记录user喜欢或收集image
 */
public class SimpleImageResult {
    private Image image;
    private int userId;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
