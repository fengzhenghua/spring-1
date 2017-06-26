package com.tydic.unicom.crawlerframe.util;

import java.io.InputStream;

public class FileUtil {

	public static void createpar(){
		
		InputStream p = Object.class.getResourceAsStream("/pro2.par");
		if(p == null){
			System.out.println("====================");
		}
//		t.pp();
	//	
//		InputStream p = Object.class.getResourceAsStream("/pro2.par");
//		Properties props = new Properties();
//		props.load(p);
//		System.out.println(props.get("mallpasswd"));
	//	
	//	
//		FileOutputStream fos = new FileOutputStream(Object.class.getResource("/pro2.par").getFile());
//		props.setProperty("mallpasswd", props.get("mallpasswd")+"1");
	//
//		// 保存，并加入注释
//		props.store(fos, "Update 'pov' 111111 value");
	}
	
	
	public void get(){
		
	}
	
	public void set(){
		
	}
}
