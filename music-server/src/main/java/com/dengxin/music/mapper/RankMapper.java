package com.dengxin.music.mapper;

import com.dengxin.music.domain.Rank;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;



@Repository
@Mapper
public interface RankMapper {
    public int insert(Rank rank);

    /*查总分*/
    public int selectScoreSum(Integer songListId);

    /*查多少人评价*/
    public int selectRankNum(Integer songListId);

    /*查询该用户是否评价*/
    public Rank selectConsumerByConsumerId(Integer consumerId);

}
