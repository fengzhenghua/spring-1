package com.tydic.unicom.crm;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UocDubboStart {
	public static void main(String[] args) throws Exception
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext_uoc.xml" });
		context.start();
		while (true) {
			Thread.sleep(1000000l);
		}
		
	}
}
