package com.dengxin.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.dengxin.music.domain.Singer;
import com.dengxin.music.domain.Song;
import com.dengxin.music.service.SongService;
import com.dengxin.music.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping("/add")
    public Object addSinger(HttpServletRequest request, @RequestParam("file") MultipartFile multipartFile) {
        JSONObject jsonObject = new JSONObject();
        String singerId = request.getParameter("singerId").trim();
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();
        String pic = "/img/songPic/tubiao.jpg";
        String lyric = request.getParameter("lyric").trim();
        //上传歌曲文件
        if (multipartFile.isEmpty()) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "歌曲上传失败");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
        //获取项目下的singerPic目录
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        //如果文件路径不存在就新增该路径
        File fileExist = new File(filePath);
        if (!fileExist.exists()) {
            fileExist.mkdir();
        }

        //实际文件地址
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        //存储到数据库里的相对文件地址
        String storeUrlPath = "/song/" + fileName;
        try {
            multipartFile.transferTo(dest);
            Song song = new Song();
            song.setSingerId(Integer.parseInt(singerId));
            song.setName(name);
            song.setIntroduction(introduction);
            song.setPic(pic);
            song.setLyric(lyric);
            song.setUrl(storeUrlPath);
            boolean flag = songService.insert(song);
            if (flag) {
                jsonObject.put(Consts.CODE, 1);
                jsonObject.put(Consts.MSG, "保存成功");
                jsonObject.put("avator", storeUrlPath);
                return jsonObject;
            }
        } catch (IOException e) {
            e.printStackTrace();
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "保存失败" + e.getMessage());
        } finally {
            return jsonObject;
        }

    }


    /*
     * 根据歌手id查询歌曲
     * */
    @GetMapping("/singer/detail")
    public Object songOfSingerId(HttpServletRequest request) {
        String singerId = request.getParameter("singerId");
        return songService.songOfSingerId(Integer.parseInt(singerId));
    }


    @PostMapping("/update")
    public Object updateSong(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        //trim()是防止其他人输入空格,用于去掉空格
        String id = request.getParameter("id").trim();
        String name = request.getParameter("name").trim();
        String introduction = request.getParameter("introduction").trim();
        String lyric = request.getParameter("lyric").trim();

        Song song = new Song();
        song.setId(Integer.parseInt(id));
        song.setName(name);
        song.setIntroduction(introduction);
        song.setLyric(lyric);
        boolean flag = songService.update(song);
        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "修改成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "修改失败");
        return jsonObject;
    }

    @GetMapping("/delete")
    public Object deleteSong(HttpServletRequest request) {
        //trim()是防止其他人输入空格,用于去掉空格
        String id = request.getParameter("id").trim();

        String fileName = songService.selectByPrimaryKey(Integer.parseInt(id)).getUrl();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator");
        //实际文件地址
        File dest = new File(filePath + fileName);
        if (dest.isFile() && dest.exists()) {
            dest.delete();
        }
        boolean flag = songService.delete(Integer.parseInt(id));

        return flag;
    }

    @PostMapping("/updateSongPic")
    public Object updateSongPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        //获取项目下的singerPic目录
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
                + System.getProperty("file.separator") + "songPic";
        //如果文件路径不存在就新增该路径
        File fileExist = new File(filePath);
        if(!fileExist.exists()){
            fileExist.mkdir();
        }

        //实际文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/img/songPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setPic(storeAvatorPath);
            boolean flag = songService.update(song);
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
    @PostMapping("/updateSongUrl")
    public Object updateSongUrl(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        //获取项目下的song目录
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        //如果文件路径不存在就新增该路径
        File fileExist = new File(filePath);
        if(!fileExist.exists()){
            fileExist.mkdir();
        }

        //实际文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/song/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setUrl(storeAvatorPath);
            boolean flag = songService.update(song);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("song",storeAvatorPath);
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

    /*
     * 根据歌手id查询歌曲
     * */
    @GetMapping("/detail")
    public Object detail(HttpServletRequest request) {
        String songId = request.getParameter("songId");
        return songService.selectByPrimaryKey(Integer.parseInt(songId));
    }

    /*
     * 根据歌曲名字查询歌曲
     * */
    @GetMapping("/songOfSongName")
    public Object songOfSongName(HttpServletRequest request) {
        String songName = request.getParameter("songName");
        return songService.songOfName(songName);
    }
    /*
     * 根据歌曲名字模糊歌曲
     * */
    @GetMapping("/likeSongOfSongName")
    public Object likeSongOfSongName(HttpServletRequest request) {
        String songName = request.getParameter("songName");
        return songService.likeSongOfName(songName);
    }

    @GetMapping("/allSong")
    public Object allSong(HttpServletRequest request) {
        return songService.allSong();
    }
}
