package com.tydic.unicom.uoc.service.sms.policy;

import org.springframework.util.StringUtils;

import com.tydic.unicom.sms.ShortMessage;
import com.tydic.unicom.uoc.service.sms.interfaces.ISmsLimitPolicy;
import com.tydic.unicom.uoc.service.sms.vo.EnSmsSentFlag;
import com.tydic.unicom.webUtil.BusiMessage;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年6月1日 上午11:24:52
 * @ClassName SmsPhoneNumLimitPolicy
 * @Description 短信号码数量限制策略
 * @version V1.0
 */
public class SmsMobileNumLimitPolicy implements ISmsLimitPolicy {
	
	/**
	 * 短信号码限制
	 */
	private int smsTelphoneNumLimit;
	
	@Override
	public BusiMessage<String> checkLimit(ShortMessage<?> shortMessage) {
		BusiMessage<String> msg = new BusiMessage<String>();
		String mobiles = shortMessage.getDesMobile();
		int maxLimit = smsTelphoneNumLimit;
		if(!StringUtils.isEmpty(mobiles)){
			if(mobiles.split(",").length <= maxLimit){
				msg.setSuccess(true);
			}else{
				msg.setSuccess(false);
				msg.setCode(String.valueOf(EnSmsSentFlag.FALIED_SMS_MOBILE_NUM_LIMIT.ordinal()));
				msg.setMsg("号码数量超过最大限制：" + maxLimit);
			}
		}
		return msg;
	}
	
	public int getSmsTelphoneNumLimit() {
		return smsTelphoneNumLimit;
	}
	
	public void setSmsTelphoneNumLimit(int smsTelphoneNumLimit) {
		this.smsTelphoneNumLimit = smsTelphoneNumLimit;
	}
	
}
