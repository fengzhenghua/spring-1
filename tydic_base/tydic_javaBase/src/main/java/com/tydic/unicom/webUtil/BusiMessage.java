package com.tydic.unicom.webUtil;

import com.tydic.unicom.security.BaseObject;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月18日 下午3:51:29
 * @ClassName BusiMessage
 * @Description 业务层返回对象
 * @version V1.0
 */
public class BusiMessage<T> extends BaseObject {
	
	private static final long serialVersionUID = 1821508042749842455L;
	
	/**
	 * API调用结果 true:成功，false：失败
	 */
	private boolean success = false;
	/**
	 * 返回码
	 */
	private String code = "";
	
	/**
	 * 返回的数据，可以任意集合或对象
	 */
	private T data;
	/**
	 * 结果说明
	 */
	private String msg = "";
	
	/**
	 * @return the success true:成功，false：失败
	 */
	public boolean getSuccess() {
		return success;
	}
	
	/**
	 * @param success the success to set true:成功，false：失败
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/**
	 * @return the code
	 * @see ApiUtils
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @param code the code to set
	 * @see ApiUtils
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}
	
	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}
	
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
