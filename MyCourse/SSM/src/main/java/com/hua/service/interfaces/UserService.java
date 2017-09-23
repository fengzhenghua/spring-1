package com.hua.service.interfaces;

import com.hua.entity.User;

import java.util.List;

/**
 * Created by fengzhenghua on
 * 2017-08-19 18:30.
 */
public interface UserService {

    List<User> getUser(User user);

    boolean insert(User user);

    boolean update(User user);

    boolean delete(User user);
}
