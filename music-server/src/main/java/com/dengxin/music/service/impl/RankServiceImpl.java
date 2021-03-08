package com.dengxin.music.service.impl;

import com.dengxin.music.domain.Rank;
import com.dengxin.music.mapper.RankMapper;
import com.dengxin.music.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RankMapper rankMapper;

    @Override
    public Boolean insert(Rank rank) {
        return rankMapper.insert(rank)>0;
    }

    @Override
    public Rank selectConsumerByConsumerId(Integer consumerId) {
        return rankMapper.selectConsumerByConsumerId(consumerId);
    }

    @Override
    public int selectScoreSum(Integer songListId) {
        return rankMapper.selectScoreSum(songListId);
    }

    @Override
    public int selectRankNum(Integer songListId) {
        return rankMapper.selectRankNum(songListId);
    }

    /*计算平均分*/
    @Override
    public int rankOfSongListId(Integer songListId) {
        int rankNum = rankMapper.selectRankNum(songListId);
        if(rankNum==0){
            return 5;
        }
        return rankMapper.selectScoreSum(songListId)/rankNum;
    }
}
