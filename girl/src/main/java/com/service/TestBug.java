package com.service;

/**
 * Created by fzh on 2017/7/23.
 */
public class TestBug {

    public static void main(String[] args){

        int a = 0;
        int b = 1;
        int c= a+1;
        int d = b+1;
        System.out.print(a+b+c+d);
    }
}
