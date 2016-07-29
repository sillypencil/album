package com.bing.image;

import com.bing.info.SimpleImageResult;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/19.
 */
public interface ImageDao {
    Image getImageById(int id);
    List<SimpleImageResult> getCollectImageIdsByUserId(int userId);
    List<SimpleImageResult> getFavorImageIdsByUserId(int userId);
    List<Image> getPermissionImagesByAlbumId(int albumId, Permission permission);
    List<Image> getPermissionImagesByUserId(int userId, Permission permission);
    List<Image> getMyImagesByAlbumId(int albumId);
    List<Image> getMyImages(int userId);
    void addImage(Image image);
    void removeImage(Map<String, Integer> idAndUserId);
    void removeImagesByAlbumId(int albumId);
    void favor(Map<String, Integer> enjoyInfo);
    void cancelFavor(Map<String, Integer> enjoyInfo);
    void collect(Map<String, Integer> collectInfo, Image image);
    void cancelCollect(Map<String, Integer> collectInfo);
    void changeAlbum(Image image);
}
