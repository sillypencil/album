package com.bing.control;

import com.bing.info.RelationInfo;
import com.bing.info.UserLoginResult;
import com.bing.user.User;
import com.bing.user.UserService;
import com.bing.util.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/17.
 */
@Controller
@RequestMapping("/user")
@SessionAttributes("userId")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String Login(ModelMap modelMap,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        try {
            Session.validateUser(session);
            int userId = Session.getCurrentUserId(session);
            UserLoginResult result = userService.getResultByUserId(userId);
            redirectAttributes.addFlashAttribute("result", result);
            return "redirect:/user/main";

        } catch (UserService.NoLoginException e) {
            Session.clearSession(session);
            return "index";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String Login(@RequestParam("account") String account,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        UserLoginResult result = null;
        try {
            result = userService.login(account, password);
        } catch (UserService.PasswordErrorException e) {
            redirectAttributes.addFlashAttribute("message", "Login error");
            return "redirect:/user/index";
        }
        session.setAttribute("userInfo", result.getUser());
        redirectAttributes.addFlashAttribute("result", result);
        return "redirect:/user/main";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET,
            produces = "text/html; charset=UTF-8")
    public String mainPage(ModelMap model,
                           HttpSession session) {
        try {
            Session.validateUser(session);
            int userId = Session.getCurrentUserId(session);
            UserLoginResult result = userService.getResultByUserId(userId);
            model.addAttribute("result", result);
            return "main";
        } catch (UserService.NoLoginException e) {
            return "redirect:/user/index";
        }
    }

    @RequestMapping(value = "/modify/info", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void modifyInfo(@RequestParam("newPassword") String newPassword,
                           @RequestParam("oldPassword") String oldPassword,
                           @RequestParam("nickname") String nickname,
                           HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        if (newPassword != null && !newPassword.trim().equals("")) {
            if (!userService.changePassowrd(userId, newPassword, oldPassword))
                throw new UserService.PasswordErrorException();
        }
        if (nickname != null && !nickname.trim().equals("")){
            userService.changeNickname(userId, nickname);
        }
    }


    @RequestMapping(value = "/upload/avatar", method = RequestMethod.POST)
    public String uploadAvatar(@RequestParam("avatar") MultipartFile avatar,
                             HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        userService.avatarUpload(userId, avatar);
        return "redirect:/user/main";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST,
            produces = "text/html; charset=UTF-8")
    public String register(@RequestParam("account") String account,
                           @RequestParam("password") String password,
                           @RequestParam("nickname") String nickname,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        UserLoginResult result = null;
        try {
            userService.register(account, password, nickname);
            result = userService.login(account, password);
        } catch (DuplicateKeyException e) {
            redirectAttributes.addFlashAttribute("message", "Account Duplicate");
            return "redirect:/user/index";
        } catch (UserService.PasswordErrorException e) {
            redirectAttributes.addFlashAttribute("message", "Login error");
            return "redirect:/user/index";
        }
        session.setAttribute("userInfo", result.getUser());
        redirectAttributes.addFlashAttribute("result", result);
        return "redirect:/user/main";
    }

    @RequestMapping(value = "/follow", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void follow(@RequestParam("leaderId") int leaderId,
                       HttpSession session) {
        Session.validateUser(session);
        int followedId = Session.getCurrentUserId(session);
        try {
            userService.follow(followedId, leaderId);
        } catch (DuplicateKeyException e) {
            throw new UserService.FollowedException();
        } catch (DataIntegrityViolationException e) {
            throw new UserService.NotExistUserException();
        }
    }

    @RequestMapping(value = "/follow/cancel", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void cancelFollow(@RequestParam("leaderId") int leaderId,
                             HttpSession session) {
        Session.validateUser(session);
        int followedId = Session.getCurrentUserId(session);
        userService.cancelFollow(followedId, leaderId);
    }

    @RequestMapping(value = "/get/{userId}/followers", method = RequestMethod.GET)
    @ResponseBody
    public List<RelationInfo> getFollower(@PathVariable("userId") int userId,
                                  HttpSession session) {
        Session.validateUser(session);
        int vistorId = Session.getCurrentUserId(session);
        return userService.getFollowersRelations(vistorId, userId);
    }

    @RequestMapping(value = "/get/{userId}/leaders", method = RequestMethod.GET)
    @ResponseBody
    public List<RelationInfo> getLeaders(@PathVariable("userId") int userId,
                                 HttpSession session) {
        Session.validateUser(session);
        int vistorId = Session.getCurrentUserId(session);
        return userService.getLeadersRelations(vistorId, userId);
    }
}
