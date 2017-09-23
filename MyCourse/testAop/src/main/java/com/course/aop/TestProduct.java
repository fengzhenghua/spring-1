package com.course.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by fengzhenghua on
 * 2017-08-10 10:32.
 */
/*
 *第一种写法
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestProduct {

    //Autowire 与 @resource在此处都可以用
    @Resource
    private Product product;

    @Test
    public void testProduct(){
        product.productIphone7(10000,3);
    }
}
/**
 * 第二种写法
 */
@ContextConfiguration(locations = "classpath:applicationContext.xml")
class TestProduct1 extends AbstractTransactionalJUnit4SpringContextTests{

    private Product product;

    @Autowired
    public void setProduct(Product product) {
        this.product = product;
    }

    @Test
    public void testProduct1(){
        product.productIphone7(10000,3);
    }
}

