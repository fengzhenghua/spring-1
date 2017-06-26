package com.tydic.unicom.uif.service.impl.ablit.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tydic.unicom.uif.service.impl.ablit.provider.CallLocalAbilityPlatProvider;
import com.tydic.unicom.uif.service.interfaces.IAbilitProvider;
import com.tydic.unicom.uif.service.vo.AbilitReqestVo;
import com.tydic.unicom.uif.service.vo.CallLocalAbilitPlatVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午10:15:21
 * @ClassName BaseCallLocalAbilityPlat
 * @Description 调用本地能力平台的基础服务
 * @version V1.0
 */
public abstract class BaseCallLocalAbilityPlat extends AbastractAblitHolder {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private IAbilitProvider<CallLocalAbilitPlatVo> abilitProvider;
	
	@PostConstruct
	public void init() {
		abilitProvider = new CallLocalAbilityPlatProvider();
	}
	
	@Override
	public UocMessage getAblitReturn(AbilitReqestVo requestVo) {
		UocMessage message = new UocMessage();	
		CallLocalAbilitPlatVo callVo = buildCallMessage(requestVo);
		if(callVo!=null){
		   String retMsg = abilitProvider.callAblit(callVo);
		   message= buildReturn(retMsg);
		}else{
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("调用能力平台json串组装失败");
		}
		return message;
	}
	
	/**
	 * 
	 * @Method: buildCallMessage
	 * @Description: 构建请求信息
	 */
	public abstract CallLocalAbilitPlatVo buildCallMessage(AbilitReqestVo requestVo);
}
