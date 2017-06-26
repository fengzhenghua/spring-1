/**
 * @ProjectName: tydic_javaBase
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yangfei
 * @date: 2014年10月14日 下午5:08:46
 * @Title: BusinessException.java
 * @Package com.tydic.unicom.exception
 * @Description: TODO
 */
package com.tydic.unicom.exception;

import java.io.Serializable;


/**
 * <p></p>
 * @author yangfei 2014年10月14日 下午5:08:46
 * @ClassName BusinessException
 * @Description TODO 业务异常类
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014年10月14日
 * @modify by reason:{方法名}:{原因}
 */
public class BusinessException extends Exception implements Serializable{
	private String system;
	private String key;
	private String title;
	private String content;
	private String returnBtnTxt;
	private String returnBtnUrl;
	private String desc;
	
	private BusinessException(){
		
	}
	
	private BusinessException(String key, String title, String returnBtnTxt, String returnBtnUrl, String desc) {
	    super();
	    this.key = key;
	    this.title = title;
	    this.returnBtnTxt = returnBtnTxt;
	    this.returnBtnUrl = returnBtnUrl;
	    this.desc = desc;
    }
	
	private BusinessException(String key, String title, String content, String returnBtnTxt, String returnBtnUrl, String desc) {
	    super();
	    this.key = key;
	    this.title = title;
	    this.content = content;
	    this.returnBtnTxt = returnBtnTxt;
	    this.returnBtnUrl = returnBtnUrl;
	    this.desc = desc;
    }
	public static BusinessException instance(){
		return new BusinessException();
	}

	public static BusinessException instance(String key, String title, String content, String returnBtnTxt, String returnBtnUrl, String desc){
		return new BusinessException(key,title,content,returnBtnTxt,returnBtnUrl,desc);
	}
	
	
    public String getContent() {
    	return content;
    }
	
    public void setContent(String content) {
    	this.content = content;
    }

	
    public String getKey() {
    	return key;
    }

	
    public void setKey(String key) {
    	this.key = key;
    }

	
    public String getTitle() {
    	return title;
    }

	
    public void setTitle(String title) {
    	this.title = title;
    }

	
    public String getReturnBtnTxt() {
    	return returnBtnTxt;
    }

	
    public void setReturnBtnTxt(String returnBtnTxt) {
    	this.returnBtnTxt = returnBtnTxt;
    }

	
    public String getReturnBtnUrl() {
    	return returnBtnUrl;
    }

	
    public void setReturnBtnUrl(String returnBtnUrl) {
    	this.returnBtnUrl = returnBtnUrl;
    }

	
    public String getDesc() {
    	return desc;
    }

	
    public void setDesc(String desc) {
    	this.desc = desc;
    }

	
    public String getSystem() {
    	return system;
    }

	
    public void setSystem(String system) {
    	this.system = system;
    }
	
}
