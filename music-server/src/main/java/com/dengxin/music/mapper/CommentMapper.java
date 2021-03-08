package com.dengxin.music.mapper;

import com.dengxin.music.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface CommentMapper {
    public int insert(Comment comment);

    public int update(Comment comment);

    public int delete(Integer id);

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
