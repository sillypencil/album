package com.bing.control;

import com.bing.user.Notify;
import com.bing.user.NotifyService;
import com.bing.util.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by binglau on 15-7-1.
 */
@RestController
@RequestMapping("/notify")
public class NotifyController {
    @Autowired
    private NotifyService notifyService;

    @RequestMapping(value = "/num", method = RequestMethod.GET)
    public int getNotifyNumByUserId(HttpSession session){
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        return notifyService.getNotifyNumByUserId(userId);
    }

    @RequestMapping(value = "/get", method = RequestMethod.PUT)
    public List<Notify> getNotifiesByUserId(HttpSession session){
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        List<Notify> notifies = notifyService.getNotifiesByUserId(userId);
        System.out.println(notifies);
        return notifies;
    }
}
