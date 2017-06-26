package com.tydic.unicom.ugc.service.common.interfaces;

public interface FunctionReflecServDu {

	/**
	 * java函数反射
	 * 
	 * @param className,methodName,paramValues,paramTypes
	 * @return Object
	 * @throws Exception
	 */
	public   Object invokeMethod(String className,String methodName,Object[] paramValues, Class[] paramTypes) throws Throwable;
	
	/**
	 * java 反射获得PO属性值
	 * @param className
	 * @param paramValues
	 * @return
	 * @throws Throwable
	 */
	public   Object invokeField(String className,String paramValue,Object obj) throws Throwable;
}
