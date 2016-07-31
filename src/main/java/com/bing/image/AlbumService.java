package com.bing.image;

import com.bing.image.AlbumDao;
import com.bing.image.ImageDao;
import com.bing.info.AlbumMeResult;
import com.bing.info.ImageMeResult;
import com.bing.user.User;
import com.bing.user.UserDao;
import com.bing.user.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;


@Service
public final class AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UserDao userDao;

    private static Logger logger = LogManager.getLogger(AlbumService.class.getName());

    public ImageMeResult getImageMeResultByAlbumId(int userId, int albumId){
        List<Image> images = getImagesByAlbumId(userId, albumId);
        ImageMeResult result = new ImageMeResult();
        Album album = images.get(0).getAlbum();
        if (album.getOwnUserId() == userId)
            result.setIsMe(true);
        else
            result.setIsMe(false);
        User user = userDao.getUserById(images.get(0).getOwnUserId());
        result.setUser(user);
        result.setImages(images);
        return result;
    }

    public List<Image> getImagesByAlbumId(int userId, int albumId) {
        List<Image> result = new ArrayList<Image>();

        Album album = albumDao.getAlbumById(albumId);
        int albumUserId = album.getOwnUserId();
        if (albumUserId == userId){
            return getMyImagesByAlbumId(userId, albumId);
        }

        result.addAll(imageDao.getPermissionImagesByAlbumId(albumId, Permission.ALL));


        Map<String, Integer> followerInfo = new HashMap<String, Integer>();
        followerInfo.put("followerId", userId);
        followerInfo.put("leaderId", albumUserId);

        if (userDao.isFRIEND(followerInfo)) {
            result.addAll(imageDao.getPermissionImagesByAlbumId(albumId, Permission.FRIEND));
        }

        return result;
    }

    private List<Image> getMyImagesByAlbumId(int userId, int albumId) {
        List<Image> result = new ArrayList<Image>();
        result.addAll(imageDao.getMyImagesByAlbumId(albumId));

        return result;
    }

    public AlbumMeResult getAlbumMeResultByUserId(int userId) {
        if (userId < 0) {
            throw new IllegalArgumentException();
        }

        List<Album> albums = albumDao.getAlbumsByUserId(userId);
        AlbumMeResult result = new AlbumMeResult();
        result.setAlbums(albums);
        User user = userDao.getUserById(userId);
        result.setUser(user);

        return result;
    }

    public List<Album> getAlbumsByUserId(int userId){
        return albumDao.getAlbumsByUserId(userId);
    }

    public Album createAlbum(int userId, String name, String description)
            throws DataIntegrityViolationException, DuplicateKeyException {

        if (name.length() <= 0) {
            throw new NeedAlbumNameException();
        }

        Album album = new Album();
        album.setDescription(description);
        album.setName(name);
        album.setOwnUserId(userId);
        int id = albumDao.addAlbum(album);
        album.setId(id);

        return album;
    }

    public void removeAlbumById(int userId, int albumId) {

        Map<String, Integer> idAndUserId = new HashMap<String, Integer>();
        idAndUserId.put("userId", userId);
        idAndUserId.put("id", albumId);

        int removeNum = albumDao.removeAlbumById(idAndUserId);
        if (removeNum == 0) {
            throw new NotExistAlbumById();
        }
    }

    public Album modifyAlbumDescription(int userId, int albumId, String description) {

        Album album = new Album();
        album.setId(albumId);
        album.setOwnUserId(userId);
        album.setDescription(description);

        Album resultAlbum = albumDao.getAlbumById(albumId);

        int updateNum = albumDao.modifyAlbumDescription(album);
        if (updateNum == 0) {
            throw new NotExistAlbumById();
        }

        return resultAlbum;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Need Album Name")
    public static final class NeedAlbumNameException extends RuntimeException {
        public NeedAlbumNameException() {
            super("ȱ���������");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Name Must Be Unique")
    public static final class NameUniqueException extends RuntimeException {
        public NameUniqueException() {
            super("AlbumName��ҪΨһ");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not Exist The Album")
    public static final class NotExistAlbumById extends RuntimeException {
        public NotExistAlbumById() {
            super("�����ڸ�id��ָ�����");
        }
    }

}
