package com.dengxin.music.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SongList implements Serializable {
    private Integer id;
    private String pic;
    private String title;
    private String introduction;
    private String style;
}
