package com.tydic.unicom.apc.common.wxpay;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BaseProtocolData {

	public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            field.setAccessible(true);
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
            Object obj;
            field.setAccessible(true);
            try {
                obj = field.get(this);
                if(obj!=null){
                	sb.append("["+field.getName()+":"+obj+"] ");
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
		
		return sb.toString();
	}
}
