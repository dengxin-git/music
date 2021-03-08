package com.dengxin.music.mapper;

import com.dengxin.music.domain.Singer;
import com.dengxin.music.domain.Song;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface SongMapper {
    public int insert(Song song);

    public int update(Song song);

    public int delete(Integer id);

    public Song selectByPrimaryKey(Integer id);

    public List<Song> allSong();

    public List<Song> songOfName(String name);

    /*
    *根据歌名模糊查询
    * */
    public List<Song> likeSongOfName(String name);

    public List<Song> songOfSingerId(Integer singerId);


}
