package com.bing.user;

import com.bing.image.*;
import com.bing.info.RelationInfo;
import com.bing.info.UserLoginResult;
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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Administrator on 2015/6/15.
 */
@Service
public final class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private ImageService imageService;
    @Autowired
    private AlbumService albumService;
    private int compressWidth;
    private int compressHeight;

    private FileAccess fileAccess = FileAccess.getInstance();

    private String webPath = null;

    public void setCompressWidth(int compressWidth) {
        this.compressWidth = compressWidth;
    }

    public void setCompressHeight(int compressHeight) {
        this.compressHeight = compressHeight;
    }

    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setImageDao(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    public String passwordByMd5(String password) {
        MessageDigest messageDigest;
        String result = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes("UTF-8"));
            byte[] resultBytes = messageDigest.digest();
            StringBuffer resultBuffer = new StringBuffer();
            for (int i = 0; i < resultBytes.length; i++) {
                if (Integer.toHexString(0xFF & resultBytes[i]).length() == 1)
                    resultBuffer.append("0").append(
                            Integer.toHexString(0xFF & resultBytes[i]));
                else
                    resultBuffer.append(Integer.toHexString(0xFF & resultBytes[i]));
            }
            result = resultBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public UserLoginResult getResultByUserId(int userId) {
        User user = userDao.getUserById(userId);
        if (user == null){
            throw new PasswordErrorException();
        }
        List<Image> images = imageService.getImages(user.getId(), user.getLeaderIds());
        UserLoginResult result = fullLoginDate(user, images);
        return result;
    }

    public UserLoginResult login(String account, String password) {
        Map<String, String> userLoginInfo = new HashMap<String, String>();
        userLoginInfo.put("account", account);
        userLoginInfo.put("password", passwordByMd5(password));
        User user = userDao.getUserByLogin(userLoginInfo);
        if (user == null){
            throw new PasswordErrorException();
        }
        List<Image> images = imageService.getImages(user.getId(), user.getLeaderIds());
        UserLoginResult result = fullLoginDate(user, images);
        return result;
    }

    private UserLoginResult fullLoginDate(User user, List<Image> images) {
        UserLoginResult result = new UserLoginResult();
        result.setUser(getUserInfo(user));
        result.setImages(getImagesInfo(images));

        return result;
    }

    public Map<String, String> getUserInfo(User user) {
        Map<String, String> userResult = new HashMap<String, String>();
        List<Album> albums = albumService.getAlbumMeResultByUserId(user.getId()).getAlbums();
        int imageNum = 0;
        for (Album album : albums){
            imageNum += album.getPictureNum();
        }

        userResult.put("id", String.valueOf(user.getId()));
        userResult.put("nickname", user.getNickname());
        userResult.put("avatarPath", user.getAvatarPath());
        userResult.put("followNum", String.valueOf(user.getFollowNum()));
        userResult.put("fansNum", String.valueOf(user.getFansNum()));
        userResult.put("notifyNum", String.valueOf(user.getNotifyNum()));
        userResult.put("albumNum", String.valueOf(albums.size()));
        userResult.put("imageNum", String.valueOf(imageNum));

        return userResult;
    }

    public List<Map<String, String>> getImagesInfo(List<Image> images) {
        List<Map<String, String>> imageResult = new LinkedList<Map<String, String>>();

        for (Image image : images) {
            Map<String, String> imageMap = new HashMap<String, String>();
            imageMap.put("id", String.valueOf(image.getId()));
            imageMap.put("imagePath", image.getImagePath());
            imageResult.add(imageMap);
        }

        return imageResult;
    }

    public List<User> getFollowers(int userId){
        List<User> users = userDao.getFollowersByUserId(userId);
        return users;
    }

    public List<User> getLeaders(int userId){
        List<User> users = userDao.getLeadersByUserId(userId);
        return users;
    }

    //判断vistor与user的followers之间的关系
    public List<RelationInfo> getFollowersRelations(int vistorId, int userId){
        List<User> followers = getFollowers(userId);
        return getRelations(vistorId, followers);
    }

    //判断vistor与user的leaders之间的关系
    public List<RelationInfo> getLeadersRelations(int vistorId, int userId) {
        List<User> leaders = getLeaders(userId);
        return getRelations(vistorId, leaders);
    }

    private List<RelationInfo> getRelations(int vistorId, List<User> users) {
        List<RelationInfo> relationInfos = new ArrayList<RelationInfo>();
        Map<String, Integer> followInfo = new HashMap<String, Integer>();
        //vistor是否是user的粉丝
        for (User user : users){
            RelationInfo relationInfo = new RelationInfo();
            relationInfo.setUser(user);
            followInfo.put("followerId", vistorId);
            followInfo.put("leaderId", user.getId());
            if (userDao.isFRIEND(followInfo)){
                relationInfo.setIsFollow(true);
            } else {
                relationInfo.setIsFollow(false);
            }
            relationInfos.add(relationInfo);
        }
        return relationInfos;
    }

    public void register(String account, String password, String nickname)
            throws DuplicateKeyException {
        User user = new User();
        user.setAccount(account);
        user.setPassword(passwordByMd5(password));
        user.setNickname(nickname);
        userDao.addUser(user);
    }

    public boolean changePassowrd(int userId, String newPassword, String oldPassword) {
        User user = userDao.getUserById(userId);
        System.out.println(user.getPassword().equals(passwordByMd5(oldPassword)));
        if (user.getPassword().equals(passwordByMd5(oldPassword))) {
            user.setPassword(passwordByMd5(newPassword));
            userDao.changePassword(user);
            return true;
        } else {
            return false;
        }
    }

    public void changeNickname(int userId, String nickname){
        User user = userDao.getUserById(userId);
        if (!user.getNickname().equals(nickname.trim()))
            user.setNickname(nickname);
            userDao.changeNickname(user);
    }

    public void avatarUpload(int userId, MultipartFile avatar){
        User user = userDao.getUserById(userId);
        long timeStrap = new Date().getTime();
        String filePath =  "avatar/" + userId + "/" + timeStrap + ".jpg";

        fileAccess.saveFile(webPath + filePath, avatar);

        try {
            ImageCompress imageCompress = new ImageCompress(webPath + filePath,
                    webPath + "compress/" + filePath);
            imageCompress.resizeFix(compressWidth, compressHeight);

        } catch (IOException e){
            throw new FileUploadException("压缩图片上传出错");
        }

        user.setAvatarPath(filePath);

        userDao.uploadAvatar(user);
    }

    public void follow(int followerId, int leaderId) throws DuplicateKeyException,
            DataIntegrityViolationException {
        Map<String, Integer> followInfo = new HashMap<String, Integer>();
        if (followerId != leaderId) {
            followInfo.put("followerId", followerId);
            followInfo.put("leaderId", leaderId);
            userDao.follow(followInfo);
        }
    }

    public void cancelFollow(int followerId, int leaderId) throws NotFollowedException{
        Map<String, Integer> followInfo = new HashMap<String, Integer>();
        if (followerId != leaderId) {
            followInfo.put("followerId", followerId);
            followInfo.put("leaderId", leaderId);
            userDao.cancelFollow(followInfo);
        }
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Please Login")
    public static final class NoLoginException extends RuntimeException {
        public NoLoginException() {
            super("���¼");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Password Error")
    public static final class PasswordErrorException extends RuntimeException {
        public PasswordErrorException() {
            super("�������");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Account Duplicate")
    public static final class AccountDuplicateException extends RuntimeException {
        public AccountDuplicateException() {
            super("�˺��ظ�");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not Exist The User")
    public static final class NotExistUserException extends RuntimeException {
        public NotExistUserException() {
            super("���û�������");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Have Been Followed")
    public static final class FollowedException extends RuntimeException {
        public FollowedException() {
            super("�ѹ�ע��");
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not Followed")
    public static final class NotFollowedException extends RuntimeException {
        public NotFollowedException() {
            super("δ��ע");
        }
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Have No Permission")
    public static final class HaveNoPermission extends RuntimeException{
        public HaveNoPermission() {
            super("��Ȩ��");
        }
    }
}
