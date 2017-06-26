package com.tydic.unicom.service.cache;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tydic.unicom.service.cache.po.*;
import com.tydic.unicom.service.cache.service.interfaces.*;


public class DubboStart {
	public static void main(String[] args) throws InterruptedException
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "config/spring/contextLoaderListener.xml" });
		context.start();
	
		while (true) {
			Thread.sleep(1000000l);
		}		
	}
}
