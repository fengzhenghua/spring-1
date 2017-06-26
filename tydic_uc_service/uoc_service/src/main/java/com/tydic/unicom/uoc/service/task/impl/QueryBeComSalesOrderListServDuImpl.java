package com.tydic.unicom.uoc.service.task.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.unicom.uoc.base.common.po.AccNbrPo;
import com.tydic.unicom.uoc.base.common.po.SaleOrderInPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoDeliverOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoPayOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderOfrMapHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderPersionHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoSaleOrderServMapHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderProdMapHisPo;
import com.tydic.unicom.uoc.base.uochis.po.InfoServiceOrderSimCardHisPo;
import com.tydic.unicom.uoc.base.uochis.po.ProcInstTaskInstHisPo;
import com.tydic.unicom.uoc.base.uochis.po.SalesOrderListHisPo;
import com.tydic.unicom.uoc.base.uochis.po.ServOrderListHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoDeliverOrderHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoPayOrderHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderOfrMapHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderPersionHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoSaleOrderServMapHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderProdMapHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoServiceOrderSimCardHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ProcInstTaskInstHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.SalesOrderListHisServ;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.ServOrderListHisServ;
import com.tydic.unicom.uoc.business.order.sale.vo.SaleOrderInVo;
import com.tydic.unicom.uoc.business.order.service.vo.InfoServOrderListVo;
import com.tydic.unicom.uoc.service.task.interfaces.QueryBeComSalesOrderListServDu;
import com.tydic.unicom.uoc.service.task.vo.InfoSalesOrderListVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("QueryBeComSalesOrderListServDu")
public class QueryBeComSalesOrderListServDuImpl implements QueryBeComSalesOrderListServDu{


	@Autowired
	private SalesOrderListHisServ salesOrderListHisServ;

	@Autowired
	private InfoSaleOrderOfrMapHisServ infoSaleOrderOfrMapHisServ;

	@Autowired
	private InfoSaleOrderServMapHisServ infoSaleOrderServMapHisServ;

	@Autowired
	private ProcInstTaskInstHisServ procInstTaskInstHisServ;

	@Autowired
	private InfoSaleOrderHisServ infoSaleOrderHisServ;

	@Autowired
	private InfoSaleOrderPersionHisServ infoSaleOrderPersionHisServ;

	@Autowired
	private InfoServiceOrderProdMapHisServ infoServiceOrderProdMapHisServ;

	@Autowired
	private InfoPayOrderHisServ infoPayOrderHisServ;

	@Autowired
	private ServOrderListHisServ servOrderListHisServ;

	@Autowired
	private InfoDeliverOrderHisServ infoDeliverOrderHisServ;
	
	@Autowired
	private InfoServiceOrderSimCardHisServ infoServiceOrderSimCardHisServ;
	



	@Override
	public UocMessage queryBeComSalesOrderList(SaleOrderInVo vo)throws Exception{
		UocMessage message=new UocMessage();

		List<InfoSalesOrderListVo> list= new ArrayList<InfoSalesOrderListVo>();
		Map<String,Object> map= new HashMap<String,Object>();
		SaleOrderInPo po = new SaleOrderInPo();
		BeanUtils.copyProperties(vo, po);
		int pageNo=Integer.parseInt(po.getPage_no());
		int pageSize=Integer.parseInt(po.getPage_size());
		int totalNum=0;

		//如果入参销售订单号不为空
		if(po.getTache_code()!=null && !"".equals(po.getTache_code())){
			if("S".equals(po.getTache_code().substring(0, 1))){
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("环节编码传入错误");
				return message;
			}
		}

		totalNum=salesOrderListHisServ.querySalesOrderListHisCount(po);
		//查询销售订单表和销售单个客信息表
		List<SalesOrderListHisPo> salesOrderListHisPoList =salesOrderListHisServ.querySalesOrderListHis(po, pageNo, pageSize);

		if(salesOrderListHisPoList!=null){
			for(SalesOrderListHisPo salesOrderListHisPo:salesOrderListHisPoList){
				InfoSalesOrderListVo infoSalesOrderListVo = new InfoSalesOrderListVo();

				//查询销售订单历史信息表
				InfoSaleOrderHisPo infoSaleOrderHisPo = new InfoSaleOrderHisPo();
				infoSaleOrderHisPo.setSale_order_no(salesOrderListHisPo.getSale_order_no());
				InfoSaleOrderHisPo infoSaleOrderHisPoTemp=infoSaleOrderHisServ.queryInfoSaleOrderHisBySaleOrderNo(infoSaleOrderHisPo);
				if(infoSaleOrderHisPoTemp!=null){
					infoSalesOrderListVo.setSale_order_no(infoSaleOrderHisPoTemp.getSale_order_no());
					infoSalesOrderListVo.setOrder_state(infoSaleOrderHisPoTemp.getOrder_state());
					infoSalesOrderListVo.setAccept_time(infoSaleOrderHisPoTemp.getAccept_time());
					infoSalesOrderListVo.setAccept_system(infoSaleOrderHisPoTemp.getAccept_system());
					infoSalesOrderListVo.setOrder_type(infoSaleOrderHisPoTemp.getOrder_type());
					infoSalesOrderListVo.setAccept_oper_name(infoSaleOrderHisPoTemp.getAccept_oper_name());
					infoSalesOrderListVo.setAccept_depart_name(infoSaleOrderHisPoTemp.getAccept_depart_name());
					infoSalesOrderListVo.setProvince_code(infoSaleOrderHisPoTemp.getProvince_code());
					infoSalesOrderListVo.setArea_code(infoSaleOrderHisPoTemp.getArea_code());
				}
				//查询销售订单个客信息历史表
				InfoSaleOrderPersionHisPo infoSaleOrderPersionHisPo = new InfoSaleOrderPersionHisPo();
				infoSaleOrderPersionHisPo.setSale_order_no(salesOrderListHisPo.getSale_order_no());
				InfoSaleOrderPersionHisPo infoSaleOrderPersionHisPoRes=infoSaleOrderPersionHisServ.queryInfoSaleOrderPersionHisBySaleOrderNo(infoSaleOrderPersionHisPo);
				if(infoSaleOrderPersionHisPoRes!=null){
					infoSalesOrderListVo.setCert_no(infoSaleOrderPersionHisPoRes.getCert_no());
					infoSalesOrderListVo.setCust_name(infoSaleOrderPersionHisPoRes.getCust_name());
				}
				//根据销售订单号反向查询销售订单业务表返回该销售订单号下的业务号码，可能多个
				List<AccNbrPo> accNbrList= new ArrayList<AccNbrPo>();
				InfoSaleOrderServMapHisPo infoSaleOrderServMapHisPo = new InfoSaleOrderServMapHisPo();
				infoSaleOrderServMapHisPo.setSale_order_no(salesOrderListHisPo.getSale_order_no());
				List<InfoSaleOrderServMapHisPo> infoSaleOrderServMapHisPoListTemp=infoSaleOrderServMapHisServ.queryInfoSaleOrderServMapHisBySaleOrderNo(infoSaleOrderServMapHisPo);
				if(infoSaleOrderServMapHisPoListTemp!=null){
					for(InfoSaleOrderServMapHisPo poRes:infoSaleOrderServMapHisPoListTemp){
						AccNbrPo accNbrPo = new AccNbrPo();
						accNbrPo.setAcc_nbr(poRes.getAcc_nbr());
						accNbrList.add(accNbrPo);
					}
				}

				//查询销售订单商品表，查询业务号码
				InfoSaleOrderOfrMapHisPo infoSaleOrderOfrMapHisPo = new InfoSaleOrderOfrMapHisPo();
				infoSaleOrderOfrMapHisPo.setSale_order_no(salesOrderListHisPo.getSale_order_no());
				List<InfoSaleOrderOfrMapHisPo> infoSaleOrderOfrMapHisPoListTemp=infoSaleOrderOfrMapHisServ.queryInfoSaleOrderOfrMapHisBySaleOrderNo(infoSaleOrderOfrMapHisPo);
				if(infoSaleOrderOfrMapHisPoListTemp!=null){
					for(InfoSaleOrderOfrMapHisPo poRes:infoSaleOrderOfrMapHisPoListTemp){
						AccNbrPo accNbrPo = new AccNbrPo();
						accNbrPo.setAcc_nbr(poRes.getAcc_nbr());
						accNbrList.add(accNbrPo);
					}
				}

				infoSalesOrderListVo.setAcc_nbr_list(accNbrList);

				//根据销售订单号查询人工任务处理表
				ProcInstTaskInstHisPo procInstTaskInstHisPo = new ProcInstTaskInstHisPo();
				procInstTaskInstHisPo.setOrder_no(salesOrderListHisPo.getSale_order_no());
				procInstTaskInstHisPo.setOrder_type("100");
				ProcInstTaskInstHisPo procInstTaskInstHisPoList=procInstTaskInstHisServ.queryProcInstTaskInstHisByOrderNo(procInstTaskInstHisPo);
				if(procInstTaskInstHisPoList!=null){
					infoSalesOrderListVo.setTask_id(procInstTaskInstHisPoList.getTask_id());
					infoSalesOrderListVo.setTache_code(procInstTaskInstHisPoList.getTache_code());
				}
				infoSalesOrderListVo.setFinish_flag("1");
				list.add(infoSalesOrderListVo);
			}
		}

		map.put("page_no", pageNo);
		map.put("page_size", pageSize);
		map.put("total_num", totalNum);
		map.put("info_sale_order", list);

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("竣工销售订单列表查询成功");
		message.addArg("info_sale_order_list", list);
		return message;
	}



	@SuppressWarnings("static-access")
	@Override
	public UocMessage queryBeComServOrderList(SaleOrderInVo vo)throws Exception{
		UocMessage message=new UocMessage();

		List<InfoServOrderListVo> list= new ArrayList<InfoServOrderListVo>();
		List<ServOrderListHisPo> servOrderListHisPoList= new ArrayList<ServOrderListHisPo>();
		List<ServOrderListHisPo> servOrderListHisPoList1= new ArrayList<ServOrderListHisPo>();
		List<ServOrderListHisPo> servOrderList= new ArrayList<ServOrderListHisPo>();
		Map<String,Object> map= new HashMap<String,Object>();
		SaleOrderInPo po = new SaleOrderInPo();
		SaleOrderInPo po1 = new SaleOrderInPo();
		BeanUtils.copyProperties(vo, po);
		int pageNo=Integer.parseInt(po.getPage_no());
		int pageSize=Integer.parseInt(po.getPage_size());
		int totalNum=0;
		int totalNum1=0;
		int totalNum2=0;
		String sysDate3="";
		String sysDate4="";
		String partMonth1="";

		//如果排序字段为空，默认反向排序  01：正向     02：反向
		if(vo.getSort_type()==null ||  "".equals(vo.getSort_type()) || "02".equals(vo.getSort_type())){
			po.setSort_type_desc("02");
		}else if("01".equals(vo.getSort_type())){
			po.setSort_type_asc("01");
		}

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
			int today=calendar3.get(calendar3.DAY_OF_MONTH);
			if(today==1){
			  calendar3.add(calendar3.DAY_OF_MONTH, 1);
			}
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

		if(po.getTache_code()!=null && !"".equals(po.getTache_code())){
			if("S".equals(po.getTache_code().substring(0, 1)) || "E".equals(po.getTache_code().substring(0, 1))){
				//totalNum=servOrderListHisServ.queryServOrderHisListCount(po);
				//servOrderListHisPoList=servOrderListHisServ.queryServOrderHisList(po, pageNo, pageSize);
				po.setTache_code_flag("1");//标识是服务订单环节
			}else if("B".equals(po.getTache_code().substring(0, 1)) || "A".equals(po.getTache_code().substring(0, 1))){
				//totalNum=servOrderListHisServ.queryServOrderHisListCountBySaleTacheCode(po);
				//servOrderListHisPoList=servOrderListHisServ.queryServOrderHisListBySaleTacheCode(po, pageNo, pageSize);
				po.setTache_code_flag("0");//标识是销售订单环节
			}else{
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("环节编码传入错误");
				return message;
			}
		}

		//totalNum=servOrderListHisServ.queryServOrderHisListCount(po);
	   // servOrderListHisPoList=servOrderListHisServ.queryServOrderHisList(po, pageNo, pageSize);

	    if(acceptTimeStart.equals(acceptTmeEnd)){
			po.setPart_month(partMonth);
			totalNum1=servOrderListHisServ.queryServOrderHisListCount(po);
		}else if(defaultStartDateTemp.equals(acceptTimeStart)){//如果查询时间段跨月，查询上一个月的数据
			po.setPart_month(partMonth);
			po.setAccept_time_start(sysDate4);
			po.setAccept_time_end(vo.getAccept_time_end());
			totalNum1=servOrderListHisServ.queryServOrderHisListCount(po);

			BeanUtils.copyProperties(po,po1);
			po1.setPart_month(partMonth1);
			po1.setAccept_time_start(vo.getAccept_time_start());
			po1.setAccept_time_end(sysDate3);
			totalNum2=servOrderListHisServ.queryServOrderHisListCount(po1);
		}

		totalNum=totalNum1+totalNum2;

		if(totalNum1!=0 || totalNum2!=0){
			if(po.getSort_type_desc()!=null && !"".equals(po.getSort_type_desc()) && "02".equals(po.getSort_type_desc()) && po.getSort_type_asc()==null){
				if(totalNum1>=pageNo*pageSize){
					int n=(pageNo-1)*pageSize;
					servOrderListHisPoList=servOrderListHisServ.queryServOrderHisList(po, n, pageSize);
					if(servOrderListHisPoList!=null){
						servOrderList.addAll(servOrderListHisPoList);
					}
				}else if(totalNum1<pageNo*pageSize){
					if(totalNum1>(pageNo-1)*pageSize){
						int n=(pageNo-1)*pageSize;
						servOrderListHisPoList=servOrderListHisServ.queryServOrderHisList(po, n, pageSize);
						int y=0;
						if(servOrderListHisPoList!=null){
							servOrderList.addAll(servOrderListHisPoList);
							y=pageSize-servOrderListHisPoList.size();
						}
						if(totalNum2!=0){
							int x=0;
							servOrderListHisPoList1=servOrderListHisServ.queryServOrderHisList(po1, x, y);
							if(servOrderListHisPoList1!=null){
								servOrderList.addAll(servOrderListHisPoList1);
							}
						}
					}else if(totalNum1<=(pageNo-1)*pageSize){
						if(totalNum2!=0){
							int n=(pageNo-1)*pageSize-totalNum1;
							servOrderListHisPoList1=servOrderListHisServ.queryServOrderHisList(po1, n, pageSize);
							if(servOrderListHisPoList1!=null){
								servOrderList.addAll(servOrderListHisPoList1);
							}
						}
					}
				}
			}else if(po.getSort_type_asc()!=null && !"".equals(po.getSort_type_asc()) && "01".equals(po.getSort_type_asc()) && po.getSort_type_desc()==null){
				if(totalNum2>=pageNo*pageSize){
					int n=(pageNo-1)*pageSize;
					servOrderListHisPoList1=servOrderListHisServ.queryServOrderHisList(po1, n, pageSize);
					if(servOrderListHisPoList1!=null){
						servOrderList.addAll(servOrderListHisPoList1);
					}
				}else if(totalNum2<pageNo*pageSize){
					if(totalNum2>(pageNo-1)*pageSize){
						int n=(pageNo-1)*pageSize;
						servOrderListHisPoList1=servOrderListHisServ.queryServOrderHisList(po1, n, pageSize);
						int y=0;
						if(servOrderListHisPoList1!=null){
							servOrderList.addAll(servOrderListHisPoList1);
							y=pageSize-servOrderListHisPoList1.size();
						}
						if(totalNum1!=0){
							int x=0;
							servOrderListHisPoList=servOrderListHisServ.queryServOrderHisList(po, x, y);
							if(servOrderListHisPoList!=null){
								servOrderList.addAll(servOrderListHisPoList);
							}
						}
					}else if(totalNum2<=(pageNo-1)*pageSize){
						if(totalNum1!=0){
							int n=(pageNo-1)*pageSize-totalNum2;
							servOrderListHisPoList=servOrderListHisServ.queryServOrderHisList(po, n, pageSize);
							if(servOrderListHisPoList!=null){
								servOrderList.addAll(servOrderListHisPoList);
							}
						}
					}
				}
			}
		}

		if(servOrderList!=null){
			for(ServOrderListHisPo servOrderListHisPo:servOrderList){
				InfoServOrderListVo infoServOrderListVo = new InfoServOrderListVo();
				BeanUtils.copyProperties(servOrderListHisPo, infoServOrderListVo);

				//根据服务订单号查询服务订单产品表
				InfoServiceOrderProdMapHisPo infoServiceOrderProdMapHisPo =new InfoServiceOrderProdMapHisPo();
				infoServiceOrderProdMapHisPo.setServ_order_no(servOrderListHisPo.getServ_order_no());
				List<InfoServiceOrderProdMapHisPo> infoServiceOrderProdMapHisPoList=infoServiceOrderProdMapHisServ.queryInfoServiceOrderProdMapHisByOrderNo(infoServiceOrderProdMapHisPo);
				if(infoServiceOrderProdMapHisPoList!=null){
					infoServOrderListVo.setProd_code(infoServiceOrderProdMapHisPoList.get(0).getProd_code());
				}

				//根据销售订单号查询支付订单表
				InfoPayOrderHisPo infoPayOrderHisPo = new InfoPayOrderHisPo();
				infoPayOrderHisPo.setRela_order_no(servOrderListHisPo.getSale_order_no());
				infoPayOrderHisPo.setRela_order_type("100");
				List<InfoPayOrderHisPo> infoPayOrderHisPoTemp=infoPayOrderHisServ.queryInfoPayOrderHisByOrderNo(infoPayOrderHisPo);
				if(infoPayOrderHisPoTemp!=null){
					infoServOrderListVo.setPay_type(infoPayOrderHisPoTemp.get(0).getPay_type());
					infoServOrderListVo.setPay_fee(infoPayOrderHisPoTemp.get(0).getPay_fee());
				}

				ProcInstTaskInstHisPo procInstTaskInstHisPo = new ProcInstTaskInstHisPo();
				procInstTaskInstHisPo.setOrder_no(servOrderListHisPo.getServ_order_no());
				procInstTaskInstHisPo.setOrder_type("101");
				ProcInstTaskInstHisPo procInstTaskInstHisPoTemp=procInstTaskInstHisServ.queryProcInstTaskInstHisByOrderNo(procInstTaskInstHisPo);
				if(procInstTaskInstHisPoTemp!=null){
					infoServOrderListVo.setTache_code(procInstTaskInstHisPoTemp.getTache_code());
				}

				//查询物流单号
				InfoDeliverOrderHisPo infoDeliverOrderHisPo = new InfoDeliverOrderHisPo();
				infoDeliverOrderHisPo.setSale_order_no(servOrderListHisPo.getSale_order_no());
				List<InfoDeliverOrderHisPo> infoDeliverOrderHisPoTemp=infoDeliverOrderHisServ.queryInfoDeliverOrderHisByOrderNo(infoDeliverOrderHisPo);
				if(infoDeliverOrderHisPoTemp!=null && infoDeliverOrderHisPoTemp.size()>0){
					infoServOrderListVo.setLogistics_no(infoDeliverOrderHisPoTemp.get(0).getLogistics_no());
				}else{
					infoServOrderListVo.setLogistics_no("");
				}
				
				//查询SIM_ID
				InfoServiceOrderSimCardHisPo infoServiceOrderSimCardHisPo = new InfoServiceOrderSimCardHisPo();
				infoServiceOrderSimCardHisPo.setServ_order_no(servOrderListHisPo.getServ_order_no());
				List<InfoServiceOrderSimCardHisPo> infoServiceOrderSimCardHisPoTemp=infoServiceOrderSimCardHisServ.queryInfoServiceOrderSimCardHisByOrderNo(infoServiceOrderSimCardHisPo);
				if(infoServiceOrderSimCardHisPoTemp!=null && infoServiceOrderSimCardHisPoTemp.size()>0){
					infoServOrderListVo.setSim_id(infoServiceOrderSimCardHisPoTemp.get(0).getSim_id());
				}else{
					infoServOrderListVo.setSim_id("");
				}
				infoServOrderListVo.setFinish_flag("1");
				list.add(infoServOrderListVo);
			}
		}
		map.put("page_no", pageNo);
		map.put("page_size", pageSize);
		map.put("total_num", totalNum);
		map.put("info_serv_order", list);

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("未竣工服务订单列表查询成功");
		message.addArg("info_serv_order_list", map);
		return message;

	}

}
