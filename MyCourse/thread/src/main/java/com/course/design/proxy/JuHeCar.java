package com.course.design.proxy;

/**
 * Created by fzh on 2017/7/28.
 */
public class JuHeCar implements Movable{

    private Car car;

    public JuHeCar(Car car) {
        this.car = car;
    }

    @Override
    public void move() throws InterruptedException {
        car.move();
    }

}
