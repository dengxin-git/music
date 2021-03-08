package com.dengxin.music.mapper;

import com.dengxin.music.domain.Consumer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface ConsumerMapper {
    public int insert(Consumer consumer);

    public int update(Consumer consumer);

    public int delete(Integer id);

    public Consumer selectByPrimaryKey(Integer id);

    public List<Consumer> allConsumer();
    /*
    根据用户名查询
    */
    public List<Consumer> consumerOfName(String name);


    /*
    * 验证密码是否正确
    * */
    public int verifyPassword(String username,String password);

    public Consumer getByUsername(String username);


}
