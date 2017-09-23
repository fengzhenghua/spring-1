package com.course.design.observer.demo;

import java.util.List;

/**
 * Created by fzh on 2017/7/26.
 * 客户实体类，客户属性，客户受到菜品更新的消息
 */
public class Customer extends CustomerServ{

    String id;
    String level;
    String name;
    String balance;//余额

    public Customer(String id, String level, String name, String balance) {
        this.id = id;
        this.level = level;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public void updateFoods(List<String> foods)  {
        StringBuffer sbuffer  = new StringBuffer("更新的食物有");
        for (int i = 0; i <foods.size() ; i++) {
            sbuffer.append(foods.get(i)+",");
        }
        System.out.println("顾客["+name+"]你好，食物已更新，"+sbuffer.toString().substring(0,sbuffer.toString().length()-1));

    }
}
