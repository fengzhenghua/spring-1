package com.design.strategy;

/**
 * Created by fzh on 2017/7/29.
 */
public class TestStrategy {

    public static void main(String[] args) {

        DuckImpl duck = new DuckImpl();
        duck.fly();
        duck.voice();
        duck.display();

    }
}
