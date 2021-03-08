package com.dengxin.music.mapper;

import com.dengxin.music.domain.Collect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface CollectMapper {
    public int insert(Collect collect);

    public int delete(Integer id);

    /**
     *根据用户id和歌曲id
     */
    public int deleteByUserIdSongId(@Param("userId")Integer userId,@Param("songId") Integer songId);

    public List<Collect> allCollect();

    /**
     *查询某个用户的收藏
     */
    public List<Collect> collectOfUserId(Integer userId);

    /**
     * 查询某个用户是否已经收藏过该歌曲
     */
    public int existSongId(@Param("userId")Integer userId,@Param("songId") Integer songId);


}
