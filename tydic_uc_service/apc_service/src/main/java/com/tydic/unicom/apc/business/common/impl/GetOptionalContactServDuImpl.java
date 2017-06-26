package com.tydic.unicom.apc.business.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.apc.base.pub.interfaces.ApDefineServ;
import com.tydic.unicom.apc.base.pub.po.ApDefinePo;
import com.tydic.unicom.apc.business.common.interfaces.GetOptionalContactServDu;
import com.tydic.unicom.apc.business.common.interfaces.IApcServiceHandler;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.apc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.exception.BusinessException;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class GetOptionalContactServDuImpl implements GetOptionalContactServDu,IApcServiceHandler{
	
	Logger logger = Logger.getLogger(GetOptionalContactServDuImpl.class);
	
	@Autowired 
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;
	@Autowired 
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired 
	private ApDefineServ apDefineServ;
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage getOptionalContact(String jsession_id, String ap_id,
			String ap_name) {
		UocMessage message = new UocMessage();
		
		if(StringUtils.isEmpty(jsession_id)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("请求参数中jsession_id不能为空");
			return message;
		}
		Map<String,Object> oper_info =new HashMap<String,Object>();
		/*
		 * 调用UAC0005校验登录
		 */
		String json_info = "{\"SERVICE_NAME\":\"getBaseOperInfo\",\"param\":{\"jsession_id\":\"" + jsession_id + "\"}}";
		try{
			UocMessage res = apcAbilityPlatformServDu.CallApcAbilityPlatform(json_info, "", "UAC");
			if (res != null) {
				String code = (String) res.getArgs().get("code");
				if (code != null && "200".equals(code)) {
					String json_info_out = (String) res.getArgs().get("json_info");
					Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_info_out);
					oper_info = (Map<String, Object>) map.get("oper_info");
					if (oper_info == null) {
						logger.info("----------无对应工号信息----------");
						message.setRespCode(RespCodeContents.PARAM_ERROR);
						message.setContent("无对应工号信息");
						return message;
					}
					logger.info("----------oper_info----------" + oper_info.toString());
				} else {
					logger.info("----------能力平台调用失败----------");
					message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
					return res;
				}
			}
		}catch(Exception e){
			logger.info("----------能力平台调用异常----------");
			e.printStackTrace();
			message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
			message.setContent("能力平台调用异常");
			return message;
		}
		
		//通过入参id跟名称(支持模糊)查询触点定义表state=0的数据
		ApDefinePo apDefinePo=new ApDefinePo();
		if(!StringUtils.isEmpty(ap_id) || !StringUtils.isEmpty(ap_name)){
			
			apDefinePo.setAp_id(ap_id);
			apDefinePo.setAp_name(ap_name);
			try{
				List<ApDefinePo> resurtlist=apDefineServ.queryApDefineByApIdOrApName(apDefinePo);
				message.addArg("ap_list", resurtlist);
				message.setContent("获取可选触点成功");
				message.setRespCode(RespCodeContents.SUCCESS);
				return message;
			}catch(Exception e){
				e.printStackTrace();
				message.setContent("查询异常");
				message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				return message;
			}
		}else{
			//不传参的情况下查出全部（不超过1000）条
			try{
				List<ApDefinePo> resurtlist=apDefineServ.queryApDefineAll(apDefinePo);
				message.addArg("ap_list", resurtlist);
				message.setContent("获取可选触点成功");
				message.setRespCode(RespCodeContents.SUCCESS);
				return message;
			}catch(Exception e){
				e.printStackTrace();
				message.setContent("查询异常");
				message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				return message;
			}
		}
		
		
	}


	@Override
	public UocMessage getMessage(String json_in, String method_name)
			throws BusinessException {
		// TODO Auto-generated method stub
		UocMessage message=new UocMessage();
		Map<String, Object> map=null;
		try {
			map = jsonToBeanServDu.jsonToMap(json_in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw (BusinessException) e;
		}
		if("getOptionalContact".equals(method_name)){
			String jsession_id="";
			String ap_id="";
			String ap_name="";
			if(map.containsKey("jsession_id")){
				jsession_id=(String) map.get("jsession_id");
			}
			if(map.containsKey("ap_id")){
				ap_id=(String) map.get("ap_id");
			}
			if(map.containsKey("ap_name")){
				ap_name=(String) map.get("ap_name");
			}
			message=getOptionalContact(jsession_id,ap_id,ap_name);
		}else{
			message.setContent("方法名错误");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			return message;
		}
		return message;
	}
	
	
	
	
}
