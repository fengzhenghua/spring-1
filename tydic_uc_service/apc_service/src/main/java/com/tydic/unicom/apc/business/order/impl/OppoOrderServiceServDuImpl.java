package com.tydic.unicom.apc.business.order.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import com.tydic.unicom.apc.base.order.interfaces.InfoOrderAttrServ;
import com.tydic.unicom.apc.base.order.interfaces.InfoOrderServ;
import com.tydic.unicom.apc.base.order.po.InfoOrderAttrPo;
import com.tydic.unicom.apc.base.order.po.InfoOrderPo;
import com.tydic.unicom.apc.business.order.interfaces.OppoOrderServiceServDu;
import com.tydic.unicom.apc.business.order.vo.OppoOrderUpdateResVo;
import com.tydic.unicom.apc.service.common.interfaces.ApcAbilityPlatformServDu;
import com.tydic.unicom.apc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.apc.service.order.interfaces.InfoOrderAttrServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class OppoOrderServiceServDuImpl implements OppoOrderServiceServDu{

	private static Logger log = Logger.getLogger(OppoOrderServiceServDuImpl.class);
	
	@Autowired
	private InfoOrderServ infoOrderServ;
	
	@Autowired
	private InfoOrderAttrServDu infoOrderAttrServDu;
	
	@Autowired
	private ApcAbilityPlatformServDu apcAbilityPlatformServDu;
	
	@Autowired
	private InfoOrderAttrServ infoOrderAttrServ;
	
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	
	
	@Override
	public UocMessage getApOrderAttrInfo(String apOrderId) throws Exception {
		log.info("=================获取触点订单属性值=================");
		UocMessage uocMessage = new UocMessage();
		uocMessage = infoOrderAttrServDu.getApOrderAttrInfo(apOrderId);
		return uocMessage;
	}
	
	@Override
	public UocMessage createApOrder(OppoOrderUpdateResVo oppoOrderUpdateResVo) throws Exception {
		log.info("=================创建触点订单=================");
		UocMessage uocMessage = new UocMessage();
		String orderId = "";
		//生成订单号
		orderId = getOrderId();
		log.info("=====================触点订单号："+orderId);
		Map<String,Object> map = oppoOrderUpdateResVo.convertThis2Map();
		Iterator<String> it = map.keySet().iterator();
		List<InfoOrderAttrPo> infoOrderAttrAddlist = new ArrayList<InfoOrderAttrPo>(); 
		while(it.hasNext()){
			String key = it.next();
			String value = map.get(key).toString();
			if(!key.equals("MAX_SIZE") && !StringUtils.isEmpty(value)){
				InfoOrderAttrPo infoOrderAttrPoAdd = setInfoOrderAttr(orderId,"100",key,value);
				infoOrderAttrAddlist.add(infoOrderAttrPoAdd);
			}
		}
		//插入触点订单属性表
		UocMessage addInfoOrderAttrResult = infoOrderAttrServDu.addInfoOrderAttrList(infoOrderAttrAddlist);
		if(!RespCodeContents.SUCCESS.equals(addInfoOrderAttrResult.getRespCode())){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("插入订单属性表数据失败！！！");
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("插入订单属性表数据成功！！！");
			uocMessage.addArg("ap_order_id", orderId);
		}
		return uocMessage;
	}
	
	@Override
	public UocMessage createOppoOrderInfo(String oper_no, String tele_type,String order_source) throws Exception {
		log.info("============（oppo）创建订单信息=============");
		UocMessage uocMessage = new UocMessage();
		String orderId = "";
		boolean isok = false;
		
		//校验参数
		if(StringUtils.isEmpty(oper_no)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("操作员编码不能为空");
			return uocMessage;
		}		
		if(StringUtils.isEmpty(tele_type)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("电信不能为空");
			return uocMessage;
		}
		if(StringUtils.isEmpty(order_source)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("订单来源不能为空");
			return uocMessage;
		}
		
		//生成订单号
		orderId = getOrderId();
		log.info("=====================订单号："+orderId);
		
		//插入订单属性表
		List<InfoOrderAttrPo> infoOrderAttrAddlist = new ArrayList<InfoOrderAttrPo>();
		InfoOrderAttrPo InfoOrderAttrPo1 = setInfoOrderAttr(orderId,"100","oper_no",oper_no);
		InfoOrderAttrPo InfoOrderAttrPo2 = setInfoOrderAttr(orderId,"100","tele_type",tele_type);
		InfoOrderAttrPo InfoOrderAttrPo3 = setInfoOrderAttr(orderId,"100","order_source",order_source);
		InfoOrderAttrPo InfoOrderAttrPo4 = setInfoOrderAttr(orderId,"100","compare_fail_times","0");
		InfoOrderAttrPo InfoOrderAttrPo5 = setInfoOrderAttr(orderId,"100","order_status","A10");
		InfoOrderAttrPo InfoOrderAttrPo6 = setInfoOrderAttr(orderId,"100","video_server_id","N");
		InfoOrderAttrPo InfoOrderAttrPo7 = setInfoOrderAttr(orderId,"100","video_live_sim","0");
		InfoOrderAttrPo InfoOrderAttrPo8 = setInfoOrderAttr(orderId,"100","video_file_name","N");
		infoOrderAttrAddlist.add(InfoOrderAttrPo1);
		infoOrderAttrAddlist.add(InfoOrderAttrPo2);
		infoOrderAttrAddlist.add(InfoOrderAttrPo3);
		infoOrderAttrAddlist.add(InfoOrderAttrPo4);
		infoOrderAttrAddlist.add(InfoOrderAttrPo5);
		infoOrderAttrAddlist.add(InfoOrderAttrPo6);
		infoOrderAttrAddlist.add(InfoOrderAttrPo7);
		infoOrderAttrAddlist.add(InfoOrderAttrPo8);
		UocMessage addInfoOrderAttrResult = infoOrderAttrServDu.addInfoOrderAttrList(infoOrderAttrAddlist);
		if(!RespCodeContents.SUCCESS.equals(addInfoOrderAttrResult.getRespCode())){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("插入订单属性表数据失败！！！");
			return uocMessage;
		}
		
		//插入订单表
		InfoOrderPo infoOrderPo = new InfoOrderPo();
		infoOrderPo.setOrder_id(orderId);
		infoOrderPo.setExpress_flag("N");
		infoOrderPo.setOrder_status("A10");
		infoOrderPo.setOrder_type("103");
		infoOrderPo.setCreate_date(new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(new Date()));
		infoOrderPo.setPay_flag("N");
		infoOrderPo.setTele_type(tele_type);
		infoOrderPo.setOrder_source(order_source);
		infoOrderPo.setCreate_operator_id(oper_no);
		isok = infoOrderServ.create(infoOrderPo);
		if(!isok){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("插入订单表数据失败！！！");
			return uocMessage;
		}
		
		uocMessage.setRespCode(RespCodeContents.SUCCESS);
		uocMessage.addArg("order_id", orderId);
		uocMessage.setContent("创建订单成功");
		return uocMessage;
	}
	
	/**
	 * 生成订单号
	 * */
	public String getOrderId() throws Exception{
		String orderId = "";
		orderId = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		orderId = orderId + GetRandomNum(5);
		return orderId;
	}
	
	/**
	 * 获取指定位数的随机数
	 * */
	public String GetRandomNum(int length) throws Exception{
		String randomNum = "";
		for(int i=0;i<length;i++){
			randomNum += (int)(Math.random()*10);
		}
		return randomNum;
	}
	
	/**
	 * 设置订单属性表参数
	 * */
	public InfoOrderAttrPo setInfoOrderAttr(String orderId,String attrType,String attrId,String attrValue) throws Exception{
		InfoOrderAttrPo infoOrderAttrPo = new InfoOrderAttrPo();
		
		String orderAttrId = String.valueOf(System.currentTimeMillis())+GetRandomNum(5);
		infoOrderAttrPo.setOrder_attr_id(orderAttrId);
		infoOrderAttrPo.setOrder_id(orderId);
		infoOrderAttrPo.setAttr_type(attrType);
		infoOrderAttrPo.setAttr_id(attrId);
		infoOrderAttrPo.setAttr_value(attrValue);
		
		return infoOrderAttrPo;
	}

	@Override
	public UocMessage updateOppoOrderInfo(OppoOrderUpdateResVo oppoOrderUpdateResVo,String order_id) throws Exception {
		Map<String, Object> mapRes = oppoOrderUpdateResVo.convertThis2Map();
		
		//设置订单属性表参数
		List<InfoOrderAttrPo> updateOrderAttrList = new ArrayList<InfoOrderAttrPo>();
		Iterator<String> iterator = mapRes.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			if(!key.equals("MAX_SIZE")){
				String value = mapRes.get(key).toString();
				if(!value.equals("")){
					InfoOrderAttrPo infoOrderAttrPoAdd = setInfoOrderAttr(order_id,"100",key,value);
					updateOrderAttrList.add(infoOrderAttrPoAdd);
				}
			}
 		}
		//更新订单属性表
		return infoOrderAttrServDu.updateInfoOrderAttrList(updateOrderAttrList);	
	}

	@Override
	public UocMessage queryOrderInfoFromUoc(String sim_id, String acc_nbr,String contact_tel,String oper_no) throws Exception {
		
		UocMessage uocMessage = new UocMessage();
		//校验参数
		if(StringUtils.isEmpty(oper_no)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("操作员编码不能为空");
			return uocMessage;
		}
		if(StringUtils.isEmpty(sim_id)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("SIM卡号不能为空");
			return uocMessage;
		}
		if(StringUtils.isEmpty(acc_nbr)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("卡号不能为空");
			return uocMessage;
		}
		
		//拼装报文
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("SERVICE_NAME", "getServiceOrderNo");
		Map<String,String> map = new HashMap<String,String>();
		map.put("jsession_id", oper_no);
		map.put("sim_id", sim_id);
		map.put("contact_tel", contact_tel);
		jsonObj.put("params", map);
		log.debug("=================通过能力平台去查询中台订单信息(报文):"+jsonObj.toString());
		uocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(jsonObj.toString(), "", "UOC");
		return uocMessage;
	}

	@Override
	public UocMessage updateOrderInfoFromUoc(String order_id) throws Exception {
		UocMessage uocMessage = new UocMessage();
		String serv_order_no = "";
		String front_file_name = "";
		String back_file_name = "";
		String video_live_sim = "";
		String video_file_name = "";
		String oper_no = "";
		String compare_result = "1";
		
		//校验参数
		if(StringUtils.isEmpty(order_id)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("订单号不能为空");
			return uocMessage;
		}
		
		//查询订单属性表信息
		InfoOrderAttrPo infoOrderAttrPo = new InfoOrderAttrPo();
		infoOrderAttrPo.setOrder_id(order_id);
		List<InfoOrderAttrPo> infoOrderAttrList = infoOrderAttrServ.queryInfoOrderAttrByOrderId(infoOrderAttrPo);
		if(infoOrderAttrList != null && infoOrderAttrList.size()>0){
			for(int i=0;i<infoOrderAttrList.size();i++){
				if("serv_order_no".equals(infoOrderAttrList.get(i).getAttr_id())){
					serv_order_no = infoOrderAttrList.get(i).getAttr_value();
				}
				else if("front_file_name".equals(infoOrderAttrList.get(i).getAttr_id())){
					front_file_name = infoOrderAttrList.get(i).getAttr_value();
				}
				else if("back_file_name".equals(infoOrderAttrList.get(i).getAttr_id())){
					back_file_name = infoOrderAttrList.get(i).getAttr_value();
				}
				else if("video_live_sim".equals(infoOrderAttrList.get(i).getAttr_id())){
					video_live_sim = infoOrderAttrList.get(i).getAttr_value();
				}
				else if("video_file_name".equals(infoOrderAttrList.get(i).getAttr_id())){
					video_file_name = infoOrderAttrList.get(i).getAttr_value();
				}
				else if("oper_no".equals(infoOrderAttrList.get(i).getAttr_id())){
					oper_no = infoOrderAttrList.get(i).getAttr_value();
				}
			}
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("查询订单属性表失败");
			return uocMessage;
		}
		
		//相似度比较，介于最小阀值和最大阀值之间，手工处理，大于等于最大阀值，自动
		int org_sim=0;
		int min_sim=0;
		int max_sim=0;
		if(!StringUtils.isEmpty(video_live_sim)){
			org_sim=Integer.parseInt(video_live_sim);
			min_sim=Integer.parseInt(propertiesParamVo.getMin_sim());
			max_sim=Integer.parseInt(propertiesParamVo.getMax_sim());
		}else {
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("查询订单属性表所保存对比相似度为空！！1");
			return uocMessage;
		}
		
		if(org_sim>=min_sim&&org_sim<max_sim){
			compare_result = "1";
		}else if(org_sim>=max_sim){
			compare_result = "0";
		}
		
		String http = propertiesParamVo.getOppoPicHttp();
//		front_file_name = http + "/" + front_file_name;
//		back_file_name = http + "/" + back_file_name;
//		video_file_name = http + "/" + video_file_name;
		
		//拼装报文
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("SERVICE_NAME", "compareResultNotify");
		Map<String,String> map = new HashMap<String,String>();
		map.put("compare_result", compare_result);
		map.put("serv_order_no", serv_order_no);
		map.put("cert_face_url", front_file_name);
		map.put("cert_video_url", video_file_name);
		map.put("cert_back_url", back_file_name);
		map.put("compare_rate", video_live_sim);
		map.put("jsession_id", oper_no);
		jsonObj.put("params", map);
		log.debug("=================通过能力平台去查询中台订单信息(报文):"+jsonObj.toString());
		uocMessage = apcAbilityPlatformServDu.CallApcAbilityPlatform(jsonObj.toString(), "", "UOC");
		return uocMessage;
	}

}
