package com.dengxin.music.service;

import com.dengxin.music.domain.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectService {

    public boolean insert(Collect collect);

    public boolean delete(Integer id);

    public boolean deleteByUserIdSongId(@Param("userId")Integer userId,@Param("songId") Integer songId);

    public List<Collect> allCollect();

    /**
     *查询某个用户的收藏
     */
    public List<Collect> collectOfUserId(Integer userId);

    /**
     * 查询某个用户是否已经收藏过该歌曲
     */
    public boolean existSongId(@Param("userId")Integer userId, @Param("songId") Integer songId);

}
