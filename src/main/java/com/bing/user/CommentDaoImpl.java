package com.bing.user;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created by Administrator on 2015/6/19.
 */
public class CommentDaoImpl extends SqlSessionDaoSupport implements CommentDao{
    public Comment getCommentById(int id) {
        return (Comment)getSqlSession().selectOne("mapper.Comment.getCommentById", id);
    }

    public List<Comment> getCommentsByImageId(int imageId) {
        return getSqlSession().selectList("mapper.Comment.getCommentsByImageId", imageId);
    }

    public List<Comment> getCommentsByUserId(int userId) {
        return getSqlSession().selectList("mapper.Comment.getCommentsByUserId", userId);
    }

    public int addComment(Comment comment) {
        return getSqlSession().insert("mapper.Comment.addComment", comment);
    }

    public boolean removeCommentById(int commentId){
        int updateNum = getSqlSession().update("mapper.Comment.removeCommentById", commentId);
        if (updateNum == 0){
            return false;
        }
        return true;
    }

    public void removeCommentsByImageId(int imageId) {
        getSqlSession().update("mapper.Comment.removeCommentsByImageId", imageId);
    }
}
