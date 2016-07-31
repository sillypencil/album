package com.bing.user;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/15.
 */
public interface UserDao {
    User getUserById(int id);
    User getUserByLogin(Map<String, String> loginInfo);
    List<User> getFollowersByUserId(int userId);
    List<User> getLeadersByUserId(int userId);
    void addUser(User user);
    void changePassword(User user);
    void changeNickname(User user);
    void follow(Map<String, Integer> followInfo);
    void cancelFollow(Map<String, Integer> followInfo);
    boolean isFRIEND(Map<String, Integer> followInfo);
    void uploadAvatar(User user);
}
