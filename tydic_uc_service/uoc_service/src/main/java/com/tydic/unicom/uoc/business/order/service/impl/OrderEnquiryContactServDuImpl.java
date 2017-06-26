package com.tydic.unicom.uoc.business.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uoc.business.common.vo.ParaVo;
import com.tydic.unicom.uoc.business.order.sale.interfaces.QuerySalesOrderListServDu;
import com.tydic.unicom.uoc.business.order.sale.vo.SaleOrderInVo;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderDetailServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.OrderEnquiryContactServDu;
import com.tydic.unicom.uoc.business.order.service.interfaces.ProcModServDu;
import com.tydic.unicom.uoc.business.order.service.vo.InfoServOrderListVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcInfoVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcInstTaskDealRecordVo;
import com.tydic.unicom.uoc.business.order.service.vo.ProcLogVo;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.order.his.interfaces.InfoServiceOrderHisServDu;
import com.tydic.unicom.uoc.service.order.his.vo.InfoServiceOrderHisVo;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class OrderEnquiryContactServDuImpl implements OrderEnquiryContactServDu{

	Logger logger = Logger.getLogger(OrderEnquiryContactServDuImpl.class);

	@Autowired
	private OrderDetailServDu orderDetailServDu;

	@Autowired
	private ProcModServDu procModServDu;

	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;

	@Autowired
	private InfoServiceOrderServDu infoServiceOrderServDu;

	@Autowired
	private InfoServiceOrderHisServDu infoServiceOrderHisServDu;

	@Autowired
	private QuerySalesOrderListServDu querySalesOrderListServDu;


	private void getBeanDu(){
		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		if(orderDetailServDu == null){
			logger.info("====================orderDetailServDu is null============================"+orderDetailServDu);
			orderDetailServDu = (OrderDetailServDu) ToolSpring.getBean("OrderDetailServDu");
		}
		if(procModServDu == null){
			logger.info("====================procModServDu is null============================"+procModServDu);
			procModServDu = (ProcModServDu) ToolSpring.getBean("ProcModServDu");
		}
		if(infoServiceOrderServDu == null){
			logger.info("====================infoServiceOrderServDu is null============================"+infoServiceOrderServDu);
			infoServiceOrderServDu = (InfoServiceOrderServDu) ToolSpring.getBean("InfoServiceOrderServDu");
		}
		if(infoServiceOrderHisServDu == null){
			logger.info("====================infoServiceOrderHisServDu is null============================"+infoServiceOrderHisServDu);
			infoServiceOrderHisServDu = (InfoServiceOrderHisServDu) ToolSpring.getBean("InfoServiceOrderHisServDu");
		}
		if(querySalesOrderListServDu == null){
			logger.info("====================querySalesOrderListServDu is null============================"+querySalesOrderListServDu);
			querySalesOrderListServDu = (QuerySalesOrderListServDu) ToolSpring.getBean("QuerySalesOrderListServDu");
		}
	}
	/**
	 * 订单详情查询 UOC0010入参组装
	 * @param json_in
	 * @return
	 * @throws Exception
	 */
	public UocMessage queryOrderDetailForAbilityPlatform(String  json_in) throws Exception{

		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);

		String jsession_id =(String) map.get("jsession_id");
		String order_no =(String) map.get("order_no");
		//String query_type =(String) map.get("query_type");
		UocMessage message =queryOrderEnquiryContact(jsession_id,order_no);
		return message;
	}


	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public UocMessage queryOrderEnquiryContact(String jsession_id,String  serv_order_no) throws Exception{
		UocMessage message = new UocMessage();
		getBeanDu();
		if(jsession_id == null || jsession_id.equals("") ){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("失败:jsession_id为必传参数");
			return message;
		}
		if(serv_order_no == null || serv_order_no.equals("") ){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("失败:serv_order_no为必传参数");
			return message;
		}

		String sale_order_no = "";
		//先查询订单创建时间
		InfoServiceOrderVo infoServiceOrderVo=null;
		List<InfoServiceOrderHisVo> infoServiceOrderHisVoList=null;
		InfoServiceOrderVo vo = new InfoServiceOrderVo();
		vo.setServ_order_no(serv_order_no);
		infoServiceOrderVo=infoServiceOrderServDu.getInfoServiceOrderByServOrderNo(vo);

		InfoServiceOrderHisVo infoServiceOrderHisVo = new InfoServiceOrderHisVo();
		infoServiceOrderHisVo.setServ_order_no(serv_order_no);
		infoServiceOrderHisVoList=infoServiceOrderHisServDu.queryInfoServiceOrderHisByOrderNo(infoServiceOrderHisVo);

		String finish_flag="";
		String accept_time_star="";
		String accept_time_end="";
		String createTime="";
		if(infoServiceOrderVo!=null){
			finish_flag="0";
			sale_order_no = infoServiceOrderVo.getSale_order_no();
			createTime=infoServiceOrderVo.getCreate_time();
		}else if(infoServiceOrderHisVoList!=null){
			finish_flag="1";
			sale_order_no = infoServiceOrderHisVoList.get(0).getSale_order_no();
			createTime=infoServiceOrderHisVoList.get(0).getCreate_time();
		}else{
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("无该服务订单数据");
			return message;
		}

		String page_no="1";
		String page_size="10";

		if(!"".equals(createTime)){
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
			Date date5 = sdf3.parse(createTime);
			Date date4 = new Date();
			Calendar calendar4 = Calendar.getInstance();
			calendar4.setTime(date5);
			calendar4.add(calendar4.MONTH, 0);
			calendar4.set(calendar4.DAY_OF_MONTH,1);
			date4 = calendar4.getTime();
			accept_time_star = sdf2.format(date4);


			Date date3 = new Date();
			Calendar calendar3 = Calendar.getInstance();
			calendar3.setTime(date5);
			calendar3.add(calendar3.MONTH, 0);
			calendar3.add(calendar3.SECOND, -1);
			calendar3.set(calendar3.DAY_OF_MONTH,calendar3.getActualMaximum(Calendar.DAY_OF_MONTH));
			date3 = calendar3.getTime();
			accept_time_end = sdf2.format(date3);
		}

		SaleOrderInVo saleOrderInVo = new SaleOrderInVo();
		saleOrderInVo.setJsession_id(jsession_id);
		saleOrderInVo.setFinish_flag(finish_flag);
		saleOrderInVo.setAccept_time_start(accept_time_star);
		saleOrderInVo.setAccept_time_end(accept_time_end);
		saleOrderInVo.setPage_no(page_no);
		saleOrderInVo.setPage_size(page_size);
		saleOrderInVo.setServ_order_no(serv_order_no);

		UocMessage uocMessage=querySalesOrderListServDu.ServOrderList(saleOrderInVo);
		if(!"0000".equals(uocMessage.getRespCode())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent(uocMessage.getContent().toString());
			return message;
		}

		String servOrderNo="";
		String order_state="";
		String cert_no = "";
		String cust_name="";
		String cust_phone = "";
		String cust_address = "";
		String accept_oper_no="";
		String province_code="";
		String area_code="";
		String accept_depart_no="";
		String acc_nbr="";
		String prod_code="";
		String oper_code="";
		String tache_code="";
		String accept_no="";
		String pay_type="";
		String accept_system="";
		String create_time="";
		String pay_fee="";
		String logistics_no="";
		String deal_oper_no = "";
		String deal_depart_no = "";
		String deal_date = "";
		String deal_time = "";
		String audit_state = "";
		String sim_id="";
		Map<String,Object> map= (Map<String, Object>) uocMessage.getArgs().get("info_serv_order_list");
		List<ProcInfoVo> proclist = new ArrayList<ProcInfoVo>();
		if(map!=null){
			List<InfoServOrderListVo> list= new ArrayList<InfoServOrderListVo>();
			list= (List<InfoServOrderListVo>) map.get("info_serv_order");
			if (list.size() != 0) {
				servOrderNo=list.get(0).getServ_order_no();
				order_state=list.get(0).getOrder_state();
				cert_no=list.get(0).getCert_no();
				cust_name=list.get(0).getCust_name();
				cust_phone = list.get(0).getCust_phone();
				cust_address = list.get(0).getCust_address();
				accept_oper_no=list.get(0).getAccept_oper_no();
				province_code=list.get(0).getProvince_code();
				area_code=list.get(0).getArea_code();
				accept_depart_no=list.get(0).getAccept_depart_no();
				acc_nbr=list.get(0).getAcc_nbr();
				prod_code=list.get(0).getProd_code();
				oper_code=list.get(0).getOper_code();
				tache_code=list.get(0).getTache_code();
				accept_no=list.get(0).getAccept_no();
				pay_type=list.get(0).getPay_type();
				accept_system=list.get(0).getAccept_system();
				create_time=list.get(0).getAccept_time();
				pay_fee=list.get(0).getPay_fee();
				logistics_no=list.get(0).getLogistics_no();
				finish_flag=list.get(0).getFinish_flag();
				sim_id=list.get(0).getSim_id();

				//查询流程图信息
				String query_type="101";
				ParaVo paraVo =new ParaVo();
				paraVo.setJsession_id(jsession_id);
				paraVo.setOrder_no(serv_order_no);
				paraVo.setQuery_type(query_type);
				paraVo.setOrder_type(query_type);
				paraVo.setFinish_flag(finish_flag);
				List<ProcLogVo> proc_log_list = new ArrayList<ProcLogVo>();
				ProcInfoVo procInfoVo = new ProcInfoVo();

				UocMessage proMessage=procModServDu.getProcViewInfo(paraVo);
				if("0000".equals(proMessage.getRespCode())){
					proc_log_list=(List<ProcLogVo>) proMessage.getArgs().get("proc_log_list");
					if(proc_log_list.size()!=0){
						for(ProcLogVo procLogVo:proc_log_list){
							procInfoVo.setTache_name(procLogVo.getTache_name());
							procInfoVo.setOper_no(procLogVo.getOper_no());
							procInfoVo.setCreate_time(procLogVo.getCreate_time());
							procInfoVo.setFinish_time(procLogVo.getDeal_time());
							proclist.add(procInfoVo);
						}
					}
				}

				// 判断业务类型oper_code=open01,open02，如果经过S00021环节，则显示审核通过，如果经过S00022环节则显示审核未通过，否则显示未审核
				if (("open01").equals(oper_code) || ("open02").equals(oper_code)) {
					ProcInstTaskDealRecordVo waitRecord = procModServDu.queryLastTaskDealRecord(serv_order_no, "S00020", query_type, sale_order_no, finish_flag, oper_code);
					ProcInstTaskDealRecordVo passRecord = procModServDu.queryLastTaskDealRecord(serv_order_no, "S00021", query_type, sale_order_no, finish_flag, oper_code);
					ProcInstTaskDealRecordVo referRecord = procModServDu.queryLastTaskDealRecord(serv_order_no, "S00022", query_type, sale_order_no, finish_flag, oper_code);
					if (waitRecord != null) {
						deal_oper_no = waitRecord.getDeal_oper_no();
						deal_depart_no = waitRecord.getDeal_depart_no();
					}

					if (passRecord != null) {
						if (waitRecord == null) {
							deal_oper_no = passRecord.getDeal_oper_no();
							deal_depart_no = passRecord.getDeal_depart_no();
						}
						String dateStr = passRecord.getDeal_time();
						deal_date = dateStr.substring(0, dateStr.indexOf(" "));
						deal_time = dateStr.substring(dateStr.indexOf(" ") + 1, dateStr.length());
						audit_state = "审核通过";
					} else if (referRecord != null) {
						if (waitRecord == null) {
							deal_oper_no = referRecord.getDeal_oper_no();
							deal_depart_no = referRecord.getDeal_depart_no();
						}
						String dateStr = referRecord.getDeal_time();
						deal_date = dateStr.substring(0, dateStr.indexOf(" "));
						deal_time = dateStr.substring(dateStr.indexOf(" ") + 1, dateStr.length());
						audit_state = "审核未通过";
					} else {
						audit_state = "未审核";
					}

				}

			}else{
				message.setRespCode(RespCodeContents.SERVICE_FAIL);
				message.setContent("无该订单数据");
				return message;
			}
		}else{
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("无该订单数据");
			return message;
		}

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("订单查询成功");
		message.addArg("serv_order_no", servOrderNo);
		message.addArg("order_state", order_state);
		message.addArg("cert_no", cert_no);
		message.addArg("cust_name", cust_name);
		message.addArg("cust_phone", cust_phone);
		message.addArg("cust_address", cust_address);
		message.addArg("accept_oper_no", accept_oper_no);
		message.addArg("province_code", province_code);
		message.addArg("area_code", area_code);
		message.addArg("accept_depart_no", accept_depart_no);
		message.addArg("acc_nbr", acc_nbr);
		message.addArg("prod_code", prod_code);
		message.addArg("oper_code", oper_code);
		message.addArg("tache_code", tache_code);
		message.addArg("accept_no", accept_no);
		message.addArg("pay_type", pay_type);
		message.addArg("accept_system", accept_system);
		message.addArg("create_time", create_time);
		message.addArg("pay_fee", pay_fee);
		message.addArg("logistics_no", logistics_no);
		message.addArg("finish_flag", finish_flag);
		message.addArg("proc_info", proclist);
		message.addArg("deal_oper_no", deal_oper_no);
		message.addArg("deal_depart_no", deal_depart_no);
		message.addArg("deal_date", deal_date);
		message.addArg("deal_time", deal_time);
		message.addArg("audit_state", audit_state);
		message.addArg("sim_id", sim_id);
		return message;

	}

}
