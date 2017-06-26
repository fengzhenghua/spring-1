package com.tydic.unicom.uoc.service.sms.interfaces;

import com.tydic.unicom.policy.ILimitPolicy;
import com.tydic.unicom.sms.ShortMessage;
import com.tydic.unicom.webUtil.BusiMessage;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年6月1日 上午11:07:03
 * @ClassName ISmsLimitPolicy
 * @Description 短信限制策略
 * @version V1.0
 */
public interface ISmsLimitPolicy extends ILimitPolicy<ShortMessage<?>> {
	/**
	 * 检查发送本短信是否符合限制策略
	 * 
	 * （调用策略前应保证shortMessage参数不为空，包括手机号码、短信内容等不为空）
	 * 
	 * @param shortMessage
	 * @return
	 */
	BusiMessage<String> checkLimit(ShortMessage<?> shortMessage);
}
