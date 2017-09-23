package com.design.strategy;

/**
 * Created by fzh on 2017/7/29.
 */
public abstract class AbstractDuck implements Duck{

    private Voice voice;


    @Override
    public void display() {
        System.out.println("我是白头鸭");
    }

    public abstract void fly();

    public  void voice(){

        voice.duckVoice();
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }
}
