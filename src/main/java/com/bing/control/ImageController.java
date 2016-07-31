package com.bing.control;

import com.bing.image.*;
import com.bing.info.ImageMeResult;
import com.bing.info.SimpleImageResult;
import com.bing.user.Comment;
import com.bing.user.CommentService;
import com.bing.util.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2015/6/23.
 */
@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public String getImage(@PathVariable("id") int id,
                           Model model,
                           HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        Image image = imageService.getImageById(userId, id);
        List<Comment> comments = commentService.getCommentsByImageId(image.getId());
        List<Image> images = albumService.getImagesByAlbumId(userId, image.getOwnAlbumId());
        List<Album> albums = albumService.getAlbumsByUserId(userId);
        if (image.getOwnUserId() == userId) {
            model.addAttribute("isMe", true);
        } else {
            model.addAttribute("isMe", false);
        }
        model.addAttribute("albums", albums);
        model.addAttribute("images", images);
        model.addAttribute("solImage", image);
        model.addAttribute("comments", comments);
        return "photo";
    }

    @RequestMapping(value = "/get/{id}/ajax", method = RequestMethod.GET)
    public Image getSolImageBYAjax(@PathVariable("id") int id,
                                   Model model,
                                   HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        Image image = imageService.getImageById(userId, id);
        return image;
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public String getMyImages(Model model,
                              HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        ImageMeResult result = imageService.getMyImages(userId);
        List<Album> albums = albumService.getAlbumsByUserId(userId);
        model.addAttribute("albums", albums);
        model.addAttribute("result", result);
        model.addAttribute("isImages", true);
        model.addAttribute("inAlbum", false);
        return "me";
    }

    @RequestMapping(value = "/me/ajax", method = RequestMethod.GET)
    @ResponseBody
    public ImageMeResult getMyImagesByAjax(HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        ImageMeResult result = imageService.getMyImages(userId);
        return result;
    }


    @RequestMapping(value = "/images/{ownImagesUserId}", method = RequestMethod.GET)
    public String getImagesByUserId(@PathVariable("ownImagesUserId") int ownImagesUserId,
                                    Model model,
                                    HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        ImageMeResult result = imageService.getImagesByUserId(userId, ownImagesUserId);
        List<Album> albums = albumService.getAlbumsByUserId(userId);
        model.addAttribute("albums", albums);
        model.addAttribute("result", result);
        model.addAttribute("isImages", true);
        model.addAttribute("inAlbum", false);
        return "me";
    }

    @RequestMapping(value = "/images/{ownImagesUserId}/ajax", method = RequestMethod.GET)
    @ResponseBody
    public ImageMeResult getImagesByUserIdAjax(@PathVariable("ownImagesUserId") int ownImagesUserId,
                                               HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        ImageMeResult result = imageService.getImagesByUserId(userId, ownImagesUserId);
        return result;
    }

    @RequestMapping(value = "/collectImages/{ownImagesUserId}/ajax")
    @ResponseBody
    public List<SimpleImageResult> getCollectImagesByUserIdAjax(@PathVariable("ownImagesUserId") int ownImageUserId,
                                                                HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        return imageService.getCollectImagesByUserId(ownImageUserId);

    }

    @RequestMapping(value = "/favorImages/{ownImagesUserId}/ajax")
    @ResponseBody
    public List<SimpleImageResult> getfavorImagesByUserIdAjax(@PathVariable("ownImagesUserId") int ownImageUserId,
                                                              HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        return imageService.getFavorImagesByUserId(ownImageUserId);

    }


    @RequestMapping(value = "/upload", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public String uploadImage(@RequestParam("ownAlbumId") int ownAlbumId,
                              @RequestParam("permission") Permission permission,
                              @RequestParam("image") MultipartFile image,
                              @RequestParam("filterType") FilterType filterType,
                              HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        Image resultImage = imageService.addImage(userId,
                ownAlbumId, image, permission, filterType);
        return "redirect:/image/me";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeImage(@RequestParam("id") int id,
                            HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        imageService.removeImage(userId, id);
    }

    @RequestMapping(value = "/favor", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void favorImage(@RequestParam("imageId") int imageId,
                           HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        imageService.favor(userId, imageId);
    }

    @RequestMapping(value = "/favor/cancel", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelFavorImage(@RequestParam("imageId") int imageId,
                                 HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        imageService.cancelFavor(userId, imageId);
    }

    @RequestMapping(value = "/collect/{imageId}", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void collectImage(@PathVariable("imageId") int imageId,
                             @RequestParam("ownAlbumId") int ownAlbumId,
                             @RequestParam("permission") Permission permission,
                             HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        imageService.collect(userId, imageId, ownAlbumId, permission);
    }

    @RequestMapping(value = "/collect/cancel", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelCollectImage(@RequestParam("imageId") int imageId,
                                   HttpSession session) {
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        imageService.cancelCollect(userId, imageId);
    }

    @RequestMapping(value = "/change/album/{imageId}", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAlbum(@PathVariable("imageId") int imageId,
                            @RequestParam("albumId") int albumId,
                            HttpSession session) {
        Session.validateUser(session);
        imageService.changeAlbum(imageId, albumId);
    }
}
