package com.course.design.observer.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fzh on 2017/7/26.
 */
public class TestMenuMessage {

    public static void main(String[] args) {
        MenuImpl menu = new MenuImpl();
        String foods1 = "毛血旺";
        String foods2 = "干锅";
        String foods3 = "猪肝";
        List<String> list = new ArrayList<String>();
        list.add(foods1);
        list.add(foods2);
        list.add(foods3);
        Customer customer1 = new Customer("2","3","小明","$55");
        Customer customer2 = new Customer("2","2","小红","$65");
        menu.resignCustomer(customer1);
        menu.resignCustomer(customer2);
        menu.addFoods(list);
    }

}
