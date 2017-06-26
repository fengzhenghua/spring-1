package com.tydic.unicom.test.spring;

import java.io.FileNotFoundException;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年3月24日 上午10:30:40
 * @ClassName JUnit4ClassRunner
 * @Description 集成springtest和log4j
 * @version V1.0
 */
public class JUnit4TestClassRunner  extends SpringJUnit4ClassRunner {
	static {
	      try {
	        Log4jConfigurer.initLogging("classpath:log4j.properties");
	      } catch (FileNotFoundException ex) {
	        System.err.println("Cannot Initialize log4j");
	      }
	    }
	    public JUnit4TestClassRunner(Class<?> clazz) throws InitializationError {
	      super(clazz);
	    }
}
