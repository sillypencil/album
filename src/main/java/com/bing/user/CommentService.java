package com.bing.user;

import com.bing.image.Image;
import com.bing.image.ImageDao;
import com.bing.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private NotifyDao notifyDao;

    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public void setImageDao(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    public Comment getCommentById(int commentId){
        return commentDao.getCommentById(commentId);
    }

    public List<Comment> getCommentsByImageId(int imageId) {
        return commentDao.getCommentsByImageId(imageId);
    }

    public Comment addComment(String content, int imageId, int userId) {
        Image image = imageDao.getImageById(imageId);
        if (image == null || image.isRemove()){
            throw new ImageService.NotExistImageById();
        }
        Comment comment = new Comment();
        comment.setImageId(imageId);
        comment.setContent(content);
        comment.setUserId(userId);
        commentDao.addComment(comment);


        //添加通知部分，自己评论自己图片通知免去
        if (image.getOwnUserId() != userId){
            Notify notify = new Notify();
            notify.setNotifiedUserId(image.getOwnUserId());
            notify.setNotifyContentId(imageId);
            notify.setNotifyType(NotifyType.COMMENT);
            notify.setNotifyUserId(userId);
            notifyDao.addNotify(notify);
        }

        return comment;
    }

    public boolean removeCommentBySelf(int commentId, int userId){
        Comment comment = getCommentById(commentId);
        if (comment == null){
            throw new NotExistCommentById();
        }
        if (comment.getUserId() != userId){
            throw new UserService.HaveNoPermission();
        }
        return commentDao.removeCommentById(commentId);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not Exist Comment By Id")
    public static final class NotExistCommentById extends RuntimeException{
        public NotExistCommentById() {
            super("�����ڸ�����");
        }
    }
}
