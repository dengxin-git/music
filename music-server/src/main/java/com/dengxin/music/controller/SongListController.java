package com.dengxin.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.dengxin.music.domain.SongList;
import com.dengxin.music.service.SongListService;
import com.dengxin.music.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/songList")
public class SongListController {

    @Autowired
    private SongListService songListService;

    @PostMapping("/add")
    public Object addSongList(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        //trim()是防止其他人输入空格,用于去掉空格
        String title = request.getParameter("title").trim();
        String pic = request.getParameter("pic").trim();
        String introduction = request.getParameter("introduction").trim();
        String style = request.getParameter("style").trim();

        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);

        boolean flag = songListService.insert(songList);

        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"添加失败");

        return jsonObject;
    }

    @PostMapping("/update")
    public Object updateSongList(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        //trim()是防止其他人输入空格,用于去掉空格
        String id = request.getParameter("id").trim();
        String title = request.getParameter("title").trim();
        String introduction = request.getParameter("introduction").trim();
        String style = request.getParameter("style").trim();

        SongList songList = new SongList();
        songList.setId(Integer.parseInt(id));
        songList.setTitle(title);
        songList.setIntroduction(introduction);
        songList.setStyle(style);

        boolean flag = songListService.update(songList);

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
    public Object deleteSongList(HttpServletRequest request){
        //trim()是防止其他人输入空格,用于去掉空格
        String id = request.getParameter("id").trim();

        boolean flag = songListService.delete(Integer.parseInt(id));

        return flag;
    }
    @GetMapping("/selectByPrimaryKey")
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return songListService.selectByPrimaryKey(Integer.parseInt(id));
    }

    @GetMapping("/allSongList")
    public Object allSongList(HttpServletRequest request){
        return songListService.allSongList();
    }

    /*
    * 根据title精确查询
    * */
    @GetMapping("/songListOfTitle")
    public Object songListOfName(HttpServletRequest request){
        String title = request.getParameter("title").trim();
        return songListService.songListOfTitle(title);
    }

    /*
    * 根据title精确查询
    * */
    @GetMapping("/likeTitle")
    public Object likeTtle(HttpServletRequest request){
        String title = request.getParameter("title").trim();
        return songListService.likeTitle("%"+title+"%");
    }

    /*
     * 根据style精确查询
     * */
    @GetMapping("/likeStyle")
    public Object likeStyle(HttpServletRequest request){
        String style = request.getParameter("style").trim();
        return songListService.likeStyle("%"+style+"%");
    }


    /*
    * 上传歌单图片
    * */
    @PostMapping("/updateSongListPic")
    public Object updateSongListPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        //获取项目下的songListPic目录
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                        + System.getProperty("file.separator") + "songListPic";
        //如果文件路径不存在就新增该路径
        File fileExist = new File(filePath);
        if(!fileExist.exists()){
            fileExist.mkdir();
        }

        //实际文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/songListPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            SongList songList = new SongList();
            songList.setId(id);
            songList.setPic(storeAvatorPath);
            boolean flag = songListService.update(songList);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("pic",storeAvatorPath);
                return jsonObject;
            }
        } catch (IOException e) {
            e.printStackTrace();
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"上传失败"+e.getMessage());
        }finally {
            return jsonObject;
        }


    }
}
