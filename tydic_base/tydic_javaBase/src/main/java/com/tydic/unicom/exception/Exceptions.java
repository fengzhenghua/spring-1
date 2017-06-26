/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年10月14日 下午7:26:44
 * @Title: Exceptions.java
 * @Package com.tydic.unicom.exception
 * @Description: TODO
 */
package com.tydic.unicom.exception;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


/**
 * <p></p>
 * @author yangfei 2014年10月14日 下午7:26:44
 * @ClassName Exceptions
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年10月14日
 * @modify by reason:{方法名}:{原因}
 */
@XStreamAlias("exceptions")
public class Exceptions implements Serializable{
	@XStreamImplicit(itemFieldName="exception")
	private List<BusinessException> exceptions;
	
    public List<BusinessException> getExceptions() {
    	return exceptions;
    }

	
    public void setExceptions(List<BusinessException> exceptions) {
    	this.exceptions = exceptions;
    }
	
}
