package com.tydic.unicom.crm.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 反射添加字符串
 */
public class Reflex {
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(33);
		list.add(444);
		try {
			Method m = list.getClass().getDeclaredMethod("add",Object.class);
			m.invoke(list, "这是一个字符串");
			System.out.println(list.get(3));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			System.out.println("未找到方法");
			e.printStackTrace();
		}
		User user = new User();
		user.setAge("22");
		user.setPassword("211");
		user.setUserName("name");
		Map<String,Object> map =convert2map(user);
		System.out.println(map.get("age"));
		System.out.println(map.get("userName"));
		System.out.println(map.get("password"));
//		
		 //声明Integer的泛型ArrayList对象，并放入Integer实例  
//        ArrayList<Integer> intList = new ArrayList<>();  
//        intList.add(new Integer(5));  
//        intList.add(7);  
//        //定义需要被加入list对象的字符串  
//        String str = "abc";  
//        //通过反射获取list对象运行时的add方法，此时该方法已经被擦除泛型  
//        Method m = intList.getClass().getMethod("add", java.lang.Object.class);  
//        //调用泛型方法加入String对象  
//        m.invoke(intList, str);  
//        //打印结果：[5, 7, abc]  
//        System.out.println(intList);  
//        for(Object obj:intList)  
//            System.out.println(obj.getClass());  
        /*打印结果： 
        class java.lang.Integer 
        class java.lang.Integer 
        class java.lang.String*/  
    }  
	//Map转javabean
	public static Map convert2map(Object o) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Field[] fds = o.getClass().getDeclaredFields();
		Map<String,Object> map = new HashMap<String,Object>();
		for(Field fd : fds){
			Method md = o.getClass().getMethod("get"+fd.getName().substring(0,1).toUpperCase()+fd.getName().substring(1));
			Object obj = md.invoke(o);
			map.put(fd.getName(), obj);
		}
		return map;
	}
	
}
