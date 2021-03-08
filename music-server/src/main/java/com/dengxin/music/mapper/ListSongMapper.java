package com.dengxin.music.mapper;

import com.dengxin.music.domain.ListSong;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface ListSongMapper {
    public int insert(ListSong listSong);

    public int update(ListSong listSong);

    public int delete(Integer id);

    public int deleteBySongIdSongListId(Integer songId,Integer songListId);

    public ListSong selectByPrimaryKey(Integer id);

    /*
    * 查询所有歌单歌曲
    * */
    public List<ListSong> allListSong();

    /*
    * 根据歌单id查询所有歌曲
    * */
    public List<ListSong> listSongOfSongListId(Integer songListId);


}
