package com.tydic.unicom.ugc.service.common.impl;

import com.tydic.unicom.ugc.service.common.interfaces.FunctionReflecServDu;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


@Service("FunctionReflecServDu")
public class FunctionReflecServDuImpl implements FunctionReflecServDu{
	

	/**
	 * 反射方法缓存
	 */
	private static Map methods = new HashMap();	
	Logger logger = Logger.getLogger(FunctionReflecServDuImpl.class);

	public FunctionReflecServDuImpl() {
	}


	/**
	 * 反射调用取item的结果函数
	 * @param className String 类名
	 * @param methodName String  方法名
	 * @param ParamValues String[]  参数数组
	 * @param paramTypes
	 * @return String
	 * @throws Throwable 
	 */
	@Override
	public  Object invokeMethod(String className, String methodName, Object[] paramValues, Class[] paramTypes) throws Throwable {
		logger.info("AJAX反射调用:" + className + "." + methodName);
		Object result = null; //初试化返回对象
		try {
			Class clazz = Class.forName(className);//根据类名加载类
			//得到类方法对
			Method method = getMethod(clazz, className, methodName, paramValues, paramTypes);
			result = method.invoke(clazz.newInstance(), paramValues); //调用方法得到返回值

		} catch (InstantiationException ex) {
			throw new Exception("反射调用[" + className + "." + methodName + "]实例化错误！");
		} catch (InvocationTargetException ex) {
			//			ex.printStackTrace();
			//			throw new Exception("反射调用[" + className + "." + methodName + "]错误！", ex);
			throw ex.getCause();
		} catch (IllegalArgumentException ex) {
			throw new Exception("传入类[" + className + "]的函数[" + methodName + "]的参数错误！", ex);
		} catch (IllegalAccessException ex) {
			throw new Exception("无法访问" + className + "类" + "的函数:" + methodName + "！", ex);
		} catch (ClassNotFoundException ex) {
			throw new Exception("没有找到类" + className + "！", ex);
		} catch (SecurityException ex) {
			throw new Exception("反射调用错误！", ex);
		} catch (NoSuchMethodException ex) {
			throw new Exception(className + "类" + "没有函数:" + methodName + "！", ex);
		}
		return result;
	}

	/**
	 * 获取反射调用的方法
	 * @param clazz Class 类名
	 * @param methodName String 方法名称
	 * @param paramValues String[] 参数名称
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @return Method 方法
	 */
	private static Method getMethod(Class clazz, String className, String methodName, Object[] paramValues, Class[] paramTypes) throws SecurityException, NoSuchMethodException, ClassNotFoundException {

		//因为所有参数必须是string,所以直接用参数length作为key
		String methodKey = className + "." + methodName + "." + paramValues.length;
		Method result = null;//初始化返回方法
		synchronized (methods) { //同步方法
			result = (Method) methods.get(methodKey);//从缓存中取方法
			if (result == null) {
				//如果缓存内没有该方法
				result = clazz.getMethod(methodName, paramTypes);//得到方法
				methods.put(methodKey, result);//将方法保存到缓存中
			}
		}
		return result;
	}


	@Override
	public   Object invokeField(String className,String paramValue,Object obj) throws Throwable{
		Object result = null;
		try {
			Class clazz = Class.forName(className);//根据类名加载类
			//得到类方法对象
			Field field=clazz.getDeclaredField(paramValue);
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);  
			Method getMethod = pd.getReadMethod();//获得get方法  
			result = getMethod.invoke(obj);//执行get方法返回一个Object  

		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return result;
	}


}
