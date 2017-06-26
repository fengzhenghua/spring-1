package com.tydic.unicom.test.spring;

import org.springframework.test.context.TestExecutionListeners;

@TestExecutionListeners({MockitoDependencyInjectionTestExecutionListener.class }) 
/**
 * 
 * <p></p>
 * @author heguoyong 2017年3月24日 下午4:11:52
 * @ClassName SpringContextUnitTestCase
 * @Description 单元测试用例基类
 * @version V1.0
 */
public abstract class SpringContextUnitTestCase extends SpringContextTestCase{
	
}
