package com.bing.user;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/15.
 */
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

    public User getUserById(int id) {
        return (User) getSqlSession().selectOne("mapper.User.getUserById", id);
    }

    public User getUserByLogin(Map<String, String> loginInfo) {
        return (User) getSqlSession().selectOne("mapper.User.getUserByLogin", loginInfo);
    }

    public List<User> getFollowersByUserId(int userId) {
        List<Integer> followers = getSqlSession().
                selectList("mapper.User.getFollowerIdsByUserId", userId);
        if (followers == null || followers.size() == 0)
            return new ArrayList<User>();
        return getSqlSession().selectList("mapper.User.getUsersByIds", followers);
    }

    public List<User> getLeadersByUserId(int userId) {
        List<Integer> leaders = getSqlSession().
                selectList("mapper.User.getLeaderIdsByUserId", userId);
        if (leaders == null || leaders.size() == 0)
            return new ArrayList<User>();
        return getSqlSession().selectList("mapper.User.getUsersByIds", leaders);
    }

    public void addUser(User user) {
        getSqlSession().insert("mapper.User.addUser", user);
    }

    public void changePassword(User user) {
        getSqlSession().update("mapper.User.changePassword", user);
    }

    public void changeNickname(User user) {
        getSqlSession().update("mapper.User.changeNickname", user);
    }

    public void uploadAvatar(User user) {
        getSqlSession().update("mapper.User.uploadAvatar", user);
    }

    public void follow(Map<String, Integer> followInfo) {
        getSqlSession().insert("mapper.User.follow", followInfo);
        getSqlSession().update("mapper.User.fansNumIncrement", followInfo.get("leaderId"));
        getSqlSession().update("mapper.User.followNumIncrement", followInfo.get("followerId"));
    }

    public void cancelFollow(Map<String, Integer> followInfo) {
        if(getSqlSession().delete("mapper.User.cancelFollow", followInfo) == 0){
            throw new UserService.NotFollowedException();
        }
        getSqlSession().update("mapper.User.fansNumDecrease", followInfo.get("leaderId"));
        getSqlSession().update("mapper.User.followNumDecrease", followInfo.get("followerId"));

    }

    public boolean isFRIEND(Map<String, Integer> followInfo) {
        if ((Integer)getSqlSession().selectOne("mapper.User.isFRIEND", followInfo) == 1)
            return true;
        return false;
    }
}
