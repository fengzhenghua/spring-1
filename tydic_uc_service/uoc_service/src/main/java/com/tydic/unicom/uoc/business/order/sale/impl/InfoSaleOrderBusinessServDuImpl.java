package com.tydic.unicom.uoc.business.order.sale.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.uoc.base.uoccode.po.InfoUserGrantPo;
import com.tydic.unicom.uoc.base.uoccode.po.UserGrantFeePo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.InfoUserGrantServ;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.UserGrantFeeServ;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderActivityPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderProdMapPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSmsWarnPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskDealRecordPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderActivityServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderProdMapServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSmsWarnServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskDealRecordServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.pay.interfaces.GrantFeePayServDu;
import com.tydic.unicom.uoc.business.order.sale.interfaces.InfoSaleOrderBusinessServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderDataBakServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.SendPhotoServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.ServiceOrderServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ChangeToArtificialServiceServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.CheckProcessServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.FindMyPersonalTaskServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessCirculationServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.RevokeProcessServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.StartProcessServDu;
import com.tydic.unicom.uoc.service.code.interfaces.CodeListServDu;
import com.tydic.unicom.uoc.service.code.interfaces.ProcTacheRetServDu;
import com.tydic.unicom.uoc.service.code.vo.ProcTacheRetVo;
import com.tydic.unicom.uoc.service.common.impl.StrUtil;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdCancelModAppServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModAttrDefineServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModFunctionServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModOperSplitRuleServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcInstTaskInstServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcModTacheLoginServDu;
import com.tydic.unicom.uoc.service.mod.vo.OrdCancelModAppVo;
import com.tydic.unicom.uoc.service.mod.vo.OrdModAttrDefineVo;
import com.tydic.unicom.uoc.service.mod.vo.OrdModVo;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoDeliverOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOfrOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOrderCancelServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoPayOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderAttrServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderDistrDetailServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderDistributionServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderEditServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderExtServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderFeeServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderOfrMapServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderPersionCertServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderPersionServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServMapServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderExtServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderFeeServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoDeliverOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoOrderCancelVo;
import com.tydic.unicom.uoc.service.order.vo.InfoPayOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderAttrVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderDistrDetailVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderDistributionVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderEditVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderExtVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderFeeVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderOfrMapVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderPersionCertVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderPersionVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderServMapVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderExtVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderFeeVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.uoc.service.task.interfaces.CheckArtificialTaskServDu;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

public class InfoSaleOrderBusinessServDuImpl implements InfoSaleOrderBusinessServDu {

	Logger logger = Logger.getLogger(InfoSaleOrderBusinessServDuImpl.class);

	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;
	@Autowired
	private InfoSaleOrderServDu infoSaleOrderServDu;
	@Autowired
	private InfoOfrOrderServDu infoOfrOrderServDu;
	@Autowired
	private InfoSaleOrderEditServDu infoSaleOrderEditServDu;
	@Autowired
	private InfoSaleOrderAttrServDu infoSaleOrderAttrServDu;
	@Autowired
	private InfoSaleOrderOfrMapServDu infoSaleOrderOfrMapServDu;
	@Autowired
	private InfoSaleOrderPersionServDu infoSaleOrderPersionServDu;
	@Autowired
	private InfoSaleOrderDistrDetailServDu infoSaleOrderDistrDetailServDu;
	@Autowired
	private InfoSaleOrderDistributionServDu infoSaleOrderDistributionServDu;
	@Autowired
	private InfoSaleOrderExtServDu infoSaleOrderExtServDu;
	@Autowired
	private InfoSaleOrderServMapServDu infoSaleOrderServMapServDu;
	@Autowired
	private InfoSaleOrderFeeServDu infoSaleOrderFeeServDu;
	@Autowired
	private InfoPayOrderServDu infoPayOrderServDu;
	@Autowired
	private InfoDeliverOrderServDu infoDeliverOrderServDu;
	@Autowired
	private InfoServiceOrderFeeServDu infoServiceOrderFeeServDu;
	@Autowired
	private OrdModAttrDefineServDu ordModAttrDefineServDu;
	@Autowired
	private OrdModOperSplitRuleServDu ordModOperSplitRuleServDu;
	@Autowired
	private ProcModTacheLoginServDu procModTacheLoginServDu;
	@Autowired
	private ProcInstTaskInstServDu procInstTaskInstServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired
	private OperServDu operServDu;
	@Autowired
	private GetIdServDu getIdServDu;
	@Autowired
	private OrdModFunctionServDu ordModFunctionServDu;
	@Autowired
	private ServiceOrderServDu serviceOrderServDu;
	@Autowired
	private StartProcessServDu startProcessServDu;
	@Autowired
	private RevokeProcessServDu revokeProcessServDu;
	@Autowired
	private CheckProcessServDu checkProcessServDu;
	@Autowired
	private FindMyPersonalTaskServDu findMyPersonalTaskServDu;
	@Autowired
	private ProcessCirculationServDu processCirculationServDu;
	@Autowired
	private ChangeToArtificialServiceServDu changeToArtificialServiceServDu;
	@Autowired
	private OrderDataBakServDu orderDataBakServDu;
	@Autowired
	private InfoSaleOrderPersionCertServDu infoSaleOrderPersionCertServDu;
	@Autowired
	private CheckArtificialTaskServDu checkArtificialTaskServDu;
	@Autowired
	private ProcInstTaskInstServ procInstTaskInstServ;
	@Autowired
	private ProcInstTaskDealRecordServ procInstTaskDealRecordServ;
	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;
	@Autowired
	private InfoOrderCancelServDu infoOrderCancelServDu;
	@Autowired
	private OrdCancelModAppServDu ordCancelModAppServDu;
	@Autowired
	private InfoServiceOrderServ infoServiceOrderServ;
	@Autowired
	private SendPhotoServDu sendPhotoServDu;
	@Autowired
	private ProcTacheRetServDu procTacheRetServDu;
	@Autowired
	private InfoServiceOrderActivityServ infoServiceOrderActivityServ;
	@Autowired
	private CodeListServDu codeListServDu;
	@Autowired
	private InfoUserGrantServ infoUserGrantServ;
	@Autowired
	private UserGrantFeeServ userGrantFeeServ;
	@Autowired
	private GrantFeePayServDu grantFeePayServDu;
	@Autowired
	private InfoServiceOrderProdMapServ infoServiceOrderProdMapServ;
	@Autowired
	private InfoSmsWarnServ infoSmsWarnServ;
	@Autowired
	private InfoServiceOrderExtServDu infoServiceOrderExtServDu;

	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;

	private void getBeanDu(){
		if(infoServiceOrderServDu == null){
			infoServiceOrderServDu = (InfoServiceOrderServDu) ToolSpring.getBean("InfoServiceOrderServDu");
		}
		if(infoSaleOrderServDu == null){
			infoSaleOrderServDu = (InfoSaleOrderServDu) ToolSpring.getBean("InfoSaleOrderServDu");
		}
		if(infoOfrOrderServDu == null){
			infoOfrOrderServDu = (InfoOfrOrderServDu) ToolSpring.getBean("InfoOfrOrderServDu");
		}
		if(infoSaleOrderEditServDu == null){
			infoSaleOrderEditServDu = (InfoSaleOrderEditServDu) ToolSpring.getBean("InfoSaleOrderEditServDu");
		}
		if(infoSaleOrderAttrServDu == null){
			infoSaleOrderAttrServDu = (InfoSaleOrderAttrServDu) ToolSpring.getBean("InfoSaleOrderAttrServDu");
		}
		if(infoSaleOrderOfrMapServDu == null){
			infoSaleOrderOfrMapServDu = (InfoSaleOrderOfrMapServDu) ToolSpring.getBean("InfoSaleOrderOfrMapServDu");
		}
		if(infoSaleOrderPersionServDu == null){
			infoSaleOrderPersionServDu = (InfoSaleOrderPersionServDu) ToolSpring.getBean("InfoSaleOrderPersionServDu");
		}
		if(infoSaleOrderDistrDetailServDu == null){
			infoSaleOrderDistrDetailServDu = (InfoSaleOrderDistrDetailServDu) ToolSpring.getBean("InfoSaleOrderDistrDetailServDu");
		}
		if(infoSaleOrderDistributionServDu == null){
			infoSaleOrderDistributionServDu = (InfoSaleOrderDistributionServDu) ToolSpring.getBean("InfoSaleOrderDistributionServDu");
		}
		if(infoSaleOrderExtServDu == null){
			infoSaleOrderExtServDu = (InfoSaleOrderExtServDu) ToolSpring.getBean("InfoSaleOrderExtServDu");
		}
		if(infoSaleOrderServMapServDu == null){
			infoSaleOrderServMapServDu = (InfoSaleOrderServMapServDu) ToolSpring.getBean("InfoSaleOrderServMapServDu");
		}
		if(infoSaleOrderFeeServDu == null){
			infoSaleOrderFeeServDu = (InfoSaleOrderFeeServDu) ToolSpring.getBean("InfoSaleOrderFeeServDu");
		}
		if(infoPayOrderServDu == null){
			infoPayOrderServDu = (InfoPayOrderServDu) ToolSpring.getBean("InfoPayOrderServDu");
		}
		if(infoDeliverOrderServDu == null){
			infoDeliverOrderServDu = (InfoDeliverOrderServDu) ToolSpring.getBean("InfoDeliverOrderServDu");
		}
		if(infoServiceOrderFeeServDu == null){
			infoServiceOrderFeeServDu = (InfoServiceOrderFeeServDu) ToolSpring.getBean("InfoServiceOrderFeeServDu");
		}
		if(ordModAttrDefineServDu == null){
			ordModAttrDefineServDu = (OrdModAttrDefineServDu) ToolSpring.getBean("OrdModAttrDefineServDu");
		}
		if(ordModOperSplitRuleServDu == null){
			ordModOperSplitRuleServDu = (OrdModOperSplitRuleServDu) ToolSpring.getBean("OrdModOperSplitRuleServDu");
		}
		if(procModTacheLoginServDu == null){
			procModTacheLoginServDu = (ProcModTacheLoginServDu) ToolSpring.getBean("ProcModTacheLoginServDu");
		}
		if(procInstTaskInstServDu == null){
			procInstTaskInstServDu = (ProcInstTaskInstServDu) ToolSpring.getBean("ProcInstTaskInstServDu");
		}
		if(jsonToBeanServDu == null){
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		if(operServDu == null){
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		if(getIdServDu == null){
			getIdServDu = (GetIdServDu) ToolSpring.getBean("GetIdServDu");
		}
		if(ordModFunctionServDu == null){
			ordModFunctionServDu = (OrdModFunctionServDu) ToolSpring.getBean("OrdModFunctionServDu");
		}
		if(serviceOrderServDu == null){
			serviceOrderServDu = (ServiceOrderServDu) ToolSpring.getBean("ServiceOrderServDu");
		}
		if(startProcessServDu == null){
			startProcessServDu = (StartProcessServDu) ToolSpring.getBean("StartProcessServDu");
		}
		if(revokeProcessServDu == null){
			revokeProcessServDu = (RevokeProcessServDu) ToolSpring.getBean("RevokeProcessServDu");
		}
		if(checkProcessServDu == null){
			checkProcessServDu = (CheckProcessServDu) ToolSpring.getBean("CheckProcessServDu");
		}
		if(findMyPersonalTaskServDu == null){
			findMyPersonalTaskServDu = (FindMyPersonalTaskServDu) ToolSpring.getBean("FindMyPersonalTaskServDu");
		}
		if(processCirculationServDu == null){
			processCirculationServDu = (ProcessCirculationServDu) ToolSpring.getBean("ProcessCirculationServDu");
		}
		if(changeToArtificialServiceServDu == null){
			changeToArtificialServiceServDu = (ChangeToArtificialServiceServDu) ToolSpring.getBean("ChangeToArtificialServiceServDu");
		}
		if(orderDataBakServDu == null){
			orderDataBakServDu = (OrderDataBakServDu) ToolSpring.getBean("OrderDataBakServDu");
		}
		if(checkArtificialTaskServDu == null){
			checkArtificialTaskServDu = (CheckArtificialTaskServDu) ToolSpring.getBean("CheckArtificialTaskServDu");
		}
		if (procInstTaskInstServ == null) {
			procInstTaskInstServ = (ProcInstTaskInstServ) ToolSpring.getBean("ProcInstTaskInstServ");
		}
		if (procInstTaskDealRecordServ == null) {
			procInstTaskDealRecordServ = (ProcInstTaskDealRecordServ) ToolSpring.getBean("ProcInstTaskDealRecordServ");
		}
		if (infoOrderCancelServDu == null) {
			infoOrderCancelServDu = (InfoOrderCancelServDu) ToolSpring.getBean("InfoOrderCancelServDu");
		}
		if (ordCancelModAppServDu == null) {
			ordCancelModAppServDu = (OrdCancelModAppServDu) ToolSpring.getBean("OrdCancelModAppServDu");
		}
		if (abilityPlatformServDu == null) {
			abilityPlatformServDu = (AbilityPlatformServDu) ToolSpring.getBean("AbilityPlatformServDu");
		}
		if (procTacheRetServDu == null) {
			procTacheRetServDu = (ProcTacheRetServDu) ToolSpring.getBean("ProcTacheRetServDu");
		}
	}


	/**
	 * 销售订单创建 UOC0003入参组装
	 * @param json_in
	 * @return
	 * @throws Exception
	 */
	public UocMessage createSaleOrderForAbilityPlatform(String  json_in) throws Exception{

		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		ParaVo vo =new ParaVo();
		JSONObject json = JSONObject.fromObject(json_in);
		String jsession_id =json.get("jsession_id").toString();
		String order_type = json.get("order_type").toString();
		String accept_no = json.get("accept_no").toString();
		String accept_type = json.get("accept_type").toString();
		String accept_system = json.get("accept_system").toString();
		String json_info = json.get("json_info").toString();
		String flow_flag = json.get("flow_flag").toString();
		String province_code = json.get("province_code").toString();
		String area_code =json.get("area_code").toString();
		String pay_flag = json.get("pay_flag").toString();
		String express_flag =json.get("express_flag").toString();
		String auto_confirm=json.get("auto_confirm").toString();
		String callback_url =json.get("callback_url").toString();
		vo.setJsession_id(jsession_id);
		vo.setOrder_type(order_type);
		vo.setAccept_no(accept_no);
		vo.setAccept_type(accept_type);
		vo.setAccept_system(accept_system);
		vo.setJson_info(json_info);
		vo.setFlow_flag(flow_flag);
		vo.setProvince_code(province_code);
		vo.setArea_code(area_code);
		vo.setPay_flag(pay_flag);
		vo.setExpress_flag(express_flag);
		vo.setAuto_confirm(auto_confirm);
		vo.setCallback_url(callback_url);
		UocMessage message =createSaleOrder(vo);
		return message;
	}


	/**
	 * 销售订单创建 UOC0003
	 */
	@Override
	public UocMessage createSaleOrder(ParaVo paraVo) throws Exception {
		logger.info("rest-----------createSaleOrder UOC0003");
		getBeanDu();
		UocMessage message = new UocMessage();

		String jsession_id = paraVo.getJsession_id();
		String order_type = paraVo.getOrder_type();
		String accept_no = paraVo.getAccept_no();
		String accept_type = paraVo.getAccept_type();
		String accept_system = paraVo.getAccept_system();
		String json_info = paraVo.getJson_info();
		String flow_flag = paraVo.getFlow_flag();
		String province_code = paraVo.getProvince_code();
		String area_code = paraVo.getArea_code();
		String pay_flag = paraVo.getPay_flag();
		if (StringUtils.isEmpty(pay_flag)) {
			pay_flag = "0";
		}
		String pay_type = paraVo.getPay_type();
		String auto_confirm = paraVo.getAuto_confirm();
		String express_flag = paraVo.getExpress_flag();
		String callback_url = paraVo.getCallback_url();
		if (express_flag == null || "".equals(express_flag)) {
			express_flag = "0";
		}

		if (jsession_id == null || "".equals(jsession_id)) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jsession_id不能为空");
			return message;
		}
		if (order_type == null || "".equals(order_type)) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("order_type不能为空");
			return message;
		}
		if (accept_system == null || "".equals(accept_system)) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("accept_system不能为空");
			return message;
		}
		if (json_info == null || "".equals(json_info)) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("json_info不能为空");
			return message;
		}
		/*
		 * 校验jession_id
		 */
		UocMessage res = operServDu.isLogin(jsession_id);
		if (!"0000".equals(res.getRespCode())) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent(res.getContent());
			return message;
		}
		Map<String, Object> oper_info = (Map) res.getArgs().get("oper_info");
		String oper_no = (String) oper_info.get("oper_no");
		String accept_depart_no = (String) oper_info.get("depart_no");
		String accpet_depart_name = (String) oper_info.get("depart_name");
		String accept_depart_type = (String) oper_info.get("channel_type");
		if ("".equals(province_code) || province_code == null) {
			province_code = (String) oper_info.get("province_code");
		}
		if ("".equals(area_code) || area_code == null) {
			area_code = (String) oper_info.get("area_code");
		}
		// 生成订单号
		String sale_order_no = "";

		// 3根据传入的accept_no查询销售订单，若存在数据则直接返回成功跳过后面的逻辑，直接返回查到的销售订单号给前台
		InfoSaleOrderVo saleOrderInVo = new InfoSaleOrderVo();
		saleOrderInVo.setAccept_no(accept_no);
		saleOrderInVo.setAccept_system(accept_system);
		saleOrderInVo.setArea_code(area_code);
		InfoSaleOrderVo existSaleOrder = infoSaleOrderServDu.queryInfoSaleOrder(saleOrderInVo);
		if (existSaleOrder != null) {
			sale_order_no = existSaleOrder.getSale_order_no();

			// 查询已创建服务订单号
			List<String> serv_order_no_list = new ArrayList<String>();
			InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
			infoServiceOrderVo.setSale_order_no(sale_order_no);
			List<InfoServiceOrderVo> infoServiceOrderVos = infoServiceOrderServDu.queryInfoServiceOrderBySaleOrderNo(infoServiceOrderVo);
			serv_order_no_list.add(infoServiceOrderVos.get(0).getServ_order_no());

			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("系统流水号重复，该订单已创建");
			message.addArg("serv_order_no_list", serv_order_no_list);
			message.addArg("sale_order_no", sale_order_no);
			return message;
		}

		sale_order_no = getIdServDu.getId("createSaleOrderNo", province_code, area_code, "");
		InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
		Map<String, String> stringMap = StrUtil.splitStringFromOrderNo(sale_order_no);
		String part_month = stringMap.get("part_month");
		// 写入销售订单
		infoSaleOrderVo = new InfoSaleOrderVo();
		if ("101".equals(flow_flag)) {// flow_flag=101，订单初始状态记录101，否则记录100
			infoSaleOrderVo.setOrder_state("101");
		} else {
			infoSaleOrderVo.setOrder_state("100");
		}
		infoSaleOrderVo.setCancle_flag("1"); // 写死1
		infoSaleOrderVo.setPay_flag(pay_flag); // 默认pay_flag0 pay_type 200, express_flag 0, cancle_flag 1, order_state 100
		infoSaleOrderVo.setPay_type(pay_type);
		infoSaleOrderVo.setExpress_flag(express_flag);
		infoSaleOrderVo.setSale_order_no(sale_order_no);
		infoSaleOrderVo.setAccept_system(accept_system);
		infoSaleOrderVo.setAccept_no(accept_no);
		infoSaleOrderVo.setAccept_type(accept_type);
		infoSaleOrderVo.setOrder_type(order_type);
		infoSaleOrderVo.setProvince_code(province_code);
		infoSaleOrderVo.setArea_code(area_code);
		infoSaleOrderVo.setPart_month(part_month);
		infoSaleOrderVo.setAccept_depart_type(accept_depart_type);
		infoSaleOrderVo.setCallback_url(callback_url);
		String accept_time = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
		infoSaleOrderVo.setAccept_time(accept_time);
		// 根据jession_id获取操作信息、工号渠道等（待补充）
		infoSaleOrderVo.setAccept_oper_no(oper_no);
		infoSaleOrderVo.setAccept_depart_no(accept_depart_no);
		infoSaleOrderVo.setAccept_depart_name(accpet_depart_name);

		boolean flag = infoSaleOrderServDu.createInfoSaleOrder(infoSaleOrderVo);
		if (!flag) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("创建订单失败");
			throw new UocException(message);
		}
		// 写入销售订单修订表
		InfoSaleOrderEditVo infoSaleOrderEditVo = new InfoSaleOrderEditVo();
		String id = getIdServDu.getId("createLogId", province_code, "*", "");
		infoSaleOrderEditVo.setId(id);
		infoSaleOrderEditVo.setJson_content(json_info);
		infoSaleOrderEditVo.setPart_month(part_month);
		infoSaleOrderEditVo.setProvince_code(province_code);
		infoSaleOrderEditVo.setArea_code(area_code);
		infoSaleOrderEditVo.setSale_order_no(sale_order_no);
		infoSaleOrderEditVo.setEdit_type("100");
		infoSaleOrderEditVo.setState("1");
		infoSaleOrderEditVo.setEdit_system("");
		infoSaleOrderEditVo.setOper_no(oper_no);
		String edit_time = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
		infoSaleOrderEditVo.setEdit_time(edit_time);
		// infoSaleOrderEditVo.setEdit_desc(edit_desc);
		infoSaleOrderEditVo.setIs_compress("1");

		flag = infoSaleOrderEditServDu.createInfoSaleOrderEdit(infoSaleOrderEditVo);
		if (!flag) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("创建订单修订表失败");
			throw new UocException(message);
		}
		/*
		 * 6、调用BASE0003订单模板获取服务取出订单模板 7、通过传入的json_info
		 * 和上面取出的订单模板信息调用BASE0005根据订单模板入库服务，订单类型填销售订单
		 */
		UocMessage result = ordModFunctionServDu.queryOrdMod(sale_order_no, "100", "100");
		if ("0000".equals(result.getRespCode())) {
			String mod_code = (String) result.getArgs().get("mod_code");
			OrdModVo ordModVo = new OrdModVo();
			ordModVo.setMod_code(mod_code);
			ordModVo.setJson_in(json_info);
			ordModVo.setOrder_type("100");
			ordModVo.setOrder_no(sale_order_no);
			ordModVo.setJsession_id(jsession_id);
			result = ordModFunctionServDu.insertByOrdMod(ordModVo);
			if (!"0000".equals(result.getRespCode())) {
				logger.info("------入库失败------");
				logger.info("---------" + result.getContent());
				throw new UocException(result);
			}
		} else {
			logger.info("---------" + result.getContent());
			throw new UocException(result);
		}
		// 8、 根据传入order_type查询业务拆分规则表，如果存在对应关系，则记录销售订单业务表，一对多时要记录多条
		// 查询业务拆分规则表
		// OrdModOperSplitRuleVo ordModOperSplitRuleVo =new
		// OrdModOperSplitRuleVo();
		// ordModOperSplitRuleVo.setSource_oper_code(order_type);
		// List<OrdModOperSplitRuleVo> ordModOperSplitRuleVos
		// =ordModOperSplitRuleServDu.queryOrdModOperSplitRuleByOperCode(ordModOperSplitRuleVo);
		// if(ordModOperSplitRuleVos !=null){
		// Map<String,String> map =jsonToBeanServDu.jsonToMapStr(json_info);
		// String acc_nbr =map.get("serial_number");
		// for(OrdModOperSplitRuleVo vo:ordModOperSplitRuleVos){
		// InfoSaleOrderServMapVo infoSaleOrderServMapVo =new
		// InfoSaleOrderServMapVo();
		// id =getIdServDu.getId("createLogId", province_code,
		// "*",sale_order_no);
		// infoSaleOrderServMapVo.setId(id);
		// infoSaleOrderServMapVo.setProvince_code(province_code);
		// infoSaleOrderServMapVo.setPart_month(part_month);
		// infoSaleOrderServMapVo.setArea_code(area_code);
		// infoSaleOrderServMapVo.setSale_order_no(sale_order_no);
		// infoSaleOrderServMapVo.setOper_code(vo.getTarget_oper_code());
		// infoSaleOrderServMapVo.setOper_name(vo.getTarget_oper_name());
		// infoSaleOrderServMapVo.setAcc_nbr(acc_nbr);
		// infoSaleOrderServMapServDu.createInfoSaleOrderServMap(infoSaleOrderServMapVo);
		// }
		//
		// }
		// 9、 判断传入auto_confirm标识为1，则自动调用UOC0005服务
		if ("1".equals(auto_confirm)) {
			ParaVo vo = new ParaVo();
			vo.setJsession_id(jsession_id);
			vo.setSale_order_no(sale_order_no);
			message = confirmSaleOrder(vo);
			if (!"0000".equals(message.getRespCode())) {
				logger.info("------订单确认失败---UOC0005---");
				throw new UocException(message);
			}
		}

		// 10、判断若flow_flag=101调用流程启动服务BASE0011，oper_type填104
		if ("101".equals(flow_flag)) {
			// 启动服务BASE0011，oper_type填104
			result = startProcessServDu.startProcess(sale_order_no, "104", jsession_id);
			if (!"0000".equals(result.getRespCode())) {
				logger.info("------启动服务失败------");
				logger.info("---------" + result.getContent());
				throw new UocException(result);
			}
		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("销售订单创建成功");
		message.addArg("sale_order_no", sale_order_no);
		return message;

	}

	/**
	 * 销售订单修改UOC0004入参组装
	 * @param json_in
	 * @return
	 * @throws Exception
	 */
	public UocMessage updateSaleOrderForAbilityPlatform(String  json_in) throws Exception{

		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		ParaVo vo =new ParaVo();
		String jsession_id =(String) map.get("jsession_id");
		String sale_order_no =(String) map.get("sale_order_no");
		String update_type =(String) map.get("update_type");
		String flow_type =(String) map.get("flow_type");
		String json_info =(String) map.get("json_info");
		String pay_flag =(String) map.get("pay_flag");
		String pay_type =(String) map.get("pay_type");
		String express_flag =(String) map.get("express_flag");
		String edit_time =(String) map.get("edit_time");
		String edit_desc =(String) map.get("edit_desc");
		String edit_system =(String) map.get("edit_system");
		@SuppressWarnings("unchecked")
		Map<String, String> action_code = (Map<String, String>) map.get("action_code");
		vo.setJsession_id(jsession_id);
		vo.setSale_order_no(sale_order_no);
		vo.setUpdate_type(update_type);
		vo.setFlow_type(flow_type);
		vo.setJson_info(json_info);
		vo.setPay_flag(pay_flag);
		vo.setPay_type(pay_type);
		vo.setExpress_flag(express_flag);
		vo.setEdit_time(edit_time);
		vo.setEdit_desc(edit_desc);
		vo.setEdit_system(edit_system);
		vo.setAction_code(action_code);
		UocMessage message =updateSaleOrder(vo);
		return message;
	}

	/**
	 * 销售订单修改UOC0004
	 */
	@Override
	public UocMessage updateSaleOrder(ParaVo paraVo)  throws Exception{
		logger.info("rest-----------updateSaleOrder");
		getBeanDu();
		UocMessage message =new UocMessage();
			//			Map<String, Object> map =jsonToBeanServDu.jsonToMap(jsonStr);
			String jsession_id =paraVo.getJsession_id();
			String sale_order_no =paraVo.getSale_order_no();
			String update_type =paraVo.getUpdate_type();
			String flow_type =paraVo.getFlow_type();
			String json_info = paraVo.getJson_info();
			String pay_flag =paraVo.getPay_flag();
			String pay_type =paraVo.getPay_type();
			String express_flag =paraVo.getExpress_flag();
			String edit_time =paraVo.getEdit_time();
			String edit_desc =paraVo.getEdit_desc();
			String edit_system =paraVo.getEdit_system();
			Map<String, String> action_code =paraVo.getAction_code();

			if(jsession_id ==null || "".equals(jsession_id)){
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("jession_id不能为空");
				return message;
			}
			if(sale_order_no ==null || "".equals(sale_order_no)){
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("sale_order_no不能为空");
				return message;
			}
			if(json_info ==null || "".equals(json_info)){
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("json_info不能为空");
				return message;
			}

			//			JSONObject object =JSONObject.fromObject(json_info_obj);
			//			logger.info(object.toString());
			//			String json_info =object.toString();
			/*
			 * 1、调用BASE0008记录接口日志 需要参数 service_code json_in json_out（待补充）
			 */



			/*
			 * 校验jession_id
			 */
			UocMessage res =operServDu.isLogin(jsession_id);
			if(!"0000".equals(res.getRespCode())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent(res.getContent());
				return message;
			}
			Map<String,Object> oper_info =(Map)res.getArgs().get("oper_info");
			String oper_no =(String)oper_info.get("oper_no");
			String province_code =(String)oper_info.get("province_code");
			String area_code =(String)oper_info.get("area_code");
			/*
			 * 查询销售订单，无信息报错
			 */
			InfoSaleOrderVo infoSaleOrderVo =new InfoSaleOrderVo();
			infoSaleOrderVo.setSale_order_no(sale_order_no);
			infoSaleOrderVo =infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			if(infoSaleOrderVo ==null){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("无对应的销售订单");
				return message;
			}
			//⦁	flow_type=0、1时调用BASE0022服务校验是否满足流转要求
			if("0".equals(flow_type) || "1".equals(flow_type)){
				 UocMessage checkRes =checkProcessServDu.checkProcess(infoSaleOrderVo.getProc_inst_id(), sale_order_no, flow_type, action_code);
				  if(!"0000".equals(checkRes.getRespCode())){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
					 	message.setContent("流转要求检验异常");
					 	return message;
				  }

			}

			//4、update销售订单表
			boolean flag =false;
			if(pay_flag !=null && !pay_flag.equals(infoSaleOrderVo.getPay_flag())){
				infoSaleOrderVo.setPay_flag(pay_flag);
				flag =true;
			}
			if(pay_type !=null && !pay_type.equals(infoSaleOrderVo.getPay_type())){
				infoSaleOrderVo.setPay_type(pay_type);
				flag =true;
			}
			if(express_flag !=null && !express_flag.equals(infoSaleOrderVo.getExpress_flag())){
				infoSaleOrderVo.setExpress_flag(express_flag);
				flag =true;
			}
			if(flag){
			 boolean update_flag =infoSaleOrderServDu.updateInfoSaleOrder(infoSaleOrderVo);
			 if(!update_flag){
				 message.setRespCode(RespCodeContents.SERVICE_FAIL);
				 message.setContent("更新销售订单失败");
				 throw new UocException(message);
			 }

			}
			//写入销售订单修订表
			InfoSaleOrderEditVo infoSaleOrderEditVo =new InfoSaleOrderEditVo();
		String id = getIdServDu.getId("createLogId", province_code, "*", "");
			infoSaleOrderEditVo.setId(id);
			infoSaleOrderEditVo.setJson_content(json_info);
			infoSaleOrderEditVo.setPart_month(infoSaleOrderVo.getPart_month());
			infoSaleOrderEditVo.setProvince_code(infoSaleOrderVo.getProvince_code());
			infoSaleOrderEditVo.setArea_code(infoSaleOrderVo.getArea_code());
			infoSaleOrderEditVo.setSale_order_no(infoSaleOrderVo.getSale_order_no());
			infoSaleOrderEditVo.setEdit_type("200");
			infoSaleOrderEditVo.setState("1");
			infoSaleOrderEditVo.setEdit_system(infoSaleOrderVo.getAccept_system());
			infoSaleOrderEditVo.setOper_no(oper_no);
			if(edit_time ==null){
				edit_time =DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
			}
			infoSaleOrderEditVo.setEdit_time(edit_time);
			infoSaleOrderEditVo.setEdit_desc(edit_desc);
			infoSaleOrderEditVo.setIs_compress("1");

			boolean create_flag =infoSaleOrderEditServDu.createInfoSaleOrderEdit(infoSaleOrderEditVo);
			if(!create_flag){
				 message.setRespCode(RespCodeContents.SERVICE_FAIL);
				 message.setContent("创建修订表失败");
				 throw new UocException(message);
			 }
			if("".equals(update_type) || update_type ==null || "0".equals(update_type)){ //7、判断如果传入的update_type为0或空，则根据销售订单号查询销售订单相关信息表，删除所有数据
				//删除相关表信息 销售订单属性集表 、销售订单个客信息表、销售订单收件人信息表、销售订单包裹信息表、销售订单商品表、销售订单业务表
				InfoSaleOrderAttrVo infoSaleOrderAttrVo =new InfoSaleOrderAttrVo();
				infoSaleOrderAttrVo.setSale_order_no(infoSaleOrderVo.getSale_order_no());
				infoSaleOrderAttrServDu.deleteInfoSaleOrderAttrBySaleOrderNo(infoSaleOrderAttrVo);
				InfoSaleOrderPersionVo infoSaleOrderPersionVo =new InfoSaleOrderPersionVo();
				infoSaleOrderPersionVo.setSale_order_no(infoSaleOrderVo.getSale_order_no());
				infoSaleOrderPersionServDu.deleteInfoSaleOrderPersionBySaleOrderNo(infoSaleOrderPersionVo);
				InfoSaleOrderDistrDetailVo infoSaleOrderDistrDetailVo =new InfoSaleOrderDistrDetailVo();
				infoSaleOrderDistrDetailVo.setSale_order_no(infoSaleOrderVo.getSale_order_no());
				infoSaleOrderDistrDetailServDu.deleteInfoSaleOrderDistrDetailBySaleOrderNo(infoSaleOrderDistrDetailVo);
				InfoSaleOrderDistributionVo infoSaleOrderDistributionVo =new InfoSaleOrderDistributionVo();
				infoSaleOrderDistributionVo.setSale_order_no(infoSaleOrderVo.getSale_order_no());
				infoSaleOrderDistributionServDu.deleteInfoSaleOrderDistributionBySaleOrderNo(infoSaleOrderDistributionVo);
				InfoSaleOrderOfrMapVo infoSaleOrderOfrMapVo =new InfoSaleOrderOfrMapVo();
				infoSaleOrderOfrMapVo.setSale_order_no(infoSaleOrderVo.getSale_order_no());
				infoSaleOrderOfrMapServDu.queryInfoSaleOrderOfrMapBySaleOrderNo(infoSaleOrderOfrMapVo);
				InfoSaleOrderServMapVo infoSaleOrderServMapVo =new InfoSaleOrderServMapVo();
				infoSaleOrderServMapVo.setSale_order_no(infoSaleOrderVo.getSale_order_no());
				infoSaleOrderServMapServDu.deleteInfoSaleOrderServMapBySaleOrderNo(infoSaleOrderServMapVo);
				InfoSaleOrderExtVo infoSaleOrderExtVo =new InfoSaleOrderExtVo();
				infoSaleOrderExtVo.setSale_order_no(sale_order_no);
				infoSaleOrderExtServDu.deleteInfoSaleOrderExtBySaleOrderNo(infoSaleOrderExtVo);
				InfoSaleOrderPersionCertVo infoSaleOrderPersionCertVo =new InfoSaleOrderPersionCertVo();
				infoSaleOrderPersionCertVo.setSale_order_no(sale_order_no);
				infoSaleOrderPersionCertServDu.deleteInfoSaleOrderPersionCertBySaleOrderNo(infoSaleOrderPersionCertVo);
			}
			/* 6调用BASE0003订单模板获取服务取出订单模板
			 * 7、通过传入的json_info 和上面取出的订单模板信息调用BASE0005根据订单模板入库服务，订单类型填销售订单
			 */
			UocMessage result =ordModFunctionServDu.queryOrdMod(sale_order_no, "100", "100");
			if("0000".equals(result.getRespCode())){
				String mod_code =(String)result.getArgs().get("mod_code");
				OrdModVo ordModVo =new OrdModVo();
				ordModVo.setMod_code(mod_code);
				ordModVo.setJson_in(json_info);
				ordModVo.setOrder_type("100");
				ordModVo.setOrder_no(sale_order_no);
				ordModVo.setJsession_id(jsession_id);
				result =ordModFunctionServDu.insertByOrdMod(ordModVo);
				if(!"0000".equals(result.getRespCode())){
					logger.info("------入库失败------");
					logger.info("---------"+result.getContent());
					throw new UocException(result);
				}
			}else{
				logger.info("---------"+result.getContent());
				throw new UocException(result);
			}

			//9
			if("0".equals(flow_type) || "1".equals(flow_type)){
				String proc_inst_id =infoSaleOrderVo.getPre_proc_inst_id();
				if(proc_inst_id ==null || "".equals(proc_inst_id)){
					//然后调用BASE0016流程流转服务，传入1时要把操作编码也同时传入
					if("0".equals(flow_type)){
						message =processCirculationServDu.processCirculation(sale_order_no, "104", flow_type, null,"");
					}else{
						message =processCirculationServDu.processCirculation(sale_order_no, "104", flow_type,action_code,"");
					}
					if(!"0000".equals(message.getRespCode())){
						throw new UocException(message);
					}
				}else{
					message.setRespCode(RespCodeContents.PARAM_ERROR);
					message.setContent("销售订单proc_inst_id为空");
					throw new UocException(message);
				}

			}


			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("销售订单修改成功");
			return message;
	}


	/**
	 * 销售订单确认UOC0005入参组装
	 * @param json_in
	 * @return
	 * @throws Exception
	 */
	public UocMessage confirmSaleOrderForAbilityPlatform(String  json_in) throws Exception{

		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		ParaVo vo =new ParaVo();

		String jsession_id =(String) map.get("jsession_id");
		String sale_order_no =(String) map.get("sale_order_no");

		vo.setJsession_id(jsession_id);
		vo.setSale_order_no(sale_order_no);
		UocMessage message =confirmSaleOrder(vo);
		return message;
	}


	/**
	 * 销售订单确认UOC0005
	 * @param paraVo
	 * @return
	 */
	@Override
	public UocMessage confirmSaleOrder(ParaVo paraVo) throws Exception {
		logger.info("rest-----------confirmSaleOrder");
		getBeanDu();
		UocMessage message = new UocMessage();
		// Map<String, Object> map =jsonToBeanServDu.jsonToMap(jsonStr);
		String jsession_id = paraVo.getJsession_id();
		String sale_order_no = paraVo.getSale_order_no();
		/*
		 * 1、调用BASE0008记录接口日志 需要参数 service_code json_in json_out（待补充）
		 */
		if (sale_order_no == null || "".equals(sale_order_no)) {
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("sale_order_no不能为空");
			return message;
		}

		/*
		 * 校验jession_id
		 */
		UocMessage res = operServDu.isLogin(jsession_id);
		if (!"0000".equals(res.getRespCode())) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent(res.getContent());
			return message;
		}
		Map<String, Object> oper_info = (Map) res.getArgs().get("oper_info");
		String oper_no = (String) oper_info.get("oper_no");
		String province_code = (String) oper_info.get("province_code");
		String area_code = (String) oper_info.get("area_code");
		String accpet_depart_no = (String) oper_info.get("depart_no");
		String accpet_depart_name = (String) oper_info.get("depart_name");
		String accept_depart_type = (String) oper_info.get("channel_type");
		InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
		infoSaleOrderVo.setSale_order_no(sale_order_no);
		infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
		if (infoSaleOrderVo == null) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("无对应的销售订单");
			return message;
		}

		if (!"100".equals(infoSaleOrderVo.getOrder_state()) && !"102".equals(infoSaleOrderVo.getOrder_state())) {

			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("销售订单状态出错");
			return message;
		}
		if ("100".equals(infoSaleOrderVo.getOrder_state())) {
			infoSaleOrderVo.setOrder_state("102");
			infoSaleOrderServDu.updateInfoSaleOrder(infoSaleOrderVo);
		}
		// 销售订单拆分
		// 1根据销售订单号查询销售订单商品表
		InfoSaleOrderEditVo infoSaleOrderEditVo = new InfoSaleOrderEditVo();
		infoSaleOrderEditVo.setSale_order_no(sale_order_no);
		infoSaleOrderEditVo = infoSaleOrderEditServDu.getInfoSaleOrderEditBySaleOrderNo(infoSaleOrderEditVo);
		String json_info = "";
		Map<String, Object> json_info_map = new HashMap<String, Object>();
		if (infoSaleOrderEditVo != null) {
			json_info = infoSaleOrderEditVo.getJson_content();
			json_info_map = jsonToBeanServDu.jsonToMap(json_info);
		}

		List<String> serv_order_no_list = new ArrayList<String>();
		InfoSaleOrderOfrMapVo infoSaleOrderOfrMapVo = new InfoSaleOrderOfrMapVo();
		infoSaleOrderOfrMapVo.setSale_order_no(sale_order_no);
		List<InfoSaleOrderOfrMapVo> infoSaleOrderOfrMapVos = infoSaleOrderOfrMapServDu.queryInfoSaleOrderOfrMapBySaleOrderNo(infoSaleOrderOfrMapVo);
		for (InfoSaleOrderOfrMapVo vo : infoSaleOrderOfrMapVos) {
			// 生成商品订单
			InfoOfrOrderVo infoOfrOrderVo = new InfoOfrOrderVo();
			String ofr_order_no = getIdServDu.getId("createOfrOrderNo", province_code, area_code, sale_order_no);
			infoOfrOrderVo.setOfr_order_no(ofr_order_no);
			infoOfrOrderVo.setSale_order_no(sale_order_no);
			infoOfrOrderVo.setProvince_code(vo.getProvince_code());
			infoOfrOrderVo.setArea_code(vo.getArea_code());
			infoOfrOrderVo.setOfr_id(vo.getOfr_id());
			infoOfrOrderVo.setOfr_name(vo.getOfr_name());
			String part_month = StrUtil.splitStringFromOrderNo(ofr_order_no).get("part_month");
			infoOfrOrderVo.setPart_month(part_month);
			infoOfrOrderVo.setCancle_flag("1");
			infoOfrOrderVo.setOrder_state("100");
			String create_time = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
			infoOfrOrderVo.setCreate_time(create_time);
			boolean create_flag = infoOfrOrderServDu.createInfoOfrOrder(infoOfrOrderVo);
			if (!create_flag) {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("创建订单失败");
				throw new UocException();
			}
			// 查询商品对应服务的接口 生成服务订单 根据商品ID ofr_id(调接口)(待补充) TODO

			List<InfoServiceOrderVo> infoServiceOrderVos = new ArrayList<InfoServiceOrderVo>();
			// for(InfoServiceOrderVo serviceOrderVo :infoServiceOrderVos){
			// 查询业务拆分规则表
			// OrdModOperSplitRuleVo ordModOperSplitRuleVo =new
			// OrdModOperSplitRuleVo();
			// ordModOperSplitRuleVo.setSource_oper_code(serviceOrderVo.getOper_code());
			// List<OrdModOperSplitRuleVo> ordModOperSplitRuleVos
			// =ordModOperSplitRuleServDu.queryOrdModOperSplitRuleByOperCode(ordModOperSplitRuleVo);
			// if(ordModOperSplitRuleVos !=null &&
			// ordModOperSplitRuleVos.size()==1){
			// serviceOrderVo.setOper_code(ordModOperSplitRuleVos.get(0).getTarget_oper_code());
			// serviceOrderVo.setOper_name(ordModOperSplitRuleVos.get(0).getTarget_oper_name());
			// infoServiceOrderServDu.createInfoServiceOrder(serviceOrderVo);
			// serv_order_no_list.add(serviceOrderVo.getServ_order_no());
			// //UocMessage result =调用BASE0003订单模板获取服务取出订单模板 需要参数 order_no
			// order_type订单类型:100销售订单，101服务订单
			// query_type（100入参模板、101出参模板、102销售订单模板）
			// UocMessage result
			// =ordModFunctionServDu.queryOrdMod(serviceOrderVo.getServ_order_no(),
			// "100", "101");
			// String mod_code ="";
			// if("0000".equals(result.getRespCode())){
			// mod_code =(String)result.getArgs().get("mod_code");
			// logger.info("------mod_code---------"+mod_code);
			// }else{
			// logger.info("---------"+result.getContent());
			// return result;
			// }
			//
			// //BASE0005根据订单模板入库服务，订单类型填服务订单 入参 mod_code order_type(101)
			// json_in(json_info)
			// OrdModVo ordModVo =new OrdModVo();
			// ordModVo.setMod_code(mod_code);
			// ordModVo.setJson_in(json_info);
			// ordModVo.setOrder_type("101");
			// ordModVo.setOrder_no(serviceOrderVo.getServ_order_no());
			// result =ordModFunctionServDu.insertByOrdMod(ordModVo);
			// if(!"0000".equals(result.getRespCode())){
			// logger.info("-----入库失败-------");
			// logger.info("---------"+result.getContent());
			// return result;
			// }
			// //调用启动流程启动服务BASE0011，oper_type填100
			// // result
			// =startProcessServDu.startProcess(serviceOrderVo.getServ_order_no(),
			// "100");
			// // if("error".equals(result.getType().toString())){
			// // logger.info("-----流程启动服务失败-------");
			// // logger.info("---------"+result.getContent());
			// // return result;
			// // }
			// }else if(ordModOperSplitRuleVos !=null &&
			// ordModOperSplitRuleVos.size()>1){
			// for(OrdModOperSplitRuleVo
			// ordModOperSplitRule:ordModOperSplitRuleVos){
			// //生成对应条数的服务订单
			// InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
			// String serv_order_no =getIdServDu.getId("createServiceOrderNo",
			// province_code, area_code,"");
			// infoServiceOrderVo.setServ_order_no(serv_order_no);
			// infoServiceOrderVo.setSale_order_no(vo.getSale_order_no());
			// infoServiceOrderVo.setOfr_order_no(ofr_order_no);
			// infoServiceOrderVo.setProvince_code(vo.getProvince_code());
			// infoServiceOrderVo.setArea_code(vo.getArea_code());
			// infoServiceOrderVo.setPart_month(part_month);
			// infoServiceOrderVo.setOper_code(ordModOperSplitRule.getTarget_oper_code());
			// infoServiceOrderVo.setOper_name(ordModOperSplitRule.getTarget_oper_name());
			// infoServiceOrderVo.setAcc_nbr(vo.getAcc_nbr());
			// infoServiceOrderVo.setAccept_oper_no(oper_no);
			// infoServiceOrderVo.setAccept_depart_no(accpet_depart_no);
			// infoServiceOrderVo.setAccept_depart_name(accpet_depart_name);
			// infoServiceOrderVo.setAccept_depart_type(accept_depart_type);
			// infoServiceOrderVo.setCancle_flag("1");
			// infoServiceOrderVo.setOrder_state("100");
			// infoServiceOrderVo.setCreate_time(create_time);
			// infoServiceOrderServDu.createInfoServiceOrder(infoServiceOrderVo);
			//
			// //UocMessage result =调用BASE0003订单模板获取服务取出订单模板 需要参数 order_no
			// order_type订单类型:100销售订单，101服务订单
			// query_type（100入参模板、101出参模板、102销售订单模板）
			// UocMessage result
			// =ordModFunctionServDu.queryOrdMod(serv_order_no, "100", "101");
			// String mod_code ="";
			// if("0000".equals(result.getRespCode())){
			// mod_code =(String)result.getArgs().get("mod_code");
			// logger.info("------mod_code---------"+mod_code);
			// }else{
			// return result;
			// }
			//
			// //BASE0005根据订单模板入库服务，订单类型填服务订单 入参 mod_code order_type(101)
			// json_in(json_info)
			// OrdModVo ordModVo =new OrdModVo();
			// ordModVo.setMod_code(mod_code);
			// ordModVo.setJson_in(json_info);
			// ordModVo.setOrder_type("101");
			// ordModVo.setOrder_no(serv_order_no);
			// result =ordModFunctionServDu.insertByOrdMod(ordModVo);
			// if(!"0000".equals(result.getRespCode())){
			// logger.info("-----入库失败-------");
			// logger.info("---------"+result.getContent());
			// return result;
			// }
			// }
			// }
			// }
		}

		// 2根据销售订单号查询销售订单业务表
		InfoSaleOrderServMapVo infoSaleOrderServMapVo = new InfoSaleOrderServMapVo();
		infoSaleOrderServMapVo.setSale_order_no(sale_order_no);
		List<InfoSaleOrderServMapVo> infoSaleOrderServMapVos = infoSaleOrderServMapServDu.queryInfoSaleOrderServMapBySaleOrderNo(infoSaleOrderServMapVo);

		if (infoSaleOrderServMapVos != null && infoSaleOrderServMapVos.size() > 0) {
			// 先生成一条虚拟的商品订单
			InfoOfrOrderVo infoOfrOrderVo = new InfoOfrOrderVo();
			String ofr_order_no = getIdServDu.getId("createOfrOrderNo", province_code, area_code, sale_order_no);
			infoOfrOrderVo.setOfr_order_no(ofr_order_no);
			infoOfrOrderVo.setSale_order_no(sale_order_no);
			infoOfrOrderVo.setProvince_code(infoSaleOrderServMapVos.get(0).getProvince_code());
			infoOfrOrderVo.setArea_code(infoSaleOrderServMapVos.get(0).getArea_code());
			infoOfrOrderVo.setOfr_id("00000");
			infoOfrOrderVo.setOfr_name("00000");
			String part_month = StrUtil.splitStringFromOrderNo(ofr_order_no).get("part_month");
			infoOfrOrderVo.setPart_month(part_month);
			infoOfrOrderVo.setCancle_flag("1");
			infoOfrOrderVo.setOrder_state("100");
			String create_time = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
			infoOfrOrderVo.setCreate_time(create_time);
			boolean create_flag = infoOfrOrderServDu.createInfoOfrOrder(infoOfrOrderVo);
			if (!create_flag) {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("创建订单失败");
				throw new UocException();
			}

			for (InfoSaleOrderServMapVo vo : infoSaleOrderServMapVos) {
				// 生成对应条数的服务订单
				InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
				String serv_order_no = getIdServDu.getId("createServiceOrderNo", province_code, area_code, sale_order_no);
				infoServiceOrderVo.setServ_order_no(serv_order_no);
				infoServiceOrderVo.setSale_order_no(vo.getSale_order_no());
				infoServiceOrderVo.setOfr_order_no(ofr_order_no);
				infoServiceOrderVo.setProvince_code(vo.getProvince_code());
				infoServiceOrderVo.setArea_code(vo.getArea_code());
				infoServiceOrderVo.setPart_month(part_month);
				infoServiceOrderVo.setOper_code(vo.getOper_code());
				infoServiceOrderVo.setOper_name(vo.getOper_name());
				infoServiceOrderVo.setAcc_nbr(vo.getAcc_nbr());
				infoServiceOrderVo.setAccept_oper_no(oper_no);
				infoServiceOrderVo.setAccept_depart_no(accpet_depart_no);
				infoServiceOrderVo.setAccept_depart_name(accpet_depart_name);
				infoServiceOrderVo.setCancle_flag("1");
				infoServiceOrderVo.setOrder_state("100");
				infoServiceOrderVo.setTele_type(vo.getTele_type());
				infoServiceOrderVo.setCreate_time(create_time);
				infoServiceOrderVo.setAccept_depart_type(accept_depart_type);
				create_flag = infoServiceOrderServDu.createInfoServiceOrder(infoServiceOrderVo);
				if (!create_flag) {
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("创建订单失败");
					throw new UocException(message);
				}
				serv_order_no_list.add(infoServiceOrderVo.getServ_order_no());

				// TODO3 订单模板获取 (加如循环)
				// UocMessage result =调用BASE0003订单模板获取服务取出订单模板 需要参数 order_no
				// order_type订单类型:100销售订单，101服务订单
				// query_type（100入参模板、101出参模板、102销售订单模板）
				UocMessage result = ordModFunctionServDu.queryOrdMod(serv_order_no, "100", "101");
				String mod_code = "";
				if ("0000".equals(result.getRespCode())) {
					mod_code = (String) result.getArgs().get("mod_code");
					logger.info("------mod_code---------" + mod_code);
				} else {
					throw new UocException(result);
				}

				OrdModAttrDefineVo ordModAttrDefineVo = new OrdModAttrDefineVo();
				ordModAttrDefineVo.setMod_code(mod_code);
				List<OrdModAttrDefineVo> ordModAttrDefineVos = ordModAttrDefineServDu.queryOrdModAttrDefineByModCode(ordModAttrDefineVo);
				for (OrdModAttrDefineVo ordModAttrDefine : ordModAttrDefineVos) {
					String attr_name = ordModAttrDefine.getAttr_name();
					String attr_type = ordModAttrDefine.getAttr_type();
					String isAllowNull = ordModAttrDefine.getIs_allow_null();

					Object obj = json_info_map.get(attr_name);
					boolean flag = false;
					// 校验数据类型是否正确
					if ("100".equals(attr_type)) {
						flag = obj instanceof Integer;
					} else if ("101".equals(attr_type)) {
						flag = obj instanceof String;
					} else if ("102".equals(attr_type)) {
						flag = obj instanceof Double;
					} else if ("103".equals(attr_type)) {
						flag = obj instanceof Date;
					} else if ("200".equals(attr_type)) {
						flag = obj instanceof JSONObject;
					} else if ("201".equals(attr_type)) {
						flag = obj instanceof String[];
					}

					// 校验是否可空
					if ("0".equals(isAllowNull)) {
						if (obj == null) {
							flag = false;
						}
					}

					logger.info("-----订单确认参数校验-------");
					logger.info("-----" + attr_name + "-------");
					logger.info("-----" + attr_type + "-------");
					logger.info("--校验结果---" + flag + "-------");
					if (!flag) {
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("参数类型校验不通过");
						throw new UocException(message);
					}
				}
				// BASE0005根据订单模板入库服务，订单类型填服务订单 入参 mod_code order_type(101)
				// json_in(json_info)
				OrdModVo ordModVo = new OrdModVo();
				ordModVo.setMod_code(mod_code);
				ordModVo.setJson_in(json_info);
				ordModVo.setOrder_type("101");
				ordModVo.setOrder_no(serv_order_no);
				ordModVo.setJsession_id(jsession_id);
				result = ordModFunctionServDu.insertByOrdMod(ordModVo);
				if (!"0000".equals(result.getRespCode())) {
					logger.info("-----入库失败-------");
					logger.info("---------" + result.getContent());
					throw new UocException(result);
				}
						}

		}

		// 生成交付订单
		// InfoDeliverOrderVo infoDeliverOrderVo =new InfoDeliverOrderVo();
		// String exprss_flag =infoSaleOrderVo.getExpress_flag();
		// if("10".equals(exprss_flag)){
		// String deliver_order_no =getIdServDu.getId("createDeliverOrderNo",
		// province_code, area_code,sale_order_no);
		// infoDeliverOrderVo.setDeliver_order_no(deliver_order_no);
		// infoDeliverOrderVo.setAccept_depart_name(infoSaleOrderVo.getAccept_depart_name());
		// infoDeliverOrderVo.setAccept_depart_no(infoSaleOrderVo.getAccept_depart_no());
		// infoDeliverOrderVo.setAccept_oper_no(infoSaleOrderVo.getAccept_oper_no());
		// infoDeliverOrderVo.setArea_code(area_code);
		// infoDeliverOrderVo.setPart_month(infoSaleOrderVo.getPart_month());
		// infoDeliverOrderVo.setProvince_code(province_code);
		// infoDeliverOrderVo.setDeliver_state("100");
		// String create_time
		// =DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
		// infoDeliverOrderVo.setCreate_time(create_time);
		// infoDeliverOrderVo.setSale_order_no(sale_order_no);
		// boolean flag =infoDeliverOrderServDu.create(infoDeliverOrderVo);
		// if(!flag){
		// logger.error("-------------生成交付订单失败---------------");
		// message.setRespCode(RespCodeContents.SERVICE_FAIL);
		// message.setContent("生成交付订单失败");
		// throw new UocException(message);
		// }
		// }

		// 调用启动流程启动服务BASE0011，oper_type填100
		UocMessage processRes = startProcessServDu.startProcess(sale_order_no, "100", jsession_id);
		if (!"0000".equals(processRes.getRespCode())) {
			logger.error("-------------流程启动失败---------------");
			throw new UocException(processRes);
		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("销售订单确认成功");
		message.addArg("serv_order_no_list", serv_order_no_list);
		return message;
	}
	/**
	 * 销售订单提交UOC0019(消息队列)
	 * @param paraVo
	 * @return
	 */
	public UocMessage submitSaleOrderForActivemq(String  json_in) throws Exception{
		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		ParaVo vo =new ParaVo();
		String jsession_id =(String) map.get("jsession_id");
		String sale_order_no =(String) map.get("sale_order_no");
		String all_json_info =(String) map.get("all_json_info");
		String manual_flag =(String) map.get("manual_flag");
		String flow_type =(String) map.get("flow_type");
		Map<String,String> action_code =(Map) map.get("action_code");
		List<String> serv_order_no_list =(List) map.get("serv_order_no_list");
		vo.setJsession_id(jsession_id);
		vo.setSale_order_no(sale_order_no);
		vo.setAll_json_info(all_json_info);
		vo.setManual_flag(manual_flag);
		vo.setFlow_type(flow_type);
		vo.setAction_code(action_code);
		vo.setServ_order_no_list(serv_order_no_list);
		UocMessage message =submitSaleOrder(vo);

		return message;
	}

	/**
	 * 销售订单提交UOC0019
	 * @param paraVo
	 * @return
	 */
	@Override
	public UocMessage submitSaleOrder(ParaVo paraVo) throws Exception{
		logger.info("rest-----------sumbitSaleOrder");
		UocMessage message =new UocMessage();
			//			Map<String, Object> map =jsonToBeanServDu.jsonToMap(jsonStr);
			String jsession_id =paraVo.getJsession_id();
			String sale_order_no =paraVo.getSale_order_no();
			String manual_flag =paraVo.getManual_flag();
			String all_json_info =paraVo.getAll_json_info();
			String flow_type =paraVo.getFlow_type();
			Map<String,String> action_code =paraVo.getAction_code();
			List<String> serv_order_no_list =paraVo.getServ_order_no_list();
			getBeanDu();
			/*
			 * 1、调用BASE0008记录接口日志 需要参数 service_code json_in json_out（待补充）
			 */

			if(sale_order_no ==null || "".equals(sale_order_no)){
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("sale_order_no不能为空");
				return message;
			}
			Map<String,Object> oper_info =new HashMap<String,Object>();
			if("0".equals(manual_flag)){
				/*
				 * 校验jession_id
				 */
				UocMessage res =operServDu.isLogin(jsession_id);
				if(!"0000".equals(res.getRespCode())){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent(res.getContent());
					return message;
				}
				oper_info =(Map)res.getArgs().get("oper_info");

			}
			String oper_no =(String)oper_info.get("oper_no");
			String province_code =(String)oper_info.get("province_code");
			String area_code =(String)oper_info.get("area_code");
			//根据销售订单号查询销售订单，判断当前状态是否为209，不是则报错
			InfoSaleOrderVo infoSaleOrderVo =new InfoSaleOrderVo();
			infoSaleOrderVo.setSale_order_no(sale_order_no);
			infoSaleOrderVo =infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			if(!"201".equals(infoSaleOrderVo.getOrder_state())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("销售订状态异常");
				return message;
			}
			if("0".equals(flow_type) || "1".equals(flow_type)){
				//调用BASE0022服务校验是否满足流转要求,flow_type传0
				UocMessage checkRes =checkProcessServDu.checkProcess(infoSaleOrderVo.getProc_inst_id(), sale_order_no, flow_type, action_code);
				if(!"0000".equals(checkRes.getRespCode())){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("流转要求检验异常");
					return message;
				}
			}
			//如果传入的serv_order_no_list为空，则根据销售订单号查询出对应的服务订单列表，否则取传入的服务订单号列表
			List<InfoServiceOrderVo> infoServiceOrderVos =new ArrayList<InfoServiceOrderVo>();
			if(serv_order_no_list ==null){
				InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
				infoServiceOrderVo.setSale_order_no(sale_order_no);
			    infoServiceOrderVos =infoServiceOrderServDu.queryInfoServiceOrderBySaleOrderNo(infoServiceOrderVo);
			}else{
				for(String serv_order_no:serv_order_no_list){
					InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
					infoServiceOrderVo.setServ_order_no(serv_order_no);
					infoServiceOrderVos.add(infoServiceOrderVo);
				}
			}

			for(InfoServiceOrderVo vo :infoServiceOrderVos){
				//依次针对每个程启动服务BASE0011  入参order_no,order_type
				String ser_order_no =vo.getServ_order_no();
				UocMessage startProcessRes =startProcessServDu.startProcess(ser_order_no, "101",jsession_id);
				if(!"0000".equals(startProcessRes.getRespCode())){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("启动流程失败");
					return message;
				}

			}
			if("0".equals(flow_type) || "1".equals(flow_type)){
				//调销售订单流转
				UocMessage processCirculationRes =processCirculationServDu.processCirculation(sale_order_no, "100", "0", action_code,"");
				if(!"0000".equals(processCirculationRes.getRespCode())){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("流程流转失败");
					return message;
				}
			}
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("销售订单提交成功");
			return message;

	}
	/**
	 * 销售订单处理UOC0020（消息队列调用）
	 * @param String
	 * @return
	 */
	public UocMessage dealSaleOrderForActivemq(String json_in) throws Exception{


		logger.info("===============dealSaleOrderForActivemq jsonToBeanServDu start======================");

		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		logger.info("===============dealSaleOrderForActivemq jsonToBeanServDu end======================");
		logger.info("======================jsonToBeanServDu after action======================="+jsonToBeanServDu);
		ParaVo vo =new ParaVo();
		String jsession_id =(String) map.get("jsession_id");
		String sale_order_no =(String) map.get("sale_order_no");
		String flow_type =(String) map.get("flow_type");
		Map action_code = (Map) map.get("action_code");
		String manual_flag =(String) map.get("manual_flag");
		List<String> serv_order_no_list =(List) map.get("serv_order_no_list");
		vo.setJsession_id(jsession_id);
		vo.setSale_order_no(sale_order_no);
		vo.setFlow_type(flow_type);
		vo.setJson_info(json_in);
		vo.setAction_code(action_code);
		vo.setManual_flag(manual_flag);
		vo.setServ_order_no_list(serv_order_no_list);
		UocMessage message =dealSaleOrder(vo);

		return message;
	}
	/**
	 * 销售订单处理UOC0020
	 * @param paraVo
	 * @return
	 */
	@Override
	public UocMessage dealSaleOrder(ParaVo paraVo) throws Exception{
		logger.info("rest-----------dealSaleOrder");
		UocMessage message =new UocMessage();
		List<Object> argsList= new ArrayList<Object>();

			String jsession_id =paraVo.getJsession_id();
			String sale_order_no =paraVo.getSale_order_no();
			String flow_type =paraVo.getFlow_type();
			List<String> serv_order_no_list =paraVo.getServ_order_no_list();
			Map<String,String> action_code =paraVo.getAction_code();
			String manual_flag =paraVo.getManual_flag();
			getBeanDu();
			/*
			 * 1、调用BASE0008记录接口日志 需要参数 service_code json_in json_out（待补充）
			 */
			if(sale_order_no ==null || "".equals(sale_order_no)){
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("sale_order_no不能为空");
				return message;
			}

			/*
			 * manual_flag为0 ,校验jession_id
			 */
			Map<String, Object> oper_info =new HashMap<String, Object>();
			if("0".equals(manual_flag)){
				UocMessage res =operServDu.isLogin(jsession_id);
				if(!"0000".equals(res.getRespCode())){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent(res.getContent());
					return message;
				}
				oper_info =(Map<String,Object>)res.getArgs().get("oper_info");
			}
			String oper_no =(String)oper_info.get("oper_no");
			String province_code =(String)oper_info.get("province_code");
			String area_code =(String)oper_info.get("area_code");
//			String role_id =(String)oper_info.get("role_id");
//			List<String> role_id_list =StrUtil.strToList(role_id);

			InfoSaleOrderVo infoSaleOrderVo =new InfoSaleOrderVo();
			infoSaleOrderVo.setSale_order_no(sale_order_no);
			infoSaleOrderVo =infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			if(infoSaleOrderVo ==null){

				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("无对应的销售订单");
				return message;
			}
			if(!"201".equals(infoSaleOrderVo.getOrder_state())){

				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("销售订单状态异常");
				return message;
			}
			//查出流程实例，然后通过流程实例、环节(调BASE0012获取)查询人工任务实例表
			String proc_inst_id =infoSaleOrderVo.getProc_inst_id();
			//环节(调BASE0012获取)
			UocMessage result =findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(proc_inst_id, sale_order_no);
			if(!"0000".equals(result.getRespCode())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("环节调用失败");
				return message;
			}

			String tache_code=(String)result.getArgs().get("current_tache");
			String current_task =(String)result.getArgs().get("current_task");
			String current_task_name =(String)result.getArgs().get("current_task_name");

			argsList.add(result.getArgs());

			//调用BASE0022服务校验是否满足流转要求,flow_type传0
			UocMessage checkRes =checkProcessServDu.checkProcess(infoSaleOrderVo.getProc_inst_id(), sale_order_no, "0", action_code);
			if(!"0000".equals(checkRes.getRespCode())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("流转要求检验异常");
				return message;
			}
			//屏蔽原有任务校验逻辑

			if(!"3".equals(flow_type)){
				 List<InfoServiceOrderVo> infoServiceOrderVos =new ArrayList<InfoServiceOrderVo>();
				if(serv_order_no_list ==null){
					InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
					infoServiceOrderVo.setSale_order_no(sale_order_no);
					infoServiceOrderVos=infoServiceOrderServDu.queryInfoServiceOrderBySaleOrderNo(infoServiceOrderVo);
				}else{
					for(String serv_order_no:serv_order_no_list){
						InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
						infoServiceOrderVo.setServ_order_no(serv_order_no);
						infoServiceOrderVos.add(infoServiceOrderVo);
					}
				}
					//依次针对每个服务订单调用服务订单处理服务UOC0008，oper_type传101，flow_type传2,依次判断每个服务成功后才调用下一个服务；
				for(InfoServiceOrderVo vo:infoServiceOrderVos){
					ParaVo para =new ParaVo();
					para.setServ_order_no(vo.getServ_order_no());
					para.setJsession_id(paraVo.getJsession_id());
					para.setOper_type("101");
					para.setFlow_type("2");
					para.setManual_flag(manual_flag);
					String oper_info_json =jsonToBeanServDu.mapToJson(oper_info);
					para.setJson_info(oper_info_json);
				// UocMessage res
				// =serviceOrderServDu.syncNoneServiceOrder(para);
				// if(!"0000".equals(res.getRespCode())){
						//如果上面有报错，则调用BASE0013转人工任务服务
					// changeToArtificialServiceServDu.changeToArtificialService(sale_order_no,
					// "100","");
					// message.setRespCode(RespCodeContents.SERVICE_FAIL);
					// message.setContent("服务订单处理异常");
					// return message;
				// }
					argsList.add(result.getArgs());
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
			message.setContent("销售订单处理成功");
			message.addArg("argsList",argsList);
			return message;
	}


	/**
	 * 销售订批量修改信息UOC0021入参组装
	 * @param json_in
	 * @return
	 * @throws Exception
	 */
	public UocMessage updateSaleOrderBatchForAbilityPlatform(String  json_in) throws Exception{

		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		ParaVo vo =new ParaVo();

		String jsession_id =(String) map.get("jsession_id");
		String sale_order_no =(String) map.get("sale_order_no");
		String manual_flag =(String) map.get("manual_flag");
		String flow_type =(String) map.get("flow_type");
		@SuppressWarnings("unchecked")
		Map<String,String> action_code = (Map<String, String>) map.get("action_code");
		String value=(String) map.get("list");
		JSONObject json = JSONObject.fromObject(value.substring(1, value.length()-1));
		@SuppressWarnings("unchecked")
		List<String> serv_order_no_list=(List<String>) json.get("serv_order_no_list");
		String json_info_list=(String) json.get("json_info_list");
		vo.setJsession_id(jsession_id);
		vo.setSale_order_no(sale_order_no);
		vo.setManual_flag(manual_flag);
		vo.setFlow_type(flow_type);
		vo.setAction_code(action_code);
		vo.setServ_order_no_list(serv_order_no_list);
		vo.setJson_info_list(json_info_list);
		UocMessage message =updateSaleOrderBatch(vo);
		return message;
	}
	/**
	 * 销售订批量修改信息UOC0021
	 * @param paraVo
	 * @return
	 */
	@Override
	public UocMessage updateSaleOrderBatch(ParaVo paraVo) {
		logger.info("rest-----------updateSaleOrder");
		getBeanDu();
		UocMessage message =new UocMessage();
		try {
			String jsession_id =paraVo.getJsession_id();
			String sale_order_no =paraVo.getSale_order_no();
			String manual_flag =paraVo.getManual_flag();
			String flow_type =paraVo.getFlow_type();
			Map<String,String> action_code =paraVo.getAction_code();
			List<String> serv_order_no_list =paraVo.getServ_order_no_list();
			String json_info_list =paraVo.getJson_info_list();
			JSONArray  json_info_list_array =JSONArray.fromObject(json_info_list);
			/*
			 * 1、调用BASE0008记录接口日志 需要参数 service_code json_in json_out（待补充）
			 */
			/*
			 * 校验jession_id
			 */
			Map<String,Object> oper_info =new HashMap<String,Object>();
			if("0".equals(manual_flag)){
				UocMessage res =operServDu.isLogin(jsession_id);
				if(!"0000".equals(res.getRespCode())){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent(res.getContent());
					return message;
				}
				oper_info =(Map)res.getArgs().get("oper_info");
			}
			String oper_no =(String)oper_info.get("oper_no");
			String province_code =(String)oper_info.get("province_code");
			String area_code =(String)oper_info.get("area_code");
			//根据销售订单号查询当前状态，以及对应的服务订单号list，当前状态不为201则报错
			InfoSaleOrderVo infoSaleOrderVo =new InfoSaleOrderVo();
			infoSaleOrderVo.setSale_order_no(sale_order_no);
			infoSaleOrderVo =infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			if(infoSaleOrderVo ==null){

				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("无对应的销售订单");
				return message;
			}
			if(!"201".equals(infoSaleOrderVo.getOrder_state())){

				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("销售订单状态异常");
				return message;
			}
			//查出流程实例，然后通过流程实例、环节(调BASE0012获取)查询人工任务实例表
			String proc_inst_id =infoSaleOrderVo.getProc_inst_id();

			if("0".equals(flow_type) || "1".equals(flow_type) || "3".equals(flow_type) ){
				//调用BASE0022服务校验是否满足流转要求,flow_type传0
				UocMessage checkRes =checkProcessServDu.checkProcess(proc_inst_id, sale_order_no, flow_type, action_code);
				if(!"0000".equals(checkRes.getRespCode())){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("流转要求检验异常");
					return message;
				}
			}
			//环节(调BASE0012获取)
			UocMessage result =findMyPersonalTaskServDu.findMyPersonalTaskByInstanceId(proc_inst_id, sale_order_no);
			if(!"0000".equals(result.getRespCode())){
				return result;
			}
			String tache_code=(String)result.getArgs().get("tache_code");
			String current_task =(String)result.getArgs().get("current_task");
			String current_task_name =(String)result.getArgs().get("current_task_name");

			InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
			infoServiceOrderVo.setSale_order_no(sale_order_no);
			List<InfoServiceOrderVo> infoServiceOrderVos =infoServiceOrderServDu.queryInfoServiceOrderBySaleOrderNo(infoServiceOrderVo);

			//屏蔽原有任务校验逻辑

			//如果flow_type=0、1、3，调用BASE0022服务校验是否满足流转要求
			if("0".equals(flow_type) || "1".equals(flow_type) || "3".equals(flow_type)){
				UocMessage checkRes =checkProcessServDu.checkProcess(infoSaleOrderVo.getProc_inst_id(), sale_order_no, flow_type, null);
				if(!"0000".equals(checkRes.getRespCode())){
					if("1".equals(manual_flag)){
						ProcInstTaskInstVo procInstTaskInstVo=new ProcInstTaskInstVo();
						procInstTaskInstVo.setOrder_no(sale_order_no);
						procInstTaskInstVo.setOrder_type("100");
						changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "");
					}
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("流转要求检验异常");
					return message;
				}
			}
			//5、	根据传入的服务订单号List，与上面的集合做比较，不在集合中则报错
			for(InfoServiceOrderVo vo:infoServiceOrderVos){
				String serNo =vo.getServ_order_no();
				logger.info(serv_order_no_list.toString());
				if(!serv_order_no_list.contains(serNo)){
					//如果上面有报错，并且manual_flag为1，则调用BASE0013转人工任务服务
					if("1".equals(manual_flag)){
						ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
						procInstTaskInstVo.setOrder_no(sale_order_no);
						procInstTaskInstVo.setOrder_type("100");
						changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "");
					}
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("服务订单号List与实际数据不符");
					return message;
				}

				try{
					//1）调用BASE0003订单模板获取服务取出订单入参模板，这里入参order_type填101，query_type填100
					UocMessage result_mod_code =ordModFunctionServDu.queryOrdMod(serNo, "100", "101");
					//2）通过传入json_info_list中对应服务订单的json_info 和上面取出的订单模板信息调用BASE0005根据订单模板入库服务，订单类型填服务订单
					if("0000".equals(result_mod_code.getRespCode())){
						String mod_code =(String)result_mod_code.getArgs().get("mod_code");
						logger.info("--------mod_code---------"+mod_code);
						OrdModVo ordModVo =new OrdModVo();
						ordModVo.setMod_code(mod_code);
						int i =serv_order_no_list.indexOf(vo.getServ_order_no());
						logger.info("++++++++"+json_info_list_array.get(i).toString());
						ordModVo.setJson_in(json_info_list_array.get(i).toString());
						ordModVo.setOrder_type("101");
						ordModVo.setOrder_no(serNo);
						ordModVo.setJsession_id(jsession_id);
						UocMessage insertByOrdModRes =ordModFunctionServDu.insertByOrdMod(ordModVo);
						if(!"0000".equals(insertByOrdModRes.getRespCode())){
							logger.error("-----"+insertByOrdModRes.toString());
							message.setRespCode(RespCodeContents.SERVICE_FAIL);
							message.setContent("服务订单入库失败");
							return message;
						}
					}else{
						return result_mod_code;
					}
				}catch(Exception e){
					e.printStackTrace();
					//如果上面有报错，并且manual_flag为1，则调用BASE0013转人工任务服务
					if("1".equals(manual_flag)){
						ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
						procInstTaskInstVo.setOrder_no(sale_order_no);
						procInstTaskInstVo.setOrder_type("100");
						changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "");
					}
					message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
					message.setContent("服务订单入库异常");
					return message;
				}
				//每个服务订单调用服务订单修改服务UOC0009，oper_type传101，flow_type传2依次判断每个服务成功后才调用下一个服务；
				//				ParaVo para =new ParaVo();
				//				para.setJsession_id(jsession_id);
				//				para.setJson_info(json_info);
				//				para.setOper_type("101");
				//				para.setFlow_type("2");
				//				para.setServ_order_no(serNo);
				//				UocMessage res =serviceOrderServDu.changeServiceOrder(para);
				//				if("error".equals(res.getType().toString())){
				//					if("1".equals(manual_flag)){
				//						//如果上面有报错，并且manual_flag为1，则调用BASE0013转人工任务服务
				//						changeToArtificialServiceServDu.changeToArtificialService(oper_info,sale_order_no, "100", current_task, current_task_name, tache_code, proc_inst_id, "");
				//					}
				//
				//					message.setType(UocMessage.Type.error);
				//				 	message.setContent("服务订单修改异常");
				//				 	return message;
				//				}

			}
			//根据接口传入的流程流转参数不同
			try{
				if("0".equals(flow_type) || "1".equals(flow_type)){
					UocMessage res =new UocMessage();
					if("0".equals(flow_type)){
						res =processCirculationServDu.processCirculation(sale_order_no, "100", flow_type, null,"");
					}else{
						res =processCirculationServDu.processCirculation(sale_order_no, "100", flow_type,action_code,"");
					}
					// if(!"0000".equals(res.getRespCode()) &&
					// "1".equals(manual_flag)){
					//
					// changeToArtificialServiceServDu.changeToArtificialService(sale_order_no,
					// "100", "");
					// }
				}
			}catch(Exception e){
				e.printStackTrace();
				// if("1".equals(manual_flag)){
				//
				// changeToArtificialServiceServDu.changeToArtificialService(sale_order_no,
				// "100", "");
				// }
				// message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
				// message.setContent("流程流转异常");
				return message;
			}

			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("销售订单批量修改信息成功");
			return message;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.toString());
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("销售订单批量修改信息异常");
			return message;
		}


	}

	/**
	 * 销售订单处理UOC0022（消息队列调用）
	 * @param String
	 * @return
	 */
	public UocMessage createInfoPayOrderForActivemq(String json_in) throws Exception{
		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		ParaVo vo =new ParaVo();
		String jsession_id =(String) map.get("jsession_id");
		String sale_order_no =(String) map.get("sale_order_no");
		vo.setJsession_id(jsession_id);
		vo.setSale_order_no(sale_order_no);
		UocMessage message =createInfoPayOrder(vo);

		return message;
	}

	/**
	 * 生成支付订单UOC0022
	 * @param paraVo
	 * @return
	 */
	@Override
	public UocMessage createInfoPayOrder(ParaVo paraVo) throws Exception{
		logger.info("rest-----------createInfoPayOrder UOC0022");
		UocMessage message =new UocMessage();
		String sale_order_no =paraVo.getSale_order_no();
		String jsession_id =paraVo.getJsession_id();
		String current_task ="";
		String current_task_name ="";
		String tache_code ="";
		String proc_inst_id ="";
			if(infoSaleOrderServDu == null){
				infoSaleOrderServDu = (InfoSaleOrderServDu) ToolSpring.getBean("InfoSaleOrderServDu");
			}
			if(infoServiceOrderFeeServDu == null){
				infoServiceOrderFeeServDu = (InfoServiceOrderFeeServDu) ToolSpring.getBean("InfoServiceOrderFeeServDu");
			}
			if(infoSaleOrderFeeServDu == null){
				infoSaleOrderFeeServDu = (InfoSaleOrderFeeServDu) ToolSpring.getBean("InfoSaleOrderFeeServDu");
			}
			if(operServDu == null){
				operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
			}
			if(getIdServDu == null){
				getIdServDu = (GetIdServDu) ToolSpring.getBean("GetIdServDu");
			}
			if(infoPayOrderServDu == null){
				infoPayOrderServDu = (InfoPayOrderServDu) ToolSpring.getBean("InfoPayOrderServDu");
			}
			if(processCirculationServDu == null){
				processCirculationServDu = (ProcessCirculationServDu) ToolSpring.getBean("ProcessCirculationServDu");
			}
			/*
			 * 1、调用BASE0008记录接口日志 需要参数 service_code json_in json_out（待补充）
			 */
			/*
			 * 校验jession_id
			 */
			UocMessage res =operServDu.isLogin(jsession_id);
			if(!"0000".equals(res.getRespCode())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent(res.getContent());
				return message;
			}
			Map<String,Object> oper_info =(Map)res.getArgs().get("oper_info");
			String oper_no =(String)oper_info.get("oper_no");
			String province_code =(String)oper_info.get("province_code");
			String area_code =(String)oper_info.get("area_code");
			String depart_no =(String)oper_info.get("depart_no");
			String depart_name =(String)oper_info.get("depart_name");

			InfoSaleOrderVo infoSaleOrderVo =new InfoSaleOrderVo();
			infoSaleOrderVo.setSale_order_no(sale_order_no);
			infoSaleOrderVo =infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
			if(infoSaleOrderVo ==null){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("无对应的销售订单");
				return message;
			}
			proc_inst_id =infoSaleOrderVo.getProc_inst_id();
			if(!"10".equals(infoSaleOrderVo.getPay_flag())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("支付状态异常");
				return message;
			}

			if(!"201".equals(infoSaleOrderVo.getOrder_state())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("销售订单状态异常");
				return message;
			}
			InfoServiceOrderFeeVo infoServiceOrderFeeVo =new InfoServiceOrderFeeVo();
			infoServiceOrderFeeVo.setSale_order_no(sale_order_no);
			List<InfoServiceOrderFeeVo> infoServiceOrderFeeVos =infoServiceOrderFeeServDu.queryInfoServiceOrderFeeBySaleOrderNo(infoServiceOrderFeeVo);
			double total_fee =0;
			double discount_fee =0;
			double payed_fee =0;

			for(InfoServiceOrderFeeVo vo:infoServiceOrderFeeVos){
				total_fee += Double.parseDouble(vo.getTotal_fee());
				discount_fee += Double.parseDouble(vo.getDiscount_fee());
				payed_fee += Double.parseDouble(vo.getPayed_fee());

			}
			//创建销售订单费用信息表
			InfoSaleOrderFeeVo infoSaleOrderFeeVo =new InfoSaleOrderFeeVo();
			infoSaleOrderFeeVo.setSale_order_no(sale_order_no);
			infoSaleOrderFeeVo.setProvince_code(province_code);
			infoSaleOrderFeeVo.setArea_code(area_code);
			String part_month =StrUtil.splitStringFromOrderNo(sale_order_no).get("part_month");
			infoSaleOrderFeeVo.setPart_month(part_month);
			infoSaleOrderFeeVo.setTotal_fee(total_fee+"");
			infoSaleOrderFeeVo.setDiscount_fee(discount_fee+"");
			infoSaleOrderFeeVo.setPayed_fee(payed_fee+"");
			infoSaleOrderFeeVo.setPay_flag(infoSaleOrderVo.getPay_flag());
			String create_time =DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
			infoSaleOrderFeeVo.setCreate_time(create_time);

			boolean flag =infoSaleOrderFeeServDu.createInfoSaleOrderFee(infoSaleOrderFeeVo);
			if(!flag){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("创建订单失败");
				throw new UocException(message);
			}
			//创建支付订单
			InfoPayOrderVo infoPayOrderVo =new InfoPayOrderVo();
			String pay_order_no =getIdServDu.getId("createPayOrderNo", province_code, area_code,sale_order_no);
			infoPayOrderVo.setPay_order_no(pay_order_no);
			infoPayOrderVo.setRela_order_no(sale_order_no);
			infoPayOrderVo.setRela_order_type("100");
			infoPayOrderVo.setProvince_code(province_code);
			infoPayOrderVo.setArea_code(area_code);
			part_month =StrUtil.splitStringFromOrderNo(sale_order_no).get("part_month");
			infoPayOrderVo.setPart_month(part_month);
			infoPayOrderVo.setPay_fee(payed_fee+"");
			infoPayOrderVo.setPay_type(infoSaleOrderVo.getPay_type());
			infoPayOrderVo.setPay_state(infoSaleOrderVo.getPay_flag());
			infoPayOrderVo.setCreate_time(create_time);
			//根据jession_id获取操作信息、工号渠道等（待补充）
			infoPayOrderVo.setAccept_oper_no(oper_no);
			infoPayOrderVo.setAccept_depart_no(depart_no);
			infoPayOrderVo.setAccept_depart_name(depart_name);

			flag =infoPayOrderServDu.createInfoPayOrder(infoPayOrderVo);
			if(!flag){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("创建订单失败");
				throw new UocException(message);
			}
			//还要调用BASE0016服务进行销售订单的流程流转，oper_type传100，flow_type传0
			message =processCirculationServDu.processCirculation(sale_order_no, "100", "0", null,"");
			if(!"0000".equals(message.getRespCode())){
			ProcInstTaskInstVo procInstTaskInstVo = new ProcInstTaskInstVo();
			procInstTaskInstVo.setOrder_no(sale_order_no);
			procInstTaskInstVo.setOrder_type("100");
			changeToArtificialServiceServDu.changeToArtificialService(procInstTaskInstVo, "");
				throw new UocException(message);
			}

			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("生成支付订单成功");
			return message;
	}



	/**
	 * 销售订单收费结果通知 UOC0023入参组装
	 * @param json_in
	 * @return
	 * @throws Exception
	 */
	public UocMessage payForNotifyInfoSaleOrderForAbilityPlatform(String  json_in) throws Exception{

		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		ParaVo vo =new ParaVo();

		String jsession_id =(String) map.get("jsession_id");
		String sale_order_no =(String) map.get("sale_order_no");
		String real_pay_sn =(String) map.get("real_pay_sn");
		String pay_type =(String) map.get("pay_type");
		String pay_time =(String) map.get("pay_time");
		String pay_system_code =(String) map.get("pay_system_code");
		vo.setJsession_id(jsession_id);
		vo.setSale_order_no(sale_order_no);
		vo.setReal_pay_sn(real_pay_sn);
		vo.setPay_type(pay_type);
		vo.setPay_time(pay_time);
		vo.setPay_system_code(pay_system_code);
		UocMessage message =payForNotifyInfoSaleOrder(vo);
		return message;
	}

	/**
	 * 销售订单收费结果通知 UOC0023
	 * @param paraVo
	 * @return
	 */
	@Override
	public UocMessage payForNotifyInfoSaleOrder(ParaVo paraVo) throws Exception {
		logger.info("rest-----------payForNotifyInfoSaleOrder UOC0023");
		getBeanDu();
		UocMessage message = new UocMessage();

		String jsession_id = paraVo.getJsession_id();
		String sale_order_no = paraVo.getSale_order_no();
		String real_pay_sn = paraVo.getReal_pay_sn();
		String pay_type = paraVo.getPay_type();
		String pay_time = paraVo.getPay_time();
		String pay_system_code = paraVo.getPay_system_code();
		String deal_code = StringUtils.isNotEmpty(paraVo.getDeal_code()) ? paraVo.getDeal_code() : "";
		String deal_desc = StringUtils.isNotEmpty(paraVo.getDeal_desc()) ? paraVo.getDeal_desc() : "";
		String deal_system_no = StringUtils.isNotEmpty(paraVo.getDeal_system_no()) ? paraVo.getDeal_system_no() : "";
		String json_info_ext = "{\"jsession_id\":\"" + jsession_id + "\"}";
		/*
		 * 1、调用BASE0008记录接口日志 需要参数 service_code json_in json_out（待补充）
		 */
		/*
		 * 校验jession_id
		 */
		UocMessage res = operServDu.isLogin(jsession_id);
		if (!"0000".equals(res.getRespCode())) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent(res.getContent());
			return message;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> oper_info = (Map<String, Object>) res.getArgs().get("oper_info");
		String oper_no = (String)oper_info.get("oper_no");
		String province_code = (String)oper_info.get("province_code");

		InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
		infoSaleOrderVo.setSale_order_no(sale_order_no);
		infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
		if (infoSaleOrderVo == null) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("无对应的销售订单");
			return message;
		}
		if (!"10".equals(infoSaleOrderVo.getPay_flag())) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("支付状态异常");
			return message;
		}

		if (!"201".equals(infoSaleOrderVo.getOrder_state())) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("销售订单状态异常");
			return message;
		}
		// 调用BASE0022服务校验是否满足流转要求,flow_type传0
		UocMessage checkRes = checkProcessServDu.checkProcess(infoSaleOrderVo.getProc_inst_id(), sale_order_no, "0", null);
		if (!"0000".equals(checkRes.getRespCode())) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("流转要求检验异常");
			return message;
		}

		// 调用BASE0025进行任务校验
		UocMessage checkMessage = checkArtificialTaskServDu.checkArtificialTaskProcess(sale_order_no, "B00004", oper_info);
		if (!checkMessage.getRespCode().equals(RespCodeContents.SUCCESS)) {
			return checkMessage;
		}

		InfoPayOrderVo infoPayOrderVo = new InfoPayOrderVo();
		infoPayOrderVo.setRela_order_no(sale_order_no);
		infoPayOrderVo.setRela_order_type("100");
		infoPayOrderVo = infoPayOrderServDu.queryInfoPayOrderByRelaOrderNo(infoPayOrderVo);
		if (infoPayOrderVo == null) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("无关联支付订单");
			return message;
		}
		infoPayOrderVo.setPay_type(pay_type);
		infoPayOrderVo.setPay_state("102");
		infoPayOrderVo.setPay_time(pay_time);
		infoPayOrderVo.setReal_pay_sn(real_pay_sn);
		infoPayOrderVo.setPay_system_code(pay_system_code);
		boolean update_flag = infoPayOrderServDu.updateInfoPayOrder(infoPayOrderVo);
		if (!update_flag) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("更新订单失败");
			throw new UocException(message);
		}
		// 还要调用BASE0016服务进行销售订单的流程流转，oper_type传100，flow_type传0
		UocMessage processCirculationRes = processCirculationServDu.processCirculation(sale_order_no, "100", "0", null, json_info_ext);
		if (!"0000".equals(processCirculationRes.getRespCode())) {
			logger.error("销售订单的流程流转失败");
			throw new UocException(processCirculationRes);
		}

		// 更新人工任务实例表的任务状态为已处理，填写完成时间，还要记录人工任务操作记录表，记录传入的处理动作、处理描述、处理系统编码
		List<ProcInstTaskInstPo> list = procInstTaskInstServ.querytaskInstByOrderNoAndTacheCode(sale_order_no, "B00004");
		ProcInstTaskInstPo po = new ProcInstTaskInstPo();
		BeanUtils.copyProperties(list.get(0), po);
		String date = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
		po.setTask_state("102");
		po.setFinish_time(date);
		logger.info("---------更新人工任务实例表---------order_no=" + sale_order_no);
		boolean flagTem = procInstTaskInstServ.updateByOrderNo(po);
		if (!flagTem) {
			logger.error("更新人工任务实例表失败");
			// 抛出业务异常
			UocMessage uocExceptionMsg = new UocMessage();
			uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocExceptionMsg.setContent("更新人工任务实例表失败");
			throw new UocException(uocExceptionMsg);
		}

		ProcInstTaskDealRecordPo procInstTaskDealRecordPo = new ProcInstTaskDealRecordPo();
		String id = getIdServDu.getId("createLogId", province_code, "*", "");
		procInstTaskDealRecordPo.setId(id);
		procInstTaskDealRecordPo.setTask_id(list.get(0).getTask_id());
		procInstTaskDealRecordPo.setProvince_code(list.get(0).getProvince_code());
		procInstTaskDealRecordPo.setArea_code(list.get(0).getArea_code());
		procInstTaskDealRecordPo.setPart_month(list.get(0).getPart_month());
		procInstTaskDealRecordPo.setDeal_time(date);
		procInstTaskDealRecordPo.setDeal_oper_no(oper_no);
		procInstTaskDealRecordPo.setDeal_system_no(deal_system_no);
		procInstTaskDealRecordPo.setDeal_code(deal_code);
		procInstTaskDealRecordPo.setDeal_desc(deal_desc);
		procInstTaskDealRecordPo.setOrder_no(sale_order_no);
		procInstTaskDealRecordPo.setOrder_type(list.get(0).getOrder_type());
		procInstTaskDealRecordPo.setTache_code(list.get(0).getTache_code());
		procInstTaskDealRecordPo.setOper_code(list.get(0).getOper_code());
		procInstTaskDealRecordPo.setCreate_time(list.get(0).getCreate_time());
		procInstTaskDealRecordPo.setProd_code(list.get(0).getProd_code());
		logger.info("---------创建人工任务操作记录---------order_no=" + sale_order_no);
		boolean flag = procInstTaskDealRecordServ.create(procInstTaskDealRecordPo);
		if (!flag) {
			logger.error("创建人工任务操作记录失败");
			// 抛出业务异常
			UocMessage uocExceptionMsg = new UocMessage();
			uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocExceptionMsg.setContent("创建人工任务操作记录失败");
			throw new UocException(uocExceptionMsg);

		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("销售订单收费通知成功");
		return message;

	}
	/**
	 * 订单撤单 UOC0007入参组装
	 * @param json_in
	 * @return
	 * @throws Exception
	 */
	public UocMessage cancelOrderForAbilityPlatform(String  json_in) throws Exception{

		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		ParaVo vo =new ParaVo();

		String jsession_id =(String) map.get("jsession_id");
		String order_no =(String) map.get("order_no");
		String order_type =(String) map.get("order_type");
		vo.setJsession_id(jsession_id);
		vo.setOrder_no(order_no);
		vo.setOrder_type(order_type);
		UocMessage message =cancelOrder(vo);
		return message;
	}

	/**
	 * 订单撤单 UOC0007
	 * @param paraVo
	 * @return
	 */
	@Override
	public UocMessage cancelOrder(ParaVo paraVo) throws Exception{
		logger.info("rest-----------cancelOrder UOC0007");
		getBeanDu();
		UocMessage message =new UocMessage();

			String jsession_id =paraVo.getJsession_id();
			String order_no =paraVo.getOrder_no();
			String order_type =paraVo.getOrder_type();
			String oper_code = "";
			String province_code = "";

			/*
			 * 1、调用BASE0008记录接口日志 需要参数 service_code json_in json_out（待补充）
			 */
			/*
			 * 校验jession_id
			 */
			UocMessage res =operServDu.isLogin(jsession_id);
			if(!"0000".equals(res.getRespCode())){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent(res.getContent());
				return message;
			}
			@SuppressWarnings("unchecked")
			Map<String, Object> oper_info = (Map<String, Object>) res.getArgs().get("oper_info");
			String oper_no =(String)oper_info.get("oper_no");

			String order_state ="";
			if("100".equals(order_type)){  //销售订单
				InfoSaleOrderVo infoSaleOrderVo =new InfoSaleOrderVo();
				infoSaleOrderVo.setSale_order_no(order_no);
				infoSaleOrderVo =infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
				if(infoSaleOrderVo ==null){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("无对应订单数据");
					return message;
				}
				order_state =infoSaleOrderVo.getOrder_state();

				if("101".equals(order_state) || "201".equals(order_state)){
					ProcInstTaskInstVo procInstTaskInstVo =new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(order_no);
					procInstTaskInstVo.setOrder_type(order_type);
					procInstTaskInstVo.setTask_state("100");
					procInstTaskInstVo =procInstTaskInstServDu.queryProcInstTaskInstByOrderNoAndTaskState(procInstTaskInstVo);
					if(procInstTaskInstVo ==null){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("此订单无人工任务数据");
						return message;
					}
				}

				if("102".equals(order_state) || "211".equals(order_state)){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("订单状态错误");
					return message;
				}

				InfoOfrOrderVo infoOfrOrderVo =new InfoOfrOrderVo();
				infoOfrOrderVo.setSale_order_no(order_no);
				List<InfoOfrOrderVo> infoOfrOrderVos =infoOfrOrderServDu.queryInfoOfrOrderBySaleOrderNo(infoOfrOrderVo);
				List<InfoServiceOrderVo> infoServiceOrderVos =new ArrayList<InfoServiceOrderVo>();
				//销售订单下面的所有商品订单以及商品订单下的所有服务订单必须一起撤单，有其中一个订单已竣工则报错
				for(InfoOfrOrderVo vo:infoOfrOrderVos){
					if("211".equals(vo.getOrder_state())){
						logger.info("销售订单下面的商品订单状态错误");
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("相关商品订单状态错误");
						return message;
					}
					InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
					infoServiceOrderVo.setOfr_order_no(vo.getOfr_order_no());
					infoServiceOrderVos =infoServiceOrderServDu.queryInfoServiceOrderByOrderNo(infoServiceOrderVo);
					for(InfoServiceOrderVo vo2:infoServiceOrderVos){
						if("211".equals(vo2.getOrder_state())){
							logger.info("销售订单下面的商品订单的服务订单状态错误");
							message.setRespCode(RespCodeContents.SERVICE_FAIL);
							message.setContent("相关商品服务订单状态错误");
							return message;
						}
					}

				}

				infoSaleOrderVo.setCancle_flag("0");
				boolean flag =infoSaleOrderServDu.updateInfoSaleOrder(infoSaleOrderVo);
				if(!flag){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("更新订单失败");
					throw new UocException(message);
				}
				for(InfoOfrOrderVo vo:infoOfrOrderVos){
					vo.setCancle_flag("0");
					flag =infoOfrOrderServDu.updateInfoOfrOrder(vo);
					if(!flag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("更新订单失败");
						throw new UocException(message);
					}
				}
				for(InfoServiceOrderVo vo2:infoServiceOrderVos){
					vo2.setCancle_flag("0");
					flag =infoServiceOrderServDu.updateInfoServiceOrder(vo2);
					if(!flag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("更新订单失败");
						throw new UocException(message);
					}
				}
				//调用BASE0010服务进行订单数据备份
				message =orderDataBakServDu.createOrderDataBakup(order_no, order_type);
				if(!"0000".equals(message.getRespCode())){
					throw new UocException(message);
				}
		} else if ("101".equals(order_type)) { // 101服务订单
			InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
			infoServiceOrderVo.setServ_order_no(order_no);
			infoServiceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
			if (infoServiceOrderVo == null) {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("无对应订单数据");
				return message;
			}
			order_state = infoServiceOrderVo.getOrder_state();
			oper_code = infoServiceOrderVo.getOper_code();
			province_code = infoServiceOrderVo.getProvince_code();

			if ("102".equals(order_state) || "211".equals(order_state)) {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("订单状态错误");
				return message;
			}

			infoServiceOrderVo.setCancle_flag("0");
			infoServiceOrderVo.setOrder_state("302");
			boolean update_flag = infoServiceOrderServDu.updateInfoServiceOrder(infoServiceOrderVo);

			//微信退款标志为1时才调用触点中心服务APC0016进行在线支付退款
			if("1".equals(propertiesParamVo.getWxpayRefundFlag())){
				JSONObject json_info = new JSONObject();
				JSONObject params = new JSONObject();
				params.put("jsession_id", jsession_id);
				params.put("serv_order_no", order_no);
				json_info.put("SERVICE_NAME", "wxpayRefund");
				json_info.put("params", params);
				logger.info("撤单----开始调apc微信退款");
				UocMessage abilityPlatMsg = abilityPlatformServDu.CallAbilityPlatform(json_info.toString(), "1400", "", "");
				//只是调用能力平台成功
				if (!RespCodeContents.SUCCESS.equals(abilityPlatMsg.getRespCode())) {
					return abilityPlatMsg;
				}else {
					String reFundCode=(String) abilityPlatMsg.getArgs().get("code");
					if(!"200".equals(reFundCode)){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("微信退款失败！");
						return message;
					}
				}
			}

			InfoOrderCancelVo infoOrderCancelVo = new InfoOrderCancelVo();
			infoOrderCancelVo.setOrder_no(infoServiceOrderVo.getServ_order_no());
			infoOrderCancelVo = infoOrderCancelServDu.queryInfoOrderCancel(infoOrderCancelVo);
			if (infoOrderCancelVo != null) {
				OrdCancelModAppVo ordCancelModAppVoQuery = new OrdCancelModAppVo();
				ordCancelModAppVoQuery.setTache_id(infoOrderCancelVo.getCancel_tache_code());
				ordCancelModAppVoQuery.setProvince_code(province_code);
				ordCancelModAppVoQuery.setOper_code(oper_code);
//				ordCancelModAppVo = ordCancelModAppServDu.queryOrdCancelModApp(ordCancelModAppVo);
				List<OrdCancelModAppVo> list = ordCancelModAppServDu.queryOrdCancelModAppByVo(ordCancelModAppVoQuery);
				if (list != null && list.size()>0) {
					for(int i=0;i<list.size();i++){
						boolean isSuccess = false;
						// BASE0023
						String param_json = operServDu.loginShareParam(oper_info, jsession_id);
						// BASE0006
						UocMessage uocMessageOrdModOut = ordModFunctionServDu.outByOrdMod(infoServiceOrderVo.getServ_order_no(),
								list.get(i).getMod_code(), "101", param_json);
						if (uocMessageOrdModOut != null){
							if ("0000".equals(uocMessageOrdModOut.getRespCode())){
								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + uocMessageOrdModOut.getContent() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
								Map<String, Object> argsOrdModOut = uocMessageOrdModOut.getArgs();
								String json_info_out = (String) argsOrdModOut.get("json_info");
								String interface_type = (String) argsOrdModOut.get("interface_type");
								String interface_param_json = (String) argsOrdModOut.get("interface_param_json");
								
								InfoServiceOrderVo serviceOrderVo = new InfoServiceOrderVo();
								serviceOrderVo.setServ_order_no(infoServiceOrderVo.getServ_order_no());
								serviceOrderVo = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(serviceOrderVo);
								
								InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
								infoSaleOrderVo.setSale_order_no(serviceOrderVo.getSale_order_no());
								infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
								if (infoSaleOrderVo == null) {
									logger.info(">>>>>>>>>>>>>>>无对应的销售订单<<<<<<<<<<<<<<<<<");
									message.setRespCode(RespCodeContents.PARAM_ERROR);
									message.setContent("无对应的销售订单");
									return message;
								}
								String callback_url = infoSaleOrderVo.getCallback_url();
								
								if (("700").equals(interface_type)) {
									if (StringUtils.isNotEmpty(callback_url)) {
										// BASE0018 调用能力平台服务
										UocMessage uocMessageAbilityPlat = abilityPlatformServDu.CallAbilityPlatform(json_info_out, interface_type,
												interface_param_json, callback_url);
										if (uocMessageAbilityPlat != null) {
											if ("0000".equals(uocMessageAbilityPlat.getRespCode())) {
												String code = (String) uocMessageAbilityPlat.getArgs().get("code");
												if (code != null && code.equals("200")) {
													isSuccess = true;
													// 然后再调用BASE0008服务记录能力平台接口日志
													logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>" + uocMessageAbilityPlat.getContent()
															+ "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
													String argsAbilityPlat = (String) uocMessageAbilityPlat.getArgs().get("json_info");
													message.addArg("abilityPlat", argsAbilityPlat);
												}
											}
											else{
												message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
												message.setContent(uocMessageAbilityPlat.getContent());
												return message;
											}
										} else {
											message.setRespCode(RespCodeContents.ABILITY_PLATFORM_FAIL);
											message.setContent("调用能力平台返销失败");
											return message;
										}
									}
								}
								
							}
						}

					}
					/*
					 * 8、若上面能查出模板并且接口调用成功，则通过当前环节、订单来源查询环节回调表取出接口url，模板，
					 * 若没有数据则不做后续处理
					 * ，然后调用BASE0006服务，取出json串,interface_type等参数
					 * ，最后调用BASE0018服务
					 */
					ProcTacheRetVo paramVo = new ProcTacheRetVo();
					Map<String, String> strMap = StrUtil.splitStringFromOrderNo(infoServiceOrderVo.getServ_order_no());
					paramVo.setArea_code(strMap.get("area_code"));
					paramVo.setProvince_code(infoServiceOrderVo.getProvince_code());
					paramVo.setTache_code(infoOrderCancelVo.getCancel_tache_code());
					List<ProcTacheRetVo> procTacheRetList = procTacheRetServDu.queryProcTacheRetByVo(paramVo);

					if (procTacheRetList != null && procTacheRetList.size() > 0) {
						// BASE0006订单模板出库服务
						UocMessage ordModOut = ordModFunctionServDu.outByOrdMod(infoServiceOrderVo.getServ_order_no(),
								procTacheRetList.get(0).getCall_ord_mod(), "101", "");
						if (ordModOut != null) {
							if ("0000".equals(ordModOut.getRespCode())) {
								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>环节回调出库:" + ordModOut.getContent()
										+ "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
								Map<String, Object> ordModOutMap = ordModOut.getArgs();
								String json_out = (String) ordModOutMap.get("json_info");
								String type = (String) ordModOutMap.get("interface_type");
								String interface_param = (String) ordModOutMap.get("interface_param_json");
								// BASE0018 调用能力平台服务
								UocMessage abilityMessage = abilityPlatformServDu.CallAbilityPlatform(json_out, type,
										interface_param, procTacheRetList.get(0).getCall_url());
								if (abilityMessage != null) {
									if ("0000".equals(abilityMessage.getRespCode())) {
										String respcode = (String) abilityMessage.getArgs().get("code");
										logger.info("---------------环节回调能力平台返回code：" + respcode);
									}
								}
							}
						}
					}
				}

			}


			if (!update_flag) {
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("更新订单失败");
				throw new UocException(message);
			}
			// 判断当前商品订单下是否所有服务订单已竣工或者撤单，如果是则商品订单更新状态为竣工；然后再判断当前销售订单下是否所有商品订单已竣工或者撤单，
			// 如果是则销售订单更新状态竣工，并且调用BASE0010服务进行订单数据备份
			List<InfoServiceOrderVo> infoServiceOrderVos = new ArrayList<InfoServiceOrderVo>();
			infoServiceOrderVos = infoServiceOrderServDu.queryInfoServiceOrderByOrderNo(infoServiceOrderVo);
			boolean flag = true; // 竣工标识
			for (InfoServiceOrderVo vo : infoServiceOrderVos) {
				if (!"211".equals(vo.getOrder_state()) && !"0".equals(vo.getCancle_flag())) {
					flag = false;
				}
			}
			if (flag) {
				InfoOfrOrderVo infoOfrOrderVo = new InfoOfrOrderVo();
				infoOfrOrderVo.setOfr_order_no(infoServiceOrderVo.getOfr_order_no());
				infoOfrOrderVo = infoOfrOrderServDu.getInfoOfrOrderByOfrOrderNo(infoOfrOrderVo);
				infoOfrOrderVo.setOrder_state("211");
				infoOfrOrderServDu.updateInfoOfrOrder(infoOfrOrderVo);

				List<InfoOfrOrderVo> infoOfrOrderVos = infoOfrOrderServDu.queryInfoOfrOrderBySaleOrderNo(infoOfrOrderVo);
				for (InfoOfrOrderVo vo : infoOfrOrderVos) {
					if (!"211".equals(vo.getOrder_state()) && !"0".equals(vo.getCancle_flag())) {
						flag = false;
					}
				}
				if (flag) {
					InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
					infoSaleOrderVo.setSale_order_no(infoOfrOrderVo.getSale_order_no());
					infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
					infoSaleOrderVo.setOrder_state("211");
					update_flag = infoSaleOrderServDu.updateInfoSaleOrder(infoSaleOrderVo);
					if (!update_flag) {
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("更新订单失败");
						throw new UocException(message);
					}
				}
			}
		} else if ("102".equals(order_type)) { // 102支付订单(待补充)
				InfoPayOrderVo infoPayOrderVo =new InfoPayOrderVo();
				infoPayOrderVo.setPay_order_no(order_no);
				infoPayOrderVo =infoPayOrderServDu.getInfoPayOrderByPayOrderNo(infoPayOrderVo);
				if(infoPayOrderVo ==null){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("无对应订单数据");
					return message;
				}
				order_state =infoPayOrderVo.getPay_state();

				if("101".equals(order_state) || "201".equals(order_state)){
					ProcInstTaskInstVo procInstTaskInstVo =new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(order_no);
					procInstTaskInstVo.setOrder_type(order_type);
					procInstTaskInstVo.setTask_state("100");
					procInstTaskInstVo =procInstTaskInstServDu.queryProcInstTaskInstByOrderNoAndTaskState(procInstTaskInstVo);
					if(procInstTaskInstVo ==null){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("此订单无人工任务数据");
						return message;
					}
				}

				if("102".equals(order_state) || "211".equals(order_state)){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("订单状态错误");
					return message;
				}

			}else if("103".equals(order_type)){ //103交付订单(待补充)
				InfoDeliverOrderVo  infoDeliverOrderVo =new InfoDeliverOrderVo();
				infoDeliverOrderVo.setDeliver_order_no(order_no);
				infoDeliverOrderVo =infoDeliverOrderServDu.getInfoDeliverOrderByPayDeliverOrderNo(infoDeliverOrderVo);
				if(infoDeliverOrderVo ==null){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("无对应订单数据");
					return message;
				}
				order_state =infoDeliverOrderVo.getDeliver_state();

				if("101".equals(order_state) || "201".equals(order_state)){
					ProcInstTaskInstVo procInstTaskInstVo =new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(order_no);
					procInstTaskInstVo.setOrder_type(order_type);
					procInstTaskInstVo.setTask_state("100");
					procInstTaskInstVo =procInstTaskInstServDu.queryProcInstTaskInstByOrderNoAndTaskState(procInstTaskInstVo);
					if(procInstTaskInstVo ==null){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("此订单无人工任务数据");
						return message;
					}
				}

				if("102".equals(order_state) || "211".equals(order_state)){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("订单状态错误");
					return message;
				}
			}else if("105".equals(order_type)){ //105商品订单
				InfoOfrOrderVo infoOfrOrderVo =new InfoOfrOrderVo();
				infoOfrOrderVo.setOfr_order_no(order_no);
				infoOfrOrderVo =infoOfrOrderServDu.getInfoOfrOrderByOfrOrderNo(infoOfrOrderVo);
				if(infoOfrOrderVo ==null){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("无对应订单数据");
					return message;
				}
				order_state =infoOfrOrderVo.getOrder_state();

				if("101".equals(order_state) || "201".equals(order_state)){
					ProcInstTaskInstVo procInstTaskInstVo =new ProcInstTaskInstVo();
					procInstTaskInstVo.setOrder_no(order_no);
					procInstTaskInstVo.setOrder_type(order_type);
					procInstTaskInstVo.setTask_state("100");
					procInstTaskInstVo =procInstTaskInstServDu.queryProcInstTaskInstByOrderNoAndTaskState(procInstTaskInstVo);
					if(procInstTaskInstVo ==null){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("此订单无人工任务数据");
						return message;
					}
				}

				if("102".equals(order_state) || "211".equals(order_state)){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("订单状态错误");
					return message;
				}
				//商品订单下的所有服务订单必须一起撤单，有其中一个订单已竣工则报错
				InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
				infoServiceOrderVo.setOfr_order_no(order_no);
				List<InfoServiceOrderVo> infoServiceOrderVos =infoServiceOrderServDu.queryInfoServiceOrderByOrderNo(infoServiceOrderVo);
				for(InfoServiceOrderVo vo2:infoServiceOrderVos){
					if("211".equals(vo2.getOrder_state())){
						logger.info("商品订单的服务订单状态错误");
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("相关服务服务订单状态错误");
						return message;
					}
				}

				boolean flag=true; //竣工标识
				List<InfoOfrOrderVo> infoOfrOrderVos =infoOfrOrderServDu.queryInfoOfrOrderBySaleOrderNo(infoOfrOrderVo);
				for(InfoOfrOrderVo vo:infoOfrOrderVos){
					if(!"211".equals(vo.getOrder_state()) && !"0".equals(vo.getCancle_flag())){
						flag =false;
					}
				}
				if(flag){
					InfoSaleOrderVo infoSaleOrderVo =new InfoSaleOrderVo();
					infoSaleOrderVo.setSale_order_no(infoOfrOrderVo.getSale_order_no());
					infoSaleOrderVo =infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
					infoSaleOrderVo.setOrder_state("211");
					boolean update_flag =infoSaleOrderServDu.updateInfoSaleOrder(infoSaleOrderVo);
					if(!update_flag){
						message.setRespCode(RespCodeContents.SERVICE_FAIL);
						message.setContent("更新订单失败");
						throw new UocException(message);
					}
				}

				//调用BASE0010服务进行订单数据备份
				UocMessage orderDataBakRes =orderDataBakServDu.createOrderDataBakup(order_no, order_type);
				if(!"0000".equals(orderDataBakRes.getRespCode())){
					throw new UocException(orderDataBakRes);
				}
			}else{
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("订单类型异常");
				return message;
			}


			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("订单撤销成功");
			return message;
	}
	/**
	 * 订单数据备份  UOC0069
	 * @return
	 */

	@Override
	public UocMessage createOrderBackUp(String total_num,String remainder){
		UocMessage message = new UocMessage();

		String flag =propertiesParamVo.getTimingBackup();
		if ("no".equals(flag)) { // 不做备份
			return null;
		}
		String areaCodeList = propertiesParamVo.getBackupAreaCodeList();
		if (areaCodeList==null || "".equals(areaCodeList)){
			return null;
		}
		if(total_num ==null || "".equals(total_num)){
			total_num ="1";
		}
		if(remainder ==null || "".equals(remainder)){
			remainder ="0";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=new Date();
		String nowDate=sdf.format(date);
		String month=nowDate.substring(4,6).trim();

		String part_month=Integer.toString(Integer.parseInt(month));//获取当前月份
		Calendar calendar=Calendar.getInstance();//获取当前日历
		calendar.setTime(date);//设置当前时间的日历

		List<InfoSaleOrderVo> list = new ArrayList<InfoSaleOrderVo>();
		List<InfoSaleOrderVo> list1 = null;

		String[]areas = areaCodeList.split(",");
		for(String area_code:areas){
			InfoSaleOrderVo infoSaleOrderVo =new InfoSaleOrderVo();
			infoSaleOrderVo.setOrder_state("211");
			infoSaleOrderVo.setPart_month(part_month);
			infoSaleOrderVo.setArea_code(area_code);
			infoSaleOrderVo.setTotal_num(total_num);
			infoSaleOrderVo.setRemainder(remainder);

			try {
				list1 = infoSaleOrderServDu.queryInfoSaleOrderByOrderState(infoSaleOrderVo);
				if(list1!=null && list1.size() > 0){
					list.addAll(list1);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			calendar.add(calendar.MONTH, -1);//设置前一个月
			Date lastDate=calendar.getTime();//获取上个月的时间
			String lastMonth =sdf.format(lastDate).substring(4,6).trim();
			String part_month1=Integer.toString(Integer.parseInt(lastMonth));//获取上个月的月份

			InfoSaleOrderVo infoSaleOrderVo1 =new InfoSaleOrderVo();
			infoSaleOrderVo1.setOrder_state("211");
			infoSaleOrderVo1.setPart_month(part_month1);
			infoSaleOrderVo1.setArea_code(area_code);
			infoSaleOrderVo1.setTotal_num(total_num);
			infoSaleOrderVo1.setRemainder(remainder);
			try {
				list1 = infoSaleOrderServDu.queryInfoSaleOrderByOrderState(infoSaleOrderVo1);

				if(list1!=null && list1.size() > 0){
					list.addAll(list1);
				}
			} catch (Exception e) {
				e.printStackTrace();

				return null;
			}
		}

		//赠款总额
		String total_grant="576";
		//每期赠款金额
		String each_grant="16";
		//首次赠款金额
		String first_grant="10";
		//赠款号码
		String device_number="";
		/*try {
			//从缓存中取code_list赠款配置数据
			CodeList codeList =new CodeList();
			//取赠款总金额
			codeList.setType_code("total_grant");
			List<CodeList> codeLists =codeListServDu.queryCodeListByTypeCodeFromRedis(codeList);
			if(codeLists==null){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("取缓存赠款配置数据total_grant为空");
				return message;
			}
			total_grant=codeLists.get(0).getCode_id();
			//取每期赠款金额
			codeList.setType_code("each_grant");
			codeLists=codeListServDu.queryCodeListByTypeCodeFromRedis(codeList);
			if(codeLists==null){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("取缓存赠款配置数据each_grant为空");
				return message;
			}
			each_grant=codeLists.get(0).getCode_id();
			//取首次赠款金额
			codeList.setType_code("first_grant");
			codeLists=codeListServDu.queryCodeListByTypeCodeFromRedis(codeList);
			if(codeLists==null){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("取缓存赠款配置数据first_grant为空");
				return message;
			}
			first_grant=codeLists.get(0).getCode_id();

		} catch (Exception e1) {
			e1.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("取缓存code_list赠款配置数据异常");
			return message;
		}*/

		//取销售订单表状态等于211的数据(每次最多1000条)，对这些订单调用BASE0010服务，oper_type填100
		if(list !=null && list.size()>0){
			for(InfoSaleOrderVo vo :list){
				logger.info("数据备份------order_no："+(vo.getSale_order_no()));
				try {
					String order_type=vo.getOrder_type();
					if("A101".equals(order_type)){
						String sale_order_no=vo.getSale_order_no();
						InfoServiceOrderPo infoServiceOrderPo=new InfoServiceOrderPo();
						infoServiceOrderPo.setSale_order_no(sale_order_no);
						List<InfoServiceOrderPo> serviceOrderList=infoServiceOrderServ.queryInfoServiceOrderBySaleOrderNo(infoServiceOrderPo);
						if(serviceOrderList!=null&&serviceOrderList.size()>0){
							String serv_order_no=serviceOrderList.get(0).getServ_order_no();
							String jsession_id=serviceOrderList.get(0).getAccept_oper_no();
							sendPhotoServDu.getSendPhoto(jsession_id, serv_order_no);
						}
					}//判断如果oper_type＝”A103”,则根据销售订单号取出服务订单号，然后调用赠款服务
					else if("A103".equals(order_type)){
						logger.info("===========赠款入口=========");
						String sale_order_no=vo.getSale_order_no();
						InfoServiceOrderPo infoServiceOrderPo=new InfoServiceOrderPo();
						infoServiceOrderPo.setSale_order_no(sale_order_no);
						List<InfoServiceOrderPo> serviceOrderList=infoServiceOrderServ.queryInfoServiceOrderBySaleOrderNo(infoServiceOrderPo);
						logger.info("===========赠款开始销售订单号========="+sale_order_no);
						if(serviceOrderList!=null&&serviceOrderList.size()>0){
							String serv_order_no=serviceOrderList.get(0).getServ_order_no();
							String jsession_id=serviceOrderList.get(0).getAccept_oper_no();
							device_number=serviceOrderList.get(0).getAcc_nbr();
							String orderState=serviceOrderList.get(0).getOrder_state();
							if("302".equals(orderState)){
								//撤单的订单不需进行赠款，跳出本次循环，进行下一次循环
								logger.info("该订单【"+sale_order_no+"】已经撤单，不需要进行赠款");
								continue;
							}
							logger.info("===========赠款开始服务订单号========="+serv_order_no);
							//根据服务订单号查询服务订单产品列表
							InfoServiceOrderProdMapPo infoServiceOrderProdMapPo=new InfoServiceOrderProdMapPo();
							infoServiceOrderProdMapPo.setServ_order_no(serv_order_no);
							List<InfoServiceOrderProdMapPo> prodMapList=infoServiceOrderProdMapServ.queryInfoServiceOrderProdMapByOrderNo(infoServiceOrderProdMapPo);
							//根据订单服务号去查INFO_SERV_ACTIVITY取到合约id
							InfoServiceOrderActivityPo InfoServiceOrderActivityPo=new InfoServiceOrderActivityPo();
							InfoServiceOrderActivityPo.setServ_order_no(serv_order_no);
							List<InfoServiceOrderActivityPo> activityList=infoServiceOrderActivityServ.queryInfoServiceOrderActivityByServOrderNo(InfoServiceOrderActivityPo);
							logger.info("===========赠款入开始取合约结果========="+activityList);
							if(activityList!=null&&activityList.size()>0){
								//取到合约id
								String activity_id=activityList.get(0).getActivity_id();
								logger.info("===========特定活动发送短信开始取到活动id========="+activity_id);
								if("90131486".equals(activity_id)){
									logger.info("特定活动要发短信，插短信表开始");
									//将短信内容写入短信表
									InfoSmsWarnPo po= new InfoSmsWarnPo();
									String sms_content="温馨提示：您已成功绑定OPPO手机，可免3年月租（赠送话费576元），请保持将号卡放在活动终端中使用，以便正常返还话费，感谢参与OPPO专属活动！";
									po.setOrder_no(serv_order_no);
									po.setOrder_type("102");
								    po.setProvince_code(vo.getProvince_code());
								    po.setArea_code(vo.getArea_code());
								    po.setPart_month(vo.getPart_month());
									po.setTache_code("");
									po.setAcc_nbr(device_number);
									po.setSms_content(sms_content);
									po.setSend_status("0");
									po.setAccept_oper_no(vo.getAccept_oper_no());
									po.setAccept_depart_no(vo.getAccept_depart_no());
									String insertTime = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
									po.setInsert_time(insertTime);
									String id = getIdServDu.getId("createLogId", vo.getProvince_code(), "*", "");
									po.setId(id);
									infoSmsWarnServ.createInfoSmsWarn(po);
									logger.info("特定活动要发短信，插短信表结束");
								}
							}
							if(prodMapList!=null&&prodMapList.size()>0){
								//取到产品id
								String prod_code=prodMapList.get(0).getProd_code();
								logger.info("===========赠款开始取到产品id========="+prod_code);
								if("90126183".equals(prod_code)){

									logger.info("特定产品要发短信，插短信表开始");
									//将短信内容写入短信表
									InfoSmsWarnPo po= new InfoSmsWarnPo();
									String sms_content="尊敬的用户，本产品月租16元，省内流量500MB/元，流量当日有效，当天超过500M还按上述规则自动叠加。本产品每个用户仅限购一张，且激活入网后不能进行过户。";
									po.setOrder_no(serv_order_no);
									po.setOrder_type("102");
								    po.setProvince_code(vo.getProvince_code());
								    po.setArea_code(vo.getArea_code());
								    po.setPart_month(vo.getPart_month());
									po.setTache_code("");
									po.setAcc_nbr(device_number);
									po.setSms_content(sms_content);
									po.setSend_status("0");
									po.setAccept_oper_no(vo.getAccept_oper_no());
									po.setAccept_depart_no(vo.getAccept_depart_no());
									String insertTime = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
									po.setInsert_time(insertTime);
									String id = getIdServDu.getId("createLogId", vo.getProvince_code(), "*", "");
									po.setId(id);
									infoSmsWarnServ.createInfoSmsWarn(po);
									logger.info("特定产品要发短信，插短信表结束");

									logger.info("===========符合赠款条件========="+prod_code);
									InfoUserGrantPo infoUserGrantPo=new InfoUserGrantPo();
									infoUserGrantPo.setDevice_number(device_number);
									infoUserGrantPo.setEach_grant(Double.valueOf(each_grant));
									infoUserGrantPo.setTotal_grant(Double.valueOf(total_grant));
									infoUserGrantPo.setResidual_grant(Double.valueOf(total_grant));
									infoUserGrantPo.setUser_status("0");
									infoUserGrantPo.setIn_net_date(new Date());
									//往用户赠费类型表插记录
									infoUserGrantServ.create(infoUserGrantPo);
									logger.info("===========往infoUserGrant新增记录结束=========");
									//首次赠款，走aop赠款接口
									//处理费用，aop接口传参单位为分,希望调用该方法时费用单位设为元吧,这里化元为分
									String fee=first_grant;
									BigDecimal grantFee = new BigDecimal(fee);
									grantFee=grantFee.multiply(new BigDecimal(100));
									fee=grantFee.toString();
									UocMessage grantReslt=grantFeePayServDu.promFeeForAop(jsession_id, serv_order_no, fee, device_number);
									logger.info("===========赠款结果========="+grantReslt.getContent());
									//调往用户赠款记录表记录赠款结果
									String deal_flag="2";
									if(RespCodeContents.SUCCESS.equals(grantReslt.getRespCode())){
										deal_flag="1";
									}else {
										deal_flag="2";
									}

									UserGrantFeePo userGrantFeePo=new UserGrantFeePo();
									userGrantFeePo.setDevice_number(device_number);
									userGrantFeePo.setActive_flag("1");
									userGrantFeePo.setGrant_fee(Double.valueOf(first_grant));
									userGrantFeePo.setDeal_flag(deal_flag);
									userGrantFeePo.setDeal_result(grantReslt.getContent());
									userGrantFeePo.setAcct_month(new SimpleDateFormat("yyyyMM").format(new Date()));
									userGrantFeePo.setCreate_date(new Date());
									userGrantFeePo.setUpdate_date(new Date());
									userGrantFeeServ.create(userGrantFeePo);
									logger.info("===========往userGrantFee新增记录结束=========");
								}
							}

							// 增加判断如果服务订单号对应的即时激励发展人(info_serv_ext表的ext_field_7)字段有值，则增加调用认证中心服务UAC0011
							InfoServiceOrderExtVo extVo = new InfoServiceOrderExtVo();
							extVo.setServ_order_no(serv_order_no);
							List<InfoServiceOrderExtVo> extList = infoServiceOrderExtServDu.queryInfoServiceOrderExtByOrderNo(extVo);

							if (StringUtils.isNotEmpty(extList.get(0).getExt_field_7())) {
								Map<String, String> voMap = new HashMap<String, String>();
								voMap.put("jsession_id", jsession_id);
								voMap.put("order_id", serv_order_no);
								voMap.put("acc_nbr", device_number);
								voMap.put("oper_id", extList.get(0).getExt_field_7());

								JSONObject jsonObj = new JSONObject();
								jsonObj.put("SERVICE_NAME", "createRewardOrderInfo");
								jsonObj.put("param", voMap);
								UocMessage res = abilityPlatformServDu.CallAbilityPlatform(jsonObj.toString(), "300", "", "");
								logger.info("===========调用UAC0011-即时激励数据写入结果=========" + res.getArgs().get("json_info"));
							}

						}

					}
					UocMessage res =orderDataBakServDu.createOrderDataBakup(vo.getSale_order_no(), "100");
				} catch (Exception e) {
					e.printStackTrace();
				}
				/*if(!"0000".equals(res.getRespCode())){
					logger.info("------数据备份失败------");
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("订单数据备份失败");
					return message;
				}*/
			}

		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("订单数据备份成功");
		return message;
	}


	@Override
	public UocMessage createGrantFeeTimer() throws Exception {
		UocMessage uocMessage =new UocMessage();

		SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyyMM");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		//设为上一个月
        cal.add(Calendar.MONTH, -1);
        Date last_month=cal.getTime();
        String acct_month=sdfFormat.format(last_month);
        //取到user_grant_fee表里deal_flag=’0’且 acct_month为上月的数据
        UserGrantFeePo userGrantFeePo=new UserGrantFeePo();
        userGrantFeePo.setAcct_month(acct_month);
        userGrantFeePo.setDeal_flag("0");
        List<UserGrantFeePo> userGrantFeeList=userGrantFeeServ.queryUserGrantFeeList(userGrantFeePo);

        if(userGrantFeeList!=null&&userGrantFeeList.size()>0){
        	logger.info("=====本次自动赠款数量====="+userGrantFeeList.size());
        	for(UserGrantFeePo userGrant:userGrantFeeList){
        		//号码为空也跳出本循环继续下一次
        		if(!StringUtils.isNotEmpty(userGrant.getDevice_number())){
        			continue;
        		}
        		//赠款费用小于等于0也跳出本次循环继续下一次
        		if(userGrant.getGrant_fee()<=0){
        			continue;
        		}
        		//查询用户赠费类型表，失效的就不继续往下执行了,跳出本次循环进行下一次循环
        		InfoUserGrantPo infoUserGrantPo=new InfoUserGrantPo();
        		infoUserGrantPo.setUser_status("0");
        		infoUserGrantPo.setDevice_number(userGrant.getDevice_number());
        		List<InfoUserGrantPo> infoUserGrantList=infoUserGrantServ.queryInfoUserGrantList(infoUserGrantPo);
        		//查处有效的数据，取第一条，希望之前保存在数据库里的有效数据不要重复哟
        		InfoUserGrantPo infoUserGrant = new InfoUserGrantPo();
        		if(infoUserGrantList!=null&&infoUserGrantList.size()>0){
        			//查处有效的数据，取第一条，希望之前保存在数据库里的有效数据不要重复哟
        			infoUserGrant=infoUserGrantList.get(0);
        			if(infoUserGrant.getResidual_grant()<=0){
        				//赠款余额小于等于0，也应该跳出本次循环进行下一次了吧
        				logger.info("该号码【"+userGrant.getDevice_number()+"赠款余额为0，之前已赠款完！");
            			continue;
        			}
        		}else {
					continue;
				}

        		//处理费用，aop接口传参单位为分,希望调用该方法时费用单位设为元吧,这里化元为分
        		String fee=Double.toString(userGrant.getGrant_fee());
				BigDecimal grantFee = new BigDecimal(fee);
				grantFee=grantFee.multiply(new BigDecimal(100));
				fee=grantFee.toString();
				String jsession_id=propertiesParamVo.getGrantOperNo();
				String serv_order_no=userGrant.getDevice_number().substring(0, 3)+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				UocMessage grantReslt=grantFeePayServDu.promFeeForAop(jsession_id, serv_order_no, fee, userGrant.getDevice_number());
				logger.info(userGrant.getDevice_number());
				logger.info("号码【"+userGrant.getDevice_number()+"】自动赠款赠款结果========="+grantReslt.getContent());
				//调往用户赠款记录表记录赠款结果
				String deal_flag="2";
				if(RespCodeContents.SUCCESS.equals(grantReslt.getRespCode())){
					logger.info("号码【"+userGrant.getDevice_number()+"】自动赠款成功");
					//赠款成功，更新INFO_USER_GRANT赠款余额等
					double residual_grant=infoUserGrant.getResidual_grant()-userGrant.getGrant_fee();
					infoUserGrantPo.setResidual_grant(residual_grant);
					infoUserGrantServ.update(infoUserGrantPo);
					deal_flag="1";
				}else {
					logger.info("号码【"+userGrant.getDevice_number()+"】自动赠款失败，原因是："+grantReslt.getContent());
					deal_flag="2";
				}

				//更新USER_GRANT_FEE表记录赠款情况
				UserGrantFeePo userGrantFee=new UserGrantFeePo();
				userGrantFee.setDeal_flag(deal_flag);
				userGrantFee.setDeal_result(grantReslt.getContent());
				userGrantFee.setUpdate_date(new Date());
				userGrantFee.setDevice_number(userGrant.getDevice_number());
				userGrantFee.setAcct_month(acct_month);
				userGrantFeeServ.update(userGrantFee);
        	}
        	uocMessage.setRespCode(RespCodeContents.SUCCESS);
        	uocMessage.setContent("自动赠款完毕！");
        }else {
			//没有符合赠款条件的数据
        	uocMessage.setRespCode(RespCodeContents.SUCCESS);
        	uocMessage.setContent("表中没有符合赠款条件的数据，不用赠款！");
		}


		return uocMessage;
	}


}
