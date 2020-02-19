package com.anglewang.community.service;

import com.anglewang.community.mapper.UserMapper;
import com.anglewang.community.model.User;
import com.anglewang.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void insertOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers=userMapper.selectByExample(userExample);
        if(dbUsers.size()==0) {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtModified());
            userMapper.insert(user);
        }else{
            User dbUser=dbUsers.get(0);
            User updateUser=new User();
            //修改
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,userExample1);
        }
    }
}
