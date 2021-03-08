package com.dengxin.music.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/*
* 歌单歌曲
* */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListSong implements Serializable {

    private Integer id;  //主键
    private Integer songId;  //歌曲id
    private Integer songListId;  //歌单id
}
