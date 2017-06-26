package com.tydic.unicom.uoc.business.common.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.unicom.service.cache.service.redis.po.CodeList;
import com.tydic.unicom.uoc.base.uoccode.po.CodeOperSendPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeOperSendServ;
import com.tydic.unicom.uoc.business.common.interfaces.ExpressServDu;
import com.tydic.unicom.uoc.business.common.vo.ExpressVo;
import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.service.code.interfaces.CodeListServDu;
import com.tydic.unicom.uoc.service.code.interfaces.CodeRegionServDu;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoDeliverOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoDeliverOrderHisVo;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderHisVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoDeliverOrderServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoSaleOrderDistributionServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderExtServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoDeliverOrderVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocException;
import com.tydic.unicom.webUtil.UocMessage;

public class ExpressServDuImpl implements ExpressServDu {

	Logger logger = Logger.getLogger(ExpressServDuImpl.class);

	@Autowired
	private OperServDu operServDu;
	@Autowired
	private InfoDeliverOrderServDu infoDeliverOrderServDu;
	@Autowired
	private InfoSaleOrderDistributionServDu infoSaleOrderDistributionServDu;
	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;
	@Autowired
	private InfoServiceOrderExtServDu infoServiceOrderExtServDu;
	@Autowired
	private CodeListServDu codeListServDu;
	@Autowired
	private CodeRegionServDu codeRegionServDu;
	@Autowired
	private CodeOperSendServ codeOperSendServ;
	@Autowired
	private InfoServiceOrderHisServDu infoServiceOrderHisServDu;
	@Autowired
	private InfoDeliverOrderHisServDu infoDeliverOrderHisServDu;

	private void getBeanDu(){
		if(operServDu == null){
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		if(infoDeliverOrderServDu == null){
			infoDeliverOrderServDu = (InfoDeliverOrderServDu) ToolSpring.getBean("InfoDeliverOrderServDu");
		}
		if(infoSaleOrderDistributionServDu == null){
			infoSaleOrderDistributionServDu = (InfoSaleOrderDistributionServDu) ToolSpring.getBean("InfoSaleOrderDistributionServDu");
		}
		if(infoServiceOrderServDu == null){
			infoServiceOrderServDu = (InfoServiceOrderServDu) ToolSpring.getBean("InfoServiceOrderServDu");
		}
		if(codeListServDu == null){
			codeListServDu = (CodeListServDu) ToolSpring.getBean("CodeListServDu");
		}
		if(codeRegionServDu == null){
			codeRegionServDu = (CodeRegionServDu) ToolSpring.getBean("CodeRegionServDu");
		}
		if(codeOperSendServ == null){
			codeOperSendServ = (CodeOperSendServ) ToolSpring.getBean("CodeOperSendServ");
		}
	}

	/**
	 * ⦁	获取顺丰纸质单信息 ⦁	接口编号UOC0039
	 */
	@Override
	public UocMessage getPaperInfoForSF(ParaVo vo) throws Exception{
		logger.info("---rest---------getPaperInfoForSF---------");
		getBeanDu();
		UocMessage message =new UocMessage();
		String jsession_id =vo.getJsession_id();
		String serv_order_no =vo.getServ_order_no();

		if(jsession_id ==null || "".equals(jsession_id)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jession_id不能为空");
			return message;
		}
		if(serv_order_no ==null || "".equals(serv_order_no)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("serv_order_no不能为空");
			return message;
		}

		/*
		 * 校验jession_id
		 */
		UocMessage res =operServDu.isLogin(jsession_id);
		if(!"0000".equals(res.getRespCode())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent(res.getContent());
			return message;
		}
		Map<String,String> oper_info =(Map)res.getArgs().get("oper_info");
		String oper_no =oper_info.get("oper_no");

		try{
			InfoServiceOrderVo infoServiceOrderVo =new InfoServiceOrderVo();
			infoServiceOrderVo.setServ_order_no(serv_order_no);
			infoServiceOrderVo =infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(infoServiceOrderVo);

			//如果是竣工订单，则需查询历史订单
			InfoServiceOrderHisVo infoServiceOrderHisVo =new InfoServiceOrderHisVo();
			boolean is_his_order_flag =false;
			if(infoServiceOrderVo == null){
				//查询历史订单
				infoServiceOrderHisVo.setServ_order_no(serv_order_no);
				List<InfoServiceOrderHisVo> infoServiceOrderHisVos =infoServiceOrderHisServDu.queryInfoServiceOrderHisByOrderNo(infoServiceOrderHisVo);

				if(infoServiceOrderHisVos ==null || infoServiceOrderHisVos.size()<=0){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("无关联服务单号");
					return message;
				}
				infoServiceOrderHisVo =infoServiceOrderHisVos.get(0);
				is_his_order_flag =true;
			}

			String logistics_no ="";
			String send_target_addr ="";
			String goods_name ="";
			String note ="";
			String send_name ="";
			String send_addr ="";
			String real_phone ="";
			String contact_name ="";
			String contact_tel ="";
			String contact_address ="";
			String sale_order_no ="";
			String cod_charge ="";
			String insure_charge = "";
			String need_return_tracking_no="";
			String return_tracking_no="";
			String send_origin_addr="";
			String cod_bank_no="";
			if (!is_his_order_flag) {
				sale_order_no = infoServiceOrderVo.getSale_order_no();

				InfoDeliverOrderVo infoDeliverOrderVo = new InfoDeliverOrderVo();
				infoDeliverOrderVo.setSale_order_no(sale_order_no);
				List<InfoDeliverOrderVo> infoDeliverOrders = infoDeliverOrderServDu.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderVo);

				if (infoDeliverOrders != null && infoDeliverOrders.size() > 0) {
					logistics_no = infoDeliverOrders.get(0).getLogistics_no();
					send_target_addr = infoDeliverOrders.get(0).getSend_target_addr();
					goods_name = infoDeliverOrders.get(0).getGoods_name();
					note = infoDeliverOrders.get(0).getNote();
					send_name = infoDeliverOrders.get(0).getSend_name();
					send_addr = infoDeliverOrders.get(0).getSend_addr();
					real_phone = infoDeliverOrders.get(0).getReal_phone();
					contact_name = infoDeliverOrders.get(0).getContact_name();
					contact_tel = infoDeliverOrders.get(0).getContact_tel();
					contact_address = (StringUtils.isEmpty(infoDeliverOrders.get(0).getPost_province_name()) ? "" : infoDeliverOrders
							.get(0).getPost_province_name())
							+ (StringUtils.isEmpty(infoDeliverOrders.get(0).getPost_city_name()) ? "" : infoDeliverOrders.get(0)
									.getPost_city_name())
							+ (StringUtils.isEmpty(infoDeliverOrders.get(0).getPost_area_name()) ? "" : infoDeliverOrders.get(0)
									.getPost_area_name()) + infoDeliverOrders.get(0).getAddress();
					cod_charge = infoDeliverOrders.get(0).getCod_charge();
					insure_charge = infoDeliverOrders.get(0).getInsure_charge();
					return_tracking_no=infoDeliverOrders.get(0).getReturn_tracking_no();
					send_origin_addr=infoDeliverOrders.get(0).getSend_origin_addr();
					cod_bank_no=infoDeliverOrders.get(0).getCod_bank_no();
				} else {
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("无关联物流信息");
					return message;
				}
			} else {
				sale_order_no = infoServiceOrderHisVo.getSale_order_no();

				InfoDeliverOrderHisVo infoDeliverOrderHisVo = new InfoDeliverOrderHisVo();
				infoDeliverOrderHisVo.setSale_order_no(sale_order_no);
				List<InfoDeliverOrderHisVo> infoDeliverOrders = infoDeliverOrderHisServDu
						.queryInfoDeliverOrderHisBySaleOrderNo(infoDeliverOrderHisVo);

				if (infoDeliverOrders != null && infoDeliverOrders.size() > 0) {
					logistics_no = infoDeliverOrders.get(0).getLogistics_no();
					send_target_addr = infoDeliverOrders.get(0).getSend_target_addr();
					goods_name = infoDeliverOrders.get(0).getGoods_name();
					note = infoDeliverOrders.get(0).getNote();
					send_name = infoDeliverOrders.get(0).getSend_name();
					send_addr = infoDeliverOrders.get(0).getSend_addr();
					real_phone = infoDeliverOrders.get(0).getReal_phone();
					contact_name = infoDeliverOrders.get(0).getContact_name();
					contact_tel = infoDeliverOrders.get(0).getContact_tel();
					contact_address = (StringUtils.isEmpty(infoDeliverOrders.get(0).getPost_province_name()) ? "" : infoDeliverOrders
							.get(0).getPost_province_name())
							+ (StringUtils.isEmpty(infoDeliverOrders.get(0).getPost_city_name()) ? "" : infoDeliverOrders.get(0)
									.getPost_city_name())
							+ (StringUtils.isEmpty(infoDeliverOrders.get(0).getPost_area_name()) ? "" : infoDeliverOrders.get(0)
									.getPost_area_name()) + infoDeliverOrders.get(0).getAddress();
					cod_charge = infoDeliverOrders.get(0).getCod_charge();
					insure_charge = infoDeliverOrders.get(0).getInsure_charge();
					return_tracking_no=infoDeliverOrders.get(0).getReturn_tracking_no();
					send_origin_addr=infoDeliverOrders.get(0).getSend_origin_addr();
					cod_bank_no=infoDeliverOrders.get(0).getCod_bank_no();
				} else {
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("无关联物流信息");
					return message;
				}

			}
			logger.info("----contact_address---------" + contact_address + "--------------");

			CodeList codeList =new CodeList();
			codeList.setType_code("month_account");
			List<CodeList> monthList = codeListServDu.queryCodeListByTypeCodeFromRedis(codeList);
			String month_account ="";
			if (monthList != null && monthList.size() > 0) {
				month_account = monthList.get(0).getCode_id();
			}
//			logger.info("----month_account---------"+month_account+"--------------");
//			String cod_account = "";
//			codeList.setType_code("cod_account");
//			List<CodeList> accountList = codeListServDu.queryCodeListByTypeCodeFromRedis(codeList);
//			if (accountList != null && accountList.size() > 0) {
//				cod_account = accountList.get(0).getCode_id();
//			}

			if(StringUtils.isNotEmpty(return_tracking_no)){
				need_return_tracking_no="1";
			}
			
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("获取顺丰纸质信息成功");
			message.addArg("send_target_addr",send_target_addr);
			message.addArg("logistics_no", logistics_no);
			message.addArg("goods_name", goods_name);
			message.addArg("note", note);
			message.addArg("send_name", send_name);
			message.addArg("send_addr", send_addr);
			message.addArg("real_phone", real_phone);
			message.addArg("contact_name", contact_name);
			message.addArg("contact_tel", contact_tel);
			message.addArg("contact_address", contact_address);
			message.addArg("month_account", month_account);
			message.addArg("cod_account", cod_bank_no);
			message.addArg("cod_charge", cod_charge);
			message.addArg("insure_charge", insure_charge);
			message.addArg("return_tracking_no", return_tracking_no);
			message.addArg("send_origin_addr", send_origin_addr);
			message.addArg("need_return_tracking_no", need_return_tracking_no);
			message.addArg("order_no", sale_order_no);
			return message;
		}catch(Exception e){
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SYSTEM_EXCEPTION);
			message.setContent("获取顺丰纸质信息异常");
			return message;
		}

	}
	/**
	 * 65	保存默认寄件信息  UOC0040
	 * @return
	 */
	@Override
	public UocMessage saveSendInfo(ExpressVo vo) throws Exception{
		logger.info("---rest---------saveSendInfo---------");
		getBeanDu();
		UocMessage message =new UocMessage();
		String jsession_id =vo.getJsession_id();
		String send_name =vo.getSend_name();
		String real_phone =vo.getReal_phone();
		String send_addr =vo.getSend_addr();

		if(jsession_id ==null || "".equals(jsession_id)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jession_id不能为空");
			return message;
		}
		if(send_name ==null || "".equals(send_name)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("send_name不能为空");
			return message;
		}
		if(real_phone ==null || "".equals(real_phone)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("real_phone不能为空");
			return message;
		}
		if(send_addr ==null || "".equals(send_addr)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("send_addr不能为空");
			return message;
		}

		/*
		 * 校验jession_id
		 */
		UocMessage res =operServDu.isLogin(jsession_id);
		if(!"0000".equals(res.getRespCode())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent(res.getContent());
			return message;
		}
		Map<String,String> oper_info =(Map)res.getArgs().get("oper_info");
		String oper_no =oper_info.get("oper_no");

		CodeOperSendPo po =new CodeOperSendPo();
		po.setOper_no(oper_no);
		po.setReal_phone(real_phone);
		po.setSend_addr(send_addr);
		po.setSend_name(send_name);
		try {
			boolean result = codeOperSendServ.saveDefaultSendInfo(po);
			if(!result){
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("保存默认寄件信息失败");
				return message;
			}
		} catch (Exception e) {
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("保存默认寄件信息错误");
			throw new UocException(message);
		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("保存默认寄件信息成功");
		return message;
	}
	/**
	 * 	获取默认寄件信息 接口编号UOC0043
	 * @param vo
	 * @return
	 */
	@Override
	public UocMessage getSendInfo(ExpressVo vo) throws Exception{
		logger.info("---rest---------getSendInfo---------");
		getBeanDu();
		UocMessage message =new UocMessage();
		String jsession_id =vo.getJsession_id();

		if(jsession_id ==null || "".equals(jsession_id)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jession_id不能为空");
			return message;
		}
		/*
		 * 校验jession_id
		 */
		UocMessage res =operServDu.isLogin(jsession_id);
		if(!"0000".equals(res.getRespCode())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent(res.getContent());
			return message;
		}
		Map<String,String> oper_info =(Map)res.getArgs().get("oper_info");
		String oper_no =oper_info.get("oper_no");


		CodeOperSendPo po =new CodeOperSendPo();
		po.setOper_no(oper_no);

		try {
			po =codeOperSendServ.getdefaultSendInfo(po);
			if(po ==null){
				po =new CodeOperSendPo();
				po.setOper_no("*");
				po =codeOperSendServ.getdefaultSendInfo(po);
				if(po ==null){
					message.setRespCode(RespCodeContents.SERVICE_FAIL);
					message.setContent("无关联默认寄件信息");
					return message;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("获取默认寄件信息错误");
			return message;
		}
		message.setRespCode(RespCodeContents.SUCCESS);
		message.addArg("send_name", po.getSend_name());
		message.addArg("send_addr", po.getSend_addr());
		message.addArg("real_phone", po.getReal_phone());
		message.setContent("获取默认寄件信息成功");
		return message;
	}

}
