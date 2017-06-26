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
import com.tydic.unicom.uoc.base.uocinst.po.InfoDeliverOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoPayOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderOfrMapPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPersionPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoSaleOrderServMapPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderProdMapPo;
import com.tydic.unicom.uoc.base.uocinst.po.InfoServiceOrderSimCardPo;
import com.tydic.unicom.uoc.base.uocinst.po.ProcInstTaskInstPo;
import com.tydic.unicom.uoc.base.uocinst.po.SalesOrderListPo;
import com.tydic.unicom.uoc.base.uocinst.po.ServOrderListPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoDeliverOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoPayOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderOfrMapServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderPersionServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoSaleOrderServMapServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderProdMapServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoServiceOrderSimCardServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ProcInstTaskInstServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.SalesOrderListServ;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.ServOrderListServ;
import com.tydic.unicom.uoc.business.order.sale.vo.SaleOrderInVo;
import com.tydic.unicom.uoc.business.order.service.vo.InfoServOrderListVo;
import com.tydic.unicom.uoc.service.task.interfaces.QueryNotComSalesOrderListServDu;
import com.tydic.unicom.uoc.service.task.vo.InfoSalesOrderListVo;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

@Service("QueryNotComSalesOrderListServDu")
public class QueryNotComSalesOrderListServDuImpl implements  QueryNotComSalesOrderListServDu{


	@Autowired
	private SalesOrderListServ salesOrderListServ;

	@Autowired
	private InfoSaleOrderOfrMapServ infoSaleOrderOfrMapServ;

	@Autowired
	private InfoSaleOrderServMapServ infoSaleOrderServMapServ;

	@Autowired
	private ProcInstTaskInstServ procInstTaskInstServ;

	@Autowired
	private InfoSaleOrderServ infoSaleOrderServ;

	@Autowired
	private InfoSaleOrderPersionServ infoSaleOrderPersionServ;

	@Autowired
	private InfoServiceOrderProdMapServ infoServiceOrderProdMapServ;

	@Autowired
	private InfoPayOrderServ infoPayOrderServ;

	@Autowired
	private ServOrderListServ servOrderListServ;
	
	@Autowired
	private InfoDeliverOrderServ infoDeliverOrderServ;
	
	@Autowired
	private InfoServiceOrderSimCardServ infoServiceOrderSimCardServ;
	


	






	@Override
	public UocMessage queryNotComSalesOrderList(SaleOrderInVo vo) throws Exception {
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
		
		totalNum=salesOrderListServ.querySalesOrderListCount(po);
		List<SalesOrderListPo> salesOrderListPoList =salesOrderListServ.querySalesOrderList(po, pageNo, pageSize);
		if(salesOrderListPoList!=null){
			for(SalesOrderListPo salesOrderListPo:salesOrderListPoList){
				InfoSalesOrderListVo infoSalesOrderListVo = new InfoSalesOrderListVo();	
				//根据销售订单号查询销售订单表
				InfoSaleOrderPo infoSaleOrderPo = new InfoSaleOrderPo();
				infoSaleOrderPo.setSale_order_no(salesOrderListPo.getSale_order_no());
				InfoSaleOrderPo infoSaleOrderPoTemp=infoSaleOrderServ.getInfoSaleOrderPoBySaleOrderNo(infoSaleOrderPo);
				if(infoSaleOrderPoTemp !=null){
					infoSalesOrderListVo.setSale_order_no(infoSaleOrderPoTemp.getSale_order_no());
					infoSalesOrderListVo.setOrder_state(infoSaleOrderPoTemp.getOrder_state());
					infoSalesOrderListVo.setAccept_time(infoSaleOrderPoTemp.getAccept_time());
					infoSalesOrderListVo.setAccept_system(infoSaleOrderPoTemp.getAccept_system());
					infoSalesOrderListVo.setOrder_type(infoSaleOrderPoTemp.getOrder_type());
					infoSalesOrderListVo.setAccept_oper_name(infoSaleOrderPoTemp.getAccept_oper_name());
					infoSalesOrderListVo.setAccept_depart_name(infoSaleOrderPoTemp.getAccept_depart_name());
					infoSalesOrderListVo.setProvince_code(infoSaleOrderPoTemp.getProvince_code());
					infoSalesOrderListVo.setArea_code(infoSaleOrderPoTemp.getArea_code());
				}
				//根据销售订单号查询销售订单单个客信息表
				InfoSaleOrderPersionPo infoSaleOrderPersionPo = new InfoSaleOrderPersionPo();
				infoSaleOrderPersionPo.setSale_order_no(salesOrderListPo.getSale_order_no());
				InfoSaleOrderPersionPo infoSaleOrderPersionPoRes=infoSaleOrderPersionServ.getInfoSaleOrderPersionBySaleOrderNo(infoSaleOrderPersionPo);
				if(infoSaleOrderPersionPoRes!=null){
					infoSalesOrderListVo.setCert_no(infoSaleOrderPersionPoRes.getCert_no());
					infoSalesOrderListVo.setCust_name(infoSaleOrderPersionPoRes.getCust_name());
				}
				//根据销售订单号反向查询销售订单业务表返回该销售订单号下的业务号码，可能多个
				List<AccNbrPo> accNbrList= new ArrayList<AccNbrPo>();
				InfoSaleOrderServMapPo infoSaleOrderServMapPo = new InfoSaleOrderServMapPo();
				infoSaleOrderServMapPo.setSale_order_no(salesOrderListPo.getSale_order_no());
				List<InfoSaleOrderServMapPo> infoSaleOrderServMapPoListTemp=infoSaleOrderServMapServ.queryInfoSaleOrderServMapBySaleOrderNo(infoSaleOrderServMapPo);
				if(infoSaleOrderServMapPoListTemp!=null){
					for(InfoSaleOrderServMapPo poRes:infoSaleOrderServMapPoListTemp){
						AccNbrPo accNbrPo = new AccNbrPo();
						accNbrPo.setAcc_nbr(poRes.getAcc_nbr());
						accNbrList.add(accNbrPo);
					}
				}
				//查询销售订单商品表，查询业务号码
				InfoSaleOrderOfrMapPo infoSaleOrderOfrMapPo = new InfoSaleOrderOfrMapPo();
				infoSaleOrderOfrMapPo.setSale_order_no(salesOrderListPo.getSale_order_no());
				List<InfoSaleOrderOfrMapPo> infoSaleOrderOfrMapPoListTemp=infoSaleOrderOfrMapServ.queryInfoSaleOrderOfrMapBySaleOrderNo(infoSaleOrderOfrMapPo);
				if(infoSaleOrderOfrMapPoListTemp!=null){
					for(InfoSaleOrderOfrMapPo poRes:infoSaleOrderOfrMapPoListTemp){
						AccNbrPo accNbrPo = new AccNbrPo();
						accNbrPo.setAcc_nbr(poRes.getAcc_nbr());
						accNbrList.add(accNbrPo);
					}
				}
				infoSalesOrderListVo.setAcc_nbr_list(accNbrList);
				//根据销售订单号查询人工任务处理表
				ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
				procInstTaskInstPo.setOrder_no(salesOrderListPo.getSale_order_no());
				procInstTaskInstPo.setOrder_type("100");
				List<ProcInstTaskInstPo> procInstTaskInstPoList=procInstTaskInstServ.queryProcInstTaskInstByOrderNo(procInstTaskInstPo);
				if(procInstTaskInstPoList!=null){
					infoSalesOrderListVo.setTask_id(procInstTaskInstPoList.get(0).getTask_id());
					infoSalesOrderListVo.setTache_code(procInstTaskInstPoList.get(0).getTache_code());
				}
				infoSalesOrderListVo.setFinish_flag("0");
				list.add(infoSalesOrderListVo);
			}
		}
		map.put("page_no", pageNo);
		map.put("page_size", pageSize);
		map.put("total_num", totalNum);
		map.put("info_sale_order", list);

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("竣工销售订单列表查询成功");
		message.addArg("info_sale_order_list", map);
		return message;
	}


	@Override
	public UocMessage queryNotComServOrderList(SaleOrderInVo vo)throws Exception{
		UocMessage message=new UocMessage();

		List<InfoServOrderListVo> list= new ArrayList<InfoServOrderListVo>();
		List<ServOrderListPo> servOrderListPoList= new ArrayList<ServOrderListPo>();
		List<ServOrderListPo> servOrderListPoList1= new ArrayList<ServOrderListPo>();
		List<ServOrderListPo> servOrderList= new ArrayList<ServOrderListPo>();
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
				//totalNum=servOrderListServ.queryServOrderListCount(po);
				//servOrderListPoList=servOrderListServ.queryServOrderList(po, pageNo, pageSize);
				po.setTache_code_flag("1");//标识是服务订单环节
			}else if("B".equals(po.getTache_code().substring(0, 1)) || "A".equals(po.getTache_code().substring(0, 1))){
				//totalNum=servOrderListServ.queryServOrderListCountBySaleTacheCode(po);
				//servOrderListPoList=servOrderListServ.queryServOrderListBySaleTacheCode(po, pageNo, pageSize);
				po.setTache_code_flag("0");//标识是销售订单环节
			}else{
				message.setRespCode(RespCodeContents.PARAM_ERROR);
				message.setContent("环节编码传入错误");
				return message;
			}
		}
		
		if(acceptTimeStart.equals(acceptTmeEnd)){
			po.setPart_month(partMonth);
			totalNum1=servOrderListServ.queryServOrderListCount(po);
		}else if(defaultStartDateTemp.equals(acceptTimeStart)){//如果查询时间段跨月，查询上一个月的数据
			po.setPart_month(partMonth);
			po.setAccept_time_start(sysDate4);
			po.setAccept_time_end(vo.getAccept_time_end());
			totalNum1=servOrderListServ.queryServOrderListCount(po);
			
			BeanUtils.copyProperties(po,po1);
			po1.setPart_month(partMonth1);
			po1.setAccept_time_start(vo.getAccept_time_start());
			po1.setAccept_time_end(sysDate3);
			totalNum2=servOrderListServ.queryServOrderListCount(po1);
		}
		
		totalNum=totalNum1+totalNum2;
		
		if(totalNum1!=0 || totalNum2!=0){
			if(po.getSort_type_desc()!=null && !"".equals(po.getSort_type_desc()) && "02".equals(po.getSort_type_desc()) && po.getSort_type_asc()==null){
				if(totalNum1>=pageNo*pageSize){
					int n=(pageNo-1)*pageSize;
					servOrderListPoList=servOrderListServ.queryServOrderList(po, n, pageSize);
					if(servOrderListPoList!=null){
						servOrderList.addAll(servOrderListPoList);
					}
				}else if(totalNum1<pageNo*pageSize){
					if(totalNum1>(pageNo-1)*pageSize){
						int n=(pageNo-1)*pageSize;
						servOrderListPoList=servOrderListServ.queryServOrderList(po, n, pageSize);
						int y=0;
						if(servOrderListPoList!=null){
							servOrderList.addAll(servOrderListPoList);
							y=pageSize-servOrderListPoList.size();
						}
						if(totalNum2!=0){
							int x=0;
							servOrderListPoList1=servOrderListServ.queryServOrderList(po1, x, y);
							if(servOrderListPoList1!=null){
								servOrderList.addAll(servOrderListPoList1);
							}
						}
					}else if(totalNum1<=(pageNo-1)*pageSize){
						if(totalNum2!=0){
							int n=(pageNo-1)*pageSize-totalNum1;
							servOrderListPoList1=servOrderListServ.queryServOrderList(po1, n, pageSize);
							if(servOrderListPoList1!=null){
								servOrderList.addAll(servOrderListPoList1);
							}
						}
					}
				}
			}else if(po.getSort_type_asc()!=null && !"".equals(po.getSort_type_asc()) && "01".equals(po.getSort_type_asc()) && po.getSort_type_desc()==null){
				if(totalNum2>=pageNo*pageSize){
					int n=(pageNo-1)*pageSize;
					servOrderListPoList1=servOrderListServ.queryServOrderList(po1, n, pageSize);
					if(servOrderListPoList!=null){
						servOrderList.addAll(servOrderListPoList1);
					}
				}else if(totalNum2<pageNo*pageSize){
					if(totalNum2>(pageNo-1)*pageSize){
						int n=(pageNo-1)*pageSize;
						servOrderListPoList1=servOrderListServ.queryServOrderList(po1, n, pageSize);
						int y=0;
						if(servOrderListPoList1!=null){
							servOrderList.addAll(servOrderListPoList1);
							y=pageSize-servOrderListPoList1.size();
						}
						if(totalNum1!=0){
							int x=0;
							servOrderListPoList=servOrderListServ.queryServOrderList(po, x, y);
							if(servOrderListPoList!=null){
								servOrderList.addAll(servOrderListPoList);
							}
						}
					}else if(totalNum2<=(pageNo-1)*pageSize){
						if(totalNum1!=0){
							int n=(pageNo-1)*pageSize-totalNum2;
							servOrderListPoList=servOrderListServ.queryServOrderList(po, n, pageSize);
							if(servOrderListPoList!=null){
								servOrderList.addAll(servOrderListPoList);
							}
						}
					}
				}
			}
		}
		
		
		if(servOrderList!=null){			
			for(ServOrderListPo servOrderListPo:servOrderList){
				InfoServOrderListVo infoServOrderListVo = new InfoServOrderListVo();
				BeanUtils.copyProperties(servOrderListPo, infoServOrderListVo);

				//根据服务订单号查询服务订单产品表
				InfoServiceOrderProdMapPo infoServiceOrderProdMapPo =new InfoServiceOrderProdMapPo();
				infoServiceOrderProdMapPo.setServ_order_no(servOrderListPo.getServ_order_no());
				List<InfoServiceOrderProdMapPo> infoServiceOrderProdMapPoList=infoServiceOrderProdMapServ.queryInfoServiceOrderProdMapByOrderNo(infoServiceOrderProdMapPo);
				if(infoServiceOrderProdMapPoList!=null){
					infoServOrderListVo.setProd_code(infoServiceOrderProdMapPoList.get(0).getProd_code());
				}

				//根据销售订单号查询支付订单表
				InfoPayOrderPo infoPayOrderPo = new InfoPayOrderPo();
				infoPayOrderPo.setRela_order_no(servOrderListPo.getSale_order_no());
				infoPayOrderPo.setRela_order_type("100");
				InfoPayOrderPo infoPayOrderPoTemp=infoPayOrderServ.queryInfoPayOrderByRelaOrderNo(infoPayOrderPo);
				if(infoPayOrderPoTemp!=null){
					infoServOrderListVo.setPay_type(infoPayOrderPoTemp.getPay_type());
					infoServOrderListVo.setPay_fee(infoPayOrderPoTemp.getPay_fee());
				}

				ProcInstTaskInstPo procInstTaskInstPo = new ProcInstTaskInstPo();
				procInstTaskInstPo.setOrder_no(servOrderListPo.getServ_order_no());
				procInstTaskInstPo.setOrder_type("101");
				List<ProcInstTaskInstPo> procInstTaskInstPoTemp=procInstTaskInstServ.queryProcInstTaskInstByOrderNo(procInstTaskInstPo);
				if(procInstTaskInstPoTemp!=null){
					infoServOrderListVo.setTache_code(procInstTaskInstPoTemp.get(0).getTache_code());
				}
				//查询物流单号
				InfoDeliverOrderPo infoDeliverOrderPo = new InfoDeliverOrderPo();
				infoDeliverOrderPo.setSale_order_no(servOrderListPo.getSale_order_no());
				List<InfoDeliverOrderPo> infoDeliverOrderPoTemp=infoDeliverOrderServ.queryInfoDeliverOrderBySaleOrderNo(infoDeliverOrderPo);
				if(infoDeliverOrderPoTemp!=null && infoDeliverOrderPoTemp.size()>0){
					infoServOrderListVo.setLogistics_no(infoDeliverOrderPoTemp.get(0).getLogistics_no());
				}else{
					infoServOrderListVo.setLogistics_no("");
				}
				
				//查询SIM_ID
				InfoServiceOrderSimCardPo infoServiceOrderSimCardPo = new InfoServiceOrderSimCardPo();
				infoServiceOrderSimCardPo.setServ_order_no(servOrderListPo.getServ_order_no());
				List<InfoServiceOrderSimCardPo> infoServiceOrderSimCardPoTemp=infoServiceOrderSimCardServ.queryInfoServiceOrderSimCardByOrderNo(infoServiceOrderSimCardPo);
				if(infoServiceOrderSimCardPoTemp!=null && infoServiceOrderSimCardPoTemp.size()>0){
					infoServOrderListVo.setSim_id(infoServiceOrderSimCardPoTemp.get(0).getSim_id());
				}else{
					infoServOrderListVo.setSim_id("");
				}
				infoServOrderListVo.setFinish_flag("0");
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

