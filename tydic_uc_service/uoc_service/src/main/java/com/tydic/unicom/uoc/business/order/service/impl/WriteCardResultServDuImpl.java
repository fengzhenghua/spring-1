package com.tydic.unicom.uoc.business.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.unicom.uoc.base.common.po.WriteCardResultInPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoWriteCardResultHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoWriteCardResultHisServ;
import com.tydic.unicom.uoc.business.order.service.interfaces.WriteCardResultServDu;
import com.tydic.unicom.uoc.business.order.service.vo.WriteCardResultInVo;
import com.tydic.unicom.uoc.service.common.interfaces.GetIdServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class WriteCardResultServDuImpl implements WriteCardResultServDu{

	Logger logger = Logger.getLogger(WriteCardResultServDuImpl.class);

	@Autowired
	private OperServDu operServDu;

	@Autowired
	private InfoWriteCardResultHisServ infoWriteCardResultHisServ;

	@Autowired
	private GetIdServDu getIdServDu;


	@Override
	public UocMessage queryWriteCardResult(WriteCardResultInVo vo) throws Exception{
		UocMessage message= new UocMessage();

		if(vo.getJsession_id()==null ||  "".equals(vo.getJsession_id())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jsession_id不能为空");
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
		if(vo.getPage_no()==null ||  "".equals(vo.getPage_no())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("page_no不能为空");
			return message;
		}
		if(vo.getPage_size()==null ||  "".equals(vo.getPage_size())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("page_size不能为空");
			return message;
		}

		int pageNo=Integer.parseInt(vo.getPage_no());
		int pageSize=Integer.parseInt(vo.getPage_size());

		UocMessage loginMessage=operServDu.isLogin(vo.getJsession_id());
		if(!"0000".equals(loginMessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("效验登录信息失败");
			return message;
		}

		@SuppressWarnings("unchecked")
		Map<String,Object> oper_info=(Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String province_code=oper_info.get("province_code").toString();
		String area_code=oper_info.get("area_code").toString();

		List<InfoWriteCardResultHisPo>  list = new ArrayList<InfoWriteCardResultHisPo>();
		List<InfoWriteCardResultHisPo>  list1 = new ArrayList<InfoWriteCardResultHisPo>();
		List<InfoWriteCardResultHisPo>  list2 = new ArrayList<InfoWriteCardResultHisPo>();
		Map<String,Object> map= new HashMap<String,Object>();
		WriteCardResultInPo po = new WriteCardResultInPo();
		WriteCardResultInPo po1 = new WriteCardResultInPo();
		BeanUtils.copyProperties(vo,po);
		po.setProvince_code(province_code);
		po.setArea_code(area_code);
		int totalNum=0;
		int totalNum1=0;
		int totalNum2=0;
		String sysDate3="";
		String sysDate4="";
		String partMonth1="";

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf2.parse(vo.getAccept_time_start());
		Date date2 = sdf2.parse(vo.getAccept_time_end());
		Date date5 = sdf3.parse(vo.getAccept_time_end());
		String acceptTimeStartTemp=sdf1.format(date1).toString().trim();
		String acceptTimeEndTemp=sdf1.format(date2).toString().trim();
		String acceptTimeStart=acceptTimeStartTemp.substring(0, 6);
		String acceptTmeEnd=acceptTimeEndTemp.substring(0, 6);


		//获取结束时间的上一个月时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date2);//把当前时间赋给日历
		calendar.add(calendar.MONTH, -1);  //设置为前1月
		dBefore = calendar.getTime();   //得到前1月的时间
		String defaultStartDate = sdf1.format(dBefore);    //格式化前1月的时间
		String defaultStartDateTemp=defaultStartDate.substring(0, 6);
		if(!acceptTmeEnd.equals(acceptTimeStart) && !defaultStartDateTemp.equals(acceptTimeStart)){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("服务订单列表查询时间段不能跨两个月");
			return message;
		}

		//如果查询时间是跨月的
		if(defaultStartDateTemp.equals(acceptTimeStart)){
			//获取上一个月的最后一天时间
			Date date3 = new Date();
			Calendar calendar3 = Calendar.getInstance(); //得到日历
			calendar3.setTime(date5);//把当前时间赋给日历
			calendar3.add(calendar3.MONTH, -1);  //设置为前1月
			calendar3.add(calendar3.SECOND, -1);  //设置为前1月
			calendar3.set(calendar3.DAY_OF_MONTH,calendar3.getActualMaximum(Calendar.DAY_OF_MONTH));
			date3 = calendar3.getTime();   //得到前1月的时间
			sysDate3 = sdf2.format(date3);    //格式化前1月的时间

			//获取这个月的第一天时间
			Date date4 = new Date();
			Calendar calendar4 = Calendar.getInstance(); //得到日历
			calendar4.setTime(date5);//把当前时间赋给日历
			calendar4.add(calendar4.MONTH, 0);  //设置为前1月
			calendar4.set(calendar4.DAY_OF_MONTH,1);
			date4 = calendar4.getTime();   //得到前1月的时间
			sysDate4 = sdf2.format(date4);    //格式化前1月的时间

			//获取上个月的part_month
			String month1=defaultStartDateTemp.substring(4, 6);
			partMonth1=Integer.toString(Integer.parseInt(month1));
		}
		String month=acceptTmeEnd.substring(4, 6);
		String partMonth=Integer.toString(Integer.parseInt(month));
		if(acceptTimeStart.equals(acceptTmeEnd)){
			po.setPart_month(partMonth);
			totalNum1=infoWriteCardResultHisServ.queryInfoWriteCardResultCount(po);
		}else if(defaultStartDateTemp.equals(acceptTimeStart)){//如果查询时间段跨月，查询上一个月的数据
			po.setPart_month(partMonth);
			po.setAccept_time_start(sysDate4);
			po.setAccept_time_end(vo.getAccept_time_end());
			totalNum1=infoWriteCardResultHisServ.queryInfoWriteCardResultCount(po);

			BeanUtils.copyProperties(po,po1);
			po1.setPart_month(partMonth1);
			po1.setAccept_time_start(vo.getAccept_time_start());
			po1.setAccept_time_end(sysDate3);
			totalNum2=infoWriteCardResultHisServ.queryInfoWriteCardResultCount(po1);
		}

		totalNum=totalNum1+totalNum2;
		if(totalNum1!=0 || totalNum2!=0){
			if(totalNum1>=pageNo*pageSize){
				int n=(pageNo-1)*pageSize;
				list1=infoWriteCardResultHisServ.queryInfoWriteCardResultHis(po, n, pageSize);
				if(list1!=null){
					list.addAll(list1);
				}
			}else if(totalNum1<pageNo*pageSize){
				if(totalNum1>(pageNo-1)*pageSize){
					int n=(pageNo-1)*pageSize;
					list1=infoWriteCardResultHisServ.queryInfoWriteCardResultHis(po, n, pageSize);
					int y=0;
					if(list1!=null){
						list.addAll(list1);
						y=pageSize-list1.size();
					}
					if(totalNum2!=0){
						int x=0;
						list2=infoWriteCardResultHisServ.queryInfoWriteCardResultHis(po1, x, y);
						if(list2!=null){
							list.addAll(list2);
						}
					}
				}else if(totalNum1<=(pageNo-1)*pageSize){
					if(totalNum2!=0){
						int n=(pageNo-1)*pageSize-totalNum1;
						list2=infoWriteCardResultHisServ.queryInfoWriteCardResultHis(po1, n, pageSize);
						if(list2!=null){
							list.addAll(list2);
						}
					}
				}
			}
		}

		map.put("page_no", pageNo);
		map.put("page_size", pageSize);
		map.put("total_num", totalNum);
		map.put("write_card_log_list", list);
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("写卡结果查询成功");
		message.addArg("write_card_log_list", map);
		return message;
	}

	@Override
	public UocMessage createWriteCardResult(WriteCardResultInVo vo) throws Exception{
		UocMessage message= new UocMessage();

		if(vo.getJsession_id()==null ||  "".equals(vo.getJsession_id())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("jsession_id不能为空");
			return message;
		}
		if(vo.getServ_order_no()==null || "".equals(vo.getServ_order_no())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("serv_order_no不能为空");
			return message;
		}
		if(vo.getSim_id()==null || "".equals(vo.getSim_id())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("sim_id不能为空");
			return message;
		}
		if(vo.getCust_name()==null || "".equals(vo.getCust_name())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("cust_name不能为空");
			return message;
		}
		if(vo.getAcc_nbr()==null || "".equals(vo.getAcc_nbr())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("acc_nbr不能为空");
			return message;
		}
		if(vo.getWrite_card_result()==null || "".equals(vo.getWrite_card_result())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("write_card_result不能为空");
			return message;
		}
		UocMessage loginMessage=operServDu.isLogin(vo.getJsession_id());
		if(!"0000".equals(loginMessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("效验登录信息失败");
			return message;
		}

		@SuppressWarnings("unchecked")
		Map<String,Object> oper_info=(Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String province_code=oper_info.get("province_code").toString();
		String area_code=oper_info.get("area_code").toString();
		String oper_no=oper_info.get("oper_no").toString();
		String depart_no=oper_info.get("depart_no").toString();

		InfoWriteCardResultHisPo po= new InfoWriteCardResultHisPo();
		po.setServ_order_no(vo.getServ_order_no());
		po.setSim_id(vo.getSim_id());
		po.setCust_name(vo.getCust_name());
		po.setAcc_nbr(vo.getAcc_nbr());
		po.setWrite_card_result(vo.getWrite_card_result());
		po.setFailed_desc(vo.getFailed_desc());
		po.setProvince_code(province_code);
		po.setArea_code(area_code);
		po.setAccept_oper_no(oper_no);
		po.setAccept_depart_no(depart_no);
		String create_time = DateUtil.getSysDateString(DateUtil.dateFormatYyyy_MM_dd_HH_mm_ss);
		po.setCreate_time(create_time);
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = sdf2.parse(create_time);
		String partMonthTemp=sdf1.format(date1).toString().trim();
		String month=partMonthTemp.substring(4, 6);
		String partMonth=Integer.toString(Integer.parseInt(month));
		po.setPart_month(partMonth);
		String id = getIdServDu.getId("createLogId", province_code, "*", "");
		po.setId(id);
		boolean flag=infoWriteCardResultHisServ.createInfoWriteCardResultHisPo(po);
		if(!flag){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("写卡结果记录失败");
			return message;
		}
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("写卡结果记录成功");
		return message;


	}


}
