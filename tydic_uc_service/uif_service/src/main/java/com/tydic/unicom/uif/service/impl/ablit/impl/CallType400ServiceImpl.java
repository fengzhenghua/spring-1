package com.tydic.unicom.uif.service.impl.ablit.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.tydic.unicom.uif.service.impl.ablit.provider.CallWebServiceByAxis2Provider;
import com.tydic.unicom.uif.service.interfaces.IAbilitProvider;
import com.tydic.unicom.uif.service.vo.AbilitReqestVo;
import com.tydic.unicom.uif.service.vo.CallWebServiceVo;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 
 * <p>
 * </p>
 * @author heguoyong 2017年5月17日 下午10:09:11
 * @ClassName CallType400ServiceImpl
 * @Description 调用webservice接口的
 * @version V1.0
 */
@Component("callWebSerAbilitService")
public class CallType400ServiceImpl extends AbastractAblitHolder {
	
	private IAbilitProvider<CallWebServiceVo> abilitProvider;
	
	@PostConstruct
	public void init() {
		abilitProvider = new CallWebServiceByAxis2Provider();
	}
	
	@Override
	public UocMessage getAblitReturn(AbilitReqestVo requestVo) {
		CallWebServiceVo callVo = buildCallMessage(requestVo);
		String retMsg = abilitProvider.callAblit(callVo);
		return buildReturn(retMsg);
	}
	
	@Override
	public CallWebServiceVo buildCallMessage(AbilitReqestVo requestVo) {
		
		return null;
	}
	
	@Override
	public UocMessage buildReturn(String returnMsg) {
		return null;
	}
	
}
