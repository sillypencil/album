package com.bing.user;

import java.util.List;

/**
 * Created by Administrator on 2015/6/19.
 */
public interface CommentDao {
    Comment getCommentById(int id);
    List<Comment> getCommentsByImageId(int imageId);
    List<Comment> getCommentsByUserId(int userId);
    int addComment(Comment comment);
    boolean removeCommentById(int commentId);
    void removeCommentsByImageId(int imageId);
}
