package com.tydic.unicom.uoc.business.common.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.tydic.dicdata.core.es.dml.model.DataSearchResultItem;
import com.tydic.unicom.uoc.base.uoccode.po.CodeListPo;
import com.tydic.unicom.uoc.base.uoccode.service.interfaces.CodeListServ;
import com.tydic.unicom.uoc.base.uochis.po.InfoDeliverOrderHisPo;
import com.tydic.unicom.uoc.base.uochis.service.interfaces.InfoDeliverOrderHisServ;
import com.tydic.unicom.uoc.base.uocinst.po.InfoDeliverOrderPo;
import com.tydic.unicom.uoc.base.uocinst.service.interfaces.InfoDeliverOrderServ;
import com.tydic.unicom.uoc.business.common.interfaces.EsReportDataServDu;
import com.tydic.unicom.uoc.business.common.vo.LogisticsReportVo;
import com.tydic.unicom.uoc.business.common.vo.ServOrderListESInVo;
import com.tydic.unicom.uoc.business.common.vo.ServOrderListESOutVo;
import com.tydic.unicom.uoc.service.code.interfaces.CodeListServDu;
import com.tydic.unicom.uoc.service.common.impl.ToolSpring;
import com.tydic.unicom.uoc.service.common.interfaces.JsonToBeanServDu;
import com.tydic.unicom.uoc.service.common.interfaces.OperServDu;
import com.tydic.unicom.uoc.service.common.vo.PropertiesParamVo;
import com.tydic.unicom.uoc.service.es.impl.EsDateUtil;
import com.tydic.unicom.uoc.service.es.interfaces.EsServiceServDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderBaseDu;
import com.tydic.unicom.uoc.service.order.interfaces.InfoServiceOrderExtServDu;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderDeveloperVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderPersionVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderProdMapVo;
import com.tydic.unicom.uoc.service.order.vo.InfoServiceOrderVo;
import com.tydic.unicom.util.DateUtil;
import com.tydic.unicom.webUtil.RespCodeContents;
import com.tydic.unicom.webUtil.UocMessage;

public class EsReportDataServDuImpl implements EsReportDataServDu {

	Logger logger = Logger.getLogger(EsReportDataServDuImpl.class);

	@Autowired
	private CodeListServ codeListServ;
	@Autowired
	private InfoDeliverOrderServ infoDeliverOrderServ;
	@Autowired
	private  EsServiceServDu esServiceServDu;
	@Autowired
	private InfoDeliverOrderHisServ infoDeliverOrderHisServ;
	@Autowired
	private OperServDu operServDu;
	@Autowired
	private InfoServiceOrderExtServDu infoServiceOrderExtServDu;
	@Autowired
	private InfoServiceOrderBaseDu infoServiceOrderBaseDu;
	@Autowired
	private CodeListServDu codeListServDu;
	@Autowired
	private JsonToBeanServDu jsonToBeanServDu;
	@Autowired
	@Qualifier("propertiesParamVo")
	private PropertiesParamVo propertiesParamVo;
	/**
	 * 物流明细报表中间数据生成UOC0067
	 */
	@Override
	public  UocMessage createLogisticsDetailData() throws Exception {
		logger.info("===========物流数据报表生成===========");
		UocMessage message=new UocMessage();
		String beforeTimeStamp="";//初始化以前时间戳
		String type_code="timestamp";
		CodeListPo codeListPo=new CodeListPo();
		codeListPo.setType_code(type_code);
		CodeListPo codeListPoTemp=codeListServ.queryCodeListByTypeCode(codeListPo);
		beforeTimeStamp=codeListPoTemp.getCode_id().trim();
//		CodeList codeList=new CodeList();
//		codeList.setList_id(type_code);
//		List<CodeList> list = codeListServDu.queryCodeListByTypeCodeFromRedis(codeList);
//		String code_id=list.get(0).getCode_id();
//		beforeTimeStamp=code_id;
		//TODO
//		for(CodeListPo codeListPoTemp:list){
//			if(codeListPoTemp!=null){
//				if(codeListPoTemp.getList_id().equals(list_id)){
//					beforeTimeStamp=codeListPoTemp.getCode_id().trim();
//				}
//			}
//		}
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate=sdf.format(date);//获取系统时间字符串
		long beforeTimestamp1=0;

		if(!"".equals(beforeTimeStamp)){
			 beforeTimestamp1=Long.parseLong(beforeTimeStamp,10);//转化以前时间戳
		}

		Date dateBefor=new Date(beforeTimestamp1);
		String strBefore=sdf.format(dateBefor);
		long systimestamp=date.getTime();//获取当前系统时间戳
		String nowTimeStamp=Long.toString(systimestamp);
		//String nowTimeStamp1=nowTimeStamp.substring(0,10);//截取当前时间戳的前10位数的时间戳
		beforeTimeStamp=nowTimeStamp;//把当前时间戳赋值到以前时间戳进行更新数据
		CodeListPo codeListPo1=new CodeListPo();
		codeListPo1.setCode_id(beforeTimeStamp);
		codeListPo1.setList_id("1060");
		boolean flag=codeListServ.update(codeListPo1);

		if(!flag){
			message.setContent("更新时间戳失败");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			return message;
		}

		SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt=new Date(beforeTimestamp1);
		String beforeDateStr=sdf1.format(dt);
		String month1=beforeDateStr.substring(4,6).trim();
		String partMonth1=Integer.toString(Integer.parseInt(month1));//获取数据库中月份
		String nowDateStr=sdf1.format(date);
		String month2=nowDateStr.substring(4, 6).trim();
		String partMonth2=Integer.toString(Integer.parseInt(month2));//获取当前月份
		Calendar calendar=Calendar.getInstance();//获取当前日历
		calendar.setTime(date);//设置当前时间的日历
		calendar.add(calendar.MONTH, -1);//设置前一个月
		Date lastDate=calendar.getTime();//获取上个月的时间
		String lastMonth =sdf1.format(lastDate).substring(4,6).trim();
		String part_month1=Integer.toString(Integer.parseInt(lastMonth));//获取上个月的月份

		InfoDeliverOrderPo infoDeliverOrderPo=new InfoDeliverOrderPo();
		infoDeliverOrderPo.setCreate_time(strBefore);
		infoDeliverOrderPo.setFinish_time(strDate);
		infoDeliverOrderPo.setPart_month(part_month1);
		//TODO
		infoDeliverOrderPo.setArea_code(propertiesParamVo.getActivemqAreaCodeList());

		List<InfoDeliverOrderPo> infoDeliverOrderPoList=new ArrayList<InfoDeliverOrderPo>();//初始化两月数据
		List<InfoDeliverOrderPo> infoDeliverOrderPoRes=infoDeliverOrderServ.queryInfoDeliverOrderAll(infoDeliverOrderPo);
		if(infoDeliverOrderPoRes!=null&&infoDeliverOrderPoRes.size()>0){
			infoDeliverOrderPoList.addAll(infoDeliverOrderPoRes);
		}

		infoDeliverOrderPo.setPart_month(partMonth2);
		List<InfoDeliverOrderPo> infoDeliverOrderPoRes1=infoDeliverOrderServ.queryInfoDeliverOrderAll(infoDeliverOrderPo);
		if(infoDeliverOrderPoRes1!=null&&infoDeliverOrderPoRes1.size()>0){
			infoDeliverOrderPoList.addAll(infoDeliverOrderPoRes1);
		}

		//历史表
		List<InfoDeliverOrderHisPo> infoDeliverOrderHisPoList=new ArrayList<InfoDeliverOrderHisPo>();//初始化两月数据历史
		InfoDeliverOrderHisPo infoDeliverOrderHisPo=new InfoDeliverOrderHisPo();
		infoDeliverOrderHisPo.setCreate_time(strBefore);
		infoDeliverOrderHisPo.setFinish_time(strDate);
		infoDeliverOrderHisPo.setPart_month(part_month1);
		//TODO
		infoDeliverOrderHisPo.setArea_code(propertiesParamVo.getActivemqAreaCodeList());
		List<InfoDeliverOrderHisPo> infoDeliverOrderHisPoRes=infoDeliverOrderHisServ.queryInfoDeliverOrderHisAll(infoDeliverOrderHisPo);
		if(infoDeliverOrderHisPoRes!=null&&infoDeliverOrderHisPoRes.size()>0){
			infoDeliverOrderHisPoList.addAll(infoDeliverOrderHisPoRes);
		}

		infoDeliverOrderHisPo.setPart_month(partMonth2);
		List<InfoDeliverOrderHisPo> infoDeliverOrderHisPoRes1=infoDeliverOrderHisServ.queryInfoDeliverOrderHisAll(infoDeliverOrderHisPo);
		if(infoDeliverOrderHisPoRes1!=null&&infoDeliverOrderHisPoRes1.size()>0){
			infoDeliverOrderHisPoList.addAll(infoDeliverOrderHisPoRes1);
		}

		List<InfoDeliverOrderPo> newList=new ArrayList<InfoDeliverOrderPo>();//历史表数据加入当前表
		if(infoDeliverOrderHisPoList!=null&&infoDeliverOrderHisPoList.size()>0){
			for(InfoDeliverOrderHisPo infoDeliverOrderHisPoTemp:infoDeliverOrderHisPoList){
				InfoDeliverOrderPo infoDeliverOrderPoTemp=new InfoDeliverOrderPo();
				BeanUtils.copyProperties(infoDeliverOrderHisPoTemp, infoDeliverOrderPoTemp);
				newList.add(infoDeliverOrderPoTemp);
				infoDeliverOrderPoList.addAll(newList);
			}
		}

		String indexName=propertiesParamVo.getIndexName();
		String typeName=month2;

		if(infoDeliverOrderPoList!=null){
			for(InfoDeliverOrderPo infoDeliverOrderPoTemp:infoDeliverOrderPoList){
				Map<String,Object> mapPo=infoDeliverOrderPoTemp.convertThis2Map();
				mapPo.remove("create_time");
				mapPo.remove("finsh_time");
				String createTimeStr=infoDeliverOrderPoTemp.getCreate_time();
				String finishTimeStr=infoDeliverOrderPoTemp.getFinish_time();
				String esStrCreate="";
				String esStrFinish="";
				if(createTimeStr!=null && !"".equals(createTimeStr)){
					Date createTime=EsDateUtil.strToDate(createTimeStr);
					long longDate1=createTime.getTime();
					esStrCreate=EsDateUtil.formatDateToEs(longDate1);
				}
				if(finishTimeStr!=null && !"".equals(finishTimeStr)){
					Date finishTime=EsDateUtil.strToDate(finishTimeStr);
					long longDate2=finishTime.getTime();
					esStrFinish=EsDateUtil.formatDateToEs(longDate2);
				}

				mapPo.put("create_time", esStrCreate);
				mapPo.put("finish_time", esStrFinish);
				List<Map<String,Object>> listsub=new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> list1=new ArrayList<Map<String,Object>>();
				list1.add(mapPo);
				Map<String,Object> map2=new HashMap<String,Object>();
				String sale_order_no=infoDeliverOrderPoTemp.getSale_order_no();
				//String id=Long.toString(new Date().getTime())+"deliver";
				String id=infoDeliverOrderPoTemp.getDeliver_order_no();
				map2.put("id",id);
				map2.put("sale_order_no",sale_order_no);
				map2.put("order", list1);
				listsub.add(map2);
				//从ES中插入数据
				boolean insertFlag=esServiceServDu.batchInsert(indexName, typeName, listsub);

				if(!insertFlag){

					logger.info("============插入数据错误===========");

				}

			}

	}

		message.setContent("物流明细报表中间数据生成成功");
		message.setRespCode(RespCodeContents.SUCCESS);
		return message;
	}
	/**
	 * 物流明细报表查询 UOC0068
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage queryLogisticsDetailData(LogisticsReportVo vo) throws Exception {

		logger.info("===========物流明细报表查询===========");

		UocMessage message=new UocMessage();

		if(vo.getPage_no()==null||"".equals(vo.getPage_no())){
			message.setContent("page_no不能为空");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			return message;
		}
		if(vo.getPage_size()==null||"".equals(vo.getPage_size())){
			message.setContent("page_size不能为空");
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			return message;
		}
		Date date=new Date();
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
		String month=sdf1.format(date).substring(4,6).trim();
		String indexName=propertiesParamVo.getIndexName();
		String typeName=month;
		int page_no=Integer.parseInt(vo.getPage_no());
		int page_size=Integer.parseInt(vo.getPage_size());

		Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date monthBegin=cal.getTime();//获取当月月初时间
        Calendar cal1 = Calendar.getInstance();
        cal1.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONDAY), cal1.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal1.set(Calendar.DAY_OF_MONTH, cal1.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal1.set(Calendar.HOUR_OF_DAY, 24);
        Date monthEnd=cal1.getTime();//获取当月月末时间
        Date create_time=new Date();
        Date finish_time=new Date();

        if(vo.getCreate_time_start()==null || "".equals(vo.getCreate_time_start())){
        	create_time=monthBegin;
        }else{
        	String create_time_start=vo.getCreate_time_start();
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            create_time=sdf.parse(create_time_start);
        }

        if(vo.getCreate_time_end()==null || "".equals(vo.getCreate_time_end())){
        	finish_time=monthEnd;
        }else{
        	String create_time_end=vo.getCreate_time_end();
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            finish_time=sdf.parse(create_time_end);
        }

        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    	Map<String,Object> map1=new HashMap<String,Object>();
    	Map<String,Object> map2=new HashMap<String,Object>();

    	map1.put("isDateTime", "yes");
    	//map1.put("paramName", "info_sale_order.create_time");
    	map1.put("paramName", "order.create_time");
    	map1.put("paramValueFrom", create_time.getTime());
    	map1.put("paramValueTo", finish_time.getTime());
    	list.add(map1);
    	if(vo.getAccept_depart_no()!=null&&!"".equals(vo.getAccept_depart_no())){
	    	map2.put("isDateTime", "no");
	    	map2.put("paramName", "info_order.accept_depart_no");
	    	map2.put("paramValue", vo.getAccept_depart_no());
	    	list.add(map2);
    	}

    	//多条数据查询
    	Map<String,Object> map=new HashMap<String,Object>();
    	int page_from=page_size*(page_no-1);

    	map.put("size", page_size);
    	//map.put("sort", "info_deliver_order.create_time");
    	map.put("from", page_from);
    	//map.put("f", value)
    	List<DataSearchResultItem> queryList=null;
    	long totalNum=0;
    	UocMessage esResult=esServiceServDu.query(indexName, typeName, list,map);
    	if(RespCodeContents.SUCCESS.equals(esResult.getRespCode())){
    		queryList=(List<DataSearchResultItem>) esResult.getArgs().get("data");
    		totalNum=(long)esResult.getArgs().get("total");
    	}
    	List<InfoDeliverOrderPo> newList=new ArrayList<InfoDeliverOrderPo>();
    	if(queryList!=null && queryList.size()>0){
    		for(int i=0;i<queryList.size();i++){
    		List<Map<String,Object>> info_deliver_order_list=(List<Map<String,Object>> )queryList.get(i)
					.getValues().get("info_order");
			Map<String,Object> mapTemp=info_deliver_order_list.get(0);
			InfoDeliverOrderPo infoDeliverOrderPo=new InfoDeliverOrderPo();
			//map转化为bean
//			InfoDeliverOrderPo infoDeliverOrderPoTemp=map2Java(infoDeliverOrderPo,mapTemp);
			BeanUtils.populate(infoDeliverOrderPo, mapTemp);
//			newList.add(infoDeliverOrderPoTemp);
			newList.add(infoDeliverOrderPo);
    		}
    	}

    	message.addArg("page_no", page_no);
    	message.addArg("page_size", page_size);
    	message.addArg("total_num", totalNum);
    	message.addArg("info_deliver_order_list",newList);
    	message.setRespCode(RespCodeContents.PARAM_ERROR);
        message.setContent("物流明细报表查询成功");
        return message;
	}

	/**
	 * 服务订单列表查询 (ES版) UOC0074 反射调用
	 */
	public UocMessage queryServOrderListESAbilityPlatform(String json_info) throws Exception {
		getBeanDu();

		ServOrderListESInVo vo = new ServOrderListESInVo();

		vo = (ServOrderListESInVo) jsonToBeanServDu.jsonInfoToBean(json_info, ServOrderListESInVo.class);
		UocMessage message = queryServOrderListES(vo);
		return message;
	}

	/**
	 * 服务订单列表查询 (ES版) UOC0074
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage queryServOrderListES(ServOrderListESInVo vo) throws Exception {
		logger.info("===========服务订单列表查询 (ES版)===========");
		UocMessage message=new UocMessage();
		if(vo.getJsession_id()==null||"".equals(vo.getJsession_id())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
	        message.setContent("jsession_id不能为空");
	        return message;
		}
		if(vo.getPage_no()==null||"".equals(vo.getPage_no())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
	        message.setContent("page_no不能为空");
	        return message;
		}
		if(vo.getPage_size()==null||"".equals(vo.getPage_size())){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
	        message.setContent("page_size不能为空");
	        return message;
		}
		UocMessage loginMessage=operServDu.isLogin(vo.getJsession_id());
		if(!"0000".equals(loginMessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("效验登录信息失败");
			return message;
		}
//		Map<String,Object> oper_info=(Map<String, Object>) loginMessage.getArgs().get("oper_info");
//		String province_code = oper_info.get("province_code").toString();
//		String area_code = oper_info.get("area_code").toString();
		Date date=new Date();
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
		String month=sdf1.format(date).substring(4,6).trim();
		String indexName=propertiesParamVo.getIndexName();
		String[] arrayIndexName={indexName};
		String typeName=month;

		Map<String,Object> map1=new HashMap<String,Object>();
		Map<String,Object> map2=new HashMap<String,Object>();
		Map<String,Object> map3=new HashMap<String,Object>();
		Map<String,Object> map4=new HashMap<String,Object>();
		Map<String,Object> map5=new HashMap<String,Object>();
		Map<String,Object> map6=new HashMap<String,Object>();
		Map<String,Object> map7=new HashMap<String,Object>();
		Map<String,Object> map8=new HashMap<String,Object>();
		Map<String,Object> map9=new HashMap<String,Object>();
		Map<String,Object> map10=new HashMap<String,Object>();
		Map<String,Object> map11=new HashMap<String,Object>();
		Map<String,Object> map12=new HashMap<String,Object>();
		Map<String,Object> map14=new HashMap<String,Object>();
		Map<String,Object> map15=new HashMap<String,Object>();
		Map<String,Object> map16=new HashMap<String,Object>();
		Map<String,Object> map17=new HashMap<String,Object>();
		Map<String,Object> map18=new HashMap<String,Object>();
		Map<String, Object> map19 = new HashMap<String, Object>();
		Map<String, Object> map20 = new HashMap<String, Object>();
		List<Map<String,Object>> listServ=new ArrayList<Map<String,Object>>();
		String province_code = vo.getProvince_code();
		String area_code = vo.getArea_code();
		String general_query_condition=vo.getGeneral_query_condition();
		String serv_order_no=vo.getServ_order_no();
		String cust_name=vo.getCust_name();
		String cert_no=vo.getCert_no();
		String accept_oper_no=vo.getAccept_oper_no();
		String accept_depart_no=vo.getAccept_depart_no();
		String order_state=vo.getOrder_state();
		String acc_nbr=vo.getAcc_nbr();
		String prod_code=vo.getProd_code();
		String oper_code=vo.getOper_code();
		String tache_code=vo.getTache_code();
		String accept_no=vo.getAccept_no();
		String pay_type=vo.getPay_type();
		String accept_system=vo.getAccept_system();
		String accept_time_start=vo.getAccept_time_start();
		String accept_time_end=vo.getAccept_time_end();
		String sim_id=vo.getSim_id();
		String deal_oper_no = vo.getDeal_oper_no();
		String deal_depart_no = vo.getDeal_depart_no();
		int page_no=Integer.parseInt(vo.getPage_no());
		int page_size=Integer.parseInt(vo.getPage_size());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date create_time=new Date();
		Date finish_time=new Date();
		//取格林威治时间的时间戳
		long createTime=0;
		long finishTime=0;
		if(accept_time_start!=null && !"".equals(accept_time_start)){
			create_time=sdf.parse(accept_time_start);
			createTime=create_time.getTime()+8*60*60*1000;//此处加8个小时时间戳
		}else{
			createTime=create_time.getTime()+8*60*60*1000;
		}
		if(accept_time_end!=null && !"".equals(accept_time_end)){
			finish_time=sdf.parse(accept_time_end);
			finishTime=finish_time.getTime()+8*60*60*1000;
		}else{
			finishTime=finish_time.getTime()+8*60*60*1000;
		}
		if(general_query_condition!=null && !"".equals(general_query_condition)){
		//通用条件匹配cust_name、cert_no、acc_nbr、accept_no、serv_order_no、sim_id
			if(general_query_condition.length()==2||general_query_condition.length()==4||general_query_condition.length()==6
					||general_query_condition.length()==8){
				cust_name=general_query_condition;
			}else if(general_query_condition.length()==18){
				cert_no=general_query_condition;
			}else if(general_query_condition.length()==11){
				acc_nbr=general_query_condition;
			}else if(general_query_condition.length()==19){
				if(general_query_condition.substring(0,5).equals("8960")){
					sim_id=general_query_condition;
				}else{
					serv_order_no=general_query_condition;
				}
			}else if(general_query_condition.substring(0,1).equals("E")){
				serv_order_no=general_query_condition;
			}else{
				accept_no=general_query_condition;
			}
		}
		/*
		 *服务订单中数据依次传入各个map
		 */
		if(serv_order_no!=null && !"".equals(serv_order_no)){
	    	map1.put("paramName", "info_service_list.serv_order_no");
			map1.put("paramValue", serv_order_no);
			listServ.add(map1);
		}
		if(acc_nbr!=null && !"".equals(acc_nbr)){
	    	map2.put("paramName", "info_service_list.acc_nbr");
			map2.put("paramValue",acc_nbr);
			listServ.add(map2);
		}
		if(province_code!=null && !"".equals(province_code)){
	    	map3.put("paramName", "info_service_list.province_code");
			map3.put("paramValue",province_code);
			listServ.add(map3);
		}
		if(area_code!=null && !"".equals(area_code)){
	    	map4.put("paramName", "info_service_list.area_code");
			map4.put("paramValue",area_code);
			listServ.add(map4);
		}
		if(oper_code!=null && !"".equals(oper_code)){
			if (oper_code.contains(",")) {
				String[] operCodes = oper_code.split(",");
				map5.put("isMultiQuery", "yes");
				map5.put("paramName", "info_service_list.oper_code");
				map5.put("paramValue", operCodes);
			} else {
				map5.put("paramName", "info_service_list.oper_code");
				map5.put("paramValue", oper_code);
			}

			listServ.add(map5);
		}
		if(accept_system!=null && !"".equals(accept_system)){
	    	map7.put("paramName", "info_service_list.accept_system");
			map7.put("paramValue",accept_system);
			listServ.add(map7);
		}
		if(order_state!=null && !"".equals(order_state)){
	    	map8.put("paramName", "info_service_list.order_state");
			map8.put("paramValue",order_state);
			listServ.add(map8);
		}
		if(accept_oper_no!=null && !"".equals(accept_oper_no)){
	    	map9.put("paramName", "info_service_list.accept_oper_no");
			map9.put("paramValue",accept_oper_no);
			listServ.add(map9);
		}
		if(accept_time_start!=null && !"".equals(accept_time_start)){
	    	map10.put("isDateTime", "yes");
	    	map10.put("paramName", "info_service_list.create_time");
	    	map10.put("paramValueFrom", createTime);
	    	map10.put("paramValueTo", finishTime);
	    	listServ.add(map10);
		}
		if(cust_name!=null && !"".equals(cust_name)){
			map6.put("isDateTime", "no");
	    	map6.put("paramName", "info_service_list.cust_name");
			map6.put("paramValue",cust_name);
			listServ.add(map6);
		}
		if(cert_no!=null && !"".equals(cert_no)){
	    	map11.put("paramName", "info_service_list.cert_no");
			map11.put("paramValue",cert_no);
			listServ.add(map11);
		}
		if(prod_code!=null && !"".equals(prod_code)){
	    	map12.put("paramName", "info_service_list.prod_code");
			map12.put("paramValue",prod_code);
			listServ.add(map12);
		}
		if(tache_code!=null && !"".equals(tache_code)){
	    	map14.put("paramName", "info_service_list.tache_code");
			map14.put("paramValue",tache_code);
			listServ.add(map14);
		}
		if(accept_no!=null && !"".equals(accept_no)){
	    	map15.put("paramName", "info_service_list.accept_no");
			map15.put("paramValue",accept_no);
			listServ.add(map15);
		}
		if(pay_type!=null && !"".equals(pay_type)){
	    	map16.put("paramName", "info_service_list.pay_type");
	    	map16.put("paramValue",pay_type);
			listServ.add(map16);
		}
		if(sim_id!=null && !"".equals(sim_id)){
			map17.put("paramName", "info_service_list.sim_id");
			map17.put("paramValue",sim_id);
			listServ.add(map17);
		}
		if(accept_depart_no!=null &&!"".equals(accept_depart_no)){
			map18.put("paramName", "info_service_list.accept_depart_no");
			map18.put("paramValue",accept_depart_no);
			listServ.add(map18);
		}
		if (deal_oper_no != null && !"".equals(deal_oper_no)) {
			map19.put("paramName", "info_service_list.deal_oper_no");
			map19.put("paramValue", deal_oper_no);
			listServ.add(map19);
		}
		if (deal_depart_no != null && !"".equals(deal_depart_no)) {
			map20.put("paramName", "info_service_list.deal_depart_no");
			map20.put("paramValue", deal_depart_no);
			listServ.add(map20);
		}
		//查询服务订单列表
		Map<String,Object> inMap=new HashMap<String,Object>();
		int page_from=page_size *( page_no-1);
		inMap.put("size", page_size);
		inMap.put("from", page_from);
		inMap.put("sort", "info_service_list.create_time");
		if("01".equals(vo.getSort_type()) || vo.getSort_type()==null || "".equals(vo.getSort_type())){
			inMap.put("sort_asc_desc", "asc");
		}else if("02".equals(vo.getSort_type())){
			inMap.put("sort_asc_desc", "desc");
		}
		int createMonth = Integer.parseInt(accept_time_start.substring(5,7));
		int endMonth = Integer.parseInt(accept_time_end.substring(5,7));
		StringBuilder sb = new StringBuilder();
		if(createMonth!=endMonth){
			for(int i=createMonth;i<=endMonth;i++){
				if(String.valueOf(createMonth).length()==1){
					sb.append("0").append(String.valueOf(createMonth)).append(",");
				}else{
					sb.append(String.valueOf(createMonth)).append(",");
				}
				createMonth=createMonth+1;
			}
		}else{
			if(String.valueOf(createMonth).length()==1){
				sb.append("0").append(String.valueOf(createMonth));
			}else{
				sb.append(String.valueOf(createMonth));
			}
		}
		String arrayStr="";
		UocMessage esResult=null;
		if(sb.toString().contains(",")){
			arrayStr = sb.toString().substring(0,sb.toString().length()-1);
			String[] arrayTypeName = arrayStr.split(",");
			esResult =esServiceServDu.query(arrayIndexName, arrayTypeName,listServ,inMap);
		}else{
			String[] arrayTypeName = {sb.toString()};
			esResult =esServiceServDu.query(arrayIndexName, arrayTypeName,listServ,inMap);
		}
		 
		long totalNum=0;
		List<DataSearchResultItem> list=null;
		if(RespCodeContents.SUCCESS.equals(esResult.getRespCode())){
			Map<String,Object> esDataMap = esResult.getArgs();
			totalNum = (long) esDataMap.get("total");//总条数
			list = (List<DataSearchResultItem>) esDataMap.get("data");
		}

		List<ServOrderListESOutVo> listOut=new ArrayList<ServOrderListESOutVo>();

		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map<String,Object> servList=(Map<String,Object>)list.get(i).getValues().get("info_service_list");
				ServOrderListESOutVo servOrderListESOutVoTemp=new ServOrderListESOutVo();
				BeanUtils.populate(servOrderListESOutVoTemp, servList);
				String esStr=servOrderListESOutVoTemp.getCreate_time();
				Date dt=EsDateUtil.strToDate(esStr);
				servOrderListESOutVoTemp.setCreate_time(DateUtil.chngDateString(dt, "yyyy-MM-dd HH:mm:ss"));
				listOut.add(servOrderListESOutVoTemp);
			}
		}

		Map<String,Object> map=new HashMap<String,Object>();
		map.put("page_no", page_no);
		map.put("page_size", page_size);
		map.put("total_num", totalNum);
		map.put("info_serv_order", listOut);

		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("订单列表查询成功");
		message.addArg("info_serv_order_list", map);
		return message;
	}


	//转换javaBean方法
	 /*public static <T> T map2Java(T javaBean, Map<String,Object> map) {
		    try {
		      // 获取javaBean属性
		      BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());
		      // 创建 JavaBean 对象
		      Object obj = javaBean.getClass().newInstance();

		      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		      if (propertyDescriptors != null && propertyDescriptors.length > 0) {
		        String propertyName = null; // javaBean属性名
		        Object propertyValue = null; // javaBean属性值
		        for (PropertyDescriptor pd : propertyDescriptors) {
		          propertyName = pd.getName();
		          if (map.containsKey(propertyName)) {
		            propertyValue = map.get(propertyName);
		            pd.getWriteMethod().invoke(obj, new Object[] { propertyValue });
		          }
		        }
		        return (T) obj;
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		    }

		    return null;
		  }*/
	 /**
	  * UOC0076 订单明细报表
	  */
	@SuppressWarnings("unchecked")
	@Override
	public UocMessage queryOrderDetailReport(LogisticsReportVo vo) throws Exception {
		UocMessage message=new UocMessage();

		if("".equals(vo.getJsession_id())||vo.getJsession_id()==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("效验登录信息失败");
			return message;
		}
		if("".equals(vo.getPage_no())||vo.getPage_no()==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("page_no不能为空");
			return message;
		}
		if("".equals(vo.getPage_size())||vo.getPage_size()==null){
			message.setRespCode(RespCodeContents.PARAM_ERROR);
			message.setContent("page_size不能为空");
			return message;
		}

		UocMessage loginMessage=operServDu.isLogin((vo.getJsession_id()));
		if(!"0000".equals(loginMessage.getRespCode().toString())){
			message.setRespCode(RespCodeContents.SERVICE_FAIL);
			message.setContent("效验登录信息失败");
			return message;
		}

		String accept_time_start=vo.getAccept_time_start();
		String accept_time_end=vo.getAccept_time_end();
		Map<String,Object> oper_info=(Map<String, Object>) loginMessage.getArgs().get("oper_info");
		String province_code=oper_info.get("province_code").toString();
		String area_code=oper_info.get("area_code").toString();
		Date date=new Date();
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
		String month=sdf1.format(date).substring(4,6).trim();
		String indexName=propertiesParamVo.getIndexName();
		String typeName=month;

		Map<String,Object> inMap=new HashMap<String,Object>();
		int page_no=Integer.parseInt(vo.getPage_no());
		int page_size=Integer.parseInt(vo.getPage_size());
		int page_from=page_size * (page_no-1);
		inMap.put("size", vo.getPage_size());
		inMap.put("from", page_from);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		//取服务订单
		List<Map<String,Object>> listServ=new ArrayList<Map<String,Object>>();
		listServ.add(getEsQueryMap("info_service_order","province_code",province_code,null,null,"no"));
		listServ.add(getEsQueryMap("info_service_order","area_code",area_code,null,null,"no"));
		listServ.add(getEsQueryMap("info_service_order","create_time",null,accept_time_start,accept_time_end,"yes"));

		/*InfoServiceOrderVo infoServiceOrderVo=new InfoServiceOrderVo();
		//客户
		InfoServiceOrderPersionVo infoServiceOrderPersionVo =new InfoServiceOrderPersionVo();
		List<InfoServiceOrderPersionVo> infoServiceOrderPersionList=new ArrayList<InfoServiceOrderPersionVo>();
		//发展人
		InfoServiceOrderDeveloperVo infoServiceOrderDeveloperVo =new InfoServiceOrderDeveloperVo();
		List<InfoServiceOrderDeveloperVo> infoServiceOrderDeveloperList=new ArrayList<InfoServiceOrderDeveloperVo>();
		//产品
		InfoServiceOrderProdMapVo infoServiceOrderProdMapVo =new InfoServiceOrderProdMapVo();
		List<InfoServiceOrderProdMapVo> infoServiceOrderProdMapList=new ArrayList<InfoServiceOrderProdMapVo>();*/
		List<DataSearchResultItem> esDataResultlist=null;
		UocMessage serv=esServiceServDu.query(indexName, listServ,inMap);
		if(RespCodeContents.SUCCESS.equals(serv.getRespCode())){
			esDataResultlist=(List<DataSearchResultItem>) serv.getArgs().get("data");
		}else{
			Map<String, Object> map=new HashMap<String,Object>();
			map.put("page_no", page_no);
			map.put("page_size", page_size);
			map.put("total_num", 0);
			map.put("page_total", 0);
			map.put("servOrderList", null);
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("操作成功");
			message.addArg("json_info", map);
			return message;
		}

		String total=serv.getArgs().get("total").toString();
		int totalNum=Integer.parseInt(total);
		int pageTotal=0;
		if(totalNum!=0){
			if(totalNum % page_size==0){
				pageTotal=totalNum/page_size;
			}else{
				pageTotal=totalNum/page_size + 1;
			}
		}

		//解析es数据
		List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
		if(esDataResultlist != null && esDataResultlist.size()>0){
//			List<InfoServiceOrderVo> infoServiceOrderList=new ArrayList<InfoServiceOrderVo>();
//			List<InfoServiceOrderPersionVo> infoServiceOrderPersionList=new ArrayList<InfoServiceOrderPersionVo>();
//			List<InfoServiceOrderDeveloperVo> infoServiceOrderDeveloperList=new ArrayList<InfoServiceOrderDeveloperVo>();
//			List<InfoServiceOrderProdMapVo> infoServiceOrderProdMapList=new ArrayList<InfoServiceOrderProdMapVo>();
			for(int i=0;i<esDataResultlist.size();i++){

				List<InfoServiceOrderVo> infoServiceOrderList=new ArrayList<InfoServiceOrderVo>();
				List<InfoServiceOrderPersionVo> infoServiceOrderPersionList=new ArrayList<InfoServiceOrderPersionVo>();
				List<InfoServiceOrderDeveloperVo> infoServiceOrderDeveloperList=new ArrayList<InfoServiceOrderDeveloperVo>();
				List<InfoServiceOrderProdMapVo> infoServiceOrderProdMapList=new ArrayList<InfoServiceOrderProdMapVo>();

				Map<String,Object> dataMap = new HashMap<String,Object>();
				//服务订单
				List<Map<String,Object>> infoServiceOrderEsList=(List<Map<String,Object>>)esDataResultlist.get(i).getValues().get("info_service_order");
				InfoServiceOrderVo infoServiceOrderVoTemp = new InfoServiceOrderVo();
				if(infoServiceOrderEsList!=null && infoServiceOrderEsList.size()>0){
					BeanUtils.populate(infoServiceOrderVoTemp, infoServiceOrderEsList.get(0));
				}
				String create_time = infoServiceOrderVoTemp.getCreate_time();
				String finish_time = infoServiceOrderVoTemp.getFinish_time();
				Date dt=EsDateUtil.strToDate(create_time);
				if(finish_time!=null && !"".equals(finish_time)){
					Date dt1=EsDateUtil.strToDate(finish_time);
					infoServiceOrderVoTemp.setFinish_time(DateUtil.chngDateString(dt1, "yyyy-MM-dd HH:mm:ss"));
				}

				infoServiceOrderVoTemp.setCreate_time(DateUtil.chngDateString(dt, "yyyy-MM-dd HH:mm:ss"));

				//服务订单客户信息表
				List<Map<String,Object>> infoServiceOrderPersionEsList=(List<Map<String,Object>>)esDataResultlist.get(i).getValues().get("info_serv_person");
				InfoServiceOrderPersionVo infoServiceOrderPersionVoTemp= new InfoServiceOrderPersionVo();
				if(infoServiceOrderPersionEsList !=null && infoServiceOrderPersionEsList.size()>0){
					BeanUtils.populate(infoServiceOrderPersionVoTemp, infoServiceOrderPersionEsList.get(0));
				}
				//发展人
				List<Map<String,Object>> infoServiceOrderDeveloperEsList=(List<Map<String,Object>>)esDataResultlist.get(i).getValues().get("info_serv_developer");
				InfoServiceOrderDeveloperVo infoServiceOrderDeveloperVoTemp = new InfoServiceOrderDeveloperVo();
				if(infoServiceOrderDeveloperEsList !=null && infoServiceOrderDeveloperEsList.size()>0){
					BeanUtils.populate(infoServiceOrderDeveloperVoTemp, infoServiceOrderDeveloperEsList.get(0));
				}
				//产品
				List<Map<String,Object>> infoServiceOrderProdMapEsList=(List<Map<String,Object>>)esDataResultlist.get(i).getValues().get("info_serv_prod_map");
				InfoServiceOrderProdMapVo infoServiceOrderProdMapVoTemp = new InfoServiceOrderProdMapVo();
				if(infoServiceOrderProdMapEsList !=null && infoServiceOrderProdMapEsList.size()>0){
					BeanUtils.populate(infoServiceOrderProdMapVoTemp, infoServiceOrderProdMapEsList.get(0));
				}

				infoServiceOrderList.add(infoServiceOrderVoTemp);
				infoServiceOrderPersionList.add(infoServiceOrderPersionVoTemp);
				infoServiceOrderDeveloperList.add(infoServiceOrderDeveloperVoTemp);
				infoServiceOrderProdMapList.add(infoServiceOrderProdMapVoTemp);

				dataMap.put("infoServiceOrderList",infoServiceOrderList);
				dataMap.put("infoServiceOrderPersionList", infoServiceOrderPersionList);
				dataMap.put("infoServiceOrderDeveloperList", infoServiceOrderDeveloperList);
				dataMap.put("infoServiceOrderProdMapList", infoServiceOrderProdMapList);
				dataList.add(dataMap);
			}
			Map<String, Object> map=new HashMap<String,Object>();
			map.put("page_no", page_no);
			map.put("page_size", page_size);
			map.put("total_num", totalNum);
			map.put("page_total", pageTotal);
			map.put("servOrderList", dataList);
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("操作成功");
			message.addArg("json_info", map);
			return message;
		}
		else{
			Map<String, Object> map=new HashMap<String,Object>();
			map.put("page_no", page_no);
			map.put("page_size", page_size);
			map.put("total_num", 0);
			map.put("page_total", 0);
			map.put("servOrderList", null);
			message.setRespCode(RespCodeContents.SUCCESS);
			message.setContent("操作成功");
			message.addArg("json_info", map);
			return message;
		}
		//查出所有的服务订单列表
		/*List<String> idList=new ArrayList<String>();
		if(list1!=null&&list1.size()>0){
			for(int i=0;i<list1.size();i++){
				List<Map<String,Object>> serv_order_list=(List<Map<String,Object>>)list1.get(i).getValues().get("info_service_order");
				if(serv_order_list!=null && serv_order_list.size()>0){
					Map<String,Object> mapTemp=serv_order_list.get(0);
					String serv_order_no=mapTemp.get("serv_order_no").toString();
					idList.add(serv_order_no);
				}
			}
		}else{
			message.setContent("无服务订单信息");
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}*/

		/*List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(serv_order_no_list!=null && serv_order_no_list.size()>0){
			for(String serv_order_no:serv_order_no_list){
				Map<String,Object> json_out = new HashMap<String,Object>();
				json_out.put("serv_order_no", serv_order_no);
				//服务订单
				//listServ=getList1("info_order",serv_order_no);
				List<InfoServiceOrderVo> infoServiceOrderList=new ArrayList<InfoServiceOrderVo>();
				List<DataSearchResultItem> list_serv=null;

				//UocMessage serv1=esServiceServDu.query(indexName, typeName, listServ,inMap);
				UocMessage serv1=esServiceServDu.query(indexName, listServ,inMap);
				if(RespCodeContents.SUCCESS.equals(serv1.getRespCode())){
					list_serv=(List<DataSearchResultItem>) serv1.getArgs().get("data");
				}
				if(list_serv!=null&&list_serv.size()>0){
					//服务订单
					List<Map<String,Object>> info_service_order_list=(List<Map<String,Object>>)list_serv.get(0).getValues().get("info_service_order");
					if(info_service_order_list!=null && info_service_order_list.size()>0){
						Map<String,Object> mapTemp=info_service_order_list.get(0);
//						InfoServiceOrderVo infoServiceOrderTemp=map2Java(infoServiceOrderVo,mapTemp);
						InfoServiceOrderVo infoServiceOrderTemp = new InfoServiceOrderVo();
						BeanUtils.populate(infoServiceOrderTemp, mapTemp);
						String esStr=infoServiceOrderTemp.getCreate_time();
						Date createTime=EsDateUtil.strToDate(esStr);
						String createStr=sdf.format(createTime);
						infoServiceOrderTemp.setCreate_time(createStr);
						infoServiceOrderList.add(infoServiceOrderTemp);

//						//取服务订单客户信息表
//						InfoServiceOrderPersionVo infoServiceOrderPersionTemp=map2Java(infoServiceOrderPersionVo,mapTemp);
//						infoServiceOrderPersionList.add(infoServiceOrderPersionTemp);
//
//						//发展人
//						InfoServiceOrderDeveloperVo infoServiceOrderDeveloperTemp=map2Java(infoServiceOrderDeveloperVo,mapTemp);
//						infoServiceOrderDeveloperList.add(infoServiceOrderDeveloperTemp);
//
//						//产品
//						InfoServiceOrderProdMapVo infoServiceOrderProdMapTemp=map2Java(infoServiceOrderProdMapVo,mapTemp);
//						infoServiceOrderProdMapList.add(infoServiceOrderProdMapTemp);
					}
					//取服务订单客户信息表
//					List<Map<String,Object>> infoServiceOrderPersionList=(List<Map<String,Object>>)list_serv.get(0).getValues().get("info_service_order");
//					if()
					json_out.put("infoServiceOrderList", infoServiceOrderList);
					json_out.put("infoServiceOrderPersionList", infoServiceOrderPersionList);
					json_out.put("infoServiceOrderDeveloperList", infoServiceOrderDeveloperList);
					json_out.put("infoServiceOrderProdMapList", infoServiceOrderProdMapList);

				}else{
					message.setContent("无服务订单信息");
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无服务订单信息<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				}

				list.add(json_out);
			}
		}

		Map<String, Object> map=new HashMap<String,Object>();
		map.put("page_no", page_no);
		map.put("page_size", page_size);
		map.put("total_num", totalNum);
		map.put("page_total", pageTotal);
		map.put("servOrderList", list);
		//map.put(" ", listOut);
		message.setRespCode(RespCodeContents.SUCCESS);
		message.setContent("操作成功");
		message.addArg("json_info", map);
		return message;*/
	}

	//封装es单个查询条件
	//TODO
	public static Map<String,Object> getEsQueryMap(String tableName,String fieldName,String fieldValue,String fromTime,String toTime,String isDate) throws Exception{
		Map<String,Object> result = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(isDate) && "yes".equals(isDate)){
			long time_from = 0;
			long time_to = 0;
			if(!StringUtils.isEmpty(fromTime)){
				time_from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fromTime).getTime()+8*60*60*1000;
			}
			else{
				time_from = new Date().getTime()+8*60*60*1000;
			}
			if(!StringUtils.isEmpty(toTime)){
				time_to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(toTime).getTime()+8*60*60*1000;
			}
			else{
				time_to = new Date().getTime()+8*60*60*1000;
			}
			result.put("isDateTime", isDate);
			result.put("paramName", tableName+"."+fieldName);
			result.put("paramValueFrom", time_from);
			result.put("paramValueTo", time_to);
		}
		else{
			result.put("isDateTime", "no");
			result.put("paramName", tableName+"."+fieldName);
			result.put("paramValue", fieldValue);
		}
		return result;
	}
	//组合List<map>
	/*public static List<Map<String,Object>> getList(String tableName,String province_code,String area_code,String accetp_time_start,String accept_time_end) throws Exception{
		Map<String,Object> map1=new HashMap<String,Object>();
		Map<String,Object> map2=new HashMap<String,Object>();
		Map<String,Object> map3=new HashMap<String,Object>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		map1.put("isDateTime", "no");
		map1.put("paramName", tableName+".province_code");
		map1.put("paramValue", province_code);
		map2.put("isDateTime", "no");
		map2.put("paramName", tableName+".area_code");
		map2.put("paramValue", area_code);
		list.add(map1);
		list.add(map2);
		if(!"".equals(accetp_time_start) && accetp_time_start!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			Date creat_time=sdf.parse(accetp_time_start);
			long create_time_l=creat_time.getTime();
			long finish_time_l=0;
			if(!"".equals(accept_time_end) && accept_time_end!=null){
				Date finish_time=sdf.parse(accept_time_end);
				finish_time_l=finish_time.getTime();
			}else{
				Date finish_time=new Date();
				finish_time_l=finish_time.getTime();
			}
			if(tableName.equals("info_service_order")){
				map3.put("isDateTime", "yes");
				map3.put("paramName", tableName+".create_time");
				map3.put("paramValueFrom",create_time_l);
				map3.put("paramValueTo",finish_time_l);
				list.add(map3);
			}
		}
		return list;
	}*/

	/*public static List<Map<String,Object>> getList1(String tableName,String order_no){
		Map<String,Object> map=new HashMap<String,Object>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		map.put("isDateTime", "no");
		map.put("paramName", tableName+".serv_order_no");
		map.put("paramValue", order_no);
		list.add(map);


		return list;
	}*/
	/*
	 * 创建订单详情数据es
	 */
	@Override
	public UocMessage createOrderIntermediateData(String serv_order_no, String order_type, String jsession_id) throws Exception {
		UocMessage message=infoServiceOrderBaseDu.createOrderIntermediateData(serv_order_no, order_type, jsession_id);
		return message;
	}
	@Override
	public UocMessage queryOrderDetailES(String jsession_id,
			String serv_order_no) throws Exception {
		return null;
	}

	private void getBeanDu() {
		if (jsonToBeanServDu == null) {
			jsonToBeanServDu = (JsonToBeanServDu) ToolSpring.getBean("JsonToBeanServDu");
		}
		if (operServDu == null) {
			operServDu = (OperServDu) ToolSpring.getBean("OperServDu");
		}
		if (codeListServ == null) {
			codeListServ = (CodeListServ) ToolSpring.getBean("CodeListServ");
		}
		if (infoDeliverOrderServ == null) {
			infoDeliverOrderServ = (InfoDeliverOrderServ) ToolSpring.getBean("InfoDeliverOrderServ");
		}
		if (esServiceServDu == null) {
			esServiceServDu = (EsServiceServDu) ToolSpring.getBean("EsServiceServDu");
		}
		if (infoDeliverOrderHisServ == null) {
			infoDeliverOrderHisServ = (InfoDeliverOrderHisServ) ToolSpring.getBean("InfoDeliverOrderHisServ");
		}
		if (infoServiceOrderExtServDu == null) {
			infoServiceOrderExtServDu = (InfoServiceOrderExtServDu) ToolSpring.getBean("InfoServiceOrderExtServDu");
		}
		if (infoServiceOrderBaseDu == null) {
			infoServiceOrderBaseDu = (InfoServiceOrderBaseDu) ToolSpring.getBean("InfoServiceOrderBaseDu");
		}
		if (codeListServDu == null) {
			codeListServDu = (CodeListServDu) ToolSpring.getBean("CodeListServDu");
		}
		if (propertiesParamVo == null) {
			propertiesParamVo = (PropertiesParamVo) ToolSpring.getBean("propertiesParamVo");
		}
	}

}
