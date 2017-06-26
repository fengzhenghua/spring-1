package com.tydic.unicom.uoc.service.sms.policy;

import org.springframework.stereotype.Component;

import com.tydic.unicom.policy.impl.CompositeLimitPolicy;
import com.tydic.unicom.sms.ShortMessage;
import com.tydic.unicom.uoc.service.sms.interfaces.ISmsLimitPolicy;

/**
 * 
 * <p></p>
 * @author heguoyong 2017年6月1日 上午11:35:01
 * @ClassName CompositeSmsLimitPolicy
 * @Description 短信组合限制
 * @version V1.0
 */
@Component("compositeSmsLimitPolicy")
public class CompositeSmsLimitPolicy extends CompositeLimitPolicy<ShortMessage<?>> implements ISmsLimitPolicy{
	
}
