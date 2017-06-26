package com.tydic.unicom.uoc.service.mod.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.service.cache.service.redis.interfaces.RedisServiceServ;
import com.tydic.unicom.service.cache.service.redis.po.OrdModApp;
import com.tydic.unicom.service.cache.service.redis.po.OrdModAttrDefine;
import com.tydic.unicom.service.cache.service.redis.po.OrdModDefine;
import com.tydic.unicom.service.cache.service.redis.po.OrdModMutiTable;
import com.tydic.unicom.service.cache.service.redis.po.OrdModParaFieldRelation;
import com.tydic.unicom.service.cache.service.redis.po.RedisData;
import com.tydic.unicom.uoc.base.uoccode.po.OrdModAppPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModAppServ;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModAttrDefineServ;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModDefineServ;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.OrdModParaFieldRelationServ;
import com.tydic.unicom.uoc.base.uocinst.po.InfoDeliverOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderFeePo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderInvoicePo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderPayPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoOfrOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoOrderCancelPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderAttrPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderDistrDetailPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderDistributionPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderEditPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderExtPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderFeePo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderOfrMapPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPersionCertPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPersionPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderServMapPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderActivityPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderAgreementPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderAttrPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderCollectionPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderDeveloperPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderExtPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderFeePo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderFixPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderGoodNbrPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderGuarantorPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderM165Po;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPackagePo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPayPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPersionPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderProdMapPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderSimCardPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderTerminalPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoDeliverOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderFeeServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderInvoiceServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderPayServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOfrOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoOrderCancelServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoPayOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderAttrServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderDistrDetailServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderDistributionServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderEditServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderExtServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderFeeServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderOfrMapServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderPersionCertServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderPersionServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderServMapServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderActivityServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderAgreementServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderAttrServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderCollectionServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderDeveloperServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderExtServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderFeeServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderFixServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderGoodNbrServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderGuarantorServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderM165Serv;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderPackageServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderPayServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderPersionServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderProdMapServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderSimCardServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderTerminalServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskDealRecordServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.uoc.pub.common.service.po.ActivemqSendPo;
import com.tydic.unicom.uoc.service.activiti.interfaces.FindMyPersonalTaskServDu;
import com.tydic.unicom.uoc.service.common.interfaces.ActivemqSendServDu;
import com.tydic.unicom.uoc.service.common.interfaces.FunctionReflecServDu;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.SqlServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModFunctionServDu;
import com.tydic.unicom.uoc.service.mod.vo.DefaultOrdModVo;
import com.tydic.unicom.uoc.service.mod.vo.OrdModVo;
import com.tydic.unicom.uoc.service.mod.vo.OutOrdModeVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderAttrServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderBaseDu;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("OrdModFunctionServDu")
public class OrdModFunctionServDuImpl implements OrdModFunctionServDu{
	Logger logger = Logger.getLogger(OrdModFunctionServDuImpl.class);


	@Autowired
	private OrdModAppServ ordModAppServ;

	@Autowired
	private OrdModParaFieldRelationServ ordModParaFieldRelationServ;

	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;

	@Autowired
	private SqlServDu sqlServDu;

	@Autowired
	private OrdModDefineServ ordModDefineServ;

	@Autowired
	private FunctionReflecServDu functionReflecServDu;

	@Autowired
	private InfoServiceOrderAttrServDu infoServiceOrderAttrServDu;

	@Autowired
	private OrdModAttrDefineServ ordModAttrDefineServ;

	@Autowired
	private GetIdServDu getIdServDu;

	@Autowired
	private FindMyPersonalTaskServDu findMyPersonalTaskServDu;

	@Autowired
	private InfoSaleOrderServ infoSaleOrderServ;

	@Autowired
	private InfoSaleOrderFeeServ infoSaleOrderFeeServ;

	@Autowired
	private InfoSaleOrderAttrServ infoSaleOrderAttrServ;

	@Autowired
	private InfoSaleOrderPersionServ infoSaleOrderPersionServ;

	@Autowired
	private InfoSaleOrderDistributionServ infoSaleOrderDistributionServ;

	@Autowired
	private InfoSaleOrderDistrDetailServ infoSaleOrderDistrDetailServ;

	@Autowired
	private InfoSaleOrderOfrMapServ infoSaleOrderOfrMapServ;

	@Autowired
	private InfoSaleOrderServMapServ infoSaleOrderServMapServ;

	@Autowired
	private InfoOfrOrderServ infoOfrOrderServ;

	@Autowired
	private InfoOfrOrderInvoiceServ infoOfrOrderInvoiceServ;

	@Autowired
	private InfoOfrOrderPayServ infoOfrOrderPayServ;

	@Autowired
	private InfoOfrOrderFeeServ infoOfrOrderFeeServ;

	@Autowired
	private InfoServiceOrderServ infoServiceOrderServ;

	@Autowired
	private InfoServiceOrderPayServ infoServiceOrderPayServ;

	@Autowired
	private InfoServiceOrderFeeServ infoServiceOrderFeeServ;

	@Autowired
	private InfoServiceOrderAttrServ infoServiceOrderAttrServ;

	@Autowired
	private InfoServiceOrderProdMapServ infoServiceOrderProdMapServ;

	@Autowired
	private InfoServiceOrderTerminalServ infoServiceOrderTerminalServ;

	@Autowired
	private InfoServiceOrderActivityServ infoServiceOrderActivityServ;

	@Autowired
	private InfoServiceOrderAgreementServ infoServiceOrderAgreementServ;

	@Autowired
	private InfoServiceOrderPackageServ infoServiceOrderPackageServ;

	@Autowired
	private InfoServiceOrderSimCardServ infoServiceOrderSimCardServ;

	@Autowired
	private InfoServiceOrderFixServ infoServiceOrderFixServ;

	@Autowired
	private InfoServiceOrderM165Serv infoServiceOrderM165Serv;

	@Autowired
	private InfoServiceOrderGoodNbrServ infoServiceOrderGoodNbrServ;

	@Autowired
	private InfoServiceOrderCollectionServ infoServiceOrderCollectionServ;

	@Autowired
	private InfoServiceOrderGuarantorServ infoServiceOrderGuarantorServ;

	@Autowired
	private InfoServiceOrderDeveloperServ infoServiceOrderDeveloperServ;

	@Autowired
	private InfoServiceOrderPersionServ infoServiceOrderPersionServ;

	@Autowired
	private InfoSaleOrderEditServ infoSaleOrderEditServ;

	@Autowired
	private InfoPayOrderServ infoPayOrderServ;

	@Autowired
	private InfoDeliverOrderServ infoDeliverOrderServ;

	@Autowired
	private ProcInstTaskInstServ procInstTaskInstServ;

	@Autowired
	private ProcInstTaskDealRecordServ procInstTaskDealRecordServ;

	@Autowired
	private InfoSaleOrderExtServ infoSaleOrderExtServ;

	@Autowired
	private InfoServiceOrderExtServ infoServiceOrderExtServ;

	@Autowired
	private RedisServiceServ redisServiceServ;

	@Autowired
	private InfoSaleOrderPersionCertServ infoSaleOrderPersionCertServ;

	@Autowired
	private InfoOrderCancelServ infoOrderCancelServ;
	
	@Autowired
	private InfoServiceOrderBaseDu infoServiceOrderBaseDu;
	
	@Autowired
	private ActivemqSendServDu activemqSendServDu;
	

	@Override
	public UocMessage queryOrdMod(String order_no,String query_type,String order_type) throws Exception{

		UocMessage message= new UocMessage();
		String proc_inst_id="";
		String tache_id="";
		OrdModApp ordModAppPoRes = new OrdModApp();
		OrdModAppPo ordModAppPo = new OrdModAppPo();
		//根据订单类型获取表数据
		if("101".equals(order_type)){
			//先根据服务订单号查询服务订单表
			InfoServiceOrderPo infoServiceOrderPo =new InfoServiceOrderPo();
			infoServiceOrderPo.setServ_order_no(order_no);
			InfoServiceOrderPo infoServiceOrderRes=infoServiceOrderServ.getInfoServiceOrderByServOrderNo(infoServiceOrderPo);
			if(infoServiceOrderRes==null){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("服务订单表无数据");
				return message;

			}
			ordModAppPo.setProvince_code(infoServiceOrderRes.getProvince_code());
			ordModAppPo.setArea_code(infoServiceOrderRes.getArea_code());
			ordModAppPo.setMod_used(query_type);
			ordModAppPo.setOper_code(infoServiceOrderRes.getOper_code());
			ordModAppPo.setTele_type(infoServiceOrderRes.getTele_type());
			if("201".equals(infoServiceOrderRes.getOrder_state())){
				proc_inst_id=infoServiceOrderRes.getProc_inst_id();
				tache_id=getTacheId(proc_inst_id,order_no);
				if(tache_id==null){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("获取当前环节失败");
					return message;
				}
				ordModAppPo.setTache_id(tache_id);
			}else if("100".equals(infoServiceOrderRes.getOrder_state())){
				InfoSaleOrderPo res = new InfoSaleOrderPo();
				res.setSale_order_no(infoServiceOrderRes.getSale_order_no());
				InfoSaleOrderPo temp=infoSaleOrderServ.getInfoSaleOrderPoBySaleOrderNo(res);
				if("100".equals(temp.getOrder_state())){
					ordModAppPo.setTache_id("A00000");
				}else if("101".equals(temp.getOrder_state())){
					proc_inst_id=temp.getPre_proc_inst_id();
					tache_id=getTacheId(proc_inst_id,temp.getSale_order_no());
					if(tache_id==null){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("获取当前环节失败");
						return message;
					}
					ordModAppPo.setTache_id(tache_id);
				}else if("102".equals(temp.getOrder_state())){				
					ordModAppPo.setTache_id("B00000");
				}else if("201".equals(temp.getOrder_state())){
					proc_inst_id=temp.getProc_inst_id();
					tache_id=getTacheId(proc_inst_id,temp.getSale_order_no());
					if(tache_id==null){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("获取当前环节失败");
						return message;
					}
					ordModAppPo.setTache_id(tache_id);
				}else{
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("销售订单状态错误,获取服务环节失败");
					return message;
				}
			}

			//获取类型为销售订单模板
		}else if("100".equals(order_type)){
			//先根据服务订单号查询销售订单表
			InfoSaleOrderPo infoSaleOrderPo = new InfoSaleOrderPo();
			infoSaleOrderPo.setSale_order_no(order_no);
			InfoSaleOrderPo infoSaleOrderPoRes=infoSaleOrderServ.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderPo);
			if(infoSaleOrderPoRes==null){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("销售订单表无数据");
				return message;
			}
			ordModAppPo.setProvince_code(infoSaleOrderPoRes.getProvince_code());
			ordModAppPo.setArea_code(infoSaleOrderPoRes.getArea_code());
			ordModAppPo.setMod_used(query_type);
			ordModAppPo.setOper_code(infoSaleOrderPoRes.getOrder_type());
			if("100".equals(infoSaleOrderPoRes.getOrder_state())){
				ordModAppPo.setTache_id("A00000");
			}else if("101".equals(infoSaleOrderPoRes.getOrder_state())){
				proc_inst_id=infoSaleOrderPoRes.getPre_proc_inst_id();
				tache_id=getTacheId(proc_inst_id,order_no);
				if(tache_id==null){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("获取当前环节失败");
					return message;
				}
				ordModAppPo.setTache_id(tache_id);
			}else if("102".equals(infoSaleOrderPoRes.getOrder_state())){				
				ordModAppPo.setTache_id("B00000");
			}else if("201".equals(infoSaleOrderPoRes.getOrder_state())){
				proc_inst_id=infoSaleOrderPoRes.getProc_inst_id();
				tache_id=getTacheId(proc_inst_id,order_no);
				if(tache_id==null){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("获取当前环节失败");
					return message;
				}
				ordModAppPo.setTache_id(tache_id);
			}else{
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("销售订单状态错误,获取当前环节失败");
				return message;
			}
		}
		UocMessage mes=redisServiceServ.queryDataFromRedis("queryOrdModAppOrderForm");
		if(!"0000".equals(mes.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取订单模板应用表缓存失败");
			return message;
		}
		RedisData redisData=(RedisData) mes.getArgs().get("RedisDataResult");
		String tele_type=ordModAppPo.getTele_type();
		if(tele_type!=null && !"".equals(tele_type)){
			//在根据服务订单表里的省份、地域、环节、业务和模板用途查询订单模板应用表
			ordModAppPoRes=(OrdModApp) redisData.getMap().get(ordModAppPo.getProvince_code()+"|"+ordModAppPo.getArea_code()+"|"+ordModAppPo.getMod_used()+"|"+ordModAppPo.getOper_code()+"|"+ordModAppPo.getTache_id()+"|"+ordModAppPo.getTele_type());
			if(ordModAppPoRes==null){
				ordModAppPoRes=(OrdModApp) redisData.getMap().get(ordModAppPo.getProvince_code()+"|"+"*"+"|"+ordModAppPo.getMod_used()+"|"+ordModAppPo.getOper_code()+"|"+ordModAppPo.getTache_id()+"|"+ordModAppPo.getTele_type());
			}
		}else{
			//在根据服务订单表里的省份、地域、环节、业务和模板用途查询订单模板应用表
			ordModAppPoRes=(OrdModApp) redisData.getMap().get(ordModAppPo.getProvince_code()+"|"+ordModAppPo.getArea_code()+"|"+ordModAppPo.getMod_used()+"|"+ordModAppPo.getOper_code()+"|"+ordModAppPo.getTache_id());
			if(ordModAppPoRes==null){
				ordModAppPoRes=(OrdModApp) redisData.getMap().get(ordModAppPo.getProvince_code()+"|"+"*"+"|"+ordModAppPo.getMod_used()+"|"+ordModAppPo.getOper_code()+"|"+ordModAppPo.getTache_id());
			}
		}
		
		if(ordModAppPoRes==null){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取订单模板无数据");
			return message;
		}
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("获取订单模板成功");
		message.addArg("mod_code", ordModAppPoRes.getMod_code());
		return message;
	}

	/**
	 * 根据流程ID和订单号获取环节
	 * @param proc_inst_id
	 * @param order_no
	 * @return
	 * @throws Exception 
	 */
	public String getTacheId(String proc_inst_id,String order_no) throws Exception{

		//获取当前环节
		UocMessage message=findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(proc_inst_id, order_no);
		if("0000".equals(message.getRespCode().toString())){
			String tache_id=(String) message.getArgs().get("current_tache");
			return tache_id;
		}else{
			return null;
		}

	}


	@SuppressWarnings("unchecked")
	@Override
	public UocMessage insertByOrdMod(OrdModVo ordModVo) throws Exception{
		UocMessage message= new UocMessage();
		if(ordModVo.getMod_code()==null||"".equals(ordModVo.getMod_code())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("mod_code参数不能为空");
			return message;
		}
		if(ordModVo.getOrder_no()==null||"".equals(ordModVo.getOrder_no())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("order_no参数不能为空");
			return message;
		}
		if(ordModVo.getOrder_type()==null||"".equals(ordModVo.getOrder_type())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("order_type参数不能为空");
			return message;
		}
		if(ordModVo.getJson_in()==null||"".equals(ordModVo.getJson_in())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("json_in参数不能为空");
			return message;
		}
		String modCode=ordModVo.getMod_code();
		String orderType=ordModVo.getOrder_type();
		Map<String, Object> ordModMap = new HashMap<String, Object>();
		List<OutOrdModeVo> list =new ArrayList<OutOrdModeVo>();
		List<OutOrdModeVo> totalList =new ArrayList<OutOrdModeVo>();

		//根据订单模板查询订单参数入库定义表，取出对应配置
		UocMessage paraMessage=redisServiceServ.queryDataFromRedis("queryordModParaFieldRelationByAttrCode");
		if(!"0000".equals(paraMessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取订单参数与数据库表字段关系表缓存失败");
			return message;
		}
		RedisData redisDataPara=(RedisData) paraMessage.getArgs().get("RedisDataResult");
		Map<String,Object> ordModParaFiledMap=redisDataPara.getMap();
		logger.info("++++++ordModParaFiledMap++++++++"+ordModParaFiledMap.toString());
		//根据mod_code取模板参数定义表默认字段缓存
		UocMessage defineMessage=redisServiceServ.queryDataFromRedis("queryDefaultOrdModAttrDefineBymodCode");
		if(!"0000".equals(defineMessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取模板参数定义表缓存失败");
			return message;
		}
		RedisData redisData=(RedisData) defineMessage.getArgs().get("RedisDataResult");
		List<OrdModAttrDefine> ordModAttrDefineList=(List<OrdModAttrDefine>) redisData.getMap().get(modCode+"Default");

		//根据mod_code取模板参数定义表所有字段缓存
		UocMessage allmessage=redisServiceServ.queryDataFromRedis("queryAllOrdModAttrDefineBymodCode");
		if(!"0000".equals(allmessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取模板参数定义表缓存失败");
			return message;
		}
		RedisData redisAllData=(RedisData) allmessage.getArgs().get("RedisDataResult");
		Map<String,Object> allDataMap= (Map<String, Object>) redisAllData.getMap().get(modCode);

		//取ord_mod_muti_table
		UocMessage muitmessage=redisServiceServ.queryDataFromRedis("ord_muti_table");
		if(!"0000".equals(muitmessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取ord_mod_muti_table缓存失败");
			return message;
		}
		RedisData redisMuitData=(RedisData) muitmessage.getArgs().get("RedisDataResult");
		Map<String,Object> muitDataMap= (Map<String, Object>) redisMuitData.getMap();
		//根据订单Id查询销售订单号、服务订单号、商品订单号、省份、区域
		DefaultOrdModVo defaultOrdModVo = new DefaultOrdModVo();
		String province_code="";
		if("101".equals(orderType)){
			InfoServiceOrderPo infoServiceOrderPoTemp =new InfoServiceOrderPo();
			infoServiceOrderPoTemp.setServ_order_no(ordModVo.getOrder_no());
			InfoServiceOrderPo infoServiceOrderPo=infoServiceOrderServ.getInfoServiceOrderByServOrderNo(infoServiceOrderPoTemp);
			if(infoServiceOrderPo==null){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("获取服务订单表数据失败");
				return message;
			}
			province_code=infoServiceOrderPo.getProvince_code();
			defaultOrdModVo.setServ_order_no(infoServiceOrderPo.getServ_order_no());
			defaultOrdModVo.setSale_order_no(infoServiceOrderPo.getSale_order_no());
			defaultOrdModVo.setOfr_order_no(infoServiceOrderPo.getOfr_order_no());
			defaultOrdModVo.setProvince_code(infoServiceOrderPo.getProvince_code());
			defaultOrdModVo.setArea_code(infoServiceOrderPo.getArea_code());
			defaultOrdModVo.setPart_month(infoServiceOrderPo.getPart_month());
		}else if("100".equals(orderType)){
			InfoSaleOrderPo infoSaleOrderPoTemp =new InfoSaleOrderPo();
			infoSaleOrderPoTemp.setSale_order_no(ordModVo.getOrder_no());
			InfoSaleOrderPo infoSaleOrderPo=infoSaleOrderServ.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderPoTemp);
			if(infoSaleOrderPo==null){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("获取销售订单表数据失败");
				return message;
			}
			defaultOrdModVo.setSale_order_no(infoSaleOrderPo.getSale_order_no());
			defaultOrdModVo.setProvince_code(infoSaleOrderPo.getProvince_code());
			defaultOrdModVo.setArea_code(infoSaleOrderPo.getArea_code());
			defaultOrdModVo.setPart_month(infoSaleOrderPo.getPart_month());
		}


		int upperId = 1;
		int maxId = 1;

		ordModMap=jsonToBeanServDu.jsonToMap(ordModVo.getJson_in());
		//循环判断map的Vvalue类型
		Iterator it=ordModMap.keySet().iterator();  
		while(it.hasNext()){
			String key=it.next().toString();			
			//判断Value是什么类型
			if("class java.lang.String".equals(ordModMap.get(key).getClass().toString())){
				String value=ordModMap.get(key).toString();
				StringToVo(key,value,list,ordModParaFiledMap,ordModAttrDefineList,modCode,allDataMap,upperId,upperId);
			}else if("class java.util.HashMap".equals(ordModMap.get(key).getClass().toString())){
				Map<String, Object> resMap = new HashMap<String, Object>();
				resMap=(Map<String, Object>) ordModMap.get(key);
				maxId = MapToVo(resMap,list,ordModParaFiledMap,ordModAttrDefineList,modCode,allDataMap,upperId,maxId);
			}else if("class java.util.ArrayList".equals(ordModMap.get(key).getClass().toString())){
				List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
				listMap=(List<Map<String, Object>>) ordModMap.get(key);
				maxId = ListToVo(listMap,list,ordModParaFiledMap,ordModAttrDefineList,modCode,allDataMap,upperId,maxId);
			}
		}

		if(ordModAttrDefineList!=null){
			totalList=totalDataToVo(ordModParaFiledMap,ordModAttrDefineList,modCode,upperId);
			AddDefault(totalList,list);
		}

		Map<String, List<OutOrdModeVo>>  splitMap = splitOutOrdModeVolist(list);
		Map<String, List<List<OutOrdModeVo>>> tempMap=splitMapListById(splitMap);
		Map<String, List<String>>  sqlMap=getSqlByMap(tempMap,splitMap,orderType,defaultOrdModVo,muitDataMap);
		Boolean results=implementSql(sqlMap);
		if(!results){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("执行入库sql语句失败");
			return message;
		}
		/**
		 * 入库成功后调用BASE0009服务写消息队列中间表，
		 * 在消息队列中调用BASE0032服务生成订单中间表数据
		 */
		if("101".equals(orderType)){
			Map<String, Object> ordModMapTemp = new HashMap<String, Object>();
			ordModMapTemp.put("serv_order_no", ordModVo.getOrder_no());
			ordModMapTemp.put("order_type", ordModVo.getOrder_type());
			ordModMapTemp.put("jsession_id", ordModVo.getJsession_id());
			String json_info=jsonToBeanServDu.mapToJson(ordModMapTemp);
			
			ActivemqSendPo activemqSendPo = new ActivemqSendPo();
			activemqSendPo.setService_code("BASE0032");
			activemqSendPo.setJson_in(json_info);
			activemqSendPo.setOrder_id(ordModVo.getOrder_no());
			activemqSendPo.setProvince_code(province_code);
			activemqSendPo.setOrder_type(orderType);
			String queue_id="OrderFinish";
			UocMessage asMessage = activemqSendServDu.createMessageQueue(activemqSendPo, queue_id);
			if("0000".equals(asMessage.getRespCode())){
				logger.info("==========创建消息队列订单数据es成功==========");
			}
		}
		
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("根据订单模板入库成功");
		return message;

	}



	/**
	 * 根据map的key找到key对应的表和字段，然后组装成一个VO
	 * @param key
	 * @param value
	 * @param list
	 * @param ordModParaFiledMap
	 * @param currId
	 * @param upperId
	 */
	public void StringToVo(String key,String value,List<OutOrdModeVo> list,Map<String,Object> ordModParaFiledMap,List<OrdModAttrDefine> ordModAttrDefineList,String modCode,Map<String,Object> allDataMap,int currId,int upperId){
		String currIdTemp=Integer.toString(currId);
		String upperIdTemp=Integer.toString(upperId);
		OutOrdModeVo outOrdModeVo = new OutOrdModeVo();
		OrdModAttrDefine po1= new OrdModAttrDefine();
		po1=(OrdModAttrDefine) allDataMap.get(key);
		if(po1!=null){
			OrdModParaFieldRelation po= new OrdModParaFieldRelation();
			po=(OrdModParaFieldRelation) ordModParaFiledMap.get(key+"-"+modCode);
			if(po==null){
				po=(OrdModParaFieldRelation) ordModParaFiledMap.get(key+"-"+"*");
			}
			if(po!=null){
				if(ordModAttrDefineList!=null){
					for(OrdModAttrDefine ordModAttrDefinePo: ordModAttrDefineList){
						if(key.equals(ordModAttrDefinePo.getAttr_code())){
							outOrdModeVo.setAttr_type(ordModAttrDefinePo.getAttr_type());
							ordModAttrDefineList.remove(ordModAttrDefinePo);
							break;
						}

					}
				}
				outOrdModeVo.setAttr_code(key);
				outOrdModeVo.setTable_name(po.getTable_name());
				outOrdModeVo.setField_name(po.getField_name());
				outOrdModeVo.setField_value(value);
				outOrdModeVo.setCurr_seq(currIdTemp);
				outOrdModeVo.setUpper_seq(upperIdTemp);
				list.add(outOrdModeVo);

			}
		}
	}



	/**
	 * 将map对象转换为对应的VO
	 * @param ordModMap
	 * @param list
	 * @param ordModParaFiledMap
	 * @param upperId
	 * @param maxId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  int MapToVo(Map<String, Object> ordModMap,List<OutOrdModeVo> list,Map<String,Object> ordModParaFiledMap,List<OrdModAttrDefine> ordModAttrDefineList,String modCode,Map<String,Object> allDataMap,int upperId,int maxId) throws Exception{
		maxId ++;
		int currId = maxId;
		//循环判断map的value类型
		Iterator item=ordModMap.keySet().iterator();  
		while(item.hasNext()){
			String key=item.next().toString();			
			//判断Value是什么类型
			if("class java.lang.String".equals(ordModMap.get(key).getClass().toString())){
				String value=ordModMap.get(key).toString();
				StringToVo(key,value,list,ordModParaFiledMap,ordModAttrDefineList,modCode,allDataMap,currId,upperId);
			}
			else if("class java.util.HashMap".equals(ordModMap.get(key).getClass().toString())){
				Map<String, Object> resMap = new HashMap<String, Object>();
				resMap=(Map<String, Object>) ordModMap.get(key);
				maxId = MapToVo(resMap,list,ordModParaFiledMap,ordModAttrDefineList,modCode,allDataMap,currId,currId);
			}else if("class java.util.ArrayList".equals(ordModMap.get(key).getClass().toString())){
				List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
				listMap=(List<Map<String, Object>>) ordModMap.get(key);
				maxId = ListToVo(listMap,list,ordModParaFiledMap,ordModAttrDefineList,modCode,allDataMap,currId,currId);
			}
		}
		return maxId;
	}

	/**
	 * 将listmap转换为对应的VO
	 * @param listMap
	 * @param list
	 * @param ordModParaFiledMap
	 * @param upperId
	 * @param maxId
	 * @return
	 * @throws Exception
	 */
	public  int ListToVo(List<Map<String,Object>> listMap,List<OutOrdModeVo> list,Map<String,Object> ordModParaFiledMap,List<OrdModAttrDefine> ordModAttrDefineList,String modCode,Map<String,Object> allDataMap,int upperId,int maxId) throws Exception{
		//循环取出list里面的map对象
		for(Map<String,Object> splMap:listMap){
			maxId = MapToVo(splMap,list,ordModParaFiledMap,ordModAttrDefineList,modCode,allDataMap,upperId,maxId);
		}
		return maxId;
	}

	/**
	 * 根据模板参数定义表和订单参数与数据库表字段关系表 将默认值转换成VO
	 * @param ordModParaFiledMap
	 * @param ordModAttrDefineList
	 * @param maxId
	 * @return
	 */
	public  List<OutOrdModeVo> totalDataToVo(Map<String,Object> ordModParaFiledMap,List<OrdModAttrDefine> ordModAttrDefineList,String modCode,int maxId){
		List<OutOrdModeVo> tolList= new ArrayList<OutOrdModeVo>();
		//循环取出list里面的map对象
		for(OrdModAttrDefine req:ordModAttrDefineList){
			String idTemp=Integer.toString(maxId);
			OrdModParaFieldRelation po= new OrdModParaFieldRelation();
			po=(OrdModParaFieldRelation) ordModParaFiledMap.get(req.getAttr_code()+"-"+modCode);
			if(po==null){
				po=(OrdModParaFieldRelation) ordModParaFiledMap.get(req.getAttr_code()+"-"+"*");
			}
			if(po!=null){
				OutOrdModeVo outOrdModeVo = new OutOrdModeVo();
				outOrdModeVo.setAttr_code(po.getAttr_code());
				outOrdModeVo.setTable_name(po.getTable_name());
				outOrdModeVo.setField_name(po.getField_name());
				outOrdModeVo.setField_value(req.getDefault_value());
				outOrdModeVo.setCurr_seq(idTemp);
				outOrdModeVo.setAttr_type(req.getAttr_type());
				tolList.add(outOrdModeVo);
				maxId++;
			}		
		}
		return tolList;
	}


	/**
	 * 将数据库里配置的默认字段对应的VO加到listvo里面
	 * @param list
	 * @param totalList
	 */
	public  void AddDefault(List<OutOrdModeVo> list,List<OutOrdModeVo> totalList){
		//循环取出list里面的map对象
		for(OutOrdModeVo tolVo:totalList){
			String flag="false";
			for(OutOrdModeVo partVo:list){
				if(partVo.getAttr_code().equals(tolVo.getAttr_code())){
					flag="ok";
					break;
				}				
			}
			if("false".equals(flag)){
				list.add(tolVo);
			}

		}

	}



	/**
	 * 根据vo里面的ID拆分集合到具体的表上
	 * @param 
	 * @return
	 * @throws 
	 * @throws 
	 */
	public   Map<String, List<List<OutOrdModeVo>>> splitMapListById(Map<String, List<OutOrdModeVo>> splitMap){

		Map<String, List<List<OutOrdModeVo>>> tempMap = new HashMap<String, List<List<OutOrdModeVo>>>();
		Iterator it=splitMap.keySet().iterator();  
		while(it.hasNext()){
			String table_name=it.next().toString();
			List<OutOrdModeVo> resList=splitMap.get(table_name);
			List<List<OutOrdModeVo>> returnList = new ArrayList<List<OutOrdModeVo>>();
			//遍历出所有的字段和对应的次数
			Map<String, List<OutOrdModeVo>> keyTimesMap = new HashMap<String, List<OutOrdModeVo>>();			
			if(resList!=null && resList.size()>0){
				for(OutOrdModeVo vo: resList){
					List<OutOrdModeVo> tList = null;
					tList = keyTimesMap.get(vo.getAttr_code());
					if(tList ==null){
						tList = new ArrayList<OutOrdModeVo>();
					}
					tList.add(vo);
					keyTimesMap.put(vo.getAttr_code(), tList);
				}
			}

			//把MAP中只有一个元素的找出来放到keyOneList;多个元素的放到keyMoreList
			List<OutOrdModeVo> keyOneList = new ArrayList<OutOrdModeVo>();
			List<OutOrdModeVo> keyMoreList = new ArrayList<OutOrdModeVo>();
			Iterator item=keyTimesMap.keySet().iterator();
			while(item.hasNext()){    
				String key=item.next().toString(); 
				List<OutOrdModeVo> poList = keyTimesMap.get(key);
				if(poList.size()==1){
					keyOneList.addAll(poList);
					item.remove();
				}else{
					keyMoreList.addAll(poList);
				}
			}

			//对多个元素的keyMoreList根据ID进行遍历
			//遍历出所有的id和对应的次数
			Map<String, List<OutOrdModeVo>> idTimesMap = new HashMap<String, List<OutOrdModeVo>>();			
			if(keyMoreList!=null && keyMoreList.size()>0){
				for(OutOrdModeVo vo: keyMoreList){
					List<OutOrdModeVo> tList = null;
					tList = idTimesMap.get(vo.getCurr_seq());
					if(tList ==null){
						tList = new ArrayList<OutOrdModeVo>();
					}
					tList.add(vo);
					idTimesMap.put(vo.getCurr_seq(), tList);
				}
			}
			//把一个的元素添加到多个的当中
			if(idTimesMap.size()!=0){				
				Iterator itor=idTimesMap.keySet().iterator();
				while(itor.hasNext()){    
					String key=itor.next().toString(); 
					List<OutOrdModeVo> poList = idTimesMap.get(key);
					poList.addAll(keyOneList);
					returnList.add(poList);
				}
			}else{
				returnList.add(keyOneList);
			}

			tempMap.put(table_name, returnList);

		}
		return tempMap;
	}

	/**
	 * 拆分集合到具体的表上
	 * @param ordModParaFiledlist
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public Map<String, List<OutOrdModeVo>> splitOutOrdModeVolist(List<OutOrdModeVo> list) throws IllegalAccessException, InvocationTargetException{

		Map<String, List<OutOrdModeVo>> returnMap = new HashMap<String, List<OutOrdModeVo>>();
		List<OutOrdModeVo> poList = null;
		String tableName; 
		for(OutOrdModeVo res : list){
			tableName = res.getTable_name().trim();
			poList = returnMap.get(tableName);
			if(poList == null){
				poList = new ArrayList<OutOrdModeVo>();
			}
			poList.add(res);
			returnMap.put(tableName,poList);
		}

		return returnMap;
	}



	/**
	 * 将拆分到具体的表上的集合组装成insert sql语句
	 * @param 
	 * @return
	 * @throws Exception 
	 * @throws  
	 * @throws  
	 */
	public Map<String, List<String>> getSqlByMap(Map<String, List<List<OutOrdModeVo>>> tempMap,Map<String, List<OutOrdModeVo>>  splitMap,String orderType,DefaultOrdModVo defaultOrdModVo,Map<String,Object> muitDataMap) throws Exception{
		Map<String, List<String>> sqlMap = new HashMap<String,List<String>>();
		Iterator it=tempMap.keySet().iterator();  
		while(it.hasNext()){
			String table_name=it.next().toString();
			if("info_serv_attr".equals(table_name)){
				//写属性表从拆分集合到表上的map里取数据
				List<OutOrdModeVo> poList = splitMap.get("info_serv_attr");
				if(poList!=null){
					List<String> attrSqlList= new ArrayList<String>();
					for(OutOrdModeVo vo:poList){
						String id = getIdServDu.getId("createLogId", defaultOrdModVo.getProvince_code(), "*",defaultOrdModVo.getServ_order_no());
						StringBuffer sb1= new StringBuffer();
						StringBuffer sb2= new StringBuffer();
						sb1.append("id");
						sb1.append(",");

						sb1.append("serv_order_no");
						sb1.append(",");

						sb1.append("ofr_order_no");
						sb1.append(",");

						sb1.append("sale_order_no");
						sb1.append(",");

						sb1.append("province_code");
						sb1.append(",");

						sb1.append("area_code");
						sb1.append(",");

						sb1.append("part_month");
						sb1.append(",");

						sb1.append("attr_code");
						sb1.append(",");

						sb1.append("attr_type");
						sb1.append(",");

						sb1.append("attr_after_value");
						sb1.append(",");

						sb1.append("attr_before_value");
						sb1.append(",");

						sb1.append("curr_seq");
						sb1.append(",");

						sb1.append("upper_seq");
						sb1.append(",");

						sb2.append("'");
						sb2.append(id);
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(defaultOrdModVo.getServ_order_no());
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(defaultOrdModVo.getOfr_order_no());
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(defaultOrdModVo.getSale_order_no());
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(defaultOrdModVo.getProvince_code());
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(defaultOrdModVo.getArea_code());
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(defaultOrdModVo.getPart_month());
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(vo.getAttr_code());
						sb2.append("'");
						sb2.append(",");

						sb2.append(101);
						sb2.append(",");

						sb2.append("'");
						sb2.append(vo.getField_value());
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(vo.getField_value());
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(vo.getCurr_seq());
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(vo.getUpper_seq());
						sb2.append("'");
						sb2.append(",");

						String oldParamKey=sb1.toString();
						String newparamKey=oldParamKey.substring(0,oldParamKey.length()-1);
						String oldParamValue=sb2.toString();
						String newparamValue=oldParamValue.substring(0,oldParamValue.length()-1);
						String inSql = "insert into info_serv_attr"+" ("+newparamKey+") values ("+newparamValue+")";
						attrSqlList.add(inSql);				
					}
					sqlMap.put("info_serv_attr", attrSqlList);
				}				
			}else if("info_serv_ext".equals(table_name) || "info_sale_ext".equals(table_name) ||"info_serv_sim_card".equals(table_name) ||"info_sale_distr".equals(table_name)  ||"info_deliver_order".equals(table_name) ){

				//先判断这四个表是否有数据，如果有数据拼装update语句，如果没有数据在拼装insert语句

				InfoSaleOrderEditPo infoSaleOrderEditPoTemp=null;
				List<InfoServiceOrderExtPo> infoServiceOrderExtPoList=null;
				List<InfoServiceOrderSimCardPo> infoServiceOrderSimCardPoTemp=null;
				InfoSaleOrderDistributionPo infoSaleOrderDistributionPoTemp=null;
				List<InfoDeliverOrderPo>  infoDeliverOrderPoTemp=null;
				if("100".equals(orderType)){
					if("info_sale_ext".equals(table_name)){
						InfoSaleOrderEditPo infoSaleOrderEditPo = new InfoSaleOrderEditPo();
						infoSaleOrderEditPo.setSale_order_no(defaultOrdModVo.getSale_order_no());
						infoSaleOrderEditPoTemp=infoSaleOrderEditServ.getInfoSaleOrderEditPoBySaleOrderNo(infoSaleOrderEditPo);	
					}else if("info_sale_distr".equals(table_name)){
						InfoSaleOrderDistributionPo infoSaleOrderDistributionPo = new InfoSaleOrderDistributionPo();
						infoSaleOrderDistributionPo.setSale_order_no(defaultOrdModVo.getSale_order_no());
						infoSaleOrderDistributionPoTemp=infoSaleOrderDistributionServ.getInfoSaleOrderDistributionBySaleOrderNo(infoSaleOrderDistributionPo);
					}else if("info_deliver_order".equals(table_name)){
						InfoDeliverOrderPo infoDeliverOrderPo = new InfoDeliverOrderPo();
						infoDeliverOrderPo.setSale_order_no(defaultOrdModVo.getSale_order_no());
						infoDeliverOrderPoTemp=infoDeliverOrderServ.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderPo);
					}
				}else if("101".equals(orderType)){
					if("info_serv_ext".equals(table_name)){
						InfoServiceOrderExtPo infoServiceOrderExtPo = new InfoServiceOrderExtPo();
						infoServiceOrderExtPo.setServ_order_no(defaultOrdModVo.getServ_order_no());
						infoServiceOrderExtPoList= infoServiceOrderExtServ.queryInfoServiceOrderExtByOrderNo(infoServiceOrderExtPo);
					}else if("info_serv_sim_card".equals(table_name)){
						InfoServiceOrderSimCardPo infoServiceOrderSimCardPo = new InfoServiceOrderSimCardPo();
						infoServiceOrderSimCardPo.setServ_order_no(defaultOrdModVo.getServ_order_no());
						infoServiceOrderSimCardPoTemp=infoServiceOrderSimCardServ.queryInfoServiceOrderSimCardByOrderNo(infoServiceOrderSimCardPo);
					}else if("info_sale_distr".equals(table_name)){
						InfoSaleOrderDistributionPo infoSaleOrderDistributionPo = new InfoSaleOrderDistributionPo();
						infoSaleOrderDistributionPo.setSale_order_no(defaultOrdModVo.getSale_order_no());
						infoSaleOrderDistributionPoTemp=infoSaleOrderDistributionServ.getInfoSaleOrderDistributionBySaleOrderNo(infoSaleOrderDistributionPo);
					}else if("info_deliver_order".equals(table_name)){
						InfoDeliverOrderPo infoDeliverOrderPo = new InfoDeliverOrderPo();
						infoDeliverOrderPo.setSale_order_no(defaultOrdModVo.getSale_order_no());
						infoDeliverOrderPoTemp=infoDeliverOrderServ.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderPo);
					}
				}
				if(infoSaleOrderEditPoTemp!=null || infoServiceOrderExtPoList!=null || infoServiceOrderSimCardPoTemp!=null || infoSaleOrderDistributionPoTemp!=null || infoDeliverOrderPoTemp!=null){
					List<List<OutOrdModeVo>> poList = tempMap.get(table_name);
					List<String> sqlList= new ArrayList<String>();
					for(List<OutOrdModeVo> oneList:poList){
						StringBuffer sb1= new StringBuffer();
						for(OutOrdModeVo vo:oneList){
							sb1.append(vo.getField_name());
							sb1.append("=");
							sb1.append("'");
							sb1.append(vo.getField_value());
							sb1.append("'");
							sb1.append(",");
						}
						String oldParamKey=sb1.toString();
						String newparamKey=oldParamKey.substring(0,oldParamKey.length()-1);
						String updateSql="";
						if("100".equals(orderType)){
							updateSql = "update "+table_name+" set "+newparamKey+" where sale_order_no= '"+defaultOrdModVo.getSale_order_no()+"' and area_code ='"+defaultOrdModVo.getArea_code()+"' and part_month ='"+defaultOrdModVo.getPart_month()+"'";
						}else if("101".equals(orderType)){
							if("info_sale_distr".equals(table_name) || "info_deliver_order".equals(table_name)){
								updateSql="update "+table_name+" set "+newparamKey+" where sale_order_no= '"+defaultOrdModVo.getSale_order_no()+"' and area_code ='"+defaultOrdModVo.getArea_code()+"' and part_month ='"+defaultOrdModVo.getPart_month()+"'";
							}else{
								updateSql="update "+table_name+" set "+newparamKey+" where serv_order_no= '"+defaultOrdModVo.getServ_order_no()+"' and area_code ='"+defaultOrdModVo.getArea_code()+"' and part_month ='"+defaultOrdModVo.getPart_month()+"'";
							}
						}
						sqlList.add(updateSql);
					}
					sqlMap.put(table_name, sqlList);
				}else{
					List<List<OutOrdModeVo>> poList = tempMap.get(table_name);
					List<String> sqlList= new ArrayList<String>();
					for(List<OutOrdModeVo> oneList:poList){
						StringBuffer sb1= new StringBuffer();
						StringBuffer sb2= new StringBuffer();
						for(OutOrdModeVo vo:oneList){
							sb1.append(vo.getField_name());
							sb1.append(",");
							sb2.append("'");
							sb2.append(vo.getField_value());
							sb2.append("'");
							sb2.append(",");
						}
						OrdModMutiTable ordModMutiTable = (OrdModMutiTable) muitDataMap.get(table_name);
						if(ordModMutiTable!=null){
							String id = getIdServDu.getId("createLogId", defaultOrdModVo.getProvince_code(), "*","");
							sb1.append("id");
							sb1.append(",");

							sb2.append("'");
							sb2.append(id);
							sb2.append("'");
							sb2.append(",");
						}
						sb1.append("sale_order_no");
						sb1.append(",");

						sb1.append("province_code");
						sb1.append(",");

						sb1.append("area_code");
						sb1.append(",");

						sb1.append("part_month");
						sb1.append(",");

						sb2.append("'");
						sb2.append(defaultOrdModVo.getSale_order_no());
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(defaultOrdModVo.getProvince_code());
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(defaultOrdModVo.getArea_code());
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(defaultOrdModVo.getPart_month());
						sb2.append("'");
						sb2.append(",");
						
						if("info_deliver_order".equals(table_name)){
							String deliver_order_no=getIdServDu.getId("createDeliverOrderNo", defaultOrdModVo.getProvince_code(), defaultOrdModVo.getArea_code(),"");
							sb1.append("deliver_order_no");
							sb1.append(",");
							
							sb2.append("'");
							sb2.append(deliver_order_no);
							sb2.append("'");
							sb2.append(",");
							
							String create_time = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
							sb1.append("create_time");
							sb1.append(",");
							
							sb2.append("all_to_date('");
							sb2.append(create_time);
							sb2.append("',");
							sb2.append("'yyyy-mm-dd hh:mm:ss')");
							sb2.append(",");

						}

						if("101".equals(orderType)){

							if(!"info_sale_distr".equals(table_name) && !"info_deliver_order".equals(table_name)){
								sb1.append("serv_order_no");
								sb1.append(",");

								sb1.append("ofr_order_no");
								sb1.append(",");

								sb2.append("'");
								sb2.append(defaultOrdModVo.getServ_order_no());
								sb2.append("'");
								sb2.append(",");

								sb2.append("'");
								sb2.append(defaultOrdModVo.getOfr_order_no());
								sb2.append("'");
								sb2.append(",");				
							}
						}
						String oldParamKey=sb1.toString();
						String newparamKey=oldParamKey.substring(0,oldParamKey.length()-1);
						String oldParamValue=sb2.toString();
						String newparamValue=oldParamValue.substring(0,oldParamValue.length()-1);
						String inSql = "insert into "+table_name+" ("+newparamKey+") values ("+newparamValue+")";
						sqlList.add(inSql);

					}
					sqlMap.put(table_name, sqlList);
				}
			}else if(!"info_service_order".equals(table_name)&& !"info_sale_order".equals(table_name) && !"info_serv_ext".equals(table_name)&& !"info_sale_ext".equals(table_name) && !"info_serv_sim_card".equals(table_name) && !"info_sale_distr".equals(table_name) && !"info_serv_attr".equals(table_name) && !"info_deliver_order".equals(table_name)){
				List<List<OutOrdModeVo>> poList = tempMap.get(table_name);
				List<String> sqlList= new ArrayList<String>();
				for(List<OutOrdModeVo> oneList:poList){
					StringBuffer sb1= new StringBuffer();
					StringBuffer sb2= new StringBuffer();
					for(OutOrdModeVo vo:oneList){
						sb1.append(vo.getField_name());
						sb1.append(",");
						//如果 是时间格式的字段，需要用all_to_date强转 
						if("103".equals(vo.getAttr_type())){
							sb2.append("all_to_date('");
							sb2.append(vo.getField_value());
							sb2.append("',");
							sb2.append("'yyyy-mm-dd hh:mm:ss')");
							sb2.append(",");

						}else{
							sb2.append("'");
							sb2.append(vo.getField_value());
							sb2.append("'");
							sb2.append(",");
						}
					}
					OrdModMutiTable ordModMutiTable = (OrdModMutiTable) muitDataMap.get(table_name);
					if(ordModMutiTable!=null){
						String id = getIdServDu.getId("createLogId", defaultOrdModVo.getProvince_code(), "*","");
						sb1.append("id");
						sb1.append(",");

						sb2.append("'");
						sb2.append(id);
						sb2.append("'");
						sb2.append(",");
					}
					sb1.append("sale_order_no");
					sb1.append(",");

					sb1.append("province_code");
					sb1.append(",");

					sb1.append("area_code");
					sb1.append(",");

					sb1.append("part_month");
					sb1.append(",");

					sb2.append("'");
					sb2.append(defaultOrdModVo.getSale_order_no());
					sb2.append("'");
					sb2.append(",");

					sb2.append("'");
					sb2.append(defaultOrdModVo.getProvince_code());
					sb2.append("'");
					sb2.append(",");

					sb2.append("'");
					sb2.append(defaultOrdModVo.getArea_code());
					sb2.append("'");
					sb2.append(",");

					sb2.append("'");
					sb2.append(defaultOrdModVo.getPart_month());
					sb2.append("'");
					sb2.append(",");

					if("101".equals(orderType) && !"info_deliver_order".equals(table_name)){

						sb1.append("serv_order_no");
						sb1.append(",");

						sb1.append("ofr_order_no");
						sb1.append(",");

						sb2.append("'");
						sb2.append(defaultOrdModVo.getServ_order_no());
						sb2.append("'");
						sb2.append(",");

						sb2.append("'");
						sb2.append(defaultOrdModVo.getOfr_order_no());
						sb2.append("'");
						sb2.append(",");					
					}
					String oldParamKey=sb1.toString();
					String newparamKey=oldParamKey.substring(0,oldParamKey.length()-1);
					String oldParamValue=sb2.toString();
					String newparamValue=oldParamValue.substring(0,oldParamValue.length()-1);
					String inSql = "insert into "+table_name+" ("+newparamKey+") values ("+newparamValue+")";
					sqlList.add(inSql);

				}
				sqlMap.put(table_name, sqlList);
			}
		}
		return sqlMap;
	}


	/**
	 * 执行sql语句
	 * @param 
	 * @return
	 * @throws  
	 * @throws  
	 */
	public boolean implementSql(Map<String, List<String>> sqlMap) throws Exception{
		Iterator it=sqlMap.keySet().iterator();  
		while(it.hasNext()){
			String key=it.next().toString();
			List<String> sqlList= sqlMap.get(key);
			for(String  sql :sqlList){
				Boolean flag=sqlServDu.createBySql(sql);
				if(!flag){
					return false;
				}				
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UocMessage outByOrdMod(String order_no,String mod_code,String order_type,String param_json) throws Exception{

		UocMessage message=new UocMessage();
		Object[] paramValues = null; //参数值数组
		Class[] pType = null; //参数类型数组


		InfoServiceOrderPo infoServiceOrderPoTemp=null;
		InfoSaleOrderPo infoSaleOrderPo=null;
		//获取服务订单表数据
		if("101".equals(order_type)){	
			InfoServiceOrderPo infoServiceOrderPo = new InfoServiceOrderPo();
			infoServiceOrderPo.setServ_order_no(order_no);
			infoServiceOrderPoTemp=infoServiceOrderServ.getInfoServiceOrderByServOrderNo(infoServiceOrderPo);
			if(infoServiceOrderPoTemp==null){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("服务订单表无数据");
				return message;
			}
		}else if("100".equals(order_type)){		
			InfoSaleOrderPo infoSaleOrderPotemp = new InfoSaleOrderPo();
			infoSaleOrderPotemp.setSale_order_no(order_no);
			infoSaleOrderPo=infoSaleOrderServ.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderPotemp);
			if(infoSaleOrderPo==null){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("销售订单表无数据");
				return message;
			}
		}else{
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("无此订单类型");
			return message;
		}

		//从缓存获取订单参数入库定义表配置
		UocMessage paraMessage=redisServiceServ.queryDataFromRedis("queryordModParaFieldRelationByAttrCode");
		if(!"0000".equals(paraMessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取订单参数与数据库表字段关系表缓存失败");
			return message;
		}
		RedisData redisDataPara=(RedisData) paraMessage.getArgs().get("RedisDataResult");
		Map<String,Object> map=redisDataPara.getMap();

		//从缓存根据模板编码获取模板参数定义表
		UocMessage defineMessage=redisServiceServ.queryDataFromRedis("queryordModAttrDefineBymodCode");
		if(!"0000".equals(defineMessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取模板参数定义表缓存失败");
			return message;
		}
		RedisData redisData=(RedisData) defineMessage.getArgs().get("RedisDataResult");
		List<OrdModAttrDefine> ordModAttrDefineList=(List<OrdModAttrDefine>) redisData.getMap().get(mod_code);

		//获取订单模板所有参数和表、表字段对应关系
		int maxId=1;
		List<OutOrdModeVo> totalList=totalDataToVo(map,ordModAttrDefineList,mod_code,maxId);

		//将所有参数拆分到具体的表上
		Map<String, List<OutOrdModeVo>>  splitMap = splitOutOrdModeVolist(totalList);

		Map<String, Object> returnMap=null;
		//根据表查询数据
		if("101".equals(order_type)){
			returnMap=splitGetData(splitMap,infoServiceOrderPoTemp,order_type);
		}else if("100".equals(order_type)){
			returnMap=splitGetData(splitMap,infoSaleOrderPo,order_type);
		}
		//从缓存根据订单模板编码获取订单模板定义表配置
		UocMessage ordMessage=redisServiceServ.queryDataFromRedis("queryordModDefineByModCode");
		if(!"0000".equals(ordMessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取订单模板定义表缓存失败");
			return message;
		}
		RedisData redisDataPoRes=(RedisData) ordMessage.getArgs().get("RedisDataResult");
		OrdModDefine ordModDefineRes=(OrdModDefine) redisDataPoRes.getMap().get(mod_code);
		if(ordModDefineRes==null){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取订单模板定义表缓存失败");
			return message;
		}

		Map<String, Object> ordModMap = new HashMap<String, Object>();
		Map<String, Object> ordModMapTemp = new HashMap<String, Object>();
		//将json串转化为map
		ordModMap=jsonToBeanServDu.jsonToMap(ordModDefineRes.getJson_module());
		ordModMapTemp=jsonToBeanServDu.jsonToMap(ordModDefineRes.getJson_module());

		try {
			Iterator item=ordModMap.keySet().iterator();
			while(item.hasNext()){ 
				Map<String,Object> fieldMap = new HashMap<String,Object>();
				Map<String,Object> fieldMapTemp = new HashMap<String,Object>();
				Object fieldValue="";
				String attrCode="";
				String flag="";
				String key=item.next().toString();
				String value="";
				String fieldJson="";
				try{
					JSONObject json = JSONObject.fromObject(ordModMap);  

					value=json.get(key).toString();
				}catch(Exception ex){
					value =ordModMap.get(key).toString().trim();
				}


				if(!"".equals(value)){
					if("@".equals(value.substring(0, 1))){//判断是否是函数类型的变量，规则是变量前面加特殊字符@
						String function=value.substring(value.indexOf("@")+1, value.indexOf("("));//取出函数名
						String paramName=value.substring(value.indexOf("(")+1, value.indexOf(")"));//取出key名称
						if("getParam".equals(function)){
							paramValues=new String[]{param_json,paramName};
							pType=new Class[]{String.class,String.class};
						}else if("getSysdate".equals(function)){
							paramValues=new String[]{paramName};
							pType=new Class[]{String.class};
						}else if("getServOrderNoByOperCode".equals(function)){
							paramValues=new String[]{paramName,order_no};
							pType=new Class[]{String.class,String.class};
						}else if("getActivityInfo".equals(function)){
							paramValues=new String[]{order_no};
							pType=new Class[]{String.class};
						}else if("transCertTypeCq".equals(function)){
							paramValues=new String[]{order_no};
							pType=new Class[]{String.class};
						}else if("getFeeInfo".equals(function)){
							paramValues=new String[]{order_no};
							pType=new Class[]{String.class};
						}else if("getProductInfo".equals(function)){
							paramValues=new String[]{order_no};
							pType=new Class[]{String.class};
						}
						//利用java反射调用函数
						if("getActivityInfo".equals(function) || "getFeeInfo".equals(function) || "getProductInfo".equals(function)){
							String result=(String)functionReflecServDu.invokeMethod("com.tydic.unicom.uoc.service.common.impl.FunctionForOutByOrdModServDuImpl", function,paramValues,pType);
							if(result!=null && !"".equals(result)){
								ordModMapTemp.put(key, JSONArray.fromObject(result));
							}else{
								ordModMapTemp.put(key, result);
							}
						}else{
							String result=(String)functionReflecServDu.invokeMethod("com.tydic.unicom.uoc.service.common.impl.FunctionForOutByOrdModServDuImpl", function,paramValues,pType);
							ordModMapTemp.put(key, result);
						}
					}else if("$".equals(value.substring(0, 1))){
						flag="1";
						attrCode=value.substring(1, value.length());
						fieldValue=splitGetFieldValue(totalList,attrCode,returnMap,flag,fieldMap);
						if(fieldValue==null){
							fieldValue="";
						}
						ordModMapTemp.put(key, fieldValue);
					}else if("#".equals(key.substring(0, 1))){
						flag="2";
						fieldJson=value.substring(1, value.length()-1);
						fieldMap=jsonToBeanServDu.jsonToMap(fieldJson);
						fieldValue=splitGetFieldValue(totalList,attrCode,returnMap,flag,fieldMap);
						//item.remove();
						ordModMapTemp.remove(key);
						ordModMapTemp.put(key.substring(1, key.length()), fieldValue);
					}else if("[".equals(value.substring(0, 1))){
						flag="2";
						fieldJson=value.substring(1, value.length()-1);
						fieldMap=jsonToBeanServDu.jsonToMap(fieldJson);
						fieldMapTemp=jsonToBeanServDu.jsonToMap(fieldJson);
						Map<String, Object> fieldValueMap=getJsonArrayFieldValue(totalList,attrCode,returnMap,flag,fieldMap,fieldMapTemp,order_no,param_json);
						List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
						list.add(fieldValueMap);
						ordModMapTemp.put(key, list);
					}else if("{".equals(value.substring(0, 1))){
						flag="2";
						fieldMap=jsonToBeanServDu.jsonToMap(value);
						fieldMapTemp=jsonToBeanServDu.jsonToMap(value);
						fieldValue=getJsonArrayFieldValue(totalList,attrCode,returnMap,flag,fieldMap,fieldMapTemp,order_no,param_json);
						ordModMapTemp.put(key, fieldValue);
					}
				}else{
					ordModMapTemp.put(key, value);
				}
			}
			
			String json_info=jsonToBeanServDu.mapToJson(ordModMapTemp);
			json_info=removeMapKey(json_info,mod_code);
			//String json_info_out = json_info.replace("#", "");
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("根据订单模板出库成功");
			message.addArg("json_info", json_info);
			message.addArg("interface_type", ordModDefineRes.getInterface_type());
			message.addArg("interface_param_json", ordModDefineRes.getInterface_param_json());
		}catch (Exception ex) {
			ex.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return message;
	}

	public Map<String,Object> getJsonArrayFieldValue(List<OutOrdModeVo> totalList,String attrCode,Map<String, Object> returnMap,String flag,Map<String,Object> fieldMap,Map<String,Object> fieldMapTemp,String order_no,String param_json) throws Throwable{

		Object[] paramValues = null; //参数值数组
		Class[] pType = null; //参数类型数组
		Iterator item=fieldMap.keySet().iterator();
		while(item.hasNext()){ 
			Map<String,Object> jsonfieldMap = new HashMap<String,Object>();
			Map<String,Object> jsonfieldMapTemp = new HashMap<String,Object>();
			Object fieldValue="";
			String attrCodeTem="";
			String flagTem="";
			String keyTem=item.next().toString();
			String value="";
			String fieldJson="";
			Map<String,Object> fieldValueMap = new HashMap<String,Object>();
			try{
				JSONObject json = JSONObject.fromObject(fieldMap);  

				value=json.get(keyTem).toString();
			}catch(Exception ex){
				value =fieldMap.get(keyTem).toString().trim();
			}


			if(!"".equals(value)){
				if("@".equals(value.substring(0, 1))){//判断是否是函数类型的变量，规则是变量前面加特殊字符@
					String function=value.substring(value.indexOf("@")+1, value.indexOf("("));//取出函数名
					String paramName=value.substring(value.indexOf("(")+1, value.indexOf(")"));//取出key名称
					if("getParam".equals(function)){
						paramValues=new String[]{param_json,paramName};
						pType=new Class[]{String.class,String.class};
					}else if("getSysdate".equals(function)){
						paramValues=new String[]{paramName};
						pType=new Class[]{String.class};
					}else if("getServOrderNoByOperCode".equals(function)){
						paramValues=new String[]{paramName,order_no};
						pType=new Class[]{String.class,String.class};
					}else if("getActivityInfo".equals(function)){
						paramValues=new String[]{order_no};
						pType=new Class[]{String.class};
					}else if("transCertTypeCq".equals(function)){
						paramValues=new String[]{order_no};
						pType=new Class[]{String.class};
					}else if("getFeeInfo".equals(function)){
						paramValues=new String[]{order_no};
						pType=new Class[]{String.class};
					}else if("getProductInfo".equals(function)){
						paramValues=new String[]{order_no};
						pType=new Class[]{String.class};
					}
					//利用java反射调用函数
					if("getActivityInfo".equals(function) || "getFeeInfo".equals(function) || "getProductInfo".equals(function)){
						String result=(String)functionReflecServDu.invokeMethod("com.tydic.unicom.uoc.service.common.impl.FunctionForOutByOrdModServDuImpl", function,paramValues,pType);
						if(result!=null && !"".equals(result)){
							fieldMapTemp.put(keyTem, JSONArray.fromObject(result));
						}else{
							fieldMapTemp.put(keyTem, result);
						}
					}else{
						String result=(String)functionReflecServDu.invokeMethod("com.tydic.unicom.uoc.service.common.impl.FunctionForOutByOrdModServDuImpl", function,paramValues,pType);
						fieldMapTemp.put(keyTem, result);
					}
				} else if("$".equals(value.substring(0, 1))){
					flagTem="1";
					attrCodeTem=value.substring(1, value.length());
					fieldValue=splitGetFieldValue(totalList,attrCodeTem,returnMap,flagTem,jsonfieldMap);
					fieldMapTemp.put(keyTem, fieldValue);
				}else if("#".equals(keyTem.substring(0, 1))){
					flagTem="2";
					fieldJson=value.substring(1, value.length()-1);
					jsonfieldMap=jsonToBeanServDu.jsonToMap(fieldJson);
					fieldValue=splitGetFieldValue(totalList,attrCodeTem,returnMap,flagTem,jsonfieldMap);
					//item.remove();
					fieldMapTemp.remove(keyTem);
					fieldMapTemp.put(keyTem.substring(1, keyTem.length()), fieldValue);
				}else if("[".equals(value.substring(0, 1))){
					flagTem="2";
					fieldJson=value.substring(1, value.length()-1);
					jsonfieldMap=jsonToBeanServDu.jsonToMap(fieldJson);
					jsonfieldMapTemp=jsonToBeanServDu.jsonToMap(fieldJson);
					fieldValueMap=getJsonArrayFieldValue(totalList,attrCodeTem,returnMap,flagTem,jsonfieldMap,jsonfieldMapTemp,order_no,param_json);
					List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
					list.add(fieldValueMap);
					fieldMapTemp.put(keyTem, list);
				}
				else if("{".equals(value.substring(0, 1))){
					flagTem="2";
					jsonfieldMap=jsonToBeanServDu.jsonToMap(value);
					jsonfieldMapTemp=jsonToBeanServDu.jsonToMap(value);
					fieldValueMap=getJsonArrayFieldValue(totalList,attrCodeTem,returnMap,flagTem,jsonfieldMap,jsonfieldMapTemp,order_no,param_json);
					fieldMapTemp.put(keyTem, fieldValueMap);
				}
			}else{
				fieldMapTemp.put(keyTem, value);
			}
		}

		return fieldMapTemp;
	}
	/**
	 * 根据表明查询数据
	 * @param splitMap
	 * @param infoServiceOrderPo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> splitGetData(Map<String, List<OutOrdModeVo>>  splitMap,Object obj,String order_type) throws Exception{

		Map<String, Object> returnMap = new HashMap<String, Object>();
		Iterator itor=splitMap.keySet().iterator();
		while(itor.hasNext()){    
			String tableName=itor.next().toString(); 
			if("info_sale_order".equals(tableName)){
				//获取销售订单表数据
				InfoSaleOrderPo po = new InfoSaleOrderPo();
				if("101".equals(order_type)){
					po.setSale_order_no(((InfoServiceOrderPo) obj).getSale_order_no());
					InfoSaleOrderPo infoSaleOrderPo=infoSaleOrderServ.getInfoSaleOrderPoBySaleOrderNo(po);
					returnMap.put(tableName, infoSaleOrderPo);
				}else if("100".equals(order_type)){
					returnMap.put(tableName, obj);
				}

			}else if("info_sale_fee".equals(tableName)){
				//获取销售订单费用信息表数据
				InfoSaleOrderFeePo infoSaleOrderFeePo =new InfoSaleOrderFeePo();
				if("101".equals(order_type)){
					infoSaleOrderFeePo.setSale_order_no(((InfoServiceOrderPo) obj).getSale_order_no());
				}else if("100".equals(order_type)){
					infoSaleOrderFeePo.setSale_order_no(((InfoSaleOrderPo) obj).getSale_order_no());
				}
				InfoSaleOrderFeePo infoSaleOrderFeePoTemp=infoSaleOrderFeeServ.getInfoSaleOrderFeePoBySaleOrderNo(infoSaleOrderFeePo);
				returnMap.put(tableName, infoSaleOrderFeePoTemp);
			}else if("info_sale_edit".equals(tableName)){
				//销售订单修订表
				InfoSaleOrderEditPo infoSaleOrderEditPo = new InfoSaleOrderEditPo();
				if("101".equals(order_type)){
					infoSaleOrderEditPo.setSale_order_no(((InfoServiceOrderPo) obj).getSale_order_no());
				}else if("100".equals(order_type)){
					infoSaleOrderEditPo.setSale_order_no(((InfoSaleOrderPo) obj).getSale_order_no());
				}
				InfoSaleOrderEditPo infoSaleOrderEditPoTemp=infoSaleOrderEditServ.getInfoSaleOrderEditPoBySaleOrderNo(infoSaleOrderEditPo);
				returnMap.put(tableName, infoSaleOrderEditPoTemp);
			}else if("info_sale_attr".equals(tableName)){
				//销售订单属性集表
				InfoSaleOrderAttrPo infoSaleOrderAttrPo =new InfoSaleOrderAttrPo();
				if("101".equals(order_type)){
					infoSaleOrderAttrPo.setSale_order_no(((InfoServiceOrderPo) obj).getSale_order_no());
				}else if("100".equals(order_type)){
					infoSaleOrderAttrPo.setSale_order_no(((InfoSaleOrderPo) obj).getSale_order_no());
				}
				List<InfoSaleOrderAttrPo> infoSaleOrderAttrPoTemp=infoSaleOrderAttrServ.queryInfoSaleOrderAttrBySaleOrderNo(infoSaleOrderAttrPo);
				returnMap.put(tableName, infoSaleOrderAttrPoTemp);
			}else if("info_sale_person".equals(tableName)){
				//销售订单个客信息表
				InfoSaleOrderPersionPo infoSaleOrderPersionPo =new InfoSaleOrderPersionPo();
				if("101".equals(order_type)){
					infoSaleOrderPersionPo.setSale_order_no(((InfoServiceOrderPo) obj).getSale_order_no());
				}else if("100".equals(order_type)){
					infoSaleOrderPersionPo.setSale_order_no(((InfoSaleOrderPo) obj).getSale_order_no());
				}
				InfoSaleOrderPersionPo infoSaleOrderPersionPoTemp=infoSaleOrderPersionServ.getInfoSaleOrderPersionBySaleOrderNo(infoSaleOrderPersionPo);
				returnMap.put(tableName, infoSaleOrderPersionPoTemp);
			}else if("info_sale_distr".equals(tableName)){
				//销售订单收件人信息表
				InfoSaleOrderDistributionPo infoSaleOrderDistributionPo =new InfoSaleOrderDistributionPo();
				if("101".equals(order_type)){
					infoSaleOrderDistributionPo.setSale_order_no(((InfoServiceOrderPo) obj).getSale_order_no());
				}else if("100".equals(order_type)){
					infoSaleOrderDistributionPo.setSale_order_no(((InfoSaleOrderPo) obj).getSale_order_no());
				}
				InfoSaleOrderDistributionPo infoSaleOrderDistributionPoTemp=infoSaleOrderDistributionServ.getInfoSaleOrderDistributionBySaleOrderNo(infoSaleOrderDistributionPo);
				returnMap.put(tableName, infoSaleOrderDistributionPoTemp);
			}else if("info_sale_distr_detail".equals(tableName)){
				//销售订单包裹信息表
				InfoSaleOrderDistrDetailPo infoSaleOrderDistrDetailPo =new InfoSaleOrderDistrDetailPo();
				if("101".equals(order_type)){
					infoSaleOrderDistrDetailPo.setSale_order_no(((InfoServiceOrderPo) obj).getSale_order_no());
				}else if("100".equals(order_type)){
					infoSaleOrderDistrDetailPo.setSale_order_no(((InfoSaleOrderPo) obj).getSale_order_no());
				}
				List<InfoSaleOrderDistrDetailPo> infoSaleOrderDistrDetailPoList=infoSaleOrderDistrDetailServ.queryInfoSaleOrderDistrDetailBySaleOrderNo(infoSaleOrderDistrDetailPo);
				returnMap.put(tableName, infoSaleOrderDistrDetailPoList);
			}else if("info_sale_ofr_map".equals(tableName)){
				//销售订单商品表
				InfoSaleOrderOfrMapPo infoSaleOrderOfrMapPo =new InfoSaleOrderOfrMapPo();
				if("101".equals(order_type)){
					infoSaleOrderOfrMapPo.setSale_order_no(((InfoServiceOrderPo) obj).getSale_order_no());
				}else if("100".equals(order_type)){
					infoSaleOrderOfrMapPo.setSale_order_no(((InfoSaleOrderPo) obj).getSale_order_no());
				}
				List<InfoSaleOrderOfrMapPo> infoSaleOrderOfrMapPoTemp=infoSaleOrderOfrMapServ.queryInfoSaleOrderOfrMapBySaleOrderNo(infoSaleOrderOfrMapPo);
				returnMap.put(tableName, infoSaleOrderOfrMapPoTemp);
			}else if("info_sale_serv_map".equals(tableName)){
				//销售订单业务表
				InfoSaleOrderServMapPo infoSaleOrderServMapPo =new InfoSaleOrderServMapPo();
				if("101".equals(order_type)){
					infoSaleOrderServMapPo.setSale_order_no(((InfoServiceOrderPo) obj).getSale_order_no());
				}else if("100".equals(order_type)){
					infoSaleOrderServMapPo.setSale_order_no(((InfoSaleOrderPo) obj).getSale_order_no());
				}
				List<InfoSaleOrderServMapPo> infoSaleOrderServMapPoTemp =infoSaleOrderServMapServ.queryInfoSaleOrderServMapBySaleOrderNo(infoSaleOrderServMapPo);
				returnMap.put(tableName, infoSaleOrderServMapPoTemp);
			}else if("info_sale_ext".equals(tableName)){
				InfoSaleOrderExtPo infoSaleOrderExtPo = new InfoSaleOrderExtPo();
				if("101".equals(order_type)){
					infoSaleOrderExtPo.setSale_order_no(((InfoServiceOrderPo) obj).getSale_order_no());
				}else if("100".equals(order_type)){
					infoSaleOrderExtPo.setSale_order_no(((InfoSaleOrderPo) obj).getSale_order_no());
				}
				InfoSaleOrderExtPo infoSaleOrderExtPoTemp=infoSaleOrderExtServ.getInfoSaleOrderExtBySaleOrderNo(infoSaleOrderExtPo);
				returnMap.put(tableName, infoSaleOrderExtPoTemp);
			}else if("info_sale_person_cert".equals(tableName)){
				InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPo = new InfoSaleOrderPersionCertPo();
				if("101".equals(order_type)){
					infoSaleOrderPersionCertPo.setSale_order_no(((InfoServiceOrderPo) obj).getSale_order_no());
				}else if("100".equals(order_type)){
					infoSaleOrderPersionCertPo.setSale_order_no(((InfoSaleOrderPo) obj).getSale_order_no());
				}
				InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPoTemp=infoSaleOrderPersionCertServ.getInfoSaleOrderPersionCertBySaleOrderNo(infoSaleOrderPersionCertPo);
				returnMap.put(tableName, infoSaleOrderPersionCertPoTemp);
			}else if("info_deliver_order".equals(tableName)){
				InfoDeliverOrderPo infoDeliverOrderPo = new InfoDeliverOrderPo();
				if("101".equals(order_type)){
					infoDeliverOrderPo.setSale_order_no(((InfoServiceOrderPo) obj).getSale_order_no());
				}else if("100".equals(order_type)){
					infoDeliverOrderPo.setSale_order_no(((InfoSaleOrderPo) obj).getSale_order_no());
				}
				List<InfoDeliverOrderPo> infoDeliverOrderPoTemp=infoDeliverOrderServ.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderPo);
				returnMap.put(tableName, infoDeliverOrderPoTemp);
			}else if("info_pay_order".equals(tableName)){
				InfoPayOrderPo infoPayOrderPo = new InfoPayOrderPo();
				if("101".equals(order_type)){
					infoPayOrderPo.setRela_order_no(((InfoServiceOrderPo) obj).getSale_order_no());
					infoPayOrderPo.setRela_order_type("100");
				}else if("100".equals(order_type)){
					infoPayOrderPo.setRela_order_no(((InfoSaleOrderPo) obj).getSale_order_no());
					infoPayOrderPo.setRela_order_type("100");
				}
				InfoPayOrderPo infoPayOrderPoTemp=infoPayOrderServ.queryInfoPayOrderByRelaOrderNo(infoPayOrderPo);
				returnMap.put(tableName, infoPayOrderPoTemp);
			}else if("task_inst".equals(tableName)){
				ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
				if("101".equals(order_type)){
					procInstTaskInstPo.setOrder_no(((InfoServiceOrderPo) obj).getServ_order_no());
					procInstTaskInstPo.setOrder_type("101");
				}else if("100".equals(order_type)){
					procInstTaskInstPo.setOrder_no(((InfoSaleOrderPo) obj).getSale_order_no());
					procInstTaskInstPo.setOrder_type("100");
				}
				ProcInstTaskInstPo procInstTaskInstPoTemp=procInstTaskInstServ.queryProcInstTaskInstByTaskState(procInstTaskInstPo);
				returnMap.put(tableName, procInstTaskInstPoTemp);
			}else if("info_order_cancel".equals(tableName)){
				InfoOrderCancelPo infoOrderCancelPo = new InfoOrderCancelPo();
				if("101".equals(order_type)){
					infoOrderCancelPo.setOrder_no(((InfoServiceOrderPo) obj).getServ_order_no());					
				}else if("100".equals(order_type)){
					infoOrderCancelPo.setOrder_no(((InfoSaleOrderPo) obj).getSale_order_no());					
				}
				InfoOrderCancelPo infoOrderCancelPoTemp=infoOrderCancelServ.queryInfoOrderCancel(infoOrderCancelPo);
				returnMap.put(tableName, infoOrderCancelPoTemp);
			}
			if("101".equals(order_type)){
				if("info_ofr_order".equals(tableName)){
					//商品订单表		
					InfoOfrOrderPo infoOfrOrderPo =new InfoOfrOrderPo();
					infoOfrOrderPo.setOfr_order_no(((InfoServiceOrderPo) obj).getOfr_order_no());
					InfoOfrOrderPo infoOfrOrderPoTemp=infoOfrOrderServ.getInfoOfrOrderByOfrOrderNo(infoOfrOrderPo);
					returnMap.put(tableName, infoOfrOrderPoTemp);
				}else if("info_ofr_invoice".equals(tableName)){
					//商品订单一次性费用发票表
					InfoOfrOrderInvoicePo infoOfrOrderInvoicePo =new InfoOfrOrderInvoicePo();
					infoOfrOrderInvoicePo.setOfr_order_no(((InfoServiceOrderPo) obj).getOfr_order_no());
					List<InfoOfrOrderInvoicePo> infoOfrOrderInvoicePoTemp = infoOfrOrderInvoiceServ.queryInfoOfrOrderInvoiceByOrderNo(infoOfrOrderInvoicePo);
					returnMap.put(tableName, infoOfrOrderInvoicePoTemp);
				}else if("info_ofr_pay".equals(tableName)){
					//商品订单收费详情表		
					InfoOfrOrderPayPo infoOfrOrderPayPo =new InfoOfrOrderPayPo();
					infoOfrOrderPayPo.setOfr_order_no(((InfoServiceOrderPo) obj).getOfr_order_no());
					List<InfoOfrOrderPayPo> infoOfrOrderPayPoTemp = infoOfrOrderPayServ.queryInfoOfrOrderPayByOrderNo(infoOfrOrderPayPo);	
					returnMap.put(tableName, infoOfrOrderPayPoTemp);
				}else if("info_ofr_fee".equals(tableName)){
					//商品订单费用详情表
					InfoOfrOrderFeePo infoOfrOrderFeePo =new InfoOfrOrderFeePo();		
					infoOfrOrderFeePo.setOfr_order_no(((InfoServiceOrderPo) obj).getOfr_order_no());
					List<InfoOfrOrderFeePo> infoOfrOrderFeePoTemp = infoOfrOrderFeeServ.queryInfoOfrOrderFeeByOrderNo(infoOfrOrderFeePo);
					returnMap.put(tableName, infoOfrOrderFeePoTemp);
				}else if("info_service_order".equals(tableName)){
					//服务订单表
					returnMap.put(tableName, obj);
				}else if("info_serv_pay".equals(tableName)){
					InfoServiceOrderPayPo infoServiceOrderPayPo =new InfoServiceOrderPayPo();
					infoServiceOrderPayPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderPayPo> infoServiceOrderPayPoTemp=infoServiceOrderPayServ.queryInfoServiceOrderPayByOrderNo(infoServiceOrderPayPo);
					returnMap.put(tableName, infoServiceOrderPayPoTemp);
				}else if("info_serv_fee".equals(tableName)){
					//服务订单费用详情表
					InfoServiceOrderFeePo infoServiceOrderFeePo =new InfoServiceOrderFeePo();
					infoServiceOrderFeePo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderFeePo> infoServiceOrderFeePoTemp=infoServiceOrderFeeServ.queryInfoServiceOrderFeeByServOrderNo(infoServiceOrderFeePo);
					returnMap.put(tableName, infoServiceOrderFeePoTemp);
				}else if("info_serv_attr".equals(tableName)){
					//服务订单属性表
					InfoServiceOrderAttrPo infoServiceOrderAttrPo =new InfoServiceOrderAttrPo();
					infoServiceOrderAttrPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderAttrPo> infoServiceOrderAttrPoTemp=infoServiceOrderAttrServ.queryInfoServiceOrderAttrByOrderNo(infoServiceOrderAttrPo);
					returnMap.put(tableName, infoServiceOrderAttrPoTemp);				
				}else if("info_serv_prod_map".equals(tableName)){
					//服务订单产品列表		
					InfoServiceOrderProdMapPo infoServiceOrderProdMapPo =new InfoServiceOrderProdMapPo();
					infoServiceOrderProdMapPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderProdMapPo> infoServiceOrderProdMapPoTemp=infoServiceOrderProdMapServ.queryInfoServiceOrderProdMapByOrderNo(infoServiceOrderProdMapPo);
					returnMap.put(tableName, infoServiceOrderProdMapPoTemp);		
				}else if("info_serv_terminal".equals(tableName)){
					//服务订单终端表
					InfoServiceOrderTerminalPo infoServiceOrderTerminalPo = new InfoServiceOrderTerminalPo();
					infoServiceOrderTerminalPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderTerminalPo> infoServiceOrderTerminalPoTemp=infoServiceOrderTerminalServ.queryInfoServiceOrderTerminalByOrderNo(infoServiceOrderTerminalPo);
					returnMap.put(tableName, infoServiceOrderTerminalPoTemp);	
				}else if("info_serv_activity".equals(tableName)){
					//服务订单优惠活动
					InfoServiceOrderActivityPo infoServiceOrderActivityPo = new InfoServiceOrderActivityPo();
					infoServiceOrderActivityPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderActivityPo> infoServiceOrderActivityPoTemp=infoServiceOrderActivityServ.queryInfoServiceOrderActivityByOrderNo(infoServiceOrderActivityPo);
					returnMap.put(tableName, infoServiceOrderActivityPoTemp);	
				}else if("info_serv_agreement".equals(tableName)){
					//服务订单协议表
					InfoServiceOrderAgreementPo infoServiceOrderAgreementPo =new InfoServiceOrderAgreementPo();
					infoServiceOrderAgreementPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderAgreementPo> infoServiceOrderAgreementPoTemp=infoServiceOrderAgreementServ.queryInfoServiceOrderAgreementByOrderNo(infoServiceOrderAgreementPo);
					returnMap.put(tableName, infoServiceOrderAgreementPoTemp);	
				}else if("info_serv_pkg".equals(tableName)){
					//服务订单套餐列表
					InfoServiceOrderPackagePo infoServiceOrderPackagePo =new InfoServiceOrderPackagePo();
					infoServiceOrderPackagePo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderPackagePo> infoServiceOrderPackagePoTemp=infoServiceOrderPackageServ.queryInfoServiceOrderPackageByOrderNo(infoServiceOrderPackagePo);
					returnMap.put(tableName, infoServiceOrderPackagePoTemp);	
				}else if("info_serv_sim_card".equals(tableName)){
					//服务订单SIM卡信息表
					InfoServiceOrderSimCardPo infoServiceOrderSimCardPo = new InfoServiceOrderSimCardPo();
					infoServiceOrderSimCardPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderSimCardPo> infoServiceOrderSimCardPoTemp =infoServiceOrderSimCardServ.queryInfoServiceOrderSimCardByOrderNo(infoServiceOrderSimCardPo);
					returnMap.put(tableName, infoServiceOrderSimCardPoTemp);	
				}else if("info_serv_fix".equals(tableName)){
					//服务订单固网信息表
					InfoServiceOrderFixPo infoServiceOrderFixPo = new InfoServiceOrderFixPo();
					infoServiceOrderFixPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderFixPo> infoServiceOrderFixPoTemp=infoServiceOrderFixServ.queryInfoServiceOrderFixByOrderNo(infoServiceOrderFixPo);
					returnMap.put(tableName, infoServiceOrderFixPoTemp);
				}else if("info_serv_m165".equals(tableName)){
					//服务订单宽带信息表
					InfoServiceOrderM165Po infoServiceOrderM165Po =new InfoServiceOrderM165Po();
					infoServiceOrderM165Po.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderM165Po> infoServiceOrderM165PoTemp=infoServiceOrderM165Serv.queryInfoServiceOrderM165ByOrderNo(infoServiceOrderM165Po);
					returnMap.put(tableName, infoServiceOrderM165PoTemp);
				}else if("info_serv_good_nbr".equals(tableName)){
					//服务订单靓号信息表
					InfoServiceOrderGoodNbrPo infoServiceOrderGoodNbrPo =new InfoServiceOrderGoodNbrPo();
					infoServiceOrderGoodNbrPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderGoodNbrPo> infoServiceOrderGoodNbrPoTemp=infoServiceOrderGoodNbrServ.queryInfoServiceOrderGoodNbrByOrderNo(infoServiceOrderGoodNbrPo);	
					returnMap.put(tableName, infoServiceOrderGoodNbrPoTemp);
				}else if("info_serv_collection".equals(tableName)){
					//服务订单银行托收表
					InfoServiceOrderCollectionPo infoServiceOrderCollectionPo = new InfoServiceOrderCollectionPo();
					infoServiceOrderCollectionPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderCollectionPo> infoServiceOrderCollectionPoTemp=infoServiceOrderCollectionServ.queryInfoServiceOrderCollectionByOrderNo(infoServiceOrderCollectionPo);
					returnMap.put(tableName, infoServiceOrderCollectionPoTemp);
				}else if("info_serv_guarantor".equals(tableName)){
					//服务订单担保人表
					InfoServiceOrderGuarantorPo infoServiceOrderGuarantorPo = new InfoServiceOrderGuarantorPo();
					infoServiceOrderGuarantorPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderGuarantorPo> infoServiceOrderGuarantorPoTemp=infoServiceOrderGuarantorServ.queryInfoServiceOrderGuarantorByOrderNo(infoServiceOrderGuarantorPo);
					returnMap.put(tableName, infoServiceOrderGuarantorPoTemp);
				}else if("info_serv_developer".equals(tableName)){
					//服务订单发展人表
					InfoServiceOrderDeveloperPo infoServiceOrderDeveloperPo = new InfoServiceOrderDeveloperPo();
					infoServiceOrderDeveloperPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderDeveloperPo> infoServiceOrderDeveloperPoTemp=infoServiceOrderDeveloperServ.queryInfoServiceOrderDeveloperByOrderNo(infoServiceOrderDeveloperPo);
					returnMap.put(tableName, infoServiceOrderDeveloperPoTemp);
				}else if("info_serv_person".equals(tableName)){
					//服务订单个客信息表
					InfoServiceOrderPersionPo infoServiceOrderPersionPo = new InfoServiceOrderPersionPo();
					infoServiceOrderPersionPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderPersionPo> infoServiceOrderPersionPoTemp=infoServiceOrderPersionServ.queryInfoServiceOrderPersionByOrderNo(infoServiceOrderPersionPo);
					returnMap.put(tableName, infoServiceOrderPersionPoTemp);
				}else if("info_serv_ext".equals(tableName)){
					InfoServiceOrderExtPo infoServiceOrderExtPo= new InfoServiceOrderExtPo();
					infoServiceOrderExtPo.setServ_order_no(((InfoServiceOrderPo) obj).getServ_order_no());
					List<InfoServiceOrderExtPo> infoServiceOrderExtPoTemp=infoServiceOrderExtServ.queryInfoServiceOrderExtByOrderNo(infoServiceOrderExtPo);
					returnMap.put(tableName, infoServiceOrderExtPoTemp);
				}
			}
		}

		return returnMap;
	}

	/**
	 * 获取json数组对应的值
	 * @param map
	 * @param object
	 * @param className
	 * @return
	 * @throws Throwable
	 */
	public Map<String, Object> splitGetArrayValue(Map<String, Object> map,Object object,String className,List<OutOrdModeVo> totalList) throws Throwable{
		Map<String, Object> arrayMap= new HashMap<String, Object>();

		Iterator ites=map.keySet().iterator();
		while(ites.hasNext()){ 
			String fieldValue="";
			String fieldName="";
			String keyset=ites.next().toString();
			String oldAttrCode=(String) map.get(keyset);
			if("$".equals(oldAttrCode.substring(0, 1))){
				String newAttrCode=oldAttrCode.substring(1, oldAttrCode.length());//去掉$符号
				for(OutOrdModeVo vo: totalList){
					if(vo.getAttr_code().equals(newAttrCode)){
						fieldName=vo.getField_name();
						break;
					}
				}
				if(fieldName!=null&&!"".equals(fieldName)){
					fieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, object);
				}
			}else{
				fieldValue=map.get(keyset).toString();
			}
			arrayMap.put(keyset, fieldValue);
		}
		//String arrayJson=jsonToBeanServDu.mapToJson(arrayMap);
		return arrayMap;

	}

	/**
	 * 如果表里数据为空，替换map格式的value值
	 * @param map
	 * @return
	 * @throws Throwable
	 */
	public Map<String, Object> splitGetNullArrayValue(Map<String, Object> map) throws Throwable{
		Map<String, Object> arrayMap= new HashMap<String, Object>();
		Iterator ites=map.keySet().iterator();
		while(ites.hasNext()){ 
			String fieldValue;
			String keyset=ites.next().toString();
			String oldAttrCode=(String) map.get(keyset);
			if("$".equals(oldAttrCode.substring(0, 1))){
				fieldValue="";
			}else{
				fieldValue=map.get(keyset).toString();
			}
			arrayMap.put(keyset, fieldValue);
		}
		//String arrayJson=jsonToBeanServDu.mapToJson(arrayMap);
		return arrayMap;
	}

	/**
	 * 获取参数对应的值
	 * @param totalList
	 * @param fieldName
	 * @param returnMap
	 * @param flag
	 * @param fieldMap
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public Object splitGetFieldValue(List<OutOrdModeVo> totalList,String attrCode,Map<String, Object> returnMap,String flag,Map<String,Object> fieldMap) throws Throwable{

		Object fieldValue="";
		String StrFieldValue="";
		List<Map<String,Object>> mapFieldValue= new ArrayList<Map<String,Object>>();
		Map<String, Object> nullArrayValue = new HashMap<String, Object>();
		String fieldName="";
		StringBuffer className= new StringBuffer();
		className.append("com.tydic.unicom.uoc.base.uocinst.po.");
		String tableName = "";
		if("1".equals(flag)){
			for(OutOrdModeVo vo: totalList){
				if(vo.getAttr_code().equals(attrCode)){
					tableName=vo.getTable_name();
					fieldName=vo.getField_name();
					break;
				}
			}
		}else if("2".equals(flag)){
			Iterator ites=fieldMap.keySet().iterator();
			while(ites.hasNext()){ 
				String keyset=ites.next().toString();
				String oldAttrCode=(String) fieldMap.get(keyset);
				String newAttrCode=oldAttrCode.substring(1, oldAttrCode.length());//去掉$符号
				String flagTemp="";
				for(OutOrdModeVo vo: totalList){
					if(vo.getAttr_code().equals(newAttrCode)){
						tableName=vo.getTable_name();
						flagTemp="OK";
						break;
					}
				}
				if("OK".equals(flagTemp)){
					break;
				}

			}
		}

		if("info_sale_order".equals(tableName)){//获取销售订单表数据
			className.append("InfoSaleOrderPo");
			InfoSaleOrderPo infoSaleOrderPo=(InfoSaleOrderPo) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoSaleOrderPo!=null){
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoSaleOrderPo);
				}
			}else if("2".equals(flag)){
				if(infoSaleOrderPo!=null){
					Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,infoSaleOrderPo,className.toString(),totalList);
					mapFieldValue.add(arrayValue);
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_sale_fee".equals(tableName)){
			//获取销售订单费用信息表数据
			className.append("InfoSaleOrderFeePo");
			InfoSaleOrderFeePo infoSaleOrderFeePoTemp=(InfoSaleOrderFeePo) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoSaleOrderFeePoTemp!=null){
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoSaleOrderFeePoTemp);
				}
			}else if("2".equals(flag)){
				if(infoSaleOrderFeePoTemp!=null){
					Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,infoSaleOrderFeePoTemp,className.toString(),totalList);
					mapFieldValue.add(arrayValue);
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}

			}

		}else if("info_sale_edit".equals(tableName)){
			//销售订单修订表
			className.append("InfoSaleOrderEditPo");
			InfoSaleOrderEditPo infoSaleOrderEditPoTemp= (InfoSaleOrderEditPo) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoSaleOrderEditPoTemp!=null){
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoSaleOrderEditPoTemp);
				}
			}else if("2".equals(flag)){
				if(infoSaleOrderEditPoTemp!=null){
					Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,infoSaleOrderEditPoTemp,className.toString(),totalList);
					mapFieldValue.add(arrayValue);
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}

			}
		}else if("info_sale_attr".equals(tableName)){
			//销售订单属性集表
			className.append("InfoSaleOrderAttrPo");
			List<InfoSaleOrderAttrPo> infoSaleOrderAttrPoTemp=(List<InfoSaleOrderAttrPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoSaleOrderAttrPoTemp!=null){
					InfoSaleOrderAttrPo infoSaleOrderAttrPo =infoSaleOrderAttrPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoSaleOrderAttrPo);
				}
			}else if("2".equals(flag)){
				if(infoSaleOrderAttrPoTemp!=null){
					for(InfoSaleOrderAttrPo po:infoSaleOrderAttrPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_sale_person".equals(tableName)){
			//销售订单个客信息表
			className.append("InfoSaleOrderPersionPo");
			InfoSaleOrderPersionPo infoSaleOrderPersionPoTemp=(InfoSaleOrderPersionPo) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoSaleOrderPersionPoTemp!=null){
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoSaleOrderPersionPoTemp);
				}
			}else if("2".equals(flag)){
				if(infoSaleOrderPersionPoTemp!=null){
					Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,infoSaleOrderPersionPoTemp,className.toString(),totalList);
					mapFieldValue.add(arrayValue);
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_sale_distr".equals(tableName)){
			//销售订单收件人信息表
			className.append("InfoSaleOrderDistributionPo");
			InfoSaleOrderDistributionPo infoSaleOrderDistributionPoTemp= (InfoSaleOrderDistributionPo) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoSaleOrderDistributionPoTemp!=null){
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoSaleOrderDistributionPoTemp);
				}
			}else if("2".equals(flag)){
				if(infoSaleOrderDistributionPoTemp!=null){
					Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,infoSaleOrderDistributionPoTemp,className.toString(),totalList);
					mapFieldValue.add(arrayValue);
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_sale_distr_detail".equals(tableName)){
			//销售订单包裹信息表
			className.append("InfoSaleOrderDistrDetailPo");
			List<InfoSaleOrderDistrDetailPo> infoSaleOrderDistrDetailPoList=(List<InfoSaleOrderDistrDetailPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoSaleOrderDistrDetailPoList!=null){
					InfoSaleOrderDistrDetailPo infoSaleOrderDistrDetailPo =infoSaleOrderDistrDetailPoList.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoSaleOrderDistrDetailPo);
				}
			}else if("2".equals(flag)){
				if(infoSaleOrderDistrDetailPoList!=null){
					for(InfoSaleOrderDistrDetailPo po:infoSaleOrderDistrDetailPoList){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_sale_ofr_map".equals(tableName)){
			//销售订单商品表
			className.append("InfoSaleOrderOfrMapPo");
			List<InfoSaleOrderOfrMapPo> infoSaleOrderOfrMapPoTemp=(List<InfoSaleOrderOfrMapPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoSaleOrderOfrMapPoTemp!=null){
					InfoSaleOrderOfrMapPo infoSaleOrderOfrMapPo =infoSaleOrderOfrMapPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoSaleOrderOfrMapPo);
				}
			}else if("2".equals(flag)){
				if(infoSaleOrderOfrMapPoTemp!=null){
					for(InfoSaleOrderOfrMapPo po:infoSaleOrderOfrMapPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_sale_serv_map".equals(tableName)){
			//销售订单业务表
			className.append("InfoSaleOrderServMapPo");
			List<InfoSaleOrderServMapPo> infoSaleOrderServMapPoTemp =(List<InfoSaleOrderServMapPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoSaleOrderServMapPoTemp!=null){
					InfoSaleOrderServMapPo infoSaleOrderServMapPo =infoSaleOrderServMapPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoSaleOrderServMapPo);
				}
			}else if("2".equals(flag)){
				if(infoSaleOrderServMapPoTemp!=null){
					for(InfoSaleOrderServMapPo po:infoSaleOrderServMapPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_sale_ext".equals(tableName)){
			//销售订单拓展属性横表
			className.append("InfoSaleOrderExtPo");
			InfoSaleOrderExtPo infoSaleOrderExtPoTemp= (InfoSaleOrderExtPo) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoSaleOrderExtPoTemp!=null){
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoSaleOrderExtPoTemp);
				}
			}else if("2".equals(flag)){
				if(infoSaleOrderExtPoTemp!=null){
					Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,infoSaleOrderExtPoTemp,className.toString(),totalList);
					mapFieldValue.add(arrayValue);
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}

		}else if("info_sale_person_cert".equals(tableName)){
			//销售订单拓展属性横表
			className.append("InfoSaleOrderPersionCertPo");
			InfoSaleOrderPersionCertPo infoSaleOrderPersionCertPoTemp= (InfoSaleOrderPersionCertPo) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoSaleOrderPersionCertPoTemp!=null){
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoSaleOrderPersionCertPoTemp);
				}
			}else if("2".equals(flag)){
				if(infoSaleOrderPersionCertPoTemp!=null){
					Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,infoSaleOrderPersionCertPoTemp,className.toString(),totalList);
					mapFieldValue.add(arrayValue);
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}

		}else if("info_deliver_order".equals(tableName)){
			//销售订单拓展属性横表
			className.append("InfoDeliverOrderPo");
			List<InfoDeliverOrderPo> infoDeliverOrderPoTemp= (List<InfoDeliverOrderPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoDeliverOrderPoTemp!=null){
					InfoDeliverOrderPo infoDeliverOrderPo =infoDeliverOrderPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoDeliverOrderPo);
				}
			}else if("2".equals(flag)){
				if(infoDeliverOrderPoTemp!=null){
					for(InfoDeliverOrderPo po:infoDeliverOrderPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}

		}else if("info_pay_order".equals(tableName)){
			//销售订单拓展属性横表
			className.append("InfoPayOrderPo");
			InfoPayOrderPo infoPayOrderPoTemp= (InfoPayOrderPo) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoPayOrderPoTemp!=null){
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoPayOrderPoTemp);
				}
			}else if("2".equals(flag)){
				if(infoPayOrderPoTemp!=null){
					Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,infoPayOrderPoTemp,className.toString(),totalList);
					mapFieldValue.add(arrayValue);
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}

		}else if("info_ofr_order".equals(tableName)){
			//商品订单表		
			className.append("InfoOfrOrderPo");
			InfoOfrOrderPo infoOfrOrderPoTemp=(InfoOfrOrderPo) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoOfrOrderPoTemp!=null){
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoOfrOrderPoTemp);
				}
			}else if("2".equals(flag)){
				if(infoOfrOrderPoTemp!=null){
					Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,infoOfrOrderPoTemp,className.toString(),totalList);
					mapFieldValue.add(arrayValue);
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_ofr_invoice".equals(tableName)){
			//商品订单一次性费用发票表
			className.append("InfoOfrOrderInvoicePo");
			List<InfoOfrOrderInvoicePo> infoOfrOrderInvoicePoTemp = (List<InfoOfrOrderInvoicePo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoOfrOrderInvoicePoTemp!=null){
					InfoOfrOrderInvoicePo infoOfrOrderInvoicePo =infoOfrOrderInvoicePoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoOfrOrderInvoicePo);
				}
			}else if("2".equals(flag)){
				if(infoOfrOrderInvoicePoTemp!=null){
					for(InfoOfrOrderInvoicePo po:infoOfrOrderInvoicePoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_ofr_pay".equals(tableName)){
			//商品订单收费详情表		
			className.append("InfoOfrOrderPayPo");
			List<InfoOfrOrderPayPo> infoOfrOrderPayPoTemp = (List<InfoOfrOrderPayPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoOfrOrderPayPoTemp!=null){
					InfoOfrOrderPayPo infoOfrOrderPayPo =infoOfrOrderPayPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoOfrOrderPayPo);
				}
			}else if("2".equals(flag)){
				if(infoOfrOrderPayPoTemp!=null){
					for(InfoOfrOrderPayPo po:infoOfrOrderPayPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_ofr_fee".equals(tableName)){
			//商品订单费用详情表
			className.append("InfoOfrOrderFeePo");
			List<InfoOfrOrderFeePo> infoOfrOrderFeePoTemp =  (List<InfoOfrOrderFeePo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoOfrOrderFeePoTemp!=null){
					InfoOfrOrderFeePo infoOfrOrderFeePo =infoOfrOrderFeePoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoOfrOrderFeePo);
				}
			}else if("2".equals(flag)){
				if(infoOfrOrderFeePoTemp!=null){
					for(InfoOfrOrderFeePo po:infoOfrOrderFeePoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_service_order".equals(tableName)){
			//服务订单表
			className.append("InfoServiceOrderPo");
			InfoServiceOrderPo infoServiceOrderPo=(InfoServiceOrderPo) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderPo!=null){
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderPo!=null){
					Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,infoServiceOrderPo,className.toString(),totalList);
					mapFieldValue.add(arrayValue);
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_serv_pay".equals(tableName)){
			className.append("InfoServiceOrderPayPo");
			List<InfoServiceOrderPayPo> infoServiceOrderPayPoTemp=(List<InfoServiceOrderPayPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderPayPoTemp!=null){
					InfoServiceOrderPayPo infoServiceOrderPayPo =infoServiceOrderPayPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderPayPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderPayPoTemp!=null){
					for(InfoServiceOrderPayPo po:infoServiceOrderPayPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_serv_fee".equals(tableName)){
			//服务订单费用详情表
			className.append("InfoServiceOrderFeePo");
			List<InfoServiceOrderFeePo> infoServiceOrderFeePoTemp=(List<InfoServiceOrderFeePo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderFeePoTemp!=null){
					InfoServiceOrderFeePo infoServiceOrderFeePo =infoServiceOrderFeePoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderFeePo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderFeePoTemp!=null){
					for(InfoServiceOrderFeePo po:infoServiceOrderFeePoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}
		}else if("info_serv_attr".equals(tableName)){
			//服务订单属性表
			className.append("InfoServiceOrderAttrPo");
			List<InfoServiceOrderAttrPo> infoServiceOrderAttrPoTemp= (List<InfoServiceOrderAttrPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderAttrPoTemp!=null){
					InfoServiceOrderAttrPo infoServiceOrderAttrPo =infoServiceOrderAttrPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderAttrPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderAttrPoTemp!=null){
					for(InfoServiceOrderAttrPo po:infoServiceOrderAttrPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}		
		}else if("info_serv_prod_map".equals(tableName)){
			//服务订单产品列表	
			className.append("InfoServiceOrderProdMapPo");
			List<InfoServiceOrderProdMapPo> infoServiceOrderProdMapPoTemp=(List<InfoServiceOrderProdMapPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderProdMapPoTemp!=null){
					InfoServiceOrderProdMapPo infoServiceOrderProdMapPo =infoServiceOrderProdMapPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderProdMapPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderProdMapPoTemp!=null){
					for(InfoServiceOrderProdMapPo po:infoServiceOrderProdMapPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}			
		}else if("info_serv_terminal".equals(tableName)){
			//服务订单终端表
			className.append("InfoServiceOrderTerminalPo");
			List<InfoServiceOrderTerminalPo> infoServiceOrderTerminalPoTemp=(List<InfoServiceOrderTerminalPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderTerminalPoTemp!=null){
					InfoServiceOrderTerminalPo infoServiceOrderTerminalPo =infoServiceOrderTerminalPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderTerminalPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderTerminalPoTemp!=null){
					for(InfoServiceOrderTerminalPo po:infoServiceOrderTerminalPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}			
		}else if("info_serv_activity".equals(tableName)){
			//服务订单优惠活动
			className.append("InfoServiceOrderActivityPo");
			List<InfoServiceOrderActivityPo> infoServiceOrderActivityPoTemp=(List<InfoServiceOrderActivityPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderActivityPoTemp!=null){
					InfoServiceOrderActivityPo infoServiceOrderActivityPo =infoServiceOrderActivityPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderActivityPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderActivityPoTemp!=null){
					for(InfoServiceOrderActivityPo po:infoServiceOrderActivityPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}			
		}else if("info_serv_agreement".equals(tableName)){
			//服务订单协议表
			className.append("InfoServiceOrderAgreementPo");
			List<InfoServiceOrderAgreementPo> infoServiceOrderAgreementPoTemp=(List<InfoServiceOrderAgreementPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderAgreementPoTemp!=null){
					InfoServiceOrderAgreementPo infoServiceOrderAgreementPo =infoServiceOrderAgreementPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderAgreementPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderAgreementPoTemp!=null){
					for(InfoServiceOrderAgreementPo po:infoServiceOrderAgreementPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}		
		}else if("info_serv_pkg".equals(tableName)){
			//服务订单套餐列表
			className.append("InfoServiceOrderPackagePo");
			List<InfoServiceOrderPackagePo> infoServiceOrderPackagePoTemp=(List<InfoServiceOrderPackagePo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderPackagePoTemp!=null){
					InfoServiceOrderPackagePo infoServiceOrderPackagePo =infoServiceOrderPackagePoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderPackagePo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderPackagePoTemp!=null){
					for(InfoServiceOrderPackagePo po:infoServiceOrderPackagePoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}		
		}else if("info_serv_sim_card".equals(tableName)){
			//服务订单SIM卡信息表
			className.append("InfoServiceOrderSimCardPo");
			List<InfoServiceOrderSimCardPo> infoServiceOrderSimCardPoTemp =(List<InfoServiceOrderSimCardPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderSimCardPoTemp!=null){
					InfoServiceOrderSimCardPo infoServiceOrderSimCardPo =infoServiceOrderSimCardPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderSimCardPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderSimCardPoTemp!=null){
					for(InfoServiceOrderSimCardPo po:infoServiceOrderSimCardPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}		
		}else if("info_serv_fix".equals(tableName)){
			//服务订单固网信息表
			className.append("InfoServiceOrderFixPo");
			List<InfoServiceOrderFixPo> infoServiceOrderFixPoTemp=(List<InfoServiceOrderFixPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderFixPoTemp!=null){
					InfoServiceOrderFixPo infoServiceOrderFixPo =infoServiceOrderFixPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderFixPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderFixPoTemp!=null){
					for(InfoServiceOrderFixPo po:infoServiceOrderFixPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}		
		}else if("info_serv_m165".equals(tableName)){
			//服务订单宽带信息表
			className.append("InfoServiceOrderM165Po");
			List<InfoServiceOrderM165Po> infoServiceOrderM165PoTemp=(List<InfoServiceOrderM165Po>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderM165PoTemp!=null){
					InfoServiceOrderM165Po infoServiceOrderM165Po =infoServiceOrderM165PoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderM165Po);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderM165PoTemp!=null){
					for(InfoServiceOrderM165Po po:infoServiceOrderM165PoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}		
		}else if("info_serv_good_nbr".equals(tableName)){
			//服务订单靓号信息表
			className.append("InfoServiceOrderGoodNbrPo");
			List<InfoServiceOrderGoodNbrPo> infoServiceOrderGoodNbrPoTemp=(List<InfoServiceOrderGoodNbrPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderGoodNbrPoTemp!=null){
					InfoServiceOrderGoodNbrPo infoServiceOrderGoodNbrPo =infoServiceOrderGoodNbrPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderGoodNbrPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderGoodNbrPoTemp!=null){
					for(InfoServiceOrderGoodNbrPo po:infoServiceOrderGoodNbrPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}		
		}else if("info_serv_collection".equals(tableName)){
			//服务订单银行托收表
			className.append("InfoServiceOrderCollectionPo");
			List<InfoServiceOrderCollectionPo> infoServiceOrderCollectionPoTemp=(List<InfoServiceOrderCollectionPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderCollectionPoTemp!=null){
					InfoServiceOrderCollectionPo infoServiceOrderCollectionPo =infoServiceOrderCollectionPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderCollectionPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderCollectionPoTemp!=null){
					for(InfoServiceOrderCollectionPo po:infoServiceOrderCollectionPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}		
		}else if("info_serv_guarantor".equals(tableName)){
			//服务订单担保人表
			className.append("InfoServiceOrderGuarantorPo");
			List<InfoServiceOrderGuarantorPo> infoServiceOrderGuarantorPoTemp=(List<InfoServiceOrderGuarantorPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderGuarantorPoTemp!=null){
					InfoServiceOrderGuarantorPo infoServiceOrderGuarantorPo =infoServiceOrderGuarantorPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderGuarantorPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderGuarantorPoTemp!=null){
					for(InfoServiceOrderGuarantorPo po:infoServiceOrderGuarantorPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}		
		}else if("info_serv_developer".equals(tableName)){
			//服务订单发展人表
			className.append("InfoServiceOrderDeveloperPo");
			List<InfoServiceOrderDeveloperPo> infoServiceOrderDeveloperPoTemp= (List<InfoServiceOrderDeveloperPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderDeveloperPoTemp!=null){
					InfoServiceOrderDeveloperPo infoServiceOrderDeveloperPo =infoServiceOrderDeveloperPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderDeveloperPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderDeveloperPoTemp!=null){
					for(InfoServiceOrderDeveloperPo po:infoServiceOrderDeveloperPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}		
		}else if("info_serv_person".equals(tableName)){
			//服务订单个客信息表
			className.append("InfoServiceOrderPersionPo");
			List<InfoServiceOrderPersionPo> infoServiceOrderPersionPoTemp=(List<InfoServiceOrderPersionPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderPersionPoTemp!=null){
					InfoServiceOrderPersionPo infoServiceOrderPersionPo =infoServiceOrderPersionPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderPersionPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderPersionPoTemp!=null){
					for(InfoServiceOrderPersionPo po:infoServiceOrderPersionPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}		
		}else if("info_serv_ext".equals(tableName)){
			//服务订单个客信息表
			className.append("InfoServiceOrderExtPo");
			List<InfoServiceOrderExtPo> infoServiceOrderExtPoTemp=(List<InfoServiceOrderExtPo>) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoServiceOrderExtPoTemp!=null){
					InfoServiceOrderExtPo infoServiceOrderExtPo =infoServiceOrderExtPoTemp.get(0);
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoServiceOrderExtPo);
				}
			}else if("2".equals(flag)){
				if(infoServiceOrderExtPoTemp!=null){
					for(InfoServiceOrderExtPo po:infoServiceOrderExtPoTemp){
						Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,po,className.toString(),totalList);
						mapFieldValue.add(arrayValue);
					}
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}
			}		
		}else if("task_inst".equals(tableName)){
			//获取销售订单费用信息表数据
			className.append("ProcInstTaskInstPo");
			ProcInstTaskInstPo procInstTaskInstPoTemp=(ProcInstTaskInstPo) returnMap.get(tableName);
			if("1".equals(flag)){
				if(procInstTaskInstPoTemp!=null){
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, procInstTaskInstPoTemp);
				}
			}else if("2".equals(flag)){
				if(procInstTaskInstPoTemp!=null){
					Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,procInstTaskInstPoTemp,className.toString(),totalList);
					mapFieldValue.add(arrayValue);
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}

			}

		}else if("info_order_cancel".equals(tableName)){
			//获取撤单原因表数据
			className.append("InfoOrderCancelPo");
			InfoOrderCancelPo infoOrderCancelPoTemp=(InfoOrderCancelPo) returnMap.get(tableName);
			if("1".equals(flag)){
				if(infoOrderCancelPoTemp!=null){
					StrFieldValue=(String) functionReflecServDu.invokeField(className.toString(), fieldName, infoOrderCancelPoTemp);
				}
			}else if("2".equals(flag)){
				if(infoOrderCancelPoTemp!=null){
					Map<String, Object> arrayValue=splitGetArrayValue(fieldMap,infoOrderCancelPoTemp,className.toString(),totalList);
					mapFieldValue.add(arrayValue);
				}else{
					nullArrayValue=splitGetNullArrayValue(fieldMap);
					mapFieldValue.add(nullArrayValue);
				}

			}

		}


		if("1".equals(flag)){
			fieldValue=StrFieldValue;
		}else if("2".equals(flag)){
			fieldValue=mapFieldValue;
		}

		return fieldValue;
	}
	
	
	/**
	 * 如果json的一个key值为空，移除该key
	 * @param map
	 * @return
	 * @throws Throwable
	 */
	public String removeMapKey(String json,String mod_code) throws Throwable{
		String resJson=json;
		if("OPEN001OUT".equals(mod_code)){
			JSONObject jsonInfo = JSONObject.fromObject(json);  
			List<Object> list = new ArrayList<Object>();
			if(jsonInfo.get("userInfo")!=null){
				String userInfo=jsonInfo.get("userInfo").toString();
				if(userInfo!=null && !"".equals(userInfo)){
					JSONArray jsonArray = JSONArray.fromObject(userInfo);
					for(int i=0;i<jsonArray.size();i++){
						JSONObject object = jsonArray.getJSONObject(i);
						if(object!=null && !object.isNullObject()){
							if(object.get("activityInfo")!=null){
								String activityInfo=object.get("activityInfo").toString();
								if(activityInfo==null || "".equals(activityInfo)){
									object.remove("activityInfo");
									list.add(object);
								}
							}
						}
					}
					
				}
				if(list!=null && list.size()>0){
					jsonInfo.remove("userInfo");
					jsonInfo.put("userInfo", list);
				}
				resJson=jsonInfo.toString();
			}
			
		}else  if("OPEN002OUT".equals(mod_code)||"OPEN005OUT".equals(mod_code)){
			JSONObject jsonInfo = JSONObject.fromObject(json);  
			if(jsonInfo!=null && !jsonInfo.isNullObject()){
				if(jsonInfo.get("feeInfo")!=null){
					String feeInfo=jsonInfo.get("feeInfo").toString();
					if(feeInfo==null || "".equals(feeInfo)){
						jsonInfo.remove("feeInfo");
					}
				}
			}
		    resJson=jsonInfo.toString();
			
		}
		return resJson;
	}

}
