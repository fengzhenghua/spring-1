package com.tydic.unicom.test.spring;

import static org.mockito.Mockito.mock;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年3月23日 下午6:10:07
 * @ClassName DependencyInjectionAsMockitoTestExecutionListener
 * @Description 用spring来实现TDD面向接口的测试方案，对依赖的类做到完全屏蔽，对目前测试类和mock类设置期望输出简单实现
 * @version V1.0
 */
public class MockitoDependencyInjectionTestExecutionListener extends DependencyInjectionTestExecutionListener {
	
	private static String SETTER = "set";
	
	private static String GETTER = "get";
	
	@SuppressWarnings({"rawtypes", "unused"})
	@Override
	protected void injectDependencies(final TestContext testContext) throws Exception {
		super.injectDependencies(testContext);
		Object bean = testContext.getTestInstance();
		Class[] mockClass = getMockClass(bean.getClass());
		Method[] methods = bean.getClass().getDeclaredMethods();
		Class clz = bean.getClass();
		Object instance = null;
		List<MockObjectMap> objs = new ArrayList<MockObjectMap>();
		autowireMockBean(clz, bean, objs);
		List<Object> stubObjs = getStubInstance(clz, bean);
		autowireMockBeanForSpring(stubObjs, objs);
	}
	
	/**
	 * 
	 * @author heguoyong 2017年3月24日 下午4:07:51
	 * @Method: autowireMockBeanForSpring
	 * @Description: 自动注入@Mock
	 */
	@SuppressWarnings({"rawtypes"})
	private void autowireMockBeanForSpring(List<Object> stubObjs, List<MockObjectMap> objs)
	        throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		for (Object object : stubObjs) {
			Class claz = object.getClass();
			do {
				for (Method method : claz.getDeclaredMethods()) {
					if (method.getName().startsWith(SETTER)) {
						for (MockObjectMap mockObjectMap : objs) {
							Object obj = method.getGenericParameterTypes()[0];
							if (obj instanceof java.lang.reflect.Type
							        && mockObjectMap.getType().getName().equalsIgnoreCase(((Class)obj).getName())) {
								method.invoke(object, mockObjectMap.getObj());
								continue;
							}
							
						}
						
					}
				}
				
				claz = claz.getSuperclass();
			} while (!claz.equals(Object.class));
		}
		
	}
	
	@SuppressWarnings({"rawtypes"})
	private void autowireMockBean(Class clz, Object bean, List<MockObjectMap> objs)
	        throws IllegalArgumentException, IllegalAccessException {
		
		for (Field field : clz.getFields()) {
			
			Annotation[] mockAnnotations = field.getAnnotations();
			for (Annotation annotation : mockAnnotations) {
				if (annotation instanceof org.mockito.Mock) {
					MockObjectMap mockObjectMap = new MockObjectMap();
					objs.add(mockObjectMap);
					mockObjectMap.setType(field.getType());
					mockObjectMap.setObj(mock(field.getType()));
					field.setAccessible(true);
					field.set(bean, mockObjectMap.getObj());
					
					continue;
				}
				
			}
			
		}
		
	}
	
	/**
	 * 取得测试类中所有的mock对象的类型
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({"rawtypes"})
	private Class[] getMockClass(Class claz) {
		List<Class> clasList = new ArrayList<Class>();
		Field[] fields = claz.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] mockAnnotations = field.getAnnotations();
			for (Annotation annotation : mockAnnotations) {
				if (annotation instanceof org.mockito.Mock) {
					clasList.add(field.getType());
					continue;
				}
				
			}
		}
		return clasList.toArray(new Class[0]);
	}
	
	/**
	 * 取得测试类中测试桩类
	 * 
	 * @param clazz
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings({"rawtypes"})
	private List<Object> getStubInstance(Class clazz, Object bean)
	        throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		List<Object> objList = new ArrayList<Object>();
		Field[] fields = clazz.getDeclaredFields();// 测试类中所有的域名
		Method[] methods = clazz.getDeclaredMethods();
		for (Field field : fields) {
			Annotation[] mockAnnotations = field.getAnnotations();
			for (Annotation annotation : mockAnnotations) {
				if (annotation instanceof Autowired) {
					
					for (Method method : methods) {
						String name = field.getName();
						if (method.getName().startsWith(GETTER) && method.getName().substring(3).equalsIgnoreCase(name)) {
							objList.add(method.invoke(bean, null)); // 将所有的测试桩类放在objList
						}
					}
					
				}
				
			}
		}
		return objList;
		
	}
	
	private class MockObjectMap {
		
		private Object obj;
		
		private Class<?> type;
		
		public Object getObj() {
			return obj;
		}
		
		public void setObj(Object obj) {
			this.obj = obj;
		}
		
		public Class<?> getType() {
			return type;
		}
		
		public void setType(Class<?> type) {
			this.type = type;
		}
		
	}
	
}
