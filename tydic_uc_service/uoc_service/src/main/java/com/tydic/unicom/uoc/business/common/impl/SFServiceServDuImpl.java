package com.tydic.unicom.uoc.business.common.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskDealRecordPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskDealRecordServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.uoc.business.common.interfaces.SFServiceServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderDataBakServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.CheckProcessServDu;
import com.tydic.unicom.uoc.service.activiti.interfaces.ProcessCirculationServDu;
import com.tydic.unicom.uoc.service.code.interfaces.CodeListServDu;
import com.tydic.unicom.uoc.service.code.interfaces.ProcTacheRetServDu;
import com.tydic.unicom.uoc.service.code.vo.CodeListVo;
import com.tydic.unicom.uoc.service.code.vo.ProcTacheRetVo;
import com.tydic.unicom.uoc.service.common.interfaces.AbilityPlatformServDu;
import com.tydic.unicom.uoc.service.common.interfaces.CrawlerActivemqSendServDu;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.mod.interfaces.OrdModFunctionServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderHisVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoDeliverOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoDeliverOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoSaleOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.uoc.service.task.interfaces.CheckArtificialTaskServDu;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.webUtil.CrawlerActivemqSendPo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

public class SFServiceServDuImpl implements SFServiceServDu{

	private static Logger logger = Logger.getLogger(SFServiceServDuImpl.class);

	@Autowired
	private OperServDu operServDu;

	@Autowired
	private OrderDataBakServDu orderDataBakServDu;

	@Autowired
	private InfoServiceOrderHisServDu infoServiceOrderHisServDu;

	@Autowired
	private ProcessCirculationServDu processCirculationServDu;

	@Autowired
	private AbilityPlatformServDu abilityPlatformServDu;

	@Autowired
	private InfoSaleOrderServDu infoSaleOrderServDu;

	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;

	@Autowired
	private CheckProcessServDu checkProcessServDu;

	@Autowired
	private CodeListServDu codeListServDu;

	@Autowired
	private InfoDeliverOrderServDu infoDeliverOrderServDu;

	@Autowired
	private GetIdServDu getIdServDu;

	@Autowired
	private CheckArtificialTaskServDu checkArtificialTaskServDu;

	@Autowired
	private ProcInstTaskInstServ procInstTaskInstServ;

	@Autowired
	private ProcInstTaskDealRecordServ procInstTaskDealRecordServ;

	@Autowired
	private ProcTacheRetServDu procTacheRetServDu;

	@Autowired
	private OrdModFunctionServDu ordModFunctionServDu;

	@Autowired
	private CrawlerActivemqSendServDu crawlerActivemqSendServDu;

	@Override
	public UocMessage getLogisticsRouterInfo(String jsession_id,String order_no, String order_no_type, String query_type) throws Exception {
		logger.info("===============获取顺丰物流信息===============");
		UocMessage uocMessage = new UocMessage();
		//参数校验
		if(jsession_id == null || jsession_id.equals("")){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("jsession_id不能为空");
			return uocMessage;
		}
		if(order_no == null || order_no.equals("")){
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("订单号不能为空");
			return uocMessage;
		}
		if(order_no_type == null || order_no_type.equals("")){
			order_no_type = "2";
		}
		if(query_type == null || query_type.equals("")){
			query_type = "1";
		}
		//获取登陆信息服务，校验是否登陆
		UocMessage uocMessageLogin = operServDu.isLogin(jsession_id);
		if(!"0000".equals(uocMessageLogin.getRespCode())){
			return uocMessageLogin;
		}
		Map<String,Object> oper_info =(Map<String, Object>) uocMessageLogin.getArgs().get("oper_info");

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String tran_id = format.format(new Date());

		//获取顺丰报文head
		String sfHead = "";
		CodeListVo sfHeadCodeListVo = new CodeListVo();
		sfHeadCodeListVo.setType_code("sf_head");
		CodeListVo sfHeadResult = codeListServDu.queryCodeListByTypeCode(sfHeadCodeListVo);
		if(sfHeadResult == null || sfHeadResult.getCode_id() == null || sfHeadResult.getCode_id().equals("")){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取顺丰报文head失败");
			return uocMessage;
		}
		else{
			sfHead = sfHeadResult.getCode_id();
		}

		//拼接获取顺丰物流信息报文(通过订单号获取)
		String SFXmlStr = getSFLogisticsRouterInfoXml(sfHead,tran_id,order_no_type,query_type,order_no);
		//调用能力平台
		logger.info("==============调用能力平台（获取顺丰物流信息）请求报文================="+SFXmlStr);
		UocMessage uocMessageAbility = abilityPlatformServDu.CallAbilityPlatform(SFXmlStr, "500", "","");
		if(!"0000".equals(uocMessageAbility.getRespCode())){
			return uocMessageAbility;
		}
		else{
			String resultStr = (String) uocMessageAbility.getArgs().get("json_info");
			logger.info("===============调用能力平台（获取顺丰物流信息）返回报文================="+resultStr);
			//解析顺丰物流信息返回报文
			List<Map<String,String>> routerInfo = getRouterInfo(resultStr);
			uocMessage.setRespCode(RespCodeContents.SUCCESS);
			uocMessage.addArg("RouterInfo", routerInfo);
		}
		return uocMessage;
	}

	/**拼接获取顺丰物流信息报文(通过订单号获取)*/
	public String getSFLogisticsRouterInfoXml(String sf_head,String tran_id,String order_no_type,String query_type,String order_no) throws Exception{
		String SFXml = "<UNI_BSS><UNI_HEAD>"+
				"<TRAN_ID>"+tran_id+"</TRAN_ID>"+
				"<ORIG_DOMAIN>USS</ORIG_DOMAIN>"+
				"<SERVICE_NAME>send2SF</SERVICE_NAME>"+
				"<ACTION_CODE>0</ACTION_CODE>"+
			"</UNI_HEAD>"+
			"<UNI_BODY>"+
				"<reqxml><![CDATA[<Request service='RouteService' lang='zh-CN'><Head>"+sf_head+"</Head><Body>"+
						"<RouteRequest tracking_type='"+order_no_type+"' method_type='"+query_type+"' tracking_number='"+order_no+"'/>"+
						"</Body></Request>]]></reqxml>"+
						"</UNI_BODY>"+
					"</UNI_BSS>";
		return SFXml;
	}

	/**解析物流路由信息报文*/
	public List<Map<String,String>> getRouterInfo(String resultStr) throws Exception{
		logger.info("==============解析物流路由信息报文============");
		List<Map<String,String>> listMapDate = new ArrayList<Map<String,String>>();
		String[] routerStrings =  resultStr.split("<Route ");
		for(int i=1;i<routerStrings.length;i++){
			String temp = routerStrings[i];
			Map<String,String> tempMap = new HashMap<String,String>();
			int startRemark = temp.indexOf("remark=");
			int endRemark = temp.indexOf('"', startRemark+8);
			String remark = temp.substring(startRemark+8, endRemark);
			int startAcceptTime = temp.indexOf("accept_time=");
			int endAcceptTime = temp.indexOf('"', startAcceptTime+13);
			String acceptTime = temp.substring(startAcceptTime+13, endAcceptTime);
			int startAcceptAddress = temp.indexOf("accept_address=");
			int endAcceptAddress = temp.indexOf('"', startAcceptAddress+16);
			String acceptAddress = temp.substring(startAcceptAddress+16, endAcceptAddress);
			tempMap.put("remark", remark);
			tempMap.put("acceptTime", acceptTime);
			tempMap.put("acceptAddress", acceptAddress);
			listMapDate.add(tempMap);
		}
		return listMapDate;
	}

	@Override
	public UocMessage sendLogisticsInfoNoProcess(String jsession_id, String serv_order_no, String d_contact, String d_tel,
			String d_address, String j_tel, String j_contact, String j_address, String name, String remark, String cod_account,
			String cod_charge, String insure_charge,String need_return_tracking_no) throws Exception {

		logger.info("=============顺丰发货（无流程）==============");
		UocMessage uocMessage = new UocMessage();
		//验证输入参数
		if(StringUtils.isEmpty(jsession_id) || StringUtils.isEmpty(serv_order_no) || StringUtils.isEmpty(d_contact)
				|| StringUtils.isEmpty(d_tel)|| StringUtils.isEmpty(d_address) || StringUtils.isEmpty(j_tel)
				|| StringUtils.isEmpty(j_contact) || StringUtils.isEmpty(j_address) || StringUtils.isEmpty(name)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("必输参数不能为空");
			return uocMessage;
		}
		if (StringUtils.isEmpty(remark)) {
			remark = "";
		}
		//获取登陆信息服务，校验是否登陆
		UocMessage uocMessageLogin = operServDu.isLogin(jsession_id);
		if(!"0000".equals(uocMessageLogin.getRespCode())){
			return uocMessageLogin;
		}
		Map<String, Object> oper_info = (Map<String, Object>) uocMessageLogin.getArgs().get("oper_info");

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String tran_id = format.format(new Date());

		//获取顺丰寄付月结账号
		String month_account = "";
		CodeListVo codeListVo = new CodeListVo();
		codeListVo.setType_code("month_account");
		CodeListVo codeListVoResult = codeListServDu.queryCodeListByTypeCode(codeListVo);
		if(codeListVoResult == null || codeListVoResult.getCode_id() == null || codeListVoResult.getCode_id().equals("")){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取顺丰寄付月结账号失败");
			return uocMessage;
		}
		else{
			month_account = codeListVoResult.getCode_id();
		}

		//获取顺丰报文head
		String sfHead = "";
		CodeListVo sfHeadCodeListVo = new CodeListVo();
		sfHeadCodeListVo.setType_code("sf_head");
		CodeListVo sfHeadResult = codeListServDu.queryCodeListByTypeCode(sfHeadCodeListVo);
		if(sfHeadResult == null || sfHeadResult.getCode_id() == null || sfHeadResult.getCode_id().equals("")){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取顺丰报文head失败");
			return uocMessage;
		}
		else{
			sfHead = sfHeadResult.getCode_id();
		}

		//生成交付订单号
		String deliver_order_no = getIdServDu.getId("createDeliverOrderNo", oper_info.get("province_code").toString(), oper_info.get("area_code").toString(), "");
		//参拼接成顺丰发货XML
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		String xml_cod_charge = "";
		String xml_insure_charge = "";
		if (!StringUtils.isEmpty(cod_charge)) {
			xml_cod_charge = String.valueOf(dcmFmt.format(Double.parseDouble(cod_charge) / 100));
			if (xml_cod_charge.indexOf(".") > 0) {
				xml_cod_charge = xml_cod_charge.replaceAll("0+?$", "");// 去掉多余的0
				xml_cod_charge = xml_cod_charge.replaceAll("[.]$", "");// 如最后一位是.则去掉
			}
		}
		if (!StringUtils.isEmpty(insure_charge)) {
			xml_insure_charge = String.valueOf(dcmFmt.format(Double.parseDouble(insure_charge) / 100));
			if (xml_insure_charge.indexOf(".") > 0) {
				xml_insure_charge = xml_insure_charge.replaceAll("0+?$", "");// 去掉多余的0
				xml_insure_charge = xml_insure_charge.replaceAll("[.]$", "");// 如最后一位是.则去掉
			}
		}
		String SFXmlStr = getSFSendInfoXml(sfHead,tran_id, month_account, deliver_order_no, j_contact, j_tel, j_address, d_contact, d_tel,
				d_address, remark, name, cod_account, xml_cod_charge, xml_insure_charge, need_return_tracking_no);
		logger.info("==========================调用能力平台（顺丰发货）请求报文========================="+SFXmlStr);
		//调用能力平台，顺丰发货
		UocMessage uocMessageAbility = abilityPlatformServDu.CallAbilityPlatform(SFXmlStr, "500", "","");
		if(!"0000".equals(uocMessageAbility.getRespCode())){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			String resultStr = (String) uocMessageAbility.getArgs().get("json_info");
			logger.info("===============调用能力平台（顺丰发货）返回报文================="+resultStr);
			int startErrorCode=resultStr.indexOf("<ERROR");
			int endErrorCode=resultStr.indexOf("</ERROR>");
			String resultErrorCode = resultStr.substring(startErrorCode,endErrorCode);
			logger.info("====================ErrorCode==================="+resultErrorCode);
			uocMessage.setContent(resultErrorCode);
			return uocMessage;
		}
		else{
			String resultStr = (String) uocMessageAbility.getArgs().get("json_info");
			logger.info("===============调用能力平台（顺丰发货）返回报文================="+resultStr);
			int startFilterResult = resultStr.indexOf("filter_result=");
			String filter_result = resultStr.substring(startFilterResult+15, startFilterResult+16);
			//可以派送
			if("2".equals(filter_result)){
				//目的地区域编码
				int startDestCode = resultStr.indexOf("destcode=");
				String destCode = resultStr.substring(startDestCode+10, startDestCode+13);
				String origCode="";
				if("1".equals(need_return_tracking_no)){
					int startOrigCode = resultStr.indexOf("origincode=");
					origCode = resultStr.substring(startOrigCode + 12, startOrigCode + 15);
				}
				//快递单号
				int startMailNo = resultStr.indexOf("mailno=");
				String mailno = resultStr.substring(startMailNo+8, startMailNo+20);
				String return_tracking_no="";
				if("1".equals(need_return_tracking_no)){
					int startTrackNo = resultStr.indexOf("return_tracking_no=");
					return_tracking_no = resultStr.substring(startTrackNo + 20, startTrackNo + 32);
				}
				//去当前库取销售订单号
				InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
				infoServiceOrderVo.setServ_order_no(serv_order_no);
				InfoServiceOrderVo infoServiceOrderVoResult = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
				boolean isok = false;
				//当前库取不到销售订单号
				if(infoServiceOrderVoResult == null || infoServiceOrderVoResult.getSale_order_no() == null || infoServiceOrderVoResult.getSale_order_no().equals("")){
					InfoServiceOrderHisVo infoServiceOrderHisVo = new InfoServiceOrderHisVo();
					infoServiceOrderHisVo.setServ_order_no(serv_order_no);
					List<InfoServiceOrderHisVo> list = infoServiceOrderHisServDu.queryInfoServiceOrderHisByOrderNo(infoServiceOrderHisVo);
					if(list == null || list.size()>0){
						uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
						uocMessage.setContent("在历史库未取到销售订单号");
						return uocMessage;
					}
					else{
						InfoDeliverOrderVo InfoDeliverOrderVoQuery = new InfoDeliverOrderVo();
						InfoDeliverOrderVoQuery.setSale_order_no(list.get(0).getSale_order_no());
						List<InfoDeliverOrderVo> listInfoDeliverOrder = infoDeliverOrderServDu.queryInfoDeliverOrderBySaleOrderNo(InfoDeliverOrderVoQuery);

						String create_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
						InfoDeliverOrderVo infoDeliverOrderVoInsert = new InfoDeliverOrderVo();

						infoDeliverOrderVoInsert.setContact_name(listInfoDeliverOrder.get(0).getContact_name());
						infoDeliverOrderVoInsert.setContact_tel(listInfoDeliverOrder.get(0).getContact_tel());
						infoDeliverOrderVoInsert.setPost_province(listInfoDeliverOrder.get(0).getPost_province());
						infoDeliverOrderVoInsert.setPost_city(listInfoDeliverOrder.get(0).getPost_city());
						infoDeliverOrderVoInsert.setPost_area(listInfoDeliverOrder.get(0).getPost_area());
						infoDeliverOrderVoInsert.setAddress(listInfoDeliverOrder.get(0).getAddress());
						infoDeliverOrderVoInsert.setPost_province_name(listInfoDeliverOrder.get(0).getPost_province_name());
						infoDeliverOrderVoInsert.setPost_city_name(listInfoDeliverOrder.get(0).getPost_city_name());
						infoDeliverOrderVoInsert.setPost_area_name(listInfoDeliverOrder.get(0).getPost_area_name());
						infoDeliverOrderVoInsert.setCod_charge(listInfoDeliverOrder.get(0).getCod_charge());

						infoDeliverOrderVoInsert.setDeliver_order_no(deliver_order_no);
						infoDeliverOrderVoInsert.setSale_order_no(list.get(0).getSale_order_no());
						infoDeliverOrderVoInsert.setCreate_time(create_time);
						infoDeliverOrderVoInsert.setDeliver_state("100");
						infoDeliverOrderVoInsert.setAccept_oper_no(oper_info.get("oper_no").toString());
						infoDeliverOrderVoInsert.setAccept_oper_name(oper_info.get("oper_name").toString());
						infoDeliverOrderVoInsert.setAccept_depart_name(oper_info.get("depart_name").toString());
						infoDeliverOrderVoInsert.setAccept_depart_no(oper_info.get("depart_no").toString());
						infoDeliverOrderVoInsert.setProvince_code(oper_info.get("province_code").toString());
						infoDeliverOrderVoInsert.setArea_code(oper_info.get("area_code").toString());
						infoDeliverOrderVoInsert.setLogistics_no(mailno);
						infoDeliverOrderVoInsert.setSend_target_addr(destCode);
						infoDeliverOrderVoInsert.setGoods_name(name);
						infoDeliverOrderVoInsert.setNote(remark);
						infoDeliverOrderVoInsert.setSend_name(j_contact);
						infoDeliverOrderVoInsert.setSend_addr(j_address);
						infoDeliverOrderVoInsert.setReal_phone(j_tel);
						infoDeliverOrderVoInsert.setInsure_charge(insure_charge);
						infoDeliverOrderVoInsert.setSend_origin_addr(origCode);
						infoDeliverOrderVoInsert.setReturn_tracking_no(return_tracking_no);
						isok = infoDeliverOrderServDu.create(infoDeliverOrderVoInsert);
						if(isok){
							//备份数据
							UocMessage bakMessage = orderDataBakServDu.createOrderDataBakup(deliver_order_no, "103");
							if(!"0000".equals(bakMessage.getRespCode())){
								uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
								uocMessage.setContent("发货成功，备份数据失败");
								return uocMessage;
							}
							else{
								uocMessage.setRespCode(RespCodeContents.SUCCESS);
								uocMessage.addArg("filterResult", filter_result);
								uocMessage.addArg("destCode", destCode);
								uocMessage.addArg("origCode", origCode);
								uocMessage.addArg("mailNo", mailno);
								uocMessage.addArg("returnTrackingNo", return_tracking_no);
								return uocMessage;
							}
						}
						else{
							uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
							uocMessage.setContent("发货成功，但记录交付订单失败");
							return uocMessage;
						}
					}
				}
				//当前库可以取到销售订单号
				else{
					InfoDeliverOrderVo InfoDeliverOrderVoQuery = new InfoDeliverOrderVo();
					InfoDeliverOrderVoQuery.setSale_order_no(infoServiceOrderVoResult.getSale_order_no());
					List<InfoDeliverOrderVo> listInfoDeliverOrder = infoDeliverOrderServDu.queryInfoDeliverOrderBySaleOrderNo(InfoDeliverOrderVoQuery);

					String create_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					InfoDeliverOrderVo infoDeliverOrderVoInsert = new InfoDeliverOrderVo();

					infoDeliverOrderVoInsert.setContact_name(listInfoDeliverOrder.get(0).getContact_name());
					infoDeliverOrderVoInsert.setContact_tel(listInfoDeliverOrder.get(0).getContact_tel());
					infoDeliverOrderVoInsert.setPost_province(listInfoDeliverOrder.get(0).getPost_province());
					infoDeliverOrderVoInsert.setPost_city(listInfoDeliverOrder.get(0).getPost_city());
					infoDeliverOrderVoInsert.setPost_area(listInfoDeliverOrder.get(0).getPost_area());
					infoDeliverOrderVoInsert.setAddress(listInfoDeliverOrder.get(0).getAddress());
					infoDeliverOrderVoInsert.setPost_province_name(listInfoDeliverOrder.get(0).getPost_province_name());
					infoDeliverOrderVoInsert.setPost_city_name(listInfoDeliverOrder.get(0).getPost_city_name());
					infoDeliverOrderVoInsert.setPost_area_name(listInfoDeliverOrder.get(0).getPost_area_name());
					infoDeliverOrderVoInsert.setCod_charge(listInfoDeliverOrder.get(0).getCod_charge());


					infoDeliverOrderVoInsert.setDeliver_order_no(deliver_order_no);
					infoDeliverOrderVoInsert.setSale_order_no(infoServiceOrderVoResult.getSale_order_no());
					infoDeliverOrderVoInsert.setCreate_time(create_time);
					infoDeliverOrderVoInsert.setDeliver_state("100");
					infoDeliverOrderVoInsert.setAccept_oper_no(oper_info.get("oper_no").toString());
					infoDeliverOrderVoInsert.setAccept_oper_name(oper_info.get("oper_name").toString());
					infoDeliverOrderVoInsert.setAccept_depart_name(oper_info.get("depart_name").toString());
					infoDeliverOrderVoInsert.setAccept_depart_no(oper_info.get("depart_no").toString());
					infoDeliverOrderVoInsert.setProvince_code(oper_info.get("province_code").toString());
					infoDeliverOrderVoInsert.setArea_code(oper_info.get("area_code").toString());
					infoDeliverOrderVoInsert.setLogistics_no(mailno);
					infoDeliverOrderVoInsert.setSend_target_addr(destCode);
					infoDeliverOrderVoInsert.setGoods_name(name);
					infoDeliverOrderVoInsert.setNote(remark);
					infoDeliverOrderVoInsert.setSend_name(j_contact);
					infoDeliverOrderVoInsert.setSend_addr(j_address);
					infoDeliverOrderVoInsert.setReal_phone(j_tel);
					infoDeliverOrderVoInsert.setInsure_charge(insure_charge);
					infoDeliverOrderVoInsert.setSend_origin_addr(origCode);
					infoDeliverOrderVoInsert.setReturn_tracking_no(return_tracking_no);
					isok = infoDeliverOrderServDu.create(infoDeliverOrderVoInsert);
					if(isok){
						uocMessage.setRespCode(RespCodeContents.SUCCESS);
						uocMessage.addArg("filterResult", filter_result);
						uocMessage.addArg("destCode", destCode);
						uocMessage.addArg("origCode", origCode);
						uocMessage.addArg("mailNo", mailno);
						uocMessage.addArg("returnTrackingNo", return_tracking_no);
						return uocMessage;
					}
					else{
						uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
						uocMessage.setContent("发货成功，但记录交付订单失败");
						return uocMessage;
					}
				}
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.addArg("filterResult", filter_result);
				return uocMessage;
			}
		}
	}

	@Override
	public UocMessage sendlogisticsInfo(String jsession_id, String serv_order_no, String d_contact, String d_tel, String d_address,
			String j_tel, String j_contact, String j_address, String name, String remark, String flow_type, String deal_code,
			String deal_desc, String deal_system_no, String cod_account, String cod_charge, String insure_charge,String need_return_tracking_no) throws Exception {

		logger.info("=============顺丰发货==============");
		UocMessage uocMessage = new UocMessage();
		//验证输入参数
		if (StringUtils.isEmpty(jsession_id) || StringUtils.isEmpty(serv_order_no) || StringUtils.isEmpty(d_contact)
				|| StringUtils.isEmpty(d_tel) || StringUtils.isEmpty(d_address) || StringUtils.isEmpty(j_tel)
				|| StringUtils.isEmpty(j_contact) || StringUtils.isEmpty(j_address) || StringUtils.isEmpty(name)
				|| StringUtils.isEmpty(flow_type)) {
			uocMessage.setRespCode(RespCodeContents.PARAM_ERROR);
			uocMessage.setContent("必输参数不能为空");
			return uocMessage;
		}
		if (StringUtils.isEmpty(remark)) {
			remark = "";
		}
		//获取登陆信息服务，校验是否登陆
		UocMessage uocMessageLogin = operServDu.isLogin(jsession_id);
		if(!"0000".equals(uocMessageLogin.getRespCode())){
			return uocMessageLogin;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> oper_info = (Map<String, Object>) uocMessageLogin.getArgs().get("oper_info");
		//查询销售订单表，判断交付标识必须为10
		InfoServiceOrderVo infoServiceOrderVo = new InfoServiceOrderVo();
		infoServiceOrderVo.setServ_order_no(serv_order_no);
		InfoServiceOrderVo infoServiceOrderVoResult = infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);
		if(infoServiceOrderVoResult == null || infoServiceOrderVoResult.getSale_order_no() == null || infoServiceOrderVoResult.getSale_order_no().equals("")){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("未查询到销售订单号");
			return uocMessage;
		}

		InfoSaleOrderVo infoSaleOrderVo = new InfoSaleOrderVo();
		infoSaleOrderVo.setSale_order_no(infoServiceOrderVoResult.getSale_order_no());
		InfoSaleOrderVo infoSaleOrderVoResult = infoSaleOrderServDu.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderVo);
		if(infoSaleOrderVoResult == null || infoSaleOrderVoResult.getExpress_flag() == null || !infoSaleOrderVoResult.getExpress_flag().equals("10")){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("交付标识错误");
			return uocMessage;
		}

		// 爬虫订单
		boolean isCrawlOrder = "CRAWLER".equals(infoSaleOrderVoResult.getAccept_system());

		String proc_inst_id = infoServiceOrderVoResult.getProc_inst_id();
		if( StringUtils.isEmpty(proc_inst_id)){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("未查询到服务订单对应流程号");
			return uocMessage;
		}
		//校验是否满足流转要求
		if (flow_type.equals("0")) {
			UocMessage uocMessageCheckProcess = checkProcessServDu.checkProcess(proc_inst_id, serv_order_no, "0", null);
			if (!"0000".equals(uocMessageCheckProcess.getRespCode())) {
				return uocMessageCheckProcess;
			}
		}
		// 调用BASE0025进行任务校验
		UocMessage checkMessage = checkArtificialTaskServDu.checkArtificialTaskProcess(serv_order_no, "S00013", oper_info);
		if (!checkMessage.getRespCode().equals(RespCodeContents.SUCCESS)) {
			return checkMessage;
		}

		//获取顺丰寄付月结账号
		String month_account = "";
		CodeListVo codeListVo = new CodeListVo();
		codeListVo.setType_code("month_account");
		CodeListVo codeListVoResult = codeListServDu.queryCodeListByTypeCode(codeListVo);
		if(codeListVoResult == null || codeListVoResult.getCode_id() == null || codeListVoResult.getCode_id().equals("")){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取顺丰寄付月结账号失败");
			return uocMessage;
		}
		else{
			month_account = codeListVoResult.getCode_id();
		}

		//获取顺丰报文head
		String sfHead = "";
		CodeListVo sfHeadCodeListVo = new CodeListVo();
		sfHeadCodeListVo.setType_code("sf_head");
		CodeListVo sfHeadResult = codeListServDu.queryCodeListByTypeCode(sfHeadCodeListVo);
		if(sfHeadResult == null || sfHeadResult.getCode_id() == null || sfHeadResult.getCode_id().equals("")){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			uocMessage.setContent("获取顺丰报文head失败");
			return uocMessage;
		}
		else{
			sfHead = sfHeadResult.getCode_id();
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String tran_id = format.format(new Date());

		//参拼接成顺丰发货XML
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		String xml_cod_charge = "";
		String xml_insure_charge = "";
		if (!StringUtils.isEmpty(cod_charge)) {
			xml_cod_charge = String.valueOf(dcmFmt.format(Double.parseDouble(cod_charge) / 100));
			if (xml_cod_charge.indexOf(".") > 0) {
				xml_cod_charge = xml_cod_charge.replaceAll("0+?$", "");// 去掉多余的0
				xml_cod_charge = xml_cod_charge.replaceAll("[.]$", "");// 如最后一位是.则去掉
			}
		}
		if (!StringUtils.isEmpty(insure_charge)) {
			xml_insure_charge = String.valueOf(dcmFmt.format(Double.parseDouble(insure_charge) / 100));
			if (xml_insure_charge.indexOf(".") > 0) {
				xml_insure_charge = xml_insure_charge.replaceAll("0+?$", "");// 去掉多余的0
				xml_insure_charge = xml_insure_charge.replaceAll("[.]$", "");// 如最后一位是.则去掉
			}
		}
		String SFXmlStr = getSFSendInfoXml(sfHead,tran_id, month_account, serv_order_no, j_contact, j_tel, j_address, d_contact, d_tel, d_address,
				remark, name, cod_account, xml_cod_charge, xml_insure_charge, need_return_tracking_no);
		logger.info("==========================调用能力平台（顺丰发货）请求报文========================="+SFXmlStr);
		//调用能力平台，顺丰发货
		UocMessage uocMessageAbility = abilityPlatformServDu.CallAbilityPlatform(SFXmlStr, "500", "","");
		if(!"0000".equals(uocMessageAbility.getRespCode())){
			uocMessage.setRespCode(RespCodeContents.SERVICE_FAIL);
			String resultStr = (String) uocMessageAbility.getArgs().get("json_info");
			logger.info("===============调用能力平台（顺丰发货）返回报文================="+resultStr);
			int startErrorCode=resultStr.indexOf("<ERROR");
			int endErrorCode=resultStr.indexOf("</ERROR>");
			String resultErrorCode = resultStr.substring(startErrorCode,endErrorCode);
			logger.info("====================ErrorCode==================="+resultErrorCode);
			uocMessage.setContent(resultErrorCode);
			return uocMessage;
		}
		else{
			String resultStr = (String) uocMessageAbility.getArgs().get("json_info");
			logger.info("===============调用能力平台（顺丰发货）返回报文================="+resultStr);
			int startFilterResult = resultStr.indexOf("filter_result=");
			String filter_result = resultStr.substring(startFilterResult+15, startFilterResult+16);
			//可以派送
			if("2".equals(filter_result)){
				//目的地区域编码
				int startDestCode = resultStr.indexOf("destcode=");
				String destCode = resultStr.substring(startDestCode+10, startDestCode+13);
				String origCode = "";
				if("1".equals(need_return_tracking_no)){
					int startOrigCode = resultStr.indexOf("origincode=");
					origCode = resultStr.substring(startOrigCode + 12, startOrigCode + 15);
				}
				//快递单号
				int startMailNo = resultStr.indexOf("mailno=");
				String mailno = resultStr.substring(startMailNo+8, startMailNo+20);
				String return_tracking_no = "";
				if("1".equals(need_return_tracking_no)){
					int startTrackNo = resultStr.indexOf("return_tracking_no=");
					return_tracking_no = resultStr.substring(startTrackNo + 20, startTrackNo + 32);
				}

				//记录交付订单表，销售订单收件人信息表
				InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
				infoDeliverOrderVo.setSale_order_no(infoServiceOrderVoResult.getSale_order_no());
				List<InfoDeliverOrderVo> list = infoDeliverOrderServDu.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderVo);
				boolean isok = false;
				if(list != null && list.size()>0){
					//更新交付订单表
					InfoDeliverOrderVo infoDeliverOrderVoUpdate = list.get(0);
					infoDeliverOrderVoUpdate.setAccept_oper_no(oper_info.get("oper_no").toString());
					infoDeliverOrderVoUpdate.setAccept_oper_name(oper_info.get("oper_name").toString());
					infoDeliverOrderVoUpdate.setAccept_depart_name(oper_info.get("depart_name").toString());
					infoDeliverOrderVoUpdate.setAccept_depart_no(oper_info.get("depart_no").toString());
					infoDeliverOrderVoUpdate.setProvince_code(oper_info.get("province_code").toString());
					infoDeliverOrderVoUpdate.setArea_code(oper_info.get("area_code").toString());
					infoDeliverOrderVoUpdate.setLogistics_no(mailno);
					infoDeliverOrderVoUpdate.setSend_target_addr(destCode);
					infoDeliverOrderVoUpdate.setGoods_name(name);
					infoDeliverOrderVoUpdate.setNote(remark);
					infoDeliverOrderVoUpdate.setSend_name(j_contact);
					infoDeliverOrderVoUpdate.setSend_addr(j_address);
					infoDeliverOrderVoUpdate.setReal_phone(j_tel);
					infoDeliverOrderVoUpdate.setInsure_charge(insure_charge);
					infoDeliverOrderVoUpdate.setDeliver_state("102");
					infoDeliverOrderVoUpdate.setSend_origin_addr(origCode);
					infoDeliverOrderVoUpdate.setReturn_tracking_no(return_tracking_no);
					String finish_time =DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
					infoDeliverOrderVoUpdate.setFinish_time(finish_time);
					infoDeliverOrderVoUpdate.setDeliver_time(finish_time);
					isok = infoDeliverOrderServDu.updateInfoDeliverOrder(infoDeliverOrderVoUpdate);
				}
				else{
					String deliver_order_no = getIdServDu.getId("createDeliverOrderNo", oper_info.get("province_code").toString(), oper_info.get("area_code").toString(), "");
					String create_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					//插入交付订单表
					InfoDeliverOrderVo infoDeliverOrderVoInsert = new InfoDeliverOrderVo();
					infoDeliverOrderVoInsert.setDeliver_order_no(deliver_order_no);
					infoDeliverOrderVoInsert.setSale_order_no(infoServiceOrderVoResult.getSale_order_no());
					infoDeliverOrderVoInsert.setCreate_time(create_time);
					infoDeliverOrderVoInsert.setDeliver_state("100");
					infoDeliverOrderVoInsert.setDeliver_time(create_time);
					infoDeliverOrderVoInsert.setAccept_oper_no(oper_info.get("oper_no").toString());
					infoDeliverOrderVoInsert.setAccept_oper_name(oper_info.get("oper_name").toString());
					infoDeliverOrderVoInsert.setAccept_depart_name(oper_info.get("depart_name").toString());
					infoDeliverOrderVoInsert.setAccept_depart_no(oper_info.get("depart_no").toString());
					infoDeliverOrderVoInsert.setProvince_code(oper_info.get("province_code").toString());
					infoDeliverOrderVoInsert.setArea_code(oper_info.get("area_code").toString());
					infoDeliverOrderVoInsert.setLogistics_no(mailno);
					infoDeliverOrderVoInsert.setSend_target_addr(destCode);
					infoDeliverOrderVoInsert.setGoods_name(name);
					infoDeliverOrderVoInsert.setNote(remark);
					infoDeliverOrderVoInsert.setSend_name(j_contact);
					infoDeliverOrderVoInsert.setSend_addr(j_address);
					infoDeliverOrderVoInsert.setReal_phone(j_tel);
					infoDeliverOrderVoInsert.setInsure_charge(insure_charge);
					infoDeliverOrderVoInsert.setSend_origin_addr(origCode);
					infoDeliverOrderVoInsert.setReturn_tracking_no(return_tracking_no);
					isok = infoDeliverOrderServDu.create(infoDeliverOrderVoInsert);
				}
				
				// 查询环节回调表取出接口url，模板
				ProcTacheRetVo procTacheRetVo = new ProcTacheRetVo();
				procTacheRetVo.setTache_code("S00013");
				procTacheRetVo.setProvince_code(oper_info.get("province_code").toString());
				procTacheRetVo.setArea_code(oper_info.get("area_code").toString());
				List<ProcTacheRetVo> tacheRetList = procTacheRetServDu.queryProcTacheRetByVo(procTacheRetVo);
				if (tacheRetList != null && tacheRetList.size() > 0) {
					// BASE0006订单模板出库服务
					UocMessage ordModOut = ordModFunctionServDu
							.outByOrdMod(serv_order_no, tacheRetList.get(0).getCall_ord_mod(), "101", "");
					if (ordModOut != null) {
						if ("0000".equals(ordModOut.getRespCode())) {
							logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>环节回调出库:" + ordModOut.getContent()
									+ "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
							Map<String, Object> ordModOutMap = ordModOut.getArgs();
							String json_out = (String) ordModOutMap.get("json_info");
							String type = (String) ordModOutMap.get("interface_type");
							String interface_param = (String) ordModOutMap.get("interface_param_json");

							if (isCrawlOrder) {
								type = "702";
							}
							// BASE0018 调用能力平台服务
							UocMessage abilityMessage = abilityPlatformServDu.CallAbilityPlatform(json_out, type, interface_param,
									infoSaleOrderVoResult.getCallback_url());
							if (abilityMessage != null) {
								if ("0000".equals(abilityMessage.getRespCode())) {
									String respcode = (String) abilityMessage.getArgs().get("code");
									logger.info("---------------环节回调能力平台返回code：" + respcode);
									// 爬虫订单回调失败
									if ((respcode == null || !respcode.equals("200")) && isCrawlOrder) {
										crawlerCallBack(json_out);
									}
								}
							}
						}
					}
				}
				

				if (isok && flow_type.equals("0")) {
					UocMessage uocMessageProcessCirculatio = processCirculationServDu.processCirculation(serv_order_no, "101", "0", null, "");
					if(!"0000".equals(uocMessageProcessCirculatio.getRespCode())){
						//抛出业务异常
						UocMessage uocExceptionMsg = new UocMessage();
						uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
						uocExceptionMsg.setContent(uocMessageProcessCirculatio.getContent());
						throw new UocException(uocExceptionMsg);
					}
					else{
						// 更新人工任务实例表的任务状态为已处理，填写完成时间，还要记录人工任务操作记录表，记录传入的处理动作、处理描述、处理系统编码
						List<ProcInstTaskInstPo> taskList = procInstTaskInstServ
								.querytaskInstByOrderNoAndTacheCode(serv_order_no, "S00013");
						ProcInstTaskInstPo po = new ProcInstTaskInstPo();
						BeanUtils.copyProperties(taskList.get(0), po);
						String date = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
						po.setTask_state("102");
						po.setFinish_time(date);
						logger.info("---------更新人工任务实例表---------order_no=" + serv_order_no);
						boolean flagTem = procInstTaskInstServ.updateByOrderNo(po);
						if (!flagTem) {
							logger.error("更新人工任务实例表失败");
							//抛出业务异常
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
							uocExceptionMsg.setContent("更新人工任务实例表失败");
							throw new UocException(uocExceptionMsg);
						}

						ProcInstTaskDealRecordPo procInstTaskDealRecordPo = new ProcInstTaskDealRecordPo();
						String id = getIdServDu.getId("createLogId", oper_info.get("province_code").toString(), "*", "");
						procInstTaskDealRecordPo.setId(id);
						procInstTaskDealRecordPo.setTask_id(taskList.get(0).getTask_id());
						procInstTaskDealRecordPo.setProvince_code(taskList.get(0).getProvince_code());
						procInstTaskDealRecordPo.setArea_code(taskList.get(0).getArea_code());
						procInstTaskDealRecordPo.setPart_month(taskList.get(0).getPart_month());
						procInstTaskDealRecordPo.setDeal_time(date);
						procInstTaskDealRecordPo.setDeal_oper_no(oper_info.get("oper_no").toString());
						procInstTaskDealRecordPo.setDeal_system_no(deal_system_no);
						procInstTaskDealRecordPo.setDeal_code(deal_code);
						procInstTaskDealRecordPo.setDeal_desc(deal_desc);
						procInstTaskDealRecordPo.setOrder_no(serv_order_no);
						procInstTaskDealRecordPo.setOrder_type(taskList.get(0).getOrder_type());
						procInstTaskDealRecordPo.setTache_code(taskList.get(0).getTache_code());
						procInstTaskDealRecordPo.setOper_code(taskList.get(0).getOper_code());
						procInstTaskDealRecordPo.setCreate_time(taskList.get(0).getCreate_time());
						procInstTaskDealRecordPo.setProd_code(taskList.get(0).getProd_code());
						logger.info("---------创建人工任务操作记录---------order_no=" + serv_order_no);
						boolean flag = procInstTaskDealRecordServ.create(procInstTaskDealRecordPo);
						if (!flag) {
							logger.error("创建人工任务操作记录失败");
							//抛出业务异常
							UocMessage uocExceptionMsg = new UocMessage();
							uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
							uocExceptionMsg.setContent("创建人工任务操作记录失败");
							throw new UocException(uocExceptionMsg);
						}

						uocMessage.setRespCode(RespCodeContents.SUCCESS);
						uocMessage.addArg("filterResult", filter_result);
						uocMessage.addArg("destCode", destCode);
						uocMessage.addArg("origCode", origCode);
						uocMessage.addArg("mailNo", mailno);
						uocMessage.addArg("returnTrackingNo", return_tracking_no);
						return uocMessage;
					}
				}
				else{
					//抛出业务异常
					UocMessage uocExceptionMsg = new UocMessage();
					uocExceptionMsg.setRespCode(RespCodeContents.SERVICE_FAIL);
					uocExceptionMsg.setContent("记录交付订单信息错误");
					throw new UocException(uocExceptionMsg);
				}
			}
			else{
				uocMessage.setRespCode(RespCodeContents.SUCCESS);
				uocMessage.addArg("filterResult", filter_result);
				return uocMessage;
			}
		}
	}

	public String getSFSendInfoXml(String sf_head,String tran_id, String month_account, String serv_order_no, String j_contact, String j_tel,
			String j_address, String d_contact, String d_tel, String d_address, String remark, String name, String cod_account,
			String cod_charge, String insure_charge, String need_return_tracking_no) throws Exception {

		String addedXmlStr = "";
		if (!StringUtils.isEmpty(cod_charge)) {
			addedXmlStr += "<AddedService name='COD' value='" + cod_charge + "' value1='" + cod_account + "' />";
		}
		if (!StringUtils.isEmpty(insure_charge)) {
			addedXmlStr += "<AddedService name='INSURE' value='" + insure_charge + "' />";
		}

		String xmlStr = "<UNI_BSS>"+
			"<UNI_HEAD>"+
				"<TRAN_ID>"+tran_id+"</TRAN_ID>"+
				"<ORIG_DOMAIN>USS</ORIG_DOMAIN>"+
				"<SERVICE_NAME>send2SF</SERVICE_NAME>"+
				"<ACTION_CODE>0</ACTION_CODE>"+
			"</UNI_HEAD>"+
			"<UNI_BODY>"+
				"<reqxml><![CDATA[<Request service=\"OrderService\" lang=\"zh-CN\">"+
					"<Head>"+sf_head+"</Head>"+
					"<Body><Order orderid=\""+serv_order_no+"\""+
					" j_company=\"\""+
					" j_contact=\""+j_contact+"\""+
					" j_tel=\""+j_tel+"\""+
					" j_mobile=\""+j_tel+"\""+
					" j_province=\"\""+
					" j_city=\"\""+
					" j_county=\"\""+
					" j_address=\""+j_address+"\""+
					" d_contact=\""+d_contact+"\""+
					" d_tel=\""+d_tel+"\""+
					" d_mobile=\""+d_tel+"\""+
					" d_province=\"\""+
					" d_city=\"\""+
					" d_county=\"\""+
					" d_address=\""+d_address+"\""+
					" express_type=\"1\""+
					" pay_method=\"1\""+
					" custid=\""+month_account+"\""+
					" parcel_quantity=\"1\""+
					" is_docall=\"0\""+
					" sendstarttime=\"\""+
					" order_source=\"\""+
					" need_return_tracking_no='"+need_return_tracking_no+"'"+
					" remark=\""+remark+"\">"+
					" <Cargo  name='" +name+ "' count='1' unit='piece' weight='' amount='' currency='CNY' source_area='CN' />"+
					addedXmlStr+
					"</Order></Body></Request>]]></reqxml>"+
			"</UNI_BODY>"+
		"</UNI_BSS>";

		return xmlStr;
	}

	private UocMessage crawlerCallBack(String json_info) throws Exception {
		CrawlerActivemqSendPo sendPo = new CrawlerActivemqSendPo();
		sendPo.setJson_info(json_info);
		return crawlerActivemqSendServDu.sendActivemqMessage(sendPo, "crawlerCallBackFail");
	}

}
