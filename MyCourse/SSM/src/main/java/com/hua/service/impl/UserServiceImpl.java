package com.hua.service.impl;

import com.hua.entity.User;
import com.hua.service.dao.UserDaoMapper;
import com.hua.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fengzhenghua on
 * 2017-08-19 18:33.
 */
@Service("UserService")
public class UserServiceImpl implements UserService{

    @Autowired(required = false)
    private UserDaoMapper userDaoMapper;

    @Override
    public List<User> getUser(User user) {
        return  userDaoMapper.getUsers(user);
    }

    @Override
    public boolean insert(User user) {
        return userDaoMapper.insertUser(user);
    }

    @Override
    public boolean update(User user) {
        return userDaoMapper.updateUser(user);
    }

    @Override
    public boolean delete(User user) {
        return userDaoMapper.deleteUser(user);
    }


}
