package com.tydic.unicom.uif.service.impl.ablit.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.tydic.unicom.uif.service.impl.ablit.provider.CallAopInterfaceProvider;
import com.tydic.unicom.uif.service.interfaces.IAbilitProvider;
import com.tydic.unicom.uif.service.vo.AbilitReqestVo;
import com.tydic.unicom.uif.service.vo.CallAopVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午10:08:48
 * @ClassName CallType800ServiceImpl
 * @Description 直接调用AOP接口
 * @version V1.0
 */
@Component("callCbAopAbilitService")
public class CallType800ServiceImpl extends AbastractAblitHolder {
	
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private IAbilitProvider<CallAopVo> abilitProvider;
	
	@Value("${aop.service.url}")
	private String url;
	
	@Value("${aop.service.appKey}")
	private String appKey;
	@PostConstruct
	public void init() {
		abilitProvider = new CallAopInterfaceProvider();
	}
	
	@Override
	public UocMessage getAblitReturn(AbilitReqestVo requestVo) {
		UocMessage message = new UocMessage();
		logger.info("开始调用AOP接口" + requestVo);
		CallAopVo callVo = buildCallMessage(requestVo);
		if(callVo==null){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("直接调用aop接口组装参数失败");
			return message;
		}
		String retMsg = abilitProvider.callAblit(callVo);
		return buildReturn(retMsg);
	}
	
	@Override
	public CallAopVo buildCallMessage(AbilitReqestVo requestVo) {
		CallAopVo vo = new CallAopVo();
		String method="";
		String bizkey="";
		String json_info=requestVo.getJson_info().toString();
		if(requestVo.getInterface_param_json()!=null && !"".equals(requestVo.getInterface_param_json())){
		   JSONObject object = JSONObject.parseObject(requestVo.getInterface_param_json());
		   method=object.get("method").toString();
		   bizkey=object.get("bizkey").toString();
		}else{
			return null;
		}
		SimpleDateFormat sm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp1 = sm1.format(new Date());
		String apptx = String.valueOf(apptx(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase())).replaceAll(
				"-", "");
		
		if("".equals(method) || method==null || "".equals(bizkey) || bizkey==null || "".equals(json_info) || json_info==null ){
			return null;
		}
		vo.setBizkey(bizkey);
		vo.setMethod(method);
		vo.setTimestamp(timestamp1);
		vo.setApptx(apptx);
		vo.setAppkey(appKey);
		vo.setMsg(json_info);
		vo.setUrl(url);
	    return vo;			
	}
	
	@Override
	public UocMessage buildReturn(String returnMsg) {
		String code = null;
		UocMessage message = new UocMessage();
		JSONObject response = JSONObject.parseObject(returnMsg);
		if(response.get("code") !=null && !"".equals(response.get("code").toString())){
			code=response.get("code").toString();
		}else{
			code="200";
		}
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("调用能力平台成功");
		message.addArg("json_info", returnMsg);
		message.addArg("code", code);
		return message;
	}
	
	
	private Long apptx(String s) {
		Long sum = 0l;
		int tmp = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				tmp = c - '0';
			} else
				if (c >= 'A' && c <= 'F') {
					tmp = c - 'A' + 10;
				} else {
					break;
				}
			sum = sum * 16 + tmp;
		}
		return sum;
	}
	
}
