package com.tydic.unicom.test.spring;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(JUnit4TestClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:application-test.xml"})
/**
 * 
 * <p></p>
 * @author heguoyong 2017年3月24日 下午4:11:07
 * @ClassName SpringContextTestCase
 * @Description spring测试用例基类
 * @version V1.0
 */
public abstract class SpringContextTestCase extends AbstractJUnit4SpringContextTests {}
