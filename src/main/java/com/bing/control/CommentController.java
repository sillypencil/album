package com.bing.control;

import com.bing.user.Comment;
import com.bing.user.CommentService;
import com.bing.util.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2015/6/29.
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/add/{imageId}", method = RequestMethod.POST)
    public Comment addComment(@RequestParam("content") String content,
                           @PathVariable("imageId") int imageId,
                           HttpSession session){
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        Comment comment = commentService.addComment(content.trim(), imageId, userId);
        return comment;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCommentBySelf(@RequestParam("commentId") int commentId,
                                    HttpSession session){
        Session.validateUser(session);
        int userId = Session.getCurrentUserId(session);
        if(!commentService.removeCommentBySelf(commentId, userId))
            throw new CommentService.NotExistCommentById();
    }

    @RequestMapping(value = "/get/{imageId}", method = RequestMethod.GET)
    public List<Comment> getCommentsByImageId(@PathVariable("imageId") int imageId,
                                              HttpSession session){
        Session.validateUser(session);
        return commentService.getCommentsByImageId(imageId);
    }
}