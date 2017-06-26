package com.tydic.unicom.crawler.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 基本的视图和数据库bean，主要方法bean到MAP
 * @author xx
 *
 */
public class BaseVoPo implements Serializable{
	private static final long serialVersionUID = 1L;
	private HashMap parmap;
	
	public void setParmap(HashMap map){
		parmap = map;
	}
	
	public HashMap getParmap(){
		return parmap;
	}
	
	public void addParmap(String k,Object obj){
		if(parmap==null){
			parmap = new HashMap();
		}
		parmap.put(k, obj);
	}
	public String getParmapItem(String k){
		
		if(parmap==null){
			return "";
		}
		if(parmap.get(k) == null)
			return "";
		return SysUtil.getStr(parmap.get(k));
	}
	/**
	 * 转换bean到MAP
	 * @return
	 */
	public Map<String,Object> convertToMap() {
		Map<String,Object> map = new HashMap<String,Object>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String filedName = fields[i].getName();
//			System.out.println("修饰符:  " + Modifier.toString(fields[i].getModifiers()) + "    " +fields[i].getModifiers());
			
			//修饰符:  private static final    26
			//修饰符:  public static final    25
			//这种常量参数就不需要打印
			if(fields[i].getModifiers() ==25 || fields[i].getModifiers()==26)
				continue;
			
			
			if("serialVersionUID".equals(filedName)){
				continue;
			}
			StringBuilder sb = new StringBuilder("get");
			sb.append(filedName.substring(0, 1).toUpperCase());
			sb.append(filedName.substring(1, filedName.length()));
			try {
				Method method = this.getClass().getMethod(sb.toString());
				Object object = method.invoke(this);
				if(object != null){
					map.put(filedName, object);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return map;
	}
}
