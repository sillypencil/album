package com.bing.user;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/6/19.
 */
public class NotifyDaoImpl extends SqlSessionDaoSupport implements NotifyDao {
    public void addNotify(Notify notify) {
        getSqlSession().insert("mapper.Notify.addNotify", notify);
    }

    public List<Notify> getNotifiesByUserId(int userId) {
        List<Notify> notifies =
                getSqlSession().selectList("mapper.Notify.getNotifiesByUserId", userId);
        getSqlSession().update("mapper.Notify.removeNotifiesByUserId", userId);
        return notifies;
    }

    public int getNotifyNumByUserId(int userId) {
        return (Integer)getSqlSession().
                selectOne("mapper.Notify.getNotifyNumByUserId", userId);
    }
}
