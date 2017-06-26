package com.tydic.unicom.aspect.log;

import com.tydic.unicom.vdsBase.po.BasePo;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年4月28日 下午3:53:17
 * @ClassName LogAuditDetails
 * @Description 日志内容对象
 * @version V1.0
 */
public class LogAuditDetailsPo extends BasePo {
	
	private static final long serialVersionUID = 5743606336091225832L;
	
	/**
	 * 日志id
	 */
	private String logAuditId;
	
	/**
	 * 方法名称
	 */
	private String functionName;
	
	/**
	 * 传入参数
	 */
	private String functionParam;
	
	/**
	 * 返回数据
	 */
	private String functionReturn;
	
	public String getLogAuditId() {
		return logAuditId;
	}
	
	public void setLogAuditId(String logAuditId) {
		this.logAuditId = logAuditId;
	}
	
	public String getFunctionName() {
		return functionName;
	}
	
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	public String getFunctionParam() {
		return functionParam;
	}
	
	public void setFunctionParam(String functionParam) {
		this.functionParam = functionParam;
	}
	
	public String getFunctionReturn() {
		return functionReturn;
	}
	
	public void setFunctionReturn(String functionReturn) {
		this.functionReturn = functionReturn;
	}
	
}
