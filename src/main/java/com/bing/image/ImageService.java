package com.bing.image;

import com.bing.info.ImageMeResult;
import com.bing.info.SimpleImageResult;
import com.bing.user.*;
import com.bing.util.FileAccess;
import com.bing.util.FileUploadException;
import com.bing.util.ImageCompress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.MarshalledObject;
import java.util.*;

/**
 * Created by Administrator on 2015/6/23.
 */
@Service
public final class ImageService {
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private NotifyDao notifyDao;

    private String webPath;
    private int compressWidth;
    private int compressHeight;
    private FileAccess fileAccess = FileAccess.getInstance();

    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }

    public void setCompressWidth(int compressWidth) {
        this.compressWidth = compressWidth;
    }

    public void setCompressHeight(int compressHeight) {
        this.compressHeight = compressHeight;
    }

    public Image getImageById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id error");
        }
        Image image = imageDao.getImageById(id);
        return image;
    }

    public Image getImageById(int userId, int id) {
        Image image = getImageById(id);
        if (image == null) {
            throw new NotExistImageById();
        }
        if (image.getPermission() == Permission.FRIEND && userId != image.getOwnUserId()) {
            Map<String, Integer> followerInfo = new HashMap<String, Integer>();
            followerInfo.put("followerId", userId);
            followerInfo.put("leaderId", image.getId());

            if (userDao.isFRIEND(followerInfo)) {
                throw new NotExistImageById();
            }
        }
        return image;
    }

    public List<Image> getImages(int userId, List<Integer> leaderIds) {
        List<Image> result = new ArrayList<Image>();

        result.addAll(imageDao.getMyImages(userId));

        for (Integer leaderId : leaderIds) {
            result.addAll(imageDao.getPermissionImagesByUserId(leaderId, Permission.ALL));
            result.addAll(imageDao.getPermissionImagesByUserId(leaderId, Permission.FRIEND));
        }

        Collections.shuffle(result);
        return result;
    }


    public ImageMeResult getMyImages(int userId) {
        List<Image> images = imageDao.getMyImages(userId);
        Collections.shuffle(images);

        ImageMeResult result = new ImageMeResult();
        result.setImages(images);

        User user = userDao.getUserById(userId);

        result.setUser(user);

        result.setIsMe(true);

        return result;
    }

    public ImageMeResult getImagesByUserId(int userId, int ownImagesUserId) {
        if (userId == ownImagesUserId) {
            return getMyImages(userId);
        }
        List<Image> images = new ArrayList<Image>();

        ImageMeResult result = new ImageMeResult();

        User user = userDao.getUserById(ownImagesUserId);

        Map<String, Integer> followerInfo = new HashMap<String, Integer>();
        followerInfo.put("followerId", userId);
        followerInfo.put("leaderId", ownImagesUserId);

        images.addAll(imageDao.getPermissionImagesByUserId(ownImagesUserId, Permission.ALL));
        System.out.println(">>>" + userDao.isFRIEND(followerInfo));
        if (userDao.isFRIEND(followerInfo)) {
            images.addAll(imageDao.getPermissionImagesByUserId(ownImagesUserId, Permission.FRIEND));
        }

        Collections.shuffle(images);

        result.setImages(images);
        result.setUser(user);

        result.setIsMe(false);

        return result;
    }

    public List<SimpleImageResult> getCollectImagesByUserId(int userId) {
        return imageDao.getCollectImageIdsByUserId(userId);
    }

    public List<SimpleImageResult> getFavorImagesByUserId(int userId) {
        return imageDao.getFavorImageIdsByUserId(userId);
    }

    public Image addImage(int userId,
                          int albumId,
                          MultipartFile image,
                          Permission permission,
                          FilterType filterType) {
        validateImage(image);
        Calendar calendar = Calendar.getInstance();
        long timeStrap = calendar.getTime().getTime();
        String filePath =
                calendar.YEAR + "/" + calendar.MONTH + "/" + calendar.DAY_OF_MONTH + "/" + timeStrap + ".jpg";

        fileAccess.saveFile(webPath + filePath, image);
        try {
            ImageCompress imageCompress = new ImageCompress(webPath + filePath,
                    webPath + "compress/" + filePath);
            imageCompress.resizeFix(compressWidth, compressHeight);

        } catch (IOException e) {
            throw new FileUploadException("压缩图片上传出错");
        }

        Image tmpImage = new Image();
        tmpImage.setOwnAlbumId(albumId);
        tmpImage.setOwnUserId(userId);
        tmpImage.setPermission(permission);
        tmpImage.setImagePath(filePath);
        tmpImage.setFilterType(filterType);

        imageDao.addImage(tmpImage);
        return tmpImage;
    }

    private void validateImage(MultipartFile image) {
        if (image.getContentType().equals("image/jpeg")) {
            return;
        }
//        System.out.println(image.getOriginalFilename());
        //暂时使用后缀名来判断
        String fileName = image.getOriginalFilename();
        if (fileName.substring(fileName.length() - 3).equals("jpg")) {
            return;
        }

        throw new ImageFormatError();
    }

    public void removeImage(int userId, int imageId) {
        Map<String, Integer> idAndUserId = new HashMap<String, Integer>();
        idAndUserId.put("id", imageId);
        idAndUserId.put("userId", userId);
        Image image = getImageById(imageId);
        if (image == null) {
            throw new NotExistImageById();
        }
        imageDao.removeImage(idAndUserId);
    }


    public void favor(int userId, int imageId) {
        Image image = getImageById(imageId);
        if (image == null) {
            throw new NotExistImageById();
        }
        if (image.getOwnUserId() == userId) {
            return;
        }
        Map<String, Integer> favorInfo = new HashMap<String, Integer>();
        favorInfo.put("imageId", imageId);
        favorInfo.put("userId", userId);
        try {
            imageDao.favor(favorInfo);
        } catch (DuplicateKeyException e){
            throw new CanNotOperateImage();
        }

        Notify notify = new Notify();
        notify.setNotifyUserId(userId);
        notify.setNotifyType(NotifyType.FAVOR);
        notify.setNotifyContentId(imageId);
        notify.setNotifiedUserId(image.getOwnUserId());
        notifyDao.addNotify(notify);
    }

    public void cancelFavor(int userId, int imageId) {
        Map<String, Integer> favorInfo = new HashMap<String, Integer>();
        favorInfo.put("imageId", imageId);
        favorInfo.put("userId", userId);
        imageDao.cancelFavor(favorInfo);
    }

    public void collect(int userId,
                        int imageId,
                        int albumId,
                        Permission permission) {
        Map<String, Integer> collectInfo = new HashMap<String, Integer>();

        Album album = albumDao.getAlbumById(albumId);
        if (album.getOwnUserId() != userId) {
            throw new UserService.HaveNoPermission();
        }

        collectInfo.put("imageId", imageId);
        collectInfo.put("userId", userId);

        Image image = getImageById(imageId);
        if (image.getPermission() == Permission.PRIVATE) {
            throw new CanNotOperateImage();
        }
        if (image.getPermission() == Permission.FRIEND) {
            Map<String, Integer> followInfo = new HashMap<String, Integer>();
            followInfo.put("followerId", userId);
            followInfo.put("leaderId", image.getOwnUserId());
            if (!userDao.isFRIEND(followInfo)) {
                throw new CanNotOperateImage();
            }
        }

        Notify notify = new Notify();
        notify.setNotifyUserId(userId);
        notify.setNotifyType(NotifyType.COLLECT);
        notify.setNotifyContentId(imageId);
        notify.setNotifiedUserId(image.getOwnUserId());
        notifyDao.addNotify(notify);

        image.setOwnAlbumId(albumId);
        image.setOwnUserId(userId);
        image.setPermission(permission);

        imageDao.collect(collectInfo, image);

    }

    public void cancelCollect(int userId, int imageId) {
        Map<String, Integer> collectInfo = new HashMap<String, Integer>();
        collectInfo.put("imageId", imageId);
        collectInfo.put("userId", userId);
        Image image = getImageById(imageId);
        imageDao.cancelCollect(collectInfo);
        removeImage(userId, imageId);
    }

    public void changeAlbum(int imageId, int albumId){
        Image image = getImageById(imageId);
        image.setOwnAlbumId(albumId);
        imageDao.changeAlbum(image);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not Exist The Image")
    public static final class NotExistImageById extends RuntimeException {
        public NotExistImageById() {
            super("不存在该图片");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Can Not Operate The Image")
    public static final class CanNotOperateImage extends RuntimeException {
        public CanNotOperateImage() {
            super("不能操作该图片");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Format Error")
    public static final class ImageFormatError extends RuntimeException {
        public ImageFormatError() {
            super("图片格式错误");
        }
    }

}
