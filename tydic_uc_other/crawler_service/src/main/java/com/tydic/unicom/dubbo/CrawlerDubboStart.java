package com.tydic.unicom.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tydic.unicom.crawler.common.CrawlerInit;
import com.tydic.unicom.crawler.common.ProParamVo;
import com.tydic.unicom.crawler.common.SysPar;

public class CrawlerDubboStart {
	public static void main(String[] args) throws Exception
	{
		//注意配置文件的文件编码是UTF-8
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		SysPar.ppvo = (ProParamVo) context.getBean("proParamVo");
		context.start();
		
		CrawlerInit crawlerInit = (CrawlerInit) context.getBean("crawlerInit");
		crawlerInit.init();
		
		while (true) {
			Thread.sleep(1000000l);
		}
		
	}
}
