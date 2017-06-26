package com.tydic.unicom.policy;

import com.tydic.unicom.webUtil.BusiMessage;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年6月1日 上午11:03:14
 * @ClassName ILimitPolicy
 * @Description 限制策略
 * @version V1.0
 */
public interface ILimitPolicy<T>{
	/**
	 * 检查指定对象是否符合指定限制策略
	 * 
	 * @param checked
	 * @return
	 */
	BusiMessage<String> checkLimit(T checked);
}
