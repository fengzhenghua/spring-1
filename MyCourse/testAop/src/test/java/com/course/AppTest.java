package com.course;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 

{
    /*
      * 判断一个数字是否为素数
      */
    public static boolean isShuSu(int num) {

        boolean flag = true;
        if(num==1|| num==2) {
            return true;
        }
        for(int i=2;i<Math.sqrt(num);i++) {
            if((num%i) == 0) {
                flag = false;//一旦取余为等于0，则不为素数
                break;
            }
        }
        //不为素数时返回false,当为素数时返回true;
        return flag;

    }

    public static void main(String[] args) {

        for(int j=1000;j<2000;j++) {
            if(isShuSu(j)) {
                System.out.print(j + " ");
            }
        }
    }
}
