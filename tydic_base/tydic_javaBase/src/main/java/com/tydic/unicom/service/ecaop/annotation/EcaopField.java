/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年11月6日 下午3:32:47
 * @Title: EcaopField.java
 * @Package com.tydic.unicom.service.ecaop.annotation
 * @Description: TODO
 */
package com.tydic.unicom.service.ecaop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.alibaba.dubbo.common.utils.DubboAppender;


/**
 * <p></p>
 * @author yangfei 2014年11月6日 下午3:32:47
 * @ClassName EcaopField
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月6日
 * @modify by reason:{方法名}:{原因}
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EcaopField {
	/**
	 * 
	 * @author yangfei 2014年11月6日 下午3:34:55
	 * @Method: type 
	 * @Description: TODO string/object两种格式。如果是object，那么className必须要有值
	 * @return 
	 * @throws
	 */
	String type() default EcaopFieldType.STRING;
	String subType() default EcaopFieldType.OBJECT;
	String className() default "com.tydic.unicom.service.ecaop.vo.Para";
	
	public class EcaopFieldType{
		public static final String INT = "int";
		public static final String DOUBLE = "double";
		public static final String LONG = "long";
		public static final String BOOLEAN = "boolean"; 
		public static final String STRING = "string";
		public static final String OBJECT = "object";
		public static final String LIST = "list";
	}
}
