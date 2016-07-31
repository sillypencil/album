package com.bing.image;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/19.
 */
public interface AlbumDao {
    Album getAlbumById(int id);

    List<Album> getAlbumsByUserId(int userId);

    int addAlbum(Album album);

    int removeAlbumById(Map<String, Integer> idAndUserId);

    int modifyAlbumDescription(Album album);
}
