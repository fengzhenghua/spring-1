package com.tydic.unicom.crm.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Spring {
	public static ClassPathXmlApplicationContext init() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		context.start();
		return context;
	}
}
