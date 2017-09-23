package com.course.design.observer.demo;

import java.util.List;

/**
 * 观察者模式
 * 定义对象一对多的依赖，当一个对象改变状态时，他的所有依赖都会受到通知并更新
 * 饭店接口，可对客户进行注册，更新，以及通知客户新上菜品
 */
public interface Hotel {

    public boolean resignCustomer(Customer customer);
    public boolean deleteCustomer(Customer customer);
    public boolean updateCustomer(Customer customer);
    public List<Customer> getCustomers();

    public void notifyCustomer();
}
