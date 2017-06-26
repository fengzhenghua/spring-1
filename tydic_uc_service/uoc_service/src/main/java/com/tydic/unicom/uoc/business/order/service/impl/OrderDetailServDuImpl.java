package com.tydic.unicom.uoc.business.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.service.cache.service.redis.po.CodeList;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderDetailServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.ProcModServDu;
import com.tydic.unicom.uoc.business.order.service.vo.OfrOrderAndServiceOrderVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcInstTaskDealRecordVo;
import com.tydic.unicom.uoc.service.code.interfaces.CodeListServDu;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.ProcInstTaskInstServDu;
import com.tydic.unicom.uoc.service.mod.vo.ProcInstTaskInstVo;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoDeliverOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoOfrOrderFeeHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoOfrOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoOfrOrderInvoiceHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoOfrOrderPayHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoPayOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderAttrHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderDistrDetailHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderDistributionHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderEditHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderExtHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderFeeHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderOfrMapHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderPersionCertHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderPersionHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoSaleOrderServMapHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderActivityHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderAgreementHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderAttrHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderCollectionHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderDeveloperHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderExtHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderFeeHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderFixHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderGoodNbrHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderGuarantorHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderM165HisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderPackageHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderPayHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderPersionHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderProdMapHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderSimCardHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderTerminalHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.ProcInstTaskInstHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoDeliverOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoOfrOrderFeeHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoOfrOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoOfrOrderInvoiceHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoOfrOrderPayHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoPayOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderAttrHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderDistrDetailHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderDistributionHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderEditHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderExtHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderFeeHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderOfrMapHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderPersionCertHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderPersionHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoSaleOrderServMapHisVo;
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
import com.tydic.unicom.uoc.service.order.his.vo.ProcInstTaskInstHisVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoDeliverOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOfrOrderFeeServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOfrOrderInvoiceServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOfrOrderPayServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoOfrOrderServDu;
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
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderActivityServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderAgreementServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderAttrServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderCollectionServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderDeveloperServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderExtServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderFeeServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderFixServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderGoodNbrServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderGuarantorServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderM165ServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderPackageServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderPayServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderPersionServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderProdMapServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderSimCardServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderTerminalServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoDeliverOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderFeeVo;
import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderInvoiceVo;
import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderPayVo;
import com.tydic.unicom.uoc.service.order.vo.InfoOfrOrderVo;
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
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderActivityVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderAgreementVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderAttrVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderCollectionVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderDeveloperVo;
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
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class OrderDetailServDuImpl implements OrderDetailServDu {

	Logger logger = Logger.getLogger(OrderDetailServDuImpl.class);
	@Autowired
	private OperServDu operServDu;
	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;
	@Autowired
	private InfoOfrOrderServDu infoOfrOrderServDu;
	@Autowired
	private InfoPayOrderServDu infoPayOrderServDu;
	@Autowired
	private InfoDeliverOrderServDu infoDeliverOrderServDu;
	@Autowired
	private InfoSaleOrderFeeServDu infoSaleOrderFeeServDu;
	@Autowired
	private InfoSaleOrderDistributionServDu infoSaleOrderDistributionServDu;
	@Autowired
	private InfoSaleOrderDistrDetailServDu infoSaleOrderDistrDetailServDu;
	@Autowired
	private InfoSaleOrderAttrServDu infoSaleOrderAttrServDu;
	@Autowired
	private InfoSaleOrderEditServDu infoSaleOrderEditServDu;
	@Autowired
	private InfoSaleOrderExtServDu infoSaleOrderExtServDu;
	@Autowired
	private InfoSaleOrderOfrMapServDu infoSaleOrderOfrMapServDu;
	@Autowired
	private InfoSaleOrderPersionServDu infoSaleOrderPersionServDu;
	@Autowired
	private InfoSaleOrderServMapServDu infoSaleOrderServMapServDu;
	@Autowired
	private InfoSaleOrderServDu infoSaleOrderServDu;
	@Autowired
	private InfoServiceOrderActivityServDu infoServiceOrderActivityServDu;
	@Autowired
	private InfoServiceOrderAgreementServDu infoServiceOrderAgreementServDu;
	@Autowired
	private InfoServiceOrderCollectionServDu infoServiceOrderCollectionServDu;
	@Autowired
	private InfoServiceOrderFixServDu infoServiceOrderFixServDu;
	@Autowired
	private InfoServiceOrderProdMapServDu infoServiceOrderProdMapServDu;
	@Autowired
	private InfoServiceOrderPersionServDu infoServiceOrderPersionServDu;
	@Autowired
	private InfoServiceOrderPayServDu infoServiceOrderPayServDu;
	@Autowired
	private InfoServiceOrderPackageServDu infoServiceOrderPackageServDu;
	@Autowired
	private InfoServiceOrderM165ServDu infoServiceOrderM165ServDu;
	@Autowired
	private InfoServiceOrderGuarantorServDu infoServiceOrderGuarantorServDu;
	@Autowired
	private InfoServiceOrderGoodNbrServDu infoServiceOrderGoodNbrServDu;
	@Autowired
	private InfoServiceOrderFeeServDu infoServiceOrderFeeServDu;
	@Autowired
	private InfoServiceOrderDeveloperServDu infoServiceOrderDeveloperServDu;
	@Autowired
	private InfoServiceOrderAttrServDu infoServiceOrderAttrServDu;
	@Autowired
	private InfoOfrOrderFeeServDu infoOfrOrderFeeServDu;
	@Autowired
	private InfoOfrOrderInvoiceServDu infoOfrOrderInvoiceServDu;
	@Autowired
	private InfoOfrOrderPayServDu infoOfrOrderPayServDu;
	@Autowired
	private InfoServiceOrderExtServDu infoServiceOrderExtServDu;
	@Autowired
	private InfoServiceOrderTerminalServDu infoServiceOrderTerminalServDu;
	@Autowired
	private InfoServiceOrderSimCardServDu infoServiceOrderSimCardServDu;
	@Autowired
	private ProcInstTaskInstServDu procInstTaskInstServDu;
	@Autowired
	private InfoServiceOrderHisServDu infoServiceOrderHisServDu;
	@Autowired
	private InfoOfrOrderHisServDu infoOfrOrderHisServDu;
	@Autowired
	private InfoPayOrderHisServDu infoPayOrderHisServDu;
	@Autowired
	private InfoDeliverOrderHisServDu infoDeliverOrderHisServDu;
	@Autowired
	private InfoSaleOrderFeeHisServDu infoSaleOrderFeeHisServDu;
	@Autowired
	private InfoSaleOrderDistributionHisServDu infoSaleOrderDistributionHisServDu;
	@Autowired
	private InfoSaleOrderDistrDetailHisServDu infoSaleOrderDistrDetailHisServDu;
	@Autowired
	private InfoSaleOrderAttrHisServDu infoSaleOrderAttrHisServDu;
	@Autowired
	private InfoSaleOrderEditHisServDu infoSaleOrderEditHisServDu;
	@Autowired
	private InfoSaleOrderExtHisServDu infoSaleOrderExtHisServDu;
	@Autowired
	private InfoSaleOrderOfrMapHisServDu infoSaleOrderOfrMapHisServDu;
	@Autowired
	private InfoSaleOrderPersionHisServDu infoSaleOrderPersionHisServDu;
	@Autowired
	private InfoSaleOrderServMapHisServDu infoSaleOrderServMapHisServDu;
	@Autowired
	private InfoSaleOrderHisServDu infoSaleOrderHisServDu;
	@Autowired
	private InfoServiceOrderActivityHisServDu infoServiceOrderActivityHisServDu;
	@Autowired
	private InfoServiceOrderAgreementHisServDu infoServiceOrderAgreementHisServDu;
	@Autowired
	private InfoServiceOrderCollectionHisServDu infoServiceOrderCollectionHisServDu;
	@Autowired
	private InfoServiceOrderFixHisServDu infoServiceOrderFixHisServDu;
	@Autowired
	private InfoServiceOrderProdMapHisServDu infoServiceOrderProdMapHisServDu;
	@Autowired
	private InfoServiceOrderPersionHisServDu infoServiceOrderPersionHisServDu;
	@Autowired
	private InfoServiceOrderPayHisServDu infoServiceOrderPayHisServDu;
	@Autowired
	private InfoServiceOrderPackageHisServDu infoServiceOrderPackageHisServDu;
	@Autowired
	private InfoServiceOrderM165HisServDu infoServiceOrderM165HisServDu;
	@Autowired
	private InfoServiceOrderGuarantorHisServDu infoServiceOrderGuarantorHisServDu;
	@Autowired
	private InfoServiceOrderGoodNbrHisServDu infoServiceOrderGoodNbrHisServDu;
	@Autowired
	private InfoServiceOrderFeeHisServDu infoServiceOrderFeeHisServDu;
	@Autowired
	private InfoServiceOrderDeveloperHisServDu infoServiceOrderDeveloperHisServDu;
	@Autowired
	private InfoServiceOrderAttrHisServDu infoServiceOrderAttrHisServDu;
	@Autowired
	private InfoOfrOrderFeeHisServDu infoOfrOrderFeeHisServDu;
	@Autowired
	private InfoOfrOrderInvoiceHisServDu infoOfrOrderInvoiceHisServDu;
	@Autowired
	private InfoOfrOrderPayHisServDu infoOfrOrderPayHisServDu;
	@Autowired
	private InfoServiceOrderExtHisServDu infoServiceOrderExtHisServDu;
	@Autowired
	private InfoServiceOrderTerminalHisServDu infoServiceOrderTerminalHisServDu;
	@Autowired
	private InfoServiceOrderSimCardHisServDu infoServiceOrderSimCardHisServDu;
	@Autowired
	private ProcInstTaskInstHisServDu procInstTaskInstHisServDu;
	@Autowired
	private InfoSaleOrderPersionCertServDu infoSaleOrderPersionCertServDu;
	@Autowired
	private InfoSaleOrderPersionCertHisServDu infoSaleOrderPersionCertHisServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired
	private ProcModServDu procModServDu;
	@Autowired
	private CodeListServDu codeListServDu;
	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;

	private RespCodeContents respCode;

	/**
	 * 订单详情查询 UOC0010入参组装
	 *
	 * @param json_in
	 * @return
	 * @throws Exception
	 */
	public UocMessage getOrderDetailForAbilityPlatform(String json_in) throws Exception {

		if (jsonToBeanServDu == null) {
			logger.info("====================jsonToBeanServDu is null============================" + jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map = jsonToBeanServDu.jsonToMap(json_in);
		ParaVo vo = new ParaVo();

		String jsession_id = (String) map.get("jsession_id");
		String order_no = (String) map.get("order_no");
		String query_type = (String) map.get("query_type");

		vo.setJsession_id(jsession_id);
		vo.setOrder_no(order_no);
		vo.setQuery_type(query_type);

		// TODO
		// 获取finsh_flag,先默认传0
		// vo.setFinish_flag("0");
		UocMessage message = getOrderDetail(vo);
		return message;
	}

	/*
	 * 订单详情查询 UOC0010
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	public UocMessage getOrderDetail(ParaVo paraVo) throws Exception {
		UocMessage uocMessage = new UocMessage();
		uocMessage.setRespCode(respCode.SERVICE_FAIL);
		uocMessage.setContent("操作失败");
		getBeanDu();
		// 对应工号信息
		@SuppressWarnings("unused")
		String province_code = "";
		@SuppressWarnings("unused")
		String area_code = "";
		@SuppressWarnings("unused")
		String depart_no = "";
		@SuppressWarnings("unused")
		String depart_name = "";
		@SuppressWarnings("unused")
		String oper_no = "";

		String jsession_id = paraVo.getJsession_id();
		String query_type = paraVo.getQuery_type();
		String order_no = paraVo.getOrder_no();
		String finish_flag = paraVo.getFinish_flag();

		String tableName = "";
		if (jsession_id == null || jsession_id.equals("")) {
			uocMessage.setContent("失败:jsession_id为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if (query_type == null || query_type.equals("")) {
			uocMessage.setContent("失败:query_type为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		if (order_no == null || order_no.equals("")) {
			uocMessage.setContent("失败:order_no为必传参数");
			uocMessage.setRespCode(respCode.PARAM_ERROR);
			return uocMessage;
		}
		// BASE0017
		UocMessage operMesg = operServDu.isLogin(jsession_id);
		if ("0000".equals(operMesg.getRespCode())) {
			@SuppressWarnings("unchecked")
			Map<String, Object> oper_info = (Map<String, Object>) operMesg.getArgs().get("oper_info");
			province_code = (String) oper_info.get("province_code");
		} else {
			return operMesg;
		}
		/*
		 * 100销售订单、101服务订单、102支付订单、103交付订单
		 */
		Map<String, Object> json_out = new HashMap<String, Object>();
		// 先查询订单表，确定订单表中有数据，如果没有数据直接错误返回


			/*
			 * 非竣工订单
			 */
			// 销售订单拓展属性横表
			if (paraVo.getFinish_flag() != null && !"".equals(paraVo.getFinish_flag())) {
				if (query_type.equals("100")) {
					/*
					 * 根据 销售订单号 取销售订单XXX表
					 */

					if (finish_flag.equals("0")) {
						/*
						 * 非竣工订单
						 */
						// 销售订单拓展属性横表
						InfoSaleOrderExtVo infoSaleOrderExtVo = new InfoSaleOrderExtVo();
						infoSaleOrderExtVo.setSale_order_no(order_no);
						InfoSaleOrderExtVo infoSaleOrderExt = infoSaleOrderExtServDu
								.getInfoSaleOrderExtBySaleOrderNo(infoSaleOrderExtVo);
						if (infoSaleOrderExt != null) {
							json_out.put("infoSaleOrderExt", infoSaleOrderExt);
							tableName = "info_sale_ext";
							Map<String, Object> mapAttrDesc = infoSaleOrderExtServDu.getAttrDesc(infoSaleOrderExt,
									tableName);
							if (mapAttrDesc != null) {
								json_out.put("infoSaleOrderExtTN", mapAttrDesc);
							}
						} else {
							uocMessage.setContent("无销售订单拓展属性横表信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单拓展属性横表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						}

						// 销售订单表
						InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
						infoSaleOrderVo.setSale_order_no(order_no);
						InfoSaleOrderVo infoSaleOrder = infoSaleOrderServDu
								.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
						if (infoSaleOrder == null) {
							uocMessage.setContent("无销售订单表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrder", infoSaleOrder);
						}

						// 销售订单费用信息表
						InfoSaleOrderFeeVo infoSaleOrderFeeVoPara = new InfoSaleOrderFeeVo();
						infoSaleOrderFeeVoPara.setSale_order_no(order_no);
						InfoSaleOrderFeeVo infoSaleOrderFee = infoSaleOrderFeeServDu
								.queryInfoSaleOrderFeeBySaleOrderNo(infoSaleOrderFeeVoPara);
						if (infoSaleOrderFee == null) {
							uocMessage.setContent("无销售订单费用信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单费用信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderFee", infoSaleOrderFee);
						}

						// 销售订单费用信息表 按支付订单号
						// InfoPayOrderVo infoPayOrderVo = new InfoPayOrderVo();
						// infoPayOrderVo.setRela_order_no(order_no);
						// infoPayOrderVo.setRela_order_type("100");
						// List<InfoSaleOrderFeeVo> infoSaleOrderFeeList =
						// infoSaleOrderFeeServDu.queryInfoSaleOrderFeeByPayOrder(infoPayOrderVo);
						// if(infoSaleOrderFeeList == null){
						// uocMessage.setContent("无销售订单费用信息");
						// logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单费用信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						// }else{
						// json_out.put("infoSaleOrderFeeList",
						// infoSaleOrderFeeList);
						// }

						// 销售订单属性表
						InfoSaleOrderAttrVo infoSaleOrderAttrVo = new InfoSaleOrderAttrVo();
						infoSaleOrderAttrVo.setSale_order_no(order_no);
						List<InfoSaleOrderAttrVo> infoSaleOrderAttrList = infoSaleOrderAttrServDu
								.queryInfoSaleOrderAttrList(infoSaleOrderAttrVo);
						if (infoSaleOrderAttrList == null) {
							uocMessage.setContent("无销售订单属性表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单属性表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderAttrList", infoSaleOrderAttrList);
						}

						// 销售订单包裹信息表
						InfoSaleOrderDistrDetailVo infoSaleOrderDistrDetailVo = new InfoSaleOrderDistrDetailVo();
						infoSaleOrderDistrDetailVo.setSale_order_no(order_no);
						List<InfoSaleOrderDistrDetailVo> infoSaleOrderDistrDetailList = infoSaleOrderDistrDetailServDu
								.queryInfoSaleOrderDistrDetailBySaleOrderNo(infoSaleOrderDistrDetailVo);
						if (infoSaleOrderDistrDetailList == null) {
							uocMessage.setContent("无销售订单包裹信息表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单包裹信息表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderDistrDetailList", infoSaleOrderDistrDetailList);
						}

						// 销售订单包裹信息表 , 按支付订单号
						// List<InfoSaleOrderDistrDetailVo>
						// infoSaleOrderDistrDetailByPayList =
						// infoSaleOrderDistrDetailServDu.queryInfoSaleOrderDistrDetailByPayOrderNo(infoPayOrderVo);
						// if(infoSaleOrderDistrDetailByPayList == null){
						// uocMessage.setContent("无销售订单包裹信息表信息");
						// logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单包裹信息表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						// }else{
						// json_out.put("infoSaleOrderDistrDetailByPayList",
						// infoSaleOrderDistrDetailByPayList);
						// }

						// 销售订单收件人信息表
						InfoSaleOrderDistributionVo InfoSaleOrderDistributionVoPara = new InfoSaleOrderDistributionVo();
						InfoSaleOrderDistributionVoPara.setSale_order_no(order_no);
						InfoSaleOrderDistributionVo infoSaleOrderDistributionVo = infoSaleOrderDistributionServDu
								.getInfoSaleOrderDistributionBySaleOrderNo(InfoSaleOrderDistributionVoPara);
						if (infoSaleOrderDistributionVo == null) {
							uocMessage.setContent("无销售订单收件人信息表信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单收件人信息表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderDistribution", infoSaleOrderDistributionVo);
						}

						// 销售订单收件人信息表 按支付订单号
						// List<InfoSaleOrderDistributionVo>
						// infoSaleOrderDistributionList =
						// infoSaleOrderDistributionServDu.queryInfoSaleOrderDistributionByPayOrderNo(infoPayOrderVo);
						// if(infoSaleOrderDistributionList == null){
						// uocMessage.setContent("无销售订单收件人信息表信息");
						// logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单收件人信息表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						// }else{
						// json_out.put("infoSaleOrderDistributionList",
						// infoSaleOrderDistributionList);
						// }

						// 销售订单修订信息表
						InfoSaleOrderEditVo infoSaleOrderEditVo = new InfoSaleOrderEditVo();
						infoSaleOrderEditVo.setSale_order_no(order_no);
						List<InfoSaleOrderEditVo> infoSaleOrderEditList = infoSaleOrderEditServDu
								.queryInfoSaleOrderEditBySaleOrderNo(infoSaleOrderEditVo);
						if (infoSaleOrderEditList == null) {
							uocMessage.setContent("无销售订单修订信息表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单修订信息表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderEditList", infoSaleOrderEditList);
						}

						// 销售订单商品表
						InfoSaleOrderOfrMapVo infoSaleOrderOfrMapVo = new InfoSaleOrderOfrMapVo();
						infoSaleOrderOfrMapVo.setSale_order_no(order_no);
						List<InfoSaleOrderOfrMapVo> infoSaleOrderOfrMapList = infoSaleOrderOfrMapServDu
								.queryInfoSaleOrderOfrMapBySaleOrderNo(infoSaleOrderOfrMapVo);
						if (infoSaleOrderOfrMapList == null) {
							uocMessage.setContent("无销售订单商品表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单商品表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderOfrMapList", infoSaleOrderOfrMapList);
						}

						// 销售订单客户信息表
						InfoSaleOrderPersionVo infoSaleOrderPersionVo = new InfoSaleOrderPersionVo();
						infoSaleOrderPersionVo.setSale_order_no(order_no);
						InfoSaleOrderPersionVo infoSaleOrderPersion = infoSaleOrderPersionServDu
								.getInfoSaleOrderPersionBySaleOrderNo(infoSaleOrderPersionVo);
						if (infoSaleOrderPersion == null) {
							uocMessage.setContent("无销售订单客户信息表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单客户信息表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderPersion", infoSaleOrderPersion);
						}

						// 新表info_sale_order_persion_cert
						InfoSaleOrderPersionCertVo infoSaleOrderPersionCertVo = new InfoSaleOrderPersionCertVo();
						infoSaleOrderPersionCertVo.setSale_order_no(order_no);
						InfoSaleOrderPersionCertVo infoSaleOrderPersionCert = infoSaleOrderPersionCertServDu
								.getInfoSaleOrderPersionCertBySaleOrderNo(infoSaleOrderPersionCertVo);
						if (infoSaleOrderPersionCert == null) {
							uocMessage.setContent("无销售订单客户证件信息表信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单客户证件信息表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderPersionCert", infoSaleOrderPersionCert);
						}

						// 销售订单业务表
						InfoSaleOrderServMapVo infoSaleOrderServMapVo = new InfoSaleOrderServMapVo();
						infoSaleOrderServMapVo.setSale_order_no(order_no);
						List<InfoSaleOrderServMapVo> infoSaleOrderServMapList = infoSaleOrderServMapServDu
								.queryInfoSaleOrderServMapBySaleOrderNo(infoSaleOrderServMapVo);
						if (infoSaleOrderServMapList == null) {
							uocMessage.setContent("无销售订单业务表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单业务表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderServMapList", infoSaleOrderServMapList);
						}

						// 同时查商品订单表
						// 取商品订单，根据销售订单号
						InfoOfrOrderVo ofrPara = new InfoOfrOrderVo();
						ofrPara.setSale_order_no(order_no);
						List<InfoOfrOrderVo> listOfr = infoOfrOrderServDu.queryInfoOfrOrderBySaleOrderNo(ofrPara);
						if (listOfr == null) {
							uocMessage.setContent("无商品订单信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrder", listOfr);
						}

						// 同时查服务订单表
						// 取服务订单，根据销售订单号
						InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
						infoServiceOrderVo.setSale_order_no(order_no);
						List<InfoServiceOrderVo> listService = infoServiceOrderServDu
								.queryInfoServiceOrderByOrderNo(infoServiceOrderVo);
						if (listService == null) {
							uocMessage.setContent("无服务订单信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderList", listService);
						}

						// 同时查支付订单表
						// 取支付订单，根据销售订单号
						InfoPayOrderVo infoPayOrderVo = new InfoPayOrderVo();
						infoPayOrderVo.setRela_order_no(order_no);
						infoPayOrderVo.setRela_order_type("100");
						InfoPayOrderVo infoPayOrderRes = infoPayOrderServDu
								.queryInfoPayOrderByRelaOrderNo(infoPayOrderVo);
						if (infoPayOrderRes == null) {
							uocMessage.setContent("无支付订单");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无支付订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoPayOrder", infoPayOrderRes);
						}

						// 同时查交付订单表
						// 取交付订单，根据销售订单号
						InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
						infoDeliverOrderVo.setSale_order_no(order_no);
						List<InfoDeliverOrderVo> listDeliver = infoDeliverOrderServDu
								.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderVo);
						if (listDeliver == null) {
							uocMessage.setContent("无交付订单");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无交付订单表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoDeliverOrderList", listDeliver);
						}

					} else if (finish_flag.equals("1")) {
						/*
						 * 竣工订单
						 */
						// 销售订单拓展属性横表
						InfoSaleOrderExtHisVo infoSaleOrderExtHisVo = new InfoSaleOrderExtHisVo();
						infoSaleOrderExtHisVo.setSale_order_no(order_no);
						InfoSaleOrderExtHisVo infoSaleOrderExt = infoSaleOrderExtHisServDu
								.getInfoSaleOrderExtHisBySaleOrderNo(infoSaleOrderExtHisVo);
						if (infoSaleOrderExt != null) {
							json_out.put("infoSaleOrderExt", infoSaleOrderExt);
							tableName = "info_sale_ext_h";
							Map<String, Object> mapAttrDesc = infoSaleOrderExtHisServDu.getAttrDesc(infoSaleOrderExt,
									tableName);
							if (mapAttrDesc != null) {
								json_out.put("infoSaleOrderExtTN", mapAttrDesc);
							}
						} else {
							uocMessage.setContent("无销售订单拓展属性横竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单拓展属性横竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						}

						// 销售订单表
						InfoSaleOrderHisVo infoSaleOrderHisVo = new InfoSaleOrderHisVo();
						infoSaleOrderHisVo.setSale_order_no(order_no);
						InfoSaleOrderHisVo infoSaleOrder = infoSaleOrderHisServDu
								.getInfoSaleOrderHisBySaleOrderNo(infoSaleOrderHisVo);
						if (infoSaleOrder == null) {
							uocMessage.setContent("无销售订单竣工表竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单竣工表竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrder", infoSaleOrder);
						}

						// 销售订单费用竣工信息表
						InfoSaleOrderFeeHisVo infoSaleOrderFeeHisVoPara = new InfoSaleOrderFeeHisVo();
						infoSaleOrderFeeHisVoPara.setSale_order_no(order_no);
						InfoSaleOrderFeeHisVo infoSaleOrderFee = infoSaleOrderFeeHisServDu
								.queryInfoSaleOrderFeeHisBySaleOrderNo(infoSaleOrderFeeHisVoPara);
						if (infoSaleOrderFee == null) {
							uocMessage.setContent("无销售订单费用竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单费用竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderFee", infoSaleOrderFee);
						}

						// 销售订单费用竣工信息表 按支付订单号
						// InfoPayOrderHisVo infoPayOrderHisVo = new
						// InfoPayOrderHisVo();
						// infoPayOrderHisVo.setRela_order_no(order_no);
						// infoPayOrderHisVo.setRela_order_type("100");
						// List<InfoSaleOrderFeeHisVo> infoSaleOrderFeeList =
						// infoSaleOrderFeeHisServDu.queryInfoSaleOrderFeeByPayOrder(infoPayOrderHisVo);
						// if(infoSaleOrderFeeList == null){
						// uocMessage.setContent("无销售订单费用竣工信息");
						// logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单费用竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						// }else{
						// json_out.put("infoSaleOrderFeeList",
						// infoSaleOrderFeeList);
						// }

						// 销售订单属性表
						InfoSaleOrderAttrHisVo infoSaleOrderAttrHisVo = new InfoSaleOrderAttrHisVo();
						infoSaleOrderAttrHisVo.setSale_order_no(order_no);
						List<InfoSaleOrderAttrHisVo> infoSaleOrderAttrList = infoSaleOrderAttrHisServDu
								.queryInfoSaleOrderAttrHisList(infoSaleOrderAttrHisVo);
						if (infoSaleOrderAttrList == null) {
							uocMessage.setContent("无销售订单属性竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单属性竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderAttrList", infoSaleOrderAttrList);
						}

						// 销售订单包裹竣工信息表
						InfoSaleOrderDistrDetailHisVo infoSaleOrderDistrDetailHisVo = new InfoSaleOrderDistrDetailHisVo();
						infoSaleOrderDistrDetailHisVo.setSale_order_no(order_no);
						List<InfoSaleOrderDistrDetailHisVo> infoSaleOrderDistrDetailList = infoSaleOrderDistrDetailHisServDu
								.queryInfoSaleOrderDistrDetailHisBySaleOrderNo(infoSaleOrderDistrDetailHisVo);
						if (infoSaleOrderDistrDetailList == null) {
							uocMessage.setContent("无销售订单包裹竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单包裹竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderDistrDetailList", infoSaleOrderDistrDetailList);
						}

						// 销售订单包裹竣工信息表 , 按支付订单号
						// List<InfoSaleOrderDistrDetailHisVo>
						// infoSaleOrderDistrDetailByPayList =
						// infoSaleOrderDistrDetailHisServDu.queryInfoSaleOrderDistrDetailByPayOrderNo(infoPayOrderHisVo);
						// if(infoSaleOrderDistrDetailByPayList == null){
						// uocMessage.setContent("无销售订单包裹竣工信息竣工表竣工信息");
						// logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单包裹竣工信息竣工表竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						// }else{
						// json_out.put("infoSaleOrderDistrDetailByPayList",
						// infoSaleOrderDistrDetailByPayList);
						// }

						// 销售订单收件人竣工信息表
						InfoSaleOrderDistributionHisVo InfoSaleOrderDistributionHisVoPara = new InfoSaleOrderDistributionHisVo();
						InfoSaleOrderDistributionHisVoPara.setSale_order_no(order_no);
						InfoSaleOrderDistributionHisVo infoSaleOrderDistributionHisVo = infoSaleOrderDistributionHisServDu
								.getInfoSaleOrderDistributionHisBySaleOrderNo(InfoSaleOrderDistributionHisVoPara);
						if (infoSaleOrderDistributionHisVo == null) {
							uocMessage.setContent("无销售订单收件人竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单收件人竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderDistribution", infoSaleOrderDistributionHisVo);
						}

						// 销售订单收件人竣工信息表 按支付订单号
						// List<InfoSaleOrderDistributionHisVo>
						// infoSaleOrderDistributionList =
						// infoSaleOrderDistributionHisServDu.queryInfoSaleOrderDistributionByPayOrderNo(infoPayOrderHisVo);
						// if(infoSaleOrderDistributionList == null){
						// uocMessage.setContent("无销售订单收件人竣工信息竣工表竣工信息");
						// logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单收件人竣工信息竣工表竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						// }else{
						// json_out.put("infoSaleOrderDistributionList",
						// infoSaleOrderDistributionList);
						// }

						// 销售订单修订竣工信息表
						InfoSaleOrderEditHisVo infoSaleOrderEditHisHisVo = new InfoSaleOrderEditHisVo();
						infoSaleOrderEditHisHisVo.setSale_order_no(order_no);
						InfoSaleOrderEditHisVo infoSaleOrderEditHis = infoSaleOrderEditHisServDu
								.getInfoSaleOrderEditBySaleOrderNo(infoSaleOrderEditHisHisVo);
						if (infoSaleOrderEditHis == null) {
							uocMessage.setContent("无销售订单修订竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单修订竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderEditHisList", infoSaleOrderEditHis);
						}

						// 销售订单商品表
						InfoSaleOrderOfrMapHisVo infoSaleOrderOfrMapHisVo = new InfoSaleOrderOfrMapHisVo();
						infoSaleOrderOfrMapHisVo.setSale_order_no(order_no);
						List<InfoSaleOrderOfrMapHisVo> infoSaleOrderOfrMapList = infoSaleOrderOfrMapHisServDu
								.queryInfoSaleOrderOfrMapHisBySaleOrderNo(infoSaleOrderOfrMapHisVo);
						if (infoSaleOrderOfrMapList == null) {
							uocMessage.setContent("无销售订单商品竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单商品竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderOfrMapList", infoSaleOrderOfrMapList);
						}

						// 销售订单客户竣工信息表
						InfoSaleOrderPersionHisVo infoSaleOrderPersionHisVo = new InfoSaleOrderPersionHisVo();
						infoSaleOrderPersionHisVo.setSale_order_no(order_no);
						InfoSaleOrderPersionHisVo infoSaleOrderPersion = infoSaleOrderPersionHisServDu
								.getInfoSaleOrderPersionHisBySaleOrderNo(infoSaleOrderPersionHisVo);
						if (infoSaleOrderPersion == null) {
							uocMessage.setContent("无销售订单客户竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单客户竣工信息竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderPersion", infoSaleOrderPersion);
						}

						// info_sale_order_persion_cert_his
						InfoSaleOrderPersionCertHisVo infoSaleOrderPersionCertHisVo = new InfoSaleOrderPersionCertHisVo();
						infoSaleOrderPersionCertHisVo.setSale_order_no(order_no);
						InfoSaleOrderPersionCertHisVo infoSaleOrderPersionCertHis = infoSaleOrderPersionCertHisServDu
								.getInfoSaleOrderPersionCertHis(infoSaleOrderPersionCertHisVo);
						if (infoSaleOrderPersionCertHis == null) {
							uocMessage.setContent("无销售订单客户证件竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单客户证件竣工信息竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderPersionCertHis", infoSaleOrderPersionCertHis);
						}

						// 销售订单业务表
						InfoSaleOrderServMapHisVo infoSaleOrderServMapHisVo = new InfoSaleOrderServMapHisVo();
						infoSaleOrderServMapHisVo.setSale_order_no(order_no);
						List<InfoSaleOrderServMapHisVo> infoSaleOrderServMapList = infoSaleOrderServMapHisServDu
								.queryInfoSaleOrderServMapHisBySaleOrderNo(infoSaleOrderServMapHisVo);
						if (infoSaleOrderServMapList == null) {
							uocMessage.setContent("无销售订单业务竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单业务竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderServMapList", infoSaleOrderServMapList);
						}

						// 同时查商品订单表
						// 取商品订单，根据销售订单号
						InfoOfrOrderHisVo ofrPara = new InfoOfrOrderHisVo();
						ofrPara.setSale_order_no(order_no);
						List<InfoOfrOrderHisVo> listOfr = infoOfrOrderHisServDu
								.queryInfoOfrOrderHisBySaleOrderNo(ofrPara);
						if (listOfr == null) {
							uocMessage.setContent("无商品订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrder", listOfr);
						}

						// 同时查服务订单表
						// 取服务订单，根据销售订单号
						InfoServiceOrderHisVo infoServiceOrderHisVo = new InfoServiceOrderHisVo();
						infoServiceOrderHisVo.setSale_order_no(order_no);
						List<InfoServiceOrderHisVo> listService = infoServiceOrderHisServDu
								.queryInfoServiceOrderHisByOrderNo(infoServiceOrderHisVo);
						if (listService == null) {
							uocMessage.setContent("无服务订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderList", listService);
						}

						// 同时查支付订单竣工表
						// 取支付订单竣工表，根据销售订单号
						InfoPayOrderHisVo infoPayOrderHisVo = new InfoPayOrderHisVo();
						infoPayOrderHisVo.setRela_order_no(order_no);
						infoPayOrderHisVo.setRela_order_type("100");
						InfoPayOrderHisVo infoPayOrderRes = infoPayOrderHisServDu
								.getInfoPayOrderHisByPayOrderNo(infoPayOrderHisVo);
						if (infoPayOrderRes == null) {
							uocMessage.setContent("无支付订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无支付订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoPayOrder", infoPayOrderRes);
						}

						// 同时查交付订单竣工表
						// 取交付订单竣工表，根据销售订单号
						InfoDeliverOrderHisVo infoDeliverOrderHisVo = new InfoDeliverOrderHisVo();
						infoDeliverOrderHisVo.setSale_order_no(order_no);
						List<InfoDeliverOrderHisVo> listDeliver = infoDeliverOrderHisServDu
								.queryInfoDeliverOrderHisBySaleOrderNo(infoDeliverOrderHisVo);
						if (listDeliver == null) {
							uocMessage.setContent("无交付订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无交付订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoDeliverOrderList", listDeliver);
						}
					}

				} else if (query_type.equals("101")) {
					/*
					 * 根据 销售订单号 、商品订单号 取服务订单XXX表
					 */
					/*
					 * 非竣工订单
					 */
					if (finish_flag.equals("0")) {

						// 取服务订单
						InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
						// 按销售订单
						// infoServiceOrderVo.setSale_order_no(order_no);
						// 按服务订单
						infoServiceOrderVo.setServ_order_no(order_no);
						// //按商品订单
						// infoServiceOrderVo.setOfr_order_no(order_no);
						List<InfoServiceOrderVo> listService = infoServiceOrderServDu
								.queryInfoServiceOrderByOrderNo(infoServiceOrderVo);
						if (listService == null) {
							uocMessage.setContent("无服务订单信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderList", listService);
						}

						// 服务订单拓展属性横表
						InfoServiceOrderExtVo InfoServiceOrderExt = new InfoServiceOrderExtVo();
						// InfoServiceOrderExt.setSale_order_no(order_no);
						InfoServiceOrderExt.setServ_order_no(order_no);
						List<InfoServiceOrderExtVo> infoServiceOrderExtList = infoServiceOrderExtServDu
								.queryInfoServiceOrderExtByOrderNo(InfoServiceOrderExt);
						Map<String, Object> infoServiceOrderExtMap = new HashMap<String, Object>();
						if (infoServiceOrderExtList == null) {
							uocMessage.setContent("无服务订单拓展属性横表信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单拓展属性横表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderExt", infoServiceOrderExtList.get(0));
							tableName = "info_serv_ext";
							Map<String, Object> mapAttrDesc = infoServiceOrderExtServDu
									.getAttrDesc(infoServiceOrderExtList.get(0), tableName);
							if (mapAttrDesc != null) {
								json_out.put("infoServiceOrderExtTN", mapAttrDesc);
							}
						}

						// 取服务订单优惠活动
						InfoServiceOrderActivityVo infoServiceOrderActivityVo = new InfoServiceOrderActivityVo();
						// infoServiceOrderActivityVo.setSale_order_no(order_no);
						infoServiceOrderActivityVo.setServ_order_no(order_no);
						// infoServiceOrderActivityVo.setOfr_order_no(order_no);
						List<InfoServiceOrderActivityVo> infoServiceOrderActivityList = infoServiceOrderActivityServDu
								.queryInfoServiceOrderActivityByOrderNo(infoServiceOrderActivityVo);
						if (infoServiceOrderActivityList == null) {
							uocMessage.setContent("无服务订单优惠活动信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单优惠活动信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							InfoServiceOrderActivityVo resultAct=getActivity(infoServiceOrderActivityList.get(0),jsession_id);
							json_out.put("infoServiceOrderActivity", resultAct);
						}

						// 取服务订单协议表
						InfoServiceOrderAgreementVo infoServiceOrderAgreementVo = new InfoServiceOrderAgreementVo();
						// infoServiceOrderAgreementVo.setSale_order_no(order_no);
						infoServiceOrderAgreementVo.setServ_order_no(order_no);
						// infoServiceOrderAgreementVo.setOfr_order_no(order_no);
						List<InfoServiceOrderAgreementVo> infoServiceOrderAgreementList = infoServiceOrderAgreementServDu
								.queryInfoServiceOrderAgreementByOrderNo(infoServiceOrderAgreementVo);
						if (infoServiceOrderAgreementList == null) {
							uocMessage.setContent("无服务订单协议表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单协议表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderAgreementList", infoServiceOrderAgreementList);
						}

						// 取服务订单属性表
						InfoServiceOrderAttrVo infoServiceOrderAttrVo = new InfoServiceOrderAttrVo();
						// infoServiceOrderAttrVo.setSale_order_no(order_no);
						infoServiceOrderAttrVo.setServ_order_no(order_no);

						List<InfoServiceOrderAttrVo> infoServiceOrderAttrList = infoServiceOrderAttrServDu
								.queryInfoServiceOrderAttrByOrderNo(infoServiceOrderAttrVo);
						if (infoServiceOrderAttrList == null) {
							uocMessage.setContent("无服务订单属性表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单属性表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderAttrList", infoServiceOrderAttrList);
						}

						// 取服务订单银行托收表
						InfoServiceOrderCollectionVo infoServiceOrderCollectionVo = new InfoServiceOrderCollectionVo();
						// infoServiceOrderCollectionVo.setSale_order_no(order_no);
						infoServiceOrderCollectionVo.setServ_order_no(order_no);
						// infoServiceOrderCollectionVo.setOfr_order_no(order_no);
						List<InfoServiceOrderCollectionVo> infoServiceOrderCollectionList = infoServiceOrderCollectionServDu
								.queryInfoServiceOrderCollectionByOrderNo(infoServiceOrderCollectionVo);
						if (infoServiceOrderCollectionList == null) {
							uocMessage.setContent("无服务订单银行托收表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单银行托收表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderCollectionList", infoServiceOrderCollectionList);
						}

						// 取服务订单发展人表
						InfoServiceOrderDeveloperVo infoServiceOrderDeveloperVo = new InfoServiceOrderDeveloperVo();
						// infoServiceOrderDeveloperVo.setSale_order_no(order_no);
						infoServiceOrderDeveloperVo.setServ_order_no(order_no);
						// infoServiceOrderDeveloperVo.setOfr_order_no(order_no);
						List<InfoServiceOrderDeveloperVo> infoServiceOrderDeveloperList = infoServiceOrderDeveloperServDu
								.queryInfoServiceOrderDeveloperByOrderNo(infoServiceOrderDeveloperVo);
						if (infoServiceOrderDeveloperList == null) {
							uocMessage.setContent("无服务订单发展人表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单发展人表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderDeveloperList", infoServiceOrderDeveloperList);
						}

						// 取服务订单费用详情表
						InfoServiceOrderFeeVo infoServiceOrderFeeVo = new InfoServiceOrderFeeVo();
						// infoServiceOrderFeeVo.setSale_order_no(order_no);
						infoServiceOrderFeeVo.setServ_order_no(order_no);
						// infoServiceOrderFeeVo.setOfr_order_no(order_no);
						List<InfoServiceOrderFeeVo> infoServiceOrderFeeList = infoServiceOrderFeeServDu
								.queryInfoServiceOrderFeeByOrderNo(infoServiceOrderFeeVo);
						if (infoServiceOrderFeeList == null) {
							uocMessage.setContent("无服务订单费用详情表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单费用详情表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderFeeList", infoServiceOrderFeeList);
						}

						// 取服务订单固网信息表
						InfoServiceOrderFixVo infoServiceOrderFixVo = new InfoServiceOrderFixVo();
						// infoServiceOrderFixVo.setSale_order_no(order_no);
						infoServiceOrderFixVo.setServ_order_no(order_no);
						// infoServiceOrderFixVo.setOfr_order_no(order_no);
						List<InfoServiceOrderFixVo> infoServiceOrderFixList = infoServiceOrderFixServDu
								.queryInfoServiceOrderFixByOrderNo(infoServiceOrderFixVo);
						if (infoServiceOrderFixList == null) {
							uocMessage.setContent("无服务订单固网信息表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单固网信息表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderFixList", infoServiceOrderFixList);
						}

						// 取服务订单靓号信息表
						InfoServiceOrderGoodNbrVo infoServiceOrderGoodNbrVo = new InfoServiceOrderGoodNbrVo();
						// infoServiceOrderGoodNbrVo.setSale_order_no(order_no);
						infoServiceOrderGoodNbrVo.setServ_order_no(order_no);
						// infoServiceOrderGoodNbrVo.setOfr_order_no(order_no);
						List<InfoServiceOrderGoodNbrVo> infoServiceOrderGoodNbrList = infoServiceOrderGoodNbrServDu
								.queryInfoServiceOrderGoodNbrByOrderNo(infoServiceOrderGoodNbrVo);
						if (infoServiceOrderGoodNbrList == null) {
							uocMessage.setContent("无服务订单靓号信息表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单靓号信息表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderGoodNbrList", infoServiceOrderGoodNbrList);
						}

						// 取服务订单担保人表
						InfoServiceOrderGuarantorVo infoServiceOrderGuarantorVo = new InfoServiceOrderGuarantorVo();
						// infoServiceOrderGuarantorVo.setSale_order_no(order_no);
						infoServiceOrderGuarantorVo.setServ_order_no(order_no);
						// infoServiceOrderGuarantorVo.setOfr_order_no(order_no);
						List<InfoServiceOrderGuarantorVo> infoServiceOrderGuarantorList = infoServiceOrderGuarantorServDu
								.queryInfoServiceOrderGuarantorByOrderNo(infoServiceOrderGuarantorVo);
						if (infoServiceOrderGuarantorList == null) {
							uocMessage.setContent("无服务订单担保人表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单担保人表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderGuarantorList", infoServiceOrderGuarantorList);
						}

						// 取服务订单宽带信息表
						InfoServiceOrderM165Vo infoServiceOrderM165Vo = new InfoServiceOrderM165Vo();
						// infoServiceOrderM165Vo.setSale_order_no(order_no);
						infoServiceOrderM165Vo.setServ_order_no(order_no);
						// infoServiceOrderM165Vo.setOfr_order_no(order_no);
						List<InfoServiceOrderM165Vo> infoServiceOrderM165List = infoServiceOrderM165ServDu
								.queryInfoServiceOrderM165ByOrderNo(infoServiceOrderM165Vo);
						if (infoServiceOrderM165List == null) {
							uocMessage.setContent("无服务订单宽带信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单宽带信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderM165List", infoServiceOrderM165List);
						}

						// 取服务订单套餐列表
						InfoServiceOrderPackageVo infoServiceOrderPackageVo = new InfoServiceOrderPackageVo();
						// infoServiceOrderPackageVo.setSale_order_no(order_no);
						infoServiceOrderPackageVo.setServ_order_no(order_no);
						// infoServiceOrderPackageVo.setOfr_order_no(order_no);
						List<InfoServiceOrderPackageVo> infoServiceOrderPackageList = infoServiceOrderPackageServDu
								.queryInfoServiceOrderPackageByOrderNo(infoServiceOrderPackageVo);
						if (infoServiceOrderPackageList == null) {
							uocMessage.setContent("无服务订单套餐列表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单套餐列表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderPackageList", infoServiceOrderPackageList);
						}

						// 取服务订单收费详情表
						InfoServiceOrderPayVo infoServiceOrderPayVo = new InfoServiceOrderPayVo();
						// infoServiceOrderPayVo.setSale_order_no(order_no);
						infoServiceOrderPayVo.setServ_order_no(order_no);
						// infoServiceOrderPayVo.setOfr_order_no(order_no);
						List<InfoServiceOrderPayVo> infoServiceOrderPayList = infoServiceOrderPayServDu
								.queryInfoServiceOrderPayByOrderNo(infoServiceOrderPayVo);
						if (infoServiceOrderPayList == null) {
							uocMessage.setContent("无服务订单收费详情表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单收费详情表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderPayList", infoServiceOrderPayList);
						}

						// 取服务订单客户信息表
						InfoServiceOrderPersionVo infoServiceOrderPersionVo = new InfoServiceOrderPersionVo();
						// infoServiceOrderPersionVo.setSale_order_no(order_no);
						infoServiceOrderPersionVo.setServ_order_no(order_no);
						// infoServiceOrderPersionVo.setOfr_order_no(order_no);
						List<InfoServiceOrderPersionVo> infoServiceOrderPersionList = infoServiceOrderPersionServDu
								.queryInfoServiceOrderPersionByOrderNo(infoServiceOrderPersionVo);
						if (infoServiceOrderPersionList == null) {
							uocMessage.setContent("无服务订单客户信息表信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单客户信息表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderPersionList", infoServiceOrderPersionList);
						}

						// 取服务订单产品列表
						InfoServiceOrderProdMapVo infoServiceOrderProdMapVo = new InfoServiceOrderProdMapVo();
						// infoServiceOrderProdMapVo.setSale_order_no(order_no);
						infoServiceOrderProdMapVo.setServ_order_no(order_no);
						// infoServiceOrderProdMapVo.setOfr_order_no(order_no);
						List<InfoServiceOrderProdMapVo> infoServiceOrderProdMapList = infoServiceOrderProdMapServDu
								.queryInfoServiceOrderProdMapByOrderNo(infoServiceOrderProdMapVo);
						if (infoServiceOrderProdMapList == null) {
							uocMessage.setContent("无服务订单产品列信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单产品列信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderProdMapList", infoServiceOrderProdMapList);
						}

						// 取服务订单终端
						InfoServiceOrderTerminalVo InfoServiceOrderTerminal = new InfoServiceOrderTerminalVo();
						// InfoServiceOrderTerminal.setSale_order_no(order_no);
						InfoServiceOrderTerminal.setServ_order_no(order_no);
						List<InfoServiceOrderTerminalVo> infoServiceOrderTerminalList = infoServiceOrderTerminalServDu
								.queryInfoServiceOrderTerminalByOrderNo(InfoServiceOrderTerminal);
						if (infoServiceOrderTerminalList == null) {
							uocMessage.setContent("无服务订单终端信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单终端信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderTerminalList", infoServiceOrderTerminalList);
						}

						// 取服务订单SIM卡
						InfoServiceOrderSimCardVo InfoServiceOrderSimCard = new InfoServiceOrderSimCardVo();
						// InfoServiceOrderSimCard.setSale_order_no(order_no);
						InfoServiceOrderSimCard.setServ_order_no(order_no);
						List<InfoServiceOrderSimCardVo> InfoServiceOrderSimCardList = infoServiceOrderSimCardServDu
								.queryInfoServiceOrderSimCardByOrderNo(InfoServiceOrderSimCard);
						if (InfoServiceOrderSimCardList == null) {
							uocMessage.setContent("无服务订单SIM卡 信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单SIM卡 信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("InfoServiceOrderSimCardList", InfoServiceOrderSimCardList);
						}

						// 取人工任务实例表
						ProcInstTaskInstVo procInstTaskInst = new ProcInstTaskInstVo();
						procInstTaskInst.setOrder_no(order_no);
						ProcInstTaskInstVo procInstTaskInstVo = procInstTaskInstServDu
								.queryProcInstTaskInstByTaskState(procInstTaskInst);
						if (procInstTaskInstVo == null) {
							uocMessage.setContent("无人工任务实例信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无人工任务实例信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("procInstTaskInstVo", procInstTaskInstVo);
						}

						// 同时查销售订单
						InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
						if (listService != null) {
							infoSaleOrderVo.setSale_order_no(listService.get(0).getSale_order_no());
							infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
							if (infoSaleOrderVo == null) {
								uocMessage.setContent("无销售订单信息");
								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							} else {
								json_out.put("infoSaleOrder", infoSaleOrderVo);
								// 同时查商品订单
								InfoOfrOrderVo infoOfrOrderVo = new InfoOfrOrderVo();
								infoOfrOrderVo.setSale_order_no(infoSaleOrderVo.getSale_order_no());
								List<InfoOfrOrderVo> infoOfrOrderList = infoOfrOrderServDu
										.queryInfoOfrOrderBySaleOrderNo(infoOfrOrderVo);
								if (infoOfrOrderList == null) {
									uocMessage.setContent("无商品订单信息");
									logger.info(
											">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
								} else {
									json_out.put("infoOfrOrderList", infoOfrOrderList);

									List<OfrOrderAndServiceOrderVo> ofrOrderAndServiceOrderList = new ArrayList<OfrOrderAndServiceOrderVo>();
									for (InfoOfrOrderVo ofrOrder : infoOfrOrderList) {
										OfrOrderAndServiceOrderVo ofrOrderAndServiceOrder = new OfrOrderAndServiceOrderVo();
										ofrOrderAndServiceOrder.setOfr_order_no(ofrOrder.getOfr_order_no());
										InfoServiceOrderVo serviceOrder = new InfoServiceOrderVo();
										serviceOrder.setOfr_order_no(ofrOrder.getOfr_order_no());
										List<InfoServiceOrderVo> serviceOrderList = infoServiceOrderServDu
												.queryInfoServiceOrderByOfrOrderNo(serviceOrder);
										if (serviceOrderList != null) {
											ofrOrderAndServiceOrder
													.setServ_order_no(serviceOrderList.get(0).getServ_order_no());
										}
										ofrOrderAndServiceOrderList.add(ofrOrderAndServiceOrder);
									}
									json_out.put("ofrOrderAndServiceOrderList", ofrOrderAndServiceOrderList);
								}

							}
						}
						// 同时查交付订单表
						// 取交付订单，根据销售订单号
						InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
						infoDeliverOrderVo.setSale_order_no(order_no);
						List<InfoDeliverOrderVo> listDeliver = infoDeliverOrderServDu
								.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderVo);
						if (listDeliver == null) {
							uocMessage.setContent("无交付订单");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无交付订单表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoDeliverOrderList", listDeliver);
						}

						// 判断业务类型oper_code=open01,open02，如果经过S00021环节，则显示审核通过，如果经过S00022环节则显示审核未通过，否则显示未审核
					if (("open01").equals(listService.get(0).getOper_code()) || ("open02").equals(listService.get(0).getOper_code())) {
						ProcInstTaskDealRecordVo passRecord = procModServDu.queryLastTaskDealRecord(order_no, "S00021", query_type,
								listService.get(0).getSale_order_no(), finish_flag, listService.get(0).getOper_code());
						ProcInstTaskDealRecordVo referRecord = procModServDu.queryLastTaskDealRecord(order_no, "S00022", query_type,
									listService.get(0).getSale_order_no(), finish_flag, listService.get(0).getOper_code());
							if (passRecord != null) {
								json_out.put("auditCondition", "审核通过");
							} else if (referRecord != null) {
								json_out.put("auditCondition", "审核未通过");
							} else {
								json_out.put("auditCondition", "未审核");
							}
						}

					} else if (finish_flag.equals("1")) {
						/*
						 * 竣工订单
						 */

						// 取服务订单
						InfoServiceOrderHisVo infoServiceOrderHisVo = new InfoServiceOrderHisVo();
						// 按销售订单
						// infoServiceOrderHisVo.setSale_order_no(order_no);
						// 按服务订单
						infoServiceOrderHisVo.setServ_order_no(order_no);
						// //按商品订单
						// infoServiceOrderHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderHisVo> listService = infoServiceOrderHisServDu
								.queryInfoServiceOrderHisByOrderNo(infoServiceOrderHisVo);
						if (listService == null) {
							uocMessage.setContent("无服务订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderList", listService);
						}

						// 服务订单拓展属性横表
						InfoServiceOrderExtHisVo InfoServiceOrderExt = new InfoServiceOrderExtHisVo();
						// InfoServiceOrderExt.setSale_order_no(order_no);
						InfoServiceOrderExt.setServ_order_no(order_no);
						InfoServiceOrderExtHisVo infoServiceOrderExt = infoServiceOrderExtHisServDu
								.queryInfoServiceOrderExtByOrderNo(InfoServiceOrderExt);

						if (infoServiceOrderExt == null) {
							uocMessage.setContent("无服务订单拓展属性横竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单拓展属性横竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderExt", infoServiceOrderExt);
							tableName = "info_serv_ext_his";
							Map<String, Object> mapAttrDesc = infoServiceOrderExtHisServDu
									.getAttrDescHis(infoServiceOrderExt, tableName);
							if (mapAttrDesc != null) {
								json_out.put("infoServiceOrderExtTN", mapAttrDesc);
							}
						}

						// 取服务订单优惠活动
						InfoServiceOrderActivityHisVo infoServiceOrderActivityHisVo = new InfoServiceOrderActivityHisVo();
						// infoServiceOrderActivityHisVo.setSale_order_no(order_no);
						infoServiceOrderActivityHisVo.setServ_order_no(order_no);
						// infoServiceOrderActivityHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderActivityHisVo> infoServiceOrderActivityList = infoServiceOrderActivityHisServDu
								.queryInfoServiceOrderActivityHisByOrderNo(infoServiceOrderActivityHisVo);
						if (infoServiceOrderActivityList == null) {
							uocMessage.setContent("无服务订单优惠活动竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单优惠活动竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							InfoServiceOrderActivityVo vo=new InfoServiceOrderActivityVo();
							BeanUtils.copyProperties(infoServiceOrderActivityList.get(0), vo);
							InfoServiceOrderActivityVo resultAct=getActivity(vo,jsession_id);
							json_out.put("infoServiceOrderActivity", resultAct);
						}

						// 取服务订单协议表
						InfoServiceOrderAgreementHisVo infoServiceOrderAgreementHisVo = new InfoServiceOrderAgreementHisVo();
						// infoServiceOrderAgreementHisVo.setSale_order_no(order_no);
						infoServiceOrderAgreementHisVo.setServ_order_no(order_no);
						// infoServiceOrderAgreementHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderAgreementHisVo> infoServiceOrderAgreementList = infoServiceOrderAgreementHisServDu
								.queryInfoServiceOrderAgreementHisByOrderNo(infoServiceOrderAgreementHisVo);
						if (infoServiceOrderAgreementList == null) {
							uocMessage.setContent("无服务订单协议竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单协议竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderAgreementList", infoServiceOrderAgreementList);
						}

						// 取服务订单属性表
						InfoServiceOrderAttrHisVo infoServiceOrderAttrHisVo = new InfoServiceOrderAttrHisVo();
						// infoServiceOrderAttrHisVo.setSale_order_no(order_no);
						infoServiceOrderAttrHisVo.setServ_order_no(order_no);

						List<InfoServiceOrderAttrHisVo> infoServiceOrderAttrList = infoServiceOrderAttrHisServDu
								.queryInfoServiceOrderAttrHisByOrderNo(infoServiceOrderAttrHisVo);
						if (infoServiceOrderAttrList == null) {
							uocMessage.setContent("无服务订单属性竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单属性竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderAttrList", infoServiceOrderAttrList);
						}

						// 取服务订单银行托收表
						InfoServiceOrderCollectionHisVo infoServiceOrderCollectionHisVo = new InfoServiceOrderCollectionHisVo();
						// infoServiceOrderCollectionHisVo.setSale_order_no(order_no);
						infoServiceOrderCollectionHisVo.setServ_order_no(order_no);
						// infoServiceOrderCollectionHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderCollectionHisVo> infoServiceOrderCollectionList = infoServiceOrderCollectionHisServDu
								.queryInfoServiceOrderCollectionHisByOrderNo(infoServiceOrderCollectionHisVo);
						if (infoServiceOrderCollectionList == null) {
							uocMessage.setContent("无服务订单银行托收竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单银行托收竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderCollectionList", infoServiceOrderCollectionList);
						}

						// 取服务订单发展人表
						InfoServiceOrderDeveloperHisVo infoServiceOrderDeveloperHisVo = new InfoServiceOrderDeveloperHisVo();
						// infoServiceOrderDeveloperHisVo.setSale_order_no(order_no);
						infoServiceOrderDeveloperHisVo.setServ_order_no(order_no);
						// infoServiceOrderDeveloperHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderDeveloperHisVo> infoServiceOrderDeveloperList = infoServiceOrderDeveloperHisServDu
								.queryInfoServiceOrderDeveloperHisByOrderNo(infoServiceOrderDeveloperHisVo);
						if (infoServiceOrderDeveloperList == null) {
							uocMessage.setContent("无服务订单发展人竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单发展人竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderDeveloperList", infoServiceOrderDeveloperList);
						}

						// 取服务订单费用详情表
						InfoServiceOrderFeeHisVo infoServiceOrderFeeHisVo = new InfoServiceOrderFeeHisVo();
						// infoServiceOrderFeeHisVo.setSale_order_no(order_no);
						infoServiceOrderFeeHisVo.setServ_order_no(order_no);
						// infoServiceOrderFeeHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderFeeHisVo> infoServiceOrderFeeList = infoServiceOrderFeeHisServDu
								.queryInfoServiceOrderFeeHisByOrderNo(infoServiceOrderFeeHisVo);
						if (infoServiceOrderFeeList == null) {
							uocMessage.setContent("无服务订单费用详情竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单费用详情竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderFeeList", infoServiceOrderFeeList);
						}

						// 销售订单收件人信息表
						InfoSaleOrderDistributionHisVo InfoSaleOrderDistributionHisVoPara = new InfoSaleOrderDistributionHisVo();
						InfoSaleOrderDistributionHisVoPara.setSale_order_no(listService.get(0).getSale_order_no());
						InfoSaleOrderDistributionHisVo infoSaleOrderDistributionHisVo = infoSaleOrderDistributionHisServDu
								.getInfoSaleOrderDistributionHisBySaleOrderNo(InfoSaleOrderDistributionHisVoPara);
						if (infoSaleOrderDistributionHisVo == null) {
							uocMessage.setContent("无销售订单收件人信息表信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单收件人信息表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderDistribution", infoSaleOrderDistributionHisVo);
						}

						// 取服务订单固网信息表
						InfoServiceOrderFixHisVo infoServiceOrderFixHisVo = new InfoServiceOrderFixHisVo();
						// infoServiceOrderFixHisVo.setSale_order_no(order_no);
						infoServiceOrderFixHisVo.setServ_order_no(order_no);
						// infoServiceOrderFixHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderFixHisVo> infoServiceOrderFixList = infoServiceOrderFixHisServDu
								.queryInfoServiceOrderFixHisByOrderNo(infoServiceOrderFixHisVo);
						if (infoServiceOrderFixList == null) {
							uocMessage.setContent("无服务订单固网信息竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单固网信息竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderFixList", infoServiceOrderFixList);
						}

						// 取服务订单靓号信息表
						InfoServiceOrderGoodNbrHisVo infoServiceOrderGoodNbrHisVo = new InfoServiceOrderGoodNbrHisVo();
						// infoServiceOrderGoodNbrHisVo.setSale_order_no(order_no);
						infoServiceOrderGoodNbrHisVo.setServ_order_no(order_no);
						// infoServiceOrderGoodNbrHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderGoodNbrHisVo> infoServiceOrderGoodNbrList = infoServiceOrderGoodNbrHisServDu
								.queryInfoServiceOrderGoodNbrHisByOrderNo(infoServiceOrderGoodNbrHisVo);
						if (infoServiceOrderGoodNbrList == null) {
							uocMessage.setContent("无服务订单靓号信息竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单靓号信息竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderGoodNbrList", infoServiceOrderGoodNbrList);
						}

						// 取服务订单担保人表
						InfoServiceOrderGuarantorHisVo infoServiceOrderGuarantorHisVo = new InfoServiceOrderGuarantorHisVo();
						// infoServiceOrderGuarantorHisVo.setSale_order_no(order_no);
						infoServiceOrderGuarantorHisVo.setServ_order_no(order_no);
						// infoServiceOrderGuarantorHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderGuarantorHisVo> infoServiceOrderGuarantorList = infoServiceOrderGuarantorHisServDu
								.queryInfoServiceOrderGuarantorHisByOrderNo(infoServiceOrderGuarantorHisVo);
						if (infoServiceOrderGuarantorList == null) {
							uocMessage.setContent("无服务订单担保人竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单担保人竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderGuarantorList", infoServiceOrderGuarantorList);
						}

						// 取服务订单宽带信息表
						InfoServiceOrderM165HisVo infoServiceOrderM165HisVo = new InfoServiceOrderM165HisVo();
						// infoServiceOrderM165HisVo.setSale_order_no(order_no);
						infoServiceOrderM165HisVo.setServ_order_no(order_no);
						// infoServiceOrderM165HisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderM165HisVo> infoServiceOrderM165List = infoServiceOrderM165HisServDu
								.queryInfoServiceOrderM165HisByOrderNo(infoServiceOrderM165HisVo);
						if (infoServiceOrderM165List == null) {
							uocMessage.setContent("无服务订单宽带竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单宽带竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderM165List", infoServiceOrderM165List);
						}

						// 取服务订单套餐列表
						InfoServiceOrderPackageHisVo infoServiceOrderPackageHisVo = new InfoServiceOrderPackageHisVo();
						// infoServiceOrderPackageHisVo.setSale_order_no(order_no);
						infoServiceOrderPackageHisVo.setServ_order_no(order_no);
						// infoServiceOrderPackageHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderPackageHisVo> infoServiceOrderPackageList = infoServiceOrderPackageHisServDu
								.queryInfoServiceOrderPackageHisByOrderNo(infoServiceOrderPackageHisVo);
						if (infoServiceOrderPackageList == null) {
							uocMessage.setContent("无服务订单套餐列竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单套餐列竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderPackageList", infoServiceOrderPackageList);
						}

						// 取服务订单收费详情表
						InfoServiceOrderPayHisVo infoServiceOrderPayHisVo = new InfoServiceOrderPayHisVo();
						// infoServiceOrderPayHisVo.setSale_order_no(order_no);
						infoServiceOrderPayHisVo.setServ_order_no(order_no);
						// infoServiceOrderPayHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderPayHisVo> infoServiceOrderPayList = infoServiceOrderPayHisServDu
								.queryInfoServiceOrderPayHisByOrderNo(infoServiceOrderPayHisVo);
						if (infoServiceOrderPayList == null) {
							uocMessage.setContent("无服务订单收费详情竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单收费详情竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderPayList", infoServiceOrderPayList);
						}

						// 取服务订单客户信息表
						InfoServiceOrderPersionHisVo infoServiceOrderPersionHisVo = new InfoServiceOrderPersionHisVo();
						// infoServiceOrderPersionHisVo.setSale_order_no(order_no);
						infoServiceOrderPersionHisVo.setServ_order_no(order_no);
						// infoServiceOrderPersionHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderPersionHisVo> infoServiceOrderPersionList = infoServiceOrderPersionHisServDu
								.queryInfoServiceOrderPersionHisByOrderNo(infoServiceOrderPersionHisVo);
						if (infoServiceOrderPersionList == null) {
							uocMessage.setContent("无服务订单客户信息竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单客户信息竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderPersionList", infoServiceOrderPersionList);
						}

						// 取服务订单产品列表
						InfoServiceOrderProdMapHisVo infoServiceOrderProdMapHisVo = new InfoServiceOrderProdMapHisVo();
						// infoServiceOrderProdMapHisVo.setSale_order_no(order_no);
						infoServiceOrderProdMapHisVo.setServ_order_no(order_no);
						// infoServiceOrderProdMapHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderProdMapHisVo> infoServiceOrderProdMapList = infoServiceOrderProdMapHisServDu
								.queryInfoServiceOrderProdMapHisByOrderNo(infoServiceOrderProdMapHisVo);
						if (infoServiceOrderProdMapList == null) {
							uocMessage.setContent("无服务订单产品列竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单产品列竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderProdMapList", infoServiceOrderProdMapList);
						}

						// 取服务订单终端
						InfoServiceOrderTerminalHisVo InfoServiceOrderTerminal = new InfoServiceOrderTerminalHisVo();
						// InfoServiceOrderTerminal.setSale_order_no(order_no);
						InfoServiceOrderTerminal.setServ_order_no(order_no);
						List<InfoServiceOrderTerminalHisVo> infoServiceOrderTerminalList = infoServiceOrderTerminalHisServDu
								.queryInfoServiceOrderTerminalHisByOrderNo(InfoServiceOrderTerminal);
						if (infoServiceOrderTerminalList == null) {
							uocMessage.setContent("无服务订单终端竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单终端竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderTerminalList", infoServiceOrderTerminalList);
						}

						// 取服务订单SIM卡
						InfoServiceOrderSimCardHisVo InfoServiceOrderSimCard = new InfoServiceOrderSimCardHisVo();
						// InfoServiceOrderSimCard.setSale_order_no(order_no);
						InfoServiceOrderSimCard.setServ_order_no(order_no);
						List<InfoServiceOrderSimCardHisVo> InfoServiceOrderSimCardList = infoServiceOrderSimCardHisServDu
								.queryInfoServiceOrderSimCardHisByOrderNo(InfoServiceOrderSimCard);
						if (InfoServiceOrderSimCardList == null) {
							uocMessage.setContent("无服务订单SIM卡 竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单SIM卡竣工 信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("InfoServiceOrderSimCardList", InfoServiceOrderSimCardList);
						}

						// 取人工任务实例竣工表
						ProcInstTaskInstHisVo procInstTaskInstHis = new ProcInstTaskInstHisVo();
						procInstTaskInstHis.setOrder_no(order_no);
						ProcInstTaskInstHisVo procInstTaskInstHisVo = procInstTaskInstHisServDu
								.queryProcInstTaskInstHisByOrderNo(procInstTaskInstHis);
						if (procInstTaskInstHisVo == null) {
							uocMessage.setContent("无人工任务实例竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无人工任务实例竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("procInstTaskInstHisVo", procInstTaskInstHisVo);
						}

						// 同时查销售订单
						InfoSaleOrderHisVo infoSaleOrderHisVo = new InfoSaleOrderHisVo();
						if (listService != null) {
							infoSaleOrderHisVo.setSale_order_no(listService.get(0).getSale_order_no());
							infoSaleOrderHisVo = infoSaleOrderHisServDu
									.getInfoSaleOrderHisBySaleOrderNo(infoSaleOrderHisVo);
							if (infoSaleOrderHisVo == null) {
								uocMessage.setContent("无销售订单信息");
								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							} else {
								json_out.put("infoSaleOrder", infoSaleOrderHisVo);
								// 同时查商品订单
								InfoOfrOrderHisVo infoOfrOrderHisVo = new InfoOfrOrderHisVo();
								infoOfrOrderHisVo.setSale_order_no(infoSaleOrderHisVo.getSale_order_no());
								List<InfoOfrOrderHisVo> infoOfrOrderHisList = infoOfrOrderHisServDu
										.queryInfoOfrOrderHisBySaleOrderNo(infoOfrOrderHisVo);
								if (infoOfrOrderHisList == null) {
									uocMessage.setContent("无商品订单信息");
									logger.info(
											">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
								} else {
									json_out.put("infoOfrOrderList", infoOfrOrderHisList);

									List<OfrOrderAndServiceOrderVo> ofrOrderAndServiceOrderList = new ArrayList<OfrOrderAndServiceOrderVo>();
									for (InfoOfrOrderHisVo ofrOrder : infoOfrOrderHisList) {
										OfrOrderAndServiceOrderVo ofrOrderAndServiceOrder = new OfrOrderAndServiceOrderVo();
										ofrOrderAndServiceOrder.setOfr_order_no(ofrOrder.getOfr_order_no());
										InfoServiceOrderHisVo serviceOrder = new InfoServiceOrderHisVo();
										serviceOrder.setOfr_order_no(ofrOrder.getOfr_order_no());
										List<InfoServiceOrderHisVo> serviceOrderList = infoServiceOrderHisServDu
												.queryInfoServiceOrderHisByOrderNo(serviceOrder);
										if (serviceOrderList != null) {
											ofrOrderAndServiceOrder
													.setServ_order_no(serviceOrderList.get(0).getServ_order_no());
										}
										ofrOrderAndServiceOrderList.add(ofrOrderAndServiceOrder);
									}
									json_out.put("ofrOrderAndServiceOrderList", ofrOrderAndServiceOrderList);
								}

							}
						}
						// 同时查交付订单竣工表
						// 取交付订单竣工表，根据销售订单号
						InfoDeliverOrderHisVo infoDeliverOrderHisVo = new InfoDeliverOrderHisVo();
						infoDeliverOrderHisVo.setSale_order_no(order_no);
						List<InfoDeliverOrderHisVo> listDeliver = infoDeliverOrderHisServDu
								.queryInfoDeliverOrderHisBySaleOrderNo(infoDeliverOrderHisVo);
						if (listDeliver == null) {
							uocMessage.setContent("无交付订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无交付订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoDeliverOrderList", listDeliver);
						}

						// 判断业务类型oper_code=open01,open02，如果经过S00021环节，则显示审核通过，如果经过S00022环节则显示审核未通过，否则显示未审核
					if (("open01").equals(listService.get(0).getOper_code()) || ("open02").equals(listService.get(0).getOper_code())) {
						ProcInstTaskDealRecordVo passRecord = procModServDu.queryLastTaskDealRecord(order_no, "S00021", query_type,
								listService.get(0).getSale_order_no(), finish_flag, listService.get(0).getOper_code());
						ProcInstTaskDealRecordVo referRecord = procModServDu.queryLastTaskDealRecord(order_no, "S00022", query_type,
									listService.get(0).getSale_order_no(), finish_flag, listService.get(0).getOper_code());
							if (passRecord != null) {
								json_out.put("auditCondition", "审核通过");
							} else if (referRecord != null) {
								json_out.put("auditCondition", "审核未通过");
							} else {
								json_out.put("auditCondition", "未审核");
							}
						}
					}

				} else if (query_type.equals("102")) {
					// 未竣工表
					if ("0".equals(finish_flag)) {
						// 取支付订单 ,根据销售订单号
						InfoPayOrderVo infoPayOrderVo = new InfoPayOrderVo();
						infoPayOrderVo.setRela_order_no(order_no);
						infoPayOrderVo.setRela_order_type("100");
						InfoPayOrderVo infoPayOrderRes = infoPayOrderServDu
								.queryInfoPayOrderByRelaOrderNo(infoPayOrderVo);
						if (infoPayOrderRes == null) {
							uocMessage.setContent("无支付订单");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无支付订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoPayOrder", infoPayOrderRes);
						}
					}
					// 竣工表
					else if ("1".equals(finish_flag)) {
						// 取支付订单 ,根据销售订单号
						InfoPayOrderHisVo infoPayOrderHisVo = new InfoPayOrderHisVo();
						infoPayOrderHisVo.setRela_order_no(order_no);
						infoPayOrderHisVo.setRela_order_type("100");
						InfoPayOrderHisVo infoPayOrderRes = infoPayOrderHisServDu
								.getInfoPayOrderHisByPayOrderNo(infoPayOrderHisVo);
						if (infoPayOrderRes == null) {
							uocMessage.setContent("无支付订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无支付订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoPayOrder", infoPayOrderRes);
						}
					}

				} else if (query_type.equals("103")) {
					// 未竣工订单
					if ("0".equals(finish_flag)) {
						// 取交付订单,根据销售订单号
						InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
						infoDeliverOrderVo.setSale_order_no(order_no);
						List<InfoDeliverOrderVo> listDeliver = infoDeliverOrderServDu
								.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderVo);
						if (listDeliver == null) {
							uocMessage.setContent("无交付订单");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无交付订单表信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoDeliverOrderList", listDeliver);
						}
					}
					// 竣工订单
					else if ("1".equals(finish_flag)) {
						// 取交付订单竣工表,根据销售订单号
						InfoDeliverOrderHisVo infoDeliverOrderHisVo = new InfoDeliverOrderHisVo();
						infoDeliverOrderHisVo.setSale_order_no(order_no);
						List<InfoDeliverOrderHisVo> listDeliver = infoDeliverOrderHisServDu
								.queryInfoDeliverOrderHisBySaleOrderNo(infoDeliverOrderHisVo);
						if (listDeliver == null) {
							uocMessage.setContent("无交付订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无交付订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoDeliverOrderList", listDeliver);
						}
					}

				} else if (query_type.equals("104")) {

					if (finish_flag.equals("0")) {

						// 取商品订单，根据销售订单号
						InfoOfrOrderVo ofrPara = new InfoOfrOrderVo();
						// ofrPara.setSale_order_no(order_no);
						// List<InfoOfrOrderVo> listOfr =
						// infoOfrOrderServDu.queryInfoOfrOrderBySaleOrderNo(ofrPara);
						ofrPara.setOfr_order_no(order_no);
						InfoOfrOrderVo listOfr = infoOfrOrderServDu.getInfoOfrOrderByOfrOrderNo(ofrPara);
						if (listOfr == null) {
							uocMessage.setContent("无商品订单信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrderList", listOfr);
						}

						// 取商品订单一次性费用发票表，根据销售订单号
						InfoOfrOrderInvoiceVo infoOfrOrderInvoiceVo = new InfoOfrOrderInvoiceVo();
						// infoOfrOrderInvoiceVo.setSale_order_no(order_no);
						infoOfrOrderInvoiceVo.setOfr_order_no(order_no);
						List<InfoOfrOrderInvoiceVo> infoOfrOrderInvoiceList = infoOfrOrderInvoiceServDu
								.queryInfoOfrOrderInvoiceByOrderNo(infoOfrOrderInvoiceVo);
						if (infoOfrOrderInvoiceList == null) {
							uocMessage.setContent("无商品订单一次性费用发票信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单一次性费用发票信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrderInvoiceList", infoOfrOrderInvoiceList);
						}

						// 取商品订单收费详情表，根据销售订单号
						InfoOfrOrderPayVo infoOfrOrderPayVo = new InfoOfrOrderPayVo();
						// infoOfrOrderPayVo.setSale_order_no(order_no);
						infoOfrOrderPayVo.setOfr_order_no(order_no);
						List<InfoOfrOrderPayVo> infoOfrOrderPayList = infoOfrOrderPayServDu
								.queryInfoOfrOrderPayByOrderNo(infoOfrOrderPayVo);
						if (infoOfrOrderPayList == null) {
							uocMessage.setContent("无商品订单收费详情信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单收费详情信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrderPayList", infoOfrOrderPayList);
						}

						// 取商品订单费用详情表，根据销售订单号
						InfoOfrOrderFeeVo infoOfrOrderFeeVo = new InfoOfrOrderFeeVo();
						// infoOfrOrderFeeVo.setSale_order_no(order_no);
						infoOfrOrderFeeVo.setOfr_order_no(order_no);
						List<InfoOfrOrderFeeVo> infoOfrOrderFeeList = infoOfrOrderFeeServDu
								.queryInfoOfrOrderFeeByOrderNo(infoOfrOrderFeeVo);
						if (infoOfrOrderFeeList == null) {
							uocMessage.setContent("无商品订单费用详情信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单费用详情信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrderFeeList", infoOfrOrderFeeList);
						}

						// 取服务订单，根据销售订单
						InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
						// infoServiceOrderVo.setSale_order_no(order_no);
						infoServiceOrderVo.setOfr_order_no(order_no);
						List<InfoServiceOrderVo> listService = infoServiceOrderServDu
								.queryInfoServiceOrderByOrderNo(infoServiceOrderVo);
						if (listService == null) {
							uocMessage.setContent("无服务订单信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderList", listService);
						}

					} else if (finish_flag.equals("1")) {
						// 取商品订单，根据销售订单号
						InfoOfrOrderHisVo ofrPara = new InfoOfrOrderHisVo();
						ofrPara.setSale_order_no(order_no);
						List<InfoOfrOrderHisVo> listOfr = infoOfrOrderHisServDu.queryInfoOfrOrderHisByOrderNo(ofrPara);
						if (listOfr == null) {
							uocMessage.setContent("无商品订单信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrderList", listOfr);
						}

						// 取商品订单一次性费用发票表，根据销售订单号
						InfoOfrOrderInvoiceHisVo infoOfrOrderInvoiceHisVo = new InfoOfrOrderInvoiceHisVo();
						// infoOfrOrderInvoiceHisVo.setSale_order_no(order_no);
						infoOfrOrderInvoiceHisVo.setOfr_order_no(order_no);
						List<InfoOfrOrderInvoiceHisVo> infoOfrOrderInHisVoiceList = infoOfrOrderInvoiceHisServDu
								.queryInfoOfrOrderInvoiceByOrderNo(infoOfrOrderInvoiceHisVo);
						if (infoOfrOrderInHisVoiceList == null) {
							uocMessage.setContent("无商品订单一次性费用发票信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单一次性费用发票信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrderInHisVoiceList", infoOfrOrderInHisVoiceList);
						}

						// 取商品订单收费详情表，根据销售订单号
						InfoOfrOrderPayHisVo infoOfrOrderPayHisVo = new InfoOfrOrderPayHisVo();
						// infoOfrOrderPayHisVo.setSale_order_no(order_no);
						infoOfrOrderPayHisVo.setOfr_order_no(order_no);
						List<InfoOfrOrderPayHisVo> infoOfrOrderPayList = infoOfrOrderPayHisServDu
								.queryInfoOfrOrderPayHisByOrderNo(infoOfrOrderPayHisVo);
						if (infoOfrOrderPayList == null) {
							uocMessage.setContent("无商品订单收费详情信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单收费详情信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrderPayList", infoOfrOrderPayList);
						}

						// 取商品订单费用详情表，根据销售订单号
						InfoOfrOrderFeeHisVo infoOfrOrderFeeHisVo = new InfoOfrOrderFeeHisVo();
						// infoOfrOrderFeeHisVo.setSale_order_no(order_no);
						infoOfrOrderFeeHisVo.setOfr_order_no(order_no);
						List<InfoOfrOrderFeeHisVo> infoOfrOrderFeeList = infoOfrOrderFeeHisServDu
								.queryInfoOfrOrderFeeHisByOrderNo(infoOfrOrderFeeHisVo);
						if (infoOfrOrderFeeList == null) {
							uocMessage.setContent("无商品订单费用详情信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单费用详情信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrderFeeList", infoOfrOrderFeeList);
						}

						// 取服务订单，根据销售订单
						InfoServiceOrderHisVo infoServiceOrderHisVo = new InfoServiceOrderHisVo();
						// infoServiceOrderHisVo.setSale_order_no(order_no);
						infoServiceOrderHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderHisVo> listService = infoServiceOrderHisServDu
								.queryInfoServiceOrderHisByOrderNo(infoServiceOrderHisVo);
						if (listService == null) {
							uocMessage.setContent("无服务订单信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderList", listService);
						}

					}
				}
			} else {
				if (query_type.equals("100")) {
					// 销售订单拓展属性横表
					InfoSaleOrderExtVo infoSaleOrderExtVo = new InfoSaleOrderExtVo();
					infoSaleOrderExtVo.setSale_order_no(order_no);
					InfoSaleOrderExtVo infoSaleOrderExt = infoSaleOrderExtServDu
							.getInfoSaleOrderExtBySaleOrderNo(infoSaleOrderExtVo);
					if (infoSaleOrderExt != null) {
						json_out.put("infoSaleOrderExt", infoSaleOrderExt);
						tableName = "info_sale_ext";
						Map<String, Object> mapAttrDesc = infoSaleOrderExtServDu.getAttrDesc(infoSaleOrderExt,
								tableName);
						if (mapAttrDesc != null) {
							json_out.put("infoSaleOrderExtTN", mapAttrDesc);
						}
					} else {
						// 销售订单拓展属性横表
						InfoSaleOrderExtHisVo infoSaleOrderExtHisVo = new InfoSaleOrderExtHisVo();
						infoSaleOrderExtHisVo.setSale_order_no(order_no);
						InfoSaleOrderExtHisVo infoSaleOrderExtTemp = infoSaleOrderExtHisServDu
								.getInfoSaleOrderExtHisBySaleOrderNo(infoSaleOrderExtHisVo);
						if (infoSaleOrderExtTemp != null) {
							json_out.put("infoSaleOrderExt", infoSaleOrderExtTemp);
							tableName = "info_sale_ext_h";
							Map<String, Object> mapAttrDesc = infoSaleOrderExtHisServDu
									.getAttrDesc(infoSaleOrderExtTemp, tableName);
							if (mapAttrDesc != null) {
								json_out.put("infoSaleOrderExtTN", mapAttrDesc);
							}
						} else {
							uocMessage.setContent("无销售订单拓展属性横竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单拓展属性横竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						}
					}

					// 销售订单表
					InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
					infoSaleOrderVo.setSale_order_no(order_no);
					InfoSaleOrderVo infoSaleOrder = infoSaleOrderServDu
							.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
					if (infoSaleOrder != null) {
						json_out.put("infoSaleOrder", infoSaleOrder);
					} else {
						// 销售订单表
						InfoSaleOrderHisVo infoSaleOrderHisVo = new InfoSaleOrderHisVo();
						infoSaleOrderHisVo.setSale_order_no(order_no);
						InfoSaleOrderHisVo infoSaleOrderTemp = infoSaleOrderHisServDu
								.getInfoSaleOrderHisBySaleOrderNo(infoSaleOrderHisVo);
						if (infoSaleOrderTemp == null) {
							uocMessage.setContent("无销售订单竣工表竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单竣工表竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrder", infoSaleOrderTemp);
						}
					}

					// 销售订单费用信息表
					InfoSaleOrderFeeVo infoSaleOrderFeeVoPara = new InfoSaleOrderFeeVo();
					infoSaleOrderFeeVoPara.setSale_order_no(order_no);
					InfoSaleOrderFeeVo infoSaleOrderFee = infoSaleOrderFeeServDu
							.queryInfoSaleOrderFeeBySaleOrderNo(infoSaleOrderFeeVoPara);
					if (infoSaleOrderFee != null) {
						json_out.put("infoSaleOrderFee", infoSaleOrderFee);
					} else {
						// 销售订单费用竣工信息表
						InfoSaleOrderFeeHisVo infoSaleOrderFeeHisVoPara = new InfoSaleOrderFeeHisVo();
						infoSaleOrderFeeHisVoPara.setSale_order_no(order_no);
						InfoSaleOrderFeeHisVo infoSaleOrderFeeTemp = infoSaleOrderFeeHisServDu
								.queryInfoSaleOrderFeeHisBySaleOrderNo(infoSaleOrderFeeHisVoPara);
						if (infoSaleOrderFeeTemp == null) {
							uocMessage.setContent("无销售订单费用竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单费用竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderFee", infoSaleOrderFeeTemp);
						}
					}

					// 销售订单属性表
					InfoSaleOrderAttrVo infoSaleOrderAttrVo = new InfoSaleOrderAttrVo();
					infoSaleOrderAttrVo.setSale_order_no(order_no);
					List<InfoSaleOrderAttrVo> infoSaleOrderAttrList = infoSaleOrderAttrServDu
							.queryInfoSaleOrderAttrList(infoSaleOrderAttrVo);
					if (infoSaleOrderAttrList != null) {
						json_out.put("infoSaleOrderAttrList", infoSaleOrderAttrList);
					} else {
						// 销售订单属性表
						InfoSaleOrderAttrHisVo infoSaleOrderAttrHisVo = new InfoSaleOrderAttrHisVo();
						infoSaleOrderAttrHisVo.setSale_order_no(order_no);
						List<InfoSaleOrderAttrHisVo> infoSaleOrderAttrListTemp = infoSaleOrderAttrHisServDu
								.queryInfoSaleOrderAttrHisList(infoSaleOrderAttrHisVo);
						if (infoSaleOrderAttrListTemp == null) {
							uocMessage.setContent("无销售订单属性竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单属性竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderAttrList", infoSaleOrderAttrListTemp);
						}
					}

					// 销售订单包裹信息表
					InfoSaleOrderDistrDetailVo infoSaleOrderDistrDetailVo = new InfoSaleOrderDistrDetailVo();
					infoSaleOrderDistrDetailVo.setSale_order_no(order_no);
					List<InfoSaleOrderDistrDetailVo> infoSaleOrderDistrDetailList = infoSaleOrderDistrDetailServDu
							.queryInfoSaleOrderDistrDetailBySaleOrderNo(infoSaleOrderDistrDetailVo);
					if (infoSaleOrderDistrDetailList != null) {
						json_out.put("infoSaleOrderDistrDetailList", infoSaleOrderDistrDetailList);
					} else {
						// 销售订单包裹竣工信息表
						InfoSaleOrderDistrDetailHisVo infoSaleOrderDistrDetailHisVo = new InfoSaleOrderDistrDetailHisVo();
						infoSaleOrderDistrDetailHisVo.setSale_order_no(order_no);
						List<InfoSaleOrderDistrDetailHisVo> infoSaleOrderDistrDetailListTemp = infoSaleOrderDistrDetailHisServDu
								.queryInfoSaleOrderDistrDetailHisBySaleOrderNo(infoSaleOrderDistrDetailHisVo);
						if (infoSaleOrderDistrDetailListTemp == null) {
							uocMessage.setContent("无销售订单包裹竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单包裹竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderDistrDetailList", infoSaleOrderDistrDetailListTemp);
						}
					}

					// 销售订单收件人信息表
					InfoSaleOrderDistributionVo InfoSaleOrderDistributionVoPara = new InfoSaleOrderDistributionVo();
					InfoSaleOrderDistributionVoPara.setSale_order_no(order_no);
					InfoSaleOrderDistributionVo infoSaleOrderDistributionVo = infoSaleOrderDistributionServDu
							.getInfoSaleOrderDistributionBySaleOrderNo(InfoSaleOrderDistributionVoPara);
					if (infoSaleOrderDistributionVo != null) {
						json_out.put("infoSaleOrderDistribution", infoSaleOrderDistributionVo);
					} else {
						// 销售订单收件人竣工信息表
						InfoSaleOrderDistributionHisVo InfoSaleOrderDistributionHisVoPara = new InfoSaleOrderDistributionHisVo();
						InfoSaleOrderDistributionHisVoPara.setSale_order_no(order_no);
						InfoSaleOrderDistributionHisVo infoSaleOrderDistributionHisVo = infoSaleOrderDistributionHisServDu
								.getInfoSaleOrderDistributionHisBySaleOrderNo(InfoSaleOrderDistributionHisVoPara);
						if (infoSaleOrderDistributionHisVo == null) {
							uocMessage.setContent("无销售订单收件人竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单收件人竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderDistribution", infoSaleOrderDistributionHisVo);
						}
					}

					// 销售订单修订信息表
					InfoSaleOrderEditVo infoSaleOrderEditVo = new InfoSaleOrderEditVo();
					infoSaleOrderEditVo.setSale_order_no(order_no);
					List<InfoSaleOrderEditVo> infoSaleOrderEditList = infoSaleOrderEditServDu
							.queryInfoSaleOrderEditBySaleOrderNo(infoSaleOrderEditVo);
					if (infoSaleOrderEditList != null) {
						json_out.put("infoSaleOrderEditList", infoSaleOrderEditList);
					} else {
						// 销售订单修订竣工信息表
						InfoSaleOrderEditHisVo infoSaleOrderEditHisHisVo = new InfoSaleOrderEditHisVo();
						infoSaleOrderEditHisHisVo.setSale_order_no(order_no);
						InfoSaleOrderEditHisVo infoSaleOrderEditHis = infoSaleOrderEditHisServDu
								.getInfoSaleOrderEditBySaleOrderNo(infoSaleOrderEditHisHisVo);
						if (infoSaleOrderEditHis == null) {
							uocMessage.setContent("无销售订单修订竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单修订竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderEditList", infoSaleOrderEditHis);
						}
					}

					// 销售订单商品表
					InfoSaleOrderOfrMapVo infoSaleOrderOfrMapVo = new InfoSaleOrderOfrMapVo();
					infoSaleOrderOfrMapVo.setSale_order_no(order_no);
					List<InfoSaleOrderOfrMapVo> infoSaleOrderOfrMapList = infoSaleOrderOfrMapServDu
							.queryInfoSaleOrderOfrMapBySaleOrderNo(infoSaleOrderOfrMapVo);
					if (infoSaleOrderOfrMapList != null) {
						json_out.put("infoSaleOrderOfrMapList", infoSaleOrderOfrMapList);
					} else {
						// 销售订单商品表
						InfoSaleOrderOfrMapHisVo infoSaleOrderOfrMapHisVo = new InfoSaleOrderOfrMapHisVo();
						infoSaleOrderOfrMapHisVo.setSale_order_no(order_no);
						List<InfoSaleOrderOfrMapHisVo> infoSaleOrderOfrMapListTemp = infoSaleOrderOfrMapHisServDu
								.queryInfoSaleOrderOfrMapHisBySaleOrderNo(infoSaleOrderOfrMapHisVo);
						if (infoSaleOrderOfrMapListTemp == null) {
							uocMessage.setContent("无销售订单商品竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单商品竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderOfrMapList", infoSaleOrderOfrMapListTemp);
						}
					}

					// 销售订单客户信息表
					InfoSaleOrderPersionVo infoSaleOrderPersionVo = new InfoSaleOrderPersionVo();
					infoSaleOrderPersionVo.setSale_order_no(order_no);
					InfoSaleOrderPersionVo infoSaleOrderPersion = infoSaleOrderPersionServDu
							.getInfoSaleOrderPersionBySaleOrderNo(infoSaleOrderPersionVo);
					if (infoSaleOrderPersion != null) {
						json_out.put("infoSaleOrderPersion", infoSaleOrderPersion);
					} else {
						// 销售订单客户竣工信息表
						InfoSaleOrderPersionHisVo infoSaleOrderPersionHisVo = new InfoSaleOrderPersionHisVo();
						infoSaleOrderPersionHisVo.setSale_order_no(order_no);
						InfoSaleOrderPersionHisVo infoSaleOrderPersionTemp = infoSaleOrderPersionHisServDu
								.getInfoSaleOrderPersionHisBySaleOrderNo(infoSaleOrderPersionHisVo);
						if (infoSaleOrderPersionTemp == null) {
							uocMessage.setContent("无销售订单客户竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单客户竣工信息竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderPersion", infoSaleOrderPersionTemp);
						}
					}

					// 新表info_sale_order_persion_cert
					InfoSaleOrderPersionCertVo infoSaleOrderPersionCertVo = new InfoSaleOrderPersionCertVo();
					infoSaleOrderPersionCertVo.setSale_order_no(order_no);
					InfoSaleOrderPersionCertVo infoSaleOrderPersionCert = infoSaleOrderPersionCertServDu
							.getInfoSaleOrderPersionCertBySaleOrderNo(infoSaleOrderPersionCertVo);
					if (infoSaleOrderPersionCert != null) {
						json_out.put("infoSaleOrderPersionCert", infoSaleOrderPersionCert);
					} else {
						InfoSaleOrderPersionCertHisVo infoSaleOrderPersionCertHisVo = new InfoSaleOrderPersionCertHisVo();
						infoSaleOrderPersionCertHisVo.setSale_order_no(order_no);
						InfoSaleOrderPersionCertHisVo infoSaleOrderPersionCertHis = infoSaleOrderPersionCertHisServDu
								.getInfoSaleOrderPersionCertHis(infoSaleOrderPersionCertHisVo);
						if (infoSaleOrderPersionCertHis == null) {
							uocMessage.setContent("无销售订单客户证件竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单客户证件竣工信息竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderPersionCert", infoSaleOrderPersionCertHis);
						}
					}

					// 销售订单业务表
					InfoSaleOrderServMapVo infoSaleOrderServMapVo = new InfoSaleOrderServMapVo();
					infoSaleOrderServMapVo.setSale_order_no(order_no);
					List<InfoSaleOrderServMapVo> infoSaleOrderServMapList = infoSaleOrderServMapServDu
							.queryInfoSaleOrderServMapBySaleOrderNo(infoSaleOrderServMapVo);
					if (infoSaleOrderServMapList != null) {
						json_out.put("infoSaleOrderServMapList", infoSaleOrderServMapList);
					} else {
						// 销售订单业务表
						InfoSaleOrderServMapHisVo infoSaleOrderServMapHisVo = new InfoSaleOrderServMapHisVo();
						infoSaleOrderServMapHisVo.setSale_order_no(order_no);
						List<InfoSaleOrderServMapHisVo> infoSaleOrderServMapListTemp = infoSaleOrderServMapHisServDu
								.queryInfoSaleOrderServMapHisBySaleOrderNo(infoSaleOrderServMapHisVo);
						if (infoSaleOrderServMapListTemp == null) {
							uocMessage.setContent("无销售订单业务竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单业务竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrderServMapList", infoSaleOrderServMapListTemp);
						}
					}

					// 取商品订单，根据销售订单号
					InfoOfrOrderVo ofrPara = new InfoOfrOrderVo();
					ofrPara.setSale_order_no(order_no);
					List<InfoOfrOrderVo> listOfr = infoOfrOrderServDu.queryInfoOfrOrderBySaleOrderNo(ofrPara);
					if (listOfr != null) {
						json_out.put("infoOfrOrder", listOfr);
					} else {
						InfoOfrOrderHisVo ofrParaHis = new InfoOfrOrderHisVo();
						ofrParaHis.setSale_order_no(order_no);
						List<InfoOfrOrderHisVo> listOfrTemp = infoOfrOrderHisServDu
								.queryInfoOfrOrderHisBySaleOrderNo(ofrParaHis);
						if (listOfrTemp == null) {
							uocMessage.setContent("无商品订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrder", listOfrTemp);
						}

					}

					// 同时查服务订单表
					// 取服务订单，根据销售订单号
					InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
					infoServiceOrderVo.setSale_order_no(order_no);
					List<InfoServiceOrderVo> listService = infoServiceOrderServDu
							.queryInfoServiceOrderByOrderNo(infoServiceOrderVo);
					if (listService != null) {
						json_out.put("infoServiceOrderList", listService);
					} else {
						InfoServiceOrderHisVo infoServiceOrderHisVo = new InfoServiceOrderHisVo();
						infoServiceOrderHisVo.setSale_order_no(order_no);
						List<InfoServiceOrderHisVo> listServiceTemp = infoServiceOrderHisServDu
								.queryInfoServiceOrderHisByOrderNo(infoServiceOrderHisVo);
						if (listServiceTemp == null) {
							uocMessage.setContent("无服务订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderList", listServiceTemp);
						}
					}

					// 同时查支付订单表
					// 取支付订单，根据销售订单号
					InfoPayOrderVo infoPayOrderVo = new InfoPayOrderVo();
					infoPayOrderVo.setRela_order_no(order_no);
					infoPayOrderVo.setRela_order_type("100");
					InfoPayOrderVo infoPayOrderRes = infoPayOrderServDu.queryInfoPayOrderByRelaOrderNo(infoPayOrderVo);
					if (infoPayOrderRes != null) {
						json_out.put("infoPayOrder", infoPayOrderRes);
					} else {
						InfoPayOrderHisVo infoPayOrderHisVo = new InfoPayOrderHisVo();
						infoPayOrderHisVo.setRela_order_no(order_no);
						infoPayOrderHisVo.setRela_order_type("100");
						InfoPayOrderHisVo infoPayOrderResTemp = infoPayOrderHisServDu
								.getInfoPayOrderHisByPayOrderNo(infoPayOrderHisVo);
						if (infoPayOrderResTemp == null) {
							uocMessage.setContent("无支付订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无支付订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoPayOrder", infoPayOrderResTemp);
						}
					}

					// 同时查交付订单表
					// 取交付订单，根据销售订单号
					InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
					infoDeliverOrderVo.setSale_order_no(order_no);
					List<InfoDeliverOrderVo> listDeliver = infoDeliverOrderServDu
							.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderVo);
					if (listDeliver != null) {
						json_out.put("infoDeliverOrderList", listDeliver);
					} else {
						InfoDeliverOrderHisVo infoDeliverOrderHisVo = new InfoDeliverOrderHisVo();
						infoDeliverOrderHisVo.setSale_order_no(order_no);
						List<InfoDeliverOrderHisVo> listDeliverTemp = infoDeliverOrderHisServDu
								.queryInfoDeliverOrderHisBySaleOrderNo(infoDeliverOrderHisVo);
						if (listDeliverTemp == null) {
							uocMessage.setContent("无交付订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无交付订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoDeliverOrderList", listDeliverTemp);
						}
					}
				} else if (query_type.equals("101")) {

					// 取服务订单
					InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
					infoServiceOrderVo.setServ_order_no(order_no);
					List<InfoServiceOrderVo> listService = infoServiceOrderServDu
							.queryInfoServiceOrderByOrderNo(infoServiceOrderVo);
					List<InfoServiceOrderHisVo> listServiceTemp = null;
					if (listService != null) {
						json_out.put("infoServiceOrderList", listService);
					} else {
						InfoServiceOrderHisVo infoServiceOrderHisVo = new InfoServiceOrderHisVo();
						infoServiceOrderHisVo.setServ_order_no(order_no);
						listServiceTemp = infoServiceOrderHisServDu
								.queryInfoServiceOrderHisByOrderNo(infoServiceOrderHisVo);
						if (listServiceTemp == null) {
							uocMessage.setContent("无服务订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderList", listServiceTemp);
						}
					}

					// 服务订单拓展属性横表
					InfoServiceOrderExtVo InfoServiceOrderExt = new InfoServiceOrderExtVo();
					InfoServiceOrderExt.setServ_order_no(order_no);
					List<InfoServiceOrderExtVo> infoServiceOrderExtList = infoServiceOrderExtServDu
							.queryInfoServiceOrderExtByOrderNo(InfoServiceOrderExt);
					if (infoServiceOrderExtList != null) {
						json_out.put("infoServiceOrderExt", infoServiceOrderExtList.get(0));
						tableName = "info_serv_ext";
						Map<String, Object> mapAttrDesc = infoServiceOrderExtServDu
								.getAttrDesc(infoServiceOrderExtList.get(0), tableName);
						if (mapAttrDesc != null) {
							json_out.put("infoServiceOrderExtTN", mapAttrDesc);
						}
					} else {
						InfoServiceOrderExtHisVo InfoServiceOrderExtHis = new InfoServiceOrderExtHisVo();
						InfoServiceOrderExtHis.setServ_order_no(order_no);
						InfoServiceOrderExtHisVo infoServiceOrderExt = infoServiceOrderExtHisServDu
								.queryInfoServiceOrderExtByOrderNo(InfoServiceOrderExtHis);
						if (infoServiceOrderExt == null) {
							uocMessage.setContent("无服务订单拓展属性横竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单拓展属性横竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderExt", infoServiceOrderExt);
							tableName = "info_serv_ext_h";
							Map<String, Object> mapAttrDesc = infoServiceOrderExtHisServDu
									.getAttrDescHis(infoServiceOrderExt, tableName);
							if (mapAttrDesc != null) {
								json_out.put("infoServiceOrderExtTN", mapAttrDesc);
							}
						}
					}

					// 取服务订单优惠活动
					InfoServiceOrderActivityVo infoServiceOrderActivityVo = new InfoServiceOrderActivityVo();
					infoServiceOrderActivityVo.setServ_order_no(order_no);
					List<InfoServiceOrderActivityVo> infoServiceOrderActivityList = infoServiceOrderActivityServDu
							.queryInfoServiceOrderActivityByOrderNo(infoServiceOrderActivityVo);
					if (infoServiceOrderActivityList != null) {
						InfoServiceOrderActivityVo resultAct=getActivity(infoServiceOrderActivityList.get(0),jsession_id);
						json_out.put("infoServiceOrderActivity", resultAct);
					} else {
						// 取服务订单优惠活动
						InfoServiceOrderActivityHisVo infoServiceOrderActivityHisVo = new InfoServiceOrderActivityHisVo();
						infoServiceOrderActivityHisVo.setServ_order_no(order_no);
						List<InfoServiceOrderActivityHisVo> infoServiceOrderActivityListTemp = infoServiceOrderActivityHisServDu
								.queryInfoServiceOrderActivityHisByOrderNo(infoServiceOrderActivityHisVo);
						if (infoServiceOrderActivityListTemp == null) {
							uocMessage.setContent("无服务订单优惠活动竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单优惠活动竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							InfoServiceOrderActivityVo vo=new InfoServiceOrderActivityVo();
							BeanUtils.copyProperties(infoServiceOrderActivityListTemp.get(0), vo);
							InfoServiceOrderActivityVo resultAct=getActivity(vo,jsession_id);
							json_out.put("infoServiceOrderActivity", resultAct);
						}
					}

					// 取服务订单协议表
					InfoServiceOrderAgreementVo infoServiceOrderAgreementVo = new InfoServiceOrderAgreementVo();
					infoServiceOrderAgreementVo.setServ_order_no(order_no);
					List<InfoServiceOrderAgreementVo> infoServiceOrderAgreementList = infoServiceOrderAgreementServDu
							.queryInfoServiceOrderAgreementByOrderNo(infoServiceOrderAgreementVo);
					if (infoServiceOrderAgreementList != null) {
						json_out.put("infoServiceOrderAgreementList", infoServiceOrderAgreementList);
					} else {
						// 取服务订单协议表
						InfoServiceOrderAgreementHisVo infoServiceOrderAgreementHisVo = new InfoServiceOrderAgreementHisVo();
						infoServiceOrderAgreementHisVo.setServ_order_no(order_no);
						List<InfoServiceOrderAgreementHisVo> infoServiceOrderAgreementListTemp = infoServiceOrderAgreementHisServDu
								.queryInfoServiceOrderAgreementHisByOrderNo(infoServiceOrderAgreementHisVo);
						if (infoServiceOrderAgreementListTemp == null) {
							uocMessage.setContent("无服务订单协议竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单协议竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderAgreementList", infoServiceOrderAgreementListTemp);
						}
					}

					// 取服务订单属性表
					InfoServiceOrderAttrVo infoServiceOrderAttrVo = new InfoServiceOrderAttrVo();
					infoServiceOrderAttrVo.setServ_order_no(order_no);
					List<InfoServiceOrderAttrVo> infoServiceOrderAttrList = infoServiceOrderAttrServDu
							.queryInfoServiceOrderAttrByOrderNo(infoServiceOrderAttrVo);
					if (infoServiceOrderAttrList != null) {
						json_out.put("infoServiceOrderAttrList", infoServiceOrderAttrList);
					} else {
						InfoServiceOrderAttrHisVo infoServiceOrderAttrHisVo = new InfoServiceOrderAttrHisVo();
						infoServiceOrderAttrHisVo.setServ_order_no(order_no);
						List<InfoServiceOrderAttrHisVo> infoServiceOrderAttrListTemp = infoServiceOrderAttrHisServDu
								.queryInfoServiceOrderAttrHisByOrderNo(infoServiceOrderAttrHisVo);
						if (infoServiceOrderAttrListTemp == null) {
							uocMessage.setContent("无服务订单属性竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单属性竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderAttrList", infoServiceOrderAttrListTemp);
						}
					}

					// 取服务订单银行托收表
					InfoServiceOrderCollectionVo infoServiceOrderCollectionVo = new InfoServiceOrderCollectionVo();
					infoServiceOrderCollectionVo.setServ_order_no(order_no);
					List<InfoServiceOrderCollectionVo> infoServiceOrderCollectionList = infoServiceOrderCollectionServDu
							.queryInfoServiceOrderCollectionByOrderNo(infoServiceOrderCollectionVo);
					if (infoServiceOrderCollectionList != null) {
						json_out.put("infoServiceOrderCollectionList", infoServiceOrderCollectionList);
					} else {
						InfoServiceOrderCollectionHisVo infoServiceOrderCollectionHisVo = new InfoServiceOrderCollectionHisVo();
						infoServiceOrderCollectionHisVo.setServ_order_no(order_no);
						List<InfoServiceOrderCollectionHisVo> infoServiceOrderCollectionListTemp = infoServiceOrderCollectionHisServDu
								.queryInfoServiceOrderCollectionHisByOrderNo(infoServiceOrderCollectionHisVo);
						if (infoServiceOrderCollectionListTemp == null) {
							uocMessage.setContent("无服务订单银行托收竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单银行托收竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderCollectionList", infoServiceOrderCollectionListTemp);
						}
					}

					// 取服务订单发展人表
					InfoServiceOrderDeveloperVo infoServiceOrderDeveloperVo = new InfoServiceOrderDeveloperVo();
					infoServiceOrderDeveloperVo.setServ_order_no(order_no);
					List<InfoServiceOrderDeveloperVo> infoServiceOrderDeveloperList = infoServiceOrderDeveloperServDu
							.queryInfoServiceOrderDeveloperByOrderNo(infoServiceOrderDeveloperVo);
					if (infoServiceOrderDeveloperList != null) {
						json_out.put("infoServiceOrderDeveloperList", infoServiceOrderDeveloperList);
					} else {
						InfoServiceOrderDeveloperHisVo infoServiceOrderDeveloperHisVo = new InfoServiceOrderDeveloperHisVo();
						infoServiceOrderDeveloperHisVo.setServ_order_no(order_no);
						List<InfoServiceOrderDeveloperHisVo> infoServiceOrderDeveloperListTemp = infoServiceOrderDeveloperHisServDu
								.queryInfoServiceOrderDeveloperHisByOrderNo(infoServiceOrderDeveloperHisVo);
						if (infoServiceOrderDeveloperListTemp == null) {
							uocMessage.setContent("无服务订单发展人竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单发展人竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderDeveloperList", infoServiceOrderDeveloperListTemp);
						}
					}

					// 取服务订单费用详情表
					InfoServiceOrderFeeVo infoServiceOrderFeeVo = new InfoServiceOrderFeeVo();
					infoServiceOrderFeeVo.setServ_order_no(order_no);
					List<InfoServiceOrderFeeVo> infoServiceOrderFeeList = infoServiceOrderFeeServDu
							.queryInfoServiceOrderFeeByOrderNo(infoServiceOrderFeeVo);
					if (infoServiceOrderFeeList != null) {
						json_out.put("infoServiceOrderFeeList", infoServiceOrderFeeList);
					} else {
						InfoServiceOrderFeeHisVo infoServiceOrderFeeHisVo = new InfoServiceOrderFeeHisVo();
						infoServiceOrderFeeHisVo.setServ_order_no(order_no);
						List<InfoServiceOrderFeeHisVo> infoServiceOrderFeeListTemp = infoServiceOrderFeeHisServDu
								.queryInfoServiceOrderFeeHisByOrderNo(infoServiceOrderFeeHisVo);
						if (infoServiceOrderFeeListTemp == null) {
							uocMessage.setContent("无服务订单费用详情竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单费用详情竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderFeeList", infoServiceOrderFeeListTemp);
						}
					}

					// 取服务订单固网信息表
					InfoServiceOrderFixVo infoServiceOrderFixVo = new InfoServiceOrderFixVo();
					infoServiceOrderFixVo.setServ_order_no(order_no);
					List<InfoServiceOrderFixVo> infoServiceOrderFixList = infoServiceOrderFixServDu
							.queryInfoServiceOrderFixByOrderNo(infoServiceOrderFixVo);
					if (infoServiceOrderFixList != null) {
						json_out.put("infoServiceOrderFixList", infoServiceOrderFixList);
					} else {
						InfoServiceOrderFixHisVo infoServiceOrderFixHisVo = new InfoServiceOrderFixHisVo();
						infoServiceOrderFixHisVo.setServ_order_no(order_no);
						List<InfoServiceOrderFixHisVo> infoServiceOrderFixListTemp = infoServiceOrderFixHisServDu
								.queryInfoServiceOrderFixHisByOrderNo(infoServiceOrderFixHisVo);
						if (infoServiceOrderFixListTemp == null) {
							uocMessage.setContent("无服务订单固网信息竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单固网信息竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderFixList", infoServiceOrderFixListTemp);
						}
					}

					// 取服务订单靓号信息表
					InfoServiceOrderGoodNbrVo infoServiceOrderGoodNbrVo = new InfoServiceOrderGoodNbrVo();
					infoServiceOrderGoodNbrVo.setServ_order_no(order_no);
					List<InfoServiceOrderGoodNbrVo> infoServiceOrderGoodNbrList = infoServiceOrderGoodNbrServDu
							.queryInfoServiceOrderGoodNbrByOrderNo(infoServiceOrderGoodNbrVo);
					if (infoServiceOrderGoodNbrList != null) {
						json_out.put("infoServiceOrderGoodNbrList", infoServiceOrderGoodNbrList);
					} else {
						InfoServiceOrderGoodNbrHisVo infoServiceOrderGoodNbrHisVo = new InfoServiceOrderGoodNbrHisVo();
						infoServiceOrderGoodNbrHisVo.setServ_order_no(order_no);
						List<InfoServiceOrderGoodNbrHisVo> infoServiceOrderGoodNbrListTemp = infoServiceOrderGoodNbrHisServDu
								.queryInfoServiceOrderGoodNbrHisByOrderNo(infoServiceOrderGoodNbrHisVo);
						if (infoServiceOrderGoodNbrListTemp == null) {
							uocMessage.setContent("无服务订单靓号信息竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单靓号信息竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderGoodNbrList", infoServiceOrderGoodNbrListTemp);
						}
					}

					// 取服务订单担保人表
					InfoServiceOrderGuarantorVo infoServiceOrderGuarantorVo = new InfoServiceOrderGuarantorVo();
					infoServiceOrderGuarantorVo.setServ_order_no(order_no);
					List<InfoServiceOrderGuarantorVo> infoServiceOrderGuarantorList = infoServiceOrderGuarantorServDu
							.queryInfoServiceOrderGuarantorByOrderNo(infoServiceOrderGuarantorVo);
					if (infoServiceOrderGuarantorList != null) {
						json_out.put("infoServiceOrderGuarantorList", infoServiceOrderGuarantorList);
					} else {
						InfoServiceOrderGuarantorHisVo infoServiceOrderGuarantorHisVo = new InfoServiceOrderGuarantorHisVo();
						infoServiceOrderGuarantorHisVo.setServ_order_no(order_no);
						List<InfoServiceOrderGuarantorHisVo> infoServiceOrderGuarantorListTemp = infoServiceOrderGuarantorHisServDu
								.queryInfoServiceOrderGuarantorHisByOrderNo(infoServiceOrderGuarantorHisVo);
						if (infoServiceOrderGuarantorListTemp == null) {
							uocMessage.setContent("无服务订单担保人竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单担保人竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderGuarantorList", infoServiceOrderGuarantorListTemp);
						}
					}

					// 取服务订单宽带信息表
					InfoServiceOrderM165Vo infoServiceOrderM165Vo = new InfoServiceOrderM165Vo();
					infoServiceOrderM165Vo.setServ_order_no(order_no);
					List<InfoServiceOrderM165Vo> infoServiceOrderM165List = infoServiceOrderM165ServDu
							.queryInfoServiceOrderM165ByOrderNo(infoServiceOrderM165Vo);
					if (infoServiceOrderM165List != null) {
						json_out.put("infoServiceOrderM165List", infoServiceOrderM165List);
					} else {
						InfoServiceOrderM165HisVo infoServiceOrderM165HisVo = new InfoServiceOrderM165HisVo();
						infoServiceOrderM165HisVo.setServ_order_no(order_no);
						List<InfoServiceOrderM165HisVo> infoServiceOrderM165ListTemp = infoServiceOrderM165HisServDu
								.queryInfoServiceOrderM165HisByOrderNo(infoServiceOrderM165HisVo);
						if (infoServiceOrderM165ListTemp == null) {
							uocMessage.setContent("无服务订单宽带竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单宽带竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderM165List", infoServiceOrderM165ListTemp);
						}
					}

					// 取服务订单套餐列表
					InfoServiceOrderPackageVo infoServiceOrderPackageVo = new InfoServiceOrderPackageVo();
					infoServiceOrderPackageVo.setServ_order_no(order_no);
					List<InfoServiceOrderPackageVo> infoServiceOrderPackageList = infoServiceOrderPackageServDu
							.queryInfoServiceOrderPackageByOrderNo(infoServiceOrderPackageVo);
					if (infoServiceOrderPackageList != null) {
						json_out.put("infoServiceOrderPackageList", infoServiceOrderPackageList);
					} else {
						InfoServiceOrderPackageHisVo infoServiceOrderPackageHisVo = new InfoServiceOrderPackageHisVo();
						infoServiceOrderPackageHisVo.setServ_order_no(order_no);
						List<InfoServiceOrderPackageHisVo> infoServiceOrderPackageListTemp = infoServiceOrderPackageHisServDu
								.queryInfoServiceOrderPackageHisByOrderNo(infoServiceOrderPackageHisVo);
						if (infoServiceOrderPackageListTemp == null) {
							uocMessage.setContent("无服务订单套餐列竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单套餐列竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderPackageList", infoServiceOrderPackageListTemp);
						}
					}

					// 取服务订单收费详情表
					InfoServiceOrderPayVo infoServiceOrderPayVo = new InfoServiceOrderPayVo();
					infoServiceOrderPayVo.setServ_order_no(order_no);
					List<InfoServiceOrderPayVo> infoServiceOrderPayList = infoServiceOrderPayServDu
							.queryInfoServiceOrderPayByOrderNo(infoServiceOrderPayVo);
					if (infoServiceOrderPayList != null) {
						json_out.put("infoServiceOrderPayList", infoServiceOrderPayList);
					} else {
						InfoServiceOrderPayHisVo infoServiceOrderPayHisVo = new InfoServiceOrderPayHisVo();
						infoServiceOrderPayHisVo.setServ_order_no(order_no);
						List<InfoServiceOrderPayHisVo> infoServiceOrderPayListTemp = infoServiceOrderPayHisServDu
								.queryInfoServiceOrderPayHisByOrderNo(infoServiceOrderPayHisVo);
						if (infoServiceOrderPayListTemp == null) {
							uocMessage.setContent("无服务订单收费详情竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单收费详情竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderPayList", infoServiceOrderPayListTemp);
						}
					}

					// 取服务订单客户信息表
					InfoServiceOrderPersionVo infoServiceOrderPersionVo = new InfoServiceOrderPersionVo();
					infoServiceOrderPersionVo.setServ_order_no(order_no);
					List<InfoServiceOrderPersionVo> infoServiceOrderPersionList = infoServiceOrderPersionServDu
							.queryInfoServiceOrderPersionByOrderNo(infoServiceOrderPersionVo);
					if (infoServiceOrderPersionList != null) {
						json_out.put("infoServiceOrderPersionList", infoServiceOrderPersionList);
					} else {
						InfoServiceOrderPersionHisVo infoServiceOrderPersionHisVo = new InfoServiceOrderPersionHisVo();
						infoServiceOrderPersionHisVo.setServ_order_no(order_no);
						List<InfoServiceOrderPersionHisVo> infoServiceOrderPersionListTemp = infoServiceOrderPersionHisServDu
								.queryInfoServiceOrderPersionHisByOrderNo(infoServiceOrderPersionHisVo);
						if (infoServiceOrderPersionListTemp == null) {
							uocMessage.setContent("无服务订单客户信息竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单客户信息竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderPersionList", infoServiceOrderPersionListTemp);
						}
					}

					// 取服务订单产品列表
					InfoServiceOrderProdMapVo infoServiceOrderProdMapVo = new InfoServiceOrderProdMapVo();
					infoServiceOrderProdMapVo.setServ_order_no(order_no);
					List<InfoServiceOrderProdMapVo> infoServiceOrderProdMapList = infoServiceOrderProdMapServDu
							.queryInfoServiceOrderProdMapByOrderNo(infoServiceOrderProdMapVo);
					if (infoServiceOrderProdMapList != null) {
						json_out.put("infoServiceOrderProdMapList", infoServiceOrderProdMapList);
					} else {
						InfoServiceOrderProdMapHisVo infoServiceOrderProdMapHisVo = new InfoServiceOrderProdMapHisVo();
						infoServiceOrderProdMapHisVo.setServ_order_no(order_no);
						List<InfoServiceOrderProdMapHisVo> infoServiceOrderProdMapListTemp = infoServiceOrderProdMapHisServDu
								.queryInfoServiceOrderProdMapHisByOrderNo(infoServiceOrderProdMapHisVo);
						if (infoServiceOrderProdMapListTemp == null) {
							uocMessage.setContent("无服务订单产品列竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单产品列竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderProdMapList", infoServiceOrderProdMapListTemp);
						}
					}

					// 取服务订单终端
					InfoServiceOrderTerminalVo InfoServiceOrderTerminal = new InfoServiceOrderTerminalVo();
					InfoServiceOrderTerminal.setServ_order_no(order_no);
					List<InfoServiceOrderTerminalVo> infoServiceOrderTerminalList = infoServiceOrderTerminalServDu
							.queryInfoServiceOrderTerminalByOrderNo(InfoServiceOrderTerminal);
					if (infoServiceOrderTerminalList != null) {
						json_out.put("infoServiceOrderTerminalList", infoServiceOrderTerminalList);
					} else {
						InfoServiceOrderTerminalHisVo InfoServiceOrderTerminalHis = new InfoServiceOrderTerminalHisVo();
						InfoServiceOrderTerminalHis.setServ_order_no(order_no);
						List<InfoServiceOrderTerminalHisVo> infoServiceOrderTerminalListTemp = infoServiceOrderTerminalHisServDu
								.queryInfoServiceOrderTerminalHisByOrderNo(InfoServiceOrderTerminalHis);
						if (infoServiceOrderTerminalListTemp == null) {
							uocMessage.setContent("无服务订单终端竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单终端竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderTerminalList", infoServiceOrderTerminalListTemp);
						}
					}

					// 取服务订单SIM卡
					InfoServiceOrderSimCardVo InfoServiceOrderSimCard = new InfoServiceOrderSimCardVo();
					InfoServiceOrderSimCard.setServ_order_no(order_no);
					List<InfoServiceOrderSimCardVo> InfoServiceOrderSimCardList = infoServiceOrderSimCardServDu
							.queryInfoServiceOrderSimCardByOrderNo(InfoServiceOrderSimCard);
					if (InfoServiceOrderSimCardList != null) {
						json_out.put("InfoServiceOrderSimCardList", InfoServiceOrderSimCardList);
					} else {
						InfoServiceOrderSimCardHisVo InfoServiceOrderSimCardHis = new InfoServiceOrderSimCardHisVo();
						InfoServiceOrderSimCardHis.setServ_order_no(order_no);
						List<InfoServiceOrderSimCardHisVo> InfoServiceOrderSimCardListTemp = infoServiceOrderSimCardHisServDu
								.queryInfoServiceOrderSimCardHisByOrderNo(InfoServiceOrderSimCardHis);
						if (InfoServiceOrderSimCardListTemp == null) {
							uocMessage.setContent("无服务订单SIM卡 竣工信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单SIM卡竣工 信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("InfoServiceOrderSimCardList", InfoServiceOrderSimCardListTemp);
						}
					}

					// 取人工任务实例表
					ProcInstTaskInstVo procInstTaskInst = new ProcInstTaskInstVo();
					procInstTaskInst.setOrder_no(order_no);
					ProcInstTaskInstVo procInstTaskInstVo = procInstTaskInstServDu
							.queryProcInstTaskInstByTaskState(procInstTaskInst);
					if (procInstTaskInstVo != null) {
						json_out.put("procInstTaskInstVo", procInstTaskInstVo);
					} else {
						ProcInstTaskInstHisVo procInstTaskInstHis = new ProcInstTaskInstHisVo();
						procInstTaskInstHis.setOrder_no(order_no);
						ProcInstTaskInstHisVo procInstTaskInstHisVo = procInstTaskInstHisServDu
								.queryProcInstTaskInstHisByOrderNo(procInstTaskInstHis);
						if (procInstTaskInstHisVo == null) {
							uocMessage.setContent("无人工任务实例竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无人工任务实例竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("procInstTaskInstVo", procInstTaskInstHisVo);
						}
					}

					// 同时查销售订单
					InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
					if (listService != null) {
						infoSaleOrderVo.setSale_order_no(listService.get(0).getSale_order_no());
						infoSaleOrderVo = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
						if (infoSaleOrderVo == null) {
							uocMessage.setContent("无销售订单信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrder", infoSaleOrderVo);
							// 同时查商品订单
							InfoOfrOrderVo infoOfrOrderVo = new InfoOfrOrderVo();
							infoOfrOrderVo.setSale_order_no(infoSaleOrderVo.getSale_order_no());
							List<InfoOfrOrderVo> infoOfrOrderList = infoOfrOrderServDu
									.queryInfoOfrOrderBySaleOrderNo(infoOfrOrderVo);
							if (infoOfrOrderList == null) {
								uocMessage.setContent("无商品订单信息");
								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							} else {
								json_out.put("infoOfrOrderList", infoOfrOrderList);

								List<OfrOrderAndServiceOrderVo> ofrOrderAndServiceOrderList = new ArrayList<OfrOrderAndServiceOrderVo>();
								for (InfoOfrOrderVo ofrOrder : infoOfrOrderList) {
									OfrOrderAndServiceOrderVo ofrOrderAndServiceOrder = new OfrOrderAndServiceOrderVo();
									ofrOrderAndServiceOrder.setOfr_order_no(ofrOrder.getOfr_order_no());
									InfoServiceOrderVo serviceOrder = new InfoServiceOrderVo();
									serviceOrder.setOfr_order_no(ofrOrder.getOfr_order_no());
									List<InfoServiceOrderVo> serviceOrderList = infoServiceOrderServDu
											.queryInfoServiceOrderByOfrOrderNo(serviceOrder);
									if (serviceOrderList != null) {
										ofrOrderAndServiceOrder
												.setServ_order_no(serviceOrderList.get(0).getServ_order_no());
									}
									ofrOrderAndServiceOrderList.add(ofrOrderAndServiceOrder);
								}
								json_out.put("ofrOrderAndServiceOrderList", ofrOrderAndServiceOrderList);
							}

						}
					}

					// 同时查销售订单
					InfoSaleOrderHisVo infoSaleOrderHisVo = new InfoSaleOrderHisVo();
					if (listServiceTemp != null) {
						infoSaleOrderHisVo.setSale_order_no(listServiceTemp.get(0).getSale_order_no());
						infoSaleOrderHisVo = infoSaleOrderHisServDu
								.getInfoSaleOrderHisBySaleOrderNo(infoSaleOrderHisVo);
						if (infoSaleOrderHisVo == null) {
							uocMessage.setContent("无销售订单信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无销售订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoSaleOrder", infoSaleOrderHisVo);
							// 同时查商品订单
							InfoOfrOrderHisVo infoOfrOrderHisVo = new InfoOfrOrderHisVo();
							infoOfrOrderHisVo.setSale_order_no(infoSaleOrderHisVo.getSale_order_no());
							List<InfoOfrOrderHisVo> infoOfrOrderHisList = infoOfrOrderHisServDu
									.queryInfoOfrOrderHisBySaleOrderNo(infoOfrOrderHisVo);
							if (infoOfrOrderHisList == null) {
								uocMessage.setContent("无商品订单信息");
								logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							} else {
								json_out.put("infoOfrOrderList", infoOfrOrderHisList);

								List<OfrOrderAndServiceOrderVo> ofrOrderAndServiceOrderList = new ArrayList<OfrOrderAndServiceOrderVo>();
								for (InfoOfrOrderHisVo ofrOrder : infoOfrOrderHisList) {
									OfrOrderAndServiceOrderVo ofrOrderAndServiceOrder = new OfrOrderAndServiceOrderVo();
									ofrOrderAndServiceOrder.setOfr_order_no(ofrOrder.getOfr_order_no());
									InfoServiceOrderHisVo serviceOrder = new InfoServiceOrderHisVo();
									serviceOrder.setOfr_order_no(ofrOrder.getOfr_order_no());
									List<InfoServiceOrderHisVo> serviceOrderList = infoServiceOrderHisServDu
											.queryInfoServiceOrderHisByOrderNo(serviceOrder);
									if (serviceOrderList != null) {
										ofrOrderAndServiceOrder
												.setServ_order_no(serviceOrderList.get(0).getServ_order_no());
									}
									ofrOrderAndServiceOrderList.add(ofrOrderAndServiceOrder);
								}
								json_out.put("ofrOrderAndServiceOrderList", ofrOrderAndServiceOrderList);
							}

						}
					}

					// 同时查交付订单表
					// 取交付订单，根据销售订单号
					InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
					infoDeliverOrderVo.setSale_order_no(order_no);
					List<InfoDeliverOrderVo> listDeliver = infoDeliverOrderServDu
							.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderVo);
					if (listDeliver != null) {
						json_out.put("infoDeliverOrderList", listDeliver);
					} else {
						InfoDeliverOrderHisVo infoDeliverOrderHisVo = new InfoDeliverOrderHisVo();
						infoDeliverOrderHisVo.setSale_order_no(order_no);
						List<InfoDeliverOrderHisVo> listDeliverTemp = infoDeliverOrderHisServDu
								.queryInfoDeliverOrderHisBySaleOrderNo(infoDeliverOrderHisVo);
						if (listDeliverTemp == null) {
							uocMessage.setContent("无交付订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无交付订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoDeliverOrderList", listDeliverTemp);
						}
					}

				} else if (query_type.equals("102")) {
					// 取支付订单 ,根据销售订单号
					InfoPayOrderVo infoPayOrderVo = new InfoPayOrderVo();
					infoPayOrderVo.setRela_order_no(order_no);
					infoPayOrderVo.setRela_order_type("100");
					InfoPayOrderVo infoPayOrderRes = infoPayOrderServDu.queryInfoPayOrderByRelaOrderNo(infoPayOrderVo);
					if (infoPayOrderRes != null) {
						json_out.put("infoPayOrder", infoPayOrderRes);
					} else {
						InfoPayOrderHisVo infoPayOrderHisVo = new InfoPayOrderHisVo();
						infoPayOrderHisVo.setRela_order_no(order_no);
						infoPayOrderHisVo.setRela_order_type("100");
						InfoPayOrderHisVo infoPayOrderResTemp = infoPayOrderHisServDu
								.getInfoPayOrderHisByPayOrderNo(infoPayOrderHisVo);
						if (infoPayOrderResTemp == null) {
							uocMessage.setContent("无支付订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无支付订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoPayOrder", infoPayOrderResTemp);
						}
					}
				} else if (query_type.equals("103")) {

					// 取交付订单,根据销售订单号
					InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
					infoDeliverOrderVo.setSale_order_no(order_no);
					List<InfoDeliverOrderVo> listDeliver = infoDeliverOrderServDu
							.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderVo);
					if (listDeliver != null) {
						json_out.put("infoDeliverOrderList", listDeliver);
					} else {
						InfoDeliverOrderHisVo infoDeliverOrderHisVo = new InfoDeliverOrderHisVo();
						infoDeliverOrderHisVo.setSale_order_no(order_no);
						List<InfoDeliverOrderHisVo> listDeliverTemp = infoDeliverOrderHisServDu
								.queryInfoDeliverOrderHisBySaleOrderNo(infoDeliverOrderHisVo);
						if (listDeliverTemp == null) {
							uocMessage.setContent("无交付订单竣工信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无交付订单竣工信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoDeliverOrderList", listDeliverTemp);
						}
					}
				} else if (query_type.equals("104")) {
					// 取商品订单，根据销售订单号
					InfoOfrOrderVo ofrPara = new InfoOfrOrderVo();
					ofrPara.setOfr_order_no(order_no);
					InfoOfrOrderVo listOfr = infoOfrOrderServDu.getInfoOfrOrderByOfrOrderNo(ofrPara);
					if (listOfr != null) {
						json_out.put("infoOfrOrderList", listOfr);
					} else {
						InfoOfrOrderHisVo ofrParaHis = new InfoOfrOrderHisVo();
						ofrParaHis.setSale_order_no(order_no);
						List<InfoOfrOrderHisVo> listOfrTemp = infoOfrOrderHisServDu
								.queryInfoOfrOrderHisByOrderNo(ofrParaHis);
						if (listOfrTemp == null) {
							uocMessage.setContent("无商品订单信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrderList", listOfrTemp);
						}
					}

					// 取商品订单一次性费用发票表，根据销售订单号
					InfoOfrOrderInvoiceVo infoOfrOrderInvoiceVo = new InfoOfrOrderInvoiceVo();
					infoOfrOrderInvoiceVo.setOfr_order_no(order_no);
					List<InfoOfrOrderInvoiceVo> infoOfrOrderInvoiceList = infoOfrOrderInvoiceServDu
							.queryInfoOfrOrderInvoiceByOrderNo(infoOfrOrderInvoiceVo);
					if (infoOfrOrderInvoiceList != null) {
						json_out.put("infoOfrOrderInvoiceList", infoOfrOrderInvoiceList);
					} else {
						InfoOfrOrderInvoiceHisVo infoOfrOrderInvoiceHisVo = new InfoOfrOrderInvoiceHisVo();
						infoOfrOrderInvoiceHisVo.setOfr_order_no(order_no);
						List<InfoOfrOrderInvoiceHisVo> infoOfrOrderInHisVoiceList = infoOfrOrderInvoiceHisServDu
								.queryInfoOfrOrderInvoiceByOrderNo(infoOfrOrderInvoiceHisVo);
						if (infoOfrOrderInHisVoiceList == null) {
							uocMessage.setContent("无商品订单一次性费用发票信息");
							logger.info(
									">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单一次性费用发票信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrderInvoiceList", infoOfrOrderInHisVoiceList);
						}
					}

					// 取商品订单收费详情表，根据销售订单号
					InfoOfrOrderPayVo infoOfrOrderPayVo = new InfoOfrOrderPayVo();
					infoOfrOrderPayVo.setOfr_order_no(order_no);
					List<InfoOfrOrderPayVo> infoOfrOrderPayList = infoOfrOrderPayServDu
							.queryInfoOfrOrderPayByOrderNo(infoOfrOrderPayVo);
					if (infoOfrOrderPayList != null) {
						json_out.put("infoOfrOrderPayList", infoOfrOrderPayList);
					} else {
						InfoOfrOrderPayHisVo infoOfrOrderPayHisVo = new InfoOfrOrderPayHisVo();
						infoOfrOrderPayHisVo.setOfr_order_no(order_no);
						List<InfoOfrOrderPayHisVo> infoOfrOrderPayListTemp = infoOfrOrderPayHisServDu
								.queryInfoOfrOrderPayHisByOrderNo(infoOfrOrderPayHisVo);
						if (infoOfrOrderPayListTemp == null) {
							uocMessage.setContent("无商品订单收费详情信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单收费详情信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrderPayList", infoOfrOrderPayListTemp);
						}
					}

					// 取商品订单费用详情表，根据销售订单号
					InfoOfrOrderFeeVo infoOfrOrderFeeVo = new InfoOfrOrderFeeVo();
					infoOfrOrderFeeVo.setOfr_order_no(order_no);
					List<InfoOfrOrderFeeVo> infoOfrOrderFeeList = infoOfrOrderFeeServDu
							.queryInfoOfrOrderFeeByOrderNo(infoOfrOrderFeeVo);
					if (infoOfrOrderFeeList != null) {
						json_out.put("infoOfrOrderFeeList", infoOfrOrderFeeList);
					} else {
						InfoOfrOrderFeeHisVo infoOfrOrderFeeHisVo = new InfoOfrOrderFeeHisVo();
						infoOfrOrderFeeHisVo.setOfr_order_no(order_no);
						List<InfoOfrOrderFeeHisVo> infoOfrOrderFeeListTemp = infoOfrOrderFeeHisServDu
								.queryInfoOfrOrderFeeHisByOrderNo(infoOfrOrderFeeHisVo);
						if (infoOfrOrderFeeListTemp == null) {
							uocMessage.setContent("无商品订单费用详情信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无商品订单费用详情信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoOfrOrderFeeList", infoOfrOrderFeeListTemp);
						}
					}

					// 取服务订单，根据销售订单
					InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
					infoServiceOrderVo.setOfr_order_no(order_no);
					List<InfoServiceOrderVo> listService = infoServiceOrderServDu
							.queryInfoServiceOrderByOrderNo(infoServiceOrderVo);
					if (listService != null) {
						json_out.put("infoServiceOrderList", listService);
					} else {
						InfoServiceOrderHisVo infoServiceOrderHisVo = new InfoServiceOrderHisVo();
						infoServiceOrderHisVo.setOfr_order_no(order_no);
						List<InfoServiceOrderHisVo> listServiceTemp = infoServiceOrderHisServDu
								.queryInfoServiceOrderHisByOrderNo(infoServiceOrderHisVo);
						if (listServiceTemp == null) {
							uocMessage.setContent("无服务订单信息");
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
						} else {
							json_out.put("infoServiceOrderList", listServiceTemp);
						}
					}
				}
			}
			uocMessage.setRespCode(respCode.SUCCESS);
			uocMessage.setContent("操作成功");
			uocMessage.addArg("json_info", json_out);
		return uocMessage;
	}

	private void getBeanDu() {
		if (operServDu == null) {
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		if (infoServiceOrderServDu == null) {
			infoServiceOrderServDu = (InfoServiceOrderServDu) ToolSpring.getBean("InfoServiceOrderServDu");
		}
		if (infoOfrOrderServDu == null) {
			infoOfrOrderServDu = (InfoOfrOrderServDu) ToolSpring.getBean("InfoOfrOrderServDu");
		}
		if (infoPayOrderServDu == null) {
			infoPayOrderServDu = (InfoPayOrderServDu) ToolSpring.getBean("InfoPayOrderServDu");
		}
		if (infoDeliverOrderServDu == null) {
			infoDeliverOrderServDu = (InfoDeliverOrderServDu) ToolSpring.getBean("InfoDeliverOrderServDu");
		}
		if (infoSaleOrderFeeServDu == null) {
			infoSaleOrderFeeServDu = (InfoSaleOrderFeeServDu) ToolSpring.getBean("InfoSaleOrderFeeServDu");
		}
		if (infoSaleOrderDistributionServDu == null) {
			infoSaleOrderDistributionServDu = (InfoSaleOrderDistributionServDu) ToolSpring
					.getBean("InfoSaleOrderDistributionServDu");
		}
		if (infoSaleOrderDistrDetailServDu == null) {
			infoSaleOrderDistrDetailServDu = (InfoSaleOrderDistrDetailServDu) ToolSpring
					.getBean("InfoSaleOrderDistrDetailServDu");
		}
		if (infoSaleOrderAttrServDu == null) {
			infoSaleOrderAttrServDu = (InfoSaleOrderAttrServDu) ToolSpring.getBean("InfoSaleOrderAttrServDu");
		}
		if (infoSaleOrderEditServDu == null) {
			infoSaleOrderEditServDu = (InfoSaleOrderEditServDu) ToolSpring.getBean("InfoSaleOrderEditServDu");
		}
		if (infoSaleOrderExtServDu == null) {
			infoSaleOrderExtServDu = (InfoSaleOrderExtServDu) ToolSpring.getBean("InfoSaleOrderExtServDu");
		}
		if (infoSaleOrderOfrMapServDu == null) {
			infoSaleOrderOfrMapServDu = (InfoSaleOrderOfrMapServDu) ToolSpring.getBean("InfoSaleOrderOfrMapServDu");
		}
		if (infoSaleOrderPersionServDu == null) {
			infoSaleOrderPersionServDu = (InfoSaleOrderPersionServDu) ToolSpring.getBean("InfoSaleOrderPersionServDu");
		}
		if (infoSaleOrderServMapServDu == null) {
			infoSaleOrderServMapServDu = (InfoSaleOrderServMapServDu) ToolSpring.getBean("InfoSaleOrderServMapServDu");
		}
		if (infoSaleOrderServDu == null) {
			infoSaleOrderServDu = (InfoSaleOrderServDu) ToolSpring.getBean("InfoSaleOrderServDu");
		}
		if (infoServiceOrderActivityServDu == null) {
			infoServiceOrderActivityServDu = (InfoServiceOrderActivityServDu) ToolSpring
					.getBean("InfoServiceOrderActivityServDu");
		}
		if (infoServiceOrderAgreementServDu == null) {
			infoServiceOrderAgreementServDu = (InfoServiceOrderAgreementServDu) ToolSpring
					.getBean("InfoServiceOrderAgreementServDu");
		}
		if (infoServiceOrderCollectionServDu == null) {
			infoServiceOrderCollectionServDu = (InfoServiceOrderCollectionServDu) ToolSpring
					.getBean("InfoServiceOrderCollectionServDu");
		}
		if (infoServiceOrderFixServDu == null) {
			infoServiceOrderFixServDu = (InfoServiceOrderFixServDu) ToolSpring.getBean("InfoServiceOrderFixServDu");
		}
		if (infoServiceOrderProdMapServDu == null) {
			infoServiceOrderProdMapServDu = (InfoServiceOrderProdMapServDu) ToolSpring
					.getBean("InfoServiceOrderProdMapServDu");
		}
		if (infoServiceOrderPersionServDu == null) {
			infoServiceOrderPersionServDu = (InfoServiceOrderPersionServDu) ToolSpring
					.getBean("InfoServiceOrderPersionServDu");
		}
		if (infoServiceOrderPayServDu == null) {
			infoServiceOrderPayServDu = (InfoServiceOrderPayServDu) ToolSpring.getBean("InfoServiceOrderPayServDu");
		}
		if (infoServiceOrderPackageServDu == null) {
			infoServiceOrderPackageServDu = (InfoServiceOrderPackageServDu) ToolSpring
					.getBean("InfoServiceOrderPackageServDu");
		}
		if (infoServiceOrderM165ServDu == null) {
			infoServiceOrderM165ServDu = (InfoServiceOrderM165ServDu) ToolSpring.getBean("InfoServiceOrderM165ServDu");
		}
		if (infoServiceOrderGuarantorServDu == null) {
			infoServiceOrderGuarantorServDu = (InfoServiceOrderGuarantorServDu) ToolSpring
					.getBean("InfoServiceOrderGuarantorServDu");
		}
		if (infoServiceOrderGoodNbrServDu == null) {
			infoServiceOrderGoodNbrServDu = (InfoServiceOrderGoodNbrServDu) ToolSpring
					.getBean("InfoServiceOrderGoodNbrServDu");
		}
		if (infoServiceOrderFeeServDu == null) {
			infoServiceOrderFeeServDu = (InfoServiceOrderFeeServDu) ToolSpring.getBean("InfoServiceOrderFeeServDu");
		}
		if (infoServiceOrderDeveloperServDu == null) {
			infoServiceOrderDeveloperServDu = (InfoServiceOrderDeveloperServDu) ToolSpring
					.getBean("InfoServiceOrderDeveloperServDu");
		}
		if (infoServiceOrderAttrServDu == null) {
			infoServiceOrderAttrServDu = (InfoServiceOrderAttrServDu) ToolSpring.getBean("InfoServiceOrderAttrServDu");
		}
		if (infoOfrOrderFeeServDu == null) {
			infoOfrOrderFeeServDu = (InfoOfrOrderFeeServDu) ToolSpring.getBean("InfoOfrOrderFeeServDu");
		}
		if (infoOfrOrderInvoiceServDu == null) {
			infoOfrOrderInvoiceServDu = (InfoOfrOrderInvoiceServDu) ToolSpring.getBean("InfoOfrOrderInvoiceServDu");
		}
		if (infoOfrOrderPayServDu == null) {
			infoOfrOrderPayServDu = (InfoOfrOrderPayServDu) ToolSpring.getBean("InfoOfrOrderPayServDu");
		}
		if (infoServiceOrderExtServDu == null) {
			infoServiceOrderExtServDu = (InfoServiceOrderExtServDu) ToolSpring.getBean("InfoServiceOrderExtServDu");
		}
		if (infoServiceOrderTerminalServDu == null) {
			infoServiceOrderTerminalServDu = (InfoServiceOrderTerminalServDu) ToolSpring
					.getBean("InfoServiceOrderTerminalServDu");
		}
		if (infoServiceOrderSimCardServDu == null) {
			infoServiceOrderSimCardServDu = (InfoServiceOrderSimCardServDu) ToolSpring
					.getBean("InfoServiceOrderSimCardServDu");
		}
		if (procInstTaskInstServDu == null) {
			procInstTaskInstServDu = (ProcInstTaskInstServDu) ToolSpring.getBean("ProcInstTaskInstServDu");
		}
		if (infoServiceOrderHisServDu == null) {
			infoServiceOrderHisServDu = (InfoServiceOrderHisServDu) ToolSpring.getBean("InfoServiceOrderHisServDu");
		}
		if (infoOfrOrderHisServDu == null) {
			infoOfrOrderHisServDu = (InfoOfrOrderHisServDu) ToolSpring.getBean("InfoOfrOrderHisServDu");
		}
		if (infoPayOrderHisServDu == null) {
			infoPayOrderHisServDu = (InfoPayOrderHisServDu) ToolSpring.getBean("InfoPayOrderHisServDu");
		}
		if (infoDeliverOrderHisServDu == null) {
			infoDeliverOrderHisServDu = (InfoDeliverOrderHisServDu) ToolSpring.getBean("InfoDeliverOrderHisServDu");
		}
		if (infoSaleOrderFeeHisServDu == null) {
			infoSaleOrderFeeHisServDu = (InfoSaleOrderFeeHisServDu) ToolSpring.getBean("InfoSaleOrderFeeHisServDu");
		}
		if (infoSaleOrderDistributionHisServDu == null) {
			infoSaleOrderDistributionHisServDu = (InfoSaleOrderDistributionHisServDu) ToolSpring
					.getBean("InfoSaleOrderDistributionHisServDu");
		}
		if (infoSaleOrderDistrDetailHisServDu == null) {
			infoSaleOrderDistrDetailHisServDu = (InfoSaleOrderDistrDetailHisServDu) ToolSpring
					.getBean("InfoSaleOrderDistrDetailHisServDu");
		}
		if (infoSaleOrderAttrHisServDu == null) {
			infoSaleOrderAttrHisServDu = (InfoSaleOrderAttrHisServDu) ToolSpring.getBean("InfoSaleOrderAttrHisServDu");
		}
		if (infoSaleOrderEditHisServDu == null) {
			infoSaleOrderEditHisServDu = (InfoSaleOrderEditHisServDu) ToolSpring.getBean("InfoSaleOrderEditHisServDu");
		}
		if (infoSaleOrderExtHisServDu == null) {
			infoSaleOrderExtHisServDu = (InfoSaleOrderExtHisServDu) ToolSpring.getBean("InfoSaleOrderExtHisServDu");
		}
		if (infoSaleOrderOfrMapHisServDu == null) {
			infoSaleOrderOfrMapHisServDu = (InfoSaleOrderOfrMapHisServDu) ToolSpring
					.getBean("InfoSaleOrderOfrMapHisServDu");
		}
		if (infoSaleOrderPersionHisServDu == null) {
			infoSaleOrderPersionHisServDu = (InfoSaleOrderPersionHisServDu) ToolSpring
					.getBean("InfoSaleOrderPersionHisServDu");
		}
		if (infoSaleOrderServMapHisServDu == null) {
			infoSaleOrderServMapHisServDu = (InfoSaleOrderServMapHisServDu) ToolSpring
					.getBean("InfoSaleOrderServMapHisServDu");
		}
		if (infoSaleOrderHisServDu == null) {
			infoSaleOrderHisServDu = (InfoSaleOrderHisServDu) ToolSpring.getBean("InfoSaleOrderHisServDu");
		}
		if (infoServiceOrderActivityHisServDu == null) {
			infoServiceOrderActivityHisServDu = (InfoServiceOrderActivityHisServDu) ToolSpring
					.getBean("InfoServiceOrderActivityHisServDu");
		}
		if (infoServiceOrderAgreementHisServDu == null) {
			infoServiceOrderAgreementHisServDu = (InfoServiceOrderAgreementHisServDu) ToolSpring
					.getBean("InfoServiceOrderAgreementHisServDu");
		}
		if (infoServiceOrderCollectionHisServDu == null) {
			infoServiceOrderCollectionHisServDu = (InfoServiceOrderCollectionHisServDu) ToolSpring
					.getBean("InfoServiceOrderCollectionHisServDu");
		}
		if (infoServiceOrderFixHisServDu == null) {
			infoServiceOrderFixHisServDu = (InfoServiceOrderFixHisServDu) ToolSpring
					.getBean("InfoServiceOrderFixHisServDu");
		}
		if (infoServiceOrderProdMapHisServDu == null) {
			infoServiceOrderProdMapHisServDu = (InfoServiceOrderProdMapHisServDu) ToolSpring
					.getBean("InfoServiceOrderProdMapHisServDu");
		}
		if (infoServiceOrderPersionHisServDu == null) {
			infoServiceOrderPersionHisServDu = (InfoServiceOrderPersionHisServDu) ToolSpring
					.getBean("InfoServiceOrderPersionHisServDu");
		}
		if (infoServiceOrderPayHisServDu == null) {
			infoServiceOrderPayHisServDu = (InfoServiceOrderPayHisServDu) ToolSpring
					.getBean("InfoServiceOrderPayHisServDu");
		}
		if (infoServiceOrderPackageHisServDu == null) {
			infoServiceOrderPackageHisServDu = (InfoServiceOrderPackageHisServDu) ToolSpring
					.getBean("InfoServiceOrderPackageHisServDu");
		}
		if (infoServiceOrderM165HisServDu == null) {
			infoServiceOrderM165HisServDu = (InfoServiceOrderM165HisServDu) ToolSpring
					.getBean("InfoServiceOrderM165HisServDu");
		}
		if (infoServiceOrderGuarantorHisServDu == null) {
			infoServiceOrderGuarantorHisServDu = (InfoServiceOrderGuarantorHisServDu) ToolSpring
					.getBean("InfoServiceOrderGuarantorHisServDu");
		}
		if (infoServiceOrderGoodNbrHisServDu == null) {
			infoServiceOrderGoodNbrHisServDu = (InfoServiceOrderGoodNbrHisServDu) ToolSpring
					.getBean("InfoServiceOrderGoodNbrHisServDu");
		}
		if (infoServiceOrderFeeHisServDu == null) {
			infoServiceOrderFeeHisServDu = (InfoServiceOrderFeeHisServDu) ToolSpring
					.getBean("InfoServiceOrderFeeHisServDu");
		}
		if (infoServiceOrderDeveloperHisServDu == null) {
			infoServiceOrderDeveloperHisServDu = (InfoServiceOrderDeveloperHisServDu) ToolSpring
					.getBean("InfoServiceOrderDeveloperHisServDu");
		}
		if (infoServiceOrderAttrHisServDu == null) {
			infoServiceOrderAttrHisServDu = (InfoServiceOrderAttrHisServDu) ToolSpring
					.getBean("InfoServiceOrderAttrHisServDu");
		}
		if (infoOfrOrderFeeHisServDu == null) {
			infoOfrOrderFeeHisServDu = (InfoOfrOrderFeeHisServDu) ToolSpring.getBean("InfoOfrOrderFeeHisServDu");
		}
		if (infoOfrOrderInvoiceHisServDu == null) {
			infoOfrOrderInvoiceHisServDu = (InfoOfrOrderInvoiceHisServDu) ToolSpring
					.getBean("InfoOfrOrderInvoiceHisServDu");
		}
		if (infoOfrOrderPayHisServDu == null) {
			infoOfrOrderPayHisServDu = (InfoOfrOrderPayHisServDu) ToolSpring.getBean("InfoOfrOrderPayHisServDu");
		}
		if (infoServiceOrderExtHisServDu == null) {
			infoServiceOrderExtHisServDu = (InfoServiceOrderExtHisServDu) ToolSpring
					.getBean("InfoServiceOrderExtHisServDu");
		}
		if (infoServiceOrderTerminalHisServDu == null) {
			infoServiceOrderTerminalHisServDu = (InfoServiceOrderTerminalHisServDu) ToolSpring
					.getBean("InfoServiceOrderTerminalHisServDu");
		}
		if (infoServiceOrderSimCardHisServDu == null) {
			infoServiceOrderSimCardHisServDu = (InfoServiceOrderSimCardHisServDu) ToolSpring
					.getBean("InfoServiceOrderSimCardHisServDu");
		}
		if (procInstTaskInstHisServDu == null) {
			procInstTaskInstHisServDu = (ProcInstTaskInstHisServDu) ToolSpring.getBean("ProcInstTaskInstHisServDu");
		}
		if (procModServDu == null) {
			procModServDu = (ProcModServDu) ToolSpring.getBean("ProcModServDu");
		}
		if (codeListServDu == null) {
			codeListServDu = (CodeListServDu) ToolSpring.getBean("CodeListServDu");
		}
		if (abilityPlatformServDu == null) {
			abilityPlatformServDu = (AbilityPlatformServDu) ToolSpring.getBean("AbilityPlatformServDu");
		}
	}

	@SuppressWarnings("unchecked")
	private InfoServiceOrderActivityVo getActivity(InfoServiceOrderActivityVo vo,String jsession_id) throws Exception{
		String actId = vo.getActivity_id();
		String actNm = vo.getActivity_name();
		if (StringUtils.isEmpty(actNm) && !StringUtils.isEmpty(actId)) {
			CodeList paramCode = new CodeList();
			paramCode.setCode_id(actId);
			CodeList activity = codeListServDu.queryCodeListByCodeIdFromRedis(paramCode);
			if (activity != null) {
				actNm = activity.getCode_name();
			} else {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("SERVICE_NAME", "querySkuDefineAndSkuAttr");
				Map<String, String> map = new HashMap<String, String>();
				map.put("jsession_id", jsession_id);
				map.put("json_info", "{\"sku_define\":{\"sku_id\" : \"'" + actId + "'\",\"sku_type\":\"03\"}}");
				jsonObj.put("params", map);
				logger.info("-------通过能力平台查询UGC活动名称---------");

				UocMessage abilityMessage = abilityPlatformServDu.CallAbilityPlatform(jsonObj.toString(), "1200", "", "");
				if ("0000".equals(abilityMessage.getRespCode()) && "200".equals(abilityMessage.getArgs().get("code").toString())) {
					if (abilityMessage.getArgs().get("json_info") != null) {
						String json_info_out = (String) abilityMessage.getArgs().get("json_info");
						Map<String, Object> outMap = jsonToBeanServDu.jsonToMap(json_info_out);
						Map<String, Object> jsonMap = new HashMap<String, Object>();
						if (outMap.get("args") instanceof String) {
							jsonMap = jsonToBeanServDu.jsonToMap((String) outMap.get("args"));
						} else {
							jsonMap = (Map<String, Object>) outMap.get("args");
						}
						if (jsonMap != null && jsonMap.get("skuDefineVo") != null) {
							Map<String, String> skuMap = (Map<String, String>) jsonMap.get("skuDefineVo");
							actNm = skuMap.get("sku_name");
						} else {
							logger.info("-------UGC sku_define表未定义该sku---------");
						}
					}
				}
			}
		}

		vo.setActivity_name(actNm);
		return vo;
	}
}
