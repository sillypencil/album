package com.bing.info;

import java.util.*;

public class UserLoginResult {
    private Map<String, String> user;
    private List<Map<String, String>> images;

    public Map<String, String> getUser() {
        return user;
    }

    public void setUser(Map<String, String> user) {
        this.user = user;
    }

    public List<Map<String, String>> getImages() {
        return images;
    }

    public void setImages(List<Map<String, String>> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "UserLoginResult{" +
                "user=" + user +
                ", images=" + images +
                '}';
    }
}
