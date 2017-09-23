package com.design.strategy;

/**
 * Created by fzh on 2017/7/29.
 */
public class DuckImpl extends AbstractDuck {

    public DuckImpl() {

        super();
        super.setVoice(new WhiteDuck());
    }

    @Override
    public void fly() {
        System.out.println("我可以飞");
    }


}
