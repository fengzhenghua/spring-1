package com.tydic.unicom.uoc.service.sms.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tydic.unicom.policy.ILimitPolicy;
import com.tydic.unicom.sms.ShortMessage;
import com.tydic.unicom.uoc.service.sms.interfaces.IMessageSendService;
import com.tydic.unicom.uoc.service.sms.interfaces.IUocSmsService;
import com.tydic.unicom.uoc.service.sms.policy.CompositeSmsLimitPolicy;
import com.tydic.unicom.uoc.service.sms.policy.SmsLengthLimitPolicy;
import com.tydic.unicom.uoc.service.sms.policy.SmsMobileNumLimitPolicy;
import com.tydic.unicom.uoc.service.sms.vo.EnSmsSentFlag;
import com.tydic.unicom.webUtil.BusiMessage;

/**
 *
 * <p>
 * </p>
 * @author heguoyong 2017年5月31日 上午11:53:31
 * @ClassName UocSmsServiceImpl
 * @Description 短信发送服务
 * @version V1.0
 */
@Component
public class UocSmsServiceImpl implements IUocSmsService {

	/**
	 * 短信发送服务
	 */
	@Autowired
	private IMessageSendService msgSendService;

	private CompositeSmsLimitPolicy compositeSmsLimitPolicy;

	private ThreadPoolTaskExecutor smsSendTaskExecutor;
	/**
	 * 短信最大长度
	 */
	private static final int MAX_LENG = 500;

	/**
	 * 最大号码批量
	 */
	private static final int MAX_NUM = 500;

	/**
	 * 短信发送线程池
	 *
	 * @param smsSendTaskExecutor
	 */
	public void setSmsSendTaskExecutor(ThreadPoolTaskExecutor smsSendTaskExecutor) {
		this.smsSendTaskExecutor = smsSendTaskExecutor;
	}

	@PostConstruct
	/**
	 *
	 * @Method: init
	 * @Description: 一般通过spirng工厂配置文件来设置线程池和bean的属性
	 */
	public void init() {
		// 构造线程池
		smsSendTaskExecutor = new ThreadPoolTaskExecutor();
		// 线程池所使用的缓冲队列
		smsSendTaskExecutor.setQueueCapacity(100000);
		// 线程池维护线程的最少数量
		smsSendTaskExecutor.setCorePoolSize(1);
		// 线程池维护线程的最大数量
		smsSendTaskExecutor.setMaxPoolSize(5);
		// 线程池维护线程所允许的空闲时间
		smsSendTaskExecutor.setKeepAliveSeconds(30000);
		// CallerRunsPolicy:主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中
		smsSendTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

		smsSendTaskExecutor.initialize();

		compositeSmsLimitPolicy = new CompositeSmsLimitPolicy();
		List<ILimitPolicy<ShortMessage<?>>> limitPolicyList = new ArrayList<ILimitPolicy<ShortMessage<?>>>();

		SmsLengthLimitPolicy lenPolicy = new SmsLengthLimitPolicy();
		lenPolicy.setTextSmsLengthLimit(MAX_LENG);
		limitPolicyList.add(lenPolicy);

		SmsMobileNumLimitPolicy numPolicy = new SmsMobileNumLimitPolicy();
		numPolicy.setSmsTelphoneNumLimit(MAX_NUM);
		limitPolicyList.add(numPolicy);

		compositeSmsLimitPolicy.setLimitPolicyList(limitPolicyList);
	}

	@Override
	public BusiMessage<String> sendShortMessage(ShortMessage<?> msg) {
		BusiMessage<String> sentRlt = null;
		if (msg != null) {
			// 检查限制
			sentRlt = compositeSmsLimitPolicy.checkLimit(msg);
			if (!sentRlt.getSuccess()) {
				return sentRlt;
			}
			if (StringUtils.isEmpty(msg.getMsgId())) {
				msg.setMsgId(UUID.randomUUID().toString());
			}
			// 记录短信id
			sentRlt.setData(msg.getMsgId());
			// 记录短信为待发状态
			sentRlt.setCode(String.valueOf(EnSmsSentFlag.OK.ordinal()));
			sentRlt.setSuccess(true);
			final SmsSendTask sendTask = new SmsSendTask();
			sendTask.setMessageSendService(msgSendService);
			sendTask.setShortMsg(msg);
			smsSendTaskExecutor.execute(sendTask);
		}

		if (!sentRlt.getSuccess()) {
			sentRlt = new BusiMessage<String>();
			sentRlt.setCode(String.valueOf(EnSmsSentFlag.FAILED.ordinal()));
			sentRlt.setMsg("短信参数为空！");
		}
		return sentRlt;
	}

	@Override
	public BusiMessage<?> saveSmsHistory(String smsId) {

		return null;
	}

}
