package com.tydic.unicom.uoc.service.sms.policy;

import com.tydic.unicom.sms.ShortMessage;
import com.tydic.unicom.uoc.service.sms.interfaces.ISmsLimitPolicy;
import com.tydic.unicom.uoc.service.sms.vo.EnSmsSentFlag;
import com.tydic.unicom.webUtil.BusiMessage;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年6月1日 上午11:20:18
 * @ClassName SmsLengthLimitPolicy
 * @Description 短信长度限制策略
 * @version V1.0
 */
public class SmsLengthLimitPolicy implements ISmsLimitPolicy {
	
	/**
	 * 文本短信长度限制
	 */
	private int textSmsLengthLimit;
	
	@Override
	public BusiMessage<String> checkLimit(ShortMessage<?> shortMessage) {
		BusiMessage<String> msg = new BusiMessage<String>();
		int smsLength = shortMessage.getMsgLength();
		int maxLimit = textSmsLengthLimit;
		if (smsLength <= maxLimit) {
			msg.setSuccess(true);
		} else {
			msg.setSuccess(false);
			msg.setCode(String.valueOf(EnSmsSentFlag.FALIED_SMS_LENGTH_LIMIT.ordinal()));
			msg.setMsg("短信长度(" + smsLength + ")" + "超过最大限制：" + maxLimit);
		}
		return msg;
	}
	
	public int getTextSmsLengthLimit() {
		return textSmsLengthLimit;
	}
	
	public void setTextSmsLengthLimit(int textSmsLengthLimit) {
		this.textSmsLengthLimit = textSmsLengthLimit;
	}
	
}
