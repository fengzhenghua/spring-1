/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年11月13日 上午9:52:22
 * @Title: XmlUtil.java
 * @Package com.tydic.unicom.util
 * @Description: TODO
 */
package com.tydic.unicom.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;

import com.thoughtworks.xstream.XStream;

/**
 * <p>
 * </p>
 * @author yangfei 2014年11月13日 上午9:52:22
 * @ClassName XmlUtil
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年11月13日
 * @modify by reason:{方法名}:{原因}
 */
public class XmlUtil {
	
	/**
	 * 
	 * @author yangfei 2014年11月13日 上午9:56:34
	 * @Method: parseObject
	 * @Description: TODO 将xml对象生成javabean
	 * @param xml
	 * @param clazz
	 * @return
	 * @throws
	 */
	public static Object parseObject(String xml, Class clazz) {
		XStream xStream = new XStream();
		xStream.processAnnotations(clazz);
		xStream.autodetectAnnotations(true);
		return xStream.fromXML(xml);
	}
	
	/**
	 * 
	 * @author yangfei 2014年11月13日 上午10:00:28
	 * @Method: parseXml
	 * @Description: TODO 将javabean对象生成xml
	 * @param object
	 * @return
	 * @throws
	 */
	
	public static String parseXml(Object object) {
		XStream xStream = new XStream();
		return xStream.toXML(object);
	}
	
	/**
	 * 
	 * @author wanghao 2014年12月1日 下午4:21:57
	 * @Method: parseXml
	 * @Description: TODO
	 * @param object
	 * @param node 节点名称
	 * @return
	 * @throws
	 */
	public static String parseXml(Object object, String node) {
		XStream xStream = new XStream();
		xStream.alias(node, object.getClass());
		return xStream.toXML(object);
	}
	
	/**
	 * 
	 * @author wanghao 2014年12月1日 上午12:37:44
	 * @Method: getOMElement
	 * @Description: TODO
	 * @param xml
	 * @return
	 * @throws
	 */
	public static OMElement getOMElement(String xml) {
		org.apache.axiom.om.OMFactory omFactory = OMAbstractFactory.getOMFactory();
		return toOMElement(xml, "utf-8");
	}
	
	/**
	 * 
	 * @author wanghao 2014年12月1日 上午12:39:03
	 * @Method: toOMElement
	 * @Description: TODO
	 * @param xmlStr
	 * @param encoding
	 * @return
	 * @throws
	 */
	private static OMElement toOMElement(String xmlStr, String encoding) {
		OMElement xmlValue;
		try {
			xmlValue = (new StAXOMBuilder(new ByteArrayInputStream(xmlStr.getBytes(encoding)))).getDocumentElement();
			return xmlValue;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
