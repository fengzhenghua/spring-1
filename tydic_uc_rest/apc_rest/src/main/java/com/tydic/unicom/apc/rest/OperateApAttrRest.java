package com.tydic.unicom.apc.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tydic.unicom.apc.business.pub.interfaces.ApAttrServDu;
import com.tydic.unicom.apc.business.pub.vo.ApAttrVo;
import com.tydic.unicom.crm.web.uss.constants.ControllerMappings;
import com.tydic.unicom.crm.web.uss.constants.UrlsMappings;
import com.tydic.unicom.webUtil.BusiMessage;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

import net.sf.json.JSONObject;

/**
 * <p>
 * 触点属性相关REST
 * </p>
 * APC0014-触点属性维护-REST<br>
 * APC0015-触点属性查询-REST<br>
 * @author ZhangCheng
 * @date 2017-04-24
 * @version V1.0
 */
@RestController
@RequestMapping(value = ControllerMappings.OPERATE_AP_ATTR_REST)
public class OperateApAttrRest {
	
	private static final Logger logger = LoggerFactory.getLogger(OperateApAttrRest.class);
	
	@Autowired
	private ApAttrServDu apAttrServDu;
	
	/** APC0014-触点属性维护-REST */
	@RequestMapping(value = UrlsMappings.SAVE_AP_ATTR_INFO,method=RequestMethod.POST)
	public UocMessage saveApAttrInfo(String jsession_id,String json_info,String oper_type){
		if(logger.isInfoEnabled()){
			logger.info("INFO [APC0014REST]==========>请求参数：jsession_id:{},json_info:{},oper_type:{}",jsession_id,json_info,oper_type);
		}
		
		UocMessage respUocMessage = new UocMessage();
		
		// 请求参数非空校验
		if(StringUtils.isEmpty(jsession_id)){
			respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respUocMessage.setContent("请求参数 jsession_id 不能为空");
			return respUocMessage;
		}
		if(StringUtils.isEmpty(json_info)){
			respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respUocMessage.setContent("请求参数 json_info 不能为空");
			return respUocMessage;
		}
		if(StringUtils.isEmpty(oper_type)){
			respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respUocMessage.setContent("请求参数 oper_type 不能为空");
			return respUocMessage;
		}
		
		// 业务参数非空校验
		ApAttrVo apAttrVo = new ApAttrVo();
		JSONObject jsonObject = JSONObject.fromObject(json_info);
		apAttrVo = (ApAttrVo) JSONObject.toBean(jsonObject, apAttrVo.getClass());
		apAttrVo.setJsession_id(jsession_id);
		apAttrVo.setOper_type(oper_type);
		
		
		
		// 调触点属性维护服务
		BusiMessage<?> respBusiMessage = null;
		try{
			respBusiMessage = apAttrServDu.saveApAttrInfo(apAttrVo);			
		}catch(Exception ex){
			respUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			respUocMessage.setContent("触点属性维护服务异常");
			return respUocMessage;
		}
		
		// 封装响应消息
		if( respBusiMessage != null ){
			respUocMessage.setRespCode(respBusiMessage.getCode()+"");
			respUocMessage.setContent(respBusiMessage.getMsg());
			return respUocMessage;
		}
		respUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
		respUocMessage.setContent("触点属性维护服务调用失败");
		return respUocMessage;
	}
	
	/** APC0015-触点属性查询-REST */
	@RequestMapping(value = UrlsMappings.GET_AP_ATTR_INFO,method=RequestMethod.POST)
	public UocMessage getApAttrInfo(String jsession_id,String json_info){
		if(logger.isInfoEnabled()){
			logger.info("INFO [APC0015REST]==========>请求参数：jsession_id:{},json_info:{}",jsession_id,json_info);
		}
		
		UocMessage respUocMessage = new UocMessage();
		
		// 请求参数非空校验
		if(StringUtils.isEmpty(jsession_id)){
			respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respUocMessage.setContent("请求参数 jsession_id 不能为空");
			return respUocMessage;
		}
		if(StringUtils.isEmpty(json_info)){
			respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respUocMessage.setContent("请求参数 json_info 不能为空");
			return respUocMessage;
		}
		
		ApAttrVo apAttrVo = new ApAttrVo();
		JSONObject jsonObject = JSONObject.fromObject(json_info);
		apAttrVo = (ApAttrVo) JSONObject.toBean(jsonObject, apAttrVo.getClass());
		apAttrVo.setJsession_id(jsession_id);
		
		// 调触点属性查询服务
		BusiMessage<List<ApAttrVo>> respBusiMessage = null;
		try{
			respBusiMessage = apAttrServDu.getApAttrInfo(apAttrVo);
		}catch(Exception ex){
			respUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			respUocMessage.setContent("触点属性查询服务异常");
			return respUocMessage;
		}
		
		// 封装响应消息
		if( respBusiMessage != null ){
			respUocMessage.setRespCode(respBusiMessage.getCode()+"");
			respUocMessage.setContent(respBusiMessage.getMsg());
			respUocMessage.addArg("apattr_list", respBusiMessage.getData());
			return respUocMessage;
		}
		respUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
		respUocMessage.setContent("触点属性查询服务调用失败");
		return respUocMessage;
	}
	
	/** 触点属性缓存-REST */
	@RequestMapping(value = UrlsMappings.REFRESH_AP_ATTR_REDIS,method=RequestMethod.POST)
	public UocMessage refreshApAttrRedis(String jsession_id,String ap_id){
		if(logger.isInfoEnabled()){
			logger.info("INFO [触点属性缓存]==========>请求参数：jsession_id:{},ap_id:{}",jsession_id,ap_id);
		}
		
		UocMessage respUocMessage = new UocMessage();
		
		// 请求参数非空校验
		if(StringUtils.isEmpty(jsession_id)){
			respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respUocMessage.setContent("请求参数 jsession_id 不能为空");
			return respUocMessage;
		}
		if(StringUtils.isEmpty(ap_id)){
			respUocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			respUocMessage.setContent("请求参数 ap_id 不能为空");
			return respUocMessage;
		}
		
		ApAttrVo apAttrVo = new ApAttrVo();
		apAttrVo.setAp_id(ap_id);
		apAttrVo.setJsession_id(jsession_id);
		
		// 调触点属性写缓存
		BusiMessage<?> respBusiMessage = null;
		try{
			respBusiMessage = apAttrServDu.saveApAttrToRedis(apAttrVo);
		}catch(Exception ex){
			respUocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			respUocMessage.setContent("触点属性写入缓存异常");
			return respUocMessage;
		}
		
		// 封装响应消息
		if( respBusiMessage != null ){
			respUocMessage.setRespCode(respBusiMessage.getCode()+"");
			respUocMessage.setContent(respBusiMessage.getMsg());
			return respUocMessage;
		}
		respUocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
		respUocMessage.setContent("触点属性写入缓存服务调用失败");
		return respUocMessage;
	}
	
	/**
	 * 根据触点id从缓存中获取触点属性信息
	 * @param ap_id
	 * @return
	 */
	@RequestMapping(value = UrlsMappings.QUERY_AP_ATTR_BY_REDIS,method=RequestMethod.POST)
	public UocMessage queryApAttrByRedis(String ap_id){
		UocMessage uocMessage = new UocMessage();
		try {
			uocMessage = apAttrServDu.queryApAttrByRedis(ap_id);
			return uocMessage;
		} catch (Exception e) {
			e.printStackTrace();
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("根据触点id获取触点信息异常");
			return uocMessage;
		}
	}
}
