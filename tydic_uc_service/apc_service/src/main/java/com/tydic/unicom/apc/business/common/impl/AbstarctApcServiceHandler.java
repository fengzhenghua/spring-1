package com.tydic.unicom.apc.business.common.impl;

import java.lang.reflect.Method;
import java.util.Map;

import com.tydic.unicom.apc.business.common.interfaces.IApcServiceHandler;
import com.tydic.unicom.exception.BusinessException;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月7日 上午11:43:22
 * @ClassName AbstarctApcServiceHandler
 * @Description 抽象处理器
 * @version V1.0
 */
public abstract class AbstarctApcServiceHandler implements IApcServiceHandler{

	 /** 
     * 反射调用方法 
     * @param newObj 实例化的一个对象 
     * @param methodName 对象的方法名 
     * @param args 参数数组 
     * @return 返回值 
     * @throws Exception 
     */ 
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object invokeMethod(Object newObj, String methodName, Object... args)throws Exception { 
        Class ownerClass = newObj.getClass(); 
        Class[] argsClass = new Class[args.length]; 
        for (int i = 0, j = args.length; i < j; i++) { 
            argsClass[i] = args[i].getClass(); 
        } 
		Method method = ownerClass.getMethod(methodName, argsClass); 
        return method.invoke(newObj, args); 
    } 

	@SuppressWarnings("rawtypes")
	@Override
	public UocMessage getMessage(String json_info,String method_name) throws BusinessException {
		//获取方法名称
		String className = "";
		try {
			Class classType = Class.forName(className);  
	        Object obj = classType.newInstance();  
	        Object[] params = (Object[])getParamValues(json_info).get(method_name);
			return (UocMessage)invokeMethod(obj,method_name,params);
		} catch (Exception e) {
			 throw  (BusinessException)e;
		}
	}
	
	public abstract Map<String, Object[]> getParamValues(String json_info);
}
