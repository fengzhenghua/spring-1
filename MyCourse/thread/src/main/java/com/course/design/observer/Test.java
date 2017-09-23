package com.design.observer;

import com.course.design.observer.Observer;
import com.course.design.observer.StatisticsDisplay;
import com.course.design.observer.WeatherData;

/**
 * Created by fzh on 2017/7/25.
 */
public class Test {

    public static void main(String[] args) {

        WeatherData weatherData = new WeatherData();
        weatherData.setMeasurements(35,5,25);
        Observer observer1 = new StatisticsDisplay(weatherData);
        Observer observer2 = new StatisticsDisplay(weatherData);
        Observer observer3 = new StatisticsDisplay(weatherData);
        weatherData.removeObserver(observer1);
        System.out.println(weatherData.getObserver().size());


    }
}
