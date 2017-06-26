package com.tydic.unicom.uoc.business.common.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uoc.business.common.interfaces.ActivemqOrderDataBakListenerServDu;
import com.tydic.unicom.uoc.pub.common.service.po.ActivemqSendPo;
import com.tydic.unicom.uoc.service.activiti.interfaces.ChangeToArtificialServiceServDu;
import com.tydic.unicom.uoc.service.common.interfaces.FunctionReflecServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.webUtil.UocMessage;

public class ActivemqOrderDataBakListenerServDuImpl implements ActivemqOrderDataBakListenerServDu{

	Logger logger = Logger.getLogger(ActivemqOrderDataBakListenerServDuImpl.class);
	
	@Autowired
	private FunctionReflecServDu functionReflecServDu;
	
	@Autowired
	private ChangeToArtificialServiceServDu changeToArtificialServiceServDu;
	
	@Override
	public void getMessageFromOrderDataBak(Object message) throws Exception {
		ActivemqSendPo activemqSendPo = (ActivemqSendPo) message;
		logger.info("=============消息队列（OrderDataBak）监听===============");
		logger.info("=======================>>service_code:"+activemqSendPo.getService_code()+" Json_in:"+activemqSendPo.getJson_in());
		//根据service_code获取反射的类名与方法名
		Map<String,String> serviceMap = getValueFromProperties(activemqSendPo.getService_code());
		String className = serviceMap.get(activemqSendPo.getService_code()+"Class");
		String methodName = serviceMap.get(activemqSendPo.getService_code()+"Method");
		Object[] paramValues = new String[]{activemqSendPo.getJson_in()};
		Class[] paramTypes = new Class[]{String.class};
		try {
			UocMessage uocMessage = (UocMessage) functionReflecServDu.invokeMethod(className, methodName, paramValues, paramTypes);
			if(!"0000".equals(uocMessage.getRespCode())){
				ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
				BeanUtils.copyProperties(activemqSendPo.getTaskInst(), procInstTaskInstVo);
				changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "102");
			}
		} catch (Throwable e) {
			logger.error("===============ActivemqOrderDataBakListenerServDuImpl异常，转人工任务==============");
			//e.printStackTrace();
			ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
			BeanUtils.copyProperties(activemqSendPo.getTaskInst(), procInstTaskInstVo);
			changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "102");		}
	}

	public Map<String,String> getValueFromProperties(String key) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		Properties props = new Properties();
		InputStream in = ActivemqOrderFinishListenerServDuImpl.class.getResourceAsStream("/serviceCode.properties");
		props.load(in);
		map.put(key+"Class", props.getProperty(key+"Class"));
		map.put(key+"Method", props.getProperty(key+"Method"));
		return map;
	}
	
}
