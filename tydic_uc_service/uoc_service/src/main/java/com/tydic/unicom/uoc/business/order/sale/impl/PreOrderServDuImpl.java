package com.tydic.unicom.uoc.business.order.sale.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.sale.interfaces.PreOrderServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ChangeToArtificialServiceServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.CheckProcessServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.FindMyPersonalTaskServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessCirculationServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.StartProcessServDu;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModFunctionServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheLoginServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdModVo;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

public class PreOrderServDuImpl implements PreOrderServDu {

	Logger logger = Logger.getLogger(PreOrderServDuImpl.class);

	@Autowired
	private OperServDu operServDu;
	@Autowired
	private StartProcessServDu startProcessServDu;
	@Autowired
	private InfoSaleOrderServDu infoSaleOrderServDu;
	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;
	@Autowired
	private FindMyPersonalTaskServDu findMyPersonalTaskServDu;
	@Autowired
	private ProcModTacheLoginServDu procModTacheLoginServDu;
	@Autowired
	private ChangeToArtificialServiceServDu changeToArtificialServiceServDu;
	@Autowired
	private CheckProcessServDu checkProcessServDu;
	@Autowired
	private OrdModFunctionServDu ordModFunctionServDu;
	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;
	@Autowired
	private ProcessCirculationServDu processCirculationServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	/**
	 * ⦁	预订单处理  UOC0033
	 * @param paraVo
	 * @return
	 */
	@Override
	public UocMessage preOrderDeal(ParaVo vo) throws Exception{
		logger.info("--------UOC0033----preOrderDeal---------");
		UocMessage message =new UocMessage();
		String jsession_id =vo.getJsession_id();
		String sale_order_no =vo.getSale_order_no();
		String flow_type =vo.getFlow_type();
		String manual_flag =vo.getManual_flag();
		String all_json_info =vo.getAll_json_info();
		Map<String,String> action_code =vo.getAction_code();


		//1、	调用BASE0008记录接口日志
		//2如果manual_flag为0，则调用BASE0017获取登陆信息服务，校验是否登陆，以及取出相应的工号信息
		Map<String, Object> oper_info =new HashMap<String,Object>();
		if("0".equals(manual_flag)){
			UocMessage res =operServDu.isLogin(jsession_id);
			if(!"0000".equals(res.getRespCode())){
				logger.info("--------"+res.getContent()+"---------");
				throw new UocException(res);
			}
			oper_info =(Map<String, Object>) res.getArgs().get("oper_info");
		}
		/*
		 * 3、先查询销售订单，判断当前状态必须为101；查出预订单流程实例，如果manual_flag为0，则调用BASE0012取出环节，
		 * 再通过流程实例、环节查询人工任务实例表，没数据则报错，有数据则再通过前面获取到的工号权限信息，查询环节对应角色表，
		 * 判断当前工号是否有执行当前任务权限，没有权限直接报错
		 */
		InfoSaleOrderVo infoSaleOrderVo =new InfoSaleOrderVo();
		infoSaleOrderVo.setSale_order_no(sale_order_no);
		infoSaleOrderVo =infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
		if(infoSaleOrderVo ==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("无对应的销售订单");
			return message;
		}
		if(!"101".equals(infoSaleOrderVo.getOrder_state())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("订单状态异常");
			return message;
		}
		String proc_inst_id =infoSaleOrderVo.getProc_inst_id();
		String tache_code="";
		String current_task ="";
		String current_task_name ="";
		//屏蔽原有任务校验逻辑

		if("0".equals(flow_type) || "1".equals(flow_type) || "3".equals(flow_type)){
			UocMessage checkRes =checkProcessServDu.checkProcess(infoSaleOrderVo.getProc_inst_id(), sale_order_no, "0", action_code);
			if(!"0000".equals(checkRes.getRespCode())){
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("流转要求检验异常");
				return message;
			}
		}
		if(!"3".equals(flow_type)){
			//调用BASE0003订单模板获取服务取到出参模板，这里入参order_type填100，query_type填101
			UocMessage result_mod_code =ordModFunctionServDu.queryOrdMod(sale_order_no, "101", "100");
			if("0000".equals(result_mod_code.getRespCode())){
				//先调用BASE0023服务拼出param_json，然后根据这个值再加上order_type=100，
				String param_json=operServDu.loginShareParam(oper_info, jsession_id);
				JSONObject obj =JSONObject.fromObject(param_json);
				obj.put("order_type","100");
				param_json =obj.toString();
				//调用BASE0006根据订单模板出库服务，取出interface_type、interface_param_json以及json串，
				String mod_code =(String)result_mod_code.getArgs().get("mod_code");
				logger.info("------------mod_code---------"+mod_code);
				UocMessage messageOrdModOut = ordModFunctionServDu.outByOrdMod(sale_order_no, mod_code, "100",param_json);
				Map<String,Object> argsOrdModOut =new HashMap<String,Object>();
				if("0000".equals(messageOrdModOut.getRespCode())){
					 argsOrdModOut = messageOrdModOut.getArgs();
				}
				String json_info_out = (String) argsOrdModOut.get("json_info");
				String interface_type = (String) argsOrdModOut.get("interface_type");
				String interface_param_json = (String) argsOrdModOut.get("interface_param_json");

				//再通过这些信息调用BASE0018调用能力平台服务，然后再调用BASE0008服务记录能力平台接口日志；
				UocMessage messageAbilityPlat = abilityPlatformServDu.CallAbilityPlatform(jsession_id, json_info_out, interface_type, interface_param_json);
				if(messageAbilityPlat != null){
					//9、如果有调用能力平台接口，则返回的信息挂在args节点下返回
					String code = (String) messageAbilityPlat.getArgs().get("code");
					if(code != null && "200".equals(code)){
						//如果有调用能力平台接口并且args节点下返回的信息不为空，则调用BASE0003订单模板获取服务取到入参模板，这里入参order_type填101，query_type填100，
						//如果查询到数据，则调用BASE0005根据订单模板入库服务，其中json串参数用args节点下的map信息转换成 json串 TODO
						logger.info("-----------------"+messageAbilityPlat.getContent()+"---------------------");
						String argsAbilityPlat = (String) messageAbilityPlat.getArgs().get("json_info");
						message.addArg("abilityPlat", argsAbilityPlat);

						if(argsAbilityPlat != null && !"".equals(argsAbilityPlat)){
							//BASE0003
							UocMessage messageOrdModAttrOutTwo = ordModFunctionServDu.queryOrdMod(sale_order_no, "100", "101");
							if(messageOrdModAttrOutTwo != null){
								if("0000".equals(messageOrdModAttrOutTwo.getRespCode())){
									logger.info("--------BASE0003-----------"+messageOrdModAttrOutTwo.getContent()+"--------------------");
									String mod_code_in = (String) messageOrdModAttrOutTwo.getArgs().get("mod_code");
									if(mod_code_in != null && !"".equals(mod_code_in)){
										//BASE0005订单模板入库服务
										OrdModVo ordModVoOutPara = new OrdModVo();
										ordModVoOutPara.setMod_code(mod_code_in);
										ordModVoOutPara.setOrder_no(sale_order_no);
										ordModVoOutPara.setOrder_type("100");
										//其中json串参数用args节点下的map信息转换成 json串 ??
										ordModVoOutPara.setJson_in(argsAbilityPlat);
										ordModVoOutPara.setJsession_id(jsession_id);
										UocMessage messageInsertByOrdMod = ordModFunctionServDu.insertByOrdMod(ordModVoOutPara);
										if(!"0000".equals(messageInsertByOrdMod.getRespCode()))	{
											logger.info("-------入库失败------------"+messageInsertByOrdMod.getContent()+"--------------------");
											ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
											procInstTaskInstVo.setOrder_no(sale_order_no);
											procInstTaskInstVo.setOrder_type("100");
											UocMessage personTaskMgs = changeToArtificialServiceServDu.changeToArtificialService(
													procInstTaskInstVo, "102");// task_property
																				// 任务性质
											if(personTaskMgs != null){
												message.setContent("获取订单入参模板失败"+personTaskMgs.getContent());
											}else{
												message.setContent("获取订单入参模板失败"+"转人工任务处理失败");
											}
											return messageInsertByOrdMod;
										}
									}
								}
							}else{
								message.setContent("获取订单入参模板失败");

									logger.info("------------------获取订单入参模板失败,转人工任务处理------------------");
								ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
								procInstTaskInstVo.setOrder_no(sale_order_no);
								procInstTaskInstVo.setOrder_type("100");
								UocMessage personTaskMgs = changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo,
										"102");// task_property 任务性质
									if(personTaskMgs != null){
										message.setContent("获取订单入参模板失败"+personTaskMgs.getContent());
									}else{
										message.setContent("获取订单入参模板失败"+"转人工任务处理失败");
									}
								return message;
							}

					}else{
						logger.info("----------能力平台调用失败----------");
							ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
							procInstTaskInstVo.setOrder_no(sale_order_no);
							procInstTaskInstVo.setOrder_type("100");
							UocMessage personTaskMgs = changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "102");// task_property
																																			// 任务性质
						if(personTaskMgs != null){
							message.setContent("获取订单入参模板失败"+personTaskMgs.getContent());
						}else{
							message.setContent("获取订单入参模板失败"+"转人工任务处理失败");
						}
						return messageAbilityPlat;
					}

				}else{
					logger.info("----------能力平台调用失败----------");
						ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
						procInstTaskInstVo.setOrder_no(sale_order_no);
						procInstTaskInstVo.setOrder_type("100");
						UocMessage personTaskMgs = changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "102");// task_property
																																		// 任务性质
					if(personTaskMgs != null){
						message.setContent("获取订单入参模板失败"+personTaskMgs.getContent());
					}else{
						message.setContent("获取订单入参模板失败"+"转人工任务处理失败");
					}
					return messageAbilityPlat;
				}

			}

		 }
		}
		if("0".equals(flow_type) || "1".equals(flow_type) || "3".equals(flow_type)){
			//调用BASE0016流程流转服务，传入1时要把操作编码也同时传入
			if("0".equals(flow_type)){
				message =processCirculationServDu.processCirculation(sale_order_no, "100", flow_type, null,"");
			}else{
				message =processCirculationServDu.processCirculation(sale_order_no, "100", flow_type,action_code,"");
			}
			if(!"0000".equals(message.getRespCode())){
				throw new UocException(message);
			}
		}else if("2".equals(flow_type)){

		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("预订单处理成功");
		return message;

 }


	/**
	 * 预订单流程启动 UOC0025
	 * @param paraVo
	 * @return
	 */
	@Override
	public UocMessage preOrderStart(ParaVo paraVo) throws Exception{
		logger.info("rest-----------advanceOrderStart UOC0025");
		UocMessage message =new UocMessage();

			String jsession_id =paraVo.getJsession_id();
			String sale_order_no =paraVo.getSale_order_no();

			/*
			 * 1、调用BASE0008记录接口日志 需要参数 service_code json_in json_out（待补充）
			 */
			/*
			 * 校验jession_id
			 */
			UocMessage res =operServDu.isLogin(jsession_id);
			if(!"0000".equals(res.getRespCode())){
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("无对应工号信息");
				return message;
			}
			Map<String,String> oper_info =(Map)res.getArgs().get("oper_info");
			String oper_no =oper_info.get("oper_no");
			String province_code =oper_info.get("province_code");
			String area_code =oper_info.get("area_code");
			/*
			 * 查询销售订单，无信息报错, 然后判断当前订单状态如果不为100则报错
			 */
			InfoSaleOrderVo infoSaleOrderVo =new InfoSaleOrderVo();
			infoSaleOrderVo.setSale_order_no(sale_order_no);
			infoSaleOrderVo =infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			if(infoSaleOrderVo ==null){
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("无对应的销售订单");
				return message;
			}
			if(!"100".equals(infoSaleOrderVo.getOrder_state())){
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("订单状态异常");
				return message;
			}
			//调用流程启动服务BASE0011，oper_type填104
			message =startProcessServDu.startProcess(sale_order_no, "104",jsession_id);
			if(!"0000".equals(message.getRespCode())){
				throw new UocException(message);
			}

			infoSaleOrderVo.setOrder_state("101");
			boolean flag =infoSaleOrderServDu.updateInfoSaleOrder(infoSaleOrderVo);
			if(!flag){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("更新订单失败");
				throw new UocException(message);
			}
			List<String> list =new ArrayList<String>();
			InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
			infoServiceOrderVo.setSale_order_no(oper_no);
			List<InfoServiceOrderVo> listVo = infoServiceOrderServDu.queryInfoServiceOrderBySaleOrderNo(infoServiceOrderVo);
			for(InfoServiceOrderVo vo : listVo){
				list.add(vo.getServ_order_no());
			}

			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("预订单流程启动成功");
			message.addArg("serv_order_no_list", listVo);
			return message;

	}

}
