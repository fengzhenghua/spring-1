package com.course.aop;

import org.springframework.stereotype.Component;

/**
 * Created by fengzhenghua on
 * 2017-08-10 10:14.
 */
@Component
public class Product {

    public String productIphone7(int numbers ,int days){

        System.out.println("生产Iphone7中...");
        String str = "生产了" + numbers + "台iphone7" + "花了" + days + "天" ;
        return str;
    }

}
