package com.hua.rest;

import com.hua.base.Message;
import com.hua.entity.User;
import com.hua.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by fengzhenghua on
 * 2017-08-19 19:30.
 */
@Controller
@RequestMapping(value = "user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "getUsers",method = RequestMethod.POST)
    @ResponseBody
    public Message getUsers(User user){
        Message message = new Message();
        List<User> users = userService.getUser(user);
        if(users!=null && users.size()>0){
            message.setContent("获取user成功");
            message.setRespCode("0000");
            message.addArgs("users",users);
            return message;
        }
        message.setContent("获取user失败");
        message.setContent("0001");
        return message;
    }

    @RequestMapping(value = "insertUser",method = RequestMethod.POST)
    @ResponseBody
    public Message insertUser(User user){
        logger.info("insertUser入参:id="+user.getId()+" name="+user.getName()+" dept="+user.getWebsite()+" dept="+user.getDept()
        +" phone="+user.getPhone());
        Message message = new Message();
        //boolean flag = false;
        try{
            userService.insert(user);

        }catch (Exception e){
            e.printStackTrace();
            message.setRespCode("0000");
            message.setContent("插入失败");
            return message;
        }
        message.setRespCode("0000");
        message.setContent("插入成功");
        return message;
    }
    @RequestMapping(value = "deleteUser",method = RequestMethod.POST)
    @ResponseBody
    public Message deleteUser(User user){
        Message message = new Message();
        try{
            userService.delete(user);
        }catch (Exception e){
            e.printStackTrace();
            message.setRespCode("00001");
            message.setContent("删除失败");
            return message;
        }
        message.setRespCode("0000");
        message.setContent("删除成功");
        return message;
    }
}
