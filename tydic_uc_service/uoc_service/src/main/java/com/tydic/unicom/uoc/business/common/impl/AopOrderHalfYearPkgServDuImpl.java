package com.tydic.unicom.uoc.business.common.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.tydic.unicom.uoc.base.uocinst.po.LogHalfYearPkgPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.LogHalfYearPkgServ;
import com.tydic.unicom.uoc.business.common.interfaces.AopOrderHalfYearPkgServDu;
import com.tydic.unicom.uoc.business.common.vo.AopHalfYearPkgVo;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class AopOrderHalfYearPkgServDuImpl implements AopOrderHalfYearPkgServDu{
	
	Logger logger = Logger.getLogger(AopOrderHalfYearPkgServDu.class);
	/**
	 * UOC0086 AOP订购半年包
	 */
	@Autowired 
	private OperServDu operServDu;
	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;
	@Autowired
	private LogHalfYearPkgServ logHalfYearPkgServ;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired 
	private GetIdServDu getIdServDu;
	@Override
	public UocMessage getAopHalfYearPkg(AopHalfYearPkgVo vo) throws Exception{
		
		logger.info("===============AOP订购半年包服务入参:"+"jsession_id:"+vo.getJsession_id()
				+"**money:"+vo.getMoney()+"**count:"+vo.getCount()+"**valid_month:"+
				vo.getValid_month()+"**res_type:"+vo.getRes_type()+"**acc_nbr:"+vo.getAcc_nbr());
		
		UocMessage message=new UocMessage();
		if(vo.getJsession_id()==null||"".equals(vo.getJsession_id())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
	        message.setContent("jsession_id不能为空");
	        return message;
		}
		if(vo.getMoney()==null || "".equals(vo.getMoney())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
	        message.setContent("money不能为空");
	        return message;
		}
		if(vo.getCount()==null || "".equals(vo.getCount())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
	        message.setContent("count不能为空");
	        return message;
		}
		if(vo.getValid_month()==null || "".equals(vo.getValid_month())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
	        message.setContent("valid_month不能为空");
	        return message;
		}
		if(vo.getRes_type()==null || "".equals(vo.getRes_type())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
	        message.setContent("res_type不能为空");
	        return message;
		}
		if(vo.getAcc_nbr()==null || "".equals(vo.getAcc_nbr())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
	        message.setContent("acc_nbr不能为空");
	        return message;
		}
		UocMessage loginMessage=operServDu.isLogin(vo.getJsession_id());
		if(!"0000".equals(loginMessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("效验登录信息失败");
			return message;
		}
		
		@SuppressWarnings("unchecked")
		Map<String,Object> oper_info=(Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String province_code = oper_info.get("province_code").toString();
		String city = oper_info.get("area_code").toString();
		String channel_id = oper_info.get("channel_id").toString();
		String channel_type = oper_info.get("channel_type").toString();
		String district = oper_info.get("district").toString();
		String oper_no = oper_info.get("oper_no").toString();
		String depart_no = oper_info.get("depart_no").toString();
		//字符串拼装
		JSONObject json = new JSONObject();
		json.put("channelId", channel_id);
		json.put("channelType", channel_type);
		json.put("city", city);
		json.put("district", district);
		json.put("operatorId", oper_no);
		json.put("province", province_code);
		json.put("opeSysType", "2");
		json.put("resType", vo.getRes_type());
		json.put("money", vo.getMoney());
		json.put("numId", vo.getAcc_nbr());
		json.put("serviceClassCode", "0050");
		json.put("count", vo.getCount());
		json.put("validTime", vo.getValid_month());
		json.put("validCalculateType", "0");
		json.put("payType", "0");
		String json_info = json.toJSONString();
		
		//logHalfYearPkgPo 
		//通过BASE0018服务调用aop订购半年包接口
		String interface_param_json = "{\"bizkey\":\"CS-018\",\"method\": \"ecaop.trades.sell.mob.comm.traffic.order\"}";
		UocMessage uocMessageAbilityPlat = abilityPlatformServDu.CallAbilityPlatform(json_info, "200", interface_param_json, "");
		if (!"0000".equals(uocMessageAbilityPlat.getRespCode())) {
			return uocMessageAbilityPlat;
		} else {
			String code = uocMessageAbilityPlat.getArgs().get("code").toString();
			String json_info_in = (String) uocMessageAbilityPlat.getArgs().get("json_info");
			if (!"200".equals(code)) {
				JSONObject jsonObject = JSONObject.parseObject(json_info_in);
				String resultStr = jsonObject.getString("detail");
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent(resultStr);
				return message;
			}
			message.addArg("abilityPlatJson", json_info_in);
		}
		//记录log_halfyear表
		String order_no = vo.getOrder_no();
		LogHalfYearPkgPo logHalfYearPkgPo = new LogHalfYearPkgPo();
		logHalfYearPkgPo.setId(getIdServDu.getId("createLogId", province_code, "*", ""));
		logHalfYearPkgPo.setOrder_no(vo.getOrder_no()==null||"".equals(vo.getOrder_no())?vo.getAcc_nbr():vo.getOrder_no());
		logHalfYearPkgPo.setProvince_code(province_code);
		logHalfYearPkgPo.setArea_code(city);
		logHalfYearPkgPo.setAcc_nbr(vo.getAcc_nbr());
		logHalfYearPkgPo.setAccept_oper_no(oper_no);
		logHalfYearPkgPo.setInsert_time(DateUtil.getSysDateString("yyyyMMddHHmmss"));
		logHalfYearPkgPo.setPart_month(Integer.toString(Integer.
				parseInt(DateUtil.getSysDateString("yyyyMMddHHmmss").substring(4,6))));
		logHalfYearPkgPo.setRes_count(vo.getCount());
		logHalfYearPkgPo.setRes_money(vo.getMoney());
		logHalfYearPkgPo.setRes_type(vo.getRes_type());
		logHalfYearPkgPo.setValid_month(vo.getValid_month());
		logHalfYearPkgPo.setAccept_depart_no(depart_no);
		//插入数据
		logHalfYearPkgServ.createLogHalfYearPkg(logHalfYearPkgPo);
		
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("调用AOP订购半年包成功");
		return message;
		
	}
}
