package com.course.design.observer.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fzh on 2017/7/26.
 * 菜单更新后，告知顾客菜品已上新
 */
public class MenuImpl implements Hotel{

    private List<Customer> customers = new ArrayList<Customer>();
    private List<String> foods;

    @Override
    public boolean resignCustomer(Customer customer) {
        return customers.add(customer);
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        int index = customers.indexOf(customer);
        customers.remove(index);
        return true;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public List<Customer> getCustomers() {
        return null;
    }
    //通知顾客
    public void notifyCustomer(){
        for(Customer customer : customers){
            customer.updateFoods(foods);
        }
    }
    //添加菜品
    public void addFoods(List<String> foods){
        this.foods = foods;
        changeFoods();
    }

    //菜品更变
    public void changeFoods(){
        notifyCustomer();
    }
}
