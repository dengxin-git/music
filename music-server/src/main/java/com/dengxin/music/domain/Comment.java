package com.dengxin.music.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/*
* 评论
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment implements Serializable {
    private Integer id;
    private Integer userId;
    /*评论类型*/
    private Byte type;
    private Integer songId;
    private Integer songListId;
    private Date createTime;
    /*评论类容*/
    private String content;
    /*评论点赞数*/
    private Integer up;
}
