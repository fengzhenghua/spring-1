package com.hua.test.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by fengzhenghua on
 * 2017-08-21 19:26.
 */
@ContextConfiguration
public class Beans {

    @Bean(name = "car")
    public Car buildCar(){
        Car car = new Car();
        car.setBrand("美国");
        car.setMapSpeed("400");
        return car;
    }
}
