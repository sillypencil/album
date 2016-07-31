package com.bing.user;

import java.util.List;

/**
 * Created by Administrator on 2015/6/19.
 */
public interface NotifyDao {
    void addNotify(Notify notify);
    List<Notify> getNotifiesByUserId(int userId);
    int getNotifyNumByUserId(int userId);
}
