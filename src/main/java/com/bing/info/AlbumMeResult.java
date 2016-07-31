package com.bing.info;

import com.bing.image.Album;
import com.bing.user.User;

import java.util.List;

public class AlbumMeResult {
    private List<Album> albums;
    private User user;
    private boolean isMe;

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
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
        return "AlbumMeResult{" +
                "albums=" + albums +
                ", user=" + user +
                ", isMe=" + isMe +
                '}';
    }
}
