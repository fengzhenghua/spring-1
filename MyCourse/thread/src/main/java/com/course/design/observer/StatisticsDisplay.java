package com.course.design.observer;

/**
 * Created by fzh on 2017/7/25.
 */
public class StatisticsDisplay implements Observer,DisplayElement  {

    private float temperature; // 温度
    private float humidity; // 湿度
    private Subject weatherData = null;

    public StatisticsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    @Override
    public void display() {
        System.out.println("Statistics: " + temperature + "F degrees and " + humidity + "% humidity");
    }
}
