package com.hua.test.ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * Created by fengzhenghua on
 * 2017-08-21 19:31.
 */
public class AnnatationApplicationContext {

    @Test
    public void getBean(){
        //通过带有@Configuration注解的pojo(Beans)装载Bean配置
        ApplicationContext context = new AnnotationConfigApplicationContext(Beans.class);
        Car car = context.getBean("car",Car.class);
        assertNotNull(car);
        System.out.println(car.getBrand());
        System.out.println(car.getMapSpeed());
    }
}
