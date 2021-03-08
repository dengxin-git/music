package com.dengxin.music.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
/*
* 评价
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rank implements Serializable {
    private Integer id;
    private Integer songListId;
    private Integer consumerId;
    private Integer score;
}
