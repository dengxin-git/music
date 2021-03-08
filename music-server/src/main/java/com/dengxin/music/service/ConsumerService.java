package com.dengxin.music.service;

import com.dengxin.music.domain.Consumer;

import java.util.List;

public interface ConsumerService {

    public boolean insert(Consumer consumer);

    public boolean update(Consumer consumer);

    public boolean delete(Integer id);

    public Consumer selectByPrimaryKey(Integer id);

    public List<Consumer> allConsumer();

    /*
     * 验证密码是否正确
     * */
    public boolean verifyPassword(String username,String password);

    public Consumer getByUsername(String username);

}
