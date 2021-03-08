package com.dengxin.music.mapper;

import com.dengxin.music.domain.SongList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface SongListMapper {
    public int insert(SongList songList);

    public int update(SongList songList);

    public int delete(Integer id);

    public SongList selectByPrimaryKey(Integer id);

    /*
    * 查询所有歌单
    * */
    public List<SongList> allSongList();

    /*
    * 根据标题精确查询歌单列表
    * */
    public List<SongList> songListOfTitle(String title);

    /*
    * 根据标题模糊查询歌单列表
    * */
    public List<SongList> likeTitle(String title);

    /*
    * 根据风格模糊查询歌单列表
    * */
    public List<SongList> likeStyle(String style);
}
