package com.dengxin.music.service;

import com.dengxin.music.domain.Rank;


public interface RankService {

    public Boolean insert(Rank rank);

    /*查询是否登录*/
    public Rank selectConsumerByConsumerId(Integer consumerId);

    /*查总分*/
    public int selectScoreSum(Integer songListId);

    /*查多少人评价*/
    public int selectRankNum(Integer songListId);

    /*计算平均分*/
    public int rankOfSongListId(Integer songListId);


}
