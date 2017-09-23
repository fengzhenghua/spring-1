package com.course.design.observer.demo;

import java.util.List;

/**
 * Created by fzh on 2017/7/26.
 * 客户抽象类，只关心新上菜品
 */
public abstract class CustomerServ {

    public abstract void updateFoods(List<String> foods);

}
