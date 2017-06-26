package com.tydic.unicom.policy.impl;

import java.util.ArrayList;
import java.util.List;

import com.tydic.unicom.policy.ILimitPolicy;
import com.tydic.unicom.webUtil.BusiMessage;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年6月1日 上午11:11:10
 * @ClassName CompositeLimitPolicy
 * @Description 组合策略限制
 * @version V1.0
 */
public class CompositeLimitPolicy<T> implements ILimitPolicy<T>{

	protected List<ILimitPolicy<T>> limitPolicyList;
	
	public CompositeLimitPolicy(){
		limitPolicyList = new ArrayList<ILimitPolicy<T>>();
	}
	
	/**
	 * 
	 * @Method: getLimitPolicyList 
	 * @Description: 策略列表
	 */
	public List<ILimitPolicy<T>> getLimitPolicyList() {
		return limitPolicyList;
	}

	/**
	 * 
	 * @Method: getLimitPolicyList 
	 * @Description: 策略列表
	 */
	public void setLimitPolicyList(List<ILimitPolicy<T>> limitPolicyList) {
		this.limitPolicyList = limitPolicyList;
	}

	@Override
	public BusiMessage<String> checkLimit(T checked) {
		
		for (ILimitPolicy<T> policy : limitPolicyList) {
			BusiMessage<String> checkRlt = policy.checkLimit(checked);
			if (!checkRlt.getSuccess()) {
				return checkRlt;
			}
		}
		BusiMessage<String> message = new BusiMessage<String>();
		message.setSuccess(true);
		return message;
	}
	
}
