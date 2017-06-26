package com.tydic.unicom.apc.service.common.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tydic.unicom.service.ecaop.annotation.EcaopField;
import com.tydic.unicom.service.ecaop.annotation.EcaopField.EcaopFieldType;
import com.tydic.unicom.apc.service.common.interfaces.JsonToBeanServDu;

@Service("JsonToBeanServDu")
public class JsonToBeanServDuImpl implements JsonToBeanServDu{

	Logger logger = Logger.getLogger(JsonToBeanServDuImpl.class);
	
	@Override
	public Map<String, Object> jsonToBean(String json,Class cla ) throws Exception {
		logger.info("================json to bean serv du======================");
		Map<String,Object> map = new HashMap<String,Object>();
		JSONObject jsonObject = JSONObject.fromObject(json);			
		map.put("error_code" , "success" );
		map.put("object", parse(jsonObject,cla));
		return map;
	}
	
	@Override	
	public Map<String, Object> jsonToMap(String jsonStr) throws Exception{
		//最外层解析  
        if(jsonStr!=null&&jsonStr.startsWith("{")&&jsonStr.endsWith("}")){
            Map<String, Object> map = new HashMap<String, Object>();  
            
            JSONObject json = JSONObject.fromObject(jsonStr);  
            for(Object k : json.keySet()){
                
                Object v = json.get(k);   
                //如果内层还是数组的话，继续解析  
                if(v instanceof JSONArray){  
                    List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
                    Iterator<JSONObject> it = ((JSONArray)v).iterator();  
                    while(it.hasNext()){  
                        JSONObject json2 = it.next();  
                        list.add(jsonToMap(json2.toString()));  
                    }  
                    map.put(k.toString(), list);  
                } else {  
                    Map<String, Object> m = jsonToMap(v.toString());
                    if(m==null)
                        map.put(k.toString(), v);
                    else 
                        map.put(k.toString(), m);  
                }  
            }  
            return map;  
        }else{
            return null;
        }	
	}
	
	@Override
	public Map<String, String> jsonToMapStr(String jsonStr) throws Exception {
		Map<String, String> map = new HashMap<String, String>(); 
		//一层解析  
		if(jsonStr!=null&&jsonStr.startsWith("{")&&jsonStr.endsWith("}")){	
			JSONObject json = JSONObject.fromObject(jsonStr);  
			for(Object k : json.keySet()){
				Object v = json.get(k);  
				map.put(k.toString(), v.toString());             

			}  
			return map;  
		}else{
			return map;
		}
	}
	

	@Override
	public String mapToJson(Map<String, Object> map) {
		JSONObject jsonObject1 = JSONObject.fromObject(map);     
		return jsonObject1.toString();                              
	}

	public Object parse(JSONObject jsonobject, Class clazz) throws Exception{
		Object object = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.getAnnotations();
			EcaopField ecaopField = field.getAnnotation(EcaopField.class);
			if (null == ecaopField) {
				continue;
			}
			String filedName = field.getName();
			String type = ecaopField.type();
			String subType = ecaopField.subType();
			String className = ecaopField.className();
			StringBuilder sb = new StringBuilder("set");
			sb.append(filedName.substring(0, 1).toUpperCase());
			sb.append(filedName.substring(1, filedName.length()));
			Class c = String.class;
			if(EcaopFieldType.STRING.equals(type)){
				c = String.class;
			}else if(EcaopFieldType.LIST.equals(type)){
				c = List.class;
			}else if(EcaopFieldType.INT.equals(type)){
				c = int.class;
			}else{
				c = Class.forName(className);
			}
			Method method = clazz.getMethod(sb.toString(),c);
			if(EcaopFieldType.STRING.equals(type)){
				try {
	                method.invoke(object, jsonobject.getString(filedName));
                } catch (Exception e) {
                	 method.invoke(object, "");
                }
			}else if(EcaopFieldType.INT.equals(type)){
				try {
					method.invoke(object, jsonobject.getInt(filedName));
                } catch (Exception e) {
                	 method.invoke(object, 0);
                }
			}else if(EcaopFieldType.LONG.equals(type)){
				try {
					method.invoke(object, jsonobject.getLong(filedName));
                } catch (Exception e) {
                	 method.invoke(object, 0l);
                }
			}else if(EcaopFieldType.DOUBLE.equals(type)){
				try {
					method.invoke(object, jsonobject.getDouble(filedName));
                } catch (Exception e) {
                	 method.invoke(object, 0.0);
                }
			}else if(EcaopFieldType.BOOLEAN.equals(type)){
				try {
					method.invoke(object, jsonobject.getBoolean(filedName));
                } catch (Exception e) {
                	 method.invoke(object, false);
                }
			}else if(EcaopFieldType.OBJECT.equals(type)){
				try {
					method.invoke(object, parse(jsonobject.getJSONObject(filedName),Class.forName(className)));
                } catch (Exception e) {
                    try {
                	method.invoke(object, Class.forName(className).newInstance());
		    } catch (Exception e2) {
			method.invoke(object, new Object());
		    }
                	
                }
			}else if(EcaopFieldType.LIST.equals(type)){
				try {
					method.invoke(object, parse(jsonobject.getJSONArray(filedName),subType,Class.forName(className)));
                } catch (Exception e) {
                	method.invoke(object, new ArrayList());
                }
			}
		}
		return object;
	}

	public Object parse(JSONArray jsonArray,String subType,Class clazz) throws Exception{
		List list = new ArrayList();
		for(Object o : jsonArray){
			if(EcaopFieldType.STRING.equals(subType)
					|| EcaopFieldType.INT.equals(subType)
					|| EcaopFieldType.LONG.equals(subType)
					|| EcaopFieldType.DOUBLE.equals(subType)
					|| EcaopFieldType.BOOLEAN.equals(subType)){
				list.add(o);
			}else if(EcaopFieldType.OBJECT.equals(subType)){
				list.add(parse((JSONObject) o ,clazz));
			}else if(EcaopFieldType.LIST.equals(subType)){
				list.add(parse((JSONArray) o,EcaopFieldType.OBJECT ,clazz));
			}
		}
		return list;
	}

	public  Object jsonInfoToBean(String json_info, Class<?> clazz ){
		JSONObject jsonObject = JSONObject.fromObject(json_info);
	   
		return  JSONObject.toBean(jsonObject, clazz);
	}
}
