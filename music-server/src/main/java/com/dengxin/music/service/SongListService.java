package com.dengxin.music.service;

import com.dengxin.music.domain.SongList;

import java.util.List;

public interface SongListService {

    public boolean insert(SongList songList);

    public boolean update(SongList songList);

    public boolean delete(Integer id);

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
