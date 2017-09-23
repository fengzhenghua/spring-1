package com.course.design.observer;

/**
 * Created by fzh on 2017/7/25.
 * 主题接口
 */

public interface Subject {

    public void registerObserver(Observer o);

    public void removeObserver(Observer o);

    public void notifyObservers();

}
