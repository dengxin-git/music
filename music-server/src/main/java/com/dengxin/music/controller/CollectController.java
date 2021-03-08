package com.dengxin.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.dengxin.music.domain.Collect;
import com.dengxin.music.service.CollectService;
import com.dengxin.music.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    @PostMapping("/add")
    public Object addCollect(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        //trim()是防止其他人输入空格,用于去掉空格
        String userId = request.getParameter("userId");
        String type = request.getParameter("type");
        String songId = request.getParameter("songId");

        if(songId==null||songId.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"没有该歌曲");
            return jsonObject;
        }
        if(collectService.existSongId(Integer.parseInt(userId),Integer.parseInt(songId))){
            jsonObject.put(Consts.CODE,2);
            jsonObject.put(Consts.MSG,"已收藏该歌曲");
            return jsonObject;
        }

        Collect collect = new Collect();
        collect.setUserId(Integer.parseInt(userId));
        collect.setType(new Byte(type));
        collect.setSongId(Integer.parseInt(songId));

        boolean flag = collectService.insert(collect);

        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"收藏成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"收藏失败");
        return jsonObject;
    }


//    @GetMapping("/delete")
//    public Object deleteCollect(HttpServletRequest request){
//        //trim()是防止其他人输入空格,用于去掉空格
//        String id = request.getParameter("id").trim();
//
//        boolean flag = collectService.delete(Integer.parseInt(id));
//
//        return flag;
//    }


    @GetMapping("/allCollect")
    public Object allCollect(HttpServletRequest request){
        return collectService.allCollect();
    }

    @GetMapping("/collectOfUserId")
    public Object collectOfUserId(HttpServletRequest request){
        String userId = request.getParameter("userId").trim();
        return collectService.collectOfUserId(Integer.parseInt(userId));
    }

    @GetMapping("/delete")
    public Object deleteCollectByUserIdSongId(HttpServletRequest request){
        //trim()是防止其他人输入空格,用于去掉空格
        String userId = request.getParameter("userId");
        String songId = request.getParameter("songId");

        boolean flag = collectService.deleteByUserIdSongId(Integer.parseInt(userId),Integer.parseInt(songId));

        return flag;
    }

}
