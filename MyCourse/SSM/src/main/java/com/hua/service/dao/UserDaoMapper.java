package com.hua.service.dao;

import com.hua.entity.User;

import java.util.List;

/**
 * Created by fengzhenghua on
 * 2017-08-19 19:14.
 */
public interface UserDaoMapper {

    List<User> getUsers(User User);

    boolean insertUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(User user);
}
