package com.tydic.unicom.vdsBase.po;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.tydic.uda.core.Sort;
import com.tydic.unicom.vdsBase.annotation.filter;
import com.tydic.unicom.vdsBase.annotation.like;
import com.tydic.unicom.vdsBase.annotation.like.Like;
import com.tydic.unicom.vdsBase.annotation.sort;
import com.tydic.unicom.webUtil.BaseVo;

public abstract class BasePo implements Serializable {
	/**
	 * 
	 */
	private static final int MAX_SIZE = 1000;
	private static final long serialVersionUID = 1L;
	
	private String essKey;


	
    public String getEssKey() {
    	return essKey;
    }

	
    public void setEssKey(String essKey) {
    	this.essKey = essKey;
    }
    
    public String logString(){
    	StringBuffer sb =  new StringBuffer();
    	Map<String,Object> map = convertThis2Map();
    	Set<String> set = map.keySet();
    	for(String key : set){
    		sb.append(key+":"+map.get(key)+"||");
    	}
    	return sb.toString();
    }

	public Map<String,Object> convertThis2Map() {
		Map<String,Object> map = new HashMap<String,Object>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String filedName = fields[i].getName();
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
		map.put("MAX_SIZE", MAX_SIZE);
		return map;
	}
	
	public Map<String,Object> getFilterMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String filedName = fields[i].getName();
			if("serialVersionUID".equals(filedName)){
				continue;
			}
			StringBuilder sb = new StringBuilder("get");
			sb.append(filedName.substring(0, 1).toUpperCase());
			sb.append(filedName.substring(1, filedName.length()));
			try {
				Method method = this.getClass().getMethod(sb.toString());
				Object object = method.invoke(this);
				filter filter = fields[i].getAnnotation(filter.class);
				if(object != null && filter != null){
					map.put(filedName, object);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return map;
	}
	
	public Map<String,Object> getFilterMap(Map<String,Object> paramMap){
		Map<String,Object> map = getFilterMap();
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String filedName = fields[i].getName();
			if("serialVersionUID".equals(filedName)){
				continue;
			}
			filter filter = fields[i].getAnnotation(filter.class);
			String paramStr = filter.paramName();
			if("".equals(paramStr)){
				return map;
			}
			for(String param :paramStr.split(",")){
				map.put(param, paramMap.get(param));
			}
		}
		return map;
	}
	
	public Map<String,String> getLikeMap(){
		Map<String,String> map = new HashMap<String,String>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String filedName = fields[i].getName();
			if("serialVersionUID".equals(filedName)){
				continue;
			}
			StringBuilder sb = new StringBuilder("get");
			sb.append(filedName.substring(0, 1).toUpperCase());
			sb.append(filedName.substring(1, filedName.length()));
			try {
				Method method = this.getClass().getMethod(sb.toString());
				Object object = method.invoke(this);
				like like = fields[i].getAnnotation(like.class);
				if(object != null && like != null){
					Like type = like.type();
					if(type.equals(Like.RIGHT)){
						map.put(filedName, object.toString() + "%");
					}else if(type.equals(Like.LEFT)){
						map.put(filedName, "%" + object.toString());
					}else if(type.equals(Like.ALL)){
						map.put(filedName, "%" + object.toString() + "%");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return map;
	}
	
	public Map<String,String> getLikeMap(Map<String,Object> paramMap){
		Map<String,String> map = getLikeMap();
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String filedName = fields[i].getName();
			if("serialVersionUID".equals(filedName)){
				continue;
			}
			like like = fields[i].getAnnotation(like.class);
			if(like != null){
				Like type = like.type();
				String paramStr = like.paramName();
				if("".equals(paramStr)){
					return map;
				}
				for(String param:paramStr.split(",")){
					if(type.equals(Like.RIGHT)){
						map.put(param, paramMap.get(param).toString() + "%");
					}else if(type.equals(Like.LEFT)){
						map.put(param, "%" + paramMap.get(param).toString());
					}else if(type.equals(Like.ALL)){
						map.put(param, "%" + paramMap.get(param).toString() + "%");
					}
				}
			}
		}
		return map;
	}
	
	public Map<String,Sort> getSortMap(){
		Map<String,Sort> map = new HashMap<String,Sort>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String filedName = fields[i].getName();
			if("serialVersionUID".equals(filedName)){
				continue;
			}
			StringBuilder sb = new StringBuilder("get");
			sb.append(filedName.substring(0, 1).toUpperCase());
			sb.append(filedName.substring(1, filedName.length()));
			try {
				sort sort = fields[i].getAnnotation(sort.class);
				if(sort != null){
					map.put(filedName, sort.order());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return map;
	}
	public Map<String,Sort> getSortMap(Map<String,Object> paramMap){
		Map<String,Sort> map = getSortMap();
		Iterator<String> it = paramMap.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			Sort sort = (Sort) paramMap.get(key);
			map.put(key, sort);
		}
		return map;
	}
	public BaseVo convert2vo( Class class1) {
		BaseVo vo = null;
		try {
			vo = (BaseVo) class1.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(this!=null){
			BeanUtils.copyProperties(this, vo);
		}
		return vo;
	}

}
