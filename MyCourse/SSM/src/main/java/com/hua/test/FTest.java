package com.hua.test;

import com.hua.common.PropertiesVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by fengzhenghua on
 * 2017-08-21 18:53.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class FTest {

    @Autowired
    @Qualifier("propertiesVo")
    private PropertiesVo propertiesVo;

    @Test
    public void getProperties(){
        String test = propertiesVo.getTestString();
        System.out.println(test);
    }
}