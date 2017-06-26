package com.tydic.unicom.webUtil;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.LocaleResolver;

@Component
public final class SpringBean implements DisposableBean,
		ApplicationContextAware, ServletContextAware {

	private static ApplicationContext ac;

	private static ServletContext servletContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		ac = applicationContext;
	}

	public void destroy() {
		ac = null;
	}

	public static ApplicationContext getApplicationContext() {
		return ac;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static <T> T getBean(String name, Class<T> type) {
		Assert.hasText(name);
		Assert.notNull(type);
		return ac.getBean(name, type);
	}

	public static String getMessage(String code, Object... args) {
		LocaleResolver localeResolver = getBean(
				"localeResolver", LocaleResolver.class);
		Locale locale = localeResolver.resolveLocale(null);
		return ac.getMessage(code, args, locale);
	}

	/**
	 * 获取对象
	 * 
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws org.springframework.beans.BeansException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		Assert.hasText(name);
		return (T) ac.getBean(name);
	}

	/**
	 * 获取类型为requiredType的对象
	 * 
	 * @param clz
	 * @return
	 * @throws org.springframework.beans.BeansException
	 */
	public static <T> T getBean(Class<T> clz) throws BeansException {
		T result = (T) ac.getBean(clz);
		return result;
	}

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return ac.containsBean(name);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
	 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 * 
	 * @param name
	 * @return boolean
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name)
			throws NoSuchBeanDefinitionException {
		return ac.isSingleton(name);
	}

	/**
	 * @param name
	 * @return Class 注册对象的类型
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 */
	public static Class<?> getType(String name)
			throws NoSuchBeanDefinitionException {
		return ac.getType(name);
	}

	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 * 
	 * @param name
	 * @return
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name)
			throws NoSuchBeanDefinitionException {
		return ac.getAliases(name);
	}

	
	public void setServletContext(ServletContext arg0) {
		servletContext = arg0;
	}
}
