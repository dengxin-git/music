package com.dengxin.music.service.impl;

import com.dengxin.music.mapper.AdminMapper;
import com.dengxin.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean verifyPassword(String username, String password) {

        int isExist = adminMapper.verifyPassword(username, password);

        return isExist>0;
    }
}
