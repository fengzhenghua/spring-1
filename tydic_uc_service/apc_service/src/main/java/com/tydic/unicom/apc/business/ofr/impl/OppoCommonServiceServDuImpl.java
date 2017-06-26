package com.tydic.unicom.apc.business.ofr.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.apc.base.order.interfaces.InfoOrderAttrServ;
import com.tydic.unicom.apc.base.order.po.InfoOrderAttrPo;
import com.tydic.unicom.apc.base.pub.interfaces.CodeAreaServ;
import com.tydic.unicom.apc.base.pub.interfaces.CodeListServ;
import com.tydic.unicom.apc.base.pub.po.CodeAreaPo;
import com.tydic.unicom.apc.base.pub.po.CodeListPo;
import com.tydic.unicom.apc.business.ofr.interfaces.OppoCommonServiceServDu;
import com.tydic.unicom.apc.business.ofr.vo.CodeAreaVo;
import com.tydic.unicom.apc.service.order.interfaces.InfoOrderAttrServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class OppoCommonServiceServDuImpl implements OppoCommonServiceServDu{

	private static Logger logger = Logger.getLogger(OppoCommonServiceServDuImpl.class);
	
	@Autowired
	private CodeListServ codeListServ;

	@Autowired
	private CodeAreaServ codeAreaServ;
	
	@Autowired
	private InfoOrderAttrServ infoOrderAttrServ;
	
	@Autowired
	private InfoOrderAttrServDu infoOrderAttrServDu;
	
	@Override
	public UocMessage getProvinceCode() throws Exception {
		UocMessage uocMessage = new UocMessage();
		List<CodeListPo> provinceCodeList = codeListServ.queryCodeListByTypeCode("province_code");
		if(provinceCodeList != null && provinceCodeList.size()>0){
			List<CodeListPo> provinceCodeListValueList = codeListServ.queryCodeListByTypeCode("province_code_value");
			if(provinceCodeListValueList != null && provinceCodeListValueList.size()>0){
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.setContent("获取省份编码成功");
				uocMessage.addArg("province_code", provinceCodeList.get(0).getCode_id());
				uocMessage.addArg("province_code_value", provinceCodeListValueList.get(0).getCode_id());
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("获取省份编码失败");
			}
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取省份编码失败");
		}
		return uocMessage;
	}

	@Override
	public UocMessage getAreaInfo(CodeAreaVo codeAreaVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		
		CodeAreaPo codeAreaPo = new CodeAreaPo();
		BeanUtils.copyProperties(codeAreaVo, codeAreaPo);
		List<CodeAreaPo> list = codeAreaServ.queryCodeAreaByParentAreaCode(codeAreaPo);
		
		if(list != null && list.size()>0){
			List<CodeAreaVo> listResult = new ArrayList<CodeAreaVo>();
			for(int i=0;i<list.size();i++){
				CodeAreaVo codeAreaVoTemp = new CodeAreaVo();
				BeanUtils.copyProperties(list.get(i), codeAreaVoTemp);
				listResult.add(codeAreaVoTemp);
			}
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("获取区域信息成功");
			uocMessage.addArg("area_info", listResult);
			return uocMessage;
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取区域信息失败");
			return uocMessage;
		}
	}

	@Override
	public UocMessage getApcOrderId(String serv_order_no) throws Exception {
		UocMessage uocMessage = new UocMessage();
		
		InfoOrderAttrPo infoOrderAttrPo = new InfoOrderAttrPo();
		infoOrderAttrPo.setAttr_id("serv_order_no");
		infoOrderAttrPo.setAttr_value(serv_order_no);
		infoOrderAttrPo = infoOrderAttrServ.queryInfoOrderAttrByAttrIdAndAttrValue(infoOrderAttrPo);
		if(infoOrderAttrPo != null){
			//判断是否已经激活过
			InfoOrderAttrPo po =new InfoOrderAttrPo();
			po.setAttr_id("order_status");
			po.setOrder_id(infoOrderAttrPo.getOrder_id());
			InfoOrderAttrPo poInfo =infoOrderAttrServ.queryInfoOrderAttrByAttrIdAndOrderId(po);
			if("A70".equals(poInfo.getAttr_value())){
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("该卡已激活过！");
				return uocMessage;
			}
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("查询触点订单号成功");
			uocMessage.addArg("order_id", infoOrderAttrPo.getOrder_id());
			return uocMessage;
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("查询触点订单号失败");
			return uocMessage;
		}
	}

	@Override
	public UocMessage updateCallBackApcOrder(String json_info) throws Exception {
		UocMessage uocMessage = new UocMessage();
		try {
			JSONObject jsonObj = JSONObject.fromObject(json_info);
			JSONObject params =  jsonObj.getJSONObject("params");
			//订单号
			//String order_no = (String)params.get("order_no");
			//订单类型 100销售订单、101服务订单
			//String order_type = (String)params.get("order_type");
			//触点流水号
			String accept_no = (String)params.get("accept_no");
			//SIM卡号
			String sim_id = (String)params.get("sim_id");
			
			if(!StringUtils.isEmpty(accept_no)){
				//触点订单号号不为空，则直接根据触点订单号更新
				//记录info_order_attr
				List<InfoOrderAttrPo> attrInfoList = new ArrayList<>();
				
				InfoOrderAttrPo orderAttr1 = new InfoOrderAttrPo();
				orderAttr1.setOrder_id(accept_no);
				orderAttr1.setAttr_type("200");
				orderAttr1.setAttr_id("sim_id");
				orderAttr1.setAttr_value(sim_id);
				attrInfoList.add(orderAttr1);
				
				//单独保存卡号后8位
				InfoOrderAttrPo orderAttr2 = new InfoOrderAttrPo();
				orderAttr2.setOrder_id(accept_no);
				orderAttr2.setAttr_type("200");
				orderAttr2.setAttr_id("sim_id_sub");
				orderAttr2.setAttr_value(sim_id.substring(sim_id.length()- 8,sim_id.length()));
				attrInfoList.add(orderAttr2);
				
				uocMessage=infoOrderAttrServDu.updateInfoOrderAttrList(attrInfoList);
				if(RespCodeContents.SUCCESS.equals(uocMessage.getRespCode())){
					uocMessage.setRespCode(RespCodeContents.SUCCESS);
					uocMessage.setContent("更新触点订单成功！！！");
				}else {
					uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocMessage.setContent("更新触点订单失败！！！");
				}
			}else {
				uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
				uocMessage.setContent("没有传触点流水！！！");
			}
		} catch (Exception e) {
			uocMessage.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			uocMessage.setContent("更新触点订单异常："+e.toString());
			return uocMessage;
		}
		
		return uocMessage;
	}

	@Override
	public UocMessage getApcInfoBySimAndDeviceNumber(String sim_id,String acc_nbr, String contact_phone) throws Exception {
		UocMessage uocMessage = new UocMessage();
		//校验入参
		if(StringUtils.isEmpty(sim_id)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("卡号后八位不能为空！！！");
			return uocMessage;
		}
		if(StringUtils.isEmpty(acc_nbr)){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("手机号码不能为空！！！");
			return uocMessage;
		}
		String order_id="";
		String order_id_bysim="";
		String order_id_bynum="";
		
		InfoOrderAttrPo order_info_sim = new InfoOrderAttrPo();
		order_info_sim.setAttr_id("sim_id_sub");
		order_info_sim.setAttr_value(sim_id);
		order_info_sim = infoOrderAttrServ.queryInfoOrderAttrByAttrIdAndAttrValue(order_info_sim);
		if(order_info_sim != null){
			order_id_bysim=order_info_sim.getOrder_id();
			
			InfoOrderAttrPo order_info_number = new InfoOrderAttrPo();
			order_info_number.setAttr_id("acc_nbr");
			order_info_number.setAttr_value(acc_nbr);
			order_info_number = infoOrderAttrServ.queryInfoOrderAttrByAttrIdAndAttrValue(order_info_number);
			
			if(order_info_number!=null){
				order_id_bynum=order_info_number.getOrder_id();
				if(!StringUtils.isEmpty(order_id_bynum)&&order_id_bynum.equals(order_id_bysim)){
					order_id=order_id_bynum;
					//查询订单属性表信息
					InfoOrderAttrPo infoOrderAttrPo = new InfoOrderAttrPo();
					infoOrderAttrPo.setOrder_id(order_id);
					List<InfoOrderAttrPo> infoOrderAttrList = infoOrderAttrServ.queryInfoOrderAttrByOrderId(infoOrderAttrPo);
					if(infoOrderAttrList != null && infoOrderAttrList.size()>0){
						uocMessage.setRespCode(RespCodeContents.SUCCESS);
						uocMessage.setContent("查询触点订单信息成功");
						for(int i=0;i<infoOrderAttrList.size();i++){
							if("serv_order_no".equals(infoOrderAttrList.get(i).getAttr_id())){
								uocMessage.addArg("serv_order_no", infoOrderAttrList.get(i).getAttr_value());
							}else if("oper_no".equals(infoOrderAttrList.get(i).getAttr_id())){
								uocMessage.addArg("oper_no", infoOrderAttrList.get(i).getAttr_value());
							}
						}
						uocMessage.addArg("order_id", order_id);
						
					}else {
						uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
						uocMessage.setContent("查询触点订单信息失败");
						return uocMessage;
					}
				}
			}else {
				uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
				uocMessage.setContent("查询触点订单信息失败");
				return uocMessage;
			}
		}else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("查询触点订单信息失败");
			return uocMessage;
		}
		
		return uocMessage;
	}

	@Override
	public UocMessage getAllProvinceInfo() throws Exception {
		UocMessage uocMessage = new UocMessage();
		CodeAreaPo codeAreaPoQuery = new CodeAreaPo();
		List<CodeAreaPo> list = codeAreaServ.queryCodeAreaAllProvinceInfo(codeAreaPoQuery);
		if(list != null && list.size()>0){
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.setContent("获取省份信息成功");
			uocMessage.addArg("provinceInfoList", list);
		}
		else{
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取省份信息失败");
		}
		return uocMessage;
	}

}
