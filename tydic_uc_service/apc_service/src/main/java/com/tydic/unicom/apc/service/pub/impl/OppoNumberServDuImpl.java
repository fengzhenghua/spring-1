package com.tydic.unicom.apc.service.pub.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.apc.business.constants.Constant;
import com.tydic.unicom.apc.business.pub.vo.NumberChgRequestVo;
import com.tydic.unicom.apc.business.pub.vo.NumberChgResponseVo;
import com.tydic.unicom.apc.business.pub.vo.NumberQryRequestVo;
import com.tydic.unicom.apc.business.pub.vo.NumberQryResponseVo;
import com.tydic.unicom.apc.service.pub.interfaces.OppoNumberServDu;
import com.tydic.unicom.service.ecaop.service.interfaces.EcaopServ;
import com.tydic.unicom.service.ecaop.vo.BaseResponseVo;
import com.tydic.unicom.service.ecaop.vo.BusiExResponseVo;
import com.tydic.unicom.service.ecaop.vo.SysExResponseVo;
@Service("OppoNumberServDu")
public class OppoNumberServDuImpl implements OppoNumberServDu{

	private static Logger logger = Logger.getLogger(OppoNumberServDuImpl.class);
	
	@Autowired
	private EcaopServ ecaopServ;
	
	@Override
	public Map<String, Object> numberQry(NumberQryRequestVo reqVo) throws Exception {
		logger.info("------------>>ecaop号码查询<<------------");
		
		Map<String ,Object> result=new HashMap<String, Object>();
		
		reqVo.setMethod(Constant.AOP_NUM_QRY);
		BaseResponseVo rsp = ecaopServ.remoteCall(reqVo, NumberQryResponseVo.class);
		if(rsp instanceof NumberQryResponseVo){
			NumberQryResponseVo numQryRsp=(NumberQryResponseVo)rsp;
			result.put("code", "200");
			result.put("detail","OK");
			result.put("obj",numQryRsp);
		}else if(rsp instanceof BusiExResponseVo){
			BusiExResponseVo busiExRsp=(BusiExResponseVo)rsp;
			result.put("code", busiExRsp.getCode());
			result.put("detail",busiExRsp.getDetail());
		}else if(rsp instanceof SysExResponseVo){
			SysExResponseVo sysExRsp=(SysExResponseVo)rsp;
			result.put("code", sysExRsp.getCode());
			result.put("detail",sysExRsp.getDetail());
		}
		logger.debug("------------>>ecaop号码查询----结果<<------------"+result);
		return result;
	}

	@Override
	public Map<String, Object> numberChg(NumberChgRequestVo reqVo) throws Exception {

		logger.info("------------>>ecaop号码预占<<------------");
		
		reqVo.setMethod(Constant.AOP_NUM_CHG);
		
		//用新的AOP
		String SnChangeTag = reqVo.getMsg().getResourcesInfo().get(0).getSnChangeTag();
		if(SnChangeTag!=null&&!"".equals(SnChangeTag)) {
			reqVo.setMethod(Constant.AOP_NUM_CHG_NEW);
		}
		
		BaseResponseVo rsp = ecaopServ.remoteCall(reqVo, NumberChgResponseVo.class);;
		Map<String ,Object> result=new HashMap<String, Object>();
		if(rsp instanceof NumberChgResponseVo){
			NumberChgResponseVo numChgRsp=(NumberChgResponseVo)rsp;
			result.put("code", "200");
			result.put("detail","OK");
			result.put("obj",numChgRsp);
		}else if(rsp instanceof BusiExResponseVo){
			BusiExResponseVo busiExRsp=(BusiExResponseVo)rsp;
			result.put("code", busiExRsp.getCode());
			result.put("detail",busiExRsp.getDetail());
		}else if(rsp instanceof SysExResponseVo){
			SysExResponseVo sysExRsp=(SysExResponseVo)rsp;
			result.put("code", sysExRsp.getCode());
			result.put("detail",sysExRsp.getDetail());
		}
		logger.debug("------------>>ecaop号码预占----结果<<------------"+result);
		return result;
	}

}
