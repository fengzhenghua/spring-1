package com.tydic.unicom.aspect.log;

import com.tydic.unicom.vdsBase.po.BasePo;
import com.tydic.unicom.util.EnumHelper;
/**
 * 
 * <p></p>
 * @author heguoyong 2017年4月28日 下午3:53:02
 * @ClassName LogAuditPo
 * @Description 审计日志对象
 * @version V1.0
 */
public class LogAuditPo extends BasePo {
	
	private static final long serialVersionUID = -2659748286114887668L;
	
	/**
	 * id
	 */
	private String logId;
	
	/**
	 * 方法名称
	 */
	private String functionName;
	
	/**
	 * 方法描述
	 */
	private String functionDescription;
	
	/**
	 * 执行时间
	 */
	private String actionTime;
	/**
	 * 执行结果：success/fail
	 */
	private String actionStatus;
	/**
	 * 执行者ID
	 */
	private String actorId;
	/**
	 * 执行者名称
	 */
	private String actorName;
	
	/**
	 * 执行者ip
	 */
	private String actorIp;
	
	/**
	 * 设备号：电脑为MAC地址，手机为IMEI
	 */
	private String actorDeviceNumber;
	
	/**
	 * 执行时间
	 */
	private String actionTimeMillisecond;
	
	public String getLogId() {
		return logId;
	}
	
	public void setLogId(String logId) {
		this.logId = logId;
	}
	
	public String getFunctionName() {
		return functionName;
	}
	
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	public String getFunctionDescription() {
		return functionDescription;
	}
	
	public void setFunctionDescription(String functionDescription) {
		this.functionDescription = functionDescription;
	}
	
	public String getActionTime() {
		return actionTime;
	}
	
	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}
	
    public EnActionStatus getActionStatus() {
        return EnumHelper.getEnum(actionStatus,EnActionStatus.class);
    }

    public void setActionStatus(EnActionStatus actionStatus) {
        this.actionStatus = actionStatus == null ? null : actionStatus.toString();
    }
	
	public String getActorId() {
		return actorId;
	}
	
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	
	public String getActorName() {
		return actorName;
	}
	
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}
	
	public String getActorIp() {
		return actorIp;
	}
	
	public void setActorIp(String actorIp) {
		this.actorIp = actorIp;
	}
	
	public String getActorDeviceNumber() {
		return actorDeviceNumber;
	}
	
	public void setActorDeviceNumber(String actorDeviceNumber) {
		this.actorDeviceNumber = actorDeviceNumber;
	}
	
	public String getActionTimeMillisecond() {
		return actionTimeMillisecond;
	}
	
	public void setActionTimeMillisecond(String actionTimeMillisecond) {
		this.actionTimeMillisecond = actionTimeMillisecond;
	}
	
}
