package com.tydic.unicom.uoc.business.order.sale.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uoc.business.order.sale.interfaces.QuerySalesOrderListServDu;
import com.tydic.unicom.uoc.business.order.sale.vo.SaleOrderInVo;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.task.interfaces.QueryBeComSalesOrderListServDu;
import com.tydic.unicom.uoc.service.task.interfaces.QueryNotComSalesOrderListServDu;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class QuerySalesOrderListServDuImpl implements QuerySalesOrderListServDu{
	
	Logger logger = Logger.getLogger(QuerySalesOrderListServDuImpl.class);

	@Autowired
	private QueryNotComSalesOrderListServDu queryNotComSalesOrderListServDu;

	@Autowired
	private QueryBeComSalesOrderListServDu queryBeComSalesOrderListServDu;

	@Autowired
	private OperServDu operServDu;
	
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;


	private void getBeanDu(){
		if(operServDu == null){
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		if(queryNotComSalesOrderListServDu == null){
			queryNotComSalesOrderListServDu = (QueryNotComSalesOrderListServDu) ToolSpring.getBean("QueryNotComSalesOrderListServDu");
		}
		if(queryBeComSalesOrderListServDu == null){
			queryBeComSalesOrderListServDu = (QueryBeComSalesOrderListServDu) ToolSpring.getBean("QueryBeComSalesOrderListServDu");
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public UocMessage SalesOrderList(SaleOrderInVo vo)throws Exception{
		UocMessage message=new UocMessage();
		if(vo==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("参数为空");
			return message;
		}
		if(vo.getJsession_id()==null || "".equals(vo.getJsession_id())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jsession_id不能为空");
			return message;
		}
		if(vo.getFinish_flag()==null || "".equals(vo.getFinish_flag())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("finish_flag不能为空");
			return message;
		}
		if(vo.getAccept_time_start()==null || "".equals(vo.getAccept_time_start())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("accept_time_start不能为空");
			return message;
		}
		if(vo.getAccept_time_end()==null || "".equals(vo.getAccept_time_end())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("accept_time_end不能为空");
			return message;
		}

		UocMessage res =operServDu.isLogin(vo.getJsession_id());
		if(!"0000".equals(res.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("需要重新登录");
			return message;
		}
		Map<String,String> oper_info =(Map)res.getArgs().get("oper_info");
		String accept_depart_no =oper_info.get("depart_no");
		String area_code =oper_info.get("area_code");
		String province_code=oper_info.get("province_code");

		//如果传入的地域和渠道编码为空，取当前工号的地域和渠道编码
		if(vo.getArea_code()==null || "".equals(vo.getArea_code())){
			vo.setArea_code(area_code);
		}
		if(vo.getAccept_depart_no()==null || "".equals(vo.getAccept_depart_no())){
			vo.setAccept_depart_no(accept_depart_no);
		}
		if(vo.getProvince_code()==null || "".equals(vo.getProvince_code())){
			vo.setProvince_code(province_code);
		}
		//判断时间不能跨月2016-11-11 17:47:47 20161111174747
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");  
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date date1 = sdf2.parse(vo.getAccept_time_start());  
		Date date2 = sdf2.parse(vo.getAccept_time_end());  
		String acceptTimeStartTemp=sdf1.format(date1).toString().trim();
		String acceptTimeEndTemp=sdf1.format(date2).toString().trim();
		String acceptTimeStart=acceptTimeStartTemp.substring(0, 6);
		String acceptTmeEnd=acceptTimeEndTemp.substring(0, 6);
		if(!acceptTimeStart.equals(acceptTmeEnd)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("查询时间不能跨月");
			return message;
		}
		
		Date dNow = new Date();   //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(calendar.MONTH, -3);  //设置为前3月
		dBefore = calendar.getTime();   //得到前3月的时间
		String defaultStartDate = sdf1.format(dBefore);    //格式化前3月的时间
		String defaultStartDateTemp=defaultStartDate.substring(0, 6);
		if(acceptTimeStart.compareTo(defaultStartDateTemp)<0){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("只能查3个月内的数据");
			return message;
		}
		

		String month=acceptTimeStartTemp.substring(4, 6);
		String partMonth=Integer.toString(Integer.parseInt(month));
		vo.setPart_month(partMonth);

		//只查未竣工数据
		if("1".equals(vo.getFinish_flag())){
			message=queryBeComSalesOrderListServDu.queryBeComSalesOrderList(vo);
		}else if("0".equals(vo.getFinish_flag())){//查询竣工销售订单列表
			message=queryNotComSalesOrderListServDu.queryNotComSalesOrderList(vo);
		}
		return message;
	}
	
	
	/**
	 * 服务订单列表查询UOC0035入参组装
	 * @param json_in
	 * @return
	 * @throws Exception
	 */
	public UocMessage servOrderListForAbilityPlatform(String  json_in) throws Exception{

		if(jsonToBeanServDu == null){
			logger.info("====================jsonToBeanServDu is null============================"+jsonToBeanServDu);
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		Map<String, Object> map =jsonToBeanServDu.jsonToMap(json_in);
		SaleOrderInVo vo =new SaleOrderInVo();

		String jsession_id =(String) map.get("jsession_id");
		String serv_order_no =(String) map.get("serv_order_no");
		String cust_name =(String) map.get("cust_name");
		String cert_no =(String) map.get("cert_no");
		String accept_oper_no =(String) map.get("accept_oper_no");
		String province_code =(String) map.get("province_code");
		String area_code =(String) map.get("area_code");
		String accept_depart_no =(String) map.get("accept_depart_no");
		String finish_flag =(String) map.get("finish_flag");		
		String order_state =(String) map.get("order_state");
		String acc_nbr =(String) map.get("acc_nbr");
		String prod_code =(String) map.get("prod_code");
		String oper_code =(String) map.get("oper_code");
		String tache_code =(String) map.get("tache_code");
		String accept_no =(String) map.get("accept_no");
		String pay_type =(String) map.get("pay_type");
		String accept_system =(String) map.get("accept_system");
		String accept_time_start =(String) map.get("accept_time_start");		
		String accept_time_end =(String) map.get("accept_time_end");
		String page_no =(String) map.get("page_no");
		String page_size =(String) map.get("page_size");
		String sort_type=map.get("sort_type").toString();
		

		vo.setJsession_id(jsession_id);
		vo.setServ_order_no(serv_order_no);
		vo.setCust_name(cust_name);
		vo.setCert_no(cert_no);
		vo.setAccept_oper_no(accept_oper_no);
		vo.setProvince_code(province_code);
		vo.setArea_code(area_code);
		vo.setAccept_depart_no(accept_depart_no);
		vo.setFinish_flag(finish_flag);
		vo.setOrder_state(order_state);
		vo.setAcc_nbr(acc_nbr);
		vo.setProd_code(prod_code);
		vo.setOper_code(oper_code);
		vo.setTache_code(tache_code);
		vo.setAccept_no(accept_no);
		vo.setPay_type(pay_type);
		vo.setAccept_system(accept_system);
		vo.setAccept_time_start(accept_time_start);
		vo.setAccept_time_end(accept_time_end);
		vo.setPage_no(page_no);
		vo.setPage_size(page_size);
		vo.setSort_type(sort_type);
		UocMessage message =ServOrderList(vo);
		return message;
	}
	
	
	
	@Override
	public UocMessage ServOrderList(SaleOrderInVo vo)throws Exception{
		getBeanDu();
		UocMessage message=new UocMessage();
		if(vo==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("参数为空");
			return message;
		}
		if(vo.getJsession_id()==null || "".equals(vo.getJsession_id())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jsession_id不能为空");
			return message;
		}
		if(vo.getFinish_flag()==null || "".equals(vo.getFinish_flag())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("finish_flag不能为空");
			return message;
		}
		if(vo.getAccept_time_start()==null || "".equals(vo.getAccept_time_start())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("accept_time_start不能为空");
			return message;
		}
		if(vo.getAccept_time_end()==null || "".equals(vo.getAccept_time_end())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("accept_time_end不能为空");
			return message;
		}

		UocMessage res =operServDu.isLogin(vo.getJsession_id());
		if(!"0000".equals(res.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("需要重新登录");
			return message;
		}
		Map<String,String> oper_info =(Map)res.getArgs().get("oper_info");
		String area_code =oper_info.get("area_code");
		String province_code=oper_info.get("province_code");

		//如果传入的地域和渠道编码为空，取当前工号的地域和渠道编码
		if(vo.getArea_code()==null || "".equals(vo.getArea_code())){
			vo.setArea_code(area_code);
		}
		if(vo.getProvince_code()==null || "".equals(vo.getProvince_code())){
			vo.setProvince_code(province_code);
		}
		//只查竣工数据
		if("1".equals(vo.getFinish_flag())){
			message=queryBeComSalesOrderListServDu.queryBeComServOrderList(vo);
		}else if("0".equals(vo.getFinish_flag())){////只未查竣工数据
			message=queryNotComSalesOrderListServDu.queryNotComServOrderList(vo);
		}
		return message;
	}

}
