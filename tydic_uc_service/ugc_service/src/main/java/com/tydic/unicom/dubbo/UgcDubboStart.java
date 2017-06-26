package com.tydic.unicom.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UgcDubboStart {
	public static void main(String[] args) throws Exception
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		context.start();
		while (true) {
			Thread.sleep(1000000l);
		}
		
	}
}
