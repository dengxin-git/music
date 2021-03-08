package com.dengxin.music.service;


import com.dengxin.music.domain.Singer;

import java.util.List;


public interface SingerService {

    public boolean insert(Singer singer);

    public boolean update(Singer singer);

    public boolean delete(Integer id);

    public Singer selectByPrimaryKey(Integer id);

    public List<Singer> allSinger();

    public List<Singer> singerOfName(String name);

    public List<Singer> singerOfSex(Integer sex);
}
