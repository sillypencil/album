package com.bing.image;

import com.bing.info.PermissionInfo;
import com.bing.info.SimpleImageResult;
import com.bing.user.CommentDao;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ImageDaoImpl extends SqlSessionDaoSupport implements ImageDao{
    @Autowired
    private CommentDao commentDao;

    public Image getImageById(int id) {
        return (Image) getSqlSession().selectOne("mapper.Image.getImageById", id);
    }

    public List<SimpleImageResult> getCollectImageIdsByUserId(int userId) {
        return getSqlSession().selectList("mapper.Image.getCollectImageIdsByUserId", userId);
    }

    public List<SimpleImageResult> getFavorImageIdsByUserId(int userId) {
        return getSqlSession().selectList("mapper.Image.getFavorImageIdsByUserId", userId);
    }

    public List<Image> getPermissionImagesByAlbumId(int albumId, Permission permission) {
        PermissionInfo permissionInfo = PermissionInfo.getInstance(albumId, permission);
        return getSqlSession().selectList("mapper.Image.getPermissionImagesByAlbumId", permissionInfo);
    }

    public List<Image> getPermissionImagesByUserId(int userId, Permission permission) {
        PermissionInfo permissionInfo = PermissionInfo.getInstance(userId, permission);
        return getSqlSession().selectList("mapper.Image.getPermissionImagesByUserId", permissionInfo);
    }

    public List<Image> getMyImagesByAlbumId(int albumId) {
        return getSqlSession().selectList("mapper.Image.getMyImagesByAlbumId", albumId);
    }

    public List<Image> getMyImages(int userId) {
        return getSqlSession().selectList("mapper.Image.getMyImages", userId);
    }

    public void addImage(Image image) {
        getSqlSession().insert("mapper.Image.addImage", image);
        getSqlSession().update("mapper.Album.pictureNumIncrement", image.getOwnAlbumId());
    }

    public void removeImage(Map<String, Integer> idAndUserId) {
        Image image = getImageById(idAndUserId.get("id"));
        if (image == null){
            throw new ImageService.NotExistImageById();
        }
        int removeNum = getSqlSession().delete("mapper.Image.removeImageById", idAndUserId);
        if (removeNum <= 0){
            throw new ImageService.NotExistImageById();
        }
        commentDao.removeCommentsByImageId(idAndUserId.get("id"));
        getSqlSession().update("mapper.Album.pictureNumDecrease", image.getOwnAlbumId());
    }

    public void removeImagesByAlbumId(int albumId) {
        getSqlSession().update("mapper.Image.removeImagesByAlbumId", albumId);
    }

    /**
     * @param favorInfo userId��imageId, ��������ǰ�����ϲ�ȷ��image������user����Ƭ
     */
    public void favor(Map<String, Integer> favorInfo) {
        getSqlSession().insert("mapper.Image.favor", favorInfo);
        getSqlSession().update("mapper.Image.favorNumIncrement", favorInfo);
        getSqlSession().update("mapper.User.favorNumIncrement", favorInfo.get("userId"));
    }

    public void cancelFavor(Map<String, Integer> favorInfo) {
        int deleteNum = getSqlSession().delete("cancelFavor", favorInfo);
        if (deleteNum == 1) {
            getSqlSession().update("mapper.Image.favorNumDecrease", favorInfo);
            getSqlSession().update("mapper.User.favorNumDecrease", favorInfo.get("userId"));
        } else {
            throw new ImageService.CanNotOperateImage();
        }
    }

    /**
     * @param collectInfo userId��imageId, ��������ǰ�����ϲ�ȷ��image������user����Ƭ
     */
    public void collect(Map<String, Integer> collectInfo, Image image) {
        addImage(image);
        getSqlSession().insert("mapper.Image.collect", collectInfo);
        getSqlSession().update("mapper.Image.collectNumIncrement", collectInfo);
        getSqlSession().update("mapper.User.collectNumIncrement", collectInfo.get("userId"));
    }

    public void cancelCollect(Map<String, Integer> collectInfo) {
        int deleteNum = getSqlSession().delete("cancelCollect", collectInfo);
        if (deleteNum == 1){
            getSqlSession().update("mapper.Image.collectNumDecrease", collectInfo);
            getSqlSession().update("mapper.User.collectNumDecrease", collectInfo.get("userId"));
        } else {
            throw new ImageService.CanNotOperateImage();
        }
    }

    public void changeAlbum(Image image) {
        getSqlSession().update("mapper.Image.changeAlbum", image);
    }
}

