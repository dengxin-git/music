package com.dengxin.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.dengxin.music.domain.Comment;
import com.dengxin.music.service.CommentService;
import com.dengxin.music.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public Object addComment(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        //trim()是防止其他人输入空格,用于去掉空格
        String userId = request.getParameter("userId");
        String type = request.getParameter("type");
        String songId = request.getParameter("songId");
        String songListId = request.getParameter("songListId");
        System.out.println(songListId);
        String content = request.getParameter("content").trim();
       // String up = request.getParameter("up").trim();

        Comment comment = new Comment();
        comment.setUserId(Integer.parseInt(userId));
        comment.setType(new Byte(type));
        if(new Byte(type)==0){
            comment.setSongId(Integer.parseInt(songId));
            System.out.println(songId);
        }else {
            comment.setSongListId(Integer.parseInt(songListId));
        }
        comment.setContent(content);
        //comment.setUp(Integer.parseInt(up));

        boolean flag = commentService.insert(comment);

        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"评论成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"评论失败");
        return jsonObject;
    }

    @PostMapping("/update")
    public Object updateComment(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        //trim()是防止其他人输入空格,用于去掉空格
        String id = request.getParameter("id").trim();
        String userId = request.getParameter("userId").trim();
        String type = request.getParameter("type").trim();
        String songId = request.getParameter("songId").trim();
        String songListId = request.getParameter("songListId").trim();
        String content = request.getParameter("content").trim();
        //String up = request.getParameter("up").trim();

        Comment comment = new Comment();
        comment.setId(Integer.parseInt(id));
        comment.setUserId(Integer.parseInt(userId));
        comment.setType(new Byte(type));
        if(songId==null&&songId.equals("")){
            songId = null;
        }else {
            comment.setSongId(Integer.parseInt(songId));
        }

        if(songListId==null&&songListId.equals("")){
            songListId = null;
        }else {
            comment.setSongListId(Integer.parseInt(songListId));
        }
        comment.setContent(content);
        //comment.setUp(Integer.parseInt(up));

        boolean flag = commentService.update(comment);

        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"修改失败");

        return jsonObject;
    }
    @GetMapping("/delete")
    public Object deleteComment(HttpServletRequest request){
        //trim()是防止其他人输入空格,用于去掉空格
        String id = request.getParameter("id").trim();

        boolean flag = commentService.delete(Integer.parseInt(id));

        return flag;
    }
    @GetMapping("/selectByPrimaryKey")
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return commentService.selectByPrimaryKey(Integer.parseInt(id));
    }

    @GetMapping("/allComment")
    public Object allComment(HttpServletRequest request){
        return commentService.allComment();
    }

    @GetMapping("/commentOfSongId")
    public Object commentOfSongId(HttpServletRequest request){
        String songId = request.getParameter("songId").trim();
        return commentService.commentOfSongId(Integer.parseInt(songId));
    }

    @GetMapping("/commentOfSongListId")
    public Object commentOfSongListId(HttpServletRequest request){
        String songListId = request.getParameter("songListId").trim();
        return commentService.commentOfSongListId(Integer.parseInt(songListId));
    }


    @PostMapping("/like")
    public Object like(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        //trim()是防止其他人输入空格,用于去掉空格
        String id = request.getParameter("id").trim();
        String up = request.getParameter("up").trim();

        Comment comment = new Comment();
        comment.setId(Integer.parseInt(id));
        comment.setUp(Integer.parseInt(up));

        boolean flag = commentService.update(comment);

        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"点赞成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"点赞失败");

        return jsonObject;
    }

}
