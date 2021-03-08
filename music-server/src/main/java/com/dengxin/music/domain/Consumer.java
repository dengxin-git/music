package com.dengxin.music.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Consumer implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private Byte sex;
    private String phoneNum;
    private String email;
    private Date birthday;
    private String introduction;
    private String location;
    private String avator;
    private Date createTime;
    private Date updateTime;

}
