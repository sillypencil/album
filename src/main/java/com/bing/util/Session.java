package com.bing.util;

import com.bing.user.UserService;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/26.
 */
public final class Session {
    public static void validateUser(HttpSession session) {

        if (session.getAttribute("userInfo") != null)
            return;
        throw new UserService.NoLoginException();
    }

    public static int getCurrentUserId(HttpSession session) {

        Map<String, String> userInfo = (Map) session.getAttribute("userInfo");
        int userId = 0;
        try {
            userId = Integer.parseInt(userInfo.get("id"));
        } catch (NumberFormatException e) {
            throw new UserService.NoLoginException();
        }
        if (userId == 0) {
            throw new UserService.NoLoginException();
        }
        return userId;
    }

    public static void clearSession(HttpSession session){
        session.invalidate();
    }
}
