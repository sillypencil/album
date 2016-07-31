package com.bing.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by binglau on 15-6-30.
 */
@Service
public class NotifyService {
    @Autowired
    private NotifyDao notifyDao;

    public List<Notify> getNotifiesByUserId(int userId){
        return notifyDao.getNotifiesByUserId(userId);
    }

    public int getNotifyNumByUserId(int userId){
        return notifyDao.getNotifyNumByUserId(userId);
    }
}
