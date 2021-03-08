package com.dengxin.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.dengxin.music.domain.Consumer;
import com.dengxin.music.service.ConsumerService;
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
import java.util.List;

/*
* 前端用户
* */


@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @PostMapping("/add")
    public Object addConsumer(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        //trim()是防止其他人输入空格,用于去掉空格
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String sex = request.getParameter("sex").trim();
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String birthday = request.getParameter("birthday").trim();
        String introduction = request.getParameter("introduction").trim();
        String location = request.getParameter("location").trim();
        String avator = request.getParameter("avator").trim();  //头像地址

        if(username==null||username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名不能为空");
            return jsonObject;
        }
        if(password==null||password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"密码不能为空");
            return jsonObject;
        }

        Consumer is_exist_consumer = consumerService.getByUsername(username);
        if(is_exist_consumer!=null){
            jsonObject.put(Consts.CODE,3);
            jsonObject.put(Consts.MSG,"用户名已存在");
            return jsonObject;
        }


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(new Byte(sex));
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirthday(birthDate);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        consumer.setAvator(avator);

        boolean flag = consumerService.insert(consumer);

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
    public Object updateConsumer(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        //trim()是防止其他人输入空格,用于去掉空格
        String id = request.getParameter("id").trim();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String sex = request.getParameter("sex").trim();
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String birthday = request.getParameter("birthday").trim();
        String introduction = request.getParameter("introduction").trim();
        String location = request.getParameter("location").trim();

        if(username==null||username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名不能为空");
            return jsonObject;
        }
        if(password==null||password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"密码不能为空");
            return jsonObject;
        }


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Consumer consumer = new Consumer();
        consumer.setId(Integer.parseInt(id));
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(new Byte(sex));
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirthday(birthDate);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);

        boolean flag = consumerService.update(consumer);

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
    public Object deleteConsumer(HttpServletRequest request){
        //trim()是防止其他人输入空格,用于去掉空格
        String id = request.getParameter("id").trim();

        boolean flag = consumerService.delete(Integer.parseInt(id));

        return flag;
    }
    @GetMapping("/selectByPrimaryKey")
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();
        return consumerService.selectByPrimaryKey(Integer.parseInt(id));
    }

    @GetMapping("/allConsumer")
    public Object allConsumer(HttpServletRequest request){
        return consumerService.allConsumer();
    }

    /*
    * 更新用户图片
    * */
    @PostMapping("/updateConsumerPic")
    public Object updateConsumerPic(@RequestParam("file")MultipartFile avatorFile,@RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"文件上传失败");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + avatorFile.getOriginalFilename();
        //获取项目下的avatorImages目录
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "avatorImages";

        //如果文件路径不存在就新增该路径
        File fileExist = new File(filePath);
        if(!fileExist.exists()){
            fileExist.mkdir();
        }

        //实际文件地址
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //存储到数据库里的相对文件地址
        String storeAvatorPath = "/avatorImages/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvator(storeAvatorPath);
            boolean flag = consumerService.update(consumer);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"上传成功");
                jsonObject.put("avator",storeAvatorPath);
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


    @PostMapping("/login")
    public Object login(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        //trim()是防止其他人输入空格,用于去掉空格

        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        if(username==null||username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"用户名不能为空");
            return jsonObject;
        }
        if(password==null||password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"密码不能为空");
            return jsonObject;
        }

        boolean flag = consumerService.verifyPassword(username,password);

        if(flag){
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"登录成功");
            jsonObject.put("userMsg",consumerService.getByUsername(username));
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"登录失败");

        return consumerService.verifyPassword(username,password);
    }

}
