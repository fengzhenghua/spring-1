package com.tydic.unicom.crm.Test;

public class Test {

	public static void main(String[] args) {

		String str = "";
		
		
		str = str.replace("`", "").replaceAll("'\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}'", "sysdate");
		
		System.out.println(str);
		
		System.out.println(System.getProperty("user.dir"));    
		
		
		str = "2017-02-23 14:31:13";
		
		System.out.println(str.replaceAll("[^0-9]", ""));
	}

}
