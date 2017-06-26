package com.tydic.unicom.uif.service.impl.ablit.impl;

import org.springframework.stereotype.Component;

import com.tydic.unicom.uif.service.vo.AbilitReqestVo;
import com.tydic.unicom.uif.service.vo.CallLocalAbilitPlatVo;
import com.tydic.unicom.webUtil.UocMessage;

@Component("callCloudSaleAbilitService")
public class CallType300ServiceImpl extends BaseCallLocalAbilityPlat{

	@Override
	public CallLocalAbilitPlatVo buildCallMessage(AbilitReqestVo requestVo) {
		CallLocalAbilitPlatVo callVo = new CallLocalAbilitPlatVo();
		
		
		return callVo;
	}
	
	@Override
	public UocMessage buildReturn(String returnMsg) {
		
		return null;
	}
	
}
