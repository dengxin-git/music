package com.dengxin.music.mapper;

import com.dengxin.music.domain.Singer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface SingerMapper {
    public int insert(Singer singer);

    public int update(Singer singer);

    public int delete(Integer id);

    public Singer selectByPrimaryKey(Integer id);

    public List<Singer> allSinger();

    public List<Singer> singerOfName(String name);

    public List<Singer> singerOfSex(Integer sex);


}
