package com.tydic.unicom.upc.service.capacity.impl;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;
import com.open.rest.api.sdk.client.OrsException;
import com.tydic.unicom.upc.vo.code.CapacityParaVo;
import com.tydic.unicom.upc.vo.code.InfoBusiVo;
import com.tydic.unicom.upc.service.capacity.CapacityConfigs;
import com.tydic.unicom.upc.service.code.interfaces.CapacityParaDu;
import com.tydic.unicom.upc.service.code.interfaces.InfoBusiServDu;

public class SendMessageToCapacityDuServ implements SendMessageToCapacityDu{
	
	private static final Logger logger = Logger.getLogger(SendMessageToCapacityDuServ.class);
	@Autowired
	private InfoBusiServDu infoBusiServDu;
	
	@Autowired
	private CapacityParaDu capacityParaDu;
	
	//向能力平台发送信息
	public void sendMessage(String message,String busi_id){
		logger.info("向业务系统为busi_id = " +busi_id + "发送通知消息" + message);
		if(busi_id == null || "".equals(busi_id)){
			throw new IllegalArgumentException("找不到busi_id");
		}
		try {
			InfoBusiVo infoBusiVo = new InfoBusiVo();
			infoBusiVo.setBusi_id(busi_id);
			
			infoBusiVo = infoBusiServDu.queryByBusiId(infoBusiVo);
			if(infoBusiVo == null){
				throw new IllegalArgumentException("为找到busi_id为" + busi_id +"的系统参数");
			}
			JSONObject data =JSONObject.fromObject(message);
			if("".equals(infoBusiVo.getAopname())||infoBusiVo.getAopname() == null){
				throw new IllegalArgumentException("能力平台为配置参数");
			}
			data.put("SERVICE_NAME", infoBusiVo.getAopname());
			int count  = setCapacityConfigs(infoBusiVo.getBusi_id(),infoBusiVo.getAopname());
			
			if(count == 0){
				throw new IllegalArgumentException("业务系统的id 或者接口的名 为空");
			}
			// 直接实例化
			QuerySubsBaseInfoClient client = new QuerySubsBaseInfoClient();
			
			JSONObject response;
			response = client.performRequest(data);
			logger.info("通知能力平完成，返回信息为：");
			logger.info(response.toString());
		}catch (JSONException e) {
			System.out.println("响应报文异常，非json格式：" + e.getMessage());
		} catch (OrsException e) {
			System.out.println("响应报文异常：" + e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("异常信息：" + e.getMessage());
		}
	}
	
	
	// 连接配置文件
	public int setCapacityConfigs(String busi_id ,String aopname){
		if(busi_id == null||"".equals(busi_id) ){
			return 0;
		}
		if(aopname == null || "".equals(aopname)){
			return 0;
		}
		CapacityParaVo vo =  new CapacityParaVo();
		vo.setBusi_id(busi_id);
		vo.setAopname(aopname);
		vo = capacityParaDu.getCapacityPara(busi_id,aopname);
		if(vo == null){
			throw new IllegalArgumentException("找不到能力平台对应的参数");
		}
		
		logger.info("或者能力平台参数成功  = " + vo.toString());
		CapacityConfigs.APP_KEY = vo.getApp_key();
		CapacityConfigs.SECRET = vo.getSecret();
		CapacityConfigs.COMMON_URL = vo.getCommon_url();
		CapacityConfigs.FORMAT = vo.getFromat();
		CapacityConfigs.TOKEN_QuerySubsBaseInfo = vo.getToken();
		CapacityConfigs.GRAND = vo.getGrand();
		return 1;
	}
	
	
	/*public QuerySubsBaseInfoClient getClient(String busi_id,String aopname){
		
		if(busi_id == null||"".equals(busi_id) ){
			return null;
		}
		if(aopname == null || "".equals(aopname)){
			return null;
		}
		CapacityParaVo vo =  new CapacityParaVo();
		vo.setBusi_id(busi_id);
		vo.setAopname(aopname);
		vo = capacityParaDu.getCapacityPara(vo);
		if(vo == null){
			throw new IllegalArgumentException("找不到能力平台对应的参数");
		}
		String app_key = vo.getApp_key();
		String common_url = vo.getCommon_url();
		String fromat = vo.getFromat();
		String grand = vo.getGrand();
		String token = vo.getToken();
		String secret = vo.getSecret();
		QuerySubsBaseInfoClient client = new QuerySubsBaseInfoClient(app_key,fromat,common_url,secret,token,grand);
		return client;
	}*/
	
	public static void main(String[] args) {

		// 构建业务入参
		JSONObject data = new JSONObject();
		data.put("SERVICE_NAME","PayCenterBack");
		data.put("result_code", "0");
		data.put("result_msg", "1");
		data.put("req_type", "2");
		data.put("req_way", "3");
		data.put("out_order_id", "4");
		data.put("pay_type", "5");
		data.put("total_fee", "6");
		data.put("real_fee", "7");
		data.put("remark", "1");
		
		System.out.println(data);
		
		// 创建服务客户端
		QuerySubsBaseInfoClient client = new QuerySubsBaseInfoClient();

		JSONObject response;
		try {
			// 执行发送
			response = client.performRequest(data);
			System.out.println(response.toString());
		} catch (JSONException e) {
			System.out.println("响应报文异常，非json格式：" + e.getMessage());
		} catch (OrsException e) {
			System.out.println("响应报文异常：" + e.getMessage());
		}

	}

}
