package com.dengxin.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.dengxin.music.domain.Rank;
import com.dengxin.music.service.RankService;
import com.dengxin.music.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private RankService rankService;

    @PostMapping("/add")
    public Object add(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String songListId = request.getParameter("songListId");
        String consumerId = request.getParameter("consumerId");
        String score = request.getParameter("score");

        //判断是否已经评价
        if(consumerId!=null){
            Rank rank = rankService.selectConsumerByConsumerId(Integer.parseInt(consumerId));
            if(rank!=null){
                jsonObject.put(Consts.CODE,3);
                jsonObject.put(Consts.MSG,"您已经评分过了");
                jsonObject.put("score",rank.getScore());
                return jsonObject;
            }
        }

        Rank rank = new Rank();
        rank.setSongListId(Integer.parseInt(songListId));
        rank.setConsumerId(Integer.parseInt(consumerId));
        rank.setScore(Integer.parseInt(score));

        boolean  flag = rankService.insert(rank);
        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"评价成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"评价失败");
        return jsonObject;
    }

    @GetMapping("/average")
    public Object rankOfSongListId(HttpServletRequest request){
        String songListId = request.getParameter("songListId");
        return rankService.rankOfSongListId(Integer.parseInt(songListId));
    }
}
