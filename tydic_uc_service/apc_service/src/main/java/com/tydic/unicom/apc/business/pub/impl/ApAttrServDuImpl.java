package com.tydic.unicom.apc.business.pub.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.apc.base.pub.interfaces.ApAttrServ;
import com.tydic.unicom.apc.base.pub.po.ApAttrPo;
import com.tydic.unicom.apc.business.constants.Constants;
import com.tydic.unicom.apc.business.pub.interfaces.ApAttrServDu;
import com.tydic.unicom.apc.business.pub.vo.ApAttrVo;
import com.tydic.unicom.apc.common.utils.GenerateIDUtils;
import com.tydic.unicom.apc.service.common.interfaces.ApcRedisServiceServDu;
import com.tydic.unicom.apc.service.common.interfaces.GetIsLoginServDu;
import com.tydic.unicom.webUtil.BusiMessage;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

/**
 * 触点中心服务<br>
 * APC0014-触点属性维护<br>
 * APC0015-触点属性查询<br>
 * 
 * @author ZhangCheng
 * @date 2017-04-24
 */
public class ApAttrServDuImpl implements ApAttrServDu {
	
	private static final Logger logger = LoggerFactory.getLogger(ApAttrServDuImpl.class);
	
	@Autowired
	private ApAttrServ apAttrServ;
	
	@Autowired
	private GetIsLoginServDu getIsLoginServDu;
	
	@Autowired
	private ApcRedisServiceServDu apcRedisServiceServDu;
	
	@Override
	public BusiMessage<?> saveApAttrInfo(ApAttrVo apAttrVo) {
		if(logger.isInfoEnabled()){
			logger.info("INFO [APC0014]==========>请求参数：{}",apAttrVo.toString());
		}
		
		BusiMessage<?> respBusiMessage = new BusiMessage<>();
		
		// 校验是否登录
		UocMessage resultUocMessage;
		try {
			resultUocMessage = getIsLoginServDu.GetIsLoginByJsessionId(apAttrVo.getJsession_id());
			if( !(RespCodeContents.SUCCESS.equals(resultUocMessage.getRespCode())) ){
				respBusiMessage.setCode(resultUocMessage.getRespCode());
				respBusiMessage.setMsg(resultUocMessage.getContent());
				return respBusiMessage;
			}
		} catch (Exception e) {
			respBusiMessage.setCode(RespCodeContents.SYSTEM_EXCEPTION);
			respBusiMessage.setMsg("登录校验异常");
			return respBusiMessage;
		}
		
		ApAttrPo apAttrPo = new ApAttrPo();
		BeanUtils.copyProperties(apAttrVo, apAttrPo);
		
		// 新增触点属性
		if(Constants.ADD.equals(apAttrVo.getOper_type())){
			apAttrPo.setId(GenerateIDUtils.getUUID());
			boolean result = apAttrServ.saveApAttr(apAttrPo);
			return this.getBusiResult(respBusiMessage, "新增触点属性", result);
		}
		
		// 修改触点属性
		if(Constants.UPDATE.equals(apAttrVo.getOper_type())){
			boolean result = apAttrServ.updateApAttr(apAttrPo);
			return this.getBusiResult(respBusiMessage, "修改触点属性", result);
		}
		
		// 删除触点属性
		if(Constants.DELETE.equals(apAttrVo.getOper_type())){
			boolean result = apAttrServ.removeApAttr(apAttrPo);
			return this.getBusiResult(respBusiMessage, "删除触点属性", result);
		}
		
		respBusiMessage.setCode(RespCodeContents.PARAM_ERROR);
		respBusiMessage.setMsg("请求参数 oper_type 输入错误");
		respBusiMessage.setSuccess(false);
		
		return respBusiMessage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BusiMessage<List<ApAttrVo>> getApAttrInfo(ApAttrVo apAttrVo) {
		if(logger.isInfoEnabled()){
			logger.info("INFO [APC0014]==========>请求参数：{}",apAttrVo.toString());
		}
		BusiMessage<List<ApAttrVo>> respBusiMessage = new BusiMessage<List<ApAttrVo>>();
		
		// 校验是否登录
		UocMessage resultUocMessage;
		try {
			resultUocMessage = getIsLoginServDu.GetIsLoginByJsessionId(apAttrVo.getJsession_id());
			if( !(RespCodeContents.SUCCESS.equals(resultUocMessage.getRespCode())) ){
				respBusiMessage.setCode(resultUocMessage.getRespCode());
				respBusiMessage.setMsg(resultUocMessage.getContent());
				return respBusiMessage;
			}
		} catch (Exception e) {
			respBusiMessage.setCode(RespCodeContents.SYSTEM_EXCEPTION);
			respBusiMessage.setMsg("登录校验异常");
			return respBusiMessage;
		}
		
		// 创建数据对象
		List<ApAttrVo> respApAttrVos = new ArrayList<ApAttrVo>();
		List<ApAttrPo> respApAttrPos = new ArrayList<ApAttrPo>();
		ApAttrPo apAttrPo = new ApAttrPo();
		BeanUtils.copyProperties(apAttrVo, apAttrPo);
		
		respApAttrPos = apAttrServ.listApAttrByApAttrPo(apAttrPo);
		if( null == respApAttrPos){
			respBusiMessage.setCode(RespCodeContents.SERVICE_FAIL);
			respBusiMessage.setMsg("查询数据为空");
			respBusiMessage.setSuccess(false);
			return respBusiMessage;
		}
		
		// 封装响应参数
		boolean respResult = false;
		if(respApAttrPos != null && respApAttrPos.size() > 0){
			for(ApAttrPo apAttrPoTemp : respApAttrPos){
				ApAttrVo apAttrVoTemp = new ApAttrVo();
				BeanUtils.copyProperties(apAttrPoTemp, apAttrVoTemp);
				respApAttrVos.add(apAttrVoTemp);
			}
			respBusiMessage.setData(respApAttrVos);
			respResult = true;
		}
		return (BusiMessage<List<ApAttrVo>>) this.getBusiResult(respBusiMessage, "查询触点属性", respResult);
	}
	
	@Override
	public BusiMessage<?> saveApAttrToRedis(ApAttrVo apAttrVo) {
		
		BusiMessage<List<ApAttrVo>> respBusiMessage = new BusiMessage<List<ApAttrVo>>();
		
		// 校验是否登录
		UocMessage resultUocMessage = null;
		try {
			resultUocMessage = getIsLoginServDu.GetIsLoginByJsessionId(apAttrVo.getJsession_id());
			if( !(RespCodeContents.SUCCESS.equals(resultUocMessage.getRespCode())) ){
				respBusiMessage.setCode(resultUocMessage.getRespCode());
				respBusiMessage.setMsg(resultUocMessage.getContent());
				return respBusiMessage;
			}
		} catch (Exception e) {
			respBusiMessage.setCode(RespCodeContents.SYSTEM_EXCEPTION);
			respBusiMessage.setMsg("登录校验异常");
			return respBusiMessage;
		}
		
		// 获取触点属性
		respBusiMessage = this.getApAttrInfo(apAttrVo);
		boolean respResult = respBusiMessage.getSuccess();
		if(respResult){
			respResult = this.saveApAttrListToRedis(respBusiMessage.getData());
			return this.getBusiResult(respBusiMessage, "触点属性写入缓存", respResult);
		}
		respBusiMessage.setMsg("触点属性写入缓存失败,"+respBusiMessage.getMsg());
		return respBusiMessage;
	}
	
	/**
	 * 将触点属性列表写入Redis
	 * @param apAttrVoList
	 * @return
	 */
	private boolean saveApAttrListToRedis(List<ApAttrVo> apAttrVoList){
		try {
			String respCode = apcRedisServiceServDu.addOrUpdateForApAttr(apAttrVoList).getRespCode();
			if(RespCodeContents.SUCCESS.equals(respCode)){
				return true;
			}
		} catch (Exception e) {
			if(logger.isInfoEnabled()){
				logger.info("INFO [APC0014]==========>Redis异常:{},异常堆栈:{}",e.getMessage(),e.getStackTrace().toString());
			}
			return false;
		}
		return false;
	}
	
	private BusiMessage<?> getBusiResult(BusiMessage<?> respBusiMessage,String msg,Boolean success){
		if(success){
			respBusiMessage.setCode(RespCodeContents.SUCCESS);
			respBusiMessage.setMsg(msg+"成功");
			respBusiMessage.setSuccess(success);
			return respBusiMessage;
		}
		respBusiMessage.setCode(RespCodeContents.SERVICE_FAIL);
		respBusiMessage.setMsg(msg+"失败");
		respBusiMessage.setSuccess(success);
		return respBusiMessage;
	}

	@Override
	public UocMessage queryApAttrByRedis(String ap_id) {
		logger.info("=================从缓存中获取触点配置ap_id:" + ap_id);
		UocMessage message=new UocMessage();
		try {
			UocMessage redisInfo = apcRedisServiceServDu.queryForApAttr(ap_id);
			if (RespCodeContents.SUCCESS.equals(redisInfo.getRespCode())) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) redisInfo.getArgs().get("apAttrInfo");
				message.
				setRespCode(RespCodeContents.SUCCESS);
				message.setContent("根据触点id获取触点信息成功");
				message.addArg("apAttrInfo", map);
			}else {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent(redisInfo.getContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("根据触点id获取触点属性异常");
		}
		return message;
	}
}
