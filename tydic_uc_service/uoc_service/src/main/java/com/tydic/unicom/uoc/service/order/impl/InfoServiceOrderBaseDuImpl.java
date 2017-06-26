package com.tydic.unicom.uoc.service.order.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderDataBakServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderDetailServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderEnquiryContactServDu;
import com.tydic.unicom.uoc.business.order.service.vo.OfrOrderAndServiceOrderVo;
import com.tydic.unicom.uoc.pub.common.service.po.ActivemqSendPo;
import com.tydic.unicom.uoc.service.activiti.interfaces.CompletePersonalTaskServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.FindMyPersonalTaskServDu;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.interfaces.ActivemqSendServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.uoc.service.es.impl.EsDateUtil;
import com.tydic.unicom.uoc.service.es.interfaces.EsServiceServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModStateRuleServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModTacheRuleServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdModStateRuleVo;
import com.tydic.unicom.uoc.service.mod.vo.OrdModTacheRuleVo;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderActivityHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderAgreementHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderAttrHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderCollectionHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderDeveloperHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderExtHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderFeeHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderFixHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderGoodNbrHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderGuarantorHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderM165HisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderPackageHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderPayHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderPersionHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderProdMapHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderSimCardHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderTerminalHisVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoDeliverOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOfrOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoPayOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderBaseDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoDeliverOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoPayOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderActivityVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderAgreementVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderAttrVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderCollectionVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderDeveloperVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderEsVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderExtVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderFeeVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderFixVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderGoodNbrVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderGuarantorVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderM165Vo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderPackageVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderPayVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderPersionVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderProdMapVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderSimCardVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderTerminalVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.webUtil.BaseVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;
import com.tydic.vt.sqljep.function.Hash;

@Service("InfoServiceOrderBaseDu")
public class InfoServiceOrderBaseDuImpl implements InfoServiceOrderBaseDu {

	Logger logger = Logger.getLogger(InfoServiceOrderBaseDuImpl.class);
	@Autowired
	private InfoSaleOrderServDu infoSaleOrderServDu;
	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;
	@Autowired
	private OrdModStateRuleServDu ordModStateRuleServDu;
	@Autowired
	private OrdModTacheRuleServDu ordModTacheRuleServDu;
	@Autowired
	private InfoOfrOrderServDu infoOfrOrderServDu;
	@Autowired
	private InfoPayOrderServDu infoPayOrderServDu;
	@Autowired
	private InfoDeliverOrderServDu infoDeliverOrderServDu;
	@Autowired
	private FindMyPersonalTaskServDu findMyPersonalTaskServDu;
	@Autowired
	private OrderDataBakServDu orderDataBakServDu;
	@Autowired
	private ActivemqSendServDu activemqSendServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired
	private CompletePersonalTaskServDu completePersonalTaskServDu;
	@Autowired
	private OrderDetailServDu orderDetailServDu;
	@Autowired
	private EsServiceServDu esServiceServDu;
	@Autowired
	private OrderEnquiryContactServDu orderEnquiryContactServDu;
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	@Override
	public UocMessage checkServiceOrderState(String serv_order_no, String oper_code, String state_code_new) {
		logger.info("BASE0001-----------checkServiceOrderRelation");
		UocMessage message = new UocMessage();
		if (serv_order_no == null || serv_order_no == "") {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("serv_order_no不能为空");
			return message;
		}
		if (oper_code == null || oper_code == "") {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("oper_code不能为空");
				return message;
			}
		if (state_code_new == null || state_code_new == "") {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("state_code_new不能为空");
				return message;
			}
		OrdModStateRuleVo ordModStateRuleVo = new OrdModStateRuleVo();
		ordModStateRuleVo.setOper_code_a(oper_code);
		ordModStateRuleVo.setState_code_a(state_code_new);
		try {
			ordModStateRuleVo = ordModStateRuleServDu.queryOrdModStateRuleByOperCodeAndStateCode(ordModStateRuleVo);
			if (ordModStateRuleVo == null) {
				logger.info("ordModStateRuleVo------" + ordModStateRuleVo);
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("无对应服务订单关系");
				return message;
			}
			String rela_type = ordModStateRuleVo.getRela_type();
			logger.info("rela_type----" + rela_type);

			InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
			infoServiceOrderVo.setServ_order_no(serv_order_no);
			infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
			if (infoServiceOrderVo == null) {
				logger.info("infoServiceOrderVo------" + infoServiceOrderVo);
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("无对应的服务订单");
				return message;
			}
			// String ofr_order_no =infoServiceOrderVo.getOfr_order_no();
			List<InfoServiceOrderVo> infoServiceOrderVos = infoServiceOrderServDu.queryInfoServiceOrderByOfrOrderNo(infoServiceOrderVo);
			if ("100".equals(rela_type)) { // 互斥关系
											// 订单的业务类型以及状态，不能存在于当前商品订单ID的其他服务订单中
				String oper_code_b = ordModStateRuleVo.getOper_code_b();
				String state_code_b = ordModStateRuleVo.getState_code_b();
				for (InfoServiceOrderVo vo : infoServiceOrderVos) {
					if (oper_code.equals(vo.getOper_code()) && state_code_new.equals(vo.getOrder_state())) {
						logger.info("oper_code------" + vo.getOper_code());
						logger.info("state_code------" + vo.getOrder_state());
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("存在互斥业务类型以及状态");
						return message; // 存在互斥业务类型以及状态 校验报错
					}
					if (oper_code_b.equals(vo.getOper_code()) && state_code_b.equals(vo.getOrder_state())) {
						logger.info("oper_code------" + vo.getOper_code());
						logger.info("state_code------" + vo.getOrder_state());
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("存在互斥业务类型以及状态");
						return message; // 存在互斥业务类型以及状态 校验报错
					}

				}

			} else if ("101".equals(rela_type)) { // 依赖关系存在，则对应配置的依赖订单的业务类型以及状态，必须存在于当前商品订单ID的其他服务订单中
				String oper_code_b = ordModStateRuleVo.getOper_code_b();
				String state_code_b = ordModStateRuleVo.getState_code_b();
				boolean flag = false;
				for (InfoServiceOrderVo vo : infoServiceOrderVos) {
					if (oper_code_b.equals(vo.getOper_code()) && state_code_b.equals(vo.getOrder_state())) {
						flag = true; // 存在依赖的业务类型以及状态
					}
					}
				if (!flag) {
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("不存在依赖的业务类型以及状态");
					return message; // 不存在依赖的业务类型以及状态 校验报错
					}

				}

		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("校验发送异常");
			return message;
			}
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("校验正常");
			return message;
		}

	@Override
	public UocMessage checkServiceOrderTache(String serv_order_no, String oper_code, String tache_code_new) {
		logger.info("BASE0002-----------checkServiceOrderRelation");
		UocMessage message = new UocMessage();
		if (serv_order_no == null || serv_order_no == "") {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("serv_order_no不能为空");
			return message;
		}
		if (oper_code == null || oper_code == "") {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("oper_code不能为空");
				return message;
			}
		if (tache_code_new == null || tache_code_new == "") {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("tache_code_new不能为空");
				return message;
			}

		OrdModTacheRuleVo ordModTacheRuleVo = new OrdModTacheRuleVo();
		ordModTacheRuleVo.setOper_code_a(oper_code);
		ordModTacheRuleVo.setTache_code_a(tache_code_new);
		try {
			ordModTacheRuleVo = ordModTacheRuleServDu.queryOrdModTacheRuleByOperCodeAndTacheCode(ordModTacheRuleVo);
			if (ordModTacheRuleVo == null) {
				logger.info("ordModStateRuleVo------" + ordModTacheRuleVo);
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("无对应服务订单关系");
				return message;
			}
			String rela_type = ordModTacheRuleVo.getRela_type();
			logger.info("rela_type----" + rela_type);

			InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
			infoServiceOrderVo.setServ_order_no(serv_order_no);
			infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
			if (infoServiceOrderVo == null) {
				logger.info("infoServiceOrderVo------" + infoServiceOrderVo);
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("无对应的服务订单");
				return message;
			}
			List<InfoServiceOrderVo> infoServiceOrderVos = infoServiceOrderServDu.queryInfoServiceOrderByOfrOrderNo(infoServiceOrderVo);
			if ("100".equals(rela_type)) { // 互斥关系
											// 则对应配置的互斥订单的业务类型以及环节，不能存在于当前商品订单ID的其他服务订单中
				String oper_code_b = ordModTacheRuleVo.getOper_code_b();
				String tache_code_b = ordModTacheRuleVo.getTache_code_b();
				for (InfoServiceOrderVo vo : infoServiceOrderVos) {
					// 服务订单表的流程实例，然后调用BASE0012获取当前任务服务获取当前环节
					String proc_inst_id = vo.getProc_inst_id();
					UocMessage result = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(proc_inst_id, vo.getServ_order_no());
					String tache_code = (String) result.getArgs().get("tache_code");
					if (oper_code_b.equals(vo.getOper_code()) && tache_code_b.equals(tache_code)) {
						logger.info("oper_code------" + vo.getOper_code());
						logger.info("state_code------" + tache_code);
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("存在互斥业务类型以及环节");
						return message; // 存在互斥业务类型以及环节 校验报错
					}
					if (oper_code.equals(vo.getOper_code()) && tache_code_new.equals(tache_code)) {
						logger.info("oper_code------" + vo.getOper_code());
						logger.info("state_code------" + tache_code);
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("存在互斥业务类型以及环节");
						return message; // 存在互斥业务类型以及环节 校验报错
					}

				}
			} else if ("101".equals(rela_type)) { // 依赖关系存在，则对应配置的依赖订单的业务类型以及环节，必须存在于当前商品订单ID的其他服务订单中
				String oper_code_b = ordModTacheRuleVo.getOper_code_b();
				String tache_code_b = ordModTacheRuleVo.getTache_code_b();
				boolean flag = false;
				for (InfoServiceOrderVo vo : infoServiceOrderVos) {
					String proc_inst_id = vo.getProc_inst_id(); // 服务订单表的流程实例，然后调用BASE0012获取当前任务服务获取当前环节
					UocMessage result = findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(proc_inst_id, vo.getServ_order_no());
					String tache_code = (String) result.getArgs().get("tache_code");
					if (oper_code_b.equals(vo.getOper_code()) && tache_code_b.equals(tache_code)) {
						flag = true; // 存在依赖的业务类型以及环节
					}
					}
				if (!flag) {
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("不存在依赖的业务类型以及环节");
					return message; // 不存在依赖的业务类型以及环节 校验报错
					}
				}

		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("校验发送异常");
			return message;

			}
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("校验正常");
			return message;
		}

	@Override
	public UocMessage createServiceOrderComplete(String order_no, String oper_type) {
		logger.info("BASE0015-----------checkServiceOrderRelation");
		UocMessage message = new UocMessage();
		if (order_no == null || order_no == "") {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("serv_order_no不能为空");
			return message;
		}
		if (oper_type == null || oper_type == "") {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("oper_type不能为空");
			return message;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String service_code = "BASE0010"; // BASE0010进行订单数据备份
		ActivemqSendPo activemqSendPo = new ActivemqSendPo();
		activemqSendPo.setService_code(service_code);
		try {
			if ("100".equals(oper_type) || "104".equals(oper_type)) { // 销售订单
				serviceOrderCompleteFunction(order_no, oper_type);

				message.setRespCode(RespCodeContents.SUCCESS);
				message.setContent("订单竣工");
				return message;
			} else if ("101".equals(oper_type)) { // 服务订单
				logger.info("BASE0015-----------服务订单");
				InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
				infoServiceOrderVo.setServ_order_no(order_no);
				infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
				if (!(infoServiceOrderVo.getOrder_state().equals("302") || infoServiceOrderVo.getOrder_state().equals("211"))) {
					infoServiceOrderVo.setOrder_state("211");
					infoServiceOrderServDu.updateInfoServiceOrder(infoServiceOrderVo);
				}
				// 流程提交
				UocMessage res = completePersonalTaskServDu.completeProcess(infoServiceOrderVo.getProc_inst_id(), order_no);
				if (!"0000".equals(res.getRespCode())) {
					res.setRespCode(RespCodeContents.PARAM_ERROR);
					res.setContent("服务订单流程提交失败");
					throw new UocException(res);
				}

				// 查询对应的商品订单的服务订单
				List<InfoServiceOrderVo> list = infoServiceOrderServDu.queryInfoServiceOrderByOfrOrderNo(infoServiceOrderVo);
				// 如果对应商品订单下所有服务订单都竣工，则对应商品订单也竣工；
				boolean ofr_flag = true;
				for (InfoServiceOrderVo vo : list) {
					if (!"211".equals(vo.getOrder_state()) && !"302".equals(vo.getOrder_state())) {
						ofr_flag = false;
					}
				}
				if (ofr_flag) {
					InfoOfrOrderVo infoOfrOrderVo = new InfoOfrOrderVo();
					infoOfrOrderVo.setOfr_order_no(infoServiceOrderVo.getOfr_order_no());
					infoOfrOrderVo = infoOfrOrderServDu.getInfoOfrOrderByOfrOrderNo(infoOfrOrderVo);
					infoOfrOrderVo.setOrder_state("211");
					infoOfrOrderServDu.updateInfoOfrOrder(infoOfrOrderVo);

					// 查询对应的销售订单的商品订单
					List<InfoOfrOrderVo> infoOfrOrderVos = infoOfrOrderServDu.queryInfoOfrOrderBySaleOrderNo(infoOfrOrderVo);
					// 如果对应销售订单下所有商品订单都竣工，则对应销售订单也竣工；
					boolean sale_flag = true;
					for (InfoOfrOrderVo vo : infoOfrOrderVos) {
						if (!"211".equals(vo.getOrder_state()) && !"302".equals(vo.getOrder_state())) {
							sale_flag = false;
						}
					}
					if (sale_flag) {
//							String proc_inst_id = serviceOrderCompleteFunction(infoServiceOrderVo.getSale_order_no(), "100");
						InfoSaleOrderVo infoSale = new InfoSaleOrderVo();
						infoSale.setSale_order_no(infoServiceOrderVo.getSale_order_no());
						infoSale = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSale);
						infoSale.setOrder_state("211");
						infoSaleOrderServDu.updateInfoSaleOrder(infoSale);

//								logger.info("------BASE0010进行订单数据备份---------");
//								map.put("order_no", infoServiceOrderVo.getSale_order_no());
//								map.put("oper_type", "100");
//								String json_in = jsonToBeanServDu.mapToJson(map);
//								activemqSendPo.setJson_in(json_in);
						// 销售订单、支付订单或者交付订单竣工后通过BASE0009服务发消息队列，对应队列ID填OrderFinish，队列完成后自动调用BASE0010进行订单数据备份
//								activemqSendServDu.SendMessage(activemqSendPo, "OrderFinish");

						UocMessage resSale = completePersonalTaskServDu.completeProcess(infoSale.getProc_inst_id(),
								infoServiceOrderVo.getSale_order_no());
						if (!"0000".equals(resSale.getRespCode())) {
							res.setRespCode(RespCodeContents.PARAM_ERROR);
							res.setContent("销售订单流程提交失败");
							throw new UocException(res);
						}
					}
				}
				logger.info("------BASE0032进行Es数据更新---------");
				//获取数据库月份
				int month = Integer.parseInt(infoServiceOrderVo.getPart_month());
				String typeName = "";
				if(month<10){
					typeName  = "0"+String.valueOf(month);
				}else{
					typeName = String.valueOf(month);
				}
				//获取上月月份
				int lastMonth = month-1;
				String typeName1 = "";
				if(lastMonth != 0){
					if(lastMonth<10){
						typeName1  = "0"+String.valueOf(lastMonth);
					}else{
						typeName1 = String.valueOf(lastMonth);
					}
				}else{
					//此处应更换IndexName为昨年，typeName为12
				}
				//先查询ES-serv_order_no
				boolean existOrder = esServiceServDu.isExist(propertiesParamVo.getIndexName(), typeName, order_no);
				boolean existOrder1 = false;
				if(!existOrder){
					existOrder1 = esServiceServDu.isExist(propertiesParamVo.getIndexName(), typeName1, order_no);
				}
				if(existOrder || existOrder1){
					//执行订单竣工时更新es数据
					Map<String, Object> ordModMapTemp = new HashMap<String, Object>();
					ordModMapTemp.put("serv_order_no", order_no);
					ordModMapTemp.put("order_type", oper_type);
					ordModMapTemp.put("jsession_id", infoServiceOrderVo.getAccept_oper_no());
					String json_info=jsonToBeanServDu.mapToJson(ordModMapTemp);
					
					ActivemqSendPo activemqSendPo1 = new ActivemqSendPo();
					activemqSendPo1.setService_code("BASE0032");
					activemqSendPo1.setJson_in(json_info);
					activemqSendPo1.setOrder_id(order_no);
					activemqSendPo1.setProvince_code(infoServiceOrderVo.getProvince_code());
					activemqSendPo1.setOrder_type(oper_type);
					String queue_id="OrderFinish";
					UocMessage asMessage = activemqSendServDu.createMessageQueue(activemqSendPo1, queue_id);
					if("0000".equals(asMessage.getRespCode())){
						logger.info("==========更新消息队列订单数据es成功==========");
					}
				}
				message.setRespCode(RespCodeContents.SUCCESS);
				message.setContent("服务订单竣工");
				return message;
			} else if ("102".equals(oper_type)) { // 支付订单
				String proc_inst_id = serviceOrderCompleteFunction(order_no, oper_type);
				if (StringUtils.isNotEmpty(proc_inst_id)) {
					map.put("order_no", order_no);
					map.put("oper_type", oper_type);
					String json_in = jsonToBeanServDu.mapToJson(map);
					activemqSendPo.setJson_in(json_in);
//					activemqSendServDu.createMessageQueue(activemqSendPo, "OrderFinish");

					// 流程提交
					UocMessage res = completePersonalTaskServDu.completeProcess(proc_inst_id, order_no);
					if (!"0000".equals(res.getRespCode())) {
						res.setRespCode(RespCodeContents.PARAM_ERROR);
						res.setContent("流程提交失败");
						throw new UocException(res);
					}
				}

				message.setRespCode(RespCodeContents.SUCCESS);
				message.setContent("支付订单竣工");
				return message;
			} else if ("103".equals(oper_type)) { // 交付订单
				String proc_inst_id = serviceOrderCompleteFunction(order_no, oper_type);
				if (StringUtils.isNotEmpty(proc_inst_id)) {
					map.put("order_no", order_no);
					map.put("oper_type", oper_type);
					String json_in = jsonToBeanServDu.mapToJson(map);
					activemqSendPo.setJson_in(json_in);
//					activemqSendServDu.createMessageQueue(activemqSendPo, "OrderFinish");

					// 流程提交
					UocMessage res = completePersonalTaskServDu.completeProcess(proc_inst_id, order_no);
					if (!"0000".equals(res.getRespCode())) {
						res.setRespCode(RespCodeContents.PARAM_ERROR);
						res.setContent("流程提交失败");
						throw new UocException(res);
					}
				}

				message.setRespCode(RespCodeContents.SUCCESS);
				message.setContent("交付订单竣工");
				return message;
			}

			logger.info("BASE0015-----------无对应订单类型");
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("无对应订单类型");
			return message;

		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("订单竣工异常");
			return message;
			}
		}

	private String serviceOrderCompleteFunction(String order_no, String oper_type) throws Exception {
		String proc_inst_id = "";
		if ("100".equals(oper_type)) { // 销售订单
			logger.info("BASE0015-----------销售订单竣工");
			InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
			infoSaleOrderVo.setSale_order_no(order_no);
			infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			proc_inst_id = infoSaleOrderVo.getProc_inst_id();
			if (infoSaleOrderVo.getOrder_state().equals("302") || infoSaleOrderVo.getOrder_state().equals("209")) {
				return "";
			} else {
				infoSaleOrderVo.setOrder_state("209"); // 211流程完工
				infoSaleOrderServDu.updateInfoSaleOrder(infoSaleOrderVo);
			}
		} else if ("101".equals(oper_type)) { // 服务订单
			logger.info("BASE0015-----------服务订单竣工");
			InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
			infoServiceOrderVo.setServ_order_no(order_no);
			infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
			proc_inst_id = infoServiceOrderVo.getProc_inst_id();
			if (infoServiceOrderVo.getOrder_state().equals("302") || infoServiceOrderVo.getOrder_state().equals("211")) {
				return "";
			} else {
				infoServiceOrderVo.setOrder_state("211");
				infoServiceOrderServDu.updateInfoServiceOrder(infoServiceOrderVo);
			}
		} else if ("102".equals(oper_type)) { // 支付订单
			logger.info("BASE0015-----------支付订单竣工");
			InfoPayOrderVo infoPayOrderVo = new InfoPayOrderVo();
			infoPayOrderVo.setPay_order_no(order_no);
			infoPayOrderVo = infoPayOrderServDu.getInfoPayOrderByPayOrderNo(infoPayOrderVo);
			proc_inst_id = infoPayOrderVo.getProc_inst_id();
			if (infoPayOrderVo.getPay_state().equals("302") || infoPayOrderVo.getPay_state().equals("211")) {
				return "";
			} else {
				infoPayOrderVo.setPay_state("211");
				infoPayOrderServDu.updateInfoPayOrder(infoPayOrderVo);
			}
		} else if ("103".equals(oper_type)) { // 交付订单
			logger.info("BASE0015-----------交付订单竣工");
			InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
			infoDeliverOrderVo.setDeliver_order_no(order_no);
			infoDeliverOrderVo = infoDeliverOrderServDu.getInfoDeliverOrderByPayDeliverOrderNo(infoDeliverOrderVo);
			proc_inst_id = infoDeliverOrderVo.getProc_inst_id();
			if (infoDeliverOrderVo.getDeliver_state().equals("302") || infoDeliverOrderVo.getDeliver_state().equals("211")) {
				return "";
			} else {
				infoDeliverOrderVo.setDeliver_state("211");
				infoDeliverOrderServDu.updateInfoDeliverOrder(infoDeliverOrderVo);
			}
		} else if ("104".equals(oper_type)) { // 交付订单
			logger.info("BASE0015-----------销售预订单竣工");
			InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
			infoSaleOrderVo.setSale_order_no(order_no);
			infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			if (infoSaleOrderVo.getOrder_state().equals("302") || infoSaleOrderVo.getOrder_state().equals("102")) {
				return "";
			} else {
				infoSaleOrderVo.setOrder_state("102"); // 211流程完工
				infoSaleOrderServDu.updateInfoSaleOrder(infoSaleOrderVo);
			}
		}

		return proc_inst_id;
	}

	/**
	 * 消息队列反射调用订单中间数据
	 * */
	public UocMessage reflexCreateOrderIntermediateData(String json_info) throws Exception{
		JSONObject jsonObj = JSONObject.fromObject(json_info);
		String serv_order_no = jsonObj.get("serv_order_no").toString();
		String order_type = jsonObj.get("order_type").toString();
		String jsession_id = jsonObj.get("jsession_id").toString();
		return createOrderIntermediateData(serv_order_no,order_type,jsession_id);
	}
	
	@SuppressWarnings("unused")
	@Override
	public UocMessage createOrderIntermediateData(String serv_order_no,String order_type,String jsession_id)
			throws Exception {
		/**
		 * 1、调用UOC0073服务，传入订单号条件，
		 * 将返回的服务订单列表数据通过ES通用方法存入ES数据源，
		 * 存之前先做删除操作；
		 */
		//初始化id
		String id=serv_order_no;
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		UocMessage message = new UocMessage();
		
		if(propertiesParamVo == null){
			propertiesParamVo = (PropertiesParamVo) ToolSpring.getBean("propertiesParamVo");
		}
		if(orderEnquiryContactServDu == null){
			orderEnquiryContactServDu = (OrderEnquiryContactServDu) ToolSpring.getBean("OrderEnquiryContactServDu");
		}
		if(orderDetailServDu == null){
			orderDetailServDu = (OrderDetailServDu) ToolSpring.getBean("OrderDetailServDu");
		}
		if(esServiceServDu == null){
			esServiceServDu = (EsServiceServDu) ToolSpring.getBean("EsServiceServDu");
		}
		
		String indexName=propertiesParamVo.getIndexName();
		String typeName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).substring(4, 6);
		UocMessage orderDetailMsg=orderEnquiryContactServDu.queryOrderEnquiryContact(jsession_id, serv_order_no);
		if (RespCodeContents.SUCCESS.equals(orderDetailMsg.getRespCode())) {
			InfoServiceOrderEsVo infoServiceOrderEsVo = new InfoServiceOrderEsVo();
			Map<String,Object> orderDetailMap = orderDetailMsg.getArgs();
			if(orderDetailMap.get("create_time")!= null){
				orderDetailMap.put("create_time", EsDateUtil.formatDateToEs(orderDetailMap.get("create_time").toString()));
			}
			map.put("info_service_list", orderDetailMap);
		}

		/**
		 * 2、调用UOC0010服务，传入订单号条件，
		 * 将返回的服务订单详情数据通过ES通用方法存入ES数据源，
		 * 存之前先做删除操作；
		 */
		ParaVo paraVo=new ParaVo();
		paraVo.setOrder_no(serv_order_no);
		paraVo.setQuery_type(order_type);
		paraVo.setJsession_id(jsession_id);
		UocMessage servOrderMsg=orderDetailServDu.getOrderDetail(paraVo);
		String esStrCreate="";//初始化订单时间(ES)格式
		String province_code="";
		String area_code="";
		if(RespCodeContents.SUCCESS.equals(servOrderMsg.getRespCode())){
			@SuppressWarnings("unchecked")
			Map<String, Object> json_out=(Map<String, Object>)servOrderMsg.getArgs().get("json_info");
			//取到服务订单（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> servlist=(List<BaseVo>) json_out.get("infoServiceOrderList");
			//取到服务订单拓展属性横表（包含历史表）数据
			BaseVo servExt=(BaseVo)json_out.get("infoServiceOrderExt");
			//取到服务订单优惠活动（包括历史表）
			@SuppressWarnings("unchecked")
			List<BaseVo> servActivitylist=(List<BaseVo>) json_out.get("infoServiceOrderActivityList");
			//取到服务订单协议表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serAgreementList=(List<BaseVo>) json_out.get("infoServiceOrderAgreementList");
			//取到服务订单属性表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serAttrList=(List<BaseVo>)json_out.get("infoServiceOrderAttrList");
			//取到服务订单银行托收表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serCollectionList=(List<BaseVo>)json_out.get("infoServiceOrderCollectionList");
			//取到服务订单发展人表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serDeveloperList=(List<BaseVo>)json_out.get("infoServiceOrderDeveloperList");
			//取到服务订单费用详情表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serFeeList=(List<BaseVo>)json_out.get("infoServiceOrderFeeList");
			//取到服务订单固网信息表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serFixList=(List<BaseVo>)json_out.get("infoServiceOrderFixList");
			//取到服务订单靓号信息表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serGoodNbrList=(List<BaseVo>)json_out.get("infoServiceOrderGoodNbrList");
			//取到服务订单担保人表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serGuarantorList=(List<BaseVo>)json_out.get("infoServiceOrderGuarantorList");
			//取到服务订单宽带信息表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serM165List=(List<BaseVo>)json_out.get("infoServiceOrderM165List");
			//取到服务订单套餐表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serPackageList=(List<BaseVo>)json_out.get("infoServiceOrderPackageList");
			//取到服务订单收费详情表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serPayList=(List<BaseVo>)json_out.get("infoServiceOrderPayList");
			//取到服务订单客户信息表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serPersionList=(List<BaseVo>)json_out.get("infoServiceOrderPersionList");
			//取到服务订单产品表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serProdMapList=(List<BaseVo>)json_out.get("infoServiceOrderProdMapList");
			//取到服务订单终端表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serTerminalList=(List<BaseVo>)json_out.get("infoServiceOrderTerminalList");
			//取到服务订单SIM卡表（包含历史表）数据
			@SuppressWarnings("unchecked")
			List<BaseVo> serSimCardList=(List<BaseVo>)json_out.get("InfoServiceOrderSimCardList");
			//取到taskInst卡表（包含历史表）数据
			BaseVo procInstTaskInstVo = (BaseVo) json_out.get("procInstTaskInstVo");
			//同时取销售订单
			BaseVo infoSaleOrder = (BaseVo) json_out.get("infoSaleOrder");
			//取商品订单
			@SuppressWarnings("unchecked")
			List<BaseVo> infoOfrOrderList = (List<BaseVo>) json_out.get("infoOfrOrderList");
			@SuppressWarnings("unchecked")
			List<BaseVo> ofrOrderAndServiceOrderList = (List<BaseVo>) json_out.get("ofrOrderAndServiceOrderList");
			//服务订单
			if(servlist!=null&&servlist.size()>0){
				List<Map<String, Object>> serviceOrderList=new ArrayList<Map<String, Object>>();
				for(BaseVo baseVo:servlist){
					Map<String, Object> servTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderVo){
						InfoServiceOrderVo vo=(InfoServiceOrderVo)baseVo;
						servTempMap = vo.convertThis2Map();
					}
					else if(baseVo instanceof InfoServiceOrderHisVo){
						InfoServiceOrderHisVo hisVo=(InfoServiceOrderHisVo)baseVo;
						servTempMap = hisVo.convertThis2Map();
					}
					//转换时间
					if(servTempMap.get("create_time")!= null){
						servTempMap.put("create_time", EsDateUtil.formatDateToEs(servTempMap.get("create_time").toString()));
					}
					if(servTempMap.get("finsh_time")!= null){
						servTempMap.put("finsh_time", EsDateUtil.formatDateToEs(servTempMap.get("finsh_time").toString()));
					}
					serviceOrderList.add(servTempMap);
				}
				map.put("info_service_order", serviceOrderList);
			}

			//服务订单拓展属性横表
			if(servExt!=null){
				Map<String,Object> servExtTempMap = new HashMap<String, Object>();
				if(servExt instanceof InfoServiceOrderExtVo){
					InfoServiceOrderExtVo infoServiceOrderExtVo=(InfoServiceOrderExtVo)servExt;
					servExtTempMap = infoServiceOrderExtVo.convertThis2Map();
				}
				else if(servExt instanceof InfoServiceOrderExtHisVo){
					InfoServiceOrderExtHisVo infoServiceOrderExtHisVo=(InfoServiceOrderExtHisVo)servExt;
					servExtTempMap = infoServiceOrderExtHisVo.convertThis2Map();
				}
				map.put("info_serv_ext", servExtTempMap);
			}

			//服务订单优惠活动
			if(servActivitylist!=null&&servActivitylist.size()>0){
				List<Map<String, Object>> serviceOrderActivityList = new ArrayList<Map<String, Object>>();
				for(BaseVo baseVo:servActivitylist){
					Map<String, Object> servActivityTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderActivityVo){
						InfoServiceOrderActivityVo activityVo=(InfoServiceOrderActivityVo)baseVo;
						servActivityTempMap = activityVo.convertThis2Map();
					}
					else if(baseVo instanceof InfoServiceOrderActivityHisVo){
						InfoServiceOrderActivityHisVo activityHisVo=(InfoServiceOrderActivityHisVo)baseVo;
						servActivityTempMap = activityHisVo.convertThis2Map();
					}
					serviceOrderActivityList.add(servActivityTempMap);
				}
				map.put("info_serv_activity", serviceOrderActivityList);
			}

			//服务订单协议
			if(serAgreementList!=null&&serAgreementList.size()>0){
				List<Map<String, Object>> serviceOrderAgreementList = new ArrayList<Map<String, Object>>();
				for(BaseVo baseVo:serAgreementList){
					Map<String, Object> serAgreementTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderAgreementVo){
						InfoServiceOrderAgreementVo agreementVo=(InfoServiceOrderAgreementVo)baseVo;
						serAgreementTempMap = agreementVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderAgreementHisVo){
						InfoServiceOrderAgreementHisVo agreementHisVo=(InfoServiceOrderAgreementHisVo)baseVo;
						serAgreementTempMap = agreementHisVo.convertThis2Map();
					}
					serviceOrderAgreementList.add(serAgreementTempMap);
				}
				map.put("info_serv_agreement", serviceOrderAgreementList);
			}


			//服务订单属性
			if(serAttrList!=null&&serAttrList.size()>0){
				List<Map<String, Object>> serviceOrderAttrList = new ArrayList<Map<String, Object>>();
				for(BaseVo baseVo:serAttrList){
					Map<String, Object> serAttrTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderAttrVo){
						InfoServiceOrderAttrVo attrVo=(InfoServiceOrderAttrVo)baseVo;
						serAttrTempMap = attrVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderAttrHisVo){
						InfoServiceOrderAttrHisVo attrHisVo=(InfoServiceOrderAttrHisVo)baseVo;
						serAttrTempMap = attrHisVo.convertThis2Map();
					}
					serviceOrderAttrList.add(serAttrTempMap);
				}
				map.put("info_serv_attr", serviceOrderAttrList);
			}

			//服务订单银行托收
			if(serCollectionList!=null&&serCollectionList.size()>0){
				List<Map<String, Object>> serviceOrderCollectionList=new ArrayList<Map<String, Object>>();
				for(BaseVo baseVo:serCollectionList){
					Map<String, Object> serviceOrderCollectionTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderCollectionVo){
						InfoServiceOrderCollectionVo collectionVo=(InfoServiceOrderCollectionVo)baseVo;
						serviceOrderCollectionTempMap = collectionVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderCollectionHisVo){
						InfoServiceOrderCollectionHisVo collectionHisVo=(InfoServiceOrderCollectionHisVo)baseVo;
						serviceOrderCollectionTempMap = collectionHisVo.convertThis2Map();
					}
					serviceOrderCollectionList.add(serviceOrderCollectionTempMap);
				}
				map.put("info_serv_collection", serviceOrderCollectionList);
			}

			//服务订单发展人
			if(serDeveloperList!=null&&serDeveloperList.size()>0){
				List<Map<String,Object>> serviceOrderDeveloperList=new ArrayList<Map<String,Object>>();
				for(BaseVo baseVo:serDeveloperList){
					Map<String,Object> serDeveloperTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderDeveloperVo){
						InfoServiceOrderDeveloperVo developerVo=(InfoServiceOrderDeveloperVo)baseVo;
						serDeveloperTempMap = developerVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderDeveloperHisVo){
						InfoServiceOrderDeveloperHisVo developerHisVo=(InfoServiceOrderDeveloperHisVo)baseVo;
						serDeveloperTempMap = developerHisVo.convertThis2Map();
					}
					serviceOrderDeveloperList.add(serDeveloperTempMap);
				}
				map.put("info_serv_developer", serviceOrderDeveloperList);
			}


			//服务订单费用详情
			if(serFeeList!=null&&serFeeList.size()>0){
				List<Map<String,Object>> serviceOrderFeeList=new ArrayList<Map<String,Object>>();
				for(BaseVo baseVo:serFeeList){
					Map<String,Object> serFeeTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderFeeVo){
						InfoServiceOrderFeeVo feeVo=(InfoServiceOrderFeeVo)baseVo;
						serFeeTempMap = feeVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderFeeHisVo){
						InfoServiceOrderFeeHisVo feeHisVo=(InfoServiceOrderFeeHisVo)baseVo;
						serFeeTempMap = feeHisVo.convertThis2Map();
					}
					serviceOrderFeeList.add(serFeeTempMap);
				}
				map.put("info_serv_fee", serviceOrderFeeList);
			}

			//服务订单固网信息
			if(serFixList!=null&&serFixList.size()>0){
				List<Map<String,Object>> serviceOrderFixList=new ArrayList<Map<String,Object>>();
				for(BaseVo baseVo:serFixList){
					Map<String,Object> serFixTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderFixVo){
						InfoServiceOrderFixVo fixVo=(InfoServiceOrderFixVo)baseVo;
						serFixTempMap = fixVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderFixHisVo){
						InfoServiceOrderFixHisVo fixHisVo=(InfoServiceOrderFixHisVo)baseVo;
						serFixTempMap = fixHisVo.convertThis2Map();
					}
					serviceOrderFixList.add(serFixTempMap);
				}
				map.put("info_serv_fix", serviceOrderFixList);
			}

			//服务订单靓号信息
			if(serGoodNbrList!=null&&serGoodNbrList.size()>0){
				List<Map<String,Object>> serviceOrderGoodNbrList=new ArrayList<Map<String,Object>>();
				for(BaseVo baseVo:serGoodNbrList){
					Map<String,Object> serGoodNbrTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderGoodNbrVo){
						InfoServiceOrderGoodNbrVo goodNbrVo=(InfoServiceOrderGoodNbrVo)baseVo;
						serGoodNbrTempMap = goodNbrVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderGoodNbrHisVo){
						InfoServiceOrderGoodNbrHisVo goodNbrHisVo=(InfoServiceOrderGoodNbrHisVo)baseVo;
						serGoodNbrTempMap = goodNbrHisVo.convertThis2Map();
					}
					serviceOrderGoodNbrList.add(serGoodNbrTempMap);
				}
				map.put("info_serv_good_nbr", serviceOrderGoodNbrList);
			}

			//服务订单担保人
			if(serGuarantorList!=null&&serGuarantorList.size()>0){
				List<Map<String,Object>> serviceOrderGuarantorList=new ArrayList<Map<String,Object>>();
				for(BaseVo baseVo:serGuarantorList){
					Map<String,Object> serGuarantorTempMap= new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderGuarantorVo){
						InfoServiceOrderGuarantorVo guarantorVo=(InfoServiceOrderGuarantorVo)baseVo;
						serGuarantorTempMap = guarantorVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderGuarantorHisVo){
						InfoServiceOrderGuarantorHisVo guarantorHisVo=(InfoServiceOrderGuarantorHisVo)baseVo;
						serGuarantorTempMap = guarantorHisVo.convertThis2Map();
					}
					serviceOrderGuarantorList.add(serGuarantorTempMap);
				}
				map.put("info_serv_guarantor", serviceOrderGuarantorList);
			}

			//服务订单宽带信息
			if(serM165List!=null&&serM165List.size()>0){
				List<Map<String,Object>> serviceOrderM165List=new ArrayList<Map<String,Object>>();
				for(BaseVo baseVo:serM165List){
					Map<String,Object> serM165TempMap = new HashMap<String,Object>();
					if(baseVo instanceof InfoServiceOrderM165Vo){
						InfoServiceOrderM165Vo m165Vo=(InfoServiceOrderM165Vo)baseVo;
						serM165TempMap = m165Vo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderM165HisVo){
						InfoServiceOrderM165HisVo m165HisVo=(InfoServiceOrderM165HisVo)baseVo;
						serM165TempMap = m165HisVo.convertThis2Map();
					}
					serviceOrderM165List.add(serM165TempMap);
				}
				map.put("info_serv_m165", serviceOrderM165List);
			}

			//服务订单套餐
			if(serPackageList!=null&&serPackageList.size()>0){
				List<Map<String,Object>> serviceOrderPackageList=new ArrayList<Map<String,Object>>();
				for(BaseVo baseVo:serPackageList){
					Map<String,Object> serPackageTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderPackageVo){
						InfoServiceOrderPackageVo packageVo=(InfoServiceOrderPackageVo)baseVo;
						serPackageTempMap = packageVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderPackageHisVo){
						InfoServiceOrderPackageHisVo packageHisVo=(InfoServiceOrderPackageHisVo)baseVo;
						serPackageTempMap = packageHisVo.convertThis2Map();
					}
					serviceOrderPackageList.add(serPackageTempMap);
				}
				map.put("info_serv_pkg", serviceOrderPackageList);
			}

			//服务订单收费详情
			if(serPayList!=null&&serPayList.size()>0){
				List<Map<String,Object>> serviceOrderPayList=new ArrayList<Map<String,Object>>();
				for(BaseVo baseVo:serPayList){
					Map<String,Object> serPayTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderPayVo){
						InfoServiceOrderPayVo payVo=(InfoServiceOrderPayVo)baseVo;
						serPayTempMap = payVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderPayHisVo){
						InfoServiceOrderPayHisVo payHisVo=(InfoServiceOrderPayHisVo)baseVo;
						serPayTempMap = payHisVo.convertThis2Map();
					}
					serviceOrderPayList.add(serPayTempMap);
				}
				map.put("info_serv_pay", serviceOrderPayList);
			}

			//服务订单客户信息
			if(serPersionList!=null&&serPersionList.size()>0){
				List<Map<String,Object>> serviceOrderPersionList=new ArrayList<Map<String,Object>>();
				for(BaseVo baseVo:serPersionList){
					Map<String,Object> serPersionTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderPersionVo){
						InfoServiceOrderPersionVo persionVo=(InfoServiceOrderPersionVo)baseVo;
						serPersionTempMap = persionVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderPersionHisVo){
						InfoServiceOrderPersionHisVo persionHisVo=(InfoServiceOrderPersionHisVo)baseVo;
						serPersionTempMap = persionHisVo.convertThis2Map();
					}
					serviceOrderPersionList.add(serPersionTempMap);
				}
				map.put("info_serv_person", serviceOrderPersionList);
			}

			//服务订单产品
			if(serProdMapList!=null&&serProdMapList.size()>0){
				List<Map<String,Object>> serviceOrderProdMapList=new ArrayList<Map<String,Object>>();
				for(BaseVo baseVo:serProdMapList){
					Map<String,Object> serProdMapTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderProdMapVo){
						InfoServiceOrderProdMapVo prodMapVo=(InfoServiceOrderProdMapVo)baseVo;
						serProdMapTempMap = prodMapVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderProdMapHisVo){
						InfoServiceOrderProdMapHisVo prodMapHisVo=(InfoServiceOrderProdMapHisVo)baseVo;
						serProdMapTempMap = prodMapHisVo.convertThis2Map();
					}
					serviceOrderProdMapList.add(serProdMapTempMap);
				}
				map.put("info_serv_prod_map", serviceOrderProdMapList);
			}

			//服务订单终端
			if(serTerminalList!=null&&serTerminalList.size()>0){
				List<Map<String,Object>> serviceOrderTerminalList=new ArrayList<Map<String,Object>>();
				for(BaseVo baseVo:serTerminalList){
					Map<String,Object> serTerminalTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderTerminalVo){
						InfoServiceOrderTerminalVo terminalVo=(InfoServiceOrderTerminalVo)baseVo;
						serTerminalTempMap = terminalVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderTerminalHisVo){
						InfoServiceOrderTerminalHisVo terminalHisVo=(InfoServiceOrderTerminalHisVo)baseVo;
						serTerminalTempMap = terminalHisVo.convertThis2Map();
					}
					serviceOrderTerminalList.add(serTerminalTempMap);
				}
				map.put("info_serv_terminal", serviceOrderTerminalList);
			}

			//服务订单SIM卡
			if(serSimCardList!=null&&serSimCardList.size()>0){
				List<Map<String,Object>> serviceOrderSimCardList=new ArrayList<Map<String,Object>>();
				for(BaseVo baseVo:serSimCardList){
					Map<String,Object> serSimCardTempMap = new HashMap<String, Object>();
					if(baseVo instanceof InfoServiceOrderSimCardVo){
						InfoServiceOrderSimCardVo simCardVo=(InfoServiceOrderSimCardVo)baseVo;
						serSimCardTempMap = simCardVo.convertThis2Map();
					}else if(baseVo instanceof InfoServiceOrderSimCardHisVo){
						InfoServiceOrderSimCardHisVo simCardHisVo=(InfoServiceOrderSimCardHisVo)baseVo;
						serSimCardTempMap = simCardHisVo.convertThis2Map();
					}
					serviceOrderSimCardList.add(serSimCardTempMap);
				}
				map.put("info_serv_sim_card", serviceOrderSimCardList);
			}

			//人工任务实例
			if(procInstTaskInstVo!=null){
				Map<String,Object> taskInstTempMap = new HashMap<String, Object>();
				taskInstTempMap = procInstTaskInstVo.convertThis2Map();
				if(taskInstTempMap.get("create_time") != null && (!"".equals(taskInstTempMap.get("create_time").toString()))){
					taskInstTempMap.put("create_time", EsDateUtil.formatDateToEs(taskInstTempMap.get("create_time").toString()));
				}
				if(taskInstTempMap.get("limit_time") != null && (!"".equals(taskInstTempMap.get("limit_time").toString()))){
					taskInstTempMap.put("limit_time", EsDateUtil.formatDateToEs(taskInstTempMap.get("limit_time").toString()));
				}
				if(taskInstTempMap.get("finish_time") != null && (!"".equals(taskInstTempMap.get("finish_time").toString()))){
					taskInstTempMap.put("finish_time", EsDateUtil.formatDateToEs(taskInstTempMap.get("finish_time").toString()));
				}
				map.put("task_inst", taskInstTempMap);
			}
			//人工记录
			
			//销售订单
			if(infoSaleOrder!=null){
				Map<String,Object> infoSaleOrderTempMap = new HashMap<String, Object>();
				infoSaleOrderTempMap = infoSaleOrder.convertThis2Map();
				if(infoSaleOrderTempMap.get("accept_time") != null && (!"".equals(infoSaleOrderTempMap.get("accept_time").toString()))){
					infoSaleOrderTempMap.put("accept_time", EsDateUtil.formatDateToEs(infoSaleOrderTempMap.get("accept_time").toString()));
				}
				map.put("info_sale_order", infoSaleOrderTempMap);
			}

			//商品订单
			if(infoOfrOrderList!=null && infoOfrOrderList.size()>0){
				List<Map<String,Object>> infoOfrOrderDataList=new ArrayList<Map<String,Object>>();
				for (BaseVo infoOfrOrderVo : infoOfrOrderList){
					Map<String,Object> infoOfrOrderTempMap = new HashMap<String, Object>();
					infoOfrOrderTempMap = infoOfrOrderVo.convertThis2Map();
					if(infoOfrOrderTempMap.get("create_time") != null && (!"".equals(infoOfrOrderTempMap.get("create_time").toString()))){
						infoOfrOrderTempMap.put("create_time", EsDateUtil.formatDateToEs(infoOfrOrderTempMap.get("create_time").toString()));
					}
					if(infoOfrOrderTempMap.get("finish_time") != null && (!"".equals(infoOfrOrderTempMap.get("finish_time").toString()))){
						infoOfrOrderTempMap.put("finish_time", EsDateUtil.formatDateToEs(infoOfrOrderTempMap.get("finish_time").toString()));
					}
					infoOfrOrderDataList.add(infoOfrOrderTempMap);
				}
				map.put("info_ofr_order", infoOfrOrderDataList);
			}
			
					boolean flag=esServiceServDu.delete(id, indexName, typeName);
					if(!flag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("ES删除服务订单数据失败！");
						return message;
					}
					logger.info("===========插入id"+id);
					boolean insertResult = esServiceServDu.insert(id, indexName, typeName, map);
					if(!insertResult){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("ES保存服务订单数据失败！");
						return message;
					}	
		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("订单ES数据生成成功！");
		return message;
	}

}
