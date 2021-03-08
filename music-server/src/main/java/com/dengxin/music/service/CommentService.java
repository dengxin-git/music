package com.dengxin.music.service;


import com.dengxin.music.domain.Comment;

import java.util.List;

public interface CommentService {


    public boolean insert(Comment comment);

    public boolean update(Comment comment);

    public boolean delete(Integer id);

    public Comment selectByPrimaryKey(Integer id);

    public List<Comment> allComment();

    /**
     *查询某个歌曲下的所有评论
     */
    public List<Comment> commentOfSongId(Integer singId);

    /**
     * 查询某个歌单下的所有评论
     */
    public List<Comment> commentOfSongListId(Integer songListId);


}
