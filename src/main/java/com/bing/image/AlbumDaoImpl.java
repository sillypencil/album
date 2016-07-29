package com.bing.image;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/19.
 */
class AlbumDaoImpl extends SqlSessionDaoSupport implements AlbumDao {

    public Album getAlbumById(int id) {
        return (Album) getSqlSession().selectOne("mapper.Album.getAlbumById", id);
    }

    public List<Album> getAlbumsByUserId(int userId) {
        return getSqlSession().selectList("mapper.Album.getAlbumsByUserId", userId);
    }

    public int addAlbum(Album album) {
        return getSqlSession().insert("mapper.Album.addAlbum", album);
    }


    public int removeAlbumById(Map<String, Integer> idAndUserId) {
        return getSqlSession().delete("mapper.Album.removeAlbumById", idAndUserId);
    }

    public int modifyAlbumDescription(Album album) {
        return getSqlSession().delete("mapper.Album.modifyAlbumDescription", album);
    }
}
