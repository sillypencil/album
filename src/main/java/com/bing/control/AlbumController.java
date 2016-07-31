package com.bing.control;

import com.bing.image.Album;
import com.bing.image.AlbumService;
import com.bing.info.AlbumMeResult;
import com.bing.info.ImageMeResult;
import com.bing.user.UserService;
import com.bing.util.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2015/6/23.
 */
@Controller
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @RequestMapping(value = "/{albumId}/images", method = RequestMethod.GET)
    public String getImagesByAlbumId(@PathVariable("albumId") int albumId,
                                     Model model,
                                     HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        ImageMeResult result = albumService.getImageMeResultByAlbumId(userId, albumId);
        List<Album> albums = albumService.getAlbumsByUserId(userId);
        model.addAttribute("albums", albums);
        model.addAttribute("result", result);
        model.addAttribute("isImages", true);
        model.addAttribute("inAlbum", true);
        model.addAttribute("album", result.getImages().get(0).getAlbum());
        return "me";
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public String getMyAlbums(Model model,
                              HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        AlbumMeResult result = albumService.getAlbumMeResultByUserId(userId);
        result.setIsMe(true);
        model.addAttribute("albums", result.getAlbums());
        model.addAttribute("result", result);
        model.addAttribute("isImages", false);
        model.addAttribute("inAlbum", false);
        return "me";
    }

    @RequestMapping(value = "/me/ajax", method = RequestMethod.GET)
    @ResponseBody
    public AlbumMeResult getMyAlbumsByAjax(HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        AlbumMeResult result = albumService.getAlbumMeResultByUserId(userId);
        result.setIsMe(true);
        return result;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public String getAlbumsByUserId(@RequestParam("userId") int userId,
                                    Model model,
                                    HttpSession session) {
        Session.validateUser(session);
        int vistorId = Session.getCurrentUserId(session);
        if (vistorId == userId){
            return "redirect:/album/me";
        }
        AlbumMeResult result = albumService.getAlbumMeResultByUserId(userId);
        result.setIsMe(true);
        model.addAttribute("albums", result.getAlbums());
        model.addAttribute("result", result);
        model.addAttribute("isImages", false);
        model.addAttribute("inAlbum", false);
        return "me";
    }

    @RequestMapping(value = "/{userId}/ajax", method = RequestMethod.GET)
    @ResponseBody
    public AlbumMeResult getAlbumsByUserIdAjax(@RequestParam("userId") int userId,
                                               HttpSession session) {
        Session.validateUser(session);
        int vistorId = Session.getCurrentUserId(session);
        if (vistorId == userId){
            return getMyAlbumsByAjax(session);
        }
        AlbumMeResult result = albumService.getAlbumMeResultByUserId(userId);
        result.setIsMe(true);
        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Album createAlbum(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        Album album = null;
        try {
            album = albumService.createAlbum(userId, name, description);
        } catch (DataIntegrityViolationException e) {
            throw new UserService.NotExistUserException();
        }
        return album;

    }

    @RequestMapping(value = "/modify/description", method = RequestMethod.PUT)
    @ResponseBody
    public Album modifyDescription(@RequestParam("albumId") int albumId,
                                   @RequestParam("description") String description,
                                   HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        Album album = albumService.modifyAlbumDescription(userId, albumId, description);
        return album;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void removeAlbum(@RequestParam("albumId") int albumId,
                            HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        albumService.removeAlbumById(userId, albumId);
    }
}
